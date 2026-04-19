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
     * <p>
     *     Launches the hypervisor and enters its frame loop.
     * </p>
     * <p>
     *     Hypervisor's launch process is followed by running special stages, whose standard
     *     order is:
     *     <ol>
     *         <li>
     *             {@link
     *             KEngineHypervisor#processSystemFeatures(KSystemFeatures, KLongReference)
     *             processing system features
     *             }
     *         </li>
     *         <li>
     *              {@link
     *              KEngineHypervisor#createAppContainer(KApplicationFeatures, KSystemFeatures)
     *              creating an app container
     *              }
     *         </li>
     *         <li>
     *              {@link KEngineHypervisor#registerSystemEvents(KEventSystem, KActivator, List)
     *              registering system events}
     *         </li>
     *         <li>
     *             {@link
     *             KEngineHypervisor#loadComponents(KEngineModule, KApplicationFeatures,
     *             KSystemFeatures, KActivator, KObjectRegistry, List) loading components}
     *         </li>
     *         <li>
     *             {@link KEngineHypervisor#addAssetTypedefs(KAssetLoader, Map)
     *             adding asset typedefs} to {@link KAssetLoader}
     *         </li>
     *         <li>
     *             {@link
     *             KEngineHypervisor#configureMessageRoutes(KActivator, KMessageSystem, Map, List)
     *             configuring message routes
     *             }
     *         </li>
     *         <li>
     *             Executing {@link KComponent#postInit()} of all loaded components
     *         </li>
     *         <li>
     *             {@link KEngineHypervisor#runDebugStages(KEngineModule) running debug stages} (if
     *             {@link KSystemFeatures#isDebugEnabled() debug mode} is enabled.
     *         </li>
     *     </ol>
     * </p>
     * <p>
     *     After the moment all stages are completed, it enters the {@link
     *     KEngineHypervisor#frameLoop(KSystemFeatures, KFrame, KMessageSystem, KFrameTaskExecutor,
     *     long) frame loop}
     * </p>
     * <p>
     *     Each of stages can be overridden. Even more, the launch process can be overridden with
     *     even possibility to change order of executed stages. However, you should do this
     *     if the standard launch process does not fit your needs.
     * </p>
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
            engineModule,
            features,
            systemFeatures,
            activator,
            objectRegistry,
            this.config.componentLoaders()
        );

        this.engineComponents.putAll(loadedComponents);


        KAssetLoader assetLoader = engineModule.assetLoader();
        this.addAssetTypedefs(assetLoader, loadedComponents);
        KMessageSystem messageSystem = engineModule.messageSystem();
        this.configureMessageRoutes(
            activator,
            messageSystem,
            loadedComponents,
            this.config.messageRoutesConfigurers()
        );

        this.engineComponents.values().forEach(KComponent::postInit);

        if (systemFeatures.isDebugEnabled()) {
            this.runDebugStages(engineModule);
        }

        this.frame = activator.createObject(KFrame.class);
        KSystemLogger.debug(this.name, "Acquired frame: %s", frame);
        this.ready.invokeSync();
        this.frameLoop(
            systemFeatures,
            frame,
            messageSystem,
            frameTaskExecutor,
            nanosPerFrame.get()
        );
    }

    /**
     * <p>
     *     Runs the frame loop. The frame loop will run until the frame is closed.
     * </p>
     * <p>
     *     Overriding this method is not actually recommended. If you do this, you are on
     *     you own. However, if this implementation does not fit your requirements, it
     *     can be overridden.
     * </p>
     * <p>
     *     An iteration of the frame loop consist of execution of scheduled frame tasks in
     *     corresponding frame event order:
     *     <ol>
     *         <li>{@link KFrameEvent#NEW_FRAME}</li>
     *         <li>{@link KFrameEvent#TICK}</li>
     *         <li>{@link KFrameEvent#PRE_SWAP}</li>
     *         <li>{@link KFrameEvent#FRAME_FINISHED}</li>
     *     </ol>
     *     There are also two special frame events that execute only once:
     *     <ul>
     *         <li>{@link KFrameEvent#ENTER}</li>
     *         <li>{@link KFrameEvent#SHUTDOWN}</li>
     *     </ul>
     * </p>
     * <p>
     *     Please note that if iteration's execution time is limited, it must wait
     *     until its time window is complete. Else it should just continue execution.
     * </p>
     * <p>
     *     This implementation also sends FPS statistics by sending a message with id
     *     {@code Konna.fps} with {@link KMessageType#METRICS} type.
     * </p>
     *
     * @param systemFeatures Application's system features
     * @param appFrame Application's frame
     * @param messageSystem Message system
     * @param frameTaskExecutor Frame task executor
     * @param nanosPerFrame Amount of nanoseconds reserved for a single frame iteration.
     *                      It is ignored if {@link KSystemFeatures#getMaxFps() max FPS}
     *                      is set to -1
     *
     * @since 0.6.0
     */
    protected void frameLoop(
        final KSystemFeatures systemFeatures,
        final KFrame appFrame,
        final KMessageSystem messageSystem,
        final KFrameTaskExecutor frameTaskExecutor,
        long nanosPerFrame
    ) {

        appFrame.show();
        KSystemLogger.info(this.name, "Entering frame loop");

        KUniversalMap fpsData = new KUniversalMap();
        DoubleSummaryStatistics fpsStats = new DoubleSummaryStatistics();
        fpsStats.accept(0.0); // to prevent division by zero

        frameTaskExecutor.executeScheduledTasks(KFrameEvent.ENTER);
        while (!appFrame.shouldClose()) {

            try {

                Instant beginTime = Instant.now();
                // new_frame
                frameTaskExecutor.executeScheduledTasks(KFrameEvent.NEW_FRAME);
                frameTaskExecutor.executeScheduledTasks(KFrameEvent.TICK);

                while (appFrame.isLocked()) { // ??? do we really need this???
                    Thread.onSpinWait();
                }

                // pre_swap
                frameTaskExecutor.executeScheduledTasks(KFrameEvent.PRE_SWAP);
                appFrame.swapBuffers();
                appFrame.pollEvents();

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
                        appFrame.setShouldClose(true);
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

    /**
     * Registers all system events and executes all found generated and externally provided
     * {@link KEventRegisterer event registers}.
     *
     * @param eventSystem Event system to register events in
     * @param activator Activator to instantiate event registers
     * @param additionalEventRegisters List of non-generated additional event registers
     *
     * @since 0.6.0
     */
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

    /**
     * <p>
     *     Processes system features by configuring something related to it.
     * </p>
     * <p>
     *     This particular implementation is activation file logging if it is set as active
     *     and sets a log level. Both operations are applied to {@link KSystemLogger}.
     *     Additionally, it calculates number of nanoseconds, reserved for a single frame.
     * </p>
     * @param systemFeatures Application's system features
     * @param nanosPerFrame Reference to a number of nanoseconds per a single frame
     *
     * @since 0.6.0
     */
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

    /**
     * Creates app container for current application.
     * @param features Application's features
     * @param systemFeatures Application's system features
     * @return Instantiated app container
     *
     * @since 0.6.0
     */
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

    /**
     * Loads all components from provided list of component loaders.
     * @param engineModule Current application's engine module
     * @param applicationFeatures Application's features
     * @param systemFeatures Application's system features
     * @param activator Activator to instantiate component loaders
     * @param objectRegistry Object registry to put loaded components to
     * @param componentLoaders List of classes of component loaders
     * @return Map of loaded components
     *
     * @since 0.6.0
     */
    protected Map<String, KComponent> loadComponents(
        final KEngineModule engineModule,
        final KApplicationFeatures applicationFeatures,
        final KSystemFeatures systemFeatures,
        final KActivator activator,
        final KObjectRegistry objectRegistry,
        final List<Class<? extends KComponentLoader>> componentLoaders
    ) {

        Map<String, KComponent> loadedComponents = new HashMap<>(componentLoaders.size());

        try {
            for (var componentLoaderClass: componentLoaders) {
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

    /**
     * Configures message routes for current application's message system. Also registers
     * all loaded components in this system.
     * @param activator Activator to instantiate {@link KMessageRoutesConfigurer}s
     * @param messageSystem Message system to configure
     * @param loadedComponents Map
     * @param configurers List of classes of message route configurers
     *
     * @since 0.6.0
     */
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

    /**
     * Adds all asset typedefs, provided by all components, to current asset loader.
     * @param assetLoader Asset loader to add the typedefs
     * @param loadedComponents Map of loaded components
     *
     * @since 0.6.0
     */
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

    /**
     * <p>
     *     Executes all debug stages for this hypervisor.
     * </p>
     * <p>
     *     As for now, debug stages consist of the only
     *     {@link KEngineHypervisor#loadDebuggers(KActivator) loadDebuggers} stage.
     * </p>
     * @param engineModule Engine module of current application
     *
     * @since 0.6.0
     */
    protected void runDebugStages(final KEngineModule engineModule) {
        KActivator activator = engineModule.activator();
        this.loadDebuggers(activator);
    }

    // todo: make it return map when KRuntime will be added

    /**
     * Instantiates all found debug classes (annotated with {@link KDebugger}).
     * @param activator Activator to create debuggers with
     *
     * @since 0.6.0
     */
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
