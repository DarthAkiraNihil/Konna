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
import io.github.darthakiranihil.konna.level.generator.KGeneratorNode;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeInputParam;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeOutputParam;
import io.github.darthakiranihil.konna.level.layer.KPassabilityLayer;
import io.github.darthakiranihil.konna.level.layer.KPassabilityState;
import io.github.darthakiranihil.konna.level.layer.tool.KPassabilityLayerTool;

import java.util.Random;

/**
 * <p>
 *     Generator node that replaces impassable cells, disconnecting two reachability areas,
 *     to passable cells. In order for impassable cell to be removed, it must be located
 *     between two passable cells of different reachability ares.
 * </p>
 * <h3>Inputs:</h3>
 * <ul>
 *     <li>{@code layer} - {@link KPassabilityLayer} - source passability layer</li>
 * </ul>
 * <h3>Outputs:</h3>
 * <ul>
 *     <li>
 *         {@code layer} - {@link KPassabilityLayer}
 *         - mutated passability layer with removed walls
 *     </li>
 * </ul>
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KRemoveWeakDisjointWallsNode implements KGeneratorNode {

    @Override
    @KGeneratorNodeInputParam(name = "layer", type = KPassabilityLayer.class)
    @KGeneratorNodeOutputParam(name = "layer", type = KPassabilityLayer.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        KPassabilityLayer layer = params.get("layer", KPassabilityLayer.class);
        KPassabilityLayerTool tool = layer.getTool();

        KSize size = layer.getSize();
        for (int x = 0; x < size.width(); x++) {
            for (int y = 0; y < size.height(); y++) {

                KPassabilityState nNW = tool.getOnPosition(x - 1, y - 1);
                KPassabilityState nN = tool.getOnPosition(x, y - 1);
                KPassabilityState nNE = tool.getOnPosition(x + 1, y - 1);
                KPassabilityState nW = tool.getOnPosition(x - 1, y);
                KPassabilityState nE = tool.getOnPosition(x + 1, y);
                KPassabilityState nSW = tool.getOnPosition(x - 1, y + 1);
                KPassabilityState nS = tool.getOnPosition(x, y + 1);
                KPassabilityState nSE = tool.getOnPosition(x + 1, y + 1);

                boolean nsIsReachable = tool.isReachable(x, y - 1, x, y + 1);
                boolean nsIsPassable =
                        nN == KPassabilityState.PASSABLE
                    &&  nS == KPassabilityState.PASSABLE;

                boolean neswIsReachable = tool.isReachable(x + 1, y - 1, x - 1, y + 1);
                boolean neswIsPassable =
                        nNE == KPassabilityState.PASSABLE
                    &&  nSW == KPassabilityState.PASSABLE;

                boolean weIsReachable = tool.isReachable(x - 1, y, x + 1, y);
                boolean weIsPassable =
                        nW == KPassabilityState.PASSABLE
                    &&  nE == KPassabilityState.PASSABLE;

                boolean nwseIsReachable = tool.isReachable(x - 1, y - 1, x + 1, y + 1);
                boolean nwseIsPassable =
                        nNW == KPassabilityState.PASSABLE
                    &&  nSE == KPassabilityState.PASSABLE;

                if (
                        (!nsIsReachable && nsIsPassable)
                    ||  (!neswIsReachable && neswIsPassable)
                    ||  (!weIsReachable && weIsPassable)
                    ||  (!nwseIsReachable && nwseIsPassable)
                ) {
                    tool.setState(x, y, KPassabilityState.PASSABLE);
                }

            }
        }

        layer.refresh();
        return params;
    }

}
