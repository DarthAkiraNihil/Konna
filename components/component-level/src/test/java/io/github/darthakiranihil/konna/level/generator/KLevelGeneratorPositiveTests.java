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
import io.github.darthakiranihil.konna.level.KLevel;
import io.github.darthakiranihil.konna.level.generator.constant.KStringConstantNode;
import io.github.darthakiranihil.konna.level.impl.PassthroughNode;
import io.github.darthakiranihil.konna.level.impl.TestLevelNode;
import io.github.darthakiranihil.konna.level.impl.TestUnvalidatedInputLevelNode;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

@NullMarked
public class KLevelGeneratorPositiveTests extends KStandardTestClass {

    @Test
    public void testGenerateValidLevel() {

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
            Map.of("assetIdConst", "valid")
        );

        KLevelGenerator generator = new KLevelGenerator(
            "valid_generator",
            KStandardTestClass.context,
            metadata
        );


        KLevel generated = generator.generate(123456L);
        Assertions.assertArrayEquals(
            new String[] {
                "mf2", "mf1"
            },
            generated.getSectorNames()
        );

    }

    @Test
    public void testGenerateValidLevelButOneNodeIsUnvalidatedByInput() {

        KLevelGeneratorMetadata metadata = new KLevelGeneratorMetadata(
            Map.of(
                "assetIdConst", KStringConstantNode.class,
                "levelNode", TestUnvalidatedInputLevelNode.class
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


        KLevel generated = generator.generate(123456L);
        Assertions.assertArrayEquals(
            new String[] {
                "mf2", "mf1"
            },
            generated.getSectorNames()
        );

    }

    @Test
    public void testGenerateValidLevelWithMoreThanOneConnection() {

        KLevelGeneratorMetadata metadata = new KLevelGeneratorMetadata(
            Map.of(
                "assetIdConst", KStringConstantNode.class,
                "passthru", PassthroughNode.class,
                "levelNode", TestUnvalidatedInputLevelNode.class
            ),
            Set.of(
                new KPair<>(
                    new KLevelGeneratorMetadata.ConnectionJoint(
                        "passthru",
                        "value"
                    ),
                    new KLevelGeneratorMetadata.ConnectionJoint(
                        "levelNode",
                        "asset_id"
                    )
                ),
                new KPair<>(
                    new KLevelGeneratorMetadata.ConnectionJoint(
                        "assetIdConst",
                        "value"
                    ),
                    new KLevelGeneratorMetadata.ConnectionJoint(
                        "passthru",
                        "value"
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


        KLevel generated = generator.generate(123456L);
        Assertions.assertArrayEquals(
            new String[] {
                "mf2", "mf1"
            },
            generated.getSectorNames()
        );

    }
}
