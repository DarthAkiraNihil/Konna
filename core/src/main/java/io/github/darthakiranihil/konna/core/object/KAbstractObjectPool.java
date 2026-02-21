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

import io.github.darthakiranihil.konna.core.object.KOnPoolableObjectObtain;
import io.github.darthakiranihil.konna.core.object.KOnPoolableObjectRelease;
import io.github.darthakiranihil.konna.core.di.KContainer;
import org.jspecify.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Abstract class for object pools that provide opportunity
 * to create and delete objects without actual creating and deleting them
 * i.e. reuse objects.
 *
 * @param <T> Class to create pool for
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public abstract class KAbstractObjectPool<T> extends KObject {

    /**
     * Reference to the method of pooled class that is called
     * when an object is obtained from the pool.
     */
    protected final @Nullable Method onObjectObtain;
    /**
     * Reference to the method of pooled class that is called
     * when an object is returned to the pool (released).
     */
    protected final @Nullable Method onObjectRelease;

    /**
     * Class of poolable object.
     */
    protected final Class<T> clazz;

    /**
     * Cached parameters of the object obtaining method.
     */
    protected final Class<?> @Nullable[] onObtainParameterClasses;
    /**
     * Cached parameter annotations of the object obtaining method.
     */
    protected final Annotation @Nullable[][] onObtainParameterAnnotations;

    /**
     * Initial pool size, measured in objects stored in the pool.
     */
    protected final int initialSize;

    /**
     * Constructs abstract pool.
     * @param clazz Poolable class
     * @param initialSize Initial size
     */
    public KAbstractObjectPool(final Class<T> clazz, int initialSize) {
        Method onObtain = null;
        Method onRelease = null;
        for (var method: clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(KOnPoolableObjectObtain.class)) {
                method.setAccessible(true);
                onObtain = method;
                continue;
            }

            if (method.isAnnotationPresent(KOnPoolableObjectRelease.class)) {
                method.setAccessible(true);
                onRelease = method;
                continue;
            }

            if (onObtain != null && onRelease != null) {
                break;
            }
        }

        if (onObtain != null) {
            this.onObtainParameterClasses = onObtain.getParameterTypes();
            this.onObtainParameterAnnotations = onObtain.getParameterAnnotations();
        } else {
            this.onObtainParameterClasses = null;
            this.onObtainParameterAnnotations = null;
        }

        this.onObjectObtain = onObtain;
        this.onObjectRelease = onRelease;
        this.initialSize = initialSize;
        this.clazz = clazz;

    }

    /**
     * Obtains an object from the pool. Calling this is a risky operation
     * if pool is not extensible and behaviour of getting object from an
     * empty pool is not specified.
     * @param container Container (for resolving dependencies for onObtain method)
     * @param nonResolvedArgs Arguments that are not resolved
     *                        (passed explicitly) for onObtain method
     * @return A pooled object
     * @throws io.github.darthakiranihil.konna.core.object.except.KEmptyObjectPoolException
     *         If there is no unused objects in the pool
     * @see io.github.darthakiranihil.konna.core.di.KInject
     */
    public abstract T obtain(
        KContainer container,
        Object... nonResolvedArgs
    );

    /**
     * Returns object back to the pool, making it available to be taken
     * by another requester class (through {@link KActivator}).
     * @param object Object to return
     */
    public abstract void release(T object);

}
