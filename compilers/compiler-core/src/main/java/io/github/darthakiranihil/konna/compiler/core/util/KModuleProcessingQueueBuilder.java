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

import javax.lang.model.type.DeclaredType;
import java.util.*;
import java.util.stream.Collectors;

public final class KModuleProcessingQueueBuilder {

    public Queue<KModuleMetadata> buildQueue(
        final List<KModuleMetadata> modules
    ) {

        KModuleDependencyGraph graph = this.buildGraph(modules);
        Map<String, KModuleMetadata> moduleMap = modules
            .stream()
            .collect(Collectors.toMap(KModuleMetadata::className, x -> x));

        Set<String> nodeNames = graph.getNodeIndices();
        Deque<KModuleMetadata> moduleQueue = new ArrayDeque<>(nodeNames.size());
        Set<String> visited = new HashSet<>(nodeNames.size());

        for (var nodeName: nodeNames) {
            var node = Objects.requireNonNull(graph.get(nodeName));
            if (visited.contains(nodeName)) {
                continue;
            }

            this.dfs(moduleMap, node, visited, moduleQueue);
        }

        return moduleQueue;
    }

    private KModuleDependencyGraph buildGraph(final List<KModuleMetadata> modules) {

        var graph = new KModuleDependencyGraph();

        for (var module: modules) {

            String moduleClassName = module.className();
            if (!graph.has(moduleClassName)) {
                graph.add(moduleClassName);
            }

            for (var dep: module.moduleDependencies()) {
                String depClassName = ((DeclaredType) dep.module())
                    .asElement()
                    .getSimpleName()
                    .toString();

                if (!graph.has(depClassName)) {
                    graph.add(depClassName);
                }

                graph.connect(depClassName, moduleClassName);
            }
        }

        for (var module: modules) {
            if (graph.has(module.className())) {
                continue;
            }

            graph.add(module.className());
        }

        return graph;
    }

    private void dfs(
        final Map<String, KModuleMetadata> moduleMap,
        final KModuleDependencyGraph.Node node,
        final Set<String> visited,
        final Deque<KModuleMetadata> moduleQueue
    ) {

        visited.add(node.index());

        for (var adjacent: node.adjacent()) {
            if (visited.contains(adjacent.index())) {
                continue;
            }

            this.dfs(
                moduleMap,
                adjacent,
                visited,
                moduleQueue
            );
        }

        moduleQueue.addFirst(moduleMap.get(node.index()));

    }

}
