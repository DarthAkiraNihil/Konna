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
import io.github.darthakiranihil.konna.core.data.json.except.KJsonValidationError;
import io.github.darthakiranihil.konna.core.io.*;
import io.github.darthakiranihil.konna.core.io.except.KAssetDefinitionError;
import io.github.darthakiranihil.konna.core.io.except.KAssetLoadingException;
import io.github.darthakiranihil.konna.core.io.except.KIoException;
import io.github.darthakiranihil.konna.core.log.KSystemLogger;

import java.io.IOException;
import java.util.*;

public class KJsonTransformerBasedAssetLoader implements KAssetLoader {

    @FunctionalInterface
    public interface AssetTransformer {

        KAssetDefinition transform(KAssetDefinition value);

    }

    public record AssetTypeData(
        String name,
        Map<String, AssetTransformer> transformersByInternalTypes
    ) {

    }

    private final Map<String, Map<String, AssetTransformer>> assetTypeData;
    private final Map<String, KAssetDefinition> indexedDefinitions;
    private final Map<String, KAssetTypedef> assetTypedefs;

    public KJsonTransformerBasedAssetLoader(
        final KResourceLoader resourceLoader,
        final KJsonParser jsonParser,
        final String pathToAssets,
        final AssetTypeData... assetTypeData
    ) {
        this.assetTypeData = new HashMap<>();
        for (var assetType: assetTypeData) {
            this.assetTypeData.put(
                assetType.name(),
                assetType.transformersByInternalTypes()
            );
        }

        this.indexedDefinitions = new HashMap<>();

        var knownTypes = this.assetTypeData.keySet();
        KJsonValidator baseValidator = KJsonObjectValidatorBuilder
            .create()
            .withSimpleField("asset_id", KJsonValueType.STRING)
            .withField("type", KJsonValueType.STRING)
            .withValidator((v) -> {
                String type = v.getString();
                if (!knownTypes.contains(type)) {
                    throw new KJsonValidationError("Asset definition is not valid");
                }
            })
            .finishField()
            .build();

        KResource[] assetsResources = resourceLoader.loadResources(pathToAssets);

        for (KResource assetResource: assetsResources) {

            if (!assetResource.name().endsWith(".json")) {
                try {
                    assetResource.close();
                } catch (IOException e) {
                    // ...
                }
                continue;
            }

            // base load and parse
            var stream = assetResource.stream();
            if (stream == null) {
                throw new KIoException(
                    String.format(
                        "Cannot read stream from asset resource %s",
                        assetResource.name()
                    )
                );
            }

            KJsonValue jsonDefinition = jsonParser.parse(stream);
            KAssetDefinition def = new KJsonAssetDefinition(jsonDefinition, baseValidator);

            // get asset id and type
            String assetId = jsonDefinition.getProperty("asset_id").getString();
            String type = jsonDefinition.getProperty("type").getString();

            // check if transformers execute successfully for this type
            Map<String, AssetTransformer> transformers = this.assetTypeData.get(type);
            if (this.indexedDefinitions.containsKey(assetId)) {
                throw new KAssetLoadingException(
                    String.format(
                        "Cannot load asset with id %s: asset id is not unique",
                        assetId
                    )
                );
            }

            this.indexedDefinitions.put(
                Objects.requireNonNull(assetId),
                def
            );

            try {
                assetResource.close();
            } catch (IOException e) {
                KSystemLogger.warning("asset_loader", e);
            }
        }

        this.assetTypedefs = new HashMap<>();

    }

    @Override
    public KAsset loadAsset(String assetId, String typeAlias) {

        KAssetDefinition raw = this.indexedDefinitions.get(assetId);
        if (raw == null) {
            throw new KAssetLoadingException(
                String.format(
                    "Cannot load asset: asset with id %s not known",
                    assetId
                )
            );
        }

        String actualType = Objects.requireNonNull(raw.getString("type"));
        AssetTransformer transformer = this.assetTypeData
            .get(actualType)
            .get(typeAlias);

        if (transformer == null) {
            throw new KAssetLoadingException(
                String.format(
                    "Cannot load asset %s - type alias %s not known",
                    assetId,
                    typeAlias
                )
            );
        }

        KAssetDefinition transformed = this
            .assetTypeData
            .get(actualType)
            .get(typeAlias)
            .transform(raw);

        KAssetTypedef typedef = this.assetTypedefs.get(typeAlias);
        typedef.getRule().validate(transformed);

        return new KAsset(
            assetId,
            new KAssetType(actualType),
            typeAlias,
            transformed
        );
    }

    @Override
    public void addAssetTypedef(KAssetTypedef typedef) {
        this.assetTypedefs.put(typedef.getName(), typedef);
        this.validateAssets(typedef.getName(), typedef.getRule());
    }

    @Override
    public void addNewAsset(String assetId, String internalType, KAssetDefinition rawDefinition) {

        var typeData = this.assetTypeData.get(internalType);
        if (typeData == null) {
            throw new KAssetLoadingException(
                String.format(
                    "Cannot add asset %s - internal type %s is not registered in the asset loader",
                    assetId,
                    internalType
                )
            );
        }

        for (var entry: typeData.entrySet()) {
            KAssetTypedef typedef = this.assetTypedefs.get(entry.getKey());
            AssetTransformer transformer = entry.getValue();
            try {
                typedef.getRule().validate(transformer.transform(rawDefinition));
            } catch (Throwable e) {
                throw new KAssetDefinitionError(e.getMessage());
            }
        }

        this.indexedDefinitions.put(assetId, rawDefinition);
    }

    private void validateAssets(final String typeAlias, final KAssetDefinitionRule rule) {

        var affectedTypes = this
            .assetTypeData
            .entrySet()
            .stream()
            .filter(
                x -> x.getValue().containsKey(typeAlias)
            )
            .map(Map.Entry::getKey)
            .toList();

        this
            .indexedDefinitions
            .values()
            .stream()
            .filter(x -> affectedTypes.contains(x.getString("type")))
            .forEach(x -> {
                String type = Objects.requireNonNull(x.getString("type"));
                try {
                    rule
                        .validate(
                            this
                                .assetTypeData
                                .get(type)
                                .get(typeAlias)
                                .transform(x)
                        );
                } catch (Throwable e) {
                    throw new KAssetDefinitionError(e.getMessage());
                }
            });
    }
}
