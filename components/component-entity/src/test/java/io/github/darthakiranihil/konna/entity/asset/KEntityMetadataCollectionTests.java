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

package io.github.darthakiranihil.konna.entity.asset;

import io.github.darthakiranihil.konna.entity.KEntityMetadata;
import io.github.darthakiranihil.konna.entity.type.KEntityMetadataTypedef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KEntityMetadataCollectionTests extends KAssetCollectionTestClass {

    @Test
    public void testGetAssetsSuccess() {

        KEntityMetadataCollection emdc = new KEntityMetadataCollection(
            this.assetLoader
        );

        KEntityMetadata emd1 = emdc.getAsset("Typpi1");
        KEntityMetadata emd2 = emdc.getAsset("Typpi2");
        KEntityMetadata emd3 = emdc.getAsset("Typpi3");
        KEntityMetadata emd4 = emdc.getAsset("Typpi4");
        KEntityMetadata emd4same = emdc.getAsset("Typpi4");

        Assertions.assertEquals("Typpi1", emd1.typeName());
        Assertions.assertEquals("Typpi2", emd2.typeName());
        Assertions.assertEquals("Typpi3", emd3.typeName());
        Assertions.assertEquals("Typpi4", emd4.typeName());

        Assertions.assertEquals(0, emd1.dataComponents().length);
        Assertions.assertEquals(0, emd1.dataExtensionList().length);

        Assertions.assertEquals(1, emd2.dataComponents().length);
        Assertions.assertEquals(0, emd2.dataExtensionList().length);

        Assertions.assertEquals(1, emd3.dataComponents().length);
        Assertions.assertEquals(1, emd3.dataExtensionList().length);

        Assertions.assertEquals(0, emd4.dataComponents().length);
        Assertions.assertEquals(1, emd4.dataExtensionList().length);
        Assertions.assertEquals(emd4, emd4same);

    }

}
