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

package io.github.darthakiranihil.konna.core.graphics.text.std;

import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.graphics.image.KImage;
import io.github.darthakiranihil.konna.core.graphics.KShader;
import io.github.darthakiranihil.konna.core.graphics.text.KTiledFont;

import java.util.HashMap;
import java.util.Map;

public final class KStandardAsciiTiledFont extends KTiledFont {

    private static final int FINAL_CHAR_CODE = 256;
    private static final int CHAR_TABLE_DIMENSION = 16;

    private final Map<Character, KImage> mappedCharacters;

    public KStandardAsciiTiledFont(final String name, final KSize charSize, final KImage face, final KShader[] shaders) {
        super(name, charSize, face, shaders);
        this.mappedCharacters = new HashMap<>();
        this.fillMapping();
    }

    public KStandardAsciiTiledFont(final String name, final KSize charSize, final KImage face) {
        super(name, charSize, face);
        this.mappedCharacters = new HashMap<>();
        this.fillMapping();
    }

    private void fillMapping() {
        for (int i = 0; i < FINAL_CHAR_CODE; i++) {

            int x = (i % CHAR_TABLE_DIMENSION) * this.charSize.width();
            int y = (i / CHAR_TABLE_DIMENSION) * this.charSize.height();

            this.mappedCharacters.put(
                (char) i,
                this.face.slice(new KVector2i(x, y), this.charSize)
            );

        }
    }

    @Override
    public KImage resolveChar(char ch) {
        return this.mappedCharacters.get(ch);
    }
}
