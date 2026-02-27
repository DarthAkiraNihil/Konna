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

package io.github.darthakiranihil.konna.level.property.factory;

import io.github.darthakiranihil.konna.level.property.KTileProperty;

/**
 * Interface for specialized factory that is used to box
 * a value into a tile property container.
 * @param <T> Type of result tile property container.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public interface KTilePropertyFactory<T extends KTileProperty> {

    /**
     * Creates a new property.
     * @param value Value of the property
     * @return Boxed property
     */
    T create(Object value);

}
