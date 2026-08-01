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

import java.lang.reflect.Array;

class PoolableArray<T> implements KArray<T> {

    private final class Iterator implements KIterator<T> {

        private int current;

        @Override
        public boolean hasNext() {
            return this.current < PoolableArray.this.limit;
        }

        @Override
        public T next() {
            return PoolableArray.this.elements[this.current++];
        }
    }

    private final T[] elements;
    private int limit;

    @SuppressWarnings("unchecked")
    public PoolableArray(final Class<T> clazz, int maxCapacity) {
        this.elements = (T[]) Array.newInstance(clazz, maxCapacity);
        this.limit = 0;
    }

    @Override
    public int length() {
        return this.limit;
    }

    @Override
    public T get(int index) {
        if (index >= this.limit) {
            throw new ArrayIndexOutOfBoundsException("Out of bounds");
        }

        return this.elements[index];
    }

    @Override
    public KIterator<T> iterator() {
        return new Iterator();
    }

    public void set(int idx, final T object) {
        this.elements[idx] = object;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

}
