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

import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.map.KMapSector;
import io.github.darthakiranihil.konna.level.map.KMapSectorSlice;

public abstract class KMapEntity extends KObject {

    private KVector2i position;
    private KMapSector currentSector;

    public KMapEntity(
        final String name,
        final KVector2i position,
        final KMapSector currentSector
    ) {
        super(name);

        this.position = position;
        this.currentSector = currentSector;
    }

    public void move() {

        KVector2i nextDirection = this.getNextMoveDirection();
        KVector2i nextPosition = this.position.add(nextDirection);
        KMapSectorSlice slice = this.currentSector.getSlice(
            nextPosition.x(), nextPosition.y()
        );

        if (slice.tile() == null) {

            KMapSector linkedSector = slice.sectorLink();
            if (linkedSector == null) {
                return;
            }

            this.currentSector = slice.sectorLink();

        }



    }

    protected abstract KVector2i getNextMoveDirection();
}
