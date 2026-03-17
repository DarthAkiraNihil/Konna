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

package io.github.darthakiranihil.konna.level.layer;

import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.entity.KLevelEntity;

import java.util.*;

/**
 * Map layer containing information about entities located inside a sector on specific positions.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
@SuppressWarnings("UnusedReturnValue")
public final class KLevelEntityLayer implements KLevelLayer<List<KLevelEntity>> {

    private final Map<KVector2i, List<KLevelEntity>> entities;

    /**
     * Constructs an empty layer.
     */
    public KLevelEntityLayer() {
        this.entities = new HashMap<>();
    }

    /**
     * Places an entity on this layer.
     * @param x X coordinate to place the entity
     * @param y Y coordinate to place the entity
     * @param entity Entity to place
     * @return This layer (for method chaining)
     */
    public KLevelEntityLayer placeEntity(int x, int y, final KLevelEntity entity) {
        return this.placeEntity(new KVector2i(x, y), entity);
    }

    /**
     * Places an entity on this layer.
     * @param position Position to place the entity
     * @param entity Entity to place
     * @return This layer (for method chaining)
     */
    public KLevelEntityLayer placeEntity(final KVector2i position, final KLevelEntity entity) {
        if (!this.entities.containsKey(position)) {
            this.entities.put(position, new LinkedList<>());
        }

        List<KLevelEntity> list = this.entities.get(position);
        list.add(entity);
        return this;
    }

    /**
     * Removes an entity from this layer.
     * @param x X coordinate to remove the entity from
     * @param y Y coordinate to remove the entity from
     * @param entity Entity to remove
     * @return This layer (for method chaining)
     */
    public KLevelEntityLayer removeEntity(int x, int y, final KLevelEntity entity) {
        return this.removeEntity(new KVector2i(x, y), entity);
    }

    /**
     * Removes an entity from this layer.
     * @param position Position to remove the entity from
     * @param entity Entity to remove
     * @return This layer (for method chaining)
     */
    public KLevelEntityLayer removeEntity(final KVector2i position, final KLevelEntity entity) {
        if (!this.entities.containsKey(position)) {
            return this;
        }

        List<KLevelEntity> list = this.entities.get(position);
        Optional<KLevelEntity> deleted = list
            .stream()
            .filter(x -> x.id() == entity.id())
            .findFirst();
        deleted.ifPresent(list::remove);
        if (list.isEmpty()) {
            this.entities.remove(position);
        }
        return this;
    }

    @Override
    public List<KLevelEntity> getOnPosition(int x, int y) {
        return this.getOnPosition(new KVector2i(x, y));
    }

    @Override
    public List<KLevelEntity> getOnPosition(final KVector2i position) {
        if (!this.entities.containsKey(position)) {
            return List.of();
        }

        return this.entities.get(position);
    }
}
