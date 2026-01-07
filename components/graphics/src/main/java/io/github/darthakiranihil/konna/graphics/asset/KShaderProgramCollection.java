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
import io.github.darthakiranihil.konna.core.io.*;
import io.github.darthakiranihil.konna.core.io.except.KAssetLoadingException;
import io.github.darthakiranihil.konna.core.io.except.KIoException;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.graphics.shader.KShader;
import io.github.darthakiranihil.konna.graphics.shader.KShaderCompiler;
import io.github.darthakiranihil.konna.graphics.shader.KShaderProgram;
import io.github.darthakiranihil.konna.graphics.shader.KShaderType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@KSingleton
public final class KShaderProgramCollection extends KObject implements KAssetCollection<KShaderProgram> {

    public static final String SHADER_PROGRAM_ASSET_TYPE = "Graphics.shaderProgram";

    private final Map<String, KShaderProgram> loadedPrograms;

    private final KAssetLoader assetLoader;
    private final KShaderCollection shaderCollection;
    private final KShaderCompiler shaderCompiler;

    public KShaderProgramCollection(
        @KInject final KAssetLoader assetLoader,
        @KInject final KShaderCollection shaderCollection,
        @KInject final KShaderCompiler shaderCompiler
    ) {

        this.assetLoader = assetLoader;
        this.shaderCollection = shaderCollection;
        this.shaderCompiler = shaderCompiler;

        this.loadedPrograms = new HashMap<>();

    }

    @Override
    public KShaderProgram getAsset(String assetId) {

        if (this.loadedPrograms.containsKey(assetId)) {
            return this.loadedPrograms.get(assetId);
        }

        KAsset asset = this.assetLoader.loadAsset(assetId, SHADER_PROGRAM_ASSET_TYPE);

        KAssetDefinition shaderProgramDefinition = asset.definition();

        String vertexShaderAssetId = shaderProgramDefinition.getString("vertex");
        String fragmentShaderAssetId = shaderProgramDefinition.getString("fragment");

        KShaderProgram linkedProgram;

        if (vertexShaderAssetId == null && fragmentShaderAssetId == null) {
            throw new KAssetLoadingException(
                String.format(
                    "Cannot load shader program %s: fragment and vertex shaders cannot be both null",
                    assetId
                )
            );
        } else if (vertexShaderAssetId == null) {
            linkedProgram = this.shaderCompiler.createShaderProgram(
                this.shaderCollection.getAsset(fragmentShaderAssetId)
            );
        } else if (fragmentShaderAssetId == null) {
            linkedProgram = this.shaderCompiler.createShaderProgram(
                this.shaderCollection.getAsset(vertexShaderAssetId)
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
}
