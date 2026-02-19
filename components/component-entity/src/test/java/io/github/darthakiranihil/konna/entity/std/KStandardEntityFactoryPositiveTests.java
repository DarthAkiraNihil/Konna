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

package io.github.darthakiranihil.konna.entity.std;

import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonParser;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonTokenizer;
import io.github.darthakiranihil.konna.core.io.std.KJsonSubtypeBasedAssetLoader;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import io.github.darthakiranihil.konna.entity.KEntity;
import io.github.darthakiranihil.konna.entity.KEntityFactory;
import io.github.darthakiranihil.konna.entity.asset.KEntityMetadataCollection;
import io.github.darthakiranihil.konna.entity.impl.TestEntityDataComponent;
import io.github.darthakiranihil.konna.entity.impl.TestEntityDataComponent2;
import io.github.darthakiranihil.konna.entity.type.KEntityMetadataTypedef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class KStandardEntityFactoryPositiveTests extends KStandardTestClass {

    private final KEntityFactory factory;

    public KStandardEntityFactoryPositiveTests() {
        var assetLoader = new KJsonSubtypeBasedAssetLoader(
            KStandardTestClass.context,
            Map.of("entities", new KJsonSubtypeBasedAssetLoader.AssetTypeData(
                new String[] { KEntityMetadataTypedef.ENTITY_METADATA_ASSET_TYPE },
                new String[] {"classpath:assets/entities.json"}
            )),
            new KStandardJsonParser(new KStandardJsonTokenizer())
        );
        assetLoader.addAssetTypedef(new KEntityMetadataTypedef());

        var metadataCollection = new KEntityMetadataCollection(
            assetLoader
        );

        this.factory = new KStandardEntityFactory(
            metadataCollection,
            KStandardTestClass.context,
            this.jsonDeserializer
        );
    }

    @Test
    public void testCreateEmptyEntity() {
        KEntity entity = this.factory.createEntity("e1", "Typpi1");
        var ted1 = entity.getComponent(TestEntityDataComponent.class);
        Assertions.assertNull(ted1);
    }

    @Test
    public void testCreateNonEmptyEntity() {

        KEntity entity = this.factory.createEntity("e2", "Typpi2");
        var ted = entity.getComponent(TestEntityDataComponent.class);
        Assertions.assertNotNull(ted);
        Assertions.assertEquals(TestEntityDataComponent.class, ted.getClass());

    }

    @Test
    void testCreateEntityWithDataExtensions() {

        KEntity entity = this.factory.createEntity("e3", "Typpi3");
        var ted1 = entity.getComponent(TestEntityDataComponent.class);
        var ted2 = entity.getComponent(TestEntityDataComponent2.class);

        Assertions.assertNotNull(ted1);
        Assertions.assertNotNull(ted2);

        Assertions.assertEquals(TestEntityDataComponent.class, ted1.getClass());
        Assertions.assertEquals(TestEntityDataComponent2.class, ted2.getClass());

    }

    @Test
    void testCreateEntityWithMultilevelDataExtensions() {

        KEntity entity = this.factory.createEntity("e4", "Typpi4");
        var ted1 = entity.getComponent(TestEntityDataComponent.class);
        var ted2 = entity.getComponent(TestEntityDataComponent2.class);

        Assertions.assertNotNull(ted1);
        Assertions.assertNotNull(ted2);

        Assertions.assertEquals(TestEntityDataComponent.class, ted1.getClass());
        Assertions.assertEquals(TestEntityDataComponent2.class, ted2.getClass());

    }

    @Test
    public void testCreateEmptyEntityWithData() {

        KEntity entity = this.factory.createEntity(
            "e1",
            "Typpi1",
            KJsonValue.fromMap(Map.of())
        );

        var ted1 = entity.getComponent(TestEntityDataComponent.class);
        Assertions.assertNull(ted1);
        // Assertions.assertEquals(TestEntityDataComponent.class, ted1.getClass());

    }

    @Test
    public void testCreateNonEmptyEntityWithData() {

        KEntity entity = this.factory.createEntity(
            "e2",
            "Typpi2",
            KJsonValue.fromMap(
                Map.of(
                    TestEntityDataComponent.class.getCanonicalName(),
                    KJsonValue.fromMap(
                        Map.of(
                            "testField", KJsonValue.fromNumber(1)
                        )
                    )
                )
            )
        );
        var ted = entity.getComponent(TestEntityDataComponent.class);
        Assertions.assertNotNull(ted);
        Assertions.assertEquals(TestEntityDataComponent.class, ted.getClass());
        Assertions.assertEquals(1, ted.getTestField());

    }

    @Test
    void testCreateEntityWithDataExtensionsWithData() {

        KEntity entity = this.factory.createEntity(
            "e3",
            "Typpi3",
            KJsonValue.fromMap(
                Map.of(
                    TestEntityDataComponent.class.getCanonicalName(),
                    KJsonValue.fromMap(
                        Map.of(
                            "testField", KJsonValue.fromNumber(1)
                        )
                    ),
                    TestEntityDataComponent2.class.getCanonicalName(),
                    KJsonValue.fromMap(
                        Map.of(
                            "stringField", KJsonValue.fromString("123")
                        )
                    )
                )
            )
        );
        var ted1 = entity.getComponent(TestEntityDataComponent.class);
        var ted2 = entity.getComponent(TestEntityDataComponent2.class);

        Assertions.assertNotNull(ted1);
        Assertions.assertNotNull(ted2);

        Assertions.assertEquals(TestEntityDataComponent.class, ted1.getClass());
        Assertions.assertEquals(TestEntityDataComponent2.class, ted2.getClass());

        Assertions.assertEquals(1, ted1.getTestField());
        Assertions.assertEquals("123", ted2.getStringField());

    }

    @Test
    void testCreateEntityWithMultilevelDataExtensionsWithData() {

        KEntity entity = this.factory.createEntity(
            "e4",
            "Typpi4",
            KJsonValue.fromMap(
                Map.of(
                    TestEntityDataComponent.class.getCanonicalName(),
                    KJsonValue.fromMap(
                        Map.of(
                            "testField", KJsonValue.fromNumber(1)
                        )
                    ),
                    TestEntityDataComponent2.class.getCanonicalName(),
                    KJsonValue.fromMap(
                        Map.of(
                            "stringField", KJsonValue.fromString("123")
                        )
                    )
                )
            )
        );
        var ted1 = entity.getComponent(TestEntityDataComponent.class);
        var ted2 = entity.getComponent(TestEntityDataComponent2.class);

        Assertions.assertNotNull(ted1);
        Assertions.assertNotNull(ted2);

        Assertions.assertEquals(TestEntityDataComponent.class, ted1.getClass());
        Assertions.assertEquals(TestEntityDataComponent2.class, ted2.getClass());

        Assertions.assertEquals(1, ted1.getTestField());
        Assertions.assertEquals("123", ted2.getStringField());

    }


}
