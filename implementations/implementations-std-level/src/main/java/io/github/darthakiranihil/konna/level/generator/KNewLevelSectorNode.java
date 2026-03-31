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
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.level.KLevelSector;
import io.github.darthakiranihil.konna.level.layer.*;
import io.github.darthakiranihil.konna.level.layer.tool.KLevelEntityLayerTool;

import java.util.Random;

public final class KNewLevelSectorNode implements KGeneratorNode {

    private final KEventSystem eventSystem;

    public KNewLevelSectorNode(@KInject KEventSystem eventSystem) {
        this.eventSystem = eventSystem;
    }

    @Override
    @KGeneratorNodeInputParam(name = "name", type = String.class)
    @KGeneratorNodeInputParam(name = "tile_layer", type = KTileLayer.class)
    @KGeneratorNodeInputParam(name = "height_layer", type = KHeightLayer.class)
    @KGeneratorNodeInputParam(name = "sector_link_layer", type = KSectorLinkLayer.class)
    @KGeneratorNodeInputParam(name = "entity_layer", type = KLevelEntityLayer.class)
    @KGeneratorNodeInputParam(name = "level_transition_layer", type = KLevelTransitionLayer.class)
    @KGeneratorNodeOutputParam(name = "sector", type = KLevelSector.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        String name = params.get("name", String.class);
        KTileLayer tileLayer = params.get("tile_layer", KTileLayer.class);
        KHeightLayer heightLayer = params.get("height_layer", KHeightLayer.class);
        KSectorLinkLayer sectorLinkLayer = params.get("sector_link_layer", KSectorLinkLayer.class);
        KLevelEntityLayer entityLayer = params.get("entity_layer", KLevelEntityLayer.class);
        KLevelTransitionLayer levelTransitionLayer = params.get(
            "level_transition_layer", KLevelTransitionLayer.class
        );

        KLevelSector sector = new KLevelSector(
            this.eventSystem,
            name,
            tileLayer,
            heightLayer,
            sectorLinkLayer,
            entityLayer,
            levelTransitionLayer
        );

        KLevelEntityLayerTool tool = entityLayer.getTool();
        tool.setSectorForAll(sector);

        KUniversalMap result = new KUniversalMap();
        result.put("sector", sector);
        return result;

    }

}
