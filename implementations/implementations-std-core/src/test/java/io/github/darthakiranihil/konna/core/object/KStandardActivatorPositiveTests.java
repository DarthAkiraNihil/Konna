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

import io.github.darthakiranihil.konna.core.object.impl.*;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KStandardActivatorPositiveTests extends KStandardTestClass {

    private final KActivator activator;
    private final KObjectRegistry objectRegistry;
    
    public KStandardActivatorPositiveTests() {
        this.objectRegistry = new KStandardObjectRegistry();
        this.activator = new KStandardActivator(new AppContainer(), this.objectRegistry);
    }

    private void assertExists(KObject... objects) {
        var registered = this.objectRegistry.listObjects();
        for (var object: objects) {
            Assertions.assertTrue(registered.stream().anyMatch(x -> x.object().id() == object.id()));
        }
    }

    private void assertNotExists(KObject... objects) {
        var registered = this.objectRegistry.listObjects();
        for (var object: objects) {
            Assertions.assertTrue(registered.stream().allMatch(x -> x.object().id() != object.id()));
        }
    }

    @Test
    public void testCreateSingletons() {

        var singleton1 = this.activator.createObject(TestSingletonInterface.class);
        var singleton2 = this.activator.createObject(TestSingletonInterface.class);

        Assertions.assertInstanceOf(KObject.class, singleton1);
        Assertions.assertInstanceOf(KObject.class, singleton2);
        Assertions.assertEquals(((KObject) singleton1).id(), ((KObject) singleton2).id());

    }

    @Test
    public void testCreateTransients() {

        var transientObject1 = this.activator.createObject(TestTransient.class);
        var transientObject2 = this.activator.createObject(TestTransient.class);
        Assertions.assertNotEquals(transientObject1.id(), transientObject2.id());

        this.assertExists(transientObject1, transientObject2);

    }

    @Test
    public void testCreateTransientWithDependency() {

        var objectWithDependency = this.activator.createObject(TestInterfaceToResolve.class);
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
    public void testMultiInject() {
        TestInjected o = this.activator.createObject(TestInjected.class);
        Assertions.assertInstanceOf(TestDependencyImplementation.class, o.getField());
        Assertions.assertInstanceOf(TestDependencyImplementation.class, o.getcField());
        Assertions.assertInstanceOf(TestDependencyImplementation.class, o.getsField());
    }

    @Test
    public void testGetInjectable() {
        TestInjectable i = this.activator.createObject(TestInjectable.class);
        TestInjectable i2 = this.activator.createObject(TestInjectable.class);
        Assertions.assertEquals(i, i2);
    }
}
