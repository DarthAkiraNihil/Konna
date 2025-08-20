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
 * Interface that provides methods to serialize different objects
 * into json values, presented by {@link KJsonValue}
 *
 * @see io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonSerializer
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public interface KJsonSerializer {

    /**
     * Serializes an object into a json value
     * @param object Object to serialize
     * @param clazz Class of the serialized object
     * @return Serialized object into KJsonValue
     * @param <T> Generic parameter of type of the serialized object
     * @throws KJsonSerializationException If it fails to serialize the object
     */
    <T> KJsonValue serialize(T object, Class<? extends T> clazz) throws KJsonSerializationException;

}
