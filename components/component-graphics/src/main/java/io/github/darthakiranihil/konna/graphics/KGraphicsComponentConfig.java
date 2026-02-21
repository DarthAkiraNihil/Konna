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

package io.github.darthakiranihil.konna.graphics;

import io.github.darthakiranihil.konna.core.data.json.*;
import io.github.darthakiranihil.konna.core.data.json.std.KJsonValueIsClassValidator;
import io.github.darthakiranihil.konna.graphics.image.KImageLoader;
import io.github.darthakiranihil.konna.graphics.render.KRenderFrontend;
import io.github.darthakiranihil.konna.graphics.shader.KShaderCompiler;

/**
 * Record class of Konna Graphics component configuration.
 * @param renderFrontendClass Class of render frontend
 * @param shaderCompilerClass Class of shader compiler
 * @param imageLoaderClass Class of image loader
 * @param transformMatrixCalculatorClass Class of transform matrix calculator
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
public record KGraphicsComponentConfig(
    @KJsonSerialized @KJsonCustomName(name = RENDER_FRONTEND_CLASS_KEY)
    Class<? extends KRenderFrontend> renderFrontendClass,

    @KJsonSerialized @KJsonCustomName(name = SHADER_COMPILER_CLASS_KEY)
    Class<? extends KShaderCompiler> shaderCompilerClass,

    @KJsonSerialized @KJsonCustomName(name = IMAGE_LOADER_CLASS_KEY)
    Class<? extends KImageLoader> imageLoaderClass,

    @KJsonSerialized @KJsonCustomName(name = TRANSFORM_MATRIX_CALCULATOR_CLASS)
    Class<? extends KTransformMatrixCalculator> transformMatrixCalculatorClass
) {
    private static final String RENDER_FRONTEND_CLASS_KEY = "render_frontend";
    private static final String SHADER_COMPILER_CLASS_KEY = "shader_compiler";
    private static final String IMAGE_LOADER_CLASS_KEY = "image_loader";
    private static final String TRANSFORM_MATRIX_CALCULATOR_CLASS = "transform_matrix_calculator";

    /**
     * @return JSON schema of config, that should be used
     *         for validation of loaded JSON  file.
     */
    public static KJsonValidator getSchema() {
        return KJsonObjectValidatorBuilder
            .create()
            .withField(RENDER_FRONTEND_CLASS_KEY, KJsonValueType.STRING)
            .withValidator(new KJsonValueIsClassValidator())
            .finishField()
            .withField(SHADER_COMPILER_CLASS_KEY, KJsonValueType.STRING)
            .withValidator(new KJsonValueIsClassValidator())
            .finishField()
            .withField(IMAGE_LOADER_CLASS_KEY, KJsonValueType.STRING)
            .withValidator(new KJsonValueIsClassValidator())
            .finishField()
            .withField(TRANSFORM_MATRIX_CALCULATOR_CLASS, KJsonValueType.STRING)
            .withValidator(new KJsonValueIsClassValidator())
            .finishField()
            .build();
    }

}
