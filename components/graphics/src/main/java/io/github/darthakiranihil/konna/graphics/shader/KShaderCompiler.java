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

/**
 * Interface for shader compiler and shader program linker that does
 * not depend on used graphics framework.
 *
 * @since 0.1.0
 * @author Darth Akira Niihl
 */
public interface KShaderCompiler {

    /**
     * Compiles a fragment shader.
     * @param source Shader source
     * @return Compiled fragment shader
     */
    KShader compileFragmentShader(String source);

    /**
     * Compiles a vertex shader.
     * @param source Shader source
     * @return Compiled vertex shader
     */
    KShader compileVertexShader(String source);

    /**
     * Compiles a shader with specified type.
     * @param source Shader source
     * @param type Shader type
     * @return Compiled shader of specified type
     */
    KShader compileShader(String source, KShaderType type);

    /**
     * Creates a shader program from vertex and fragment shaders.
     * @param fragment Fragment shader of the program
     * @param vertex Vertex shader of the program
     * @return Linked shader program of passed shaders
     */
    KShaderProgram createShaderProgram(KShader fragment, KShader vertex);

    /**
     * Creates a shader program consisting of only one shader.
     * @param shader Shader to be added to the program
     * @return Linked shader program of passed shader
     */
    KShaderProgram createShaderProgram(KShader shader);

}
