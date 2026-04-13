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

    enum NoObjectPolicy {
        RETURN_EMPTY,
        THROW_EXCEPTION
    }

    /**
     * Initial size of assigned pool, measured in objects stored in it.
     * @return Object count to store in the pool at creation
     */
    int initialSize();
    NoObjectPolicy noObjectPolicy() default NoObjectPolicy.THROW_EXCEPTION;

    boolean extensible() default false;
    int maxSize() default Integer.MAX_VALUE;
    float extensionFactor() default 1.5f;

}
