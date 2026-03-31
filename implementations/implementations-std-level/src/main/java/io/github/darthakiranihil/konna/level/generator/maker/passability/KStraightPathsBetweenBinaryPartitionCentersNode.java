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
import io.github.darthakiranihil.konna.core.except.KInvalidArgumentException;
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

public final class KStraightPathsBetweenBinaryPartitionCentersNode implements KGeneratorNode {

    @Override
    @KGeneratorNodeInputParam(name = "partition", type = KPartition.class)
    @KGeneratorNodeOutputParam(name = "layer", type = KPassabilityLayer.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        KPartition partition = params.get("partition", KPartition.class);

        KPassabilityLayer layer = new KPassabilityLayer(partition.getSize());
        KPassabilityLayerTool tool = layer.getTool();

        if (partition.getSubpartitions().size() != 2) {
            throw new KInvalidArgumentException(
                "This node requires partitions to have exact two subpartitions!"
            );
        }
        Queue<List<KPartition>> queue = new LinkedList<>();
        queue.add(partition.getSubpartitions());
        while (!queue.isEmpty()) {
            List<KPartition> connected = queue.poll();
            if (connected.size() != 2) {
                throw new KInvalidArgumentException(
                    "This node requires partitions to have exact two subpartitions!"
                );
            }

            var first = connected.getFirst();
            var second = connected.getLast();

            KVector2i firstCenter = first.getCenter();
            KVector2i secondCenter = second.getCenter();

            if (firstCenter.x() == secondCenter.x()) {
                int yDiff = firstCenter.y() - secondCenter.y();
                tool.digStraightPassableLine(
                    firstCenter,
                    Math.abs(yDiff),
                    yDiff > 0 ? KVector2i.UP : KVector2i.DOWN
                );
            } else if (firstCenter.y() == secondCenter.y()) {
                int xDiff = firstCenter.x() - secondCenter.x();
                tool.digStraightPassableLine(
                    firstCenter,
                    Math.abs(xDiff),
                    xDiff > 0 ? KVector2i.LEFT : KVector2i.RIGHT
                );
            }

            if (!first.getSubpartitions().isEmpty()) {
                queue.add(first.getSubpartitions());
            }
            if (!second.getSubpartitions().isEmpty()) {
                queue.add(second.getSubpartitions());
            }

        }

        layer.refresh();

        KUniversalMap result = new KUniversalMap();
        result.put("layer", layer);
        return result;
    }

}
