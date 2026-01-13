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

import io.github.darthakiranihil.konna.core.data.json.KJsonPropertyValidationInfo;
import io.github.darthakiranihil.konna.core.data.json.KJsonValidator;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.data.json.KJsonValueType;
import io.github.darthakiranihil.konna.core.data.json.std.KJsonObjectValidator;
import io.github.darthakiranihil.konna.core.data.json.std.KJsonValueIsClassValidator;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.io.KAsset;
import io.github.darthakiranihil.konna.core.io.KAssetCollection;
import io.github.darthakiranihil.konna.core.io.KAssetDefinition;
import io.github.darthakiranihil.konna.core.io.KAssetLoader;
import io.github.darthakiranihil.konna.core.io.except.KAssetLoadingException;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.graphics.image.KTexture;
import io.github.darthakiranihil.konna.graphics.text.KTiledFont;
import io.github.darthakiranihil.konna.graphics.text.KTiledFontFormat;
import io.github.darthakiranihil.konna.graphics.text.std.KSquaredAsciiTiledFontFormat;

import java.util.HashMap;
import java.util.Map;

public final class KTiledFontCollection extends KObject implements KAssetCollection<KTiledFont> {

    /**
     * Constant for tiled font asset type inside Graphics component.
     */
    public static final String TILED_FONT_ASSET_TYPE = "Graphics.tiledFont";
    /**
     * Tiled font asset type schema.
     */
    public static final KPair<String, KJsonValidator> ASSET_SCHEMA = new KPair<>(
        TILED_FONT_ASSET_TYPE,
        new TiledFontAssetSchema()
    );

    private static final class TiledFontAssetSchema implements KJsonValidator {

        private final KJsonValidator schema;

        TiledFontAssetSchema() {

            var builder = new KJsonPropertyValidationInfo.Builder();

            this.schema = new KJsonObjectValidator(
                builder
                    .withName("name")
                    .withExpectedType(KJsonValueType.STRING)
                    .build(),
                builder
                    .withName("face")
                    .withExpectedType(KJsonValueType.STRING)
                    .build(),
                builder
                    .withName("glyph_size")
                    .withExpectedType(KJsonValueType.OBJECT)
                    .withValidator(
                        new KJsonObjectValidator(
                            builder
                                .createSeparated()
                                .withName("width")
                                .withExpectedType(KJsonValueType.NUMBER_INT)
                                .build(),
                            builder
                                .createSeparated()
                                .withName("height")
                                .withExpectedType(KJsonValueType.NUMBER_INT)
                                .build()
                        )
                    )
                    .build(),
                builder
                    .withName("format")
                    .withExpectedType(KJsonValueType.STRING)
                    .withRequired(false)
                    .withDefaultValue(KSquaredAsciiTiledFontFormat.class.getCanonicalName())
                    .withValidator(KJsonValueIsClassValidator.INSTANCE)
                    .build()
            );

        }

        @Override
        public void validate(final KJsonValue value) {
            this.schema.validate(value);
        }
    }

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

    @Override
    @SuppressWarnings("unchecked")
    public KTiledFont getAsset(final String assetId) {

        if (this.loadedFonts.containsKey(assetId)) {
            return this.loadedFonts.get(assetId);
        }

        KAsset asset = this.assetLoader.loadAsset(assetId, TILED_FONT_ASSET_TYPE);
        KAssetDefinition fontDefinition = asset.definition();

        String name = fontDefinition.getString("name");
        if (name == null) {
            throw new KAssetLoadingException(
                String.format(
                    "Cannot get tiled font asset %s: name cannot be null",
                    assetId
                )
            );
        }

        KAssetDefinition glyphSizeData = fontDefinition.getSubdefinition("glyph_size");
        KSize glyphSize = new KSize(
            glyphSizeData.getInt("width"),
            glyphSizeData.getInt("height")
        );

        String faceId = fontDefinition.getString("face");
        if (faceId == null) {
            throw new KAssetLoadingException(
                String.format(
                    "Cannot get tiled font asset %s: font face id cannot be null",
                    assetId
                )
            );
        }

        KTexture face = this.textureCollection.getAsset(faceId);

        KTiledFontFormat fontFormat;
        String fontFormatClass = fontDefinition.getString("format");
        try {
            fontFormat = this
                .activator
                .createObject(
                    (Class<? extends KTiledFontFormat>) Class.forName(fontFormatClass)
                );
        } catch (Throwable e) {
            throw new KAssetLoadingException(
                String.format(
                        "Cannot get tiled font asset %s: cannot load format class %s. "
                    +   "Make sure it exists and implement KTiledFontFormat interface",
                    assetId,
                    fontFormatClass
                )
            );
        }

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
