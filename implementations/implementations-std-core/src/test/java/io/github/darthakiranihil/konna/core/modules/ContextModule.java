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

package io.github.darthakiranihil.konna.core.modules;

import io.github.darthakiranihil.konna.core.app.*;
import io.github.darthakiranihil.konna.core.data.json.KStandardJsonParser;
import io.github.darthakiranihil.konna.core.data.json.KStandardJsonTokenizer;
import io.github.darthakiranihil.konna.core.di.*;
import io.github.darthakiranihil.konna.core.io.KAssetLoader;
import io.github.darthakiranihil.konna.core.io.KJsonSubtypeBasedAssetLoader;
import io.github.darthakiranihil.konna.core.io.KResourceLoader;
import io.github.darthakiranihil.konna.core.io.KStandardResourceLoader;
import io.github.darthakiranihil.konna.core.io.protocol.KClasspathProtocol;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.message.KMessageSystem;
import io.github.darthakiranihil.konna.core.message.KStandardEventSystem;
import io.github.darthakiranihil.konna.core.message.KStandardMessageSystem;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KObjectRegistry;
import io.github.darthakiranihil.konna.core.object.KStandardActivator;
import io.github.darthakiranihil.konna.core.object.KStandardObjectRegistry;
import io.github.darthakiranihil.konna.core.util.KClassGraphClasspathSearchEngine;
import io.github.darthakiranihil.konna.core.util.KClasspathSearchEngine;
import io.github.darthakiranihil.konna.test.KTestFrame;

import java.util.List;
import java.util.Map;

@KModule
public class ContextModule extends KAbstractModule {

    public ContextModule(
        KApplicationFeatures applicationFeatures,
        KSystemFeatures systemFeatures,
        KAppContainer appContainer
    ) {
        super(applicationFeatures, systemFeatures, appContainer);
    }

    @KSingleton
    public KActivator activator() {
        return new KStandardActivator(
            this.appContainer,
            this.appContainer.getInstanceInferred(KObjectRegistry.class)
        );
    }

    @KSingleton
    public KObjectRegistry objectRegistry() {
        return new KStandardObjectRegistry(
            this.appContainer.getInstanceInferred(KFrameTaskScheduler.class)
        );
    }

    @KSingleton
    public KEventSystem eventSystem() {
        return new KStandardEventSystem();
    }

    @KSingleton
    public KMessageSystem messageSystem() {
        return new KStandardMessageSystem(this.appContainer.getInstanceInferred(KActivator.class));
    }

    @KSingleton
    public KResourceLoader resourceLoader() {
        return new KStandardResourceLoader(
            List.of(new KClasspathProtocol(ClassLoader.getSystemClassLoader()))
        );
    }

    @KSingleton
    public KAssetLoader assetLoader() {
        return new KJsonSubtypeBasedAssetLoader(
            this.appContainer.getInstanceInferred(KResourceLoader.class),
            Map.of(),
            new KStandardJsonParser(new KStandardJsonTokenizer())
        );
    }

    @KSingleton
    @KAlsoProvides({ KFrameTaskExecutor.class, KFrameTaskScheduler.class})
    public KFrameTaskSystem frameTaskSystem() {
        return new KStandardFrameTaskSystem();
    }

    @KSingleton
    public KClasspathSearchEngine classpathSearchEngine() {
        return new KClassGraphClasspathSearchEngine();
    }

    @KSingleton
    public KFrame frame() {
        return new KTestFrame(
            this.appContainer.getInstanceInferred(KFrameTaskScheduler.class)
        );
    }

}
