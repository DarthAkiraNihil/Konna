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

import io.github.darthakiranihil.konna.core.object.except.KDeletionException;
import io.github.darthakiranihil.konna.core.object.except.KInstantiationException;
import io.github.darthakiranihil.konna.core.object.impl.*;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KStandardActivatorNegativeTests extends KStandardTestClass {

    private final KActivator activator;

    protected KStandardActivatorNegativeTests() {
        super();
        this.activator = KStandardTestClass.context;
    }

    @Test
    public void testDeleteNonInstantiatedSingletons() {

        TestSingleton singleton = new TestSingleton();
        Assertions.assertThrowsExactly(KDeletionException.class, () -> this.activator.deleteObject(singleton));

        TestWeakSingleton weakSingleton = new TestWeakSingleton();
        Assertions.assertThrowsExactly(KDeletionException.class, () -> this.activator.deleteObject(weakSingleton));

    }

    @Test
    public void testDeleteImmortal() {

        var immortal = this.activator.createObject(TestImmortal.class);
        Assertions.assertThrowsExactly(KDeletionException.class, () -> this.activator.deleteObject(immortal));

    }

    @Test
    public void testEmptyPool() {

        var p1 = this.activator.createObject(TestPoolable.class, 0);
        var p2 = this.activator.createObject(TestPoolable.class, 0);
        Assertions.assertThrowsExactly(
            KInstantiationException.class,
            () -> this.activator.createObject(
                TestPoolable.class, 0
            )
        );

        this.activator.deleteObject(p1);
        this.activator.deleteObject(p2);

        var wp1 = this.activator.createObject(TestWeakPoolable.class, 0);
        var wp2 = this.activator.createObject(TestWeakPoolable.class, 0);
        Assertions.assertThrowsExactly(
            KInstantiationException.class,
            () -> this.activator.createObject(
                TestWeakPoolable.class, 0
            )
        );

        this.activator.deleteObject(wp1);
        this.activator.deleteObject(wp2);

    }

    @Test
    public void testDependencyResolveFailed() {
        Assertions.assertThrowsExactly(
            KInstantiationException.class,
            () -> this.activator.createObject(
                TestDependencyInterface.class
            )
        );
    }
}
