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
import io.github.darthakiranihil.konna.core.object.except.KEmptyObjectPoolException;
import io.github.darthakiranihil.konna.core.object.except.KInvalidPoolableClassException;

import java.lang.reflect.Method;

public abstract class KAbstractObjectPool<T> extends KObject {

    protected final Method onObjectObtain;
    protected final Method onObjectRelease;

    protected final Class<T> clazz;
    protected final Class<?>[] onObtainParameterClasses;

    protected final int initialSize;

    public KAbstractObjectPool(final Class<T> clazz, int initialSize) {
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

        if ((onObtain == null) != (onRelease == null)) {
            throw new KInvalidPoolableClassException(
                String.format(
                    "Cannot create pool for %s -"
                        + " the class must have either both methods invoked"
                        + " for obtaining and releasing the object or none",
                    clazz
                )
            );
        }

        if (onObtain != null) {
            this.onObtainParameterClasses = onObtain.getParameterTypes();
        } else {
            this.onObtainParameterClasses = null;
        }

        this.onObjectObtain = onObtain;
        this.onObjectRelease = onRelease;
        this.initialSize = initialSize;
        this.clazz = clazz;

    }

    public abstract T obtain(KContainer container) throws KEmptyObjectPoolException;
    public abstract void release(T object);

}
