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
import io.github.darthakiranihil.konna.core.engine.except.KComponentLoadingException;
import io.github.darthakiranihil.konna.core.engine.except.KHypervisorInitializationException;
import io.github.darthakiranihil.konna.core.log.KLogger;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Konna Engine Hypervisor - the primal class for the engine that controls the whole system.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public class KEngineHypervisor extends KObject {

    private final KComponentLoader componentLoader;
    private final KServiceLoader serviceLoader;
    private final Map<String, KComponent> engineComponents;
    private final KJsonParser jsonParser;

    /**
     * Constructs hypervisor with provided config.
     * @param config Config of the hypervisor
     */
    public KEngineHypervisor(final KEngineHypervisorConfig config) {

        super(KEngineHypervisor.class.getSimpleName());

        KLogger.info("Initializing engine hypervisor [config = %s]", config);

        this.jsonParser = KActivator.create(KJsonParser.class);
        KContainer local = KActivator.newContainer();

        local
            .add(config.serviceLoader())
            .add(config.componentLoader());

        try {

            this.componentLoader = KActivator.create(config.componentLoader(), local);

            KLogger.info("Created component loader %s", config.componentLoader());

            this.serviceLoader = KActivator.create(config.serviceLoader(), local);

            KLogger.info("Created service loader %s", config.serviceLoader());

            this.engineComponents = new HashMap<>();

            for (var component: config.components()) {
                this.componentLoader.load(component, this.serviceLoader, this.engineComponents);
            }

            KLogger.info("Loaded %d components", this.engineComponents.size());

        } catch (
            KComponentLoadingException e
        ) {
            KLogger.error(e);
            throw new KHypervisorInitializationException(e);
        }

    }

}
