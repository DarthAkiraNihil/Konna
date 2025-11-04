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

package io.github.darthakiranihil.konna.core.graphics.shape;

import io.github.darthakiranihil.konna.core.struct.KIntVector2d;

public record KCircle(
    int x,
    int y,
    int r,
    KColor outlineColor,
    KColor fillColor
) {

    public KCircle(int x, int y, int r) {
        this(x, y, r, null, null);
    }

    public KCircle(int x, int y, int r, final KColor outlineColor) {
        this(x, y, r, outlineColor, null);
    }

    public KCircle(final KIntVector2d coordinates, int r) {
        this(coordinates, r, null, null);
    }

    public KCircle(final KIntVector2d coordinates, int r, final KColor outlineColor) {
        this(coordinates, r, outlineColor, null);
    }

    public KCircle(final KIntVector2d coordinates, int r, final KColor outlineColor, final KColor fillColor) {
        this(coordinates.x(), coordinates.y(), r, outlineColor, fillColor);
    }

}
