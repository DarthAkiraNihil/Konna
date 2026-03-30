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

package io.github.darthakiranihil.konna.level.generator.passability;

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

public final class KPassabilityLayerMaskNode implements KGeneratorNode {

    @Override
    @KGeneratorNodeInputParam(name = "source", type = KPassabilityLayer.class)
    @KGeneratorNodeInputParam(name = "mask", type = KPassabilityLayer.class)
    @KGeneratorNodeInputParam(name = "offset", type = KVector2i.class)
    @KGeneratorNodeOutputParam(name = "masked", type = KPassabilityLayer.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        KPassabilityLayer source = params.get("source", KPassabilityLayer.class);
        KPassabilityLayer mask = params.get("mask", KPassabilityLayer.class);
        KVector2i offset = params.get("offset", KVector2i.class);

        KPassabilityLayer masked = new KPassabilityLayer(source.getSize());
        KPassabilityLayerTool tool = masked.getTool();

        KSize maskSize = mask.getSize();
        for (int x = 0; x < maskSize.width(); x++) {
            for (int y = 0; y < maskSize.height(); y++) {

                KPassabilityState maskState = mask.getOnPosition(x, y);
                if (maskState != KPassabilityState.VOID) {
                    tool
                        .setState(
                            offset.x() + x,
                            offset.y() + y,
                            source.getOnPosition(
                                offset.x() + x,
                                offset.y() + y
                            )
                        );
                }
            }
        }

        masked.refresh();

        KUniversalMap result = new KUniversalMap();
        result.put("masked", masked);
        return result;

    }
}
