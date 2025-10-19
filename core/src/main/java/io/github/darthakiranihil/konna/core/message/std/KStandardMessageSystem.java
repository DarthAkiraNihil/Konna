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

package io.github.darthakiranihil.konna.core.message.std;

import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.engine.KComponent;
import io.github.darthakiranihil.konna.core.message.KMessage;
import io.github.darthakiranihil.konna.core.message.KMessageSystem;
import io.github.darthakiranihil.konna.core.message.KTunnel;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.util.KThreadUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Standard implementation of {@link KMessageSystem}.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public class KStandardMessageSystem extends KObject implements KMessageSystem {

    private record RouteConnectionData(
        KComponent component,
        KTunnel[] tunnels,
        String shortEndpointName
    ) {
    }

    private static final class RouteData {
        private final Map<String, RouteConnectionData> connections;

        RouteData() {
            this.connections = new HashMap<>();
        }
    }

    private final KActivator activator;
    private final Map<String, KComponent> components;
    private final Map<String, RouteData> routes;

    /**
     * Standard constructor.
     * @param activator Activator that will be used to create tunnel instances.
     * @param components Array of engine components that are able to communicate with messages
     */
    public KStandardMessageSystem(
        @KInject final KActivator activator,
        final KComponent[] components
    ) {
        this.activator = activator;

        this.components = new HashMap<>();
        for (KComponent component: components) {
            this.components.put(component.name(), component);
        }

        this.routes = new HashMap<>();
    }

    @Override
    public void deliverMessage(final KMessage message) {

        if (!this.routes.containsKey(message.messageId())) {
            return;
        }

        var route = this.routes.get(message.messageId());
        for (var v: route.connections.values()) {
            KThreadUtils.runAsync(() -> {
                KMessage finalMessage = message;
                for (KTunnel tunnel: v.tunnels) {
                    finalMessage = tunnel.processMessage(finalMessage);
                }

                v.component.acceptMessage(v.shortEndpointName, finalMessage);
            });
        }

    }

    @Override
    public void deliverMessageSync(final KMessage message) {

        if (!this.routes.containsKey(message.messageId())) {
            return;
        }

        var route = this.routes.get(message.messageId());
        route
            .connections
            .values()
            .forEach((v) -> {
                KMessage finalMessage = message;
                for (KTunnel tunnel: v.tunnels) {
                    finalMessage = tunnel.processMessage(finalMessage);
                }

                v.component.acceptMessageSync(v.shortEndpointName, message);
            });

    }

    @SafeVarargs
    @Override
    public final KMessageSystem addMessageRoute(
        final String messageId,
        final String destinationEndpoint,
        final Class<? extends KTunnel>... tunnels
    ) {

        KComponent destinationComponent = this.resolveComponent(destinationEndpoint);
        String shortEndpointName = destinationEndpoint.split("\\.")[1];

        if (this.routes.containsKey(messageId)) {
            var route = this.routes.get(messageId);
            route.connections.put(
                destinationEndpoint,
                new RouteConnectionData(
                    destinationComponent,
                    this.makeTunnels(tunnels),
                    shortEndpointName
                )
            );
            return this;
        }

        var route = new RouteData();
        route.connections.put(
            destinationEndpoint,
            new RouteConnectionData(
                destinationComponent,
                this.makeTunnels(tunnels),
                shortEndpointName
            )
        );
        this.routes.put(messageId, route);
        return this;
    }

    private KComponent resolveComponent(final String destinationEndpoint) {
        var component = destinationEndpoint.split("\\.")[0];
        if (!this.components.containsKey(component)) {
            return null;
        }

        return this.components.get(component);
    }

    @SafeVarargs
    private KTunnel[] makeTunnels(final Class<? extends KTunnel>... tunnels) {
        KTunnel[] instantiatedTunnels =  new KTunnel[tunnels.length];
        for (int i = 0; i < tunnels.length; i++) {
            instantiatedTunnels[i] = this.activator.create(tunnels[i]);
        }
        return instantiatedTunnels;
    }

}
