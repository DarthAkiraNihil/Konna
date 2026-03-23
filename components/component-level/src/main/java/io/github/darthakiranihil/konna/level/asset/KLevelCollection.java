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

package io.github.darthakiranihil.konna.level.asset;

import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.except.KClassNotFoundException;
import io.github.darthakiranihil.konna.core.io.*;
import io.github.darthakiranihil.konna.core.io.except.KAssetDefinitionError;
import io.github.darthakiranihil.konna.core.io.except.KAssetLoadingException;
import io.github.darthakiranihil.konna.core.log.system.KSystemLogger;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.core.struct.KTriplet;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.core.util.KClassUtils;
import io.github.darthakiranihil.konna.level.KLevel;
import io.github.darthakiranihil.konna.level.KLevelSector;
import io.github.darthakiranihil.konna.level.entity.KAutonomousEntity;
import io.github.darthakiranihil.konna.level.entity.KAutonomousEntityController;
import io.github.darthakiranihil.konna.level.entity.KControllableEntity;
import io.github.darthakiranihil.konna.level.entity.KStaticEntity;
import io.github.darthakiranihil.konna.level.layer.*;
import io.github.darthakiranihil.konna.level.layer.tool.*;
import io.github.darthakiranihil.konna.level.type.KLevelTypedef;

import java.util.*;

/**
 * Collection of level assets of type
 * {@link KLevelTypedef#LEVEL_ASSET_TYPE}.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
@KSingleton
public final class KLevelCollection extends KObject implements KAssetCollection<KLevel> {

    private record RawSector(
        KLevelSector containedSector,
        KSize size,

        KTileLayer tileLayer,
        KHeightLayer heightLayer,
        KSectorLinkLayer sectorLinkLayer,
        KLevelEntityLayer entityLayer,
        KLevelTransitionLayer levelTransitionLayer,

        String[] tiles,
        KAssetDefinition[] sectorLinks,
        KAssetDefinition[] entities,
        int[] heights,
        KAssetDefinition[] levelTransitions
    ) {

    }

    private final KAssetLoader assetLoader;
    private final KEventSystem eventSystem;
    private final KTileCollection tileCollection;
    private final KActivator activator;

    /**
     * Standard constructor.
     * @param assetLoader Asset loader
     * @param eventSystem Event system that will be injected in loaded entities and sectors
     * @param tileCollection Tile collection to get tiles for loaded level
     * @param activator Activator to create autonomous entity controllers
     */
    public KLevelCollection(
        @KInject final KAssetLoader assetLoader,
        @KInject final KEventSystem eventSystem,
        @KInject final KTileCollection tileCollection,
        @KInject final KActivator activator
    ) {
        super(
            "Level.levelCollection",
            KStructUtils.setOfTags(KTag.DefaultTags.ASSET_COLLECTION)
        );

        this.assetLoader = assetLoader;
        this.eventSystem = eventSystem;
        this.tileCollection = tileCollection;
        this.activator = activator;

    }

    @Override
    public KLevel getAsset(final String assetId) {

        KAsset asset = this.assetLoader.loadAsset(assetId, KLevelTypedef.LEVEL_ASSET_TYPE);
        KAssetDefinition definition = asset.definition();

        KAssetDefinition rawSectorsDefinitions = definition.getSubdefinition("sectors");
        Map<String, RawSector> rawSectors = this.createRawSectors(rawSectorsDefinitions);
        List<KLevelSector> filledSectors = new LinkedList<>();
        List<KTriplet<
            KAutonomousEntity,
            Class<? extends KAutonomousEntityController>,
            KAssetDefinition
        >> autonomousEntities = new LinkedList<>();

        for (var rawSector: rawSectors.entrySet()) {
            RawSector rs = rawSector.getValue();

            this.fillTileLayer(rs);
            this.fillSectorLinkLayer(rs, rawSectors);
            this.fillEntityLayer(rs, autonomousEntities);
            this.fillHeightLayer(rs);
            this.fillLevelTransitionLayer(rs);

            rs.containedSector.refresh();

            filledSectors.add(
                rs.containedSector()
            );
        }

        KLevel level = new KLevel(
            assetId,
            filledSectors
        );
        this.createControllers(autonomousEntities, level);
        return level;
    }

    private Map<String, RawSector> createRawSectors(
        final KAssetDefinition rawSectorsDefinitions
    ) {
        Map<String, RawSector> rawSectors = new HashMap<>();

        for (String sectorName: rawSectorsDefinitions.getProperties()) {
            KAssetDefinition rawSector = rawSectorsDefinitions.getSubdefinition(sectorName);
            KAssetDefinition rawSize = rawSector.getSubdefinition("size");
            KSize size = new KSize(
                rawSize.getInt("width"),
                rawSize.getInt("height")
            );

            KTileLayer tileLayer = new KTileLayer(size);
            KHeightLayer heightLayer = new KHeightLayer(size);
            KSectorLinkLayer sectorLinkLayer = new KSectorLinkLayer();
            KLevelEntityLayer entityLayer = new KLevelEntityLayer();
            KLevelTransitionLayer levelTransitionLayer = new KLevelTransitionLayer();

            KLevelSector createdSector = new KLevelSector(
                this.eventSystem,
                sectorName,
                tileLayer,
                heightLayer,
                sectorLinkLayer,
                entityLayer,
                levelTransitionLayer
            );

            rawSectors.put(
                sectorName,
                new RawSector(
                    createdSector,
                    size,
                    tileLayer,
                    heightLayer,
                    sectorLinkLayer,
                    entityLayer,
                    levelTransitionLayer,
                    Objects.requireNonNull(rawSector.getStringArray("tiles")),
                    rawSector.getSubdefinitionArray("sector_links"),
                    rawSector.getSubdefinitionArray("entities"),
                    rawSector.getIntArray("heights"),
                    rawSector.getSubdefinitionArray("level_transitions")
                )
            );
        }

        return rawSectors;
    }

    private void fillTileLayer(
        final RawSector rawSector
    ) {

        String[] rawTiles = rawSector.tiles;
        KTileLayer layer = rawSector.tileLayer;
        KTileLayerTool tool = layer.getTool();
        KSize size = rawSector.size;

        for (int i = 0; i < rawTiles.length; i++) {

            int x = i % size.width();
            int y = i / size.width();

            tool.placeTile(
                x, y, this.tileCollection.getAsset(rawTiles[i])
            );

        }

    }

    private void fillSectorLinkLayer(
        final RawSector rawSector,
        final Map<String, RawSector> rawSectorsMap
    ) {

        KSectorLinkLayer layer = rawSector.sectorLinkLayer;
        KSectorLinkLayerTool tool = layer.getTool();
        KAssetDefinition[] rawLinks = rawSector.sectorLinks;

        for (var rawLink: rawLinks) {

            int x = rawLink.getSubdefinition("position").getInt("x");
            int y = rawLink.getSubdefinition("position").getInt("y");

            if (x > rawSector.size.width() || x < 0 || y > rawSector.size.height() || y < 0) {
                throw new KAssetLoadingException(
                    "Sector link is out of bounds of sector space"
                );
            }

            String linkedSector = Objects.requireNonNull(rawLink.getString("sector"));
            if (!rawSectorsMap.containsKey(linkedSector)) {
                throw new KAssetLoadingException(
                    String.format(
                        "Attempting to link the sector to another named %s, which does not exist",
                        linkedSector
                    )
                );
            }

            int destinationX = rawLink.getSubdefinition("destination").getInt("x");
            int destinationY = rawLink.getSubdefinition("destination").getInt("y");
            if (
                    destinationX > rawSector.size.width()
                ||  destinationX < 0
                ||  destinationY > rawSector.size.height()
                ||  destinationY < 0
            ) {
                throw new KAssetLoadingException(
                    "Sector link destination is out of bounds of linked sector space"
                );
            }

            tool.link(
                x,
                y,
                rawSectorsMap.get(linkedSector).containedSector,
                destinationX,
                destinationY
            );

        }

    }

    private void fillEntityLayer(
        final RawSector rawSector,
        final List<KTriplet<
            KAutonomousEntity,
            Class<? extends KAutonomousEntityController>,
            KAssetDefinition
        >> autonomousEntitiesList
    ) {

        KLevelEntityLayer layer = rawSector.entityLayer;
        KAssetDefinition[] rawData = rawSector.entities;

        for (var data: rawData) {

            int x = data.getSubdefinition("position").getInt("x");
            int y = data.getSubdefinition("position").getInt("y");

            if (x > rawSector.size.width() || x < 0 || y > rawSector.size.height() || y < 0) {
                throw new KAssetLoadingException(
                    "Entities are out of bounds of sector space"
                );
            }

            KAssetDefinition[] controllables = data.getSubdefinitionArray("controllable");
            KAssetDefinition[] staticEntities = data.getSubdefinitionArray("static");
            KAssetDefinition[] autonomousEntities = data.getSubdefinitionArray("autonomous");

            var tool = layer.getTool();
            this.placeSimpleEntities(tool, controllables, x, y, rawSector.containedSector, false);
            this.placeSimpleEntities(tool, staticEntities, x, y, rawSector.containedSector, true);

            for (var autonomous: autonomousEntities) {

                var auto = new KAutonomousEntity(
                    this.eventSystem,
                    Objects.requireNonNull(autonomous.getString("name")),
                    Objects.requireNonNull(autonomous.getString("descriptor")),
                    new KVector2i(x, y),
                    rawSector.containedSector
                );

                tool.placeEntity(x, y, auto);
                autonomousEntitiesList.add(
                    new KTriplet<>(
                        auto,
                        autonomous.getClassObject(
                            "controller",
                            KAutonomousEntityController.class
                        ),
                        autonomous.getSubdefinition("params")
                    )
                );
            }
        }

    }

    private void placeSimpleEntities(
        final KLevelEntityLayerTool tool,
        final KAssetDefinition[] entities,
        int x,
        int y,
        final KLevelSector containedSector,
        boolean areStatic
    ) {
        for (var entity: entities) {
            tool.placeEntity(
                x,
                y,
                areStatic
                    ? new KStaticEntity(
                        this.eventSystem,
                        Objects.requireNonNull(entity.getString("name")),
                        Objects.requireNonNull(entity.getString("descriptor")),
                        new KVector2i(x, y),
                        containedSector
                    )
                    : new KControllableEntity(
                        this.eventSystem,
                        Objects.requireNonNull(entity.getString("name")),
                        Objects.requireNonNull(entity.getString("descriptor")),
                        new KVector2i(x, y),
                        containedSector
                    )
            );
        }
    }

    private void fillHeightLayer(
        final RawSector rawSector
    ) {

        int[] heights = rawSector.heights;
        KHeightLayer layer = rawSector.heightLayer;
        KHeightLayerTool tool = layer.getTool();
        KSize size = rawSector.size;

        for (int i = 0; i < heights.length; i++) {

            int x = i % size.width();
            int y = i / size.width();

            tool.setHeight(
                x, y, heights[i]
            );

        }

    }

    private void fillLevelTransitionLayer(
        final RawSector rawSector
    ) {

        KLevelTransitionLayer layer = rawSector.levelTransitionLayer;
        KLevelTransitionLayerTool tool = layer.getTool();
        KAssetDefinition[] rawTransitions = rawSector.levelTransitions;

        for (var rawTransition: rawTransitions) {

            int x = rawTransition.getSubdefinition("position").getInt("x");
            int y = rawTransition.getSubdefinition("position").getInt("y");

            if (x > rawSector.size.width() || x < 0 || y > rawSector.size.height() || y < 0) {
                throw new KAssetLoadingException(
                    "Level transition is out of bounds of sector space"
                );
            }

            String levelDescriptor = Objects.requireNonNull(
                rawTransition.getString("level_descriptor")
            );
            KTransitionedLevelType levelType = rawTransition.getEnum(
                "level_type",
                KTransitionedLevelType.class
            );
            String destinationSector = Objects.requireNonNull(
                rawTransition.getString("destination_sector")
            );

            int destinationX = rawTransition.getSubdefinition("destination_position").getInt("x");
            int destinationY = rawTransition.getSubdefinition("destination_position").getInt("y");
            if (destinationX < 0 ||  destinationY < 0) {
                throw new KAssetLoadingException(
                    "Level transition destination is not valid!"
                );
            }

            tool.makeTransition(
                x,
                y,
                levelDescriptor,
                levelType,
                destinationSector,
                destinationX,
                destinationY
            );
        }

    }

    @SuppressWarnings("unchecked")
    private void createControllers(
        final List<KTriplet<
            KAutonomousEntity,
            Class<? extends KAutonomousEntityController>,
            KAssetDefinition
        >> autonomousEntitiesList,
        final KLevel level
    ) {
        for (var data: autonomousEntitiesList) {
            KAutonomousEntity entity = data.first();
            Class<? extends KAutonomousEntityController> controllerClass = data.second();
            KAssetDefinition controllerParams = data.third();

            try {
                var paramsValidator = KClassUtils
                        .getForName(
                            String.format(
                                "konna.generated.level.entity.%s$$ParamValidator",
                                controllerClass.getSimpleName()
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
                        throw new KAssetLoadingException(e);
                    }
                }
            } catch (KClassNotFoundException e) {
                KSystemLogger.warning(
                    this.name,
                    "Could not find param validator for %s. Skipping validation",
                    controllerClass.getCanonicalName()
                );
            }

            KAutonomousEntityController controller = this
                .activator
                .createObject(
                    controllerClass,
                    entity,
                    level,
                    controllerParams
                );

            entity.setController(controller);
        }
    }
}
