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
import io.github.darthakiranihil.konna.core.data.json.except.KJsonValidationError;
import io.github.darthakiranihil.konna.core.data.json.std.KJsonObjectValidator;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.except.KInvalidArgumentException;
import io.github.darthakiranihil.konna.core.io.*;
import io.github.darthakiranihil.konna.core.io.except.KAssetLoadingException;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.graphics.image.KImage;
import io.github.darthakiranihil.konna.graphics.image.KImageLoader;
import io.github.darthakiranihil.konna.graphics.image.KTexture;
import io.github.darthakiranihil.konna.graphics.shader.KShaderProgram;
import io.github.darthakiranihil.konna.graphics.shader.KShaderType;

import java.util.HashMap;
import java.util.Map;

/**
 * Collection of texture assets of type {@link KTextureCollection#TEXTURE_ASSET_TYPE}.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
@KSingleton
public final class KTextureCollection extends KObject implements KAssetCollection<KTexture> {

    /**
     * Constant for texture asset type inside Graphics component.
     */
    public static final String TEXTURE_ASSET_TYPE = "Graphics.texture";
    /**
     * Texture asset type schema.
     */
    public static final KPair<String, KJsonValidator> ASSET_SCHEMA = new KPair<>(
        TEXTURE_ASSET_TYPE,
        new TextureAssetSchema()
    );

    private static final class TextureAssetSchema implements KJsonValidator {

        private final KJsonValidator schema;

        public TextureAssetSchema() {

            var builder = new KJsonPropertyValidationInfo.Builder();

            this.schema = new KJsonObjectValidator(
                builder
                    .withName("image")
                    .withExpectedType(KJsonValueType.STRING)
                    .build(),
                builder
                    .withName("shader")
                    .withExpectedType(KJsonValueType.STRING)
                    .withRequired(false)
                    .withDefaultValue("default")
                    .build()
            );

        }

        @Override
        public void validate(KJsonValue value) {
            this.schema.validate(value);
        }
    }

    private final KAssetLoader assetLoader;
    private final KImageLoader imageLoader;
    private final KShaderProgramCollection shaderProgramCollection;

    private final Map<String, KTexture> loadedTextures;

    public KTextureCollection(
        @KInject final KAssetLoader assetLoader,
        @KInject final KImageLoader imageLoader,
        @KInject final KShaderProgramCollection shaderProgramCollection
    ) {
        this.assetLoader = assetLoader;
        this.imageLoader = imageLoader;
        this.shaderProgramCollection = shaderProgramCollection;

        this.loadedTextures = new HashMap<>();
    }

    @Override
    public KTexture getAsset(String assetId) {

        if (this.loadedTextures.containsKey(assetId)) {
            return this.loadedTextures.get(assetId);
        }

        KAsset asset = this.assetLoader.loadAsset(assetId, TEXTURE_ASSET_TYPE);
        KAssetDefinition textureDefinition = asset.definition();

        String imageFile = textureDefinition.getString("image");
        String shaderId = textureDefinition.getString("shader");

        if (imageFile == null || shaderId == null) {
            throw new KAssetLoadingException(
                String.format("Cannot load texture %s: shader or image file path is null", assetId)
            );
        }

        KShaderProgram textureShader = shaderId.equals("default")
            ? this.shaderProgramCollection.getDefaultTextureShader()
            : this.shaderProgramCollection.getAsset(shaderId);

        KImage image = this.imageLoader.load(imageFile);

        KTexture texture = new KTexture(
            image,
            textureShader
        );
        this.loadedTextures.put(assetId, texture);
        return texture;
    }
}
