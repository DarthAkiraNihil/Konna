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
import io.github.darthakiranihil.konna.core.di.KAppContainer;
import io.github.darthakiranihil.konna.core.di.KEngineModule;
import io.github.darthakiranihil.konna.core.engine.except.KComponentLoadingException;
import io.github.darthakiranihil.konna.core.engine.except.KHypervisorInitializationException;
import io.github.darthakiranihil.konna.core.except.KException;
import io.github.darthakiranihil.konna.core.io.KAssetLoader;
import io.github.darthakiranihil.konna.core.io.KAssetTypedef;
import io.github.darthakiranihil.konna.core.log.system.KSystemLogger;
import io.github.darthakiranihil.konna.core.message.*;
import io.github.darthakiranihil.konna.core.object.*;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.core.util.KClasspathSearchEngine;
import io.github.darthakiranihil.konna.core.util.KReflectionUtils;
import io.github.darthakiranihil.konna.core.util.KThreadUtils;
import org.jspecify.annotations.Nullable;

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

    protected @Nullable KEngineModule engineModule;
    /**
     * Spawned application's frame.
     */
    protected @Nullable KFrame frame;
    /**
     * Applications's frame task system.
     */
    protected @Nullable KFrameTaskExecutor frameTaskExecutor;
    protected @Nullable KMessageSystem messageSystem;

    private final KSimpleEvent ready;

    private KSystemFeatures systemFeatures;
    private KAppContainer appContainer;

    private final DoubleSummaryStatistics fpsStats;
    private final KUniversalMap fpsData;
    private long nanosPerFrame;

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

        this.ready = new KSimpleEvent(KEngineHypervisor.HYPERVISOR_READY_EVENT_NAME);
        this.systemFeatures = new KSystemFeatures();
        this.appContainer = new KAppContainer.Mock();

        this.fpsStats = new DoubleSummaryStatistics();
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

        this.systemFeatures = new KSystemFeatures(features);
        this.processSystemFeatures();
        this.appContainer = this.createAppContainer(features);

        this.engineModule = KEngineModule.create(this.appContainer);
        KActivator activator = this.engineModule.activator();
        this.frameTaskExecutor = activator.createObject(KFrameTaskExecutor.class);
        this.frameTaskExecutor.setIsDebug(this.systemFeatures.isDebugEnabled());

        this.registerSystemEvents();

        KSystemLogger.info(this.name, "Launching engine hypervisor [config = %s]", config);
        this.frame = activator.createObject(KFrame.class);

        for (var eventRegisterer: config.eventRegisterers()) {
            KEventRegisterer registerer = activator.createObject(eventRegisterer);
            registerer.registerEvents(this.engineModule.eventSystem());
        }

        KSystemLogger.info(
            this.name,
            "%d event registerers have been executed",
            config.eventRegisterers().size()
        );

        KObjectRegistry objectRegistry = this.engineModule.objectRegistry();
        try {

            for (var componentLoaderClass: config.componentLoaders()) {
                KComponentLoader componentLoader = activator.createObject(componentLoaderClass);
                KComponent loaded = componentLoader.load(this.engineModule, features, this.systemFeatures);
                // todo: remove KObjectInstantiationType after reworking registry
                objectRegistry.pushObjectToRegistry(loaded, KObjectInstantiationType.SINGLETON);
                this.engineComponents.put(loaded.name(), loaded);
            }

            KSystemLogger.info(this.name, "Loaded %d components", engineComponents.size());

        } catch (
            KComponentLoadingException e
        ) {
            KSystemLogger.error(this.name, e);
            throw new KHypervisorInitializationException(e);
        }

        KMessageSystem messageSystem = this.engineModule.messageSystem();
        KAssetLoader assetLoader = this.engineModule.assetLoader();

        engineComponents.forEach((_k, v) -> {
            messageSystem.registerComponent(v);

            KAssetTypedef[] assetTypedefs = v.getAssetTypedefs();
            for (var typedef: assetTypedefs) {
                assetLoader.addAssetTypedef(typedef);
            }

        });
        KSystemLogger.info(
            this.name,
            "Registered %d components in the message system",
            engineComponents.size()
        );


        for (var routeConfigurer: config.messageRoutesConfigurers()) {
            KMessageRoutesConfigurer configurer = activator.createObject(routeConfigurer);
            configurer.setupRoutes(messageSystem);
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

        if (this.systemFeatures.isDebugEnabled()) {

            KClasspathSearchEngine classpath = activator.createObject(KClasspathSearchEngine.class);

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
                        activator.createObject(debugger)
                    );
                }
            }

        }

        // ready
        this.ready.invokeSync();

    }

    /**
     * Creates a frame of the application and enters the main event loop,
     * until it should close.
     *
     * @since 0.3.0
     */
    public void frameLoop() {

        if (this.engineModule == null) {
            throw new KHypervisorInitializationException(
                "Cannot enter frame loop: engine module is null!"
            );
        }

        this.messageSystem = this.engineModule.messageSystem();
        if (this.frame == null) {
            throw new KHypervisorInitializationException(
                "Cannot enter frame loop: frame is null!"
            );
        }

        if (this.frameTaskExecutor == null) {
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

        this.frameTaskExecutor.executeScheduledTasks(KFrameEvent.ENTER);
        while (!this.frame.shouldClose()) {

            try {

                Instant beginTime = Instant.now();
                // new_frame
                this.frameTaskExecutor.executeScheduledTasks(KFrameEvent.NEW_FRAME);

                this.frameTaskExecutor.executeScheduledTasks(KFrameEvent.TICK);

                while (this.frame.isLocked()) {
                    Thread.onSpinWait();
                }

                // pre_swap
                this.frameTaskExecutor.executeScheduledTasks(KFrameEvent.PRE_SWAP);
                this.frame.swapBuffers();
                this.frame.pollEvents();

                var deltaTime = Duration.between(beginTime, Instant.now());
                long sleepTime = deltaTime.getNano() + this.nanosPerFrame;
                if (this.systemFeatures.getMaxFps() != -1 && sleepTime > 0) {
                    KThreadUtils.sleepForNano(sleepTime);
                    deltaTime = Duration.between(beginTime, Instant.now());
                }
                var currentFps = ONE_SEC_IN_NANOS / deltaTime.getNano();
                this.fpsStats.accept(currentFps);
                this.fpsData.put("fps", currentFps);
                this.fpsData.put("avg_fps", (float) this.fpsStats.getAverage());
                this.messageSystem.deliverMessageSync(
                    KMessage.metrics(
                        "Konna.fps",
                        this.fpsData
                    )
                );

                this.frameTaskExecutor.executeScheduledTasks(KFrameEvent.FRAME_FINISHED);
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
        this.frameTaskExecutor.executeScheduledTasks(KFrameEvent.SHUTDOWN);
        this.shutdown();

    }

    /**
     * Performs graceful shutdown of this hypervisor.
     * It won't have any effect if there is no loaded engine context.
     */
    public void shutdown() {
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
        if (this.engineModule == null) {
            return; // will be checked on entering frame loop
        }

        KEventSystem eventSystem = this.engineModule.eventSystem();
        KActivator activator = this.engineModule.activator();
        eventSystem.registerEvent(this.ready);

        KClasspathSearchEngine classpath = activator.createObject(KClasspathSearchEngine.class);
        try (
            var result = classpath
                .queryGenerated()
                .implementsInterface(KEventRegisterer.class)
                .execute()
        ) {
            result
                .loadClasses()
                .stream()
                .map(c -> (KEventRegisterer) activator.createObject(c))
                .forEach(er -> er.registerEvents(eventSystem));
        }
    }

    private void processSystemFeatures() {
        if (this.systemFeatures.isDebugEnabled()) {
            KSystemLogger.info(
                this.name,
                "Debug mode is enabled"
            );
        }

        if (this.systemFeatures.isFileLoggingActive()) {
            KSystemLogger.activateFileLogging();
        }

        KSystemLogger.setLogLevel(this.systemFeatures.getLogLevel());
        this.nanosPerFrame = (long) (ONE_SEC_IN_NANOS / this.systemFeatures.getMaxFps());
    }

    private KAppContainer createAppContainer(final KApplicationFeatures features) {
        var constructor = KReflectionUtils.getConstructor(
            this.config.applicationContainer(),
            KApplicationFeatures.class,
            KSystemFeatures.class
        );

        if (constructor == null) {
            throw new KHypervisorInitializationException(
                String.format(
                        "App container class (%s) does not provide a constructor "
                    +   "with exact two arguments of KApplicationFeatures and KSystemFeatures",
                    this.config.applicationContainer()
                )
            );
        }

        try {
            return KReflectionUtils.newInstance(
                constructor,
                features,
                this.systemFeatures
            );
        } catch (Throwable e) {
            throw new KHypervisorInitializationException("Could not create engine app container");
        }
    }

}
