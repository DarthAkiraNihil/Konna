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

package io.github.darthakiranihil.konna.level;

import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.entity.KMapEntity;
import io.github.darthakiranihil.konna.level.layer.KSectorLinkData;
import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * Immutable data structure that represents information
 * about all sector layers on specific position.
 * @param sectorName Sector name that the slice belongs to
 * @param position Position of the slice in assigned sector
 * @param tile Tile located on the slice
 * @param height Height of the slice
 * @param seen Flag indicates if this slice has been visited earlier
 * @param sectorLink Link to the sector assigned to the slice
 * @param entities List of entities located on the slice
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public record KLevelSectorSlice(
    String sectorName,
    KVector2i position,
    int height,
    @Nullable KTileInfo tile,
    boolean seen,
    @Nullable KSectorLinkData sectorLink,
    List<KMapEntity> entities
) {

}
