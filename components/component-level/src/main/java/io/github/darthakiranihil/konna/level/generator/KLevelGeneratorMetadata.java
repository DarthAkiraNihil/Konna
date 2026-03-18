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

import io.github.darthakiranihil.konna.core.struct.KPair;

import java.util.Map;
import java.util.Set;

/**
 * Metadata that is used by {@link KLevelGenerator} to create nodes (including constants)
 * and connections between them in order to properly process them in level generation process.
 *
 * @param nodes Map of node ids and their classes
 * @param connections Set of connections between nodes
 * @param constants Map of node ids and their constants contained in them (a node must be
 *                  a {@link KConstantNode}
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public record KLevelGeneratorMetadata(
    Map<String, Class<? extends KGeneratorNode>> nodes,
    Set<KPair<ConnectionJoint, ConnectionJoint>> connections,
    Map<String, Object> constants
) {

    /**
     * Record containing one part of internode connection.
     * @param nodeId Node id that this joint belongs to
     * @param paramName Parameter name that connects to the joint
     */
    public record ConnectionJoint(
        String nodeId,
        String paramName
    ) {

    }

}
