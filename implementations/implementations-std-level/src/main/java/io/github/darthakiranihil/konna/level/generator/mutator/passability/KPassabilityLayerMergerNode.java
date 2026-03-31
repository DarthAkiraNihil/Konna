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
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNode;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeInputParam;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeOutputParam;
import io.github.darthakiranihil.konna.level.layer.KPassabilityLayer;
import io.github.darthakiranihil.konna.level.layer.KPassabilityState;
import io.github.darthakiranihil.konna.level.layer.tool.KPassabilityLayerTool;

import java.util.Random;

public final class KPassabilityLayerMergerNode implements KGeneratorNode {

    @Override
    @KGeneratorNodeInputParam(name = "first", type = KPassabilityLayer.class)
    @KGeneratorNodeInputParam(name = "second", type = KPassabilityLayer.class)
    @KGeneratorNodeInputParam(name = "second_layer_offset", type = KVector2i.class)
    @KGeneratorNodeOutputParam(name = "merged", type = KPassabilityLayer.class)
    @KGeneratorNodeOutputParam(name = "new_size", type = KSize.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        KPassabilityLayer first = params.get("first", KPassabilityLayer.class);
        KPassabilityLayer second = params.get("second", KPassabilityLayer.class);
        KVector2i secondLayerOffset = params.get("second_layer_offset", KVector2i.class);

        KSize firstSize = first.getSize();
        KSize secondSize = second.getSize();

        KSize mergedSize = new KSize(
            Math.max(firstSize.width(), secondSize.width() + secondLayerOffset.x()),
            Math.max(firstSize.height(), secondSize.height() + secondLayerOffset.y())
        );

        KPassabilityLayer merged = new KPassabilityLayer(mergedSize);
        KPassabilityLayerTool tool = merged.getTool();

        for (int x = 0; x < firstSize.width(); x++) {
            for (int y = 0; y < firstSize.height(); y++) {

                KPassabilityState firstState = first.getOnPosition(x, y);
                KPassabilityState mergedState = merged.getOnPosition(x, y);

                if (
                        mergedState == KPassabilityState.VOID
                    &&  firstState != KPassabilityState.VOID
                ) {
                    tool.setState(x, y, firstState);
                } else if (
                        mergedState == KPassabilityState.IMPASSABLE
                    &&  firstState == KPassabilityState.PASSABLE
                ) {
                    tool.setState(x, y, firstState);
                }
            }
        }

        for (int x = 0; x < secondSize.width(); x++) {
            for (int y = 0; y < secondSize.height(); y++) {

                int dstX = x + secondLayerOffset.x();
                int dstY = y + secondLayerOffset.y();

                KPassabilityState secondState = second.getOnPosition(dstX, dstY);
                KPassabilityState mergedState = merged.getOnPosition(dstX, dstY);

                if (
                        mergedState == KPassabilityState.VOID
                    &&  secondState != KPassabilityState.VOID
                ) {
                    tool.setState(dstX, dstY, secondState);
                } else if (
                        mergedState == KPassabilityState.IMPASSABLE
                    &&  secondState == KPassabilityState.PASSABLE
                ) {
                    tool.setState(dstX, dstY, secondState);
                }
            }
        }

        merged.refresh();

        KUniversalMap result = new KUniversalMap();
        result.put("merged", merged);
        result.put("new_size", mergedSize);
        return result;

    }

}
