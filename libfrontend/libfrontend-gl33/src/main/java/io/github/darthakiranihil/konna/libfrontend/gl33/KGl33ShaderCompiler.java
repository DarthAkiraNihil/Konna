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

package io.github.darthakiranihil.konna.libfrontend.gl33;

import io.github.darthakiranihil.konna.core.graphics.except.KShaderCompilationException;
import io.github.darthakiranihil.konna.core.graphics.shader.KShader;
import io.github.darthakiranihil.konna.core.graphics.shader.KShaderCompiler;
import io.github.darthakiranihil.konna.core.graphics.shader.KShaderProgram;
import io.github.darthakiranihil.konna.core.graphics.shader.KShaderType;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.libfrontend.gl20.KGl20;

public final class KGl33ShaderCompiler extends KObject implements KShaderCompiler {

    private final KGl33 gl;

    public KGl33ShaderCompiler(final KGl33 gl) {
        this.gl = gl;
    }

    // TODO: link state check
    @Override
    public KShader compileShader(final String source, final KShaderType type) {
        int shaderId = this.gl.glCreateShader(type == KShaderType.FRAGMENT ? KGl20.GL_FRAGMENT_SHADER : KGl20.GL_VERTEX_SHADER) ;
        this.gl.glShaderSource(shaderId, source);
        this.gl.glCompileShader(shaderId);
        return new KShader(shaderId, type);
    }

    @Override
    public KShader compileFragmentShader(String source) {
        return this.compileShader(source, KShaderType.FRAGMENT);
    }

    @Override
    public KShader compileVertexShader(String source) {
        return this.compileShader(source, KShaderType.VERTEX);
    }

    // TODO: LINK CHECK
    @Override
    public KShaderProgram createShaderProgram(KShader fragment, KShader vertex) {
        if (fragment.type() != KShaderType.FRAGMENT) {
            throw new KShaderCompilationException("Cannot pass non-fragment shader as program's fragment shader");
        }

        if (vertex.type() != KShaderType.VERTEX) {
            throw new KShaderCompilationException("Cannot pass non-vertex shader as program's vertex shader");
        }

        int program = this.gl.glCreateProgram();
        this.gl.glAttachShader(program, fragment.id());
        this.gl.glAttachShader(program, vertex.id());
        this.gl.glLinkProgram(program);
        return new KGl33ShaderProgram(program, this.gl);
    }

    @Override
    public KShaderProgram createShaderProgram(KShader shader) {
        int program = this.gl.glCreateProgram();
        this.gl.glAttachShader(program, shader.id());
        this.gl.glLinkProgram(program);
        return new KGl33ShaderProgram(program, this.gl);
    }

}
