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

package io.github.darthakiranihil.konna.level.generator.layer;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.KLevelSector;
import io.github.darthakiranihil.konna.level.entity.KStaticEntity;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNode;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeInputParam;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeOutputParam;
import io.github.darthakiranihil.konna.level.layer.KLevelEntityLayer;
import io.github.darthakiranihil.konna.level.layer.KPassabilityLayer;
import io.github.darthakiranihil.konna.level.layer.tool.KLevelEntityLayerTool;
import io.github.darthakiranihil.konna.level.layer.tool.KPassabilityLayerTool;

import java.util.Random;

public final class KRandomlyPlaceStaticEntitiesNode implements KGeneratorNode {

    private static final int RANDOM_POSITION_SELECTION_ATTEMPTS = 1024;

    private final KEventSystem eventSystem;

    public KRandomlyPlaceStaticEntitiesNode(@KInject KEventSystem eventSystem) {
        this.eventSystem = eventSystem;
    }

    @Override
    @KGeneratorNodeInputParam(name = "amount", type = Integer.class)
    @KGeneratorNodeInputParam(name = "entity_layer", type = KLevelEntityLayer.class)
    @KGeneratorNodeInputParam(name = "passability_layer", type = KPassabilityLayer.class)
    @KGeneratorNodeInputParam(name = "name", type = String.class)
    @KGeneratorNodeInputParam(name = "descriptor", type = String.class)
    @KGeneratorNodeInputParam(name = "sector", type = KLevelSector.class)
    @KGeneratorNodeOutputParam(name = "entity_layer", type = KLevelEntityLayer.class)
    @KGeneratorNodeOutputParam(name = "passability_layer", type = KPassabilityLayer.class)
    @KGeneratorNodeOutputParam(name = "sector", type = KLevelSector.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        KLevelEntityLayer entityLayer = params.get("entity_layer", KLevelEntityLayer.class);
        KPassabilityLayer passabilityLayer = params.get("passability_layer", KPassabilityLayer.class);
        String name = params.get("name", String.class);
        String descriptor = params.get("descriptor", String.class);
        KLevelSector sector = params.get("sector", KLevelSector.class);
        int amount = params.get("amount", Integer.class);

        KPassabilityLayerTool passabilityLayerTool = passabilityLayer.getTool();
        KLevelEntityLayerTool entityLayerTool = entityLayer.getTool();

        if (!passabilityLayerTool.hasPassable()) {
            KUniversalMap result = new KUniversalMap();
            result.put("entity_layer", entityLayer);
            result.put("passability_layer", passabilityLayer);
            result.put("sector", sector);
            return result;
        }

        for (int i = 0; i < amount; i++) {
            KVector2i position = passabilityLayerTool.getRandomPassablePosition(rnd);

            KStaticEntity entity = new KStaticEntity(
                this.eventSystem,
                name,
                descriptor,
                position,
                sector
            );

            entityLayerTool.placeEntity(position, entity);

        }

        KUniversalMap result = new KUniversalMap();
        result.put("entity_layer", entityLayer);
        result.put("passability_layer", passabilityLayer);
        result.put("sector", sector);
        return result;
    }

}
