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
 * Interface for a collection of <i>fixed</i> size and same elements type that can be referenced
 * with a numeric index (so this is basically an array). Its elements cannot be modified
 * (i.e. you cannot change an element located by some index)
 *
 * @param <T> Type of contained element
 *
 * @since 0.7.0
 * @author Darth Akira Nihil
 */
public interface KArray<T> extends KIterable<T> {

    /**
     * @return Length of this array
     */
    int length();

    /**
     * @param index Index of the element
     * @return Element located by specified index
     */
    T get(int index);

}
