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

package io.github.darthakiranihil.konna.core.engine.std;

import io.github.darthakiranihil.konna.core.di.KContainerResolver;
import io.github.darthakiranihil.konna.core.engine.KEngineContext;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.message.KMessageSystem;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KObjectRegistry;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.util.KIndex;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;

/**
 * Implementation of {@link KEngineContext} that requires ready instances of all
 * context class in order to construct the context.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public final class KManuallyProvidedEngineContext extends KObject implements KEngineContext {

    private final KActivator activator;
    private final KContainerResolver containerResolver;
    private final KIndex index;
    private final KObjectRegistry objectRegistry;
    private final KEventSystem eventSystem;
    private final KMessageSystem messageSystem;

    /**
     * Standard constructor.
     * @param activator Activator of the context
     * @param containerResolver Container resolver of the context
     * @param index Index of the context
     * @param objectRegistry Object registry of the context
     * @param eventSystem Event system of the context
     * @param messageSystem Message system of the context
     */
    public KManuallyProvidedEngineContext(
        final KActivator activator,
        final KContainerResolver containerResolver,
        final KIndex index,
        final KObjectRegistry objectRegistry,
        final KEventSystem eventSystem,
        final KMessageSystem messageSystem
        ) {
        super("context", KStructUtils.setOfTags(KTag.DefaultTags.SYSTEM, KTag.DefaultTags.STD));
        this.activator = activator;
        this.containerResolver = containerResolver;
        this.index = index;
        this.objectRegistry = objectRegistry;
        this.eventSystem = eventSystem;
        this.messageSystem = messageSystem;
    }

    @Override
    public KActivator activator() {
        return this.activator;
    }

    @Override
    public KContainerResolver containerResolver() {
        return this.containerResolver;
    }

    @Override
    public KIndex index() {
        return this.index;
    }

    @Override
    public KObjectRegistry objectRegistry() {
        return this.objectRegistry;
    }

    @Override
    public KEventSystem eventSystem() {
        return this.eventSystem;
    }

    @Override
    public KMessageSystem messageSystem() {
        return this.messageSystem;
    }
}
