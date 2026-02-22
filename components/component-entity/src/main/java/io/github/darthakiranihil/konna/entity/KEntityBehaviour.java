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

package io.github.darthakiranihil.konna.entity;

import org.jspecify.annotations.Nullable;

/**
 * Encapsulation of any entity behaviour that performs some operations
 * over multiple (or none) data components.
 *
 * @since 0.4.0
 * @author Darth Akira Nihil
 */
public abstract class KEntityBehaviour {

    private final KEntity entity;

    /**
     * Standard constructor.
     * @param entity Entity assigned to this behaviour
     */
    public KEntityBehaviour(final KEntity entity) {
        this.entity = entity;
    }

    /**
     * Tries to get a data components with specific class from assigned entity.
     * @param clazz Class of the data component to get
     * @return The actual data component instance or {@code null} if it is not found
     * @param <T> Type parameter for retrieved component
     */
    public final <T extends KEntityDataComponent> @Nullable T getComponent(
        final Class<T> clazz
    ) {
        return this.entity.getComponent(clazz);
    }

    /**
     * Action performed once the entity has been created.
     */
    public void awake() {

    }

    /**
     * Action performed when the entity has been destroyed.
     */
    public void onDestroy() {

    }

    /**
     * Action performed when the entity has been deactivated.
     */
    public void onDisable() {

    }

    /**
     * Action performed when the entity has been activated.
     */
    public void onEnable() {

    }

    /**
     * Action performed on each tick if assigned entity is active.
     */
    public void update() {

    }

}
