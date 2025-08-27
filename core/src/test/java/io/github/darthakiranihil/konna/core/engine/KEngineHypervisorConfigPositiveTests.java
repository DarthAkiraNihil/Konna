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

public class KEngineHypervisorConfigPositiveTests extends KStandardTestClass {

    @Test
    public void testFromJsonSuccess() {

        String config = "{" +
            "\"component_loader\": \"io.github.darthakiranihil.konna.core.engine.std.KStandardComponentLoader\"," +
            "\"service_loader\": \"io.github.darthakiranihil.konna.core.engine.std.KStandardServiceLoader\"," +
            "\"components\": [" +
                "\"io.github.darthakiranihil.konna.core.engine.TestComponent\"" +
            "]}";

        KJsonValue parsed;
        try {
            parsed = KStandardTestClass.jsonParser.parse(config);
        } catch (KJsonParseException e) {
            Assertions.fail(e);
            return;
        }

        try {
            KEngineHypervisorConfig loadedConfig = KEngineHypervisorConfig.fromJson(parsed);

            Assertions.assertEquals(TestComponent.class, loadedConfig.components().getFirst());
            Assertions.assertEquals(KStandardComponentLoader.class, loadedConfig.componentLoader());
            Assertions.assertEquals(KStandardServiceLoader.class, loadedConfig.serviceLoader());

        } catch (KHypervisorInitializationException e) {
            Assertions.fail(e);
        }


    }
}
