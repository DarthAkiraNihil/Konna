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

import io.github.darthakiranihil.konna.core.app.KApplicationFeatures;
import io.github.darthakiranihil.konna.core.app.KFrameTaskSystem;
import io.github.darthakiranihil.konna.core.data.json.KStandardJsonParser;
import io.github.darthakiranihil.konna.core.data.json.KStandardJsonTokenizer;
import io.github.darthakiranihil.konna.core.di.KAppContainer;
import io.github.darthakiranihil.konna.core.engine.KEngineContext;
import io.github.darthakiranihil.konna.core.engine.KEngineContextLoader;
import io.github.darthakiranihil.konna.core.engine.KProxiedEngineContext;
import io.github.darthakiranihil.konna.core.io.KJsonSubtypeBasedAssetLoader;
import io.github.darthakiranihil.konna.core.io.KStandardResourceLoader;
import io.github.darthakiranihil.konna.core.io.protocol.KClasspathProtocol;
import io.github.darthakiranihil.konna.core.log.KLogLevel;
import io.github.darthakiranihil.konna.core.log.KSimpleLogFormatter;
import io.github.darthakiranihil.konna.core.log.system.*;
import io.github.darthakiranihil.konna.core.message.KStandardEventSystem;
import io.github.darthakiranihil.konna.core.message.KStandardMessageSystem;
import io.github.darthakiranihil.konna.core.object.KStandardActivator;
import io.github.darthakiranihil.konna.core.object.KStandardObjectRegistry;
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

        var objectRegistry = new KStandardObjectRegistry();
        var activator = new KStandardActivator(container, objectRegistry);
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
        var activator2 = new KStandardActivator(container, objectRegistry);
        var frameTaskSystem = activator.createObject(KFrameTaskSystem.class);
        KSystemLogger.addLogHandler(new KFileLogHandler("_log.log", new KTimestampLogFormatter()));
        KSystemLogger.addLogHandler(new KTerminalLogHandler(new KColorfulTerminalLogFormatter()));
        KSystemLogger.addLogHandler(new KTerminalLogHandler(new KSimpleLogFormatter()));

        eventSystem.startPolling();
        KStandardTestClass.context = new KProxiedEngineContext(
            activator2,
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
        KStandardTestClass.msgSystem = messageSystem;
        KSystemLogger.activateFileLogging();
        KSystemLogger.setLogLevel(KLogLevel.INFO);

        return KStandardTestClass.context;
    }

}
