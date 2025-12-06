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

package io.github.darthakiranihil.konna.core.data.json;

import io.github.darthakiranihil.konna.core.data.json.except.KJsonValueException;

import java.util.List;
import java.util.Map;

/**
 * Enumeration for representing the type of json value.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public enum KJsonValueType {

    /**
     * The value is a json object.
     */
    OBJECT,
    /**
     * The value is an array.
     */
    ARRAY,
    /**
     * The value is an integral number.
     */
    NUMBER_INT,
    /**
     * The value is a float.
     */
    NUMBER_FLOAT,
    /**
     * The value is a string.
     */
    STRING,
    /**
     * The value is true of false.
     */
    BOOLEAN,
    /**
     * The value is null.
     */
    NULL;

    public static KJsonValueType fromObject(final Object o) {

        if (o == null) {
            return NULL;
        }

        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return ARRAY;
        }

        return switch (o) {
            case Boolean __ -> BOOLEAN;
            case Integer __ -> NUMBER_INT;
            case Byte __ -> NUMBER_INT;
            case Short __ -> NUMBER_INT;
            case Long __ -> NUMBER_INT;
            case Float __ -> NUMBER_FLOAT;
            case Double __ -> NUMBER_FLOAT;
            case Character __ -> STRING;
            case String __ -> STRING;
            case List<?> __ -> ARRAY;
            case Map<?, ?> __ -> OBJECT;
            default ->
                throw new KJsonValueException(String.format("Cannot resolve json value type for %s", o.getClass()));
        };

    }

}
