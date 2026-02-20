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

import io.github.darthakiranihil.konna.core.di.KContainer;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.object.except.KDeletionException;
import io.github.darthakiranihil.konna.core.object.except.KEmptyObjectPoolException;
import io.github.darthakiranihil.konna.core.object.except.KInstantiationException;
import io.github.darthakiranihil.konna.core.util.KReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Implementation of {@link KAbstractObjectPool} that represents
 * object pool in its classic meaning.
 * @param <T> Class of poolable object
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public class KObjectPool<T> extends KAbstractObjectPool<T> {

    private final Queue<T> unusedObjects;
    private final KActivator activator;

    public KObjectPool(
        final Class<T> clazz,
        int initialSize,
        final KActivator activator,
        final KObjectRegistry objectRegistry
    ) {
        super(clazz, initialSize);
        this.addTags(KTag.DefaultTags.POOL, KTag.DefaultTags.STD);

        this.unusedObjects = new ConcurrentLinkedQueue<>();
        this.activator = activator;

        for (int i = 0; i < initialSize; i++) {
            var constructor = Objects.requireNonNull(KReflectionUtils.getConstructor(clazz));
            T object = KReflectionUtils.newInstance(constructor);

            this.unusedObjects.add(object);
            if (object instanceof KObject) {
                objectRegistry.pushObjectToRegistry(
                    (KObject) object,
                    KObjectInstantiationType.POOLABLE
                );
            }
        }
    }

    /**
     * Obtains an object from the pool. Calling this is a risky operation
     * if pool is not extensible and behaviour of getting object from an
     * empty pool is not specified.
     * @param container Container (for resolving dependencies for onObtain method)
     * @param nonInjectedArgs Arguments that are not injected
     *                        (passed explicitly) when onObtain method is invoked
     * @return A pooled object
     * @throws KEmptyObjectPoolException If there is no unused objects in the pool
     * @see KInject
     */
    public T obtain(
        final KContainer container,
        final Object... nonInjectedArgs
    ) {


        if (this.unusedObjects.peek() == null) {
            throw new KEmptyObjectPoolException(this.clazz);
        }
        var obtained = this.unusedObjects.peek();

        // no onObtain - no parameter classes and annotations
        if (
                this.onObjectObtain == null
            ||  this.onObtainParameterClasses == null
            ||  this.onObtainParameterAnnotations == null
        ) {
            this.unusedObjects.poll();
            return obtained;
        }

        try {
            Object[] parameters = new Object[this.onObtainParameterClasses.length];
            int nonResolvedArgsProcessed = 0;

            for (int i = 0; i < this.onObtainParameterClasses.length; i++) {
                boolean isNonResolved = true;
                for (int j = 0; j < this.onObtainParameterAnnotations[i].length; j++) {
                    if (this.onObtainParameterAnnotations[i][j] instanceof KInject) {
                        isNonResolved = false;
                        break;
                    }
                }

                if (isNonResolved) {
                    parameters[i] = nonInjectedArgs[nonResolvedArgsProcessed];
                    nonResolvedArgsProcessed++;
                } else {
                    parameters[i] = this.activator.createObject(
                        this.onObtainParameterClasses[i],
                        container
                    );
                }
            }
            this.onObjectObtain.invoke(obtained, parameters);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new KInstantiationException(clazz, e);
        }

        this.unusedObjects.poll();
        return obtained;

    }

    /**
     * Returns object back to the pool, making it available to be taken
     * by another requester class (through {@link KActivator}).
     * @param object Object to return
     */
    public void release(final T object) {

        if (this.onObjectRelease != null) {
            KReflectionUtils.invokeMethod(this.onObjectRelease, object);
        }
        this.unusedObjects.add(object);

    }


}
