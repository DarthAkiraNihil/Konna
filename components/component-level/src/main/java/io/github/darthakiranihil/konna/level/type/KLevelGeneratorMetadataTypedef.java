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

package io.github.darthakiranihil.konna.level.type;

import io.github.darthakiranihil.konna.core.io.KAssetDefinition;
import io.github.darthakiranihil.konna.core.io.KAssetDefinitionRule;
import io.github.darthakiranihil.konna.core.io.KAssetTypedef;
import io.github.darthakiranihil.konna.core.io.KCompositeAssetDefinitionRuleBuilder;
import io.github.darthakiranihil.konna.core.io.except.KAssetDefinitionError;
import io.github.darthakiranihil.konna.level.generator.KConstantNode;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNode;

import java.util.Objects;

/**
 * <p>
 *     Asset type definition for level generator metadata that is used in
 *     level procedural generation engine to construct nodes, connections between them
 *     and constants.
 * </p>
 * <p>
 *      Its asset schema is
 *      <ul>
 *          <li>
 *              {@code nodes} - nodes of defining generator (subdefinition)
 *              <ul>
 *                  <li>key is a non-null string, representing node id inside this metadata</li>
 *                  <li>value is a reference to {@link Class} extending {@link KGeneratorNode}</li>
 *              </ul>
 *          </li>
 *          <li>
 *              {@code connections} - connections between nodes (array of subdefinitions)
 *              <ul>
 *                  <li>
 *                      {@code from} - start of connection (subdefinition)
 *                      <ul>
 *                          <li>{@code node} - node id (must be presented in {@code nodes})</li>
 *                          <li>{@code param} - parameter name to extract from node output</li>
 *                      </ul>
 *                  </li>
 *                  <li>
 *                      {@code to} - end of connection (subdefinition)
 *                      <ul>
 *                          <li>{@code node} - node id (must be presented in {@code nodes})</li>
 *                          <li>{@code param} - parameter name to put to node input</li>
 *                      </ul>
 *                  </li>
 *              </ul>
 *          </li>
 *          <li>
 *              {@code constants} - constants values to put to constant nodes (subdefinition)
 *              <ul>
 *                  <li>
 *                      key is a non-null string, pointing to node id inside this metadata
 *                      (node must extend {@link KConstantNode})
 *                  </li>
 *                  <li>value is non-validated object (any type, but must be handled by node)</li>
 *              </ul>
 *          </li>
 *      </ul>
 * </p>
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KLevelGeneratorMetadataTypedef implements KAssetTypedef {

    /**
     * Constant for level generator metadata type inside Level component.
     */
    public static final String LEVEL_GENERATOR_METADATA_TYPE = "Level.generatorMetadata";

    @Override
    public String getName() {
        return LEVEL_GENERATOR_METADATA_TYPE;
    }

    @Override
    public KAssetDefinitionRule getRule() {
        var jointValidator = KCompositeAssetDefinitionRuleBuilder
            .create()
            .withNotNullString("node")
            .withNotNullString("param")
            .build();


        return KCompositeAssetDefinitionRuleBuilder
            .create()
            .withValidatedSubdefinition(
                "nodes",
                (nodes) -> {
                    for (var node: nodes.getProperties()) {
                        if (nodes.hasClassObject(node, KGeneratorNode.class)) {
                            continue;
                        }

                        throw new KAssetDefinitionError(
                            String.format(
                                    "Node %s is not a valid node. Check if it"
                                +   "implements KGeneratorNode interface",
                                node
                            )
                        );
                    }
                }
            )
            .withValidatedSubdefinitionArray(
                "connections",
                KCompositeAssetDefinitionRuleBuilder
                    .create()
                    .withValidatedSubdefinition("from", jointValidator)
                    .withValidatedSubdefinition("to", jointValidator)
                    .build()
            )
            .withRule((md) -> {
                KAssetDefinition nodes = md.getSubdefinition("nodes");

                int i = 0;
                for (var connection: md.getSubdefinitionArray("connections")) {
                    String fromNode = Objects.requireNonNull(
                        connection.getSubdefinition("from").getString("node")
                    );
                    if (!nodes.hasClassObject(fromNode, KGeneratorNode.class)) {
                        throw new KAssetDefinitionError(
                            String.format(
                                "Connection %d comes from unknown node: %s",
                                i,
                                fromNode
                            )
                        );
                    }

                    String toNode = Objects.requireNonNull(
                        connection.getSubdefinition("to").getString("node")
                    );
                    if (!nodes.hasClassObject(toNode, KGeneratorNode.class)) {
                        throw new KAssetDefinitionError(
                            String.format(
                                "Connection %d leads to unknown node: %s",
                                i,
                                toNode
                            )
                        );
                    }

                    i++;
                }
            })
            .withRule((md) -> {
                KAssetDefinition constants = md.getSubdefinition("constants");
                KAssetDefinition nodes = md.getSubdefinition("nodes");

                for (var constant: constants.getProperties()) {
                    if (nodes.hasClassObject(constant, KConstantNode.class)) {
                        continue;
                    }

                    throw new KAssetDefinitionError(
                        String.format(
                            "Constant node %s points to an unknown or non-constant node",
                            constant
                        )
                    );
                }
            })
            .build();
    }
}
