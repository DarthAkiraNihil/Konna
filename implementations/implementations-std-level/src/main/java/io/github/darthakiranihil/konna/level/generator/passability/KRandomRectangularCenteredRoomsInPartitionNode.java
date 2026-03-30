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
import io.github.darthakiranihil.konna.level.layer.tool.KPassabilityLayerTool;
import io.github.darthakiranihil.konna.level.struct.KPartition;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public final class KRandomRectangularCenteredRoomsInPartitionNode implements KGeneratorNode {

    @Override
    @KGeneratorNodeInputParam(name = "partition", type = KPartition.class)
    @KGeneratorNodeInputParam(name = "min_filling", type = Float.class)
    @KGeneratorNodeOutputParam(name = "layer", type = KPassabilityLayer.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        KPartition partition = params.get("partition", KPartition.class);
        float minFilling = params.get("min_filling", Float.class);

        KPassabilityLayer layer = new KPassabilityLayer(partition.getSize());
        KPassabilityLayerTool tool = layer.getTool();

        List<KPartition> leaves = new LinkedList<>();

        if (partition.getSubpartitions().isEmpty()) {
            leaves.add(partition);
        } else {
            Queue<KPartition> leafSearchQueue = new LinkedList<>(partition.getSubpartitions());
            while (!leafSearchQueue.isEmpty()) {
                KPartition child = leafSearchQueue.poll();
                if (child.getSubpartitions().isEmpty()) {
                    leaves.add(child);
                } else {
                    leafSearchQueue.addAll(child.getSubpartitions());
                }
            }
        }

        for (KPartition leaf: leaves) {
            boolean dug = false;
            while (!dug) {
                KVector2i topLeft = leaf.getTopLeft();
                KVector2i center = leaf.getCenter();
                KVector2i bottomRight = leaf.getBottomRight();
                KSize size = leaf.getSize();

                KSize diggingBoxSize = size.reduce(2, 2);

                KVector2i newTopLeft = new KVector2i(
                    rnd.nextInt(topLeft.x(), center.x() + 1),
                    rnd.nextInt(topLeft.y(), center.y() + 1)
                );

                KVector2i newBottomRight = new KVector2i(
                    rnd.nextInt(center.x(), bottomRight.x() + 1),
                    rnd.nextInt(center.y(), bottomRight.y() + 1)
                );

                KSize newSize = new KSize(
                    newBottomRight.x() - newTopLeft.x(),
                    newBottomRight.y() - newTopLeft.y()
                );

                if (newSize.area() >= minFilling * diggingBoxSize.area()) {
                    tool.digPassableRectangle(newTopLeft, newSize);
                    dug = true;
                }

            }
        }

        layer.refresh();

        KUniversalMap result = new KUniversalMap();
        result.put("layer", layer);
        return result;
    }

}
