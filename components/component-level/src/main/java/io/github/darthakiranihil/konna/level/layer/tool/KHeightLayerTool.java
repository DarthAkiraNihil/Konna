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

/**
 * Height layer tool interface, providing operations for height processing and manipulation.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public interface KHeightLayerTool extends KSizedLayerTool {

    /**
     * @param x X coordinate of position to get height of
     * @param y Y coordinate of position to get height of
     * @return Height of specific position or {@link Integer#MAX_VALUE} if the position is not
     *         valid for this layer
     */
    int getHeight(int x, int y);
    /**
     * @param position Position to get height of
     * @return Height of specific position or {@link Integer#MAX_VALUE} if the position is not
     *         valid for this layer
     */
    default int getHeight(final KVector2i position) {
        return this.getHeight(position.x(), position.y());
    }

    /**
     * Sets height for specific position. It does not have any effect if the position is not valid
     * for this layer.
     * @param x X coordinate of position to set height for
     * @param y Y coordinate of position to set height for
     * @param height New height value
     * @return This layer (for method chaining)
     */
    KHeightLayerTool setHeight(int x, int y, int height);
    /**
     * Sets height for specific position. It does not have any effect if the position is not valid
     * for this layer.
     * @param position Position to set height for
     * @param height New height value
     * @return This layer (for method chaining)
     */
    default KHeightLayerTool setHeight(final KVector2i position, int height) {
        return this.setHeight(position.x(), position.y(), height);
    }

}
