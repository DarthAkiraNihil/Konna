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

package io.github.darthakiranihil.konna.core.struct.math;

import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Representation of a 2D vector, which coordinates are represented with ints.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
public final class KVector2i {

    private final int x;
    private final int y;

    private KVector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private static int hash(int x, int y) {
        int result = 1;
        result = result * 31 + x;
        result = result * 33 + y;
        return result;
    }

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
        int hash = KVector2i.hash(x, y);
        if (!INSTANCES.containsKey(hash)) {
            INSTANCES.put(hash, new KVector2i(x, y));
        }

        KVector2i instance = INSTANCES.get(hash);
        return instance.x == x && instance.y == y
            ? instance
            : new KVector2i(x, y);
    }

    /**
     * @return X coordinate
     */
    public int x() {
        return this.x;
    }

    /**
     * @return Y coordinate
     */
    public int y() {
        return this.y;
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

    @Override
    public boolean equals(final @Nullable Object object) {
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        KVector2i kVector2i = (KVector2i) object;
        return this.x == kVector2i.x && this.y == kVector2i.y;
    }

    @Override
    public int hashCode() {
        return KVector2i.hash(this.x, this.y);
    }

    @Override
    public String toString() {
        return "KVector2i{" + "x=" + this.x + ", y=" + this.y + '}';
    }

}
