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

import java.util.HashMap;
import java.util.Map;

/**
 * Map layer containing information about tiles assigned to a map.
 * The layer is immutable by size.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KTileLayer implements KMapLayer<KTileInfo> {

    private static final class TileInfo {
        private final KTileInfo info;
        private int refs;

        TileInfo(final KTileInfo info) {
            this.info = info;
            this.refs = 1;
        }
    }

    private final int[][] tiles;
    private final Map<Integer, TileInfo> tileInfos;

    private final KSize size;

    /**
     * Constructs an empty layer with specific size.
     * @param width Layer width
     * @param height Layer height
     */
    public KTileLayer(int width, int height) {
        this(new KSize(width, height));
    }

    /**
     * Constructs an empty layer with specific size.
     * @param size Layer size
     */
    public KTileLayer(final KSize size) {
        this.size = size;
        this.tiles = new int[size.height()][size.width()];
        this.tileInfos = new HashMap<>();
    }

    @Override
    public @Nullable KTileInfo getOnPosition(int x, int y) {
        if (x >= this.size.width() || x < 0 || y >= this.size.height() || y < 0) {
            return null;
        }

        int numericId = this.tiles[y][x];
        if (!this.tileInfos.containsKey(numericId)) {
            return null;
        }

        TileInfo info = this.tileInfos.get(numericId);
        return info.info;
    }

    /**
     * Places a tile on this layer. It does not perform such operation of the position
     * is out of bounds.
     * @param x X coordinate to place the tile
     * @param y Y coordinate to place the tile
     * @param tile Tile to place
     * @return This layer (for method chaining)
     */
    public KTileLayer placeTile(int x, int y, final KTileInfo tile) {

        if (x >= this.size.width() || x < 0 || y >= this.size.height() || y < 0) {
            return this;
        }

        int newTileId = tile.getId();
        if (!this.tileInfos.containsKey(newTileId)) {
            this.tileInfos.put(newTileId, new TileInfo(tile));
        } else {
            TileInfo info = this.tileInfos.get(newTileId);
            info.refs++;
        }

        int oldTileId = this.tiles[y][x];
        this.tiles[y][x] = newTileId;

        if (oldTileId == newTileId || oldTileId == 0) {
            return this;
        }

        TileInfo oldInfo = this.tileInfos.get(oldTileId);
        oldInfo.refs--;
        if (oldInfo.refs <= 0) {
            this.tileInfos.remove(oldTileId);
        }

        return this;

    }

    /**
     * Places a tile on this layer. It does not perform such operation of the position
     * is out of bounds.
     * @param position Coordinates to place the tile on
     * @param tile Tile to place
     * @return This layer (for method chaining)
     */
    public KTileLayer placeTile(final KVector2i position, final KTileInfo tile) {
        return this.placeTile(position.x(), position.y(), tile);
    }

    /**
     * @return Size of this layer
     */
    public KSize getSize() {
        return this.size;
    }
}
