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

package io.github.darthakiranihil.konna.level.generator.mutator.passability;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.except.KInvalidArgumentException;
import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNode;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeInputParam;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeOutputParam;
import io.github.darthakiranihil.konna.level.layer.KPassabilityLayer;
import io.github.darthakiranihil.konna.level.layer.KPassabilityState;
import io.github.darthakiranihil.konna.level.layer.tool.KPassabilityLayerTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class KSimpleCellularAutomatonNode implements KGeneratorNode {

    @Override
    @KGeneratorNodeInputParam(name = "init_state_layer", type = KPassabilityLayer.class)
    @KGeneratorNodeInputParam(name = "rule", type = String.class)
    @KGeneratorNodeInputParam(name = "iterations", type = Integer.class)
    @KGeneratorNodeOutputParam(name = "layer", type = KPassabilityLayer.class)
    // B2/S123
    public KUniversalMap process(final KUniversalMap params, final Random rnd) {

        String rule = params.get("rule", String.class);
        int iterations = params.get("iterations", Integer.class);

        var parsedRule = this.parseRule(rule);
        KPassabilityLayer current = params.get("init_state_layer", KPassabilityLayer.class);
        KSize size = current.getSize();

        KPassabilityLayer next = new KPassabilityLayer(size);

        for (int it = 0; it < iterations; it++) {
            KPassabilityLayerTool nextTool = next.getTool();

            for (int x = 0; x < size.width(); x++) {
                for (int y = 0; y < size.height(); y++) {
                    KPassabilityState currentState = current.getOnPosition(x, y);
                    int alive = this.countAliveNeighbours(x, y, current);

                    if (currentState == KPassabilityState.IMPASSABLE) {
                        if (!parsedRule.second().contains(alive)) {
                            nextTool.setState(x, y, KPassabilityState.VOID);
                        }
                    } else {
                        if (parsedRule.first().contains(alive)) {
                            nextTool.setState(x, y, KPassabilityState.IMPASSABLE);
                        }
                    }
                }
            }

            KPassabilityLayer temp = current;
            current = next;
            next = temp;

        }

        KUniversalMap result = new KUniversalMap();
        result.put("layer", current);
        return result;
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
        if (stayRule.charAt(0) != 'S') {
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

    private int countAliveNeighbours(int x, int y, final KPassabilityLayer layer) {
        int alive = 0;

        for (int xd = -1; xd <= 1; xd++) {
            for (int yd = -1; yd <= 1; yd++) {
                if (xd == 0 && yd == 0) {
                    continue;
                }

                if (layer.getOnPosition(x + xd, y + yd) == KPassabilityState.IMPASSABLE) {
                    alive++;
                }
            }
        }

        return alive;
    }
}
