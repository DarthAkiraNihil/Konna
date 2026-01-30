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

/**
 * Interface for a tiled font format that returns glyph data for a character
 * inside font's tilesheet. However, it does not actually depend on it since
 * UV coordinates do not connect to tiled font texture size so only characters'
 * positioning inside it matters.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
public interface KTiledFontFormat {

    /**
     * Returns glyph data for a passed character.
     * @param symbol Character to get glyph data of
     * @return Glyph data of the character
     */
    KTiledFontGlyph getGlyph(char symbol);

}
