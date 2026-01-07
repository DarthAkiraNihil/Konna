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

    public static final String U_TEXTURE = "u_texture";
    public static final String U_PROJ_VIEW = "u_projView";

    public static final String ATTR_COLOR = "a_color";
    public static final String ATTR_POSITION = "a_position";
    public static final String ATTR_TEX_COORD = "a_tex_coord";

    public static final String DEFAULT_VERT_SHADER = "uniform mat4 " + U_PROJ_VIEW + ";\n"
        + "attribute vec4 " + ATTR_COLOR + ";\n" + "attribute vec2 " + ATTR_TEX_COORD + ";\n"
        + "attribute vec2 " + ATTR_POSITION + ";\n" + "varying vec4 vColor;\n"
        + "varying vec2 vTexCoord; \n" + "void main() {\n" + "	vColor = " + ATTR_COLOR + ";\n"
        + "	vTexCoord = " + ATTR_TEX_COORD + ";\n" + "	gl_Position = " + U_PROJ_VIEW
        + " * vec4(" + ATTR_POSITION + ".xy, 0.0, 1.0);\n" + "}";

    public static final String DEFAULT_FRAG_SHADER = "uniform sampler2D " + U_TEXTURE + ";\n"
        + "varying vec4 vColor;\n" + "varying vec2 vTexCoord;\n" + "void main() {\n"
        + "	vec4 texColor = texture2D(" + U_TEXTURE + ", vTexCoord);\n"
        + "	gl_FragColor = vColor * texColor;\n" + "}";

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
