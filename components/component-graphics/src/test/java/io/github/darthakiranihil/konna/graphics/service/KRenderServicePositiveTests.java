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
import io.github.darthakiranihil.konna.core.app.KStandardArgumentParser;
import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.di.KAppContainer;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisor;
import io.github.darthakiranihil.konna.core.engine.KEngineHypervisorConfig;
import io.github.darthakiranihil.konna.core.engine.KService;
import io.github.darthakiranihil.konna.core.message.KMessage;
import io.github.darthakiranihil.konna.core.message.KMessageRoutesConfigurer;
import io.github.darthakiranihil.konna.core.message.KMessageSystem;
import io.github.darthakiranihil.konna.core.message.KSimpleEvent;
import io.github.darthakiranihil.konna.core.object.KObjectRegistry;
import io.github.darthakiranihil.konna.core.struct.ref.KBooleanReference;
import io.github.darthakiranihil.konna.core.util.KReflectionUtils;
import io.github.darthakiranihil.konna.graphics.KGraphicsComponentLoader;
import io.github.darthakiranihil.konna.graphics.impl.TestMessageRouteConfigurer;
import io.github.darthakiranihil.konna.graphics.render.KRenderable;
import io.github.darthakiranihil.konna.graphics.shape.KRectangle;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

@NullMarked
public class KRenderServicePositiveTests extends KStandardTestClass {

    private static final KSimpleEvent CAPTURE_THE_FLAG = new KSimpleEvent("captureTheFlag");

    @Test
    @SuppressWarnings("unchecked")
    public void testRenderObjects() {

        KBooleanReference executed = new KBooleanReference();
        UUID subToken = CAPTURE_THE_FLAG.subscribe(() -> executed.set(true));

        class Asserter implements KMessageRoutesConfigurer {

            private final KObjectRegistry objectRegistry;

            @KInject
            public Asserter(KObjectRegistry objectRegistry) {
                this.objectRegistry = objectRegistry;
            }

            @Override
            public void setupRoutes(KMessageSystem messageSystem) {
                var body = new KUniversalMap();
                var obj = new KRectangle(100, 100, 100, 100);
                body.put("object", obj);
                messageSystem.deliverMessageSync(KMessage.regular("render", body));

                var packedService = this.objectRegistry
                    .getObjectsOfType(KService.class)
                    .stream()
                    .filter(o -> ((KService) o.getCastObject()).name().equals("RenderService"))
                    .findFirst();
                Assertions.assertTrue(packedService.isPresent());
                KService service = packedService.get().getCastObject();

                Field renderablesField = KReflectionUtils.getField(KRenderService.class, "currentRenderables");
                Assertions.assertNotNull(renderablesField);
                var renderables = (List<KRenderable>) KReflectionUtils.getFieldValue(renderablesField, service);
                Assertions.assertNotNull(renderables);

                Assertions.assertEquals(1, renderables.size());
                Assertions.assertEquals(obj, renderables.getFirst());

                body.put("objects", new KRenderable[] {obj});

                messageSystem.deliverMessageSync(KMessage.regular("bulkRender", body));

                Assertions.assertEquals(1, renderables.size());
                messageSystem.deliverMessageSync(KMessage.regular("bulkAddToRender", body));
                Assertions.assertEquals(2, renderables.size());
                Assertions.assertEquals(obj, renderables.getFirst());

                CAPTURE_THE_FLAG.invokeSync();
            }
        }

        KonnaBootstrapConfig config = new KonnaBootstrapConfig(
            KStandardArgumentParser.class,
            KEngineHypervisor.class,
            new KEngineHypervisorConfig(
                KAppContainer.useGenerated(),
                List.of(TestMessageRouteConfigurer.class, Asserter.class),
                List.of(),
                List.of(KGraphicsComponentLoader.class)
            )
        );

        Konna konna = new Konna(new String[0], config);
        konna.run();

        Assertions.assertTrue(executed.get());
        CAPTURE_THE_FLAG.unsubscribe(subToken);

    }
}
