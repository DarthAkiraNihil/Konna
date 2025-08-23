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

import java.util.List;

@SuppressWarnings("ALL")
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
