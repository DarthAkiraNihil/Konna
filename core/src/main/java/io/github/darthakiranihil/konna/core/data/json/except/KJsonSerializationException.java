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
 * Exception that is caused by different (de)serialization errors.
 * The common case is when a json value does not correspond to the deserialized class
 */
public class KJsonSerializationException extends KException implements KThrowable {

    /**
     * Constructs exception with a provided cause
     * @param cause Cause of the exception
     */
    public KJsonSerializationException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs exception with a provided message
     * @param message Exception message
     */
    public KJsonSerializationException(String message) {
        super(message);
    }

    @Override
    public KThrowableSeverity getSeverity() {
        return KThrowableSeverity.FATAL;
    }
}
