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

package io.github.darthakiranihil.konna.core.struct.math;

import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KVector2iTests extends KStandardTestClass {

    @Test
    public void testCreateNonStatic() {

        KVector2i a = KVector2i.create(1, 2);
        KVector2i b = KVector2i.create(1, 2);

        Assertions.assertSame(a, b);
        Assertions.assertEquals(a, b);

        Assertions.assertEquals(a.x(), b.x());
        Assertions.assertEquals(a.y(), b.y());

        Assertions.assertEquals(a.hashCode(), b.hashCode());
        Assertions.assertEquals(a.toString(), b.toString());

    }

    @Test
    public void testCreateStatic() {
        KVector2i zero = KVector2i.create(0, 0);
        KVector2i one = KVector2i.create(1, 1);

        Assertions.assertSame(KVector2i.ZERO, zero);
        Assertions.assertSame(KVector2i.ONE, one);
        Assertions.assertEquals(KVector2i.ZERO, zero);
        Assertions.assertEquals(KVector2i.ONE, one);

        Assertions.assertEquals(KVector2i.ZERO.x(), zero.x());
        Assertions.assertEquals(KVector2i.ZERO.y(), zero.y());

        Assertions.assertEquals(KVector2i.ONE.x(), one.x());
        Assertions.assertEquals(KVector2i.ONE.y(), one.y());

        Assertions.assertEquals(KVector2i.ZERO.hashCode(), zero.hashCode());
        Assertions.assertEquals(KVector2i.ZERO.hashCode(), zero.hashCode());
        Assertions.assertEquals(KVector2i.ONE.toString(), one.toString());
        Assertions.assertEquals(KVector2i.ONE.toString(), one.toString());
    }

    @SuppressWarnings("MisorderedAssertEqualsArguments")
    @Test
    public void testEquals() {

        KVector2i a = KVector2i.create(1, 2);

        KVector2i dx = KVector2i.create(2, 2);
        KVector2i dy = KVector2i.create(1, 3);
        KVector2i same = KVector2i.create(1, 2);

        Assertions.assertNotEquals(a, null);
        Assertions.assertNotEquals(a, 1);
        Assertions.assertNotEquals(a, dx);
        Assertions.assertNotEquals(a, dy);
        Assertions.assertEquals(a, same);

    }

    @Test
    public void testMaths() {

        KVector2i a = KVector2i.create(1, 2);
        KVector2i b = KVector2i.create(3, 4);

        KVector2i sum = a.add(b);
        KVector2i diff = a.subtract(b);
        KVector2i neg = a.negate();

        KVector2i sumCheck = KVector2i.create(4, 6);
        KVector2i diffCheck = KVector2i.create(-2, -2);
        KVector2i negCheck = KVector2i.create(-1, -2);

        Assertions.assertEquals(sumCheck, sum);
        Assertions.assertEquals(diffCheck, diff);
        Assertions.assertEquals(negCheck, neg);

        Assertions.assertSame(sumCheck, sum);
        Assertions.assertSame(diffCheck, diff);
        Assertions.assertSame(negCheck, neg);

    }
}
