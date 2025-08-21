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
 * Indicates the type of list-like field elements (the generic type).
 * It is required as Java uses type erasure on generics, so it is impossible
 * to get list generic parameter at runtime.
 * Other fields are not affected by this annotation, at least in the standard implementation of deserializer
 *
 * @see io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonDeserializer
 * @author Darth Akira Nihil
 * @since 0.1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface KJsonArray {
    /**
     * Returns the type of list elements.
     * @return The actual type
     */
    Class<?> elementType();
}
