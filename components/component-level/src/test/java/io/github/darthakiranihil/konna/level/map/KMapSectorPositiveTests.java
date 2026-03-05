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

package io.github.darthakiranihil.konna.level.map;

import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.KTileInfo;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class KMapSectorPositiveTests extends KStandardTestClass {

    @Test
    public void testGetSectorSliceWithoutLinkedSector() {

        KTileInfo tileInfo = new KTileInfo(1, true, 16, Map.of());

        KMapSector sector = new KMapSector(
            KStandardTestClass.context,
            "sector_1",
            (new KTileLayer(2, 2))
                .placeTile(0, 0, tileInfo)
                .placeTile(0, 1, tileInfo)
                .placeTile(1, 0, tileInfo)
                .placeTile(1, 1, tileInfo),
            new KSectorLinkLayer(),
            new KMapEntityLayer()
        );

        KMapSectorSlice sectorSlice = sector.getSlice(0, 0);
        Assertions.assertNotNull(sectorSlice.tile());
        Assertions.assertEquals(tileInfo.getId(), sectorSlice.tile().getId());
        Assertions.assertNull(sectorSlice.sectorLink());

    }

    @Test
    public void testGetSectorSliceWithLinkedSector() {

        KTileInfo tileInfo = new KTileInfo(1, true, 16, Map.of());

        KMapSector sector2 = new KMapSector(
            KStandardTestClass.context,
            "sector_2",
            (new KTileLayer(2, 2))
                .placeTile(0, 0, tileInfo)
                .placeTile(0, 1, tileInfo)
                .placeTile(1, 0, tileInfo)
                .placeTile(1, 1, tileInfo),
            new KSectorLinkLayer(),
            new KMapEntityLayer()
        );

        KMapSector sector = new KMapSector(
            KStandardTestClass.context,
            "sector_1",
            (new KTileLayer(2, 2))
                .placeTile(0, 0, tileInfo)
                .placeTile(0, 1, tileInfo)
                .placeTile(1, 0, tileInfo)
                .placeTile(1, 1, tileInfo),
            (new KSectorLinkLayer()).link(0, 0, sector2, 1, 1),
            new KMapEntityLayer()
        );

        KMapSectorSlice sectorSlice = sector.getSlice(0, 0);
        Assertions.assertNotNull(sectorSlice.tile());
        Assertions.assertEquals(tileInfo.getId(), sectorSlice.tile().getId());
        Assertions.assertNotNull(sectorSlice.sectorLink());
        Assertions.assertEquals(sector2.name(), sectorSlice.sectorLink().linkedSector().name());
        Assertions.assertEquals(new KVector2i(1, 1), sectorSlice.sectorLink().destination());

    }
}
