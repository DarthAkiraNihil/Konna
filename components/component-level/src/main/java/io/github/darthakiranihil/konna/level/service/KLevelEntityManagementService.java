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

package io.github.darthakiranihil.konna.level.service;

import io.github.darthakiranihil.konna.core.app.KFrameEvent;
import io.github.darthakiranihil.konna.core.app.KFrameTaskScheduler;
import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.engine.KComponentServiceMetaInfo;
import io.github.darthakiranihil.konna.core.engine.KServiceEndpoint;
import io.github.darthakiranihil.konna.core.except.KClassNotFoundException;
import io.github.darthakiranihil.konna.core.io.KAssetDefinition;
import io.github.darthakiranihil.konna.core.io.KAssetDefinitionRule;
import io.github.darthakiranihil.konna.core.io.except.KAssetDefinitionError;
import io.github.darthakiranihil.konna.core.log.system.KSystemLogger;
import io.github.darthakiranihil.konna.core.message.*;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.core.util.KClassUtils;
import io.github.darthakiranihil.konna.level.entity.*;
import io.github.darthakiranihil.konna.level.KLevel;
import io.github.darthakiranihil.konna.level.KLevelSector;
import io.github.darthakiranihil.konna.level.KLevelSectorSlice;
import io.github.darthakiranihil.konna.level.layer.tool.KLevelEntityLayerTool;
import org.jspecify.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Level entity management service for handling game entities
 * inside current active level.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
@KSingleton
@KComponentServiceMetaInfo(
    name = "LevelEntityManagementService"
)
@KRequiresEvent(name = "levelLoaded", simple = false, type = KLevel.class)
@KRequiresEvent(name = "levelUnloaded")
@SuppressWarnings("FieldCanBeLocal,unused")
public class KLevelEntityManagementService extends KObject {

    private static final String
        MOVE_ENTITIES_TASK_ID = "LevelEntitiyManagementService.moveEntities";
    private static final String
        CLEAN_DESTROYED_ENTITIES_TASK_ID = "LevelEntitiyManagementService.cleanDestroyedEntities";

    private static final int PRIORITY = 1;

    private final KEventAction<KLevel> onLeveLoadedConsumer = this::onLevelLoaded;
    private final KSimpleEventAction onLevelUnloadedConsumer = this::onLevelUnloaded;

    private final Map<UUID, KControllableEntity> controllables;
    private final Map<UUID, KStaticEntity> statics;
    private final Map<UUID, KAutonomousEntity> autonomouses;

    private final Queue<KLevelEntity> deletionQueue;

    private final KEventSystem eventSystem;
    private final KActivator activator;
    private final KFrameTaskScheduler frameTaskScheduler;

    private @Nullable KMessenger messenger;
    private @Nullable KLevel currentLevel;

    /**
     * Standard constructor.
     * @param eventSystem Event system to get {@code levelLoaded} and {@code levelUnloaded}
     *                    events
     * @param activator Activator to create autonomous entities' controllers
     */
    public KLevelEntityManagementService(
        @KInject final KEventSystem eventSystem,
        @KInject final KFrameTaskScheduler frameTaskScheduler,
        @KInject final KActivator activator
    ) {
        super(
            "Level.LevelEntityManagementService",
            KStructUtils.setOfTags(KTag.DefaultTags.SERVICE)
        );

        KEventSubscriber<KLevel> levelLoaded = eventSystem
            .getEventSubscriber("levelLoaded");
        KSimpleEventSubscriber levelUnloaded = eventSystem
                .getSimpleEventSubscriber("levelUnloaded");

        levelUnloaded.subscribe(this.onLevelUnloadedConsumer);
        levelLoaded.subscribe(this.onLeveLoadedConsumer);

        this.controllables = new HashMap<>();
        this.statics = new HashMap<>();
        this.autonomouses = new HashMap<>();

        this.activator = activator;
        this.eventSystem = eventSystem;
        this.frameTaskScheduler = frameTaskScheduler;

        this.deletionQueue = new ConcurrentLinkedQueue<>();
    }

    /**
     * Sets a messenger for this service.
     * @param messenger Messenger to set
     */
    public void setMessenger(final KMessenger messenger) {
        this.messenger = messenger;
    }

    /**
     * <p>
     *     Schedules movement of all entities presented at the current moment.
     *     Autonomous entities are moved first, then controllable entities.
     * </p>
     * <p>
     *     If service has an assigned messenger, it sends {@code controllableEntities}
     *     message with list of all controllable entities located by {@code moved} key.
     * </p>
     * <p>
     *     All operations above will be executed on frame finishing.
     * </p>
     */
    @KServiceEndpoint(route = "moveAllEntities")
    protected void moveAllEntities() {
        this.frameTaskScheduler.scheduleTemporalTask(
            MOVE_ENTITIES_TASK_ID,
            KFrameEvent.FRAME_FINISHED,
            PRIORITY,
            this::moveEntities
        );
    }

    /**
     * Sets a direction for a controllable entity if it exists.
     * @param entityId Entity ID to move
     * @param direction New direction for moved entity
     */
    @KServiceEndpoint(route = "setDirectionForControllableEntity")
    protected void setDirectionForControllableEntity(
        @KBodyValue("entity_id") final UUID entityId,
        @KBodyValue("direction") final KVector2i direction
    ) {

        if (!this.controllables.containsKey(entityId)) {
            return;
        }

        KControllableEntity entity = this.controllables.get(entityId);
        entity.setNextMoveDirection(direction);

    }

    /**
     * <p>
     *     Creates a new autonomous entity in current level if it is presented.
     *     It will fail if deployment sector, position or controller are invalid.
     * </p>
     * <p>
     *     If an entity has been created, it will send {@code autonomousEntityCreated} message
     *     with created entity id (key {@code entity_id}), its position (key {@code position})
     *     and instance (key {@code instance})
     * </p>
     * @param entityName Name of created entity
     * @param descriptor Descriptor of created entity
     * @param sectorName Name of sector to create entity in (deployment sector)
     * @param position Position to place entity on
     * @param controller Controller for autonomous entity
     * @param controllerParams Controller params
     */
    @SuppressWarnings("unchecked")
    @KServiceEndpoint(route = "createAutonomousEntity")
    protected void createAutonomousEntity(
        @KBodyValue("name") final String entityName,
        @KBodyValue("descriptor") final String descriptor,
        @KBodyValue("sector_name") final String sectorName,
        @KBodyValue("position") final KVector2i position,
        @KBodyValue("controller") final Class<? extends KAutonomousEntityController> controller,
        @KBodyValue("params") final KAssetDefinition controllerParams
    ) {

        KLevelSector deploymentSector = this.getDeploymentSector(
            sectorName, position, "an autonomous"
        );
        if (deploymentSector == null) {
            return;
        }

        KAutonomousEntity entity = new KAutonomousEntity(
            this.eventSystem,
            entityName,
            descriptor
        );
        entity.setPosition(deploymentSector, position);

        try {
            var paramsValidator = KClassUtils
                .getGeneratedForName(
                    String.format(
                        "level.entity.%s$$ParamValidator",
                        controller.getSimpleName()
                    )
                );

            if (!KAssetDefinitionRule.class.isAssignableFrom(paramsValidator)) {
                KSystemLogger.warning(
                    this.name,
                    "Found validator is not an KAssetDefinitionRule instance!"
                        +   "Skipping validation"
                );
            } else {
                KAssetDefinitionRule validator = this.activator
                    .createObject((Class<? extends KAssetDefinitionRule>) paramsValidator);
                try {
                    validator.validate(controllerParams);
                } catch (KAssetDefinitionError e) {
                    KSystemLogger.warning(
                        this.name,
                            "Could not create an autonomous entity:"
                        +   "controller params validation failed: %s",
                        e.getMessage()
                    );
                    return;
                }
            }
        } catch (KClassNotFoundException e) {
            KSystemLogger.warning(
                this.name,
                "Could not find param validator for %s. Skipping validation",
                controller.getCanonicalName()
            );
        }

        KAutonomousEntityController controllerInstance = this
            .activator
            .createObject(
                controller,
                controller.getSimpleName(),
                controllerParams
            );

        controllerInstance.setLevel(Objects.requireNonNull(this.currentLevel));
        controllerInstance.setAssignedEntity(entity);

        entity.setController(controllerInstance);
        var tool = deploymentSector.getTool(KLevelEntityLayerTool.class);
        tool.placeEntity(position, entity);
        this.autonomouses.put(entity.id(), entity);

        this.sendMessage(entity, "autonomousEntityCreated");

    }

    /**
     * <p>
     *     Destroys an autonomous entity in current level if it is presented.
     *     If there is no loaded level, it will fail
     * </p>
     * <p>
     *     If it succeeds, it will send {@code autonomousEntityDestroyed} message
     *     with created entity id (key {@code entity_id}), its position (key {@code position})
     *     and instance (key {@code instance})
     * </p>
     * @param entityId Entity id to destroy
     */
    @KServiceEndpoint(route = "destroyAutonomousEntity")
    protected void destroyAutonomousEntity(
        @KBodyValue("entity_id") final UUID entityId
    ) {
        if (this.currentLevel == null) {
            KSystemLogger.warning(
                this.name,
                "Cannot delete an autonomous entity: no level loaded"
            );
            return;
        }

        if (!this.autonomouses.containsKey(entityId)) {
            KSystemLogger.warning(
                this.name,
                "Unknown autonomous entity id: %s",
                entityId
            );
            return;
        }

        KAutonomousEntity entity = this.autonomouses.get(entityId);
        this.deletionQueue.add(entity);
        this.scheduleCleaning();

    }

    /**
     * <p>
     *     Creates a new static entity in current level if it is presented.
     *     It will fail if deployment sector or position are invalid.
     * </p>
     * <p>
     *     If an entity has been created, it will send {@code staticEntityCreated} message
     *     with created entity id (key {@code entity_id}), its position (key {@code position})
     *     and instance (key {@code instance})
     * </p>
     * @param entityName Name of created entity
     * @param descriptor Descriptor of created entity
     * @param sectorName Name of sector to create entity in (deployment sector)
     * @param position Position to place entity on
     */
    @KServiceEndpoint(route = "createStaticEntity")
    protected void createStaticEntity(
        @KBodyValue("name") final String entityName,
        @KBodyValue("descriptor") final String descriptor,
        @KBodyValue("sector_name") final String sectorName,
        @KBodyValue("position") final KVector2i position
    ) {

        KLevelSector deploymentSector = this.getDeploymentSector(sectorName, position, "a static");
        if (deploymentSector == null) {
            return;
        }

        KStaticEntity entity = new KStaticEntity(
            this.eventSystem,
            entityName,
            descriptor
        );
        entity.setPosition(deploymentSector, position);

        var tool = deploymentSector.getTool(KLevelEntityLayerTool.class);
        tool.placeEntity(position, entity);
        this.statics.put(entity.id(), entity);

        this.sendMessage(entity, "staticEntityCreated");

    }

    /**
     * <p>
     *     Destroys a static entity in current level if it is presented.
     *     If there is no loaded level, it will fail
     * </p>
     * <p>
     *     If it succeeds, it will send {@code staticEntityDestroyed} message
     *     with created entity id (key {@code entity_id}), its position (key {@code position})
     *     and instance (key {@code instance})
     * </p>
     * @param entityId Entity id to destroy
     */
    @KServiceEndpoint(route = "destroyStaticEntity")
    protected void destroyStaticEntity(
        @KBodyValue("entity_id") final UUID entityId
    ) {
        if (this.currentLevel == null) {
            KSystemLogger.warning(
                this.name,
                "Cannot delete a static entity: no level loaded"
            );
            return;
        }

        if (!this.statics.containsKey(entityId)) {
            KSystemLogger.warning(
                this.name,
                "Unknown static entity id: %s",
                entityId
            );
            return;
        }

        KStaticEntity entity = this.statics.get(entityId);
        this.deletionQueue.add(entity);
        this.scheduleCleaning();

    }

    /**
     * <p>
     *     Creates a new controllable entity in current level if it is presented.
     *     It will fail if deployment sector or position are invalid.
     * </p>
     * <p>
     *     If an entity has been created, it will send {@code controllableEntityCreated} message
     *     with created entity id (key {@code entity_id}), its position (key {@code position})
     *     and instance (key {@code instance})
     * </p>
     * @param entityName Name of created entity
     * @param descriptor Descriptor of created entity
     * @param sectorName Name of sector to create entity in (deployment sector)
     * @param position Position to place entity on
     */
    @KServiceEndpoint(route = "createControllableEntity")
    protected void createControllableEntity(
        @KBodyValue("name") final String entityName,
        @KBodyValue("descriptor") final String descriptor,
        @KBodyValue("sector_name") final String sectorName,
        @KBodyValue("position") final KVector2i position
    ) {

        KLevelSector deploymentSector = this.getDeploymentSector(
            sectorName, position, "a controllable"
        );
        if (deploymentSector == null) {
            return;
        }

        KControllableEntity entity = new KControllableEntity(
            this.eventSystem,
            entityName,
            descriptor
        );
        entity.setPosition(deploymentSector, position);

        var tool = deploymentSector.getTool(KLevelEntityLayerTool.class);
        tool.placeEntity(position, entity);
        this.controllables.put(entity.id(), entity);

        this.sendMessage(entity, "controllableEntityCreated");

    }

    /**
     * <p>
     *     Destroys a controllable entity in current level if it is presented.
     *     If there is no loaded level, it will fail
     * </p>
     * <p>
     *     If it succeeds, it will send {@code controllableEntityDestroyed} message
     *     with created entity id (key {@code entity_id}), its position (key {@code position})
     *     and instance (key {@code instance})
     * </p>
     * @param entityId Entity id to destroy
     */
    @KServiceEndpoint(route = "destroyControllableEntity")
    protected void destroyControllableEntity(
        @KBodyValue("entity_id") final UUID entityId
    ) {
        if (this.currentLevel == null) {
            KSystemLogger.warning(
                this.name,
                "Cannot delete a controllable entity: no level loaded"
            );
            return;
        }

        if (!this.controllables.containsKey(entityId)) {
            KSystemLogger.warning(
                this.name,
                "Unknown controllable entity id: %s",
                entityId
            );
            return;
        }

        KControllableEntity entity = this.controllables.get(entityId);
        this.deletionQueue.add(entity);
        this.scheduleCleaning();

    }

    /**
     * Handles level loading by getting all loaded entities and adding them
     * to own lists to track.
     * @param level Loaded level instance
     */
    protected void onLevelLoaded(final KLevel level) {

        this.currentLevel = level;

        for (String sectorName: level.getSectorNames()) {
            KLevelSector sector = level.getSector(sectorName);

            KSize sectorSize = sector.getSize();
            for (int i = 0; i < sectorSize.width(); i++) {
                for (int j = 0; j < sectorSize.height(); j++) {
                    KLevelSectorSlice slice = sector.getSlice(i, j);
                    for (var entity: slice.entities()) {
                        switch (entity) {
                            case KControllableEntity controllable -> this.controllables.put(
                                controllable.id(),
                                controllable
                            );
                            case KStaticEntity staticEntity -> this.statics.put(
                                staticEntity.id(),
                                staticEntity
                            );
                            case KAutonomousEntity autonomous -> this.autonomouses.put(
                                autonomous.id(),
                                autonomous
                            );
                            default -> KSystemLogger.warning(
                                this.name,
                                "Unknown entity type: %s",
                                entity.getClass()
                            );
                        }
                    }
                }
            }
        }

    }

    /**
     * Handles level unloading by clearing lists of all tracked entities.
     */
    protected void onLevelUnloaded() {
        this.controllables.clear();
        this.statics.clear();
        this.autonomouses.clear();
    }

    private @Nullable KLevelSector getDeploymentSector(
        final String sectorName,
        final KVector2i position,
        final String entityType
    ) {
        if (this.currentLevel == null) {
            KSystemLogger.warning(
                this.name,
                "Cannot create %s entity: no level loaded",
                entityType
            );
            return null;
        }

        if (!this.currentLevel.hasSector(sectorName)) {
            KSystemLogger.warning(
                this.name,
                "Cannot create %s entity: no sector with name %s",
                entityType,
                sectorName
            );
        }

        KLevelSector deploymentSector = this.currentLevel.getSector(sectorName);
        KLevelSectorSlice slice = deploymentSector.getSlice(position.x(), position.y());
        if (slice.tile() == null) {
            KSystemLogger.warning(
                this.name,
                "Cannot create %s entity: invalid position",
                entityType
            );
            return null;
        }

        return deploymentSector;
    }

    private void sendMessage(final KLevelEntity entity, final String messageId) {
        if (this.messenger == null) {
            return;
        }

        KUniversalMap body = new KUniversalMap();
        body.put("id", entity.id());
        body.put("position", entity.getPosition());
        body.put("instance", entity);

        this.messenger.sendRegular(
            messageId, body
        );
    }

    private void cleanDestroyedEntities() {

        List<KLevelEntity> deleted = new ArrayList<>(this.deletionQueue.size());
        while (!this.deletionQueue.isEmpty()) {
            KLevelEntity entity = this.deletionQueue.poll();

            switch (entity) {
                case KStaticEntity s -> this.statics.remove(s.id());
                case KControllableEntity c -> this.controllables.remove(c.id());
                case KAutonomousEntity a -> this.autonomouses.remove(a.id());
            }

            var position = entity.getPosition();
            var tool = position.second().getTool(KLevelEntityLayerTool.class);
            tool.removeEntity(position.first(), entity);
            deleted.add(entity);
        }

        KSystemLogger.debug(
            this.name,
            "Deleted %d entities", deleted.size()
        );

        if (this.messenger == null) {
            return;
        }

        KUniversalMap body = new KUniversalMap();
        body.put("instances", deleted);
        this.messenger.sendRegular(
            "entitiesDestroyed",
            body
        );

    }

    private void moveEntities() {

        this.autonomouses.values().forEach(KLevelEntity::move);
        this.controllables.values().forEach(KLevelEntity::move);

        if (this.messenger == null) {
            return;
        }

        KUniversalMap body = new KUniversalMap();
        body.put("moved_controllables", this.controllables.keySet());
        Map<UUID, KLevelSectorSlice> controllableDestinations = new HashMap<>();
        for (var entry: this.controllables.entrySet()) {
            KLevelEntity entity = entry.getValue();
            KLevelSector sector = entity.getPosition().second();
            controllableDestinations.put(
                entry.getKey(),
                sector.getSlice(entity.getPosition().first())
            );
        }
        body.put("controllables_destinations", controllableDestinations);

        body.put("moved_autonomous", this.autonomouses.keySet());
        Map<UUID, KLevelSectorSlice> autonomousesDestinations = new HashMap<>();
        for (var entry: this.autonomouses.entrySet()) {
            KLevelEntity entity = entry.getValue();
            KLevelSector sector = entity.getPosition().second();
            autonomousesDestinations.put(
                entry.getKey(),
                sector.getSlice(entity.getPosition().first())
            );
        }
        body.put("autonomouses_destinations", autonomousesDestinations);

        this.messenger.sendRegular(
            "entitiesMoved",
            body
        );

    }

    private void scheduleCleaning() {
        this.frameTaskScheduler.scheduleTemporalTask(
            CLEAN_DESTROYED_ENTITIES_TASK_ID,
            KFrameEvent.FRAME_FINISHED,
            PRIORITY,
            this::cleanDestroyedEntities
        );
    }


}
