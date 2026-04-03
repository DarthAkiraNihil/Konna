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

import io.github.darthakiranihil.konna.core.io.KAssetDefinition;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.entity.KAutonomousEntityController;
import io.github.darthakiranihil.konna.level.layer.KTransitionedLevelType;

import java.util.List;
import java.util.Map;

/**
 * Record containing level sector metadata.
 *
 * @param name Sector name
 * @param size Sector size
 * @param tileAssetIds Asset ids of sector tiles
 * @param heights Heights of this sector
 * @param entities Entities of this sector
 * @param sectorLinkMetadata Sector links of this sector
 * @param levelTransitionMetadata Level transitions of this sector
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public record KLevelSectorMetadata(
    String name,
    KSize size,
    String[][] tileAssetIds,
    int[][] heights,
    EntityLayerMetadata entities,
    SectorLinkMetadata[] sectorLinkMetadata,
    LevelTransitionMetadata[] levelTransitionMetadata

) {

    /**
     * Container for sector link metadata.
     * @param position Position of this link
     * @param destinationSectorName Sector name that is connected with this link
     * @param destinationSectorPosition Position on destination sector that this link points to
     */
    public record SectorLinkMetadata(
        KVector2i position,
        String destinationSectorName,
        KVector2i destinationSectorPosition
    ) {

    }

    /**
     * Container for level transition metadata.
     * @param position Position of this transition.
     * @param levelDescriptor Level descriptor that this transition is connected to
     * @param levelType Type of connected level
     * @param destinationSector Sector on connected level that is considered transition destination
     * @param destinationPosition Position on destination sector
     */
    public record LevelTransitionMetadata(
        KVector2i position,
        String levelDescriptor,
        KTransitionedLevelType levelType,
        String destinationSector,
        KVector2i destinationPosition
    ) {

    }

    /**
     * Container for level entity metadata.
     * @param staticEntities Static entities assigned to entity layer
     * @param controllableEntities Controllable entities assigned to entity layer
     * @param autonomousEntities Autonomous entities assigned to entity layer
     */
    public record EntityLayerMetadata(
        Map<KVector2i, List<SimpleLevelEntityMetadata>> staticEntities,
        Map<KVector2i, List<SimpleLevelEntityMetadata>> controllableEntities,
        Map<KVector2i, List<AutonomousLevelEntityMetadata>> autonomousEntities
    ) {

    }

    /**
     * Container for static and controllable entities' metadata.
     * @param name Entity's name
     * @param descriptor Entity's descriptor
     */
    public record SimpleLevelEntityMetadata(
        String name,
        String descriptor
    ) {

    }

    /**
     * Container for autonomous entities' metadata.
     * @param name Entity's name
     * @param descriptor Entity's descriptor
     * @param controller Controller class
     * @param params Controller params
     */
    public record AutonomousLevelEntityMetadata(
        String name,
        String descriptor,
        Class<? extends KAutonomousEntityController> controller,
        KAssetDefinition params
    ) {

    }

}
