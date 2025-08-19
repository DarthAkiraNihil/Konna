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
 * Record for storing json token information
 * @param token Token descriptor
 * @param value Token match. Can be an int, float, string, boolean or null, or char if token is a single-character one (like braces etc.)
 */
public record KJsonTokenPair(KJsonToken token, Object value) {

    public static final KJsonTokenPair OPEN_BRACE = new KJsonTokenPair(KJsonToken.OPEN_BRACE, '{');
    public static final KJsonTokenPair CLOSE_BRACE = new KJsonTokenPair(KJsonToken.CLOSE_BRACE, '}');
    public static final KJsonTokenPair OPEN_SQUARE_BRACKET = new KJsonTokenPair(KJsonToken.OPEN_SQUARE_BRACKET, '[');
    public static final KJsonTokenPair CLOSE_SQUARE_BRACKET = new KJsonTokenPair(KJsonToken.CLOSE_SQUARE_BRACKET, ']');
    public static final KJsonTokenPair COMMA = new KJsonTokenPair(KJsonToken.COMMA, ',');
    public static final KJsonTokenPair SEMICOLON = new KJsonTokenPair(KJsonToken.SEMICOLON, ':');
    public static final KJsonTokenPair TRUE = new KJsonTokenPair(KJsonToken.TRUE, true);
    public static final KJsonTokenPair FALSE = new KJsonTokenPair(KJsonToken.FALSE, false);
    public static final KJsonTokenPair NULL = new KJsonTokenPair(KJsonToken.NULL, null);
    public static final KJsonTokenPair EOF = new KJsonTokenPair(KJsonToken.EOF, '\0');

    /**
     * Constructs token pair from a string
     * @param value The token value
     * @return A new token pair with {@link KJsonToken}.STRING type
     */
    public static KJsonTokenPair fromString(String value) {
        return new KJsonTokenPair(KJsonToken.STRING, value);
    }

    /**
     * Constructs token pair from an integer
     * @param value The token value
     * @return A new token pair with {@link KJsonToken}.NUMBER_INT type
     */
    public static KJsonTokenPair fromInteger(int value) {
        return new KJsonTokenPair(KJsonToken.NUMBER_INT, value);
    }

    /**
     * Constructs token pair from a float
     * @param value The token value
     * @return A new token pair with {@link KJsonToken}.NUMBER_FLOAT type
     */
    public static KJsonTokenPair fromFloat(float value) {
        return new KJsonTokenPair(KJsonToken.NUMBER_FLOAT, value);
    }
}
