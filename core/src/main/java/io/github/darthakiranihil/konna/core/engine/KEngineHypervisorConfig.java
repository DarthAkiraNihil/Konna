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

import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.engine.except.KHypervisorInitializationException;
import io.github.darthakiranihil.konna.core.log.KLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Record class of initial Konna hypervisor configuration.
 * @param componentLoader Class of engine component loader
 * @param serviceLoader Class of component services loader
 * @param components List of engine components classes to load
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public record KEngineHypervisorConfig(
    Class<? extends KComponentLoader> componentLoader,
    Class<? extends KServiceLoader> serviceLoader,
    List<Class<? extends KComponent>> components
) {

    private static final String COMPONENT_LOADER_KEY = "component_loader";
    private static final String SERVICE_LOADER_KEY = "service_loader";
    private static final String COMPONENTS_KEY = "components";

    /**
     * Constructs config from json.
     * @param json Json value containing configuration
     * @return Constructed hypervisor configuration
     */
    public static KEngineHypervisorConfig fromJson(final KJsonValue json) {

        try {
            return KEngineHypervisorConfig.getConfig(json);
        } catch (Throwable e) {
            throw new KHypervisorInitializationException(e);
        }

    }

    @SuppressWarnings("unchecked")
    private static KEngineHypervisorConfig getConfig(final KJsonValue json) throws Exception {

        String componentLoaderClassName = json.getProperty(COMPONENT_LOADER_KEY).getString();
        String serviceLoaderClassName = json.getProperty(SERVICE_LOADER_KEY).getString();

        Class<?> rawComponentLoaderClass = Class.forName(componentLoaderClassName);
        Class<? extends KComponentLoader>
            compoentLoaderClass = (Class<? extends KComponentLoader>) rawComponentLoaderClass;

        Class<?> rawServiceLoaderClass = Class.forName(serviceLoaderClassName);
        Class<? extends KServiceLoader>
            serviceLoaderClass = (Class<? extends KServiceLoader>) rawServiceLoaderClass;

        List<Class<? extends KComponent>> components = new ArrayList<>();
        json.getProperty(COMPONENTS_KEY).forEach((component) -> {
            try {
                components.add(
                    (Class<? extends KComponent>) Class.forName(component.getString())
                );
            } catch (ClassNotFoundException e) {
                KLogger.error(e);
                throw new KHypervisorInitializationException(e);
            }

        });

        return new KEngineHypervisorConfig(
            compoentLoaderClass,
            serviceLoaderClass,
            components
        );

    }


}
