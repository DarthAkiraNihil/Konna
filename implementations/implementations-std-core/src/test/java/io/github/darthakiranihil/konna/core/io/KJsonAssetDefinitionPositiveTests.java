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
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.data.json.KStandardJsonParser;
import io.github.darthakiranihil.konna.core.io.KJsonAssetDefinition;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class KJsonAssetDefinitionPositiveTests extends KStandardTestClass {

    @Test
    public void testPropertiesButTheyDoNotExist() {

        KAssetDefinition def = new KJsonAssetDefinition(
            KJsonValue.fromMap(Map.of()), v -> {}
        );

        Assertions.assertFalse(def.hasIntArray("array"));
        Assertions.assertFalse(def.hasFloatArray("array"));
        Assertions.assertFalse(def.hasBooleanArray("array"));
        Assertions.assertFalse(def.hasStringArray("array"));
        Assertions.assertFalse(def.hasSubdefinitionArray("array"));

        KAssetDefinition def2 = new KJsonAssetDefinition(
            KJsonValue.fromMap(Map.of("aboba", KJsonValue.fromNumber(1))), v -> {}
        );
        Assertions.assertFalse(def2.hasIntArray("aboba"));

    }

    @Test
    public void testGetClassProperties() {

        KAssetDefinition def = new KJsonAssetDefinition(
            KJsonValue.fromMap(
                Map.of(
                    "class_property", KJsonValue.fromString(KStandardJsonParser.class.getCanonicalName()),
                    "class_array_property", KJsonValue.fromList(
                        List.of(
                            KJsonValue.fromString(KStandardJsonParser.class.getCanonicalName())
                        )
                    )
            )
            ),
            v -> {}
        );

        Assertions.assertEquals(KStandardJsonParser.class, def.getClassObject("class_property"));
        Assertions.assertEquals(KStandardJsonParser.class, def.getClassObject("class_property", KJsonParser.class));

        Assertions.assertEquals(1, def.getClassObjectArray("class_array_property").length);
        Assertions.assertEquals(1, def.getClassObjectArray("class_array_property", KJsonParser.class).length);

    }

    @Test
    public void testHasClassProperties() {

        KAssetDefinition def = new KJsonAssetDefinition(
            KJsonValue.fromMap(
                Map.of(
                    "class_property", KJsonValue.fromString(KStandardJsonParser.class.getCanonicalName()),
                    "class_array_property", KJsonValue.fromList(
                        List.of(
                            KJsonValue.fromString(KStandardJsonParser.class.getCanonicalName())
                        )
                    )
                )
            ),
            v -> {}
        );

        Assertions.assertTrue(def.hasClassObject("class_property"));
        Assertions.assertTrue(def.hasClassObject("class_property", KJsonParser.class));

        Assertions.assertTrue(def.hasClassObjectArray("class_array_property"));
        Assertions.assertTrue(def.hasClassObjectArray("class_array_property", KJsonParser.class));

    }

    @Test
    public void testDoesNotHaveClassProperties() {

        KAssetDefinition def = new KJsonAssetDefinition(
            KJsonValue.fromMap(
                Map.of(
                    "class_property", KJsonValue.fromString(KStandardJsonParser.class.getCanonicalName()),
                    "class_array_property", KJsonValue.fromList(
                        List.of(
                            KJsonValue.fromString(KStandardJsonParser.class.getCanonicalName())
                        )
                    )
                )
            ),
            v -> {}
        );

        Assertions.assertFalse(def.hasClassObject("proppp"));
        Assertions.assertFalse(def.hasClassObject("proppp", KJsonParser.class));
        Assertions.assertFalse(def.hasClassObject("class_property", KJsonValidator.class));

        Assertions.assertFalse(def.hasClassObjectArray("dfdf"));
        Assertions.assertFalse(def.hasClassObjectArray("dfdfdf", KJsonParser.class));
        Assertions.assertFalse(def.hasClassObjectArray("class_array_property", KJsonValidator.class));

    }
}
