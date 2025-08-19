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
 * Exception that is caused when json tokenizer can't get the next token due to different errors
 * such as unclosed string, incorrect exponential-format number or so on.
 * By default, exception is an error because it is certainly not good, but it depends on goals, sometimes invalid json
 * just leads to not loading some kind of content
 */
public class KJsonTokenException extends KException implements KThrowable {

    /**
     * Default constructor with error message
     * @param line Line of error occurring
     * @param column Column of error occurring
     */
    public KJsonTokenException(long line, long column) {
        super(String.format("Error reading json token at line: %d, column: %d", line, column));
    }

    @Override
    public KThrowableSeverity getSeverity() {
        return KThrowableSeverity.ERROR;
    }

}
