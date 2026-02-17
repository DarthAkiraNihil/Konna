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

import io.github.darthakiranihil.konna.core.data.json.*;
import io.github.darthakiranihil.konna.core.data.json.std.KJsonValueIsClassValidator;
import io.github.darthakiranihil.konna.core.io.except.KAssetDefinitionError;
import io.github.darthakiranihil.konna.core.io.std.KJsonAssetDefinition;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class KJsonAssetDefinitionNegativeTests extends KStandardTestClass {

    @NullMarked
    private static final class Schema implements KJsonValidator {

        private final KJsonValidator validator;

        public Schema() {

            this.validator = KJsonObjectValidatorBuilder
                .create()
                .withSimpleField("int_property", KJsonValueType.NUMBER_INT)
                .build();

        }

        @Override
        public void validate(KJsonValue value) {
            this.validator.validate(value);
        }
    }

    @Test
    public void testCreateDefinitionOfNonObject() {

        Assertions.assertThrows(
            KAssetDefinitionError.class,
            () -> new KJsonAssetDefinition(KJsonValue.fromNumber(1), KJsonValueIsClassValidator.INSTANCE)
        );

    }

    @Test
    public void testGetSubdefinitionOfNonObject() {

        try {
            KJsonValue addedDef = this.jsonParser.parse("""
                {
                            "int_property": 1
                }"""
            );

            Assertions.assertThrows(
                KAssetDefinitionError.class,
                () -> new KJsonAssetDefinition(addedDef, new Schema()).getSubdefinition("int_property")
            );
        } catch (Throwable e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void testHasArraysButExceptionOccurred() {

        KAssetDefinition def = new KJsonAssetDefinition(
            KJsonValue.fromMap(Map.of(
                "array",
                KJsonValue.fromList(List.of(
                    KJsonValue.fromNumber(1),
                    KJsonValue.fromNumber(2),
                    KJsonValue.fromNumber(3)
                ))
            )), v -> {}
        );
        KAssetDefinition def2 = new KJsonAssetDefinition(
            KJsonValue.fromMap(Map.of(
                "array",
                KJsonValue.fromList(List.of(
                    KJsonValue.fromString("1")
                ))
            )), v -> {}
        );

        Assertions.assertFalse(def.hasStringArray("array"));
        Assertions.assertFalse(def.hasSubdefinitionArray("array"));
        Assertions.assertFalse(def.hasBooleanArray("array"));
        Assertions.assertFalse(def2.hasIntArray("array"));
        Assertions.assertFalse(def2.hasFloatArray("array"));

    }

    @Test
    public void testGetEnumButValueIsNotValid() {
        KAssetDefinition def = new KJsonAssetDefinition(
            KJsonValue.fromMap(Map.of(
                "enum",
                KJsonValue.fromString("ABIBA")
            )), v -> {}
        );

        Assertions.assertThrows(KAssetDefinitionError.class, () -> {
            def.getEnum("enum", KJsonValueType.class);
        });
    }

    @Test
    public void testHasEnumAndExceptionOccurred() {
        KAssetDefinition def = new KJsonAssetDefinition(
            KJsonValue.fromMap(Map.of(
                "enum",
                KJsonValue.fromString("ABIBA")
            )), v -> {}
        );

        Assertions.assertFalse(def.hasEnum("enum", KJsonValueType.class));
    }

    @Test
    public void testStringAndIntArrayButExceptionOccurred() {

        KAssetDefinition def = new KJsonAssetDefinition(
            KJsonValue.fromMap(Map.of()), v -> {}
        );

        Assertions.assertThrows(KAssetDefinitionError.class, () -> def.getString("aboba"));
        Assertions.assertThrows(KAssetDefinitionError.class, () -> def.getIntArray("aboba"));

    }
}
