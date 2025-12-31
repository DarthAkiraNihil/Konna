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

    /**
     * Zero vector - (0,0)
     */
    public static final KVector2i ZERO = new KVector2i(0, 0);

    /**
     * Adds a vector to this vector.
     * @param other Vector to add
     * @return This vector (for method chaining)
     */
    public KVector2i add(KVector2i other) {
        return new KVector2i(this.x + other.x(), this.y + other.y());
    }

    /**
     * Subtracts a vector from this vector.
     * @param other Vector to subtract
     * @return This vector (for method chaining)
     */
    public KVector2i subtract(KVector2i other) {
        return new KVector2i(this.x - other.x(), this.y - other.y());
    }

    /**
     * Negates this vector.
     * @return A new vector with negated coordinates of this vector
     */
    public KVector2i negate() {
        return new KVector2i(-this.x, -this.y);
    }
}
