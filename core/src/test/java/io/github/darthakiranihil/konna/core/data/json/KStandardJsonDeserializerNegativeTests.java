package io.github.darthakiranihil.konna.core.data.json;

import io.github.darthakiranihil.konna.core.data.json.except.KJsonParseException;
import io.github.darthakiranihil.konna.core.data.json.except.KJsonSerializationException;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class KStandardJsonDeserializerNegativeTests extends KStandardTestClass {

    private static class SerializerTestClass {
        public int field1;
        @KJsonIgnored public float field2;
        @KJsonSerialized final private String field3;
        @KJsonCustomName(name = "field_44") @KJsonArray(elementType = Float.class) public List<Float> field4;
        private final boolean field5;

        public SerializerTestClass(int field1, float field2, String field3, List<Float> field4, boolean field5) {
            this.field1 = field1;
            this.field2 = field2;
            this.field3 = field3;
            this.field4 = field4;
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

    }

    @Test
    public void testDeserializeObjectDeserializeNonMarkedPrivate() {

        String data = "{\"field1\": 123, \"field3\": \"aboba\", \"field_4\": [1.0, 2.0], \"field5\": true}";
        KJsonValue jsonValue;
        try {
            jsonValue = KStandardTestClass.jsonParser.parse(data);
        } catch (KJsonParseException e) {
            Assertions.fail(e);
            return;
        }

        Assertions.assertThrowsExactly(KJsonSerializationException.class, () -> {
            KStandardTestClass.jsonDeserializer.deserialize(jsonValue, KStandardJsonDeserializerNegativeTests.SerializerTestClass.class);
        });

    }

    private static class ClassWithUnmarkedArray {
        public List<Integer> field1;

        public ClassWithUnmarkedArray(List<Integer> field1) {
            this.field1 = field1;
        }
    }

    @Test
    public void testDeserializeObjectDeserializeNonAnnotatedArray() {

        String data = "{\"field1\": [1, 2, 3]}";
        KJsonValue jsonValue;
        try {
            jsonValue = KStandardTestClass.jsonParser.parse(data);
        } catch (KJsonParseException e) {
            Assertions.fail(e);
            return;
        }

        Assertions.assertThrowsExactly(KJsonSerializationException.class, () -> {
            KStandardTestClass.jsonDeserializer.deserialize(jsonValue, KStandardJsonDeserializerNegativeTests.ClassWithUnmarkedArray.class);
        });

    }

}
