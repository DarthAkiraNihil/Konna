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
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.level.KTileInfo;
import io.github.darthakiranihil.konna.level.layer.KPassabilityLayer;
import io.github.darthakiranihil.konna.level.layer.KPassabilityState;
import io.github.darthakiranihil.konna.level.layer.KTileLayer;
import io.github.darthakiranihil.konna.level.layer.tool.KTileLayerTool;

import java.util.Random;

public final class KSimplePassabilityToTileRendererNode implements KGeneratorNode {

    @Override
    @KGeneratorNodeInputParam(name = "passability_layer", type = KPassabilityLayer.class)
    @KGeneratorNodeInputParam(name = "passable_tile", type = KTileInfo.class)
    @KGeneratorNodeInputParam(name = "impassable_tile", type = KTileInfo.class)
    @KGeneratorNodeOutputParam(name = "layer", type = KTileLayer.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        KPassabilityLayer source = params.get("passability_layer", KPassabilityLayer.class);
        KTileInfo passableTile = params.get("passable_tile", KTileInfo.class);
        KTileInfo impassableTile = params.get("impassable_tile", KTileInfo.class);

        KSize sourceSize = source.getSize();
        KTileLayer rendered = new KTileLayer(sourceSize);
        KTileLayerTool tool = rendered.getTool();

        for (int x = 0; x < sourceSize.width(); x++) {
            for (int y = 0; y < sourceSize.height(); y++) {

                KPassabilityState state = source.getOnPosition(x, y);
                if (state == null) {
                    continue;
                }

                switch (state) {
                    case PASSABLE -> tool.placeTile(x, y, passableTile);
                    case IMPASSABLE -> tool.placeTile(x, y, impassableTile);
                }

            }
        }

        KUniversalMap result = new KUniversalMap();
        result.put("layer", rendered);
        return result;
    }
}
