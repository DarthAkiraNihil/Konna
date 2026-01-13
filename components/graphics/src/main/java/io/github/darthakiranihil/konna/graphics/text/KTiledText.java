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
import io.github.darthakiranihil.konna.core.util.KStringUtils;
import io.github.darthakiranihil.konna.graphics.KColor;
import io.github.darthakiranihil.konna.graphics.image.KRenderableTexture;
import io.github.darthakiranihil.konna.graphics.render.KRenderFrontend;
import io.github.darthakiranihil.konna.graphics.shader.KShaderProgram;
import io.github.darthakiranihil.konna.graphics.shape.KAbstractShape;
import org.jspecify.annotations.Nullable;

public class KTiledText extends KAbstractShape {

    private static final int ELEMENTS_PER_GLYPH = 4;

    private final KVector2i startPos;

    private KColor color;
    private boolean ignoreNewline;
    private String text;
    private KTiledFont font;

    private @Nullable KRenderableTexture rendered;

    public KTiledText(
        final String text,
        final KVector2i startPos,
        final KTiledFont font,
        final KColor color
    ) {
        super(startPos);

        this.text = text;
        this.font = font;
        this.startPos = startPos;
        this.color = color;
        this.ignoreNewline = false;
    }

    public KTiledText(
        final KVector2i startPos,
        final KTiledFont font,
        final KColor color
    ) {
        this("", startPos, font, color);
    }

    public KTiledText(
        final String text,
        final KVector2i startPos,
        final KTiledFont font,
        final KColor color,
        final KShaderProgram shader
    ) {
        super(
            startPos,
            shader
        );

        this.text = text;
        this.font = font;
        this.startPos = startPos;
        this.color = color;
        this.ignoreNewline = false;
    }

    public KTiledText(
        final KVector2i startPos,
        final KTiledFont font,
        final KColor color,
        final KShaderProgram shader
    ) {
        this("", startPos, font, color, shader);
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

    public KColor getColor() {
        return this.color;
    }

    public void setColor(KColor color) {
        this.color = color;
    }

    public boolean isIgnoreNewline() {
        return this.ignoreNewline;
    }

    public void setIgnoreNewline(boolean ignoreNewline) {
        this.ignoreNewline = ignoreNewline;
    }

    public KRenderableTexture getRendered() {
        if (this.rendered != null) {
            return this.rendered;
        }

        KTiledFontGlyph[] glyphs = this.font.getTextGlyphs(this.text);
        int textLengthFull = this.ignoreNewline
            ? this.text.length() * ELEMENTS_PER_GLYPH
            : (this.text.length() - KStringUtils.count(this.text, "\n")) * ELEMENTS_PER_GLYPH;

        KVector2i[] xy = new KVector2i[textLengthFull];
        KVector2f[] uv = new KVector2f[textLengthFull];
        KColor[] colors = new KColor[textLengthFull];

        int line = 0;
        int column = 0;
        int putChars = 0;

        for (int i = 0; i < this.text.length(); i++) {
            if (!this.ignoreNewline && this.text.charAt(i) == '\n') {
                line++;
                column = 0;
                continue;
            }

            KVector2f[] glyphUv = glyphs[i].uv();
            xy[putChars * ELEMENTS_PER_GLYPH] = new KVector2i(
                this.startPos.x() + column * this.font.glyphSize().width(),
                this.startPos.y() + line * this.font.glyphSize().height()
            );
            uv[putChars * ELEMENTS_PER_GLYPH] = glyphUv[0];
            colors[putChars * ELEMENTS_PER_GLYPH] = this.color;

            xy[putChars * ELEMENTS_PER_GLYPH + 1] = new KVector2i(
                this.startPos.x() + (column + 1) * this.font.glyphSize().width(),
                this.startPos.y() + line * this.font.glyphSize().height()
            );
            uv[putChars * ELEMENTS_PER_GLYPH+ 1] = glyphUv[1];
            colors[putChars * ELEMENTS_PER_GLYPH + 1] = this.color;

            xy[putChars * ELEMENTS_PER_GLYPH + 2] = new KVector2i(
                this.startPos.x() + (column + 1) * this.font.glyphSize().width(),
                this.startPos.y() + (line + 1) * this.font.glyphSize().height()
            );
            uv[putChars * ELEMENTS_PER_GLYPH + 2] = glyphUv[2];
            colors[putChars * ELEMENTS_PER_GLYPH + 2] = this.color;

            xy[putChars * ELEMENTS_PER_GLYPH + 3] = new KVector2i(
                this.startPos.x() + column * this.font.glyphSize().width(),
                this.startPos.y() + (line + 1) * this.font.glyphSize().height()
            );
            uv[putChars * ELEMENTS_PER_GLYPH + 3] = glyphUv[3];
            colors[putChars * ELEMENTS_PER_GLYPH + 3] = this.color;

            column++;
            putChars++;
        }

        this.rendered = new KRenderableTexture(
            uv,
            xy,
            colors,
            this.font.face()
        );
        return this.rendered;
    }

    @Override
    public void render(final KRenderFrontend rf) {

        rf.render(this);

    }
}
