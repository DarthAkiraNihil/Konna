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

package io.github.darthakiranihil.konna.graphics.asset;

import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.io.KAsset;
import io.github.darthakiranihil.konna.core.io.KAssetCollection;
import io.github.darthakiranihil.konna.core.io.KAssetDefinition;
import io.github.darthakiranihil.konna.core.io.KAssetLoader;
import io.github.darthakiranihil.konna.core.io.except.KAssetLoadingException;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.graphics.image.KRenderableTexture;
import io.github.darthakiranihil.konna.graphics.image.KRenderableTextureSource;
import io.github.darthakiranihil.konna.graphics.image.KTexture;
import io.github.darthakiranihil.konna.graphics.image.KTextureSliceSet;
import io.github.darthakiranihil.konna.graphics.type.KRenderableTextureTypedef;

import java.util.Objects;

public final class KRenderableTextureCollection implements KAssetCollection<KRenderableTexture> {

    private final KAssetLoader assetLoader;

    private final KTextureSliceSetCollection textureSliceSetCollection;
    private final KTextureCollection textureCollection;

    public KRenderableTextureCollection(
        @KInject final KAssetLoader assetLoader,
        @KInject final KTextureCollection textureCollection,
        @KInject final KTextureSliceSetCollection textureSliceSetCollection
    ) {
        this.assetLoader = assetLoader;
        this.textureCollection = textureCollection;
        this.textureSliceSetCollection = textureSliceSetCollection;
    }

    @Override
    public KRenderableTexture getAsset(final String assetId) {
        return this.getAsset(assetId, KVector2i.ZERO, 0);
    }

    public KRenderableTexture getAsset(final String assetId, int unit) {
        return this.getAsset(assetId, KVector2i.ZERO, unit);
    }

    public KRenderableTexture getAsset(final String assetId, final KVector2i[] xy) {
        return this.getAsset(assetId, xy, 0);
    }

    public KRenderableTexture getAsset(final String assetId, final KVector2i[] xy, int unit) {
        KAsset asset = this.assetLoader.loadAsset(assetId, KRenderableTextureTypedef.RENDERABLE_TEXTURE_ASSET_TYPE);
        KAssetDefinition definition = asset.definition();

        KRenderableTextureSource source = definition.getEnum("source", KRenderableTextureSource.class);
        switch (source) {
            case WHOLE_TEXTURE -> {
                KTexture texture = this.textureCollection.getAsset(assetId);
                return KRenderableTexture.wrapIntoRectangle(
                    KVector2i.ZERO, texture, unit
                );
            }
            case SLICE_SET -> {
                String sliceSetId = Objects.requireNonNull(definition.getString("slice_set"));
                KTextureSliceSet set = this.textureSliceSetCollection.getAsset(sliceSetId);
                return set.getTexture(assetId, xy, unit);
            }
        }

        throw new KAssetLoadingException(
            String.format("Unknown source: %s", source)
        );
    }

    public KRenderableTexture getAsset(final String assetId, final KVector2i topLeftCorner) {
        return this.getAsset(assetId, topLeftCorner, 0);
    }

    public KRenderableTexture getAsset(final String assetId, final KVector2i topLeftCorner, int unit) {
        KAsset asset = this.assetLoader.loadAsset(assetId, KRenderableTextureTypedef.RENDERABLE_TEXTURE_ASSET_TYPE);
        KAssetDefinition definition = asset.definition();

        KRenderableTextureSource source = definition.getEnum("source", KRenderableTextureSource.class);
        switch (source) {
            case WHOLE_TEXTURE -> {
                KTexture texture = this.textureCollection.getAsset(assetId);
                return KRenderableTexture.wrapIntoRectangle(
                    topLeftCorner, texture, unit
                );
            }
            case SLICE_SET -> {
                String sliceSetId = Objects.requireNonNull(definition.getString("slice_set"));
                KTextureSliceSet set = this.textureSliceSetCollection.getAsset(sliceSetId);
                return set.getTexture(assetId, unit);
            }
        }

        throw new KAssetLoadingException(
            String.format("Unknown source: %s", source)
        );
    }

}
