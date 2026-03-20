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

import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.KLevelSector;
import io.github.darthakiranihil.konna.level.layer.tool.KSectorLinkLayerTool;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Map layer containing information about links to other map sectors
 * assigned to any map position.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KSectorLinkLayer
    implements KObjectLevelLayer<KSectorLinkData, KSectorLinkLayerTool> {

    private static class Tool implements KSectorLinkLayerTool {

        private final KSectorLinkLayer self;

        public Tool(final KSectorLinkLayer self) {
            this.self = self;
        }

        @Override
        public KSectorLinkLayerTool link(
            int x,
            int y,
            final KLevelSector linkedSector,
            int destinationX,
            int destinationY
        ) {
            return this.link(
                new KVector2i(x, y),
                linkedSector,
                new KVector2i(destinationX, destinationY)
            );
        }

        @Override
        public KSectorLinkLayerTool link(
            final KVector2i position,
            final KLevelSector linkedSector,
            final KVector2i destination
        ) {
            this.self.links.put(position, new KSectorLinkData(linkedSector, destination));
            return this;
        }

        @Override
        public @Nullable KSectorLinkData getOnPosition(int x, int y) {
            return this.getOnPosition(new KVector2i(x, y));
        }

        @Override
        public @Nullable KSectorLinkData getOnPosition(final KVector2i position) {
            return this.self.links.get(position);
        }

        /**
         * @param destinationSector Destination sector for found links
         * @return Map of links that points to the destination sector
         */
        public Map<KVector2i, KSectorLinkData> getToSector(final String destinationSector) {

            return this
                .self
                .links
                .entrySet()
                .stream()
                .filter(x -> x.getValue().linkedSector().name().equals(destinationSector))
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));

        }
    }

    private final Map<KVector2i, KSectorLinkData> links;
    private final KSectorLinkLayerTool tool;

    /**
     * Creates an empty layer.
     */
    public KSectorLinkLayer() {
        this.links = new HashMap<>();
        this.tool = new Tool(this);
    }

    @Override
    public @Nullable KSectorLinkData getOnPosition(int x, int y) {
        return this.getOnPosition(new KVector2i(x, y));
    }

    @Override
    public @Nullable KSectorLinkData getOnPosition(final KVector2i position) {
        return this.links.get(position);
    }

    @Override
    public KSectorLinkLayerTool getTool() {
        return this.tool;
    }
}
