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

package io.github.darthakiranihil.konna.level;

import io.github.darthakiranihil.konna.core.struct.KVector2i;

/**
 * Interface providing methods to observe different levels
 * to get a field of view.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public interface KLevelObserver {

    /**
     * Observes a point in specified level, but doesn't mark observed places as visited.
     * @param level Level to observe
     * @param sector Sector to begin observing in
     * @param point Position to begin observing on
     * @param visionRange How far does it need to observe
     * @return Field of view for this position
     * @since 0.5.1
     */
    default KFov observePointBlindly(
        final KLevel level,
        final String sector,
        final KVector2i point,
        int visionRange
    ) {
        return this.observePointBlindly(level, sector, point.x(), point.y(), visionRange);
    }

    /**
     * Observes a point in specified level, but doesn't mark observed places as visited.
     * @param level Level to observe
     * @param sector Sector to begin observing in
     * @param x X coordinate of position to begin observing on
     * @param y Y coordinate of position to begin observing on
     * @param visionRange How far does it need to observe
     * @return Field of view for this position
     * @since 0.5.1
     */
    KFov observePointBlindly(
        KLevel level,
        String sector,
        int x,
        int y,
        int visionRange
    );

    /**
     * Observes a point in specified level.
     * @param level Level to observe
     * @param sector Sector to begin observing in
     * @param point Position to begin observing on
     * @param visionRange How far does it need to observe
     * @return Field of view for this position
     */
    default KFov observePoint(
        final KLevel level,
        final String sector,
        final KVector2i point,
        int visionRange
    ) {
        return this.observePoint(level, sector, point.x(), point.y(), visionRange);
    }

    /**
     * Observes a point in specified level.
     * @param level Level to observe
     * @param sector Sector to begin observing in
     * @param x X coordinate of position to begin observing on
     * @param y Y coordinate of position to begin observing on
     * @param visionRange How far does it need to observe
     * @return Field of view for this position
     */
    KFov observePoint(
        KLevel level,
        String sector,
        int x,
        int y,
        int visionRange
    );

}
