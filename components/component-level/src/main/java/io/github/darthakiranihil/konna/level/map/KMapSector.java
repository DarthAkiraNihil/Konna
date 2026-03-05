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

package io.github.darthakiranihil.konna.level.map;

import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.message.KEvent;
import io.github.darthakiranihil.konna.core.message.KEventAction;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.message.KRequiresEvent;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.KLevelComponentTags;
import io.github.darthakiranihil.konna.level.entity.KMapEntity;

import java.util.Objects;

/**
 * The elementary unit of a game level (location) that provides all information
 * about its environment (including tiles, links to other sectors etc.).
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
@KRequiresEvent(
    name = "entityLeftSector",
    simple = false,
    type = KMapSector.EventData.class
)
@KRequiresEvent(
    name = "entityMoved",
    simple = false,
    type = KMapSector.EventData.class
)
public final class KMapSector extends KObject {

    public record EventData(
        KMapEntity entity,
        KPair<KVector2i, KMapSector> previousPosition
    ) {

    }

    private final KEventAction<EventData> entityLeftSectorConsumer = this::onEntityLeftSector;
    private final KEventAction<EventData> entityMovedConsumer = this::onEntityMoved;

    private final KEvent<EventData> entityLeftSectorEvent;
    private final KEvent<EventData> entityMovedEvent;

    private final KTileLayer tileLayer;
    private final KSectorLinkLayer sectorLinkLayer;
    private final KMapEntityLayer entityLayer;

    /**
     * Standard constructor.
     * @param name Name of the sector
     * @param tileLayer Tile layer, assigned to this sector
     * @param sectorLinkLayer Sector link layer, assigned to this sector
     */
    public KMapSector(
        final KEventSystem eventSystem,
        final String name,
        final KTileLayer tileLayer,
        final KSectorLinkLayer sectorLinkLayer,
        final KMapEntityLayer entityLayer
    ) {
        super(name, KStructUtils.setOfTags(KLevelComponentTags.SECTOR));

        this.tileLayer = tileLayer;
        this.sectorLinkLayer = sectorLinkLayer;
        this.entityLayer = entityLayer;

        this.entityLeftSectorEvent = Objects.requireNonNull(
            eventSystem.getEvent("entityLeftSector")
        );
        this.entityMovedEvent = Objects.requireNonNull(
            eventSystem.getEvent("entityMoved")
        );

        this.entityMovedEvent.subscribe(this.entityMovedConsumer);
        this.entityLeftSectorEvent.subscribe(this.entityLeftSectorConsumer);

    }

    /**
     * Returns information of all sector layers that are placed on specific location.
     * @param x X coordinate of sliced position
     * @param y Y coordinate of sliced position
     * @return Slice of the sector on specific place
     */
    public KMapSectorSlice getSlice(
        int x,
        int y
    ) {

        return new KMapSectorSlice(
            this.tileLayer.getOnPosition(x, y),
            this.sectorLinkLayer.getOnPosition(x, y),
            this.entityLayer.getOnPosition(x, y)
        );

    }


    public void unload() {

        this.entityMovedEvent.unsubscribe(this.entityMovedConsumer);
        this.entityLeftSectorEvent.unsubscribe(this.entityLeftSectorConsumer);

    }

    private void onEntityMoved(final EventData data) {

        if (data.previousPosition.second() != this) {
            return;
        }

        this
            .entityLayer
            .placeEntity(data.entity.getPosition().first(), data.entity)
            .removeEntity(data.previousPosition.first(), data.entity);

    }

    private void onEntityLeftSector(final EventData data) {
        if (data.previousPosition.second() == this) {
            this.entityLayer.removeEntity(
                data.previousPosition.first(),
                data.entity
            );
            return;
        }

        var dst = data.entity.getPosition();
        if (dst.second() != this) {
            return;
        }

        this.entityLayer.placeEntity(
            dst.first(),
            data.entity
        );

    }
}
