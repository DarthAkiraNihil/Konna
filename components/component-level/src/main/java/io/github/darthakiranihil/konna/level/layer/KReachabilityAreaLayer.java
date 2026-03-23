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

package io.github.darthakiranihil.konna.level.layer;

import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.KTileInfo;
import io.github.darthakiranihil.konna.level.layer.tool.KReachabilityAreaLayerTool;
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
public final class KReachabilityAreaLayer
    extends KAbstractSizedLayer<KReachabilityAreaLayerTool> {

    private static final class Tool implements KReachabilityAreaLayerTool {

        private final KReachabilityAreaLayer self;

        Tool(final KReachabilityAreaLayer self) {
            this.self = self;
        }

        @Override
        public boolean isReachable(int srcX, int srcY, int dstX, int dstY) {

            if (
                    srcX >= this.self.size.width()
                ||  srcX < 0
                || srcY >= this.self.size.height()
                || srcY < 0
            ) {
                return false;
            }

            if (
                    dstX >= this.self.size.width()
                ||  dstX < 0
                ||  dstY >= this.self.size.height()
                ||  dstY < 0
            ) {
                return false;
            }

            int srcArea = this.self.areas[srcY][srcX];
            int dstArea = this.self.areas[dstY][dstX];

            return srcArea == dstArea;

        }

        @Override
        public KSize getSize() {
            return this.self.size;
        }
    }

    private final int[][] areas;
    private final KTileLayer tileLayer;
    private final KHeightLayer heightLayer;
    private final KReachabilityAreaLayerTool tool;

    /**
     * Constructs a layer with automatic filling it.
     * @param tileLayer Assigned tile layer
     * @param heightLayer Assigned height layer
     */
    public KReachabilityAreaLayer(
        final KTileLayer tileLayer,
        final KHeightLayer heightLayer
    ) {
        super(tileLayer.getSize());
        this.areas = new int[this.size.height()][this.size.width()];
        this.tileLayer = tileLayer;
        this.heightLayer = heightLayer;
        this.tool = new Tool(this);

        this.fillLayer(tileLayer, heightLayer);
    }

    /**
     * Recalculates reachability areas of this layer.
     */
    public void refresh() {
        this.fillLayer(this.tileLayer, this.heightLayer);
    }

    @Override
    public KReachabilityAreaLayerTool getTool() {
        return this.tool;
    }

    private void fillLayer(
        final KTileLayer sourceTileLayer,
        final KHeightLayer sourceHeightLayer
    ) {

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

                this.areas[visitedY][visitedX] = area;
                int visitedZ = sourceHeightLayer.getOnPosition(visitedX, visitedY);

                this.testNeighborTile(
                    visitedX - 1,
                    visitedY,
                    visitedZ,
                    toVisit,
                    seen,
                    sourceTileLayer,
                    sourceHeightLayer
                );
                this.testNeighborTile(
                    visitedX + 1,
                    visitedY,
                    visitedZ,
                    toVisit,
                    seen,
                    sourceTileLayer,
                    sourceHeightLayer
                );
                this.testNeighborTile(
                    visitedX - 1,
                    visitedY - 1,
                    visitedZ,
                    toVisit,
                    seen,
                    sourceTileLayer,
                    sourceHeightLayer
                );
                this.testNeighborTile(
                    visitedX,
                    visitedY - 1,
                    visitedZ,
                    toVisit,
                    seen,
                    sourceTileLayer,
                    sourceHeightLayer
                );
                this.testNeighborTile(
                    visitedX + 1,
                    visitedY - 1,
                    visitedZ,
                    toVisit,
                    seen,
                    sourceTileLayer,
                    sourceHeightLayer
                );
                this.testNeighborTile(
                    visitedX - 1,
                    visitedY + 1,
                    visitedZ,
                    toVisit,
                    seen,
                    sourceTileLayer,
                    sourceHeightLayer
                );
                this.testNeighborTile(
                    visitedX,
                    visitedY + 1,
                    visitedZ,
                    toVisit,
                    seen,
                    sourceTileLayer,
                    sourceHeightLayer
                );
                this.testNeighborTile(
                    visitedX + 1,
                    visitedY + 1,
                    visitedZ,
                    toVisit,
                    seen,
                    sourceTileLayer,
                    sourceHeightLayer
                );

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
        int previousZ,
        final Deque<KVector2i> visitedQueue,
        final Set<KVector2i> seen,
        final KTileLayer sourceTileLayer,
        final KHeightLayer sourceHeightLayer
    ) {

        KTileInfo tileInfo = sourceTileLayer.getOnPosition(x, y);
        int height = sourceHeightLayer.getOnPosition(x, y);
        KVector2i pos = new KVector2i(x, y);
        if (
                tileInfo != null
            &&  tileInfo.isPassable()
            &&  this.areas[y][x] == 0
            &&  !seen.contains(pos)
            &&  Math.abs(previousZ - height) <= 1
        ) {
            visitedQueue.push(pos);
            seen.add(pos);
        }

    }

}
