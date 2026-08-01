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

import io.github.darthakiranihil.konna.core.except.KException;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public sealed interface KQueue<T> extends KCollection<T> {

    static <T> KQueue<T> createConcurrent(
        final Class<T> clazz,
        int initialCapacity
    ) {
        return new ConcurrentQueue<>(clazz, initialCapacity);
    }

    final class ConcurrentQueue<T> implements KQueue<T> {

        private final class Iterator implements KIterator<T> {

            private int current;

            Iterator() {
                ConcurrentQueue.this.lock.lock();
                try {
                    this.current = ConcurrentQueue.this.tail;
                    ConcurrentQueue.this.isIterated = true;
                } finally {
                    ConcurrentQueue.this.lock.unlock();
                }
            }

            @Override
            public boolean hasNext() {
                boolean has = this.current != ( ConcurrentQueue.this.tail + 1) % ConcurrentQueue.this.elements.length;
                if (!has) {
                    ConcurrentQueue.this.isIterated = false;
                    ConcurrentQueue.this.iterationCondition.signal();
                }

                return has;
            }

            @Override
            public T next() {
                T e = Objects.requireNonNull(ConcurrentQueue.this.elements[this.current]);
                this.current = (this.current + 1) % ConcurrentQueue.this.elements.length;
                return e;
            }


        }

        private static final float EXTENSION_FACTOR = 1.5f;

        private @Nullable T[] elements;
        private int head;
        private int tail;

        private volatile boolean isIterated;
        private final ReentrantLock lock;
        private final Condition iterationCondition;

        @SuppressWarnings("unchecked")
        ConcurrentQueue(
            final Class<T> clazz,
            int initialCapacity
        ) {

            this.elements = (T[]) Array.newInstance(clazz, initialCapacity);
            this.head = 0;
            this.tail = 0;

            this.lock = new ReentrantLock();
            this.iterationCondition = this.lock.newCondition();
        }

        @Override
        public int size() {
            if (this.isEmpty()) {
                return 0;
            }

            return this.head <= this.tail
                ? this.tail - this.head + 1
                : this.tail + this.head;
        }

        @Override
        public boolean isEmpty() {
            return this.head == -1;
        }

        @Override
        @SuppressWarnings("unchecked")
        public void add(T element) {
            this.lock.lock();
            try {
                if (this.head == -1) {
                    this.head = 0;
                    this.elements[0] = element;
                    return;
                }

                if (( this.tail + 1 ) % this.elements.length == this.head) {

                    T[] newElements = (T[]) Array.newInstance(
                        element.getClass(),
                        (int) ( this.elements.length * EXTENSION_FACTOR )
                    );
                    System.arraycopy(this.elements, 0, newElements, 0, this.elements.length);

                    this.elements = newElements;
                }

                this.tail = ( this.tail + 1 ) % this.elements.length;
                this.elements[this.tail] = element;
            } finally {
                this.lock.unlock();
            }
        }

        @Override
        public @Nullable T poll() {
            this.lock.lock();
            try {

                if (this.isIterated) {
                    this.iterationCondition.await();
                }

                if (this.isEmpty()) {
                    return null;
                }

                if (this.head == this.tail) {
                    T e = this.elements[this.head];
                    this.elements[this.head] = null;
                    this.head = -1;
                    this.tail = 0;
                    return e;
                }

                T e = this.elements[this.head];
                this.elements[this.head] = null;
                this.head = (this.head + 1) % this.elements.length;
                return e;

            } catch (InterruptedException e) {
                throw new KException(e.getMessage());
            } finally {
                this.lock.unlock();
            }
        }

        @Override
        public @Nullable T head() {
            this.lock.lock();
            try {
                return this.isEmpty()
                    ? null
                    : this.elements[this.head];
            } finally {
                this.lock.unlock();
            }

        }

        @Override
        public boolean contains(T object) {
            if (this.isEmpty()) {
                return false;
            }

            this.lock.lock();
            try {
                int end = ( this.tail + 1 ) % this.elements.length;
                for (int i = this.head; i != end; i = ( i + 1 ) % this.elements.length) {
                    if (object.equals(this.elements[i])) {
                        return true;
                    }
                }
            } finally {
                this.lock.unlock();
            }

            return false;
        }

        @Override
        public KIterator<T> iterator() {
            return new Iterator();
        }
    }

    void add(T element);

    @Nullable T poll();
    @Nullable T head();

}
