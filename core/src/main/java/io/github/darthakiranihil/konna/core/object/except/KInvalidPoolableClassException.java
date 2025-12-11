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
import io.github.darthakiranihil.konna.core.except.KExceptionSeverity;

/**
 * Exception thrown when there is an attempt of creating an
 * {@link io.github.darthakiranihil.konna.core.object.KObjectPool}
 * or {@link io.github.darthakiranihil.konna.core.object.KWeakObjectPool} of a class that
 * does not fit poolable object criteria: the class contains two methods annotated with
 * {@link io.github.darthakiranihil.konna.core.object.KOnPoolableObjectObtain} and
 * {@link io.github.darthakiranihil.konna.core.object.KOnPoolableObjectRelease}.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public class KInvalidPoolableClassException extends KException {

    public KInvalidPoolableClassException(final String message) {
        super(message);
    }

    @Override
    public KExceptionSeverity getSeverity() {
        return KExceptionSeverity.FATAL;
    }
}
