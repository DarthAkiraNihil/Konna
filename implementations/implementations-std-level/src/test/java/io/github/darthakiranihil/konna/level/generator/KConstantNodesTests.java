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

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.except.KInvalidArgumentException;
import io.github.darthakiranihil.konna.core.io.KAssetDefinition;
import io.github.darthakiranihil.konna.core.io.KMapAssetDefinition;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.generator.constant.*;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Random;

public class KConstantNodesTests extends KStandardTestClass {

    @Test
    public void testIntConstantNodeProcessSuccess() {

        KIntConstantNode node = new KIntConstantNode(1);
        KUniversalMap result = node.process(new KUniversalMap(), new Random());
        Assertions.assertTrue(result.containsKey("value"));
        Assertions.assertEquals(1, result.get("value"));

    }

    @Test
    public void testIntConstantNodeConstructionFailed() {

        Assertions.assertThrows(
            KInvalidArgumentException.class,
            () -> new KIntConstantNode("123")
        );

    }

    @Test
    public void testFloatConstantNodeProcessSuccess() {

        KFloatConstantNode node = new KFloatConstantNode(1.0f);
        KUniversalMap result = node.process(new KUniversalMap(), new Random());
        Assertions.assertTrue(result.containsKey("value"));
        Assertions.assertEquals(1.0f, result.get("value"));

    }

    @Test
    public void testFloatConstantNodeConstructionFailed() {

        Assertions.assertThrows(
            KInvalidArgumentException.class,
            () -> new KFloatConstantNode("123")
        );

    }

    @Test
    public void testBooleanConstantNodeProcessSuccess() {

        KBooleanConstantNode node = new KBooleanConstantNode(true);
        KUniversalMap result = node.process(new KUniversalMap(), new Random());
        Assertions.assertTrue(result.containsKey("value"));
        Assertions.assertEquals(true, result.get("value"));

    }

    @Test
    public void testBooleanConstantNodeConstructionFailed() {

        Assertions.assertThrows(
            KInvalidArgumentException.class,
            () -> new KBooleanConstantNode("123")
        );

    }

    @Test
    public void testStringConstantNodeProcessSuccess() {

        KStringConstantNode node = new KStringConstantNode("123");
        KUniversalMap result = node.process(new KUniversalMap(), new Random());
        Assertions.assertTrue(result.containsKey("value"));
        Assertions.assertEquals("123", result.get("value"));

    }

    @Test
    public void testStringConstantNodeConstructionFailed() {

        Assertions.assertThrows(
            KInvalidArgumentException.class,
            () -> new KStringConstantNode(1)
        );

    }

    @Test
    public void testVector2iConstantNodeProcessSuccess() {

        KAssetDefinition def = new KMapAssetDefinition(
            Map.of("x", 1, "y", 1)
        );
        KVector2iConstantNode node = new KVector2iConstantNode(def);
        KUniversalMap result = node.process(new KUniversalMap(), new Random());
        Assertions.assertTrue(result.containsKey("value"));
        Assertions.assertEquals(new KVector2i(1, 1), result.get("value"));

    }

    @Test
    public void testVector2iConstantNodeConstructionFailedBecauseOfArgType() {
        Assertions.assertThrows(
            KInvalidArgumentException.class,
            () -> new KVector2iConstantNode(1)
        );
    }

    @Test
    public void testVector2iConstantNodeNodeConstructionFailedBecauseOfInvalidDefinition() {
        Assertions.assertThrows(
            KInvalidArgumentException.class,
            () -> new KVector2iConstantNode(new KMapAssetDefinition(Map.of()))
        );
        Assertions.assertThrows(
            KInvalidArgumentException.class,
            () -> new KVector2iConstantNode(new KMapAssetDefinition(Map.of("x", 1)))
        );
        Assertions.assertThrows(
            KInvalidArgumentException.class,
            () -> new KVector2iConstantNode(new KMapAssetDefinition(Map.of("y", 1)))
        );
    }

    @Test
    public void testSizeConstantNodeProcessSuccess() {

        KAssetDefinition def = new KMapAssetDefinition(
            Map.of("width", 1, "height", 1)
        );
        KSizeConstantNode node = new KSizeConstantNode(def);
        KUniversalMap result = node.process(new KUniversalMap(), new Random());
        Assertions.assertTrue(result.containsKey("value"));
        Assertions.assertEquals(new KSize(1, 1), result.get("value"));

    }

    @Test
    public void testSizeConstantNodeConstructionFailedBecauseOfArgType() {
        Assertions.assertThrows(
            KInvalidArgumentException.class,
            () -> new KSizeConstantNode(1)
        );
    }

    @Test
    public void testSizeConstantNodeNodeConstructionFailedBecauseOfInvalidDefinition() {
        Assertions.assertThrows(
            KInvalidArgumentException.class,
            () -> new KSizeConstantNode(new KMapAssetDefinition(Map.of()))
        );
        Assertions.assertThrows(
            KInvalidArgumentException.class,
            () -> new KSizeConstantNode(new KMapAssetDefinition(Map.of("width", 1)))
        );
        Assertions.assertThrows(
            KInvalidArgumentException.class,
            () -> new KSizeConstantNode(new KMapAssetDefinition(Map.of("height", 1)))
        );
    }
}
