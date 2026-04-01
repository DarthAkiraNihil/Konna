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
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.layer.KPassabilityLayer;
import io.github.darthakiranihil.konna.level.layer.KPassabilityState;
import io.github.darthakiranihil.konna.level.layer.tool.KPassabilityLayerTool;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class KPassabilityLayerAddNodeTests extends KStandardTestClass {

    @Test
    public void testProcessSuccess() {

        KPassabilityLayer p1 = new KPassabilityLayer(KSize.squared(3));
        KPassabilityLayer p2 = new KPassabilityLayer(KSize.squared(3));

        KPassabilityLayerTool p1t = p1.getTool();
        KPassabilityLayerTool p2t = p2.getTool();

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++){
                p1t.setState(x, y, KPassabilityState.IMPASSABLE);
                p2t.setState(x, y, KPassabilityState.IMPASSABLE);
            }
        }

        p1t.setState(1, 1, KPassabilityState.PASSABLE);
        p2t.setState(1, 1, KPassabilityState.PASSABLE);

        p1.refresh();
        p2.refresh();

        KPassabilityLayerAddNode node = new KPassabilityLayerAddNode();
        KUniversalMap params = new KUniversalMap();
        params.put("first", p1);
        params.put("second", p2);
        params.put("offset", new KVector2i(1, 0));
        KUniversalMap nodeResult = node.process(params, new Random());

        Assertions.assertNotNull(nodeResult.getSafe("result", KPassabilityLayer.class));
        Assertions.assertNotNull(nodeResult.getSafe("result_size", KSize.class));

        KPassabilityLayer result = nodeResult.get("result", KPassabilityLayer.class);
        KSize resultSize = nodeResult.get("result_size", KSize.class);

        Assertions.assertEquals(new KSize(4, 3), resultSize);
        Assertions.assertEquals(result.getSize(), resultSize);

        for (int x = 0; x < 4; x++) {
            Assertions.assertEquals(KPassabilityState.IMPASSABLE, result.getOnPosition(x, 0));
            Assertions.assertEquals(KPassabilityState.IMPASSABLE, result.getOnPosition(x, 2));
        }


        Assertions.assertEquals(KPassabilityState.IMPASSABLE, result.getOnPosition(0, 0));
        Assertions.assertEquals(KPassabilityState.IMPASSABLE, result.getOnPosition(3, 2));

        for (int x = 1; x < 3; x++) {
            Assertions.assertEquals(KPassabilityState.PASSABLE, result.getOnPosition(x, 1));
        }
    }
}
