package io.github.darthakiranihil.konna.core.message;

import io.github.darthakiranihil.konna.core.engine.KComponent;

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
     * the message won't be sent. This operation is synchronous, which is useful for testing.
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
        Class<? extends KTunnel>[] tunnels
    );

    void registerComponent(KComponent component);

}
