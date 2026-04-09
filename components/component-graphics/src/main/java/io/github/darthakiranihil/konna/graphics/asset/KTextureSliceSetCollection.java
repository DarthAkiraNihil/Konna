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
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.di.KSingleton;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.graphics.KColor;
import io.github.darthakiranihil.konna.graphics.image.KTexture;
import io.github.darthakiranihil.konna.graphics.image.KTextureSliceData;
import io.github.darthakiranihil.konna.graphics.image.KTextureSliceSet;
import io.github.darthakiranihil.konna.graphics.type.KTextureSliceSetTypedef;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Collection of texture slice sets assets of type
 * {@link KTextureSliceSetTypedef#TEXTURE_SLICE_SET_ASSET_TYPE}.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
@KSingleton
public final class KTextureSliceSetCollection
    extends KObject
    implements KAssetCollection<KTextureSliceSet> {

    private final KAssetLoader assetLoader;
    private final KTextureCollection textureCollection;
    private final Map<String, KTextureSliceSet> loadedSets;

    /**
     * Standard constructor.
     * @param assetLoader Asset loader
     * @param textureCollection Texture collection to extract base textures
     */
    public KTextureSliceSetCollection(
        @KInject final KAssetLoader assetLoader,
        @KInject final KTextureCollection textureCollection
    ) {
        super(
            "Graphics.textureSliceSetCollection",
            KStructUtils.setOfTags(KTag.DefaultTags.ASSET_COLLECTION)
        );

        this.assetLoader = assetLoader;
        this.textureCollection = textureCollection;

        this.loadedSets = new HashMap<>();
    }

    @Override
    public KTextureSliceSet getAsset(final String assetId) {

        if (this.loadedSets.containsKey(assetId)) {
            return this.loadedSets.get(assetId);
        }

        KAsset asset = this.assetLoader.loadAsset(
            assetId,
            KTextureSliceSetTypedef.TEXTURE_SLICE_SET_ASSET_TYPE
        );

        String baseTextureId = Objects.requireNonNull(asset.getString("base_texture"));
        KTexture baseTexture = this.textureCollection.getAsset(baseTextureId);

        KAssetDefinition[] slices = asset.getSubdefinitionArray("slices");
        Map<String, KTextureSliceData> slicesData = new HashMap<>();

        for (var slice: slices) {
            KAssetDefinition[] rawUv = slice.getSubdefinitionArray("uv");
            KAssetDefinition[] rawXy = slice.getSubdefinitionArray("xy");
            KAssetDefinition[] rawColors = slice.getSubdefinitionArray("colors");

            // On this stage it is guaranteed for uv, xy and colors to have the same length
            // (thanks to the validator!)
            int componentsCount = rawUv.length;

            KVector2f[] uv = new KVector2f[componentsCount];
            KVector2i[] xy = new KVector2i[componentsCount];
            KColor[] colors = new KColor[componentsCount];

            for (int i = 0; i < componentsCount; i++) {

                uv[i] = new KVector2f(
                    rawUv[i].getFloat("u"),
                    rawUv[i].getFloat("v")
                );

                xy[i] = new KVector2i(
                    rawXy[i].getInt("x"),
                    rawXy[i].getInt("y")
                );

                colors[i] = new KColor(
                    rawColors[i].getInt("r"),
                    rawColors[i].getInt("g"),
                    rawColors[i].getInt("b")
                );

            }

            String sliceName = Objects.requireNonNull(slice.getString("name"));
            slicesData.put(sliceName, new KTextureSliceData(uv, xy, colors));

        }

        KTextureSliceSet set = new KTextureSliceSet(baseTexture, slicesData);
        this.loadedSets.put(assetId, set);
        return set;

    }
}
