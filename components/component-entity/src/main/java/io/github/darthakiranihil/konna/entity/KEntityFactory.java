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

package io.github.darthakiranihil.konna.entity;

import io.github.darthakiranihil.konna.core.data.json.KJsonValue;

/**
 * Interface for simple creating entity objects without manual resolving
 * required data components etc.
 *
 * @since 0.4.0
 * @author Darth Akira Nihil
 */
public interface KEntityFactory {

    /**
     * Creates empty entity by its type.
     * @param name Name of created entity
     * @param type Entity type
     * @return Created entity instance
     */
    KEntity createEntity(String name, String type);
    /**
     * Creates empty entity by its type, but also recovers its data components
     * contents by deserializing them from JSON.
     * @param name Name of created entity
     * @param type Entity type
     * @param data Entity data
     * @return Created entity instance with provided data
     */
    KEntity createEntity(String name, String type, KJsonValue data);

}
