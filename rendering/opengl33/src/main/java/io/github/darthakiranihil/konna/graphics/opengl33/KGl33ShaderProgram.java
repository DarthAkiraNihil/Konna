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

package io.github.darthakiranihil.konna.graphics.opengl33;

import io.github.darthakiranihil.konna.graphics.shader.KShaderProgram;
import io.github.darthakiranihil.konna.libfrontend.opengl.KGl33;

/**
 * Implementation of {@link KShaderProgram} that requires OpenGL 3.3
 * to be created.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public final class KGl33ShaderProgram implements KShaderProgram {

    private final int id;
    private final KGl33 gl;

    /**
     * Constructs a shader program with provided id and
     * OpenGL 3.3 library frontend to be used to set shader attributes,
     * uniforms etc.
     * @param id Shader id
     * @param gl OpenGL 3.3 frontend
     */
    public KGl33ShaderProgram(int id, final KGl33 gl) {
        this.id = id;
        this.gl = gl;
    }

    @Override
    public int id() {
        return this.id;
    }

    @Override
    public int getAttribute(final String name) {
        return this.gl.glGetAttribLocation(this.id, name);
    }

    @Override
    public int getUniform(final String name) {
        return this.gl.glGetUniformLocation(this.id, name);
    }

}
