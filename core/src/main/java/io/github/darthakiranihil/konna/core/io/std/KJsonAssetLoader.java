/*
 * Copyright 2025-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.darthakiranihil.konna.core.io.std;

import io.github.darthakiranihil.konna.core.data.json.*;
import io.github.darthakiranihil.konna.core.data.json.except.KJsonParseException;
import io.github.darthakiranihil.konna.core.data.json.except.KJsonValidationError;
import io.github.darthakiranihil.konna.core.data.json.std.KJsonObjectValidator;
import io.github.darthakiranihil.konna.core.io.*;
import io.github.darthakiranihil.konna.core.io.except.KAssetLoadingException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class KJsonAssetLoader implements KAssetLoader {

    public record AssetTypeData(
        @KJsonArray(elementType = String.class)
        String[] aliases,

        @KJsonArray(elementType = String.class)
        String[] paths
    ) {

    }

    private final Map<String, KJsonValidator> typeAliasesSchemas;
    private final Map<String, KJsonValidator> fullAssetDefinitionsValidators;

    private final KResourceLoader resourceLoader;
    private final KJsonParser jsonParser;

    private final Map<String, KJsonAssetLoader.AssetTypeData> assetTypeData;
    private final Map<String, KAssetType> builtAssetTypes;
    private final Map<String, String> reverseAssetTypeMap;

    private final Map<String, Map<String, KJsonValue>> loadedRawAssetDefinitions;

    public KJsonAssetLoader(
        final KResourceLoader resourceLoader,
        final Map<String, AssetTypeData> assetTypesData,
        final KJsonParser jsonParser
    ) {

        this.resourceLoader = resourceLoader;
        this.jsonParser = jsonParser;

        this.typeAliasesSchemas = new HashMap<>();
        this.fullAssetDefinitionsValidators = new HashMap<>();

        this.loadedRawAssetDefinitions = new HashMap<>();

        this.assetTypeData = new HashMap<>();
        this.builtAssetTypes = new HashMap<>();
        this.reverseAssetTypeMap = new HashMap<>();

        for (var entry: assetTypesData.entrySet()) {

            AssetTypeData data = entry.getValue();
            for (String alias: data.aliases) {
                this.reverseAssetTypeMap.put(alias, entry.getKey());
            }

            String internalType = entry.getKey();
            this.loadedRawAssetDefinitions.put(
                internalType,
                this.loadRawDefinitionsOfType(
                    internalType,
                    entry.getValue()
                )
            );

            this.builtAssetTypes.put(
                internalType,
                new KAssetType(internalType)
            );

        }

    }

    @Override
    public KAsset loadAsset(String assetId, String typeAlias) {

        String internalType = this.reverseAssetTypeMap.get(typeAlias);
        if (internalType == null) {
            throw new KAssetLoadingException(
                String.format(
                    "Cannot load asset %s - type alias %s not known",
                    assetId,
                    typeAlias
                )
            );
        }

        KAssetType builtType = this.builtAssetTypes.get(internalType);
        KJsonValue raw = this
            .loadedRawAssetDefinitions
            .get(internalType)
            .get(assetId)
            .getProperty(typeAlias);
        KJsonValidator validator = this.typeAliasesSchemas.get(typeAlias);

        return new KAsset(
            assetId,
            builtType,
            typeAlias,
            new KJsonAssetDefinition(raw, validator)
        );

    }

    @Override
    public void addAssetTypeAlias(String typeAlias, KJsonValidator schema) {

        this.typeAliasesSchemas.put(typeAlias, schema);
        String internalType = this.reverseAssetTypeMap.get(typeAlias);
        if (internalType == null) {
            return;
        }

        var loadedAssets = this.loadedRawAssetDefinitions.get(internalType);
        for (var rawAssetDefinitionEntry: loadedAssets.entrySet()) {
            KJsonValue rawAssetDefinition = rawAssetDefinitionEntry.getValue();
            schema.validate(rawAssetDefinition.getProperty(typeAlias));
        }

    }

    @Override
    public void addNewAsset(String assetId, String internalType, KJsonValue rawDefinition) {

        var data = this.assetTypeData.get(internalType);
        if (data == null) {
            throw new KAssetLoadingException(
                String.format(
                    "Cannot add asset %s - internal type %s is not registered in the asset loader",
                    assetId,
                    internalType
                )
            );
        }

        if (!this.fullAssetDefinitionsValidators.containsKey(internalType)) {
            this.fullAssetDefinitionsValidators.put(
                internalType,
                this.buildFullValidator(data.aliases)
            );
        }

        KJsonValidator validator = this.fullAssetDefinitionsValidators.get(internalType);
        try {
            validator.validate(rawDefinition);
        } catch (KJsonValidationError e) {
            throw new KAssetLoadingException(e);
        }

        Map<String, KJsonValue> assetsOfType = this.loadedRawAssetDefinitions.get(internalType);
        assetsOfType.put(assetId, rawDefinition);

    }

    private Map<String, KJsonValue> loadRawDefinitionsOfType(
        final String type,
        final AssetTypeData data
    ) {
        Map<String, KJsonValue> loadedDefinitionsOfType = new HashMap<>();

        KJsonValidator baseValidator = this.buildBaseValidator(data.aliases);
        for (String alias: data.aliases) {
            this.reverseAssetTypeMap.put(alias, type);
        }

        for (String path: data.paths) {

            try (KResource resource = this.resourceLoader.loadResource(path)) {

                KJsonValue parsed = this.jsonParser.parse(resource.stream());

                for (var dataEntry: parsed.entrySet()) {
                    KJsonValue rawAssetDefinition = dataEntry.getValue();
                    baseValidator.validate(rawAssetDefinition);

                    loadedDefinitionsOfType.put(
                        dataEntry.getKey(),
                        rawAssetDefinition
                    );
                }

            } catch (IOException | KJsonParseException e) { //TODO
                throw new RuntimeException(e);
            }

        }

        return loadedDefinitionsOfType;
    }

    private KJsonValidator buildBaseValidator(
        final String[] aliases
    ) {
        var infos = new KJsonPropertyValidationInfo[aliases.length];
        var builder = new KJsonPropertyValidationInfo.Builder();

        for (int i = 0; i < aliases.length; i++) {
            infos[i] = builder
                .withName(aliases[i])
                .withExpectedType(KJsonValueType.OBJECT)
                .build();
        }

        return new KJsonObjectValidator(infos);
    }

    private KJsonValidator buildFullValidator(
        final String[] aliases
    ) {

        var infos = new KJsonPropertyValidationInfo[aliases.length];
        var builder = new KJsonPropertyValidationInfo.Builder();

        for (int i = 0; i < aliases.length; i++) {
            infos[i] = builder
                .withName(aliases[i])
                .withExpectedType(KJsonValueType.OBJECT)
                .withValidator(this.typeAliasesSchemas.get(aliases[i]))
                .build();
        }

        return new KJsonObjectValidator(infos);

    }


}
