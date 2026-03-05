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

package io.github.darthakiranihil.konna.level.map;

import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.entity.KMapEntity;
import org.jspecify.annotations.Nullable;

import java.util.*;

public final class KMapEntityLayer implements KMapLayer<List<KMapEntity>> {

    private final Map<KVector2i, List<KMapEntity>> entities;

    public KMapEntityLayer() {
        this.entities = new HashMap<>();
    }

    public KMapEntityLayer placeEntity(int x, int y, final KMapEntity entity) {
        return this.placeEntity(new KVector2i(x, y), entity);
    }

    public KMapEntityLayer placeEntity(final KVector2i position, final KMapEntity entity) {
        if (!this.entities.containsKey(position)) {
            this.entities.put(position, new LinkedList<>());
        }

        List<KMapEntity> list = this.entities.get(position);
        list.add(entity);
        return this;
    }

    public KMapEntityLayer removeEntity(int x, int y, final KMapEntity entity) {
        return this.removeEntity(new KVector2i(x, y), entity);
    }

    public KMapEntityLayer removeEntity(final KVector2i position, final KMapEntity entity) {
        if (!this.entities.containsKey(position)) {
            return this;
        }

        List<KMapEntity> list = this.entities.get(position);
        Optional<KMapEntity> deleted = list
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
    public List<KMapEntity> getOnPosition(int x, int y) {
        return this.getOnPosition(new KVector2i(x, y));
    }

    @Override
    public List<KMapEntity> getOnPosition(final KVector2i position) {
        if (!this.entities.containsKey(position)) {
            return List.of();
        }

        return this.entities.get(position);
    }
}
