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

import io.github.darthakiranihil.konna.core.except.KNoSuchElementException;
import org.jspecify.annotations.Nullable;

public final class KObtainedPoolableObject<T extends KPoolable> implements AutoCloseable {

    private final @Nullable T object;
    private final KObjectPool<T> pool;

    public KObtainedPoolableObject(final KObjectPool<T> pool) {
        this(null, pool);
    }

    public KObtainedPoolableObject(final @Nullable T object, final KObjectPool<T> pool) {
        this.object = object;
        this.pool = pool;
    }

    public boolean isPresent() {
        return this.object != null;
    }

    public T get() {
        if (this.object == null) {
            throw new KNoSuchElementException("Poolable object is not provided");
        }

        return this.object;
    }

    @Override
    public void close() {
        if (this.object != null) {
            this.pool.release(this.object);
        }
    }
}
