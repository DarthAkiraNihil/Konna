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

package io.github.darthakiranihil.konna.level.asset;

import io.github.darthakiranihil.konna.core.message.KEvent;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.message.KStandardEventSystem;
import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.core.struct.graph.KIntWeightedGraph;
import io.github.darthakiranihil.konna.core.util.KHashMapBasedCache;
import io.github.darthakiranihil.konna.core.util.KReflectionUtils;
import io.github.darthakiranihil.konna.level.entity.*;
import io.github.darthakiranihil.konna.level.impl.FalseValidatedController;
import io.github.darthakiranihil.konna.level.impl.TestController;
import io.github.darthakiranihil.konna.level.impl.TestControllerWithoutValidator;
import io.github.darthakiranihil.konna.level.KLevel;
import io.github.darthakiranihil.konna.level.KLevelSector;
import io.github.darthakiranihil.konna.level.KLevelSectorSlice;
import io.github.darthakiranihil.konna.level.layer.KTransitionedLevelType;
import io.github.darthakiranihil.konna.level.layer.tool.KLevelTransitionLayerTool;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("ExtractMethodRecommender")
public class KLevelMetadataCollectionPositiveTests extends KAssetCollectionTestClass {

    @Test
    public void testLoadValidLevel() {

        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));

        KLevelMetadataCollection levelCollection = new KLevelMetadataCollection(
            this.assetLoader,
            es,
            new KTileCollection(
                this.assetLoader,
                new KHashMapBasedCache(),
                new KTilePropertyCollection(this.assetLoader, KAssetCollectionTestClass.context)
            ),
            KStandardTestClass.context
        );

        KLevel loaded = levelCollection.getAsset("valid");
        Assertions.assertArrayEquals(
            new String[] {
                "mf2", "mf1"
            },
            loaded.getSectorNames()
        );

        KLevelSector mf1 = loaded.getSector("mf1");
        KLevelSector mf2 = loaded.getSector("mf2");

        KLevelSectorSlice sl1 = mf1.getSlice(0, 0);
        KLevelSectorSlice sl2 = mf2.getSlice(1, 1);

        Assertions.assertNotNull(sl1.sectorLink());
        Assertions.assertNotNull(sl2.sectorLink());

        Assertions.assertNotNull(sl1.tile());
        Assertions.assertNotNull(sl2.tile());

        Assertions.assertEquals(mf2, sl1.sectorLink().linkedSector());
        Assertions.assertEquals(mf1, sl2.sectorLink().linkedSector());

        Assertions.assertEquals(1, sl1.tile().getId());
        Assertions.assertEquals(1, sl2.tile().getId());

        Assertions.assertEquals(2, sl1.entities().size());
        var c1 = sl1
            .entities()
            .stream()
            .filter(x -> x.getDescriptor().equals("c1") && KControllableEntity.class.isAssignableFrom(x.getClass()))
            .findAny();

        var s1 = sl1
            .entities()
            .stream()
            .filter(x -> x.getDescriptor().equals("s1") && KStaticEntity.class.isAssignableFrom(x.getClass()))
            .findAny();

        Assertions.assertTrue(c1.isPresent());
        Assertions.assertTrue(s1.isPresent());

        loaded.unload();

        Assertions.assertEquals(new KPair<>(new KVector2i(0, 0), mf1), c1.get().getPosition());
        Assertions.assertEquals(new KPair<>(new KVector2i(0, 0), mf1), s1.get().getPosition());

    }

    @Test
    @SuppressWarnings("unchecked")
    public void testLoadAndCheckConnectivityGraph() {

        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));

        KLevelMetadataCollection levelCollection = new KLevelMetadataCollection(
            this.assetLoader,
            es,
            new KTileCollection(
                this.assetLoader,
                new KHashMapBasedCache(),
                new KTilePropertyCollection(this.assetLoader, KAssetCollectionTestClass.context)
            ),
            KStandardTestClass.context
        );

        KLevel loaded = levelCollection.getAsset("connectivity_graph_test");
        KIntWeightedGraph<String> graph = (KIntWeightedGraph<String>) KReflectionUtils.getFieldValue(
            KLevel.class,
            loaded,
            "sectorConnectivityGraph",
            KIntWeightedGraph.class
        );

        Assertions.assertNotNull(graph);
        Assertions.assertTrue(graph.has("mf1"));
        Assertions.assertTrue(graph.has("mf2"));
        Assertions.assertTrue(graph.has("mf3"));
        Assertions.assertTrue(graph.has("mf4"));

        var p = graph.getPath("mf1", "mf4");
        Assertions.assertEquals(4, p.size());
        Assertions.assertEquals("mf1", p.get(0));
        Assertions.assertEquals("mf2", p.get(1));
        Assertions.assertEquals("mf3", p.get(2));
        Assertions.assertEquals("mf4", p.get(3));

    }

    @Test
    public void testLoadWithValidatedAutonomousEntity() {

        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));

        KLevelMetadataCollection levelCollection = new KLevelMetadataCollection(
            this.assetLoader,
            es,
            new KTileCollection(
                this.assetLoader,
                new KHashMapBasedCache(),
                new KTilePropertyCollection(this.assetLoader, KAssetCollectionTestClass.context)
            ),
            KStandardTestClass.context
        );

        KLevel level = levelCollection.getAsset("valid_only_autonomous");
        KLevelSector sector = level.getSector("mf1");
        KLevelSectorSlice slice = sector.getSlice(0, 0);
        Assertions.assertEquals(1, slice.entities().size());

        KLevelEntity entity = slice.entities().getFirst();
        Assertions.assertTrue(KAutonomousEntity.class.isAssignableFrom(entity.getClass()));

        KAutonomousEntityController controller = KReflectionUtils.getFieldValue(
            KAutonomousEntity.class,
            entity,
            "controller",
            KAutonomousEntityController.class
        );
        Assertions.assertNotNull(controller);

        Assertions.assertEquals(TestController.class, controller.getClass());
        TestController tc = (TestController) controller;
        Assertions.assertEquals(42069, tc.getTest());

        Assertions.assertEquals(new KVector2i(1, 0), controller.getNextMoveDirection());
        entity.move();
        Assertions.assertEquals(new KPair<>(new KVector2i(1, 0), sector), entity.getPosition());

    }

    @Test
    public void testLoadFalseValidatedAutonomousEntity() {

        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));

        KLevelMetadataCollection levelCollection = new KLevelMetadataCollection(
            this.assetLoader,
            es,
            new KTileCollection(
                this.assetLoader,
                new KHashMapBasedCache(),
                new KTilePropertyCollection(this.assetLoader, KAssetCollectionTestClass.context)
            ),
            KStandardTestClass.context
        );

        KLevel level = levelCollection.getAsset("valid_validator_is_not_a_rule");
        KLevelSector sector = level.getSector("mf1");
        KLevelSectorSlice slice = sector.getSlice(0, 0);
        Assertions.assertEquals(1, slice.entities().size());

        KLevelEntity entity = slice.entities().getFirst();
        Assertions.assertTrue(KAutonomousEntity.class.isAssignableFrom(entity.getClass()));

        KAutonomousEntityController controller = KReflectionUtils.getFieldValue(
            KAutonomousEntity.class,
            entity,
            "controller",
            KAutonomousEntityController.class
        );
        Assertions.assertNotNull(controller);

        Assertions.assertEquals(FalseValidatedController.class, controller.getClass());
        Assertions.assertEquals(KVector2i.ZERO, controller.getNextMoveDirection());
        entity.move();
        Assertions.assertEquals(new KPair<>(new KVector2i(0, 0), sector), entity.getPosition());

    }

    @Test
    public void testLoadAutonomousEntityWithoutValidation() {

        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));

        KLevelMetadataCollection levelCollection = new KLevelMetadataCollection(
            this.assetLoader,
            es,
            new KTileCollection(
                this.assetLoader,
                new KHashMapBasedCache(),
                new KTilePropertyCollection(this.assetLoader, KAssetCollectionTestClass.context)
            ),
            KStandardTestClass.context
        );

        KLevel level = levelCollection.getAsset("valid_no_validator");
        KLevelSector sector = level.getSector("mf1");
        KLevelSectorSlice slice = sector.getSlice(0, 0);
        Assertions.assertEquals(1, slice.entities().size());

        KLevelEntity entity = slice.entities().getFirst();
        Assertions.assertTrue(KAutonomousEntity.class.isAssignableFrom(entity.getClass()));

        KAutonomousEntityController controller = KReflectionUtils.getFieldValue(
            KAutonomousEntity.class,
            entity,
            "controller",
            KAutonomousEntityController.class
        );
        Assertions.assertNotNull(controller);

        Assertions.assertEquals(TestControllerWithoutValidator.class, controller.getClass());
        Assertions.assertEquals(KVector2i.ZERO, controller.getNextMoveDirection());
        entity.move();
        Assertions.assertEquals(new KPair<>(new KVector2i(0, 0), sector), entity.getPosition());

    }

    @Test
    public void testLoadValidWithLevelTransition() {

        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));

        KLevelMetadataCollection levelCollection = new KLevelMetadataCollection(
            this.assetLoader,
            es,
            new KTileCollection(
                this.assetLoader,
                new KHashMapBasedCache(),
                new KTilePropertyCollection(this.assetLoader, KAssetCollectionTestClass.context)
            ),
            KStandardTestClass.context
        );

        KLevel level = levelCollection.getAsset("valid_with_level_transition");
        KLevelSector sector = level.getSector("mf1");
        KLevelSectorSlice slice = sector.getSlice(0, 0);
        Assertions.assertNotNull(slice.levelTransition());

        var transition1 = slice.levelTransition();
        Assertions.assertEquals(KVector2i.ZERO, transition1.destinationPosition());
        Assertions.assertEquals("aboba123", transition1.levelDescriptor());
        Assertions.assertEquals(KTransitionedLevelType.GENERATED, transition1.type());
        Assertions.assertEquals("abiba", transition1.destinationSector());

        KLevelSector sector2 = level.getSector("mf2");
        KLevelSectorSlice slice2 = sector2.getSlice(0, 0);
        Assertions.assertNotNull(slice2.levelTransition());

        var transition2 = slice2.levelTransition();
        Assertions.assertEquals(KVector2i.ZERO, transition2.destinationPosition());
        Assertions.assertEquals("aboba123", transition2.levelDescriptor());
        Assertions.assertEquals(KTransitionedLevelType.PREDEFINED, transition2.type());
        Assertions.assertEquals("abiba", transition2.destinationSector());

        var freakingTool = sector2.getTool(KLevelTransitionLayerTool.class);
        Assertions.assertEquals(transition2, freakingTool.getOnPosition(KVector2i.ZERO));
        Assertions.assertEquals(transition2, freakingTool.getOnPosition(0, 0));


    }
}
