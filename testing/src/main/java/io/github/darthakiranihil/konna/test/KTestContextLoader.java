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

package io.github.darthakiranihil.konna.test;

import io.github.darthakiranihil.konna.core.app.*;
import io.github.darthakiranihil.konna.core.data.json.*;
import io.github.darthakiranihil.konna.core.di.KAppContainer;
import io.github.darthakiranihil.konna.core.di.KStandardContainerAccessor;
import io.github.darthakiranihil.konna.core.engine.KEngineContext;
import io.github.darthakiranihil.konna.core.engine.KEngineContextLoader;
import io.github.darthakiranihil.konna.core.engine.KProxiedEngineContext;
import io.github.darthakiranihil.konna.core.io.KAssetLoader;
import io.github.darthakiranihil.konna.core.io.KJsonSubtypeBasedAssetLoader;
import io.github.darthakiranihil.konna.core.io.KStandardResourceLoader;
import io.github.darthakiranihil.konna.core.io.protocol.KClasspathProtocol;
import io.github.darthakiranihil.konna.core.log.KLogLevel;
import io.github.darthakiranihil.konna.core.log.KLogger;
import io.github.darthakiranihil.konna.core.log.KSimpleLogFormatter;
import io.github.darthakiranihil.konna.core.log.system.*;
import io.github.darthakiranihil.konna.core.message.*;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KStandardActivator;
import io.github.darthakiranihil.konna.core.object.KStandardActivator2;
import io.github.darthakiranihil.konna.core.object.KStandardObjectRegistry;
import io.github.darthakiranihil.konna.core.util.KCache;
import io.github.darthakiranihil.konna.core.util.KClassGraphClasspathSearchEngine;
import io.github.darthakiranihil.konna.core.util.KClasspathSearchEngine;
import io.github.darthakiranihil.konna.core.util.KHashMapBasedCache;
import org.jetbrains.annotations.TestOnly;

import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link KEngineContextLoader} to be used only in tests.
 * Returns context, defined in {@link KStandardTestClass#context}
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
@TestOnly
public final class KTestContextLoader implements KEngineContextLoader {

    @Override
    public KEngineContext load(final KApplicationFeatures features, final KAppContainer container) {
        var classpath = new KClassGraphClasspathSearchEngine();
        var containerResolver = new KStandardContainerAccessor();
        containerResolver
            .getContainer()
            .add(KJsonParser.class, KStandardJsonParser.class)
            .add(KJsonTokenizer.class, KStandardJsonTokenizer.class)
            .add(KJsonDeserializer.class, KStandardJsonDeserializer.class)
            .add(KJsonSerializer.class, KStandardJsonSerializer.class)
            .add(KActivator.class, KProxiedEngineContext.class)
            .add(KMessageSystem.class, KProxiedEngineContext.class)
            .add(KAssetLoader.class, KProxiedEngineContext.class)
            .add(KEventSystem.class, KProxiedEngineContext.class)
            .add(KMessenger.class, KStandardMessenger.class)
            .add(KLogger.class, KStandardLogger.class)
            .add(KClasspathSearchEngine.class, KClassGraphClasspathSearchEngine.class)
            .add(KFrameTaskScheduler.class, KProxiedEngineContext.class)
            .add(KFrameTaskExecutor.class, KStandardFrameTaskSystem.class)
            .add(KFrameTaskSystem.class, KStandardFrameTaskSystem.class)
            .add(KFrameTaskPrioritizer.class, KFrameTaskPrioritizer.LeaveAsIs.class)
            .add(KCache.class, KHashMapBasedCache.class);

        var objectRegistry = new KStandardObjectRegistry();
        var activator = new KStandardActivator(containerResolver, objectRegistry, classpath);
        var messageSystem = new KStandardMessageSystem(activator);
        var eventSystem = new KStandardEventSystem();
        var resourceLoader = new KStandardResourceLoader(
            List.of(
                new KClasspathProtocol(
                    ClassLoader.getSystemClassLoader()
                )
            )
        );
        var assetLoader = new KJsonSubtypeBasedAssetLoader(
            resourceLoader,
            Map.of(),
            new KStandardJsonParser(new KStandardJsonTokenizer())
        );
        var activator2 = new KStandardActivator2(container, objectRegistry);
        var frameTaskSystem = activator.createObject(KFrameTaskSystem.class);
        KSystemLogger.addLogHandler(new KFileLogHandler("_log.log", new KTimestampLogFormatter()));
        KSystemLogger.addLogHandler(new KTerminalLogHandler(new KColorfulTerminalLogFormatter()));
        KSystemLogger.addLogHandler(new KTerminalLogHandler(new KSimpleLogFormatter()));

        eventSystem.startPolling();
        KStandardTestClass.context = new KProxiedEngineContext(
            activator,
            activator2,
            containerResolver,
            objectRegistry,
            eventSystem,
            messageSystem,
            resourceLoader,
            assetLoader,
            frameTaskSystem,
            ctx -> {
                eventSystem.stopPolling();
            }
        );
        activator.addContext(KStandardTestClass.context);
        KStandardTestClass.msgSystem = messageSystem;
        KSystemLogger.activateFileLogging();
        KSystemLogger.setLogLevel(KLogLevel.INFO);

        return KStandardTestClass.context;
    }

}
