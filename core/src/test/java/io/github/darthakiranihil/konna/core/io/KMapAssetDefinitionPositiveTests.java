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

package io.github.darthakiranihil.konna.core.io;

import io.github.darthakiranihil.konna.core.data.json.KJsonParser;
import io.github.darthakiranihil.konna.core.data.json.KJsonValidator;
import io.github.darthakiranihil.konna.core.data.json.KJsonValueType;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonParser;
import io.github.darthakiranihil.konna.core.io.std.KMapAssetDefinition;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class KMapAssetDefinitionPositiveTests extends KStandardTestClass {

    private final KAssetDefinition definition;

    @SuppressWarnings("UnnecessaryBoxing")
    public KMapAssetDefinitionPositiveTests() {
        Map<String, Object> source = new HashMap<>();
        source.put("int_property", 1);
        source.put("long_property", 1L);
        source.put("short_property", (short) 1);
        source.put("byte_property", (byte) 1);
        source.put("float_property", 1.0f);
        source.put("double_property", 1.0);
        source.put("boolean_property", true);
        source.put("string_property", "Aboba123");
        source.put("subdef_property", new KMapAssetDefinition());
        source.put("map_property", new HashMap<>());

        source.put("int_array_property", new int[] {1});
        source.put("long_array_property", new long[] {1});
        source.put("short_array_property", new short[] {1});
        source.put("byte_array_property", new byte[] {1});
        source.put("float_array_property", new float[] {1});
        source.put("double_array_property", new double[] {1});
        source.put("boolean_array_property", new boolean[] {true});
        source.put("string_array_property", new String[0]);
        source.put("subdef_array_property", new KMapAssetDefinition[0]);
        source.put("map_array_property", new Map[] {new HashMap<String, Object>()});

        source.put("boxed_int_property", Integer.valueOf(1));
        source.put("boxed_long_property", Long.valueOf(1L));
        source.put("boxed_short_property", Short.valueOf((short) 1));
        source.put("boxed_byte_property", Byte.valueOf((byte) 1));
        source.put("boxed_float_property", Float.valueOf(1.0f));
        source.put("boxed_double_property", Double.valueOf(1.0));
        source.put("boxed_boolean_property", Boolean.valueOf(true));
        source.put("boxed_int_array_property", new Integer[] {1});
        source.put("boxed_long_array_property", new Long[] {1L});
        source.put("boxed_short_array_property", new Short[] {1});
        source.put("boxed_byte_array_property", new Byte[] {1});
        source.put("boxed_float_array_property", new Float[] {1f});
        source.put("boxed_double_array_property", new Double[] {1.0});
        source.put("boxed_boolean_array_property", new Boolean[] {true});

        source.put("enum_as_enum_property", KJsonValueType.ARRAY);
        source.put("enum_as_string_property", "ARRAY");

        source.put("class_as_string_property", KStandardJsonParser.class.getCanonicalName());
        source.put("class_as_class_property", KStandardJsonParser.class);
        source.put("class_array_as_string_array_property", new String[] {KStandardJsonParser.class.getCanonicalName()});
        source.put("class_array_as_class_array_property", new Class[] {KStandardJsonParser.class});
        source.put("empty_class_array_as_class_array_property", new Class[0]);

        this.definition = new KMapAssetDefinition(
            source
        );
    }

    @Test
    public void testGetProperties() {

        Assertions.assertEquals(1, this.definition.getInt("int_property"));
        Assertions.assertEquals(1L, this.definition.getInt("long_property"));
        Assertions.assertEquals((short) 1, this.definition.getInt("short_property"));
        Assertions.assertEquals((byte) 1, this.definition.getInt("byte_property"));
        Assertions.assertEquals(1.0f, this.definition.getFloat("float_property"));
        Assertions.assertEquals(1.0, this.definition.getFloat("double_property"));
        Assertions.assertTrue(this.definition.getBoolean("boolean_property"));
        Assertions.assertEquals("Aboba123", this.definition.getString("string_property"));


        Assertions.assertEquals(1, this.definition.getInt("boxed_int_property"));
        Assertions.assertEquals(1L, this.definition.getInt("boxed_long_property"));
        Assertions.assertEquals((short) 1, this.definition.getInt("boxed_short_property"));
        Assertions.assertEquals((byte) 1, this.definition.getInt("boxed_byte_property"));
        Assertions.assertEquals(1.0f, this.definition.getFloat("boxed_float_property"));
        Assertions.assertEquals(1.0, this.definition.getFloat("boxed_double_property"));
        Assertions.assertTrue(this.definition.getBoolean("boxed_boolean_property"));

        Assertions.assertDoesNotThrow(
            () -> {
                this.definition.getSubdefinition("subdef_property").getClass();
                this.definition.getSubdefinition("map_property").getClass();
            }
        );

        Assertions.assertDoesNotThrow(
            () -> {
                this.definition.getSubdefinitionArray("subdef_array_property").getClass();
                this.definition.getSubdefinitionArray("map_array_property").getClass();
            }
        );

        Assertions.assertEquals(1, this.definition.getIntArray("int_array_property").length);
        Assertions.assertEquals(1, this.definition.getIntArray("long_array_property").length);
        Assertions.assertEquals(1, this.definition.getIntArray("short_array_property").length);
        Assertions.assertEquals(1, this.definition.getIntArray("byte_array_property").length);
        Assertions.assertEquals(1, this.definition.getFloatArray("float_array_property").length);
        Assertions.assertEquals(1, this.definition.getFloatArray("double_array_property").length);
        Assertions.assertEquals(1, this.definition.getBooleanArray("boolean_array_property").length);
        Assertions.assertEquals(0, this.definition.getStringArray("string_array_property").length);

        Assertions.assertEquals(1, this.definition.getIntArray("boxed_int_array_property").length);
        Assertions.assertEquals(1, this.definition.getIntArray("boxed_long_array_property").length);
        Assertions.assertEquals(1, this.definition.getIntArray("boxed_short_array_property").length);
        Assertions.assertEquals(1, this.definition.getIntArray("boxed_byte_array_property").length);
        Assertions.assertEquals(1, this.definition.getFloatArray("boxed_float_array_property").length);
        Assertions.assertEquals(1, this.definition.getFloatArray("boxed_double_array_property").length);
        Assertions.assertEquals(1, this.definition.getBooleanArray("boxed_boolean_array_property").length);

        Assertions.assertEquals(KJsonValueType.ARRAY, this.definition.getEnum("enum_as_enum_property", KJsonValueType.class));
        Assertions.assertEquals(KJsonValueType.ARRAY, this.definition.getEnum("enum_as_string_property", KJsonValueType.class));

        Assertions.assertEquals(KStandardJsonParser.class, this.definition.getClassObject("class_as_string_property"));
        Assertions.assertEquals(KStandardJsonParser.class, this.definition.getClassObject("class_as_class_property"));
        Assertions.assertEquals(KStandardJsonParser.class, this.definition.getClassObject("class_as_string_property", KJsonParser.class));
        Assertions.assertEquals(KStandardJsonParser.class, this.definition.getClassObject("class_as_class_property", KJsonParser.class));

        Assertions.assertEquals(1, this.definition.getClassObjectArray("class_array_as_string_array_property").length);
        Assertions.assertEquals(1, this.definition.getClassObjectArray("class_array_as_class_array_property").length);
        Assertions.assertEquals(1, this.definition.getClassObjectArray("class_array_as_string_array_property", KJsonParser.class).length);
        Assertions.assertEquals(1, this.definition.getClassObjectArray("class_array_as_class_array_property", KJsonParser.class).length);

    }

    @Test
    public void testHasProperties() {

        Assertions.assertTrue(this.definition.hasInt("int_property"));
        Assertions.assertTrue(this.definition.hasInt("long_property"));
        Assertions.assertTrue(this.definition.hasInt("short_property"));
        Assertions.assertTrue(this.definition.hasInt("byte_property"));
        Assertions.assertTrue(this.definition.hasFloat("float_property"));
        Assertions.assertTrue(this.definition.hasFloat("double_property"));
        Assertions.assertTrue(this.definition.hasBoolean("boolean_property"));
        Assertions.assertTrue(this.definition.hasString("string_property"));


        Assertions.assertTrue(this.definition.hasInt("boxed_int_property"));
        Assertions.assertTrue(this.definition.hasInt("boxed_long_property"));
        Assertions.assertTrue(this.definition.hasInt("boxed_short_property"));
        Assertions.assertTrue(this.definition.hasInt("boxed_byte_property"));
        Assertions.assertTrue(this.definition.hasFloat("boxed_float_property"));
        Assertions.assertTrue(this.definition.hasFloat("boxed_double_property"));
        Assertions.assertTrue(this.definition.hasBoolean("boxed_boolean_property"));

        Assertions.assertTrue(this.definition.hasIntArray("int_array_property"));
        Assertions.assertTrue(this.definition.hasIntArray("long_array_property"));
        Assertions.assertTrue(this.definition.hasIntArray("short_array_property"));
        Assertions.assertTrue(this.definition.hasIntArray("byte_array_property"));
        Assertions.assertTrue(this.definition.hasFloatArray("float_array_property"));
        Assertions.assertTrue(this.definition.hasFloatArray("double_array_property"));
        Assertions.assertTrue(this.definition.hasBooleanArray("boolean_array_property"));
        Assertions.assertTrue(this.definition.hasStringArray("string_array_property"));

        Assertions.assertTrue(this.definition.hasIntArray("boxed_int_array_property"));
        Assertions.assertTrue(this.definition.hasIntArray("boxed_long_array_property"));
        Assertions.assertTrue(this.definition.hasIntArray("boxed_short_array_property"));
        Assertions.assertTrue(this.definition.hasIntArray("boxed_byte_array_property"));
        Assertions.assertTrue(this.definition.hasFloatArray("boxed_float_array_property"));
        Assertions.assertTrue(this.definition.hasFloatArray("boxed_double_array_property"));
        Assertions.assertTrue(this.definition.hasBooleanArray("boxed_boolean_array_property"));
        Assertions.assertTrue(this.definition.hasSubdefinitionArray("subdef_array_property"));
        Assertions.assertTrue(this.definition.hasSubdefinitionArray("map_array_property"));
        Assertions.assertTrue(this.definition.hasSubdefinition("subdef_property"));
        Assertions.assertTrue(this.definition.hasSubdefinition("map_property"));
        Assertions.assertTrue(this.definition.hasEnum("enum_as_enum_property", KJsonValueType.class));
        Assertions.assertTrue(this.definition.hasEnum("enum_as_string_property", KJsonValueType.class));

        Assertions.assertTrue(this.definition.hasClassObject("class_as_string_property"));
        Assertions.assertTrue(this.definition.hasClassObject("class_as_class_property", KJsonParser.class));
        Assertions.assertTrue(this.definition.hasClassObject("class_as_string_property"));
        Assertions.assertTrue(this.definition.hasClassObject("class_as_class_property", KJsonParser.class));

        Assertions.assertTrue(this.definition.hasClassObjectArray("class_array_as_string_array_property"));
        Assertions.assertTrue(this.definition.hasClassObjectArray("class_array_as_class_array_property", KJsonParser.class));
        Assertions.assertTrue(this.definition.hasClassObjectArray("class_array_as_string_array_property"));
        Assertions.assertTrue(this.definition.hasClassObjectArray("class_array_as_class_array_property", KJsonParser.class));
        Assertions.assertTrue(this.definition.hasClassObjectArray("empty_class_array_as_class_array_property", KJsonParser.class));
        Assertions.assertTrue(this.definition.hasClassObjectArray("empty_class_array_as_class_array_property", KJsonValidator.class));

    }

    @Test
    public void testDoesNotHaveProperties() {

        Assertions.assertFalse(this.definition.hasInt("float_property"));
        Assertions.assertFalse(this.definition.hasInt("aboab123"));
        Assertions.assertFalse(this.definition.hasBooleanArray("aboba1234"));
        Assertions.assertFalse(this.definition.hasFloat("long_property"));
        Assertions.assertFalse(this.definition.hasBoolean("string_property"));
        Assertions.assertFalse(this.definition.hasString("boolean_property"));

        Assertions.assertFalse(this.definition.hasIntArray("float_array_property"));
        Assertions.assertFalse(this.definition.hasFloatArray("long_array_property"));
        Assertions.assertFalse(this.definition.hasBooleanArray("string_array_property"));
        Assertions.assertFalse(this.definition.hasStringArray("boolean_array_property"));
        Assertions.assertFalse(this.definition.hasStringArray("tete"));
        Assertions.assertFalse(this.definition.hasSubdefinition("boolean_array_property"));

        Assertions.assertFalse(this.definition.hasSubdefinitionArray("boxed_boolean_array_property"));
        Assertions.assertFalse(this.definition.hasBooleanArray("map_array_property"));
        Assertions.assertFalse(this.definition.hasEnum("map_array_property", KJsonValueType.class));

        Assertions.assertFalse(this.definition.hasClassObject("aboba123"));
        Assertions.assertFalse(this.definition.hasClassObject("string_property"));
        Assertions.assertFalse(this.definition.hasClassObject("aboba123", KJsonParser.class));
        Assertions.assertFalse(this.definition.hasClassObject("string_property", KJsonParser.class));
        Assertions.assertFalse(this.definition.hasClassObject("string_property", KJsonValidator.class));

        Assertions.assertFalse(this.definition.hasClassObjectArray("aboba213"));
        Assertions.assertFalse(this.definition.hasClassObjectArray("boolean_array_property"));
        Assertions.assertFalse(this.definition.hasClassObjectArray("aboba213", KJsonParser.class));
        Assertions.assertFalse(this.definition.hasClassObjectArray("boolean_array_property", KJsonParser.class));
        Assertions.assertFalse(this.definition.hasClassObjectArray("class_array_as_class_array_property", KJsonValidator.class));

    }
}
