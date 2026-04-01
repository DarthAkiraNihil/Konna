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
import io.github.darthakiranihil.konna.level.KTileInfo;
import io.github.darthakiranihil.konna.level.layer.KPassabilityLayer;
import io.github.darthakiranihil.konna.level.layer.KPassabilityState;
import io.github.darthakiranihil.konna.level.layer.KTileLayer;
import io.github.darthakiranihil.konna.level.layer.tool.KPassabilityLayerTool;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Random;

public class KSimpleMapPassabilityToTilesNodeTests extends KStandardTestClass {

    @Test
    public void testProcessSuccess() {

        KPassabilityLayer p = new KPassabilityLayer(KSize.squared(3));
        KPassabilityLayerTool pt = p.getTool();

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++){
                pt.setState(x, y, KPassabilityState.IMPASSABLE);
            }
        }

        pt.setState(1, 1, KPassabilityState.PASSABLE);
        p.refresh();

        KTileInfo passable = new KTileInfo(
            "passable",
            1,
            true,
            0,
            Map.of()
        );
        KTileInfo impassable = new KTileInfo(
            "impassable",
            2,
            false,
            16,
            Map.of()
        );

        KUniversalMap params = new KUniversalMap();
        params.put("passability_layer", p);
        params.put("passable_tile", passable);
        params.put("impassable_tile", impassable);

        var node = new KSimpleMapPassabilityToTilesNode();
        KUniversalMap result = node.process(params, new Random());

        Assertions.assertNotNull(result.getSafe("layer", KTileLayer.class));
        KTileLayer layer = result.get("layer", KTileLayer.class);

        Assertions.assertEquals(p.getSize(), layer.getSize());

        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                KTileInfo info = layer.getOnPosition(x, y);
                Assertions.assertNotNull(info);
                if (x == 1 && y == 1) {
                    Assertions.assertEquals(passable.getId(), info.getId());
                } else {
                    Assertions.assertEquals(impassable.getId(), info.getId());
                }
            }
        }

    }
}
