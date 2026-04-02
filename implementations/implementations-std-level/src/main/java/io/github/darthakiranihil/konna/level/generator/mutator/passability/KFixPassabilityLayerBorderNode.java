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
 *     Generator node that replaces all passable cells,
 *     located on layer border, to impassable cells.
 * </p>
 * <h3>Inputs:</h3>
 * <ul>
 *     <li>{@code layer} - {@link KPassabilityLayer} - source passability layer</li>
 * </ul>
 * <h3>Outputs:</h3>
 * <ul>
 *     <li>
 *         {@code layer} - {@link KPassabilityLayer} - mutated passability layer with fixed border
 *     </li>
 * </ul>
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KFixPassabilityLayerBorderNode implements KGeneratorNode {


    @Override
    @KGeneratorNodeInputParam(name = "layer", type = KPassabilityLayer.class)
    @KGeneratorNodeOutputParam(name = "layer", type = KPassabilityLayer.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        KPassabilityLayer layer = params.get("layer", KPassabilityLayer.class);
        KPassabilityLayerTool tool = layer.getTool();

        KSize size = layer.getSize();
        for (int x = 0; x < size.width(); x++) {
            this.setImpassableIfPassable(x, 0, tool);
            this.setImpassableIfPassable(x, size.height() - 1, tool);
        }

        for (int y = 0; y < size.height(); y++) {
            this.setImpassableIfPassable(0, y, tool);
            this.setImpassableIfPassable(size.width() - 1, y, tool);
        }

        layer.refresh();

        // since output param is the same
        return params;
    }

    private void setImpassableIfPassable(int x, int y, final KPassabilityLayerTool tool) {
        KPassabilityState state = tool.getOnPosition(x, y);
        if (state == KPassabilityState.PASSABLE) {
            tool.setState(x, y, KPassabilityState.IMPASSABLE);
        }
    }

}
