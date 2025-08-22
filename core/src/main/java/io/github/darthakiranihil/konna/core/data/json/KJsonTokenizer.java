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

import java.io.InputStream;
import java.io.Reader;

/**
 * Interface for a json tokenizer.
 * It provides base methods to extract tokens from a json-containing value like string (so far).
 * Tokenizer extracts symbols from a source and then converts
 * to a token pair that is used by parser to construct a json value
 *
 * @see KJsonTokenPair
 * @see KJsonValue
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public interface KJsonTokenizer {

    /**
     * Returns the next token from json source.
     * @param sequenceToken Sequence token, returned by {@link #addSource(InputStream)} method,
     *                      that describes tokenizing sequence
     * @return The next json token
     * @throws KJsonTokenException If it fails to read the token
     */
    KJsonTokenPair getNextToken(int sequenceToken) throws KJsonTokenException;

    /**
     * Adds an input stream as new json source to tokenize.
     * @param source Source to tokenize
     * @return sequence token that describes source in tokenization process
     */
    int addSource(InputStream source);

    /**
     * Adds a string as new json source to tokenize.
     * @param source Source to tokenize
     * @return sequence token that describes source in tokenization process
     */
    int addSource(String source);
    int addSource(Reader source);
    int addSource(char[] source);
}
