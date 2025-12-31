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

import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.graphics.KColor;
import org.junit.jupiter.api.Test;

public class KPolygonPositiveTests extends KShapeTestClass {

    private final KVector2i[] points = new KVector2i[] {
        new KVector2i(10, 10),
        new KVector2i(10, 16),
        new KVector2i(16, 16),
    };
    private final int[] xs = new int[] {10, 10, 16};
    private final int[] ys = new int[] {10, 16, 16};

    private final KColor outlineColor = KColor.GREEN;
    private final KColor fillColor = KColor.YELLOW;

    @Test
    public void testCreateShapeStandard() {

        KPolygon shape = new KPolygon(this.points, this.outlineColor, this.fillColor);
        this.assertPolygon(shape, this.points, this.outlineColor, this.fillColor);

    }

    @Test
    public void testCreateShapeWithoutColors() {

        KPolygon shape = new KPolygon(this.points);
        this.assertPolygon(shape, this.points, KColor.TRANSPARENT, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeWithoutFillColor() {

        KPolygon shape = new KPolygon(this.points, this.outlineColor);
        this.assertPolygon(shape, this.points, outlineColor, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeStandardSeparated() {

        KPolygon shape = new KPolygon(this.xs, this.ys, this.outlineColor, this.fillColor);
        this.assertPolygon(shape, this.points, this.outlineColor, this.fillColor);

    }

    @Test
    public void testCreateShapeWithoutColorsSeparated() {

        KPolygon shape = new KPolygon(this.xs, this.ys);
        this.assertPolygon(shape, this.points, KColor.TRANSPARENT, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeWithoutFillColorSeparated() {

        KPolygon shape = new KPolygon(this.xs, this.ys, this.outlineColor);
        this.assertPolygon(shape, this.points, this.outlineColor, KColor.TRANSPARENT);

    }

}
