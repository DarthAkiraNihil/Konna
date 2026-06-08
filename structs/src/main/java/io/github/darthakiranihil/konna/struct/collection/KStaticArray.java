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

final class KStaticArray<T> implements KMutableArray<T> {

    private class Iterator implements KIterator<T> {

        int current;

        @Override
        public boolean hasNext() {
            return this.current < KStaticArray.this.items.length;
        }

        @Override
        public T next() {
            return KStaticArray.this.items[this.current++];
        }

    }

    private final T[] items;

    @SuppressWarnings("unchecked")
    KStaticArray(int size) {
        this.items = (T[]) new Object[size];
    }

    @Override
    public T get(int index) {
        return this.items[index];
    }

    @Override
    public void set(int idx, T value) {
        this.items[idx] = value;
    }

    @Override
    public int length() {
        return this.items.length;
    }

    @Override
    public KIterator<T> iterator() {
        return new Iterator();
    }

}
