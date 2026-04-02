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
import io.github.darthakiranihil.konna.level.generator.KConstantNode;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeOutputParam;

import java.util.Random;

/**
 * Constant generator node containing a boolean value.
 *
 * @since 0.5.0
 * @author Darth Akira Nihil
 */
public final class KBooleanConstantNode implements KConstantNode {

    private final boolean object;

    /**
     * Constructs the node. Will throw a {@link KInvalidArgumentException} if
     * the passed object is not a boolean.
     * @param object Value to pack into the constant
     */
    public KBooleanConstantNode(final Object object) {
        if (!Boolean.class.isAssignableFrom(object.getClass())) {
            throw new KInvalidArgumentException("Object must be a float!");
        }

        this.object = (Boolean) object;
    }

    @Override
    @KGeneratorNodeOutputParam(name = "value", type = Boolean.class)
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {
        KUniversalMap result = new KUniversalMap();
        result.put("value", this.object);
        return result;
    }

}
