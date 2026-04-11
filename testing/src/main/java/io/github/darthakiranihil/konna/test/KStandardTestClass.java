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
import io.github.darthakiranihil.konna.core.app.KStandardApplicationFeatures;
import io.github.darthakiranihil.konna.core.app.KSystemFeatures;
import io.github.darthakiranihil.konna.core.data.json.*;
import io.github.darthakiranihil.konna.core.di.KAppContainer;
import io.github.darthakiranihil.konna.core.di.KEngineModule;
import io.github.darthakiranihil.konna.core.log.KSimpleLogFormatter;
import io.github.darthakiranihil.konna.core.log.system.*;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.core.util.KReflectionUtils;
import org.jetbrains.annotations.TestOnly;

import java.util.Map;

/**
 * Standard test class, containing implementations of most common Konna classes.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
@TestOnly
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

    static {
        KSystemLogger.addLogHandler(new KFileLogHandler("_log.log", new KTimestampLogFormatter()));
        KSystemLogger.addLogHandler(new KTerminalLogHandler(new KColorfulTerminalLogFormatter()));
        KSystemLogger.addLogHandler(new KTerminalLogHandler(new KSimpleLogFormatter()));
        KSystemLogger.activateFileLogging();
    }

    public static KEngineModule getModule() {
        var constructor = KReflectionUtils.getConstructor(
            KAppContainer.useGenerated(),
            KApplicationFeatures.class,
            KSystemFeatures.class
        );

        assert constructor != null;
        return KEngineModule.create(
            KReflectionUtils.newInstance(
                constructor,
                new KStandardApplicationFeatures(Map.of()),
                new KSystemFeatures()
            )
        );

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
