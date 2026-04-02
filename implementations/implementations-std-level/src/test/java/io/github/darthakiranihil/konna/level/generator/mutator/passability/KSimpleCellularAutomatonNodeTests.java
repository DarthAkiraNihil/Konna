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
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.layer.KPassabilityLayer;
import io.github.darthakiranihil.konna.level.layer.KPassabilityState;
import io.github.darthakiranihil.konna.level.layer.tool.KPassabilityLayerTool;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class KSimpleCellularAutomatonNodeTests extends KStandardTestClass {

    @Test
    public void testProcessSuccess() {

        KPassabilityLayer passabilityLayer = new KPassabilityLayer(new KSize(4, 4));
        KPassabilityLayerTool tool = passabilityLayer.getTool();
        tool.setState(0, 2, KPassabilityState.IMPASSABLE);
        tool.setState(1, 2, KPassabilityState.IMPASSABLE);
        tool.setState(2, 2, KPassabilityState.IMPASSABLE);
        tool.setState(2, 1, KPassabilityState.IMPASSABLE);
        tool.setState(1, 0, KPassabilityState.IMPASSABLE);

        KUniversalMap params = new KUniversalMap();
        params.put("init_state_layer", passabilityLayer);
        params.put("rule", "B3/S23");
        params.put("iterations", 1);

        KSimpleCellularAutomatonNode node = new KSimpleCellularAutomatonNode();
        KUniversalMap result = node.process(params, new Random());

        Assertions.assertNotNull(result.getSafe("layer", KPassabilityLayer.class));
        KPassabilityLayer layer = result.get("layer", KPassabilityLayer.class);

        Assertions.assertEquals(KPassabilityState.IMPASSABLE, layer.getOnPosition(0, 1));
        Assertions.assertEquals(KPassabilityState.IMPASSABLE, layer.getOnPosition(2, 1));
        Assertions.assertEquals(KPassabilityState.IMPASSABLE, layer.getOnPosition(1, 2));
        Assertions.assertEquals(KPassabilityState.IMPASSABLE, layer.getOnPosition(2, 2));
        Assertions.assertEquals(KPassabilityState.IMPASSABLE, layer.getOnPosition(new KVector2i(1, 3)));


    }

    @Test
    public void testProcessFailedInvalidRule() {

        Assertions.assertThrows(
            KInvalidArgumentException.class,
            () -> {
                KPassabilityLayer passabilityLayer = new KPassabilityLayer(new KSize(4, 4));
                KUniversalMap params = new KUniversalMap();
                params.put("init_state_layer", passabilityLayer);
                params.put("rule", "B3S23");
                params.put("iterations", 1);

                KSimpleCellularAutomatonNode node = new KSimpleCellularAutomatonNode();
                node.process(params, new Random());
            }
        );

        Assertions.assertThrows(
            KInvalidArgumentException.class,
            () -> {
                KPassabilityLayer passabilityLayer = new KPassabilityLayer(new KSize(4, 4));
                KUniversalMap params = new KUniversalMap();
                params.put("init_state_layer", passabilityLayer);
                params.put("rule", "x/S23");
                params.put("iterations", 1);

                KSimpleCellularAutomatonNode node = new KSimpleCellularAutomatonNode();
                node.process(params, new Random());
            }
        );

        Assertions.assertThrows(
            KInvalidArgumentException.class,
            () -> {
                KPassabilityLayer passabilityLayer = new KPassabilityLayer(new KSize(4, 4));
                KUniversalMap params = new KUniversalMap();
                params.put("init_state_layer", passabilityLayer);
                params.put("rule", "B2/x23");
                params.put("iterations", 1);

                KSimpleCellularAutomatonNode node = new KSimpleCellularAutomatonNode();
                node.process(params, new Random());
            }
        );


    }
}
