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

package io.github.darthakiranihil.konna.core.data.graph;

import io.github.darthakiranihil.konna.core.struct.KPair;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;

public class KHashMapGraph<IDX, COL, W> implements KGraph<IDX, COL, W> {

    private final class HashMapGraphNode implements KGraph.Node<IDX, COL, W> {

        private final IDX index;
        private final COL color;
        private final Set<KPair<W, Node<IDX, COL, W>>> adjacent;

        private @Nullable IDX parent;

        HashMapGraphNode(IDX index, COL color) {
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
        public Set<KPair<W, Node<IDX, COL, W>>> adjacent() {
            return this.adjacent;
        }

    }

    private final Map<IDX, HashMapGraphNode> nodes;

    public KHashMapGraph() {
        this.nodes = new HashMap<>();
    }

    @Override
    public void add(IDX index, COL color) {
        this.nodes.put(
            index,
            new HashMapGraphNode(
                index,
                color
            )
        );
    }

    @Override
    public @Nullable Node<IDX, COL, W> get(IDX index) {
        return this.nodes.get(index);
    }

    @Override
    public void connect(IDX src, IDX dst, W weight) {
        var srcNode = Objects.requireNonNull(this.nodes.get(src));
        var dstNode = Objects.requireNonNull(this.nodes.get(dst));

        srcNode.adjacent.add(new KPair<>(weight, dstNode));
    }

    @Override
    public void biConnect(IDX src, IDX dst, W weight) {
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
    public List<KPair<IDX, W>> getPath(IDX src, IDX dst) {
        return List.of();
    }

    @Override
    public @NonNull Iterator<Node<IDX, COL, W>> iterator() {
        return this
            .nodes
            .values()
            .stream()
            .map(x -> (Node<IDX, COL, W>) x)
            .iterator();
    }

    @Override
    public void forEach(Consumer<? super Node<IDX, COL, W>> action) {
        this.nodes.values().forEach(action);
    }

    @Override
    public Spliterator<Node<IDX, COL, W>> spliterator() {
        return this
            .nodes
            .values()
            .stream()
            .map(x -> (Node<IDX, COL, W>) x)
            .spliterator();
    }
}
