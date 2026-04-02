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
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.level.layer.KPassabilityLayer;
import io.github.darthakiranihil.konna.level.layer.KPassabilityState;
import io.github.darthakiranihil.konna.level.layer.tool.KPassabilityLayerTool;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class KExtrudeBorderByFirstAndLastImpassableNodeTests extends KStandardTestClass {

    @Test
    public void testProcessSuccess() {


        KPassabilityLayer passabilityLayer = new KPassabilityLayer(new KSize(7, 7));
        KPassabilityLayerTool tool = passabilityLayer.getTool();

        Random rnd = new Random();
        for (int x = 1; x < 6; x++) {
            tool.setState(x, 1, KPassabilityState.IMPASSABLE);
            tool.setState(x, 5, KPassabilityState.IMPASSABLE);
        }
        for (int y = 1; y < 6; y++) {
            tool.setState(1, y, KPassabilityState.IMPASSABLE);
            tool.setState(5, y, KPassabilityState.IMPASSABLE);
        }

        for (int x = 2; x < 5; x++) {
            for (int y = 2; y < 5; y++) {
                if (rnd.nextFloat() > 0.5) {
                    tool.setState(x, y, KPassabilityState.IMPASSABLE);
                }
            }
        }

        KUniversalMap params = new KUniversalMap();
        params.put("layer", passabilityLayer);

        KExtrudeBorderByFirstAndLastImpassableNode node = new KExtrudeBorderByFirstAndLastImpassableNode();
        KUniversalMap result = node.process(params, rnd);

        Assertions.assertNotNull(result.getSafe("layer", KPassabilityLayer.class));
        KPassabilityLayer layer = result.get("layer", KPassabilityLayer.class);

        for (int x = 1; x < 6; x++) {
            Assertions.assertEquals(KPassabilityState.IMPASSABLE, layer.getOnPosition(x, 1));
            Assertions.assertEquals(KPassabilityState.IMPASSABLE, layer.getOnPosition(x, 5));
        }
        for (int y = 1; y < 6; y++) {
            Assertions.assertEquals(KPassabilityState.IMPASSABLE, layer.getOnPosition(1, y));
            Assertions.assertEquals(KPassabilityState.IMPASSABLE, layer.getOnPosition(5, y));
        }

        for (int x = 2; x < 5; x++) {
            for (int y = 2; y < 5; y++) {
                Assertions.assertEquals(KPassabilityState.PASSABLE, layer.getOnPosition(x, y));
            }
        }

    }
}
