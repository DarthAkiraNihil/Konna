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
import io.github.darthakiranihil.konna.core.object.except.KDeletionException;
import io.github.darthakiranihil.konna.core.object.except.KEmptyObjectPoolException;
import io.github.darthakiranihil.konna.core.object.except.KInstantiationException;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class KWeakObjectPool<T> extends KAbstractObjectPool<T> {

    private final Queue<WeakReference<T>> unusedObjects;

    public KWeakObjectPool(final Class<T> clazz, int initialSize) {
        super(clazz, initialSize);
        this.unusedObjects = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < initialSize; i++) {
            T object;
            try {
                object = clazz.getConstructor().newInstance();
            } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new KInstantiationException(clazz, e);
            }
            this.unusedObjects.add(new WeakReference<>(object));
        }
    }

    public T obtain(final KContainer container) throws KEmptyObjectPoolException {

        var ref = this.unusedObjects.peek();
        if (ref == null) {
            throw new KEmptyObjectPoolException(this.clazz);
        }
        T obtained = ref.get();
        if (obtained == null) {
            try {
                obtained = clazz.getConstructor().newInstance();
            } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new KInstantiationException(clazz, e);
            }
        }

        try {
            Object[] parameters = new Object[this.onObtainParameterClasses.length];
            for (int i = 0; i < this.onObtainParameterClasses.length; i++) {
                parameters[i] = KActivator.create(this.onObtainParameterClasses[i], container);
            }
            this.onObjectObtain.invoke(obtained, parameters);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new KInstantiationException(clazz, e);
        }

        this.unusedObjects.poll();
        return obtained;

    }

    public void release(final T object) {

        try {
            this.onObjectRelease.invoke(object);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new KDeletionException(object, e.getMessage());
        }

        this.unusedObjects.add(new WeakReference<>(object));

    }


}
