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

import io.github.darthakiranihil.konna.core.data.json.except.KJsonParseException;

import java.io.InputStream;
import java.io.Reader;

/**
 * Interface for json parsers that constructs json values from tokens got from source.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public interface KJsonParser {

    /**
     * Parses json string and constructs a json value if given string is a valid json.
     * @param string String source of a json
     * @return Parsed json value
     * @throws KJsonParseException If json is invalid (incorrect token or syntax)
     */
    KJsonValue parse(String string) throws KJsonParseException;
    /**
     * Parses json from an input stream and constructs a json
     * value if given stream contains a valid json.
     * @param stream Stream source of a json
     * @return Parsed json value
     * @throws KJsonParseException If json is invalid (incorrect token or syntax)
     */
    KJsonValue parse(InputStream stream) throws KJsonParseException;
    /**
     * Parses json from a reader and constructs a json
     * value if given reader contains a valid json.
     * @param reader Reader source of a json
     * @return Parsed json value
     * @throws KJsonParseException If json is invalid (incorrect token or syntax)
     */
    KJsonValue parse(Reader reader) throws KJsonParseException;
    /**
     * Parses json from a char array and constructs a json value
     * if given array contains a valid json.
     * @param chars Array source of a json
     * @return Parsed json value
     * @throws KJsonParseException If json is invalid (incorrect token or syntax)
     */
    KJsonValue parse(char[] chars) throws KJsonParseException;

}
