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

import io.github.darthakiranihil.konna.core.graphics.render.KRenderFrontend;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;

public class KRectangle extends KPolygon {

    private final int width;
    private final int height;

    public KRectangle(int x, int y, int width, int height, final KColor outlineColor, final KColor fillColor) {
        super(
            new KVector2i[] {
                new KVector2i(x, y),
                new KVector2i(x + width, y),
                new KVector2i(x + width, y + height),
                new KVector2i(x, y + height),
            },
            outlineColor,
            fillColor
        );
        this.width = width;
        this.height = height;
    }

    public KRectangle(final KVector2i coordinates, final KSize size) {
        this(coordinates, size, null, null);
    }

    public KRectangle(final KVector2i coordinates, final KSize size, final KColor outlineColor) {
        this(coordinates, size, outlineColor, null);
    }

    public KRectangle(final KVector2i coordinates, final KSize size, final KColor outlineColor, final KColor fillColor) {
        this(coordinates.x(), coordinates.y(), size.width(), size.height(), outlineColor, fillColor);
    }

    public KRectangle(int x, int y, int width, int height) {
        this(x, y, width, height, null, null);
    }

    public KRectangle(int x, int y, int width, int height, final KColor outlineColor) {
        this(x, y, width, height, outlineColor, null);
    }

    public static KRectangle square(int x, int y, int side) {
        return new KRectangle(x, y, side, side, null, null);
    }

    public static KRectangle square(int x, int y, int side, final KColor outlineColor) {
        return new KRectangle(x, y, side, side, outlineColor, null);
    }

    public static KRectangle square(int x, int y, int side, final KColor outlineColor, final KColor fillColor) {
        return new KRectangle(x, y, side, side, outlineColor, fillColor);
    }

    public static KRectangle square(final KVector2i coordinates, int side) {
        return new KRectangle(coordinates, KSize.squared(side));
    }

    public static KRectangle square(final KVector2i coordinates, int side, final KColor outlineColor) {
        return new KRectangle(coordinates, KSize.squared(side), outlineColor);
    }

    public static KRectangle square(final KVector2i coordinates, int side, final KColor outlineColor, final KColor fillColor) {
        return new KRectangle(coordinates, KSize.squared(side), outlineColor, fillColor);
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    @Override
    public void render(final KRenderFrontend rf) {
        rf.render(this);
    }

}
