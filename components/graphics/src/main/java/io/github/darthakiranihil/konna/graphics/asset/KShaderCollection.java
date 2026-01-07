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
import io.github.darthakiranihil.konna.core.io.except.KIoException;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.graphics.shader.KShader;
import io.github.darthakiranihil.konna.graphics.shader.KShaderCompiler;
import io.github.darthakiranihil.konna.graphics.shader.KShaderType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@KSingleton
public final class KShaderCollection extends KObject implements KAssetCollection<KShader> {

    public static final String SHADER_ASSET_TYPE = "Graphics.shader";
    public static final KPair<String, KJsonValidator> ASSET_SCHEMA = new KPair<>(
        SHADER_ASSET_TYPE,
        new ShaderAssetSchema()
    );

    private static final class ShaderAssetSchema implements KJsonValidator {

        private final KJsonValidator schema;

        public ShaderAssetSchema() {

            var builder = new KJsonPropertyValidationInfo.Builder();

            this.schema = new KJsonObjectValidator(
                builder
                    .withName("type")
                    .withExpectedType(KJsonValueType.STRING)
                    .withValidator((v) -> {
                        String s = v.getString();
                        try {
                            KShaderType.fromString(s);
                        } catch (KInvalidArgumentException e) {
                            throw new KJsonValidationError(e.getMessage());
                        }
                    })
                    .build(),
                builder
                    .withName("source")
                    .withExpectedType(KJsonValueType.STRING) // todo: value is path
                    .build()
            );

        }

        @Override
        public void validate(KJsonValue value) {
            this.schema.validate(value);
        }
    }

    private final Map<String, KShader> loadedShaders;

    private final KAssetLoader assetLoader;
    private final KResourceLoader resourceLoader;
    private final KShaderCompiler shaderCompiler;

    public KShaderCollection(
        @KInject final KAssetLoader assetLoader,
        @KInject final KResourceLoader resourceLoader,
        @KInject final KShaderCompiler shaderCompiler
        ) {
        this.assetLoader = assetLoader;
        this.resourceLoader = resourceLoader;
        this.shaderCompiler = shaderCompiler;

        this.loadedShaders = new HashMap<>();
    }

    @Override
    public KShader getAsset(String assetId) {

        if (this.loadedShaders.containsKey(assetId)) {
            return this.loadedShaders.get(assetId);
        }

        KAsset asset = this.assetLoader.loadAsset(assetId, SHADER_ASSET_TYPE);

        KAssetDefinition shaderDefinition = asset.definition();
        String rawType = shaderDefinition.getString("type");
        String sourcePath = shaderDefinition.getString("source");

        if (rawType == null || sourcePath == null) {
            throw new KAssetLoadingException(
                String.format("Cannot load shader %s: type or source path is null", assetId)
            );
        }

        try (KResource shaderSource = this.resourceLoader.loadResource(sourcePath)) {

            if (!shaderSource.exists()) {
                throw new KAssetLoadingException(
                    String.format("Cannot load shader %s: source does not exist", assetId)
                );
            }

            KShaderType shaderType = KShaderType.fromString(rawType);
            KShader compiledShader = this.shaderCompiler.compileShader(shaderSource.string(), shaderType);

            this.loadedShaders.put(assetId, compiledShader);

            return compiledShader;

        } catch (IOException e) {
            throw new KIoException(e);
        }

    }
}
