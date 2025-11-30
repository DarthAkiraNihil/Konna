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

package io.github.darthakiranihil.konna.core.graphics.image;

import io.github.darthakiranihil.konna.core.graphics.KVertexAttribute;
import io.github.darthakiranihil.konna.core.graphics.render.KRenderFrontend;
import io.github.darthakiranihil.konna.core.graphics.shader.KShaderProgram;
import io.github.darthakiranihil.konna.core.graphics.shape.KAbstractShape;
import io.github.darthakiranihil.konna.core.graphics.shape.KColor;
import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.struct.KVector2i;

public class KTexture extends KAbstractShape {

    public static final String U_TEXTURE = "u_texture";
    public static final String U_PROJ_VIEW = "u_projView";

    public static final String DEFAULT_VERT_SHADER = "uniform mat4 " + U_PROJ_VIEW + ";\n"
        + "attribute vec4 " + KVertexAttribute.ATTR_COLOR + ";\n" + "attribute vec2 " + KVertexAttribute.ATTR_TEX_COORD + ";\n"
        + "attribute vec2 " + KVertexAttribute.ATTR_POSITION + ";\n" + "varying vec4 vColor;\n"
        + "varying vec2 vTexCoord; \n" + "void main() {\n" + "	vColor = " + KVertexAttribute.ATTR_COLOR + ";\n"
        + "	vTexCoord = " + KVertexAttribute.ATTR_TEX_COORD + ";\n" + "	gl_Position = " + U_PROJ_VIEW
        + " * vec4(" + KVertexAttribute.ATTR_POSITION + ".xy, 0.0, 1.0);\n" + "}";

    public static final String DEFAULT_FRAG_SHADER = "uniform sampler2D " + U_TEXTURE + ";\n"
        + "varying vec4 vColor;\n" + "varying vec2 vTexCoord;\n" + "void main() {\n"
        + "	vec4 texColor = texture2D(" + U_TEXTURE + ", vTexCoord);\n"
        + "	gl_FragColor = vColor * texColor;\n" + "}";

    private final KVector2f[] uv;
    private final KVector2i[] xy;
    private final KColor color;

    private KImage attachedImage;
    private KShaderProgram shader;

    public KTexture(
        KVector2f[] uv,
        KVector2i[] xy,
        KImage image,
        KShaderProgram shader
    ) {
        this.uv = uv;
        this.xy = xy;
        this.attachedImage = image;
        this.shader = shader;
        this.color = KColor.WHITE;
    }


    public KVector2i[] xy() {
        return this.xy;
    }

    public KVector2f[] uv() {
        return this.uv;
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

    public KShaderProgram getShader() {
        return this.shader;
    }

    public void setShader(KShaderProgram shader) {
        this.shader = shader;
    }

    @Override
    public void render(KRenderFrontend rf) {
        if (this.shader != null) {
            rf.setActiveShader(this.shader);
        }
        rf.render(this);
        rf.disableActiveShader();
    }
}
