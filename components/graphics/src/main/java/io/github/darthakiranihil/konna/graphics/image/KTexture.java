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

package io.github.darthakiranihil.konna.graphics.image;

import io.github.darthakiranihil.konna.graphics.shader.KShaderProgram;

public class KTexture {

    private final KImage attachedImage;
    private final KShaderProgram shader;

    public KTexture(
        KImage image,
        KShaderProgram shader
    ) {
        this.attachedImage = image;
        this.shader = shader;
    }

    public KImage attachedImage() {
        return this.attachedImage;
    }

    public KShaderProgram shader() {
        return this.shader;
    }

}
