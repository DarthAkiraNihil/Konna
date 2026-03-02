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

import io.github.darthakiranihil.konna.core.struct.KVector2i;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Map layer containing information about links to other map sectors
 * assigned to any map position.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KSectorLinkLayer implements KMapLayer<KMapSector> {

    private final Map<KVector2i, KMapSector> links;

    /**
     * Creates an empty layer.
     */
    public KSectorLinkLayer() {
        this.links = new HashMap<>();
    }

    /**
     * Adds a link to another sector on specific position.
     * @param x X coordinate of placed link
     * @param y Y coordinate of placed link
     * @param linkedSector Sector to link
     * @return This layer (for method chaining)
     */
    public KSectorLinkLayer link(int x, int y, final KMapSector linkedSector) {
        return this.link(new KVector2i(x, y), linkedSector);
    }

    /**
     * Adds a link to another sector on specific position.
     * @param position Place for the link
     * @param linkedSector Sector to link
     * @return This layer (for method chaining)
     */
    public KSectorLinkLayer link(final KVector2i position, final KMapSector linkedSector) {
        this.links.put(position, linkedSector);
        return this;
    }

    @Override
    public @Nullable KMapSector getOnPosition(int x, int y) {
        return this.getOnPosition(new KVector2i(x, y));
    }

    @Override
    public @Nullable KMapSector getOnPosition(final KVector2i position) {
        return this.links.get(position);
    }

}
