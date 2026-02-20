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

package io.github.darthakiranihil.konna.entity.asset;

import io.github.darthakiranihil.konna.annotation.core.di.KInject;
import io.github.darthakiranihil.konna.core.io.KAsset;
import io.github.darthakiranihil.konna.core.io.KAssetCollection;
import io.github.darthakiranihil.konna.core.io.KAssetDefinition;
import io.github.darthakiranihil.konna.core.io.KAssetLoader;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.annotation.core.object.KSingleton;
import io.github.darthakiranihil.konna.entity.KEntityDataComponent;
import io.github.darthakiranihil.konna.entity.KEntityMetadata;
import io.github.darthakiranihil.konna.entity.type.KEntityMetadataTypedef;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Collection of shader assets of type
 * {@link KEntityMetadataTypedef#ENTITY_METADATA_ASSET_TYPE}.
 *
 * @since 0.4.0
 * @author Darth Akira Nihil
 */
@KSingleton
public final class KEntityMetadataCollection
    extends KObject
    implements KAssetCollection<KEntityMetadata> {

    private final KAssetLoader assetLoader;
    private final Map<String, KEntityMetadata> loadedMetadata;

    /**
     * Standard constructor.
     * @param assetLoader Asset loader (to load entity metadata)
     */
    public KEntityMetadataCollection(
        @KInject final KAssetLoader assetLoader
    ) {
        this.assetLoader = assetLoader;
        this.loadedMetadata = new HashMap<>();
    }

    @Override
    public KEntityMetadata getAsset(final String assetId) {

        if (this.loadedMetadata.containsKey(assetId)) {
            return this.loadedMetadata.get(assetId);
        }

        KAsset asset = this.assetLoader.loadAsset(
            assetId, KEntityMetadataTypedef.ENTITY_METADATA_ASSET_TYPE
        );
        KAssetDefinition metadataDefinition = asset.definition();

        String typeName = Objects.requireNonNull(metadataDefinition.getString("type_name"));

        String[] dataExtensions = Objects.requireNonNull(
            metadataDefinition.getStringArray("data_extensions")
        );

        Class<? extends KEntityDataComponent>[] dataComponents = metadataDefinition
            .getClassObjectArray("data_components", KEntityDataComponent.class);

        KEntityMetadata metadata = new KEntityMetadata(
            typeName,
            dataExtensions,
            dataComponents
        );
        this.loadedMetadata.put(assetId, metadata);
        return metadata;

    }
}
