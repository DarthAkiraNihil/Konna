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

import io.github.darthakiranihil.konna.core.app.KApplicationFeatures;
import io.github.darthakiranihil.konna.core.app.KStandardApplicationFeatures;
import io.github.darthakiranihil.konna.core.app.KSystemFeatures;
import io.github.darthakiranihil.konna.core.di.KAppContainer;
import io.github.darthakiranihil.konna.core.di.KEngineModule;
import io.github.darthakiranihil.konna.core.engine.except.KComponentLoadingException;
import io.github.darthakiranihil.konna.core.engine.impl.TestService;
import io.github.darthakiranihil.konna.core.util.KReflectionUtils;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class KComponentPositiveTests extends KStandardTestClass {



    @Test
    public void testInstantiateComponentSuccess() {
        try {
            var constructor = KReflectionUtils.getConstructor(
                KAppContainer.useGenerated(),
                KApplicationFeatures.class,
                KSystemFeatures.class
            );

            Assertions.assertNotNull(constructor);
            KEngineModule engineModule = KEngineModule.create(
                KReflectionUtils.newInstance(
                    constructor,
                    new KStandardApplicationFeatures(Map.of()),
                    new KSystemFeatures()
                )
            );

            new TestComponent(
                engineModule,
                new TestService()
            );
        } catch (KComponentLoadingException e) {
            Assertions.fail(e);
        }
    }
}
