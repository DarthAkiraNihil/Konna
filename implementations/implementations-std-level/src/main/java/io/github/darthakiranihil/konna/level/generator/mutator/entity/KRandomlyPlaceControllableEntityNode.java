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

package io.github.darthakiranihil.konna.level.generator.mutator.entity;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.entity.KControllableEntity;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNode;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeInputParam;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeOutputParam;
import io.github.darthakiranihil.konna.level.layer.KLevelEntityLayer;
import io.github.darthakiranihil.konna.level.layer.KPassabilityLayer;
import io.github.darthakiranihil.konna.level.layer.tool.KPassabilityLayerTool;

import java.util.Random;

/**
 * <p>
 *     Generator node that randomly places one controllable entity on any passable cell.
 *     However, if there is no passable cells, the node will not place entity.
 * </p>
 * <h3>Inputs:</h3>
 * <ul>
 *     <li>{@code entity_layer} - {@link KLevelEntityLayer} - level entity layer to place on</li>
 *     <li>{@code passability_layer} - {@link KPassabilityLayer} - passability layer</li>
 *     <li>{@code name} - {@link String} - name of placed entity</li>
 *     <li>{@code descriptor} - {@link String} - descriptor of placed entity</li>
 * </ul>
 * <h3>Outputs:</h3>
 * <ul>
 *     <li>
 *         {@code entity_layer} - {@link KLevelEntityLayer}
 *         - source entity layer with placed entity
 *     </li>
 *     <li>{@code passability_layer} - {@link KPassabilityLayer} - source passability layer</li>
 * </ul>
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KRandomlyPlaceControllableEntityNode implements KGeneratorNode {

    private final KEventSystem eventSystem;

    /**
     * Constructs this node.
     * @param eventSystem Event system to inject into placed entity
     */
    public KRandomlyPlaceControllableEntityNode(@KInject KEventSystem eventSystem) {
        this.eventSystem = eventSystem;
    }

    @Override
    @KGeneratorNodeInputParam(name = "entity_layer", type = KLevelEntityLayer.class)
    @KGeneratorNodeInputParam(name = "passability_layer", type = KPassabilityLayer.class)
    @KGeneratorNodeInputParam(name = "name", type = String.class)
    @KGeneratorNodeInputParam(name = "descriptor", type = String.class)
    @KGeneratorNodeOutputParam(name = "entity_layer", type = KLevelEntityLayer.class)
    @KGeneratorNodeOutputParam(name = "passability_layer", type = KPassabilityLayer.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        KLevelEntityLayer entityLayer = params.get("entity_layer", KLevelEntityLayer.class);
        KPassabilityLayer passabilityLayer = params.get("passability_layer", KPassabilityLayer.class);
        String name = params.get("name", String.class);
        String descriptor = params.get("descriptor", String.class);

        KPassabilityLayerTool passabilityLayerTool = passabilityLayer.getTool();
        if (!passabilityLayerTool.hasPassable()) {
            KUniversalMap result = new KUniversalMap();
            result.put("entity_layer", entityLayer);
            result.put("passability_layer", passabilityLayer);
            return result;
        }

        KVector2i position = passabilityLayerTool.getRandomPassablePosition(rnd);
        KControllableEntity entity = new KControllableEntity(
            this.eventSystem,
            name,
            descriptor
        );

        entityLayer.getTool().placeEntity(position, entity);

        KUniversalMap result = new KUniversalMap();
        result.put("entity_layer", entityLayer);
        result.put("passability_layer", passabilityLayer);
        return result;

    }
}
