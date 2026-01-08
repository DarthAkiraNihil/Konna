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

package io.github.darthakiranihil.konna.graphics.impl;

import io.github.darthakiranihil.konna.graphics.shader.KShader;
import io.github.darthakiranihil.konna.graphics.shader.KShaderCompiler;
import io.github.darthakiranihil.konna.graphics.shader.KShaderProgram;
import io.github.darthakiranihil.konna.graphics.shader.KShaderType;
import org.jspecify.annotations.NullMarked;

@NullMarked
public class TestShaderCompiler implements KShaderCompiler {

    @Override
    public KShader compileFragmentShader(String source) {
        return new KShader(0, KShaderType.FRAGMENT);
    }

    @Override
    public KShader compileVertexShader(String source) {
        return new KShader(0, KShaderType.VERTEX);
    }

    @Override
    public KShader compileShader(String source, KShaderType type) {
        return new KShader(0, type);
    }

    @Override
    public KShaderProgram createShaderProgram(KShader fragment, KShader vertex) {
        return new TestShaderProgram();
    }

    @Override
    public KShaderProgram createShaderProgram(KShader shader) {
        return new TestShaderProgram();
    }

    @Override
    public KShaderProgram getDefaultTextureShader() {
        return new TestShaderProgram();
    }
}
