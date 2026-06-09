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
 *     Standard iterator that provides all operations for iterable objects.
 * </p>
 * <p>
 *     Unlike {@link KIterator}, this interface allows to call {@link KMutableIterator#remove()}
 *     method as this is conceptually supposed to be used when changing a collection during
 *     iteration is necessary.
 * </p>
 * @param <T> Type of iterated object
 *
 * @since 0.7.0
 * @author Darth Akira Nihil
 */
public interface KMutableIterator<T> extends KIterator<T> {

    /**
     * Removes the element from underlying collection. Implementation notes can be applied from
     * {@link java.util.Iterator#remove()}. Should not throw an exception.
     */
    @Override
    void remove();

}
