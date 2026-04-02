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

package io.github.darthakiranihil.konna.level.generator.mutator.entity;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.message.KEvent;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.message.KStandardEventSystem;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.level.KLevelSector;
import io.github.darthakiranihil.konna.level.entity.KStaticEntity;
import io.github.darthakiranihil.konna.level.layer.KLevelEntityLayer;
import io.github.darthakiranihil.konna.level.layer.KPassabilityLayer;
import io.github.darthakiranihil.konna.level.layer.KPassabilityState;
import io.github.darthakiranihil.konna.level.layer.tool.KLevelEntityLayerTool;
import io.github.darthakiranihil.konna.level.layer.tool.KPassabilityLayerTool;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class KRandomlyPlaceStaticEntitiesNodeTests extends KStandardTestClass {

    @Test
    public void testProcessSuccess() {

        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));

        KPassabilityLayer passabilityLayer = new KPassabilityLayer(KSize.squared(5));
        KPassabilityLayerTool passabilityLayerTool = passabilityLayer.getTool();

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                passabilityLayerTool.setState(x, y, KPassabilityState.PASSABLE);
            }
        }

        for (int i = 0; i < 5; i++) {
            passabilityLayerTool.setState(i, 0, KPassabilityState.IMPASSABLE);
            passabilityLayerTool.setState(i, 4, KPassabilityState.IMPASSABLE);
            passabilityLayerTool.setState(0, i, KPassabilityState.IMPASSABLE);
            passabilityLayerTool.setState(4, i, KPassabilityState.IMPASSABLE);
        }

        KLevelEntityLayer entityLayer = new KLevelEntityLayer();

        KUniversalMap params = new KUniversalMap();
        params.put("amount", 2);
        params.put("entity_layer", entityLayer);
        params.put("passability_layer", passabilityLayer);
        params.put("name", "rnd_e");
        params.put("descriptor", "rnd_e");

        var node = new KRandomlyPlaceStaticEntitiesNode(es);
        KUniversalMap result = node.process(params, new Random(42069L));

        Assertions.assertNotNull(result.getSafe("entity_layer", KLevelEntityLayer.class));
        Assertions.assertNotNull(result.getSafe("passability_layer", KPassabilityLayer.class));

        KLevelEntityLayer layer = result.get("entity_layer", KLevelEntityLayer.class);
        KLevelEntityLayerTool tool = layer.getTool();

        var created = tool.findEntitiesWithDescriptor("rnd_e");
        Assertions.assertEquals(2, created.size());

        for (var e: created) {
            Assertions.assertEquals("rnd_e", e.name());
            Assertions.assertInstanceOf(KStaticEntity.class, e);
        }

    }

    @Test
    public void testProcessWithoutPassable() {

        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));

        KPassabilityLayer passabilityLayer = new KPassabilityLayer(KSize.squared(5));
        KPassabilityLayerTool passabilityLayerTool = passabilityLayer.getTool();

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                passabilityLayerTool.setState(x, y, KPassabilityState.IMPASSABLE);
            }
        }

        KLevelEntityLayer entityLayer = new KLevelEntityLayer();

        KUniversalMap params = new KUniversalMap();
        params.put("amount", 2);
        params.put("entity_layer", entityLayer);
        params.put("passability_layer", passabilityLayer);
        params.put("name", "rnd_e");
        params.put("descriptor", "rnd_e");

        var node = new KRandomlyPlaceStaticEntitiesNode(es);
        KUniversalMap result = node.process(params, new Random(42069L));

        Assertions.assertNotNull(result.getSafe("entity_layer", KLevelEntityLayer.class));
        Assertions.assertNotNull(result.getSafe("passability_layer", KPassabilityLayer.class));

        KLevelEntityLayer layer = result.get("entity_layer", KLevelEntityLayer.class);
        KLevelEntityLayerTool tool = layer.getTool();

        var created = tool.findEntitiesWithDescriptor("rnd_e");
        Assertions.assertEquals(0, created.size());

    }
}
