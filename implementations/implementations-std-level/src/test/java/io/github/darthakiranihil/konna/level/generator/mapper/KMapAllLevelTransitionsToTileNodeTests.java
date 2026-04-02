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
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.KTileInfo;
import io.github.darthakiranihil.konna.level.layer.KLevelTransitionLayer;
import io.github.darthakiranihil.konna.level.layer.KTileLayer;
import io.github.darthakiranihil.konna.level.layer.KTransitionedLevelType;
import io.github.darthakiranihil.konna.level.layer.tool.KLevelTransitionLayerTool;
import io.github.darthakiranihil.konna.level.layer.tool.KTileLayerTool;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Random;

public class KMapAllLevelTransitionsToTileNodeTests extends KStandardTestClass {

    @Test
    public void testProcessSuccess() {

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
        KTileInfo transition = new KTileInfo(
            "transition",
            3,
            true,
            0,
            Map.of()
        );

        KTileLayer l = new KTileLayer(3, 3);

        KTileLayerTool lt = l.getTool();

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                lt.placeTile(x, y, impassable);
            }
        }
        lt.placeTile(1, 1, passable);

        KLevelTransitionLayer levelTransitionLayer = new KLevelTransitionLayer();
        KLevelTransitionLayerTool ltt = levelTransitionLayer.getTool();
        ltt.makeTransition(
            new KVector2i(1, 1),
            "left 4 ded",
            KTransitionedLevelType.GENERATED,
            "ded",
            new KVector2i(1, 0)
        );

        KUniversalMap params = new KUniversalMap();
        params.put("tile_layer", l);
        params.put("level_transition_layer", levelTransitionLayer);
        params.put("transition_tile", transition);

        var node = new KMapAllLevelTransitionsToTileNode();
        KUniversalMap result = node.process(params, new Random());

        Assertions.assertNotNull(result.getSafe("tile_layer", KTileLayer.class));
        Assertions.assertNotNull(result.getSafe("level_transition_layer", KLevelTransitionLayer.class));

        KTileLayer resultTileLayer = result.get("tile_layer", KTileLayer.class);
        KTileInfo center = resultTileLayer.getOnPosition(1, 1);
        Assertions.assertNotNull(center);
        Assertions.assertEquals(transition.getId(), center.getId());
    }
}
