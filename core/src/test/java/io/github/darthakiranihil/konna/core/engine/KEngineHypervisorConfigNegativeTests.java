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
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KEngineHypervisorConfigNegativeTests extends KStandardTestClass {

    @Test
    public void testFromJsonFailedNoKey() {

        String config = "{" +
            "\"service_loader\": \"io.github.darthakiranihil.konna.core.engine.std.KStandardComponentServiceLoader\"," +
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

        Assertions.assertThrowsExactly(
            KHypervisorInitializationException.class,
            () -> KEngineHypervisorConfig.fromJson(parsed)
        );

    }

    @Test
    public void testFromJsonClassNotFound() {

        String config = "{" +
            "\"component_loader\": \"io.github.darthakiranihil.konna.core.engine.std.KstandardComponentLoader\"," +
            "\"service_loader\": \"io.github.darthakiranihil.konna.core.engine.std.KStandardComponentServiceLoader\"," +
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

        Assertions.assertThrowsExactly(
            KHypervisorInitializationException.class,
            () -> KEngineHypervisorConfig.fromJson(parsed)
        );

    }

    @Test
    public void testFromJsonComponentClassNotFound() {

        String config = "{" +
            "\"component_loader\": \"io.github.darthakiranihil.konna.core.engine.std.KStandardComponentLoader\"," +
            "\"service_loader\": \"io.github.darthakiranihil.konna.core.engine.std.KStandardComponentServiceLoader\"," +
            "\"components\": [" +
            "\"io.github.darthakiranihil.konna.core.engine.TestCompoent\"" +
            "]}";

        KJsonValue parsed;
        try {
            parsed = KStandardTestClass.jsonParser.parse(config);
        } catch (KJsonParseException e) {
            Assertions.fail(e);
            return;
        }

        Assertions.assertThrowsExactly(
            KHypervisorInitializationException.class,
            () -> KEngineHypervisorConfig.fromJson(parsed)
        );
    }
}
