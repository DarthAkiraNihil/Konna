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
import io.github.darthakiranihil.konna.core.io.KAsset;
import io.github.darthakiranihil.konna.core.io.KAssetCollection;
import io.github.darthakiranihil.konna.core.io.KAssetDefinition;
import io.github.darthakiranihil.konna.core.io.KAssetLoader;
import io.github.darthakiranihil.konna.core.io.except.KAssetLoadingException;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.KLevelMetadata;
import io.github.darthakiranihil.konna.level.KLevelSectorMetadata;
import io.github.darthakiranihil.konna.level.entity.KAutonomousEntityController;
import io.github.darthakiranihil.konna.level.layer.KTransitionedLevelType;
import io.github.darthakiranihil.konna.level.type.KLevelMetadataTypedef;

import java.util.*;

/**
 * Collection of level assets of type
 * {@link KLevelMetadataTypedef#LEVEL_METADATA_ASSET_TYPE}.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
@KSingleton
public final class KLevelMetadataCollection
    extends KObject
    implements KAssetCollection<KLevelMetadata> {

    private final KAssetLoader assetLoader;

    /**
     * Standard constructor.
     * @param assetLoader Asset loader
     */
    public KLevelMetadataCollection(
        @KInject final KAssetLoader assetLoader
    ) {
        super(
            "Level.levelCollection",
            KStructUtils.setOfTags(KTag.DefaultTags.ASSET_COLLECTION)
        );

        this.assetLoader = assetLoader;

    }

    @Override
    public KLevelMetadata getAsset(final String assetId) {

        KAsset asset = this.assetLoader.loadAsset(assetId, KLevelMetadataTypedef.LEVEL_METADATA_ASSET_TYPE);
        KAssetDefinition definition = asset.definition();

        KAssetDefinition rawSectorsDefinitions = definition.getSubdefinition("sectors");
        Map<String, KLevelSectorMetadata> sectorMetadata = this.readSectorMetadata(rawSectorsDefinitions);
        return new KLevelMetadata(
            assetId,
            sectorMetadata
        );
    }

    private Map<String, KLevelSectorMetadata> readSectorMetadata(
        final KAssetDefinition rawSectorDefinitions
    ) {

        Map<String, KLevelSectorMetadata> metadata = new HashMap<>();

        List<String> sectors = rawSectorDefinitions.getProperties();
        for (String sectorName: sectors) {
            KAssetDefinition rawSector = rawSectorDefinitions.getSubdefinition(sectorName);

            KAssetDefinition rawSize = rawSector.getSubdefinition("size");
            KSize size = new KSize(
                rawSize.getInt("width"),
                rawSize.getInt("height")
            );

            metadata.put(
                sectorName,
                new KLevelSectorMetadata(
                    sectorName,
                    size,
                    this.readTileAssetIds(size, rawSector),
                    this.readHeights(size, rawSector),
                    this.readEntities(size, rawSector),
                    this.readSectorLinks(
                        size, rawSector, sectors
                    ),
                    this.readLevelTransitions(size, rawSector)
                )
            );
        }

        return metadata;

    }

    private String[][] readTileAssetIds(
        final KSize size,
        final KAssetDefinition rawSector
    ) {

        String[] rawTiles = Objects.requireNonNull(rawSector.getStringArray("tiles"));
        String[][] tileAssetIds = new String[size.height()][size.height()];

        for (int i = 0; i < rawTiles.length; i++) {

            int x = i % size.width();
            int y = i / size.width();
            tileAssetIds[y][x] = rawTiles[i];

        }

        return tileAssetIds;
    }

    private int[][] readHeights(
        final KSize size,
        final KAssetDefinition rawSector
    ) {

        int[] rawHeights = rawSector.getIntArray("heights");
        int[][] heights = new int[size.height()][size.width()];

        for (int i = 0; i < rawHeights.length; i++) {

            int x = i % size.width();
            int y = i / size.width();
            heights[y][x] = rawHeights[i];

        }

        return heights;
    }

    private KLevelSectorMetadata.EntityLayerMetadata readEntities(
        final KSize size,
        final KAssetDefinition rawSector
    ) {
        KAssetDefinition[] rawData = rawSector.getSubdefinitionArray("entities");

        Map<KVector2i, List<KLevelSectorMetadata.SimpleLevelEntityMetadata>>
            statics = new HashMap<>();
        Map<KVector2i, List<KLevelSectorMetadata.SimpleLevelEntityMetadata>>
            controllables = new HashMap<>();
        Map<KVector2i, List<KLevelSectorMetadata.AutonomousLevelEntityMetadata>>
            autonomouses = new HashMap<>();

        for (var data: rawData) {

            int x = data.getSubdefinition("position").getInt("x");
            int y = data.getSubdefinition("position").getInt("y");

            if (x > size.width() || x < 0 || y > size.height() || y < 0) {
                throw new KAssetLoadingException("Entities are out of bounds of sector space");
            }

            KAssetDefinition[] rawStatics = data.getSubdefinitionArray("static");
            KAssetDefinition[] rawControllables = data.getSubdefinitionArray("controllable");
            KAssetDefinition[] rawAutonomouses = data.getSubdefinitionArray("autonomous");

            KVector2i position = new KVector2i(x, y);
            statics.putIfAbsent(position, new ArrayList<>(rawStatics.length));
            controllables.putIfAbsent(position, new ArrayList<>(rawControllables.length));
            autonomouses.putIfAbsent(position, new ArrayList<>(rawAutonomouses.length));

            List<KLevelSectorMetadata.SimpleLevelEntityMetadata>
                staticsOnPosition = statics.get(position);
            for (var rawStatic : rawStatics) {
                staticsOnPosition.add(new KLevelSectorMetadata.SimpleLevelEntityMetadata(
                    Objects.requireNonNull(rawStatic.getString("name")),
                    Objects.requireNonNull(rawStatic.getString("descriptor"))
                ));
            }

            List<KLevelSectorMetadata.SimpleLevelEntityMetadata>
                controllablesOnPosition = controllables.get(position);
            for (var rawControllable : rawControllables) {
                controllablesOnPosition.add(new KLevelSectorMetadata.SimpleLevelEntityMetadata(
                    Objects.requireNonNull(rawControllable.getString("name")),
                    Objects.requireNonNull(rawControllable.getString("descriptor"))
                ));
            }

            List<KLevelSectorMetadata.AutonomousLevelEntityMetadata>
                autonomousOnPosition = autonomouses.get(position);
            for (var rawAutonomous : rawAutonomouses) {
                autonomousOnPosition.add(new KLevelSectorMetadata.AutonomousLevelEntityMetadata(
                    Objects.requireNonNull(rawAutonomous.getString("name")),
                    Objects.requireNonNull(rawAutonomous.getString("descriptor")),
                    rawAutonomous.getClassObject(
                        "controller", KAutonomousEntityController.class
                    ),
                    rawAutonomous.getSubdefinition("params")
                ));
            }

        }

        return new KLevelSectorMetadata.EntityLayerMetadata(
            statics,
            controllables,
            autonomouses
        );
    }

    private KLevelSectorMetadata.SectorLinkMetadata[] readSectorLinks(
        final KSize size,
        final KAssetDefinition rawSector,
        final List<String> availableSectors
    ) {

        KAssetDefinition[] rawLinks = rawSector.getSubdefinitionArray("sector_links");
        var sectorLinkMetadata = new KLevelSectorMetadata.SectorLinkMetadata[rawLinks.length];

        int link = 0;
        for (var rawLink: rawLinks) {

            int x = rawLink.getSubdefinition("position").getInt("x");
            int y = rawLink.getSubdefinition("position").getInt("y");

            if (x > size.width() || x < 0 || y > size.height() || y < 0) {
                throw new KAssetLoadingException(
                    "Sector link is out of bounds of sector space"
                );
            }

            String linkedSector = Objects.requireNonNull(rawLink.getString("sector"));
            if (!availableSectors.contains(linkedSector)) {
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
                destinationX > size.width()
                    ||  destinationX < 0
                    ||  destinationY > size.height()
                    ||  destinationY < 0
            ) {
                throw new KAssetLoadingException(
                    "Sector link destination is out of bounds of linked sector space"
                );
            }

            sectorLinkMetadata[link] = new KLevelSectorMetadata.SectorLinkMetadata(
                new KVector2i(x, y),
                linkedSector,
                new KVector2i(destinationX, destinationY)
            );
            link++;

        }

        return sectorLinkMetadata;
    }

    private KLevelSectorMetadata.LevelTransitionMetadata[] readLevelTransitions(
        final KSize size,
        final KAssetDefinition rawSector
    ) {
        KAssetDefinition[] rawTransitions = rawSector.getSubdefinitionArray("level_transitions");
        var levelTransitionMetadata =
            new KLevelSectorMetadata.LevelTransitionMetadata[rawTransitions.length];

        int transition = 0;
        for (var rawTransition: rawTransitions) {

            int x = rawTransition.getSubdefinition("position").getInt("x");
            int y = rawTransition.getSubdefinition("position").getInt("y");

            if (x > size.width() || x < 0 || y > size.height() || y < 0) {
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

            levelTransitionMetadata[transition] = new KLevelSectorMetadata.LevelTransitionMetadata(
                new KVector2i(x, y),
                levelDescriptor,
                levelType,
                destinationSector,
                new KVector2i(destinationX, destinationY)
            );
            transition++;
        }

        return levelTransitionMetadata;
    }

}
