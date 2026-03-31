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

import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.struct.KVector2i;

/**
 * Representation of a map entity that can be moved manually.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KControllableEntity extends KLevelEntity {

    private KVector2i nextMove;

    public KControllableEntity(
        final KEventSystem eventSystem,
        final String name,
        final String descriptor
    ) {
        super(eventSystem, name, descriptor);

        this.nextMove = KVector2i.ZERO;
    }

    /**
     * @return The set move direction of {@link KVector2i#ZERO} if it is not specified
     */
    @Override
    protected KVector2i getNextMoveDirection() {
        KVector2i move = this.nextMove;
        this.nextMove = KVector2i.ZERO;
        return move;
    }

    /**
     * Sets the next move direction for this entity.
     * It will be used on next call of {@link KControllableEntity#getNextMoveDirection()}
     * @param direction The next move direction
     */
    public void setNextMoveDirection(final KVector2i direction) {
        this.nextMove = direction;
    }
}
