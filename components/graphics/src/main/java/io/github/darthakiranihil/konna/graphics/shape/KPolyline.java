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

package io.github.darthakiranihil.konna.graphics.shape;

import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.graphics.KColor;
import io.github.darthakiranihil.konna.graphics.render.KRenderFrontend;

import java.util.Arrays;
import java.util.Objects;

public class KPolyline extends KAbstractShape {

    private final KVector2i[] points;
    private KColor color;

    public KPolyline(final KVector2i[] points, final KColor color) {
        this.points = points;
        this.color = color;
    }

    public KPolyline(final int[] xPoints, final int[] yPoints, final KColor color) {
        this(KStructUtils.furlArraysToVectors(xPoints, yPoints), color);
    }

    public KPolyline(final int[] xPoints, final int[] yPoints) {
        this(KStructUtils.furlArraysToVectors(xPoints, yPoints), KColor.TRANSPARENT);
    }

    public KPolyline(final KVector2i[] points) {
        this(points, KColor.TRANSPARENT);
    }

    @Override
    public void render(KRenderFrontend rf) {
        rf.render(this);
    }

    public KVector2i[] points() {
        return this.points;
    }

    public KColor getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KPolyline kPolyline = (KPolyline) o;
        return Objects.deepEquals(this.points, kPolyline.points) && Objects.equals(this.color, kPolyline.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(this.points), this.color);
    }

}
