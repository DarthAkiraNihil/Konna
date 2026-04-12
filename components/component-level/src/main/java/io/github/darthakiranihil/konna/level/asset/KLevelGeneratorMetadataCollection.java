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

package io.github.darthakiranihil.konna.level.asset;

import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.io.KAsset;
import io.github.darthakiranihil.konna.core.io.KAssetCollection;
import io.github.darthakiranihil.konna.core.io.KAssetDefinition;
import io.github.darthakiranihil.konna.core.io.KAssetLoader;
import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNode;
import io.github.darthakiranihil.konna.level.generator.KLevelGeneratorMetadata;
import io.github.darthakiranihil.konna.level.type.KLevelGeneratorMetadataTypedef;

import java.util.*;

/**
 * Collection of level generator metadata assets of type
 * {@link KLevelGeneratorMetadataTypedef#LEVEL_GENERATOR_METADATA_TYPE}.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KLevelGeneratorMetadataCollection
    implements KAssetCollection<KLevelGeneratorMetadata> {

    private final KAssetLoader assetLoader;

    /**
     * Standard constructor.
     * @param assetLoader Asset loader
     */
    @KInject
    public KLevelGeneratorMetadataCollection(
        final KAssetLoader assetLoader
    ) {
        this.assetLoader = assetLoader;
    }

    @Override
    public KLevelGeneratorMetadata getAsset(final String assetId) {
        KAsset asset = this.assetLoader.loadAsset(
            assetId,
            KLevelGeneratorMetadataTypedef.LEVEL_GENERATOR_METADATA_TYPE
        );

        KAssetDefinition rawNodes = asset.getSubdefinition("nodes");
        KAssetDefinition[] rawConnections = asset.getSubdefinitionArray("connections");
        KAssetDefinition rawConstants = asset.getSubdefinition("constants");

        Map<String, Class<? extends KGeneratorNode>>
            nodes = new HashMap<>(rawNodes.getProperties().size());

        Set<KPair<
            KLevelGeneratorMetadata.ConnectionJoint,
            KLevelGeneratorMetadata.ConnectionJoint
        >> connections = new HashSet<>();
        Map<String, Object> constants = new HashMap<>(rawConstants.getProperties().size());

        for (var nodeId: rawNodes.getProperties()) {
            nodes.put(
                nodeId,
                rawNodes.getClassObject(nodeId, KGeneratorNode.class)
            );
        }

        for (var connection: rawConnections) {
            KAssetDefinition from = connection.getSubdefinition("from");
            KAssetDefinition to = connection.getSubdefinition("to");

            connections.add(
                new KPair<>(
                    new KLevelGeneratorMetadata.ConnectionJoint(
                        Objects.requireNonNull(from.getString("node")),
                        Objects.requireNonNull(from.getString("param"))
                    ),
                    new KLevelGeneratorMetadata.ConnectionJoint(
                        Objects.requireNonNull(to.getString("node")),
                        Objects.requireNonNull(to.getString("param"))
                    )
                )
            );
        }

        for (var constant: rawConstants.getProperties()) {
            constants.put(
                constant,
                Objects.requireNonNull(rawConstants.getObject(constant))
            );
        }

        return new KLevelGeneratorMetadata(
            nodes,
            connections,
            constants
        );
    }
}
