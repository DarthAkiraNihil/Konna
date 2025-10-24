package io.github.darthakiranihil.konna.core.engine;

import io.github.darthakiranihil.konna.core.message.KMessage;

public interface KMessageToEndpointConverter {

    final class NoConverter implements KMessageToEndpointConverter {
        @Override
        public Object[] convert(final KMessage message) {
            return new Object[0];
        }
    }

    Object[] convert(KMessage message);

}
