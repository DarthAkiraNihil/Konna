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

import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.level.KLevelComponentTags;

import java.util.Objects;

/**
 * The elementary unit of a game level (location) that provides all information
 * about its environment (including tiles, links to other sectors etc.).
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KMapSector extends KObject {

    private final KTileLayer tileLayer;
    private final KSectorLinkLayer sectorLinkLayer;

    /**
     * Standard constructor.
     * @param name Name of the sector
     * @param tileLayer Tile layer, assigned to this sector
     * @param sectorLinkLayer Sector link layer, assigned to this sector
     */
    public KMapSector(
        final String name,
        final KTileLayer tileLayer,
        final KSectorLinkLayer sectorLinkLayer
    ) {
        super(name, KStructUtils.setOfTags(KLevelComponentTags.SECTOR));
        this.tileLayer = tileLayer;
        this.sectorLinkLayer = sectorLinkLayer;

    }

    /**
     * Returns information of all sector layers that are placed on specific location.
     * @param x X coordinate of sliced position
     * @param y Y coordinate of sliced position
     * @return Slice of the sector on specific place
     */
    public KMapSectorSlice getSlice(
        int x,
        int y
    ) {

        return new KMapSectorSlice(
            Objects.requireNonNull(this.tileLayer.getOnPosition(x, y)),
            this.sectorLinkLayer.getOnPosition(x, y)
        );

    }


}
