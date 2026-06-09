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

package io.github.darthakiranihil.konna.struct.collection;

/**
 * <p>
 *     Same as {@link KArray}, but its elements can be set as you desire (i.e. you change
 *     factical element located by some index.
 * </p>
 * <p>
 *     Despite the name of the interface, it is not a {@link KMutableIterable} as it is supposed
 *     to change collection's size, which is not possible for array as its size is <i>fixed</i>
 * </p>
 *
 * @param <T> Type of contained object
 *
 * @since 0.7.0
 * @author Darth Akira Nihil
 */
public interface KMutableArray<T> extends KArray<T> {

    /**
     * Sets an element by specified index.
     * @param idx Index of placed element
     * @param value Value to set
     */
    void set(int idx, T value);

}
