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

package io.github.darthakiranihil.konna.core.engine;

import io.github.darthakiranihil.konna.core.engine.except.KComponentLoadingException;

import java.util.Map;

/**
 * Interface for a component loader - class which task is to instantiate and initialize
 * given component that is prepared for subsequent configuring.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public interface KComponentLoader {

    /**
     * Loads engine component with given class and puts it in loadedComponentMap.
     * @param component Class of component to load
     * @param serviceLoader Instance of component services loader
     * @param loadedComponentMap Map of loaded components. Used to prevent loading two components
     *                           with the same name, provided by {@link KComponentMetaInfo}
     *                           annotation
     * @throws KComponentLoadingException when component failed to be loaded
 *                                        (see cause for details)
     */
    void load(
        Class<? extends KComponent> component,
        KServiceLoader serviceLoader,
        Map<String, KComponent> loadedComponentMap
    ) throws KComponentLoadingException;

}
