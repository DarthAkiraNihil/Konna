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
import io.github.darthakiranihil.konna.core.data.json.KJsonValidator;
import io.github.darthakiranihil.konna.core.di.KContainer;
import io.github.darthakiranihil.konna.core.di.KContainerModifier;
import io.github.darthakiranihil.konna.core.engine.except.KComponentLoadingException;
import io.github.darthakiranihil.konna.core.engine.except.KHypervisorInitializationException;
import io.github.darthakiranihil.konna.core.log.KSystemLogger;
import io.github.darthakiranihil.konna.core.message.KEventRegisterer;
import io.github.darthakiranihil.konna.core.message.KMessageRoutesConfigurer;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
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
            contextLoader = config.contextLoader().getDeclaredConstructor().newInstance();
        } catch (
            NoSuchMethodException
            |   InstantiationException
            |   IllegalAccessException
            |   InvocationTargetException e) {
            throw new KHypervisorInitializationException(e);
        }

        this.ctx = contextLoader.load(features);

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
            .add(config.componentLoader());

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
     * Performs graceful shutdown of this hypervisor.
     * It won't have any effect if there is no loaded engine context.
     */
    public void shutdown() {
        if (this.ctx == null) {
            return;
        }

        this.ctx.handleShutdown();
        this.engineComponents.values().forEach(KComponent::shutdown);
        this.engineComponents.clear();
    }

}
