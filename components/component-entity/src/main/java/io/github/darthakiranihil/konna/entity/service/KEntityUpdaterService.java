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

package io.github.darthakiranihil.konna.entity.service;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.engine.KComponentServiceMetaInfo;
import io.github.darthakiranihil.konna.core.log.KSystemLogger;
import io.github.darthakiranihil.konna.core.message.KMessenger;
import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.core.struct.KTriplet;
import io.github.darthakiranihil.konna.core.util.KReflectionUtils;
import io.github.darthakiranihil.konna.entity.KEntity;
import io.github.darthakiranihil.konna.entity.KEntityDataComponent;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.*;

@KComponentServiceMetaInfo(
    name = "EntityUpdaterService"
)
public class KEntityUpdaterService {

    private final KActiveEntitiesService activeEntitiesService;

    private @Nullable KMessenger messenger;

    public KEntityUpdaterService(
        @KInject KActiveEntitiesService activeEntitiesService
    ) {
        this.activeEntitiesService = activeEntitiesService;
    }

    protected void updateEntity(
        final UUID entityId,
        final Map<Class<? extends KEntityDataComponent>, List<KPair<String, Object>>> updatedProperties
    ) {

        KEntity updated = this.activeEntitiesService.getEntity(entityId);
        if (updated == null) {
            return;
        }

        List<KEntityDataComponent> updatedComponents = new LinkedList<>();
        for (var updateEntry: updatedProperties.entrySet()) {

            Class<? extends KEntityDataComponent> componentClass = updateEntry.getKey();
            KEntityDataComponent component = updated.getComponent(componentClass);
            if (component == null) {
                KSystemLogger.warning(
                    "EntityUpdaterService",
                    "Cannot update component %s of entity %s[%s]: component not found",
                    componentClass,
                    updated.name(),
                    updated.id()
                );
                continue;
            }

            for (var property: updateEntry.getValue()) {
                String name = property.first();
                Method setter = KReflectionUtils.getSetter(componentClass, name);
                if (setter == null) {
                    KSystemLogger.warning(
                        "EntityUpdaterService",
                        "Could not get setter for entity property %s [data_component=%s]",
                        name,
                        componentClass
                    );
                    continue;
                }

                KReflectionUtils.invokeMethod(
                    setter, component, property.second()
                );
            }

            updatedComponents.add(component.readonlyClone());
        }

        KSystemLogger.debug(
            "EntityUpdaterService",
            "Updated %d components for entity %s[%s]",
            updatedComponents.size(),
            updated.name(),
            updated.id()
        );

        if (messenger == null) {
            return;
        }

        KUniversalMap body = new KUniversalMap();
        body.put("id", updated.id());
        body.put("name", updated.name());
        body.put("type", updated.type());
        body.put("updated_components", updatedComponents);
        this.messenger.sendRegular(
            "entityUpdated",
            body
        );
    }

    public void setMessenger(final KMessenger messenger) {
        this.messenger = messenger;
    }


}
