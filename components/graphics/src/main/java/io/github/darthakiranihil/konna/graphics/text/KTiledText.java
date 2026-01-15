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

/**
 * Renderable primitive that represents text, which characters are taken
 * from a tilesheet (that is a texture) with fixed size of them.
 * It is a mutable container so its text can be edited without creating a
 * new object of this type. By default, all tiled texts do not render newlines
 * as a character from their own tilesheets (and then it creates a new line on rendered text).
 * In order to ignore it and render '\n' char as is, a ignoreNewline flag should be enabled
 * via {@link KTiledText#setIgnoreNewline(boolean)}.
 * Attention: by default it uses 0-texture unit.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public class KTiledText extends KAbstractShape {

    private static final int ELEMENTS_PER_GLYPH = 4;

    private final KVector2i startPos;

    private KColor color;
    private boolean ignoreNewline;
    private String text;
    private KTiledFont font;
    private int unit;

    private @Nullable KRenderableTexture rendered;

    /**
     * Standard constructor.
     * @param text Text string
     * @param startPos Render start position
     * @param font Text font
     * @param color Text color
     */
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

    /**
     * Standard constructor with empty text.
     * @param startPos Render start position
     * @param font Text font
     * @param color Text color
     */
    public KTiledText(
        final KVector2i startPos,
        final KTiledFont font,
        final KColor color
    ) {
        this("", startPos, font, color);
    }

    /**
     * Standard constructor with specific shader.
     * @param text Text string
     * @param startPos Render start position
     * @param font Text font
     * @param color Text color
     * @param shader Specific shader to be used for its rendering
     */
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

    /**
     * Standard constructor with empty text and specific shader.
     * @param startPos Render start position
     * @param font Text font
     * @param color Text color
     * @param shader Specific shader to be used for its rendering
     */
    public KTiledText(
        final KVector2i startPos,
        final KTiledFont font,
        final KColor color,
        final KShaderProgram shader
    ) {
        this("", startPos, font, color, shader);
    }

    /**
     * @return Text string of this tiled text object
     */
    public String getText() {
        return this.text;
    }

    /**
     * Sets text string for this tiled text object.
     * @param text New text string
     */
    public void setText(final String text) {
        this.text = text;
        this.rendered = null;
    }

    /**
     * @return Font of this tiled text object.
     */
    public KTiledFont getFont() {
        return this.font;
    }

    /**
     * Sets font for this tiled text object.
     * @param font New text font
     */
    public void setFont(final KTiledFont font) {
        this.font = font;
        this.rendered = null;
    }

    /**
     * @return Color of this tiled text object.
     */
    public KColor getColor() {
        return this.color;
    }

    /**
     * Sets text color for this tiled text object.
     * @param color New text color
     */
    public void setColor(final KColor color) {
        this.color = color;
        this.rendered = null;
    }

    /**
     * @return IgnoreNewLine flag of this tiled text object
     */
    public boolean isIgnoreNewline() {
        return this.ignoreNewline;
    }

    /**
     * Sets IgnoreNewLine flag for this tiled text object.
     * @param ignoreNewline New value of IgnoreNewLine flag
     */
    public void setIgnoreNewline(boolean ignoreNewline) {
        this.ignoreNewline = ignoreNewline;
        this.rendered = null;
    }

    /**
     * @return Texture unit for this renderable texture.
     */
    public int getUnit() {
        return this.unit;
    }

    /**
     * Sets texture unit for this renderable texture.
     * @param unit New texture unit
     */
    public void setUnit(int unit) {
        this.unit = unit;
        this.rendered = null;
    }

    /**
     * @return Renderable texture representation for this tiled text object.
     */
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
            uv[putChars * ELEMENTS_PER_GLYPH + 1] = glyphUv[1];
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
            this.font.face(),
            this.unit
        );
        return this.rendered;
    }

    @Override
    public void render(final KRenderFrontend rf) {
        rf.render(this);
    }
}
