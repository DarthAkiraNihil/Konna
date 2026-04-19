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
import io.github.darthakiranihil.konna.core.app.KFrameEvent;
import io.github.darthakiranihil.konna.core.app.KFrameTaskExecutor;
import io.github.darthakiranihil.konna.core.app.KStandardArgumentParser;
import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.di.KAppContainer;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisor;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisorConfig;
import io.github.darthakiranihil.konna.core.engine.KService;
import io.github.darthakiranihil.konna.core.io.KMapAssetDefinition;
import io.github.darthakiranihil.konna.core.message.*;
import io.github.darthakiranihil.konna.core.object.KObjectRegistry;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.core.struct.ref.KBooleanReference;
import io.github.darthakiranihil.konna.core.util.KReflectionUtils;
import io.github.darthakiranihil.konna.level.KLevel;
import io.github.darthakiranihil.konna.level.KLevelComponentLoader;
import io.github.darthakiranihil.konna.level.entity.KAutonomousEntity;
import io.github.darthakiranihil.konna.level.entity.KControllableEntity;
import io.github.darthakiranihil.konna.level.entity.KStaticEntity;
import io.github.darthakiranihil.konna.level.impl.FalseValidatedController;
import io.github.darthakiranihil.konna.level.impl.TestController;
import io.github.darthakiranihil.konna.level.impl.TestControllerWithoutValidator;
import io.github.darthakiranihil.konna.level.impl.TestMessageRouteConfigurer;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("unchecked")
public class KLevelEntityManagementServiceTests extends KStandardTestClass {

    private static final KSimpleEvent CAPTURE_THE_FLAG = new KSimpleEvent("captureTheFlag");

    private static KService assertService(
        KObjectRegistry objectRegistry
    ) {
        var packedService = objectRegistry
            .getObjectsOfType(KService.class)
            .stream()
            .filter(o -> ((KService) o.getCastObject()).name().equals("LevelEntityManagementService"))
            .findFirst();

        Assertions.assertTrue(packedService.isPresent());
        return packedService.get().getCastObject();
    }
    
    private static abstract class AsserterForNotLoadedLevel implements KTunnel {
        private final KObjectRegistry objectRegistry;
        private final KMessageSystem messageSystem;
        private final KFrameTaskExecutor frameTaskExecutor;

        @KInject
        public AsserterForNotLoadedLevel(
            KObjectRegistry objectRegistry,
            KMessageSystem messageSystem,
            KFrameTaskExecutor frameTaskExecutor
        ) {
            this.objectRegistry = objectRegistry;
            this.messageSystem = messageSystem;
            this.frameTaskExecutor = frameTaskExecutor;
        }

        @Override
        public final KMessage processMessage(KMessage message) {
            KService service = assertService(this.objectRegistry);

            var currentLevel = KReflectionUtils.getFieldValue(
                KLevelEntityManagementService.class,
                service,
                "currentLevel",
                KLevel.class
            );
            var controllables = (Map<UUID, KControllableEntity>) KReflectionUtils.getFieldValue(
                KLevelEntityManagementService.class,
                service,
                "controllables",
                Map.class
            );
            var statics = (Map<UUID, KStaticEntity>) KReflectionUtils.getFieldValue(
                KLevelEntityManagementService.class,
                service,
                "statics",
                Map.class
            );
            var autonomouses = (Map<UUID, KAutonomousEntity>) KReflectionUtils.getFieldValue(
                KLevelEntityManagementService.class,
                service,
                "autonomouses",
                Map.class
            );

            Assertions.assertNull(currentLevel);
            Assertions.assertNotNull(controllables);
            Assertions.assertNotNull(statics);
            Assertions.assertNotNull(autonomouses);
            
            Assertions.assertEquals(0,  controllables.size());
            Assertions.assertEquals(0,  statics.size());
            Assertions.assertEquals(0,  autonomouses.size());

            this.check(controllables, statics, autonomouses, this.messageSystem, this.frameTaskExecutor);
            CAPTURE_THE_FLAG.invokeSync();
            return KMessage.DROP;
        }

        protected abstract void check(
            Map<UUID, KControllableEntity> controllables,
            Map<UUID, KStaticEntity> statics,
            Map<UUID, KAutonomousEntity> autonomouses,
            KMessageSystem messageSystem,
            KFrameTaskExecutor frameTaskExecutor
        );
    }
    
    private static abstract class AsserterForLoadedLevel implements KTunnel {

        private final KObjectRegistry objectRegistry;
        private final KMessageSystem messageSystem;
        private final KFrameTaskExecutor frameTaskExecutor;

        @KInject
        public AsserterForLoadedLevel(
            KObjectRegistry objectRegistry,
            KMessageSystem messageSystem,
            KFrameTaskExecutor frameTaskExecutor
        ) {
            this.objectRegistry = objectRegistry;
            this.messageSystem = messageSystem;
            this.frameTaskExecutor = frameTaskExecutor;
        }

        @Override
        public final KMessage processMessage(KMessage message) {
            KService service = assertService(this.objectRegistry);

            var currentLevel = KReflectionUtils.getFieldValue(
                KLevelEntityManagementService.class,
                service,
                "currentLevel",
                KLevel.class
            );
            var controllables = (Map<UUID, KControllableEntity>) KReflectionUtils.getFieldValue(
                KLevelEntityManagementService.class,
                service,
                "controllables",
                Map.class
            );
            var statics = (Map<UUID, KStaticEntity>) KReflectionUtils.getFieldValue(
                KLevelEntityManagementService.class,
                service,
                "statics",
                Map.class
            );
            var autonomouses = (Map<UUID, KAutonomousEntity>) KReflectionUtils.getFieldValue(
                KLevelEntityManagementService.class,
                service,
                "autonomouses",
                Map.class
            );

            Assertions.assertNotNull(currentLevel);
            Assertions.assertNotNull(controllables);
            Assertions.assertNotNull(statics);
            Assertions.assertNotNull(autonomouses);

            Assertions.assertEquals("test_level_entity_management_service",  currentLevel.name());
            Assertions.assertEquals(1,  controllables.size());
            Assertions.assertEquals(1,  statics.size());
            Assertions.assertEquals(1,  autonomouses.size());

            this.check(controllables, statics, autonomouses, this.messageSystem, this.frameTaskExecutor);
            CAPTURE_THE_FLAG.invokeSync();
            return message;
        }

        protected abstract void check(
            Map<UUID, KControllableEntity> controllables,
            Map<UUID, KStaticEntity> statics,
            Map<UUID, KAutonomousEntity> autonomouses,
            KMessageSystem messageSystem,
            KFrameTaskExecutor frameTaskExecutor
        );
    }

    @Test
    public void testMoveControllableEntity() {

        class Asserter extends AsserterForLoadedLevel {

            public Asserter(
                KObjectRegistry objectRegistry,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                super(objectRegistry, messageSystem, frameTaskExecutor);
            }

            @Override
            protected void check(
                Map<UUID, KControllableEntity> controllables,
                Map<UUID, KStaticEntity> statics,
                Map<UUID, KAutonomousEntity> autonomouses,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                KUniversalMap body = new KUniversalMap();
                var id = (UUID)  controllables.keySet().toArray()[0];
                body.clear();
                body.put("entity_id", id);
                body.put("direction", new KVector2i(0, 1));
                messageSystem.deliverMessageSync(KMessage.regular("setDirectionForControllableEntity", body));
                messageSystem.deliverMessageSync(KMessage.regular("moveAllEntities", new KUniversalMap()));
                frameTaskExecutor.executeScheduledTasks(KFrameEvent.FRAME_FINISHED);
                Assertions.assertEquals(new KVector2i(2, 1),  controllables.get(id).getPosition().first());

                var autoId = (UUID) autonomouses.keySet().toArray()[0];
                Assertions.assertEquals(new KVector2i(1, 0),  autonomouses.get(autoId).getPosition().first());
                var staticId = (UUID) statics.keySet().toArray()[0];
                Assertions.assertEquals(new KVector2i(0, 1),  statics.get(staticId).getPosition().first());
            }
        }

        class Sender implements KMessageRoutesConfigurer {

            @Override
            public void setupRoutes(KMessageSystem messageSystem) {
                messageSystem.addMessageRoute(
                    "Level.levelLoaded",
                    KMessageSystem.SINK,
                    List.of(Asserter.class)
                );

                var body = new KUniversalMap();
                body.put("level_name", "test_level_entity_management_service");
                body.put("sector", "mf1");
                messageSystem.deliverMessageSync(KMessage.regular("loadLevel", body));
            }

        }

        Konna konna = new Konna(
            new String[0],
            new KonnaBootstrapConfig(
                KStandardArgumentParser.class,
                KEngineHypervisor.class,
                new KEngineHypervisorConfig(
                    KAppContainer.useGenerated(),
                    List.of(TestMessageRouteConfigurer.class, Sender.class),
                    List.of(),
                    List.of(KLevelComponentLoader.class)
                )
            )
        );

        KBooleanReference executed = new KBooleanReference();
        UUID subToken = CAPTURE_THE_FLAG.subscribe(() -> executed.set(true));
        konna.run();
        Assertions.assertTrue(executed.get());
        CAPTURE_THE_FLAG.unsubscribe(subToken);

    }

    @Test
    public void testCreateAutonomousEntityValid() {

        class Asserter extends AsserterForLoadedLevel {

            public Asserter(
                KObjectRegistry objectRegistry,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                super(objectRegistry, messageSystem, frameTaskExecutor);
            }

            @Override
            protected void check(
                Map<UUID, KControllableEntity> controllables,
                Map<UUID, KStaticEntity> statics,
                Map<UUID, KAutonomousEntity> autonomouses,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                KUniversalMap body = new KUniversalMap();
                body.clear();
                body.put("name", "synthetic");
                body.put("descriptor", "synthetic");
                body.put("sector_name", "mf1");
                body.put("position", new KVector2i(2, 2));
                body.put("controller", TestController.class);
                body.put("params", new KMapAssetDefinition(Map.of("test", 42069)));

                messageSystem.deliverMessageSync(KMessage.regular("createAutonomousEntity", body));
                Assertions.assertEquals(2, autonomouses.size());
                Assertions.assertTrue(
                    autonomouses
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
        }

        class Sender implements KMessageRoutesConfigurer {

            @Override
            public void setupRoutes(KMessageSystem messageSystem) {
                messageSystem.addMessageRoute(
                    "Level.levelLoaded",
                    KMessageSystem.SINK,
                    List.of(Asserter.class)
                );

                var body = new KUniversalMap();
                body.put("level_name", "test_level_entity_management_service");
                body.put("sector", "mf1");
                messageSystem.deliverMessageSync(KMessage.regular("loadLevel", body));
            }

        }

        Konna konna = new Konna(
            new String[0],
            new KonnaBootstrapConfig(
                KStandardArgumentParser.class,
                KEngineHypervisor.class,
                new KEngineHypervisorConfig(
                    KAppContainer.useGenerated(),
                    List.of(TestMessageRouteConfigurer.class, Sender.class),
                    List.of(),
                    List.of(KLevelComponentLoader.class)
                )
            )
        );

        KBooleanReference executed = new KBooleanReference();
        UUID subToken = CAPTURE_THE_FLAG.subscribe(() -> executed.set(true));
        konna.run();
        Assertions.assertTrue(executed.get());
        CAPTURE_THE_FLAG.unsubscribe(subToken);
        
    }

    @Test
    public void testCreateAutonomousEntityValidationSkippedBecauseNoValidator() {

        class Asserter extends AsserterForLoadedLevel {

            public Asserter(
                KObjectRegistry objectRegistry,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                super(objectRegistry, messageSystem, frameTaskExecutor);
            }

            @Override
            protected void check(
                Map<UUID, KControllableEntity> controllables,
                Map<UUID, KStaticEntity> statics,
                Map<UUID, KAutonomousEntity> autonomouses,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                KUniversalMap body = new KUniversalMap();
                body.clear();
                body.put("name", "synthetic");
                body.put("descriptor", "synthetic");
                body.put("sector_name", "mf1");
                body.put("position", new KVector2i(2, 2));
                body.put("controller", TestControllerWithoutValidator.class);
                body.put("params", new KMapAssetDefinition(Map.of("test", 42069)));

                messageSystem.deliverMessageSync(KMessage.regular("createAutonomousEntity", body));
                Assertions.assertEquals(2, autonomouses.size());
                Assertions.assertTrue(
                    autonomouses
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
        }

        class Sender implements KMessageRoutesConfigurer {

            @Override
            public void setupRoutes(KMessageSystem messageSystem) {
                messageSystem.addMessageRoute(
                    "Level.levelLoaded",
                    KMessageSystem.SINK,
                    List.of(Asserter.class)
                );

                var body = new KUniversalMap();
                body.put("level_name", "test_level_entity_management_service");
                body.put("sector", "mf1");
                messageSystem.deliverMessageSync(KMessage.regular("loadLevel", body));
            }

        }

        Konna konna = new Konna(
            new String[0],
            new KonnaBootstrapConfig(
                KStandardArgumentParser.class,
                KEngineHypervisor.class,
                new KEngineHypervisorConfig(
                    KAppContainer.useGenerated(),
                    List.of(TestMessageRouteConfigurer.class, Sender.class),
                    List.of(),
                    List.of(KLevelComponentLoader.class)
                )
            )
        );

        KBooleanReference executed = new KBooleanReference();
        UUID subToken = CAPTURE_THE_FLAG.subscribe(() -> executed.set(true));
        konna.run();
        Assertions.assertTrue(executed.get());
        CAPTURE_THE_FLAG.unsubscribe(subToken);
        
    }
    
    @Test
    public void testCreateAutonomousEntityValidationSkippedBecauseInvalidValidator() {
        class Asserter extends AsserterForLoadedLevel {

            public Asserter(
                KObjectRegistry objectRegistry,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                super(objectRegistry, messageSystem, frameTaskExecutor);
            }

            @Override
            protected void check(
                Map<UUID, KControllableEntity> controllables,
                Map<UUID, KStaticEntity> statics,
                Map<UUID, KAutonomousEntity> autonomouses,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                KUniversalMap body = new KUniversalMap();
                body.clear();
                body.put("name", "synthetic");
                body.put("descriptor", "synthetic");
                body.put("sector_name", "mf1");
                body.put("position", new KVector2i(2, 2));
                body.put("controller", FalseValidatedController.class);
                body.put("params", new KMapAssetDefinition(Map.of("test", 42069)));

                messageSystem.deliverMessageSync(KMessage.regular("createAutonomousEntity", body));
                Assertions.assertEquals(2, autonomouses.size());
                Assertions.assertTrue(
                    autonomouses
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
        }

        class Sender implements KMessageRoutesConfigurer {

            @Override
            public void setupRoutes(KMessageSystem messageSystem) {
                messageSystem.addMessageRoute(
                    "Level.levelLoaded",
                    KMessageSystem.SINK,
                    List.of(Asserter.class)
                );

                var body = new KUniversalMap();
                body.put("level_name", "test_level_entity_management_service");
                body.put("sector", "mf1");
                messageSystem.deliverMessageSync(KMessage.regular("loadLevel", body));
            }

        }

        Konna konna = new Konna(
            new String[0],
            new KonnaBootstrapConfig(
                KStandardArgumentParser.class,
                KEngineHypervisor.class,
                new KEngineHypervisorConfig(
                    KAppContainer.useGenerated(),
                    List.of(TestMessageRouteConfigurer.class, Sender.class),
                    List.of(),
                    List.of(KLevelComponentLoader.class)
                )
            )
        );

        KBooleanReference executed = new KBooleanReference();
        UUID subToken = CAPTURE_THE_FLAG.subscribe(() -> executed.set(true));
        konna.run();
        Assertions.assertTrue(executed.get());
        CAPTURE_THE_FLAG.unsubscribe(subToken);
    }
    
    @Test
    public void testCreateAutonomousEntityButValidationFailed() {
        class Asserter extends AsserterForLoadedLevel {

            public Asserter(
                KObjectRegistry objectRegistry,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                super(objectRegistry, messageSystem, frameTaskExecutor);
            }

            @Override
            protected void check(
                Map<UUID, KControllableEntity> controllables,
                Map<UUID, KStaticEntity> statics,
                Map<UUID, KAutonomousEntity> autonomouses,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                KUniversalMap body = new KUniversalMap();
                body.clear();
                body.put("name", "synthetic");
                body.put("descriptor", "synthetic");
                body.put("sector_name", "mf1");
                body.put("position", new KVector2i(2, 2));
                body.put("controller", TestController.class);
                body.put("params", new KMapAssetDefinition(Map.of("test2", 42069)));

                messageSystem.deliverMessageSync(KMessage.regular("createAutonomousEntity", body));
                Assertions.assertEquals(1, autonomouses.size());
            }
        }

        class Sender implements KMessageRoutesConfigurer {

            @Override
            public void setupRoutes(KMessageSystem messageSystem) {
                messageSystem.addMessageRoute(
                    "Level.levelLoaded",
                    KMessageSystem.SINK,
                    List.of(Asserter.class)
                );

                var body = new KUniversalMap();
                body.put("level_name", "test_level_entity_management_service");
                body.put("sector", "mf1");
                messageSystem.deliverMessageSync(KMessage.regular("loadLevel", body));
            }

        }

        Konna konna = new Konna(
            new String[0],
            new KonnaBootstrapConfig(
                KStandardArgumentParser.class,
                KEngineHypervisor.class,
                new KEngineHypervisorConfig(
                    KAppContainer.useGenerated(),
                    List.of(TestMessageRouteConfigurer.class, Sender.class),
                    List.of(),
                    List.of(KLevelComponentLoader.class)
                )
            )
        );

        KBooleanReference executed = new KBooleanReference();
        UUID subToken = CAPTURE_THE_FLAG.subscribe(() -> executed.set(true));
        konna.run();
        Assertions.assertTrue(executed.get());
        CAPTURE_THE_FLAG.unsubscribe(subToken);
    }
    
    @Test
    public void testDestroyAutonomousEntity() {
        class Asserter extends AsserterForLoadedLevel {

            public Asserter(
                KObjectRegistry objectRegistry,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                super(objectRegistry, messageSystem, frameTaskExecutor);
            }

            @Override
            protected void check(
                Map<UUID, KControllableEntity> controllables,
                Map<UUID, KStaticEntity> statics,
                Map<UUID, KAutonomousEntity> autonomouses,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                var deletedId = (UUID) autonomouses.keySet().toArray()[0];
                KUniversalMap body = new KUniversalMap();
                body.clear();
                body.put("entity_id", deletedId);

                var previousPosition = autonomouses.get(deletedId).getPosition();

                messageSystem.deliverMessageSync(KMessage.regular("destroyAutonomousEntity", body));
                frameTaskExecutor.executeScheduledTasks(KFrameEvent.FRAME_FINISHED);
                Assertions.assertEquals(0, autonomouses.size());
                Assertions.assertEquals(0, previousPosition
                    .second()
                    .getSlice(previousPosition.first().x(), previousPosition.first().y())
                    .entities()
                    .size()
                );
            }
        }

        class Sender implements KMessageRoutesConfigurer {

            @Override
            public void setupRoutes(KMessageSystem messageSystem) {
                messageSystem.addMessageRoute(
                    "Level.levelLoaded",
                    KMessageSystem.SINK,
                    List.of(Asserter.class)
                );

                var body = new KUniversalMap();
                body.put("level_name", "test_level_entity_management_service");
                body.put("sector", "mf1");
                messageSystem.deliverMessageSync(KMessage.regular("loadLevel", body));
            }

        }

        Konna konna = new Konna(
            new String[0],
            new KonnaBootstrapConfig(
                KStandardArgumentParser.class,
                KEngineHypervisor.class,
                new KEngineHypervisorConfig(
                    KAppContainer.useGenerated(),
                    List.of(TestMessageRouteConfigurer.class, Sender.class),
                    List.of(),
                    List.of(KLevelComponentLoader.class)
                )
            )
        );

        KBooleanReference executed = new KBooleanReference();
        UUID subToken = CAPTURE_THE_FLAG.subscribe(() -> executed.set(true));
        konna.run();
        Assertions.assertTrue(executed.get());
        CAPTURE_THE_FLAG.unsubscribe(subToken);
    }
    
    @Test
    public void testCreateControllableEntity() {
        class Asserter extends AsserterForLoadedLevel {

            public Asserter(
                KObjectRegistry objectRegistry,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                super(objectRegistry, messageSystem, frameTaskExecutor);
            }

            @Override
            protected void check(
                Map<UUID, KControllableEntity> controllables,
                Map<UUID, KStaticEntity> statics,
                Map<UUID, KAutonomousEntity> autonomouses,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                KUniversalMap body = new KUniversalMap();
                body.clear();
                body.put("name", "synthetic");
                body.put("descriptor", "synthetic");
                body.put("sector_name", "mf1");
                body.put("position", new KVector2i(2, 2));

                messageSystem.deliverMessageSync(KMessage.regular("createControllableEntity", body));
                Assertions.assertEquals(2, controllables.size());
                Assertions.assertTrue(
                    controllables
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
        }

        class Sender implements KMessageRoutesConfigurer {

            @Override
            public void setupRoutes(KMessageSystem messageSystem) {
                messageSystem.addMessageRoute(
                    "Level.levelLoaded",
                    KMessageSystem.SINK,
                    List.of(Asserter.class)
                );

                var body = new KUniversalMap();
                body.put("level_name", "test_level_entity_management_service");
                body.put("sector", "mf1");
                messageSystem.deliverMessageSync(KMessage.regular("loadLevel", body));
            }

        }

        Konna konna = new Konna(
            new String[0],
            new KonnaBootstrapConfig(
                KStandardArgumentParser.class,
                KEngineHypervisor.class,
                new KEngineHypervisorConfig(
                    KAppContainer.useGenerated(),
                    List.of(TestMessageRouteConfigurer.class, Sender.class),
                    List.of(),
                    List.of(KLevelComponentLoader.class)
                )
            )
        );

        KBooleanReference executed = new KBooleanReference();
        UUID subToken = CAPTURE_THE_FLAG.subscribe(() -> executed.set(true));
        konna.run();
        Assertions.assertTrue(executed.get());
        CAPTURE_THE_FLAG.unsubscribe(subToken);
    }
    
    @Test
    public void testDestroyControllableEntity() {
        class Asserter extends AsserterForLoadedLevel {

            public Asserter(
                KObjectRegistry objectRegistry,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                super(objectRegistry, messageSystem, frameTaskExecutor);
            }

            @Override
            protected void check(
                Map<UUID, KControllableEntity> controllables,
                Map<UUID, KStaticEntity> statics,
                Map<UUID, KAutonomousEntity> autonomouses,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                var deletedId = (UUID) controllables.keySet().toArray()[0];
                KUniversalMap body = new KUniversalMap();
                body.clear();
                body.put("entity_id", deletedId);

                var previousPosition = controllables.get(deletedId).getPosition();

                messageSystem.deliverMessageSync(KMessage.regular("destroyControllableEntity", body));
                frameTaskExecutor.executeScheduledTasks(KFrameEvent.FRAME_FINISHED);
                
                Assertions.assertEquals(0, controllables.size());
                Assertions.assertEquals(0, previousPosition
                    .second()
                    .getSlice(previousPosition.first().x(), previousPosition.first().y())
                    .entities()
                    .size()
                );
            }
        }

        class Sender implements KMessageRoutesConfigurer {

            @Override
            public void setupRoutes(KMessageSystem messageSystem) {
                messageSystem.addMessageRoute(
                    "Level.levelLoaded",
                    KMessageSystem.SINK,
                    List.of(Asserter.class)
                );

                var body = new KUniversalMap();
                body.put("level_name", "test_level_entity_management_service");
                body.put("sector", "mf1");
                messageSystem.deliverMessageSync(KMessage.regular("loadLevel", body));
            }

        }

        Konna konna = new Konna(
            new String[0],
            new KonnaBootstrapConfig(
                KStandardArgumentParser.class,
                KEngineHypervisor.class,
                new KEngineHypervisorConfig(
                    KAppContainer.useGenerated(),
                    List.of(TestMessageRouteConfigurer.class, Sender.class),
                    List.of(),
                    List.of(KLevelComponentLoader.class)
                )
            )
        );

        KBooleanReference executed = new KBooleanReference();
        UUID subToken = CAPTURE_THE_FLAG.subscribe(() -> executed.set(true));
        konna.run();
        Assertions.assertTrue(executed.get());
        CAPTURE_THE_FLAG.unsubscribe(subToken);
    }
    
    @Test
    public void testCreateStaticEntity() {
        class Asserter extends AsserterForLoadedLevel {

            public Asserter(
                KObjectRegistry objectRegistry,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                super(objectRegistry, messageSystem, frameTaskExecutor);
            }

            @Override
            protected void check(
                Map<UUID, KControllableEntity> controllables,
                Map<UUID, KStaticEntity> statics,
                Map<UUID, KAutonomousEntity> autonomouses,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                KUniversalMap body = new KUniversalMap();
                body.clear();
                body.put("name", "synthetic");
                body.put("descriptor", "synthetic");
                body.put("sector_name", "mf1");
                body.put("position", new KVector2i(2, 2));

                messageSystem.deliverMessageSync(KMessage.regular("createStaticEntity", body));
                Assertions.assertEquals(2, statics.size());
                Assertions.assertTrue(
                    statics
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
        }

        class Sender implements KMessageRoutesConfigurer {

            @Override
            public void setupRoutes(KMessageSystem messageSystem) {
                messageSystem.addMessageRoute(
                    "Level.levelLoaded",
                    KMessageSystem.SINK,
                    List.of(Asserter.class)
                );

                var body = new KUniversalMap();
                body.put("level_name", "test_level_entity_management_service");
                body.put("sector", "mf1");
                messageSystem.deliverMessageSync(KMessage.regular("loadLevel", body));
            }

        }

        Konna konna = new Konna(
            new String[0],
            new KonnaBootstrapConfig(
                KStandardArgumentParser.class,
                KEngineHypervisor.class,
                new KEngineHypervisorConfig(
                    KAppContainer.useGenerated(),
                    List.of(TestMessageRouteConfigurer.class, Sender.class),
                    List.of(),
                    List.of(KLevelComponentLoader.class)
                )
            )
        );

        KBooleanReference executed = new KBooleanReference();
        UUID subToken = CAPTURE_THE_FLAG.subscribe(() -> executed.set(true));
        konna.run();
        Assertions.assertTrue(executed.get());
        CAPTURE_THE_FLAG.unsubscribe(subToken);
    }
    
    @Test
    public void testDestroyStaticEntity() {
        class Asserter extends AsserterForLoadedLevel {

            public Asserter(
                KObjectRegistry objectRegistry,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                super(objectRegistry, messageSystem, frameTaskExecutor);
            }

            @Override
            protected void check(
                Map<UUID, KControllableEntity> controllables,
                Map<UUID, KStaticEntity> statics,
                Map<UUID, KAutonomousEntity> autonomouses,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                var deletedId = (UUID) statics.keySet().toArray()[0];
                KUniversalMap body = new KUniversalMap();
                body.clear();
                body.put("entity_id", deletedId);

                var previousPosition = statics.get(deletedId).getPosition();

                messageSystem.deliverMessageSync(KMessage.regular("destroyStaticEntity", body));
                
                frameTaskExecutor.executeScheduledTasks(KFrameEvent.FRAME_FINISHED);
                Assertions.assertEquals(0, statics.size());
                Assertions.assertEquals(0, previousPosition
                    .second()
                    .getSlice(previousPosition.first().x(), previousPosition.first().y())
                    .entities()
                    .size()
                );
            }
        }

        class Sender implements KMessageRoutesConfigurer {

            @Override
            public void setupRoutes(KMessageSystem messageSystem) {
                messageSystem.addMessageRoute(
                    "Level.levelLoaded",
                    KMessageSystem.SINK,
                    List.of(Asserter.class)
                );

                var body = new KUniversalMap();
                body.put("level_name", "test_level_entity_management_service");
                body.put("sector", "mf1");
                messageSystem.deliverMessageSync(KMessage.regular("loadLevel", body));
            }

        }

        Konna konna = new Konna(
            new String[0],
            new KonnaBootstrapConfig(
                KStandardArgumentParser.class,
                KEngineHypervisor.class,
                new KEngineHypervisorConfig(
                    KAppContainer.useGenerated(),
                    List.of(TestMessageRouteConfigurer.class, Sender.class),
                    List.of(),
                    List.of(KLevelComponentLoader.class)
                )
            )
        );

        KBooleanReference executed = new KBooleanReference();
        UUID subToken = CAPTURE_THE_FLAG.subscribe(() -> executed.set(true));
        konna.run();
        Assertions.assertTrue(executed.get());
        CAPTURE_THE_FLAG.unsubscribe(subToken);
    }
    
    @Test
    public void testCreateEntitiesOnInvalidPosition() {
        class Asserter extends AsserterForLoadedLevel {

            public Asserter(
                KObjectRegistry objectRegistry,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                super(objectRegistry, messageSystem, frameTaskExecutor);
            }

            @Override
            protected void check(
                Map<UUID, KControllableEntity> controllables,
                Map<UUID, KStaticEntity> statics,
                Map<UUID, KAutonomousEntity> autonomouses,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                KUniversalMap body = new KUniversalMap();
                body.clear();
                body.put("name", "synthetic");
                body.put("descriptor", "synthetic");
                body.put("sector_name", "mf1");
                body.put("position", new KVector2i(999, 99));

                messageSystem.deliverMessageSync(KMessage.regular("createStaticEntity", body));
                messageSystem.deliverMessageSync(KMessage.regular("createControllableEntity", body));

                body.put("controller", TestController.class);
                body.put("params", new KMapAssetDefinition(Map.of("test", 42069)));
                messageSystem.deliverMessageSync(KMessage.regular("createAutonomousEntity", body));

                Assertions.assertEquals(1, statics.size());
                Assertions.assertEquals(1, controllables.size());
                Assertions.assertEquals(1, autonomouses.size());
            }
        }

        class Sender implements KMessageRoutesConfigurer {

            @Override
            public void setupRoutes(KMessageSystem messageSystem) {
                messageSystem.addMessageRoute(
                    "Level.levelLoaded",
                    KMessageSystem.SINK,
                    List.of(Asserter.class)
                );

                var body = new KUniversalMap();
                body.put("level_name", "test_level_entity_management_service");
                body.put("sector", "mf1");
                messageSystem.deliverMessageSync(KMessage.regular("loadLevel", body));
            }

        }

        Konna konna = new Konna(
            new String[0],
            new KonnaBootstrapConfig(
                KStandardArgumentParser.class,
                KEngineHypervisor.class,
                new KEngineHypervisorConfig(
                    KAppContainer.useGenerated(),
                    List.of(TestMessageRouteConfigurer.class, Sender.class),
                    List.of(),
                    List.of(KLevelComponentLoader.class)
                )
            )
        );

        KBooleanReference executed = new KBooleanReference();
        UUID subToken = CAPTURE_THE_FLAG.subscribe(() -> executed.set(true));
        konna.run();
        Assertions.assertTrue(executed.get());
        CAPTURE_THE_FLAG.unsubscribe(subToken);
    }
    
    @Test
    public void testCreateEntitiesOnUnknownSector() {
        class Asserter extends AsserterForLoadedLevel {

            public Asserter(
                KObjectRegistry objectRegistry,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                super(objectRegistry, messageSystem, frameTaskExecutor);
            }

            @Override
            protected void check(
                Map<UUID, KControllableEntity> controllables,
                Map<UUID, KStaticEntity> statics,
                Map<UUID, KAutonomousEntity> autonomouses,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                KUniversalMap body = new KUniversalMap();
                body.put("name", "synthetic");
                body.put("descriptor", "synthetic");
                body.put("sector_name", "mf99");
                body.put("position", new KVector2i(1, 1));

                messageSystem.deliverMessageSync(KMessage.regular("createStaticEntity", body));
                messageSystem.deliverMessageSync(KMessage.regular("createControllableEntity", body));

                body.put("controller", TestController.class);
                body.put("params", new KMapAssetDefinition(Map.of("test", 42069)));
                messageSystem.deliverMessageSync(KMessage.regular("createAutonomousEntity", body));

                Assertions.assertEquals(1, statics.size());
                Assertions.assertEquals(1, controllables.size());
                Assertions.assertEquals(1, autonomouses.size());
            }
        }

        class Sender implements KMessageRoutesConfigurer {

            @Override
            public void setupRoutes(KMessageSystem messageSystem) {
                messageSystem.addMessageRoute(
                    "Level.levelLoaded",
                    KMessageSystem.SINK,
                    List.of(Asserter.class)
                );

                var body = new KUniversalMap();
                body.put("level_name", "test_level_entity_management_service");
                body.put("sector", "mf1");
                messageSystem.deliverMessageSync(KMessage.regular("loadLevel", body));
            }

        }

        Konna konna = new Konna(
            new String[0],
            new KonnaBootstrapConfig(
                KStandardArgumentParser.class,
                KEngineHypervisor.class,
                new KEngineHypervisorConfig(
                    KAppContainer.useGenerated(),
                    List.of(TestMessageRouteConfigurer.class, Sender.class),
                    List.of(),
                    List.of(KLevelComponentLoader.class)
                )
            )
        );

        KBooleanReference executed = new KBooleanReference();
        UUID subToken = CAPTURE_THE_FLAG.subscribe(() -> executed.set(true));
        konna.run();
        Assertions.assertTrue(executed.get());
        CAPTURE_THE_FLAG.unsubscribe(subToken);
    }
    
    @Test
    public void testDestroyUnknownEntities() {
        class Asserter extends AsserterForLoadedLevel {

            public Asserter(
                KObjectRegistry objectRegistry,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                super(objectRegistry, messageSystem, frameTaskExecutor);
            }

            @Override
            protected void check(
                Map<UUID, KControllableEntity> controllables,
                Map<UUID, KStaticEntity> statics,
                Map<UUID, KAutonomousEntity> autonomouses,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                KUniversalMap body = new KUniversalMap();
                body.put("entity_id", UUID.randomUUID());

                messageSystem.deliverMessageSync(KMessage.regular("destroyStaticEntity", body));
                messageSystem.deliverMessageSync(KMessage.regular("destroyControllableEntity", body));
                messageSystem.deliverMessageSync(KMessage.regular("destroyAutonomousEntity", body));

                Assertions.assertEquals(1, statics.size());
                Assertions.assertEquals(1, controllables.size());
                Assertions.assertEquals(1, autonomouses.size());
            }
        }

        class Sender implements KMessageRoutesConfigurer {

            @Override
            public void setupRoutes(KMessageSystem messageSystem) {
                messageSystem.addMessageRoute(
                    "Level.levelLoaded",
                    KMessageSystem.SINK,
                    List.of(Asserter.class)
                );

                var body = new KUniversalMap();
                body.put("level_name", "test_level_entity_management_service");
                body.put("sector", "mf1");
                messageSystem.deliverMessageSync(KMessage.regular("loadLevel", body));
            }

        }

        Konna konna = new Konna(
            new String[0],
            new KonnaBootstrapConfig(
                KStandardArgumentParser.class,
                KEngineHypervisor.class,
                new KEngineHypervisorConfig(
                    KAppContainer.useGenerated(),
                    List.of(TestMessageRouteConfigurer.class, Sender.class),
                    List.of(),
                    List.of(KLevelComponentLoader.class)
                )
            )
        );

        KBooleanReference executed = new KBooleanReference();
        UUID subToken = CAPTURE_THE_FLAG.subscribe(() -> executed.set(true));
        konna.run();
        Assertions.assertTrue(executed.get());
        CAPTURE_THE_FLAG.unsubscribe(subToken);
    }
    
    @Test
    public void testSetDirectionForUnknownControllable() {
        class Asserter extends AsserterForLoadedLevel {

            public Asserter(
                KObjectRegistry objectRegistry,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                super(objectRegistry, messageSystem, frameTaskExecutor);
            }

            @Override
            protected void check(
                Map<UUID, KControllableEntity> controllables,
                Map<UUID, KStaticEntity> statics,
                Map<UUID, KAutonomousEntity> autonomouses,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                KUniversalMap body = new KUniversalMap();
                var id = (UUID)  controllables.keySet().toArray()[0];
                var previousPosition = controllables.get(id).getPosition();

                body.put("entity_id", UUID.randomUUID());
                body.put("direction", new KVector2i(0, 1));
                messageSystem.deliverMessageSync(KMessage.regular("setDirectionForControllableEntity", body));
                messageSystem.deliverMessageSync(KMessage.regular("moveAllEntities", new KUniversalMap()));
                frameTaskExecutor.executeScheduledTasks(KFrameEvent.FRAME_FINISHED);
                
                Assertions.assertEquals(previousPosition,  controllables.get(id).getPosition());

                var autoId = (UUID) autonomouses.keySet().toArray()[0];
                Assertions.assertEquals(new KVector2i(1, 0),  autonomouses.get(autoId).getPosition().first());
                var staticId = (UUID) statics.keySet().toArray()[0];
                Assertions.assertEquals(new KVector2i(0, 1),  statics.get(staticId).getPosition().first());
            }
        }

        class Sender implements KMessageRoutesConfigurer {

            @Override
            public void setupRoutes(KMessageSystem messageSystem) {
                messageSystem.addMessageRoute(
                    "Level.levelLoaded",
                    KMessageSystem.SINK,
                    List.of(Asserter.class)
                );

                var body = new KUniversalMap();
                body.put("level_name", "test_level_entity_management_service");
                body.put("sector", "mf1");
                messageSystem.deliverMessageSync(KMessage.regular("loadLevel", body));
            }

        }

        Konna konna = new Konna(
            new String[0],
            new KonnaBootstrapConfig(
                KStandardArgumentParser.class,
                KEngineHypervisor.class,
                new KEngineHypervisorConfig(
                    KAppContainer.useGenerated(),
                    List.of(TestMessageRouteConfigurer.class, Sender.class),
                    List.of(),
                    List.of(KLevelComponentLoader.class)
                )
            )
        );

        KBooleanReference executed = new KBooleanReference();
        UUID subToken = CAPTURE_THE_FLAG.subscribe(() -> executed.set(true));
        konna.run();
        Assertions.assertTrue(executed.get());
        CAPTURE_THE_FLAG.unsubscribe(subToken);
    }
    
    @Test
    public void testCreateEntitiesWithoutLevel() {
        class Asserter extends AsserterForNotLoadedLevel {

            public Asserter(
                KObjectRegistry objectRegistry,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                super(objectRegistry, messageSystem, frameTaskExecutor);
            }

            @Override
            protected void check(
                Map<UUID, KControllableEntity> controllables,
                Map<UUID, KStaticEntity> statics,
                Map<UUID, KAutonomousEntity> autonomouses,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                KUniversalMap body = new KUniversalMap();
                body.put("name", "synthetic");
                body.put("descriptor", "synthetic");
                body.put("sector_name", "mf99");
                body.put("position", new KVector2i(1, 1));

                messageSystem.deliverMessageSync(KMessage.regular("createStaticEntity", body));
                messageSystem.deliverMessageSync(KMessage.regular("createControllableEntity", body));

                body.put("controller", TestController.class);
                body.put("params", new KMapAssetDefinition(Map.of("test", 42069)));
                messageSystem.deliverMessageSync(KMessage.regular("createAutonomousEntity", body));

                Assertions.assertEquals(0, statics.size());
                Assertions.assertEquals(0, controllables.size());
                Assertions.assertEquals(0, autonomouses.size());
            }
        }

        class Sender implements KMessageRoutesConfigurer {

            @Override
            public void setupRoutes(KMessageSystem messageSystem) {
                messageSystem.addMessageRoute(
                    "fakeLoadLevel",
                    "Level.LevelService.loadLevel",
                    List.of(Asserter.class)
                );

                var body = new KUniversalMap();
                body.put("level_name", "test_level_entity_management_service");
                body.put("sector", "mf1");
                messageSystem.deliverMessageSync(KMessage.regular("fakeLoadLevel", body));
            }

        }

        Konna konna = new Konna(
            new String[0],
            new KonnaBootstrapConfig(
                KStandardArgumentParser.class,
                KEngineHypervisor.class,
                new KEngineHypervisorConfig(
                    KAppContainer.useGenerated(),
                    List.of(TestMessageRouteConfigurer.class, Sender.class),
                    List.of(),
                    List.of(KLevelComponentLoader.class)
                )
            )
        );

        KBooleanReference executed = new KBooleanReference();
        UUID subToken = CAPTURE_THE_FLAG.subscribe(() -> executed.set(true));
        konna.run();
        Assertions.assertTrue(executed.get());
        CAPTURE_THE_FLAG.unsubscribe(subToken);
    }
    
    @Test
    public void testDestroyEntitiesWithoutLevel() {
        class Asserter extends AsserterForNotLoadedLevel {

            public Asserter(
                KObjectRegistry objectRegistry,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                super(objectRegistry, messageSystem, frameTaskExecutor);
            }

            @Override
            protected void check(
                Map<UUID, KControllableEntity> controllables,
                Map<UUID, KStaticEntity> statics,
                Map<UUID, KAutonomousEntity> autonomouses,
                KMessageSystem messageSystem,
                KFrameTaskExecutor frameTaskExecutor
            ) {
                KUniversalMap body = new KUniversalMap();
                body.put("entity_id", UUID.randomUUID());

                messageSystem.deliverMessageSync(KMessage.regular("destroyStaticEntity", body));
                messageSystem.deliverMessageSync(KMessage.regular("destroyControllableEntity", body));
                messageSystem.deliverMessageSync(KMessage.regular("destroyAutonomousEntity", body));

                Assertions.assertEquals(0, statics.size());
                Assertions.assertEquals(0, controllables.size());
                Assertions.assertEquals(0, autonomouses.size());
            }
        }

        class Sender implements KMessageRoutesConfigurer {

            @Override
            public void setupRoutes(KMessageSystem messageSystem) {
                messageSystem.addMessageRoute(
                    "fakeLoadLevel",
                    "Level.LevelService.loadLevel",
                    List.of(Asserter.class)
                );

                var body = new KUniversalMap();
                body.put("level_name", "test_level_entity_management_service");
                body.put("sector", "mf1");
                messageSystem.deliverMessageSync(KMessage.regular("fakeLoadLevel", body));
            }

        }

        Konna konna = new Konna(
            new String[0],
            new KonnaBootstrapConfig(
                KStandardArgumentParser.class,
                KEngineHypervisor.class,
                new KEngineHypervisorConfig(
                    KAppContainer.useGenerated(),
                    List.of(TestMessageRouteConfigurer.class, Sender.class),
                    List.of(),
                    List.of(KLevelComponentLoader.class)
                )
            )
        );

        KBooleanReference executed = new KBooleanReference();
        UUID subToken = CAPTURE_THE_FLAG.subscribe(() -> executed.set(true));
        konna.run();
        Assertions.assertTrue(executed.get());
        CAPTURE_THE_FLAG.unsubscribe(subToken);
    }

}



