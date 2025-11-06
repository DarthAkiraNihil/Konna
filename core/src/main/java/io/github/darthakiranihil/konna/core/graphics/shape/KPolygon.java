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

import io.github.darthakiranihil.konna.core.struct.KVector2i;

import java.awt.*;
import java.util.Arrays;

public record KPolygon(
    int[] xPoints,
    int[] yPoints,
    int edgesCount,
    KColor outlineColor,
    KColor fillColor
) {

    public KPolygon(final int[] xPoints, final int[] yPoints, int edgesCount) {
        this(xPoints, yPoints, edgesCount, null, null);
    }

    public KPolygon(final int[] xPoints, final int[] yPoints, int edgesCount, final KColor outlineColor) {
        this(xPoints, yPoints, edgesCount, outlineColor, null);
    }

    public KPolygon(final KVector2i[] points) {
        this(points, null, null);
    }

    public KPolygon(final KVector2i[] points, final KColor outlineColor) {
        this(points, outlineColor, null);
    }

    public KPolygon(final KVector2i[] points, final KColor outlineColor, final KColor fillColor) {
        this(
            Arrays.stream(points).mapToInt(KVector2i::x).toArray(),
            Arrays.stream(points).mapToInt(KVector2i::y).toArray(),
            points.length,
            outlineColor,
            fillColor
        );
    }

    public Polygon raw() {
        return new Polygon(this.xPoints, this.yPoints, this.edgesCount);
    }

}
