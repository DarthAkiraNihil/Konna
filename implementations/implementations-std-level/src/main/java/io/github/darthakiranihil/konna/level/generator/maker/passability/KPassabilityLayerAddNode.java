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

/**
 * <p>
 *     Generator node that performs add operation to two passability layers
 *     and returns addition result as a new layer.
 * </p>
 * <p>
 *     Addition is performed by the rule that all passable cells from second layer
 *     will overwrite all cells on the first layer (i.e. that are copy of the first layer), but
 *     all impassable will overwrite only voids.
 * </p>
 * <h3>Inputs:</h3>
 * <ul>
 *     <li>{@code first} - {@link KPassabilityLayer} - first passability layer</li>
 *     <li>{@code second} - {@link KPassabilityLayer} - second passability layer</li>
 *     <li>{@code offset} - {@link KVector2i} - offset of second layer overlapping</li>
 * </ul>
 * <h3>Outputs:</h3>
 * <ul>
 *     <li>{@code result} - {@link KPassabilityLayer} - addition result layer</li>
 *     <li>{@code result_size} - {@link KSize} - size of addition result</li>
 * </ul>
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KPassabilityLayerAddNode implements KGeneratorNode {

    @Override
    @KGeneratorNodeInputParam(name = "first", type = KPassabilityLayer.class)
    @KGeneratorNodeInputParam(name = "second", type = KPassabilityLayer.class)
    @KGeneratorNodeInputParam(name = "offset", type = KVector2i.class)
    @KGeneratorNodeOutputParam(name = "result", type = KPassabilityLayer.class)
    @KGeneratorNodeOutputParam(name = "result_size", type = KSize.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        KPassabilityLayer first = params.get("first", KPassabilityLayer.class);
        KPassabilityLayer second = params.get("second", KPassabilityLayer.class);
        KVector2i offset = params.get("offset", KVector2i.class);

        KSize firstSize = first.getSize();
        KSize secondSize = second.getSize();

        KSize resultSize = new KSize(
            Math.max(firstSize.width(), secondSize.width() + offset.x()),
            Math.max(firstSize.height(), secondSize.height() + offset.y())
        );

        KPassabilityLayer resultLayer = new KPassabilityLayer(resultSize);
        KPassabilityLayerTool tool = resultLayer.getTool();

        for (int x = 0; x < firstSize.width(); x++) {
            for (int y = 0; y < firstSize.height(); y++) {
                tool.setState(x, y, first.getOnPosition(x, y));
            }
        }

        for (int x = 0; x < secondSize.width(); x++) {
            for (int y = 0; y < secondSize.height(); y++) {

                int dstX = x + offset.x();
                int dstY = y + offset.y();

                KPassabilityState secondState = second.getOnPosition(x, y);
                KPassabilityState resultState = resultLayer.getOnPosition(dstX, dstY);

                if (
                        resultState == KPassabilityState.VOID
                    &&  secondState != KPassabilityState.VOID
                ) {
                    tool.setState(dstX, dstY, secondState);
                } else if (
                        resultState == KPassabilityState.IMPASSABLE
                    &&  secondState == KPassabilityState.PASSABLE
                ) {
                    tool.setState(dstX, dstY, secondState);
                }
            }
        }

        resultLayer.refresh();

        KUniversalMap result = new KUniversalMap();
        result.put("result", resultLayer);
        result.put("result_size", resultSize);
        return result;

    }

}
