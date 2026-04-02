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
import io.github.darthakiranihil.konna.level.layer.KTileLayer;
import io.github.darthakiranihil.konna.level.layer.tool.KTileLayerTool;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Random;

public class KInjectTilesNodeTests extends KStandardTestClass {

    @Test
    public void testProcessSuccess() {

        KTileInfo passable1 = new KTileInfo(
            "passable1",
            1,
            true,
            0,
            Map.of()
        );
        KTileInfo impassable1 = new KTileInfo(
            "impassable1",
            2,
            false,
            16,
            Map.of()
        );
        KTileInfo passable2 = new KTileInfo(
            "passable2",
            3,
            true,
            0,
            Map.of()
        );
        KTileInfo impassable2 = new KTileInfo(
            "impassable2",
            4,
            false,
            16,
            Map.of()
        );

        KTileLayer l1 = new KTileLayer(3, 3);
        KTileLayer l2 = new KTileLayer(3, 3);

        KTileLayerTool lt1 = l1.getTool();
        KTileLayerTool lt2 = l2.getTool();

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                lt1.placeTile(x, y, impassable1);
                if (x < 2) {
                    lt2.placeTile(x, y, impassable2);
                }
            }
        }

        lt1.placeTile(1, 1, passable1);
        lt2.placeTile(0, 1, passable2);

        KUniversalMap params = new KUniversalMap();
        params.put("destination", l1);
        params.put("injected", l2);
        params.put("offset", new KVector2i(1, 0));

        KInjectTilesNode node = new KInjectTilesNode();
        KUniversalMap nodeResult = node.process(params, new Random());

        Assertions.assertNotNull(nodeResult.getSafe("result", KTileLayer.class));
        KTileLayer result = nodeResult.get("result", KTileLayer.class);

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                KTileInfo info = result.getOnPosition(x, y);
                Assertions.assertNotNull(info);

                if (x == 0) {
                    Assertions.assertEquals(impassable1.getId(), info.getId());
                } else if (x == 1 && y == 1) {
                    Assertions.assertEquals(passable2.getId(), info.getId());
                } else {
                    Assertions.assertEquals(impassable2.getId(), info.getId());
                }
            }
        }

    }
}
