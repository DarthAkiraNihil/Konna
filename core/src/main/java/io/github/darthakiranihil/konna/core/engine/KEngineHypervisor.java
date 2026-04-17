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
import io.github.darthakiranihil.konna.core.engine.except.KHypervisorInitializationException;
import io.github.darthakiranihil.konna.core.except.KException;
import io.github.darthakiranihil.konna.core.io.KAssetLoader;
import io.github.darthakiranihil.konna.core.io.KAssetTypedef;
import io.github.darthakiranihil.konna.core.log.system.KSystemLogger;
import io.github.darthakiranihil.konna.core.message.*;
import io.github.darthakiranihil.konna.core.object.*;
import io.github.darthakiranihil.konna.core.struct.ref.KLongReference;
import io.github.darthakiranihil.konna.core.util.KClasspathSearchEngine;
import io.github.darthakiranihil.konna.core.util.KReflectionUtils;
import io.github.darthakiranihil.konna.core.util.KThreadUtils;
import org.jspecify.annotations.Nullable;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

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

    /**
     * Spawned application's frame.
     */
    protected @Nullable KFrame frame;
    private final KSimpleEvent ready;

    /**
     * Constructs hypervisor with provided config.
     * @param config Initialization config of the hypervisor
     */
    public KEngineHypervisor(
        final KEngineHypervisorConfig config
    ) {
        super(
            "KEngineHypervisor",
            Collections.singleton(KDefaultTags.SYSTEM)
        );

        this.config = config;
        this.engineComponents = new HashMap<>();
        this.loadedDebuggers = new HashMap<>();
        this.ready = new KSimpleEvent(KEngineHypervisor.HYPERVISOR_READY_EVENT_NAME);
    }

    /**
     * Launches the hypervisor with subsystem initialization,
     * loading context, components and its services.
     * @param features Application features, retrieved from
     *                 {@link io.github.darthakiranihil.konna.core.app.KArgumentParser}
     *                 after parsing arguments
     */
    public void launch(final KApplicationFeatures features) {
        KSystemLogger.info(this.name, "Launching hypervisor");
        KSystemFeatures systemFeatures = new KSystemFeatures(features);
        KLongReference nanosPerFrame = new KLongReference(-1);
        this.processSystemFeatures(systemFeatures, nanosPerFrame);
        KSystemLogger.debug(this.name, systemFeatures.toString());

        KAppContainer appContainer = this.createAppContainer(features, systemFeatures);
        KSystemLogger.debug(
            this.name, "Created app container: %s", appContainer
        );

        KEngineModule engineModule = KEngineModule.create(appContainer);
        KSystemLogger.debug(this.name, "Acquired engine module: %s", engineModule);

        KActivator activator = engineModule.activator();
        KEventSystem eventSystem = engineModule.eventSystem();

        // todo: remove method and pass is debug to constructor through k system features
        KFrameTaskExecutor frameTaskExecutor = activator.createObject(KFrameTaskExecutor.class);
        frameTaskExecutor.setIsDebug(systemFeatures.isDebugEnabled());
        KSystemLogger.debug(this.name, "Acquired frame task executor: %s", frameTaskExecutor);

        this.registerSystemEvents(eventSystem, activator, this.config.eventRegisterers());

        KObjectRegistry objectRegistry = engineModule.objectRegistry();
        Map<String, KComponent> loadedComponents = this.loadComponents(
            engineModule, features, systemFeatures, activator, objectRegistry
        );

        KMessageSystem messageSystem = engineModule.messageSystem();
        this.configureMessageRoutes(
            activator,
            messageSystem,
            loadedComponents,
            this.config.messageRoutesConfigurers()
        );

        KAssetLoader assetLoader = engineModule.assetLoader();
        this.addAssetTypedefs(assetLoader, loadedComponents);
        this.engineComponents.values().forEach(KComponent::postInit);

        if (systemFeatures.isDebugEnabled()) {
            this.runDebugStages(engineModule);
        }

        this.frame = activator.createObject(KFrame.class);
        KSystemLogger.debug(this.name,"Acquired frame: %s", frame);
        this.ready.invokeSync();
        this.frameLoop(
            systemFeatures,
            frame,
            messageSystem,
            frameTaskExecutor,
            nanosPerFrame.get()
        );
    }

    private void frameLoop(
        final KSystemFeatures systemFeatures,
        final KFrame frame,
        final KMessageSystem messageSystem,
        final KFrameTaskExecutor frameTaskExecutor,
        long nanosPerFrame
    ) {

        frame.show();
        KSystemLogger.info(
            this.name,
            "Entering frame loop. Class: %s",
            frame.getClass().getSimpleName()
        );

        KUniversalMap fpsData = new KUniversalMap();
        DoubleSummaryStatistics fpsStats = new DoubleSummaryStatistics();
        fpsStats.accept(0.0); // to prevent division by zero

        frameTaskExecutor.executeScheduledTasks(KFrameEvent.ENTER);
        while (!frame.shouldClose()) {

            try {

                Instant beginTime = Instant.now();
                // new_frame
                frameTaskExecutor.executeScheduledTasks(KFrameEvent.NEW_FRAME);
                frameTaskExecutor.executeScheduledTasks(KFrameEvent.TICK);

                while (frame.isLocked()) { // ??? do we really need this???
                    Thread.onSpinWait();
                }

                // pre_swap
                frameTaskExecutor.executeScheduledTasks(KFrameEvent.PRE_SWAP);
                frame.swapBuffers();
                frame.pollEvents();

                var deltaTime = Duration.between(beginTime, Instant.now());
                long sleepTime = deltaTime.getNano() + nanosPerFrame;

                if (systemFeatures.getMaxFps() != -1 && sleepTime > 0) {
                    KThreadUtils.sleepForNano(sleepTime);
                    deltaTime = Duration.between(beginTime, Instant.now());
                }

                var currentFps = ONE_SEC_IN_NANOS / deltaTime.getNano();
                fpsStats.accept(currentFps);

                fpsData.put("fps", currentFps);
                fpsData.put("avg_fps", (float) fpsStats.getAverage());
                messageSystem.deliverMessageSync(KMessage.metrics("Konna.fps", fpsData));

                frameTaskExecutor.executeScheduledTasks(KFrameEvent.FRAME_FINISHED);
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
                        frame.setShouldClose(true);
                        break;
                    }
                }
            }
        }

        KSystemLogger.info(this.name, "Leaving frame loop");
        frameTaskExecutor.executeScheduledTasks(KFrameEvent.SHUTDOWN);
    }

    @Override
    protected void deleteSelf() {
        this.engineComponents.clear();

        if (this.frame != null) {
            this.frame.setShouldClose(true);
            this.frame = null;
        }
    }

    protected void registerSystemEvents(
        final KEventSystem eventSystem,
        final KActivator activator,
        final List<Class<? extends KEventRegisterer>> additionalEventRegisters
    ) {

        eventSystem.registerEvent(this.ready);
        var classpath = activator.createObject(KClasspathSearchEngine.class);

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

        // todo: why do we fucking need this since all required event registers are
        //  generated automatically?????
        for (var eventRegisterer: additionalEventRegisters) {
            KEventRegisterer registerer = activator.createObject(eventRegisterer);
            registerer.registerEvents(eventSystem);
        }

        KSystemLogger.info(
            this.name,
            "%d additional event registerers have been executed",
            config.eventRegisterers().size()
        );

    }

    protected void processSystemFeatures(
        final KSystemFeatures systemFeatures,
        final KLongReference nanosPerFrame
    ) {
        if (systemFeatures.isDebugEnabled()) {
            KSystemLogger.info(
                this.name,
                "Debug mode is enabled"
            );
        }

        if (systemFeatures.isFileLoggingActive()) {
            KSystemLogger.activateFileLogging();
        }

        KSystemLogger.setLogLevel(systemFeatures.getLogLevel());
        nanosPerFrame.set((long) (ONE_SEC_IN_NANOS / systemFeatures.getMaxFps()));
    }

    protected KAppContainer createAppContainer(
        final KApplicationFeatures features,
        final KSystemFeatures systemFeatures
    ) {
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
                systemFeatures
            );
        } catch (Throwable e) {
            throw new KHypervisorInitializationException("Could not create engine app container");
        }
    }

    protected Map<String, KComponent> loadComponents(
        final KEngineModule engineModule,
        final KApplicationFeatures applicationFeatures,
        final KSystemFeatures systemFeatures,
        final KActivator activator,
        final KObjectRegistry objectRegistry
    ) {

        Map<String, KComponent> loadedComponents = new HashMap<>(config.componentLoaders().size());

        try {
            for (var componentLoaderClass: config.componentLoaders()) {
                KSystemLogger.debug(
                    this.name,
                    "Executing component loader %s",
                    componentLoaderClass.getSimpleName()
                );

                KComponentLoader componentLoader = activator.createObject(componentLoaderClass);
                KComponent loaded = componentLoader.load(
                    engineModule,
                    applicationFeatures,
                    systemFeatures
                );

                objectRegistry.pushImmortalObject(loaded);
                loadedComponents.put(loaded.name(), loaded);
                this.addChild(loaded);
            }

            KSystemLogger.info(this.name, "Loaded %d components", engineComponents.size());
        } catch (Throwable e) {
            throw new KHypervisorInitializationException(e);
        }

        return loadedComponents;
    }

    protected void configureMessageRoutes(
        final KActivator activator,
        final KMessageSystem messageSystem,
        final Map<String, KComponent> loadedComponents,
        final List<Class<? extends KMessageRoutesConfigurer>> configurers
    ) {
        for (KComponent component: loadedComponents.values()) {
            messageSystem.registerComponent(component);
        }

        for (var routeConfigurer: configurers) {
            KSystemLogger.debug(
                this.name,
                "Executing message route configurer %s", routeConfigurer.getSimpleName()
            );
            KMessageRoutesConfigurer configurer = activator.createObject(routeConfigurer);
            configurer.setupRoutes(messageSystem);
        }

        KSystemLogger.info(
            this.name, "Executed %d message route configurers", configurers.size()
        );
    }

    protected void addAssetTypedefs(
        final KAssetLoader assetLoader,
        final Map<String, KComponent> loadedComponents
    ) {

        for (KComponent component: loadedComponents.values()) {
            KSystemLogger.debug(
                this.name,
                "Adding asset typedefs of %s",
                component.getClass().getSimpleName()
            );

            KAssetTypedef[] assetTypedefs = component.getAssetTypedefs();
            for (var typedef: assetTypedefs) {
                assetLoader.addAssetTypedef(typedef);
            }
        }

    }

    protected void runDebugStages(final KEngineModule engineModule) {
        KActivator activator = engineModule.activator();
        this.loadDebuggers(activator);
    }

    // todo: make it return map when KRuntime will be added
    protected void loadDebuggers(final KActivator activator) {
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

}
