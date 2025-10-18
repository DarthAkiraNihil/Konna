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
import io.github.darthakiranihil.konna.core.log.KLogger;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KObjectRegistry;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.util.KIndex;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class KManuallyProvidedEngineContext extends KObject implements KEngineContext {

    private final KActivator activator;
    private final KContainerResolver containerResolver;
    private final KIndex index;
    private final KLogger logger;
    private final KObjectRegistry objectRegistry;

    public KManuallyProvidedEngineContext(
        final KActivator activator,
        final KContainerResolver containerResolver,
        final KIndex index,
        final KLogger logger,
        final KObjectRegistry objectRegistry
    ) {
        super("context", new HashSet<>(List.of(KTag.DefaultTags.SYSTEM, KTag.DefaultTags.STD)));
        this.activator = activator;
        this.containerResolver = containerResolver;
        this.index = index;
        this.logger = logger;
        this.objectRegistry = objectRegistry;
    }

    public KActivator activator() {
        return this.activator;
    }

    public KContainerResolver containerResolver() {
        return this.containerResolver;
    }

    public KIndex index() {
        return this.index;
    }

    public KLogger logger() {
        return this.logger;
    }

    public KObjectRegistry objectRegistry() {
        return this.objectRegistry;
    }

}
