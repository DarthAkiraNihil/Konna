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

import io.github.darthakiranihil.konna.core.di.except.KDependencyResolveException;
import io.github.darthakiranihil.konna.core.except.KInvalidArgumentException;
import io.github.darthakiranihil.konna.core.object.except.KDeletionException;
import io.github.darthakiranihil.konna.core.object.except.KInstantiationException;
import io.github.darthakiranihil.konna.core.object.impl.*;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KStandardActivatorNegativeTests extends KStandardTestClass {

    private final KActivator activator;

    protected KStandardActivatorNegativeTests() {
        super();
        this.activator = new KStandardActivator(new AppContainer(), new KStandardObjectRegistry());
    }

    @Test
    public void testDependencyResolveFailed() {
        Assertions.assertThrowsExactly(
            KDependencyResolveException.class,
            () -> this.activator.createObject(
                TestUnresolvedInterface.class
            )
        );
    }

    @Test
    public void testCreateExplicitButItsAbstractClassOrInterface() {
        Assertions.assertThrows(
            KInvalidArgumentException.class,
            () -> this.activator.createObject(TestDependencyInterface.class, () -> new Object[0])
        );
        Assertions.assertThrows(
            KInvalidArgumentException.class,
            () -> this.activator.createObject(TestAbstractClass.class, () -> new Object[0])
        );
    }
}
