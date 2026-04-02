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
import io.github.darthakiranihil.konna.level.layer.KHeightLayer;
import io.github.darthakiranihil.konna.level.layer.tool.KHeightLayerTool;

import java.util.Random;

/**
 * <p>
 *     Generator node that returns a new {@link KHeightLayer} with specified
 *     size and constant height on the whole layer.
 * </p>
 * <h3>Inputs:</h3>
 * <ul>
 *     <li>{@code size} - {@link KSize} - size of the layer</li>
 *     <li>{@code height} - {@link Integer} - height to set on all layer cells</li>
 * </ul>
 * <h3>Outputs:</h3>
 * <ul>
 *     <li>{@code layer} - {@link KHeightLayer} - constructed height layer</li>
 * </ul>
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KConstantHeightLayerNode implements KGeneratorNode {

    @Override
    @KGeneratorNodeInputParam(name = "size", type = KSize.class)
    @KGeneratorNodeInputParam(name = "height", type = Integer.class)
    @KGeneratorNodeOutputParam(name = "layer", type = KHeightLayer.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        KSize size = params.get("size", KSize.class);
        int height = params.get("height", Integer.class);

        KHeightLayer layer = new KHeightLayer(size);
        KHeightLayerTool tool = layer.getTool();

        for (int x = 0; x < size.width(); x++) {
            for (int y = 0; y < size.height(); y++) {
                tool.setHeight(x, y, height);
            }
        }

        KUniversalMap result = new KUniversalMap();
        result.put("layer", layer);
        return result;

    }

}
