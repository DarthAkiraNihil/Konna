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

package io.github.darthakiranihil.konna.graphics.text.std;

import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.graphics.text.KTiledFontFormat;
import io.github.darthakiranihil.konna.graphics.text.KTiledFontGlyph;

@KSingleton
public class KSquaredAsciiTiledFontFormat extends KObject implements KTiledFontFormat {

    private final KTiledFontGlyph[][] generatedGlyphs;

    private static final float UV_WIDTH = 0.0625f;

    public KSquaredAsciiTiledFontFormat() {
        super("squared_ascii_tiled_font_format");
        this.generatedGlyphs = new KTiledFontGlyph[16][16];

        for (int i = 0; i < 256; i++) {
            int row = i / 16;
            int col = i % 16;
            this.generatedGlyphs[row][col] = new KTiledFontGlyph(
                new KVector2f[]{
                    new KVector2f(UV_WIDTH * col, UV_WIDTH * row),
                    new KVector2f(UV_WIDTH * (col + 1), UV_WIDTH * row),
                    new KVector2f(UV_WIDTH * (col + 1), UV_WIDTH * (row + 1)),
                    new KVector2f(UV_WIDTH * col, UV_WIDTH * (row + 1)),
                }
            );
        }
    }

    @Override
    public KTiledFontGlyph getGlyph(char symbol) {
        return this.generatedGlyphs[(int) symbol / 16][(int) symbol % 16];
    }
}
