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
import io.github.darthakiranihil.konna.level.generator.KGeneratorNode;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeInputParam;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeOutputParam;
import io.github.darthakiranihil.konna.level.layer.KPassabilityLayer;
import io.github.darthakiranihil.konna.level.layer.KPassabilityState;
import io.github.darthakiranihil.konna.level.layer.tool.KPassabilityLayerTool;

import java.util.List;
import java.util.Random;

public final class KRemovePassableTouchingVoidNode implements KGeneratorNode {

    @Override
    @KGeneratorNodeInputParam(name = "layer", type = KPassabilityLayer.class)
    @KGeneratorNodeOutputParam(name = "layer", type = KPassabilityLayer.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        KPassabilityLayer layer = params.get("layer", KPassabilityLayer.class);
        KPassabilityLayerTool tool = layer.getTool();

        KSize size = layer.getSize();
        for (int x = 1; x < size.width() - 1; x++) {
            for (int y = 1; y < size.height() - 1; y++) {

                KPassabilityState center = tool.getOnPosition(x, y);
                KPassabilityState nNW = tool.getOnPosition(x - 1, y - 1);
                KPassabilityState nN = tool.getOnPosition(x, y - 1);
                KPassabilityState nNE = tool.getOnPosition(x + 1, y - 1);
                KPassabilityState nW = tool.getOnPosition(x - 1, y);
                KPassabilityState nE = tool.getOnPosition(x + 1, y);
                KPassabilityState nSW = tool.getOnPosition(x - 1, y + 1);
                KPassabilityState nS = tool.getOnPosition(x, y + 1);
                KPassabilityState nSE = tool.getOnPosition(x + 1, y + 1);

                if (
                        List.of(nNW, nN, nNE, nW, nE, nSW, nS, nSE).contains(KPassabilityState.VOID)
                    &&  center == KPassabilityState.PASSABLE
                ) {
                    tool.setState(x, y, KPassabilityState.IMPASSABLE);
                }

            }
        }

        layer.refresh();
        // since output param is the same
        return params;
    }

}
