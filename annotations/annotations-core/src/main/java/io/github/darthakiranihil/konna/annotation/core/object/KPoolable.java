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

package io.github.darthakiranihil.konna.annotation.core.object;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Marks class as poolable so all instances of this class should be retrieved
 * from their assigned pool when instantiation is requested.
 * </p>
 * <p>
 * Each poolable class should provide either both 2 methods annotated with
 * {@link KOnPoolableObjectObtain} and {@link KOnPoolableObjectRelease} correspondingly
 * or none of them. Multiple methods with such annotations, one method with both of them,
 * only one method with one of these annotations are not allowed and will
 * be prevented at compile-time.
 * </p>
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface KPoolable {

    /**
     * Initial size of assigned pool, measured in objects stored in it.
     * @return Object count to store in the pool at creation
     */
    int initialPoolSize();

    /**
     * Flag that indicates that weak references should be used
     * in assigned object pool.
     * @return Flag of weak reference usage in an object pool.
     */
    boolean weak() default false;

}
