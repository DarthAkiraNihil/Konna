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
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.layer.KPassabilityLayer;
import io.github.darthakiranihil.konna.level.layer.KPassabilityState;
import io.github.darthakiranihil.konna.level.layer.tool.KPassabilityLayerTool;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class KRandomPointOnPassabilityLayerNodeTests extends KStandardTestClass {

    @Test
    public void testProcessSuccess() {

        KPassabilityLayer passabilityLayer = new KPassabilityLayer(KSize.squared(5));
        KPassabilityLayerTool passabilityLayerTool = passabilityLayer.getTool();

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                passabilityLayerTool.setState(x, y, KPassabilityState.PASSABLE);
            }
        }

        KUniversalMap params = new KUniversalMap();
        params.put("layer", passabilityLayer);

        var node = new KRandomPointOnPassabilityLayerNode();
        KUniversalMap result = node.process(params, new Random(42069L));
        Assertions.assertNotNull(result.getSafe("point", KVector2i.class));

        KVector2i point = result.get("point", KVector2i.class);
        KPassabilityState state = passabilityLayer.getOnPosition(point);
        Assertions.assertEquals(KPassabilityState.PASSABLE, state);

    }

}
