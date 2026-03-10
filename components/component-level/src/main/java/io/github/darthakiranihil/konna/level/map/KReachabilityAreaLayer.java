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

package io.github.darthakiranihil.konna.level.map;

import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.KTileInfo;
import org.jspecify.annotations.Nullable;

import java.util.*;

/**
 * Map layer containing information about reachability areas that represent
 * sector zones that are not connected. This it a technical layer because
 * it should not be filled manually.
 *
 * @since 0.5.0
 * @since Darth Akira Nihil
 */
public final class KReachabilityAreaLayer {

    private final int[][] areas;
    private final KSize size;
    private final KTileLayer tileLayer;

    /**
     * Constructs a layer with automatic filling it.
     * @param tileLayer Assigned tile layer
     */
    public KReachabilityAreaLayer(final KTileLayer tileLayer) {

        this.size = tileLayer.getSize();
        this.areas = new int[this.size.height()][this.size.width()];
        this.tileLayer = tileLayer;

        this.fillLayer(tileLayer);
    }

    /**
     * @param src Source point
     * @param dst Destination point
     * @return Whether it is possible to reach destination from source point in this layer
     */
    public boolean isReachable(final KVector2i src, final KVector2i dst) {
        return this.isReachable(src.x(), src.y(), dst.x(), dst.y());
    }

    /**
     * @param srcX X coordinate of source point
     * @param srcY Y coordinate of source point
     * @param dstX X coordinate of destination point
     * @param dstY Y coordinate of destination point
     * @return Whether it is possible to reach destination from source point in this layer
     */
    public boolean isReachable(int srcX, int srcY, int dstX, int dstY) {

        if (srcX >= this.size.width() || srcX < 0 || srcY >= this.size.height() || srcY < 0) {
            return false;
        }

        if (dstX >= this.size.width() || dstX < 0 || dstY >= this.size.height() || dstY < 0) {
            return false;
        }

        int srcArea = this.areas[srcY][srcX];
        int dstArea = this.areas[dstY][dstX];

        return srcArea == dstArea;

    }

    /**
     * @return Size of this layer
     */
    public KSize getSize() {
        return this.size;
    }

    /**
     * Recalculates reachability areas of this layer.
     */
    public void refresh() {
        this.fillLayer(this.tileLayer);
    }

    private void fillLayer(final KTileLayer sourceTileLayer) {

        KVector2i start = this.getNextFreeTilePosition(sourceTileLayer);
        int area = 1;
        while (start != null) {

            Set<KVector2i> seen = new HashSet<>();
            Deque<KVector2i> toVisit = new LinkedList<>();
            toVisit.add(start);
            while (!toVisit.isEmpty()) {
                KVector2i visited = toVisit.pop();

                int visitedX = visited.x();
                int visitedY = visited.y();

                this.areas[visited.y()][visited.x()] = area;

                this.testNeighborTile(visitedX - 1, visitedY, toVisit, seen, sourceTileLayer);
                this.testNeighborTile(visitedX + 1, visitedY, toVisit, seen, sourceTileLayer);
                this.testNeighborTile(visitedX - 1, visitedY - 1, toVisit, seen, sourceTileLayer);
                this.testNeighborTile(visitedX, visitedY - 1, toVisit, seen, sourceTileLayer);
                this.testNeighborTile(visitedX + 1, visitedY - 1, toVisit, seen, sourceTileLayer);
                this.testNeighborTile(visitedX - 1, visitedY + 1, toVisit, seen, sourceTileLayer);
                this.testNeighborTile(visitedX, visitedY + 1, toVisit, seen, sourceTileLayer);
                this.testNeighborTile(visitedX + 1, visitedY + 1, toVisit, seen, sourceTileLayer);

            }

            start = this.getNextFreeTilePosition(sourceTileLayer);
            area++;

        }

    }

    private @Nullable KVector2i getNextFreeTilePosition(
        final KTileLayer sourceTileLayer
    ) {

        for (int i = 0; i < this.size.width(); i++) {
            for (int j = 0; j < this.size.height(); j++) {

                KTileInfo tile = sourceTileLayer.getOnPosition(i, j);
                if (tile != null && tile.isPassable() && this.areas[j][i] == 0) {
                    return new KVector2i(i, j);
                }

            }
        }

        return null;
    }

    private void testNeighborTile(
        int x,
        int y,
        final Deque<KVector2i> visitedQueue,
        final Set<KVector2i> seen,
        final KTileLayer sourceTileLayer
    ) {

        KTileInfo tileInfo = sourceTileLayer.getOnPosition(x, y);
        KVector2i pos = new KVector2i(x, y);
        if (
                tileInfo != null
            &&  tileInfo.isPassable()
            &&  this.areas[y][x] == 0
            &&  !seen.contains(pos)
        ) {
            visitedQueue.push(pos);
            seen.add(pos);
        }

    }

}
