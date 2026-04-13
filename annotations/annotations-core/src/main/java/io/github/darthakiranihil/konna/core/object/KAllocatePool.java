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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 *     Annotates class with information about pool that is needed to be created for this type.
 * </p>
 * <p>
 *     Each annotated class must implement {@code KPoolable} interface, provide
 *     a non-public zero-arg constructor and at most one method annotated with
 *     {@link KOnPoolableObjectObtain}.
 * </p>

 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface KAllocatePool {

    /**
     * Enumeration representing pool policy in situations when there is an attempt to
     * obtain an object from it, but there is no free objects.
     */
    enum NoObjectPolicy {
        /**
         * Return empty object container (<i>not the actual object</i>).
         */
        RETURN_EMPTY,
        /**
         * Throw an exception.
         */
        THROW_EXCEPTION
    }

    /**
     * @return Initial pool size, if its extensible, or its size, that measures number of objects
     *         to be allocated on pool creation. Must be positive.
     */
    int initialSize();

    /**
     * @return Pool policy in situations where there is an attempt to obtain an object
     *         when there is no unused objects.
     */
    NoObjectPolicy noObjectPolicy() default NoObjectPolicy.THROW_EXCEPTION;

    /**
     * @return Whether the pool is extensible, i.e. its storage may be extended when there is no
     *         free objects but the pool is still requested.
     */
    boolean extensible() default false;

    /**
     * @return Maximum size of objects contained in this pool. Does not make sense if the pool
     *         is not extensible. Default is {@link Integer#MAX_VALUE}. Must be greater than
     *         initial size.
     */
    int maxSize() default Integer.MAX_VALUE;

    /**
     * @return Factor to multiply current pool size on when it needs to be extended.
     *         Must not be less than 1.0 + EPS;
     */
    float extensionFactor() default 1.5f;

}
