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
import io.github.darthakiranihil.konna.core.data.json.except.KJsonParseException;
import io.github.darthakiranihil.konna.core.engine.except.KHypervisorInitializationException;
import io.github.darthakiranihil.konna.core.engine.std.KStandardComponentLoader;
import io.github.darthakiranihil.konna.core.engine.std.KStandardServiceLoader;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;

public class KEngineHypervisorPositiveTests extends KStandardTestClass {

    @Test
    @SuppressWarnings("unchecked")
    public void testInstantiateHypervisorSuccess() {

        String config = "{" +
            "\"component_loader\": \"io.github.darthakiranihil.konna.core.engine.std.KStandardComponentLoader\"," +
            "\"service_loader\": \"io.github.darthakiranihil.konna.core.engine.std.KStandardServiceLoader\"," +
            "\"components\": [" +
            "\"io.github.darthakiranihil.konna.core.engine.TestComponent\"" +
            "]}";

        KJsonValue parsed;
        try {
            parsed = this.jsonParser.parse(config);
        } catch (KJsonParseException e) {
            Assertions.fail(e);
            return;
        }

        KEngineHypervisorConfig loadedConfig;
        try {
            loadedConfig = KEngineHypervisorConfig.fromJson(parsed);
        } catch (KHypervisorInitializationException e) {
            Assertions.fail(e);
            return;
        }

        KEngineHypervisor hypervisor = new KEngineHypervisor(loadedConfig, this.context);

        try {
            Field componentLoader = KEngineHypervisor.class.getDeclaredField("componentLoader");
            Field serviceLoader = KEngineHypervisor.class.getDeclaredField("serviceLoader");
            Field engineComponents = KEngineHypervisor.class.getDeclaredField("engineComponents");

            componentLoader.setAccessible(true);
            serviceLoader.setAccessible(true);
            engineComponents.setAccessible(true);

            Assertions.assertEquals(KStandardComponentLoader.class, componentLoader.get(hypervisor).getClass());
            Assertions.assertEquals(KStandardServiceLoader.class, serviceLoader.get(hypervisor).getClass());

            Map<String, KComponent> mapOfComponents = (Map<String, KComponent>) engineComponents.get(hypervisor);
            Assertions.assertTrue(mapOfComponents.containsKey("TestComponent"));

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }
}
