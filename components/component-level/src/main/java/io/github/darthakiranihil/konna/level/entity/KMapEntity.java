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

package io.github.darthakiranihil.konna.level.entity;

import io.github.darthakiranihil.konna.core.message.KEvent;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.message.KRequiresEvent;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.layer.KLevelSector;
import io.github.darthakiranihil.konna.level.layer.KLevelSectorSlice;
import io.github.darthakiranihil.konna.level.layer.KSectorLinkData;

import java.util.Objects;

/**
 * <p>Base class for all map entities, that is, however, sealed, since there are not so many
 * possible types of map entities.</p>
 * <p>
 *     Each entity has its current sector it's located in and position in this sector.
 *     Also, it requires {@code entityLeftSector} and {@code entityMoved} events to work properly
 *     (they are used for notifying sectors about their movement inside them)
 * </p>
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
public abstract sealed class KMapEntity
    extends KObject
    permits
        KControllableEntity,
        KStaticEntity,
        KAutonomousEntity {

    private final KEvent<KLevelSector.EventData> entityLeftSectorEvent;
    private final KEvent<KLevelSector.EventData> entityMovedEvent;

    private KVector2i position;
    private KLevelSector currentSector;

    private final String descriptor;

    /**
     * Standard constructor.
     * @param eventSystem Event system to get {@code entityLeftSector}
     *                    and {@code entityMoved} events
     * @param name Entity name
     * @param descriptor Entity descriptor, representing it (maybe an asset id,
     *                   referencing created entity)
     * @param position Initial position
     * @param currentSector Initial sector that is entity attached to
     */
    public KMapEntity(
        final KEventSystem eventSystem,
        final String name,
        final String descriptor,
        final KVector2i position,
        final KLevelSector currentSector
    ) {
        super(name);

        this.position = position;
        this.currentSector = currentSector;

        this.descriptor = descriptor;

        this.entityLeftSectorEvent = Objects.requireNonNull(
            eventSystem.getEvent("entityLeftSector")
        );
        this.entityMovedEvent = Objects.requireNonNull(
            eventSystem.getEvent("entityMoved")
        );

    }

    /**
     * Performs movement for this entity, that is performed with reading its next move direction
     * and updating position according to destination tile passability and sector links (if
     * the entity requires to go to another sector, it will be handled properly. It is performed
     * by adding it to the destination sector and removing it from the source sector).
     */
    public final void move() {

        var previousPosition = this.getPosition();
        KVector2i nextDirection = this.getNextMoveDirection();
        if (nextDirection == KVector2i.ZERO) {
            return;
        }

        KVector2i nextPosition = this.position.add(nextDirection);
        KLevelSectorSlice previousSlice = this.currentSector.getSlice(
            this.position.x(),
            this.position.y()
        );
        int previousHeight = previousSlice.height();

        KLevelSectorSlice slice = this.currentSector.getSlice(
            nextPosition.x(), nextPosition.y()
        );

        if (slice.tile() == null) { // maybe we need to jump to the linked sector

            KSectorLinkData linkedSectorData = this
                .currentSector
                .getSlice(this.position.x(), this.position.y())
                .sectorLink();

            if (linkedSectorData == null) { // oops, there are no link, cannot move
                return;
            }
            nextPosition = linkedSectorData.destination().add(nextDirection);

            KLevelSectorSlice dstSlice = linkedSectorData
                .linkedSector()
                .getSlice(
                    nextPosition.x(),
                    nextPosition.y()
                );

            if (
                    dstSlice.tile() == null
                ||  !dstSlice.tile().isPassable()
                ||  Math.abs(dstSlice.height() - previousHeight) > 1
            ) {
                return;
            }

            this.position = nextPosition;
            this.currentSector = linkedSectorData.linkedSector();
            this.entityLeftSectorEvent.invoke(
                new KLevelSector.EventData(
                    this, previousPosition
                )
            );
            return;
        }

        if (!slice.tile().isPassable() || Math.abs(slice.height() - previousHeight) > 1) {
            return;
        }

        this.position = nextPosition;
        this.entityMovedEvent.invoke(new KLevelSector.EventData(this, previousPosition));
    }

    /**
     * @return Next move direction for this entity.
     */
    protected abstract KVector2i getNextMoveDirection();

    /**
     * @return Current sector of this entity and the position inside it
     */
    public final KPair<KVector2i, KLevelSector> getPosition() {
        return new KPair<>(this.position, this.currentSector);
    }

    /**
     * @return This entity's descriptor
     */
    public String getDescriptor() {
        return this.descriptor;
    }

    /**
     * Sets a new sector as current for it. Warning: it is not sage as the bounds are not checked.
     * Actually should be used in level generators.
     * @param mapSector New map sector
     * @param newPosition Position inside the sector
     */
    public final void setCurrentSector(final KLevelSector mapSector, final KVector2i newPosition) {
        this.currentSector = mapSector;
    }
}
