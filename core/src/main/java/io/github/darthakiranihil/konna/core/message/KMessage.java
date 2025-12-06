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

import java.util.UUID;

/**
 * Data structure representing a message to be delivered to other components
 * or so on.
 * The message is defined by its messageId and type. The messageId is literally the
 * name of the message that is used to identify kind of the message. The type
 * determines the way the message should be interpreted.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public final class KMessage {

    private final UUID id;
    private final String messageId;
    private final KUniversalMap body;
    private final KMessageType type;

    private KMessage(
        final String messageId,
        final KUniversalMap body,
        final KMessageType type
    ) {
        this.id = UUID.randomUUID();
        this.messageId = messageId;
        this.body = body;
        this.type = type;
    }

    /**
     * Constructs a regular message.
     * @param messageId Message id
     * @param body Message body
     * @return Message with {@link KMessageType}.REGULAR type
     */
    public static KMessage regular(final String messageId, final KUniversalMap body) {
        return new KMessage(messageId, body, KMessageType.REGULAR);
    }

    /**
     * Constructs a system message.
     * @param messageId Message id
     * @param body Message body
     * @return Message with {@link KMessageType}.SYSTEM type
     */
    public static KMessage system(final String messageId, final KUniversalMap body) {
        return new KMessage(messageId, body, KMessageType.SYSTEM);
    }

    /**
     * Constructs a debug message.
     * @param messageId Message id
     * @param body Message body
     * @return Message with {@link KMessageType}.DEBUG type
     */
    public static KMessage debug(final String messageId, final KUniversalMap body) {
        return new KMessage(messageId, body, KMessageType.DEBUG);
    }

    /**
     * Constructs a metrics message.
     * @param messageId Message id
     * @param body Message body
     * @return Message with {@link KMessageType}.METRICS type
     */
    public static KMessage metrics(final String messageId, final KUniversalMap body) {
        return new KMessage(messageId, body, KMessageType.METRICS);
    }

    /**
     * Returns the unique id of the message.
     * @return Unique ID of the message
     */
    public UUID id() {
        return this.id;
    }

    /**
     * Returns messageId (name) of the message.
     * @return messageId (name) of the message
     */
    public String messageId() {
        return this.messageId;
    }

    /**
     * Returns body of the message.
     * @return Body of the message
     */
    public KUniversalMap body() {
        return this.body;
    }

    /**
     * Returns type of the message.
     * @return Type of the message.
     */
    public KMessageType type() {
        return this.type;
    }

    @Override
    public String toString() {
        return "KMessage[" + id + "](" + messageId + ", " + type + ')';
    }
}
