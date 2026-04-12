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

import io.github.darthakiranihil.konna.core.app.KFrameEvent;
import io.github.darthakiranihil.konna.core.app.KFrameTaskDescription;
import io.github.darthakiranihil.konna.core.app.KFrameTaskScheduler;
import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.di.KSingleton;
import io.github.darthakiranihil.konna.core.engine.KService;
import io.github.darthakiranihil.konna.core.engine.KServiceEndpoint;
import io.github.darthakiranihil.konna.core.log.system.KSystemLogger;
import io.github.darthakiranihil.konna.core.message.KBodyValue;
import io.github.darthakiranihil.konna.core.message.KMessenger;
import io.github.darthakiranihil.konna.core.object.KDefaultTags;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.entity.KEntity;
import io.github.darthakiranihil.konna.entity.KEntityBehaviour;
import io.github.darthakiranihil.konna.entity.KEntityFactory;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Service for handling entities that are active during this frame
 * (or something like that).
 *
 * @since 0.4.0
 * @author Darth Akira Nihil
 */
@KSingleton
public class KEntityManagementService extends KObject implements KService {

    /**
     * Description of task that runs {@link KEntityBehaviour#update()} method of all active
     * entities.
     */
    public static final KFrameTaskDescription ON_TICK_TASK = KFrameTaskDescription.ofPersistent(
        "EntityManagementService.updateEntiies",
        KFrameEvent.TICK,
        2
    );

    /**
     * Description of task that runs extra
     * behavior methods on all entities that have requested this.
     * The extra methods are {@link KEntityBehaviour#awake()}, {@link KEntityBehaviour#onEnable()},
     * {@link KEntityBehaviour#onDisable()} and {@link KEntityBehaviour#onDestroy()}.
     */
    public static final KFrameTaskDescription
        PROCESS_BEHAVIOURS_TASK = KFrameTaskDescription.ofImmediateTemporal(
        "EntityManagementService.processBehaviours",
        KFrameEvent.FRAME_FINISHED,
        3
    );

    private enum BehaviourProcessingAction {
        CREATE,
        ACTIVATE,
        DEACTIVATE,
        DESTROY
    }

    private record BehaviourProcessingData(
        KEntity entity,
        BehaviourProcessingAction action
    ) {

    }

    private final KEntityFactory entityFactory;

    private final Map<UUID, KEntity> activeEntities;
    private final Map<UUID, KEntity> inactiveEntities;
    private final Queue<BehaviourProcessingData> behaviourProcessingQueue;

    private final KFrameTaskScheduler frameTaskScheduler;
    private final KMessenger messenger;


    /**
     * Standard constructor.
     * @param entityFactory Entity factory for create entities
     * @param frameTaskScheduler Frame task scheduler to schedule its tasks.
     * @param messenger Messenger created inside
     *                  {@link io.github.darthakiranihil.konna.entity.KEntityComponent}
     *                  to send messages
     */
    @KInject
    public KEntityManagementService(
        final KMessenger messenger,
        final KEntityFactory entityFactory,
        final KFrameTaskScheduler frameTaskScheduler
    ) {

        super(
            "EntityManagementService",
            Collections.singleton(KDefaultTags.SERVICE)
        );

        this.entityFactory = entityFactory;
        this.frameTaskScheduler = frameTaskScheduler;
        this.messenger = messenger;

        this.activeEntities = new HashMap<>();
        this.inactiveEntities = new HashMap<>();
        this.behaviourProcessingQueue = new ConcurrentLinkedQueue<>();

        this.frameTaskScheduler.scheduleTask(
            ON_TICK_TASK,
            this::updateEntities
        );

    }

    /**
     * Creates an active entity with specific name and type.
     * If it has been successfully created, a message with id {@code Entity.entityCreated}
     * will be produced.
     * It may fail if type is unknown for the entity factory.
     * @param entityName Name of created entity
     * @param entityType Type of created entity
     */
    @KServiceEndpoint(
        route = "createEntity"
    )
    protected void createEntity(
        @KBodyValue(KInternals.NAME_KEY) final String entityName,
        @KBodyValue(KInternals.TYPE_KEY) final String entityType
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

        this.scheduleBehaviourProcessing(BehaviourProcessingAction.CREATE, created);
        this.sendEntityMessage(created, "entityCreated");

    }

    /**
     * Creates an active entity with specific name and type with restoring its data.
     * If it has been successfully created and deserialized,
     * a message with id {@code Entity.entityCreated} will be produced.
     * It may fail if type is unknown for the entity factory or data deserialization has failed.
     * @param entityName Name of created entity
     * @param entityType Type of created entity
     * @param data Data of restored entity
     */
    @KServiceEndpoint(
        route = "restoreEntity"
    )
    protected void restoreEntity(
        @KBodyValue(KInternals.NAME_KEY) final String entityName,
        @KBodyValue(KInternals.TYPE_KEY) final String entityType,
        @KBodyValue(KInternals.DATA_KEY) final KJsonValue data
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

        this.scheduleBehaviourProcessing(BehaviourProcessingAction.CREATE, created);
        this.sendEntityMessage(created, "entityCreated");

    }

    /**
     * Deactivates entity with specific id. It won't have any effect if the
     * entity does not exist or already deactivated. Otherwise, it deactivates it
     * and produces a message with id {@code Entity.entityDeactivated}.
     * @param entityId ID of entity to deactivate
     */
    @KServiceEndpoint(
        route = "deactivateEntity"
    )
    protected void deactivateEntity(
        @KBodyValue(KInternals.ENTITY_ID_KEY) final UUID entityId
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

        this.scheduleBehaviourProcessing(BehaviourProcessingAction.DEACTIVATE, deactivated);
        this.sendEntityMessage(deactivated, "entityDeactivated");

    }

    /**
     * Activates entity with specific id. It won't have any effect if the
     * entity does not exist or already activated. Otherwise, it activates it
     * and produces a message with id {@code Entity.entityActivated}.
     * @param entityId ID of entity to activate
     */
    @KServiceEndpoint(
        route = "activateEntity"
    )
    protected void activateEntity(
        @KBodyValue(KInternals.ENTITY_ID_KEY) final UUID entityId
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

        this.scheduleBehaviourProcessing(BehaviourProcessingAction.ACTIVATE, activated);
        this.sendEntityMessage(activated, "entityActivated");

    }

    /**
     * Destroys entity with specific id. It won't have any effect if the
     * entity does not exist. Otherwise, it destroys it
     * and produces a message with id {@code Entity.entityDestroyed}.
     * @param entityId ID of entity to destroy
     */
    @KServiceEndpoint(
        route = "destroyEntity"
    )
    protected void destroyEntity(
        @KBodyValue(KInternals.ENTITY_ID_KEY) final UUID entityId
    ) {

        boolean flag = false;
        KEntity deleted = null;
        // todo: explicitly delete by delete() method in KManagedObject
        if (this.activeEntities.containsKey(entityId)) {
            deleted = this.activeEntities.remove(entityId);
            flag = true;
        } else if (this.inactiveEntities.containsKey(entityId)) {
            deleted = this.inactiveEntities.remove(entityId);
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

        this.scheduleBehaviourProcessing(BehaviourProcessingAction.DESTROY, deleted);
        this.sendEntityMessage(deleted, "entityDestroyed");

    }

    private void sendEntityMessage(
        final KEntity entity,
        final String messageId
    ) {

        KUniversalMap body = new KUniversalMap();
        body.put("id", entity.id());
        body.put("type", entity.name());
        body.put("name", entity.type());
        body.put("instance", entity);

        this.messenger.sendRegular(
            messageId,
            body
        );

    }

    private void updateEntities() {

        this
            .activeEntities
            .values()
            .stream()
            .map(KEntity::getBehaviours)
            .forEach(b -> b.forEach(KEntityBehaviour::update));

    }

    private void runExtraBehaviourMethods() {
        while (!this.behaviourProcessingQueue.isEmpty()) {
            var data = this.behaviourProcessingQueue.poll();
            KEntity entity = data.entity;
            List<KEntityBehaviour> behaviours = entity.getBehaviours();

            switch (data.action()) {
                case CREATE: {
                    behaviours.forEach(KEntityBehaviour::awake);
                    behaviours.forEach(KEntityBehaviour::onEnable);
                    break;
                }
                case ACTIVATE: {
                    behaviours.forEach(KEntityBehaviour::onEnable);
                    break;
                }
                case DEACTIVATE: {
                    behaviours.forEach(KEntityBehaviour::onDisable);
                    break;
                }
                case DESTROY: {
                    behaviours.forEach(KEntityBehaviour::onDestroy);
                    break;
                }
            }
        }
    }

    private void scheduleBehaviourProcessing(
        final BehaviourProcessingAction action,
        final KEntity entity
    ) {
        this.behaviourProcessingQueue.add(
            new KEntityManagementService.BehaviourProcessingData(
                entity, action
            )
        );

        this.frameTaskScheduler.scheduleTask(
            PROCESS_BEHAVIOURS_TASK,
            this::runExtraBehaviourMethods
        );
    }

}
