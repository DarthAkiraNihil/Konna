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

package io.github.darthakiranihil.konna.core.app.except;

import io.github.darthakiranihil.konna.core.except.KException;
import io.github.darthakiranihil.konna.core.except.KExceptionSeverity;

/**
 * Exception thrown when argument parsing has failed due to
 * different reasons. By default, the exception is fatal since
 * args define application features, so it cannot start without them
 * is they were not parsed.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public class KArgumentParseException extends KException {

    /**
     * Convenience constructor-like-method for exception caused
     * by failed validation.
     * @param arg Argument's long key that failed validation
     * @return Constructed exception object
     */
    public static KArgumentParseException validationFailed(final String arg) {
        return new KArgumentParseException(
            String.format(
                "Argument parsing failed for %s: argument is not valid",
                arg
            )
        );
    }

    /**
     * Convenience constructor-like-method for exception caused
     * when arg is required but were not provided.
     * @param arg Argument's long key that caused this.
     * @return Constructed exception object
     */
    public static KArgumentParseException fieldIsRequired(final String arg) {
        return new KArgumentParseException(
            String.format(
                "Argument parsing failed for %s: argument is required",
                arg
            )
        );
    }

    /**
     * Convenience constructor-like-method for exception caused
     * when the same argument is parsed twice.
     * @param arg Argument's long key that is parsed twice.
     * @return Constructed exception object
     */
    public static KArgumentParseException argumentAlreadyParsed(final String arg) {
        return new KArgumentParseException(
            String.format(
                "Argument parsing failed for %s: argument has been already parsed",
                arg
            )
        );
    }

    public KArgumentParseException(final String message) {
        super(message);
    }

}
