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

package io.github.darthakiranihil.konna.level;

import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.except.KClassNotFoundException;
import io.github.darthakiranihil.konna.core.io.KAssetDefinition;
import io.github.darthakiranihil.konna.core.io.KAssetDefinitionRule;
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
import io.github.darthakiranihil.konna.level.asset.KTileCollection;
import io.github.darthakiranihil.konna.level.entity.KAutonomousEntity;
import io.github.darthakiranihil.konna.level.entity.KAutonomousEntityController;
import io.github.darthakiranihil.konna.level.entity.KControllableEntity;
import io.github.darthakiranihil.konna.level.entity.KStaticEntity;
import io.github.darthakiranihil.konna.level.layer.*;
import io.github.darthakiranihil.konna.level.layer.tool.*;

import java.util.*;

@KSingleton
public class KStandardLevelLoader extends KObject implements KLevelLoader {

    private record RawSector(
        KLevelSectorMetadata metadata,
        KLevelSector sectorInstance,

        KTileLayer tileLayer,
        KHeightLayer heightLayer,
        KSectorLinkLayer sectorLinkLayer,
        KLevelEntityLayer entityLayer,
        KLevelTransitionLayer levelTransitionLayer
    ) {

    }

    private final KEventSystem eventSystem;
    private final KActivator activator;
    private final KTileCollection tileCollection;

    public KStandardLevelLoader(
        @KInject final KEventSystem eventSystem,
        @KInject final KActivator activator,
        @KInject final KTileCollection tileCollection
    ) {
        super(
            "KStandardLevelLoader", KStructUtils.setOfTags(KTag.DefaultTags.STD)
        );

        this.eventSystem = eventSystem;
        this.activator = activator;
        this.tileCollection = tileCollection;
    }

    @Override
    public KLevel load(final KLevelMetadata metadata) {

        Map<String, KLevelSectorMetadata> sectorMetadata = metadata.sectorMetadata();
        Map<String, RawSector> rawSectors = this.createRawSectors(sectorMetadata);
        List<KLevelSector> finalizedSectors = new ArrayList<>(rawSectors.size());
        List<KTriplet<
            KAutonomousEntity,
            Class<? extends KAutonomousEntityController>,
            KAssetDefinition
        >> autonomousEntities = new LinkedList<>();

        for (var rawSectorEntry: rawSectors.entrySet()) {

            RawSector rs = rawSectorEntry.getValue();

            this.fillTileLayer(rs);
            this.fillSectorLinkLayer(rs, rawSectors);
            this.fillEntityLayer(rs, autonomousEntities);
            this.fillHeightLayer(rs);
            this.fillLevelTransitionLayer(rs);

            this.finalizeSector(rs.sectorInstance);
            finalizedSectors.add(rs.sectorInstance);

        }

        KLevel level = new KLevel(
            metadata.name(),
            finalizedSectors
        );
        this.createControllers(autonomousEntities, level);
        return level;
    }

    private Map<String, RawSector> createRawSectors(
        final Map<String, KLevelSectorMetadata> metadata
    ) {
        Map<String, RawSector> rawSectors = new HashMap<>();

        for (KLevelSectorMetadata sector: metadata.values()) {

            KSize size = sector.size();

            KTileLayer tileLayer = new KTileLayer(size);
            KHeightLayer heightLayer = new KHeightLayer(size);
            KSectorLinkLayer sectorLinkLayer = new KSectorLinkLayer();
            KLevelEntityLayer entityLayer = new KLevelEntityLayer();
            KLevelTransitionLayer levelTransitionLayer = new KLevelTransitionLayer();

            KLevelSector sectorInstance = new KLevelSector(
                this.eventSystem,
                sector.name(),
                tileLayer,
                heightLayer,
                sectorLinkLayer,
                entityLayer,
                levelTransitionLayer
            );

            rawSectors.put(
                sector.name(),
                new RawSector(
                    sector,
                    sectorInstance,
                    tileLayer,
                    heightLayer,
                    sectorLinkLayer,
                    entityLayer,
                    levelTransitionLayer
                )
            );
        }

        return rawSectors;
    }

    private void fillTileLayer(final RawSector rawSector) {

        KSize size = rawSector.sectorInstance.getSize();
        String[][] rawTiles = rawSector.metadata.tileAssetIds();

        KTileLayer layer = rawSector.tileLayer;
        KTileLayerTool tool = layer.getTool();

        for (int x = 0; x < size.width(); x++) {
            for (int y = 0; y < size.height(); y++) {

                tool.placeTile(
                    x,
                    y,
                    this.tileCollection.getAsset(rawTiles[y][x])
                );

            }
        }

    }

    private void fillSectorLinkLayer(
        final RawSector rawSector,
        final Map<String, RawSector> allRawSectors
    ) {

        var rawLinks = rawSector.metadata.sectorLinkMetadata();

        KSectorLinkLayer layer = rawSector.sectorLinkLayer;
        KSectorLinkLayerTool tool = layer.getTool();

        for (var rawLink: rawLinks) {

            tool.link(
                rawLink.position(),
                allRawSectors.get(rawLink.destinationSectorName()).sectorInstance,
                rawLink.destinationSectorPosition()
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

        KLevelSectorMetadata.EntityLayerMetadata rawEntityData = rawSector.metadata.entities();
        KLevelEntityLayer layer = rawSector.entityLayer;
        KLevelEntityLayerTool tool = layer.getTool();

        var statics = rawEntityData.staticEntities();
        for (var staticEntitiesOnPosition: statics.entrySet()) {

            KVector2i position = staticEntitiesOnPosition.getKey();
            var staticEntities = staticEntitiesOnPosition.getValue();

            for (KLevelSectorMetadata.SimpleLevelEntityMetadata staticEntity: staticEntities) {
                tool.placeEntity(
                    position,
                    new KStaticEntity(
                        this.eventSystem,
                        staticEntity.name(),
                        staticEntity.descriptor()
                    )
                );
            }
        }

        var controllables = rawEntityData.controllableEntities();
        for (var controllableEntitiesOnPosition: controllables.entrySet()) {

            KVector2i position = controllableEntitiesOnPosition.getKey();
            var controllableEntities = controllableEntitiesOnPosition.getValue();

            for (var controllableEntity: controllableEntities) {
                tool.placeEntity(
                    position,
                    new KControllableEntity(
                        this.eventSystem,
                        controllableEntity.name(),
                        controllableEntity.descriptor()
                    )
                );
            }
        }

        var autonomous = rawEntityData.autonomousEntities();
        for (var autonomousEntitiesOnPosition: autonomous.entrySet()) {
            KVector2i position = autonomousEntitiesOnPosition.getKey();
            var autonomousEntities = autonomousEntitiesOnPosition.getValue();

            for (var autonomousEntity: autonomousEntities) {

                KAutonomousEntity auto = new KAutonomousEntity(
                    this.eventSystem,
                    autonomousEntity.name(),
                    autonomousEntity.descriptor()
                );

                tool.placeEntity(position, auto);

                autonomousEntitiesList.add(
                    new KTriplet<>(auto, autonomousEntity.controller(), autonomousEntity.params())
                );
            }
        }

    }

    private void fillHeightLayer(
        final RawSector rawSector
    ) {

        KHeightLayer layer = rawSector.heightLayer;
        KHeightLayerTool tool = layer.getTool();

        int[][] heights = rawSector.metadata.heights();
        KSize size = rawSector.sectorInstance.getSize();

        for (int x = 0; x < size.width(); x++) {
            for (int y = 0; y < size.height(); y++) {
                tool.setHeight(x, y, heights[y][x]);
            }
        }

    }

    private void fillLevelTransitionLayer(
        final RawSector rawSector
    ) {

        KLevelTransitionLayer layer = rawSector.levelTransitionLayer;
        KLevelTransitionLayerTool tool = layer.getTool();

        var rawTransitions = rawSector.metadata.levelTransitionMetadata();

        for (KLevelSectorMetadata.LevelTransitionMetadata rawTransition: rawTransitions) {

            tool.makeTransition(
                rawTransition.position(),
                rawTransition.levelDescriptor(),
                rawTransition.levelType(),
                rawTransition.destinationSector(),
                rawTransition.destinationPosition()
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
                    controllerClass.getSimpleName(),
                    controllerParams
                );

            controller.setLevel(level);
            controller.setAssignedEntity(entity);
            entity.setController(controller);
        }
    }

    private void finalizeSector(final KLevelSector sector) {
        sector.refresh();
        KLevelEntityLayerTool tool = sector.getTool(KLevelEntityLayerTool.class);
        tool.setSectorForAll(sector);
    }

}
