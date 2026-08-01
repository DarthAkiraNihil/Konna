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

import io.github.darthakiranihil.konna.struct.KReflectionUtils;
import io.github.darthakiranihil.konna.struct.except.KEmptyObjectPoolException;
import io.github.darthakiranihil.konna.struct.object.KPoolable;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

final class KExtensibleObjectPool<T extends KPoolable> implements KObjectPool<T> {

    private final Class<T> clazz;

    private final int maxSize;
    private final float extensionFactor;

    private final KQueue<T> conveyor;
    private final KQueue<PoolableArray<T>> arrayConveyor;
    private final ReentrantLock acquisitionLock;

    private final Constructor<T> constructor;

    @SuppressWarnings("unchecked")
    KExtensibleObjectPool(
        Class<T> clazz,
        int initialSize,
        int maxSize,
        float extensionFactor
    ) {

        this.clazz = clazz;

        this.maxSize = maxSize;
        this.extensionFactor = extensionFactor;

        this.acquisitionLock = new ReentrantLock();
        this.conveyor = KQueue.create(clazz, initialSize, extensionFactor);

        var constructor = Objects.requireNonNull(KReflectionUtils.getConstructor(clazz));
        for (int i = 0; i < initialSize; i++) {
            T object = KReflectionUtils.newInstance(constructor);
            this.conveyor.add(object);
        }

        // goofy ahh Java limitation bypassing
        var arr = new PoolableArray<>(clazz, 0);
        this.arrayConveyor = (KQueue<PoolableArray<T>>) KQueue.create(arr.getClass(), KObjectPool.ARRAY_INSTANTIATIONS);
        for (int i = 0; i < KObjectPool.ARRAY_INSTANTIATIONS; i++) {
            this.arrayConveyor.add(new PoolableArray<>(clazz, KObjectPool.PRE_ALLOCATED_ARRAY_SIZE));
        }

        this.constructor = constructor;
    }

    @Override
    public T obtain() {
        this.acquisitionLock.lock();
        try {
            if (this.conveyor.isEmpty()) {
                this.extend();
            }

            if (this.conveyor.isEmpty()) {
                throw new KEmptyObjectPoolException(this.clazz);
            }

            return Objects.requireNonNull(this.conveyor.poll());
        } finally {
            this.acquisitionLock.unlock();
        }
    }

    @Override
    public @Nullable T obtainSafe() {
        this.acquisitionLock.lock();
        try {
            if (this.conveyor.isEmpty()) {
                this.extend();
            }

            return this.conveyor.poll();
        } finally {
            this.acquisitionLock.unlock();
        }
    }

    @Override
    public KArray<T> obtainMany(int count) {
        this.acquisitionLock.lock();
        try {
            if (this.conveyor.size() < count) {
                // it actually just adds elements in order to extend queue but the fuck is this?
                this.extend();
            }

            if (this.conveyor.size() < count) {
                throw new KEmptyObjectPoolException(this.clazz);
            }

            if (count > KObjectPool.PRE_ALLOCATED_ARRAY_SIZE) {
                var arr = new PoolableArray<>(this.clazz, count);
                for (int i = 0; i < count; i++) {
                    arr.set(i, this.obtain());
                }
                return arr;
            }

            var arr = this.arrayConveyor.isEmpty() ? new PoolableArray<>(
                this.clazz,
                count
            ) : Objects.requireNonNull(this.arrayConveyor.poll());
            arr.setLimit(count);
            for (int i = 0; i < count; i++) {
                arr.set(i, this.obtain());
            }
            return arr;
        } finally {
            this.acquisitionLock.unlock();
        }
    }

    @Override
    public void release(final T object) {
        this.acquisitionLock.lock();
        try {
            object.reset();
            if (this.conveyor.size() + 1 > this.maxSize) {
                return;
            }

            this.conveyor.add(object);
        } finally {
            this.acquisitionLock.unlock();
        }
    }

    @Override
    public void releaseAll(final KIterable<T> objects) {
        this.acquisitionLock.lock();
        try {
            for (var obj: objects) {
                obj.reset();
                if (this.conveyor.size() + 1 > this.maxSize) {
                    return;
                }

                this.conveyor.add(obj);
            }
        } finally {
            this.acquisitionLock.unlock();
        }
    }

    private void extend() {
        int currentSize = this.conveyor.size();
        if (currentSize >= this.maxSize) {
            return;
        }

        int newSize = Math.min(
            (int) ((float) currentSize * this.extensionFactor),
            this.maxSize
        );

        int diff = newSize - currentSize;
        for (int i = 0; i < diff; i++) {
            T object = KReflectionUtils.newInstance(this.constructor);

            this.conveyor.add(object);
        }
    }

}
