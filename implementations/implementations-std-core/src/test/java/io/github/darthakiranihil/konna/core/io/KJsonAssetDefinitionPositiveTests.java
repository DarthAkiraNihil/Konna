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
        KAsset asset = new KAsset("abiba", "aboba", def);

        Assertions.assertFalse(asset.hasIntArray("array"));
        Assertions.assertFalse(asset.hasFloatArray("array"));
        Assertions.assertFalse(asset.hasBooleanArray("array"));
        Assertions.assertFalse(asset.hasStringArray("array"));
        Assertions.assertFalse(asset.hasSubdefinitionArray("array"));

        KAssetDefinition def2 = new KJsonAssetDefinition(
            KJsonValue.fromMap(Map.of("aboba", KJsonValue.fromNumber(1))), v -> {}
        );
        KAsset asset2 = new KAsset("abiba2", "aboba2", def2);
        Assertions.assertFalse(asset2.hasIntArray("aboba"));

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
        KAsset asset = new KAsset("abiba", "aboba", def);
        
        Assertions.assertEquals(KStandardJsonParser.class, asset.getClassObject("class_property"));
        Assertions.assertEquals(KStandardJsonParser.class, asset.getClassObject("class_property", KJsonParser.class));

        Assertions.assertEquals(1, asset.getClassObjectArray("class_array_property").length);
        Assertions.assertEquals(1, asset.getClassObjectArray("class_array_property", KJsonParser.class).length);

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
        
        KAsset asset = new KAsset("abiba", "aboba", def);
        Assertions.assertTrue(asset.hasClassObject("class_property"));
        Assertions.assertTrue(asset.hasClassObject("class_property", KJsonParser.class));

        Assertions.assertTrue(asset.hasClassObjectArray("class_array_property"));
        Assertions.assertTrue(asset.hasClassObjectArray("class_array_property", KJsonParser.class));

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
        
        KAsset asset = new KAsset("abiba", "aboba", def);
        Assertions.assertEquals(2, asset.getProperties().size());
        Assertions.assertFalse(asset.hasClassObject("proppp"));
        Assertions.assertFalse(asset.hasClassObject("proppp", KJsonParser.class));
        Assertions.assertFalse(asset.hasClassObject("class_property", KJsonValidator.class));

        Assertions.assertFalse(asset.hasClassObjectArray("dfdf"));
        Assertions.assertFalse(asset.hasClassObjectArray("dfdfdf", KJsonParser.class));
        Assertions.assertFalse(asset.hasClassObjectArray("class_array_property", KJsonValidator.class));

    }

    @Test
    public void testGetObjectProperties() {

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
        
        KAsset asset = new KAsset("abiba", "aboba", def);
        Assertions.assertNotNull(asset.getObject("class_property"));
        Assertions.assertEquals(1, asset.getObjectArray("class_array_property").length);

    }

    @Test
    public void testHasObjectProperties() {

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

        KAsset asset = new KAsset("abiba", "aboba", def);
        Assertions.assertTrue(asset.hasObject("class_property"));
        Assertions.assertTrue(asset.hasObjectArray("class_array_property"));

    }

    @Test
    public void testDoesNotHaveObjectProperties() {

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

        KAsset asset = new KAsset("abiba", "aboba", def);
        Assertions.assertEquals(2, asset.getProperties().size());
        Assertions.assertFalse(asset.hasObject("proppp"));

        Assertions.assertFalse(asset.hasObjectArray("proppp"));
        Assertions.assertFalse(asset.hasObjectArray("class_property"));

    }
}
