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

package io.github.darthakiranihil.konna.core;

import io.github.darthakiranihil.konna.core.app.KApplicationArgument;
import io.github.darthakiranihil.konna.core.app.KStandardArgumentParser;
import io.github.darthakiranihil.konna.core.di.KAppContainer;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisor;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisorConfig;
import io.github.darthakiranihil.konna.core.engine.TestComponentLoader;
import io.github.darthakiranihil.konna.core.except.KException;
import io.github.darthakiranihil.konna.test.KEmptyEventRegisterer;
import io.github.darthakiranihil.konna.test.KEmptyRouteConfigurer;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Test;

import java.util.List;

public class KonnaPositiveTests extends KStandardTestClass {

    private static final KonnaBootstrapConfig BOOTSTRAP = new KonnaBootstrapConfig(
        KStandardArgumentParser.class,
        KEngineHypervisor.class,
        new KEngineHypervisorConfig(
            KAppContainer.Mock.class,
            List.of(KEmptyRouteConfigurer.class),
            List.of(KEmptyEventRegisterer.class),
            List.of(TestComponentLoader.class)
        )
    );

    @Test
    public void testStartKonna() {

        try {
            Konna konnaWithOnlyDefaultArgs = new Konna(new String[0], BOOTSTRAP);
            konnaWithOnlyDefaultArgs.run();
            Konna konnaWithCustomArgs = new Konna(new String[0], List.of(new KApplicationArgument("a", "aaa", "wawa")), BOOTSTRAP);
            konnaWithCustomArgs.run();

        } catch (Throwable e) {
            throw new KException(e);
        }

    }
}
