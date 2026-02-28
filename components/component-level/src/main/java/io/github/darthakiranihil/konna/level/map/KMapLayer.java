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

import io.github.darthakiranihil.konna.core.struct.KVector2i;
import org.jspecify.annotations.Nullable;

/**
 * Interface that represents a container of items, located on specific places.
 * @param <T> Type of item located on this layer
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public interface KMapLayer<T> {

    /**
     * @param x X coordinate of placed item
     * @param y Y coordinate of placed item
     * @return The item located on specified place of {@code null} if it is not found/presented
     */
    @Nullable T getOnPosition(int x, int y);

    /**
     * @param position Position of placed item
     * @return The item located on specified place of {@code null} if it is not found/presented
     */
    default @Nullable T getOnPosition(KVector2i position) {
        return this.getOnPosition(position.x(), position.y());
    }

}
