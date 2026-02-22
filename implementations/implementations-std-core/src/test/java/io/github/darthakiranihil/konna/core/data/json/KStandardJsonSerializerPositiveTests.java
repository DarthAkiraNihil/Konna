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

import io.github.darthakiranihil.konna.core.data.json.except.KJsonSerializationException;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class KStandardJsonSerializerPositiveTests extends KStandardTestClass {

    private final KJsonSerializer serializer;

    public KStandardJsonSerializerPositiveTests() {
        this.serializer = this.jsonSerializer;
    }

    @Test
    public void testSerializeInteger() {

        int testVariable = 1;
        Integer boxedTestVariable = 2;

        try {
            KJsonValue testValue = this.serializer.serialize(testVariable, int.class);
            KJsonValue testBoxedValue = this.serializer.serialize(boxedTestVariable, Integer.class);

            Assertions.assertEquals(KJsonValueType.NUMBER_INT, testValue.getType());
            Assertions.assertEquals(KJsonValueType.NUMBER_INT, testBoxedValue.getType());

            Assertions.assertEquals(testVariable, testValue.getInt());
            Assertions.assertEquals(boxedTestVariable, testBoxedValue.getInt());

        } catch (KJsonSerializationException e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testSerializeByte() {

        byte testVariable = 1;
        Byte boxedTestVariable = 2;

        try {
            KJsonValue testValue = this.serializer.serialize(testVariable, byte.class);
            KJsonValue testBoxedValue = this.serializer.serialize(boxedTestVariable, Byte.class);

            Assertions.assertEquals(KJsonValueType.NUMBER_INT, testValue.getType());
            Assertions.assertEquals(KJsonValueType.NUMBER_INT, testBoxedValue.getType());

            Assertions.assertEquals(testVariable, testValue.getByte());
            Assertions.assertEquals(boxedTestVariable, testBoxedValue.getByte());

        } catch (KJsonSerializationException e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testSerializeShort() {

        short testVariable = 1;
        Short boxedTestVariable = 2;

        try {
            KJsonValue testValue = this.serializer.serialize(testVariable, short.class);
            KJsonValue testBoxedValue = this.serializer.serialize(boxedTestVariable, Short.class);

            Assertions.assertEquals(KJsonValueType.NUMBER_INT, testValue.getType());
            Assertions.assertEquals(KJsonValueType.NUMBER_INT, testBoxedValue.getType());

            Assertions.assertEquals(testVariable, testValue.getShort());
            Assertions.assertEquals(boxedTestVariable, testBoxedValue.getShort());

        } catch (KJsonSerializationException e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testSerializeLong() {

        long testVariable = 1;
        Long boxedTestVariable = 2L;

        try {
            KJsonValue testValue = this.serializer.serialize(testVariable, long.class);
            KJsonValue testBoxedValue = this.serializer.serialize(boxedTestVariable, Long.class);

            Assertions.assertEquals(KJsonValueType.NUMBER_INT, testValue.getType());
            Assertions.assertEquals(KJsonValueType.NUMBER_INT, testBoxedValue.getType());

            Assertions.assertEquals(testVariable, testValue.getLong());
            Assertions.assertEquals(boxedTestVariable, testBoxedValue.getLong());

        } catch (KJsonSerializationException e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testSerializeFloat() {

        float testVariable = 1.0f;
        Float boxedTestVariable = 2.0f;

        try {
            KJsonValue testValue = this.serializer.serialize(testVariable, float.class);
            KJsonValue testBoxedValue = this.serializer.serialize(boxedTestVariable, Float.class);

            Assertions.assertEquals(KJsonValueType.NUMBER_FLOAT, testValue.getType());
            Assertions.assertEquals(KJsonValueType.NUMBER_FLOAT, testBoxedValue.getType());

            Assertions.assertEquals(testVariable, testValue.getFloat());
            Assertions.assertEquals(boxedTestVariable, testBoxedValue.getFloat());

        } catch (KJsonSerializationException e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testSerializeDouble() {

        double testVariable = 1.0;
        Double boxedTestVariable = 2.0;

        try {
            KJsonValue testValue = this.serializer.serialize(testVariable, double.class);
            KJsonValue testBoxedValue = this.serializer.serialize(boxedTestVariable, Double.class);

            Assertions.assertEquals(KJsonValueType.NUMBER_FLOAT, testValue.getType());
            Assertions.assertEquals(KJsonValueType.NUMBER_FLOAT, testBoxedValue.getType());

            Assertions.assertEquals(testVariable, testValue.getDouble());
            Assertions.assertEquals(boxedTestVariable, testBoxedValue.getDouble());

        } catch (KJsonSerializationException e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testSerializeBoolean() {

        boolean testVariable = true;
        Boolean boxedTestVariable = false;

        try {
            KJsonValue testValue = this.serializer.serialize(testVariable, boolean.class);
            KJsonValue testBoxedValue = this.serializer.serialize(boxedTestVariable, Boolean.class);

            Assertions.assertEquals(KJsonValueType.BOOLEAN, testValue.getType());
            Assertions.assertEquals(KJsonValueType.BOOLEAN, testBoxedValue.getType());

            Assertions.assertEquals(testVariable, testValue.getBoolean());
            Assertions.assertEquals(boxedTestVariable, testBoxedValue.getBoolean());

        } catch (KJsonSerializationException e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testSerializeChar() {

        char testVariable = 'a';
        Character boxedTestVariable = 'b';

        try {
            KJsonValue testValue = this.serializer.serialize(testVariable, char.class);
            KJsonValue testBoxedValue = this.serializer.serialize(boxedTestVariable, Character.class);

            Assertions.assertEquals(KJsonValueType.STRING, testValue.getType());
            Assertions.assertEquals(KJsonValueType.STRING, testBoxedValue.getType());

            Assertions.assertEquals(testVariable, testValue.getChar());
            Assertions.assertEquals(boxedTestVariable, testBoxedValue.getChar());

        } catch (KJsonSerializationException e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testSerializeString() {

        String testVariable = "123_test";

        try {
            KJsonValue testValue = this.serializer.serialize(testVariable, String.class);

            Assertions.assertEquals(KJsonValueType.STRING, testValue.getType());

            Assertions.assertEquals(testVariable, testValue.getString());

        } catch (KJsonSerializationException e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testSerializeList() {

        List<String> testList = List.of("aboba_123");

        try {
            KJsonValue testValue = this.serializer.serialize(testList, List.class);

            Assertions.assertEquals(KJsonValueType.ARRAY, testValue.getType());

            int i = 0;
            for (KJsonValue entry : testValue) {
                Assertions.assertEquals(KJsonValueType.STRING, entry.getType());
                Assertions.assertEquals(testList.get(i), entry.getString());
                i++;
            }

        } catch (KJsonSerializationException e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testSerializeArray() {

        String[] testArray = new String[] {"aboba_123"};

        try {
            KJsonValue testValue = this.serializer.serialize(testArray, String[].class);

            Assertions.assertEquals(KJsonValueType.ARRAY, testValue.getType());

            int i = 0;
            for (KJsonValue entry : testValue) {
                Assertions.assertEquals(KJsonValueType.STRING, entry.getType());
                Assertions.assertEquals(testArray[i], entry.getString());
                i++;
            }

        } catch (KJsonSerializationException e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void voidTestSerializeMap() {

        Map<String, Integer> testMap = Map.of(
            "aboba", 1, "giggity", 2
        );

        try {
            KJsonValue testValue = this.serializer.serialize(testMap, Map.class);

            Assertions.assertEquals(KJsonValueType.OBJECT, testValue.getType());

            for (var entry: testMap.entrySet()) {

                String key = entry.getKey();
                KJsonValue value = testValue.getProperty(key);

                Assertions.assertEquals(KJsonValueType.NUMBER_INT, value.getType());
                Assertions.assertEquals(testMap.get(key), value.getInt());

            }

        } catch (KJsonSerializationException e) {
            Assertions.fail(e);
        }

    }

    private static class SerializerTestClass {
        public int field1;
        @KJsonIgnored public float field2;
        @KJsonSerialized final private String field3;
        @KJsonCustomName(name = "field_4") public List<Float> field4;
        private final boolean field5;
        public int[] field6;

        public SerializerTestClass(int field1, float field2, String field3, List<Float> field4, boolean field5, int[] field6) {
            this.field1 = field1;
            this.field2 = field2;
            this.field3 = field3;
            this.field4 = field4;
            this.field5 = field5;
            this.field6 = field6;
        }

        public int getField1() {
            return this.field1;
        }

        public String getField3() {
            return this.field3;
        }

        public List<Float> getField4() {
            return this.field4;
        }

        public int[] getField6() {
            return field6;
        }
    }

    @Test
    public void testSerializeObject() {
        KJsonSerializer serializer = new KStandardJsonSerializer();

        List<Float> testList = List.of(1.0f, 2.0f);
        int[] testArray = new int[] {1, 2, 3};
        SerializerTestClass testObject = new SerializerTestClass(1, 2.0f, "TEST", testList, false, testArray);

        KJsonValue serialized;
        try {
            serialized = serializer.serialize(testObject, SerializerTestClass.class);
        } catch (KJsonSerializationException e) {
            Assertions.fail(e);
            return;
        }

        Assertions.assertEquals(KJsonValueType.OBJECT, serialized.getType());

        Assertions.assertTrue(serialized.hasProperty("field1"));
        Assertions.assertFalse(serialized.hasProperty("field2"));
        Assertions.assertTrue(serialized.hasProperty("field3"));

        Assertions.assertFalse(serialized.hasProperty("field4"));
        Assertions.assertTrue(serialized.hasProperty("field_4"));

        Assertions.assertFalse(serialized.hasProperty("field5"));
        Assertions.assertTrue(serialized.hasProperty("field6"));

        KJsonValue field1 = serialized.getProperty("field1");
        KJsonValue field3 = serialized.getProperty("field3");
        KJsonValue field4 = serialized.getProperty("field_4");
        KJsonValue field6 = serialized.getProperty("field6");

        Assertions.assertEquals(KJsonValueType.NUMBER_INT, field1.getType());
        Assertions.assertEquals(KJsonValueType.STRING, field3.getType());
        Assertions.assertEquals(KJsonValueType.ARRAY, field4.getType());
        Assertions.assertEquals(KJsonValueType.ARRAY, field6.getType());

        Assertions.assertEquals(testObject.getField1(), field1.getInt());
        Assertions.assertEquals(testObject.getField3(), field3.getString());

        int i = 0;
        List<Float> originalField4 = testObject.getField4();

        for (KJsonValue entry : field4) {
            Assertions.assertEquals(originalField4.get(i), entry.getFloat());
            i++;
        }

        int[] originalField6 = testObject.getField6();

        i = 0;
        for (KJsonValue entry : field6) {
            Assertions.assertEquals(originalField6[i], entry.getInt());
            i++;
        }
    }
}
