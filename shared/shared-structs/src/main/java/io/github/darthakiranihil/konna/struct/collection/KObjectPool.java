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

public interface KObjectPool<T extends KPoolable> {

    int ARRAY_INSTANTIATIONS = 4;
    int PRE_ALLOCATED_ARRAY_SIZE = 16;

    static <T extends KPoolable> KObjectPool<T> createFixed(
        final Class<T> clazz,
        int size
    ) {
        return new KFixedObjectPool<>(clazz, size);
    }

    static <T extends KPoolable> KObjectPool<T> createExtensible(
        final Class<T> clazz,
        int initialSize,
        int maxSize,
        float extensionFactor
    ) {
        return new KExtensibleObjectPool<>(clazz, initialSize, maxSize, extensionFactor);
    }

    static <T extends KPoolable> KObjectPool<T> createExtensible(
        final Class<T> clazz,
        int initialSize,
        int maxSize
    ) {
        return KObjectPool.createExtensible(clazz, initialSize, maxSize, KCollection.DEFAULT_EXTENSION_FACTOR);
    }

    static <T extends KPoolable> KObjectPool<T> createExtensible(
        final Class<T> clazz,
        int initialSize
    ) {
        return KObjectPool.createExtensible(clazz, initialSize, Integer.MAX_VALUE);
    }

    T obtain();
    @Nullable T obtainSafe();
    KArray<T> obtainMany(int count);

    void release(T object);
    void releaseAll(KIterable<T> objects);

}
