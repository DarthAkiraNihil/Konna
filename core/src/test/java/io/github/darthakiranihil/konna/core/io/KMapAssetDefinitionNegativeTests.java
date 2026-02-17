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

import io.github.darthakiranihil.konna.core.data.json.KJsonValueType;
import io.github.darthakiranihil.konna.core.io.except.KAssetDefinitionError;
import io.github.darthakiranihil.konna.core.io.std.KMapAssetDefinition;
import io.github.darthakiranihil.konna.core.object.KObjectInstantiationType;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class KMapAssetDefinitionNegativeTests extends KStandardTestClass {

    private final KAssetDefinition definition;

    @SuppressWarnings("UnnecessaryBoxing")
    public KMapAssetDefinitionNegativeTests() {
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
        source.put("null", null);

        this.definition = new KMapAssetDefinition(
            source
        );
    }

    @Test
    public void testGetSubdefs() {

        Assertions.assertThrows(KAssetDefinitionError.class, () -> this.definition.getSubdefinition("null"));
        Assertions.assertThrows(KAssetDefinitionError.class, () -> this.definition.getSubdefinition("boolean_property"));
        Assertions.assertThrows(KAssetDefinitionError.class, () -> this.definition.getSubdefinitionArray("null"));
        Assertions.assertThrows(KAssetDefinitionError.class, () -> this.definition.getSubdefinitionArray("boolean_property"));

    }

    @Test
    public void testGetNonExistentEnum() {

        Assertions.assertThrows(KAssetDefinitionError.class, () -> this.definition.getEnum("null", KJsonValueType.class));
        Assertions.assertThrows(KAssetDefinitionError.class, () -> this.definition.getEnum("string_property", KJsonValueType.class));
        Assertions.assertThrows(KAssetDefinitionError.class, () -> this.definition.getEnum("string_property", KObjectInstantiationType.class));

    }


}
