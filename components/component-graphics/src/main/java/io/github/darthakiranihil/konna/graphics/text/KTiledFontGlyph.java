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

/**
 * Container for character information of a tiled font from its tilesheet.
 * @param uv UV coordinates of the character in tiled font's tilesheet
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
public record KTiledFontGlyph(
    KVector2f[] uv
) {
}
