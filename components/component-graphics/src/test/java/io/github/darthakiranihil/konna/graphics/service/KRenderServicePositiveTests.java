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

package io.github.darthakiranihil.konna.graphics.service;

import io.github.darthakiranihil.konna.core.Konna;
import io.github.darthakiranihil.konna.core.KonnaBootstrapConfig;
import io.github.darthakiranihil.konna.core.app.KFrameSpawnOptions;
import io.github.darthakiranihil.konna.core.app.KStandardArgumentParser;
import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisor;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisorConfig;
import io.github.darthakiranihil.konna.core.except.KException;
import io.github.darthakiranihil.konna.core.message.KMessage;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.graphics.KGraphicsComponentLoader;
import io.github.darthakiranihil.konna.graphics.impl.TestFrameLoader;
import io.github.darthakiranihil.konna.graphics.impl.TestMessageRouteConfigurer;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import io.github.darthakiranihil.konna.graphics.render.KRenderable;
import io.github.darthakiranihil.konna.graphics.shape.KRectangle;
import io.github.darthakiranihil.konna.test.KTestContextLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class KRenderServicePositiveTests extends KStandardTestClass {

    private static final KonnaBootstrapConfig BOOTSTRAP = new KonnaBootstrapConfig(
        KStandardArgumentParser.class,
        KEngineHypervisor.class,
        new KEngineHypervisorConfig(
            KTestContextLoader.class,
            List.of(TestMessageRouteConfigurer.class),
            List.of(),
            List.of(KGraphicsComponentLoader.class),
            TestFrameLoader.class,
            new KFrameSpawnOptions(KSize.squared(1000), "Hello, world!")
        )
    );

    private final Method shutdown;

    public KRenderServicePositiveTests() {

        try {
            this.shutdown = Konna.class.getDeclaredMethod("shutdown");
            this.shutdown.setAccessible(true);
        } catch (Throwable e) {
            throw new KException(e);
        }

    }

    @Test
    @SuppressWarnings("unchecked")
    public void testRenderObject() {

        try {

            Konna konnaWithOnlyDefaultArgs = new Konna(new String[0], BOOTSTRAP);
            konnaWithOnlyDefaultArgs.run();

            Field currentRenderables = KRenderService.class.getDeclaredField("currentRenderables");
            currentRenderables.setAccessible(true);

            var body = new KUniversalMap();
            var obj = new KRectangle(100, 100, 100, 100);
            body.put("object", obj);

            TimeUnit.SECONDS.sleep(2);
            KStandardTestClass.context.deliverMessageSync(KMessage.regular("render", body));

            var renderServiceResult = KStandardTestClass.context
                .listObjects()
                .stream()
                .filter(o -> o.object().name().equals("RenderService"))
                .findFirst();

            Assertions.assertTrue(renderServiceResult.isPresent());

            var renderables = (List<KRenderable>) currentRenderables.get(renderServiceResult.get().object());
            Assertions.assertEquals(1, renderables.size());
            Assertions.assertEquals(obj, renderables.getFirst());

            body.put("objects", new KRenderable[] {obj});

            TimeUnit.SECONDS.sleep(2);
            KStandardTestClass.context.deliverMessageSync(KMessage.regular("bulkRender", body));

            renderables = (List<KRenderable>) currentRenderables.get(renderServiceResult.get().object());
            Assertions.assertEquals(1, renderables.size());

            TimeUnit.SECONDS.sleep(2);
            KStandardTestClass.context.deliverMessageSync(KMessage.regular("bulkAddToRender", body));

            renderables = (List<KRenderable>) currentRenderables.get(renderServiceResult.get().object());
            Assertions.assertEquals(2, renderables.size());

            Assertions.assertEquals(obj, renderables.getFirst());

            Assertions.assertDoesNotThrow(() -> this.shutdown.invoke(konnaWithOnlyDefaultArgs));

        } catch (Throwable e) {
            Assertions.fail(e);
        }

    }
}
