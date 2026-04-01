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
import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNode;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeInputParam;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeOutputParam;
import io.github.darthakiranihil.konna.level.layer.KPassabilityLayer;
import io.github.darthakiranihil.konna.level.layer.KPassabilityState;
import io.github.darthakiranihil.konna.level.layer.tool.KPassabilityLayerTool;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public final class KExtrudeBorderByFirstAndLastImpassableNode implements KGeneratorNode {

    @Override
    @KGeneratorNodeInputParam(name = "layer", type = KPassabilityLayer.class)
    @KGeneratorNodeOutputParam(name = "layer", type = KPassabilityLayer.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        KPassabilityLayer layer = params.get("layer", KPassabilityLayer.class);
        KSize size = layer.getSize();

        List<KPair<Integer, KPair<Integer, Integer>>> horizontal = new LinkedList<>();
        List<KPair<Integer, KPair<Integer, Integer>>> vertical = new LinkedList<>();

        for (int x = 0; x < size.width(); x++) {
            int firstY = -1;
            int lastY = size.height();

            for (int y = 0; y < size.height(); y++) {
                if (layer.getOnPosition(x, y) == KPassabilityState.IMPASSABLE) {
                    firstY = y;
                    break;
                }
            }

            for (int y = size.height() - 1; y >= 0; y--) {
                if (layer.getOnPosition(x, y) == KPassabilityState.IMPASSABLE) {
                    lastY = y;
                    break;
                }
            }

            if (firstY != -1 && lastY != size.height()) {
                vertical.add(new KPair<>(x, new KPair<>(firstY, lastY)));
            }
        }

        for (int y = 0; y < size.height(); y++) {
            int firstX = -1;
            int lastX = size.width();

            for (int x = 0; x < size.width(); x++) {
                if (layer.getOnPosition(x, y) == KPassabilityState.IMPASSABLE) {
                    firstX = x;
                    break;
                }
            }

            for (int x = size.width() - 1; x >= 0; x--) {
                if (layer.getOnPosition(x, y) == KPassabilityState.IMPASSABLE) {
                    lastX = x;
                    break;
                }
            }

            if (firstX != -1 && lastX != size.width()) {
                horizontal.add(new KPair<>(y, new KPair<>(firstX, lastX)));
            }
        }

        KPassabilityLayerTool tool = layer.getTool();
        for (var hp: horizontal) {
            int y = hp.first();
            var h = hp.second();

            tool.setState(h.first(), y, KPassabilityState.IMPASSABLE);
            int start = h.first() + 1;
            int finish = h.second();

            for (int x = start; x < finish; x++) {
                tool.setState(x, y, KPassabilityState.PASSABLE);
            }
            tool.setState(finish, y, KPassabilityState.IMPASSABLE);
        }

        for (var vp: vertical) {
            int x = vp.first();
            var v = vp.second();

            tool.setState(x, v.first(), KPassabilityState.IMPASSABLE);
            int start = v.first() + 1;
            int finish = v.second();

            for (int y = start; y < finish; y++) {
                if (tool.getOnPosition(x, y) == KPassabilityState.IMPASSABLE) {
                    continue;
                }
                tool.setState(x, y, KPassabilityState.PASSABLE);
            }
            tool.setState(x, finish, KPassabilityState.IMPASSABLE);
        }

        return params;
    }

}
