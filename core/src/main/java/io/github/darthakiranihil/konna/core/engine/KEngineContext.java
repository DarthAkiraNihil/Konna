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

import io.github.darthakiranihil.konna.core.di.KContainerResolver;
import io.github.darthakiranihil.konna.core.io.KAssetLoader;
import io.github.darthakiranihil.konna.core.io.KResourceLoader;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.message.KMessageSystem;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KObjectRegistry;
import io.github.darthakiranihil.konna.core.util.KIndex;

/**
 * Interface for the essential part of Konna Engine - the engine context.
 * It provides signatures for methods of getting the most important objects
 * used in Konna system.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public interface KEngineContext extends
    KActivator,
    KContainerResolver,
    KIndex,
    KObjectRegistry,
    KEventSystem,
    KMessageSystem,
    KResourceLoader,
    KAssetLoader {

    /**
     * Executes graceful shutdown for this context.
     */
    void handleShutdown();

}

