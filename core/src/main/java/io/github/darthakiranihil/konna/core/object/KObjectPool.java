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
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.Collections;

/**
 * Implementation that represents
 * object pool in its classic meaning.
 * @param <T> Class of poolable object
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public abstract sealed class KObjectPool<T extends KPoolable>
    extends KObject
    permits
        KFixedObjectPool,
        KExtensibleObjectPool {

    public static <T extends KPoolable> KObjectPool<T> create(
        final Class<T> clazz,
        final KActivator activator,
        final KObjectRegistry objectRegistry,
        final KPoolMetadata metadata
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

    protected final Class<?> clazz;
    private final @Nullable Method onObjectObtain;
    private final Class<?> @Nullable[] onObtainParameterClasses;
    private final KActivator activator;

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

        this.onObtainParameterClasses = onObtain == null
            ? null
            : onObtain.getParameterTypes();
        this.onObjectObtain = onObtain;

        this.clazz = clazz;
        this.activator = activator;
    }

    public abstract KObtainedPoolableObject<T> obtain();
    public abstract KObtainedPoolableObject<T> obtain(int timeout);
    public abstract KObtainedPoolableObject<T> obtain(KArgs explicitArgs);
    public abstract KObtainedPoolableObject<T> obtain(KArgs explicitArgs, int timeout);

    protected final KObtainedPoolableObject<T> prepareObject(T obtained) {
        if (this.onObjectObtain == null || this.onObtainParameterClasses == null) {
            return new KObtainedPoolableObject<>(obtained, this);
        }

        Object[] args = new Object[this.onObtainParameterClasses.length];
        for (int i = 0; i < args.length; i++) {
            args[i] = this.activator.createObject(this.onObtainParameterClasses[i]);
        }

        KReflectionUtils.invokeMethod(
            this.onObjectObtain,
            obtained,
            args
        );

        return new KObtainedPoolableObject<>(obtained, this);
    }

    protected final KObtainedPoolableObject<T> prepareObject(final T obtained, final KArgs explicitArgs) {
        if (this.onObjectObtain == null || this.onObtainParameterClasses == null) {
            return new KObtainedPoolableObject<>(obtained, this);
        }

        Object[] args = new Object[this.onObtainParameterClasses.length];
        Object[] explicit = explicitArgs.unpack();

        for (int i = 0; i < args.length; i++) {
            args[i] = (i < explicit.length)
                ? explicit[i]
                : this.activator.createObject(this.onObtainParameterClasses[i]);
        }

        KReflectionUtils.invokeMethod(
            this.onObjectObtain,
            obtained,
            args
        );

        return new KObtainedPoolableObject<>(obtained, this);
    }


    /**
     * Returns object back to the pool, making it available to be taken
     * by another requester class (through {@link KActivator}).
     * @param object Object to return
     */
    @MustBeInvokedByOverriders
    void release(final T object) {
        object.reset();
    }

}
