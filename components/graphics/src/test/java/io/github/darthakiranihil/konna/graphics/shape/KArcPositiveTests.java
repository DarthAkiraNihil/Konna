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
        this.assertArc(shape, this.coordinates, this.size, this.startAngle, this.arcAngle, this.outlineColor, this.fillColor);

    }

    @Test
    public void testCreateShapeWithoutColors() {

        KArc shape = new KArc(this.coordinates, this.size, this.startAngle, this.arcAngle);
        this.assertArc(shape, this.coordinates, this.size, this.startAngle, this.arcAngle, KColor.TRANSPARENT, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeWithoutFillColor() {

        KArc shape = new KArc(this.coordinates, this.size, this.startAngle, this.arcAngle, this.outlineColor);
        this.assertArc(shape, this.coordinates, this.size, this.startAngle, this.arcAngle, this.outlineColor, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeStandardSeparated() {

        KArc shape = new KArc(this.coordinates.x(), this.coordinates.y(), this.size.width(), this.size.height(), this.startAngle, this.arcAngle, this.outlineColor, this.fillColor);
        this.assertArc(shape, this.coordinates, this.size, this.startAngle, this.arcAngle, this.outlineColor, this.fillColor);

    }

    @Test
    public void testCreateShapeWithoutColorsSeparated() {

        KArc shape = new KArc(this.coordinates.x(), this.coordinates.y(), this.size.width(), this.size.height(), this.startAngle, this.arcAngle);
        this.assertArc(shape, this.coordinates, this.size, this.startAngle, this.arcAngle, KColor.TRANSPARENT, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeWithoutFillColorSeparated() {

        KArc shape = new KArc(this.coordinates.x(), this.coordinates.y(), this.size.width(), this.size.height(), this.startAngle, this.arcAngle, this.outlineColor);
        this.assertArc(shape, this.coordinates, this.size, this.startAngle, this.arcAngle, this.outlineColor, KColor.TRANSPARENT);

    }
}
