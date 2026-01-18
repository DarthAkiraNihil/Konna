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
 * Representation of a 4d vector, which coordinates are represented with floats.
 * @param x X coordinate
 * @param y Y coordinate
 * @param z Z coordinate
 * @param w W coordinate
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
public record KVector4f(
    float x,
    float y,
    float z,
    float w
) {

    /**
     * Zero vector - (0,0,0,0).
     */
    public static final KVector4f ZERO = new KVector4f(0.0f, 0.0f, 0.0f, 0.0f);
    /**
     * Unit vector - (1,1,1,1).
     */
    public static final KVector4f ONE = new KVector4f(1.0f, 1.0f, 1.0f, 1.0f);

}
