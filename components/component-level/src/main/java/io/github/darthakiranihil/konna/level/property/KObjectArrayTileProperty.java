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

package io.github.darthakiranihil.konna.level.property;

/**
 * Container for additional tile property that is an object array where object's
 * type is user-defined. Usually if the type is marked with corresponding annotation,
 * the annotation processor of Level generates corresponding implementations of this
 * interface for marked type.
 * @param <T> Type of object contained in this property
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public non-sealed interface KObjectArrayTileProperty<T> extends KTileProperty {

    /**
     * @return Value contained in this property
     */
    T[] getValue();

}
