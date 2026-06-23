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

import io.github.darthakiranihil.konna.struct.object.KPoolable;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

final class KFixedObjectPool<T extends KPoolable> implements KObjectPool<T> {

    private T[] objects;
    private int nextAvailable;
    private final boolean waitForObject;

    @SuppressWarnings("unchecked")
    KFixedObjectPool(
        Class<T> clazz,
        int size,
        boolean waitForObject
    ) {
        this.objects = (T[]) Array.newInstance(clazz, size);

        for (int i = 0; i < size; i++) {
            var constructor = Objects.requireNonNull(KReflectionUtils.getConstructor(this.clazz));
            T object = (T) KReflectionUtils.newInstance(constructor);

            this.unusedObjects.add(object);
            objectRegistry.pushObject(object);
        }
    }

    @Override
    public T obtain() {
        return null;
    }

    @Override
    public @Nullable T obtainSafe() {
        return null;
    }

    @Override
    public KArray<T> obtainMany(int count) {
        return null;
    }

    @Override
    public void release(T object) {

    }

    @Override
    public void releaseAll(KIterable<T> objects) {

    }
}
