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

package io.github.darthakiranihil.konna.entity;

import io.github.darthakiranihil.konna.core.app.KApplicationFeatures;
import io.github.darthakiranihil.konna.core.app.KStandardApplicationFeatures;
import io.github.darthakiranihil.konna.core.app.KSystemFeatures;
import io.github.darthakiranihil.konna.core.data.json.KJsonDeserializer;
import io.github.darthakiranihil.konna.core.data.json.KJsonParser;
import io.github.darthakiranihil.konna.core.di.KAppContainer;
import io.github.darthakiranihil.konna.core.di.KEngineModule;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.engine.KComponent;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisor;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisorConfig;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.message.KSimpleEvent;
import io.github.darthakiranihil.konna.core.message.KSimpleEventSubscriber;
import io.github.darthakiranihil.konna.core.struct.ref.KBooleanReference;
import io.github.darthakiranihil.konna.core.util.KReflectionUtils;
import io.github.darthakiranihil.konna.test.KEmptyEventRegisterer;
import io.github.darthakiranihil.konna.test.KEmptyRouteConfigurer;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import io.github.darthakiranihil.konna.test.KTestHypervisor;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class KEntityComponentPositiveTests extends KStandardTestClass {

    public static final KSimpleEvent CAPTURE_THE_FLAG = new KSimpleEvent("captureTheFlag");

    @NullMarked
    private static final class TestEntityComponentLoader extends KEntityComponentLoader {

        @KInject
        public TestEntityComponentLoader(
            KJsonParser parser,
            KJsonDeserializer deserializer
        ) {
            super(parser, deserializer);
        }

        @Override
        public KComponent load(
            KEngineModule engineModule,
            KApplicationFeatures features,
            KSystemFeatures systemConfig
        ) {
            KComponent loaded = super.load(engineModule, features, systemConfig);
            CAPTURE_THE_FLAG.invokeSync();
            return loaded;
        }

    }
    @Test
    @SuppressWarnings("unchecked")
    public void testLoadKonnaWithEntityComponentSuccess() {

        KEngineHypervisorConfig config = new KEngineHypervisorConfig(
            KAppContainer.useGenerated(),
            List.of(KEmptyRouteConfigurer.class),
            List.of(KEmptyEventRegisterer.class),
            List.of(TestEntityComponentLoader.class)
        );

        KBooleanReference checkerExecuted = new KBooleanReference();

        KEngineHypervisor hypervisor = new KTestHypervisor(
            config,
            (h) -> {
                Field engineComponentsField = KReflectionUtils.getField(KEngineHypervisor.class, "engineComponents");
                Assertions.assertNotNull(engineComponentsField);

                var engineComponents = (Map<String, KComponent>) KReflectionUtils.getFieldValue(engineComponentsField, h);
                Assertions.assertNotNull(engineComponents);
                Assertions.assertTrue(engineComponents.containsKey("Entity"));
            }
        );
        KEngineModule module = KStandardTestClass.getModule();
        KEventSystem eventSystem = module.eventSystem();
        eventSystem.registerEvent(CAPTURE_THE_FLAG);

        KSimpleEventSubscriber captureTheFlag = eventSystem.getSimpleEventSubscriber("captureTheFlag");
        UUID subToken = captureTheFlag.subscribe(() -> checkerExecuted.set(true));

        hypervisor.launch(new KStandardApplicationFeatures(Map.of("log-level", "INFO", "max-fps", "-1")));
        Assertions.assertTrue(checkerExecuted.get());
        captureTheFlag.unsubscribe(subToken);
    }

}
