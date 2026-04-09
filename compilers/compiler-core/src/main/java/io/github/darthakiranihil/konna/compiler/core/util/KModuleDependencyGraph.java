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

package io.github.darthakiranihil.konna.compiler.core.util;

import org.jspecify.annotations.Nullable;

import java.util.*;


public final class KModuleDependencyGraph {

    public static final class Node {

        private final String index;
        private final Set<Node> adjacent;

        Node(final String index) {
            this.index = index;
            this.adjacent = new HashSet<>();
        }

        public String index() {
            return this.index;
        }

        public Set<Node> adjacent() {
            return this.adjacent;
        }

    }

    private final Map<String, Node> nodes;

    /**
     * Constructs an empty graph.
     */
    public KModuleDependencyGraph() {
        this.nodes = new HashMap<>();
    }

    public Set<String> getNodeIndices() {
        return this.nodes.keySet();
    }

    public void add(final String index) {
        this.nodes.put(index, new Node(index));
    }

    public @Nullable Node get(final String index) {
        return this.nodes.get(index);
    }

    public void connect(final String src, final String dst) {
        var srcNode = this.nodes.get(src);
        var dstNode = this.nodes.get(dst);
        if (srcNode == null || dstNode == null) {
            return;
        }

        srcNode.adjacent.add(dstNode);
    }

    public boolean has(final String index) {
        return this.nodes.containsKey(index);
    }

}
