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

package io.github.darthakiranihil.konna.level.generator.maker.layer;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNode;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeInputParam;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeOutputParam;
import io.github.darthakiranihil.konna.level.layer.KNoiseLayer;
import io.github.darthakiranihil.konna.level.layer.tool.KNoiseLayerTool;

import java.util.Random;

/**
 * <p>
 *     Generator node that returns a new {@link KNoiseLayer} with specified
 *     size where all values are set randomly without any specific distribution
 *     (i.e. fully randomly). Noise values are between {@code -1.0} and {@code 1.0},
 *     both inclusive.
 * </p>
 * <h3>Inputs:</h3>
 * <ul>
 *     <li>{@code size} - {@link KSize} - size of the layer</li>
 * </ul>
 * <h3>Outputs:</h3>
 * <ul>
 *     <li>{@code layer} - {@link KNoiseLayer} - constructed noise layer</li>
 * </ul>
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KRandomNormalizedNoiseLayerNode implements KGeneratorNode {

    private static final long MIN = -1_000_000_000L;
    private static final long MAX = 1_000_000_001L;
    private static final float DENOMINATOR = 1_000_000_000.0f;

    @Override
    @KGeneratorNodeInputParam(name = "size", type = KSize.class)
    @KGeneratorNodeOutputParam(name = "layer", type = KNoiseLayer.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        KSize size = params.get("size", KSize.class);
        KNoiseLayer layer = new KNoiseLayer(size);
        KNoiseLayerTool tool = layer.getTool();

        for (int x = 0; x < size.width(); x++) {
            for (int y = 0; y < size.height(); y++) {
                long base = rnd.nextLong(MIN, MAX);
                tool.setNoiseValue(x, y, base / DENOMINATOR);
            }
        }

        KUniversalMap result = new KUniversalMap();
        result.put("layer", layer);
        return result;
    }

}
