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
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.annotation.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.graphics.shader.KShaderCompiler;
import io.github.darthakiranihil.konna.graphics.shader.KShaderProgram;
import io.github.darthakiranihil.konna.graphics.type.KShaderProgramTypedef;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Collection of shader programs of type
 * {@link KShaderProgramTypedef#SHADER_PROGRAM_ASSET_TYPE}.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
@KSingleton
public final class KShaderProgramCollection
    extends KObject
    implements KAssetCollection<KShaderProgram> {

    private final Map<String, KShaderProgram> loadedPrograms;

    private final KAssetLoader assetLoader;
    private final KShaderCollection shaderCollection;
    private final KShaderCompiler shaderCompiler;

    /**
     * Standard constructor.
     * @param assetLoader Asset loader (to load shader program definition)
     * @param shaderCollection Shader collection (to get shaders of the shader program)
     * @param shaderCompiler Shader compiler (to link shader program from shaders)
     */
    public KShaderProgramCollection(
        @KInject final KAssetLoader assetLoader,
        @KInject final KShaderCollection shaderCollection,
        @KInject final KShaderCompiler shaderCompiler
    ) {

        super(
            "Graphics.shaderProgramCollection",
            KStructUtils.setOfTags(KTag.DefaultTags.ASSET_COLLECTION)
        );

        this.assetLoader = assetLoader;
        this.shaderCollection = shaderCollection;
        this.shaderCompiler = shaderCompiler;

        this.loadedPrograms = new HashMap<>();

    }

    /**
     * Returns build shader program asset by its asset id.
     * If loading shader program has not been compiled, it performs it by calling
     * {@link KShaderCompiler}.
     * @param assetId Asset id of building object
     * @return Built shader program asset
     */
    @Override
    public KShaderProgram getAsset(final String assetId) {

        if (this.loadedPrograms.containsKey(assetId)) {
            return this.loadedPrograms.get(assetId);
        }

        KAsset asset = this.assetLoader.loadAsset(
            assetId,
            KShaderProgramTypedef.SHADER_PROGRAM_ASSET_TYPE
        );

        KAssetDefinition shaderProgramDefinition = asset.definition();

        String vertexShaderAssetId = shaderProgramDefinition.getString("vertex");
        String fragmentShaderAssetId = shaderProgramDefinition.getString("fragment");

        KShaderProgram linkedProgram;

        if (vertexShaderAssetId == null) {
            linkedProgram = this.shaderCompiler.createShaderProgram(
                this.shaderCollection.getAsset(Objects.requireNonNull(fragmentShaderAssetId))
            );
        } else if (fragmentShaderAssetId == null) {
            linkedProgram = this.shaderCompiler.createShaderProgram(
                this.shaderCollection.getAsset(Objects.requireNonNull(vertexShaderAssetId))
            );
        } else {
            linkedProgram = this.shaderCompiler.createShaderProgram(
                this.shaderCollection.getAsset(fragmentShaderAssetId),
                this.shaderCollection.getAsset(vertexShaderAssetId)
            );
        }

        this.loadedPrograms.put(
            assetId,
            linkedProgram
        );

        return linkedProgram;
    }

    /**
     * Convenience method to get default texture shader from {@link KShaderCompiler}.
     * @return Default texture shader
     */
    public KShaderProgram getDefaultTextureShader() {
        return this.shaderCompiler.getDefaultTextureShader();
    }
}
