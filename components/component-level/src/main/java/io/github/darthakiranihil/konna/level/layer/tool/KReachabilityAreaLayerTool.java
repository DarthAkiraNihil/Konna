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

public interface KReachabilityAreaLayerTool extends KSizedLayerTool {

    /**
     * @param src Source point
     * @param dst Destination point
     * @return Whether it is possible to reach destination from source point in this layer
     */
    default boolean isReachable(final KVector2i src, final KVector2i dst) {
        return this.isReachable(src.x(), src.y(), dst.x(), dst.y());
    }

    /**
     * @param srcX X coordinate of source point
     * @param srcY Y coordinate of source point
     * @param dstX X coordinate of destination point
     * @param dstY Y coordinate of destination point
     * @return Whether it is possible to reach destination from source point in this layer
     */
    boolean isReachable(int srcX, int srcY, int dstX, int dstY);

}
