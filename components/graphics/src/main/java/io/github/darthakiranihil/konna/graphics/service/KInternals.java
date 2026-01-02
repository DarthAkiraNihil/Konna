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

package io.github.darthakiranihil.konna.graphics.service;

import io.github.darthakiranihil.konna.core.data.json.KJsonDeserializer;
import io.github.darthakiranihil.konna.core.data.json.KJsonValue;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.engine.KMessageToEndpointConverter;
import io.github.darthakiranihil.konna.core.log.KSystemLogger;
import io.github.darthakiranihil.konna.core.message.KMessage;
import io.github.darthakiranihil.konna.core.message.except.KInvalidMessageException;
import io.github.darthakiranihil.konna.core.object.KUninstantiable;
import io.github.darthakiranihil.konna.graphics.except.KInvalidRenderableClassException;
import io.github.darthakiranihil.konna.graphics.internal.KRenderableObjectTypeMapping;
import io.github.darthakiranihil.konna.graphics.render.KRenderable;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
final class KInternals extends KUninstantiable {

    public static final class MessageToRenderableConverter implements KMessageToEndpointConverter {

        private final KRenderableObjectTypeMapping mapping;
        private final KJsonDeserializer deserializer;

        public MessageToRenderableConverter(
            @KInject final KRenderableObjectTypeMapping mapping,
            @KInject final KJsonDeserializer deserializer
        ) {
            this.mapping = mapping;
            this.deserializer = deserializer;
        }

        @Override
        public Object[] convert(final KMessage message) {

            var body = message.body();

            if (!body.containsKey("type")) {
                throw new KInvalidMessageException("Could not get renderable object type in the message");
            }

            String type = body.get("type", String.class);
            Class<? extends KRenderable> renderableClass = this.mapping.getMapping(type);
            if (renderableClass == null) {
                throw new KInvalidRenderableClassException(
                    String.format(
                        "Unknown renderable type: %s",
                        type
                    )
                );
            }

            if (!body.containsKey("data")) {
                throw new KInvalidMessageException("Could not get renderable object data in the message");
            }
            KJsonValue data = body.get("data", KJsonValue.class);

            // todo: validation
            KRenderable renderable = this.deserializer.deserialize(data, renderableClass);
            if (renderable == null) {
                KSystemLogger.warning(
                    "Graphics",
                    "Could not create renderable object from the message: %s",
                    type
                );
                return new Object[] { new KRenderable.EMPTY() };
            }

            return new Object[] { renderable };
        }
    }

}
