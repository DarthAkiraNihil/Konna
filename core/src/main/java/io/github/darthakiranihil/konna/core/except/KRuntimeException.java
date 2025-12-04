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

package io.github.darthakiranihil.konna.core.except;

/**
 * Class for unchecked Konna exceptions.
 * @see KThrowable
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public abstract class KRuntimeException extends RuntimeException implements KThrowable {

    /**
     * Constructs exception with a provided message.
     * @param message Exception message
     */
    public KRuntimeException(final String message) {
        super(message);
    }

    /**
     * Constructs exception with a provided cause.
     * @param cause The throwable caused the exception
     */
    public KRuntimeException(final Throwable cause) {
        super(cause);
    }

    /**
     * Constructs exception with provided message and cause.
     * @param message Exception message
     * @param cause The throwable that caused the exception
     */
    public KRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
