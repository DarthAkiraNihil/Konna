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

import io.github.darthakiranihil.konna.core.data.json.*;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.io.KAsset;
import io.github.darthakiranihil.konna.core.io.KAssetCollection;
import io.github.darthakiranihil.konna.core.io.KAssetDefinition;
import io.github.darthakiranihil.konna.core.io.KAssetLoader;
import io.github.darthakiranihil.konna.core.io.except.KAssetLoadingException;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.graphics.image.*;
import io.github.darthakiranihil.konna.graphics.shader.KShaderProgram;
import io.github.darthakiranihil.konna.graphics.type.KTextureTypeDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * Collection of texture assets of type {@link KTextureTypeDefinition#TEXTURE_ASSET_TYPE}.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
@KSingleton
public final class KTextureCollection extends KObject implements KAssetCollection<KTexture> {

    private final KAssetLoader assetLoader;
    private final KImageLoader imageLoader;
    private final KShaderProgramCollection shaderProgramCollection;

    private final Map<String, KTexture> loadedTextures;

    /**
     * Standard constructor.
     * @param assetLoader Asset loader (to load texture assets)
     * @param imageLoader Image loader (to load texture images)
     * @param shaderProgramCollection (to load texture shaders)
     */
    public KTextureCollection(
        @KInject final KAssetLoader assetLoader,
        @KInject final KImageLoader imageLoader,
        @KInject final KShaderProgramCollection shaderProgramCollection
    ) {
        super(
            "Graphics.textureCollection",
            KStructUtils.setOfTags(KTag.DefaultTags.ASSET_COLLECTION)
        );

        this.assetLoader = assetLoader;
        this.imageLoader = imageLoader;
        this.shaderProgramCollection = shaderProgramCollection;

        this.loadedTextures = new HashMap<>();
    }

    /**
     * Returns built texture asset by its asset id.
     * In order to build it, the collection checks for shader and image options
     * of loading texture asset. Shader is taken from {@link KShaderProgramCollection}.
     * @param assetId Asset id of texture
     * @return Built texture
     */
    @Override
    public KTexture getAsset(final String assetId) {

        if (this.loadedTextures.containsKey(assetId)) {
            return this.loadedTextures.get(assetId);
        }

        KAsset asset = this.assetLoader.loadAsset(assetId, KTextureTypeDefinition.TEXTURE_ASSET_TYPE);
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

        KAssetDefinition filtering = textureDefinition.getSubdefinition("filtering");
        KAssetDefinition wrapping = textureDefinition.getSubdefinition("wrapping");

        KTextureFiltering minFilter = KTextureFiltering.valueOf(filtering.getString("min"));
        KTextureFiltering magFilter = KTextureFiltering.valueOf(filtering.getString("mag"));
        KTextureWrapping uWrapping = KTextureWrapping.valueOf(wrapping.getString("u"));
        KTextureWrapping vWrapping = KTextureWrapping.valueOf(wrapping.getString("v"));

        KTexture texture = new KTexture(
            image,
            textureShader,
            minFilter,
            magFilter,
            uWrapping,
            vWrapping
        );

        this.loadedTextures.put(assetId, texture);
        return texture;
    }
}
