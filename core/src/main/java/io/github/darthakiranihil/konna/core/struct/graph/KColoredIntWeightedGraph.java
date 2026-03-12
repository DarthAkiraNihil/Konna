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

import java.util.List;
import java.util.Set;

/**
 * Interface for a weighted directed graph, where all node has its own color.
 * @param <IDX> Index type to reference a graph node
 * @param <COL> Type of node's color
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public interface KColoredIntWeightedGraph<IDX, COL>
    extends Iterable<KColoredIntWeightedGraph.Node<IDX, COL>> {

    /**
     * Interface for a graph node container.
     * @param <IDX> Index type of graph node
     * @param <COL> Type of node's color
     */
    interface Node<IDX, COL> {
        /**
         * @return Index of this node
         */
        IDX index();

        /**
         * @return Color of this node
         */
        COL color();

        /**
         * @return Set of nodes that are adjacent to this node (with connection weights)
         */
        Set<KPair<Integer, Node<IDX, COL>>> adjacent();
    }

    /**
     * @return Set of node indices inside this graph
     */
    Set<IDX> getNodeIndices();

    /**
     * Removes all nodes from this graph.
     */
    void clear();

    /**
     * Adds a node to this graph.
     * @param index Node index
     * @param color Node color
     */
    void add(IDX index, COL color);

    /**
     * @param index Index of node
     * @return Graph node with specified index
     */
    @Nullable Node<IDX, COL> get(IDX index);

    /**
     * @param index Index of tested node
     * @return Whether this graph has a node with specified index or not
     */
    boolean has(IDX index);

    /**
     * Connects a source node to the destination with specific weight.
     * If one or all of connected nodes are not presented in the graph, nothing happens.
     * @param src Source node index
     * @param dst Destination node index
     * @param weight Connection weight
     */
    void connect(IDX src, IDX dst, int weight);

    /**
     * Connects a source node to the destination with specific weight, and vise versa
     * (bidirectional connection)
     * If one or all of connected nodes are not presented in the graph, nothing happens.
     * @param src Source node index
     * @param dst Destination node index
     * @param weight Connection weight
     */
    void biConnect(IDX src, IDX dst, int weight);

    /**
     * Returns path between two nodes.
     * If one or all of connected nodes are not presented in the graph, an empty path is returned.
     * @param src Source node index
     * @param dst Destination node index
     * @return Path between specified nodes
     */
    default List<IDX> getPath(final IDX src, final IDX dst) {
        return this.getPath(src, dst, false);
    }

    /**
     * Returns path between two nodes.
     * If one or all of connected nodes are not presented in the graph, an empty path is returned.
     * @param src Source node index
     * @param dst Destination node index
     * @param forceOverwriteSrcCost Flag that indicates if cost of source node
     *                              should be overwritten when it is adjacent
     *                              (used for cases when you need to find another path from
     *                              source to destination, that is the source)
     * @return Path between specified nodes
     */
    List<IDX> getPath(IDX src, IDX dst, boolean forceOverwriteSrcCost);

}
