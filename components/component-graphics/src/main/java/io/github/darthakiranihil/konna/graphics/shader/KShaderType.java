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

package io.github.darthakiranihil.konna.graphics.shader;

import io.github.darthakiranihil.konna.core.data.json.KJsonValidator;
import io.github.darthakiranihil.konna.core.data.json.except.KJsonValidationError;

/**
 * Enumeration for all supported shader types.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public enum KShaderType {

    /**
     * Fragment shader.
     */
    FRAGMENT,
    /**
     * Vertex shader.
     */
    VERTEX;

    /**
     * Json validator for this type.
     */
    public static final KJsonValidator VALIDATOR = (v) -> {
        String s = v.getString();

        try {
            KShaderType.valueOf(s);
        } catch (IllegalArgumentException e) {
            throw new KJsonValidationError(e.getMessage());
        }

    };

}
