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
import io.github.darthakiranihil.konna.core.struct.graph.KIntWeightedGraph;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

public class KHashMapIntWeightedGraph<IDX> implements KIntWeightedGraph<IDX> {

    private final class HashMapGraphNode implements KIntWeightedGraph.Node<IDX> {

        private final IDX index;
        private final Set<KPair<Integer, KIntWeightedGraph.Node<IDX>>> adjacent;

        HashMapGraphNode(IDX index) {
            this.index = index;
            this.adjacent = new HashSet<>();
        }

        @Override
        public IDX index() {
            return this.index;
        }
        
        @Override
        public Set<KPair<Integer, KIntWeightedGraph.Node<IDX>>> adjacent() {
            return this.adjacent;
        }

    }

    private static final class DijkstraNode<IDX> {

        private final KIntWeightedGraph.Node<IDX> node;
        private int pathWeight;
        private boolean visited;
        private @Nullable DijkstraNode<IDX> parent;

        DijkstraNode(final KIntWeightedGraph.Node<IDX> node) {
            this.node = node;
            this.pathWeight = Integer.MAX_VALUE;
            this.parent = null;
            this.visited = false;
        }

    }

    private final Map<IDX, HashMapGraphNode> nodes;

    public KHashMapIntWeightedGraph() {
        this.nodes = new HashMap<>();
    }

    @Override
    public void add(IDX index) {
        this.nodes.put(
            index,
            new HashMapGraphNode(index)
        );
    }

    @Override
    public @Nullable Node<IDX> get(IDX index) {
        return this.nodes.get(index);
    }

    @Override
    public void connect(IDX src, IDX dst, int weight) {
        var srcNode = this.nodes.get(src);
        var dstNode = this.nodes.get(dst);
        if (srcNode == null || dstNode == null) {
            return;
        }

        srcNode.adjacent.add(new KPair<>(weight, dstNode));
    }

    @Override
    public void biConnect(IDX src, IDX dst, int weight) {
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
    public List<IDX> getPath(IDX src, IDX dst) {

        Map<IDX, DijkstraNode<IDX>> dijkstraNodes = new HashMap<>();
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

        Queue<DijkstraNode<IDX>> processingQueue = new LinkedList<>();
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
    public @NonNull Iterator<KIntWeightedGraph.Node<IDX>> iterator() {
        return this
            .nodes
            .values()
            .stream()
            .map(x -> (KIntWeightedGraph.Node<IDX>) x)
            .iterator();
    }

    @Override
    public void forEach(Consumer<? super KIntWeightedGraph.Node<IDX>> action) {
        this.nodes.values().forEach(action);
    }

    @Override
    public Spliterator<KIntWeightedGraph.Node<IDX>> spliterator() {
        return this
            .nodes
            .values()
            .stream()
            .map(x -> (KIntWeightedGraph.Node<IDX>) x)
            .spliterator();
    }
    
}
