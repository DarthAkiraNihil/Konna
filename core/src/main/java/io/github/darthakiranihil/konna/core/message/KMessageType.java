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

/**
 * Enumeration that represents the type of message that may influence
 * its treatment during deliverance.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public enum KMessageType {
    /**
     * Signalizes that the message should be treated as-is, without any special instructions.
     * Usually used in common messages, sent between components.
     */
    REGULAR,
    /**
     * Signalizes that the message is connected with essential parts of Konna that may affect
     * important system parts. The common case of its occurring is sending a message from the
     * hypervisor itself to the components.
     */
    SYSTEM,
    /**
     * Signalizes that the message contains debug information, that should be treated in
     * a special way. For most cases, you will need a special class that will capture such
     * type of messages.
     */
    DEBUG,
    /**
     * Signalizes that the message contains metrics data, that should be collected in
     * a special class. The common case for its usage is sending metrics of the components
     * to the hypervisor to write it to another class to store them or something like that.
     */
    METRICS,
}
