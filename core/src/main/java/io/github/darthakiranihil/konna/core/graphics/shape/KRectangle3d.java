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

import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;

public record KRectangle3d(
    int x,
    int y,
    int width,
    int height,
    boolean raised,
    KColor outlineColor,
    KColor fillColor
) {

    public KRectangle3d(final KVector2i coordinates, final KSize size, boolean raised) {
        this(coordinates, size, raised, null, null);
    }

    public KRectangle3d(final KVector2i coordinates, final KSize size, boolean raised, final KColor outlineColor) {
        this(coordinates, size, raised, outlineColor, null);
    }

    public KRectangle3d(final KVector2i coordinates, final KSize size, boolean raised, final KColor outlineColor, final KColor fillColor) {
        this(coordinates.x(), coordinates.y(), size.width(), size.height(), raised, outlineColor, fillColor);
    }

    public KRectangle3d(int x, int y, int width, int height, boolean raised) {
        this(x, y, width, height, raised, null, null);
    }

    public KRectangle3d(int x, int y, int width, int height, boolean raised, final KColor outlineColor) {
        this(x, y, width, height, raised, outlineColor, null);
    }

    public static KRectangle3d square(int x, int y, int side, boolean raised) {
        return new KRectangle3d(x, y, side, side, raised, null, null);
    }

    public static KRectangle3d square(int x, int y, int side, boolean raised, final KColor outlineColor) {
        return new KRectangle3d(x, y, side, side, raised, outlineColor, null);
    }

    public static KRectangle3d square(int x, int y, int side, boolean raised, final KColor outlineColor, final KColor fillColor) {
        return new KRectangle3d(x, y, side, side, raised, outlineColor, fillColor);
    }

    public static KRectangle3d square(final KVector2i coordinates, int side, boolean raised) {
        return new KRectangle3d(coordinates, KSize.squared(side), raised);
    }

    public static KRectangle3d square(final KVector2i coordinates, int side, boolean raised, final KColor outlineColor) {
        return new KRectangle3d(coordinates, KSize.squared(side), raised, outlineColor);
    }

    public static KRectangle3d square(final KVector2i coordinates, int side, boolean raised, final KColor outlineColor, final KColor fillColor) {
        return new KRectangle3d(coordinates, KSize.squared(side), raised, outlineColor, fillColor);
    }

}
