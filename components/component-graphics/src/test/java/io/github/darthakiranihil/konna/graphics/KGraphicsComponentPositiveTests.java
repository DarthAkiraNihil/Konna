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

import io.github.darthakiranihil.konna.core.app.std.KStandardApplicationFeatures;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.data.json.except.KJsonParseException;
import io.github.darthakiranihil.konna.core.data.json.except.KJsonSerializationException;
import io.github.darthakiranihil.konna.core.di.KContainerModifier;
import io.github.darthakiranihil.konna.core.engine.KComponent;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisor;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisorConfig;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import io.github.darthakiranihil.konna.graphics.render.KRenderFrontend;
import io.github.darthakiranihil.konna.graphics.impl.TestRenderFrontend;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class KGraphicsComponentPositiveTests extends KStandardTestClass {

    @Test
    @SuppressWarnings("unchecked")
    public void testLoadKonnaWithGraphicsComponentSuccess() {

        String config = """
            {
                "context_loader": "io.github.darthakiranihil.konna.core.test.KTestContextLoader",
                "component_loader": "io.github.darthakiranihil.konna.core.engine.std.KStandardComponentLoader",
                "service_loader": "io.github.darthakiranihil.konna.core.engine.std.KStandardServiceLoader",
                "route_configurers": [
                    "io.github.darthakiranihil.konna.core.test.KEmptyRouteConfigurer"
                ],
                "event_registerers": [
                    "io.github.darthakiranihil.konna.core.test.KEmptyEventRegisterer"
                ],
                "components": [
                    "io.github.darthakiranihil.konna.graphics.KGraphicsComponent"
                ],
                "frame_loader": "io.github.darthakiranihil.konna.core.test.KTestFrameLoader",
                  "frame_options": {
                    "size": {
                        "width": 1000,
                        "height": 800
                    },
                    "title": "Hello, world!"
                  }
            }""";

        KJsonValue parsed;
        try {
            parsed = this.jsonParser.parse(config);
        } catch (KJsonParseException e) {
            Assertions.fail(e);
            return;
        }

        KEngineHypervisorConfig loadedConfig;
        try {
            loadedConfig = this.jsonDeserializer.deserialize(
                parsed,
                KEngineHypervisorConfig.class
            );
        } catch (KJsonSerializationException e) {
            Assertions.fail(e);
            return;
        }

        Assertions.assertNotNull(loadedConfig);
        KEngineHypervisor hypervisor = new KEngineHypervisor(loadedConfig);
        hypervisor.launch(new KStandardApplicationFeatures(new HashMap<>()));
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
