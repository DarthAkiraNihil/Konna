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
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.graphics.image.KRenderableTexture;
import io.github.darthakiranihil.konna.graphics.image.KRenderableTextureSource;
import io.github.darthakiranihil.konna.graphics.image.KTexture;
import io.github.darthakiranihil.konna.graphics.image.KTextureSliceSet;
import io.github.darthakiranihil.konna.graphics.type.KRenderableTextureTypedef;

import java.util.Objects;

/**
 * Collection of renderable textures assets of type
 * {@link KRenderableTextureTypedef#RENDERABLE_TEXTURE_ASSET_TYPE}.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
@KSingleton
public final class KRenderableTextureCollection
    extends KObject
    implements KAssetCollection<KRenderableTexture> {

    private final KAssetLoader assetLoader;

    private final KTextureSliceSetCollection textureSliceSetCollection;
    private final KTextureCollection textureCollection;

    /**
     * Standard constructor.
     * @param assetLoader Asset loader
     * @param textureCollection Texture collection to extract the whole textures
     * @param textureSliceSetCollection Texture slice collection to extract sliced textures
     */
    public KRenderableTextureCollection(
        @KInject final KAssetLoader assetLoader,
        @KInject final KTextureCollection textureCollection,
        @KInject final KTextureSliceSetCollection textureSliceSetCollection
    ) {
        super(
            "Graphics.renderableTextureCollection",
            KStructUtils.setOfTags(KTag.DefaultTags.ASSET_COLLECTION)
        );

        this.assetLoader = assetLoader;
        this.textureCollection = textureCollection;
        this.textureSliceSetCollection = textureSliceSetCollection;
    }

    /**
     * Returns a renderable texture on 0-texture-unit and without xys shift.
     * @param assetId Asset id of resulting renderable texture
     * @return Renderable texture with specified id
     */
    @Override
    public KRenderableTexture getAsset(final String assetId) {
        return this.getAsset(assetId, KVector2i.ZERO, 0);
    }

    /**
     * Returns a renderable texture on 0-texture-unit.
     * @param assetId Asset id of resulting renderable texture
     * @param topLeftCorner XY-shift of resulting texture's xys
     * @return Renderable texture with specified id
     */
    public KRenderableTexture getAsset(final String assetId, final KVector2i topLeftCorner) {
        return this.getAsset(assetId, topLeftCorner, 0);
    }

    /**
     * Returns a renderable texture on specific texture unit and xys shift. It handles textures
     * differently, depending on its source (e.g., if the texture source is the whole texture,
     * it will wrap it all into rectangle, which is not performed if source is a slice set).
     * @param assetId Asset id of resulting renderable texture
     * @param topLeftCorner XY-shift of resulting texture's xys
     * @param unit Texture unit of the resulting texture
     * @return Renderable texture with specified id
     */
    public KRenderableTexture getAsset(
        final String assetId,
        final KVector2i topLeftCorner,
        int unit
    ) {
        KAsset asset = this.assetLoader.loadAsset(
            assetId,
            KRenderableTextureTypedef.RENDERABLE_TEXTURE_ASSET_TYPE
        );
        KAssetDefinition definition = asset.definition();

        KRenderableTextureSource source = definition.getEnum(
            "source",
            KRenderableTextureSource.class
        );
        switch (source) {
            case WHOLE_TEXTURE -> {
                KTexture texture = this.textureCollection.getAsset(assetId);
                return KRenderableTexture.wrapIntoRectangle(assetId, topLeftCorner, texture, unit);
            }
            case SLICE_SET -> {
                String sliceSetId = Objects.requireNonNull(definition.getString("slice_set"));
                KTextureSliceSet set = this.textureSliceSetCollection.getAsset(sliceSetId);
                return set.getTexture(assetId, topLeftCorner, unit);
            }
        }

        throw new KAssetLoadingException(
            String.format("Unknown source: %s", source)
        );
    }

}
