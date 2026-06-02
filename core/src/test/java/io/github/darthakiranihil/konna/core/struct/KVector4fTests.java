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

package io.github.darthakiranihil.konna.core.struct;

import io.github.darthakiranihil.konna.core.struct.math.KVector4f;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KVector4fTests extends KStandardTestClass {

    @Test
    public void testCreateNonStatic() {

        KVector4f a = KVector4f.create(1.0f, 2.0f, 3.0f, 4.0f);
        KVector4f b = KVector4f.create(1.0f, 2.0f, 3.0f, 4.0f);

        Assertions.assertSame(a, b);
        Assertions.assertEquals(a, b);

        Assertions.assertEquals(a.x(), b.x());
        Assertions.assertEquals(a.y(), b.y());
        Assertions.assertEquals(a.z(), b.z());
        Assertions.assertEquals(a.w(), b.w());

        Assertions.assertEquals(a.hashCode(), b.hashCode());
        Assertions.assertEquals(a.toString(), b.toString());

    }

    @Test
    public void testCreateStatic() {
        KVector4f zero = KVector4f.create(0.0f, 0.0f, 0.0f, 0.0f);
        KVector4f one = KVector4f.create(1.0f, 1.0f, 1.0f, 1.0f);

        Assertions.assertSame(KVector4f.ZERO, zero);
        Assertions.assertSame(KVector4f.ONE, one);
        Assertions.assertEquals(KVector4f.ZERO, zero);
        Assertions.assertEquals(KVector4f.ONE, one);

        Assertions.assertEquals(KVector4f.ZERO.x(), zero.x());
        Assertions.assertEquals(KVector4f.ZERO.y(), zero.y());
        Assertions.assertEquals(KVector4f.ZERO.z(), zero.z());
        Assertions.assertEquals(KVector4f.ZERO.w(), zero.w());

        Assertions.assertEquals(KVector4f.ONE.x(), one.x());
        Assertions.assertEquals(KVector4f.ONE.y(), one.y());
        Assertions.assertEquals(KVector4f.ONE.z(), one.z());
        Assertions.assertEquals(KVector4f.ONE.w(), one.w());

        Assertions.assertEquals(KVector4f.ZERO.hashCode(), zero.hashCode());
        Assertions.assertEquals(KVector4f.ZERO.hashCode(), zero.hashCode());
        Assertions.assertEquals(KVector4f.ONE.toString(), one.toString());
        Assertions.assertEquals(KVector4f.ONE.toString(), one.toString());
    }

    @SuppressWarnings("MisorderedAssertEqualsArguments")
    @Test
    public void testEquals() {

        KVector4f a = KVector4f.create(1.0f, 2.0f, 3.0f, 4.0f);

        KVector4f dx = KVector4f.create(2.0f, 2.0f, 3.0f, 4.0f);
        KVector4f dy = KVector4f.create(1.0f, 3.0f, 3.0f, 4.0f);
        KVector4f dz = KVector4f.create(1.0f, 2.0f, 4.0f, 4.0f);
        KVector4f dw = KVector4f.create(1.0f, 2.0f, 3.0f, 5.0f);
        KVector4f same = KVector4f.create(1.0f, 2.0f, 3.0f, 4.0f);

        Assertions.assertNotEquals(a, null);
        Assertions.assertNotEquals(a, 1);
        Assertions.assertNotEquals(a, dx);
        Assertions.assertNotEquals(a, dy);
        Assertions.assertNotEquals(a, dz);
        Assertions.assertNotEquals(a, dw);
        Assertions.assertEquals(a, same);

    }
}
