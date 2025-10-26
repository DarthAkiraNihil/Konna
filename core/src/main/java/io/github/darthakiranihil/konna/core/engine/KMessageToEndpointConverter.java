package io.github.darthakiranihil.konna.core.engine;

import io.github.darthakiranihil.konna.core.message.KMessage;

/**
 * Interface for a special type of class, which purpose is to convert
 * {@link KMessage} body to an array of endpoint arguments.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public interface KMessageToEndpointConverter {

    /**
     * Default implementation of {@link KMessageToEndpointConverter}, that
     * literally does nothing: it just converts a message to an empty object array.
     * It is used by default for a service endpoint.
     */
    final class NoConverter implements KMessageToEndpointConverter {
        @Override
        public Object[] convert(final KMessage message) {
            return new Object[0];
        }
    }

    /**
     * Accepts a message and converts it to an array of objects, that
     * represent called service endpoint arguments. Conversion should be
     * performed to the message body, however, it is not required.
     * @param message Message to convert
     * @return Array representing args of called endpoint
     */
    Object[] convert(KMessage message);

}
