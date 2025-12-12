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

package io.github.darthakiranihil.konna.core.message;

import io.github.darthakiranihil.konna.core.engine.KComponent;

import java.util.List;

/**
 * Interface for the essential part of Konna message system. It provides
 * methods for delivering Konna messages with feature of its translation
 * using tunnels, so different components can understand each other.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public interface KMessageSystem {

    /**
     * Delivers given message to destinations specified by its message id. If no route is specified,
     * the message won't be sent. This operation is asynchronous
     * @param message Message to send.
     */
    void deliverMessage(KMessage message);

    /**
     * Delivers given message to destinations specified by its message id. If no route is specified,
     * the message won't be sent. This operation is synchronous.
     * @param message Message to send.
     */
    void deliverMessageSync(KMessage message);

    /**
     * Adds a new route for given message id to specified destination endpoint.
     * If route with specified message id and destination endpoint already
     * exists in the system, it will be overridden.
     * @param messageId Full message id (component name + internal message id)
     * @param destinationEndpoint Full path to destination endpoint
     *                            (component name + internal endpoint name)
     * @param tunnels List of tunnels that should be used when delivering a message through
     *                created route.
     * @return This message system (for method chaining)
     */
    KMessageSystem addMessageRoute(
        String messageId,
        String destinationEndpoint,
        List<Class<? extends KTunnel>> tunnels
    );

    /**
     * Adds a new route for given message id to specified destination endpoint.
     * If route with specified message id and destination endpoint already
     * exists in the system, it will be overridden.
     * @param messageId Full message id (component name + internal message id)
     * @param destinationEndpoint Full path to destination endpoint
     *                            (component name + internal endpoint name)
     * @return This message system (for method chaining)
     */
    KMessageSystem addMessageRoute(
        String messageId,
        String destinationEndpoint
    );

    /**
     * Registers component in the system, so it is now allowed to
     * be a message destination.
     * @param component Component to register
     */
    void registerComponent(KComponent component);

    /**
     * Starts polling process for this message system.
     * This is required for the system to deliver sent messages.
     */
    void startPollingMessages();
    /**
     * Stops polling process for this message system.
     * After this call you still can deliver messages with it,
     * but they won't be handled until {@link KMessageSystem#startPollingMessages()}
     * is called.
     */
    void stopPollingMessages();

}
