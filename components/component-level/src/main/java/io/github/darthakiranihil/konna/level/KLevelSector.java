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

import io.github.darthakiranihil.konna.core.except.KInvalidArgumentException;
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
import io.github.darthakiranihil.konna.level.layer.tool.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

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

    private static final int RANDOM_POSITION_SELECTION_ATTEMPTS = 1024;

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
    private final KLevelTransitionLayer levelTransitionLayer;

    private final KReachabilityAreaLayer reachabilityAreaLayer;
    private final KVisitedPlacesLayer visitedPlacesLayer;

    private final Map<Class<? extends KLayerTool>, KLayerTool> tools;

    /**
     * Standard constructor.
     * @param eventSystem Event system to get {@code entityLeftSector}
     *                    and {@code entityMoved} events
     * @param name Name of the sector
     * @param tileLayer Tile layer, assigned to this sector
     * @param heightLayer Height layer, assigned to this sector
     * @param sectorLinkLayer Sector link layer, assigned to this sector
     * @param entityLayer Entity layer, assigned to this sector
     * @param levelTransitionLayer Level transition layer, assigned to this sector
     */
    public KLevelSector(
        final KEventSystem eventSystem,
        final String name,
        final KTileLayer tileLayer,
        final KHeightLayer heightLayer,
        final KSectorLinkLayer sectorLinkLayer,
        final KLevelEntityLayer entityLayer,
        final KLevelTransitionLayer levelTransitionLayer
    ) {
        super(name, KStructUtils.setOfTags(KLevelComponentTags.SECTOR));

        this.tileLayer = tileLayer;
        this.heightLayer = heightLayer;
        this.sectorLinkLayer = sectorLinkLayer;
        this.entityLayer = entityLayer;
        this.levelTransitionLayer = levelTransitionLayer;

        this.entityLeftSectorEvent = Objects.requireNonNull(
            eventSystem.getEvent("entityLeftSector")
        );
        this.entityMovedEvent = Objects.requireNonNull(
            eventSystem.getEvent("entityMoved")
        );

        this.entityMovedEvent.subscribe(this.entityMovedConsumer);
        this.entityLeftSectorEvent.subscribe(this.entityLeftSectorConsumer);

        this.reachabilityAreaLayer = new KReachabilityAreaLayer(tileLayer, heightLayer);
        this.visitedPlacesLayer = new KVisitedPlacesLayer(tileLayer.getSize());

        this.tools = new HashMap<>();

        this.tools.put(KHeightLayerTool.class, this.heightLayer.getTool());
        this.tools.put(KLevelEntityLayerTool.class, this.entityLayer.getTool());
        this.tools.put(KReachabilityAreaLayerTool.class, this.reachabilityAreaLayer.getTool());
        this.tools.put(KSectorLinkLayerTool.class, this.sectorLinkLayer.getTool());
        this.tools.put(KTileLayerTool.class, this.sectorLinkLayer.getTool());
        this.tools.put(KVisitedPlacesLayerTool.class, this.visitedPlacesLayer.getTool());
        this.tools.put(KLevelTransitionLayerTool.class, this.levelTransitionLayer.getTool());

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
            this.visitedPlacesLayer.getOnPosition(x, y),
            this.sectorLinkLayer.getOnPosition(x, y),
            this.entityLayer.getOnPosition(x, y),
            this.levelTransitionLayer.getOnPosition(x, y)
        );

    }

    /**
     * @param position Coordinates of sliced position
     * @return Slice of the sector on specific place
     */
    public KLevelSectorSlice getSlice(
        final KVector2i position
    ) {
        return new KLevelSectorSlice(
            this.name,
            position,
            this.heightLayer.getOnPosition(position),
            this.tileLayer.getOnPosition(position),
            this.visitedPlacesLayer.getOnPosition(position),
            this.sectorLinkLayer.getOnPosition(position),
            this.entityLayer.getOnPosition(position),
            this.levelTransitionLayer.getOnPosition(position)
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
            var tool = this.visitedPlacesLayer.getTool();
            tool.visitPlace(x, y);
        }
        return this.getSlice(x, y);

    }

    /**
     * <p>
     *     Tries to get a layer tool for this sector. The tool may expose
     *     API that performs operations, that may change the sector. If you do that,
     *     you know what are you doing!
     * </p>
     * <p>
     *     Since this operation is not that safe, if will throw {@link KInvalidArgumentException}
     *     if passed tool class is not presented in the sector.
     * </p>
     * @param tool Tool class
     * @return Layer tool with specified class assigned to the sector
     * @param <T> Type of retrieved layer tool
     */
    @SuppressWarnings("unchecked")
    public <T extends KLayerTool> T getTool(final Class<T> tool) {
        if (!this.tools.containsKey(tool)) {
            throw new KInvalidArgumentException(
                String.format(
                    "Unknown tool class: %s. Assigned layer is not presented in the sector!",
                    tool
                )
            );
        }
        return (T) this.tools.get(tool);
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

    public KVector2i randomPosition(final Random rnd) {

        int attempts = 0;
        while (attempts < RANDOM_POSITION_SELECTION_ATTEMPTS) {
            KVector2i point = new KVector2i(
                rnd.nextInt(0, this.getSize().width()),
                rnd.nextInt(0, this.getSize().height())
            );

            var tile = this.tileLayer.getOnPosition(point);
            if (tile == null || !tile.isPassable()) {
                attempts++;
            } else {
                return point;
            }
        }

        return new KVector2i(
            rnd.nextInt(0, this.getSize().width()),
            rnd.nextInt(0, this.getSize().height())
        );

    }

    private void onEntityMoved(final EventData data) {

        if (data.previousPosition.second() != this) {
            return;
        }

        this
            .entityLayer
            .getTool()
            .placeEntity(data.entity.getPosition().first(), data.entity)
            .removeEntity(data.previousPosition.first(), data.entity);

    }

    private void onEntityLeftSector(final EventData data) {
        var tool = this.entityLayer.getTool();
        if (data.previousPosition.second() == this) {
            tool.removeEntity(
                data.previousPosition.first(),
                data.entity
            );
            return;
        }

        var dst = data.entity.getPosition();
        if (dst.second() != this) {
            return;
        }

        tool.placeEntity(
            dst.first(),
            data.entity
        );

    }
}
