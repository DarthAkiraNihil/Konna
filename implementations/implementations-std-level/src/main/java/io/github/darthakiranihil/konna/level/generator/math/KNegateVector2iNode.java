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

package io.github.darthakiranihil.konna.level.generator.math;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNode;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeInputParam;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeOutputParam;

import java.util.Random;

public class KNegateVector2iNode implements KGeneratorNode {

    @Override
    @KGeneratorNodeInputParam(name = "value", type = KVector2i.class)
    @KGeneratorNodeOutputParam(name = "value", type = KVector2i.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        KVector2i value = params.get("value", KVector2i.class);
        KUniversalMap result = new KUniversalMap();
        result.put("value", value.negate());
        return result;

    }
}
