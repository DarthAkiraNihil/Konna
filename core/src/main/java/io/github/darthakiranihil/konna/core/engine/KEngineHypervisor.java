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
import io.github.darthakiranihil.konna.core.di.KContainer;
import io.github.darthakiranihil.konna.core.di.KContainerResolver;
import io.github.darthakiranihil.konna.core.di.KEnvironmentContainerModifier;
import io.github.darthakiranihil.konna.core.engine.except.KComponentLoadingException;
import io.github.darthakiranihil.konna.core.engine.except.KHypervisorInitializationException;
import io.github.darthakiranihil.konna.core.log.KLogger;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.util.KIndex;
import io.github.darthakiranihil.konna.core.util.KStructUtils;

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
    private final KLogger logger;
    private final KIndex index;

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
        this.logger = ctx.logger();
        this.index = ctx.index();

        this.logger.info("Initializing engine hypervisor [config = %s]", config);
        this.logger.info(
            "%s: Indexed %d classes in %d packages",
            this.index.getClass().getSimpleName(),
            this.index.getClassIndex().size(),
            this.index.getPackageIndex().size()
        );
        var envs = this.containerResolver.getEnvironments();
        this.logger.info(
            "%s: created %d environments: %s",
            this.containerResolver.getClass().getSimpleName(),
            envs.size(),
            envs
        );
        this.logger.info(
            "Got activator: %s", this.activator.getClass().getSimpleName()
        );
        this.logger.info(
            "Got object registry: %s", ctx.objectRegistry().getClass().getSimpleName()
        );
        this.logger.info(
            "Got logger: %s", this.logger.getClass().getSimpleName()
        );

        this.jsonParser = this.activator.create(KJsonParser.class);
        KContainer master = this.containerResolver.resolve();

        master
            .add(config.serviceLoader())
            .add(config.componentLoader());

        try {

            this.componentLoader = this.activator.create(config.componentLoader());

            this.logger.info("Created component loader %s", config.componentLoader());

            this.serviceLoader = this.activator.create(config.serviceLoader());

            this.logger.info("Created service loader %s", config.serviceLoader());

            this.engineComponents = new HashMap<>();

            for (var component: config.components()) {
                this.componentLoader.load(
                    ctx,
                    component,
                    this.serviceLoader,
                    this.engineComponents
                );
            }

            this.logger.info("Loaded %d components", this.engineComponents.size());

        } catch (
            KComponentLoadingException e
        ) {
            this.logger.error(e);
            throw new KHypervisorInitializationException(e);
        }

    }

}
