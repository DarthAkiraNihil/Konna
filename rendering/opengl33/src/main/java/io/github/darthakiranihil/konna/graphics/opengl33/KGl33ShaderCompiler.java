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
import io.github.darthakiranihil.konna.core.log.KSystemLogger;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.struct.KBufferUtils;
import io.github.darthakiranihil.konna.core.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.graphics.shader.KShader;
import io.github.darthakiranihil.konna.graphics.shader.KShaderCompiler;
import io.github.darthakiranihil.konna.graphics.shader.KShaderProgram;
import io.github.darthakiranihil.konna.graphics.shader.KShaderType;
import io.github.darthakiranihil.konna.graphics.shader.except.KShaderCompilationException;
import io.github.darthakiranihil.konna.libfrontend.opengl.KGl33;
import org.jspecify.annotations.Nullable;

import java.nio.IntBuffer;

/**
 * Implementation of {@link KShaderCompiler} that requires OpenGL 3.3 in
 * order to compile sharers and link shader programs.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
@KExcludeFromGeneratedCoverageReport
public final class KGl33ShaderCompiler extends KObject implements KShaderCompiler {

    public static final String U_COLOR = "u_color";
    public static final String U_TEXTURE = "u_texture";

    public static final String DEFAULT_SHAPE_FRAGMENT_SHADER = """
        #version 330 core
        out vec4 FragColor;
        \s
        uniform vec4\s""" + U_COLOR + """
        ;
        void main()
        {
            FragColor =\s""" + U_COLOR + """
            ;
        }
    """;

    public static final String DEFAULT_SHAPE_VERTEX_SHADER = """
        #version 330 core
        layout (location = 0) in vec3 aPos;
        \s
        uniform mat4 transform;
        \s
        void main()
        {
            gl_Position = transform * vec4(aPos, 1.0); // see how we directly give a vec3 to vec4's constructor
        }
    """;

    /**
     * Default texture fragment shader.
     */
    public static final String DEFAULT_TEXTURE_FRAGMENT_SHADER = """
        #version 330 core
        out vec4 FragColor;
        \s
        in vec3 TexColor;
        in vec2 TexCoord;
        \s
        uniform sampler2D\s""" + U_TEXTURE + """
        ;
        void main()
        {
            FragColor = texture(""" + U_TEXTURE + """
            , TexCoord) * vec4(TexColor, 1.0);
        }
    """;

    /**
     * Default texture vertex shader.
     */
    public static final String DEFAULT_TEXTURE_VERTEX_SHADER = """
        #version 330 core
        layout (location = 0) in vec3 aPos;
        layout (location = 1) in vec3 aColor;
        layout (location = 2) in vec2 aTexCoord;
        \s
        out vec3 TexColor;
        out vec2 TexCoord;
        \s
        uniform mat4 transform;
        \s
        void main()
        {
            gl_Position = transform * vec4(aPos, 1.0);
            TexColor = aColor;
            TexCoord = aTexCoord;
        }
    """;

    private final KGl33 gl;
    private @Nullable KShaderProgram defaultShader;
    private @Nullable KShaderProgram defaultTextureShader;

    /**
     * Constructs shader compiler with provided OpenGL 3.3 frontend.
     * @param gl OpenGL 3.3 frontend
     */
    public KGl33ShaderCompiler(@KInject final KGl33 gl) {
        super("gl33_shader_compiler");
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

        KSystemLogger.info(
            this.name,
            "Compiled shader id: %d", shaderId
        );
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

        KSystemLogger.info(
            this.name,
            "Linked shader program id: %d, vertex: %s, fragment: %s",
            program,
            vertex,
            fragment
        );
        return new KGl33ShaderProgram(program, this.gl);
    }

    @Override
    public KShaderProgram createShaderProgram(final KShader shader) {
        int program = this.gl.glCreateProgram();
        this.gl.glAttachShader(program, shader.id());
        this.gl.glLinkProgram(program);
        return new KGl33ShaderProgram(program, this.gl);
    }

    @Override
    public KShaderProgram getDefaultTextureShader() {
        if (this.defaultTextureShader == null) {
            this.defaultTextureShader = this.createShaderProgram(
                this.compileFragmentShader(DEFAULT_TEXTURE_FRAGMENT_SHADER),
                this.compileVertexShader(DEFAULT_TEXTURE_VERTEX_SHADER)
            );
        }

        return this.defaultTextureShader;
    }

    @Override
    public KShaderProgram getDefaultShader() {
        if (this.defaultShader == null) {
            this.defaultShader = this.createShaderProgram(
                this.compileFragmentShader(DEFAULT_SHAPE_FRAGMENT_SHADER),
                this.compileVertexShader(DEFAULT_SHAPE_VERTEX_SHADER)
            );
        }

        return this.defaultShader;
    }
}
