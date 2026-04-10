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

import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.engine.except.KEndpointRoutingException;
import io.github.darthakiranihil.konna.core.engine.except.KServiceLoadingException;
import io.github.darthakiranihil.konna.core.log.system.KSystemLogger;
import io.github.darthakiranihil.konna.core.message.KMessage;
import io.github.darthakiranihil.konna.core.object.KActivator2;
import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.core.util.KClassUtils;
import io.github.darthakiranihil.konna.core.util.KReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Convenience wrapper for a component service.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public final class KServiceEntry {

    @SuppressWarnings("unchecked")
    private static Map<String, KPair<KMessageToEndpointConverter, Method>> getServiceEndpoints(
        final KService service
    ) {
        Class<? extends KService> serviceClass = service.getClass();

        Method[] methods = serviceClass.getDeclaredMethods();
        Map<String, KPair<KMessageToEndpointConverter, Method>> endpoints = new HashMap<>();

        for (var method: methods) {
            if (!method.isAnnotationPresent(KServiceEndpoint.class)) {
                continue;
            }

            method.setAccessible(true);
            KServiceEndpoint endpointMeta = method.getAnnotation(KServiceEndpoint.class);
            var rawConverterClass = KClassUtils
                .getForName(
                    String.format(
                        "%s.generated.%s$$EndpointConverter_%s",
                        serviceClass.getPackageName(),
                        serviceClass.getSimpleName(),
                        endpointMeta.route()
                    )
                );

            if (!KMessageToEndpointConverter.class.isAssignableFrom(rawConverterClass)) {
                throw new KServiceLoadingException(
                    "Found converter class is not a valid converter."
                );
            }

            Class<? extends KMessageToEndpointConverter>
                converterClass = (Class<? extends KMessageToEndpointConverter>) rawConverterClass;

            KMessageToEndpointConverter converter = KReflectionUtils.newInstance(
                Objects.requireNonNull(KReflectionUtils.getConstructor(converterClass))
            );

            endpoints.put(
                endpointMeta.route(),
                new KPair<>(converter, method)
            );
        }

        return endpoints;
    }

    private final KService service;
    private final Map<String, KPair<KMessageToEndpointConverter, Method>> endpoints;
    private final KActivator2 activator;

    public KServiceEntry(
        final KService service,
        final KActivator2 activator
    ) {
        this.service = service;
        this.activator = activator;

        this.endpoints = KServiceEntry.getServiceEndpoints(service);
    }

    /**
     * Calls a service endpoint.
     * @param route Route of called endpoint
     * @param message Message to accept
     * @throws KEndpointRoutingException If component with given route does not exist
     *                                   in the service
     */
    public void callEndpoint(
        final String route,
        final KMessage message
    ) {
        if (!this.endpoints.containsKey(route)) {
            throw new KEndpointRoutingException(
                String.format(
                    "Cannot call non-existent endpoint: %s",
                    route
                )
            );
        }

        var endpointEntry = this.endpoints.get(route);
        var converter = endpointEntry.first();
        var endpoint = endpointEntry.second();

        var providedArgs = converter.convert(message);

        var parameterTypes = endpoint.getParameterTypes();
        var parameterAnnotations = endpoint.getParameterAnnotations();

        Object[] parameters = new Object[parameterTypes.length];
        int nonInjectedArgsProcessed = 0;

        for (int i = 0; i < parameterTypes.length; i++) {
            boolean isNonInjected = true;
            for (int j = 0; j < parameterAnnotations[i].length; j++) {
                if (parameterAnnotations[i][j] instanceof KInject) {
                    isNonInjected = false;
                    break;
                }
            }

            if (isNonInjected) {
                parameters[i] = providedArgs[nonInjectedArgsProcessed];
                nonInjectedArgsProcessed++;
            } else {
                parameters[i] = this.activator.createObject(parameterTypes[i]);
            }
        }

        try {
            endpoint.invoke(this.service, parameters);
            KSystemLogger.debug(
                "ServiceEntry",
                "Successfully called endpoint %s of service %s",
                route,
                this.service
            );
        } catch (InvocationTargetException e) {
            throw new KEndpointRoutingException(e.getTargetException());
        } catch (IllegalAccessException e) {
            throw new KEndpointRoutingException(e);
        }
    }

    /**
     * Used to get information about endpoint existence in current service.
     * Literally an additional guard method before real endpoint calling.
     * @param route Route of the endpoint
     * @return {@code true} if specified endpoint is presented, else {@code false}
     */
    public boolean hasEndpoint(
        final String route
    ) {
        return this.endpoints.containsKey(route);
    }


}
