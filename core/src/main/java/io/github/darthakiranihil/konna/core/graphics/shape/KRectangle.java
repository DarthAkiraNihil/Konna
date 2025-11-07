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

import io.github.darthakiranihil.konna.core.graphics.KTransform;
import io.github.darthakiranihil.konna.core.graphics.render.KRenderFrontend;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2d;
import io.github.darthakiranihil.konna.core.struct.KVector2i;

public class KRectangle implements KShape {

    private final int x;
    private final int y;
    private final int width;
    private final int height;

    private KColor outlineColor;
    private KColor fillColor;
    private final KTransform transform;

    public KRectangle(int x, int y, int width, int height, final KColor outlineColor, final KColor fillColor) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.outlineColor = outlineColor;
        this.fillColor = fillColor;
        this.transform = new KTransform();
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

    @Override
    public int x() {
        return this.x;
    }

    @Override
    public int y() {
        return this.y;
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    public KColor getOutlineColor() {
        return this.outlineColor;
    }

    public KColor getFillColor() {
        return this.fillColor;
    }

    @Override
    public void render(final KRenderFrontend rf) {
        rf.render(this);
    }

    @Override
    public KTransform getTransform() {
        return this.transform;
    }

    @Override
    public void rotate(double theta) {
        this.transform.rotate(theta);
    }

    @Override
    public void rotate(double theta, KVector2i pivot) {
        this.transform.rotate(theta, pivot);
    }

    @Override
    public void scale(KVector2d factor) {
        this.transform.scale(factor);
    }

    @Override
    public void translate(KVector2i value) {
        this.transform.translate(value);
    }
}
