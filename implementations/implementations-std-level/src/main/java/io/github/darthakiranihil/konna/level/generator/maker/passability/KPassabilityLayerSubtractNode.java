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

package io.github.darthakiranihil.konna.level.generator.maker.passability;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNode;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeInputParam;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeOutputParam;
import io.github.darthakiranihil.konna.level.layer.KPassabilityLayer;
import io.github.darthakiranihil.konna.level.layer.KPassabilityState;
import io.github.darthakiranihil.konna.level.layer.tool.KPassabilityLayerTool;

import java.util.Random;

public final class KPassabilityLayerSubtractNode implements KGeneratorNode {

    @Override
    @KGeneratorNodeInputParam(name = "first", type = KPassabilityLayer.class)
    @KGeneratorNodeInputParam(name = "second", type = KPassabilityLayer.class)
    @KGeneratorNodeInputParam(name = "offset", type = KVector2i.class)
    @KGeneratorNodeOutputParam(name = "result", type = KPassabilityLayer.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        KPassabilityLayer first = params.get("first", KPassabilityLayer.class);
        KPassabilityLayer second = params.get("second", KPassabilityLayer.class);
        KVector2i offset = params.get("offset", KVector2i.class);

        KSize firstSize = first.getSize();
        KPassabilityLayer resultLayer = new KPassabilityLayer(firstSize);
        KPassabilityLayerTool tool = resultLayer.getTool();

        for (int x = 0; x < firstSize.width(); x++) {
            for (int y = 0; y < firstSize.height(); y++) {
                tool.setState(x, y, first.getOnPosition(x, y));
            }
        }

        KSize secondSize = second.getSize();
        for (int x = 0; x < secondSize.width(); x++) {
            for (int y = 0; y < secondSize.height(); y++) {

                KPassabilityState secondState = second.getOnPosition(x, y);
                if (secondState == KPassabilityState.VOID) {
                    continue;
                }

                tool.setState(
                    offset.x() + x,
                    offset.y() + y,
                    KPassabilityState.VOID
                );
            }
        }

        resultLayer.refresh();

        KUniversalMap result = new KUniversalMap();
        result.put("result", resultLayer);
        return result;

    }
}
