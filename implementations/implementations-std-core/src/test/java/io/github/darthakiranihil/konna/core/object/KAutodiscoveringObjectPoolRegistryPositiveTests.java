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

import io.github.darthakiranihil.konna.core.di.KEngineModule;
import io.github.darthakiranihil.konna.core.object.poolables.TestExplicitPoolable;
import io.github.darthakiranihil.konna.core.object.poolables.TestSimplePoolable;
import io.github.darthakiranihil.konna.core.util.KClasspathSearchEngine;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KAutodiscoveringObjectPoolRegistryPositiveTests extends KStandardTestClass {

    private final KObjectPoolRegistry registry;

    public KAutodiscoveringObjectPoolRegistryPositiveTests() {

        KEngineModule module = KStandardTestClass.getModule();
        this.registry = new KAutodiscoveringObjectPoolRegistry(
            module.activator().createObject(KClasspathSearchEngine.class),
            module.activator(),
            module.objectRegistry()
        );
    }

    @Test
    public void testGetSimplePoolable() {
        try (var o = this.registry.obtain(TestSimplePoolable.class)) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertEquals(1, o.get().getValue());
        }

        try (var o = this.registry.obtain(TestSimplePoolable.class, 1)) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertEquals(1, o.get().getValue());
        }
    }

    @Test
    public void testGetExplicitPoolable() {
        KArgs args = () -> new Object[] {1};
        try (var o = this.registry.obtain(TestExplicitPoolable.class, args)) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertEquals(1, o.get().getValue());
        }

        try (var o = this.registry.obtain(TestExplicitPoolable.class, args, 1)) {
            Assertions.assertTrue(o.isPresent());
            Assertions.assertEquals(1, o.get().getValue());
        }
    }
}
