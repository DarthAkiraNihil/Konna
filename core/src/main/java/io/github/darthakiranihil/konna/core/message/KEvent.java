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

import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.core.util.KThreadUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Representation of an event - a simple message that should be delivered
 * directly to the listener, without any middlewares and so on.
 * @param <T> Type of event argument
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public class KEvent<T> extends KObject {

    private final List<KEventAction<T>> listeners;
    private KEventQueue eventQueue;

    /**
     * Initializes event with empty listener list.
     * @param name Name of the event
     */
    public KEvent(final String name) {
        super(name, KStructUtils.setOfTags(KTag.DefaultTags.EVENT));
        this.listeners = new LinkedList<>();
    }

    /**
     * Adds a new subscriber to the event. This method is synchronized.
     * @param callable Method reference to be called on event invocation
     */
    public synchronized void subscribe(
        final KEventAction<T> callable
    ) {
        this.listeners.add(callable);
    }

    /**
     * Removes a subscriber from the event. This method is synchronized.
     * @param callable Method reference to remove from event listeners list
     */
    public synchronized void unsubscribe(
        final KEventAction<T> callable
    ) {
        this.listeners.remove(callable);
    }

    /**
     * Invokes the event, calling all methods that have been subscribed to the event.
     * Each subscriber will be invoked asynchronously, so each listener will be called
     * in a separated thread.
     * @param arg Argument of the event
     */
    public void invoke(final T arg) {
        if (this.eventQueue != null) {
            this.eventQueue.queueEvent(this, arg);
            return;
        }

        KThreadUtils.runAsync(() -> {
            this.invokeSync(arg);
        });
    }

    /**
     * Invokes the event, calling all methods that have been subscribed to the event.
     * Each subscriber will be invoked synchronously, so each listener will be called
     * in the same thread of the method. It's not recommended to call it if subscribers' methods
     * are complex and its execution time is huge or the number of listeners is enormous, since it
     * will cause a long time of execution locking. However, this method is useful in tests because
     * them often require assertions of values if they are affected, and with async invocation
     * there is no guarantee that values will change before assertions.
     * @param arg Argument of the event
     */
    public void invokeSync(final T arg) {
        for (KEventAction<T> listener: this.listeners) {
            listener.accept(arg);
        }
    }

    public void setEventQueue(final KEventQueue eventQueue) {
        this.eventQueue = eventQueue;
    }

}
