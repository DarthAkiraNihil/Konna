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
import io.github.darthakiranihil.konna.level.layer.tool.KLevelTransitionLayerTool;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class KLevelTransitionLayer
    implements KObjectLevelLayer<KLevelTransitionData, KLevelTransitionLayerTool> {

    private static class Tool implements KLevelTransitionLayerTool {

        private final KLevelTransitionLayer self;

        Tool(final KLevelTransitionLayer self) {
            this.self = self;
        }

        @Override
        public KLevelTransitionLayerTool makeTransition(
            final KVector2i position,
            final String levelDescriptor,
            final KTransitionedLevelType levelType,
            final String destinationSector,
            final KVector2i destination
        ) {
            this.self.links.put(
                position,
                new KLevelTransitionData(
                    levelDescriptor,
                    levelType,
                    destinationSector,
                    destination
                )
            );
            return this;
        }

        @Override
        public @Nullable KLevelTransitionData getOnPosition(final KVector2i position) {
            return this.self.links.get(position);
        }
    }

    private final Map<KVector2i, KLevelTransitionData> links;
    private final KLevelTransitionLayerTool tool;

    /**
     * Creates an empty layer.
     */
    public KLevelTransitionLayer() {
        this.links = new HashMap<>();
        this.tool = new KLevelTransitionLayer.Tool(this);
    }

    @Override
    public @Nullable KLevelTransitionData getOnPosition(int x, int y) {
        return this.getOnPosition(new KVector2i(x, y));
    }

    @Override
    public @Nullable KLevelTransitionData getOnPosition(final KVector2i position) {
        return this.links.get(position);
    }

    @Override
    public KLevelTransitionLayerTool getTool() {
        return this.tool;
    }

}
