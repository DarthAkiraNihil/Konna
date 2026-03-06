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

public interface KIntWeightedGraph<IDX> extends Iterable<KIntWeightedGraph.Node<IDX>> {

    interface Node<IDX> {
        IDX index();
        Set<KPair<Integer, Node<IDX>>> adjacent();
    }

    Set<IDX> getNodeIndices();
    void clear();

    void add(IDX index);
    @Nullable Node<IDX> get(IDX index);

    void connect(IDX src, IDX dst, int weight);
    void biConnect(IDX src, IDX dst, int weight);

    List<IDX> getPath(IDX src, IDX dst);

}