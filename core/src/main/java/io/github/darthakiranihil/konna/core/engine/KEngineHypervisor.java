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

import io.github.darthakiranihil.konna.core.app.*;
import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.debug.KDebugger;
import io.github.darthakiranihil.konna.core.di.KContainer;
import io.github.darthakiranihil.konna.core.di.KContainerModifier;
import io.github.darthakiranihil.konna.core.engine.except.KComponentLoadingException;
import io.github.darthakiranihil.konna.core.engine.except.KHypervisorInitializationException;
import io.github.darthakiranihil.konna.core.except.KException;
import io.github.darthakiranihil.konna.core.io.KAssetTypedef;
import io.github.darthakiranihil.konna.core.log.KLogLevel;
import io.github.darthakiranihil.konna.core.log.system.KSystemLogger;
import io.github.darthakiranihil.konna.core.message.KEventRegisterer;
import io.github.darthakiranihil.konna.core.message.KMessage;
import io.github.darthakiranihil.konna.core.message.KMessageRoutesConfigurer;
import io.github.darthakiranihil.konna.core.message.KSimpleEvent;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.core.util.KClasspathSearchEngine;
import io.github.darthakiranihil.konna.core.util.KThreadUtils;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.time.Instant;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
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
    protected @Nullable KFrameTaskSystem frameTaskSystem;

    private final KSimpleEvent tick;
    private final KSimpleEvent newFrame;
    private final KSimpleEvent frameFinished;
    private final KSimpleEvent ready;
    private final KSimpleEvent loopLeaving;

    private boolean debug;
    private final DoubleSummaryStatistics fps;
    private int maxFps;
    private long nanosPerFrame;
    private final KUniversalMap fpsData;

    /**
     * Constructs hypervisor with provided config.
     * @param config Initialization config of the hypervisor
     */
    public KEngineHypervisor(
        final KEngineHypervisorConfig config
    ) {
        super(
            "KEngineHypervisor",
            KStructUtils.setOfTags(KTag.DefaultTags.SYSTEM)
        );

        this.config = config;

        this.engineComponents = new HashMap<>();
        this.loadedDebuggers = new HashMap<>();
        this.ctx = null;

        this.tick = new KSimpleEvent(KFrame.TICK_EVENT_NAME);
        this.newFrame = new KSimpleEvent(KFrame.NEW_FRAME_EVENT_NAME);
        this.frameFinished = new KSimpleEvent(KFrame.FRAME_FINISHED_EVENT_NAME);
        this.ready = new KSimpleEvent(KEngineHypervisor.HYPERVISOR_READY_EVENT_NAME);
        this.loopLeaving = new KSimpleEvent(KFrame.LOOP_LEAVING_EVENT_NAME);

        this.maxFps = -1;
        this.fps = new DoubleSummaryStatistics();
        this.fpsData = new KUniversalMap();
    }

    /**
     * Launches the hypervisor with subsystem initialization,
     * loading context, components and its services.
     * @param features Application features, retrieved from
     *                 {@link io.github.darthakiranihil.konna.core.app.KArgumentParser}
     *                 after parsing arguments
     */
    public void launch(final KApplicationFeatures features) {

        this.processSystemFeatures(features);
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
        this.frameTaskSystem = this.ctx.createObject(KFrameTaskSystem.class);
        this.frameTaskSystem.setIsDebug(this.debug);

        this.registerSystemEvents();

        KSystemLogger.info(this.name, "Launching engine hypervisor [config = %s]", config);

        KContainer master = this.ctx.getContainer();

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

            KAssetTypedef[] assetTypedefs = v.getAssetTypedefs();
            for (var typedef: assetTypedefs) {
                ctx.addAssetTypedef(typedef);
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

            KClasspathSearchEngine classpath = ctx.createObject(KClasspathSearchEngine.class);

            try (
                var debuggersSearchResult = classpath
                    .query()
                    .withAnnotation(KDebugger.class)
                    .execute()
            ) {
                var debuggers = debuggersSearchResult.loadClasses();
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

        if (this.frameTaskSystem == null) {
            throw new KHypervisorInitializationException(
                "Cannot enter frame loop: frame task system is null!"
            );
        }

        this.frame.show();

        KSystemLogger.info(
            this.name,
            "Entering frame loop. Class: %s",
            this.frame.getClass().getCanonicalName()
        );

        this.frameTaskSystem.executeScheduledTasks(KFrameEvent.ENTER);
        while (!this.frame.shouldClose()) {

            try {

                Instant beginTime = Instant.now();
                // new_frame
                this.newFrame.invokeSync();
                this.frameTaskSystem.executeScheduledTasks(KFrameEvent.NEW_FRAME);

                this.tick.invokeSync();
                this.frameTaskSystem.executeScheduledTasks(KFrameEvent.TICK);

                while (this.frame.isLocked()) {
                    Thread.onSpinWait();
                }

                // pre_swap
                this.frameTaskSystem.executeScheduledTasks(KFrameEvent.PRE_SWAP);
                this.frame.swapBuffers();
                this.frame.pollEvents();

                var deltaTime = Duration.between(beginTime, Instant.now());
                long sleepTime = deltaTime.getNano() + this.nanosPerFrame;
                if (this.maxFps != -1 && sleepTime > 0) {
                    KThreadUtils.sleepForNano(sleepTime);
                    deltaTime = Duration.between(beginTime, Instant.now());
                }
                var currentFps = ONE_SEC_IN_NANOS / deltaTime.getNano();
                this.fps.accept(currentFps);
                this.fpsData.put("fps", currentFps);
                this.fpsData.put("avg_fps", (float) this.fps.getAverage());
                this.ctx.deliverMessageSync(
                    KMessage.metrics(
                        "Konna.fps",
                        this.fpsData
                    )
                );

                this.frameFinished.invokeSync();
                this.frameTaskSystem.executeScheduledTasks(KFrameEvent.FRAME_FINISHED);
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
        this.frameTaskSystem.executeScheduledTasks(KFrameEvent.SHUTDOWN);
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
        this.ctx.registerEvent(this.ready);
        this.ctx.registerEvent(this.loopLeaving);

        KClasspathSearchEngine classpath = this.ctx.createObject(KClasspathSearchEngine.class);
        try (
            var result = classpath
                .queryGenerated()
                .implementsInterface(KEventRegisterer.class)
                .execute()
        ) {
            result
                .loadClasses()
                .stream()
                .map(c -> (KEventRegisterer) this.ctx.createObject(c))
                .forEach(er -> er.registerEvents(this.ctx));
        }
    }

    private void processSystemFeatures(final KApplicationFeatures features) {
        String debugFeature = features.getFeature("debug");
        if (debugFeature != null && debugFeature.equals("true")) {
            this.debug = true;
            KSystemLogger.info(
                this.name,
                "Debug mode is enabled"
            );
        }
        String logToFileFeature = features.getFeature("log-to-file");
        if (logToFileFeature != null && logToFileFeature.equals("true")) {
            KSystemLogger.activateFileLogging();
        }

        String logLevel = features.getFeature("log-level");
        if (logLevel != null) {
            KSystemLogger.setLogLevel(KLogLevel.valueOf(logLevel));
        }

        String maxFpsFeature = features.getFeature("max-fps");
        if (maxFpsFeature != null) {
            this.maxFps = Integer.parseInt(maxFpsFeature);
        }
        this.nanosPerFrame = (long) (ONE_SEC_IN_NANOS / this.maxFps);
    }

}
