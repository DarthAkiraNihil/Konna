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
        this.assertRectangle(shape, this.corner, this.size, null, this.outlineColor, this.fillColor);

    }

    @Test
    public void testCreateShapeWithoutColors() {

        KRectangle shape = new KRectangle(this.corner, this.size);
        this.assertRectangle(shape, this.corner, this.size, null, KColor.TRANSPARENT, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeWithoutFillColor() {

        KRectangle shape = new KRectangle(this.corner, this.size, this.outlineColor);
        this.assertRectangle(shape, this.corner, this.size, null, outlineColor, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeStandardSeparated() {

        KRectangle shape = new KRectangle(this.corner.x(), this.corner.y(), this.size.width(), this.size.height(), this.outlineColor, this.fillColor);
        this.assertRectangle(shape, this.corner, this.size, null, this.outlineColor, this.fillColor);

    }

    @Test
    public void testCreateShapeWithoutColorsSeparated() {

        KRectangle shape = new KRectangle(this.corner.x(), this.corner.y(), this.size.width(), this.size.height());
        this.assertRectangle(shape, this.corner, this.size, null, KColor.TRANSPARENT, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeWithoutFillColorSeparated() {

        KRectangle shape = new KRectangle(this.corner.x(), this.corner.y(), this.size.width(), this.size.height(), this.outlineColor);
        this.assertRectangle(shape, this.corner, this.size, null, this.outlineColor, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeStandardWithShader() {

        KRectangle shape = new KRectangle(this.corner, this.size, this.shader, this.outlineColor, this.fillColor);
        this.assertRectangle(shape, this.corner, this.size, this.shader, this.outlineColor, this.fillColor);

    }

    @Test
    public void testCreateShapeWithoutColorsWithShader() {

        KRectangle shape = new KRectangle(this.corner, this.size, this.shader);
        this.assertRectangle(shape, this.corner, this.size,this.shader,  KColor.TRANSPARENT, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeWithoutFillColorWithShader() {

        KRectangle shape = new KRectangle(this.corner, this.size, this.shader, this.outlineColor);
        this.assertRectangle(shape, this.corner, this.size, this.shader, outlineColor, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeStandardSeparatedWithShader() {

        KRectangle shape = new KRectangle(this.corner.x(), this.corner.y(), this.size.width(), this.size.height(), this.shader, this.outlineColor, this.fillColor);
        this.assertRectangle(shape, this.corner, this.size, this.shader, this.outlineColor, this.fillColor);

    }

    @Test
    public void testCreateShapeWithoutColorsSeparatedWithShader() {

        KRectangle shape = new KRectangle(this.corner.x(), this.corner.y(), this.size.width(), this.size.height(), this.shader);
        this.assertRectangle(shape, this.corner, this.size, this.shader, KColor.TRANSPARENT, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeWithoutFillColorSeparatedWithShader() {

        KRectangle shape = new KRectangle(this.corner.x(), this.corner.y(), this.size.width(), this.size.height(), this.shader, this.outlineColor);
        this.assertRectangle(shape, this.corner, this.size, this.shader, this.outlineColor, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateSquareStandard() {

        KRectangle shape = KRectangle.square(this.corner, this.side, this.outlineColor, this.fillColor2);
        this.assertSquare(shape, this.corner, this.side, null, this.outlineColor, this.fillColor2);

    }

    @Test
    public void testCreateSquareWithoutColors() {

        KRectangle shape = KRectangle.square(this.corner, this.side);
        this.assertSquare(shape, this.corner, this.side, null, KColor.TRANSPARENT, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateSquareWithoutFillColor() {

        KRectangle shape = KRectangle.square(this.corner, this.side, this.outlineColor);
        this.assertSquare(shape, this.corner, this.side, null, outlineColor, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateSquareStandardSeparated() {

        KRectangle shape = KRectangle.square(this.corner.x(), this.corner.y(), this.side, this.outlineColor, this.fillColor2);
        this.assertSquare(shape, this.corner, this.side, null, this.outlineColor, this.fillColor2);

    }

    @Test
    public void testCreateSquareWithoutColorsSeparated() {

        KRectangle shape = KRectangle.square(this.corner.x(), this.corner.y(), this.side);
        this.assertSquare(shape, this.corner, this.side, null, KColor.TRANSPARENT, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateSquareWithoutFillColorSeparated() {

        KRectangle shape = KRectangle.square(this.corner.x(), this.corner.y(), this.side, this.outlineColor);
        this.assertSquare(shape, this.corner, this.side, null, this.outlineColor, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateSquareStandardWithShader() {

        KRectangle shape = KRectangle.square(this.corner, this.side, this.shader, this.outlineColor, this.fillColor2);
        this.assertSquare(shape, this.corner, this.side, this.shader, this.outlineColor, this.fillColor2);

    }

    @Test
    public void testCreateSquareWithoutColorsWithShader() {

        KRectangle shape = KRectangle.square(this.corner, this.side, this.shader);
        this.assertSquare(shape, this.corner, this.side, this.shader, KColor.TRANSPARENT, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateSquareWithoutFillColorWithShader() {

        KRectangle shape = KRectangle.square(this.corner, this.side, this.shader, this.outlineColor);
        this.assertSquare(shape, this.corner, this.side, this.shader, outlineColor, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateSquareStandardSeparatedWithShader() {

        KRectangle shape = KRectangle.square(this.corner.x(), this.corner.y(), this.side, this.shader, this.outlineColor, this.fillColor2);
        this.assertSquare(shape, this.corner, this.side, this.shader, this.outlineColor, this.fillColor2);

    }

    @Test
    public void testCreateSquareWithoutColorsSeparatedWithShader() {

        KRectangle shape = KRectangle.square(this.corner.x(), this.corner.y(), this.side, this.shader);
        this.assertSquare(shape, this.corner, this.side, this.shader, KColor.TRANSPARENT, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateSquareWithoutFillColorSeparatedWithShader() {

        KRectangle shape = KRectangle.square(this.corner.x(), this.corner.y(), this.side, this.shader, this.outlineColor);
        this.assertSquare(shape, this.corner, this.side, this.shader, this.outlineColor, KColor.TRANSPARENT);

    }

}
