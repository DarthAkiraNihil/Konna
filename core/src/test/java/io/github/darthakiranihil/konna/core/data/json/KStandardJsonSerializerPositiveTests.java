package io.github.darthakiranihil.konna.core.data.json;

import io.github.darthakiranihil.konna.core.data.json.except.KJsonSerializationException;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonSerializer;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class KStandardJsonSerializerPositiveTests extends KStandardTestClass {

    private final KJsonSerializer serializer;

    public KStandardJsonSerializerPositiveTests() {
        this.serializer = KStandardTestClass.jsonSerializer;
    }

    @Test
    void testSerializeInteger() {

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
    void testSerializeByte() {

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
    void testSerializeShort() {

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
    void testSerializeLong() {

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
    void testSerializeFloat() {

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
    void testSerializeDouble() {

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
    void testSerializeBoolean() {

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
    void testSerializeChar() {

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
    void testSerializeString() {

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
    void testSerializeList() {

        List<String> testList = List.of("aboba_123");

        try {
            KJsonValue testValue = this.serializer.serialize(testList, List.class);

            Assertions.assertEquals(KJsonValueType.ARRAY, testValue.getType());

            int i = 0;
            for (Iterator<KJsonValue> it = testValue.iterator(); it.hasNext(); ) {
                var entry = it.next();

                Assertions.assertEquals(KJsonValueType.STRING, entry.getType());
                Assertions.assertEquals(testList.get(i), entry.getString());
                i++;
            }

        } catch (KJsonSerializationException e) {
            Assertions.fail(e);
        }

    }

    @Test
    void voidTestSerializeMap() {

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

        public SerializerTestClass(int field1, float field2, String field3, List<Float> field4, boolean field5) {
            this.field1 = field1;
            this.field2 = field2;
            this.field3 = field3;
            this.field4 = field4;
            this.field5 = field5;
        }

        public int getField1() {
            return field1;
        }

        public String getField3() {
            return field3;
        }

        public List<Float> getField4() {
            return field4;
        }

    }

    @Test
    void testSerializeObject() {
        KJsonSerializer serializer = new KStandardJsonSerializer();

        List<Float> testList = List.of(1.0f, 2.0f);
        SerializerTestClass testObject = new SerializerTestClass(1, 2.0f, "TEST", testList, false);

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

        KJsonValue field1 = serialized.getProperty("field1");
        KJsonValue field3 = serialized.getProperty("field3");
        KJsonValue field4 = serialized.getProperty("field_4");

        Assertions.assertEquals(KJsonValueType.NUMBER_INT, field1.getType());
        Assertions.assertEquals(KJsonValueType.STRING, field3.getType());
        Assertions.assertEquals(KJsonValueType.ARRAY, field4.getType());

        Assertions.assertEquals(testObject.getField1(), field1.getInt());
        Assertions.assertEquals(testObject.getField3(), field3.getString());

        int i = 0;
        List<Float> originalField4 = testObject.getField4();

        for (Iterator<KJsonValue> it = field4.iterator(); it.hasNext(); ) {
            var entry = it.next();
            Assertions.assertEquals(originalField4.get(i), entry.getFloat());
            i++;
        }
    }
}
