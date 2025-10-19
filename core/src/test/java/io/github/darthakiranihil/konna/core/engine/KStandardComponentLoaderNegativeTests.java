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

package io.github.darthakiranihil.konna.core.engine;

import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.engine.except.KComponentLoadingException;
import io.github.darthakiranihil.konna.core.engine.std.KStandardComponentLoader;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class KStandardComponentLoaderNegativeTests extends KStandardTestClass {

    private static class TestComponentNoMeta extends KComponent {

        public TestComponentNoMeta(
            KServiceLoader serviceLoader,
            String name,
            KEngineContext ctx,
            String servicesPackage,
            KJsonValue config
        ) throws KComponentLoadingException {
            super(serviceLoader, name, ctx, servicesPackage, config);
        }

        @Override
        protected void applyConfig(KJsonValue config) {

        }
    }

    @Test
    public void testLoadComponentNoMetaInfo() {

        KStandardComponentLoader loader = new KStandardComponentLoader(
            this.jsonParser
        );

        Map<String, KComponent> loadedComponents = new HashMap<>();

        Assertions.assertThrowsExactly(
            KComponentLoadingException.class,
            () -> loader.load(
                KStandardTestClass.context,
                TestComponentNoMeta.class,
                null,
                loadedComponents
            )
        );

        Assertions.assertFalse(loadedComponents.containsKey("TestComponent"));

    }

    @Test
    public void testLoadComponentAlreadyLoaded() {

        KStandardComponentLoader loader = new KStandardComponentLoader(
            this.jsonParser
        );

        Map<String, KComponent> loadedComponents = new HashMap<>();
        loadedComponents.put("TestComponent", null);

        Assertions.assertThrowsExactly(
            KComponentLoadingException.class,
            () -> loader.load(
                KStandardTestClass.context,
                TestComponent.class,
                null,
                loadedComponents
            )
        );

    }

}
