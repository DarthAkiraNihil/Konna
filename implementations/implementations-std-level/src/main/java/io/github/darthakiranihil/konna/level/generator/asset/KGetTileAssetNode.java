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

package io.github.darthakiranihil.konna.level.generator.asset;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.level.KTileInfo;
import io.github.darthakiranihil.konna.level.asset.KTileCollection;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNode;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeInputParam;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeOutputParam;

import java.util.Random;

/**
 * <p>
 *     Generator node that returns a tile asset from {@link KTileCollection}
 *     by its id.
 * </p>
 * <h3>Inputs:</h3>
 * <ul>
 *     <li>{@code asset_id} - {@link String} - asset id of tile</li>
 * </ul>
 * <h3>Outputs:</h3>
 * <ul>
 *     <li>{@code tile} - {@link KTileInfo} - corresponding tile asset</li>
 * </ul>
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KGetTileAssetNode implements KGeneratorNode {

    private final KTileCollection tileCollection;

    /**
     * Constructs this node.
     * @param tileCollection Tile collection to retrieve tiles from
     */
    @KInject
    public KGetTileAssetNode(
        final KTileCollection tileCollection
    ) {
        this.tileCollection = tileCollection;
    }

    @Override
    @KGeneratorNodeInputParam(name = "asset_id", type = String.class)
    @KGeneratorNodeOutputParam(name = "tile", type = KTileInfo.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        String assetId = params.get("asset_id", String.class);
        KUniversalMap result = new KUniversalMap();
        result.put("tile", this.tileCollection.getAsset(assetId));
        return result;

    }
}
