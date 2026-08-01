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

import io.github.darthakiranihil.konna.core.except.KInvalidArgumentException;
import io.github.darthakiranihil.konna.struct.KReflectionUtils;
import io.github.darthakiranihil.konna.struct.except.KEmptyObjectPoolException;
import io.github.darthakiranihil.konna.struct.object.KPoolable;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

// todo: разделить массив на часть под массивы под одиночные объекты
//       граница между ними двигается в зависимости от того, сколько выделено массивов и одиночных объектов
//       чтобы полноценно использовать объекты массивов, выделенных раньше, необходимо, чтобы все перед ними были также освобождены
//       возможно, какой-то другой способ контроля этого
//       выделять также заранее массивы, которые вьюхи на массив объектов
//       возможно, сделать отдельный класс вьюхи как массива
final class KFixedObjectPool<T extends KPoolable> implements KObjectPool<T> {

    private final class View implements KArray<T> {

        private final class Iterator implements KIterator<T> {

            private int offset;

            @Override
            public boolean hasNext() {
                return this.offset < View.this.count;
            }

            @Override
            public T next() {
                return KFixedObjectPool.this.objects[View.this.start + this.offset++];
            }

        }

        private int start;
        private int count;

        @Override
        public int length() {
            return this.count;
        }

        @Override
        public T get(int index) {
            if (index >= this.count) {
                throw new KInvalidArgumentException("Out of bounds");
            }

            return KFixedObjectPool.this.objects[this.start + index];
        }

        @Override
        public KIterator<T> iterator() {
            return new Iterator();
        }

    }

    private final Class<T> clazz;
    private final KQueue<T> conveyor;
    private int nextAvailable;

    private int arrayBorder;

    private final ReentrantLock acquisitionLock;

    @SuppressWarnings("unchecked")
    KFixedObjectPool(
        Class<T> clazz,
        int size
    ) {
        this.clazz = clazz;
        this.objects = (T[]) Array.newInstance(clazz, size);

        var constructor = Objects.requireNonNull(KReflectionUtils.getConstructor(clazz));
        for (int i = 0; i < size; i++) {

            T object = KReflectionUtils.newInstance(constructor);
            this.objects[i] = object;

        }

        this.nextAvailable = size - 1;
        this.acquisitionLock = new ReentrantLock();
    }

    @Override
    public T obtain() {
        this.acquisitionLock.lock();

        try {
            if (this.nextAvailable == this.arrayBorder) {
                throw new KEmptyObjectPoolException(this.clazz);
            }

            return this.objects[this.nextAvailable--];
        } finally {
            this.acquisitionLock.unlock();
        }

    }

    @Override
    public @Nullable T obtainSafe() {
        this.acquisitionLock.lock();

        try {

            return this.nextAvailable == this.arrayBorder
                ? null
                : this.objects[this.nextAvailable--];

        } finally {
            this.acquisitionLock.unlock();
        }
    }

    @Override
    public KArray<T> obtainMany(int count) {
        return null;
    }

    @Override
    public void release(final T object) {

    }

    @Override
    public void releaseAll(final KIterable<T> objects) {

    }
}
