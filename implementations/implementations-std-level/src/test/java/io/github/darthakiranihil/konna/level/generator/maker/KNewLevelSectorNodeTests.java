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

package io.github.darthakiranihil.konna.level.generator.maker;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.message.KEvent;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.message.KStandardEventSystem;
import io.github.darthakiranihil.konna.level.KLevelSector;
import io.github.darthakiranihil.konna.level.KLevelSectorSlice;
import io.github.darthakiranihil.konna.level.layer.*;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class KNewLevelSectorNodeTests extends KStandardTestClass {

    @SuppressWarnings("ExtractMethodRecommender")
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

        KUniversalMap params = new KUniversalMap();
        params.put("name", "SASA LELE");
        params.put("tile_layer", tileLayer);
        params.put("height_layer", heightLayer);
        params.put("sector_link_layer", sectorLinkLayer);
        params.put("entity_layer", entityLayer);
        params.put("level_transition_layer", levelTransitionLayer);


        var node = new KNewLevelSectorNode(es);
        KUniversalMap result = node.process(params, new Random(42069L));
        Assertions.assertNotNull(result.getSafe("sector", KLevelSector.class));
        KLevelSector sector = result.get("sector", KLevelSector.class);

        Assertions.assertEquals("SASA LELE", sector.name());
        KLevelSectorSlice slice = sector.getSlice(0, 0);
        Assertions.assertNull(slice.tile());

    }
}
