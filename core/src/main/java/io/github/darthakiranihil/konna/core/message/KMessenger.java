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
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;

/**
 * Convenience base class which purpose is to simplify
 * sending messages by providing methods that just accepts a message id
 * and a body instead of manually created message
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public abstract class KMessenger extends KObject {

    /**
     * Reference to the message system.
     */
    protected final KMessageSystem messageSystem;

    /**
     * Base constructor.
     * @param messageSystem Parent message system that messenger sends messages to
     */
    @KInject
    public KMessenger(
        final KMessageSystem messageSystem
    ) {
        super(
            "messenger",
            KStructUtils.setOfTags(KTag.DefaultTags.SYSTEM)
        );
        
        this.messageSystem = messageSystem;
    }

    /**
     * Creates a regular message and passes it to the message system to process.
     * This operation is asynchronous.
     * @param messageId Message id
     * @param body Message body
     */
    public abstract void sendRegular(String messageId, KUniversalMap body);
    /**
     * Creates a system message and passes it to the message system to process.
     * This operation is asynchronous.
     * @param messageId Message id
     * @param body Message body
     */
    public abstract void sendSystem(String messageId, KUniversalMap body);
    /**
     * Creates a debug message and passes it to the message system to process.
     * This operation is asynchronous.
     * @param messageId Message id
     * @param body Message body
     */
    public abstract void sendDebug(String messageId, KUniversalMap body);
    /**
     * Creates a metrics message and passes it to the message system to process.
     * This operation is asynchronous.
     * @param messageId Message id
     * @param body Message body
     */
    public abstract void sendMetrics(String messageId, KUniversalMap body);

    /**
     * Creates a regular message and passes it to the message system to process.
     * This operation is synchronous, which is useful for testing.
     * @param messageId Message id
     * @param body Message body
     */
    public abstract void sendRegularSync(String messageId, KUniversalMap body);
    /**
     * Creates a system message and passes it to the message system to process.
     * This operation is synchronous, which is useful for testing.
     * @param messageId Message id
     * @param body Message body
     */
    public abstract void sendSystemSync(String messageId, KUniversalMap body);
    /**
     * Creates a debug message and passes it to the message system to process.
     * This operation is synchronous, which is useful for testing.
     * @param messageId Message id
     * @param body Message body
     */
    public abstract void sendDebugSync(String messageId, KUniversalMap body);
    /**
     * Creates a metrics message and passes it to the message system to process.
     * This operation is synchronous, which is useful for testing.
     * @param messageId Message id
     * @param body Message body
     */
    public abstract void sendMetricsSync(String messageId, KUniversalMap body);

}
