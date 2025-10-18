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

    /**
     * Constructs hypervisor with provided config.
     * @param initializationConfig Initialization config of the hypervisor
     * @param ctx Engine execution context
     */
    public KEngineHypervisor(
        final KEngineHypervisorConfig initializationConfig,
        final KEngineContext ctx
    ) {

        super(
            KEngineHypervisor.class.getSimpleName(),
            new HashSet<>(List.of(KTag.DefaultTags.SYSTEM))
        );

        this.activator = ctx.activator();
        this.containerResolver = ctx.containerResolver();
        this.logger = ctx.logger();

        this.logger.info("Initializing engine hypervisor [config = %s]", initializationConfig);

        this.jsonParser = this.activator.create(KJsonParser.class);
        KContainer master = this.containerResolver.resolve();

        master
            .add(initializationConfig.serviceLoader())
            .add(initializationConfig.componentLoader());

        try {

            this.componentLoader = this.activator.create(initializationConfig.componentLoader());

            this.logger.info("Created component loader %s", initializationConfig.componentLoader());

            this.serviceLoader = this.activator.create(initializationConfig.serviceLoader());

            this.logger.info("Created service loader %s", initializationConfig.serviceLoader());

            this.engineComponents = new HashMap<>();

            for (var component: initializationConfig.components()) {
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
