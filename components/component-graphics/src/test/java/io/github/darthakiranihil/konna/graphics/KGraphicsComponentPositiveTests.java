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

package io.github.darthakiranihil.konna.graphics;

import io.github.darthakiranihil.konna.core.app.KStandardApplicationFeatures;
import io.github.darthakiranihil.konna.core.di.KAppContainer;
import io.github.darthakiranihil.konna.core.engine.KComponent;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisor;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisorConfig;
import io.github.darthakiranihil.konna.test.KEmptyEventRegisterer;
import io.github.darthakiranihil.konna.test.KEmptyRouteConfigurer;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class KGraphicsComponentPositiveTests extends KStandardTestClass {

    @Test
    @SuppressWarnings("unchecked")
    public void testLoadKonnaWithGraphicsComponentSuccess() {

        KEngineHypervisorConfig config = new KEngineHypervisorConfig(
            KAppContainer.useGenerated(),
            List.of(KEmptyRouteConfigurer.class),
            List.of(KEmptyEventRegisterer.class),
            List.of(KGraphicsComponentLoader.class)
        );

        Assertions.assertNotNull(config);
        KEngineHypervisor hypervisor = new KEngineHypervisor(config);
        hypervisor.launch(new KStandardApplicationFeatures(Map.of("log-level", "INFO", "max-fps", "-1")));
        try {
            Field engineComponents = KEngineHypervisor.class.getDeclaredField("engineComponents");

            engineComponents.setAccessible(true);

            Map<String, KComponent> mapOfComponents = (Map<String, KComponent>) engineComponents.get(hypervisor);
            Assertions.assertTrue(mapOfComponents.containsKey("Graphics"));

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }
}
