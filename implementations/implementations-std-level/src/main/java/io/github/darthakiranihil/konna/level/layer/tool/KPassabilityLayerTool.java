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

import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.layer.KPassabilityState;
import org.jspecify.annotations.Nullable;

public interface KPassabilityLayerTool
    extends KReadableObjectLayerTool<KPassabilityState> {

    void setState(int x, int y, KPassabilityState state);
    void setImpassableIfVoid(int x, int y);
    void digPassableRectangle(KVector2i topLeft, KSize size);
    void digStraightPassableLine(KVector2i start, int length, KVector2i direction);

    @Override
    KPassabilityState getOnPosition(int x, int y);

    @Override
    default KPassabilityState getOnPosition(final KVector2i position) {
        return this.getOnPosition(position.x(), position.y());
    }

}
