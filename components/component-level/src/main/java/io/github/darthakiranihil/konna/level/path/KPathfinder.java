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

/**
 * Interface providing methods to find paths in specific locations
 * between two points.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public interface KPathfinder {

    /**
     * Finds a path from source to destination inside specific location.
     * @param location Location to find path in
     * @param src Source coordinates (sector + position)
     * @param dst Destination coordinates (sector + position)
     * @return Path from source to destination in specified location (if present),
     *         else {@link KPath#EMPTY}
     */
    default KPath findPath(
        final KLocation location,
        final KPair<String, KVector2i> src,
        final KPair<String, KVector2i> dst
    ) {
        return this.findPath(location, src.first(), src.second(), dst.first(), dst.second());
    }

    /**
     * Finds a path from source to destination inside specific location.
     * @param location Location to find path in
     * @param srcSector Source sector name
     * @param srcPosition Source position
     * @param dstSector Destination sector name
     * @param dstPosition Destination position
     * @return Path from source to destination in specified location (if present),
     *         else {@link KPath#EMPTY}
     */
    default KPath findPath(
        final KLocation location,
        final String srcSector,
        final KVector2i srcPosition,
        final String dstSector,
        final KVector2i dstPosition
    ) {
        return this.findPath(
            location,
            srcSector,
            srcPosition.x(),
            srcPosition.y(),
            dstSector,
            dstPosition.x(),
            dstPosition.y()
        );
    }

    /**
     * Finds a path from source to destination inside specific location.
     * @param location Location to find path in
     * @param srcSector Source sector name
     * @param srcX X coordinate of source position
     * @param srcY Y coordinate of source position
     * @param dstSector Destination sector name
     * @param dstX X coordinate of destination position
     * @param dstY Y coordinate of destination position
     * @return Path from source to destination in specified location (if present),
     *         else {@link KPath#EMPTY}
     */
    KPath findPath(
        KLocation location,
        String srcSector,
        int srcX,
        int srcY,
        String dstSector,
        int dstX,
        int dstY
    );

}
