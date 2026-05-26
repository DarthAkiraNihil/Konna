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

package io.github.darthakiranihil.konna.core.struct;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Representation of a 2d vector, which coordinates are represented with ints.
 * @param x X coordinate
 * @param y Y coordinate
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
public record KVector2i(
    int x,
    int y
) {
    // todo: soft references
    private static final Map<Integer, KVector2i> INSTANCES;

    /**
     * Zero vector - (0,0).
     */
    public static final KVector2i ZERO = new KVector2i(0, 0);
    /**
     * Uniform vector - (1,1).
     */
    public static final KVector2i ONE = new KVector2i(1, 1);
    /**
     * Negative uniform vector - (-1,-1).
     */
    public static final KVector2i MINUS_ONE = new KVector2i(-1, -1);

    /**
     * Vector representation of right direction - (1, 0).
     */
    public static final KVector2i RIGHT = new KVector2i(1, 0);
    /**
     * Vector representation of left direction - (1, 0).
     */
    public static final KVector2i LEFT = new KVector2i(-1, 0);
    /**
     * Vector representation of up direction - (0, 1).
     */
    public static final KVector2i UP = new KVector2i(0, 1);
    /**
     * Vector representation of down direction - (0, -1).
     */
    public static final KVector2i DOWN = new KVector2i(0, -1);

    static {
        INSTANCES = new ConcurrentHashMap<>();

        INSTANCES.put(ZERO.hashCode(), ZERO);
        INSTANCES.put(ONE.hashCode(), ONE);
        INSTANCES.put(MINUS_ONE.hashCode(), MINUS_ONE);
        INSTANCES.put(RIGHT.hashCode(), RIGHT);
        INSTANCES.put(LEFT.hashCode(), LEFT);
        INSTANCES.put(UP.hashCode(), UP);
        INSTANCES.put(DOWN.hashCode(), DOWN);
    }

    static KVector2i create(int x, int y) {
        int hash = Objects.hash(x, y);
        if (!INSTANCES.containsKey(hash)) {
            INSTANCES.put(hash, new KVector2i(x, y));
        }

        return INSTANCES.get(hash);
    }

    /**
     * Adds a vector to this vector.
     * @param other Vector to add
     * @return Result vector
     */
    public KVector2i add(final KVector2i other) {
        return KVector2i.create(this.x + other.x(), this.y + other.y());
    }

    /**
     * Subtracts a vector from this vector.
     * @param other Vector to subtract
     * @return Result vector
     */
    public KVector2i subtract(final KVector2i other) {
        return KVector2i.create(this.x - other.x(), this.y - other.y());
    }

    /**
     * Negates this vector.
     * @return A new vector with negated coordinates of this vector
     */
    public KVector2i negate() {
        return KVector2i.create(-this.x, -this.y);
    }
}
