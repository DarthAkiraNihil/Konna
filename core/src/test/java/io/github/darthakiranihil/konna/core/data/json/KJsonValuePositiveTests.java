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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Assertions.assertNotEquals(null, a);
        Assertions.assertNotEquals(2, a);
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

    @Test
    public void testSetProperty() {
        KJsonValue val = KJsonValue.fromMap(new HashMap<>());
        try {
            val.setProperty("lol", KJsonValue.fromNumber(0));
            Assertions.assertTrue(val.hasProperty("lol"));
            Assertions.assertEquals(0, val.getProperty("lol").getInt());
        } catch (Throwable e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void testJsonValueTypeFromObject() {

        boolean boolPrimitive = false;
        Boolean boolBoxed = true;

        int intPrimitive = 0;
        Integer intBoxed = 1;

        byte bytePrimitive = 0;
        Byte byteBoxed = 1;

        short shortPrimitive = 0;
        Short shortBoxed = 1;

        long longPrimitive = 0;
        Long longBoxed = 1L;

        float floatPrimitive = 0.0f;
        Float floatBoxed = 1.0f;

        double doublePrimitive = 0.0;
        Double doubleBoxed =  1.0;

        char charPrimitive = '0';
        Character charBoxed = '0';

        String string = "1234";

        int[] array = new int[] {1, 2, 3};

        List<Integer> list = List.of(1, 2, 3);

        Map<String, String> map = new HashMap<>();

        Assertions.assertEquals(KJsonValueType.BOOLEAN, KJsonValueType.fromObject(boolPrimitive));
        Assertions.assertEquals(KJsonValueType.BOOLEAN, KJsonValueType.fromObject(boolBoxed));

        Assertions.assertEquals(KJsonValueType.NUMBER_INT, KJsonValueType.fromObject(bytePrimitive));
        Assertions.assertEquals(KJsonValueType.NUMBER_INT, KJsonValueType.fromObject(byteBoxed));
        Assertions.assertEquals(KJsonValueType.NUMBER_INT, KJsonValueType.fromObject(shortPrimitive));
        Assertions.assertEquals(KJsonValueType.NUMBER_INT, KJsonValueType.fromObject(shortBoxed));
        Assertions.assertEquals(KJsonValueType.NUMBER_INT, KJsonValueType.fromObject(intPrimitive));
        Assertions.assertEquals(KJsonValueType.NUMBER_INT, KJsonValueType.fromObject(intBoxed));
        Assertions.assertEquals(KJsonValueType.NUMBER_INT, KJsonValueType.fromObject(longPrimitive));
        Assertions.assertEquals(KJsonValueType.NUMBER_INT, KJsonValueType.fromObject(longBoxed));

        Assertions.assertEquals(KJsonValueType.NUMBER_FLOAT, KJsonValueType.fromObject(floatPrimitive));
        Assertions.assertEquals(KJsonValueType.NUMBER_FLOAT, KJsonValueType.fromObject(floatBoxed));
        Assertions.assertEquals(KJsonValueType.NUMBER_FLOAT, KJsonValueType.fromObject(doublePrimitive));
        Assertions.assertEquals(KJsonValueType.NUMBER_FLOAT, KJsonValueType.fromObject(doubleBoxed));

        Assertions.assertEquals(KJsonValueType.STRING, KJsonValueType.fromObject(charPrimitive));
        Assertions.assertEquals(KJsonValueType.STRING, KJsonValueType.fromObject(charBoxed));
        Assertions.assertEquals(KJsonValueType.STRING, KJsonValueType.fromObject(string));

        Assertions.assertEquals(KJsonValueType.ARRAY, KJsonValueType.fromObject(array));
        Assertions.assertEquals(KJsonValueType.ARRAY, KJsonValueType.fromObject(list));
        Assertions.assertEquals(KJsonValueType.OBJECT, KJsonValueType.fromObject(map));
        Assertions.assertEquals(KJsonValueType.NULL, KJsonValueType.fromObject(null));


    }
}
