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

import io.github.darthakiranihil.konna.core.data.json.KJsonValueType;
import io.github.darthakiranihil.konna.core.except.KException;
import io.github.darthakiranihil.konna.core.except.KExceptionSeverity;

/**
 * Exception thrown when validating a json value has been failed.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public class KJsonValidationError extends KException {

    /**
     * Convenience constructor-like-method for validation error caused
     * by incorrect type of validated value.
     * @param expected Expected type of validated value
     * @param actual Actual type of validated value
     * @return Constructed exception object
     */
    public static KJsonValidationError incorrectType(
        final KJsonValueType expected,
        final KJsonValueType actual
    ) {
        return new KJsonValidationError(
            String.format(
                "Incorrect value type. Expected %s but got %s",
                expected,
                actual
            )
        );
    }

    /**
     * Convenience constructor-like-method for initial validation errors.
     * @param property Property that failed validation
     * @param message Validation error message
     * @return Constructed exception object
     */
    public static KJsonValidationError generalError(final String property, final String message) {
        return new KJsonValidationError(
            String.format(
                "%s: %s",
                property,
                message
            )
        );
    }

    /**
     * Convenience constructor-like-method for validation errors
     * that are caused by other validation errors from nested json values.
     * @param property Property that failed validation
     * @param cause Thrown validation error
     * @return Constructed exception object
     */
    public static KJsonValidationError wrappedError(
        final String property,
        final KJsonValidationError cause
    ) {
        return new KJsonValidationError(
            String.format(
                "%s: %s",
                property,
                cause.getMessage()
            ),
            cause
        );
    }

    public KJsonValidationError(final String message, final Throwable cause) {
        super(message, cause);
    }

    public KJsonValidationError(final String message) {
        super(message);
    }



    @Override
    public KExceptionSeverity getSeverity() {
        return KExceptionSeverity.ERROR;
    }
}
