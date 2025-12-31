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

public class KRectanglePositiveTests extends KShapeTestClass {

    private final KVector2i corner = new KVector2i(10, 10);
    private final KSize size = new KSize(10, 16);

    private final int side = 20;

    private final KColor outlineColor = KColor.PINK;
    private final KColor fillColor = KColor.ORANGE;
    private final KColor fillColor2 = KColor.CYAN;


    @Test
    public void testCreateShapeStandard() {

        KRectangle shape = new KRectangle(this.corner, this.size, this.outlineColor, this.fillColor);
        this.assertRectangle(shape, this.corner, this.size, this.outlineColor, this.fillColor);

    }

    @Test
    public void testCreateShapeWithoutColors() {

        KRectangle shape = new KRectangle(this.corner, this.size);
        this.assertRectangle(shape, this.corner, this.size, KColor.TRANSPARENT, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeWithoutFillColor() {

        KRectangle shape = new KRectangle(this.corner, this.size, this.outlineColor);
        this.assertRectangle(shape, this.corner, this.size, outlineColor, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeStandardSeparated() {

        KRectangle shape = new KRectangle(this.corner.x(), this.corner.y(), this.size.width(), this.size.height(), this.outlineColor, this.fillColor);
        this.assertRectangle(shape, this.corner, this.size, this.outlineColor, this.fillColor);

    }

    @Test
    public void testCreateShapeWithoutColorsSeparated() {

        KRectangle shape = new KRectangle(this.corner.x(), this.corner.y(), this.size.width(), this.size.height());
        this.assertRectangle(shape, this.corner, this.size, KColor.TRANSPARENT, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeWithoutFillColorSeparated() {

        KRectangle shape = new KRectangle(this.corner.x(), this.corner.y(), this.size.width(), this.size.height(), this.outlineColor);
        this.assertRectangle(shape, this.corner, this.size, this.outlineColor, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateSquareStandard() {

        KRectangle shape = KRectangle.square(this.corner, this.side, this.outlineColor, this.fillColor2);
        this.assertSquare(shape, this.corner, this.side, this.outlineColor, this.fillColor2);

    }

    @Test
    public void testCreateSquareWithoutColors() {

        KRectangle shape = KRectangle.square(this.corner, this.side);
        this.assertSquare(shape, this.corner, this.side, KColor.TRANSPARENT, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateSquareWithoutFillColor() {

        KRectangle shape = KRectangle.square(this.corner, this.side, this.outlineColor);
        this.assertSquare(shape, this.corner, this.side, outlineColor, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateSquareStandardSeparated() {

        KRectangle shape = KRectangle.square(this.corner.x(), this.corner.y(), this.side, this.outlineColor, this.fillColor2);
        this.assertSquare(shape, this.corner, this.side, this.outlineColor, this.fillColor2);

    }

    @Test
    public void testCreateSquareWithoutColorsSeparated() {

        KRectangle shape = KRectangle.square(this.corner.x(), this.corner.y(), this.side);
        this.assertSquare(shape, this.corner, this.side, KColor.TRANSPARENT, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateSquareWithoutFillColorSeparated() {

        KRectangle shape = KRectangle.square(this.corner.x(), this.corner.y(), this.side, this.outlineColor);
        this.assertSquare(shape, this.corner, this.side, this.outlineColor, KColor.TRANSPARENT);

    }

}
