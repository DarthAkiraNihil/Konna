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

import io.github.darthakiranihil.konna.core.data.json.except.KJsonParseException;
import io.github.darthakiranihil.konna.core.data.json.except.KJsonSerializationException;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KStandardJsonDeserializerPositiveTests extends KStandardTestClass {

    public static class SerializerTestClass {
        public int field1;
        @KJsonIgnored public float field2;
        @KJsonSerialized private final String field3;
        @KJsonCustomName(name = "field_4") @KJsonArray(elementType = Float.class) public List<Float> field4;
        private final boolean field5;
        @KJsonSerialized @KJsonArray(elementType = int.class) private int[] field6;
        @KJsonMap(
            valueType = Object.class,
            mapType = HashMap.class
        ) public Map<String, Object> field7;

        public SerializerTestClass(int field1, float field2, String field3, List<Float> field4, boolean field5) {
            this.field1 = field1;
            this.field2 = field2;
            this.field3 = field3;
            this.field4 = field4;
            this.field5 = field5;
        }

        public SerializerTestClass(
            @KJsonConstructorParameter(name = "field3") String field3,
            @KJsonConstructorParameter(name = "field5") boolean field5
        ) {
            this.field3 = field3;
            this.field5 = field5;
        }

        public int getField1() {
            return this.field1;
        }

        public float getField2() {
            return this.field2;
        }

        public String getField3() {
            return this.field3;
        }

        public List<Float> getField4() {
            return this.field4;
        }

        public boolean getField5() {
            return this.field5;
        }
        public int[] getField6() { return this.field6; }

    }

    public record TestRecord(
        @KJsonIgnored int aboba,
        String biba,
        @KJsonArray(elementType = int.class) int[] boba,
        @KJsonMap(mapType = HashMap.class, valueType = Object.class) Map<String, Object> chungus
    ) {

    }

    @Test
    public void testDeserializeObject() {

        String data = "{\"field1\": 123, \"field3\": \"aboba\", \"field_4\": [1.0, 2.0], \"field6\": [1, 2, 3], \"field3\": \"Aboba\", \"field5\": true, \"field7\":{\"biba\": \"boba\"}}";
        KJsonValue jsonValue;
        try {
            jsonValue = this.jsonParser.parse(data);
        } catch (KJsonParseException e) {
            Assertions.fail(e);
            return;
        }

        SerializerTestClass deserialized;

        try {
            deserialized = this.jsonDeserializer.deserialize(jsonValue, SerializerTestClass.class);
        } catch (KJsonSerializationException e) {
            Assertions.fail(e);
            return;
        }

        Assertions.assertNotNull(deserialized);
        Assertions.assertEquals(123, deserialized.getField1());
        Assertions.assertEquals(0.0f, deserialized.getField2());
        Assertions.assertEquals("Aboba", deserialized.getField3());
        Assertions.assertTrue(deserialized.getField5());
        Assertions.assertTrue(deserialized.field7.containsKey("biba"));
        Assertions.assertEquals("boba", deserialized.field7.get("biba"));

        List<Float> check = List.of(1.0f, 2.0f);
        Assertions.assertEquals(check, deserialized.getField4());
        int[] checkArray = new int[] {1, 2, 3};
        Assertions.assertArrayEquals(checkArray, deserialized.getField6());

    }

    @Test
    public void testDeserializeNull() {

        try {
            Assertions.assertNull(jsonDeserializer.deserialize(new KJsonValue(KJsonValueType.NULL, null), SerializerTestClass.class));
        } catch (KJsonSerializationException e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testDeserializeBoolean() {

        try {
            Boolean value = jsonDeserializer.deserialize(new KJsonValue(KJsonValueType.BOOLEAN, true), Boolean.class);
            Assertions.assertNotNull(value);
            Assertions.assertTrue(value);
        } catch (KJsonSerializationException e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testDeserializeRecordUncovered() {

        String data = "{\"aboba\": 123, \"biba\": \"aboba\", \"boba\": [1, 2, 3], \"chungus\": {\"big\": \"chungus\"}}";
        KJsonValue jsonValue;
        try {
            jsonValue = this.jsonParser.parse(data);
        } catch (KJsonParseException e) {
            Assertions.fail(e);
            return;
        }

        TestRecord deserialized;

        try {
            deserialized = this.jsonDeserializer.deserialize(jsonValue, TestRecord.class);
        } catch (KJsonSerializationException e) {
            Assertions.fail(e);
            return;
        }

        Assertions.assertNotNull(deserialized);
        Assertions.assertEquals(0, deserialized.aboba());
        Assertions.assertEquals("aboba", deserialized.biba());
        Assertions.assertTrue(deserialized.chungus().containsKey("big"));
        Assertions.assertEquals("chungus", deserialized.chungus().get("big"));
        int[] checkArray = new int[] {1, 2, 3};
        Assertions.assertArrayEquals(checkArray, deserialized.boba());
    }

    @Test
    public void testDeserializeMap() {

        String data = "{\"field1\": 123}";
        KJsonValue jsonValue;
        try {
            jsonValue = this.jsonParser.parse(data);
        } catch (KJsonParseException e) {
            Assertions.fail(e);
            return;
        }

        try {
            Map<String, Object> deserialized = this.jsonDeserializer.deserialize(jsonValue, HashMap.class, Object.class);
            Assertions.assertTrue(deserialized.containsKey("field1"));
            Assertions.assertEquals(123, deserialized.get("field1"));
        } catch (KJsonSerializationException e) {
            Assertions.fail(e);
            return;
        }
    }
}
