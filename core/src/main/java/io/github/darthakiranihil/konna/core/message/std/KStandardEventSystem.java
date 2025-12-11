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

import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.log.KSystemLogger;
import io.github.darthakiranihil.konna.core.message.KEvent;
import io.github.darthakiranihil.konna.core.message.KEventQueue;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.message.KSimpleEvent;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Standard implementation of {@link KEventSystem} with automatic event registration
 * in an event queue.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@KSingleton(immortal = true)
public class KStandardEventSystem extends KObject implements KEventSystem {

    private final Map<String, KEvent<?>> events;
    private final Map<String, KSimpleEvent> simpleEvents;
    private final KEventQueue eventQueue;

    /**
     * Standard constructor.
     * @param eventQueue Event queue to register events in
     */
    public KStandardEventSystem(
        @KInject KEventQueue eventQueue
    ) {
        super(
            "std_event_system",
            KStructUtils.setOfTags(
                KTag.DefaultTags.SYSTEM,
                KTag.DefaultTags.STD
            )
        );

        this.eventQueue = eventQueue;
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
        event.setEventQueue(this.eventQueue);
        KSystemLogger.debug("Registered event name=%s", event.name());
    }

    /**
     * Registers a new simple event in the system and connects it
     * with contained event queue.
     * @param event Event to register.
     */
    @Override
    public void registerEvent(final KSimpleEvent event) {
        this.simpleEvents.put(event.name(), event);
        event.setEventQueue(this.eventQueue);
        KSystemLogger.debug("Registered simple event name=%s", event.name());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> KEvent<T> getEvent(final String eventName) {
        if (this.events.containsKey(eventName)) {
            return (KEvent<T>) this.events.get(eventName);
        }

        return null;
    }

    @Override
    public KSimpleEvent getSimpleEvent(final String eventName) {
        if (this.simpleEvents.containsKey(eventName)) {
            return this.simpleEvents.get(eventName);
        }

        return null;
    }
}
