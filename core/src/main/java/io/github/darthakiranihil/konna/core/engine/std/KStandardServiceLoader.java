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

package io.github.darthakiranihil.konna.core.engine.std;

import io.github.darthakiranihil.konna.core.engine.KComponentService;
import io.github.darthakiranihil.konna.core.engine.KServiceEndpoint;
import io.github.darthakiranihil.konna.core.engine.KServiceEntry;
import io.github.darthakiranihil.konna.core.engine.KServiceLoader;
import io.github.darthakiranihil.konna.core.engine.except.KServiceLoadingException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class KStandardServiceLoader implements KServiceLoader {

    @Override
    public void load(
        final Class<?> service,
        final Map<String, KServiceEntry> loadedServicesMap
    ) throws KServiceLoadingException {

        String serviceName = service.getAnnotation(KComponentService.class).name();

        if (loadedServicesMap.containsKey(serviceName)) {
            throw new KServiceLoadingException(
                String.format(
                        "Cannot load service %s: there is a service with the same name"
                    +   "within the component: %s",
                    service,
                    serviceName
                )
            );
        }

        Method[] methods = service.getDeclaredMethods();
        Map<String, Method> endpoints = new HashMap<>();
        for (var method: methods) {
            if (!method.isAnnotationPresent(KServiceEndpoint.class)) {
                continue;
            }

            KServiceEndpoint endpointMeta = method.getAnnotation(KServiceEndpoint.class);
            endpoints.put(
                endpointMeta.route(),
                method
            );
        }

        Constructor<?> serviceConstructor;
        try {
             serviceConstructor = service.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new KServiceLoadingException(e);
        }

        try {
            Object instantiatedService = serviceConstructor.newInstance();
            loadedServicesMap.put(
                serviceName,
                new KServiceEntry(
                    instantiatedService,
                    endpoints
                )
            );
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new KServiceLoadingException(e);
        }
    }
}
