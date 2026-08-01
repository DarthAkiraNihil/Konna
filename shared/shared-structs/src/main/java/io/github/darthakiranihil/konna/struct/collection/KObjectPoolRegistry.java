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
import io.github.darthakiranihil.konna.struct.object.KPoolable;

import java.util.HashMap;
import java.util.Map;

public final class KObjectPoolRegistry {

    private static final int INITIAL_CAPACITY = 8;

    // todo: make work with custom maps
    private final Map<Class<? extends KPoolable>, KObjectPool<? extends KPoolable>> pools;

    public KObjectPoolRegistry() {
        this.pools = new HashMap<>(INITIAL_CAPACITY);
    }

    public <T extends KPoolable> void add(final Class<T> clazz, final KObjectPool<T> pool) {
        this.pools.put(clazz, pool);
    }

    @SuppressWarnings("unchecked")
    public <T extends KPoolable> T obtain(final Class<T> clazz) {
        if (!this.pools.containsKey(clazz)) {
            throw new KInvalidArgumentException(String.format(
                "No pool of class %s", clazz
            ));
        }

        return ((KObjectPool<T>) this.pools.get(clazz)).obtain();
    }

    @SuppressWarnings("unchecked")
    public <T extends KPoolable> KArray<T> obtainMany(final Class<T> clazz, int count) {
        if (!this.pools.containsKey(clazz)) {
            throw new KInvalidArgumentException(String.format(
                "No pool of class %s", clazz
            ));
        }

        return ((KObjectPool<T>) this.pools.get(clazz)).obtainMany(count);
    }

    @SuppressWarnings("unchecked")
    public <T extends KPoolable> void release(final T object) {
        var clazz = object.getClass();
        if (!this.pools.containsKey(clazz)) {
            throw new KInvalidArgumentException(String.format(
                "No pool of class %s", clazz
            ));
        }

        ((KObjectPool<T>) this.pools.get(clazz)).release(object);
    }

    @SuppressWarnings("unchecked")
    public <T extends KPoolable> void releaseAll(final KIterable<T> objects) {
        var it = objects.iterator();
        T object = it.next();
        var clazz = object.getClass();
        if (!this.pools.containsKey(clazz)) {
            throw new KInvalidArgumentException(String.format(
                "No pool of class %s", clazz
            ));
        }

        KObjectPool<T> pool = (KObjectPool<T>) this.pools.get(clazz);
        pool.release(object);
        while (it.hasNext()) {
            pool.release(it.next());
        }
    }

    public void flush() {
        this.pools.clear();
    }

}
