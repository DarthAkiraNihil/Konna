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

import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.graphics.KColor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KArcPositiveTests extends KShapeTestClass {

    private final KVector2i coordinates = new KVector2i(10, 10);
    private final KSize size = new KSize(10, 10);
    private final int startAngle = 30;
    private final int arcAngle = 60;
    private final KColor outlineColor = KColor.BLACK;
    private final KColor fillColor = KColor.GRAY;

    @Test
    public void testCreateShapeStandard() {

        KArc shape = new KArc(this.coordinates, this.size, this.startAngle, this.arcAngle, this.outlineColor, this.fillColor);
        this.assertArc(shape, this.coordinates, this.size, this.startAngle, this.arcAngle, null, this.outlineColor, this.fillColor);

    }

    @Test
    public void testCreateShapeWithoutColors() {

        KArc shape = new KArc(this.coordinates, this.size, this.startAngle, this.arcAngle);
        this.assertArc(shape, this.coordinates, this.size, this.startAngle, this.arcAngle, null, KColor.TRANSPARENT, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeWithoutFillColor() {

        KArc shape = new KArc(this.coordinates, this.size, this.startAngle, this.arcAngle, this.outlineColor);
        this.assertArc(shape, this.coordinates, this.size, this.startAngle, this.arcAngle, null, this.outlineColor, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeStandardSeparated() {

        KArc shape = new KArc(this.coordinates.x(), this.coordinates.y(), this.size.width(), this.size.height(), this.startAngle, this.arcAngle, this.outlineColor, this.fillColor);
        this.assertArc(shape, this.coordinates, this.size, this.startAngle, this.arcAngle, null, this.outlineColor, this.fillColor);

    }

    @Test
    public void testCreateShapeWithoutColorsSeparated() {

        KArc shape = new KArc(this.coordinates.x(), this.coordinates.y(), this.size.width(), this.size.height(), this.startAngle, this.arcAngle);
        this.assertArc(shape, this.coordinates, this.size, this.startAngle, this.arcAngle, null, KColor.TRANSPARENT, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeWithoutFillColorSeparated() {

        KArc shape = new KArc(this.coordinates.x(), this.coordinates.y(), this.size.width(), this.size.height(), this.startAngle, this.arcAngle, this.outlineColor);
        this.assertArc(shape, this.coordinates, this.size, this.startAngle, this.arcAngle, null, this.outlineColor, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeStandardWithShader() {

        KArc shape = new KArc(this.coordinates, this.size, this.startAngle, this.arcAngle, this.shader, this.outlineColor, this.fillColor);
        this.assertArc(shape, this.coordinates, this.size, this.startAngle, this.arcAngle, this.shader, this.outlineColor, this.fillColor);

    }

    @Test
    public void testCreateShapeWithoutColorsWithShader() {

        KArc shape = new KArc(this.coordinates, this.size, this.startAngle, this.arcAngle, this.shader);
        this.assertArc(shape, this.coordinates, this.size, this.startAngle, this.arcAngle, this.shader, KColor.TRANSPARENT, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeWithoutFillColorWithShader() {

        KArc shape = new KArc(this.coordinates, this.size, this.startAngle, this.arcAngle, this.shader, this.outlineColor);
        this.assertArc(shape, this.coordinates, this.size, this.startAngle, this.arcAngle, this.shader, this.outlineColor, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeStandardSeparatedWithShader() {

        KArc shape = new KArc(this.coordinates.x(), this.coordinates.y(), this.size.width(), this.size.height(), this.startAngle, this.arcAngle, this.shader, this.outlineColor, this.fillColor);
        this.assertArc(shape, this.coordinates, this.size, this.startAngle, this.arcAngle, this.shader, this.outlineColor, this.fillColor);

    }

    @Test
    public void testCreateShapeWithoutColorsSeparatedWithShader() {

        KArc shape = new KArc(this.coordinates.x(), this.coordinates.y(), this.size.width(), this.size.height(), this.startAngle, this.arcAngle, this.shader);
        this.assertArc(shape, this.coordinates, this.size, this.startAngle, this.arcAngle, this.shader, KColor.TRANSPARENT, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeWithoutFillColorSeparatedWithShader() {

        KArc shape = new KArc(this.coordinates.x(), this.coordinates.y(), this.size.width(), this.size.height(), this.startAngle, this.arcAngle, this.shader, this.outlineColor);
        this.assertArc(shape, this.coordinates, this.size, this.startAngle, this.arcAngle, this.shader, this.outlineColor, KColor.TRANSPARENT);

    }

    @Test
    @SuppressWarnings({ "ConstantValue", "SimplifiableAssertion" })
    public void testEqualsAndHashCode() {

        KArc primal = new KArc(10, 10, 11, 11, 10, 10, KColor.PINK, KColor.ORANGE);

        KArc diff1 = new KArc(10, 11, 11, 11, 10, 10, KColor.PINK, KColor.ORANGE);
        KArc diff2 = new KArc(10, 10, 11, 10, 10, 10, KColor.PINK, KColor.ORANGE);
        KArc diff3 = new KArc(10, 10, 11, 11, 11, 10, KColor.PINK, KColor.ORANGE);
        KArc diff4 = new KArc(10, 10, 11, 11, 10, 11, KColor.PINK, KColor.ORANGE);
        KArc diff5 = new KArc(10, 10, 11, 11, 10, 10, KColor.RED, KColor.ORANGE);
        KArc diff6 = new KArc(10, 10, 11, 11, 10, 10, KColor.PINK, KColor.GRAY);

        KArc equal = new KArc(10, 10, 11, 11, 10, 10, KColor.PINK, KColor.ORANGE);

        Assertions.assertFalse(primal.equals(null));
        Assertions.assertFalse(primal.equals(1));

        Assertions.assertFalse(primal.equals(diff1));
        Assertions.assertFalse(primal.equals(diff2));
        Assertions.assertFalse(primal.equals(diff3));
        Assertions.assertFalse(primal.equals(diff4));
        Assertions.assertFalse(primal.equals(diff5));
        Assertions.assertFalse(primal.equals(diff6));

        Assertions.assertTrue(primal.equals(equal));

        Assertions.assertEquals(primal.hashCode(), equal.hashCode());

    }
}
