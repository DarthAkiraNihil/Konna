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
import io.github.darthakiranihil.konna.core.io.*;
import io.github.darthakiranihil.konna.core.io.except.KAssetLoadingException;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.entity.KControllableEntity;
import io.github.darthakiranihil.konna.level.map.*;
import io.github.darthakiranihil.konna.level.type.KLocationTypedef;

import java.util.*;

/**
 * Collection of location assets of type
 * {@link KLocationTypedef#LOCATION_ASSET_TYPE}.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
@KSingleton
public final class KLocationCollection extends KObject implements KAssetCollection<KLocation> {

    private record RawSector(
        KMapSector containedSector,
        KSize size,

        KTileLayer tileLayer,
        KSectorLinkLayer sectorLinkLayer,
        KMapEntityLayer entityLayer,

        String[] tiles,
        KAssetDefinition[] sectorLinks,
        KAssetDefinition[] entities
    ) {

    }

    private final KAssetLoader assetLoader;
    private final KEventSystem eventSystem;
    private final KTileCollection tileCollection;

    /**
     * Standard constructor.
     * @param assetLoader Asset loader
     * @param tileCollection Tile collection to get tiles for loaded location
     */
    public KLocationCollection(
        @KInject final KAssetLoader assetLoader,
        @KInject final KEventSystem eventSystem,
        @KInject final KTileCollection tileCollection
    ) {
        super(
            "Level.locationCollection",
            KStructUtils.setOfTags(KTag.DefaultTags.ASSET_COLLECTION)
        );

        this.assetLoader = assetLoader;
        this.eventSystem = eventSystem;
        this.tileCollection = tileCollection;

    }

    @Override
    public KLocation getAsset(final String assetId) {

        KAsset asset = this.assetLoader.loadAsset(assetId, KLocationTypedef.LOCATION_ASSET_TYPE);
        KAssetDefinition definition = asset.definition();

        KAssetDefinition[] rawSectorsDefinitions = definition.getSubdefinitionArray("sectors");
        Map<String, RawSector> rawSectors = this.createRawSectors(rawSectorsDefinitions);
        List<KMapSector> filledSectors = new LinkedList<>();

        for (var rawSector: rawSectors.entrySet()) {
            RawSector rs = rawSector.getValue();

            this.fillTileLayer(rs);
            this.fillSectorLinkLayer(rs, rawSectors);
            this.fillEntityLayer(rs);

            filledSectors.add(
                rs.containedSector()
            );
        }

        return new KLocation(
            assetId,
            filledSectors
        );
    }

    private Map<String, RawSector> createRawSectors(
        final KAssetDefinition[] rawSectorsDefinitions
    ) {
        Map<String, RawSector> rawSectors = new HashMap<>();

        for (KAssetDefinition rawSector: rawSectorsDefinitions) {
            String sectorName = Objects.requireNonNull(
                rawSector.getString("name")
            );
            KAssetDefinition rawSize = rawSector.getSubdefinition("size");
            KSize size = new KSize(
                rawSize.getInt("width"),
                rawSize.getInt("height")
            );

            KTileLayer tileLayer = new KTileLayer(size);
            KSectorLinkLayer sectorLinkLayer = new KSectorLinkLayer();
            KMapEntityLayer entityLayer = new KMapEntityLayer();

            KMapSector createdSector = new KMapSector(
                this.eventSystem,
                sectorName,
                tileLayer,
                sectorLinkLayer,
                entityLayer
            );

            rawSectors.put(
                sectorName,
                new RawSector(
                    createdSector,
                    size,
                    tileLayer,
                    sectorLinkLayer,
                    entityLayer,
                    Objects.requireNonNull(rawSector.getStringArray("tiles")),
                    rawSector.getSubdefinitionArray("sector_links"),
                    rawSector.getSubdefinitionArray("entities")
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
        KSize size = rawSector.size;

        for (int i = 0; i < rawTiles.length; i++) {

            int x = i % size.width();
            int y = i / size.width();

            layer.placeTile(
                x, y, this.tileCollection.getAsset(rawTiles[i])
            );

        }

    }

    private void fillSectorLinkLayer(
        final RawSector rawSector,
        final Map<String, RawSector> rawSectorsMap
    ) {

        KSectorLinkLayer layer = rawSector.sectorLinkLayer;
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

            layer.link(
                x,
                y,
                rawSectorsMap.get(linkedSector).containedSector,
                destinationX,
                destinationY
            );

        }

    }

    private void fillEntityLayer(
        final RawSector rawSector
    ) {

        KMapEntityLayer layer = rawSector.entityLayer;
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
            for (var controllable: controllables) {
                layer.placeEntity(
                    x,
                    y,
                    new KControllableEntity(
                        this.eventSystem,
                        Objects.requireNonNull(controllable.getString("name")),
                        Objects.requireNonNull(controllable.getString("descriptor")),
                        new KVector2i(x, y),
                        rawSector.containedSector
                    )
                );
            }

        }

    }
}
