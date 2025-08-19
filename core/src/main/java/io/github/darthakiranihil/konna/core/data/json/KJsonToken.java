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

/**
 * Enumeration that describes a json token
 */
public enum KJsonToken {

    /**
     * Symbol of "{"
     */
    OPEN_BRACE,
    /**
     * Symbol of "}"
     */
    CLOSE_BRACE,

    /**
     * Symbol of "["
     */
    OPEN_SQUARE_BRACKET,
    /**
     * Symbol of "]"
     */
    CLOSE_SQUARE_BRACKET,

    /**
     * A string
     */
    STRING,
    /**
     * An integer
     */
    NUMBER_INT,
    /**
     * A float (can be written in exponential form)
     */
    NUMBER_FLOAT,

    /**
     * Symbol of ","
     */
    COMMA,
    /**
     * Symbol of ":"
     */
    SEMICOLON,

    /**
     * True literal
     */
    TRUE,
    /**
     * False literal
     */
    FALSE,
    /**
     * Null literal
     */
    NULL,
    /**
     * Signalizes the end of a file
     */
    EOF,

}
