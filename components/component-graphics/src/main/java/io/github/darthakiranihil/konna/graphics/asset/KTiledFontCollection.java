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

import io.github.darthakiranihil.konna.annotation.core.di.KInject;
import io.github.darthakiranihil.konna.core.io.KAsset;
import io.github.darthakiranihil.konna.core.io.KAssetCollection;
import io.github.darthakiranihil.konna.core.io.KAssetDefinition;
import io.github.darthakiranihil.konna.core.io.KAssetLoader;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.graphics.image.KTexture;
import io.github.darthakiranihil.konna.graphics.text.KTiledFont;
import io.github.darthakiranihil.konna.graphics.text.KTiledFontFormat;
import io.github.darthakiranihil.konna.graphics.type.KTiledFontTypedef;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Collection of texture assets of type {@link KTiledFontTypedef#TILED_FONT_ASSET_TYPE}.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
public final class KTiledFontCollection extends KObject implements KAssetCollection<KTiledFont> {

    private final KAssetLoader assetLoader;
    private final KTextureCollection textureCollection;
    private final KActivator activator;

    private final Map<String, KTiledFont> loadedFonts;

    /**
     * Standard constructor.
     * @param assetLoader Asset loader (to load texture assets)
     * @param textureCollection Texture collection (to load font faces)
     * @param activator Activator to instantiate tiled font formats
     */
    public KTiledFontCollection(
        @KInject final KAssetLoader assetLoader,
        @KInject final KTextureCollection textureCollection,
        @KInject final KActivator activator
    ) {
        super(
            "Graphics.tiledFontCollection",
            KStructUtils.setOfTags(KTag.DefaultTags.ASSET_COLLECTION)
        );

        this.assetLoader = assetLoader;
        this.textureCollection = textureCollection;
        this.activator = activator;

        this.loadedFonts = new HashMap<>();
    }

    /**
     * Returns built tiled font asset by its asset id.
     * In order to build it, the collection looks for texture in {@link KTextureCollection}
     * passed in tiled font settings and tries to instantiate passed {@link KTiledFontFormat}.
     * @param assetId Asset id of tiled font
     * @return Built tiled font
     */
    @Override
    public KTiledFont getAsset(final String assetId) {

        if (this.loadedFonts.containsKey(assetId)) {
            return this.loadedFonts.get(assetId);
        }

        KAsset asset = this.assetLoader.loadAsset(
            assetId,
            KTiledFontTypedef.TILED_FONT_ASSET_TYPE
        );
        KAssetDefinition fontDefinition = asset.definition();

        String name = Objects.requireNonNull(fontDefinition.getString("name"));

        KAssetDefinition glyphSizeData = fontDefinition.getSubdefinition("glyph_size");
        KSize glyphSize = new KSize(
            glyphSizeData.getInt("width"),
            glyphSizeData.getInt("height")
        );

        String faceId = Objects.requireNonNull(fontDefinition.getString("face"));
        KTexture face = this.textureCollection.getAsset(faceId);

        KTiledFontFormat fontFormat = this
            .activator
            .createObject(
                fontDefinition.getClassObject(
                    "format",
                    KTiledFontFormat.class
                )
            );

        KTiledFont font = new KTiledFont(
            name,
            face,
            fontFormat,
            glyphSize
        );

        this.loadedFonts.put(assetId, font);
        return font;
    }
}
