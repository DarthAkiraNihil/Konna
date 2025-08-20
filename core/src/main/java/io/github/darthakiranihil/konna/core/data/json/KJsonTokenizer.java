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

package io.github.darthakiranihil.konna.core.data.json;

import io.github.darthakiranihil.konna.core.data.json.except.KJsonTokenException;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonTokenizer;

/**
 * Base class for a json tokenizer.
 * It provides base methods to extract tokens from a json-containing value like string (so far).
 * Tokenizer extracts symbols from a source and then converts to a token pair that is used by parser to
 * construct a json value
 *
 * @see KJsonTokenPair
 * @see KJsonValue
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public abstract class KJsonTokenizer {

    /**
     * String source of a json
     */
    protected String source;

    /**
     * Constructs tokenizer from a string as a Json source
     * @param source String source of a json
     */
    public KJsonTokenizer(String source) {
        this.source = source;
    }

    /**
     * Returns the next token from json source
     * @return The next json token
     * @throws KJsonTokenException If it fails to read the token
     */
    public abstract KJsonTokenPair getNextToken() throws KJsonTokenException;

    /**
     * Resets tokenizer state and sets new source of possible json tokens
     * @param newSource New source to tokenize
     */
    public abstract void reset(String newSource);
}
