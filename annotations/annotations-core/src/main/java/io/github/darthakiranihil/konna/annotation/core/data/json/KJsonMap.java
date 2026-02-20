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

package io.github.darthakiranihil.konna.annotation.core.data.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

/**
 * Indicates the type of map-like field elements (the generic type).
 * It is required as Java uses type erasure on generics, so it is impossible
 * to get list generic parameter at runtime.
 * Other fields are not affected by this annotation,
 * at least in the standard implementation of deserializer
 *
 * @author Darth Akira Nihil
 * @since 0.2.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.RECORD_COMPONENT, ElementType.TYPE})
public @interface KJsonMap {

    /**
     * Returns the type of the map that should be
     * created when deserializing an object. The type must not be abstract!
     * @return The type to use for map instantiation
     */
    Class<? extends Map> mapType();
    /**
     * Returns the type of map values.
     * @return The actual type
     */
    Class<?> valueType();

}
