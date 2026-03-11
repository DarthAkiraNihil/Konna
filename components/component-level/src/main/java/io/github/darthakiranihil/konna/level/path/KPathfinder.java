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

package io.github.darthakiranihil.konna.level.path;

import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.map.KLocation;

public interface KPathfinder {

    default KPath findPath(KLocation location, KPair<String, KVector2i> src, KPair<String, KVector2i> dst) {
        return this.findPath(location, src.first(), src.second(), dst.first(), dst.second());
    }

    default KPath findPath(KLocation location, String srcSector, KVector2i srcPosition, String dstSector, KVector2i dstPosition) {
        return this.findPath(location, srcSector, srcPosition.x(), srcPosition.y(), dstSector, dstPosition.x(), dstPosition.y());
    }

    KPath findPath(KLocation location, String srcSector, int srcX, int srcY, String dstSector, int dstX, int dstY);

}
