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

import io.github.darthakiranihil.konna.core.di.KContainer;
import io.github.darthakiranihil.konna.core.di.KContainerModifier;
import io.github.darthakiranihil.konna.core.engine.except.KComponentLoadingException;
import io.github.darthakiranihil.konna.core.engine.except.KServiceLoadingException;
import io.github.darthakiranihil.konna.core.log.system.KSystemLogger;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KPair;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.core.util.KClassUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Standard implementation of {@link KServiceLoader}.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@KContainerModifier
public class KStandardServiceLoader extends KObject implements KServiceLoader {

    public KStandardServiceLoader() {
        super(
            "std_service_loader",
            KStructUtils.setOfTags(
                KTag.DefaultTags.SYSTEM,
                KTag.DefaultTags.STD
            )
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public void load(
        final KEngineContext ctx,
        final Class<?> service,
        final Map<String, KServiceEntry> loadedServicesMap
    ) {

        String serviceName = service.getAnnotation(KComponentServiceMetaInfo.class).name();
        KSystemLogger.info(this.name, "Loading service %s [%s]", serviceName, service);

        if (loadedServicesMap.containsKey(serviceName)) {
            KSystemLogger.fatal(
                this.name,
            "Cannot load service %s: there is a service with the same name"
                +   "within the component: %s",
                service,
                serviceName
            );
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
        Map<String, KPair<KMessageToEndpointConverter, Method>> endpoints = new HashMap<>();
        for (var method: methods) {
            if (!method.isAnnotationPresent(KServiceEndpoint.class)) {
                continue;
            }

            method.setAccessible(true);
            KServiceEndpoint endpointMeta = method.getAnnotation(KServiceEndpoint.class);
            var converterClass = KClassUtils
                .getForName(
                    String.format(
                        "%s.generated.%s$$EndpointConverter_%s",
                        service.getPackageName(),
                        serviceName,
                        endpointMeta.route()
                    )
                );

            if (!KMessageToEndpointConverter.class.isAssignableFrom(converterClass)) {
                throw new KServiceLoadingException(
                    "Found converter class is not a valid converter."
                );
            }

            KMessageToEndpointConverter converter =
                ctx.createObject((Class<? extends KMessageToEndpointConverter>) converterClass);

            endpoints.put(
                endpointMeta.route(),
                new KPair<>(converter, method)
            );
        }
        KSystemLogger.info(
            this.name,
            "Found %d service endpoints in %s [%s]",
            endpoints.size(),
            serviceName,
            service
        );

        KContainer master = ctx.getContainer();
        master.add(service);

        Object instantiatedService = ctx.createObject(service);
        loadedServicesMap.put(
            serviceName,
            new KServiceEntry(
                instantiatedService,
                endpoints,
                ctx
            )
        );
        KSystemLogger.info(this.name, "Loaded service %s [%s]", serviceName, service);
    }
}
