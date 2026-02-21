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

import io.github.darthakiranihil.konna.core.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.graphics.shader.KShaderProgram;
import io.github.darthakiranihil.konna.libfrontend.opengl.KGl33;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link KShaderProgram} that requires OpenGL 3.3
 * to be created.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
@KExcludeFromGeneratedCoverageReport
public final class KGl33ShaderProgram implements KShaderProgram {

    private final int id;
    private final KGl33 gl;

    private final Map<String, Integer> uniformLocations;

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

        this.uniformLocations = new HashMap<>();
    }

    @Override
    public int id() {
        return this.id;
    }

    @Override
    public void setUniform(final String uniformName, final float[] value) {
        this.gl.glUniform4fv(
            this.getUniformLocation(uniformName),
            value
        );
    }

    @Override
    public void setUniform(final String uniformName, int value) {
        this.gl.glUniform1i(
            this.getUniformLocation(uniformName),
            value
        );
    }

    @Override
    public void setUniformMatrix(final String uniformName, final float[] value) {
        this.gl.glUniformMatrix4fv(
            this.getUniformLocation(uniformName),
            false,
            value
        );
    }

    private int getUniformLocation(final String uniformName) {
        if (!this.uniformLocations.containsKey(uniformName)) {
            this.uniformLocations.put(
                uniformName,
                this.gl.glGetUniformLocation(this.id, uniformName)
            );
        }

        return this.uniformLocations.get(uniformName);
    }
}
