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
import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.engine.KEngineContext;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisor;
import io.github.darthakiranihil.konna.core.except.KException;
import io.github.darthakiranihil.konna.core.message.KMessage;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import io.github.darthakiranihil.konna.entity.KEntity;
import io.github.darthakiranihil.konna.entity.impl.TestEntityDataComponent;
import io.github.darthakiranihil.konna.entity.impl.TestEntityDataComponent2;
import io.github.darthakiranihil.konna.entity.type.KEntityMetadataTypedef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class KEntityManagementServicePositiveTests extends KStandardTestClass {

    private final Method shutdown;
    private final Field hypervisor;
    private final Field ctx;

    public KEntityManagementServicePositiveTests() {

        try {
            this.shutdown = Konna.class.getDeclaredMethod("shutdown");
            this.shutdown.setAccessible(true);

            this.hypervisor = Konna.class.getDeclaredField("hypervisor");
            this.hypervisor.setAccessible(true);

            this.ctx = KEngineHypervisor.class.getDeclaredField("ctx");
            this.ctx.setAccessible(true);
        } catch (Throwable e) {
            throw new KException(e);
        }

        // KStandardTestClass.context.addAssetTypedef(new KEntityMetadataTypedef());

    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCreateEntity() {

        try {

            Konna konnaWithOnlyDefaultArgs = new Konna(new String[0]);
            konnaWithOnlyDefaultArgs.run();

            TimeUnit.SECONDS.sleep(1);
            System.out.println("GECON");
            KEngineContext realContext = (KEngineContext) this.ctx.get(this.hypervisor.get(konnaWithOnlyDefaultArgs));

            Field activeEntities = KEntityManagementService.class.getDeclaredField("activeEntities");
            Field inactiveEntities = KEntityManagementService.class.getDeclaredField("inactiveEntities");
            activeEntities.setAccessible(true);
            inactiveEntities.setAccessible(true);



            var body = new KUniversalMap();
            body.put("name", "E1");
            body.put("type", "Typpi3");

            realContext.deliverMessageSync(KMessage.regular("createEntity", body));

            var service = realContext
                .listObjects()
                .stream()
                .filter(o -> o.object().name().equals("Entity.EntityManagementService"))
                .findFirst();

            Assertions.assertTrue(service.isPresent());

            var active = (Map<UUID, KEntity>) activeEntities.get(service.get().object());
            var inactive = (Map<UUID, KEntity>) inactiveEntities.get(service.get().object());

            Assertions.assertEquals(1, active.size());
            Assertions.assertEquals(0, inactive.size());

            UUID createdId = (UUID) active.keySet().toArray()[0];
            Assertions.assertEquals("E1", active.get(createdId).name());
            Assertions.assertEquals("Typpi3", active.get(createdId).type());

            // Assertions.assertDoesNotThrow(() -> this.shutdown.invoke(konnaWithOnlyDefaultArgs));

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }

    @Test
    @SuppressWarnings("unchecked")
    public void testRestoreEntity() {

        try {

            Konna konnaWithOnlyDefaultArgs = new Konna(new String[0]);
            konnaWithOnlyDefaultArgs.run();

            TimeUnit.SECONDS.sleep(1);
            System.out.println("GECON");
            KEngineContext realContext = (KEngineContext) this.ctx.get(this.hypervisor.get(konnaWithOnlyDefaultArgs));

            Field activeEntities = KEntityManagementService.class.getDeclaredField("activeEntities");
            Field inactiveEntities = KEntityManagementService.class.getDeclaredField("inactiveEntities");
            activeEntities.setAccessible(true);
            inactiveEntities.setAccessible(true);



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

            realContext.deliverMessageSync(KMessage.regular("restoreEntity", body));

            var service = realContext
                .listObjects()
                .stream()
                .filter(o -> o.object().name().equals("Entity.EntityManagementService"))
                .findFirst();

            Assertions.assertTrue(service.isPresent());

            var active = (Map<UUID, KEntity>) activeEntities.get(service.get().object());
            var inactive = (Map<UUID, KEntity>) inactiveEntities.get(service.get().object());

            Assertions.assertEquals(1, active.size());
            Assertions.assertEquals(0, inactive.size());

            UUID createdId = (UUID) active.keySet().toArray()[0];
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

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDeactivateAndActivateEntity() {

        try {

            Konna konnaWithOnlyDefaultArgs = new Konna(new String[0]);
            konnaWithOnlyDefaultArgs.run();

            TimeUnit.SECONDS.sleep(1);
            System.out.println("GECON");
            KEngineContext realContext = (KEngineContext) this.ctx.get(this.hypervisor.get(konnaWithOnlyDefaultArgs));

            Field activeEntities = KEntityManagementService.class.getDeclaredField("activeEntities");
            Field inactiveEntities = KEntityManagementService.class.getDeclaredField("inactiveEntities");
            activeEntities.setAccessible(true);
            inactiveEntities.setAccessible(true);



            var body = new KUniversalMap();
            body.put("name", "E1");
            body.put("type", "Typpi3");

            realContext.deliverMessageSync(KMessage.regular("createEntity", body));

            var service = realContext
                .listObjects()
                .stream()
                .filter(o -> o.object().name().equals("Entity.EntityManagementService"))
                .findFirst();

            Assertions.assertTrue(service.isPresent());

            var active = (Map<UUID, KEntity>) activeEntities.get(service.get().object());
            var inactive = (Map<UUID, KEntity>) inactiveEntities.get(service.get().object());

            Assertions.assertEquals(1, active.size());
            Assertions.assertEquals(0, inactive.size());

            UUID createdId = (UUID) active.keySet().toArray()[0];
            KEntity created = active.get(createdId);
            Assertions.assertNotNull(created);
            Assertions.assertEquals("E1", created.name());
            Assertions.assertEquals("Typpi3", created.type());

            KUniversalMap entityIdBody = new KUniversalMap();
            entityIdBody.put("entity_id", createdId);
            KUniversalMap nonExistentEntityIdBody = new KUniversalMap();
            nonExistentEntityIdBody.put("entity_id", UUID.randomUUID());

            realContext.deliverMessageSync(KMessage.regular("deactivateEntity", entityIdBody));
            realContext.deliverMessageSync(KMessage.regular("deactivateEntity", nonExistentEntityIdBody));
            Assertions.assertEquals(0, active.size());
            Assertions.assertEquals(1, inactive.size());

            realContext.deliverMessageSync(KMessage.regular("deactivateEntity", entityIdBody));
            Assertions.assertEquals(0, active.size());
            Assertions.assertEquals(1, inactive.size());

            realContext.deliverMessageSync(KMessage.regular("activateEntity", entityIdBody));
            realContext.deliverMessageSync(KMessage.regular("activateEntity", nonExistentEntityIdBody));
            Assertions.assertEquals(1, active.size());
            Assertions.assertEquals(0, inactive.size());

            realContext.deliverMessageSync(KMessage.regular("activateEntity", entityIdBody));
            Assertions.assertEquals(1, active.size());
            Assertions.assertEquals(0, inactive.size());

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDestroyActiveEntity() {

        try {

            Konna konnaWithOnlyDefaultArgs = new Konna(new String[0]);
            konnaWithOnlyDefaultArgs.run();

            TimeUnit.SECONDS.sleep(1);
            System.out.println("GECON");
            KEngineContext realContext = (KEngineContext) this.ctx.get(this.hypervisor.get(konnaWithOnlyDefaultArgs));

            Field activeEntities = KEntityManagementService.class.getDeclaredField("activeEntities");
            Field inactiveEntities = KEntityManagementService.class.getDeclaredField("inactiveEntities");
            activeEntities.setAccessible(true);
            inactiveEntities.setAccessible(true);

            var body = new KUniversalMap();
            body.put("name", "E1");
            body.put("type", "Typpi3");

            realContext.deliverMessageSync(KMessage.regular("createEntity", body));

            var service = realContext
                .listObjects()
                .stream()
                .filter(o -> o.object().name().equals("Entity.EntityManagementService"))
                .findFirst();

            Assertions.assertTrue(service.isPresent());

            var active = (Map<UUID, KEntity>) activeEntities.get(service.get().object());
            var inactive = (Map<UUID, KEntity>) inactiveEntities.get(service.get().object());

            Assertions.assertEquals(1, active.size());
            Assertions.assertEquals(0, inactive.size());

            UUID createdId = (UUID) active.keySet().toArray()[0];
            KEntity created = active.get(createdId);
            Assertions.assertNotNull(created);
            Assertions.assertEquals("E1", created.name());
            Assertions.assertEquals("Typpi3", created.type());

            KUniversalMap entityIdBody = new KUniversalMap();
            entityIdBody.put("entity_id", createdId);

            realContext.deliverMessageSync(KMessage.regular("destroyEntity", entityIdBody));
            Assertions.assertEquals(0, active.size());
            Assertions.assertEquals(0, inactive.size());

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDestroyInactiveEntity() {

        try {

            Konna konnaWithOnlyDefaultArgs = new Konna(new String[0]);
            konnaWithOnlyDefaultArgs.run();

            TimeUnit.SECONDS.sleep(1);
            System.out.println("GECON");
            KEngineContext realContext = (KEngineContext) this.ctx.get(this.hypervisor.get(konnaWithOnlyDefaultArgs));

            Field activeEntities = KEntityManagementService.class.getDeclaredField("activeEntities");
            Field inactiveEntities = KEntityManagementService.class.getDeclaredField("inactiveEntities");
            activeEntities.setAccessible(true);
            inactiveEntities.setAccessible(true);

            var body = new KUniversalMap();
            body.put("name", "E1");
            body.put("type", "Typpi3");

            realContext.deliverMessageSync(KMessage.regular("createEntity", body));

            var service = realContext
                .listObjects()
                .stream()
                .filter(o -> o.object().name().equals("Entity.EntityManagementService"))
                .findFirst();

            Assertions.assertTrue(service.isPresent());

            var active = (Map<UUID, KEntity>) activeEntities.get(service.get().object());
            var inactive = (Map<UUID, KEntity>) inactiveEntities.get(service.get().object());

            Assertions.assertEquals(1, active.size());
            Assertions.assertEquals(0, inactive.size());

            UUID createdId = (UUID) active.keySet().toArray()[0];
            KEntity created = active.get(createdId);
            Assertions.assertNotNull(created);
            Assertions.assertEquals("E1", created.name());
            Assertions.assertEquals("Typpi3", created.type());

            KUniversalMap entityIdBody = new KUniversalMap();
            entityIdBody.put("entity_id", createdId);

            realContext.deliverMessageSync(KMessage.regular("deactivateEntity", entityIdBody));
            Assertions.assertEquals(0, active.size());
            Assertions.assertEquals(1, inactive.size());

            realContext.deliverMessageSync(KMessage.regular("destroyEntity", entityIdBody));
            Assertions.assertEquals(0, active.size());
            Assertions.assertEquals(0, inactive.size());

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDestroyNonExistentEntity() {

        try {

            Konna konnaWithOnlyDefaultArgs = new Konna(new String[0]);
            konnaWithOnlyDefaultArgs.run();

            TimeUnit.SECONDS.sleep(1);
            KEngineContext realContext = (KEngineContext) this.ctx.get(this.hypervisor.get(konnaWithOnlyDefaultArgs));

            Field activeEntities = KEntityManagementService.class.getDeclaredField("activeEntities");
            Field inactiveEntities = KEntityManagementService.class.getDeclaredField("inactiveEntities");
            activeEntities.setAccessible(true);
            inactiveEntities.setAccessible(true);

            var service = realContext
                .listObjects()
                .stream()
                .filter(o -> o.object().name().equals("Entity.EntityManagementService"))
                .findFirst();

            Assertions.assertTrue(service.isPresent());

            var active = (Map<UUID, KEntity>) activeEntities.get(service.get().object());
            var inactive = (Map<UUID, KEntity>) inactiveEntities.get(service.get().object());

            KUniversalMap entityIdBody = new KUniversalMap();
            entityIdBody.put("entity_id", UUID.randomUUID());

            realContext.deliverMessageSync(KMessage.regular("destroyEntity", entityIdBody));
            Assertions.assertEquals(0, active.size());
            Assertions.assertEquals(0, inactive.size());

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }


}
