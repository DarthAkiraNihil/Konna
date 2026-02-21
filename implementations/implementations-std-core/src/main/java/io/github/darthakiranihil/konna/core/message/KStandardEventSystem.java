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

import io.github.darthakiranihil.konna.core.log.system.KSystemLogger;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Standard implementation of {@link KEventSystem} with automatic event registration
 * in an event queue.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@KSingleton(immortal = true)
public class KStandardEventSystem extends KObject implements KQueueBasedEventSystem {

    private static final String WATCHER_THREAD_NAME = "event_watcher";
    private final Queue<Runnable> eventQueue;

    private final Map<String, KEvent<?>> events;
    private final Map<String, KSimpleEvent> simpleEvents;

    private @Nullable Thread watcher;
    private volatile boolean polling;

    /**
     * Standard constructor.
     */
    public KStandardEventSystem() {
        super(
            "std_event_system",
            KStructUtils.setOfTags(
                KTag.DefaultTags.SYSTEM,
                KTag.DefaultTags.STD
            )
        );

        this.eventQueue = new ConcurrentLinkedQueue<>();
        this.events = new HashMap<>();
        this.simpleEvents = new HashMap<>();
    }

    /**
     * Registers a new event in the system and connects it with
     * contained event queue.
     * @param event Event to register.
     * @param <T> Type of the event argument
     */
    @Override
    public <T> void registerEvent(final KEvent<T> event) {
        this.events.put(event.name(), event);
        event.setEventQueue(this);
        KSystemLogger.debug(this.name, "Registered event name=%s", event.name());
    }

    /**
     * Registers a new simple event in the system and connects it
     * with contained event queue.
     * @param event Event to register.
     */
    @Override
    public void registerEvent(final KSimpleEvent event) {
        this.simpleEvents.put(event.name(), event);
        event.setEventQueue(this);
        KSystemLogger.debug(this.name, "Registered simple event name=%s", event.name());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> @Nullable KEvent<T> getEvent(final String eventName) {
        if (this.events.containsKey(eventName)) {
            return (KEvent<T>) this.events.get(eventName);
        }

        return null;
    }

    @Override
    public @Nullable KSimpleEvent getSimpleEvent(final String eventName) {
        if (this.simpleEvents.containsKey(eventName)) {
            return this.simpleEvents.get(eventName);
        }

        return null;
    }

    @Override
    public <T> void queueEvent(final KEvent<T> event, final T arg) {
        this.eventQueue.add(() -> {
            event.invokeSync(arg);
            KSystemLogger.debug(this.name, "Invoked event %s", event);
        });
    }

    @Override
    public void queueEvent(final KSimpleEvent event) {
        this.eventQueue.add(() -> {
            event.invokeSync();
            KSystemLogger.debug(this.name, "Invoked event %s", event);
        });
    }

    @Override
    public void startPolling() {
        this.polling = true;
        this.watcher = new Thread(this::watch);
        this.watcher.setName(WATCHER_THREAD_NAME);
        this.watcher.start();
    }

    @Override
    public void stopPolling() {
        this.polling = false;
        this.watcher = null;
    }

    private void watch() {

        KSystemLogger.info(
            this.name,
            "Event watcher thread has been started. Now polling events [host = %s]",
            this
        );

        while (this.polling || !this.eventQueue.isEmpty()) {

            Runnable eventInvoker = this.eventQueue.poll();
            if (eventInvoker != null) {
                eventInvoker.run();
            }

        }

        KSystemLogger.info(
            this.name,
            "Event watcher thread has been stopped [host = %s]",
            this
        );
    }
}
