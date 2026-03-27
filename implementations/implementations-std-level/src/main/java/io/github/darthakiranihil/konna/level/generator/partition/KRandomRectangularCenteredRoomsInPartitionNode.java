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

import java.util.Random;

public final class KRandomRectangularCenteredRoomsInPartitionNode implements KGeneratorNode {

    // todo
    @Override
    @KGeneratorNodeInputParam(name = "partition", type = KPartition.class)
    @KGeneratorNodeInputParam(name = "ratio", type = Float.class)
    // @KGeneratorNodeInputParam(name = "max_ratio", type = Float.class)
    // @KGeneratorNodeInputParam(name = "min_greater_side", type = Integer.class)
    @KGeneratorNodeOutputParam(name = "layer", type = KPassabilityLayer.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        KPartition partition = params.get("partition", KPartition.class);
        // float minRatio = params.get("min_ratio", Float.class);
        // float maxRatio = params.get("max_ratio", Float.class);
        // float minGreaterSide = params.get("min_greater_side", Integer.class);

        KPassabilityLayer layer = new KPassabilityLayer(partition.getFramingSize());
        KPassabilityLayerTool tool = layer.getTool();

        for (KPartitionNode node: partition.getNodes()) {

            KVector2i topLeft = node.topLeft();
            KSize size = node.size();
            KVector2i center = node.center();

            KVector2i newTopLeft = new KVector2i(
                rnd.nextInt(topLeft.x(), topLeft.x() + size.width()),
                rnd.nextInt(topLeft.y(), topLeft.y() + size.height())
            );

            KVector2i diff = newTopLeft.subtract(center);

            KVector2i newBottomRight = new KVector2i(
                diff.x() > 0
                    ? rnd.nextInt(center.x())
                    : rnd.nextInt(center.x() + 1, topLeft.x() + size.width()),
                diff.y() > 0
                    ? rnd.nextInt(center.y())
                    : rnd.nextInt(center.y() + 1, topLeft.y() + size.height())
            );

            KSize newSize = new KSize(
                newBottomRight.x() - newTopLeft.x(),
                newBottomRight.y() - newTopLeft.y()
            );

            tool.digPassableRectangle(newTopLeft, newSize);
        }

        KUniversalMap result = new KUniversalMap();
        result.put("layer", layer);
        return result;
    }

}
