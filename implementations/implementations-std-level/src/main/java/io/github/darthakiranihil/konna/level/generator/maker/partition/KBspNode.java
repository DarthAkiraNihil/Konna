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

package io.github.darthakiranihil.konna.level.generator.maker.partition;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNode;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeInputParam;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeOutputParam;
import io.github.darthakiranihil.konna.level.struct.KPartition;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>
 *     Generator node that returns a {@link KPartition}, created from applying
 *     binary space partitioning to area of specific size.
 * </p>
 * <h3>Inputs:</h3>
 * <ul>
 *     <li>
 *     {@code size} - {@link KSize} -
 *     size the root partition (that will be split into subpartitions
 *     </li>
 *     <li>{@code iterations} - {@link Integer} - max depth of BSP tree</li>
 *     <li>
 *         {@code vectical_split_min_ratio} - {@link Float}
 *         - min ratio of widths of subpartitions, appeared as result of vertical split
 *     </li>
 *     <li>
 *         {@code vectical_split_max_ratio} - {@link Float}
 *         - max ratio of widths of subpartitions, appeared as result of vertical split
 *     </li>
 *     <li>
 *         {@code horizontal_split_min_ratio} - {@link Float}
 *         - min ratio of heights of subpartitions, appeared as result of horizontal split
 *     </li>
 *     <li>
 *         {@code horizontal_split_max_ratio} - {@link Float}
 *         - max ratio of heights of subpartitions, appeared as result of horizontal split
 *     </li>
 * </ul>
 * <h3>Outputs:</h3>
 * <ul>
 *     <li>{@code partition} - {@link KPartition} - resulting BSP partition instance</li>
 * </ul>
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KBspNode implements KGeneratorNode {

    @Override
    @KGeneratorNodeInputParam(name = "size", type = KSize.class)
    @KGeneratorNodeInputParam(name = "iterations", type = Integer.class)
    @KGeneratorNodeInputParam(name = "vertical_split_min_ratio", type = Float.class)
    @KGeneratorNodeInputParam(name = "vertical_split_max_ratio", type = Float.class)
    @KGeneratorNodeInputParam(name = "horizontal_split_min_ratio", type = Float.class)
    @KGeneratorNodeInputParam(name = "horizontal_split_max_ratio", type = Float.class)
    @KGeneratorNodeOutputParam(name = "partition", type = KPartition.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        int iterations = params.get("iterations", Integer.class);
        KSize size = params.get("size", KSize.class);

        float verticalSplitMinRatio = params.get("vertical_split_min_ratio", Float.class);
        float verticalSplitMaxRatio = params.get("vertical_split_max_ratio", Float.class);
        float horizontalSplitMinRatio = params.get("horizontal_split_min_ratio", Float.class);
        float horizontalSplitMaxRatio = params.get("horizontal_split_max_ratio", Float.class);

        KPartition root = this.makePartition(
            KVector2i.ZERO,
            size,
            verticalSplitMinRatio,
            verticalSplitMaxRatio,
            horizontalSplitMinRatio,
            horizontalSplitMaxRatio,
            iterations,
            rnd
        );

        KUniversalMap result = new KUniversalMap();
        result.put("partition", root);
        return result;

    }

    private KPartition makePartition(
        final KVector2i topLeft,
        final KSize size,
        float verticalSplitMinRatio,
        float verticalSplitMaxRatio,
        float horizontalSplitMinRatio,
        float horizontalSplitMaxRatio,
        int iteration,
        final Random rnd
    ) {

        // BspTreeNode root = new BspTreeNode(topLeft, size);
        if (iteration == 0) {
            return new KPartition(topLeft, size, List.of());
        }

        List<KPartition> children = new ArrayList<>(2);
        KPartition root = new KPartition(topLeft, size, children);

        var split = this.split(
            topLeft,
            size,
            verticalSplitMinRatio,
            verticalSplitMaxRatio,
            horizontalSplitMinRatio,
            horizontalSplitMaxRatio,
            rnd
        );

        children.add(this.makePartition(
            split.first().first(),
            split.first().second(),
            verticalSplitMinRatio,
            verticalSplitMaxRatio,
            horizontalSplitMinRatio,
            horizontalSplitMaxRatio,
            iteration - 1,
            rnd
        ));
        children.add(this.makePartition(
            split.second().first(),
            split.second().second(),
            verticalSplitMinRatio,
            verticalSplitMaxRatio,
            horizontalSplitMinRatio,
            horizontalSplitMaxRatio,
            iteration - 1,
            rnd
        ));

        return root;

    }

    private KPair<
        KPair<KVector2i, KSize>,
        KPair<KVector2i, KSize>
    > split(
        final KVector2i topLeft,
        final KSize size,
        float verticalSplitMinRatio,
        float verticalSplitMaxRatio,
        float horizontalSplitMinRatio,
        float horizontalSplitMaxRatio,
        final Random rnd
    ) {

        boolean splitByVertical = rnd.nextBoolean();
        if (splitByVertical) {

            float ratio = rnd.nextFloat(verticalSplitMinRatio, verticalSplitMaxRatio);
            int splitPoint = (int) (size.width() * ratio);
            while (splitPoint == 0 || splitPoint >= size.width()) {
                ratio = rnd.nextFloat(verticalSplitMinRatio, verticalSplitMaxRatio);
                splitPoint = (int) (size.width() * ratio);
            }

            var first = new KPair<>(topLeft, new KSize(splitPoint, size.height()));
            var second = new KPair<>(
                topLeft.add(new KVector2i(splitPoint, 0)),
                new KSize(
                size.width() - splitPoint,
                    size.height()
                )
            );

            return new KPair<>(first, second);

        } else {

            float ratio = rnd.nextFloat(horizontalSplitMinRatio, horizontalSplitMaxRatio);
            int splitPoint = (int) (size.height() * ratio);
            while (splitPoint == 0 || splitPoint >= size.height()) {
                ratio = rnd.nextFloat(verticalSplitMinRatio, verticalSplitMaxRatio);
                splitPoint = (int) (size.height() * ratio);
            }

            var first = new KPair<>(topLeft, new KSize(size.width(), splitPoint));
            var second = new KPair<>(
                topLeft.add(new KVector2i(0, splitPoint)),
                new KSize(
                    size.width(),
                    size.height() - splitPoint
                )
            );

            return new KPair<>(first, second);

        }
    }
}
