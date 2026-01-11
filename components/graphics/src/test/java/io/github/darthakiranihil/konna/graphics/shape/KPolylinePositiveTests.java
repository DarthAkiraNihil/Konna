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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KPolylinePositiveTests extends KShapeTestClass {

    private final KVector2i[] points = new KVector2i[] {
        new KVector2i(10, 10),
        new KVector2i(10, 16),
        new KVector2i(16, 16),
    };
    private final int[] xs = new int[] {10, 10, 16};
    private final int[] ys = new int[] {10, 16, 16};

    private final KColor color = KColor.MAGENTA;

    @Test
    public void testCreateShapeStandard() {

        KPolyline shape = new KPolyline(this.points, this.color);
        this.assertPolyline(shape, this.points, null, this.color);

    }

    @Test
    public void testCreateShapeWithoutColors() {

        KPolyline shape = new KPolyline(this.points);
        this.assertPolyline(shape, this.points, null, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeStandardSeparated() {

        KPolyline shape = new KPolyline(this.xs, this.ys, this.color);
        this.assertPolyline(shape, this.points, null, this.color);

    }

    @Test
    public void testCreateShapeWithoutColorsSeparated() {

        KPolyline shape = new KPolyline(this.xs, this.ys);
        this.assertPolyline(shape, this.points, null, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeStandardWithShader() {

        KPolyline shape = new KPolyline(this.points, this.shader, this.color);
        this.assertPolyline(shape, this.points, this.shader, this.color);

    }

    @Test
    public void testCreateShapeWithoutColorsWithShader() {

        KPolyline shape = new KPolyline(this.points, this.shader);
        this.assertPolyline(shape, this.points, this.shader, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeStandardSeparatedWithShader() {

        KPolyline shape = new KPolyline(this.xs, this.ys, this.shader, this.color);
        this.assertPolyline(shape, this.points, this.shader, this.color);

    }

    @Test
    public void testCreateShapeWithoutColorsSeparatedWithShader() {

        KPolyline shape = new KPolyline(this.xs, this.ys, this.shader);
        this.assertPolyline(shape, this.points, this.shader, KColor.TRANSPARENT);

    }

    @Test
    @SuppressWarnings({ "ConstantValue", "SimplifiableAssertion" })
    public void testEqualsAndHashCode() {

        KPolyline primal = new KPolyline(
            new KVector2i[] {
                new KVector2i(10, 10),
                new KVector2i(10, 16),
                new KVector2i(16, 16),
            },
            KColor.PINK
        );

        KPolyline diff1 = new KPolyline(
            new KVector2i[] {
                new KVector2i(10, 11),
                new KVector2i(10, 16),
                new KVector2i(16, 16),
            },
            KColor.PINK
        );
        KPolyline diff2 = new KPolyline(
            new KVector2i[] {
                new KVector2i(10, 10),
                new KVector2i(10, 16),
                new KVector2i(16, 16),
            },
            KColor.GREEN
        );

        KPolyline equal = new KPolyline(
            new KVector2i[] {
                new KVector2i(10, 10),
                new KVector2i(10, 16),
                new KVector2i(16, 16),
            },
            KColor.PINK
        );

        Assertions.assertFalse(primal.equals(null));
        Assertions.assertFalse(primal.equals(1));

        Assertions.assertFalse(primal.equals(diff1));
        Assertions.assertFalse(primal.equals(diff2));

        Assertions.assertTrue(primal.equals(equal));

        Assertions.assertEquals(primal.hashCode(), equal.hashCode());

    }


}
