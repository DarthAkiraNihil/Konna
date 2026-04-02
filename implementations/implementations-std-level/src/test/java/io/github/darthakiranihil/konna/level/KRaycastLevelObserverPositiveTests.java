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
import io.github.darthakiranihil.konna.level.entity.KStaticEntity;
import io.github.darthakiranihil.konna.level.layer.*;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class KRaycastLevelObserverPositiveTests extends KStandardTestClass {

    @Test
    public void testObservePointRegular() {

        KLevelObserver observer = new KRaycastLevelObserver();
        KTileInfo tileInfo = new KTileInfo("tt", 1, true, 0, Map.of());
        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));
        KTileLayer tileLayer = new KTileLayer(11, 11);
        var tool = tileLayer.getTool();
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                tool.placeTile(i, j, tileInfo);
            }
        }

        KLevelEntityLayer entityLayer = new KLevelEntityLayer();
        entityLayer.getTool().placeEntity(5,4, new KStaticEntity(es, "ste", "ste"));

        KLevelSector sector = new KLevelSector(
            es,
            "sector_1",
            tileLayer,
            new KHeightLayer(new KSize(11, 11)),
            new KSectorLinkLayer(),
            entityLayer,
            new KLevelTransitionLayer()
        );

        KLevel level = new KLevel("loc1", List.of(sector));

        KFov fov = observer.observePoint(level,"sector_1", 5, 5, 3);
        KFov fov2 = observer.observePoint(level, "sector_1", new KVector2i(5, 5), 3);
        Assertions.assertEquals(21, fov.getObservedSlices().size());
        Assertions.assertEquals(fov.getObservedSlices().size(), fov2.getObservedSlices().size());
        Assertions.assertEquals(1, fov.getEntitiesWithDescriptor("ste").size());
        var positions = fov.getObservedSlices().stream().map(KLevelSectorSlice::position).toList();
        Assertions.assertTrue(
            positions.containsAll(
                List.of(
                    new KVector2i(3, 4),
                    new KVector2i(3, 5),
                    new KVector2i(3, 6),
                    new KVector2i(4, 3),
                    new KVector2i(4, 4),
                    new KVector2i(4, 5),
                    new KVector2i(4, 6),
                    new KVector2i(4, 7),
                    new KVector2i(5, 3),
                    new KVector2i(5, 4),
                    new KVector2i(5, 5),
                    new KVector2i(5, 6),
                    new KVector2i(5, 7),
                    new KVector2i(6, 3),
                    new KVector2i(6, 4),
                    new KVector2i(6, 5),
                    new KVector2i(6, 6),
                    new KVector2i(6, 7),
                    new KVector2i(7, 4),
                    new KVector2i(7, 5),
                    new KVector2i(7, 6)
                )
            )
        );

    }

    @Test
    public void testObserveWithSectorLink() {

        KLevelObserver observer = new KRaycastLevelObserver();
        KTileInfo tileInfo = new KTileInfo("tt", 1, true, 0, Map.of());
        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));
        KTileLayer tileLayer = new KTileLayer(11, 11);
        KTileLayer tileLayer2 = new KTileLayer(11, 11);
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                tileLayer.getTool().placeTile(i, j, tileInfo);
                tileLayer2.getTool().placeTile(i, j, tileInfo);
            }
        }

        KSectorLinkLayer sl1 = new KSectorLinkLayer();
        KSectorLinkLayer sl2 = new KSectorLinkLayer();

        KLevelSector sector2 = new KLevelSector(
            es,
            "sector_2",
            tileLayer2,
            new KHeightLayer(new KSize(11, 11)),
            sl2,
            new KLevelEntityLayer(),
            new KLevelTransitionLayer()
        );
        KLevelSector sector = new KLevelSector(
            es,
            "sector_1",
            tileLayer,
            new KHeightLayer(new KSize(11, 11)),
            sl1,
            new KLevelEntityLayer(),
            new KLevelTransitionLayer()
        );

        sl1.getTool().link(0, 5, sector2, 10, 5);
        sl2.getTool().link(10, 5, sector, 0, 5);

        KLevel level = new KLevel("loc1", List.of(sector, sector2));

        KFov fov = observer.observePoint(level,"sector_1", 0, 5, 3);
        Assertions.assertEquals(26, fov.getObservedSlices().size());
        var positions = fov.getObservedSlices().stream().map(KLevelSectorSlice::position).toList();
        Assertions.assertTrue(
            positions.containsAll(
                List.of(
                    new KVector2i(0, 6),
                    new KVector2i(10, 7),
                    new KVector2i(9, 3),
                    new KVector2i(10, 3),
                    new KVector2i(8, 4),
                    new KVector2i(2, 6),
                    new KVector2i(8, 5),
                    new KVector2i(9, 5),
                    new KVector2i(10, 4),
                    new KVector2i(10, 5),
                    new KVector2i(0, 4),
                    new KVector2i(8, 3),
                    new KVector2i(8, 6),
                    new KVector2i(2, 5),
                    new KVector2i(9, 4),
                    new KVector2i(1, 4),
                    new KVector2i(1, 3),
                    new KVector2i(2, 4),
                    new KVector2i(10, 6),
                    new KVector2i(0, 3),
                    new KVector2i(0, 7),
                    new KVector2i(1, 5),
                    new KVector2i(1, 7),
                    new KVector2i(1, 6),
                    new KVector2i(9, 6),
                    new KVector2i(0, 5)
                )
            )
        );

    }

    @Test
    public void testObserveWithHeightDecreasing() {

        KLevelObserver observer = new KRaycastLevelObserver();
        KTileInfo tileInfo = new KTileInfo("tt", 1, true, 0, Map.of());
        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));
        KTileLayer tileLayer = new KTileLayer(11, 11);
        KHeightLayer heightLayer = new KHeightLayer(tileLayer.getSize());

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                tileLayer.getTool().placeTile(i, j, tileInfo);
            }
        }

        for (int i = 0; i < 11; i++) {
            for (int j = 8; j < 11; j++) {
                heightLayer.getTool().setHeight(i, j, 2);
            }
        }

        KLevelSector sector = new KLevelSector(
            es,
            "sector_1",
            tileLayer,
            heightLayer,
            new KSectorLinkLayer(),
            new KLevelEntityLayer(),
            new KLevelTransitionLayer()
        );

        KLevel level = new KLevel("loc1", List.of(sector));

        KFov fov = observer.observePoint(level,"sector_1", 5, 8, 3);
        Assertions.assertEquals(32, fov.getObservedSlices().size());
        var positions = fov.getObservedSlices().stream().map(KLevelSectorSlice::position).toList();
        Assertions.assertTrue(
            positions.containsAll(
                List.of(
                    new KVector2i(7, 5),
                    new KVector2i(7, 6),
                    new KVector2i(6, 6),
                    new KVector2i(4, 5),
                    new KVector2i(6, 7),
                    new KVector2i(6, 8),
                    new KVector2i(4, 6),
                    new KVector2i(6, 9),
                    new KVector2i(4, 7),
                    new KVector2i(6, 10),
                    new KVector2i(4, 8),
                    new KVector2i(2, 6),
                    new KVector2i(4, 9),
                    new KVector2i(2, 7),
                    new KVector2i(4, 10),
                    new KVector2i(8, 6),
                    new KVector2i(6, 5),
                    new KVector2i(8, 7),
                    new KVector2i(5, 5),
                    new KVector2i(7, 7),
                    new KVector2i(7, 8),
                    new KVector2i(5, 6),
                    new KVector2i(7, 9),
                    new KVector2i(3, 5),
                    new KVector2i(5, 7),
                    new KVector2i(5, 8),
                    new KVector2i(3, 6),
                    new KVector2i(5, 9),
                    new KVector2i(3, 7),
                    new KVector2i(5, 10),
                    new KVector2i(3, 8),
                    new KVector2i(3, 9)
                )
            )
        );

    }

    @Test
    public void testObserveWithHeightIncreasing() {

        KLevelObserver observer = new KRaycastLevelObserver();
        KTileInfo tileInfo = new KTileInfo("tt", 1, true, 0, Map.of());
        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));
        KTileLayer tileLayer = new KTileLayer(11, 11);
        KHeightLayer heightLayer = new KHeightLayer(tileLayer.getSize());

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                tileLayer.getTool().placeTile(i, j, tileInfo);
            }
        }

        for (int i = 0; i < 11; i++) {
            for (int j = 8; j < 11; j++) {
                heightLayer.getTool().setHeight(i, j, 2);
            }
        }

        KLevelSector sector = new KLevelSector(
            es,
            "sector_1",
            tileLayer,
            heightLayer,
            new KSectorLinkLayer(),
            new KLevelEntityLayer(),
            new KLevelTransitionLayer()
        );

        KLevel level = new KLevel("loc1", List.of(sector));

        KFov fov = observer.observePoint(level,"sector_1", 5, 7, 3);
        Assertions.assertEquals(18, fov.getObservedSlices().size());
        var positions = fov.getObservedSlices().stream().map(KLevelSectorSlice::position).toList();
        Assertions.assertTrue(
            positions.containsAll(
                List.of(
                    new KVector2i(6, 7),
                    new KVector2i(7, 8),
                    new KVector2i(7, 7),
                    new KVector2i(6, 5),
                    new KVector2i(5, 5),
                    new KVector2i(7, 6),
                    new KVector2i(6, 6),
                    new KVector2i(6, 8),
                    new KVector2i(4, 6),
                    new KVector2i(4, 7),
                    new KVector2i(3, 6),
                    new KVector2i(4, 8),
                    new KVector2i(3, 7),
                    new KVector2i(3, 8),
                    new KVector2i(5, 7),
                    new KVector2i(5, 8),
                    new KVector2i(5, 6),
                    new KVector2i(4, 5)
                )
            )
        );

    }
    
}
