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

package io.github.darthakiranihil.konna.level.path;

import io.github.darthakiranihil.konna.core.struct.KVector2i;

import java.util.Queue;

public final class KPath {

    private final Queue<KVector2i> directions;

    public KPath(final Queue<KVector2i> directions) {
        this.directions = directions;
    }

    public KVector2i next() {
        if (this.directions.isEmpty()) {
            return KVector2i.ZERO;
        }

        return this.directions.poll();
    }
}
