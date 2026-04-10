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

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.object.KTag;

/**
 * Standard implementation of {@link KMessenger}.
 *
 * @since 0.2.0
 * @author Darth Akria Nihil
 */
public class KStandardMessenger extends KMessenger {

    /**
     * Standard constructor.
     * @param messageSystem Parent message system that messenger sends messages to
     */
    @KInject
    public KStandardMessenger(
        final KMessageSystem messageSystem
    ) {
        super(messageSystem);
        this.addTag(KTag.DefaultTags.STD);
    }

    @Override
    public void sendRegular(final String messageId, final KUniversalMap body) {
        this
            .messageSystem
            .deliverMessage(
                KMessage.regular(
                    messageId,
                    body
                )
            );
    }

    @Override
    public void sendSystem(final String messageId, final KUniversalMap body) {
        this
            .messageSystem
            .deliverMessage(
                KMessage.system(
                    messageId,
                    body
                )
            );
    }

    @Override
    public void sendDebug(final String messageId, final KUniversalMap body) {
        this
            .messageSystem
            .deliverMessage(
                KMessage.debug(
                    messageId,
                    body
                )
            );
    }

    @Override
    public void sendMetrics(final String messageId, final KUniversalMap body) {
        this
            .messageSystem
            .deliverMessage(
                KMessage.metrics(
                    messageId,
                    body
                )
            );
    }

    @Override
    public void sendRegularSync(final String messageId, final KUniversalMap body) {
        this
            .messageSystem
            .deliverMessageSync(
                KMessage.regular(
                    messageId,
                    body
                )
            );
    }

    @Override
    public void sendSystemSync(final String messageId, final KUniversalMap body) {
        this
            .messageSystem
            .deliverMessageSync(
                KMessage.system(
                    messageId,
                    body
                )
            );
    }

    @Override
    public void sendDebugSync(final String messageId, final KUniversalMap body) {
        this
            .messageSystem
            .deliverMessageSync(
                KMessage.debug(
                    messageId,
                    body
                )
            );
    }

    @Override
    public void sendMetricsSync(final String messageId, final KUniversalMap body) {
        this
            .messageSystem
            .deliverMessageSync(
                KMessage.metrics(
                    messageId,
                    body
                )
            );
    }
    
}
