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

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.annotation.core.di.KInject;
import io.github.darthakiranihil.konna.core.message.KMessage;
import io.github.darthakiranihil.konna.core.message.KMessageSystem;
import io.github.darthakiranihil.konna.core.message.KMessenger;
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
     * @param messageIdSpecifier Prefix to be attached to internal message id on sending
     */
    public KStandardMessenger(
        @KInject final KMessageSystem messageSystem,
        final String messageIdSpecifier
    ) {
        super(messageSystem, messageIdSpecifier);
        this.addTag(KTag.DefaultTags.STD);
    }

    @Override
    public void sendRegular(final String internalMessageId, final KUniversalMap body) {
        this
            .messageSystem
            .deliverMessage(
                KMessage.regular(
                    this.makeFullMessageId(internalMessageId),
                    body
                )
            );
    }

    @Override
    public void sendSystem(final String shortMessageId, final KUniversalMap body) {
        this
            .messageSystem
            .deliverMessage(
                KMessage.system(
                    this.makeFullMessageId(shortMessageId),
                    body
                )
            );
    }

    @Override
    public void sendDebug(final String shortMessageId, final KUniversalMap body) {
        this
            .messageSystem
            .deliverMessage(
                KMessage.debug(
                    this.makeFullMessageId(shortMessageId),
                    body
                )
            );
    }

    @Override
    public void sendMetrics(final String shortMessageId, final KUniversalMap body) {
        this
            .messageSystem
            .deliverMessage(
                KMessage.metrics(
                    this.makeFullMessageId(shortMessageId),
                    body
                )
            );
    }

    @Override
    public void sendRegularSync(final String shortMessageId, final KUniversalMap body) {
        this
            .messageSystem
            .deliverMessageSync(
                KMessage.regular(
                    this.makeFullMessageId(shortMessageId),
                    body
                )
            );
    }

    @Override
    public void sendSystemSync(final String shortMessageId, final KUniversalMap body) {
        this
            .messageSystem
            .deliverMessageSync(
                KMessage.system(
                    this.makeFullMessageId(shortMessageId),
                    body
                )
            );
    }

    @Override
    public void sendDebugSync(final String shortMessageId, final KUniversalMap body) {
        this
            .messageSystem
            .deliverMessageSync(
                KMessage.debug(
                    this.makeFullMessageId(shortMessageId),
                    body
                )
            );
    }

    @Override
    public void sendMetricsSync(final String shortMessageId, final KUniversalMap body) {
        this
            .messageSystem
            .deliverMessageSync(
                KMessage.metrics(
                    this.makeFullMessageId(shortMessageId),
                    body
                )
            );
    }

    private String makeFullMessageId(final String shortMessageId) {
        return String.format("%s.%s", this.messageIdPrefix, shortMessageId);
    }
}
