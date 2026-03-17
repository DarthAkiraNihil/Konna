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
import io.github.darthakiranihil.konna.core.except.KClassNotFoundException;
import io.github.darthakiranihil.konna.core.log.system.KSystemLogger;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.core.struct.KTriplet;
import io.github.darthakiranihil.konna.core.struct.graph.KColoredIntWeightedGraph;
import io.github.darthakiranihil.konna.core.struct.graph.KHashMapColoredIntWeightedGraph;
import io.github.darthakiranihil.konna.core.util.KClassUtils;
import io.github.darthakiranihil.konna.core.util.KValidator;
import io.github.darthakiranihil.konna.level.KLevel;
import io.github.darthakiranihil.konna.level.except.KGenerationException;
import org.jspecify.annotations.Nullable;

import java.util.*;

public final class KLevelGenerator extends KObject {

    private static final class GeneratorGraphNode {

        private final String nodeId;
        private boolean visited;
        private int priority;
        private int connectionsFromThisNode;

        public GeneratorGraphNode(
            final String nodeId,
            int initialConnections
        ) {
            this.nodeId = nodeId;
            this.connectionsFromThisNode = initialConnections;
        }
    }

    private static final class GeneratorProcessingRecord {

        private final KGeneratorNode nodeInstance;
        private final @Nullable KValidator<KUniversalMap> inputParamsValidator;
        private final KValidator<KUniversalMap> outputParamsValidator;

        // from node, from param, to param
        private final List<KTriplet<String, String, String>> dependencies;

        private @Nullable KUniversalMap outputParams;

        public GeneratorProcessingRecord(
            final KGeneratorNode nodeInstance,
            final @Nullable KValidator<KUniversalMap> inputParamsValidator,
            final KValidator<KUniversalMap> outputParamsValidator
        ) {
            this.nodeInstance = nodeInstance;
            this.inputParamsValidator = inputParamsValidator;
            this.outputParamsValidator = outputParamsValidator;
            this.dependencies = new LinkedList<>();
        }
    }

    private final KActivator activator;
    private final KLevelGeneratorMetadata metadata;

    public KLevelGenerator(
        final String name,
        final KActivator activator,
        final KLevelGeneratorMetadata metadata
    ) {
        super(name);
        this.activator = activator;
        this.metadata = metadata;

    }

    KLevel generate(long seed) {

        var nodes = this.createNodes();
        this.fillDependencies(nodes, this.metadata.connections());
        Queue<GeneratorProcessingRecord> processingQueue = this.createProcessingQueue(nodes);
        Random rnd = new Random(seed);

        GeneratorProcessingRecord ultimate = null;
        while (!processingQueue.isEmpty()) {
            var current = processingQueue.poll();
            if (processingQueue.isEmpty()) {
                ultimate = current;
            }
            KUniversalMap input = new KUniversalMap();
            // todo: skip building input for constant nodes
            for (var dependency: current.dependencies) {
                GeneratorProcessingRecord source = nodes.get(dependency.first());
                if (source.outputParams == null) {
                    throw new KGenerationException(
                        String.format(
                            "Illegal state: node %s has not been processed, but requested now",
                            dependency.first()
                        )
                    );
                }

                if (!source.outputParams.containsKey(dependency.second())) {
                    throw new KGenerationException(
                        String.format(
                            "Attempted to get output parameter %s of node %s, which is not presented",
                            dependency.first(),
                            dependency.second()
                        )
                    );
                }

                input.put(
                    dependency.third(),
                    source.outputParams.get(dependency.second())
                );
            }

            if (current.inputParamsValidator != null) {
                current.inputParamsValidator.validate(input);
            }

            KUniversalMap output = current.nodeInstance.process(input, rnd);
            current.outputParamsValidator.validate(output);
            current.outputParams = output;

        }

        if (ultimate == null) {
            throw new KGenerationException("Final node is null! Cannot get generated level");
        }

        KLevel generatedLevel = ultimate.outputParams.getSafe("level", KLevel.class);
        if (generatedLevel == null) {
            throw new KGenerationException(
                    "Cannot get generated level instance from the final node. "
                +   "It must be located by \"level\" key and be a KLevel instance"
            );
        }
        return generatedLevel;
    }

    @SuppressWarnings("unchecked")
    private Map<String, GeneratorProcessingRecord> createNodes() {

        Map<String, GeneratorProcessingRecord> nodes = new HashMap<>();

        this.metadata.nodes().forEach((k, v) -> {
            var instance = this.activator.createObject(v);
            String nodeClassName = v.getSimpleName();

            KValidator<KUniversalMap> inputParamsValidator = null;
            try {
                Class<? extends KValidator<KUniversalMap>> inputParamsValidatorClass =
                    (Class<? extends KValidator<KUniversalMap>>) KClassUtils.getForName(
                    String.format(
                        "konna.generated.level.generator.%s$$InputValidator",
                        nodeClassName
                    ));
                inputParamsValidator = this.activator.createObject(inputParamsValidatorClass);
            } catch (KClassNotFoundException e) {
                KSystemLogger.warning(
                    this.name,
                    "Could not find input params validator for node %s."
                        +   "Validation will be skipped!",
                    v.getCanonicalName()
                );
            }

            KValidator<KUniversalMap> outputParamsValidator;
            try {
                Class<? extends KValidator<KUniversalMap>> outputParamsValidatorClass =
                    (Class<? extends KValidator<KUniversalMap>>) KClassUtils.getForName(
                        String.format(
                            "konna.generated.level.generator.%s$$OutputValidator",
                            nodeClassName
                        ));
                outputParamsValidator = this.activator.createObject(outputParamsValidatorClass);
            } catch (KClassNotFoundException e) {
                throw new KGenerationException(
                    String.format(
                            "%s: no output validator specified!"
                        +   "Be sure the generator node specifies at least one output parameter",
                        k
                    )
                );
            }

            nodes.put(
                k,
                new GeneratorProcessingRecord(
                    instance,
                    inputParamsValidator,
                    outputParamsValidator
                )
            );
        });

        return nodes;

    }

    private Queue<GeneratorProcessingRecord> createProcessingQueue(
        final Map<String, GeneratorProcessingRecord> nodes
    ) {

        KColoredIntWeightedGraph<String, GeneratorGraphNode>
            graph = new KHashMapColoredIntWeightedGraph<>();

        for (var connection: this.metadata.connections()) {
            KLevelGeneratorMetadata.ConnectionJoint from = connection.first();
            KLevelGeneratorMetadata.ConnectionJoint to = connection.second();

            String fromId = from.nodeId();
            String toId = to.nodeId();

            if (!graph.has(fromId)) {
                graph.add(fromId, new GeneratorGraphNode(fromId, 1));
            } else {
                var fromNode = Objects.requireNonNull(graph.get(fromId));
                fromNode.color().connectionsFromThisNode++;
            }

            if (!graph.has(toId)) {
                graph.add(toId, new GeneratorGraphNode(toId, 0));
            }

            graph.biConnect(fromId, toId, 0);
        }

        KColoredIntWeightedGraph.Node<String, GeneratorGraphNode> root = null;
        for (String nodeId: nodes.keySet()) {
            var candidate = Objects.requireNonNull(graph.get(nodeId));
            if (candidate.color().connectionsFromThisNode > 0) {
                continue;
            }

            if (root == null) {
                root = candidate;
            } else {
                throw new KGenerationException(
                    "Invalid generator: there can be only one root node (which is the final node)"
                );
            }
        }

        Queue<KColoredIntWeightedGraph.Node<String, GeneratorGraphNode>>
            priorityBuildingQueue = new ArrayDeque<>(graph.getNodeIndices().size());
        Objects.requireNonNull(root);
        root.color().visited = true;

        priorityBuildingQueue.addAll(
            root
                .adjacent()
                .stream()
                .map(KPair::second)
                .toList()
        );
        Queue<GeneratorGraphNode> rawProcessingQueue = new PriorityQueue<>(
            graph.getNodeIndices().size(),
            Comparator.comparing(x -> x.priority)
        );
        rawProcessingQueue.add(root.color());

        while (!priorityBuildingQueue.isEmpty()) {
            var current = priorityBuildingQueue.poll();
            var sumPriority = current
                .adjacent()
                .stream()
                .map(KPair::second)
                .filter(x -> x.color().visited)
                .mapToInt(x -> x.color().priority)
                .sum();

            var currentColor = current.color();
            currentColor.priority = sumPriority - 1;
            currentColor.visited = true;
            rawProcessingQueue.add(currentColor);

            priorityBuildingQueue.addAll(
                current
                    .adjacent()
                    .stream()
                    .map(KPair::second)
                    .filter(x -> x.color().visited)
                    .toList()
            );
        }

        Queue<GeneratorProcessingRecord> processingQueue = new ArrayDeque<>(rawProcessingQueue.size());
        while (!rawProcessingQueue.isEmpty()) {
            processingQueue.add(nodes.get(rawProcessingQueue.poll().nodeId));
        }
        return processingQueue;

    }

    private void fillDependencies(
        final Map<String, GeneratorProcessingRecord> nodes,
        final KPair<
            KLevelGeneratorMetadata.ConnectionJoint,
            KLevelGeneratorMetadata.ConnectionJoint
        >[] connections
    ) {

        for (var connection: connections) {
            var from = connection.first();
            var to = connection.second();

            nodes.get(to.nodeId()).dependencies.add(
                new KTriplet<>(from.nodeId(), from.paramName(), to.paramName())
            );
        }

    }

}

/*
 *
 * {
 * "nodes": {
 * "id": "class"
 * },
 * "connections": [
 *     {
 * "from": {
 * "node": "id",
 * "param": "name"
 * },
 * "to":{
 * "node":"id",
 * "param": "name"
 * }
*      }
 * ],
 * "constants":{
 * "id": value
 * }
 * }
 *
 *
 *
 *
 */