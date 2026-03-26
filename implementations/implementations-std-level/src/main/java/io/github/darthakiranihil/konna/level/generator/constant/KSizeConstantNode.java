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

package io.github.darthakiranihil.konna.level.generator.constant;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.except.KInvalidArgumentException;
import io.github.darthakiranihil.konna.core.io.KAssetDefinition;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.level.generator.KConstantNode;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeOutputParam;

import java.util.Random;

public final class KSizeConstantNode implements KConstantNode {

    private final KSize object;

    /**
     * Constructs the node. Will throw a {@link KInvalidArgumentException} if
     * the passed object is not a valid size data (not an asset definition).
     * @param object Value to pack into the constant
     */
    public KSizeConstantNode(final Object object) {
        if (!KAssetDefinition.class.isAssignableFrom(object.getClass())) {
            throw new KInvalidArgumentException("Object must be an asset definition!");
        }

        KAssetDefinition data = (KAssetDefinition) object;
        if (!data.hasInt("height") || !data.hasInt("width")) {
            throw new KInvalidArgumentException(
                "Size data is not valid (must contain \"height\" and \"width\" ints"
            );
        }

        this.object = new KSize(
            data.getInt("width"),
            data.getInt("height")
        );
    }

    @Override
    @KGeneratorNodeOutputParam(name = "value", type = KSize.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {
        KUniversalMap result = new KUniversalMap();
        result.put("value", this.object);
        return result;
    }

}
