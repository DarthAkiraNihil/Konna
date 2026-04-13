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

package io.github.darthakiranihil.konna.core.object;

import io.github.darthakiranihil.konna.core.object.except.KEmptyObjectPoolException;
import io.github.darthakiranihil.konna.core.util.KReflectionUtils;
import io.github.darthakiranihil.konna.core.util.KThreadUtils;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

final class KExtensibleObjectPool<T extends KPoolable> extends KObjectPool<T> {

    private final Object extensionLock = new Object();

    private int currentSize;
    private final int maxSize;
    private final float extensionFactor;
    private final KObjectRegistry objectRegistry;

    private Queue<T> unusedObjects;

    @SuppressWarnings("unchecked")
    KExtensibleObjectPool(
        final Class<T> clazz,
        int initialSize,
        int maxSize,
        float extensionFactor,
        final KActivator activator,
        final KObjectRegistry objectRegistry
    ) {
        super("ExtensiblePool", clazz, initialSize, activator, objectRegistry);

        this.objectRegistry = objectRegistry;

        this.maxSize = maxSize;
        this.extensionFactor = extensionFactor;
        this.currentSize = initialSize;

        this.unusedObjects = new ArrayBlockingQueue<>(initialSize);
        for (int i = 0; i < initialSize; i++) {
            var constructor = Objects.requireNonNull(KReflectionUtils.getConstructor(this.clazz));
            T object = (T) KReflectionUtils.newInstance(constructor);

            this.unusedObjects.add(object);
            objectRegistry.pushObject(object);
        }
    }

    @SuppressWarnings("unchecked")
    private void extend() {
        if (this.currentSize >= this.maxSize) {
            return;
        }

        int newSize = Math.min((int) ((float) this.currentSize * this.extensionFactor), this.maxSize);
        synchronized (this.extensionLock) {
            this.unusedObjects.clear();
            this.unusedObjects = new ArrayBlockingQueue<>(newSize);
        }

        int diff = newSize - this.currentSize;
        for (int i = 0; i < diff; i++) {
            var constructor = Objects.requireNonNull(KReflectionUtils.getConstructor(this.clazz));
            T object = (T) KReflectionUtils.newInstance(constructor);

            this.unusedObjects.add(object);
            this.objectRegistry.pushObject(object);
        }

        this.currentSize = newSize;
    }

    @Override
    public KObtainedPoolableObject<T> obtain() {
        if (this.unusedObjects.isEmpty()) {
            this.extend();
        }

        if (this.unusedObjects.isEmpty()) {
            throw new KEmptyObjectPoolException(this.clazz);
        }

        T obtained = this.unusedObjects.poll();
        return this.prepareObject(obtained);
    }

    @Override
    public KObtainedPoolableObject<T> obtain(int timeout) {
        if (this.unusedObjects.isEmpty()) {
            KThreadUtils.sleepForSeconds(timeout);
        }

        return this.obtain();
    }

    @Override
    public KObtainedPoolableObject<T> obtain(final KArgs args) {
        if (this.unusedObjects.isEmpty()) {
            this.extend();
        }

        if (this.unusedObjects.isEmpty()) {
            throw new KEmptyObjectPoolException(this.clazz);
        }

        T obtained = this.unusedObjects.poll();
        return this.prepareObject(obtained, args);
    }

    @Override
    public KObtainedPoolableObject<T> obtain(final KArgs args, int timeout) {
        if (this.unusedObjects.isEmpty()) {
            KThreadUtils.sleepForSeconds(timeout);
        }

        return this.obtain(args);
    }

    @Override
    void release(T object) {
        super.release(object);
        synchronized (this.extensionLock) {
            this.unusedObjects.add(object);
        }
    }

}
