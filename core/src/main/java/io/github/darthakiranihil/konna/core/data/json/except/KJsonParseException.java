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

import io.github.darthakiranihil.konna.core.except.KThrowableSeverity;
import io.github.darthakiranihil.konna.core.except.KThrowable;

/**
 * Exception that is caused when json parser could not parse a json.
 * The common reason may include:
 * <ul>
 *       <li>tokenizer could not get the next token</li>
 *       <li>json tokens are in incorrect order that do not correspond to json specification</li>
 *  </ul>
 *  By default, the exception is fatal
 */
public class KJsonParseException extends Exception implements KThrowable {

    /**
     * Constructor for situations when the token got is unexpected
     * @param tokenValue The token value
     */
    public KJsonParseException(Object tokenValue) {
        super(String.format("Error parsing json, unexpected token: %s", tokenValue));
    }

    /**
     * Constructor for situations when the reason is out of parser's scope
     * @param cause The cause
     */
    public KJsonParseException(Throwable cause) {
        super(cause);
    }

    @Override
    public KThrowableSeverity getSeverity() {
        return KThrowableSeverity.FATAL;
    }
}
