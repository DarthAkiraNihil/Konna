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
import io.github.darthakiranihil.konna.level.entity.KLevelEntity;

import java.util.List;

public interface KLevelEntityLayerTool extends KReadableObjectLayerTool<List<KLevelEntity>> {

    /**
     * Places an entity on this layer.
     * @param x X coordinate to place the entity
     * @param y Y coordinate to place the entity
     * @param entity Entity to place
     * @return This layer (for method chaining)
     */
    KLevelEntityLayerTool placeEntity(int x, int y, final KLevelEntity entity);

    /**
     * Places an entity on this layer.
     * @param position Position to place the entity
     * @param entity Entity to place
     * @return This layer (for method chaining)
     */
    KLevelEntityLayerTool placeEntity(final KVector2i position, final KLevelEntity entity);

    /**
     * Removes an entity from this layer.
     * @param x X coordinate to remove the entity from
     * @param y Y coordinate to remove the entity from
     * @param entity Entity to remove
     * @return This layer (for method chaining)
     */
    KLevelEntityLayerTool removeEntity(int x, int y, final KLevelEntity entity);

    /**
     * Removes an entity from this layer.
     * @param position Position to remove the entity from
     * @param entity Entity to remove
     * @return This layer (for method chaining)
     */
    KLevelEntityLayerTool removeEntity(final KVector2i position, final KLevelEntity entity);

}
