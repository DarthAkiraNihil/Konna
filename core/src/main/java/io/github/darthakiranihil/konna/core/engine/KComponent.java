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
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.engine.except.KComponentLoadingException;
import io.github.darthakiranihil.konna.core.engine.except.KServiceLoadingException;
import io.github.darthakiranihil.konna.core.log.KLogger;
import io.github.darthakiranihil.konna.core.message.KMessage;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.util.KAnnotationUtils;
import io.github.darthakiranihil.konna.core.util.KStructUtils;

import java.util.*;

/**
 * Base class for Konna engine component.
 *
 * @since 0.2.0
 * @author DarthAkiraNihil
 */
public abstract class KComponent extends KObject {

    /**
     * List of component services.
     */
    protected final Map<String, KServiceEntry> services;
    /**
     * Logger of component's engine context.
     */
    protected final KLogger logger;

    public KComponent(
        @KInject final KServiceLoader serviceLoader,
        final String name,
        final KEngineContext ctx,
        final String servicesPackage,
        final KJsonValue config
    ) throws KComponentLoadingException {
        super(name, KStructUtils.setOfTags(KTag.DefaultTags.SYSTEM));
        this.logger = ctx.logger();

        String componentClass = this.getClass().toString();
        this.logger.info("Creating component %s", componentClass);

        List<Class<?>> serviceClasses = KAnnotationUtils.findAnnotatedClasses(
            ctx.index(),
            servicesPackage,
            KComponentServiceMetaInfo.class
        );
        this.logger.info(
            "Found %d services of component %s",
            serviceClasses.size(),
            componentClass
        );

        Map<String, KServiceEntry> instantiatedServices = new HashMap<>();
        try {
            for (var serviceClass: serviceClasses) {
                serviceLoader.load(
                    ctx,
                    serviceClass,
                    instantiatedServices
                );
            }
        } catch (
            KServiceLoadingException e
        ) {
            this.logger.fatal(e);
            throw new KComponentLoadingException(e);
        }
        this.logger.info(
            "Loaded %d services of component %s",
            instantiatedServices.size(),
            componentClass
        );

        this.services = instantiatedServices;
        this.logger.info("Applying config for %s", componentClass);
        this.applyConfig(config);
        this.logger.info("Created component %s", componentClass);
    }

    /**
     * Accepts given message, routes it to given endpoint of internal service, which will process it
     * in other way. It happens asynchronously.
     * @param endpoint Destination endpoint of the message.
     * @param message Message to accept.
     */
    public abstract void acceptMessage(String endpoint, KMessage message);
    /**
     * Accepts given message, routes it to given endpoint of internal service, which will process it
     * in other way. It happens synchronously, that is useful for testing.
     * @param endpoint Destination endpoint of the message.
     * @param message Message to accept.
     */
    public abstract void acceptMessageSync(String endpoint, KMessage message);

    protected abstract void applyConfig(KJsonValue config);
}
