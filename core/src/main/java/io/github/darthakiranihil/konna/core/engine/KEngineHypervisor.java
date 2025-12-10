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

import io.github.darthakiranihil.konna.core.data.json.KJsonParser;
import io.github.darthakiranihil.konna.core.data.json.KJsonValidator;
import io.github.darthakiranihil.konna.core.di.KContainer;
import io.github.darthakiranihil.konna.core.di.KContainerResolver;
import io.github.darthakiranihil.konna.core.di.KEnvironmentContainerModifier;
import io.github.darthakiranihil.konna.core.engine.except.KComponentLoadingException;
import io.github.darthakiranihil.konna.core.engine.except.KHypervisorInitializationException;
import io.github.darthakiranihil.konna.core.io.KAssetLoader;
import io.github.darthakiranihil.konna.core.log.KSystemLogger;
import io.github.darthakiranihil.konna.core.message.KEventRegisterer;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.message.KMessageRoutesConfigurer;
import io.github.darthakiranihil.konna.core.message.KMessageSystem;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.core.util.KIndex;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Konna Engine Hypervisor - the primal class for the engine that controls the whole system.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@KEnvironmentContainerModifier
public class KEngineHypervisor extends KObject {

    private final KComponentLoader componentLoader;
    private final KServiceLoader serviceLoader;
    private final Map<String, KComponent> engineComponents;
    private final KJsonParser jsonParser;

    private final KActivator activator;
    private final KContainerResolver containerResolver;
    private final KIndex index;
    private final KEventSystem eventSystem;
    private final KMessageSystem messageSystem;
    private final KAssetLoader assetLoader;

    /**
     * Constructs hypervisor with provided config.
     * @param config Initialization config of the hypervisor
     */
    public KEngineHypervisor(
        final KEngineHypervisorConfig config
    ) {

        super(
            KEngineHypervisor.class.getSimpleName(),
            KStructUtils.setOfTags(KTag.DefaultTags.SYSTEM)
        );

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

        var ctx = contextLoader.load();

        this.activator = ctx.activator();
        this.containerResolver = ctx.containerResolver();
        this.index = ctx.index();
        this.messageSystem = ctx.messageSystem();
        this.eventSystem = ctx.eventSystem();
        this.assetLoader = ctx.assetLoader();

        KSystemLogger.info("Initializing engine hypervisor [config = %s]", config);
        KSystemLogger.info(
            "%s: Indexed %d classes in %d packages",
            this.index.getClass().getSimpleName(),
            this.index.getClassIndex().size(),
            this.index.getPackageIndex().size()
        );
        var envs = this.containerResolver.getEnvironments();
        KSystemLogger.info(
            "%s: created %d environments: %s",
            this.containerResolver.getClass().getSimpleName(),
            envs.size(),
            envs
        );
        KSystemLogger.info(
            "Got activator: %s", this.activator.getClass().getSimpleName()
        );
        KSystemLogger.info(
            "Got object registry: %s", ctx.objectRegistry().getClass().getSimpleName()
        );
        KSystemLogger.info(
            "Got event system: %s", this.eventSystem.getClass().getSimpleName()
        );
        KSystemLogger.info(
            "Got message system: %s", this.messageSystem.getClass().getSimpleName()
        );
        KSystemLogger.info(
            "Got asset loader: %s", ctx.assetLoader().getClass().getSimpleName()
        );

        this.jsonParser = this.activator.create(KJsonParser.class);
        KContainer master = this.containerResolver.resolve();

        master
            .add(config.serviceLoader())
            .add(config.componentLoader());

        try {

            this.componentLoader = this.activator.create(config.componentLoader());

            KSystemLogger.info("Created component loader %s", config.componentLoader());

            this.serviceLoader = this.activator.create(config.serviceLoader());

            KSystemLogger.info("Created service loader %s", config.serviceLoader());

            this.engineComponents = new HashMap<>();

            for (var component: config.components()) {
                this.componentLoader.load(
                    ctx,
                    component,
                    this.serviceLoader,
                    this.engineComponents
                );
            }

            KSystemLogger.info("Loaded %d components", this.engineComponents.size());

        } catch (
            KComponentLoadingException e
        ) {
            KSystemLogger.error(e);
            throw new KHypervisorInitializationException(e);
        }

        this.engineComponents.forEach((_k, v) -> {
            this.messageSystem.registerComponent(v);

            List<KPair<String, KJsonValidator>> assetSchemas = v.getAssetSchemas();
            for (var assetSchema: assetSchemas) {

                this.assetLoader.addAssetTypeAlias(
                    assetSchema.first(),
                    assetSchema.second()
                );

            }

        });

        KSystemLogger.info(
            "Registered %d components in the message system",
            this.engineComponents.size()
        );


        for (var routeConfigurer: config.messageRoutesConfigurers()) {
            KMessageRoutesConfigurer configurer = this.activator.create(routeConfigurer);
            configurer.setupRoutes(this.messageSystem);
        }

        KSystemLogger.info(
            "%d message route configurers have been executed",
            config.messageRoutesConfigurers().size()
        );

        for (var eventRegisterer: config.eventRegisterers()) {
            KEventRegisterer registerer = this.activator.create(eventRegisterer);
            registerer.registerEvents(this.eventSystem);
        }

        KSystemLogger.info(
            "%d event registerers have been executed",
            config.eventRegisterers().size()
        );

        this.engineComponents.forEach((_k, c) -> {
            c.postInit();
        });

        KSystemLogger.info(
            "Component's post-init is completed"
        );


    }

}
