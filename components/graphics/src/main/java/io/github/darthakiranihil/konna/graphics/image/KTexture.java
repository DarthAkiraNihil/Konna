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

import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.graphics.KColor;
import io.github.darthakiranihil.konna.graphics.render.KRenderFrontend;
import io.github.darthakiranihil.konna.graphics.shader.KShaderProgram;
import io.github.darthakiranihil.konna.graphics.shape.KAbstractShape;
import org.jspecify.annotations.Nullable;

public class KTexture {

    private final KColor color;

    private KImage attachedImage;
    private @Nullable KShaderProgram shader;

    public KTexture(
        KImage image,
        KShaderProgram shader
    ) {
        this.attachedImage = image;
        this.shader = shader;
        this.color = KColor.WHITE;
    }

    public KColor color() {
        return this.color;
    }

    public KImage getAttachedImage() {
        return this.attachedImage;
    }

    public void setAttachedImage(KImage attachedImage) {
        this.attachedImage = attachedImage;
    }

    public @Nullable KShaderProgram getShader() {
        return this.shader;
    }

    public void setShader(KShaderProgram shader) {
        this.shader = shader;
    }

}
