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

public class KPolygon implements KShape {

    private final KVector2i[] points;
    private KColor outlineColor;
    private KColor fillColor;
    private final KTransform transform;

    public KPolygon(
        KVector2i[] points,
        KColor outlineColor,
        KColor fillColor
    ) {
        this.points = points;
        this.outlineColor = outlineColor;
        this.fillColor = fillColor;
        this.transform = new KTransform();
    }

    public KPolygon(final int[] xPoints, final int[] yPoints) {
        this(xPoints, yPoints, null, null);
    }

    public KPolygon(final int[] xPoints, final int[] yPoints, final KColor outlineColor) {
        this(xPoints, yPoints, outlineColor, null);
    }

    public KPolygon(final int[] xPoints, final int[] yPoints, final KColor outlineColor, final KColor fillColor) {
        this(KStructUtils.furlArraysToVectors(xPoints, yPoints), outlineColor, fillColor);
    }

    public KPolygon(final KVector2i[] points) {
        this(points, null, null);
    }

    public KPolygon(final KVector2i[] points, final KColor outlineColor) {
        this(points, outlineColor, null);
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
