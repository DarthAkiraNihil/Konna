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
import io.github.darthakiranihil.konna.core.util.KReflectionUtils;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.Collections;

/**
 * <p>
 *     An object pool with its classic meaning.
 * </p>
 * <p>
 *     This class' instances should be not created by hand (even its constructor is protected),
 *     as its allocation is up to {@link KObjectPoolRegistry}. However, it is still possible with
 *     {@link KObjectPool#create(Class, KActivator, KObjectRegistry, KAllocatePool)}.
 * </p>
 *
 * @param <T> Type of poolable object
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public abstract sealed class KObjectPool<T extends KPoolable>
    extends KObject
    permits
        KFixedObjectPool,
        KExtensibleObjectPool {

    /**
     * Creates a new pool based on {@link KAllocatePool }metadata.
     * @param clazz Class of poolable object
     * @param activator Activator to pass it to the pool
     * @param objectRegistry Object registry to pass it to the pool
     * @param metadata Pool allocation metadata
     * @return Created pool for specified class
     * @param <T> Type of poolable object
     *
     * @since 0.6.0
     */
    public static <T extends KPoolable> KObjectPool<T> create(
        final Class<T> clazz,
        final KActivator activator,
        final KObjectRegistry objectRegistry,
        final KAllocatePool metadata
    ) {
        if (metadata.extensible()) {
            return switch (metadata.noObjectPolicy()) {
                case THROW_EXCEPTION -> new KExtensibleObjectPool<>(
                    clazz,
                    activator,
                    objectRegistry,
                    metadata.initialSize(),
                    metadata.maxSize(),
                    metadata.extensionFactor(),
                    false
                );
                case RETURN_EMPTY -> new KExtensibleObjectPool<>(
                    clazz,
                    activator,
                    objectRegistry,
                    metadata.initialSize(),
                    metadata.maxSize(),
                    metadata.extensionFactor(),
                    true
                );
            };
        } else {
            return switch (metadata.noObjectPolicy()) {
                case THROW_EXCEPTION -> new KFixedObjectPool<>(
                    clazz,
                    activator,
                    objectRegistry,
                    metadata.initialSize(),
                    false
                );
                case RETURN_EMPTY -> new KFixedObjectPool<>(
                    clazz,
                    activator,
                    objectRegistry,
                    metadata.initialSize(),
                    true
                );
            };
        }
    }

    /**
     * Type of poolable object.
     */
    protected final Class<?> clazz;

    private final boolean isOnObtainInjected;
    private final @Nullable Method onObjectObtain;
    private final Class<?> @Nullable[] onObtainParameterClasses;
    private final KActivator activator;

    /**
     * Constructs the pool.
     * @param poolQualifier Additional pool qualifier. Just for its name
     * @param clazz Type of poolable object
     * @param activator Activator to inject parameters into
     *        {@link KOnPoolableObjectObtain}-annotated methods
     */
    protected KObjectPool(
        final String poolQualifier,
        final Class<T> clazz,
        final KActivator activator
    ) {
        super(
            String.format("%s.%s", poolQualifier, clazz),
            Collections.singleton(KDefaultTags.POOL)
        );

        Method onObtain = null;
        for (var method: clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(KOnPoolableObjectObtain.class)) {
                method.setAccessible(true);
                onObtain = method;
                break;
            }
        }

        if (onObtain == null) {
            this.onObtainParameterClasses = null;
            this.isOnObtainInjected = false;
        } else {
            this.onObtainParameterClasses = onObtain.getParameterTypes();
            this.isOnObtainInjected = onObtain.isAnnotationPresent(KInject.class);
        }
        this.onObjectObtain = onObtain;

        this.clazz = clazz;
        this.activator = activator;
    }

    /**
     * <p>
     *     Obtains an object from the pool.
     * </p>
     * <p>
     *     If pool's NoObjectPolicy is {@link KAllocatePool.NoObjectPolicy#THROW_EXCEPTION}, then
     *     {@link io.github.darthakiranihil.konna.core.object.except.KEmptyObjectPoolException}
     *     will be thrown, else empty instance of {@link KObtainedPoolableObject}.
     * </p>
     * @return Container of obtained poolable object
     * @since 0.6.0
     */
    public abstract KObtainedPoolableObject<T> obtain();

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
     * @param timeout Amount of seconds to wait before the second acquisition attempt
     * @return Container of obtained poolable object
     * @since 0.6.0
     */
    public abstract KObtainedPoolableObject<T> obtain(int timeout);

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
     * @param explicitArgs Explicit args to pass to {@link KOnPoolableObjectObtain}-annotated
     *                     method
     * @return Container of obtained poolable object
     * @since 0.6.0
     */
    public abstract KObtainedPoolableObject<T> obtain(KArgs explicitArgs);

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
     * @param timeout Amount of seconds to wait before the second acquisition attempt
     * @param explicitArgs Explicit args to pass to {@link KOnPoolableObjectObtain}-annotated
     *                     method
     * @return Container of obtained poolable object
     * @since 0.6.0
     */
    public abstract KObtainedPoolableObject<T> obtain(KArgs explicitArgs, int timeout);

    /**
     * Prepares object before it will be obtained by the requester.
     * @param obtained Object that has been retrieved from pool's storage
     * @return Container of prepared and valid obtained object
     */
    protected final KObtainedPoolableObject<T> prepareObject(final T obtained) {
        if (this.onObjectObtain == null || this.onObtainParameterClasses == null) {
            return new KObtainedPoolableObject<>(obtained, this);
        }

        Object[] args = new Object[this.onObtainParameterClasses.length];
        if (this.isOnObtainInjected) {
            for (int i = 0; i < args.length; i++) {
                args[i] = this.activator.createObject(this.onObtainParameterClasses[i]);
            }
        }

        KReflectionUtils.invokeMethod(
            this.onObjectObtain,
            obtained,
            args
        );

        return new KObtainedPoolableObject<>(obtained, this);
    }

    /**
     * Prepares object before it will be obtained by the requester.
     * @param obtained Object that has been retrieved from pool's storage
     * @param explicitArgs Explicit args to pass to {@link KOnPoolableObjectObtain}-annotated
     *                     method
     * @return Container of prepared and valid obtained object
     */
    protected final KObtainedPoolableObject<T> prepareObject(
        final T obtained,
        final KArgs explicitArgs
    ) {
        if (this.onObjectObtain == null || this.onObtainParameterClasses == null) {
            return new KObtainedPoolableObject<>(obtained, this);
        }

        Object[] args = new Object[this.onObtainParameterClasses.length];
        Object[] explicit = explicitArgs.unpack();

        if (this.isOnObtainInjected) {
            for (int i = 0; i < args.length; i++) {
                args[i] = (i < explicit.length)
                    ? explicit[i]
                    : this.activator.createObject(this.onObtainParameterClasses[i]);
            }
        } else {
            System.arraycopy(explicit, 0, args, 0, explicit.length);
        }

        KReflectionUtils.invokeMethod(
            this.onObjectObtain,
            obtained,
            args
        );

        return new KObtainedPoolableObject<>(obtained, this);
    }


    /**
     * Returns object back to the pool, making it available to be taken by another class.
     * @param object Object to return
     */
    @MustBeInvokedByOverriders
    void release(final T object) {
        object.reset();
    }

}
