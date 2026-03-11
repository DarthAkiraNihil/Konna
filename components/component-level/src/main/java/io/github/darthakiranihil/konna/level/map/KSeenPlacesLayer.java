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

public final class KSeenPlacesLayer {

    private final boolean[][] seen;
    private final KSize size;

    public KSeenPlacesLayer(final KSize size) {
        this.seen = new boolean[size.height()][size.width()];
        this.size = size;
    }

    public KSize getSize() {
        return this.size;
    }

    public boolean getSeenStatus(final KVector2i position) {
        return this.getSeenStatus(position.x(), position.y());
    }

    public boolean getSeenStatus(int x, int y) {

        if (x < 0 || x >= this.size.width() || y < 0 || y >= this.size.height()) {
            return false;
        }

        return this.seen[y][x];
    }

    public void seeThePlace(final KVector2i position) {
        this.seeThePlace(position.x(), position.y());
    }

    public void seeThePlace(int x, int y) {

        if (x < 0 || x >= this.size.width() || y < 0 || y >= this.size.height()) {
            return;
        }

        this.seen[y][x] = true;
    }

}
