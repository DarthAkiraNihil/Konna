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

public interface KColoredIntWeightedGraph<IDX, COL> extends Iterable<KColoredIntWeightedGraph.Node<IDX, COL>> {

    interface Node<IDX, COL> {
        IDX index();
        COL color();
        Set<KPair<Integer, Node<IDX, COL>>> adjacent();
    }

    Set<IDX> getNodeIndices();
    void clear();

    void add(IDX index, COL color);
    @Nullable Node<IDX, COL> get(IDX index);

    void connect(IDX src, IDX dst, int weight);
    void biConnect(IDX src, IDX dst, int weight);

    List<IDX> getPath(IDX src, IDX dst);

}
