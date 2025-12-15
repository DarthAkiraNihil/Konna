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

package io.github.darthakiranihil.konna.core.engine.except;

import io.github.darthakiranihil.konna.core.except.KException;

/**
 * Exception thrown when an engine component could not be loaded.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public class KComponentLoadingException extends KException {

    public KComponentLoadingException(final Throwable cause) {
        super(cause);
    }

    public KComponentLoadingException(final String message) {
        super(message);
    }

}
