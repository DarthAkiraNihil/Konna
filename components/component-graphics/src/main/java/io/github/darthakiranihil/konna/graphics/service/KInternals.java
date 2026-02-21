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

import io.github.darthakiranihil.konna.core.engine.KMessageToEndpointConverter;
import io.github.darthakiranihil.konna.core.message.KMessage;
import io.github.darthakiranihil.konna.core.message.except.KInvalidMessageException;
import io.github.darthakiranihil.konna.core.object.KUninstantiable;
import io.github.darthakiranihil.konna.core.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.graphics.render.KRenderable;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@KExcludeFromGeneratedCoverageReport
final class KInternals extends KUninstantiable {

    public static final class MessageToRenderableConverter implements KMessageToEndpointConverter {

        private static final String RENDERABLE_KEY = "object";

        @Override
        public Object[] convert(final KMessage message) {

            var body = message.body();

            if (!body.containsKey(RENDERABLE_KEY)) {
                throw new KInvalidMessageException(
                    "Could not get renderable object from the message"
                );
            }

            KRenderable object = body.get(RENDERABLE_KEY, KRenderable.class);
            return new Object[] {object};

        }
    }

    public static final class MessageToRenderableArrayConverter
        implements KMessageToEndpointConverter {

        private static final String RENDERABLE_ARRAY_KEY = "objects";

        @Override
        public Object[] convert(final KMessage message) {

            var body = message.body();

            if (!body.containsKey(RENDERABLE_ARRAY_KEY)) {
                throw new KInvalidMessageException(
                    "Could not get renderable object array from the message"
                );
            }

            KRenderable[] objects = body.get(RENDERABLE_ARRAY_KEY, KRenderable[].class);
            return new Object[] {objects};

        }

    }

}
