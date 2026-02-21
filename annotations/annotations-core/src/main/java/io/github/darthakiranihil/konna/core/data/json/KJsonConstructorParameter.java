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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Sets name for the constructor parameter that should be used when deserializing a class.
 * Deserializer may use it in case deserialized class contains
 * final fields, that only can be set using constructors. It is recommended to
 * map them in that way that deserializing class in serialized representation contains
 * the data required for deserialization (basically serialized and deserialized representations
 * of the class should remain the same)
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface KJsonConstructorParameter {
    /**
     * Returns the name of the constructor parameter.
     * @return The actual name
     */
    String name();
}
