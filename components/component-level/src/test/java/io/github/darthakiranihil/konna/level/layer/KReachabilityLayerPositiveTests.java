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

package io.github.darthakiranihil.konna.level.layer;

import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.KTileInfo;
import io.github.darthakiranihil.konna.level.layer.tool.KReachabilityAreaLayerTool;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class KReachabilityLayerPositiveTests extends KStandardTestClass {

    private final KReachabilityAreaLayerTool tool;

    public KReachabilityLayerPositiveTests() {

        KTileLayer layer = new KTileLayer(3, 3);
        KTileInfo tileInfo1 = new KTileInfo("tt", 1, true, 16, Map.of());
        KTileInfo tileInfo2 = new KTileInfo("tt", 2, false, 16, Map.of());

        layer
            .getTool()
            .placeTile(new KVector2i(0, 0), tileInfo1)
            .placeTile(0, 1, tileInfo1)
            .placeTile(0, 2, tileInfo1)
            .placeTile(1, 0, tileInfo2)
            .placeTile(1, 1, tileInfo2)
            .placeTile(1, 2, tileInfo2)
            .placeTile(2, 0, tileInfo1)
            .placeTile(2, 1, tileInfo1)
            .placeTile(2, 2, tileInfo1);

        KReachabilityAreaLayer reachabilityAreaLayer = new KReachabilityAreaLayer(
            layer,
            new KHeightLayer(
                new KSize(
                    3,
                    3
                )
            )
        );

        Assertions.assertEquals(layer.getSize(), reachabilityAreaLayer.getSize());
        
        this.tool = reachabilityAreaLayer.getTool();
    }

    @Test
    public void testReachable() {

        Assertions.assertTrue(
            this.tool.isReachable(
                new KVector2i(0, 0),
                new KVector2i(0, 2)
            )
        );
        Assertions.assertTrue(
            this.tool.isReachable(
                new KVector2i(2, 0),
                new KVector2i(2, 2)
            )
        );
        Assertions.assertTrue(
            this.tool.isReachable(
                0, 0, 0, 2
            )
        );
        Assertions.assertTrue(
            this.tool.isReachable(
                2, 0, 2, 2
            )
        );

    }

    @Test
    public void testUnreachableBecauseOfDifferentAreas() {

        Assertions.assertFalse(
            this.tool.isReachable(
                new KVector2i(0, 0),
                new KVector2i(2, 2)
            )
        );
        Assertions.assertFalse(
            this.tool.isReachable(
                new KVector2i(2, 0),
                new KVector2i(0, 2)
            )
        );
        Assertions.assertFalse(
            this.tool.isReachable(
                0, 0, 2, 2
            )
        );
        Assertions.assertFalse(
            this.tool.isReachable(
                2, 0, 0, 2
            )
        );

    }

    @Test
    public void testUnreachableBecauseOfOutOfBounds() {

        Assertions.assertFalse(
            this.tool.isReachable(new KVector2i(-1, 0), new KVector2i(0, 1))
        );
        Assertions.assertFalse(
            this.tool.isReachable(new KVector2i(3, 0), new KVector2i(0, 1))
        );
        Assertions.assertFalse(
            this.tool.isReachable(new KVector2i(0, -1), new KVector2i(0, 1))
        );
        Assertions.assertFalse(
            this.tool.isReachable(new KVector2i(0, 3), new KVector2i(0, 1))
        );

        Assertions.assertFalse(
            this.tool.isReachable(new KVector2i(0, 1), new KVector2i(-1, 0))
        );
        Assertions.assertFalse(
            this.tool.isReachable(new KVector2i(0, 1), new KVector2i(3, 0))
        );
        Assertions.assertFalse(
            this.tool.isReachable(new KVector2i(0, 1), new KVector2i(0, -1))
        );
        Assertions.assertFalse(
            this.tool.isReachable(new KVector2i(0, 1), new KVector2i(0, 3))
        );

    }

    @Test
    public void testUnreachableBecauseOfImpassableTile() {

        Assertions.assertFalse(
            this.tool.isReachable(new KVector2i(0, 0), new KVector2i(1, 1))
        );
        Assertions.assertFalse(
            this.tool.isReachable(0, 0, 1, 1)
        );

    }

    @Test
    public void testUnreachableByHeight() {

        KTileLayer layer = new KTileLayer(3, 3);
        KTileInfo tileInfo1 = new KTileInfo("tt", 1, true, 16, Map.of());

        layer
            .getTool()
            .placeTile(new KVector2i(0, 0), tileInfo1)
            .placeTile(0, 1, tileInfo1)
            .placeTile(0, 2, tileInfo1)
            .placeTile(1, 0, tileInfo1)
            .placeTile(1, 1, tileInfo1)
            .placeTile(1, 2, tileInfo1)
            .placeTile(2, 0, tileInfo1)
            .placeTile(2, 1, tileInfo1)
            .placeTile(2, 2, tileInfo1);

        KHeightLayer hl = new KHeightLayer(new KSize(3, 3));
        hl
            .getTool()
            .setHeight(2, 0, 2)
            .setHeight(2, 1, 2)
            .setHeight(2, 2, 2);

        var rl = new KReachabilityAreaLayer(layer, hl).getTool();

        Assertions.assertFalse(rl.isReachable(0, 0, 2, 2));
        Assertions.assertFalse(rl.isReachable(new KVector2i(0, 0), new KVector2i(2, 2)));

    }
}
