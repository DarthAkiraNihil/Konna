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

import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.layer.KPassabilityState;
import org.jspecify.annotations.Nullable;

import java.util.Random;

/**
 * Passability layer tool interface, providing operations for manipulating passability states
 * on assigned layer.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public interface KPassabilityLayerTool
    extends KReadableObjectLayerTool<KPassabilityState>, KReachabilityAreaLayerTool {

    /**
     * Sets a passability state for specific place.
     * @param x X coordinate of set state
     * @param y Y coordinate of set state
     * @param state State to set
     */
    void setState(int x, int y, KPassabilityState state);

    /**
     * Sets cell as impassable, if it is void.
     * @param x X coordinate of tested cell
     * @param y Y coordinate of tested cell
     */
    void setImpassableIfVoid(int x, int y);

    /**
     * Digs a passable rectangle in this layer by placing passable cells with specific
     * {@code size} on {@code topLeft} and impassable cells around that rectangle.
     * @param topLeft Top left coordinate of dug rectangle
     * @param size Size of dug rectangle
     */
    void digPassableRectangle(KVector2i topLeft, KSize size);

    /**
     * Digs a passable line in this layer by placing passable cells on a line from {@code start}
     * with specific {@code length} towards {@code direction},
     * and impassable cells around that line.
     * @param start Start position of line
     * @param length Line length
     * @param direction Digging direction
     */
    void digStraightPassableLine(KVector2i start, int length, KVector2i direction);

    /**
     * @param x X coordinate of tested cell
     * @param y Y coordinate fo tested cell
     * @return Number of reachability area located of specific cell
     */
    int getReachabilityArea(int x, int y);

    @Override
    KPassabilityState getOnPosition(int x, int y);

    @Override
    default KPassabilityState getOnPosition(final KVector2i position) {
        return this.getOnPosition(position.x(), position.y());
    }

    /**
     * @param rnd {@link Random} instance to use
     * @return A random passable point on this layer (or a random point, if there is no passable)
     */
    KVector2i getRandomPassablePosition(Random rnd);

    /**
     * @return Whether this layer has passable cells or not
     */
    boolean hasPassable();

}
