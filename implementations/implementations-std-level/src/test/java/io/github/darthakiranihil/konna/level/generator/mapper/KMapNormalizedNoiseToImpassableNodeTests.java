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

package io.github.darthakiranihil.konna.level.generator.mapper;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.level.layer.KNoiseLayer;
import io.github.darthakiranihil.konna.level.layer.KPassabilityLayer;
import io.github.darthakiranihil.konna.level.layer.KPassabilityState;
import io.github.darthakiranihil.konna.level.layer.tool.KNoiseLayerTool;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class KMapNormalizedNoiseToImpassableNodeTests extends KStandardTestClass {

    @Test
    public void testProcessSuccess() {

        KNoiseLayer noise = new KNoiseLayer(KSize.squared(3));
        KNoiseLayerTool noiseLayerTool = noise.getTool();

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                noiseLayerTool.setNoiseValue(x, y, x == y ? 0.6f : 0.4f);
            }
        }

        KUniversalMap params = new KUniversalMap();
        params.put("noise", noise);
        params.put("min_limit", 0.5f);

        var node = new KMapNormalizedNoiseToImpassableNode();
        KUniversalMap result = node.process(params, new Random());

        Assertions.assertNotNull(result.getSafe("layer", KPassabilityLayer.class));
        KPassabilityLayer layer = result.get("layer", KPassabilityLayer.class);

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                Assertions.assertEquals(
                    x == y ? KPassabilityState.IMPASSABLE : KPassabilityState.VOID,
                    layer.getOnPosition(x, y)
                );
            }
        }
    }

}
