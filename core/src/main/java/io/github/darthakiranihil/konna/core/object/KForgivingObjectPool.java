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

import io.github.darthakiranihil.konna.core.util.KReflectionUtils;
import io.github.darthakiranihil.konna.core.util.KThreadUtils;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

final class KForgivingObjectPool<T extends KPoolable> extends KObjectPool<T> {
    private final Queue<T> unusedObjects;

    @SuppressWarnings("unchecked")
    KForgivingObjectPool(
        final Class<T> clazz,
        int size,
        final KActivator activator,
        final KObjectRegistry objectRegistry
    ) {
        super("StrictPool", clazz, size, activator, objectRegistry);
        this.unusedObjects = new ArrayBlockingQueue<>(size);

        for (int i = 0; i < size; i++) {
            var constructor = Objects.requireNonNull(KReflectionUtils.getConstructor(this.clazz));
            T object = (T) KReflectionUtils.newInstance(constructor);

            this.unusedObjects.add(object);
            objectRegistry.pushObject(object);
        }
    }

    @Override
    public KObtainedPoolableObject<T> obtain() {
        if (this.unusedObjects.isEmpty()) {
            return new KObtainedPoolableObject<>(this);
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
            return new KObtainedPoolableObject<>(this);
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
        this.unusedObjects.add(object);
    }

}
