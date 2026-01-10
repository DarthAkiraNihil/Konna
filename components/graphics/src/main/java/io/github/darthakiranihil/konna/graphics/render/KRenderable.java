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

package io.github.darthakiranihil.konna.graphics.render;

import io.github.darthakiranihil.konna.graphics.shader.KShaderProgram;
import org.jspecify.annotations.Nullable;

/**
 * Interface for an object that can be rendered with
 * {@link KRenderFrontend}.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public interface KRenderable {

    /**
     * Implementation of an empty rendered object that literally
     * is not rendered at all. May be useful for test purposes.
     */
    final class EMPTY implements KRenderable {
        @Override
        public void render(final KRenderFrontend rf) {

        }

        @Override
        public @Nullable KShaderProgram getShader() {
            return null;
        }
    }

    /**
     * Renders this object.
     * @param rf Render frontend used for object rendering
     */
    void render(KRenderFrontend rf);
    @Nullable KShaderProgram getShader();

}
