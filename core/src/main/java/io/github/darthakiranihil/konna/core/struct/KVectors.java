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

import io.github.darthakiranihil.konna.core.object.KUninstantiable;

/**
 * Factory class for creating vector objects.
 *
 * @since 0.7.0
 * @author Darth Akira Nihil
 */
public final class KVectors extends KUninstantiable {

    private KVectors() {
        super();
    }

    /**
     * <p>
     *     Creates a new 2D-int vector with specified coordinates.
     * </p>
     * <p>
     *     If there is a vector
     *     with those coordinates that is already allocated, the reference to it
     *     will be returned instead of creating a new one.
     * </p>
     *
     * @param x X coordinate of vector
     * @param y Y coordinate of vector
     * @return An instance of vector with specified coordinates
     */
    public static KVector2i new2i(int x, int y) {
        return KVector2i.create(x, y);
    }

    /**
     * <p>
     *     Creates a new 2D-float vector with specified coordinates.
     * </p>
     * <p>
     *     If there is a vector
     *     with those coordinates that is already allocated, the reference to it
     *     will be returned instead of creating a new one.
     * </p>
     *
     * @param x X coordinate of vector
     * @param y Y coordinate of vector
     * @return An instance of vector with specified coordinates
     */
    public static KVector2f new2f(float x, float y) {
        return KVector2f.create(x, y);
    }

    /**
     * <p>
     *     Creates a new 4D-float vector with specified coordinates.
     * </p>
     * <p>
     *     If there is a vector
     *     with those coordinates that is already allocated, the reference to it
     *     will be returned instead of creating a new one.
     * </p>
     *
     * @param x X coordinate of vector
     * @param y Y coordinate of vector
     * @param z Z coordinate of vector
     * @param w W coordinate of vector
     * @return An instance of vector with specified coordinates
     */
    public static KVector4f new4f(float x, float y, float z, float w) {
        return KVector4f.create(x, y, z, w);
    }

}
