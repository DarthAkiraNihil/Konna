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

package io.github.darthakiranihil.konna.level.layer.tool;

import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.KLevelSector;
import io.github.darthakiranihil.konna.level.entity.KLevelEntity;

import java.util.List;

/**
 * Level entity layer tool interface, providing operations for manipulating
 * level entities.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public interface KLevelEntityLayerTool extends KReadableObjectLayerTool<List<KLevelEntity>> {

    /**
     * Places an entity on this layer.
     * @param x X coordinate to place the entity
     * @param y Y coordinate to place the entity
     * @param entity Entity to place
     * @return This layer (for method chaining)
     */
    default KLevelEntityLayerTool placeEntity(int x, int y, final KLevelEntity entity) {
        return this.placeEntity(new KVector2i(x, y), entity);
    }

    /**
     * Places an entity on this layer.
     * @param position Position to place the entity
     * @param entity Entity to place
     * @return This layer (for method chaining)
     */
    KLevelEntityLayerTool placeEntity(KVector2i position, KLevelEntity entity);

    /**
     * Removes an entity from this layer.
     * @param x X coordinate to remove the entity from
     * @param y Y coordinate to remove the entity from
     * @param entity Entity to remove
     * @return This layer (for method chaining)
     */
    default KLevelEntityLayerTool removeEntity(int x, int y, final KLevelEntity entity) {
        return this.removeEntity(new KVector2i(x, y), entity);
    }

    /**
     * Removes an entity from this layer.
     * @param position Position to remove the entity from
     * @param entity Entity to remove
     * @return This layer (for method chaining)
     */
    KLevelEntityLayerTool removeEntity(KVector2i position, KLevelEntity entity);

    List<KLevelEntity> findEntitiesWithDescriptor(String descriptor);

    /**
     * Sets a sector for all entities contained in this layer.
     * <i>Warning!</i> This is a dangerous operation and supposed to be used
     * on late setting entities' current sector on level loading and generating.
     * Entities' positions will be copied from layer.
     * @param sector Sector to set
     * @return This layer (for method chaining)
     */
    KLevelEntityLayerTool setSectorForAll(KLevelSector sector);

}
