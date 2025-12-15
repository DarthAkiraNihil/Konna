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

package io.github.darthakiranihil.konna.core.data.json.except;

import io.github.darthakiranihil.konna.core.except.KException;
import io.github.darthakiranihil.konna.core.except.KExceptionSeverity;

/**
 * Exception thrown when json parser could not parse a json-containing data.
 * The common reasons for it are:
 * <ul>
 *       <li>tokenizer could not get the next token</li>
 *       <li>json syntax error</li>
 *  </ul>
 *  By default, the exception is fatal
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public class KJsonParseException extends KException {

    private static final String
        UNEXPECTED_TOKEN_MESSAGE_TEMPLATE = "Error parsing json, unexpected token: %s";

    /**
     * Constructs exception with unexpected token-like message.
     * @param tokenValue Token value
     */
    public KJsonParseException(final Object tokenValue) {
        super(String.format(KJsonParseException.UNEXPECTED_TOKEN_MESSAGE_TEMPLATE, tokenValue));
    }

    public KJsonParseException(final Throwable cause) {
        super(cause);
    }

}
