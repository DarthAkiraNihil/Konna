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
import io.github.darthakiranihil.konna.core.message.KEventRegisterer;
import io.github.darthakiranihil.konna.core.message.KMessageRoutesConfigurer;

import java.util.LinkedList;
import java.util.List;

/**
 * Record class of initial Konna hypervisor configuration.
 * @param contextLoader Class of engine context loader
 * @param componentLoader Class of engine component loader
 * @param serviceLoader Class of component services loader
 * @param messageRoutesConfigurers List of engine message routes configurers
 * @param eventRegisterers List of engine event registerers
 * @param components List of engine components classes to load
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public record KEngineHypervisorConfig(
    Class<? extends KEngineContextLoader> contextLoader,
    Class<? extends KComponentLoader> componentLoader,
    Class<? extends KServiceLoader> serviceLoader,
    List<Class<? extends KMessageRoutesConfigurer>> messageRoutesConfigurers,
    List<Class<? extends KEventRegisterer>> eventRegisterers,
    List<Class<? extends KComponent>> components
) {

    private static final String ENGINE_CONTEXT_LOADER_KEY = "context_loader";
    private static final String COMPONENT_LOADER_KEY = "component_loader";
    private static final String SERVICE_LOADER_KEY = "service_loader";
    private static final String COMPONENTS_KEY = "components";
    private static final String MESSAGE_ROUTE_CONFIGURERS_KEY = "route_configurers";
    private static final String EVENT_REGISTERERS_KEY = "event_registerers";

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

        String contextLoaderClassName = json.getProperty(ENGINE_CONTEXT_LOADER_KEY).getString();
        String componentLoaderClassName = json.getProperty(COMPONENT_LOADER_KEY).getString();
        String serviceLoaderClassName = json.getProperty(SERVICE_LOADER_KEY).getString();

        Class<?> rawContextLoaderClass = Class.forName(contextLoaderClassName);
        Class<? extends KEngineContextLoader>
            contextLoaderClass = (Class<? extends KEngineContextLoader>) rawContextLoaderClass;

        Class<?> rawComponentLoaderClass = Class.forName(componentLoaderClassName);
        Class<? extends KComponentLoader>
            compoentLoaderClass = (Class<? extends KComponentLoader>) rawComponentLoaderClass;

        Class<?> rawServiceLoaderClass = Class.forName(serviceLoaderClassName);
        Class<? extends KServiceLoader>
            serviceLoaderClass = (Class<? extends KServiceLoader>) rawServiceLoaderClass;

        List<Class<? extends KComponent>> components = new LinkedList<>();
        json.getProperty(COMPONENTS_KEY).forEach((component) -> {
            try {
                components.add(
                    (Class<? extends KComponent>) Class.forName(component.getString())
                );
            } catch (ClassNotFoundException e) {
                throw new KHypervisorInitializationException(e);
            }

        });

        List<Class<? extends KMessageRoutesConfigurer>>
            messageRoutesConfigurers = new LinkedList<>();
        json.getProperty(MESSAGE_ROUTE_CONFIGURERS_KEY).forEach((configurer) -> {
            try {
                messageRoutesConfigurers.add(
                    (Class<? extends KMessageRoutesConfigurer>) Class.forName(
                        configurer.getString()
                    )
                );
            } catch (ClassNotFoundException e) {
                throw new KHypervisorInitializationException(e);
            }
        });

        List<Class<? extends KEventRegisterer>>
            eventRegisterers = new LinkedList<>();
        json.getProperty(EVENT_REGISTERERS_KEY).forEach((registerer) -> {
            try {
                eventRegisterers.add(
                    (Class<? extends KEventRegisterer>) Class.forName(
                        registerer.getString()
                    )
                );
            } catch (ClassNotFoundException e) {
                throw new KHypervisorInitializationException(e);
            }
        });

        return new KEngineHypervisorConfig(
            contextLoaderClass,
            compoentLoaderClass,
            serviceLoaderClass,
            messageRoutesConfigurers,
            eventRegisterers,
            components
        );

    }


}
