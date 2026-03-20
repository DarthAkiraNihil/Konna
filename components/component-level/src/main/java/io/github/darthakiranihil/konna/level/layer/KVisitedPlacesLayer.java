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

import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.layer.tool.KVisitedPlacesLayerTool;

/**
 * Special map layer that contains information about seen status of places.
 * It is supposed to be used when rendering a level since visited-and-seen,
 * visited-and-unseen and unvisited are different types of places.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KVisitedPlacesLayer
    extends KAbstractSizedLayer<KVisitedPlacesLayerTool>
    implements KBooleanLevelLayer<KVisitedPlacesLayerTool> {

    private static final class Tool implements KVisitedPlacesLayerTool {

        private final KVisitedPlacesLayer self;

        public Tool(final KVisitedPlacesLayer self) {
            this.self = self;
        }

        @Override
        public boolean isVisited(int x, int y) {

            if (x < 0 || x >= this.self.size.width() || y < 0 || y >= this.self.size.height()) {
                return false;
            }

            return this.self.seen[y][x];
        }

        /**
         * Marks a specific place as seen.
         * @param x X coordinate of position to mark
         * @param y Y coordinate of position to mark
         */
        @Override
        public void visitPlace(int x, int y) {

            if (x < 0 || x >= this.self.size.width() || y < 0 || y >= this.self.size.height()) {
                return;
            }

            this.self.seen[y][x] = true;
        }

        @Override
        public KSize getSize() {
            return this.self.size;
        }
    }

    private final boolean[][] seen;
    private final KVisitedPlacesLayerTool tool;

    /**
     * Constructs a layer full of unseen places.
     * @param size Size of this layer
     */
    public KVisitedPlacesLayer(final KSize size) {
        super(size);
        this.seen = new boolean[size.height()][size.width()];
        this.tool = new Tool(this);
    }

    @Override
    public boolean getOnPosition(int x, int y) {
        if (x < 0 || x >= this.size.width() || y < 0 || y >= this.size.height()) {
            return false;
        }

        return this.seen[y][x];
    }

    @Override
    public KVisitedPlacesLayerTool getTool() {
        return this.tool;
    }
}
