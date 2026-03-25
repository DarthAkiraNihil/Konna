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

package io.github.darthakiranihil.konna.core.struct.graph;

import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KHashMapColoredIntWeightMapPositiveTests extends KStandardTestClass {

    @Test
    public void testAddNode() {

        KColoredIntWeightedGraph<String, Integer> g = new KHashMapColoredIntWeightedGraph<>();
        g.add("1", 123);
        var node = g.get("1");

        Assertions.assertNotNull(node);
        Assertions.assertEquals("1", node.index());
        Assertions.assertEquals(123, node.color());
        Assertions.assertEquals(0, node.adjacent().size());

    }

    @Test
    public void testGetNonExistentNode() {

        KColoredIntWeightedGraph<String, Integer> g = new KHashMapColoredIntWeightedGraph<>();
        var node = g.get("1");
        Assertions.assertNull(node);

    }

    @Test
    public void testConnectNodes() {

        KColoredIntWeightedGraph<String, Integer> g = new KHashMapColoredIntWeightedGraph<>();
        g.add("1", 1);
        g.add("2", 2);

        g.connect("1", "2", 1);

        var node = g.get("1");
        Assertions.assertNotNull(node);
        Assertions.assertEquals("1", node.index());
        Assertions.assertEquals(1, node.color());
        Assertions.assertEquals(1, node.adjacent().size());
        Assertions.assertTrue(node.adjacent().stream().anyMatch(x -> x.first() == 1));
        Assertions.assertTrue(node.adjacent().stream().anyMatch(x -> x.second().index().equals("2")));

    }

    @Test
    public void testBiConnectNodes() {

        KColoredIntWeightedGraph<String, Integer> g = new KHashMapColoredIntWeightedGraph<>();
        g.add("1", 1);
        g.add("2", 2);

        g.biConnect("1", "2", 1);

        Assertions.assertTrue(g.has("1"));
        Assertions.assertTrue(g.has("2"));

        var node = g.get("1");
        var node2 = g.get("2");

        Assertions.assertNotNull(node);
        Assertions.assertNotNull(node2);

        Assertions.assertEquals("1", node.index());
        Assertions.assertEquals(1, node.color());
        Assertions.assertEquals(1, node.adjacent().size());

        Assertions.assertEquals("2", node2.index());
        Assertions.assertEquals(2, node2.color());
        Assertions.assertEquals(1, node2.adjacent().size());

        Assertions.assertTrue(node.adjacent().stream().anyMatch(x -> x.first() == 1));
        Assertions.assertTrue(node.adjacent().stream().anyMatch(x -> x.second().index().equals("2")));

        Assertions.assertTrue(node2.adjacent().stream().anyMatch(x -> x.first() == 1));
        Assertions.assertTrue(node2.adjacent().stream().anyMatch(x -> x.second().index().equals("1")));

    }

    @Test
    public void testFindPathBetweenReachable() {

        KColoredIntWeightedGraph<String, Integer> g = new KHashMapColoredIntWeightedGraph<>();
        g.add("1", 1);
        g.add("2", 1);
        g.add("3", 1);
        g.add("4", 1);

        g.connect("1", "2", 1);
        g.connect("2", "3", 1);
        g.connect("3", "4", 1);
        g.connect("3", "1", 1);

        var p = g.getPath("1", "4");
        Assertions.assertEquals(4, p.size());
        Assertions.assertEquals("1", p.get(0));
        Assertions.assertEquals("2", p.get(1));
        Assertions.assertEquals("3", p.get(2));
        Assertions.assertEquals("4", p.get(3));
    }

    @Test
    public void testFindPathBetweenUnreachable() {
        KColoredIntWeightedGraph<String, Integer> g = new KHashMapColoredIntWeightedGraph<>();
        g.add("1", 1);
        g.add("2", 1);
        g.add("3", 1);
        g.add("4", 1);

        g.connect("1", "2", 1);
        g.connect("2", "3", 1);
        g.connect("3", "4", 1);
        g.connect("3", "1", 1);

        var p = g.getPath("4", "1");
        Assertions.assertEquals(0, p.size());
    }

    @Test
    public void testClearGraph() {
        KColoredIntWeightedGraph<String, Integer> g = new KHashMapColoredIntWeightedGraph<>();
        g.add("1", 1);
        Assertions.assertTrue(g.getNodeIndices().contains("1"));
        g.clear();
        Assertions.assertFalse(g.getNodeIndices().contains("1"));
    }

    @Test
    public void testIterableMethods() {

        KColoredIntWeightedGraph<String, Integer> g = new KHashMapColoredIntWeightedGraph<>();
        g.add("1", 1);
        for (var n: g) {
            Assertions.assertEquals("1", n.index());
        }
        g.spliterator(); // IDK how to test it
        g.iterator(); // IDK how to test it
        g.forEach(x -> Assertions.assertEquals("1", x.index()));

    }

    @Test
    public void testConnectNonExistentNodes() {
        KColoredIntWeightedGraph<String, Integer> g = new KHashMapColoredIntWeightedGraph<>();
        g.add("1", 1);
        g.add("2", 1);

        var n1 = g.get("1");
        Assertions.assertNotNull(n1);

        Assertions.assertDoesNotThrow(() -> g.connect("1", "3", 0));
        Assertions.assertEquals(0, n1.adjacent().size());

        Assertions.assertDoesNotThrow(() -> g.connect("3", "2", 0));
        Assertions.assertDoesNotThrow(() -> g.connect("3", "4", 0));
    }

    @Test
    public void testFindPathToNonExistent() {
        KColoredIntWeightedGraph<String, Integer> g = new KHashMapColoredIntWeightedGraph<>();
        g.add("1", 1);
        g.add("2", 1);
        g.add("3", 1);
        g.add("4", 1);

        g.connect("1", "2", 1);
        g.connect("2", "3", 1);
        g.connect("3", "4", 1);
        g.connect("3", "1", 1);

        var p = g.getPath("1", "5");
        Assertions.assertEquals(0, p.size());
    }

    @Test
    public void testFindPathFromNonExistent() {
        KColoredIntWeightedGraph<String, Integer> g = new KHashMapColoredIntWeightedGraph<>();
        g.add("1", 1);
        g.add("2", 1);
        g.add("3", 1);
        g.add("4", 1);

        g.connect("1", "2", 1);
        g.connect("2", "3", 1);
        g.connect("3", "4", 1);
        g.connect("3", "1", 1);

        var p = g.getPath("5", "4");
        Assertions.assertEquals(0, p.size());
    }

    @Test
    public void testFindPathToSelfAndIsSelf() {

        KColoredIntWeightedGraph<String, Integer> g = new KHashMapColoredIntWeightedGraph<>();
        g.add("1", 1);
        g.add("2", 1);
        g.add("3", 1);
        g.add("4", 1);

        g.connect("1", "2", 1);
        g.connect("2", "3", 1);
        g.connect("3", "4", 1);
        g.connect("3", "1", 1);

        var p = g.getPath("1", "1");
        Assertions.assertEquals(1, p.size());

    }
}
