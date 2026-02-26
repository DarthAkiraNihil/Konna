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

package io.github.darthakiranihil.konna.level;

import io.github.darthakiranihil.konna.core.io.KAssetDefinition;

/**
 * Interface for a class that transforms and asset definition
 * to an object of specific type.
 * @param <T> Transformation result type
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public interface KObjectTilePropertyMapper<T> {

    /**
     * Maps a definition to the object.
     * @param definition Definition to map
     * @return The mapped object
     */
    T map(KAssetDefinition definition);

}
