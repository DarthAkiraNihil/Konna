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

import io.github.darthakiranihil.konna.core.app.KFrame;
import io.github.darthakiranihil.konna.core.app.KFrameLock;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.engine.KComponentServiceMetaInfo;
import io.github.darthakiranihil.konna.core.engine.KServiceEndpoint;
import io.github.darthakiranihil.konna.core.log.KSystemLogger;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.message.KSimpleEvent;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.graphics.KColor;
import io.github.darthakiranihil.konna.graphics.render.KRenderFrontend;
import io.github.darthakiranihil.konna.graphics.render.KRenderable;
import io.github.darthakiranihil.konna.graphics.shape.KRectangle;

import java.util.LinkedList;
import java.util.List;

/**
 * Service for rendering different objects using {@link KRenderFrontend}.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
@KSingleton
@KComponentServiceMetaInfo(
    name = "RenderService"
)
public class KRenderService extends KObject {

    private final KRenderFrontend renderFrontend;
    private final KActivator activator;

    private final List<KRenderable> currentRenderables;
    private final KRectangle rekt;

    /**
     * Standard constructor.
     * @param renderFrontend Render frontend to use for rendering objects
     */
    public KRenderService(
        @KInject final KRenderFrontend renderFrontend,
        @KInject final KActivator activator,
        @KInject final KEventSystem eventSystem
    ) {
        super("Graphics.RenderService", KStructUtils.setOfTags(KTag.DefaultTags.SERVICE));
        this.renderFrontend = renderFrontend;
        this.activator = activator;

        this.currentRenderables = new LinkedList<>();
        this.rekt = KRectangle.square(100, 100, 400, KColor.RED);
        this.currentRenderables.add(this.rekt);

        KSystemLogger.debug(
            "Graphics.RenderService",
            "Created render frontend: %s", renderFrontend.getClass().getCanonicalName()
        );

        KSimpleEvent tick = eventSystem.getSimpleEvent(KFrame.TICK_EVENT_NAME);
        if (tick != null) {
            tick.subscribe(this::render);
        }
    }

    /**
     * Renders an object.
     * @param renderable Object to be rendered with {@link KRenderFrontend}
     */
    @KServiceEndpoint(
        route = "render",
        converter = KInternals.MessageToRenderableConverter.class
    )
    public void render(final KRenderable renderable) {
        this.currentRenderables.clear();
        this.currentRenderables.add(renderable);
        //renderable.render(this.renderFrontend);
    }

    /**
     * Renders an array of objects.
     * @param renderables Object array to be rendered with {@link KRenderFrontend}
     */
    @KServiceEndpoint(
        route = "bulkRender",
        converter = KInternals.MessageToRenderableArrayConverter.class
    )
    public void render(final KRenderable[] renderables) {
        this.currentRenderables.clear();
        this.currentRenderables.addAll(List.of(renderables));
//        for (KRenderable renderable: renderables) {
//            renderable.render(this.renderFrontend);
//        }
    }

    private void render() {
        this.renderFrontend.initializeIfNot();
        this.renderFrontend.clear();
        this.rekt.rotate(0.1);

        KFrameLock lock = this.activator.createObject(KFrameLock.class);

        for (KRenderable renderable: this.currentRenderables) {
            renderable.render(this.renderFrontend);
        }

        this.activator.deleteObject(lock);
    }

}
