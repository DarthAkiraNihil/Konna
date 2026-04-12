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

package io.github.darthakiranihil.konna.core.di;

import org.jspecify.annotations.Nullable;

/**
 * <p>
 *     Interface for a supplier of objects with different types.
 * </p>
 * <p>
 *     Actually it is not strictly defined how to resolve an instance of object with passed type.
 *     It can be pretty simple (like if-else and get explicitly created instance), or more complex,
 *     including delegating the job to other classes or containers.
 * </p>
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public interface KContainer {

    /**
     * @param clazz Class of object to get
     * @return Instance of object with specified class or {@code null}
     *         if corresponding class is not found
     *
     * @since 0.6.0
     */
    @Nullable Object getInstance(Class<?> clazz);

}
