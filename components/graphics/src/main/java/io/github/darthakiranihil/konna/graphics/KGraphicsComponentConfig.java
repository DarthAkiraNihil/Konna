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
import io.github.darthakiranihil.konna.core.data.json.std.KJsonObjectValidator;
import io.github.darthakiranihil.konna.core.data.json.std.KJsonValueIsClassValidator;
import io.github.darthakiranihil.konna.graphics.render.KRenderFrontend;
import io.github.darthakiranihil.konna.graphics.shader.KShaderCompiler;

/**
 * Record class of Konna Graphics component configuration.
 * @param renderFrontendClass Class of render frontend
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public record KGraphicsComponentConfig(
    @KJsonSerialized @KJsonCustomName(name = RENDER_FRONTEND_CLASS_KEY)
    Class<? extends KRenderFrontend> renderFrontendClass,

    @KJsonSerialized @KJsonCustomName(name = SHADER_COMPILER_CLASS_KEY)
    Class<? extends KShaderCompiler> shaderCompilerClass
) {
    private static final String RENDER_FRONTEND_CLASS_KEY = "render_frontend";
    private static final String SHADER_COMPILER_CLASS_KEY = "shader_compiler";

    private static final class Schema implements KJsonValidator {

        private final KJsonValidator schema;

        Schema() {
            var propInfoBuilder = new KJsonPropertyValidationInfo.Builder();

            this.schema = new KJsonObjectValidator(
                propInfoBuilder
                    .withName(RENDER_FRONTEND_CLASS_KEY)
                    .withExpectedType(KJsonValueType.STRING)
                    .withValidator(
                        KJsonValueIsClassValidator.INSTANCE
                    )
                    .build(),
                propInfoBuilder
                    .withName(SHADER_COMPILER_CLASS_KEY)
                    .withExpectedType(KJsonValueType.STRING)
                    .withValidator(
                        KJsonValueIsClassValidator.INSTANCE
                    )
                    .build()
            );
        }

        @Override
        public void validate(final KJsonValue value) {
            this.schema.validate(value);
        }
    }

    /**
     * JSON schema of config, that should be used
     * for validation of loaded json file.
     */
    public static final KJsonValidator SCHEMA = new Schema();

}
