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
import io.github.darthakiranihil.konna.core.app.KFrameSpawnOptions;
import io.github.darthakiranihil.konna.core.app.KStandardArgumentParser;
import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.di.KAppContainer;
import io.github.darthakiranihil.konna.core.engine.KEngineContext;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisor;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisorConfig;
import io.github.darthakiranihil.konna.core.except.KException;
import io.github.darthakiranihil.konna.core.message.KMessage;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.level.KLevel;
import io.github.darthakiranihil.konna.level.KLevelComponentLoader;
import io.github.darthakiranihil.konna.level.KLevelSector;
import io.github.darthakiranihil.konna.level.impl.ContextLoader;
import io.github.darthakiranihil.konna.level.impl.TestFrameLoader;
import io.github.darthakiranihil.konna.level.impl.TestMessageRouteConfigurer;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class KLevelServicePositiveTests extends KStandardTestClass {

    private static final KonnaBootstrapConfig BOOTSTRAP = new KonnaBootstrapConfig(
        KStandardArgumentParser.class,
        KEngineHypervisor.class,
        new KEngineHypervisorConfig(
            KAppContainer.useGenerated(),
            ContextLoader.class,
            List.of(TestMessageRouteConfigurer.class),
            List.of(),
            List.of(KLevelComponentLoader.class),
            TestFrameLoader.class,
            new KFrameSpawnOptions(KSize.squared(1000), "Hello, world!")
        )
    );

    private final Method shutdown;
    private final Field hypervisor;
    private final Field ctx;

    public KLevelServicePositiveTests() {

        try {
            this.shutdown = Konna.class.getDeclaredMethod("shutdown");
            this.shutdown.setAccessible(true);

            this.hypervisor = Konna.class.getDeclaredField("hypervisor");
            this.hypervisor.setAccessible(true);

            this.ctx = KEngineHypervisor.class.getDeclaredField("ctx");
            this.ctx.setAccessible(true);
        } catch (Throwable e) {
            throw new KException(e);
        }

        // KStandardTestClass.context.addAssetTypedef(new KEntityMetadataTypedef());

    }

    @Test
    public void testLoadLevel() {

        try {

            Konna konnaWithOnlyDefaultArgs = new Konna(new String[0], BOOTSTRAP);
            konnaWithOnlyDefaultArgs.run();

            TimeUnit.SECONDS.sleep(2);
            KEngineContext realContext = (KEngineContext) this.ctx.get(this.hypervisor.get(konnaWithOnlyDefaultArgs));

            Field currentLevel = KLevelService.class.getDeclaredField("currentLevel");
            Field currentSector = KLevelService.class.getDeclaredField("currentSector");
            currentLevel.setAccessible(true);
            currentSector.setAccessible(true);

            var body = new KUniversalMap();
            body.put("level_name", "valid");
            body.put("sector", "mf2");
            realContext.deliverMessageSync(KMessage.regular("loadLevel", body));

            var service = realContext
                .listObjects()
                .stream()
                .filter(o -> o.object().name().equals("LevelService"))
                .findFirst();

            Assertions.assertTrue(service.isPresent());

            var cloc = (KLevel) currentLevel.get(service.get().object());
            var csec = (KLevelSector) currentSector.get(service.get().object());

            Assertions.assertEquals("valid", cloc.name());
            Assertions.assertEquals(2, cloc.getSectorNames().length);

            Assertions.assertEquals("mf2", csec.name());

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testLoadTwice() {

        try {

            Konna konnaWithOnlyDefaultArgs = new Konna(new String[0], BOOTSTRAP);
            konnaWithOnlyDefaultArgs.run();

            TimeUnit.SECONDS.sleep(2);
            KEngineContext realContext = (KEngineContext) this.ctx.get(this.hypervisor.get(konnaWithOnlyDefaultArgs));

            Field currentLevel = KLevelService.class.getDeclaredField("currentLevel");
            Field currentSector = KLevelService.class.getDeclaredField("currentSector");
            currentLevel.setAccessible(true);
            currentSector.setAccessible(true);

            var body = new KUniversalMap();
            body.put("level_name", "valid");
            body.put("sector", "mf2");
            realContext.deliverMessageSync(KMessage.regular("loadLevel", body));

            var service = realContext
                .listObjects()
                .stream()
                .filter(o -> o.object().name().equals("LevelService"))
                .findFirst();

            Assertions.assertTrue(service.isPresent());

            var cloc = (KLevel) currentLevel.get(service.get().object());
            var csec = (KLevelSector) currentSector.get(service.get().object());

            Assertions.assertEquals("valid", cloc.name());
            Assertions.assertEquals(2, cloc.getSectorNames().length);

            Assertions.assertEquals("mf2", csec.name());

            realContext.deliverMessageSync(KMessage.regular("loadLevel", body));

            cloc = (KLevel) currentLevel.get(service.get().object());
            csec = (KLevelSector) currentSector.get(service.get().object());

            Assertions.assertEquals("valid", cloc.name());
            Assertions.assertEquals(2, cloc.getSectorNames().length);

            Assertions.assertEquals("mf2", csec.name());

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testGenerateAndLoad() {

        try {

            Konna konnaWithOnlyDefaultArgs = new Konna(new String[0], BOOTSTRAP);
            konnaWithOnlyDefaultArgs.run();

            TimeUnit.SECONDS.sleep(2);
            KEngineContext realContext = (KEngineContext) this.ctx.get(this.hypervisor.get(konnaWithOnlyDefaultArgs));

            Field currentLevel = KLevelService.class.getDeclaredField("currentLevel");
            Field currentSector = KLevelService.class.getDeclaredField("currentSector");
            currentLevel.setAccessible(true);
            currentSector.setAccessible(true);

            var body = new KUniversalMap();
            body.put("generator", "valid");
            body.put("seed", 123456L);
            body.put("sector", "mf2");
            realContext.deliverMessageSync(KMessage.regular("generateLevelAndLoad", body));

            var service = realContext
                .listObjects()
                .stream()
                .filter(o -> o.object().name().equals("LevelService"))
                .findFirst();

            Assertions.assertTrue(service.isPresent());

            var cloc = (KLevel) currentLevel.get(service.get().object());
            var csec = (KLevelSector) currentSector.get(service.get().object());

            Assertions.assertEquals("valid", cloc.name());
            Assertions.assertEquals(2, cloc.getSectorNames().length);

            Assertions.assertEquals("mf2", csec.name());

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testGenerateAndLoadTwice() {
        try {

            Konna konnaWithOnlyDefaultArgs = new Konna(new String[0], BOOTSTRAP);
            konnaWithOnlyDefaultArgs.run();

            TimeUnit.SECONDS.sleep(2);
            KEngineContext realContext = (KEngineContext) this.ctx.get(this.hypervisor.get(konnaWithOnlyDefaultArgs));

            Field currentLevel = KLevelService.class.getDeclaredField("currentLevel");
            Field currentSector = KLevelService.class.getDeclaredField("currentSector");
            currentLevel.setAccessible(true);
            currentSector.setAccessible(true);

            var body = new KUniversalMap();
            body.put("generator", "valid");
            body.put("seed", 123456L);
            body.put("sector", "mf2");
            realContext.deliverMessageSync(KMessage.regular("generateLevelAndLoad", body));

            var service = realContext
                .listObjects()
                .stream()
                .filter(o -> o.object().name().equals("LevelService"))
                .findFirst();

            Assertions.assertTrue(service.isPresent());

            var cloc = (KLevel) currentLevel.get(service.get().object());
            var csec = (KLevelSector) currentSector.get(service.get().object());

            Assertions.assertEquals("valid", cloc.name());
            Assertions.assertEquals(2, cloc.getSectorNames().length);

            Assertions.assertEquals("mf2", csec.name());

            body.put("seed", 567890L);
            body.put("sector", "mf2");
            realContext.deliverMessageSync(KMessage.regular("generateLevelAndLoad", body));

            cloc = (KLevel) currentLevel.get(service.get().object());
            csec = (KLevelSector) currentSector.get(service.get().object());

            Assertions.assertEquals("valid", cloc.name());
            Assertions.assertEquals(2, cloc.getSectorNames().length);

            Assertions.assertEquals("mf2", csec.name());

        } catch (Throwable e) {
            Assertions.fail(e);
        }
    }
}
