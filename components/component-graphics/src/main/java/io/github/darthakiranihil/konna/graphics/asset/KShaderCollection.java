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
import io.github.darthakiranihil.konna.core.io.*;
import io.github.darthakiranihil.konna.core.io.except.KAssetLoadingException;
import io.github.darthakiranihil.konna.core.io.except.KIoException;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.annotation.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.graphics.shader.KShader;
import io.github.darthakiranihil.konna.graphics.shader.KShaderCompiler;
import io.github.darthakiranihil.konna.graphics.shader.KShaderType;
import io.github.darthakiranihil.konna.graphics.type.KShaderTypedef;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Collection of shader assets of type
 * {@link KShaderTypedef#SHADER_ASSET_TYPE}.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
@KSingleton
public final class KShaderCollection extends KObject implements KAssetCollection<KShader> {

    private final Map<String, KShader> loadedShaders;

    private final KAssetLoader assetLoader;
    private final KResourceLoader resourceLoader;
    private final KShaderCompiler shaderCompiler;

    /**
     * Standard constructor.
     * @param assetLoader Asset loader (to load shader asset definition)
     * @param resourceLoader Resource loader (to read shader source file)
     * @param shaderCompiler Shader compiler (to compile the shader)
     */
    public KShaderCollection(
        @KInject final KAssetLoader assetLoader,
        @KInject final KResourceLoader resourceLoader,
        @KInject final KShaderCompiler shaderCompiler
    ) {
        super(
            "Graphics.shaderCollection",
            KStructUtils.setOfTags(KTag.DefaultTags.ASSET_COLLECTION)
        );

        this.assetLoader = assetLoader;
        this.resourceLoader = resourceLoader;
        this.shaderCompiler = shaderCompiler;

        this.loadedShaders = new HashMap<>();
    }

    /**
     * Returns built shader asset by its shader id and compiles it if it has
     * not been compiled yet.
     * @param assetId Asset id of building object
     * @return Built shader asset
     */
    @Override
    public KShader getAsset(final String assetId) {

        if (this.loadedShaders.containsKey(assetId)) {
            return this.loadedShaders.get(assetId);
        }

        KAsset asset = this.assetLoader.loadAsset(assetId, KShaderTypedef.SHADER_ASSET_TYPE);

        KAssetDefinition shaderDefinition = asset.definition();
        KShaderType type = shaderDefinition.getEnum("type", KShaderType.class);
        String sourcePath = Objects.requireNonNull(shaderDefinition.getString("source"));

        try (KResource shaderSource = this.resourceLoader.loadResource(sourcePath)) {

            if (!shaderSource.exists()) {
                throw new KAssetLoadingException(
                    String.format("Cannot load shader %s: source does not exist", assetId)
                );
            }

            KShader compiledShader = this.shaderCompiler.compileShader(
                shaderSource.string(), type
            );

            this.loadedShaders.put(assetId, compiledShader);

            return compiledShader;

        } catch (IOException e) {
            throw new KIoException(e);
        }

    }
}
