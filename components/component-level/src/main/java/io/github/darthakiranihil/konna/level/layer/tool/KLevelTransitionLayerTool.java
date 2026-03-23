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
import io.github.darthakiranihil.konna.level.layer.KLevelTransitionData;
import io.github.darthakiranihil.konna.level.layer.KTransitionedLevelType;
import org.jspecify.annotations.Nullable;

public interface KLevelTransitionLayerTool
    extends KReadableObjectLayerTool<KLevelTransitionData> {

    default KLevelTransitionLayerTool makeTransition(
        int x,
        int y,
        String levelDescriptor,
        KTransitionedLevelType levelType,
        String destinationSector,
        int destinationX,
        int destinationY
    ) {
        return this.makeTransition(
            new KVector2i(x, y),
            levelDescriptor,
            levelType,
            destinationSector,
            new KVector2i(destinationX, destinationY)
        );
    }

    KLevelTransitionLayerTool makeTransition(
        KVector2i position,
        String levelDescriptor,
        KTransitionedLevelType levelType,
        String destinationSector,
        KVector2i destination
    );

    @Override
    default @Nullable KLevelTransitionData getOnPosition(int x, int y) {
        return this.getOnPosition(new KVector2i(x, y));
    }

    @Override
    @Nullable KLevelTransitionData getOnPosition(KVector2i position);


}
