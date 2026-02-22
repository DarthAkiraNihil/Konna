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

/**
 * Container for entity metadata that is used to create real entity objects.
 * @param typeName Type name of the entity
 * @param dataExtensionList List of all types that this type is extended from by data components
 * @param dataComponents List of data components used by this entity type
 * @param behaviourExtensionList List of all types that this type is extended from by behaviours
 * @param behaviours List of behaviours used by this entity type
 *
 * @since 0.4.0
 * @author Darth Akira Nihil
 */
public record KEntityMetadata(
    String typeName,
    String[] dataExtensionList,
    Class<? extends KEntityDataComponent>[] dataComponents,
    String[] behaviourExtensionList,
    Class<? extends KEntityBehaviour>[] behaviours
) {

}
