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

package io.github.darthakiranihil.konna.level.generator.special;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.io.KAssetDefinition;
import io.github.darthakiranihil.konna.core.io.KMapAssetDefinition;
import io.github.darthakiranihil.konna.core.message.KEvent;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.message.KStandardEventSystem;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.KLevel;
import io.github.darthakiranihil.konna.level.KLevelSector;
import io.github.darthakiranihil.konna.level.entity.KAutonomousEntity;
import io.github.darthakiranihil.konna.level.entity.KAutonomousEntityController;
import io.github.darthakiranihil.konna.level.layer.*;
import io.github.darthakiranihil.konna.level.layer.tool.KLevelEntityLayerTool;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Random;

public class KFinalizeLevelNodeTests extends KStandardTestClass {

    @Test
    public void testProcessSuccess() {

        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));

        KTileLayer tileLayer = new KTileLayer(1, 1);
        KHeightLayer heightLayer = new KHeightLayer(1, 1);
        KSectorLinkLayer sectorLinkLayer = new KSectorLinkLayer();
        KLevelEntityLayer entityLayer = new KLevelEntityLayer();
        KLevelTransitionLayer levelTransitionLayer = new KLevelTransitionLayer();

        KLevelEntityLayerTool tool = entityLayer.getTool();
        KAutonomousEntity entity = new KAutonomousEntity(es, "AE", "AE");
        entity.setController(new Controller("COCO", new KMapAssetDefinition()));
        tool.placeEntity(0, 0, entity);
        tool.placeEntity(0, 0, new KAutonomousEntity(es, "abab", "abab"));

        KLevelSector sector = new KLevelSector(
            es,
            "SASA LELE",
            tileLayer,
            heightLayer,
            sectorLinkLayer,
            entityLayer,
            levelTransitionLayer
        );

        KLevel level = new KLevel(
            "You stupid? It's SALE SALE!",
            Collections.singletonList(sector)
        );

        KUniversalMap params = new KUniversalMap();
        params.put("level", level);

        var node = new KFinalizeLevelNode();
        KUniversalMap result = node.process(params, new Random(42069L));
        Assertions.assertNotNull(result.get("level", KLevel.class));
        Assertions.assertEquals(level, result.get("level", KLevel.class));
        Assertions.assertDoesNotThrow(entity::move);


    }

    private static final class Controller extends KAutonomousEntityController {

        public Controller(
            String qualifier,
            KAssetDefinition params
        ) {
            super(qualifier, params);
        }

        @Override
        public @NonNull KVector2i getNextMoveDirection() {
            this.getAssignedEntity();
            this.getLevel();
            return KVector2i.ZERO;
        }
    }
}
