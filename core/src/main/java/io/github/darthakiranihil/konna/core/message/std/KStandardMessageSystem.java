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
import io.github.darthakiranihil.konna.core.log.KSystemLogger;
import io.github.darthakiranihil.konna.core.message.KMessage;
import io.github.darthakiranihil.konna.core.message.KMessageSystem;
import io.github.darthakiranihil.konna.core.message.KTunnel;
import io.github.darthakiranihil.konna.core.object.KActivator;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Standard implementation of {@link KMessageSystem} that uses a watcher thread
 * to poll and deliver sent messages.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@KSingleton(immortal = true)
public class KStandardMessageSystem extends KObject implements KMessageSystem {

    private static final String WATCHER_THREAD_NAME = "message_watcher";

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

    private final Queue<KMessage> messageQueue;
    private volatile boolean polling;
    private @Nullable Thread watcher;

    /**
     * Standard constructor.
     * @param activator Activator that will be used to create tunnel instances.
     */
    public KStandardMessageSystem(
        @KInject final KActivator activator
    ) {
        this.activator = activator;

        this.components = new HashMap<>();
        this.routes = new HashMap<>();
        this.messageQueue = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void registerComponent(final KComponent component) {
        this.components.put(component.name(), component);
        KSystemLogger.debug(
            "Component %s has been registered in message system",
            component.name()
        );
    }

    @Override
    public void deliverMessage(final KMessage message) {

        this.messageQueue.add(message);

    }

    @Override
    public void deliverMessageSync(final KMessage message) {

        KSystemLogger.info(
            "Delivering message %s",
            message
        );

        if (!this.routes.containsKey(message.messageId())) {
            KSystemLogger.warning(
                "Dropped message %s, as no route configured for it",
                message
            );
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

                v.component.acceptMessage(v.shortEndpointName, message);
            });

    }

    // @SafeVarargs
    @Override
    public KMessageSystem addMessageRoute(
        final String messageId,
        final String destinationEndpoint,
        final List<Class<? extends KTunnel>> tunnels
    ) {
        this.internalAddMessageRoute(messageId, destinationEndpoint, tunnels);
        KSystemLogger.debug(
            "Added message route for messageId=%s to destinationEndpoint=%s with %d tunnels",
            messageId,
            destinationEndpoint,
            tunnels.size()
        );
        return this;
    }

    @Override
    public KMessageSystem addMessageRoute(
        final String messageId,
        final String destinationEndpoint
    ) {
        this.internalAddMessageRoute(messageId, destinationEndpoint, List.of());
        KSystemLogger.debug(
            "Added message route for messageId=%s to destinationEndpoint=%s",
            messageId,
            destinationEndpoint
        );
        return this;
    }

    @Override
    public void startPollingMessages() {
        this.polling = true;
        this.watcher = new Thread(this::watch);
        this.watcher.setName(WATCHER_THREAD_NAME);
        this.watcher.start();
    }

    @Override
    public void stopPollingMessages() {
        this.polling = false;
        this.watcher = null;
    }

    private void internalAddMessageRoute(
        final String messageId,
        final String destinationEndpoint,
        final List<Class<? extends KTunnel>> tunnels
    ) {
        KComponent destinationComponent = this.resolveComponent(destinationEndpoint);
        if (destinationComponent == null) {
            return;
        }
        String shortEndpointName =
            destinationEndpoint
                .subSequence(destinationEndpoint.indexOf('.') + 1, destinationEndpoint.length())
                .toString();

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
            return;
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
    }

    private @Nullable KComponent resolveComponent(final String destinationEndpoint) {
        var component = destinationEndpoint.split("\\.")[0];
        if (!this.components.containsKey(component)) {
            return null;
        }

        return this.components.get(component);
    }

    private KTunnel[] makeTunnels(final List<Class<? extends KTunnel>>  tunnels) {
        KTunnel[] instantiatedTunnels =  new KTunnel[tunnels.size()];
        for (int i = 0; i < tunnels.size(); i++) {
            instantiatedTunnels[i] = this.activator.createObject(tunnels.get(i));
        }
        return instantiatedTunnels;
    }

    private void watch() {

        KSystemLogger.info(
            "Message watcher thread has been started. Now polling messages [host = %s]",
            this
        );

        while (this.polling || !this.messageQueue.isEmpty()) {

            KMessage message = this.messageQueue.poll();
            if (message == null) {
                continue;
            }

            this.deliverMessageSync(message);
        }

        KSystemLogger.info(
            "Message watcher thread has been stopped [host = %s]",
            this
        );

    }

}
