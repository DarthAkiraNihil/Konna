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

package io.github.darthakiranihil.konna.entity.service;

import io.github.darthakiranihil.konna.core.Konna;
import io.github.darthakiranihil.konna.core.KonnaBootstrapConfig;
import io.github.darthakiranihil.konna.core.app.KStandardArgumentParser;
import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.di.KAppContainer;
import io.github.darthakiranihil.konna.core.di.KEngineModule;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisor;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisorConfig;
import io.github.darthakiranihil.konna.core.engine.KService;
import io.github.darthakiranihil.konna.core.message.*;
import io.github.darthakiranihil.konna.core.object.KObjectRegistry;
import io.github.darthakiranihil.konna.core.struct.ref.KBooleanReference;
import io.github.darthakiranihil.konna.core.util.KReflectionUtils;
import io.github.darthakiranihil.konna.entity.KEntity;
import io.github.darthakiranihil.konna.entity.KEntityComponentLoader;
import io.github.darthakiranihil.konna.entity.impl.TestEntityDataComponent;
import io.github.darthakiranihil.konna.entity.impl.TestEntityDataComponent2;
import io.github.darthakiranihil.konna.entity.impl.TestMessageRouteConfigurer;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("unchecked")
public class KEntityManagementServicePositiveTests extends KStandardTestClass {

    private static final KSimpleEvent CAPTURE_THE_FLAG = new KSimpleEvent("captureTheFlag");

    private static void assertMessage(KMessage message) {
        Assertions.assertEquals("Entity.entityCreated", message.messageId());
        KUniversalMap body = message.body();
        Assertions.assertNotNull(body.getSafe("id", UUID.class));
        Assertions.assertNotNull(body.getSafe("type", String.class));
        Assertions.assertEquals("Typpi3", body.get("type", String.class));
        Assertions.assertNotNull(body.getSafe("name", String.class));
        Assertions.assertEquals("E1", body.get("name", String.class));
        Assertions.assertNotNull(body.getSafe("instance", KEntity.class));

    }

    private static KService assertService(
        KObjectRegistry objectRegistry
    ) {
        var packedService = objectRegistry
            .getObjectsOfType(KService.class)
            .stream()
            .filter(o -> ((KService) o.getCastObject()).name().equals("EntityManagementService"))
            .findFirst();

        Assertions.assertTrue(packedService.isPresent());
        return packedService.get().getCastObject();
    }

    private final Field activeEntities;
    private final Field inactiveEntities;

    public KEntityManagementServicePositiveTests() {

        Field activeEntitiesField = KReflectionUtils.getField(KEntityManagementService.class, "activeEntities");
        Assertions.assertNotNull(activeEntitiesField);
        this.activeEntities = activeEntitiesField;

        Field inactiveEntitiesField = KReflectionUtils.getField(KEntityManagementService.class, "inactiveEntities");
        Assertions.assertNotNull(inactiveEntitiesField);
        this.inactiveEntities = inactiveEntitiesField;

    }

    private void runTest(
        KMessageRoutesConfigurer sender
    ) {
        Konna konnaWithOnlyDefaultArgs = new Konna(
            new String[0],
            new KonnaBootstrapConfig(
                KStandardArgumentParser.class,
                KEngineHypervisor.class,
                new KEngineHypervisorConfig(
                    KAppContainer.useGenerated(),
                    List.of(
                    TestMessageRouteConfigurer.class,
                    sender.getClass()
                ),
                List.of(),
                List.of(KEntityComponentLoader.class)
            )
        ));
        konnaWithOnlyDefaultArgs.run();
    }

    @Test
    public void testCreateEntity() {

        KBooleanReference executed = new KBooleanReference();
        KEngineModule engineModule = KStandardTestClass.getModule();
        KEventSystem eventSystem = engineModule.eventSystem();
        eventSystem.registerEvent(CAPTURE_THE_FLAG);
        KSimpleEventSubscriber captureTheFlag = eventSystem.getSimpleEventSubscriber("captureTheFlag");
        UUID subToken = captureTheFlag.subscribe(() -> executed.set(true));

        class Asserter implements KTunnel {

            private final KObjectRegistry objectRegistry;

            @KInject
            public Asserter(KObjectRegistry objectRegistry) {
                this.objectRegistry = objectRegistry;
            }

            @Override
            public KMessage processMessage(KMessage message) {

                assertMessage(message);
                KService service = assertService(this.objectRegistry);

                var active = (Map<UUID, KEntity>) KReflectionUtils.getFieldValue(activeEntities, service);
                var inactive = (Map<UUID, KEntity>) KReflectionUtils.getFieldValue(inactiveEntities, service);

                Assertions.assertNotNull(active);
                Assertions.assertNotNull(inactive);

                Assertions.assertEquals(1, active.size());
                Assertions.assertEquals(0, inactive.size());

                UUID createdId = message.body().get("id", UUID.class);
                Assertions.assertEquals("E1", active.get(createdId).name());
                Assertions.assertEquals("Typpi3", active.get(createdId).type());

                CAPTURE_THE_FLAG.invokeSync();

                return message;
            }

        }

        this.runTest(
            (ms) -> {
                ms.addMessageRoute("Entity.entityCreated", KMessageSystem.SINK, List.of(Asserter.class));

                var body = new KUniversalMap();
                body.put("name", "E1");
                body.put("type", "Typpi3");

                ms.deliverMessageSync(KMessage.regular("createEntity", body));
            }
        );

        Assertions.assertTrue(executed.get());
        captureTheFlag.unsubscribe(subToken);

    }


    @Test
    @SuppressWarnings("unchecked")
    public void testRestoreEntity() {

        KBooleanReference executed = new KBooleanReference();
        KEngineModule engineModule = KStandardTestClass.getModule();
        KEventSystem eventSystem = engineModule.eventSystem();
        eventSystem.registerEvent(CAPTURE_THE_FLAG);
        KSimpleEventSubscriber captureTheFlag = eventSystem.getSimpleEventSubscriber("captureTheFlag");
        UUID subToken = captureTheFlag.subscribe(() -> executed.set(true));

        class Asserter implements KTunnel {

            private final KObjectRegistry objectRegistry;

            @KInject
            public Asserter(KObjectRegistry objectRegistry) {
                this.objectRegistry = objectRegistry;
            }

            @Override
            public KMessage processMessage(KMessage message) {

                assertMessage(message);
                KService service = assertService(this.objectRegistry);

                var active = (Map<UUID, KEntity>) KReflectionUtils.getFieldValue(activeEntities, service);
                var inactive = (Map<UUID, KEntity>) KReflectionUtils.getFieldValue(inactiveEntities, service);

                Assertions.assertNotNull(active);
                Assertions.assertNotNull(inactive);

                Assertions.assertEquals(1, active.size());
                Assertions.assertEquals(0, inactive.size());

                UUID createdId = message.body().get("id", UUID.class);
                KEntity created = active.get(createdId);
                Assertions.assertNotNull(created);
                Assertions.assertEquals("E1", created.name());
                Assertions.assertEquals("Typpi3", created.type());

                TestEntityDataComponent tedc1 = created.getComponent(TestEntityDataComponent.class);
                TestEntityDataComponent2 tedc2 = created.getComponent(TestEntityDataComponent2.class);

                Assertions.assertNotNull(tedc1);
                Assertions.assertNotNull(tedc2);
                Assertions.assertEquals(1, tedc1.getTestField());
                Assertions.assertEquals("123", tedc2.getStringField());

                CAPTURE_THE_FLAG.invokeSync();
                return message;
            }
        }

        this.runTest(
            (ms) -> {
                ms.addMessageRoute("Entity.entityCreated", KMessageSystem.SINK, List.of(Asserter.class));

                var body = new KUniversalMap();
                body.put("name", "E1");
                body.put("type", "Typpi3");
                body.put(
                    "data",
                    KJsonValue.fromMap(
                        Map.of(
                            TestEntityDataComponent.class.getCanonicalName(),
                            KJsonValue.fromMap(
                                Map.of(
                                    "testField", KJsonValue.fromNumber(1)
                                )
                            ),
                            TestEntityDataComponent2.class.getCanonicalName(),
                            KJsonValue.fromMap(
                                Map.of(
                                    "stringField", KJsonValue.fromString("123")
                                )
                            )
                        )
                    )
                );

                ms.deliverMessageSync(KMessage.regular("restoreEntity", body));
            }
        );

        Assertions.assertTrue(executed.get());
        captureTheFlag.unsubscribe(subToken);

    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDeactivateAndActivateEntity() {

        KBooleanReference executed = new KBooleanReference();
        KEngineModule engineModule = KStandardTestClass.getModule();
        KEventSystem eventSystem = engineModule.eventSystem();
        eventSystem.registerEvent(CAPTURE_THE_FLAG);
        KSimpleEventSubscriber captureTheFlag = eventSystem.getSimpleEventSubscriber("captureTheFlag");
        UUID subToken = captureTheFlag.subscribe(() -> executed.set(true));

        class Asserter implements KTunnel {

            private final KObjectRegistry objectRegistry;
            private final KMessageSystem messageSystem;


            @KInject
            public Asserter(
                KObjectRegistry objectRegistry,
                KMessageSystem messageSystem
            ) {
                this.objectRegistry = objectRegistry;
                this.messageSystem = messageSystem;
            }

            @Override
            public KMessage processMessage(KMessage message) {
                assertMessage(message);
                KService service = assertService(this.objectRegistry);

                var active = (Map<UUID, KEntity>) KReflectionUtils.getFieldValue(activeEntities, service);
                var inactive = (Map<UUID, KEntity>) KReflectionUtils.getFieldValue(inactiveEntities, service);
                Assertions.assertNotNull(active);
                Assertions.assertNotNull(inactive);

                Assertions.assertEquals(1, active.size());
                Assertions.assertEquals(0, inactive.size());

                UUID createdId = message.body().get("id", UUID.class);
                KEntity created = active.get(createdId);
                Assertions.assertNotNull(created);
                Assertions.assertEquals("E1", created.name());
                Assertions.assertEquals("Typpi3", created.type());

                KUniversalMap entityIdBody = new KUniversalMap();
                entityIdBody.put("entity_id", createdId);
                KUniversalMap nonExistentEntityIdBody = new KUniversalMap();
                nonExistentEntityIdBody.put("entity_id", UUID.randomUUID());

                this.messageSystem.deliverMessageSync(KMessage.regular("deactivateEntity", entityIdBody));
                this.messageSystem.deliverMessageSync(KMessage.regular("deactivateEntity", nonExistentEntityIdBody));
                this.messageSystem.deliverMessageSync(KMessage.regular("deactivateEntity", entityIdBody));

                Assertions.assertEquals(0, active.size());
                Assertions.assertEquals(1, inactive.size());

                this.messageSystem.deliverMessageSync(KMessage.regular("activateEntity", entityIdBody));
                this.messageSystem.deliverMessageSync(KMessage.regular("activateEntity", nonExistentEntityIdBody));
                this.messageSystem.deliverMessageSync(KMessage.regular("activateEntity", entityIdBody));

                Assertions.assertEquals(1, active.size());
                Assertions.assertEquals(0, inactive.size());

                CAPTURE_THE_FLAG.invokeSync();
                return message;
            }

        }

        this.runTest(
            (ms) -> {
                ms.addMessageRoute("Entity.entityCreated", KMessageSystem.SINK, List.of(Asserter.class));
                ms.addMessageRoute("Entity.entityActivated", KMessageSystem.SINK, List.of());
                ms.addMessageRoute("Entity.entityDeactivated", KMessageSystem.SINK, List.of());

                var body = new KUniversalMap();
                body.put("name", "E1");
                body.put("type", "Typpi3");

                ms.deliverMessageSync(KMessage.regular("createEntity", body));
            }
        );

        Assertions.assertTrue(executed.get());
        captureTheFlag.unsubscribe(subToken);

    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDestroyActiveEntity() {

        KBooleanReference executed = new KBooleanReference();
        KEngineModule engineModule = KStandardTestClass.getModule();
        KEventSystem eventSystem = engineModule.eventSystem();
        eventSystem.registerEvent(CAPTURE_THE_FLAG);
        KSimpleEventSubscriber captureTheFlag = eventSystem.getSimpleEventSubscriber("captureTheFlag");
        UUID subToken = captureTheFlag.subscribe(() -> executed.set(true));


        class Asserter implements KTunnel {

            private final KObjectRegistry objectRegistry;
            private final KMessageSystem messageSystem;

            @KInject
            public Asserter(KObjectRegistry objectRegistry, KMessageSystem messageSystem) {
                this.objectRegistry = objectRegistry;
                this.messageSystem = messageSystem;
            }

            @Override
            public KMessage processMessage(KMessage message) {
                assertMessage(message);
                KService service = assertService(this.objectRegistry);

                var active = (Map<UUID, KEntity>) KReflectionUtils.getFieldValue(activeEntities, service);
                var inactive = (Map<UUID, KEntity>) KReflectionUtils.getFieldValue(inactiveEntities, service);
                Assertions.assertNotNull(active);
                Assertions.assertNotNull(inactive);

                Assertions.assertEquals(1, active.size());
                Assertions.assertEquals(0, inactive.size());

                UUID createdId = message.body().get("id", UUID.class);
                KEntity created = active.get(createdId);
                Assertions.assertNotNull(created);
                Assertions.assertEquals("E1", created.name());
                Assertions.assertEquals("Typpi3", created.type());

                KUniversalMap entityIdBody = new KUniversalMap();
                entityIdBody.put("entity_id", createdId);

                this.messageSystem.deliverMessageSync(KMessage.regular("destroyEntity", entityIdBody));
                Assertions.assertEquals(0, active.size());
                Assertions.assertEquals(0, inactive.size());

                CAPTURE_THE_FLAG.invokeSync();
                return message;
            }
        }

        this.runTest(
            (ms) -> {
                ms.addMessageRoute("Entity.entityCreated", KMessageSystem.SINK, List.of(Asserter.class));
                ms.addMessageRoute("Entity.entityDestroyed", KMessageSystem.SINK, List.of());

                var body = new KUniversalMap();
                body.put("name", "E1");
                body.put("type", "Typpi3");

                ms.deliverMessageSync(KMessage.regular("createEntity", body));
            }
        );

        Assertions.assertTrue(executed.get());
        captureTheFlag.unsubscribe(subToken);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDestroyInactiveEntity() {

        KBooleanReference executed = new KBooleanReference();
        KEngineModule engineModule = KStandardTestClass.getModule();
        KEventSystem eventSystem = engineModule.eventSystem();
        eventSystem.registerEvent(CAPTURE_THE_FLAG);
        KSimpleEventSubscriber captureTheFlag = eventSystem.getSimpleEventSubscriber("captureTheFlag");
        UUID subToken = captureTheFlag.subscribe(() -> executed.set(true));

        class Asserter implements KTunnel {

            private final KObjectRegistry objectRegistry;
            private final KMessageSystem messageSystem;

            @KInject
            public Asserter(KObjectRegistry objectRegistry, KMessageSystem messageSystem) {
                this.objectRegistry = objectRegistry;
                this.messageSystem = messageSystem;
            }

            @Override
            public KMessage processMessage(KMessage message) {
                assertMessage(message);
                KService service = assertService(this.objectRegistry);

                var active = (Map<UUID, KEntity>) KReflectionUtils.getFieldValue(activeEntities, service);
                var inactive = (Map<UUID, KEntity>) KReflectionUtils.getFieldValue(inactiveEntities, service);
                Assertions.assertNotNull(active);
                Assertions.assertNotNull(inactive);

                Assertions.assertEquals(1, active.size());
                Assertions.assertEquals(0, inactive.size());

                UUID createdId = message.body().get("id", UUID.class);
                KEntity created = active.get(createdId);
                Assertions.assertNotNull(created);
                Assertions.assertEquals("E1", created.name());
                Assertions.assertEquals("Typpi3", created.type());

                KUniversalMap entityIdBody = new KUniversalMap();
                entityIdBody.put("entity_id", createdId);

                messageSystem.deliverMessageSync(KMessage.regular("deactivateEntity", entityIdBody));
                Assertions.assertEquals(0, active.size());
                Assertions.assertEquals(1, inactive.size());

                this.messageSystem.deliverMessageSync(KMessage.regular("destroyEntity", entityIdBody));
                Assertions.assertEquals(0, active.size());
                Assertions.assertEquals(0, inactive.size());

                CAPTURE_THE_FLAG.invokeSync();
                return message;
            }
        }

        this.runTest(
            (ms) -> {
                ms.addMessageRoute("Entity.entityCreated", KMessageSystem.SINK, List.of(Asserter.class));
                ms.addMessageRoute("Entity.entityDestroyed", KMessageSystem.SINK, List.of());

                var body = new KUniversalMap();
                body.put("name", "E1");
                body.put("type", "Typpi3");

                ms.deliverMessageSync(KMessage.regular("createEntity", body));
            }
        );

        Assertions.assertTrue(executed.get());
        captureTheFlag.unsubscribe(subToken);

    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDestroyNonExistentEntity() {

        KBooleanReference executed = new KBooleanReference();
        KEngineModule engineModule = KStandardTestClass.getModule();
        KEventSystem eventSystem = engineModule.eventSystem();
        eventSystem.registerEvent(CAPTURE_THE_FLAG);
        KSimpleEventSubscriber captureTheFlag = eventSystem.getSimpleEventSubscriber("captureTheFlag");
        UUID subToken = captureTheFlag.subscribe(() -> executed.set(true));

        class Asserter implements KTunnel {

            private final KObjectRegistry objectRegistry;
            private final KMessageSystem messageSystem;

            @KInject
            public Asserter(KObjectRegistry objectRegistry, KMessageSystem messageSystem) {
                this.objectRegistry = objectRegistry;
                this.messageSystem = messageSystem;
            }

            @Override
            public KMessage processMessage(KMessage message) {
                KService service = assertService(this.objectRegistry);

                var active = (Map<UUID, KEntity>) KReflectionUtils.getFieldValue(activeEntities, service);
                var inactive = (Map<UUID, KEntity>) KReflectionUtils.getFieldValue(inactiveEntities, service);
                Assertions.assertNotNull(active);
                Assertions.assertNotNull(inactive);

                KUniversalMap entityIdBody = new KUniversalMap();
                entityIdBody.put("entity_id", UUID.randomUUID());

                this.messageSystem.deliverMessageSync(KMessage.regular("destroyEntity", entityIdBody));
                Assertions.assertEquals(0, active.size());
                Assertions.assertEquals(0, inactive.size());

                CAPTURE_THE_FLAG.invokeSync();
                return KMessage.DROP;
            }
        }

        this.runTest(
            (ms) -> {
                ms.addMessageRoute("Entity.entityCreated", KMessageSystem.SINK, List.of(Asserter.class));
                ms.addMessageRoute("Entity.entityDestroyed", KMessageSystem.SINK, List.of());

                ms.addMessageRoute(
                    "fakeCreateEntity",
                    "Entity.EntityManagementService.createEntity",
                    List.of(Asserter.class)
                );

                var body = new KUniversalMap();
                body.put("name", "E1");
                body.put("type", "Typpi3");
                ms.deliverMessageSync(KMessage.regular("fakeCreateEntity", body));
            }
        );

        Assertions.assertTrue(executed.get());
        captureTheFlag.unsubscribe(subToken);

    }


}
