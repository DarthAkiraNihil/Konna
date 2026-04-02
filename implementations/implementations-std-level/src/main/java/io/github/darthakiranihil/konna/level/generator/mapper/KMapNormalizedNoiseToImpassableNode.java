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
import io.github.darthakiranihil.konna.level.generator.KGeneratorNode;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeInputParam;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeOutputParam;
import io.github.darthakiranihil.konna.level.layer.KNoiseLayer;
import io.github.darthakiranihil.konna.level.layer.KPassabilityLayer;
import io.github.darthakiranihil.konna.level.layer.KPassabilityState;
import io.github.darthakiranihil.konna.level.layer.tool.KPassabilityLayerTool;

import java.util.Random;

/**
 * <p>
 *     Generator node that maps all noise values to impassable cells on a passability layer,
 *     if they are greater than some another value. Other cells become void.
 * </p>
 * <h3>Inputs:</h3>
 * <ul>
 *     <li>{@code noise} - {@link KNoiseLayer} - source noise layer</li>
 *     <li>{@code min_limit} - {@link Float} - min noise value to make a cell impassable</li>
 * </ul>
 * <h3>Outputs:</h3>
 * <ul>
 *     <li>{@code layer} - {@link KPassabilityLayer} - mapped passability layer</li>
 * </ul>
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KMapNormalizedNoiseToImpassableNode implements KGeneratorNode {

    @Override
    @KGeneratorNodeInputParam(name = "noise", type = KNoiseLayer.class)
    @KGeneratorNodeInputParam(name = "min_limit", type = Float.class)
    @KGeneratorNodeOutputParam(name = "layer", type = KPassabilityLayer.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        KNoiseLayer noise = params.get("noise", KNoiseLayer.class);
        float minLimit = params.get("min_limit", Float.class);

        KSize size = noise.getSize();
        KPassabilityLayer passabilityLayer = new KPassabilityLayer(size);
        KPassabilityLayerTool tool = passabilityLayer.getTool();

        for (int x = 0; x < size.width(); x++) {
            for (int y = 0; y < size.height(); y++) {
                if (noise.getOnPosition(x, y) < minLimit) {
                    continue;
                }

                tool.setState(x, y, KPassabilityState.IMPASSABLE);
            }
        }

        KUniversalMap result = new KUniversalMap();
        result.put("layer", passabilityLayer);
        return result;
    }
}
