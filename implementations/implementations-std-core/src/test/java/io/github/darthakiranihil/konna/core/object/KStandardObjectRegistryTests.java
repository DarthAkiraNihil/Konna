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

package io.github.darthakiranihil.konna.core.object;

import io.github.darthakiranihil.konna.core.app.KFrameTaskSystem;
import io.github.darthakiranihil.konna.core.app.KStandardFrameTaskSystem;
import io.github.darthakiranihil.konna.core.object.except.KDeletedObjectException;
import io.github.darthakiranihil.konna.core.util.KThreadUtils;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class KStandardObjectRegistryTests extends KStandardTestClass {

    private static final class TestObject {

    }

    private static final class TestKObject extends KObject {
        public TestKObject() {
            super("TestKObject", Set.of("Aboba"));
        }
    }

    private final KObjectRegistry objectRegistry;

    public KStandardObjectRegistryTests() {
        KFrameTaskSystem frameTaskSystem = new KStandardFrameTaskSystem();
        this.objectRegistry = new KStandardObjectRegistry(frameTaskSystem);
    }

    @Test
    public void testPutObject() {
        TestKObject object = new TestKObject();
        KObjectRegistryRecord created = this.objectRegistry.pushObject(object);
        KObjectRegistryRecord stored = this.objectRegistry.getObject(created.objectId());
        Assertions.assertNotNull(stored);

        Assertions.assertEquals(created.objectId(), stored.objectId());
        Assertions.assertEquals(created.recordId(), stored.recordId());
        Assertions.assertNotEquals(stored.objectId(), stored.recordId());

        Assertions.assertFalse(stored.isSynthetic());
        Assertions.assertFalse(stored.isImmortal());
        Assertions.assertTrue(stored.isPresent());
        Assertions.assertEquals(TestKObject.class, stored.getObjectClass());
        Assertions.assertDoesNotThrow(stored::getObject);
        Assertions.assertDoesNotThrow(() -> {
            KObject _c = stored.getCastObject();
        });

        Assertions.assertTrue(stored.getObjectTags().contains("Aboba"));
        stored.toString();
    }

    @Test
    public void testPutImmortalObject() {
        TestKObject object = new TestKObject();
        KObjectRegistryRecord created = this.objectRegistry.pushImmortalObject(object);
        KObjectRegistryRecord stored = this.objectRegistry.getObject(created.objectId());
        Assertions.assertNotNull(stored);

        Assertions.assertEquals(created.objectId(), stored.objectId());
        Assertions.assertEquals(created.recordId(), stored.recordId());
        Assertions.assertNotEquals(stored.objectId(), stored.recordId());

        Assertions.assertFalse(stored.isSynthetic());
        Assertions.assertTrue(stored.isImmortal());
        Assertions.assertTrue(stored.isPresent());
        Assertions.assertEquals(TestKObject.class, stored.getObjectClass());
        Assertions.assertDoesNotThrow(stored::getObject);
        Assertions.assertDoesNotThrow(() -> {
            KObject _c = stored.getCastObject();
        });

        Assertions.assertTrue(stored.getObjectTags().contains("Aboba"));
        stored.toString();
    }

    @Test
    public void testPutSyntheticObject() {
        TestObject object = new TestObject();
        KObjectRegistryRecord created = this.objectRegistry.pushObject(object);
        KObjectRegistryRecord stored = this.objectRegistry.getObject(created.objectId());
        Assertions.assertNotNull(stored);

        Assertions.assertEquals(created.objectId(), stored.objectId());
        Assertions.assertEquals(created.recordId(), stored.recordId());
        Assertions.assertEquals(stored.objectId(), stored.recordId());

        Assertions.assertTrue(stored.isSynthetic());
        Assertions.assertFalse(stored.isImmortal());
        Assertions.assertTrue(stored.isPresent());
        Assertions.assertEquals(TestObject.class, stored.getObjectClass());
        Assertions.assertDoesNotThrow(stored::getObject);
        Assertions.assertDoesNotThrow(() -> {
            TestObject _c = stored.getCastObject();
        });

        Assertions.assertTrue(stored.getObjectTags().contains("synthetic"));
        stored.toString();
    }

    @Test
    public void testPutSyntheticImmortalObject() {
        TestObject object = new TestObject();
        KObjectRegistryRecord created = this.objectRegistry.pushImmortalObject(object);
        KObjectRegistryRecord stored = this.objectRegistry.getObject(created.objectId());
        Assertions.assertNotNull(stored);

        Assertions.assertEquals(created.objectId(), stored.objectId());
        Assertions.assertEquals(created.recordId(), stored.recordId());
        Assertions.assertEquals(stored.objectId(), stored.recordId());

        Assertions.assertTrue(stored.isSynthetic());
        Assertions.assertTrue(stored.isImmortal());
        Assertions.assertTrue(stored.isPresent());
        Assertions.assertEquals(TestObject.class, stored.getObjectClass());
        Assertions.assertDoesNotThrow(stored::getObject);
        Assertions.assertDoesNotThrow(() -> {
            TestObject _c = stored.getCastObject();
        });

        Assertions.assertTrue(stored.getObjectTags().contains("synthetic"));
        stored.toString();
    }

    @Test
    public void testPutObjectButItsDeleted() {
        KObjectRegistryRecord created = this.objectRegistry.pushObject(new TestKObject());
        KObjectRegistryRecord stored = this.objectRegistry.getObject(created.objectId());
        Assertions.assertNotNull(stored);
        System.gc();
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(created.objectId(), stored.objectId());
        Assertions.assertEquals(created.recordId(), stored.recordId());
        Assertions.assertNotEquals(stored.objectId(), stored.recordId());

        Assertions.assertFalse(stored.isSynthetic());
        Assertions.assertFalse(stored.isImmortal());
        Assertions.assertFalse(stored.isPresent());
        Assertions.assertEquals(TestKObject.class, stored.getObjectClass());
        Assertions.assertThrows(KDeletedObjectException.class, stored::getObject);
        Assertions.assertThrows(
            KDeletedObjectException.class,
            () -> { KObject _c = stored.getCastObject(); }
        );

        Assertions.assertTrue(stored.getObjectTags().contains("Aboba"));
        stored.toString();
    }

    @Test
    public void testPutImmortalObjectButItsDeleted() {
        KObjectRegistryRecord created = this.objectRegistry.pushImmortalObject(new TestKObject());
        KObjectRegistryRecord stored = this.objectRegistry.getObject(created.objectId());
        Assertions.assertNotNull(stored);

        System.gc();
        KThreadUtils.sleepForSeconds(1);

        Assertions.assertEquals(created.objectId(), stored.objectId());
        Assertions.assertEquals(created.recordId(), stored.recordId());
        Assertions.assertNotEquals(stored.objectId(), stored.recordId());

        Assertions.assertFalse(stored.isSynthetic());
        Assertions.assertTrue(stored.isImmortal());
        Assertions.assertTrue(stored.isPresent());
        Assertions.assertEquals(TestKObject.class, stored.getObjectClass());
        Assertions.assertDoesNotThrow(stored::getObject);
        Assertions.assertDoesNotThrow(() -> {
            KObject _c = stored.getCastObject();
        });

        Assertions.assertTrue(stored.getObjectTags().contains("Aboba"));
        stored.toString();
    }

    @Test
    public void testPutSyntheticObjectButItsDeleted() {
        KObjectRegistryRecord created = this.objectRegistry.pushObject(new TestObject());
        KObjectRegistryRecord stored = this.objectRegistry.getObject(created.objectId());
        Assertions.assertNotNull(stored);
        System.gc();
        KThreadUtils.sleepForSeconds(1);
        Assertions.assertEquals(created.objectId(), stored.objectId());
        Assertions.assertEquals(created.recordId(), stored.recordId());
        Assertions.assertEquals(stored.objectId(), stored.recordId());

        Assertions.assertTrue(stored.isSynthetic());
        Assertions.assertFalse(stored.isImmortal());
        Assertions.assertFalse(stored.isPresent());
        Assertions.assertEquals(TestObject.class, stored.getObjectClass());
        Assertions.assertThrows(KDeletedObjectException.class, stored::getObject);
        Assertions.assertThrows(
            KDeletedObjectException.class,
            () -> { TestObject _c = stored.getCastObject(); }
        );

        Assertions.assertTrue(stored.getObjectTags().contains("synthetic"));
        stored.toString();
    }

    @Test
    public void testPutSyntheticImmortalObjectButItsDeleted() {
        KObjectRegistryRecord created = this.objectRegistry.pushImmortalObject(new TestObject());
        KObjectRegistryRecord stored = this.objectRegistry.getObject(created.objectId());
        Assertions.assertNotNull(stored);
        System.gc();
        KThreadUtils.sleepForSeconds(1);

        Assertions.assertEquals(created.objectId(), stored.objectId());
        Assertions.assertEquals(created.recordId(), stored.recordId());
        Assertions.assertEquals(stored.objectId(), stored.recordId());

        Assertions.assertTrue(stored.isSynthetic());
        Assertions.assertTrue(stored.isImmortal());
        Assertions.assertTrue(stored.isPresent());
        Assertions.assertEquals(TestObject.class, stored.getObjectClass());
        Assertions.assertDoesNotThrow(stored::getObject);
        Assertions.assertDoesNotThrow(() -> {
            TestObject _c = stored.getCastObject();
        });

        Assertions.assertTrue(stored.getObjectTags().contains("synthetic"));
        stored.toString();
    }

    @Test
    public void testRemoveObject() {
        KObjectRegistryRecord created = this.objectRegistry.pushObject(new TestObject());
        this.objectRegistry.removeObject(created.objectId());
        Assertions.assertNull(this.objectRegistry.getObject(created.objectId()));
    }

    @Test
    public void testGetObjectsWithTag() {
        KObjectRegistryRecord created = this.objectRegistry.pushImmortalObject(new TestObject());
        KObjectRegistryRecord created2 = this.objectRegistry.pushImmortalObject(new TestKObject());
        var found = this.objectRegistry.getObjectsWithTag("synthetic").stream().toList();
        Assertions.assertEquals(1, found.size());
        Assertions.assertEquals(created.objectId(), found.getFirst().objectId());
        Assertions.assertNotEquals(created2.objectId(), found.getFirst().objectId());
    }

    @Test
    public void testGetObjectsOfType() {
        KObjectRegistryRecord created = this.objectRegistry.pushImmortalObject(new TestObject());
        KObjectRegistryRecord created2 = this.objectRegistry.pushImmortalObject(new TestKObject());
        var found = this.objectRegistry.getObjectsOfType(TestObject.class).stream().toList();
        Assertions.assertEquals(1, found.size());
        Assertions.assertEquals(created.objectId(), found.getFirst().objectId());
        Assertions.assertNotEquals(created2.objectId(), found.getFirst().objectId());
    }

}
