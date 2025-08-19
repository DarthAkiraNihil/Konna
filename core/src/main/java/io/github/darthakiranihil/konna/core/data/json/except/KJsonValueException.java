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

import io.github.darthakiranihil.konna.core.except.KRuntimeException;
import io.github.darthakiranihil.konna.core.except.KThrowable;
import io.github.darthakiranihil.konna.core.except.KThrowableSeverity;

/**
 * Exception that is caused by incorrect way of handling a Json value
 * The common reason may include:
 * <ul>
 *      <li>returning iterator from a non-array</li>
 *      <li>casting internal value into invalid type</li>
 * </ul>
 * By default, the exception is fatal
 */
public class KJsonValueException extends KRuntimeException implements KThrowable {

    /**
     * Constructs exception with a provided message
     * @param message Exception message
     */
    public KJsonValueException(String message) {
        super(message);
    }

    @Override
    public KThrowableSeverity getSeverity() {
        return KThrowableSeverity.FATAL;
    }
}
