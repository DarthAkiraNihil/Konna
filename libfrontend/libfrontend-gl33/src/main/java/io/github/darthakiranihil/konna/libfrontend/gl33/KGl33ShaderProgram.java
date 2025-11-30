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

import io.github.darthakiranihil.konna.core.graphics.shader.KShaderProgram;
import io.github.darthakiranihil.konna.libfrontend.gl20.KGl20;

public final class KGl33ShaderProgram implements KShaderProgram {

    private final int id;
    private final KGl33 gl;

    public KGl33ShaderProgram(int id, KGl33 gl) {
        this.id = id;
        this.gl = gl;
    }

    @Override
    public int id() {
        return this.id;
    }

    @Override
    public int getAttribute(String name) {
        return this.gl.glGetAttribLocation(this.id, name);
    }

    @Override
    public int getUniform(String name) {
        return this.gl.glGetUniformLocation(this.id, name);
    }
}
