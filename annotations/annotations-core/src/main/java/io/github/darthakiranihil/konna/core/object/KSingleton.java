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
 * Marks class as a singleton so {@link KActivator} should create only one
 * instance of it.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface KSingleton {
    /**
     * Flag that specifies if a singleton should be stored in {@link KActivator}
     * using a weak reference (if {@code true}) or not. Defaults to {@code false}
     * @return Flag of weak reference usage
     */
    boolean weak() default false;

    /**
     * Flag that indicates if singleton is immortal, so it cannot be deleted with {@link KActivator}
     * and only application termination can destroy immortal objects. Has priority above
     * weak flag, so if both weak and immortal are true, weak flag will be ignored.
     * @return Flag of object immortality
     */
    boolean immortal() default false;
}
