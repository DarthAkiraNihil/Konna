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
import io.github.darthakiranihil.konna.core.data.json.std.KJsonObjectValidator;
import io.github.darthakiranihil.konna.core.io.KAsset;
import io.github.darthakiranihil.konna.core.io.KAssetLoader;
import io.github.darthakiranihil.konna.core.io.KResource;
import io.github.darthakiranihil.konna.core.io.KResourceLoader;

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
    private final KResourceLoader resourceLoader;
    private final KJsonParser jsonParser;

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
        this.loadedRawAssetDefinitions = new HashMap<>();
        this.reverseAssetTypeMap = new HashMap<>();

        for (var entry: assetTypesData.entrySet()) {


            Map<String, KJsonValue> loadedDefinitionsOfType = new HashMap<>();

            AssetTypeData data = entry.getValue();
            KJsonValidator baseValidator = this.buildBaseValidator(data.aliases);
            for (String alias: data.aliases) {
                this.reverseAssetTypeMap.put(alias, entry.getKey());
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

            this.loadedRawAssetDefinitions.put(
                entry.getKey(),
                loadedDefinitionsOfType
            );

        }

    }

    @Override
    public KAsset loadAsset(String assetId, String typeAlias) {

        return null;

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
}
