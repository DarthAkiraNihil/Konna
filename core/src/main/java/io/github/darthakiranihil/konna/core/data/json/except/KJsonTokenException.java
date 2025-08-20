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
import io.github.darthakiranihil.konna.core.except.KThrowable;
import io.github.darthakiranihil.konna.core.except.KThrowableSeverity;

/**
 * Exception thrown when json tokenizer can't get the next token because of reasons
 * such as unclosed string, incorrect exponential-format number or so on.
 * By default, exception is an error because it is certainly not good, but it depends on goals,
 * sometimes invalid json just leads to not loading some kind of content
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public class KJsonTokenException extends KException implements KThrowable {

    private static final String DEFAULT_MESSAGE_TEMPLATE = "Error reading json token at line: %d, column: %d";
    /**
     * Constructs exception with error message containing line and column of tokenizer error occurring
     * @param line Line of error occurring
     * @param column Column of error occurring
     */
    public KJsonTokenException(long line, long column) {
        super(String.format(KJsonTokenException.DEFAULT_MESSAGE_TEMPLATE, line, column));
    }

    @Override
    public KThrowableSeverity getSeverity() {
        return KThrowableSeverity.ERROR;
    }

}
