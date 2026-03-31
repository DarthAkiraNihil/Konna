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

/**
 * <p>
 *     Level procedural generation engine based on node system.
 * </p>
 * <p>
 *     On construction, it accepts prepared metadata, that is used to create generator graph
 *     to be processed. Also finds all input and output validators for all processed nodes.
 * </p>
 * <p>
 *     There are some requirements that metadata should meet in order for generation to be
 *     ended successfully:
 *     <ul>
 *         <li>
 *             Generator graph must contain one and only one root node (that does not have
 *             any adjacent nodes). If there are more than one of it,
 *             a {@link KGenerationException} will be thrown.
 *         </li>
 *         <li>
 *             The root node must return in output the only instance of {@link KLevel}, located
 *             by {@code level} key! (actually the output may contain multiple values, but
 *             only {@code level} will be used, others will be ignored)
 *         </li>
 *         <li>
 *             All nodes used in the generator must specify at least one output parameter,
 *             else {@link KGenerationException} will be thrown on node that does not fit
 *             this requirement.
 *         </li>
 *         <li>
 *             Each constant in metadata must point to existing node inside this generator.
 *         </li>
 *     </ul>
 * </p>
 *
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KLevelGenerator extends KObject {

    private static final class GeneratorGraphNode {

        private final String nodeId;
        private boolean visited;
        private int connectionsFromThisNode;

        GeneratorGraphNode(
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

        GeneratorProcessingRecord(
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

    /**
     * Standard constructor.
     * @param name Generator name
     * @param activator Activator to create nodes
     * @param metadata Generator metadata
     */
    public KLevelGenerator(
        final String name,
        final KActivator activator,
        final KLevelGeneratorMetadata metadata
    ) {
        super(name);
        this.activator = activator;
        this.metadata = metadata;

    }

    /**
     * Generates a new level by processing nodes, specified in the metadata.
     * During this process it generates a {@link Random} instance, that is shared by all
     * processed nodes.
     * @param seed The initial seed for generator
     * @return Generated level
     */
    public KLevel generate(long seed) {

        var nodes = this.createNodes();
        this.fillDependencies(nodes, this.metadata.connections());
        var dependencyGraph = this.buildDependencyGraph();
        KColoredIntWeightedGraph.Node<String, GeneratorGraphNode> root = null;
        for (String nodeId: nodes.keySet()) {
            var candidate = Objects.requireNonNull(dependencyGraph.get(nodeId));
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

        Queue<GeneratorProcessingRecord>
            processingQueue = this.createProcessingQueue(nodes, dependencyGraph);
        Random rnd = new Random(seed);

        GeneratorProcessingRecord ultimate = null;
        while (!processingQueue.isEmpty()) {
            var current = processingQueue.poll();
            if (processingQueue.isEmpty()) {
                ultimate = current;
            }

            try {
                KSystemLogger.debug(
                    this.name, "Processing node %s", current.nodeInstance
                );
                KUniversalMap input = this.prepareInput(nodes, current);
                KUniversalMap output = current.nodeInstance.process(input, rnd);
                current.outputParamsValidator.validate(output);
                current.outputParams = output;
            } catch (Throwable e) {
                KSystemLogger.error(this.name, e);
                throw new KGenerationException(e.getMessage());
            }

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

        KSystemLogger.info(this.name, "Level has been successfully generated");
        return generatedLevel;
    }

    private KUniversalMap prepareInput(
        final Map<String, GeneratorProcessingRecord> nodes,
        final GeneratorProcessingRecord current
    ) {
        KUniversalMap input = new KUniversalMap();
        if (current.nodeInstance instanceof KConstantNode) {
            return input;
        }

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
                        dependency.second(),
                        dependency.first()
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

        return input;
    }

    private Map<String, GeneratorProcessingRecord> createNodes() {

        Map<String, GeneratorProcessingRecord> nodes = new HashMap<>();
        for (var node: this.metadata.nodes().entrySet()) {
            var nodeId = node.getKey();
            var nodeClass = node.getValue();
            boolean isConst = KConstantNode.class.isAssignableFrom(nodeClass);

            if (isConst) {
                nodes.put(
                    nodeId,
                    this.createConstantNode(nodeId, nodeClass)
                );
            } else {
                nodes.put(
                    nodeId,
                    this.createCommonNode(nodeId, nodeClass)
                );
            }
        }

        return nodes;

    }

    @SuppressWarnings("unchecked")
    private GeneratorProcessingRecord createConstantNode(
        final String nodeId,
        final Class<? extends KGeneratorNode> nodeClass
    ) {

        Object constant = this.metadata.constants().get(nodeId);
        if (constant == null) {
            throw new KGenerationException(
                String.format(
                    "Unknown constant node: %s",
                    nodeId
                )
            );
        }

        var instance = this.activator.createObject(nodeClass, constant);
        String nodeClassName = nodeClass.getSimpleName();

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
                    nodeId
                )
            );
        }

        return new GeneratorProcessingRecord(
            instance,
            null,
            outputParamsValidator
        );

    }

    @SuppressWarnings("unchecked")
    private GeneratorProcessingRecord createCommonNode(
        final String nodeId,
        final Class<? extends KGeneratorNode> nodeClass
    ) {
        var instance = this.activator.createObject(nodeClass);
        String nodeClassName = nodeClass.getSimpleName();

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
            "Could not find input params validator for node %s. "
                +   "Validation will be skipped!",
                nodeClass.getCanonicalName()
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
                    nodeId
                )
            );
        }

        return new GeneratorProcessingRecord(
            instance,
            inputParamsValidator,
            outputParamsValidator
        );

    }



    private void fillDependencies(
        final Map<String, GeneratorProcessingRecord> nodes,
        final Set<KPair<
            KLevelGeneratorMetadata.ConnectionJoint,
            KLevelGeneratorMetadata.ConnectionJoint
        >> connections
    ) {

        for (var connection: connections) {
            var from = connection.first();
            var to = connection.second();

            nodes.get(to.nodeId()).dependencies.add(
                new KTriplet<>(from.nodeId(), from.paramName(), to.paramName())
            );
        }

    }

    private KColoredIntWeightedGraph<String, GeneratorGraphNode> buildDependencyGraph() {

        KColoredIntWeightedGraph<String, GeneratorGraphNode>
            tree = new KHashMapColoredIntWeightedGraph<>();

        for (var connection: this.metadata.connections()) {
            KLevelGeneratorMetadata.ConnectionJoint from = connection.first();
            KLevelGeneratorMetadata.ConnectionJoint to = connection.second();

            String fromId = from.nodeId();
            String toId = to.nodeId();

            if (!tree.has(fromId)) {
                tree.add(fromId, new GeneratorGraphNode(fromId, 1));
            } else {
                var fromNode = Objects.requireNonNull(tree.get(fromId));
                fromNode.color().connectionsFromThisNode++;
            }

            if (!tree.has(toId)) {
                tree.add(toId, new GeneratorGraphNode(toId, 0));
            }

            tree.connect(fromId, toId, 1);
        }

        for (var node: this.metadata.nodes().keySet()) {
            if (tree.has(node)) {
                continue;
            }

            tree.add(node, new GeneratorGraphNode(node, 0));
        }

        return tree;

    }

    private void dfs(
        final Map<String, GeneratorProcessingRecord> createdNodes,
        final KColoredIntWeightedGraph.Node<String, GeneratorGraphNode> node,
        final Deque<GeneratorProcessingRecord> nodeDeque
    ) {

        node.color().visited = true;
        for (var adjacent: node.adjacent()) {
            var adjacentNode = adjacent.second();
            if (adjacentNode.color().visited) {
                continue;
            }

            this.dfs(createdNodes, adjacentNode, nodeDeque);
        }
        nodeDeque.addFirst(
            createdNodes.get(node.color().nodeId)
        );

    }

    private Queue<GeneratorProcessingRecord> createProcessingQueue(
        final Map<String, GeneratorProcessingRecord> createdNodes,
        final KColoredIntWeightedGraph<String, GeneratorGraphNode> dependencyGraph
    ) {

        Set<String> nodeNames = dependencyGraph.getNodeIndices();
        Deque<GeneratorProcessingRecord> nodeDeque = new ArrayDeque<>(nodeNames.size());

        for (var nodeName: nodeNames) {
            var node = Objects.requireNonNull(dependencyGraph.get(nodeName));
            if (node.color().visited) {
                continue;
            }

            this.dfs(createdNodes, node, nodeDeque);
        }

        return nodeDeque;

    }

}
