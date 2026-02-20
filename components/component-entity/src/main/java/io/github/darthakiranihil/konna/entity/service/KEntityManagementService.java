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
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.engine.KComponentServiceMetaInfo;
import io.github.darthakiranihil.konna.core.engine.KServiceEndpoint;
import io.github.darthakiranihil.konna.core.log.KSystemLogger;
import io.github.darthakiranihil.konna.core.message.KMessenger;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.entity.KEntity;
import io.github.darthakiranihil.konna.entity.KEntityFactory;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Service for handling entities that are active during this frame
 * (or something like that).
 *
 * @since 0.4.0
 * @author Darth Akira Nihil
 */
@KSingleton
@KComponentServiceMetaInfo(
    name = "EntityManagementService"
)
public class KEntityManagementService extends KObject {

    private final KEntityFactory entityFactory;
    private final KActivator activator;

    private final Map<UUID, KEntity> activeEntities;
    private final Map<UUID, KEntity> inactiveEntities;

    private @Nullable KMessenger messenger;

    public KEntityManagementService(
        @KInject final KActivator activator,
        @KInject final KEntityFactory entityFactory
    ) {

        super(
            "Entity.EntityManagementService",
            KStructUtils.setOfTags(KTag.DefaultTags.SERVICE)
        );

        this.activator = activator;
        this.entityFactory = entityFactory;

        this.activeEntities = new HashMap<>();
        this.inactiveEntities = new HashMap<>();

    }

    @KServiceEndpoint(
        route = "createEntity",
        converter = KInternals.MessageToEntityCreationDataConverter.class
    )
    protected void createEntity(
        final String entityName,
        final String entityType
    ) {

        KEntity created = this.entityFactory.createEntity(entityName, entityType);
        this.activeEntities.put(created.id(), created);
        KSystemLogger.debug(
            "ActiveEntitiesService",
            "Created entity [type=%s, name=%s, id=%s]",
            entityType,
            entityName,
            created.id()
        );

        this.sendEntityMessage(created, "entityCreated");

    }

    @KServiceEndpoint(
        route = "restoreEntity",
        converter = KInternals.MessageToEntityRestorationDataConverter.class
    )
    protected void restoreEntity(
        final String entityName,
        final String entityType,
        final KJsonValue data
    ) {

        KEntity created = this.entityFactory.createEntity(entityName, entityType, data);
        this.activeEntities.put(created.id(), created);
        KSystemLogger.debug(
            "ActiveEntitiesService",
            "Created entity [type=%s, name=%s, id=%s]",
            entityType,
            entityName,
            created.id()
        );

        this.sendEntityMessage(created, "entityCreated");

    }

    @KServiceEndpoint(
        route = "deactivateEntity",
        converter = KInternals.MessageToEntityIdConverter.class
    )
    protected void deactivateEntity(
        final UUID entityId
    ) {

        if (
                this.inactiveEntities.containsKey(entityId)
            ||  !this.activeEntities.containsKey(entityId)
        ) {
            return;
        }

        KEntity deactivated = this.activeEntities.remove(entityId);
        this.inactiveEntities.put(entityId, deactivated);
        KSystemLogger.debug(
            "ActiveEntitiesService",
            "Deactivated entity [type=%s, name=%s, id=%s]",
            deactivated.type(),
            deactivated.name(),
            deactivated.id()
        );

        this.sendEntityMessage(deactivated, "entityDeactivated");

    }

    @KServiceEndpoint(
        route = "activateEntity",
        converter = KInternals.MessageToEntityIdConverter.class
    )
    protected void activateEntity(
        final UUID entityId
    ) {

        if (
                !this.inactiveEntities.containsKey(entityId)
            ||  this.activeEntities.containsKey(entityId)
        ) {
            return;
        }

        KEntity activated = this.inactiveEntities.remove(entityId);
        this.activeEntities.put(entityId, activated);
        KSystemLogger.debug(
            "ActiveEntitiesService",
            "Activated entity [type=%s, name=%s, id=%s]",
            activated.type(),
            activated.name(),
            activated.id()
        );

        this.sendEntityMessage(activated, "entityActivated");

    }

    @KServiceEndpoint(
        route = "destroyEntity",
        converter = KInternals.MessageToEntityIdConverter.class
    )
    protected void destroyEntity(
        final UUID entityId
    ) {

        boolean flag = false;
        KEntity deleted = null;
        if (this.activeEntities.containsKey(entityId)) {
            deleted = this.activeEntities.remove(entityId);
            this.activator.deleteObject(deleted);
            flag = true;
        } else if (this.inactiveEntities.containsKey(entityId)) {
            deleted = this.inactiveEntities.remove(entityId);
            this.activator.deleteObject(deleted);
            flag = true;
        }

        if (!flag) {
            return;
        }

        KSystemLogger.debug(
            "ActiveEntitiesService",
            "Destroyed entity [type=%s, name=%s, id=%s]",
            deleted.type(),
            deleted.name(),
            deleted.id()
        );

        this.sendEntityMessage(deleted, "entityDestroyed");

    }

    @Nullable KEntity getEntity(final UUID entityId) {

        if (this.activeEntities.containsKey(entityId)) {
            return this.activeEntities.get(entityId);
        }

        if (this.inactiveEntities.containsKey(entityId)) {
            return this.inactiveEntities.get(entityId);
        }

        return null;

    }

    /*
    public List<KEntity> getActiveEntities() {
        return List.copyOf(this.activeEntities.values());
    }
    */

    public void setMessenger(final KMessenger messenger) {
        this.messenger = messenger;
    }

    private void sendEntityMessage(
        final KEntity entity,
        final String messageId
    ) {

        if (this.messenger == null) {
            return;
        }

        KUniversalMap body = new KUniversalMap();
        body.put("id", entity.id());
        body.put("type", entity.name());
        body.put("name", entity.type());

        this.messenger.sendRegular(
            messageId,
            body
        );

    }

}
