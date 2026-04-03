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

import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.KLevelMetadata;
import io.github.darthakiranihil.konna.level.KLevelSectorMetadata;
import io.github.darthakiranihil.konna.level.impl.FalseValidatedController;
import io.github.darthakiranihil.konna.level.impl.TestController;
import io.github.darthakiranihil.konna.level.impl.TestControllerWithoutValidator;
import io.github.darthakiranihil.konna.level.layer.KTransitionedLevelType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KLevelMetadataCollectionPositiveTests extends KAssetCollectionTestClass {

    @Test
    public void testLoadValidLevel() {

        KLevelMetadataCollection levelCollection = new KLevelMetadataCollection(this.assetLoader);

        KLevelMetadata loaded = levelCollection.getAsset("valid");
        Assertions.assertEquals("valid", loaded.name());
        Assertions.assertArrayEquals(
            new String[] {
                "mf2", "mf1"
            },
            loaded.sectorMetadata().keySet().toArray(new String[0])
        );

        KLevelSectorMetadata mf1 = loaded.sectorMetadata().get("mf1");
        KLevelSectorMetadata mf2 = loaded.sectorMetadata().get("mf2");

        var l1 = Arrays.stream(mf1.sectorLinkMetadata())
            .findFirst()
            .filter(x -> x.position().equals(KVector2i.ZERO));
        Assertions.assertTrue(l1.isPresent());

        var l2 = Arrays.stream(mf2.sectorLinkMetadata())
            .findFirst()
            .filter(x -> x.position().equals(KVector2i.ONE));
        Assertions.assertTrue(l2.isPresent());

        Assertions.assertEquals("tile_valid", mf1.tileAssetIds()[0][0]);
        Assertions.assertEquals("tile_valid", mf2.tileAssetIds()[0][0]);

        Assertions.assertEquals(mf2.name(), l1.get().destinationSectorName());
        Assertions.assertEquals(mf1.name(), l2.get().destinationSectorName());

        List<KLevelSectorMetadata.SimpleLevelEntityMetadata> entities = new ArrayList<>(2);
        entities.addAll(mf1.entities().staticEntities().get(KVector2i.ZERO));
        entities.addAll(mf1.entities().controllableEntities().get(KVector2i.ZERO));

        Assertions.assertEquals(2, entities.size());
        var c1 = entities
            .stream()
            .filter(x -> x.descriptor().equals("c1") && x.name().equals("c1"))
            .findAny();

        var s1 = entities
            .stream()
            .filter(x -> x.descriptor().equals("s1") && x.name().equals("s1"))
            .findAny();

        Assertions.assertTrue(c1.isPresent());
        Assertions.assertTrue(s1.isPresent());

    }

    @Test
    public void testLoadWithValidatedAutonomousEntity() {

        KLevelMetadataCollection levelCollection = new KLevelMetadataCollection(this.assetLoader);

        KLevelMetadata level = levelCollection.getAsset("valid_only_autonomous");
        Assertions.assertEquals("valid_only_autonomous", level.name());

        KLevelSectorMetadata sector = level.sectorMetadata().get("mf1");
        var entities = sector.entities().autonomousEntities().get(KVector2i.ZERO);
        Assertions.assertEquals(1, entities.size());

        var entity = entities.getFirst();
        Assertions.assertEquals("s1", entity.name());
        Assertions.assertEquals("s2", entity.descriptor());
        Assertions.assertEquals(TestController.class, entity.controller());

    }

    @Test
    public void testLoadFalseValidatedAutonomousEntity() {

        KLevelMetadataCollection levelCollection = new KLevelMetadataCollection(this.assetLoader);

        KLevelMetadata level = levelCollection.getAsset("valid_validator_is_not_a_rule");
        Assertions.assertEquals("valid_validator_is_not_a_rule", level.name());

        KLevelSectorMetadata sector = level.sectorMetadata().get("mf1");
        var entities = sector.entities().autonomousEntities().get(KVector2i.ZERO);
        Assertions.assertEquals(1, entities.size());
        var entity = entities.getFirst();

        Assertions.assertEquals("s1", entity.name());
        Assertions.assertEquals("s2", entity.descriptor());
        Assertions.assertEquals(FalseValidatedController.class, entity.controller());

    }

    @Test
    public void testLoadAutonomousEntityWithoutValidation() {

        KLevelMetadataCollection levelCollection = new KLevelMetadataCollection(this.assetLoader);

        KLevelMetadata level = levelCollection.getAsset("valid_no_validator");
        Assertions.assertEquals("valid_no_validator", level.name());

        KLevelSectorMetadata sector = level.sectorMetadata().get("mf1");
        var entities = sector.entities().autonomousEntities().get(KVector2i.ZERO);
        Assertions.assertEquals(1, entities.size());
        var entity = entities.getFirst();

        Assertions.assertEquals("s1", entity.name());
        Assertions.assertEquals("s2", entity.descriptor());
        Assertions.assertEquals(TestControllerWithoutValidator.class, entity.controller());

    }

    @Test
    public void testLoadValidWithLevelTransition() {

        KLevelMetadataCollection levelCollection = new KLevelMetadataCollection(this.assetLoader);

        KLevelMetadata level = levelCollection.getAsset("valid_with_level_transition");
        Assertions.assertEquals("valid_with_level_transition", level.name());

        KLevelSectorMetadata sector = level.sectorMetadata().get("mf1");
        var transition = Arrays.stream(sector.levelTransitionMetadata())
            .filter(x -> x.position().equals(KVector2i.ZERO))
            .findFirst();
        Assertions.assertTrue(transition.isPresent());

        var transition1 = transition.get();
        Assertions.assertEquals(KVector2i.ZERO, transition1.destinationPosition());
        Assertions.assertEquals("aboba123", transition1.levelDescriptor());
        Assertions.assertEquals(KTransitionedLevelType.GENERATED, transition1.levelType());
        Assertions.assertEquals("abiba", transition1.destinationSector());

        KLevelSectorMetadata sector2 = level.sectorMetadata().get("mf2");
        var transitionOfSector2 = Arrays.stream(sector2.levelTransitionMetadata())
            .filter(x -> x.position().equals(KVector2i.ZERO))
            .findFirst();
        Assertions.assertTrue(transitionOfSector2.isPresent());

        var transition2 = transitionOfSector2.get();
        Assertions.assertEquals(KVector2i.ZERO, transition2.destinationPosition());
        Assertions.assertEquals("aboba123", transition2.levelDescriptor());
        Assertions.assertEquals(KTransitionedLevelType.PREDEFINED, transition2.levelType());
        Assertions.assertEquals("abiba", transition2.destinationSector());

    }
}
