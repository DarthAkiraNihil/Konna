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
import org.jspecify.annotations.Nullable;

/**
 * Representation of an entity that moves automatically i.e. being
 * controller by another objects that specifies move directions for the assigned entity.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KAutonomousEntity extends KLevelEntity {

    private @Nullable KAutonomousEntityController controller;

    public KAutonomousEntity(
        final KEventSystem eventSystem,
        final String name,
        final String descriptor
    ) {
        super(eventSystem, name, descriptor);
    }

    /**
     * @return Next move direction provided by its assigned controller or
     *         {@link KVector2i#ZERO} if it is not specified
     */
    @Override
    protected KVector2i getNextMoveDirection() {
        if (this.controller == null) {
            return KVector2i.ZERO;
        }

        return this.controller.getNextMoveDirection();
    }

    /**
     * Assigns a controller to this entity.
     * @param controller Controller to assign to this entity
     */
    public void setController(final KAutonomousEntityController controller) {
        this.controller = controller;
    }

    /**
     * @return Controller assigned to this entity.
     */
    public @Nullable KAutonomousEntityController getController() {
        return controller;
    }
}
