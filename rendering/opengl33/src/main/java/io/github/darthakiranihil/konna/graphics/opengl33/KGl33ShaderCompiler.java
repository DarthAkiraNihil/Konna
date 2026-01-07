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

import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.struct.KBufferUtils;
import io.github.darthakiranihil.konna.graphics.shader.KShader;
import io.github.darthakiranihil.konna.graphics.shader.KShaderCompiler;
import io.github.darthakiranihil.konna.graphics.shader.KShaderProgram;
import io.github.darthakiranihil.konna.graphics.shader.KShaderType;
import io.github.darthakiranihil.konna.graphics.shader.except.KShaderCompilationException;
import io.github.darthakiranihil.konna.libfrontend.opengl.KGl33;

import java.nio.IntBuffer;

/**
 * Implementation of {@link KShaderCompiler} that requires OpenGL 3.3 in
 * order to compile sharers and link shader programs.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public final class KGl33ShaderCompiler extends KObject implements KShaderCompiler {

    private final KGl33 gl;

    /**
     * Constructs shader compiler with provided OpenGL 3.3 frontend.
     * @param gl OpenGL 3.3 frontend
     */
    public KGl33ShaderCompiler(@KInject final KGl33 gl) {
        this.gl = gl;
    }

    @Override
    public KShader compileShader(final String source, final KShaderType type) {
        int shaderId = this.gl.glCreateShader(
            type == KShaderType.FRAGMENT
                ? KGl33.GL_FRAGMENT_SHADER
                : KGl33.GL_VERTEX_SHADER
        );
        this.gl.glShaderSource(shaderId, source);
        this.gl.glCompileShader(shaderId);

        IntBuffer status = KBufferUtils.createIntBuffer(1);
        this.gl.glGetShaderiv(shaderId, KGl33.GL_COMPILE_STATUS, status);
        if (status.get(0) == 0) {
            String log = this.gl.glGetShaderInfoLog(shaderId);
            this.gl.glDeleteShader(shaderId);
            throw new KShaderCompilationException(log);
        }

        return new KShader(shaderId, type);
    }

    @Override
    public KShader compileFragmentShader(final String source) {
        return this.compileShader(source, KShaderType.FRAGMENT);
    }

    @Override
    public KShader compileVertexShader(final String source) {
        return this.compileShader(source, KShaderType.VERTEX);
    }

    @Override
    public KShaderProgram createShaderProgram(final KShader fragment, final KShader vertex) {
        if (fragment.type() != KShaderType.FRAGMENT) {
            throw new KShaderCompilationException(
                "Cannot pass non-fragment shader as program's fragment shader"
            );
        }

        if (vertex.type() != KShaderType.VERTEX) {
            throw new KShaderCompilationException(
                "Cannot pass non-vertex shader as program's vertex shader"
            );
        }

        int program = this.gl.glCreateProgram();
        this.gl.glAttachShader(program, fragment.id());
        this.gl.glAttachShader(program, vertex.id());
        this.gl.glLinkProgram(program);

        IntBuffer status = KBufferUtils.createIntBuffer(1);
        this.gl.glGetProgramiv(program, KGl33.GL_LINK_STATUS, status);
        if (status.get(0) == 0) {
            String log = this.gl.glGetProgramInfoLog(program);
            this.gl.glDeleteProgram(program);
            throw new KShaderCompilationException(log);
        }

        return new KGl33ShaderProgram(program, this.gl);
    }

    @Override
    public KShaderProgram createShaderProgram(final KShader shader) {
        int program = this.gl.glCreateProgram();
        this.gl.glAttachShader(program, shader.id());
        this.gl.glLinkProgram(program);
        return new KGl33ShaderProgram(program, this.gl);
    }

}
