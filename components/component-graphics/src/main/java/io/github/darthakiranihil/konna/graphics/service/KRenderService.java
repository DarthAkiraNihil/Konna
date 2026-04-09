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
import io.github.darthakiranihil.konna.core.app.KFrameEvent;
import io.github.darthakiranihil.konna.core.app.KFrameTaskDescription;
import io.github.darthakiranihil.konna.core.app.KFrameTaskScheduler;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.engine.KService;
import io.github.darthakiranihil.konna.core.engine.KServiceEndpoint;
import io.github.darthakiranihil.konna.core.log.system.KSystemLogger;
import io.github.darthakiranihil.konna.core.message.KBodyValue;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.di.KSingleton;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.graphics.render.KRenderFrontend;
import io.github.darthakiranihil.konna.graphics.render.KRenderable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Service for rendering different objects using {@link KRenderFrontend}.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
@KSingleton
public class KRenderService extends KObject implements KService {

    /**
     * Description of task that renders all objects that is contained by the service.
     * By default, it is supposed to be executed in the very last order before buffers' swapping.
     */
    public static final KFrameTaskDescription RENDER_TASK = KFrameTaskDescription.ofPersistent(
        "RenderService.render",
        KFrameEvent.PRE_SWAP,
        Integer.MAX_VALUE
    );

    private final Object renderLock = new Object();

    private final KRenderFrontend renderFrontend;

    private final List<KRenderable> currentRenderables;

    /**
     * Standard constructor.
     * @param renderFrontend Render frontend to use for rendering objects
     * @param frame The frame of the current context
     * @param frameTaskScheduler Frame task scheduler to schedule render task
     */
    public KRenderService(
        @KInject final KRenderFrontend renderFrontend,
        @KInject final KFrameTaskScheduler frameTaskScheduler,
        @KInject final KFrame frame
    ) {
        super("RenderService", KStructUtils.setOfTags(KTag.DefaultTags.SERVICE));
        this.renderFrontend = renderFrontend;

        this.currentRenderables = new CopyOnWriteArrayList<>();


        KSystemLogger.debug(
            this.name,
            "Created render frontend: %s", renderFrontend.getClass().getCanonicalName()
        );

        frameTaskScheduler.scheduleTask(RENDER_TASK, this::render);
        this.renderFrontend.setViewportSize(frame.getSize());
    }

    /**
     * Renders an object.
     * @param renderable Object to be rendered with {@link KRenderFrontend}
     */
    @KServiceEndpoint(
        route = "render"
    )
    public void render(@KBodyValue("object") final KRenderable renderable) {

        synchronized (this.renderLock) {
            this.currentRenderables.clear();
            this.currentRenderables.add(renderable);
        }

    }

    /**
     * Renders an array of objects.
     * @param renderables Object array to be rendered with {@link KRenderFrontend}
     */
    @KServiceEndpoint(
        route = "bulkRender"
    )
    public void render(@KBodyValue("objects") final KRenderable[] renderables) {

        synchronized (this.renderLock) {
            this.currentRenderables.clear();
            this.currentRenderables.addAll(List.of(renderables));
        }
    }

    /**
     * Add an array of objects to list of current rendered objects.
     * @param renderables Object array to be rendered with {@link KRenderFrontend}
     * @since 0.5.0
     */
    @KServiceEndpoint(
        route = "bulkAddToRender"
    )
    public void addToRender(@KBodyValue("objects") final KRenderable[] renderables) {

        synchronized (this.renderLock) {
            this.currentRenderables.addAll(List.of(renderables));
        }

    }

    private void render() {
        synchronized (this.renderLock) {
            this.renderFrontend.clear();

            //KFrameLock lock = this.activator.createObject(KFrameLock.class);

            for (KRenderable renderable : this.currentRenderables) {
                renderable.render(this.renderFrontend);
            }
        }
        //this.activator.deleteObject(lock);

    }

}
