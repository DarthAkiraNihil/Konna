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
import io.github.darthakiranihil.konna.core.engine.except.KServiceLoadingException;
import io.github.darthakiranihil.konna.core.engine.impl.TestService;
import io.github.darthakiranihil.konna.core.engine.std.KStandardServiceLoader;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class KStandardServiceLoaderNegativeTests extends KStandardTestClass {

    @KComponentServiceMetaInfo(name = "serv")
    private static class ServiceWithoutZeroArgsConstructor {

        public ServiceWithoutZeroArgsConstructor(KJsonValue _val) {

        }

    }

    @Test
    public void testLoadServiceAlreadyLoaded() {

        KServiceLoader loader = new KStandardServiceLoader();
        Map<String, KServiceEntry> loadedServices = new HashMap<>();
        loadedServices.put("TestService", null);

        Assertions.assertThrowsExactly(
            KServiceLoadingException.class,
            () -> loader.load(this.context, TestService.class, loadedServices)
        );

    }

}
