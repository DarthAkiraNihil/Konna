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
import io.github.darthakiranihil.konna.core.object.KArgs;
import io.github.darthakiranihil.konna.core.object.KDefaultTags;
import io.github.darthakiranihil.konna.core.object.KObject;

import java.util.Collections;

/**
 * Convenience base class which purpose is to simplify
 * sending messages by using only internal message id instead of full id.
 * Usually a component should create corresponding messenger and pass it to
 * component context (so it is used everywhere inside the component)
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public final class KMessenger extends KObject {

    /**
     * Returns {@link KArgs} object for all non-injected (explicitly passed)
     * messenger constructor's args.
     * @param messageIdPrefix Prefix to be attached to internal message id on sending
     * @return Packed args
     */
    public static KArgs args(final String messageIdPrefix) {
        return () -> new Object[] {messageIdPrefix};
    }
    
    private final KMessageSystem messageSystem;
    private final String messageIdPrefix;

    /**
     * Standard constructor.
     * @param messageSystem Parent message system that messenger sends messages to
     * @param messageIdPrefix Prefix to be attached to internal message id on sending
     */
    @KInject
    public KMessenger(
        final String messageIdPrefix,
        final KMessageSystem messageSystem
    ) {
        super(
            String.format("messenger_%s", messageIdPrefix),
            Collections.singleton(KDefaultTags.SYSTEM)
        );

        this.messageIdPrefix = messageIdPrefix;
        this.messageSystem = messageSystem;
    }
        
    /**
     * Creates a regular message and passes it to the message system to process.
     * This operation is asynchronous.
     * @param internalMessageId Internal message id (does not include component name)
     * @param body Message body
     */
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
    
    /**
     * Creates a system message and passes it to the message system to process.
     * This operation is asynchronous.
     * @param internalMessageId Internal message id (does not include component name)
     * @param body Message body
     */
    public void sendSystem(final String internalMessageId, final KUniversalMap body) {
        this
            .messageSystem
            .deliverMessage(
                KMessage.system(
                    this.makeFullMessageId(internalMessageId),
                    body
                )
            );
    }
    
    /**
     * Creates a debug message and passes it to the message system to process.
     * This operation is asynchronous.
     * @param internalMessageId Internal message id (does not include component name)
     * @param body Message body
     */
    public void sendDebug(final String internalMessageId, final KUniversalMap body) {
        this
            .messageSystem
            .deliverMessage(
                KMessage.debug(
                    this.makeFullMessageId(internalMessageId),
                    body
                )
            );
    }
    
    /**
     * Creates a metrics message and passes it to the message system to process.
     * This operation is asynchronous.
     * @param internalMessageId Internal message id (does not include component name)
     * @param body Message body
     */
    public void sendMetrics(final String internalMessageId, final KUniversalMap body) {
        this
            .messageSystem
            .deliverMessage(
                KMessage.metrics(
                    this.makeFullMessageId(internalMessageId),
                    body
                )
            );
    }
    
    /**
     * Creates a regular message and passes it to the message system to process.
     * This operation is synchronous, which is useful for testing.
     * @param internalMessageId Internal message id (does not include component name)
     * @param body Message body
     */
    public void sendRegularSync(final String internalMessageId, final KUniversalMap body) {
        this
            .messageSystem
            .deliverMessageSync(
                KMessage.regular(
                    this.makeFullMessageId(internalMessageId),
                    body
                )
            );
    }
    
    /**
     * Creates a system message and passes it to the message system to process.
     * This operation is synchronous, which is useful for testing.
     * @param internalMessageId Internal message id (does not include component name)
     * @param body Message body
     */
    public void sendSystemSync(final String internalMessageId, final KUniversalMap body) {
        this
            .messageSystem
            .deliverMessageSync(
                KMessage.system(
                    this.makeFullMessageId(internalMessageId),
                    body
                )
            );
    }
    
    /**
     * Creates a debug message and passes it to the message system to process.
     * This operation is synchronous, which is useful for testing.
     * @param internalMessageId Internal message id (does not include component name)
     * @param body Message body
     */
    public void sendDebugSync(final String internalMessageId, final KUniversalMap body) {
        this
            .messageSystem
            .deliverMessageSync(
                KMessage.debug(
                    this.makeFullMessageId(internalMessageId),
                    body
                )
            );
    }
    
    /**
     * Creates a metrics message and passes it to the message system to process.
     * This operation is synchronous, which is useful for testing.
     * @param internalMessageId Internal message id (does not include component name)
     * @param body Message body
     */
    public void sendMetricsSync(final String internalMessageId, final KUniversalMap body) {
        this
            .messageSystem
            .deliverMessageSync(
                KMessage.metrics(
                    this.makeFullMessageId(internalMessageId),
                    body
                )
            );
    }

    private String makeFullMessageId(final String internalMessageId) {
        return String.format("%s.%s", this.messageIdPrefix, internalMessageId);
    }

}
