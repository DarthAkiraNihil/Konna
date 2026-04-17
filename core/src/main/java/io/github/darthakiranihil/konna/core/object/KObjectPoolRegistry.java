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

/**
 * Special interface for getting poolable object without direct accessing
 * to their pools.
 *
 * @since 0.6.0
 * @since Darth Akira Nihil
 */
public interface KObjectPoolRegistry {

    /**
     * <p>
     *     Obtains an object from the pool.
     * </p>
     * <p>
     *     If pool's NoObjectPolicy is {@link KAllocatePool.NoObjectPolicy#THROW_EXCEPTION}, then
     *     {@link io.github.darthakiranihil.konna.core.object.except.KEmptyObjectPoolException}
     *     will be thrown, else empty instance of {@link KObtainedPoolableObject}.
     * </p>
     * @param clazz Class of object to obtain
     * @param <T> type of poolable object
     * @return Container of obtained poolable object
     * @throws io.github.darthakiranihil.konna.core.except.KNoSuchElementException if there is no
     *         pool associated with specified class
     */
    <T extends KPoolable> KObtainedPoolableObject<T> obtain(Class<T> clazz);
    /**
     * <p>
     *     Obtains an object from the pool, but if there is no object, it firstly waits
     *     for {@code timeout} seconds, then tries to obtain an object again.
     * </p>
     * <p>
     *     If pool's NoObjectPolicy is {@link KAllocatePool.NoObjectPolicy#THROW_EXCEPTION}, then
     *     {@link io.github.darthakiranihil.konna.core.object.except.KEmptyObjectPoolException}
     *     will be thrown, else empty instance of {@link KObtainedPoolableObject}.
     * </p>
     * @param clazz Class of object to obtain
     * @param timeout Amount of seconds to wait before the second acquisition attempt
     * @param <T> type of poolable object
     * @return Container of obtained poolable object
     * @throws io.github.darthakiranihil.konna.core.except.KNoSuchElementException if there is no
     *         pool associated with specified class
     */
    <T extends KPoolable> KObtainedPoolableObject<T> obtain(Class<T> clazz, int timeout);

    /**
     * <p>
     *     Obtains an object from the pool with passing explicit args, that are not
     *     injected by {@link KActivator}.
     * </p>
     * <p>
     *     If pool's NoObjectPolicy is {@link KAllocatePool.NoObjectPolicy#THROW_EXCEPTION}, then
     *     {@link io.github.darthakiranihil.konna.core.object.except.KEmptyObjectPoolException}
     *     will be thrown, else empty instance of {@link KObtainedPoolableObject}.
     * </p>
     * @param clazz Class of object to obtain
     * @param explicitArgs Explicit args to pass to {@link KOnPoolableObjectObtain}-annotated
     *                     method
     * @param <T> type of poolable object
     * @return Container of obtained poolable object
     * @throws io.github.darthakiranihil.konna.core.except.KNoSuchElementException if there is no
     *         pool associated with specified class
     */
    <T extends KPoolable> KObtainedPoolableObject<T> obtain(Class<T> clazz, KArgs explicitArgs);

    /**
     * <p>
     *     Obtains an object from the pool, with passing explicit args, that are not
     *     injected by {@link KActivator}, but if there is no object, it firstly waits
     *     for {@code timeout} seconds, then tries to obtain an object again.
     * </p>
     * <p>
     *     If pool's NoObjectPolicy is {@link KAllocatePool.NoObjectPolicy#THROW_EXCEPTION}, then
     *     {@link io.github.darthakiranihil.konna.core.object.except.KEmptyObjectPoolException}
     *     will be thrown, else empty instance of {@link KObtainedPoolableObject}.
     * </p>
     * @param clazz Class of object to obtain
     * @param timeout Amount of seconds to wait before the second acquisition attempt
     * @param explicitArgs Explicit args to pass to {@link KOnPoolableObjectObtain}-annotated
     *                     method
     * @param <T> type of poolable object
     * @return Container of obtained poolable object
     * @throws io.github.darthakiranihil.konna.core.except.KNoSuchElementException if there is no
     *         pool associated with specified class
     */
    <T extends KPoolable> KObtainedPoolableObject<T> obtain(
        Class<T> clazz, KArgs explicitArgs, int timeout
    );

}
