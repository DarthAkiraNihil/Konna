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
import io.github.darthakiranihil.konna.core.engine.except.KComponentLoadingException;
import io.github.darthakiranihil.konna.core.engine.except.KHypervisorInitializationException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Konna Engine Hypervisor - the primal class for the engine that controls the whole system.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public class KEngineHypervisor {

    private final KComponentLoader componentLoader;
    private final List<KComponent> engineComponents;
    private final KJsonParser jsonParser;

    /**
     * Constructs hypervisor with provided config.
     * @param config Config of the hypervisor.
     */
    public KEngineHypervisor(final KJsonParser jsonParser, final KEngineHypervisorConfig config) {

        this.jsonParser = jsonParser;

        try {

            var componentLoaderConstructor = config.componentLoader().getConstructor(KJsonParser.class);
            this.componentLoader = componentLoaderConstructor.newInstance(this.jsonParser);
            this.engineComponents = new ArrayList<>();

            for (var component: config.components()) {
                this.engineComponents.add(
                    this.componentLoader.load(component)
                );
            }

        } catch (
                NoSuchMethodException
            |   InvocationTargetException
            |   InstantiationException
            |   IllegalAccessException
            |   KComponentLoadingException e
        ) {
            throw new KHypervisorInitializationException(e);
        }

    }

}
