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

import io.github.darthakiranihil.konna.core.di.KContainer;
import io.github.darthakiranihil.konna.core.object.impl.*;
import io.github.darthakiranihil.konna.core.object.registry.KObjectRegistry;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KActivatorPositiveTests extends KStandardTestClass {

    private final KContainer container;

    public KActivatorPositiveTests() {
        KContainer local = KActivator.newContainer();
        local
            .add(TestImmortal.class)
            .add(TestPoolable.class)
            .add(TestSingleton.class)
            .add(TestTemporal.class)
            .add(TestTransient.class)
            .add(TestWeakPoolable.class)
            .add(TestWeakSingleton.class)
            .add(TestExplicitTransient.class)
            .add(TestInterfaceToResolve.class, TestResolvedImplementation.class)
            .add(TestDependencyInterface.class, TestDependencyImplementation.class);

        this.container = local;
    }

    private static void assertExists(KObject... objects) {
        var registered = KObjectRegistry.listObjects();
        for (var object: objects) {
            Assertions.assertTrue(registered.stream().anyMatch(x -> x.object().id() == object.id()));
        }
    }

    private static void assertNotExists(KObject... objects) {
        var registered = KObjectRegistry.listObjects();
        for (var object: objects) {
            Assertions.assertTrue(registered.stream().allMatch(x -> x.object().id() != object.id()));
        }
    }

    @Test
    public void testCreateSingletons() {

        var singleton1 = KActivator.create(TestSingleton.class, this.container);
        var singleton2 = KActivator.create(TestSingleton.class, this.container);

        Assertions.assertEquals(singleton1.id(), singleton2.id());

        var weakSingleton1 = KActivator.create(TestWeakSingleton.class, this.container);
        var weakSingleton2 = KActivator.create(TestWeakSingleton.class, this.container);

        Assertions.assertEquals(weakSingleton1.id(), weakSingleton2.id());

        var immortal1 = KActivator.create(TestImmortal.class, this.container);
        var immortal2 = KActivator.create(TestImmortal.class, this.container);

        Assertions.assertEquals(immortal1.id(), immortal2.id());
        KActivatorPositiveTests.assertExists(singleton1, singleton2, weakSingleton1, weakSingleton2, immortal1, immortal2);
    }

    @Test
    public void testCreatePoolables() {

        var poolable = KActivator.create(TestPoolable.class, this.container, 1);
        var id = poolable.id();
        Assertions.assertEquals(1, poolable.getField());

        KActivator.delete(poolable);
        poolable = KActivator.create(TestPoolable.class, this.container, 1);
        KActivator.delete(poolable);
        poolable = KActivator.create(TestPoolable.class, this.container, 1);

        Assertions.assertEquals(id, poolable.id());

        var weakPoolable = KActivator.create(TestWeakPoolable.class, this.container,1);
        var weakId = weakPoolable.id();
        Assertions.assertEquals(1, weakPoolable.getField());

        KActivator.delete(weakPoolable);
        weakPoolable = KActivator.create(TestWeakPoolable.class, this.container, 1);
        KActivator.delete(weakPoolable);
        weakPoolable = KActivator.create(TestWeakPoolable.class, this.container, 1);

        Assertions.assertEquals(weakId, weakPoolable.id());

        KActivatorPositiveTests.assertExists(poolable, weakPoolable);

    }

    @Test
    public void testCreateTransients() {

        var transientObject1 = KActivator.create(TestTransient.class, this.container);
        var transientObject2 = KActivator.create(TestTransient.class, this.container);
        Assertions.assertNotEquals(transientObject1.id(), transientObject2.id());

        var explicitTransientObject1 = KActivator.create(TestExplicitTransient.class, this.container);
        var explicitTransientObject2 = KActivator.create(TestExplicitTransient.class, this.container);
        Assertions.assertNotEquals(explicitTransientObject1.id(), explicitTransientObject2.id());

        var temporalObject1 = KActivator.create(TestTemporal.class, this.container);
        var temporalObject2 = KActivator.create(TestTemporal.class, this.container);
        Assertions.assertNotEquals(temporalObject1.id(), temporalObject2.id());

        KActivatorPositiveTests.assertExists(transientObject1, transientObject2, explicitTransientObject1, explicitTransientObject2);
        KActivatorPositiveTests.assertNotExists(temporalObject1, temporalObject2);

    }

    @Test
    public void testCreateTransientWithDependency() {

        var objectWithDependency = KActivator.create(TestInterfaceToResolve.class, this.container);
        Assertions.assertInstanceOf(TestResolvedImplementation.class, objectWithDependency);
        Assertions.assertEquals(10, objectWithDependency.amogus());

        try {
            var serviceField = objectWithDependency.getClass().getDeclaredField("dep");
            serviceField.setAccessible(true);
            Assertions.assertInstanceOf(TestDependencyImplementation.class, serviceField.get(objectWithDependency));
        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testDeleteTransients() {
        var transientObject = KActivator.create(TestTransient.class, this.container);
        KActivator.delete(transientObject);
        KActivatorPositiveTests.assertNotExists(transientObject);
    }

    @Test
    public void testDeletePoolables() {

        var poolable = KActivator.create(TestPoolable.class, this.container, 1);
        Assertions.assertEquals(1, poolable.getField());

        KActivator.delete(poolable);
        KActivatorPositiveTests.assertExists(poolable);
        Assertions.assertEquals(-1, poolable.getField());

        var weakPoolable = KActivator.create(TestWeakPoolable.class, this.container, 1);
        Assertions.assertEquals(1, weakPoolable.getField());

        KActivator.delete(weakPoolable);
        KActivatorPositiveTests.assertExists(weakPoolable);
        Assertions.assertEquals(-1, weakPoolable.getField());

    }

    @Test
    public void testDeleteSingletons() {

        var singleton = KActivator.create(TestSingleton.class, this.container);
        KActivator.delete(singleton);
        KActivatorPositiveTests.assertNotExists(singleton);

        var weakSingleton = KActivator.create(TestWeakSingleton.class, this.container);
        KActivator.delete(weakSingleton);
        KActivatorPositiveTests.assertNotExists(weakSingleton);

    }
}
