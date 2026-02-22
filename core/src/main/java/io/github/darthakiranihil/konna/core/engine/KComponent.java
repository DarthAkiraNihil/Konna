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
import io.github.darthakiranihil.konna.core.engine.except.KEndpointRoutingException;
import io.github.darthakiranihil.konna.core.engine.except.KServiceLoadingException;
import io.github.darthakiranihil.konna.core.io.KAssetTypedef;
import io.github.darthakiranihil.konna.core.log.system.KSystemLogger;
import io.github.darthakiranihil.konna.core.message.KMessage;
import io.github.darthakiranihil.konna.core.message.KMessenger;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.core.util.KAnnotationUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * Messenger, assigned to current component.
     */
    protected final KMessenger messenger;
    /**
     * Engine context in which this component has been created.
     */
    protected final KEngineContext ctx;

    /**
     * Base constructor, that performs all essential tasks to create a component:
     * loading services, creating assigned messenger and so on.
     * @param serviceLoader Engine service loader. It is injected
     * @param name Name of the component
     * @param ctx Current engine context
     * @param servicesPackage Name of component's services package
     * @param config Component configuration
     * @throws KComponentLoadingException If any exception is thrown on component instantiation
     */
    public KComponent(
        @KInject final KServiceLoader serviceLoader,
        final String name,
        final KEngineContext ctx,
        final String servicesPackage,
        final KJsonValue config
    ) {

        super(name, KStructUtils.setOfTags(KTag.DefaultTags.SYSTEM));

        String componentClass = this.getClass().toString();
        KSystemLogger.info(name, "Creating component %s", componentClass);
        this.ctx = ctx;
        KSystemLogger.info(name, "Applying config for %s", componentClass);
        this.applyConfig(config);

        this.services = this.loadServices(ctx, servicesPackage, serviceLoader);
        KSystemLogger.info(
            name,
            "Loaded %d services of component %s",
            this.services.size(),
            componentClass
        );

        this.messenger = this.loadMessenger(ctx, name);
        KSystemLogger.info(
            name,
            "Created messenger %s for the component %s",
            this.messenger,
            componentClass
        );

        KSystemLogger.info(name, "Created component %s", componentClass);
    }

    /**
     * Accepts given message, routes it to given endpoint of internal service, which will process it
     * in other way. It happens synchronously, that is useful for testing.
     * @param endpoint Endpoint where the message is sent to
     * @param message Message to accept.
     */
    public void acceptMessage(final String endpoint, final KMessage message) {
        var splitId = endpoint.split("\\.");
        String service = splitId[0];
        String route = splitId[1];

        if (!this.services.containsKey(service)) {
            return;
        }

        var serviceEntry = this.services.get(service);
        if (!serviceEntry.hasEndpoint(route)) {
            return;
        }

        try {
            serviceEntry.callEndpoint(route, message);
            KSystemLogger.debug(
                this.name,
                "Successfully accepted message %s. Destination: %s.%s",
                message,
                this.name,
                route
            );
        } catch (KEndpointRoutingException e) {
            KSystemLogger.warning(
                this.name,
                "%s: Unexpected error occurred when accepting message: %s",
                this.getClass().getCanonicalName(),
                e
            );
        }
    }

    /**
     * Returns asset type definitions of that types that are internal for its component.
     * @return Array of asset type definitions, used inside this component
     */
    public abstract KAssetTypedef[] getAssetTypedefs();

    /**
     * Runs post-init operations for this component. By default, the method does nothing
     * so it should be overridden.
     */
    public void postInit() {

    }

    /**
     * Loads services for the engine component. By default, it instantiates all
     * classes that are defined in the servicesPackage, provided by {@link KComponentMetaInfo}.
     * @param ctx Current engine context
     * @param servicesPackage Name of component's services package
     * @param serviceLoader Engine service loader
     * @return Mapping "Service name -> Service entry" that contain information
     *         about service instance and its endpoints.
     * @throws KComponentLoadingException If a service failed to load
 *                                        (so a component cannot be loaded either)
     */
    protected Map<String, KServiceEntry> loadServices(
        final KEngineContext ctx,
        final String servicesPackage,
        final KServiceLoader serviceLoader
    ) {

        List<Class<?>> serviceClasses = KAnnotationUtils.findAnnotatedClasses(
            ctx,
            servicesPackage,
            KComponentServiceMetaInfo.class
        );
        KSystemLogger.info(
            this.name,
            "Found %d services of component %s",
            serviceClasses.size(),
            this.getClass().getCanonicalName()
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
            KSystemLogger.fatal(this.name, e);
            throw new KComponentLoadingException(e);
        }
        return instantiatedServices;

    }

    /**
     * Loads messenger for current component.
     * @param ctx Current engine context
     * @param name Name of the component
     * @return Loaded messenger that is assigned to the current component
     */
    protected KMessenger loadMessenger(
        final KEngineContext ctx,
        final String name
    ) {
        return ctx
            .createObject(KMessenger.class, name);
    }

    /**
     * Applies custom config to this component.
     * @param config Custom component config.
     */
    protected abstract void applyConfig(KJsonValue config);

    /**
     * Performs graceful shutdown for this component.
     * It is highly recommended to override this method if there is no simple
     * logic for its disposing.
     */
    protected void shutdown() {
        KSystemLogger.info(this.name, "Component %s has been shut down", this.name);
    }
}
