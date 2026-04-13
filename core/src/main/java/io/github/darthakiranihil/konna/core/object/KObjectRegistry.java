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

import org.jspecify.annotations.Nullable;

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

    /**
     * Constant for tag attached to all synthetic records (that store non-KObjects).
     */
    String SYNTHETIC_TAG = "synthetic";

    /**
     * Adds an object to this registry.
     * @param object Object to add
     * @return Registry record created for pushed object
     *
     * @since 0.6.0
     */
    KObjectRegistryRecord pushObject(Object object);

    /**
     * Adds an object to this registry. Pushing an object with this method guarantees that it
     * won't be deleted nor collected by GC, unless it is explicitly deleted.
     * @param object Object to add
     * @return Registry record created for pushed object
     *
     * @since 0.6.0
     */
    KObjectRegistryRecord pushImmortalObject(Object object);

    /**
     * @param objectId ID of object to get record of
     * @return Registry record associated with passed object id or {@code null} if it is not found
     *
     * @since 0.6.0
     */
    @Nullable KObjectRegistryRecord getObject(UUID objectId);

    /**
     * Removes a registry record for specific object, if corresponding id is presented
     * in the registry.
     * @param objectId ID of object to remove
     *
     * @since 0.6.0
     */
    void removeObject(UUID objectId);

    /**
     * @param tag Tag to be in found objects
     * @return Set of objects that are in this registry and have specified tag
     *
     * @since 0.6.0
     */
    Set<KObjectRegistryRecord> getObjectsWithTag(String tag);

    /**
     * @param clazz Required type of referenced objects
     * @return Set of objects that are in this registry and stored instances have specified type
     *
     * @since 0.6.0
     */
    Set<KObjectRegistryRecord> getObjectsOfType(Class<?> clazz);

    /**
     * @return Set of all objects stored in this registry
     *
     * @since 0.6.0
     */
    Set<KObjectRegistryRecord> getObjects();


}
