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
import io.github.darthakiranihil.konna.core.io.*;
import io.github.darthakiranihil.konna.core.io.except.KAssetDefinitionError;
import io.github.darthakiranihil.konna.core.io.except.KAssetLoadingException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link KAssetLoader} that uses JSON files to read asset definitions from.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public class KJsonSubtypeBasedAssetLoader implements KAssetLoader {

    /**
     * Representation of internal (defined inside application) asset type data.
     * @param aliases Type aliases (usually types of assets, required by components)
     * @param paths Paths to asset definition files for this type
     */
    public record AssetTypeData(
        @KJsonArray(elementType = String.class)
        String[] aliases,

        @KJsonArray(elementType = String.class)
        String[] paths
    ) {

    }

    private final Map<String, KAssetTypedef> typeAliasesDefinitions;
    private final Map<String, KAssetDefinitionRule> fullAssetDefinitionsValidators;

    private final KResourceLoader resourceLoader;
    private final KJsonParser jsonParser;

    private final Map<String, KJsonSubtypeBasedAssetLoader.AssetTypeData> assetTypeData;
    private final Map<String, KAssetType> builtAssetTypes;
    private final Map<String, String> reverseAssetTypeMap;

    private final Map<String, Map<String, KAssetDefinition>> loadedRawAssetDefinitions;

    /**
     * Standard constructor. Loads all passed asset definitions and validates them in
     * base way (all asset definitions should be a json object with top-level keys of type aliases,
     * connected with internal type).
     * @param resourceLoader Resource loader to use to load definition files
     * @param assetTypesData Asset types data
     * @param jsonParser Json parser to parse definitions
     */
    public KJsonSubtypeBasedAssetLoader(
        final KResourceLoader resourceLoader,
        final Map<String, AssetTypeData> assetTypesData,
        final KJsonParser jsonParser
    ) {

        this.resourceLoader = resourceLoader;
        this.jsonParser = jsonParser;

        this.typeAliasesDefinitions = new HashMap<>();
        this.fullAssetDefinitionsValidators = new HashMap<>();

        this.loadedRawAssetDefinitions = new HashMap<>();

        this.assetTypeData = assetTypesData;
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
    public KAsset loadAsset(final String assetId, final String typeAlias) {

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
        KAssetDefinition raw = this
            .loadedRawAssetDefinitions
            .get(internalType)
            .get(assetId)
            .getSubdefinition(typeAlias);

        KAssetDefinitionRule validator = this
            .typeAliasesDefinitions
            .get(typeAlias)
            .getRule();

        validator.validate(raw);

        return new KAsset(
            assetId,
            builtType,
            typeAlias,
            raw
        );

    }

    @Override
    public void addAssetTypedef(final KAssetTypedef typedef) {

        String typeName = typedef.getName();
        this.typeAliasesDefinitions.put(typeName, typedef);
        String internalType = this.reverseAssetTypeMap.get(typeName);
        if (internalType == null) {
            return;
        }

        var loadedAssets = this.loadedRawAssetDefinitions.get(internalType);
        KAssetDefinitionRule validator = typedef.getRule();
        for (var rawAssetDefinitionEntry: loadedAssets.entrySet()) {
            KAssetDefinition rawAssetDefinition = rawAssetDefinitionEntry.getValue();
            try {
                validator.validate(rawAssetDefinition.getSubdefinition(typeName));
            } catch (KJsonValidationError e) {
                throw new KAssetLoadingException(e);
            }
        }

    }

    @Override
    public void addNewAsset(
        final String assetId,
        final String internalType,
        final KAssetDefinition rawDefinition
    ) {

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

        KAssetDefinitionRule validator = this.fullAssetDefinitionsValidators.get(internalType);
        try {
            validator.validate(rawDefinition);
        } catch (KJsonValidationError e) {
            throw new KAssetLoadingException(e);
        }

        Map<String, KAssetDefinition> assetsOfType = this.loadedRawAssetDefinitions.get(
            internalType
        );
        assetsOfType.put(assetId, rawDefinition);

    }

    private Map<String, KAssetDefinition> loadRawDefinitionsOfType(
        final String type,
        final AssetTypeData data
    ) {
        Map<String, KAssetDefinition> loadedDefinitionsOfType = new HashMap<>();

        KJsonValidator baseValidator = this.buildBaseValidator(data.aliases);
        for (String alias: data.aliases) {
            this.reverseAssetTypeMap.put(alias, type);
        }

        for (String path: data.paths) {

            try (KResource resource = this.resourceLoader.loadResource(path)) {

                InputStream stream = resource.stream();
                if (stream == null) {
                    throw new KAssetLoadingException(
                        String.format(
                            "Asset definitions file on %s is not found",
                            path
                        )
                    );
                }
                KJsonValue parsed = this.jsonParser.parse(stream);

                for (var dataEntry: parsed.entrySet()) {
                    KJsonValue rawAssetDefinition = dataEntry.getValue();
                    KAssetDefinition def = new KJsonAssetDefinition(
                        rawAssetDefinition,
                        baseValidator
                    );
                    loadedDefinitionsOfType.put(dataEntry.getKey(), def);
                }

            } catch (IOException | KJsonParseException e) {
                throw new KAssetDefinitionError(e.getMessage());
            }

        }

        return loadedDefinitionsOfType;
    }

    private KJsonValidator buildBaseValidator(
        final String[] aliases
    ) {
        var builder = KJsonObjectValidatorBuilder.create();
        for (String alias : aliases) {
            builder.withSimpleField(alias, KJsonValueType.OBJECT);
        }
        return builder.build();
    }

    private KAssetDefinitionRule buildFullValidator(
        final String[] aliases
    ) {

        var builder = KCompositeAssetDefinitionRuleBuilder.create();

        for (String alias: aliases) {
            builder
                .withValidatedSubdefinition(
                    alias,
                    this
                        .typeAliasesDefinitions
                        .get(alias)
                        .getRule()
                );
        }

        return builder.build();

    }
}
