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
import io.github.darthakiranihil.konna.core.data.json.except.KJsonSerializationException;
import io.github.darthakiranihil.konna.core.engine.impl.TestContextLoader;
import io.github.darthakiranihil.konna.core.engine.std.KStandardComponentLoader;
import io.github.darthakiranihil.konna.core.engine.std.KStandardServiceLoader;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KEngineLauncherConfigPositiveTests extends KStandardTestClass {

    @Test
    public void testFromJsonSuccess() {

        String config = "{" +
            "\"context_loader\": \"io.github.darthakiranihil.konna.core.engine.impl.TestContextLoader\"" +
            "\"component_loader\": \"io.github.darthakiranihil.konna.core.engine.std.KStandardComponentLoader\"," +
            "\"service_loader\": \"io.github.darthakiranihil.konna.core.engine.std.KStandardServiceLoader\"," +
            "\"route_configurers\": []," +
            "\"event_registerers\": []," +
            "\"components\": [" +
                "\"io.github.darthakiranihil.konna.core.engine.TestComponent\"" +
            "]}";

        KJsonValue parsed;
        try {
            parsed = this.jsonParser.parse(config);
            KEngineHypervisorConfig.SCHEMA.validate(parsed);
        } catch (KJsonParseException e) {
            Assertions.fail(e);
            return;
        }

        try {
            KEngineHypervisorConfig loadedConfig = this.jsonDeserializer.deserialize(
                parsed,
                KEngineHypervisorConfig.class
            );

            Assertions.assertNotNull(loadedConfig);
            Assertions.assertEquals(TestComponent.class, loadedConfig.components()[0]);
            Assertions.assertEquals(KStandardComponentLoader.class, loadedConfig.componentLoader());
            Assertions.assertEquals(KStandardServiceLoader.class, loadedConfig.serviceLoader());
            Assertions.assertEquals(TestContextLoader.class, loadedConfig.contextLoader());
            Assertions.assertEquals(0, loadedConfig.eventRegisterers().size());
            Assertions.assertEquals(0, loadedConfig.messageRoutesConfigurers().size());

        } catch (KJsonSerializationException e) {
            Assertions.fail(e);
        }


    }
}
