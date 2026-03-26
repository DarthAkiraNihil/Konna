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

package io.github.darthakiranihil.konna.level.generator;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.struct.KPartition;
import io.github.darthakiranihil.konna.level.struct.KPartitionNode;
import org.jspecify.annotations.Nullable;

import java.util.*;

public final class KBspNode implements KGeneratorNode {

    private static final class BspTreeNode {

        private final KVector2i topLeft;
        private final KSize size;

        private @Nullable BspTreeNode leftChild;
        private @Nullable BspTreeNode rightChild;

        public BspTreeNode(
            final KVector2i topLeft,
            final KSize size
        ) {
            this.topLeft = topLeft;
            this.size = size;
        }
    }

    @Override
    @KGeneratorNodeInputParam(name = "size", type = KSize.class)
    @KGeneratorNodeInputParam(name = "iterations", type = Integer.class)
    @KGeneratorNodeOutputParam(name = "partition", type = KPartition.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        int iterations = params.get("iterations", Integer.class);
        KSize size = params.get("size", KSize.class);

        BspTreeNode root = this.makePartition(KVector2i.ZERO, size, iterations, rnd);

        ArrayList<KPartitionNode> partitionNodes = new ArrayList<>(1 << iterations);
        Queue<BspTreeNode> nodeQueue = new ArrayDeque<>(1 << iterations);
        nodeQueue.add(root);

        while (!nodeQueue.isEmpty()) {
            BspTreeNode node = nodeQueue.poll();

            if (node.rightChild != null) {
                nodeQueue.add(node.rightChild);
            }

            if (node.leftChild != null) {
                nodeQueue.add(node.leftChild);
            }

            if (node.leftChild == null && node.rightChild == null) {
                partitionNodes.add(
                    new KPartitionNode(
                        node.topLeft,
                        node.size
                    )
                );
            }
        }

        partitionNodes.trimToSize();

        KUniversalMap result = new KUniversalMap();
        result.put("partition", new KPartition(partitionNodes));
        return result;

    }

    private BspTreeNode makePartition(
        final KVector2i topLeft,
        final KSize size,
        int iteration,
        final Random rnd
    ) {

        BspTreeNode root = new BspTreeNode(topLeft, size);

        if (iteration != 0) {
            var children = this.split(topLeft, size, rnd);
            root.leftChild = this.makePartition(
                children.first().topLeft,
                children.first().size,
                iteration - 1,
                rnd
            );
            root.rightChild = this.makePartition(
                children.second().topLeft,
                children.second().size,
                iteration - 1,
                rnd
            );

        }

        return root;

    }

    private KPair<BspTreeNode, BspTreeNode> split(
        final KVector2i topLeft,
        final KSize size,
        final Random rnd
    ) {

        boolean splitByVertical = rnd.nextBoolean();
        if (splitByVertical) {

            int splitPoint = rnd.nextInt(size.width());
            return new KPair<>(
                new BspTreeNode(
                    topLeft,
                    new KSize(
                        splitPoint,
                        size.height()
                    )
                ),
                new BspTreeNode(
                    topLeft.add(new KVector2i(splitPoint, 0)),
                    new KSize(
                        size.width() - splitPoint,
                        size.height()
                    )
                )
            );

        } else {

            int splitPoint = rnd.nextInt(size.height());
            return new KPair<>(
                new BspTreeNode(
                    topLeft,
                    new KSize(
                        size.width(),
                        splitPoint
                    )
                ),
                new BspTreeNode(
                    topLeft.add(new KVector2i(0, splitPoint)),
                    new KSize(
                        size.width(),
                        size.height() - splitPoint
                    )
                )
            );

        }

    }
}
