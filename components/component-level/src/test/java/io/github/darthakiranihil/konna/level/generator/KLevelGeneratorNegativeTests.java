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

package io.github.darthakiranihil.konna.level.generator;

import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.level.except.KGenerationException;
import io.github.darthakiranihil.konna.level.impl.TestLevelNode;
import io.github.darthakiranihil.konna.level.impl.TestUnvalidatedOutputConstantNode;
import io.github.darthakiranihil.konna.level.impl.TestUnvalidatedOutputNode;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

public class KLevelGeneratorNegativeTests extends KStandardTestClass {

    @Test
    public void testGenerateLevelWithUnknownConstant() {

        KLevelGeneratorMetadata metadata = new KLevelGeneratorMetadata(
            Map.of(
                "assetIdConst", KStringConstantNode.class,
                "levelNode", TestLevelNode.class
            ),
            Set.of(
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
            ),
            Map.of("assetIdConst1", "valid")
        );

        KLevelGenerator generator = new KLevelGenerator(
            "valid_generator",
            KStandardTestClass.context,
            metadata
        );

        Assertions.assertThrows(
            KGenerationException.class,
            () -> generator.generate(456789L)
        );

    }

    @Test
    public void testGenerateLevelWithMultipleRootNodes() {

        KLevelGeneratorMetadata metadata = new KLevelGeneratorMetadata(
            Map.of(
                "assetIdConst", KStringConstantNode.class,
                "levelNode", TestLevelNode.class,
                "levelNode1", TestLevelNode.class
            ),
            Set.of(
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
            ),
            Map.of("assetIdConst", "valid")
        );

        KLevelGenerator generator = new KLevelGenerator(
            "valid_generator",
            KStandardTestClass.context,
            metadata
        );

        Assertions.assertThrows(
            KGenerationException.class,
            () -> generator.generate(456789L)
        );

    }

    @Test
    public void testGenerateLevelButParamIsUnknown() {

        KLevelGeneratorMetadata metadata = new KLevelGeneratorMetadata(
            Map.of(
                "assetIdConst", KStringConstantNode.class,
                "levelNode", TestLevelNode.class
            ),
            Set.of(
                new KPair<>(
                    new KLevelGeneratorMetadata.ConnectionJoint(
                        "assetIdConst",
                        "value1"
                    ),
                    new KLevelGeneratorMetadata.ConnectionJoint(
                        "levelNode",
                        "asset_id"
                    )
                )
            ),
            Map.of("assetIdConst", "valid")
        );

        KLevelGenerator generator = new KLevelGenerator(
            "valid_generator",
            KStandardTestClass.context,
            metadata
        );

        Assertions.assertThrows(
            KGenerationException.class,
            () -> generator.generate(456789L)
        );

    }

    @Test
    public void testGenerateLevelButFinalNodeIsInvalid() {

        KLevelGeneratorMetadata metadata = new KLevelGeneratorMetadata(
            Map.of(
                "assetIdConst", KStringConstantNode.class
            ),
            Set.of(),
            Map.of("assetIdConst", "valid")
        );

        KLevelGenerator generator = new KLevelGenerator(
            "valid_generator",
            KStandardTestClass.context,
            metadata
        );

        Assertions.assertThrows(
            KGenerationException.class,
            () -> generator.generate(456789L)
        );

    }

    @Test
    public void testGenerateLevelButOneNodeDoesNotSpecifyOutputParams() {

        KLevelGeneratorMetadata metadata = new KLevelGeneratorMetadata(
            Map.of(
                "invalidNode", TestUnvalidatedOutputNode.class
            ),
            Set.of(),
            Map.of()
        );

        KLevelGenerator generator = new KLevelGenerator(
            "valid_generator",
            KStandardTestClass.context,
            metadata
        );

        Assertions.assertThrows(
            KGenerationException.class,
            () -> generator.generate(456789L)
        );

    }

    @Test
    public void testGenerateLevelButOneConstantNodeDoesNotSpecifyOutputParams() {

        KLevelGeneratorMetadata metadata = new KLevelGeneratorMetadata(
            Map.of(
                "invalidNode", TestUnvalidatedOutputConstantNode.class
            ),
            Set.of(),
            Map.of("invalidNode", "1234")
        );

        KLevelGenerator generator = new KLevelGenerator(
            "valid_generator",
            KStandardTestClass.context,
            metadata
        );

        Assertions.assertThrows(
            KGenerationException.class,
            () -> generator.generate(456789L)
        );
    }
}
