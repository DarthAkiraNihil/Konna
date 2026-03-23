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
import io.github.darthakiranihil.konna.level.KLevelSector;
import io.github.darthakiranihil.konna.level.layer.KSectorLinkData;
import org.jspecify.annotations.Nullable;

import java.util.Map;

/**
 * Sector link layer tool interface providing operations for manipulating sector links
 * and getting information about them.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public interface KSectorLinkLayerTool extends KReadableObjectLayerTool<KSectorLinkData> {

    /**
     * Adds a link to another sector on specific position.
     * @param x X coordinate of placed link
     * @param y Y coordinate of placed link
     * @param linkedSector Sector to link
     * @param destinationX X coordinate of link destination in the linked sector
     * @param destinationY Y coordinate of link destination in the linked sector
     * @return This layer (for method chaining)
     */
    KSectorLinkLayerTool link(
        int x,
        int y,
        KLevelSector linkedSector,
        int destinationX,
        int destinationY
    );

    /**
     * Adds a link to another sector on specific position.
     * @param position Place for the link
     * @param linkedSector Sector to link
     * @param destination Position of link destination in the linked sector
     * @return This layer (for method chaining)
     */
    KSectorLinkLayerTool link(
        KVector2i position,
        KLevelSector linkedSector,
        KVector2i destination
    );

    @Override
    default @Nullable KSectorLinkData getOnPosition(int x, int y) {
        return this.getOnPosition(new KVector2i(x, y));
    }

    @Override
    @Nullable KSectorLinkData getOnPosition(KVector2i position);

    /**
     * @param destinationSector Destination sector for found links
     * @return Map of links that points to the destination sector
     */
    Map<KVector2i, KSectorLinkData> getToSector(String destinationSector);

}
