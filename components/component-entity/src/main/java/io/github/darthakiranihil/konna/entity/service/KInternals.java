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

package io.github.darthakiranihil.konna.entity.service;

import io.github.darthakiranihil.konna.core.engine.KMessageToEndpointConverter;
import io.github.darthakiranihil.konna.core.message.KMessage;
import io.github.darthakiranihil.konna.core.message.except.KInvalidMessageException;
import io.github.darthakiranihil.konna.core.object.KUninstantiable;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
final class KInternals extends KUninstantiable {

    public static final class MessageToEntityCreationDataConverter
        implements KMessageToEndpointConverter {

        private static final String NAME_KEY = "name";
        private static final String TYPE_KEY = "type";

        @Override
        public Object[] convert(final KMessage message) {

            var body = message.body();

            if (!body.containsKey(NAME_KEY)) {
                throw new KInvalidMessageException(
                    "Could not get entity name from the message"
                );
            }

            if (!body.containsKey(TYPE_KEY)) {
                throw new KInvalidMessageException(
                    "Could not get entity type from the message"
                );
            }

            return new Object[] {
                body.get(NAME_KEY),
                body.get(TYPE_KEY),
            };

        }
    }

}
