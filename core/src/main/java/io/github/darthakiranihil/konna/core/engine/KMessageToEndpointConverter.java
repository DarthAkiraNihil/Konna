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
     * Default implementation of {@link KMessageToEndpointConverter}, that
     * literally just returns array of the only message, that has been passed to the endpoint.
     *
     * @since 0.4.0
     * @author Darth Akira Nihil
     */
    final class DirectConverter implements KMessageToEndpointConverter {
        @Override
        public Object[] convert(final KMessage message) {
            return new Object[] {message};
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
