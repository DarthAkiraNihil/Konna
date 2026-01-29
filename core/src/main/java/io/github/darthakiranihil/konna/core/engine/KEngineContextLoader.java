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

import io.github.darthakiranihil.konna.core.app.KApplicationFeatures;

/**
 * Represents interface for loading the engine context, which
 * means it must initialize all essential classes.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public interface KEngineContextLoader {
    /**
     * Loads the engine context - initializes all required classes
     * and returns it.
     * @param features Application features, retrieved from
     *                 {@link io.github.darthakiranihil.konna.core.app.KArgumentParser}
     *                 after parsing application arguments
     * @return Loaded context
     */
    KEngineContext load(KApplicationFeatures features);

    /**
     * Executes post-load for previously loaded context.
     * @param context Loaded context
     * @param features Current application features
     * @since 0.3.0
     */
    default void postLoad(final KEngineContext context, final KApplicationFeatures features) {

    }
}
