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

import java.util.Stack;

public final class KReachabilityAreaLayer {

    private final int[][] areas;
    private final KSize size;

    public KReachabilityAreaLayer(final KTileLayer tileLayer) {

        this.size = tileLayer.getSize();
        this.areas = new int[this.size.height()][this.size.width()];
        this.fillLayer(tileLayer);
    }

    public boolean isReachable(final KVector2i src, final KVector2i dst) {
        return this.isReachable(src.x(), src.y(), dst.x(), dst.y());
    }

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

    private void fillLayer(final KTileLayer tileLayer) {

        KVector2i start = this.getNextFreeTilePosition(tileLayer);
        int area = 1;
        while (start != null) {

            Stack<KVector2i> toVisit = new Stack<>();
            while (!toVisit.empty()) {
                KVector2i visited = toVisit.pop();

                int visitedX = visited.x();
                int visitedY = visited.y();

                this.areas[visited.y()][visited.x()] = area;

                this.testNeighborTile(visitedX - 1, visitedY, toVisit, tileLayer);
                this.testNeighborTile(visitedX + 1, visitedY, toVisit, tileLayer);
                this.testNeighborTile(visitedX - 1, visitedY - 1, toVisit, tileLayer);
                this.testNeighborTile(visitedX, visitedY - 1, toVisit, tileLayer);
                this.testNeighborTile(visitedX + 1, visitedY - 1, toVisit, tileLayer);
                this.testNeighborTile(visitedX - 1, visitedY + 1, toVisit, tileLayer);
                this.testNeighborTile(visitedX, visitedY + 1, toVisit, tileLayer);
                this.testNeighborTile(visitedX + 1, visitedY + 1, toVisit, tileLayer);

            }

            start = this.getNextFreeTilePosition(tileLayer);
            area++;

        }

    }

    private @Nullable KVector2i getNextFreeTilePosition(final KTileLayer tileLayer) {

        for (int i = 0; i < this.size.width(); i++) {
            for (int j = 0; j < this.size.height(); j++) {

                KTileInfo tile = tileLayer.getOnPosition(i, j);
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
        final Stack<KVector2i> visitedStack,
        final KTileLayer tileLayer
    ) {

        KTileInfo tileInfo = tileLayer.getOnPosition(x, y);
        if (tileInfo != null && tileInfo.isPassable()) {
            visitedStack.push(new KVector2i(x, y));
        }

    }

}
