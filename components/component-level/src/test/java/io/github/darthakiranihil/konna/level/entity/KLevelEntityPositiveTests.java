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

package io.github.darthakiranihil.konna.level.entity;

import io.github.darthakiranihil.konna.core.message.KEvent;
import io.github.darthakiranihil.konna.core.message.KStandardEventSystem;
import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.core.util.KThreadUtils;
import io.github.darthakiranihil.konna.level.KLevelSector;
import io.github.darthakiranihil.konna.level.KTileInfo;
import io.github.darthakiranihil.konna.level.layer.*;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class KLevelEntityPositiveTests extends KStandardTestClass {

    @Test
    public void testMoveStaticEntity() {

        KTileInfo tileInfo = new KTileInfo(1, true, 16, Map.of());

        KStandardEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));
        es.startPolling();

        KLevelEntityLayer mel = new KLevelEntityLayer();
        KTileLayer tileLayer = new KTileLayer(2, 2);
        tileLayer
            .getTool()
            .placeTile(0, 0, tileInfo)
            .placeTile(0, 1, tileInfo)
            .placeTile(1, 0, tileInfo)
            .placeTile(1, 1, tileInfo);
        KLevelSector sector = new KLevelSector(
            es,
            "sector_1",
            tileLayer,
            new KHeightLayer(new KSize(2, 2)),
            new KSectorLinkLayer(),
            mel,
            new KLevelTransitionLayer()
        );
        KStaticEntity staticEntity = new KStaticEntity(es, "se1", "se1", new KVector2i(0, 0), sector);
        mel.getTool().placeEntity(0, 0, staticEntity);

        var previousPos = staticEntity.getPosition();
        staticEntity.move();

        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(previousPos, staticEntity.getPosition());
        Assertions.assertEquals(1, mel.getOnPosition(0, 0).size());
        es.stopPolling();
    }

    @Test
    public void testMoveControllableInsideSector() {
        KTileInfo tileInfo = new KTileInfo(1, true, 16, Map.of());

        KStandardEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));
        es.startPolling();

        KLevelEntityLayer mel = new KLevelEntityLayer();
        KTileLayer tileLayer = new KTileLayer(2, 2);
        tileLayer
            .getTool()
            .placeTile(0, 0, tileInfo)
            .placeTile(0, 1, tileInfo)
            .placeTile(1, 0, tileInfo)
            .placeTile(1, 1, tileInfo);

        KLevelSector sector = new KLevelSector(
            es,
            "sector_1",
            tileLayer,
            new KHeightLayer(new KSize(2, 2)),
            new KSectorLinkLayer(),
            mel,
            new KLevelTransitionLayer()
        );
        KControllableEntity controllableEntity = new KControllableEntity(es, "se1", "se1", new KVector2i(0, 0), sector);
        mel.getTool().placeEntity(0, 0, controllableEntity);

        controllableEntity.setNextMoveDirection(new KVector2i(1, 0));
        controllableEntity.move();

        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(
            new KPair<>(new KVector2i(1, 0), sector),
            controllableEntity.getPosition()
        );

        Assertions.assertEquals(0, mel.getOnPosition(0, 0).size());
        Assertions.assertEquals(1, mel.getOnPosition(1, 0).size());
        es.stopPolling();
    }

    @Test
    public void testMoveControllableInsideSectorThroughImpassable() {
        KTileInfo tileInfo = new KTileInfo(1, true, 16, Map.of());
        KTileInfo impassableTileInfo = new KTileInfo(2, false, 16, Map.of());

        KStandardEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));
        es.startPolling();

        KLevelEntityLayer mel = new KLevelEntityLayer();
        KTileLayer tileLayer = new KTileLayer(2, 2);
        tileLayer
            .getTool()
            .placeTile(0, 0, tileInfo)
            .placeTile(0, 1, tileInfo)
            .placeTile(1, 0, impassableTileInfo)
            .placeTile(1, 1, tileInfo);

        KLevelSector sector = new KLevelSector(
            es,
            "sector_1",
            tileLayer,
            new KHeightLayer(new KSize(2, 2)),
            new KSectorLinkLayer(),
            mel,
            new KLevelTransitionLayer()
        );
        KControllableEntity controllableEntity = new KControllableEntity(es, "se1", "se1", new KVector2i(0, 0), sector);
        mel.getTool().placeEntity(0, 0, controllableEntity);

        var previousPosition = controllableEntity.getPosition();
        controllableEntity.setNextMoveDirection(new KVector2i(1, 0));
        controllableEntity.move();
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(
            previousPosition,
            controllableEntity.getPosition()
        );
        Assertions.assertEquals(1, mel.getOnPosition(0, 0).size());
        Assertions.assertEquals(0, mel.getOnPosition(1, 0).size());
        es.stopPolling();
    }

    @Test
    public void testMoveControllableWithoutMoving() {

        KTileInfo tileInfo = new KTileInfo(1, true, 16, Map.of());

        KStandardEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));
        es.startPolling();

        KLevelEntityLayer mel = new KLevelEntityLayer();
        KTileLayer tileLayer = new KTileLayer(2, 2);
        tileLayer
            .getTool()
            .placeTile(0, 0, tileInfo)
            .placeTile(0, 1, tileInfo)
            .placeTile(1, 0, tileInfo)
            .placeTile(1, 1, tileInfo);
        KLevelSector sector = new KLevelSector(
            es,
            "sector_1",
            tileLayer,
            new KHeightLayer(new KSize(2, 2)),
            new KSectorLinkLayer(),
            mel,
            new KLevelTransitionLayer()
        );
        KControllableEntity controllableEntity = new KControllableEntity(es, "se1", "se1", new KVector2i(0, 0), sector);
        mel.getTool().placeEntity(0, 0, controllableEntity);

        var previousPosition = controllableEntity.getPosition();
        controllableEntity.move();
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(
            previousPosition,
            controllableEntity.getPosition()
        );

        Assertions.assertEquals(1, mel.getOnPosition(0, 0).size());
        es.stopPolling();
    }

    @Test
    public void testMoveControllableTowardsAnotherSector() {
        KTileInfo tileInfo = new KTileInfo(1, true, 16, Map.of());

        KStandardEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));
        es.startPolling();

        KLevelEntityLayer mel = new KLevelEntityLayer();
        KLevelEntityLayer mel2 = new KLevelEntityLayer();
        KTileLayer tileLayer2 = new KTileLayer(2, 2);
        tileLayer2
            .getTool()
            .placeTile(0, 0, tileInfo)
            .placeTile(0, 1, tileInfo)
            .placeTile(1, 0, tileInfo)
            .placeTile(1, 1, tileInfo);

        KTileLayer tileLayer = new KTileLayer(2, 2);
        tileLayer
            .getTool()
            .placeTile(0, 0, tileInfo)
            .placeTile(0, 1, tileInfo)
            .placeTile(1, 0, tileInfo)
            .placeTile(1, 1, tileInfo);

        KLevelSector sector2 = new KLevelSector(
            es,
            "sector_2",
            tileLayer2,
            new KHeightLayer(new KSize(2, 2)),
            new KSectorLinkLayer(),
            mel2,
            new KLevelTransitionLayer()
        );

        KSectorLinkLayer sll = new KSectorLinkLayer();
        sll.getTool().link(0, 0, sector2, 1, 1);

        KLevelSector sector = new KLevelSector(
            es,
            "sector_1",
            tileLayer,
            new KHeightLayer(new KSize(2, 2)),
            sll,
            mel,
            new KLevelTransitionLayer()
        );

        KControllableEntity controllableEntity = new KControllableEntity(es, "se1", "se1", new KVector2i(0, 0), sector);
        mel.getTool().placeEntity(0, 0, controllableEntity);

        controllableEntity.setNextMoveDirection(new KVector2i(-1, 0));
        controllableEntity.move();
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(
            new KPair<>(new KVector2i(0, 1), sector2),
            controllableEntity.getPosition()
        );

        Assertions.assertEquals(0, mel.getOnPosition(0, 0).size());
        Assertions.assertEquals(1, mel2.getOnPosition(0, 1).size());
        es.stopPolling();
    }

    @Test
    public void testMoveControllableTowardsAnotherSectorThroughImpassable() {
        KTileInfo tileInfo = new KTileInfo(1, true, 16, Map.of());
        KTileInfo impassableTileInfo = new KTileInfo(2, false, 16, Map.of());

        KStandardEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));
        es.startPolling();

        KLevelEntityLayer mel = new KLevelEntityLayer();
        KLevelEntityLayer mel2 = new KLevelEntityLayer();
        KTileLayer tileLayer = new KTileLayer(2, 2);
        tileLayer
            .getTool()
            .placeTile(0, 0, impassableTileInfo)
            .placeTile(0, 1, impassableTileInfo)
            .placeTile(1, 0, impassableTileInfo)
            .placeTile(1, 1, tileInfo);

        KLevelSector sector2 = new KLevelSector(
            es,
            "sector_2",
            tileLayer,
            new KHeightLayer(new KSize(2, 2)),
            new KSectorLinkLayer(),
            mel2,
            new KLevelTransitionLayer()
        );

        KTileLayer tileLayer2 = new KTileLayer(2, 2);
        tileLayer2
            .getTool()
            .placeTile(0, 0, tileInfo)
            .placeTile(0, 1, tileInfo)
            .placeTile(1, 0, tileInfo)
            .placeTile(1, 1, tileInfo);

        KSectorLinkLayer sll = new KSectorLinkLayer();
        sll.getTool().link(0, 0, sector2, 1, 1);
        KLevelSector sector = new KLevelSector(
            es,
            "sector_1",
            tileLayer2,
            new KHeightLayer(new KSize(2, 2)),
            sll,
            mel,
            new KLevelTransitionLayer()
        );

        KControllableEntity controllableEntity = new KControllableEntity(es, "se1", "se1", new KVector2i(0, 0), sector);
        mel.getTool().placeEntity(0, 0, controllableEntity);

        var previousPosition = controllableEntity.getPosition();
        controllableEntity.setNextMoveDirection(new KVector2i(-1, 0));
        controllableEntity.move();
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(
            previousPosition,
            controllableEntity.getPosition()
        );

        Assertions.assertEquals(1, mel.getOnPosition(0, 0).size());
        Assertions.assertEquals(0, mel2.getOnPosition(0, 1).size());
        es.stopPolling();
    }

    @Test
    public void testMoveControllableTowardsAnotherSectorButThereIsNot() {

        KTileInfo tileInfo = new KTileInfo(1, true, 16, Map.of());

        KStandardEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));
        es.startPolling();

        KLevelEntityLayer mel = new KLevelEntityLayer();
        KTileLayer tileLayer = new KTileLayer(2, 2);
        tileLayer
            .getTool()
            .placeTile(0, 0, tileInfo)
            .placeTile(0, 1, tileInfo)
            .placeTile(1, 0, tileInfo)
            .placeTile(1, 1, tileInfo);
        KLevelSector sector = new KLevelSector(
            es,
            "sector_1",
            tileLayer,
            new KHeightLayer(new KSize(2, 2)),
            new KSectorLinkLayer(),
            mel,
            new KLevelTransitionLayer()
        );
        KControllableEntity controllableEntity = new KControllableEntity(es, "se1", "se1", new KVector2i(0, 0), sector);
        mel.getTool().placeEntity(0, 0, controllableEntity);

        var previousPosition = controllableEntity.getPosition();
        controllableEntity.setNextMoveDirection(new KVector2i(-1, 0));
        controllableEntity.move();
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(
            previousPosition,
            controllableEntity.getPosition()
        );

        Assertions.assertEquals(1, mel.getOnPosition(0, 0).size());
        es.stopPolling();
    }

    @Test
    public void testMoveControllableEntityButHeightDiffIsTooBig() {

        KTileInfo tileInfo = new KTileInfo(1, true, 16, Map.of());

        KStandardEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));
        es.startPolling();

        KLevelEntityLayer mel = new KLevelEntityLayer();
        KTileLayer tileLayer = new KTileLayer(2, 2);
        tileLayer
            .getTool()
            .placeTile(0, 0, tileInfo)
            .placeTile(0, 1, tileInfo)
            .placeTile(1, 0, tileInfo)
            .placeTile(1, 1, tileInfo);

        KHeightLayer hl = new KHeightLayer(new KSize(2, 2));
        hl
            .getTool()
            .setHeight(0, 1, 2)
            .setHeight(1, 1, 2);
        KLevelSector sector = new KLevelSector(
            es,
            "sector_1",
            tileLayer,
            hl,
            new KSectorLinkLayer(),
            mel,
            new KLevelTransitionLayer()
        );
        KControllableEntity controllableEntity = new KControllableEntity(es, "se1", "se1", new KVector2i(0, 0), sector);
        mel.getTool().placeEntity(0, 0, controllableEntity);

        var previousPosition = controllableEntity.getPosition();
        controllableEntity.setNextMoveDirection(new KVector2i(0, 1));
        controllableEntity.move();
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(
            previousPosition,
            controllableEntity.getPosition()
        );

        Assertions.assertEquals(1, mel.getOnPosition(0, 0).size());
        es.stopPolling();

    }

    @Test
    public void testControllableEntityToAnotherSectorButHeightDiffIsTooBig() {

        KTileInfo tileInfo = new KTileInfo(1, true, 16, Map.of());

        KStandardEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));
        es.startPolling();

        KLevelEntityLayer mel = new KLevelEntityLayer();
        KLevelEntityLayer mel2 = new KLevelEntityLayer();
        KTileLayer tileLayer2 = new KTileLayer(2, 2);
        tileLayer2
            .getTool()
            .placeTile(0, 0, tileInfo)
            .placeTile(0, 1, tileInfo)
            .placeTile(1, 0, tileInfo)
            .placeTile(1, 1, tileInfo);

        KHeightLayer hl = new KHeightLayer(new KSize(2, 2));
        hl
            .getTool()
            .setHeight(0, 0, 2)
            .setHeight(1, 0, 2)
            .setHeight(0, 1, 2)
            .setHeight(1, 1, 2);
        KLevelSector sector2 = new KLevelSector(
            es,
            "sector_2",
            tileLayer2,
            hl,
            new KSectorLinkLayer(),
            mel2,
            new KLevelTransitionLayer()
        );

        KTileLayer tileLayer = new KTileLayer(2, 2);
        tileLayer
            .getTool()
            .placeTile(0, 0, tileInfo)
            .placeTile(0, 1, tileInfo)
            .placeTile(1, 0, tileInfo)
            .placeTile(1, 1, tileInfo);

        KSectorLinkLayer sll = new KSectorLinkLayer();
        sll.getTool().link(0, 0, sector2, 1, 1);
        KLevelSector sector = new KLevelSector(
            es,
            "sector_1",
            tileLayer,
            new KHeightLayer(new KSize(2, 2)),
            sll,
            mel,
            new KLevelTransitionLayer()
        );

        KControllableEntity controllableEntity = new KControllableEntity(es, "se1", "se1", new KVector2i(0, 0), sector);
        mel.getTool().placeEntity(0, 0, controllableEntity);

        var previousPosition = controllableEntity.getPosition();
        controllableEntity.setNextMoveDirection(new KVector2i(-1, 0));
        controllableEntity.move();
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(
            previousPosition,
            controllableEntity.getPosition()
        );

        Assertions.assertEquals(1, mel.getOnPosition(0, 0).size());
        Assertions.assertEquals(0, mel2.getOnPosition(0, 1).size());
        es.stopPolling();

    }

    @Test
    public void testMoveAutonomousWithoutController() {

        KTileInfo tileInfo = new KTileInfo(1, true, 16, Map.of());

        KStandardEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));
        es.startPolling();

        KLevelEntityLayer mel = new KLevelEntityLayer();
        KLevelEntityLayer mel2 = new KLevelEntityLayer();
        KTileLayer tileLayer2 = new KTileLayer(2, 2);
        tileLayer2
            .getTool()
            .placeTile(0, 0, tileInfo)
            .placeTile(0, 1, tileInfo)
            .placeTile(1, 0, tileInfo)
            .placeTile(1, 1, tileInfo);
        KHeightLayer hl = new KHeightLayer(2, 2);
        hl
            .getTool()
            .setHeight(0, 0, 2)
            .setHeight(1, 0, 2)
            .setHeight(0, 1, 2)
            .setHeight(1, 1, 2);

        KLevelSector sector2 = new KLevelSector(
            es,
            "sector_2",
            tileLayer2,
            hl,
            new KSectorLinkLayer(),
            mel2,
            new KLevelTransitionLayer()
        );

        KSectorLinkLayer sll = new KSectorLinkLayer();
        sll.getTool().link(0, 0, sector2, 1, 1);
        KTileLayer tileLayer = new KTileLayer(2, 2);
        tileLayer
            .getTool()
            .placeTile(0, 0, tileInfo)
            .placeTile(0, 1, tileInfo)
            .placeTile(1, 0, tileInfo)
            .placeTile(1, 1, tileInfo);
        KLevelSector sector = new KLevelSector(
            es,
            "sector_1",
            tileLayer,
            new KHeightLayer(new KSize(2, 2)),
            sll,
            mel,
            new KLevelTransitionLayer()
        );

        KAutonomousEntity auto = new KAutonomousEntity(es, "se1", "se1", new KVector2i(0, 0), sector);
        mel.getTool().placeEntity(0, 0, auto);
        auto.move();
        Assertions.assertEquals(new KPair<>(KVector2i.ZERO, sector), auto.getPosition());

    }
}
