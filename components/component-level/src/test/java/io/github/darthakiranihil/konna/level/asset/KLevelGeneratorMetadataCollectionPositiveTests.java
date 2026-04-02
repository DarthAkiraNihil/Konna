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

package io.github.darthakiranihil.konna.level.asset;

import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.level.generator.KLevelGeneratorMetadata;
import io.github.darthakiranihil.konna.level.generator.constant.KStringConstantNode;
import io.github.darthakiranihil.konna.level.impl.TestLevelNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KLevelGeneratorMetadataCollectionPositiveTests extends KAssetCollectionTestClass {

    @Test
    public void testLoadValidMetadata() {

        KLevelGeneratorMetadataCollection collection = new KLevelGeneratorMetadataCollection(
            this.assetLoader
        );

        KLevelGeneratorMetadata metadata = collection.getAsset("valid");

        var nodes = metadata.nodes();
        var connections = metadata.connections();
        var constants = metadata.constants();

        Assertions.assertEquals(2, nodes.size());
        Assertions.assertEquals(1, connections.size());
        Assertions.assertEquals(1, constants.size());

        Assertions.assertTrue(nodes.containsKey("assetIdConst"));
        Assertions.assertTrue(nodes.containsKey("levelNode"));
        Assertions.assertEquals(KStringConstantNode.class, nodes.get("assetIdConst"));
        Assertions.assertEquals(TestLevelNode.class, nodes.get("levelNode"));

        Assertions.assertTrue(
            connections.contains(
                new KPair<>(
                    new KLevelGeneratorMetadata.ConnectionJoint(
                        "assetIdConst",
                        "value"
                    ),
                    new KLevelGeneratorMetadata.ConnectionJoint(
                        "levelNode",
                        "asset_id"
                    )
                )
            )
        );
        Assertions.assertTrue(constants.containsKey("assetIdConst"));
        Assertions.assertEquals("valid", constants.get("assetIdConst"));

    }
}
