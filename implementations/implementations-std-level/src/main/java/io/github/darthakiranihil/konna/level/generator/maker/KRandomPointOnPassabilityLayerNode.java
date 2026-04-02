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
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNode;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeInputParam;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeOutputParam;
import io.github.darthakiranihil.konna.level.layer.KPassabilityLayer;

import java.util.Random;

/**
 * <p>
 *     Generator node that returns a random point on a passability layer.
 * </p>
 * <h3>Inputs:</h3>
 * <ul>
 *     <li>{@code layer} - {@link KPassabilityLayer} - layer to pick point from</li>
 * </ul>
 * <h3>Outputs:</h3>
 * <ul>
 *     <li>{@code point} - {@link KVector2i} - coordinates of random point on passed layer</li>
 * </ul>
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KRandomPointOnPassabilityLayerNode implements KGeneratorNode {

    @Override
    @KGeneratorNodeInputParam(name = "layer", type = KPassabilityLayer.class)
    @KGeneratorNodeOutputParam(name = "point", type = KVector2i.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        KPassabilityLayer layer = params.get("layer", KPassabilityLayer.class);
        KUniversalMap result = new KUniversalMap();
        result.put(
            "point",
            new KVector2i(
                rnd.nextInt(0, layer.getSize().width()),
                rnd.nextInt(0, layer.getSize().height())
            )
        );
        return result;

    }
}
