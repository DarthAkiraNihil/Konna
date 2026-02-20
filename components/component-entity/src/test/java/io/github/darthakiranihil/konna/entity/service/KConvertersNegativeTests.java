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

package io.github.darthakiranihil.konna.entity.service;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.except.KInvalidArgumentException;
import io.github.darthakiranihil.konna.core.message.KMessage;
import io.github.darthakiranihil.konna.core.message.except.KInvalidMessageException;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KConvertersNegativeTests extends KStandardTestClass {

    @Test
    public void testMessageToEntityCreationData() {

        var converter = new KInternals.MessageToEntityCreationDataConverter();

        KUniversalMap malformed = new KUniversalMap();
        Assertions.assertThrows(KInvalidMessageException.class, () -> converter.convert(KMessage.regular("test", malformed)));
        malformed.put("name", "Aboba123");
        Assertions.assertThrows(KInvalidMessageException.class, () -> converter.convert(KMessage.regular("test", malformed)));

    }

    @Test
    public void testMessageToEntityRestorationData() {

        var converter = new KInternals.MessageToEntityRestorationDataConverter();

        KUniversalMap malformed = new KUniversalMap();
        Assertions.assertThrows(KInvalidMessageException.class, () -> converter.convert(KMessage.regular("test", malformed)));
        malformed.put("name", "Aboba123");
        Assertions.assertThrows(KInvalidMessageException.class, () -> converter.convert(KMessage.regular("test", malformed)));
        malformed.put("type", "Aboba123");
        Assertions.assertThrows(KInvalidMessageException.class, () -> converter.convert(KMessage.regular("test", malformed)));

    }

    @Test
    public void testMessageToEntityId() {

        var converter = new KInternals.MessageToEntityIdConverter();

        KUniversalMap malformed = new KUniversalMap();
        Assertions.assertThrows(KInvalidMessageException.class, () -> converter.convert(KMessage.regular("test", malformed)));

    }
}
