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
import io.github.darthakiranihil.konna.level.KLevelSector;
import io.github.darthakiranihil.konna.level.entity.KLevelEntity;
import io.github.darthakiranihil.konna.level.layer.tool.KLevelEntityLayerTool;

import java.util.*;

/**
 * Map layer containing information about entities located inside a sector on specific positions.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
@SuppressWarnings("UnusedReturnValue")
public final class KLevelEntityLayer
    implements KObjectLevelLayer<List<KLevelEntity>, KLevelEntityLayerTool> {

    private static final class Tool implements KLevelEntityLayerTool {

        private final KLevelEntityLayer self;

        Tool(final KLevelEntityLayer self) {
            this.self = self;
        }

        @Override
        public KLevelEntityLayerTool placeEntity(
            final KVector2i position,
            final KLevelEntity entity
        ) {
            if (!this.self.entities.containsKey(position)) {
                this.self.entities.put(position, new LinkedList<>());
            }

            List<KLevelEntity> list = this.self.entities.get(position);
            list.add(entity);
            return this;
        }

        @Override
        public KLevelEntityLayerTool removeEntity(
            final KVector2i position,
            final KLevelEntity entity
        ) {
            if (!this.self.entities.containsKey(position)) {
                return this;
            }

            List<KLevelEntity> list = this.self.entities.get(position);
            Optional<KLevelEntity> deleted = list
                .stream()
                .filter(x -> x.id() == entity.id())
                .findFirst();
            deleted.ifPresent(list::remove);
            if (list.isEmpty()) {
                this.self.entities.remove(position);
            }
            return this;
        }

        @Override
        public List<KLevelEntity> getOnPosition(int x, int y) {
            return this.getOnPosition(new KVector2i(x, y));
        }

        @Override
        public List<KLevelEntity> getOnPosition(final KVector2i position) {
            if (!this.self.entities.containsKey(position)) {
                return List.of();
            }

            return this.self.entities.get(position);
        }

        @Override
        public KLevelEntityLayerTool setSectorForAll(KLevelSector sector) {
            for (var entry: this.self.entities.entrySet()) {
                KVector2i position = entry.getKey();
                List<KLevelEntity> entitiesOnPosition = entry.getValue();
                entitiesOnPosition.forEach(e -> e.setPosition(sector, position));
            }
            return this;
        }
    }

    private final Map<KVector2i, List<KLevelEntity>> entities;
    private final KLevelEntityLayerTool tool;

    /**
     * Constructs an empty layer.
     */
    public KLevelEntityLayer() {
        this.entities = new HashMap<>();
        this.tool = new Tool(this);
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

    @Override
    public KLevelEntityLayerTool getTool() {
        return this.tool;
    }
}
