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

    public record Config(
        @KJsonCustomName(name = ASSET_DEFINITIONS_PATHS_KEY)
        @KJsonArray(elementType = String.class)
        String[] assetDefinitionsPaths,

        @KJsonCustomName(name = ASSET_TYPES_DEFINITIONS_KEY)
        Map<String, String[]> assetTypesDefinitions
    ) {

        private static final String ASSET_DEFINITIONS_PATHS_KEY = "asset_definitions_paths";
        private static final String ASSET_TYPES_DEFINITIONS_KEY = "asset_types";

    }

    private final Map<String, KJsonValidator> typeAliasesSchemas;
    private final Map<String, String[]> assetTypesDefinitions;
    private final KResourceLoader resourceLoader;
    private final KJsonParser jsonParser;

    private final Map<String, Map<String, KJsonValue>> loadedRawAssetDefinitions;

    public KJsonAssetLoader(
        final KResourceLoader resourceLoader,
        final Map<String, AssetTypeData> assetTypesData,
        final Config config,
        final KJsonParser jsonParser
    ) {

        this.resourceLoader = resourceLoader;
        this.jsonParser = jsonParser;
        this.assetTypesDefinitions = config.assetTypesDefinitions;

        this.typeAliasesSchemas = new HashMap<>();
        this.loadedRawAssetDefinitions = new HashMap<>();

        for (var entry: assetTypesData.entrySet()) {

            Map<String, KJsonValue> loadedDefinitionsOfType = new HashMap<>();

            AssetTypeData data = entry.getValue();
            KJsonValidator baseValidator = this.buildBaseValidator(data.aliases);

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
