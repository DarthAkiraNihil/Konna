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

import io.github.darthakiranihil.konna.core.message.KEvent;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.message.KStandardEventSystem;
import io.github.darthakiranihil.konna.level.KTileInfo;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class KLocationPositiveTests extends KStandardTestClass {

    @Test
    public void testGetSectorSuccess() {

        KTileInfo tileInfo = new KTileInfo(1, true, 16, Map.of());
        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KMapSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KMapSector.EventData>("entityLeftSector"));

        KMapSector sector = new KMapSector(
            es,
            "sector_1",
            (new KTileLayer(2, 2))
                .placeTile(0, 0, tileInfo)
                .placeTile(0, 1, tileInfo)
                .placeTile(1, 0, tileInfo)
                .placeTile(1, 1, tileInfo),
            new KSectorLinkLayer(),
            new KMapEntityLayer()
        );

        KLocation location = new KLocation("loc1", List.of(sector));

        String[] sectors = location.getSectorNames();
        Assertions.assertEquals(1, sectors.length);
        Assertions.assertEquals("sector_1", sectors[0]);

        KMapSector sector1 = location.getSector("sector_1");
        Assertions.assertEquals(sector.name(), sector1.name());

    }
}
