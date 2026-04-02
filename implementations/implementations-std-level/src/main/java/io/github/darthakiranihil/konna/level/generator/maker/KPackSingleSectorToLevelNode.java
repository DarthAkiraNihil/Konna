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

package io.github.darthakiranihil.konna.level.generator.maker;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.level.KLevel;
import io.github.darthakiranihil.konna.level.KLevelSector;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNode;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeInputParam;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeOutputParam;

import java.util.Collections;
import java.util.Random;

/**
 * <p>
 *     Generator node that creates a new level from a single sector.
 * </p>
 * <h3>Inputs:</h3>
 * <ul>
 *     <li>{@code name} - {@link String} - level name</li>
 *     <li>{@code sector} - {@link KLevelSector} - sector to assign to the level</li>
 * </ul>
 * <h3>Outputs:</h3>
 * <ul>
 *     <li>
 *         {@code level} - {@link KLevel} - constructed level consisting of the only passed sector
 *     </li>
 * </ul>
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KPackSingleSectorToLevelNode implements KGeneratorNode {

    @Override
    @KGeneratorNodeInputParam(name = "name", type = String.class)
    @KGeneratorNodeInputParam(name = "sector", type = KLevelSector.class)
    @KGeneratorNodeOutputParam(name = "level", type = KLevel.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        String name = params.get("name", String.class);
        KLevelSector sector = params.get("sector", KLevelSector.class);

        KLevel level = new KLevel(
            name, Collections.singletonList(sector)
        );
        KUniversalMap result = new KUniversalMap();
        result.put("level", level);
        return result;

    }

}
