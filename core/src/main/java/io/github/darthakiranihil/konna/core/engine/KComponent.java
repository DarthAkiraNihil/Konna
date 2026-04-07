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

import io.github.darthakiranihil.konna.core.engine.except.KComponentLoadingException;
import io.github.darthakiranihil.konna.core.engine.except.KEndpointRoutingException;
import io.github.darthakiranihil.konna.core.except.KException;
import io.github.darthakiranihil.konna.core.io.KAssetTypedef;
import io.github.darthakiranihil.konna.core.log.system.KSystemLogger;
import io.github.darthakiranihil.konna.core.message.KMessage;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Base class for Konna engine component.
 *
 * @since 0.2.0
 * @author DarthAkiraNihil
 */
public abstract class KComponent extends KObject {

    private static Map<String, KServiceEntry> packServices(
        final KService[] services,
        final KActivator activator
    ) {
        Map<String, KServiceEntry> packed = new HashMap<>(services.length);
        for (KService service: services) {
            packed.put(
                service.name(),
                new KServiceEntry(service, activator)
            );
        }
        return packed;
    }

    protected final Map<String, KServiceEntry> services;

    /**
     * Engine context in which this component has been created.
     */
    protected final KEngineContext ctx;

    /**
     * Base constructor, that performs all essential tasks to create a component:
     * loading services, creating assigned messenger and so on.
     * @param name Name of the component
     * @param ctx Current engine context
     * @throws KComponentLoadingException If any exception is thrown on component instantiation
     */
    protected KComponent(
        final String name,
        final KEngineContext ctx,
        final KService[] services
    ) {
        super(name, KStructUtils.setOfTags(KTag.DefaultTags.SYSTEM));

        this.ctx = ctx;
        this.services = KComponent.packServices(services, ctx);
    }

    /**
     * Accepts given message, routes it to given endpoint of internal service, which will process it
     * in other way. It happens synchronously, that is useful for testing.
     * @param endpoint Endpoint where the message is sent to
     * @param message Message to accept.
     */
    public final void acceptMessage(final String endpoint, final KMessage message) {
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
                "Unexpected error occurred when accepting message: %s",
                e
            );
        } catch (KException e) {
            KSystemLogger.error(
                this.name,
                "Unexpected error occurred when accepting message: %s",
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
     * Performs graceful shutdown for this component.
     * It is highly recommended to override this method if there is no simple
     * logic for its disposing.
     */
    protected void shutdown() {
        KSystemLogger.info(this.name, "Component %s has been shut down", this.name);
    }
}
