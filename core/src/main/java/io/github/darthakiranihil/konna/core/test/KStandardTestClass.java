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

/**
 * Standard test class, containing implementations of most common Konna classes.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public class KStandardTestClass {

    /**
     * Implementation of a json tokenizer.
     */
    protected static KJsonTokenizer jsonTokenizer;
    /**
     * Implementation of a json parser.
     */
    protected static KJsonParser jsonParser;
    /**
     * Implementation of a json serializer.
     */
    protected static KJsonSerializer jsonSerializer;
    /**
     * Implementation of a json deserializer.
     */
    protected static KJsonDeserializer jsonDeserializer;
    /**
     * Implementation of a json stringifier.
     */
    protected static KJsonStringifier jsonStringifier;

    static {

        KStandardTestClass.jsonTokenizer = new KStandardJsonTokenizer();
        KStandardTestClass.jsonParser = new KStandardJsonParser(KStandardTestClass.jsonTokenizer);
        KStandardTestClass.jsonSerializer = new KStandardJsonSerializer();
        KStandardTestClass.jsonDeserializer = new KStandardJsonDeserializer();
        KStandardTestClass.jsonStringifier = new KStandardJsonStringifier();

    }

    /**
     * Default constructor.
     */
    public KStandardTestClass() {
    }
}
