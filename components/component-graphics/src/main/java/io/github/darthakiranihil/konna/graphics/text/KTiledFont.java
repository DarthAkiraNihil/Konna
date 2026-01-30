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

import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.graphics.image.KTexture;

/**
 * Container for a tiled font where characters are taken from a tilesheet.
 * @param name Name of the font
 * @param face Font tilesheet texture to get characters from
 * @param format Tile font format
 * @param glyphSize Size of a character inside its tilesheet
 */
public record KTiledFont(
    String name,
    KTexture face,
    KTiledFontFormat format,
    KSize glyphSize
) {

    /**
     * Returns glyph data for passed string.
     * @param text Text to get glyphs of
     * @return Font glyphs of the text
     */
    public KTiledFontGlyph[] getTextGlyphs(final String text) {

        KTiledFontGlyph[] glyphs = new KTiledFontGlyph[text.length()];
        for (int i = 0; i < text.length(); i++) {
            glyphs[i] = this.format.getGlyph(text.charAt(i));
        }
        return glyphs;

    }
}
