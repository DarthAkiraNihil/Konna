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

package io.github.darthakiranihil.konna.core.util;

import io.github.darthakiranihil.konna.core.except.KException;
import io.github.darthakiranihil.konna.core.object.KUninstantiable;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Utility class providing different useful reflection utils.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
public final class KReflectionUtils extends KUninstantiable {

    private KReflectionUtils() {
        super();
    }

    /**
     * Gets field of a class with automatic cast.
     * @param ofClass Class where to get field from
     * @param classInstance Instance of class that is being accessed
     * @param fieldName Field name
     * @param fieldClass Field class
     * @return Field value or {@code null} if field is not presented,
     * field and target or class and instance types do not match.
     * @param <T> Type of accessed field.
     */
    public static <T> @Nullable T getField(
        final Class<?> ofClass,
        final Object classInstance,
        final String fieldName,
        final Class<T> fieldClass
    ) {

        if (classInstance.getClass() != ofClass) {
            return null;
        }

        try {
            Field f = ofClass.getDeclaredField(fieldName);
            f.setAccessible(true);
            Object obj = f.get(classInstance);
            return fieldClass.cast(obj);
        } catch (Throwable e) {
            return null;
        }
    }

    public static @Nullable Method getMethod(
        final Class<?> ofClass,
        final String methodName
    ) {

        try {
            Method m = ofClass.getDeclaredMethod(methodName);
            m.setAccessible(true);
            return m;
        } catch (Throwable e) {
            return null;
        }

    }

    @SuppressWarnings("UnusedReturnValue")
    public static Object invokeMethod(
        final Method method,
        final Object classInstance,
        final Object... methodArgs
    ) {

        try {
            return method.invoke(classInstance, methodArgs);
        } catch (Throwable e) {
            throw new KException(e.getMessage());
        }

    }

    public static @Nullable Method getSetter(
        final Class<?> ofClass,
        final String forField
    ) {
        return KReflectionUtils.getMethod(
            ofClass,
            String.format(
                "set%s",
                forField.substring(0, 1).toUpperCase() + forField.substring(1)
            )
        );
    }

}
