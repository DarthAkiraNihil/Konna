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

import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;

public final class KHeightLayer {

    private final int[][] heights;
    private final KSize size;

    public KHeightLayer(KSize size) {
        this.heights = new int[size.height()][size.width()];
        this.size = size;
    }

    public int getOnPosition(final KVector2i position) {
        return this.getOnPosition(position.x(), position.y());
    }

    public int getOnPosition(int x, int y) {
        if (x >= this.size.width() || x < 0 || y >= this.size.height() || y < 0) {
            return Integer.MAX_VALUE;
        }

        return this.heights[y][x];
    }

    public KHeightLayer setHeight(final KVector2i position, int height) {
        return this.setHeight(position.x(), position.y(), height);
    }

    public KHeightLayer setHeight(int x, int y, int height) {
        if (x >= this.size.width() || x < 0 || y >= this.size.height() || y < 0) {
            return this;
        }

        this.heights[y][x] = height;
        return this;
    }

}
