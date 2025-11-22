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
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.core.struct.KVector2d;
import io.github.darthakiranihil.konna.core.struct.KVector2i;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

public class KPolygon extends KAbstractShape {

    private final KVector2i[] points;
    private KColor outlineColor;
    private KColor fillColor;

    public KPolygon(
        KVector2i[] points,
        KColor outlineColor,
        KColor fillColor
    ) {
        this.points = points;
        this.outlineColor = outlineColor;
        this.fillColor = fillColor;
    }

    public KPolygon(final int[] xPoints, final int[] yPoints) {
        this(xPoints, yPoints, KColor.TRANSPARENT, KColor.TRANSPARENT);
    }

    public KPolygon(final int[] xPoints, final int[] yPoints, final KColor outlineColor) {
        this(xPoints, yPoints, outlineColor, KColor.TRANSPARENT);
    }

    public KPolygon(final int[] xPoints, final int[] yPoints, final KColor outlineColor, final KColor fillColor) {
        this(KStructUtils.furlArraysToVectors(xPoints, yPoints), outlineColor, fillColor);
    }

    public KPolygon(final KVector2i[] points) {
        this(points, KColor.TRANSPARENT, KColor.TRANSPARENT);
    }

    public KPolygon(final KVector2i[] points, final KColor outlineColor) {
        this(points, outlineColor, KColor.TRANSPARENT);
    };

    public KVector2i[] points() {
        return this.points;
    }

    public KColor getOutlineColor() {
        return this.outlineColor;
    }

    public KColor getFillColor() {
        return this.fillColor;
    }

    @Override
    public void render(KRenderFrontend rf) {
        rf.render(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KPolygon kPolygon = (KPolygon) o;
        return
                Objects.deepEquals(this.points, kPolygon.points)
            &&  Objects.equals(this.outlineColor, kPolygon.outlineColor)
            &&  Objects.equals(this.fillColor, kPolygon.fillColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(this.points), this.outlineColor, this.fillColor);
    }
}
