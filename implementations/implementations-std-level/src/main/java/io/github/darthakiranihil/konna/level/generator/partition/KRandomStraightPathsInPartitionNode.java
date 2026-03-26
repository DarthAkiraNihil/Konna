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

package io.github.darthakiranihil.konna.level.generator.partition;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNode;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeInputParam;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeOutputParam;
import io.github.darthakiranihil.konna.level.layer.KPassabilityLayer;
import io.github.darthakiranihil.konna.level.layer.tool.KPassabilityLayerTool;
import io.github.darthakiranihil.konna.level.struct.KPartition;
import io.github.darthakiranihil.konna.level.struct.KPartitionNode;

import java.util.List;
import java.util.Random;

public final class KRandomStraightPathsInPartitionNode implements KGeneratorNode {

    @Override
    @KGeneratorNodeInputParam(name = "partition", type = KPartition.class)
    @KGeneratorNodeOutputParam(name = "layer", type = KPassabilityLayer.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        KPartition partition = params.get("partition", KPartition.class);

        KPassabilityLayer layer = new KPassabilityLayer(partition.getFramingSize());
        KPassabilityLayerTool tool = layer.getTool();

        List<KPartitionNode> nodes = partition.getNodes();

        for (KPartitionNode node: nodes) {

            boolean isPathVertical = rnd.nextBoolean();
            if (isPathVertical) {
                this.digVerticalPath(node, nodes, rnd, tool);
            } else {
                this.digHorizontalPath(node, nodes, rnd, tool);
            }
        }

        KUniversalMap result = new KUniversalMap();
        result.put("layer", layer);
        return result;
    }

    private void digVerticalPath(
        final KPartitionNode node,
        final List<KPartitionNode> nodes,
        final Random rnd,
        final KPassabilityLayerTool tool
    ) {

        KVector2i topLeft = node.topLeft();
        KSize size = node.size();

        int nodeEndX = topLeft.x() + size.width();
        int nodeEndY = topLeft.y() + size.height();

        List<KPartitionNode> candidates = nodes
            .stream()
            .filter(
                p -> {
                    int pEndX = p.topLeft().x() + p.size().width();
                    boolean startXIsInBounds =
                            (p.topLeft().x() >= topLeft.x())
                        &&  (p.topLeft().x() < nodeEndX);

                    boolean endXIsInBounds =
                            (pEndX >= topLeft.x())
                        &&  (pEndX < nodeEndX);

                    return
                            !p.equals(node)
                        &&  (startXIsInBounds || endXIsInBounds);
                }
            )
            .toList();

        if (candidates.isEmpty()) {
            return;
        }

        KPartitionNode dst = candidates.get(rnd.nextInt(candidates.size()));
        int minX = Math.max(topLeft.x(), dst.topLeft().x());
        int maxX = Math.min(nodeEndX, dst.topLeft().x() + dst.size().width());
        KVector2i start = new KVector2i(
            rnd.nextInt(minX, maxX + 1),
            rnd.nextInt(topLeft.y(), nodeEndY)
        );
        int dstY = rnd.nextInt(
            dst.topLeft().y(),
            dst.topLeft().y() + dst.size().height()
        );

        int diff = dstY - start.y();
        tool.digStraightPassableLine(start, Math.abs(diff), diff > 0 ? KVector2i.DOWN : KVector2i.UP);
    }

    private void digHorizontalPath(
        final KPartitionNode node,
        final List<KPartitionNode> nodes,
        final Random rnd,
        final KPassabilityLayerTool tool
    ) {

        KVector2i topLeft = node.topLeft();
        KSize size = node.size();

        int nodeEndX = topLeft.x() + size.width();
        int nodeEndY = topLeft.y() + size.height();

        List<KPartitionNode> candidates = nodes
            .stream()
            .filter(
                p -> {
                    int pEndY = p.topLeft().y() + p.size().height();
                    boolean startYIsInBounds =
                            (p.topLeft().y() >= topLeft.y())
                        &&  (p.topLeft().y() < nodeEndY);

                    boolean endYIsInBounds =
                            (pEndY >= topLeft.y())
                        &&  (pEndY < nodeEndY);

                    return
                            !p.equals(node)
                        && (startYIsInBounds || endYIsInBounds);
                }
            )
            .toList();

        if (candidates.isEmpty()) {
            return;
        }

        KPartitionNode dst = candidates.get(rnd.nextInt(candidates.size()));
        int minY = Math.max(topLeft.y(), dst.topLeft().y());
        int maxY = Math.min(nodeEndY, dst.topLeft().y() + dst.size().height());
        KVector2i start = new KVector2i(
            rnd.nextInt(topLeft.x(), nodeEndX),
            rnd.nextInt(minY, maxY + 1)
        );
        int dstX = rnd.nextInt(
            dst.topLeft().x(),
            dst.topLeft().x() + dst.size().width()
        );

        int diff = dstX - start.x();
        tool.digStraightPassableLine(start, Math.abs(diff), diff > 0 ? KVector2i.RIGHT : KVector2i.LEFT);
    }

}
