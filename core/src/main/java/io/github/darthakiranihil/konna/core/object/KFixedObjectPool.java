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
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Implementation of object pool with fixed size.
 * @param <T> Type of pooled object
 *
 * @since 0.6.0
 * @author Darth Akira Nihil
 */
final class KFixedObjectPool<T extends KPoolable> extends KObjectPool<T> {

    private final Queue<T> unusedObjects;
    private final KNoObjectPolicy noObjectPolicy;

    @SuppressWarnings("unchecked")
    KFixedObjectPool(
        final Class<T> clazz,
        final KActivator activator,
        final KObjectRegistry objectRegistry,
        KNoObjectPolicy noObjectPolicy,
        int size
    ) {
        super(
            String.format("Fixed%sPool", KNoObjectPolicy.getTypeQualifier(noObjectPolicy)),
            clazz,
            activator
        );

        this.noObjectPolicy = noObjectPolicy;
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

        Optional<T> obtained = this.noObjectPolicy.obtainRawObject(this.unusedObjects, this.clazz);
        return obtained
            .map(this::prepareObject)
            .orElseGet(() -> new KObtainedPoolableObject<>(this));

    }

    @Override
    public KObtainedPoolableObject<T> obtain(final KArgs args) {

        Optional<T> obtained = this.noObjectPolicy.obtainRawObject(this.unusedObjects, this.clazz);
        return obtained
            .map((x) -> this.prepareObject(x, args))
            .orElseGet(() -> new KObtainedPoolableObject<>(this));

    }

    @Override
    void release(final T object) {
        super.release(object);
        this.unusedObjects.add(object);
    }

}
