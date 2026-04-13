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

import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.except.KNoSuchElementException;
import io.github.darthakiranihil.konna.core.util.KClasspathSearchEngine;

import java.util.HashMap;
import java.util.Map;

public class KAutodiscoveringObjectPoolRegistry implements KObjectPoolRegistry {

    private final Map<Class<? extends KPoolable>, KObjectPool<? extends KPoolable>> pools;

    @KInject
    @SuppressWarnings("unchecked")
    private KAutodiscoveringObjectPoolRegistry(
        final KClasspathSearchEngine classpath,
        final KActivator activator,
        final KObjectRegistry objectRegistry
    ) {
        try (var poolables = classpath
            .query()
            .implementsInterface(KPoolable.class)
            .withAnnotation(KPoolMetadata.class)
            .execute()
        ) {
            var loadedPoolables = poolables
                .loadClasses()
                .stream()
                .map(x -> (Class<? extends KPoolable>) x)
                .toList();

            this.pools = new HashMap<>(loadedPoolables.size());
            for (var loadedPoolable: loadedPoolables) {
                KObjectPool<? extends KPoolable> pool = KObjectPool.create(
                    loadedPoolable,
                    activator,
                    objectRegistry,
                    loadedPoolable.getAnnotation(KPoolMetadata.class)
                );

                this.pools.put(loadedPoolable, pool);
                objectRegistry.pushObject(pool);
            }
        }

    }

    @Override
    public <T extends KPoolable> KObtainedPoolableObject<T> obtain(Class<T> clazz) {
        return this.getPool(clazz).obtain();
    }

    @Override
    public <T extends KPoolable> KObtainedPoolableObject<T> obtain(Class<T> clazz, int timeout) {
        return this.getPool(clazz).obtain(timeout);
    }

    @Override
    public <T extends KPoolable> KObtainedPoolableObject<T> obtain(
        Class<T> clazz,
        KArgs explicitArgs
    ) {
        return this.getPool(clazz).obtain(explicitArgs);
    }

    @Override
    public <T extends KPoolable> KObtainedPoolableObject<T> obtain(
        Class<T> clazz,
        KArgs explicitArgs,
        int timeout
    ) {
        return this.getPool(clazz).obtain(explicitArgs, timeout);
    }

    @SuppressWarnings("unchecked")
    private <T extends KPoolable> KObjectPool<T> getPool(Class<T> clazz) {
        if (!this.pools.containsKey(clazz)) {
            throw new KNoSuchElementException(
                String.format("Pool for class %s is not found", clazz)
            );
        }

        return (KObjectPool<T>) this.pools.get(clazz);
    }
}
