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
import io.github.darthakiranihil.konna.core.di.KContainer;
import io.github.darthakiranihil.konna.core.di.KContainerModifier;
import io.github.darthakiranihil.konna.core.engine.except.KComponentLoadingException;
import io.github.darthakiranihil.konna.core.engine.except.KHypervisorInitializationException;
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
import java.util.*;

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
     * Configuration of this hypervisor.
     */
    protected final KEngineHypervisorConfig config;
    /**
     * Loaded engine components.
     */
    protected final Map<String, KComponent> engineComponents;
    /**
     * Loaded engine context.
     */
    protected @Nullable KEngineContext ctx;
    /**
     * Spawned application's frame.
     */
    protected @Nullable KFrame frame;

    private final KSimpleEvent tick;

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
        this.ctx = null;
        this.tick = new KSimpleEvent(KFrame.TICK_EVENT_NAME);
    }

    /**
     * Launches the hypervisor with subsystem initialization,
     * loading context, components and its services.
     * @param features Application features, retrieved from
     *                 {@link io.github.darthakiranihil.konna.core.app.KArgumentParser}
     *                 after parsing arguments
     */
    public void launch(final KApplicationFeatures features) {

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

        this.ctx.registerEvent(this.tick);

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

        while (!this.frame.shouldClose()) {

            Instant beginTime = Instant.now();

            this.tick.invokeSync();

            while (this.frame.isLocked()) {
                Thread.onSpinWait();
            }
            this.frame.swapBuffers();
            this.frame.pollEvents();

            var deltaTime = Duration.between(beginTime, Instant.now());
            KSystemLogger.debug("hypervisor", "FPS: %f", 1000000000.0f  / deltaTime.getNano());

        }

        KSystemLogger.info(this.name, "Leaving frame loop");
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

}
