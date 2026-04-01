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
import io.github.darthakiranihil.konna.level.layer.tool.KNoiseLayerTool;

public final class KNoiseLayer
    extends KAbstractSizedLayer<KNoiseLayerTool>
    implements KFloatLevelLayer<KNoiseLayerTool> {

    private static final class Tool implements KNoiseLayerTool {

        private final KNoiseLayer self;

        public Tool(final KNoiseLayer self) {
            this.self = self;
        }

        @Override
        public float getNoiseValue(int x, int y) {
            return this.self.getOnPosition(x, y);
        }

        @Override
        public void setNoiseValue(int x, int y, float value) {
            if (
                    x < 0
                ||  x >= this.self.size.width()
                ||  y < 0
                ||  y >= this.self.size.height()
            ) {
                return;
            }

            this.self.noise[y][x] = value;
        }
    }

    private final float[][] noise;
    private final KNoiseLayerTool tool;

    public KNoiseLayer(final KSize size) {
        super(size);

        this.tool = new Tool(this);
        this.noise = new float[size.height()][size.width()];
    }

    @Override
    public float getOnPosition(int x, int y) {
        if (x < 0 || x >= this.size.width() || y < 0 || y >= this.size.height()) {
            return 0.0f;
        }

        return this.noise[y][x];
    }

    @Override
    public KNoiseLayerTool getTool() {
        return this.tool;
    }
}
