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

package io.github.darthakiranihil.konna.core.object.registry;

import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KObjectInstantiationType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class KObjectRegistry extends KObject {

    private KObjectRegistry() {

    }

    private static final Set<KObjectRegistryRecord> OBJECTS = new HashSet<>();

    public static void push(final KObject obj, final KObjectInstantiationType instantiationType) {
        KObjectRegistry.OBJECTS.add(new KObjectRegistryRecord(obj, instantiationType));
    }

    public static void remove(final UUID objectId) {
        KObjectRegistry.OBJECTS.removeIf(
            (x) -> x.object().id() == objectId
        );
    }

    public static Set<KObjectRegistryRecord> listObjects() {
        return KObjectRegistry.OBJECTS;
    }

}
