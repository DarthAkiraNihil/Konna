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
 * Representation of a registry record of {@link KObjectRegistry}.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public interface KObjectRegistryRecord {

    /**
     * @return ID of this record
     */
    UUID recordId();

    /**
     * Returns ID of object contained by this record. If it is synthetic, it returns the same value
     * as {@link KObjectRegistryRecord#recordId()}
     * @return ID of registered object or its record ID
     */
    UUID objectId();

    /**
     * @return Whether this record is synthetic (created for a non-KObject) or real.
     */
    boolean isSynthetic();

    /**
     * @return Whether the object in this record is presented or has been deleted by GC.
     */
    boolean isPresent();

    /**
     * @return Whether the object in this record is immortal or not. Immortal objects
     *         are hold by {@link KObjectRegistry} by strong references, so they cannot be deleted.
     */
    boolean isImmortal();

    /**
     * @return Class of contained object, even if it has been deleted by GC
     */
    Class<?> getObjectClass();

    /**
     * @return Raw object contained in this record.
     * @throws io.github.darthakiranihil.konna.core.object.except.KDeletedObjectException
     *         if accessed object has been deleted by GC
     */
    Object getObject();

    /**
     * @return Object, cast to inferred generic type, contained in this record
     * @param <T> Type of retrieved object
     * @throws io.github.darthakiranihil.konna.core.object.except.KDeletedObjectException
     *         if accessed object has been deleted by GC
     */
    <T> T getCastObject();

    /**
     * @return Set of tags of contained object or, of record is synthetic,
     *         set of the only {@link KObjectRegistry#SYNTHETIC_TAG}
     */
    Set<String> getObjectTags();

}
