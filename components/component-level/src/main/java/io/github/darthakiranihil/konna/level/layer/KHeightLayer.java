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
import io.github.darthakiranihil.konna.level.layer.tool.KHeightLayerTool;

/**
 * Map layer containing information about heights in the assigned sector.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KHeightLayer
    extends KAbstractSizedLayer<KHeightLayerTool>
    implements KIntLevelLayer<KHeightLayerTool> {

    private final static class Tool implements KHeightLayerTool {

        private final KHeightLayer self;

        public Tool(final KHeightLayer self) {
            this.self = self;
        }

        @Override
        public int getHeight(int x, int y) {
            return this.self.getOnPosition(x, y);
        }

        @Override
        public KHeightLayerTool setHeight(int x, int y, int height) {
            if (x >= this.self.size.width() || x < 0 || y >= this.self.size.height() || y < 0) {
                return this;
            }

            this.self.heights[y][x] = height;
            return this;
        }

        @Override
        public KSize getSize() {
            return this.self.size;
        }
    }


    private final int[][] heights;
    private final KHeightLayerTool tool;

    /**
     * Constructs a layer with zero height with specific size.
     * @param width Layer width
     * @param height Layer height
     */
    public KHeightLayer(int width, int height) {
        this(new KSize(width, height));
    }

    /**
     * Constructs a layer with zero height for all places.
     * @param size Size of the layer
     */
    public KHeightLayer(final KSize size) {
        super(size);
        this.heights = new int[size.height()][size.width()];
        this.tool = new Tool(this);
    }

    @Override
    public int getOnPosition(int x, int y) {
        if (x >= this.size.width() || x < 0 || y >= this.size.height() || y < 0) {
            return Integer.MAX_VALUE;
        }

        return this.heights[y][x];
    }

    @Override
    public KHeightLayerTool getTool() {
        return this.tool;
    }
}
