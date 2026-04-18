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

package io.github.darthakiranihil.konna.level.service;

import io.github.darthakiranihil.konna.core.Konna;
import io.github.darthakiranihil.konna.core.KonnaBootstrapConfig;
import io.github.darthakiranihil.konna.core.app.KStandardArgumentParser;
import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.di.KAppContainer;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisor;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisorConfig;
import io.github.darthakiranihil.konna.core.engine.KService;
import io.github.darthakiranihil.konna.core.message.*;
import io.github.darthakiranihil.konna.core.object.KObjectRegistry;
import io.github.darthakiranihil.konna.core.struct.ref.KBooleanReference;
import io.github.darthakiranihil.konna.core.struct.ref.KIntReference;
import io.github.darthakiranihil.konna.core.util.KReflectionUtils;
import io.github.darthakiranihil.konna.level.KLevel;
import io.github.darthakiranihil.konna.level.KLevelComponentLoader;
import io.github.darthakiranihil.konna.level.KLevelSector;
import io.github.darthakiranihil.konna.level.impl.TestMessageRouteConfigurer;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

public class KLevelServicePositiveTests extends KStandardTestClass {

    private static final KSimpleEvent CAPTURE_THE_FLAG = new KSimpleEvent("captureTheFlag");

    private static KService assertService(
        KObjectRegistry objectRegistry
    ) {
        var packedService = objectRegistry
            .getObjectsOfType(KService.class)
            .stream()
            .filter(o -> ((KService) o.getCastObject()).name().equals("LevelService"))
            .findFirst();

        Assertions.assertTrue(packedService.isPresent());
        return packedService.get().getCastObject();
    }

    private static void assertMessage(
        KMessage message,
        String messageId
    ) {

        Assertions.assertEquals(messageId, message.messageId());
        KUniversalMap body = message.body();

        Assertions.assertNotNull(body.getSafe("level", KLevel.class));
        Assertions.assertNotNull(body.getSafe("sector", KLevelSector.class));

    }

    private final Field currentLevel;

    public KLevelServicePositiveTests() {

        Field rawCurrentLevel = KReflectionUtils.getField(KLevelService.class, "currentLevel");
        Assertions.assertNotNull(rawCurrentLevel);
        this.currentLevel = rawCurrentLevel;

    }

    @Test
    public void testLoadLevel() {

        class Asserter implements KTunnel {

            private final KObjectRegistry objectRegistry;

            @KInject
            public Asserter(KObjectRegistry objectRegistry) {
                this.objectRegistry = objectRegistry;
            }

            @Override
            public KMessage processMessage(KMessage message) {
                assertMessage(message, "Level.levelLoaded");
                KService service = assertService(this.objectRegistry);

                var actualLevel = (KLevel) KReflectionUtils.getFieldValue(currentLevel, service);

                Assertions.assertNotNull(actualLevel);
                Assertions.assertEquals("valid", actualLevel.name());
                Assertions.assertEquals(2, actualLevel.getSectorNames().length);
                Assertions.assertEquals("mf2", message.body().get("sector", KLevelSector.class).name());

                CAPTURE_THE_FLAG.invokeSync();
                return message;
            }
        }

        class Sender implements KMessageRoutesConfigurer {

            @Override
            public void setupRoutes(KMessageSystem messageSystem) {
                messageSystem.addMessageRoute("Level.levelLoaded", KMessageSystem.SINK, List.of(Asserter.class));

                KUniversalMap body = new KUniversalMap();
                body.put("level_name", "valid");
                body.put("sector", "mf2");
                messageSystem.deliverMessageSync(KMessage.regular("loadLevel", body));
            }
        }

        KBooleanReference executed = new KBooleanReference();
        UUID subToken = CAPTURE_THE_FLAG.subscribe(() -> executed.set(true));

        KonnaBootstrapConfig config = new KonnaBootstrapConfig(
            KStandardArgumentParser.class,
            KEngineHypervisor.class,
            new KEngineHypervisorConfig(
                KAppContainer.useGenerated(),
                List.of(TestMessageRouteConfigurer.class, Sender.class),
                List.of(),
                List.of(KLevelComponentLoader.class)
            )
        );
        Konna konnaWithOnlyDefaultArgs = new Konna(new String[0], config);
        konnaWithOnlyDefaultArgs.run();

        CAPTURE_THE_FLAG.unsubscribe(subToken);
        Assertions.assertTrue(executed.get());

    }

    @Test
    public void testLoadTwice() {

        class Asserter implements KTunnel {

            private final KObjectRegistry objectRegistry;

            @KInject
            public Asserter(KObjectRegistry objectRegistry) {
                this.objectRegistry = objectRegistry;
            }

            @Override
            public KMessage processMessage(KMessage message) {
                assertMessage(message, "Level.levelLoaded");
                KService service = assertService(this.objectRegistry);

                var actualLevel = (KLevel) KReflectionUtils.getFieldValue(currentLevel, service);

                Assertions.assertNotNull(actualLevel);
                Assertions.assertEquals("valid", actualLevel.name());
                Assertions.assertEquals(2, actualLevel.getSectorNames().length);
                Assertions.assertEquals("mf2", message.body().get("sector", KLevelSector.class).name());

                CAPTURE_THE_FLAG.invokeSync();
                return message;
            }
        }

        class Sender implements KMessageRoutesConfigurer {

            @Override
            public void setupRoutes(KMessageSystem messageSystem) {
                messageSystem.addMessageRoute("Level.levelLoaded", KMessageSystem.SINK, List.of(Asserter.class));

                KUniversalMap body = new KUniversalMap();
                body.put("level_name", "valid");
                body.put("sector", "mf2");
                messageSystem.deliverMessageSync(KMessage.regular("loadLevel", body));
                messageSystem.deliverMessageSync(KMessage.regular("loadLevel", body));
            }
        }

        KIntReference executions = new KIntReference();
        UUID subToken = CAPTURE_THE_FLAG.subscribe(() -> executions.set(executions.get() + 1));

        KonnaBootstrapConfig config = new KonnaBootstrapConfig(
            KStandardArgumentParser.class,
            KEngineHypervisor.class,
            new KEngineHypervisorConfig(
                KAppContainer.useGenerated(),
                List.of(TestMessageRouteConfigurer.class, Sender.class),
                List.of(),
                List.of(KLevelComponentLoader.class)
            )
        );
        Konna konnaWithOnlyDefaultArgs = new Konna(new String[0], config);
        konnaWithOnlyDefaultArgs.run();

        CAPTURE_THE_FLAG.unsubscribe(subToken);
        Assertions.assertEquals(2, executions.get());

    }

    @Test
    public void testGenerateAndLoad() {

        class Asserter implements KTunnel {

            private final KObjectRegistry objectRegistry;

            @KInject
            public Asserter(KObjectRegistry objectRegistry) {
                this.objectRegistry = objectRegistry;
            }

            @Override
            public KMessage processMessage(KMessage message) {
                assertMessage(message, "Level.generatedLevelLoaded");
                KService service = assertService(this.objectRegistry);

                var actualLevel = (KLevel) KReflectionUtils.getFieldValue(currentLevel, service);

                Assertions.assertNotNull(actualLevel);
                Assertions.assertEquals("valid", actualLevel.name());
                Assertions.assertEquals(2, actualLevel.getSectorNames().length);
                Assertions.assertEquals("mf2", message.body().get("sector", KLevelSector.class).name());

                CAPTURE_THE_FLAG.invokeSync();
                return message;
            }
        }

        class Sender implements KMessageRoutesConfigurer {

            @Override
            public void setupRoutes(KMessageSystem messageSystem) {
                messageSystem.addMessageRoute("Level.generatedLevelLoaded", KMessageSystem.SINK, List.of(Asserter.class));

                var body = new KUniversalMap();
                body.put("generator", "valid");
                body.put("seed", 123456L);
                body.put("sector", "mf2");
                messageSystem.deliverMessageSync(KMessage.regular("generateLevelAndLoad", body));
            }
        }

        KBooleanReference executed = new KBooleanReference();
        UUID subToken = CAPTURE_THE_FLAG.subscribe(() -> executed.set(true));

        KonnaBootstrapConfig config = new KonnaBootstrapConfig(
            KStandardArgumentParser.class,
            KEngineHypervisor.class,
            new KEngineHypervisorConfig(
                KAppContainer.useGenerated(),
                List.of(TestMessageRouteConfigurer.class, Sender.class),
                List.of(),
                List.of(KLevelComponentLoader.class)
            )
        );
        Konna konnaWithOnlyDefaultArgs = new Konna(new String[0], config);
        konnaWithOnlyDefaultArgs.run();

        CAPTURE_THE_FLAG.unsubscribe(subToken);
        Assertions.assertTrue(executed.get());

    }

    @Test
    public void testLoadAndLoadTwice() {

        class Asserter implements KTunnel {

            private final KObjectRegistry objectRegistry;

            @KInject
            public Asserter(KObjectRegistry objectRegistry) {
                this.objectRegistry = objectRegistry;
            }

            @Override
            public KMessage processMessage(KMessage message) {
                assertMessage(message, "Level.generatedLevelLoaded");
                KService service = assertService(this.objectRegistry);

                var actualLevel = (KLevel) KReflectionUtils.getFieldValue(currentLevel, service);

                Assertions.assertNotNull(actualLevel);
                Assertions.assertEquals("valid", actualLevel.name());
                Assertions.assertEquals(2, actualLevel.getSectorNames().length);
                Assertions.assertEquals("mf2", message.body().get("sector", KLevelSector.class).name());

                CAPTURE_THE_FLAG.invokeSync();
                return message;
            }
        }

        class Sender implements KMessageRoutesConfigurer {

            @Override
            public void setupRoutes(KMessageSystem messageSystem) {
                messageSystem.addMessageRoute("Level.generatedLevelLoaded", KMessageSystem.SINK, List.of(Asserter.class));

                var body = new KUniversalMap();
                body.put("generator", "valid");
                body.put("seed", 123456L);
                body.put("sector", "mf2");
                messageSystem.deliverMessageSync(KMessage.regular("generateLevelAndLoad", body));
                messageSystem.deliverMessageSync(KMessage.regular("generateLevelAndLoad", body));
            }
        }

        KIntReference executions = new KIntReference();
        UUID subToken = CAPTURE_THE_FLAG.subscribe(() -> executions.set(executions.get() + 1));

        KonnaBootstrapConfig config = new KonnaBootstrapConfig(
            KStandardArgumentParser.class,
            KEngineHypervisor.class,
            new KEngineHypervisorConfig(
                KAppContainer.useGenerated(),
                List.of(TestMessageRouteConfigurer.class, Sender.class),
                List.of(),
                List.of(KLevelComponentLoader.class)
            )
        );
        Konna konnaWithOnlyDefaultArgs = new Konna(new String[0], config);
        konnaWithOnlyDefaultArgs.run();

        CAPTURE_THE_FLAG.unsubscribe(subToken);
        Assertions.assertEquals(2, executions.get());

    }

}
