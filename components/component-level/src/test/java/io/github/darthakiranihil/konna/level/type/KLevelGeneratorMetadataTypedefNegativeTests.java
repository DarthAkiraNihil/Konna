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

package io.github.darthakiranihil.konna.level.type;

import io.github.darthakiranihil.konna.core.io.KAssetDefinition;
import io.github.darthakiranihil.konna.core.io.KMapAssetDefinition;
import io.github.darthakiranihil.konna.core.io.except.KAssetDefinitionError;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class KLevelGeneratorMetadataTypedefNegativeTests extends KStandardTestClass {

    @Test
    public void testValidateWithInvalidNodeClass() {
        KLevelGeneratorMetadataTypedef typedef = new KLevelGeneratorMetadataTypedef();
        Map<String, Object> source = new HashMap<>();
        source.put("nodes", new KMapAssetDefinition(Map.of("n1", "io.github.darthakiranihil.konna.core.Konna")));

        Assertions.assertThrows(
            KAssetDefinitionError.class,
            () -> typedef.getRule().validate(
                new KMapAssetDefinition(source)
            )
        );
    }

    @Test
    public void testValidateWithInvalidConnectionSource() {
        KLevelGeneratorMetadataTypedef typedef = new KLevelGeneratorMetadataTypedef();
        Map<String, Object> source = new HashMap<>();
        source.put("nodes", new KMapAssetDefinition(
            Map.of(
                "n1", "io.github.darthakiranihil.konna.level.impl.TestLevelNode",
                "n2", "io.github.darthakiranihil.konna.level.impl.TestLevelNode"
            )));
        source.put("connections", new KAssetDefinition[] {
            new KMapAssetDefinition(
                Map.of(
                    "from", new KMapAssetDefinition(Map.of("node", "n3", "param", "p1")),
                    "to", new KMapAssetDefinition(Map.of("node", "n2", "param", "p1"))
                )
            )
        });

        Assertions.assertThrows(
            KAssetDefinitionError.class,
            () -> typedef.getRule().validate(
                new KMapAssetDefinition(source)
            )
        );
    }

    @Test
    public void testValidateWithInvalidConnectionDestination() {

        KLevelGeneratorMetadataTypedef typedef = new KLevelGeneratorMetadataTypedef();
        Map<String, Object> source = new HashMap<>();
        source.put("nodes", new KMapAssetDefinition(
            Map.of(
                "n1", "io.github.darthakiranihil.konna.level.impl.TestLevelNode",
                "n2", "io.github.darthakiranihil.konna.level.impl.TestLevelNode"
            )));
        source.put("connections", new KAssetDefinition[] {
            new KMapAssetDefinition(
                Map.of(
                    "from", new KMapAssetDefinition(Map.of("node", "n2", "param", "p1")),
                    "to", new KMapAssetDefinition(Map.of("node", "n3", "param", "p1"))
                )
            )
        });

        Assertions.assertThrows(
            KAssetDefinitionError.class,
            () -> typedef.getRule().validate(
                new KMapAssetDefinition(source)
            )
        );

    }

    @Test
    public void testValidateWithUnknownConstant() {

        KLevelGeneratorMetadataTypedef typedef = new KLevelGeneratorMetadataTypedef();
        Map<String, Object> source = new HashMap<>();
        source.put("nodes", new KMapAssetDefinition(
            Map.of(
                "n1", "io.github.darthakiranihil.konna.level.impl.TestLevelNode",
                "n2", "io.github.darthakiranihil.konna.level.impl.TestLevelNode",
                "nc1", "io.github.darthakiranihil.konna.level.generator.constant.KStringConstantNode"
            )));
        source.put("connections", new KAssetDefinition[] {
            new KMapAssetDefinition(
                Map.of(
                    "from", new KMapAssetDefinition(Map.of("node", "n2", "param", "p1")),
                    "to", new KMapAssetDefinition(Map.of("node", "n1", "param", "p1"))
                )
            )
        });
        source.put("constants", new KMapAssetDefinition(
            Map.of("coco2", "123")
        ));

        Assertions.assertThrows(
            KAssetDefinitionError.class,
            () -> typedef.getRule().validate(
                new KMapAssetDefinition(source)
            )
        );

    }
}
