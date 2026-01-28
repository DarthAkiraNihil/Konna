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

import io.github.darthakiranihil.konna.core.data.json.KJsonSerialized;

/**
 * Representation of a size, that is basically {@link KVector2i}, but with other
 * coordinates names.
 * @param width Width
 * @param height Height
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
public record KSize(
    @KJsonSerialized
    int width,
    @KJsonSerialized
    int height
) {

    /**
     * Creates size with equal width and height (just like a square has).
     * @param side Side of the size.
     * @return Size with equal width and height, defined by size
     */
    public static KSize squared(int side) {
        return new KSize(side, side);
    }

    /**
     * @return Int vector representation of the size
     */
    public KVector2i asIntVector() {
        return new KVector2i(this.width, this.height);
    }

    /**
     * @return Float vector representation of the size
     */
    public KVector2f asFloatVector() {
        return new KVector2f(this.width, this.height);
    }
}
