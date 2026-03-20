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

import io.github.darthakiranihil.konna.core.message.KEvent;
import io.github.darthakiranihil.konna.core.message.KEventAction;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.message.KRequiresEvent;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.entity.KLevelEntity;
import io.github.darthakiranihil.konna.level.layer.*;

import java.util.*;

/**
 * The elementary unit of a game level that provides all information
 * about its environment (including tiles, links to other sectors etc.).
 * It requires {@code entityLeftSector} and {@code entityMoved} event to work properly.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
@KRequiresEvent(
    name = "entityLeftSector",
    simple = false,
    type = KLevelSector.EventData.class
)
@KRequiresEvent(
    name = "entityMoved",
    simple = false,
    type = KLevelSector.EventData.class
)
public final class KLevelSector extends KObject {

    /**
     * Event data record for {@code entityLeftSector} and {@code entityMoved} events.
     * @param entity Affected entity
     * @param previousPosition Previous position of affected entity
     */
    public record EventData(
        KLevelEntity entity,
        KPair<KVector2i, KLevelSector> previousPosition
    ) {

    }

    private final KEventAction<EventData> entityLeftSectorConsumer = this::onEntityLeftSector;
    private final KEventAction<EventData> entityMovedConsumer = this::onEntityMoved;

    private final KEvent<EventData> entityLeftSectorEvent;
    private final KEvent<EventData> entityMovedEvent;

    private final KTileLayer tileLayer;
    private final KHeightLayer heightLayer;
    private final KSectorLinkLayer sectorLinkLayer;
    private final KLevelEntityLayer entityLayer;

    private final KReachabilityAreaLayer reachabilityAreaLayer;
    private final KVisitedPlacesLayer seenPlacesLayer;

    /**
     * Standard constructor.
     * @param eventSystem Event system to get {@code entityLeftSector}
     *                    and {@code entityMoved} events
     * @param name Name of the sector
     * @param tileLayer Tile layer, assigned to this sector
     * @param heightLayer Height layer, assigned to this sector
     * @param sectorLinkLayer Sector link layer, assigned to this sector
     * @param entityLayer Entity layer, assigned to this sector
     */
    public KLevelSector(
        final KEventSystem eventSystem,
        final String name,
        final KTileLayer tileLayer,
        final KHeightLayer heightLayer,
        final KSectorLinkLayer sectorLinkLayer,
        final KLevelEntityLayer entityLayer
    ) {
        super(name, KStructUtils.setOfTags(KLevelComponentTags.SECTOR));

        this.tileLayer = tileLayer;
        this.heightLayer = heightLayer;
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

        this.reachabilityAreaLayer = new KReachabilityAreaLayer(tileLayer, heightLayer);
        this.seenPlacesLayer = new KVisitedPlacesLayer(tileLayer.getSize());
    }

    /**
     * Returns size of this sector, that is defined as size of its tile layer since
     * each sized layers of sectors are equal by size.
     * @return Size of this sector
     */
    public KSize getSize() {
        return this.tileLayer.getSize();
    }

    /**
     * Returns information of all sector layers that are placed on specific level.
     * @param x X coordinate of sliced position
     * @param y Y coordinate of sliced position
     * @return Slice of the sector on specific place
     */
    public KLevelSectorSlice getSlice(
        int x,
        int y
    ) {

        return new KLevelSectorSlice(
            this.name,
            new KVector2i(x, y),
            this.heightLayer.getOnPosition(x, y),
            this.tileLayer.getOnPosition(x, y),
            this.seenPlacesLayer.getSeenStatus(x, y),
            this.sectorLinkLayer.getOnPosition(x, y),
            this.entityLayer.getOnPosition(x, y)
        );

    }

    /**
     * Returns information of all sector layers that are placed on specific level
     * and marks it as seen.
     * @param x X coordinate of sliced position
     * @param y Y coordinate of sliced position
     * @return Slice of the sector on specific place
     */
    public KLevelSectorSlice getSliceAndVisit(
        int x,
        int y
    ) {

        KTileInfo tile = this.tileLayer.getOnPosition(x, y);
        if (tile != null) {
            this.seenPlacesLayer.seeThePlace(x, y);
        }
        return this.getSlice(x, y);

    }

    /**
     * @param src Source point
     * @param dst Destination point
     * @return Whether it is possible to reach destination from source point in this layer
     */
    public boolean isReachable(final KVector2i src, final KVector2i dst) {
        return this.reachabilityAreaLayer.isReachable(src, dst);
    }

    /**
     * @param srcX X coordinate of source point
     * @param srcY Y coordinate of source point
     * @param dstX X coordinate of destination point
     * @param dstY Y coordinate of destination point
     * @return Whether it is possible to reach destination from source point in this layer
     */
    public boolean isReachable(int srcX, int srcY, int dstX, int dstY) {
        return this.reachabilityAreaLayer.isReachable(srcX, srcY, dstX, dstY);
    }

    // todo: remove this disgusting poltergeist shit make a direct access,
    // but not through layers but something like "tools"
    /**
     * Places an entity on this layer.
     * @param x X coordinate to place the entity
     * @param y Y coordinate to place the entity
     * @param entity Entity to place
     * @return This layer (for method chaining)
     */
    public KLevelEntityLayer placeEntity(int x, int y, final KLevelEntity entity) {
        return this.entityLayer.placeEntity(x, y, entity);
    }

    /**
     * Places an entity on this layer.
     * @param position Position to place the entity
     * @param entity Entity to place
     * @return This layer (for method chaining)
     */
    public KLevelEntityLayer placeEntity(final KVector2i position, final KLevelEntity entity) {
        return this.entityLayer.placeEntity(position, entity);
    }

    /**
     * Removes an entity from this layer.
     * @param x X coordinate to remove the entity from
     * @param y Y coordinate to remove the entity from
     * @param entity Entity to remove
     * @return This layer (for method chaining)
     */
    public KLevelEntityLayer removeEntity(int x, int y, final KLevelEntity entity) {
        return this.entityLayer.removeEntity(x, y, entity);
    }

    /**
     * Removes an entity from this layer.
     * @param position Position to remove the entity from
     * @param entity Entity to remove
     * @return This layer (for method chaining)
     */
    public KLevelEntityLayer removeEntity(final KVector2i position, final KLevelEntity entity) {
        return this.entityLayer.removeEntity(position, entity);
    }

    /**
     * @param destinationSector Name of sector that is destination for found links
     * @return Map of links to the destination sector in this sector
     */
    public Map<KVector2i, KSectorLinkData> getLinksToSector(final String destinationSector) {
        return this.sectorLinkLayer.getToSector(destinationSector);
    }

    /**
     * Unloads this sector (unsubscribes from {@code entityLeftSector}
     * and {@code entityMoved} events).
     */
    public void unload() {

        this.entityMovedEvent.unsubscribe(this.entityMovedConsumer);
        this.entityLeftSectorEvent.unsubscribe(this.entityLeftSectorConsumer);

    }

    /**
     * Refreshes this sector.
     */
    public void refresh() {

        this.reachabilityAreaLayer.refresh();

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
