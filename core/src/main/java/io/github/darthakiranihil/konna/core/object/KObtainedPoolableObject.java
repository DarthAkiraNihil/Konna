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

/**
 * <p>
 *     Container of poolable object that represents poolable objects usage semantics.
 * </p>
 * <p>
 *     It is designed to be used with try-with-resources construction for guaranteed
 *     returning obtained object to its pool as soon it is not required. However,
 *     you still can just store this object and close it later. It will be returned
 *     to pool in any case, but if the object is being holding for a long time,
 *     it may affect other poolable object consumers.
 * </p>
 * @param <T> Type of contained poolable object
 *
 * @since 0.6.0
 * @author Darth Akira Nihil
 */
public final class KObtainedPoolableObject<T extends KPoolable> implements AutoCloseable {

    private final @Nullable T object;
    private final KObjectPool<T> pool;

    KObtainedPoolableObject(final KObjectPool<T> pool) {
        this(null, pool);
    }

    KObtainedPoolableObject(final @Nullable T object, final KObjectPool<T> pool) {
        this.object = object;
        this.pool = pool;
    }

    /**
     * Returns flag that defines if the contained object is actually presented
     * in this container or not. Calling this method before accessing the actual
     * object via {@link KObtainedPoolableObject#get()} is a good practice, though
     * it is a bit senseless if pool's NoObject policy is
   * {@link io.github.darthakiranihil.konna.core.object.KAllocatePool.NoObjectPolicy#RETURN_EMPTY}.
     * @return Whether the obtained object is presented in this container or not
     *         (i.e. has not been obtained)
     */
    public boolean isPresent() {
        return this.object != null;
    }

    /**
     * @return Object contained in this container.
     * @throws KNoSuchElementException if object is not actually presented in this container
     */
    public T get() {
        if (this.object == null) {
            throw new KNoSuchElementException("Poolable object is not provided");
        }

        return this.object;
    }

    /**
     * Returns obtained object to its assigned pool.
     */
    @Override
    public void close() {
        if (this.object != null) {
            this.pool.release(this.object);
        }
    }
}
