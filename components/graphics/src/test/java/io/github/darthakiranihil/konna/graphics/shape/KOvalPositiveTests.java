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

public class KOvalPositiveTests extends KShapeTestClass {

    private final KVector2i center = new KVector2i(10, 10);
    private final KSize size = new KSize(10, 10);
    private final KColor outlineColor = KColor.RED;
    private final KColor fillColor = KColor.BLUE;

    @Test
    public void testCreateShapeStandard() {

        KOval shape = new KOval(this.center, this.size, this.outlineColor, this.fillColor);
        this.assertOval(shape, this.center, this.size, this.outlineColor, this.fillColor);

    }

    @Test
    public void testCreateShapeWithoutColors() {

        KOval shape = new KOval(this.center, this.size);
        this.assertOval(shape, this.center, this.size, KColor.TRANSPARENT, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeWithoutFillColor() {

        KOval shape = new KOval(center, this.size, this.outlineColor);
        this.assertOval(shape, this.center, this.size, this.outlineColor, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeStandardSeparated() {

        KOval shape = new KOval(this.center.x(), this.center.y(), this.size.width(), this.size.height(), this.outlineColor, this.fillColor);
        this.assertOval(shape, this.center, this.size, this.outlineColor, this.fillColor);

    }

    @Test
    public void testCreateShapeWithoutColorsSeparated() {

        KOval shape = new KOval(this.center.x(), this.center.y(), this.size.width(), this.size.height());
        this.assertOval(shape, this.center, this.size, KColor.TRANSPARENT, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeWithoutFillColorSeparated() {

        KOval shape = new KOval(this.center.x(), this.center.y(), this.size.width(), this.size.height(), this.outlineColor);
        this.assertOval(shape, this.center, this.size, this.outlineColor, KColor.TRANSPARENT);

    }

    @Test
    @SuppressWarnings({ "ConstantValue", "SimplifiableAssertion" })
    public void testEqualsAndHashCode() {

        KOval primal = new KOval(10, 10, 10, 10, KColor.PINK, KColor.BLUE);

        KOval diff1 = new KOval(10, 11, 10, 10, KColor.PINK, KColor.BLUE);
        KOval diff2 = new KOval(10, 10, 10, 11, KColor.PINK, KColor.BLUE);
        KOval diff3 = new KOval(10, 10, 10, 10, KColor.RED, KColor.BLUE);
        KOval diff4 = new KOval(10, 10, 10, 10, KColor.PINK, KColor.GREEN);

        KOval equal = new KOval(10, 10, 10, 10, KColor.PINK, KColor.BLUE);

        Assertions.assertFalse(primal.equals(null));
        Assertions.assertFalse(primal.equals(1));

        Assertions.assertFalse(primal.equals(diff1));
        Assertions.assertFalse(primal.equals(diff2));
        Assertions.assertFalse(primal.equals(diff3));
        Assertions.assertFalse(primal.equals(diff4));

        Assertions.assertTrue(primal.equals(equal));

        Assertions.assertEquals(primal.hashCode(), equal.hashCode());

    }

}
