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

package io.github.darthakiranihil.konna.level.layer.tool;

import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.KTileInfo;

/**
 * Tile layer tool interface, providing operations for manipulating tiles located on
 * the layer.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public interface KTileLayerTool extends KReadableObjectLayerTool<KTileInfo>, KSizedLayerTool {

    /**
     * Places a tile on this layer. It does not perform such operation of the position
     * is out of bounds.
     * @param x X coordinate to place the tile
     * @param y Y coordinate to place the tile
     * @param tile Tile to place
     * @return This layer (for method chaining)
     */
    KTileLayerTool placeTile(int x, int y, KTileInfo tile);

    /**
     * Places a tile on this layer. It does not perform such operation of the position
     * is out of bounds.
     * @param position Coordinates to place the tile on
     * @param tile Tile to place
     * @return This layer (for method chaining)
     */
    KTileLayerTool placeTile(KVector2i position, KTileInfo tile);

}
