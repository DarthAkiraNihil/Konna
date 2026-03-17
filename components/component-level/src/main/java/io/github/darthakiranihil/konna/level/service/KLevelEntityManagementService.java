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
import io.github.darthakiranihil.konna.level.map.KLocation;
import io.github.darthakiranihil.konna.level.map.KMapSector;
import io.github.darthakiranihil.konna.level.map.KMapSectorSlice;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

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
@KRequiresEvent(name = "locationLoaded", simple = false, type = KLocation.class)
@KRequiresEvent(name = "locationUnloaded")
@SuppressWarnings("FieldCanBeLocal,unused")
public class KLevelEntityManagementService extends KObject {

    private final KEventAction<KLocation> onLocationLoadedConsumer = this::onLocationLoaded;
    private final KSimpleEventAction onLocationUnloadedConsumer = this::onLocationUnloaded;

    private final Map<UUID, KControllableEntity> controllables;
    private final Map<UUID, KStaticEntity> statics;
    private final Map<UUID, KAutonomousEntity> autonomouses;

    private final KEventSystem eventSystem;
    private final KActivator activator;

    private @Nullable KMessenger messenger;
    private @Nullable KLocation currentLocation;

    public KLevelEntityManagementService(
        @KInject final KEventSystem eventSystem,
        @KInject final KActivator activator
    ) {
        super("Level.LevelEntityManagementService", KStructUtils.setOfTags(KTag.DefaultTags.SERVICE));

        KEvent<KLocation> locationLoaded = Objects.requireNonNull(eventSystem.getEvent("locationLoaded"));
        KSimpleEvent locationUnloaded = Objects.requireNonNull(eventSystem.getSimpleEvent("locationUnloaded"));

        locationUnloaded.subscribe(this.onLocationUnloadedConsumer);
        locationLoaded.subscribe(this.onLocationLoadedConsumer);

        this.controllables = new HashMap<>();
        this.statics = new HashMap<>();
        this.autonomouses = new HashMap<>();

        this.activator = activator;
        this.eventSystem = eventSystem;
    }

    public void setMessenger(final KMessenger messenger) {
        this.messenger = messenger;
    }

    @KServiceEndpoint(route = "moveAllEntities")
    protected void moveAllEntities() {
        this.autonomouses.values().forEach(KMapEntity::move);
        this.controllables.values().forEach(KMapEntity::move);

        if (this.messenger == null) {
            return;
        }

        KUniversalMap body = new KUniversalMap();
        body.put("moved", this.controllables.keySet());

        this.messenger.sendRegular(
            "controllableEntitiesMoved",
            body
        );
    }

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

        KMapSector deploymentSector = this.getDeploymentSector(sectorName, position, "an autonomous");
        if (deploymentSector == null) {
            return;
        }

        KAutonomousEntity entity = new KAutonomousEntity(
            this.eventSystem,
            entityName,
            descriptor,
            position,
            deploymentSector
        );

        try {
            var paramsValidator = KClassUtils
                .getForName(
                    String.format(
                        "konna.generated.level.entity.%s$$ParamValidator",
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
                entity,
                Objects.requireNonNull(this.currentLocation),
                controllerParams
            );

        entity.setController(controllerInstance);
        deploymentSector.placeEntity(position, entity);
        this.autonomouses.put(entity.id(), entity);

        this.sendMessage(entity, "autonomousEntityCreated");

    }

    @KServiceEndpoint(route = "destroyAutonomousEntity")
    protected void destroyAutonomousEntity(
        @KBodyValue("entity_id") final UUID entityId
    ) {
        if (this.currentLocation == null) {
            KSystemLogger.warning(
                this.name,
                "Cannot delete an autonomous entity: no location loaded"
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

        KAutonomousEntity entity = this.autonomouses.remove(entityId);
        var position = entity.getPosition();
        position.second().removeEntity(position.first(), entity);

        this.sendMessage(entity, "autonomousEntityDestroyed");
    }

    @KServiceEndpoint(route = "createStaticEntity")
    protected void createStaticEntity(
        @KBodyValue("name") final String entityName,
        @KBodyValue("descriptor") final String descriptor,
        @KBodyValue("sector_name") final String sectorName,
        @KBodyValue("position") final KVector2i position
    ) {

        KMapSector deploymentSector = this.getDeploymentSector(sectorName, position, "a static");
        if (deploymentSector == null) {
            return;
        }

        KStaticEntity entity = new KStaticEntity(
            this.eventSystem,
            entityName,
            descriptor,
            position,
            deploymentSector
        );

        deploymentSector.placeEntity(position, entity);
        this.statics.put(entity.id(), entity);

        this.sendMessage(entity, "staticEntityCreated");

    }

    @KServiceEndpoint(route = "destroyStaticEntity")
    protected void destroyStaticEntity(
        @KBodyValue("entity_id") final UUID entityId
    ) {
        if (this.currentLocation == null) {
            KSystemLogger.warning(
                this.name,
                "Cannot delete a static entity: no location loaded"
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

        KStaticEntity entity = this.statics.remove(entityId);
        var position = entity.getPosition();
        position.second().removeEntity(position.first(), entity);

        this.sendMessage(entity, "staticEntityDestroyed");

    }

    @KServiceEndpoint(route = "createControllableEntity")
    protected void createControllableEntity(
        @KBodyValue("name") final String entityName,
        @KBodyValue("descriptor") final String descriptor,
        @KBodyValue("sector_name") final String sectorName,
        @KBodyValue("position") final KVector2i position
    ) {

        KMapSector deploymentSector = this.getDeploymentSector(sectorName, position, "a controllable");
        if (deploymentSector == null) {
            return;
        }

        KControllableEntity entity = new KControllableEntity(
            this.eventSystem,
            entityName,
            descriptor,
            position,
            deploymentSector
        );

        deploymentSector.placeEntity(position, entity);
        this.controllables.put(entity.id(), entity);

        this.sendMessage(entity, "controllableEntityCreated");

    }

    @KServiceEndpoint(route = "destroyControllableEntity")
    protected void destroyControllableEntity(
        @KBodyValue("entity_id") final UUID entityId
    ) {
        if (this.currentLocation == null) {
            KSystemLogger.warning(
                this.name,
                "Cannot delete a controllable entity: no location loaded"
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

        KControllableEntity entity = this.controllables.remove(entityId);
        var position = entity.getPosition();
        position.second().removeEntity(position.first(), entity);

        this.sendMessage(entity, "controllableEntityDestroyed");
    }

    protected void onLocationLoaded(final KLocation location) {

        this.currentLocation = location;

        for (String sectorName: location.getSectorNames()) {
            KMapSector sector = location.getSector(sectorName);

            KSize sectorSize = sector.getSize();
            for (int i = 0; i < sectorSize.width(); i++) {
                for (int j = 0; j < sectorSize.height(); j++) {
                    KMapSectorSlice slice = sector.getSlice(i, j);
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

    protected void onLocationUnloaded() {
        this.controllables.clear();
        this.statics.clear();
        this.autonomouses.clear();
    }

    private @Nullable KMapSector getDeploymentSector(
        final String sectorName,
        final KVector2i position,
        final String entityType
    ) {
        if (this.currentLocation == null) {
            KSystemLogger.warning(
                this.name,
                "Cannot create %s entity: no location loaded",
                entityType
            );
            return null;
        }

        if (!this.currentLocation.hasSector(sectorName)) {
            KSystemLogger.warning(
                this.name,
                "Cannot create %s entity: no sector with name %s",
                entityType,
                sectorName
            );
        }

        KMapSector deploymentSector = this.currentLocation.getSector(sectorName);
        KMapSectorSlice slice = deploymentSector.getSlice(position.x(), position.y());
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

    private void sendMessage(final KMapEntity entity, final String messageId) {
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


}
