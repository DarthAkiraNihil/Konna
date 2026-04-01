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

package io.github.darthakiranihil.konna.level.generator.mapper;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.KTileInfo;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNode;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeInputParam;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeOutputParam;
import io.github.darthakiranihil.konna.level.layer.KTileLayer;
import io.github.darthakiranihil.konna.level.layer.tool.KTileLayerTool;

import java.util.Random;

public final class KInjectTilesNode implements KGeneratorNode {

    @Override
    @KGeneratorNodeInputParam(name = "destination", type = KTileLayer.class)
    @KGeneratorNodeInputParam(name = "injected", type = KTileLayer.class)
    @KGeneratorNodeInputParam(name = "offset", type = KVector2i.class)
    @KGeneratorNodeOutputParam(name = "result", type = KTileLayer.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        KTileLayer destination = params.get("destination", KTileLayer.class);
        KTileLayer injected = params.get("injected", KTileLayer.class);
        KVector2i offset = params.get("offset", KVector2i.class);

        KTileLayerTool dstTool = destination.getTool();
        KSize injectedSize = injected.getSize();
        for (int x = 0; x < injectedSize.width(); x++) {
            for (int y = 0; y < injectedSize.height(); y++) {
                KTileInfo injectedTile = injected.getOnPosition(x, y);
                if (injectedTile == null) {
                    continue;
                }

                dstTool.placeTile(
                    x + offset.x(),
                    y + offset.y(),
                    injectedTile
                );
            }
        }

        KUniversalMap result = new KUniversalMap();
        result.put("result", destination);
        return result;

    }
}
