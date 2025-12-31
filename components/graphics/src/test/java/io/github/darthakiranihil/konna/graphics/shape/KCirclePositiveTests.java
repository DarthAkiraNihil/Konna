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

public class KCirclePositiveTests extends KShapeTestClass {

    private final KVector2i coordinates = new KVector2i(10, 10);
    private final int radius = 1;
    private final KColor outlineColor = KColor.DARK_GRAY;
    private final KColor fillColor = KColor.LIGHT_GRAY;

    @Test
    public void testCreateShapeStandard() {

        KCircle shape = new KCircle(this.coordinates, this.radius, this.outlineColor, this.fillColor);
        this.assertCircle(shape, this.coordinates, this.radius, this.outlineColor, this.fillColor);

    }

    @Test
    public void testCreateShapeWithoutColors() {

        KCircle shape = new KCircle(this.coordinates, this.radius);
        this.assertCircle(shape, this.coordinates, this.radius, KColor.TRANSPARENT, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeWithoutFillColor() {

        KCircle shape = new KCircle(this.coordinates, this.radius, this.outlineColor);
        this.assertCircle(shape, this.coordinates, this.radius, this.outlineColor, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeStandardSeparated() {

        KCircle shape = new KCircle(this.coordinates.x(), this.coordinates.y(), this.radius, this.outlineColor, this.fillColor);
        this.assertCircle(shape, this.coordinates, this.radius, this.outlineColor, this.fillColor);

    }

    @Test
    public void testCreateShapeWithoutColorsSeparated() {

        KCircle shape = new KCircle(this.coordinates.x(), this.coordinates.y(), this.radius);
        this.assertCircle(shape, this.coordinates, this.radius, KColor.TRANSPARENT, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeWithoutFillColorSeparated() {

        KCircle shape = new KCircle(this.coordinates.x(), this.coordinates.y(), this.radius, this.outlineColor);
        this.assertCircle(shape, this.coordinates, this.radius, this.outlineColor, KColor.TRANSPARENT);

    }

}
