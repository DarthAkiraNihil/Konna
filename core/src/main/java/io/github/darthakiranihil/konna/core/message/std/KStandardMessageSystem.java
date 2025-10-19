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

import io.github.darthakiranihil.konna.core.engine.KComponent;
import io.github.darthakiranihil.konna.core.message.KMessage;
import io.github.darthakiranihil.konna.core.message.KMessageSystem;
import io.github.darthakiranihil.konna.core.message.KTunnel;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.util.KPair;

import java.util.HashMap;
import java.util.Map;

public class KStandardMessageSystem extends KObject implements KMessageSystem {

    private static final class RouteData {
        private final Map<String, KPair<KComponent, KTunnel[]>> connections;

        public RouteData() {
            this.connections = new HashMap<>();
        }
    }

    private final Map<String, KComponent> components;
    private final Map<String, RouteData> routes;

    public KStandardMessageSystem(final KComponent[] components) {
        this.components = new HashMap<>();
        for (KComponent component: components) {
            this.components.put(component.name(), component);
        }

        this.routes = new HashMap<>();
    }

    @Override
    public void deliverMessage(final KMessage message) {

    }

    @Override
    public void deliverMessageSync(final KMessage message) {

        if (!this.routes.containsKey(message.messageId())) {
            return;
        }



    }

    @Override
    public KMessageSystem addMessageRoute(
        final String messageId,
        final String destinationEndpoint,
        final KTunnel... tunnels
    ) {

        KComponent destinationComponent = this.resolveComponent(destinationEndpoint);

        if (this.routes.containsKey(messageId)) {
            var route = this.routes.get(messageId);
            route.connections.put(destinationEndpoint, new KPair<>(destinationComponent, tunnels));
            return this;
        }

        var route = new RouteData();
        route.connections.put(destinationEndpoint, new KPair<>(destinationComponent, tunnels));
        this.routes.put(messageId, route);
        return this;
    }

    private String extractComponentCoordinate(final String messageId) {
        return messageId.split("\\.")[0];
    }

    private KComponent resolveComponent(final String destinationEndpoint) {
        var component = destinationEndpoint.split("\\.")[0];
        if (!this.components.containsKey(component)) {
            return null;
        }

        return this.components.get(component);
    }

}
