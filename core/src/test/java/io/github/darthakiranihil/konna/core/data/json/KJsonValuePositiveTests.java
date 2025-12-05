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

package io.github.darthakiranihil.konna.core.data.json;

import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class KJsonValuePositiveTests extends KStandardTestClass {

    @Test
    public void testIsNullAndGetNullString() {

        KJsonValue realNull = new KJsonValue(KJsonValueType.STRING, null);
        KJsonValue nullByType = new KJsonValue(KJsonValueType.NULL, 1);

        try {
            Field realNullType = KJsonValue.class.getDeclaredField("type");
            realNullType.setAccessible(true);
            realNullType.set(realNull, KJsonValueType.STRING);
        } catch (Throwable e) {
            Assertions.fail(e);
        }

        Assertions.assertTrue(realNull.isNull());
        Assertions.assertTrue(nullByType.isNull());
        Assertions.assertNull(realNull.getString());
    }

    @Test
    public void testGetSpliterator() {
        List<KJsonValue> list = new ArrayList<>();
        list.add(KJsonValue.fromNumber(1));

        Assertions.assertEquals(
            list.spliterator().getClass(),
            KJsonValue.fromList(list).spliterator().getClass()
        );
    }

    @Test
    public void testApplyForEach() {
        List<KJsonValue> list = new ArrayList<>();

        list.add(KJsonValue.fromNumber(1));
        list.add(KJsonValue.fromNumber(2));
        list.add(KJsonValue.fromNumber(3));

        AtomicInteger counter = new AtomicInteger();

        KJsonValue value = KJsonValue.fromList(list);
        value.forEach((v) -> counter.addAndGet(v.getInt()));

        Assertions.assertEquals(6, counter.get());
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void testEqualsAndHashCode() {

        KJsonValue a = KJsonValue.fromNumber(0);
        KJsonValue b = KJsonValue.fromNumber(1);
        KJsonValue c = KJsonValue.fromNumber(1);
        KJsonValue d = KJsonValue.fromNumber(1.0f);

        Assertions.assertNotEquals(a, b);
        Assertions.assertNotEquals(a, null);
        Assertions.assertNotEquals(a, 2);
        Assertions.assertNotEquals(c, d);
        Assertions.assertEquals(b, c);
        Assertions.assertEquals(b, b);

        Assertions.assertNotEquals(a.hashCode(), b.hashCode());
        Assertions.assertEquals(b.hashCode(), c.hashCode());

    }

    @Test
    public void testToString() {

        Assertions.assertEquals(
            "KJsonValue[NUMBER_INT]{class java.lang.Integer}",
            KJsonValue.fromNumber(0).toString()
        );

    }

}
