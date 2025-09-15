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
import io.github.darthakiranihil.konna.core.log.KLogger;
import io.github.darthakiranihil.konna.core.object.except.KDeletionException;
import io.github.darthakiranihil.konna.core.object.except.KInstantiationException;
import io.github.darthakiranihil.konna.core.object.except.KInvalidPoolableClassException;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class KObjectPool<T> extends KObject {

    private static Unsafe theUnsafe;

    static {
        try {
            Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeField.setAccessible(true);
            KObjectPool.theUnsafe = (Unsafe) theUnsafeField.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            KLogger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    private final List<T> objects;
    private final Queue<T> unusedObjects;

    private final Method onObjectObtain;
    private final Method onObjectRelease;

    private final Class<T> clazz;
    private final Class<?>[] onObtainParameterClasses;

    @SuppressWarnings("unchecked")
    public KObjectPool(final Class<T> clazz, int initialSize) {
        this.clazz = clazz;
        this.objects = new CopyOnWriteArrayList<>();
        this.unusedObjects = new ConcurrentLinkedQueue<>();

        Method onObtain = null;
        Method onRelease = null;
        for (var method: clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(KOnPoolableObjectObtain.class)) {
                method.setAccessible(true);
                onObtain = method;
            }
            if (method.isAnnotationPresent(KOnPoolableObjectRelease.class)) {
                method.setAccessible(true);
                onRelease = method;
            }

            if (onObtain != null && onRelease != null) {
                break;
            }
        }

        if (onObtain == null || onRelease == null) {
            throw new KInvalidPoolableClassException(
                String.format(
                    "Cannot create pool for %s -"
                    + " the class does not have both methods invoked"
                    + " for obtaining and releasing the object",
                    clazz
                )
            );
        }

        this.onObtainParameterClasses = onObtain.getParameterTypes();

        this.onObjectObtain = onObtain;
        this.onObjectRelease = onRelease;

        for (int i = 0; i < initialSize; i++) {
            T object;
            try {
                object = (T) clazz.getConstructor().newInstance();
                // object = (T) KObjectPool.theUnsafe.allocateInstance(clazz);
            } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new KInstantiationException(clazz, e);
            }
            this.objects.add(object);
            this.unusedObjects.add(object);
        }
    }

    public T obtain(final KContainer container, final Object... args) {

        var obtained = this.unusedObjects.peek();
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

        this.unusedObjects.add(object);

    }


}
