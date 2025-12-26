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

package io.github.darthakiranihil.konna.core.test;

import io.github.darthakiranihil.konna.core.data.json.*;
import io.github.darthakiranihil.konna.core.data.json.std.*;
import io.github.darthakiranihil.konna.core.di.KContainerModifier;
import io.github.darthakiranihil.konna.core.di.std.KStandardContainerAccessor;
import io.github.darthakiranihil.konna.core.engine.KEngineContext;
import io.github.darthakiranihil.konna.core.engine.std.KProxiedEngineContext;
import io.github.darthakiranihil.konna.core.io.std.KJsonAssetLoader;
import io.github.darthakiranihil.konna.core.io.std.KStandardResourceLoader;
import io.github.darthakiranihil.konna.core.io.std.protocol.KClasspathProtocol;
import io.github.darthakiranihil.konna.core.log.KLogger;
import io.github.darthakiranihil.konna.core.log.KSystemLogger;
import io.github.darthakiranihil.konna.core.log.std.*;
import io.github.darthakiranihil.konna.core.message.KMessageSystem;
import io.github.darthakiranihil.konna.core.message.KMessenger;
import io.github.darthakiranihil.konna.core.message.KQueueBasedMessageSystem;
import io.github.darthakiranihil.konna.core.message.std.KStandardEventSystem;
import io.github.darthakiranihil.konna.core.message.std.KStandardMessageSystem;
import io.github.darthakiranihil.konna.core.message.std.KStandardMessenger;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.object.std.KStandardActivator;
import io.github.darthakiranihil.konna.core.object.std.KStandardObjectRegistry;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.core.util.std.KStandardIndex;
import org.jetbrains.annotations.TestOnly;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * Standard test class, containing implementations of most common Konna classes.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
@TestOnly
@KContainerModifier
public class KStandardTestClass extends KObject {

    /**
     * Implementation of a json tokenizer.
     */
    protected final KJsonTokenizer jsonTokenizer;
    /**
     * Implementation of a json parser.
     */
    protected final KJsonParser jsonParser;
    /**
     * Implementation of a json serializer.
     */
    protected final KJsonSerializer jsonSerializer;
    /**
     * Implementation of a json deserializer.
     */
    protected final KJsonDeserializer jsonDeserializer;
    /**
     * Implementation of a json stringifier.
     */
    protected final KJsonStringifier jsonStringifier;
    /**
     * Engine context, required for running tests.
     */
    @SuppressWarnings("DataFlowIssue")
    protected static KEngineContext context = null;

    /**
     * Returns engine context for testing environment.
     * @return Engine context for testing environment
     */
    public static KEngineContext getContext() {
        return KStandardTestClass.context;
    }

    /**
     * Convenience reference to message system.
     */
    protected static @Nullable KQueueBasedMessageSystem msgSystem;

    static {
        var index = new KStandardIndex();

        var containerResolver = new KStandardContainerAccessor(index);
        containerResolver
            .getContainer()
            .add(KJsonParser.class, KStandardJsonParser.class)
            .add(KJsonTokenizer.class, KStandardJsonTokenizer.class)
            .add(KActivator.class, KProxiedEngineContext.class)
            .add(KMessageSystem.class, KProxiedEngineContext.class)
            .add(KMessenger.class, KStandardMessenger.class)
            .add(KLogger.class, KStandardLogger.class);

        var objectRegistry = new KStandardObjectRegistry();
        var activator = new KStandardActivator(containerResolver, objectRegistry, index);
        var messageSystem = new KStandardMessageSystem(activator);
        var eventSystem = new KStandardEventSystem();
        var resourceLoader = new KStandardResourceLoader(
            List.of(
                new KClasspathProtocol(
                    ClassLoader.getSystemClassLoader()
                )
            )
        );
        var assetLoader = new KJsonAssetLoader(
            resourceLoader,
            Map.of(),
            new KStandardJsonParser(new KStandardJsonTokenizer())
        );
        KSystemLogger.addLogHandler(new KFileLogHandler("_log.log", new KTimestampLogFormatter()));
        KSystemLogger.addLogHandler(new KTerminalLogHandler(new KColorfulTerminalLogFormatter()));
        KSystemLogger.addLogHandler(new KTerminalLogHandler(new KSimpleLogFormatter()));

        KStandardTestClass.context = new KProxiedEngineContext(
            activator,
            containerResolver,
            index,
            objectRegistry,
            eventSystem,
            messageSystem,
            resourceLoader,
            assetLoader
        );
        activator.addContext(KStandardTestClass.context);
        KStandardTestClass.msgSystem = messageSystem;
    }

    /**
     * Default constructor.
     */
    protected KStandardTestClass() {
        super(
            "std_test_class",
            KStructUtils.setOfTags(KTag.DefaultTags.TEST, KTag.DefaultTags.STD)
        );
        this.jsonTokenizer = new KStandardJsonTokenizer();
        this.jsonParser = new KStandardJsonParser(this.jsonTokenizer);
        this.jsonSerializer = new KStandardJsonSerializer();
        this.jsonDeserializer = new KStandardJsonDeserializer();
        this.jsonStringifier = new KStandardJsonStringifier();

    }
}
