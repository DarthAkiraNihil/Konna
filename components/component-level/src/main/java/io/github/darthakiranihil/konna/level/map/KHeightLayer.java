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

/**
 * Map layer containing information about heights in the assigned sector.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KHeightLayer {

    private final int[][] heights;
    private final KSize size;

    /**
     * Constructs a layer with zero height for all places.
     * @param size Size of the layer
     */
    public KHeightLayer(KSize size) {
        this.heights = new int[size.height()][size.width()];
        this.size = size;
    }

    /**
     * @return Size of this layer
     */
    public KSize getSize() {
        return this.size;
    }

    /**
     * @param position Position to get height of
     * @return Height of specific position or {@link Integer#MAX_VALUE} if the position is not
     *         valid for this layer
     */
    public int getOnPosition(final KVector2i position) {
        return this.getOnPosition(position.x(), position.y());
    }

    /**
     * @param x X coordinate of position to get height of
     * @param y Y coordinate of position to get height of
     * @return Height of specific position or {@link Integer#MAX_VALUE} if the position is not
     *         valid for this layer
     */
    public int getOnPosition(int x, int y) {
        if (x >= this.size.width() || x < 0 || y >= this.size.height() || y < 0) {
            return Integer.MAX_VALUE;
        }

        return this.heights[y][x];
    }

    /**
     * Sets height for specific position. It does not have any effect if the position is not valid
     * for this layer.
     * @param position Position to set height for
     * @param height New height value
     * @return This layer (for method chaining)
     */
    public KHeightLayer setHeight(final KVector2i position, int height) {
        return this.setHeight(position.x(), position.y(), height);
    }

    /**
     * Sets height for specific position. It does not have any effect if the position is not valid
     * for this layer.
     * @param x X coordinate of position to set height for
     * @param y Y coordinate of position to set height for
     * @param height New height value
     * @return This layer (for method chaining)
     */
    public KHeightLayer setHeight(int x, int y, int height) {
        if (x >= this.size.width() || x < 0 || y >= this.size.height() || y < 0) {
            return this;
        }

        this.heights[y][x] = height;
        return this;
    }

}
