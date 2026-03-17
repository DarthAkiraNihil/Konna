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

package io.github.darthakiranihil.konna.level;

import io.github.darthakiranihil.konna.core.message.KEvent;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.message.KStandardEventSystem;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.layer.KHeightLayer;
import io.github.darthakiranihil.konna.level.layer.KLevelEntityLayer;
import io.github.darthakiranihil.konna.level.layer.KSectorLinkLayer;
import io.github.darthakiranihil.konna.level.layer.KTileLayer;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class KLevelSectorPositiveTests extends KStandardTestClass {

    @Test
    public void testGetSectorSliceWithoutLinkedSector() {

        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));
        KTileInfo tileInfo = new KTileInfo(1, true, 16, Map.of());

        KLevelSector sector = new KLevelSector(
            es,
            "sector_1",
            (new KTileLayer(2, 2))
                .placeTile(0, 0, tileInfo)
                .placeTile(0, 1, tileInfo)
                .placeTile(1, 0, tileInfo)
                .placeTile(1, 1, tileInfo),
            new KHeightLayer(new KSize(2, 2)),
            new KSectorLinkLayer(),
            new KLevelEntityLayer()
        );

        KLevelSectorSlice sectorSlice = sector.getSlice(0, 0);
        Assertions.assertNotNull(sectorSlice.tile());
        Assertions.assertEquals(tileInfo.getId(), sectorSlice.tile().getId());
        Assertions.assertNull(sectorSlice.sectorLink());

    }

    @Test
    public void testGetSectorSliceWithLinkedSector() {

        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));
        KTileInfo tileInfo = new KTileInfo(1, true, 16, Map.of());

        KLevelSector sector2 = new KLevelSector(
            es,
            "sector_2",
            (new KTileLayer(2, 2))
                .placeTile(0, 0, tileInfo)
                .placeTile(0, 1, tileInfo)
                .placeTile(1, 0, tileInfo)
                .placeTile(1, 1, tileInfo),
            new KHeightLayer(new KSize(2, 2)),
            new KSectorLinkLayer(),
            new KLevelEntityLayer()
        );

        KLevelSector sector = new KLevelSector(
            es,
            "sector_1",
            (new KTileLayer(2, 2))
                .placeTile(0, 0, tileInfo)
                .placeTile(0, 1, tileInfo)
                .placeTile(1, 0, tileInfo)
                .placeTile(1, 1, tileInfo),
            new KHeightLayer(new KSize(2, 2)),
            (new KSectorLinkLayer()).link(0, 0, sector2, 1, 1),
            new KLevelEntityLayer()
        );

        KLevelSectorSlice sectorSlice = sector.getSlice(0, 0);
        Assertions.assertNotNull(sectorSlice.tile());
        Assertions.assertEquals(tileInfo.getId(), sectorSlice.tile().getId());
        Assertions.assertNotNull(sectorSlice.sectorLink());
        Assertions.assertEquals(sector2.name(), sectorSlice.sectorLink().linkedSector().name());
        Assertions.assertEquals(new KVector2i(1, 1), sectorSlice.sectorLink().destination());

    }

    @Test
    public void testReachable() {

        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));
        KTileLayer layer = new KTileLayer(3, 3);
        KTileInfo tileInfo1 = new KTileInfo(1, true, 16, Map.of());
        KTileInfo tileInfo2 = new KTileInfo(2, false, 16, Map.of());

        layer
            .placeTile(new KVector2i(0, 0), tileInfo1)
            .placeTile(0, 1, tileInfo1)
            .placeTile(0, 2, tileInfo1)
            .placeTile(1, 0, tileInfo2)
            .placeTile(1, 1, tileInfo2)
            .placeTile(1, 2, tileInfo2)
            .placeTile(2, 0, tileInfo1)
            .placeTile(2, 1, tileInfo1)
            .placeTile(2, 2, tileInfo1);

        KLevelSector sector = new KLevelSector(
            es,
            "sector",
            layer,
            new KHeightLayer(new KSize(3, 3)),
            new KSectorLinkLayer(),
            new KLevelEntityLayer()
        );

        Assertions.assertTrue(
            sector.isReachable(
                new KVector2i(0, 0),
                new KVector2i(0, 2)
            )
        );
        Assertions.assertTrue(
            sector.isReachable(
                new KVector2i(2, 0),
                new KVector2i(2, 2)
            )
        );
        Assertions.assertTrue(
            sector.isReachable(
                0, 0, 0, 2
            )
        );
        Assertions.assertTrue(
            sector.isReachable(
                2, 0, 2, 2
            )
        );

    }

    @Test
    public void testUnreachableBecauseOfDifferentAreas() {

        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));
        KTileLayer layer = new KTileLayer(3, 3);
        KTileInfo tileInfo1 = new KTileInfo(1, true, 16, Map.of());
        KTileInfo tileInfo2 = new KTileInfo(2, false, 16, Map.of());

        layer
            .placeTile(new KVector2i(0, 0), tileInfo1)
            .placeTile(0, 1, tileInfo1)
            .placeTile(0, 2, tileInfo1)
            .placeTile(1, 0, tileInfo2)
            .placeTile(1, 1, tileInfo2)
            .placeTile(1, 2, tileInfo2)
            .placeTile(2, 0, tileInfo1)
            .placeTile(2, 1, tileInfo1)
            .placeTile(2, 2, tileInfo1);

        KLevelSector sector = new KLevelSector(
            es,
            "sector",
            layer,
            new KHeightLayer(new KSize(3, 3)),
            new KSectorLinkLayer(),
            new KLevelEntityLayer()
        );

        Assertions.assertFalse(
            sector.isReachable(
                new KVector2i(0, 0),
                new KVector2i(2, 2)
            )
        );
        Assertions.assertFalse(
            sector.isReachable(
                new KVector2i(2, 0),
                new KVector2i(0, 2)
            )
        );
        Assertions.assertFalse(
            sector.isReachable(
                0, 0, 2, 2
            )
        );
        Assertions.assertFalse(
            sector.isReachable(
                2, 0, 0, 2
            )
        );

    }

    @Test
    public void testUnreachableBecauseOfOutOfBounds() {

        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));
        KTileLayer layer = new KTileLayer(3, 3);
        KTileInfo tileInfo1 = new KTileInfo(1, true, 16, Map.of());
        KTileInfo tileInfo2 = new KTileInfo(2, false, 16, Map.of());

        layer
            .placeTile(new KVector2i(0, 0), tileInfo1)
            .placeTile(0, 1, tileInfo1)
            .placeTile(0, 2, tileInfo1)
            .placeTile(1, 0, tileInfo2)
            .placeTile(1, 1, tileInfo2)
            .placeTile(1, 2, tileInfo2)
            .placeTile(2, 0, tileInfo1)
            .placeTile(2, 1, tileInfo1)
            .placeTile(2, 2, tileInfo1);

        KLevelSector sector = new KLevelSector(
            es,
            "sector",
            layer,
            new KHeightLayer(new KSize(3, 3)),
            new KSectorLinkLayer(),
            new KLevelEntityLayer()
        );

        Assertions.assertFalse(
            sector.isReachable(new KVector2i(-1, 0), new KVector2i(0, 1))
        );
        Assertions.assertFalse(
            sector.isReachable(new KVector2i(3, 0), new KVector2i(0, 1))
        );
        Assertions.assertFalse(
            sector.isReachable(new KVector2i(0, -1), new KVector2i(0, 1))
        );
        Assertions.assertFalse(
            sector.isReachable(new KVector2i(0, 3), new KVector2i(0, 1))
        );

        Assertions.assertFalse(
            sector.isReachable(new KVector2i(0, 1), new KVector2i(-1, 0))
        );
        Assertions.assertFalse(
            sector.isReachable(new KVector2i(0, 1), new KVector2i(3, 0))
        );
        Assertions.assertFalse(
            sector.isReachable(new KVector2i(0, 1), new KVector2i(0, -1))
        );
        Assertions.assertFalse(
            sector.isReachable(new KVector2i(0, 1), new KVector2i(0, 3))
        );

    }

    @Test
    public void testUnreachableBecauseOfImpassableTile() {

        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));
        KTileLayer layer = new KTileLayer(3, 3);
        KTileInfo tileInfo1 = new KTileInfo(1, true, 16, Map.of());
        KTileInfo tileInfo2 = new KTileInfo(2, false, 16, Map.of());

        layer
            .placeTile(new KVector2i(0, 0), tileInfo1)
            .placeTile(0, 1, tileInfo1)
            .placeTile(0, 2, tileInfo1)
            .placeTile(1, 0, tileInfo2)
            .placeTile(1, 1, tileInfo2)
            .placeTile(1, 2, tileInfo2)
            .placeTile(2, 0, tileInfo1)
            .placeTile(2, 1, tileInfo1)
            .placeTile(2, 2, tileInfo1);

        KLevelSector sector = new KLevelSector(
            es,
            "sector",
            layer,
            new KHeightLayer(new KSize(3, 3)),
            new KSectorLinkLayer(),
            new KLevelEntityLayer()
        );

        Assertions.assertFalse(
            sector.isReachable(new KVector2i(0, 0), new KVector2i(1, 1))
        );
        Assertions.assertFalse(
            sector.isReachable(0, 0, 1, 1)
        );

    }
}
