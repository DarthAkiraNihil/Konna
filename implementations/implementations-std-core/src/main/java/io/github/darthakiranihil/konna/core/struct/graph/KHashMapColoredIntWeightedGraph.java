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

import io.github.darthakiranihil.konna.core.struct.KPair;
import org.jspecify.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

/**
 * Implementation of {@link KColoredIntWeightedGraph} that uses HashMap for storing graph nodes.
 * (uses Dijkstra's algorithm to calculate path between two nodes).
 * @param <IDX> Index type to reference a graph node
 * @param <COL> Type of node's color
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public class KHashMapColoredIntWeightedGraph<IDX, COL>
    implements KColoredIntWeightedGraph<IDX, COL> {

    private final class HashMapGraphNode implements KColoredIntWeightedGraph.Node<IDX, COL> {

        private final IDX index;
        private final COL color;
        private final Set<KPair<Integer, Node<IDX, COL>>> adjacent;

        HashMapGraphNode(final IDX index, final COL color) {
            this.index = index;
            this.color = color;
            this.adjacent = new HashSet<>();
        }

        @Override
        public IDX index() {
            return this.index;
        }

        @Override
        public COL color() {
            return this.color;
        }

        @Override
        public Set<KPair<Integer, Node<IDX, COL>>> adjacent() {
            return this.adjacent;
        }

    }

    private static final class DijkstraNode<IDX, COL> {

        private final Node<IDX, COL> node;
        private int pathWeight;
        private boolean visited;
        private @Nullable DijkstraNode<IDX, COL> parent;

        DijkstraNode(final Node<IDX, COL> node) {
            this.node = node;
            this.pathWeight = Integer.MAX_VALUE;
            this.parent = null;
            this.visited = false;
        }

    }

    private final Map<IDX, HashMapGraphNode> nodes;

    /**
     * Constructs an empty graph.
     */
    public KHashMapColoredIntWeightedGraph() {
        this.nodes = new HashMap<>();
    }

    @Override
    public void add(final IDX index, final COL color) {
        this.nodes.put(
            index,
            new HashMapGraphNode(
                index,
                color
            )
        );
    }

    @Override
    public @Nullable Node<IDX, COL> get(final IDX index) {
        return this.nodes.get(index);
    }

    @Override
    public void connect(final IDX src, final IDX dst, int weight) {
        var srcNode = this.nodes.get(src);
        var dstNode = this.nodes.get(dst);
        if (srcNode == null || dstNode == null) {
            return;
        }

        srcNode.adjacent.add(new KPair<>(weight, dstNode));
    }

    @Override
    public void biConnect(final IDX src, final IDX dst, int weight) {
        this.connect(src, dst, weight);
        this.connect(dst, src, weight);
    }

    @Override
    public Set<IDX> getNodeIndices() {
        return this.nodes.keySet();
    }

    @Override
    public void clear() {
        this.nodes.clear();
    }

    @Override
    public List<IDX> getPath(final IDX src, final IDX dst) {

        Map<IDX, DijkstraNode<IDX, COL>> dijkstraNodes = new HashMap<>();
        for (var e: this.nodes.entrySet()) {
            dijkstraNodes.put(
                e.getKey(),
                new DijkstraNode<>(e.getValue())
            );
        }

        var srcNode = dijkstraNodes.get(src);
        if (srcNode == null) {
            return List.of();
        }
        srcNode.pathWeight = 0;

        Queue<DijkstraNode<IDX, COL>> processingQueue = new LinkedList<>();
        processingQueue.add(srcNode);

        while (!processingQueue.isEmpty()) {

            var dijkstraNode = processingQueue.poll();
            var adjacentNodes = dijkstraNode.node.adjacent();
            for (var adjacentNode: adjacentNodes) {

                var realNode = adjacentNode.second();
                IDX adjIdx = realNode.index();

                var dijkstraAdjNode = dijkstraNodes.get(adjIdx);
                if (dijkstraNode.pathWeight + adjacentNode.first() < dijkstraAdjNode.pathWeight) {
                    dijkstraAdjNode.pathWeight = dijkstraNode.pathWeight + adjacentNode.first();
                    dijkstraAdjNode.parent = dijkstraNode;
                }

            }
            dijkstraNode.visited = true;

            processingQueue.addAll(
                adjacentNodes
                    .stream()
                    .map(x -> dijkstraNodes.get(x.second().index()))
                    .filter(x -> !x.visited)
                    .sorted(Comparator.comparing(x -> x.pathWeight))
                    .toList()
            );
        }

        var dstNode = dijkstraNodes.get(dst);
        if (dstNode == null || dstNode.parent == null) {
            return List.of();
        }

        List<IDX> path = new LinkedList<>();
        while (dstNode != null) {
            path.addFirst(dstNode.node.index());
            dstNode = dstNode.parent;
        }
        return Collections.unmodifiableList(path);
    }

    @Override
    public Iterator<Node<IDX, COL>> iterator() {
        return this
            .nodes
            .values()
            .stream()
            .map(x -> (Node<IDX, COL>) x)
            .iterator();
    }

    @Override
    public void forEach(final Consumer<? super Node<IDX, COL>> action) {
        this.nodes.values().forEach(action);
    }

    @Override
    public Spliterator<Node<IDX, COL>> spliterator() {
        return this
            .nodes
            .values()
            .stream()
            .map(x -> (Node<IDX, COL>) x)
            .spliterator();
    }

}
