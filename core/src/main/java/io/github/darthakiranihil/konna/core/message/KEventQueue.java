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

import io.github.darthakiranihil.konna.core.util.KPoller;

/**
 * Interface for an event queue that holds invoked events
 * and invokes them according to FIFO principle.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public interface KEventQueue extends KPoller {

    /**
     * Puts an event to the queue.
     * @param event Event object
     * @param arg Event argument
     * @param <T> Type of event argument
     */
    <T> void queueEvent(KEvent<T> event, T arg);
    /**
     * Puts a simple event to the queue.
     * @param event Event object
     */
    void queueEvent(KSimpleEvent event);

    /**
     * Starts polling process for this event queue.
     * This is required for the queue to handle put events.
     */
    void startPolling();
    /**
     * Stops polling process for this event queue.
     * After this call you still can put events to the queue,
     * but they won't be handled until {@link KEventQueue#startPolling()}
     * is called.
     */
    void stopPolling();

}
