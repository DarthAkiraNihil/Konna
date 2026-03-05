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

import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.message.KEvent;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.message.KRequiresEvent;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.map.KMapSector;
import io.github.darthakiranihil.konna.level.map.KMapSectorSlice;
import io.github.darthakiranihil.konna.level.map.KSectorLinkData;

import java.util.Objects;

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
public abstract class KMapEntity extends KObject {

    private final KEvent<KMapSector.EventData> entityLeftSectorEvent;
    private final KEvent<KMapSector.EventData> entityMovedEvent;

    private KVector2i position;
    private KMapSector currentSector;

    public KMapEntity(
        @KInject final KEventSystem eventSystem,
        final String name,
        final KVector2i position,
        final KMapSector currentSector
    ) {
        super(name);

        this.position = position;
        this.currentSector = currentSector;

        this.entityLeftSectorEvent = Objects.requireNonNull(
            eventSystem.getEvent("entityLeftSector")
        );
        this.entityMovedEvent = Objects.requireNonNull(
            eventSystem.getEvent("entityMoved")
        );

    }

    public final void move() {

        var previousPosition = this.getPosition();
        KVector2i nextDirection = this.getNextMoveDirection();
        if (nextDirection == KVector2i.ZERO) {
            return;
        }

        KVector2i nextPosition = this.position.add(nextDirection);
        KMapSectorSlice slice = this.currentSector.getSlice(
            nextPosition.x(), nextPosition.y()
        );

        if (slice.tile() == null) { // maybe we need to jump to the linked sector

            KSectorLinkData linkedSectorData = slice.sectorLink();
            if (linkedSectorData == null) { // oops, there are no link, cannot move
                return;
            }
            nextPosition = linkedSectorData.destination().add(nextDirection);

            KMapSectorSlice dstSlice = linkedSectorData
                .linkedSector()
                .getSlice(
                    nextPosition.x(),
                    nextPosition.y()
                );

            if (dstSlice.tile() == null || !dstSlice.tile().isPassable()) {
                return;
            }

            this.position = nextPosition;
            this.currentSector = linkedSectorData.linkedSector();
            this.entityLeftSectorEvent.invoke(
                new KMapSector.EventData(
                    this, previousPosition
                )
            );
            return;
        }

        if (!slice.tile().isPassable()) {
            return;
        }

        this.position = nextPosition;
        this.entityMovedEvent.invoke(new KMapSector.EventData(this, previousPosition));
    }

    protected abstract KVector2i getNextMoveDirection();

    public final KPair<KVector2i, KMapSector> getPosition() {
        return new KPair<>(this.position, this.currentSector);
    }

}
