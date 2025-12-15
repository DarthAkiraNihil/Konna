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

/**
 * Exception that is caused by incorrect way of handling a Json value.
 * The common reasons are:
 * <ul>
 *      <li>returning iterator from a non-array Json value</li>
 *      <li>casting internal value into invalid type</li>
 *      <li>getting entry set of a non-object JsonValue</li>
 * </ul>
 *
 * By default, the exception is fatal
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public class KJsonValueException extends KException {

    private static final String
        TYPE_MISMATCH_MESSAGE_TEMPLATE =
                "Cannot get object from the json value: type mismatch."
            +   "Requested: %s, actual type: %s";

    public KJsonValueException(
        final KJsonValueType requestedType,
        final KJsonValueType actualType
    ) {
        super(String.format(
            KJsonValueException.TYPE_MISMATCH_MESSAGE_TEMPLATE,
            requestedType,
            actualType
        ));
    }

    public KJsonValueException(final String message) {
        super(message);
    }

}
