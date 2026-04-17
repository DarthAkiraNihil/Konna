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

import io.github.darthakiranihil.konna.core.object.KDefaultTags;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.util.KThreadUtils;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Representation of an event - a simple message that should be delivered
 * directly to the listener, without any middlewares and so on.
 * Just the same as {@link KEvent}, but its action does not accept any arguments
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public final class KSimpleEvent
    extends KObject
    implements KSimpleEventInvoker, KSimpleEventSubscriber {
    private static final int INITIAL_LISTENERS_CAPACITY = 4;
    private final Object lock = new Object();

    private final Map<UUID, KSimpleEventAction> listeners;
    private @Nullable KEventQueue eventQueue;

    /**
     * Initializes event with empty listener list.
     * @param name Name of the event
     */
    public KSimpleEvent(final String name) {
        super(name, Collections.singleton(KDefaultTags.EVENT));
        this.listeners = new HashMap<>(INITIAL_LISTENERS_CAPACITY);
    }

    /**
     * Adds a new subscriber to the event. This method is synchronized.
     * @param callable Method reference to be called on event invocation
     */
    @Override
    public UUID subscribe(
        final KSimpleEventAction callable
    ) {
        UUID subscriptionToken = UUID.randomUUID();
        synchronized (this.lock) {
            this.listeners.put(subscriptionToken, callable);
        }
        return subscriptionToken;
    }

    @Override
    public void unsubscribe(
        final UUID subscriptionToken
    ) {
        synchronized (this.lock) {
            this.listeners.remove(subscriptionToken);
        }
    }

    /**
     * Invokes the event, calling all methods that have been subscribed to the event
     * asynchronously i.e. in a separated thread, but it is applied only if no event queue
     * is assigned to this event.
     * If the event is connected to an event queue, it will be passed to it and invoked
     * when it reaches the end of the queue.
     */
    @Override
    public void invoke() {
        if (this.eventQueue != null) {
            this.eventQueue.queueEvent(this);
            return;
        }

        KThreadUtils.runAsync(this::invokeSync);
    }

    @Override
    public void invokeSync() {
        synchronized (this.lock) {
            for (KSimpleEventAction listener : this.listeners.values()) {
                listener.accept();
            }
        }
    }

    /**
     * Sets an event queue for this event so on invoking asynchronously
     * it will put its call to it instead of creating a task by itself.
     * @param eventQueue Assigned event queue
     */
    public void setEventQueue(final KEventQueue eventQueue) {
        this.eventQueue = eventQueue;
    }
}
