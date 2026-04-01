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

package io.github.darthakiranihil.konna.level.generator.maker.passability;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.except.KInvalidArgumentException;
import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNode;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeInputParam;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeOutputParam;
import io.github.darthakiranihil.konna.level.layer.KPassabilityLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class KSimpleCellularAutomatonNode implements KGeneratorNode {

    @Override
    @KGeneratorNodeInputParam(name = "size", type = KSize.class)
    @KGeneratorNodeInputParam(name = "rule", type = String.class)
    @KGeneratorNodeOutputParam(name = "layer", type = KPassabilityLayer.class)
    // B2/S123
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        KSize size = params.get("size", KSize.class);
        String rule = params.get("rule", String.class);

        var parsedRule = this.parseRule(rule);
        KPassabilityLayer layer = new KPassabilityLayer(size);

        return null;
    }

    private KPair<List<Integer>, List<Integer>> parseRule(final String rule) {
        var parts = rule.split("/");
        if (parts.length != 2) {
            throw new KInvalidArgumentException(
                String.format(
                    "Cellular automaton rule is not valid: %s",
                    rule
                )
            );
        }

        String bornRule = parts[0];
        if (bornRule.charAt(0) != 'B') {
            throw new KInvalidArgumentException(
                String.format(
                    "The first char of \"born\" must be B, but got %s",
                    bornRule.charAt(0)
                )
            );
        }

        String stayRule = parts[1];
        if (stayRule.charAt(0) != 'B') {
            throw new KInvalidArgumentException(
                String.format(
                    "The first char of \"stay\" must be S, but got %s",
                    stayRule.charAt(0)
                )
            );
        }

        ArrayList<Integer> born = new ArrayList<>(8);
        for (int i = 1; i < bornRule.length(); i++) {
            born.add(Character.getNumericValue(bornRule.charAt(i)));
        }

        ArrayList<Integer> stay = new ArrayList<>(8);
        for (int i = 1; i < stayRule.length(); i++) {
            stay.add(Character.getNumericValue(stayRule.charAt(i)));
        }

        born.trimToSize();
        stay.trimToSize();

        return new KPair<>(
            born, stay
        );
    }
}
