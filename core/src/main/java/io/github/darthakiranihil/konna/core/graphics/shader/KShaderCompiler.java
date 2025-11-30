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

package io.github.darthakiranihil.konna.core.graphics.shader;

public interface KShaderCompiler {

    KShader compileFragmentShader(String source);
    KShader compileVertexShader(String source);
    KShader compileShader(String source, KShaderType type);

    KShaderProgram createShaderProgram(KShader fragment, KShader vertex);
    KShaderProgram createShaderProgram(KShader shader);

}
