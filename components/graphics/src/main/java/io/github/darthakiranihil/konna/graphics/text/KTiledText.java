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

package io.github.darthakiranihil.konna.graphics.text;

import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.graphics.KColor;
import io.github.darthakiranihil.konna.graphics.image.KRenderableTexture;
import io.github.darthakiranihil.konna.graphics.render.KRenderFrontend;
import io.github.darthakiranihil.konna.graphics.shader.KShaderProgram;
import io.github.darthakiranihil.konna.graphics.shape.KAbstractShape;
import org.jspecify.annotations.Nullable;

public class KTiledText extends KAbstractShape {

    private final KVector2i startPos;
    private String text;
    private KTiledFont font;

    private @Nullable KRenderableTexture rendered;

    public KTiledText(
        final String text,
        final KVector2i startPos,
        final KTiledFont font
    ) {
        super(
            new KVector2i(
                text.length() / 2 * font.glyphSize().width(),
                text.length() / 2 * font.glyphSize().height()
            )
        );

        this.text = text;
        this.font = font;
        this.startPos = startPos;
    }

    public KTiledText(
        final KVector2i startPos,
        final KTiledFont font
    ) {
        this("", startPos, font);
    }

    public KTiledText(
        final String text,
        final KVector2i startPos,
        final KTiledFont font,
        final KShaderProgram shader
    ) {
        super(
            new KVector2i(
                text.length() / 2 * font.glyphSize().width(),
                text.length() / 2 * font.glyphSize().height()
            ),
            shader
        );

        this.text = text;
        this.font = font;
        this.startPos = startPos;
    }

    public KTiledText(
        final KVector2i startPos,
        final KTiledFont font,
        final KShaderProgram shader
    ) {
        this("", startPos, font, shader);
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
        this.rendered = null;
    }

    public KTiledFont getFont() {
        return this.font;
    }

    public void setFont(KTiledFont font) {
        this.font = font;
        this.rendered = null;
    }

    public KRenderableTexture getRendered() {
        if (this.rendered != null) {
            return this.rendered;
        }

        KTiledFontGlyph[] glyphs = this.font.getTextGlyphs(this.text);
        KVector2i[] xy = new KVector2i[this.text.length() * 4];
        KVector2f[] uv = new KVector2f[this.text.length() * 4];
        KColor[] colors = new KColor[this.text.length() * 4];

        int textLengthFull = this.text.length() * 4;
        for (int i = 0; i < textLengthFull; i += 4) {

            KVector2f[] glyphUv = glyphs[i].uv();

            xy[i] = new KVector2i(
                this.startPos.x() + i * this.font.glyphSize().width(),
                this.startPos.y()
            );

            uv[i] = glyphUv[i];
            colors[i] = KColor.WHITE;

            xy[i + 1] = new KVector2i(
                this.startPos.x() + (i + 1) * this.font.glyphSize().width(),
                this.startPos.y()
            );

            uv[i + 1] = glyphUv[i + 1];
            colors[i] = KColor.WHITE;

            xy[i + 2] = new KVector2i(
                this.startPos.x() + (i + 1) * this.font.glyphSize().width(),
                this.startPos.y() + this.font.glyphSize().height()
            );

            uv[i + 2] = glyphUv[i + 2];
            colors[i] = KColor.WHITE;

            xy[i + 3] = new KVector2i(
                this.startPos.x() + i * this.font.glyphSize().width(),
                this.startPos.y() + this.font.glyphSize().height()
            );

            uv[i + 3] = glyphUv[i + 3];
            colors[i] = KColor.WHITE;
        }

        this.rendered = new KRenderableTexture(
            uv,
            xy,
            colors,
            this.font.texture()
        );
        return this.rendered;
    }

    @Override
    public void render(final KRenderFrontend rf) {

        rf.render(this);

    }
}
