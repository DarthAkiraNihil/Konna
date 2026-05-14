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

package io.github.darthakiranihil.konna.core.io;

import io.github.darthakiranihil.konna.core.data.json.KJsonParser;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.except.KInvalidArgumentException;
import io.github.darthakiranihil.konna.core.io.except.KAssetDefinitionError;
import io.github.darthakiranihil.konna.core.io.except.KAssetLoadingException;
import io.github.darthakiranihil.konna.core.log.system.KSystemLogger;
import io.github.darthakiranihil.konna.core.object.KDefaultTags;
import io.github.darthakiranihil.konna.core.object.KObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of {@link KAssetLoader} that uses JSON files to read asset definitions from,
 * but when an asset is loaded, loader performs its definition transformation according to loader's
 * configuration.
 * Each asset's definition must be in a single file, that holds a JSON object with mandatory
 * {@code asset_id} (that must be unique) and {@code type} keys.
 *
 * @since 0.4.0
 * @author Darth Akira Nihil
 */
public class KJsonAssetLoader
    extends KObject
    implements KAssetLoader {

    private enum SchemaVersion {
        VERSION_1 {
            private static final KAssetDefinitionRule RULE = KCompositeAssetDefinitionRuleBuilder
                .create()
                .withNotNullString("asset_id")
                .withNotNullString("type")
                .withSubdefinition("data")
                .build();

            @Override
            protected void validate(final KAssetDefinition definition) {
                RULE.validate(definition);
            }

            @Override
            protected KAsset constructAsset(final KAssetDefinition definition) {
                return new KAsset(
                    Objects.requireNonNull(definition.getString("asset_id")),
                    Objects.requireNonNull(definition.getString("type")),
                    definition.getSubdefinition("data")
                );
            }
        };

        static SchemaVersion getSchemaVersion(int value) {
            if (value == 1) {
                return VERSION_1;
            }

            throw new KInvalidArgumentException(String.format(
                "Unknown version of asset metafile schema: %d",
                value
            ));
        }

        public final KAsset makeAsset(final KAssetDefinition definition) {
            this.validate(definition);
            return this.constructAsset(definition);
        }

        protected abstract void validate(KAssetDefinition definition);

        protected abstract KAsset constructAsset(KAssetDefinition definition);
    }

    private final Map<String, KAsset> loadedAssets;
    private final Map<String, KAssetTypedef> assetTypedefs;

    /**
     * Standard constructor.
     *
     * @param resourceLoader Resource loader (to load JSON files)
     * @param jsonParser     JSON parser to parse loaded definitions
     * @param pathsToAssets  Paths to directories to look definitions in
     */
    public KJsonAssetLoader(
        final KResourceLoader resourceLoader,
        final KJsonParser jsonParser,
        final String[] pathsToAssets
    ) {
        super("JsonAssetLoader", Set.of(KDefaultTags.STD, KDefaultTags.SYSTEM));

        this.loadedAssets = new HashMap<>();

        int loadedAssetsCount = 0;

        for (String pathToAssets : pathsToAssets) {
            KResource[] assetsResources = resourceLoader.loadResources(pathToAssets, true);
            for (KResource assetResource : assetsResources) {
                if (!assetResource.name().endsWith(".json")) {
                    assetResource.close();
                    continue;
                }

                try (assetResource) {
                    InputStream stream = assetResource.stream();
                    if (stream == null) {
                        throw new KAssetLoadingException(
                            String.format(
                                "Cannot read stream from asset resource %s",
                                assetResource.name()
                            )
                        );
                    }

                    KJsonValue jsonDefinition = jsonParser.parse(stream);
                    KAssetDefinition def = new KJsonAssetDefinition(jsonDefinition);
                    if (!def.hasInt("schema_version")) {
                        throw new KAssetLoadingException(
                            String.format(
                                "Could not read metafile schema version of %s",
                                assetResource.name()
                            )
                        );
                    }

                    int schemaVersionValue = def.getInt("schema_version");
                    SchemaVersion schemaVersion = SchemaVersion.getSchemaVersion(
                        schemaVersionValue
                    );
                    KAsset loaded = schemaVersion.makeAsset(def);
                    if (this.loadedAssets.containsKey(loaded.getId())) {
                        throw new KAssetLoadingException(String.format(
                            "Cannot load asset with id %s: asset id is not unique",
                            loaded.getId()
                        ));
                    }

                    this.loadedAssets.put(loaded.getId(), loaded);
                    loadedAssetsCount++;
                }
            }
        }

        KSystemLogger.info(this.name, "Loaded %d assets", loadedAssetsCount);

        this.assetTypedefs = new HashMap<>();

    }

    // todo: fallback assets
    @Override
    public KAsset loadAsset(final String assetId, final String typeAlias) {
        KAsset asset = this.loadedAssets.get(assetId);
        if (asset == null) {
            throw new KAssetLoadingException(String.format("Unknown asset: %s", assetId));
        }
        if (!asset.getType().equals(typeAlias)) {
            throw new KAssetLoadingException(String.format(
                "Expected for asset %s to have type %s, but got %s",
                assetId,
                typeAlias,
                asset.getType()
            ));
        }

        return asset;
    }

    @Override
    public void addAssetTypedef(final KAssetTypedef typedef) {
        this.assetTypedefs.put(typedef.getName(), typedef);

        KAssetDefinitionRule rule = typedef.getRule();
        String typeName = typedef.getName();

        for (KAsset asset : this.loadedAssets.values()) {
            if (asset.getType().equals(typeName)) {
                rule.validate(asset);
            }
        }
    }

    @Override
    public void addNewAsset(
        final KAsset asset
    ) {

        String type = asset.getType();
        String assetId = asset.getId();

        KAssetTypedef typedef = this.assetTypedefs.get(asset.getType());

        if (typedef == null) {
            throw new KAssetLoadingException(String.format(
                "Cannot add asset %s - type %s is not registered in the asset loader",
                assetId,
                type
            ));
        }

        try {
            typedef.getRule().validate(asset);
        } catch (Throwable e) {
            throw new KAssetDefinitionError(e.getMessage());
        }

        this.loadedAssets.put(assetId, asset);
    }

}
