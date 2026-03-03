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

/**
 * Special data structure that holds all information about a slice
 * of a base texture.
 * @param uv UV coordinates of the slice
 * @param xy XY coordinates of the slice
 * @param colors Colors of the slice
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public record KTextureSliceData(
    KVector2f[] uv,
    KVector2i[] xy,
    KColor[] colors
) {

}
