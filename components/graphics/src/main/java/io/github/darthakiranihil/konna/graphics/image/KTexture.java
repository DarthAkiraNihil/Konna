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

/**
 * Simple container for texture data that holds data of what to render.
 * However, this is not renderable since does not provide vertices and uvs.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public class KTexture {

    private final KImage attachedImage;
    private final KShaderProgram shader;

    /**
     * Standard constructor.
     * @param image Image attached to this texture
     * @param shader Shader used for rendering this texture
     */
    public KTexture(
        final KImage image,
        final KShaderProgram shader
    ) {
        this.attachedImage = image;
        this.shader = shader;
    }

    /**
     * Returns attached image of this texture.
     * @return Attached image of this texture
     */
    public KImage attachedImage() {
        return this.attachedImage;
    }

    /**
     * Returns shader used for rendering this texture.
     * @return Shader used for rendering this texture
     */
    public KShaderProgram shader() {
        return this.shader;
    }

}
