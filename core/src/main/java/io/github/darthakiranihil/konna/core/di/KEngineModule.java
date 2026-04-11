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

import io.github.darthakiranihil.konna.core.app.KFrameTaskScheduler;
import io.github.darthakiranihil.konna.core.io.KAssetLoader;
import io.github.darthakiranihil.konna.core.io.KResourceLoader;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.message.KMessageSystem;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KObjectRegistry;
import org.jspecify.annotations.Nullable;

public final class KEngineModule implements KContainer {

    public static KEngineModule create(final KAppContainer appContainer) {
        KEngineModule module = new KEngineModule(
            appContainer.getInstanceInferred(KActivator.class),
            appContainer.getInstanceInferred(KObjectRegistry.class),
            appContainer.getInstanceInferred(KEventSystem.class),
            appContainer.getInstanceInferred(KMessageSystem.class),
            appContainer.getInstanceInferred(KResourceLoader.class),
            appContainer.getInstanceInferred(KAssetLoader.class),
            appContainer.getInstanceInferred(KFrameTaskScheduler.class)
        );

        if (appContainer.engineModule == null) {
            appContainer.setEngineModule(module);
        }
        return module;
    }

    private final KActivator activator;
    private final KObjectRegistry objectRegistry;
    private final KEventSystem eventSystem;
    private final KMessageSystem messageSystem;
    private final KResourceLoader resourceLoader;
    private final KAssetLoader assetLoader;
    private final KFrameTaskScheduler frameTaskScheduler;

    private KEngineModule(
        final KActivator activator,
        final KObjectRegistry objectRegistry,
        final KEventSystem eventSystem,
        final KMessageSystem messageSystem,
        final KResourceLoader resourceLoader,
        final KAssetLoader assetLoader,
        final KFrameTaskScheduler frameTaskScheduler
    ) {
        this.activator = activator;
        this.objectRegistry = objectRegistry;
        this.eventSystem = eventSystem;
        this.messageSystem = messageSystem;
        this.resourceLoader = resourceLoader;
        this.assetLoader = assetLoader;
        this.frameTaskScheduler = frameTaskScheduler;
    }

    // ?????
    public KActivator activator() {
        return this.activator;
    }

    public KObjectRegistry objectRegistry() {
        return this.objectRegistry;
    }

    public KEventSystem eventSystem() {
        return this.eventSystem;
    }

    public KMessageSystem messageSystem() {
        return this.messageSystem;
    }

    public KResourceLoader resourceLoader() {
        return this.resourceLoader;
    }

    public KAssetLoader assetLoader() {
        return this.assetLoader;
    }

    public KFrameTaskScheduler frameTaskScheduler() {
        return this.frameTaskScheduler;
    }

    @Override
    public @Nullable Object getInstance(Class<?> clazz) {
        if (KActivator.class.isAssignableFrom(clazz)) {
            return this.activator;
        } else if (KObjectRegistry.class.isAssignableFrom(clazz)) {
            return this.objectRegistry;
        } else if (KEventSystem.class.isAssignableFrom(clazz)) {
            return this.eventSystem;
        } else if (KMessageSystem.class.isAssignableFrom(clazz)) {
            return this.messageSystem;
        } else if (KResourceLoader.class.isAssignableFrom(clazz)) {
            return this.resourceLoader;
        } else if (KAssetLoader.class.isAssignableFrom(clazz)) {
            return this.assetLoader;
        } else if (KFrameTaskScheduler.class.isAssignableFrom(clazz)) {
            return this.frameTaskScheduler;
        }

        return null;
    }
}
