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
import io.github.darthakiranihil.konna.core.di.KMasterContainerModifier;
import io.github.darthakiranihil.konna.core.object.impl.*;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@KMasterContainerModifier
public class KStandardActivatorPositiveTests extends KStandardTestClass {

    private final KActivator activator;
    
    public KStandardActivatorPositiveTests() {
        KContainer master = this.context.containerResolver().resolve();
        master
            .add(TestInterfaceToResolve.class, TestResolvedImplementation.class)
            .add(TestDependencyInterface.class, TestDependencyImplementation.class);

        this.activator = this.context.activator();
    }

    private void assertExists(KObject... objects) {
        var registered = this.context.objectRegistry().listObjects();
        for (var object: objects) {
            Assertions.assertTrue(registered.stream().anyMatch(x -> x.object().id() == object.id()));
        }
    }

    private void assertNotExists(KObject... objects) {
        var registered = this.context.objectRegistry().listObjects();
        for (var object: objects) {
            Assertions.assertTrue(registered.stream().allMatch(x -> x.object().id() != object.id()));
        }
    }

    @Test
    public void testCreateSingletons() {

        var singleton1 = this.activator.create(TestSingleton.class);
        var singleton2 = this.activator.create(TestSingleton.class);

        Assertions.assertEquals(singleton1.id(), singleton2.id());

        var weakSingleton1 = this.activator.create(TestWeakSingleton.class);
        var weakSingleton2 = this.activator.create(TestWeakSingleton.class);

        Assertions.assertEquals(weakSingleton1.id(), weakSingleton2.id());

        var immortal1 = this.activator.create(TestImmortal.class);
        var immortal2 = this.activator.create(TestImmortal.class);

        Assertions.assertEquals(immortal1.id(), immortal2.id());
        this.assertExists(singleton1, singleton2, weakSingleton1, weakSingleton2, immortal1, immortal2);
    }

    @Test
    public void testCreatePoolables() {

        var poolable = this.activator.create(TestPoolable.class, 1);
        var id = poolable.id();
        Assertions.assertEquals(1, poolable.getField());

        this.activator.delete(poolable);
        poolable = this.activator.create(TestPoolable.class, 1);
        this.activator.delete(poolable);
        poolable = this.activator.create(TestPoolable.class, 1);

        Assertions.assertEquals(id, poolable.id());

        var weakPoolable = this.activator.create(TestWeakPoolable.class,1);
        var weakId = weakPoolable.id();
        Assertions.assertEquals(1, weakPoolable.getField());

        this.activator.delete(weakPoolable);
        weakPoolable = this.activator.create(TestWeakPoolable.class, 1);
        this.activator.delete(weakPoolable);
        weakPoolable = this.activator.create(TestWeakPoolable.class, 1);

        Assertions.assertEquals(weakId, weakPoolable.id());

        var specialPoolable = this.activator.create(TestPoolableWithoutSpecialMethods.class);
        Assertions.assertEquals(5, specialPoolable.baba());
        this.activator.delete(specialPoolable);

        this.assertExists(poolable, weakPoolable);

    }

    @Test
    public void testCreateTransients() {

        var transientObject1 = this.activator.create(TestTransient.class);
        var transientObject2 = this.activator.create(TestTransient.class);
        Assertions.assertNotEquals(transientObject1.id(), transientObject2.id());

        var explicitTransientObject1 = this.activator.create(TestExplicitTransient.class);
        var explicitTransientObject2 = this.activator.create(TestExplicitTransient.class);
        Assertions.assertNotEquals(explicitTransientObject1.id(), explicitTransientObject2.id());

        var temporalObject1 = this.activator.create(TestTemporal.class);
        var temporalObject2 = this.activator.create(TestTemporal.class);
        Assertions.assertNotEquals(temporalObject1.id(), temporalObject2.id());

        this.assertExists(transientObject1, transientObject2, explicitTransientObject1, explicitTransientObject2);
        this.assertNotExists(temporalObject1, temporalObject2);

    }

    @Test
    public void testCreateTransientWithDependency() {

        var objectWithDependency = this.activator.create(TestInterfaceToResolve.class);
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
        var transientObject = this.activator.create(TestTransient.class);
        this.activator.delete(transientObject);
        this.assertNotExists(transientObject);
    }

    @Test
    public void testDeletePoolables() {

        var poolable = this.activator.create(TestPoolable.class, 1);
        Assertions.assertEquals(1, poolable.getField());

        this.activator.delete(poolable);
        this.assertExists(poolable);
        Assertions.assertEquals(-1, poolable.getField());

        var weakPoolable = this.activator.create(TestWeakPoolable.class, 1);
        Assertions.assertEquals(1, weakPoolable.getField());

        this.activator.delete(weakPoolable);
        this.assertExists(weakPoolable);
        Assertions.assertEquals(-1, weakPoolable.getField());

    }

    @Test
    public void testDeleteSingletons() {

        var singleton = this.activator.create(TestSingleton.class);
        this.activator.delete(singleton);
        this.assertNotExists(singleton);

        var weakSingleton = this.activator.create(TestWeakSingleton.class);
        this.activator.delete(weakSingleton);
        this.assertNotExists(weakSingleton);

    }
}
