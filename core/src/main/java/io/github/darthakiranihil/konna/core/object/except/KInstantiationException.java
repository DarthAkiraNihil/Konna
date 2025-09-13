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

package io.github.darthakiranihil.konna.core.object.except;

import io.github.darthakiranihil.konna.core.except.KRuntimeException;
import io.github.darthakiranihil.konna.core.except.KThrowable;
import io.github.darthakiranihil.konna.core.except.KThrowableSeverity;

/**
 * Exception thrown when {@link io.github.darthakiranihil.konna.core.object.KActivator}
 * failed to create an object.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public class KInstantiationException extends KRuntimeException implements KThrowable {

    /**
     * Constructs exception with given class and cause.
     * @param clazz Class that failed to be instantiated
     * @param cause Cause of failure
     */
    public KInstantiationException(final Class<?> clazz, final Throwable cause) {
        super(
            String.format(
                "Cannot instantiate an object of class: %s - %s",
                clazz,
                cause
            )
        );
    }

    @Override
    public KThrowableSeverity getSeverity() {
        return KThrowableSeverity.FATAL;
    }
}
