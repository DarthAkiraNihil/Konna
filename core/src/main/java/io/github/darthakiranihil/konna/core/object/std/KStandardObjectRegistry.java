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

package io.github.darthakiranihil.konna.core.object.std;

import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.object.*;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;

import java.util.*;

/**
 * Utility class that stores records about all objects, created with
 * {@link io.github.darthakiranihil.konna.core.object.KActivator} (excluding temporal). Should be
 * used in debugging, though it is possible to use it in other way.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@KSingleton(immortal = true)
public final class KStandardObjectRegistry extends KObject implements KObjectRegistry {

    private final Set<KObjectRegistryRecord> objects;

    public KStandardObjectRegistry() {
        super(
            "object_registry",
            KStructUtils.setOfTags(
                KTag.DefaultTags.SYSTEM,
                KTag.DefaultTags.STD
            )
        );
        this.objects = new HashSet<>();
    }

    /**
     * Pushes an object to the registry. If id of the pushed object already
     * exists in the registry, no record will be added to the registry.
     * @param obj Object to push
     * @param instantiationType Instantiation type of the object
     */
    @Override
    public void pushObjectToRegistry(
        final KObject obj,
        final KObjectInstantiationType instantiationType
    ) {
        this.objects.add(
            new KObjectRegistryRecord(
                obj,
                instantiationType
            )
        );
    }

    /**
     * Removes object from the registry. If the object with given id
     * is not registered, nothing will happen.
     * @param objectId ID of removed object.
     */
    @Override
    public void removeObjectFromRegistry(final UUID objectId) {
        this.objects.removeIf(
            (x) -> x.object().id() == objectId
        );
    }

    /**
     * Lists all objects that have been registered.
     * @return Set of all registered objects.
     */
    @Override
    public Set<KObjectRegistryRecord> listObjects() {
        return Collections.unmodifiableSet(this.objects);
    }

}
