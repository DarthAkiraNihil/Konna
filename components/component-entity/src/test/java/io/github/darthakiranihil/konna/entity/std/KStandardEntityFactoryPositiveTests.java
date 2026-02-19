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

import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonParser;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonTokenizer;
import io.github.darthakiranihil.konna.core.io.std.KJsonSubtypeBasedAssetLoader;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import io.github.darthakiranihil.konna.entity.KEntity;
import io.github.darthakiranihil.konna.entity.KEntityFactory;
import io.github.darthakiranihil.konna.entity.KEntityMetadata;
import io.github.darthakiranihil.konna.entity.asset.KEntityMetadataCollection;
import io.github.darthakiranihil.konna.entity.impl.TestEntityDataComponent;
import io.github.darthakiranihil.konna.entity.type.KEntityMetadataTypedef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class KStandardEntityFactoryPositiveTests extends KStandardTestClass {

    private final KEntityFactory factory;
    private final KEntityMetadataCollection metadataCollection;

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

        this.metadataCollection = new KEntityMetadataCollection(
            assetLoader
        );

        this.factory = new KStandardEntityFactory(
            this.metadataCollection,
            KStandardTestClass.context,
            this.jsonDeserializer
        );
    }

    @Test
    public void testEmptyCreateEntity() {

        KEntityMetadata typpi1 = this.metadataCollection.getAsset("Typpi1");

        KEntity entity = this.factory.createEntity("e1", "Typpi1");

        var ted1 = entity.getComponent(TestEntityDataComponent.class);

        Assertions.assertNotNull(ted1);
        Assertions.assertEquals(TestEntityDataComponent.class, ted1.getClass());

    }
}
