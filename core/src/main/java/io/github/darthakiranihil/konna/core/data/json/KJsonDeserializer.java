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

import io.github.darthakiranihil.konna.core.data.json.except.KJsonSerializationException;

/**
 * Interface providing methods to deserialize json values into objects
 * @see io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonDeserializer
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public interface KJsonDeserializer {

    /**
     * Deserializes a json value into a new object of passed type.
     * @implSpec Implementation of this should have capability to handle list-like elements, that includes correct deserialization of list
     *           elements. As Java uses type erasure for generics, there should be a way to get list element type by another way.
     *           It can be achieved using {@link KJsonArray} annotation
     *
     * @param value Json value to deserialize
     * @param clazz Class of destination object
     * @return Deserialized object
     * @param <T> Generic type of deserialized object
     * @throws KJsonSerializationException If it fails to deserialize, mostly because of attempting to deserialize object with structure that differs from json value
     */
    <T> T deserialize(KJsonValue value, Class<?> clazz) throws KJsonSerializationException;

}
