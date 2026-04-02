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

package io.github.darthakiranihil.konna.level.generator.mutator.passability;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.level.except.KGenerationException;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNode;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeInputParam;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeOutputParam;
import io.github.darthakiranihil.konna.level.layer.KPassabilityLayer;
import io.github.darthakiranihil.konna.level.layer.KPassabilityState;
import io.github.darthakiranihil.konna.level.layer.tool.KPassabilityLayerTool;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * <p>
 *     Generator node that leaves only that passable cells, that belong to reachability zone
 *     with the biggest area (also touching impassable tiles), others will be replaced with void.
 * </p>
 * <h3>Inputs:</h3>
 * <ul>
 *     <li>{@code layer} - {@link KPassabilityLayer} - source passability layer</li>
 * </ul>
 * <h3>Outputs:</h3>
 * <ul>
 *     <li>
 *         {@code layer} - {@link KPassabilityLayer}
 *         - mutated passability layer with only one area left
 *     </li>
 * </ul>
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KLeaveOnlyBiggestReachabilityAreaNode implements KGeneratorNode {

    @Override
    @KGeneratorNodeInputParam(name = "layer", type = KPassabilityLayer.class)
    @KGeneratorNodeOutputParam(name = "layer", type = KPassabilityLayer.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        KPassabilityLayer layer = params.get("layer", KPassabilityLayer.class);
        KPassabilityLayerTool tool = layer.getTool();
        Map<Integer, Integer> areas = new HashMap<>();

        KSize size = layer.getSize();
        for (int x = 0; x < size.width(); x++) {
            for (int y = 0; y < size.height(); y++) {

                int area = tool.getReachabilityArea(x, y);
                if (area == 0) {
                    continue;
                }

                if (!areas.containsKey(area)) {
                    areas.put(area, 0);
                }

                areas.put(area, areas.get(area) + 1);
            }
        }

        var max = areas
            .entrySet()
            .stream()
            .max(Map.Entry.comparingByValue());

        if (max.isEmpty()) {
            throw new KGenerationException("Oops!");
        }

        int maxArea = max.get().getKey();
        KPassabilityLayer processed = new KPassabilityLayer(layer.getSize());
        KPassabilityLayerTool processedTool = processed.getTool();

        for (int x = 0; x < size.width(); x++) {
            for (int y = 0; y < size.height(); y++) {

                int area = tool.getReachabilityArea(x, y);
                if (area != maxArea || tool.getOnPosition(x, y) != KPassabilityState.PASSABLE) {
                    continue;
                }

                processedTool.setState(x, y, KPassabilityState.PASSABLE);
                this.setImpassableIfSourceIsImpassable(x - 1, y - 1, layer, processedTool);
                this.setImpassableIfSourceIsImpassable(x, y - 1, layer, processedTool);
                this.setImpassableIfSourceIsImpassable(x + 1, y - 1, layer, processedTool);
                this.setImpassableIfSourceIsImpassable(x - 1, y, layer, processedTool);
                this.setImpassableIfSourceIsImpassable(x + 1, y, layer, processedTool);
                this.setImpassableIfSourceIsImpassable(x - 1, y + 1, layer, processedTool);
                this.setImpassableIfSourceIsImpassable(x, y + 1, layer, processedTool);
                this.setImpassableIfSourceIsImpassable(x + 1, y + 1, layer, processedTool);

            }
        }

        processed.refresh();

        KUniversalMap result = new KUniversalMap();
        result.put("layer", processed);
        return result;
    }

    private void setImpassableIfSourceIsImpassable(
        int x,
        int y,
        final KPassabilityLayer sourceLayer,
        final KPassabilityLayerTool processedTool
    ) {
        if (sourceLayer.getOnPosition(x, y) != KPassabilityState.IMPASSABLE) {
            return;
        }

        processedTool.setImpassableIfVoid(x, y);
    }

}
