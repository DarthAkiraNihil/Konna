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

package io.github.darthakiranihil.konna.level.struct;

import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;

import java.util.List;

public final class KPartition {

    private final List<KPartitionNode> nodes;
    private final KSize framingSize;

    public KPartition(final List<KPartitionNode> nodes) {
        this.nodes = nodes;

        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (KPartitionNode node: nodes) {
            KVector2i topLeft = node.topLeft();
            KSize size = node.size();

            minX = Math.min(topLeft.x(), minX);
            minY = Math.min(topLeft.y(), minY);
            maxX = Math.max(topLeft.x() + size.width(), maxX);
            maxY = Math.max(topLeft.y() + size.height(), maxY);
        }

        this.framingSize = new KSize(maxX - minX, maxY - minY);
    }

    public KSize getFramingSize() {
        return this.framingSize;
    }

    public List<KPartitionNode> getNodes() {
        return this.nodes;
    }

}
