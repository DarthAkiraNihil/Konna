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

import io.github.darthakiranihil.konna.core.except.KException;
import io.github.darthakiranihil.konna.core.except.KThrowable;
import io.github.darthakiranihil.konna.core.except.KExceptionSeverity;

/**
 * Exception thrown when {@link io.github.darthakiranihil.konna.core.object.KObjectPool} or
 * {@link io.github.darthakiranihil.konna.core.object.KWeakObjectPool} do not contain an
 * object that is not used by anyone. At the moment it is default behaviour but may be changed
 * later. By default, the exception is fatal.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public class KEmptyObjectPoolException extends KException implements KThrowable {

    /**
     * Constructs exception with provided class which obtaining from pool
     * has been failed.
     * @param clazz Class that failed to be retrieved from pool
     */
    public KEmptyObjectPoolException(final Class<?> clazz) {
        super(
            String.format(
                "Cannot get object of class %s from its pool: all pool objects are in use",
                clazz
            )
        );
    }

    @Override
    public KExceptionSeverity getSeverity() {
        return KExceptionSeverity.FATAL;
    }
}
