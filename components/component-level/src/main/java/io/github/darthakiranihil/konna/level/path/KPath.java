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

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Simple data structure containing move directions for a map entity to use for transporting itself
 * inside a sector.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KPath {

    public static final KPath EMPTY = new KPath(new LinkedList<>());

    private final Queue<KVector2i> directions;

    /**
     * Standard constructor.
     * @param directions Queue of directions to use for moving
     */
    public KPath(final Queue<KVector2i> directions) {
        this.directions = directions;
    }

    /**
     * @return Next move direction or {@link KVector2i#ZERO} if there is no more directions
     */
    public KVector2i next() {
        if (this.directions.isEmpty()) {
            return KVector2i.ZERO;
        }

        return this.directions.poll();
    }
}
