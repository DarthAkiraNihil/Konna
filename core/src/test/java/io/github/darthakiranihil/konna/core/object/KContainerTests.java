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
import io.github.darthakiranihil.konna.core.di.except.KDependencyResolveException;
import io.github.darthakiranihil.konna.core.object.impl.TestDependencyImplementation;
import io.github.darthakiranihil.konna.core.object.impl.TestDependencyInterface;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KContainerTests extends KStandardTestClass {

    @Test
    public void testResolveSuccess() {
        KContainer local = new KContainer();
        local.add(TestDependencyInterface.class, TestDependencyImplementation.class);
        Assertions.assertEquals(
            TestDependencyImplementation.class,
            local.resolve(TestDependencyInterface.class)
        );
    }

    @Test
    public void testResolveSuccessFromParent() {

        KContainer local = new KContainer();
        local.add(TestDependencyInterface.class, TestDependencyImplementation.class);
        KContainer child = new KContainer(local);

        Assertions.assertEquals(
            TestDependencyImplementation.class,
            child.resolve(TestDependencyInterface.class)
        );

    }

    @Test
    public void testResolveFailed() {
        Assertions.assertThrowsExactly(
            KDependencyResolveException.class,
            () -> new KContainer().resolve(TestDependencyInterface.class)
        );
    }
}
