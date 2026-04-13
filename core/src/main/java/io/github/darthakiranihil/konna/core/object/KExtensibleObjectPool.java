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

import java.lang.reflect.Constructor;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Implementation of pool, that extends on no free objects provided.
 * @param <T> Type of pooled object
 *
 * @since 0.6.0
 * @author Darth Akira Nihil
 */
final class KExtensibleObjectPool<T extends KPoolable> extends KObjectPool<T> {

    private final Object extensionLock = new Object();

    private int currentSize;
    private final int maxSize;
    private final float extensionFactor;

    private final Constructor<T> constructor;
    private final KObjectRegistry objectRegistry;
    private final boolean allowObtainOnEmpty;

    private Queue<T> unusedObjects;

    @SuppressWarnings("unchecked")
    KExtensibleObjectPool(
        final Class<T> clazz,
        final KActivator activator,
        final KObjectRegistry objectRegistry,
        int initialSize,
        int maxSize,
        float extensionFactor,
        boolean allowObtainOnEmpty
    ) {
        super(
            allowObtainOnEmpty ? "ExtensibleForgivingPool" : "ExtensibleStrictPool",
            clazz,
            activator
        );

        this.allowObtainOnEmpty = allowObtainOnEmpty;
        this.objectRegistry = objectRegistry;

        this.maxSize = maxSize;
        this.extensionFactor = extensionFactor;
        this.currentSize = initialSize;

        this.unusedObjects = new ArrayBlockingQueue<>(initialSize);
        this.constructor = (Constructor<T>) Objects.requireNonNull(
            KReflectionUtils.getConstructor(this.clazz)
        );

        for (int i = 0; i < initialSize; i++) {
            T object = KReflectionUtils.newInstance(this.constructor);
            this.unusedObjects.add(object);
            objectRegistry.pushObject(object);
        }
    }

    private void extend() {
        if (this.currentSize >= this.maxSize) {
            return;
        }

        int newSize = Math.min(
            (int) ((float) this.currentSize * this.extensionFactor),
            this.maxSize
        );
        synchronized (this.extensionLock) {
            this.unusedObjects.clear();
            this.unusedObjects = new ArrayBlockingQueue<>(newSize);
        }

        int diff = newSize - this.currentSize;
        for (int i = 0; i < diff; i++) {
            T object = (T) KReflectionUtils.newInstance(this.constructor);

            this.unusedObjects.add(object);
            this.objectRegistry.pushObject(object);
        }

        this.currentSize = newSize;
    }

    @Override
    public KObtainedPoolableObject<T> obtain() {
        return this.obtain(false);
    }

    @Override
    public KObtainedPoolableObject<T> obtain(int timeout) {
        if (this.unusedObjects.isEmpty()) {
            this.extend();
            KThreadUtils.sleepForSeconds(timeout);
        }

        return this.obtain(true);
    }

    @Override
    public KObtainedPoolableObject<T> obtain(final KArgs args) {
        return this.obtain(args, false);
    }

    @Override
    public KObtainedPoolableObject<T> obtain(final KArgs args, int timeout) {
        if (this.unusedObjects.isEmpty()) {
            this.extend();
            KThreadUtils.sleepForSeconds(timeout);
        }

        return this.obtain(args, true);
    }

    @Override
    void release(final T object) {
        super.release(object);
        synchronized (this.extensionLock) {
            this.unusedObjects.add(object);
        }
    }

    private KObtainedPoolableObject<T> obtain(boolean doNotExtend) {
        if (this.unusedObjects.isEmpty() && !doNotExtend) {
            this.extend();
        }

        if (this.unusedObjects.isEmpty()) {
            if (this.allowObtainOnEmpty) {
                return new KObtainedPoolableObject<>(this);
            }

            throw new KEmptyObjectPoolException(this.clazz);
        }

        T obtained = this.unusedObjects.poll();
        return this.prepareObject(obtained);
    }

    private KObtainedPoolableObject<T> obtain(final KArgs explicitArgs, boolean doNotExtend) {
        if (this.unusedObjects.isEmpty() && !doNotExtend) {
            this.extend();
        }

        if (this.unusedObjects.isEmpty()) {
            if (this.allowObtainOnEmpty) {
                return new KObtainedPoolableObject<>(this);
            }

            throw new KEmptyObjectPoolException(this.clazz);
        }

        T obtained = this.unusedObjects.poll();
        return this.prepareObject(obtained, explicitArgs);
    }

}
