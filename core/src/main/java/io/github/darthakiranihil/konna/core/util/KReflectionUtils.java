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
import io.github.darthakiranihil.konna.core.object.except.KInstantiationException;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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

    /**
     * Returns method handle of any class without checked exceptions.
     * @param ofClass Class to get method from
     * @param methodName Method name
     * @param parameterTypes Types of method parameter
     * @return The {@link Method} object with specific name and parameters or {@code null},
     *         if the method is not found
     * @since 0.4.0
     */
    public static @Nullable Method getMethod(
        final Class<?> ofClass,
        final String methodName,
        final Class<?>... parameterTypes
    ) {

        try {
            Method m = ofClass.getDeclaredMethod(methodName, parameterTypes);
            m.setAccessible(true);
            return m;
        } catch (Throwable e) {
            return null;
        }

    }

    /**
     * Invokes passed method without bypassing mandatory checked exceptions catching.
     * @param method Method handle to invoke
     * @param classInstance Instance of class whose method will be called
     * @param methodArgs Method arguments
     * @return Return value of invoked method
     * @since 0.4.0
     */
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

    /**
     * Returns getter handle for specific class field. In order for this method
     * to return the real handle, the getter must be named like {@code get[Field]}
     * where {@code [Field]} is capitalized name of accessed property.
     * @param ofClass Class to find getter in
     * @param forField Field to get getter of
     * @return The {@link Method} instance, representing getter for the passed field
     *         or {@code null} if it is not found
     * @since 0.4.0
     */
    public static @Nullable Method getGetter(
        final Class<?> ofClass,
        final String forField
    ) {
        return KReflectionUtils.getMethod(
            ofClass,
            String.format(
                "get%s",
                forField.substring(0, 1).toUpperCase() + forField.substring(1)
            )
        );
    }

    /**
     * Returns setter handle for specific class field. In order for this method
     * to return the real handle, the getter must be named like {@code set[Field]}
     * where {@code [Field]} is capitalized name of accessed property.
     * @param ofClass Class to find setter in
     * @param forField Field to get setter of
     * @param fieldType Field type (setter parameter type)
     * @return The {@link Method} instance, representing setter for the passed field
     *         or {@code null} if it is not found
     * @since 0.4.0
     */
    public static @Nullable Method getSetter(
        final Class<?> ofClass,
        final String forField,
        final Class<?> fieldType
    ) {
        return KReflectionUtils.getMethod(
            ofClass,
            String.format(
                "set%s",
                forField.substring(0, 1).toUpperCase() + forField.substring(1)
            ),
            fieldType
        );
    }

    /**
     * Returns constructor handle of any class without checked exceptions.
     * @param ofClass Class to get method from
     * @param parameterTypes Types of method parameter
     * @param <T> Type parameter of instances created by constructor
     * @return The {@link Constructor} object with specific name and parameters or {@code null},
     *         if the constructor is not found
     * @since 0.4.0
     */
    public static <T> @Nullable Constructor<T> getConstructor(
        final Class<T> ofClass,
        final Class<?>... parameterTypes
    ) {

        try {
            var constructor = ofClass.getDeclaredConstructor(parameterTypes);
            constructor.setAccessible(true);
            return constructor;
        } catch (NoSuchMethodException e) {
            return null;
        }

    }

    /**
     * Creates a new instance using constructor without checked exceptions.
     * @param constructor Constructor instance to use
     * @param parameters Constructor parameters
     * @return The created instance
     * @param <T> Type of created object
     *
     * @since 0.4.0
     */
    public static <T> T newInstance(
        final Constructor<T> constructor,
        final Object... parameters
    ) {
        try {
            return constructor.newInstance(parameters);
        } catch (InvocationTargetException e) {
            throw new KInstantiationException(e.getTargetException().getMessage());
        } catch (Exception e) {
            throw new KInstantiationException(e.getMessage());
        }
    }

}
