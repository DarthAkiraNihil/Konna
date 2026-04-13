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

package io.github.darthakiranihil.konna.core.object;

import java.util.Set;
import java.util.UUID;

/**
 * Interface for utility class that stores records about all objects, created with
 * {@link KActivator} or another tools.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public interface KObjectRegistry {

    String SYNTHETIC_TAG = "synthetic";

    KObjectRegistryRecord pushObject(Object object);
    KObjectRegistryRecord pushImmortalizedObject(Object object);
    void immortalizeObject(UUID recordId);
    void removeObject(UUID recordId);
    Set<KObjectRegistryRecord> getObjectsWithTag(String tag);
    Set<KObjectRegistryRecord> getObjectsOfType(String tag);
    Set<KObjectRegistryRecord> getObjects();


}
