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

package io.github.darthakiranihil.konna.level.generator.render;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.level.KTileInfo;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNode;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeInputParam;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeOutputParam;
import io.github.darthakiranihil.konna.level.layer.KLevelTransitionLayer;
import io.github.darthakiranihil.konna.level.layer.KTileLayer;
import io.github.darthakiranihil.konna.level.layer.tool.KTileLayerTool;

import java.util.Random;

public final class KMapAllLevelTransitionsToTile implements KGeneratorNode {

    @Override
    @KGeneratorNodeInputParam(name = "tile_layer", type = KTileLayer.class)
    @KGeneratorNodeInputParam(name = "level_transition_layer", type = KLevelTransitionLayer.class)
    @KGeneratorNodeInputParam(name = "transition_tile", type = KTileInfo.class)
    @KGeneratorNodeOutputParam(name = "tile_layer", type = KTileLayer.class)
    @KGeneratorNodeOutputParam(name = "level_transition_layer", type = KLevelTransitionLayer.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        KTileLayer tileLayer = params.get("tile_layer", KTileLayer.class);
        KLevelTransitionLayer levelTransitionLayer = params.get("level_transition_layer", KLevelTransitionLayer.class);
        KTileInfo transitionTile = params.get("transition_tile", KTileInfo.class);

        KSize tileLayerSize = tileLayer.getSize();
        KTileLayerTool tool = tileLayer.getTool();

        for (int x = 0; x < tileLayerSize.width(); x++) {
            for (int y = 0; y < tileLayerSize.height(); y++) {

                var transition = levelTransitionLayer.getOnPosition(x, y);
                if (transition == null) {
                    continue;
                }

                tool.placeTile(x, y, transitionTile);

            }
        }

        KUniversalMap result = new KUniversalMap();
        result.put("level_transition_layer", levelTransitionLayer);
        result.put("tile_layer", tileLayer);
        return result;
    }

}
