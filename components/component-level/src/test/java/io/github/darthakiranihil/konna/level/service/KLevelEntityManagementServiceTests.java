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

package io.github.darthakiranihil.konna.level.service;

import io.github.darthakiranihil.konna.core.Konna;
import io.github.darthakiranihil.konna.core.KonnaBootstrapConfig;
import io.github.darthakiranihil.konna.core.app.KFrameSpawnOptions;
import io.github.darthakiranihil.konna.core.app.KStandardArgumentParser;
import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.di.KAppContainer;
import io.github.darthakiranihil.konna.core.di.KEngineModule;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisor;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisorConfig;
import io.github.darthakiranihil.konna.core.io.KMapAssetDefinition;
import io.github.darthakiranihil.konna.core.message.KMessage;
import io.github.darthakiranihil.konna.core.message.KMessageSystem;
import io.github.darthakiranihil.konna.core.object.KObjectRegistry;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.core.util.KReflectionUtils;
import io.github.darthakiranihil.konna.core.util.KThreadUtils;
import io.github.darthakiranihil.konna.level.KLevel;
import io.github.darthakiranihil.konna.level.KLevelComponentLoader;
import io.github.darthakiranihil.konna.level.entity.KAutonomousEntity;
import io.github.darthakiranihil.konna.level.entity.KControllableEntity;
import io.github.darthakiranihil.konna.level.entity.KStaticEntity;
import io.github.darthakiranihil.konna.level.impl.*;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("unchecked")
public class KLevelEntityManagementServiceTests extends KStandardTestClass {

    private static final KonnaBootstrapConfig BOOTSTRAP = new KonnaBootstrapConfig(
        KStandardArgumentParser.class,
        KEngineHypervisor.class,
        new KEngineHypervisorConfig(
            KAppContainer.useGenerated(),
            ContextLoader.class,
            List.of(TestMessageRouteConfigurer.class),
            List.of(),
            List.of(KLevelComponentLoader.class),
            TestFrameLoader.class,
            new KFrameSpawnOptions(KSize.squared(1000), "Hello, world!")
        )
    );

    private final Method shutdown;
    private final Field hypervisor;
    private final Field ctx;

    private Map<UUID, KControllableEntity> controllables;
    private Map<UUID, KStaticEntity> statics;
    private Map<UUID, KAutonomousEntity> autonomouses;
    private KLevel currentLevel;
    private KMessageSystem messageSystem;

    public KLevelEntityManagementServiceTests() {

        this.shutdown = KReflectionUtils.getMethod(Konna.class, "shutdown");
        this.hypervisor = KReflectionUtils.getField(Konna.class, "hypervisor");
        this.ctx = KReflectionUtils.getField(KEngineHypervisor.class, "engineModule");

    }

    @BeforeEach
    void setUp(final TestInfo testInfo) {
        Konna konnaWithOnlyDefaultArgs = new Konna(new String[0], BOOTSTRAP);
        konnaWithOnlyDefaultArgs.run();
        KThreadUtils.sleepForSeconds(2);
        KEngineModule realContext = KReflectionUtils.getFieldValue(
            this.ctx,
            Objects.requireNonNull(KReflectionUtils.getFieldValue(
                this.hypervisor,
                konnaWithOnlyDefaultArgs
            )),
            KEngineModule.class
        );

        Assertions.assertNotNull(realContext);

        this.messageSystem = realContext.messageSystem();
        KObjectRegistry objectRegistry = realContext.objectRegistry();

        if (!testInfo.getTags().contains("doNotLoadLevel")) {
            var body = new KUniversalMap();
            body.put("level_name", "test_level_entity_management_service");
            body.put("sector", "mf1");
            messageSystem.deliverMessageSync(KMessage.regular("loadLevel", body));
        }

        var serviceOpt =  objectRegistry
            .listObjects()
            .stream()
            .filter(o -> o.object().name().equals("LevelEntityManagementService"))
            .findFirst();

        Assertions.assertTrue(serviceOpt.isPresent());
        var service = (KLevelEntityManagementService) serviceOpt.get().object();

        this.currentLevel = KReflectionUtils.getFieldValue(
            KLevelEntityManagementService.class,
            service,
            "currentLevel",
            KLevel.class
        );

        this.controllables = (Map<UUID, KControllableEntity>) KReflectionUtils.getFieldValue(
            KLevelEntityManagementService.class,
            service,
            "controllables",
            Map.class
        );
        this.statics = (Map<UUID, KStaticEntity>) KReflectionUtils.getFieldValue(
            KLevelEntityManagementService.class,
            service,
            "statics",
            Map.class
        );
        this.autonomouses = (Map<UUID, KAutonomousEntity>) KReflectionUtils.getFieldValue(
            KLevelEntityManagementService.class,
            service,
            "autonomouses",
            Map.class
        );

        if (testInfo.getTags().contains("doNotLoadLevel")) {
            Assertions.assertNull(this.currentLevel);
        } else {
            Assertions.assertNotNull(this.currentLevel);
        }
        Assertions.assertNotNull(this.controllables);
        Assertions.assertNotNull(this.statics);
        Assertions.assertNotNull(this.autonomouses);
    }

    @Test
    public void testMoveControllableEntity() {

        Assertions.assertEquals("test_level_entity_management_service",  this.currentLevel.name());
        Assertions.assertEquals(1,  this.controllables.size());
        Assertions.assertEquals(1,  this.statics.size());
        Assertions.assertEquals(1,  this.autonomouses.size());

        KUniversalMap body = new KUniversalMap();
        var id = (UUID)  this.controllables.keySet().toArray()[0];
        body.clear();
        body.put("entity_id", id);
        body.put("direction", new KVector2i(0, 1));
        this.messageSystem.deliverMessageSync(KMessage.regular("setDirectionForControllableEntity", body));
        this.messageSystem.deliverMessageSync(KMessage.regular("moveAllEntities", new KUniversalMap()));
        KThreadUtils.sleepForSeconds(2);
        Assertions.assertEquals(new KVector2i(2, 1),  this.controllables.get(id).getPosition().first());

        var autoId = (UUID) autonomouses.keySet().toArray()[0];
        Assertions.assertEquals(new KVector2i(1, 0),  this.autonomouses.get(autoId).getPosition().first());
        var staticId = (UUID) statics.keySet().toArray()[0];
        Assertions.assertEquals(new KVector2i(0, 1),  this.statics.get(staticId).getPosition().first());

    }

    @Test
    public void testCreateAutonomousEntityValid() {

        Assertions.assertEquals("test_level_entity_management_service",  this.currentLevel.name());
        Assertions.assertEquals(1,  this.controllables.size());
        Assertions.assertEquals(1,  this.statics.size());
        Assertions.assertEquals(1,  this.autonomouses.size());

        KUniversalMap body = new KUniversalMap();
        body.clear();
        body.put("name", "synthetic");
        body.put("descriptor", "synthetic");
        body.put("sector_name", "mf1");
        body.put("position", new KVector2i(2, 2));
        body.put("controller", TestController.class);
        body.put("params", new KMapAssetDefinition(Map.of("test", 42069)));

        this.messageSystem.deliverMessageSync(KMessage.regular("createAutonomousEntity", body));
        Assertions.assertEquals(2, this.autonomouses.size());
        Assertions.assertTrue(
            this
                .autonomouses
                .values()
                .stream()
                .anyMatch(x -> {
                    var pos = x.getPosition();
                    return
                        pos.first().equals(new KVector2i(2, 2))
                            &&  pos.second().name().equals("mf1")
                            &&  x.name().equals("synthetic")
                            &&  x.getDescriptor().equals("synthetic");
                })
        );

    }

    @Test
    public void testCreateAutonomousEntityValidationSkippedBecauseNoValidator() {

        Assertions.assertEquals("test_level_entity_management_service",  this.currentLevel.name());
        Assertions.assertEquals(1,  this.controllables.size());
        Assertions.assertEquals(1,  this.statics.size());
        Assertions.assertEquals(1,  this.autonomouses.size());

        KUniversalMap body = new KUniversalMap();
        body.clear();
        body.put("name", "synthetic");
        body.put("descriptor", "synthetic");
        body.put("sector_name", "mf1");
        body.put("position", new KVector2i(2, 2));
        body.put("controller", TestControllerWithoutValidator.class);
        body.put("params", new KMapAssetDefinition(Map.of("test", 42069)));

        this.messageSystem.deliverMessageSync(KMessage.regular("createAutonomousEntity", body));
        Assertions.assertEquals(2, this.autonomouses.size());
        Assertions.assertTrue(
            this
                .autonomouses
                .values()
                .stream()
                .anyMatch(x -> {
                    var pos = x.getPosition();
                    return
                        pos.first().equals(new KVector2i(2, 2))
                            &&  pos.second().name().equals("mf1")
                            &&  x.name().equals("synthetic")
                            &&  x.getDescriptor().equals("synthetic");
                })
        );

    }

    @Test
    public void testCreateAutonomousEntityValidationSkippedBecauseInvalidValidator() {

        Assertions.assertEquals("test_level_entity_management_service",  this.currentLevel.name());
        Assertions.assertEquals(1,  this.controllables.size());
        Assertions.assertEquals(1,  this.statics.size());
        Assertions.assertEquals(1,  this.autonomouses.size());

        KUniversalMap body = new KUniversalMap();
        body.clear();
        body.put("name", "synthetic");
        body.put("descriptor", "synthetic");
        body.put("sector_name", "mf1");
        body.put("position", new KVector2i(2, 2));
        body.put("controller", FalseValidatedController.class);
        body.put("params", new KMapAssetDefinition(Map.of("test", 42069)));

        this.messageSystem.deliverMessageSync(KMessage.regular("createAutonomousEntity", body));
        Assertions.assertEquals(2, this.autonomouses.size());
        Assertions.assertTrue(
            this
                .autonomouses
                .values()
                .stream()
                .anyMatch(x -> {
                    var pos = x.getPosition();
                    return
                        pos.first().equals(new KVector2i(2, 2))
                            &&  pos.second().name().equals("mf1")
                            &&  x.name().equals("synthetic")
                            &&  x.getDescriptor().equals("synthetic");
                })
        );

    }

    @Test
    public void testCreateAutonomousEntityButValidationFailed() {

        Assertions.assertEquals("test_level_entity_management_service",  this.currentLevel.name());
        Assertions.assertEquals(1,  this.controllables.size());
        Assertions.assertEquals(1,  this.statics.size());
        Assertions.assertEquals(1,  this.autonomouses.size());

        KUniversalMap body = new KUniversalMap();
        body.clear();
        body.put("name", "synthetic");
        body.put("descriptor", "synthetic");
        body.put("sector_name", "mf1");
        body.put("position", new KVector2i(2, 2));
        body.put("controller", TestController.class);
        body.put("params", new KMapAssetDefinition(Map.of("test2", 42069)));

        this.messageSystem.deliverMessageSync(KMessage.regular("createAutonomousEntity", body));
        Assertions.assertEquals(1, this.autonomouses.size());

    }

    @Test
    public void testDestroyAutonomousEntity() {

        Assertions.assertEquals("test_level_entity_management_service",  this.currentLevel.name());
        Assertions.assertEquals(1,  this.controllables.size());
        Assertions.assertEquals(1,  this.statics.size());
        Assertions.assertEquals(1,  this.autonomouses.size());

        var deletedId = (UUID) this.autonomouses.keySet().toArray()[0];
        KUniversalMap body = new KUniversalMap();
        body.clear();
        body.put("entity_id", deletedId);

        var previousPosition = this.autonomouses.get(deletedId).getPosition();

        this.messageSystem.deliverMessageSync(KMessage.regular("destroyAutonomousEntity", body));
        KThreadUtils.sleepForSeconds(2);
        Assertions.assertEquals(0, this.autonomouses.size());
        Assertions.assertEquals(0, previousPosition
            .second()
            .getSlice(previousPosition.first().x(), previousPosition.first().y())
            .entities()
            .size()
        );

    }

    @Test
    public void testCreateControllableEntity() {

        Assertions.assertEquals("test_level_entity_management_service",  this.currentLevel.name());
        Assertions.assertEquals(1,  this.controllables.size());
        Assertions.assertEquals(1,  this.statics.size());
        Assertions.assertEquals(1,  this.autonomouses.size());

        KUniversalMap body = new KUniversalMap();
        body.clear();
        body.put("name", "synthetic");
        body.put("descriptor", "synthetic");
        body.put("sector_name", "mf1");
        body.put("position", new KVector2i(2, 2));

        this.messageSystem.deliverMessageSync(KMessage.regular("createControllableEntity", body));
        Assertions.assertEquals(2, this.controllables.size());
        Assertions.assertTrue(
            this
                .controllables
                .values()
                .stream()
                .anyMatch(x -> {
                    var pos = x.getPosition();
                    return
                        pos.first().equals(new KVector2i(2, 2))
                            &&  pos.second().name().equals("mf1")
                            &&  x.name().equals("synthetic")
                            &&  x.getDescriptor().equals("synthetic");
                })
        );

    }

    @Test
    public void testDestroyControllableEntity() {

        Assertions.assertEquals("test_level_entity_management_service",  this.currentLevel.name());
        Assertions.assertEquals(1,  this.controllables.size());
        Assertions.assertEquals(1,  this.statics.size());
        Assertions.assertEquals(1,  this.autonomouses.size());

        var deletedId = (UUID) this.controllables.keySet().toArray()[0];
        KUniversalMap body = new KUniversalMap();
        body.clear();
        body.put("entity_id", deletedId);

        var previousPosition = this.controllables.get(deletedId).getPosition();

        this.messageSystem.deliverMessageSync(KMessage.regular("destroyControllableEntity", body));
        KThreadUtils.sleepForSeconds(2);
        Assertions.assertEquals(0, this.controllables.size());
        Assertions.assertEquals(0, previousPosition
            .second()
            .getSlice(previousPosition.first().x(), previousPosition.first().y())
            .entities()
            .size()
        );
    }

    @Test
    public void testCreateStaticEntity() {

        Assertions.assertEquals("test_level_entity_management_service",  this.currentLevel.name());
        Assertions.assertEquals(1,  this.controllables.size());
        Assertions.assertEquals(1,  this.statics.size());
        Assertions.assertEquals(1,  this.autonomouses.size());

        KUniversalMap body = new KUniversalMap();
        body.clear();
        body.put("name", "synthetic");
        body.put("descriptor", "synthetic");
        body.put("sector_name", "mf1");
        body.put("position", new KVector2i(2, 2));

        this.messageSystem.deliverMessageSync(KMessage.regular("createStaticEntity", body));
        Assertions.assertEquals(2, this.statics.size());
        Assertions.assertTrue(
            this
                .statics
                .values()
                .stream()
                .anyMatch(x -> {
                    var pos = x.getPosition();
                    return
                            pos.first().equals(new KVector2i(2, 2))
                        &&  pos.second().name().equals("mf1")
                        &&  x.name().equals("synthetic")
                        &&  x.getDescriptor().equals("synthetic");
                })
        );

    }

    @Test
    public void testDestroyStaticEntity() {

        Assertions.assertEquals("test_level_entity_management_service",  this.currentLevel.name());
        Assertions.assertEquals(1,  this.controllables.size());
        Assertions.assertEquals(1,  this.statics.size());
        Assertions.assertEquals(1,  this.autonomouses.size());

        var deletedId = (UUID) this.statics.keySet().toArray()[0];
        KUniversalMap body = new KUniversalMap();
        body.clear();
        body.put("entity_id", deletedId);

        var previousPosition = this.statics.get(deletedId).getPosition();

        this.messageSystem.deliverMessageSync(KMessage.regular("destroyStaticEntity", body));
        KThreadUtils.sleepForSeconds(2);
        Assertions.assertEquals(0, this.statics.size());
        Assertions.assertEquals(0, previousPosition
            .second()
            .getSlice(previousPosition.first().x(), previousPosition.first().y())
            .entities()
            .size()
        );
    }

    @Test
    public void testCreateEntitiesOnInvalidPosition() {

        Assertions.assertEquals("test_level_entity_management_service",  this.currentLevel.name());
        Assertions.assertEquals(1,  this.controllables.size());
        Assertions.assertEquals(1,  this.statics.size());
        Assertions.assertEquals(1,  this.autonomouses.size());

        KUniversalMap body = new KUniversalMap();
        body.clear();
        body.put("name", "synthetic");
        body.put("descriptor", "synthetic");
        body.put("sector_name", "mf1");
        body.put("position", new KVector2i(999, 99));

        this.messageSystem.deliverMessageSync(KMessage.regular("createStaticEntity", body));
        this.messageSystem.deliverMessageSync(KMessage.regular("createControllableEntity", body));

        body.put("controller", TestController.class);
        body.put("params", new KMapAssetDefinition(Map.of("test", 42069)));
        this.messageSystem.deliverMessageSync(KMessage.regular("createAutonomousEntity", body));

        Assertions.assertEquals(1, this.statics.size());
        Assertions.assertEquals(1, this.controllables.size());
        Assertions.assertEquals(1, this.autonomouses.size());

    }

    @Test
    public void testCreateEntitiesOnUnknownSector() {

        Assertions.assertEquals("test_level_entity_management_service",  this.currentLevel.name());
        Assertions.assertEquals(1,  this.controllables.size());
        Assertions.assertEquals(1,  this.statics.size());
        Assertions.assertEquals(1,  this.autonomouses.size());

        KUniversalMap body = new KUniversalMap();
        body.put("name", "synthetic");
        body.put("descriptor", "synthetic");
        body.put("sector_name", "mf99");
        body.put("position", new KVector2i(1, 1));

        this.messageSystem.deliverMessageSync(KMessage.regular("createStaticEntity", body));
        this.messageSystem.deliverMessageSync(KMessage.regular("createControllableEntity", body));

        body.put("controller", TestController.class);
        body.put("params", new KMapAssetDefinition(Map.of("test", 42069)));
        this.messageSystem.deliverMessageSync(KMessage.regular("createAutonomousEntity", body));

        Assertions.assertEquals(1, this.statics.size());
        Assertions.assertEquals(1, this.controllables.size());
        Assertions.assertEquals(1, this.autonomouses.size());

    }

    @Test
    public void testDestroyUnknownEntities() {
        Assertions.assertEquals("test_level_entity_management_service",  this.currentLevel.name());
        Assertions.assertEquals(1,  this.controllables.size());
        Assertions.assertEquals(1,  this.statics.size());
        Assertions.assertEquals(1,  this.autonomouses.size());

        KUniversalMap body = new KUniversalMap();
        body.put("entity_id", UUID.randomUUID());

        this.messageSystem.deliverMessageSync(KMessage.regular("destroyStaticEntity", body));
        this.messageSystem.deliverMessageSync(KMessage.regular("destroyControllableEntity", body));
        this.messageSystem.deliverMessageSync(KMessage.regular("destroyAutonomousEntity", body));

        Assertions.assertEquals(1, this.statics.size());
        Assertions.assertEquals(1, this.controllables.size());
        Assertions.assertEquals(1, this.autonomouses.size());

    }

    @Test
    public void testSetDirectionForUnknownControllable() {

        Assertions.assertEquals("test_level_entity_management_service",  this.currentLevel.name());
        Assertions.assertEquals(1,  this.controllables.size());
        Assertions.assertEquals(1,  this.statics.size());
        Assertions.assertEquals(1,  this.autonomouses.size());

        KUniversalMap body = new KUniversalMap();
        var id = (UUID)  this.controllables.keySet().toArray()[0];
        var previousPosition = this.controllables.get(id).getPosition();

        body.put("entity_id", UUID.randomUUID());
        body.put("direction", new KVector2i(0, 1));
        this.messageSystem.deliverMessageSync(KMessage.regular("setDirectionForControllableEntity", body));
        this.messageSystem.deliverMessageSync(KMessage.regular("moveAllEntities", new KUniversalMap()));

        KThreadUtils.sleepForSeconds(2);
        Assertions.assertEquals(previousPosition,  this.controllables.get(id).getPosition());

        var autoId = (UUID) autonomouses.keySet().toArray()[0];
        Assertions.assertEquals(new KVector2i(1, 0),  this.autonomouses.get(autoId).getPosition().first());
        var staticId = (UUID) statics.keySet().toArray()[0];
        Assertions.assertEquals(new KVector2i(0, 1),  this.statics.get(staticId).getPosition().first());

    }

    @Test
    @Tag("doNotLoadLevel")
    public void testCreateEntitiesWithoutLevel() {

        Assertions.assertNull(this.currentLevel);
        Assertions.assertEquals(0,  this.controllables.size());
        Assertions.assertEquals(0,  this.statics.size());
        Assertions.assertEquals(0,  this.autonomouses.size());

        KUniversalMap body = new KUniversalMap();
        body.put("name", "synthetic");
        body.put("descriptor", "synthetic");
        body.put("sector_name", "mf99");
        body.put("position", new KVector2i(1, 1));

        this.messageSystem.deliverMessageSync(KMessage.regular("createStaticEntity", body));
        this.messageSystem.deliverMessageSync(KMessage.regular("createControllableEntity", body));

        body.put("controller", TestController.class);
        body.put("params", new KMapAssetDefinition(Map.of("test", 42069)));
        this.messageSystem.deliverMessageSync(KMessage.regular("createAutonomousEntity", body));

        Assertions.assertEquals(0, this.statics.size());
        Assertions.assertEquals(0, this.controllables.size());
        Assertions.assertEquals(0, this.autonomouses.size());

    }

    @Test
    @Tag("doNotLoadLevel")
    public void testDestroyEntitiesWithoutLevel() {

        Assertions.assertNull(this.currentLevel);
        Assertions.assertEquals(0,  this.controllables.size());
        Assertions.assertEquals(0,  this.statics.size());
        Assertions.assertEquals(0,  this.autonomouses.size());

        KUniversalMap body = new KUniversalMap();
        body.put("entity_id", UUID.randomUUID());

        this.messageSystem.deliverMessageSync(KMessage.regular("destroyStaticEntity", body));
        this.messageSystem.deliverMessageSync(KMessage.regular("destroyControllableEntity", body));
        this.messageSystem.deliverMessageSync(KMessage.regular("destroyAutonomousEntity", body));

        Assertions.assertEquals(0, this.statics.size());
        Assertions.assertEquals(0, this.controllables.size());
        Assertions.assertEquals(0, this.autonomouses.size());

    }


}



