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

import io.github.darthakiranihil.konna.core.app.KApplicationFeatures;
import io.github.darthakiranihil.konna.core.app.KSystemFeatures;

/**
 * Abstract class for app container that is supposed to be used
 * across the whole application.
 *
 * @since 0.6.0
 * @author Darth Akira Nihil
 */
public abstract class KAppContainer implements KContainer2 {

    /**
     * Application features assigned to this container.
     */
    protected final KApplicationFeatures applicationFeatures;
    /**
     * System features assigned to this container.
     */
    protected final KSystemFeatures systemFeatures;

    /**
     * Constructs this container.
     * @param applicationFeatures Application features assigned to this container
     * @param systemFeatures System features assigned to this container
     */
    protected KAppContainer(
        final KApplicationFeatures applicationFeatures,
        final KSystemFeatures systemFeatures
    ) {
        this.applicationFeatures = applicationFeatures;
        this.systemFeatures = systemFeatures;
    }

    @Override
    public abstract Object getInstance(Class<?> clazz);
}
