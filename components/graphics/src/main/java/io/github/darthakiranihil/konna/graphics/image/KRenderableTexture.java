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
import io.github.darthakiranihil.konna.graphics.render.KRenderFrontend;
import io.github.darthakiranihil.konna.graphics.shader.KShaderProgram;
import io.github.darthakiranihil.konna.graphics.shape.KAbstractShape;

import java.util.Arrays;
import java.util.Objects;

public class KRenderableTexture extends KAbstractShape {

    private final KVector2f[] uv;
    private final KVector2i[] xy;
    private final KTexture texture;

    public KRenderableTexture(
        KVector2f[] uv,
        KVector2i[] xy,
        KTexture texture
    ) {
        this.uv = uv;
        this.xy = xy;
        this.texture = texture;
    }

    public KVector2i[] xy() {
        return this.xy;
    }

    public KVector2f[] uv() {
        return this.uv;
    }

    public KTexture texture() {
        return this.texture;
    }

    @Override
    public void render(KRenderFrontend rf) {
        KShaderProgram textureShader = this.texture.getShader();
        if (textureShader != null) {
            rf.setActiveShader(textureShader);
        }

        rf.render(this);
        rf.disableActiveShader();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        KRenderableTexture that = (KRenderableTexture) o;
        return Objects.deepEquals(this.uv, that.uv) && Objects.deepEquals(this.xy, that.xy) && Objects.equals(this.texture, that.texture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(this.uv), Arrays.hashCode(this.xy), this.texture);
    }
}
