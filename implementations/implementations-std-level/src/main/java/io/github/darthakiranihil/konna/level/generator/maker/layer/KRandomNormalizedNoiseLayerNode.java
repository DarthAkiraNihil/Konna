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

public final class KRandomNormalizedNoiseLayerNode implements KGeneratorNode {

    @Override
    @KGeneratorNodeInputParam(name = "size", type = KSize.class)
    @KGeneratorNodeOutputParam(name = "layer", type = KNoiseLayer.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        KSize size = params.get("size", KSize.class);
        KNoiseLayer layer = new KNoiseLayer(size);
        KNoiseLayerTool tool = layer.getTool();

        for (int x = 0; x < size.width(); x++) {
            for (int y = 0; y < size.height(); y++) {
                tool.setNoiseValue(x, y, rnd.nextFloat());
            }
        }

        KUniversalMap result = new KUniversalMap();
        result.put("layer", layer);
        return result;
    }

}
