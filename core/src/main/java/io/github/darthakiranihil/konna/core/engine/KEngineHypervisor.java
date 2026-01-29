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
import io.github.darthakiranihil.konna.core.app.KFrame;
import io.github.darthakiranihil.konna.core.app.KFrameLoader;
import io.github.darthakiranihil.konna.core.data.json.KJsonValidator;
import io.github.darthakiranihil.konna.core.debug.KDebugger;
import io.github.darthakiranihil.konna.core.di.KContainer;
import io.github.darthakiranihil.konna.core.di.KContainerModifier;
import io.github.darthakiranihil.konna.core.engine.except.KComponentLoadingException;
import io.github.darthakiranihil.konna.core.engine.except.KHypervisorInitializationException;
import io.github.darthakiranihil.konna.core.except.KException;
import io.github.darthakiranihil.konna.core.log.KSystemLogger;
import io.github.darthakiranihil.konna.core.message.KEventRegisterer;
import io.github.darthakiranihil.konna.core.message.KMessageRoutesConfigurer;
import io.github.darthakiranihil.konna.core.message.KSimpleEvent;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Konna Engine hypervisor - the primal class for the engine that starts
 * and controls the whole system.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@KContainerModifier
public class KEngineHypervisor extends KObject {

    /**
     * Name of the event that is invoked when the hypervisor is ready.
     */
    public static final String HYPERVISOR_READY_EVENT_NAME = "ready";

    /**
     * Constant for one second in nanoseconds in float type.
     */
    public static final float ONE_SEC_IN_NANOS = 1000000000.0f;

    /**
     * Configuration of this hypervisor.
     */
    protected final KEngineHypervisorConfig config;
    /**
     * Loaded engine components.
     */
    protected final Map<String, KComponent> engineComponents;
    /**
     * Loaded engine debuggers.
     */
    protected final Map<String, Object> loadedDebuggers;
    /**
     * Loaded engine context.
     */
    protected @Nullable KEngineContext ctx;
    /**
     * Spawned application's frame.
     */
    protected @Nullable KFrame frame;

    private final KSimpleEvent tick;
    private final KSimpleEvent newFrame;
    private final KSimpleEvent frameFinished;
    private final KSimpleEvent debugTick;
    private final KSimpleEvent loopEnter;
    private final KSimpleEvent preSwap;
    private final KSimpleEvent ready;
    private final KSimpleEvent loopLeaving;

    private boolean debug;

    /**
     * Constructs hypervisor with provided config.
     * @param config Initialization config of the hypervisor
     */
    public KEngineHypervisor(
        final KEngineHypervisorConfig config
    ) {
        super(
            "konna_hypervisor",
            KStructUtils.setOfTags(KTag.DefaultTags.SYSTEM)
        );

        this.config = config;

        this.engineComponents = new HashMap<>();
        this.loadedDebuggers = new HashMap<>();
        this.ctx = null;

        this.tick = new KSimpleEvent(KFrame.TICK_EVENT_NAME);
        this.newFrame = new KSimpleEvent(KFrame.NEW_FRAME_EVENT_NAME);
        this.frameFinished = new KSimpleEvent(KFrame.FRAME_FINISHED_EVENT_NAME);
        this.debugTick = new KSimpleEvent(KFrame.DEBUG_TICK_EVENT_NAME);
        this.loopEnter = new KSimpleEvent(KFrame.LOOP_ENTER_EVENT_NAME);
        this.preSwap = new KSimpleEvent(KFrame.PRE_SWAP_EVENT_NAME);
        this.ready = new KSimpleEvent(KEngineHypervisor.HYPERVISOR_READY_EVENT_NAME);
    this.loopLeaving = new KSimpleEvent(KFrame.LOOP_LEAVING_EVENT_NAME);
    }

    /**
     * Launches the hypervisor with subsystem initialization,
     * loading context, components and its services.
     * @param features Application features, retrieved from
     *                 {@link io.github.darthakiranihil.konna.core.app.KArgumentParser}
     *                 after parsing arguments
     */
    public void launch(final KApplicationFeatures features) {

        String debugFeature = features.getFeature("debug");
        if (debugFeature != null && debugFeature.equals("true")) {
            this.debug = true;
            KSystemLogger.info(
                this.name,
                "Debug mode is enabled"
            );
        }

        KEngineContextLoader contextLoader;
        try {
            contextLoader = this.config.contextLoader().getDeclaredConstructor().newInstance();
        } catch (
            NoSuchMethodException
            |   InstantiationException
            |   IllegalAccessException
            |   InvocationTargetException e) {
            throw new KHypervisorInitializationException(e);
        }

        this.ctx = contextLoader.load(features);
        this.registerSystemEvents();

        KSystemLogger.info(this.name, "Launching engine hypervisor [config = %s]", config);
        KSystemLogger.info(
            this.name,
            "index: indexed %d classes in %d packages",
            ctx.getClassIndex().size(),
            ctx.getPackageIndex().size()
        );

        KContainer master = ctx.getContainer();

        master
            .add(config.serviceLoader())
            .add(config.componentLoader())
            .add(KFrameLoader.class, config.frameLoader());

        KFrameLoader frameLoader = this.ctx.createObject(KFrameLoader.class);
        this.frame = frameLoader.load(this.ctx, this.config.frameSpawnOptions());

        for (var eventRegisterer: config.eventRegisterers()) {
            KEventRegisterer registerer = ctx.createObject(eventRegisterer);
            registerer.registerEvents(ctx);
        }

        KSystemLogger.info(
            this.name,
            "%d event registerers have been executed",
            config.eventRegisterers().size()
        );

        try {

            KComponentLoader componentLoader = ctx.createObject(config.componentLoader());
            KSystemLogger.info(this.name, "Created component loader %s", config.componentLoader());
            KServiceLoader serviceLoader = ctx.createObject(config.serviceLoader());
            KSystemLogger.info(this.name, "Created service loader %s", config.serviceLoader());

            for (var component: config.components()) {
                componentLoader.load(
                    ctx,
                    component,
                    serviceLoader,
                    this.engineComponents
                );
            }

            KSystemLogger.info(this.name, "Loaded %d components", engineComponents.size());

        } catch (
            KComponentLoadingException e
        ) {
            KSystemLogger.error(this.name, e);
            throw new KHypervisorInitializationException(e);
        }

        engineComponents.forEach((_k, v) -> {
            ctx.registerComponent(v);

            List<KPair<String, KJsonValidator>> assetSchemas = v.getAssetSchemas();
            for (var assetSchema: assetSchemas) {

                ctx.addAssetTypeAlias(
                    assetSchema.first(),
                    assetSchema.second()
                );

            }

        });
        KSystemLogger.info(
            this.name,
            "Registered %d components in the message system",
            engineComponents.size()
        );


        for (var routeConfigurer: config.messageRoutesConfigurers()) {
            KMessageRoutesConfigurer configurer = ctx.createObject(routeConfigurer);
            configurer.setupRoutes(ctx);
        }
        KSystemLogger.info(
            this.name,
            "%d message route configurers have been executed",
            config.messageRoutesConfigurers().size()
        );

        engineComponents.values().forEach(KComponent::postInit);
        KSystemLogger.info(
            this.name,
            "Components' post-init is completed"
        );

        if (this.debug) {
            var debuggers = this
                .ctx
                .getClassIndex()
                .stream()
                .filter(c -> c.isAnnotationPresent(KDebugger.class))
                .toList();

            KSystemLogger.info(
                this.name,
                "Found %d debuggers",
                debuggers.size()
            );

            for (var debugger: debuggers) {
                KSystemLogger.info(
                    this.name,
                    "Loading debugger %s",
                    debugger.getCanonicalName()
                );
                this.loadedDebuggers.put(
                    debugger.getCanonicalName(),
                    this.ctx.createObject(debugger)
                );
            }

        }

        // ready
        this.ready.invokeSync();
        contextLoader.postLoad(this.ctx, features);

    }

    /**
     * Creates a frame of the application and enters the main event loop,
     * until it should close.
     *
     * @since 0.3.0
     */
    public void frameLoop() {

        if (this.ctx == null) {
            throw new KHypervisorInitializationException(
                "Cannot enter frame loop: context is null!"
            );
        }

        if (this.frame == null) {
            throw new KHypervisorInitializationException(
                "Cannot enter frame loop: frame is null!"
            );
        }

        this.frame.show();

        KSystemLogger.info(
            this.name,
            "Entering frame loop. Class: %s",
            this.frame.getClass().getCanonicalName()
        );

        this.loopEnter.invokeSync();
        while (!this.frame.shouldClose()) {

            try {

                Instant beginTime = Instant.now();
                // new_frame
                this.newFrame.invokeSync();

                this.tick.invokeSync();
                if (this.debug) {
                    this.debugTick.invokeSync();
                }

                while (this.frame.isLocked()) {
                    Thread.onSpinWait();
                }

                // pre_swap
                this.preSwap.invokeSync();
                this.frame.swapBuffers();
                this.frame.pollEvents();

                var deltaTime = Duration.between(beginTime, Instant.now());
                KSystemLogger.debug(
                    "hypervisor", "FPS: %f",
                    ONE_SEC_IN_NANOS / deltaTime.getNano()
                );

                this.frameFinished.invokeSync();
                // frame_finished

            } catch (KException kex) {
                switch (kex.getSeverity()) {
                    case EXPECTED, WARNING: {
                        KSystemLogger.warning(this.name, kex);
                        break;
                    }
                    case ERROR: {
                        KSystemLogger.error(this.name, kex);
                        break;
                    }
                    case FATAL: {
                        KSystemLogger.fatal(this.name, "An unhandled fatal exception occurred");
                        KSystemLogger.fatal(this.name, kex);
                        this.shutdown();
                        break;
                    }
                }
            }
        }

        KSystemLogger.info(this.name, "Leaving frame loop");
        this.loopLeaving.invokeSync();
        this.shutdown();

    }

    /**
     * Performs graceful shutdown of this hypervisor.
     * It won't have any effect if there is no loaded engine context.
     */
    public void shutdown() {
        if (this.ctx == null) {
            return;
        }

        this.ctx.handleShutdown();
        this.ctx = null;

        this.engineComponents.values().forEach(KComponent::shutdown);
        this.engineComponents.clear();

        if (this.frame != null) {
            this.frame.setShouldClose(true);
            this.frame = null;
        }
    }

    /**
     * Registers system events for this hypervisor.
     * @since 0.3.0
     */
    protected void registerSystemEvents() {
        if (this.ctx == null) {
            return; // will be checked on entering frame loop
        }

        this.ctx.registerEvent(this.tick);
        this.ctx.registerEvent(this.newFrame);
        this.ctx.registerEvent(this.frameFinished);
        this.ctx.registerEvent(this.debugTick);
        this.ctx.registerEvent(this.preSwap);
        this.ctx.registerEvent(this.loopEnter);
        this.ctx.registerEvent(this.ready);
        this.ctx.registerEvent(this.loopLeaving);

    }

}
