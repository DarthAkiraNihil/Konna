package io.github.darthakiranihil.konna.core.data.json;

import io.github.darthakiranihil.konna.core.data.json.except.KJsonSerializationException;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class KStandardJsonSerializerPositiveTests {

    private static class SerializerTestClass {
        public int testFieldA;
        public float testFieldB;
        public String testFieldC;

        public SerializerTestClass(int testFieldA, float testFieldB, String testFieldC) {
            this.testFieldA = testFieldA;
            this.testFieldB = testFieldB;
            this.testFieldC = testFieldC;
        }
    }

    @Test
    void testBaseJsonSerialization() {
        KJsonSerializer serializer = new KStandardJsonSerializer();

        SerializerTestClass testObject = new SerializerTestClass(1, 2.0f, "TEST");

        Map<String, Object> compareData = new HashMap<>();
        compareData.put("testFieldA", 1);
        compareData.put("testFieldB", 2.0f);
        compareData.put("testFieldC", "TEST");

        try {
            KJsonValue serialized = serializer.serialize(testObject, SerializerTestClass.class);
            Assertions.assertEquals(KJsonValueType.OBJECT, serialized.getType());
            for (var entry: serialized.entrySet()) {
                Assertions.assertEquals(compareData.get(entry.getKey()), entry.getValue().getRawObject());
            }
        } catch (KJsonSerializationException e) {
            Assertions.fail(e);
        }
    }
}
