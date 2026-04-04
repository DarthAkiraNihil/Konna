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
 * Represents an event system - a service that holds all created events
 * and provides opportunity to get them so the caller class will be able
 * to subscribe to them.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public interface KEventSystem {

    /**
     * Registers a new event in the system.
     * @param event Event to register.
     * @param <T> Type of the event argument
     */
    <T> void registerEvent(KEvent<T> event);

    /**
     * Registers a new simple event in the system.
     * @param event Event to register.
     */
    void registerEvent(KSimpleEvent event);

    /**
     * Returns an event invoker, registered in the system, by its name.
     * If it's not presented,
     * {@link io.github.darthakiranihil.konna.core.except.KNotFoundException} will be thrown.
     * @param eventName Name of the event.
     * @return The event with given name or {@code null}
     * @param <T> Type of the event argument.
     *
     * @since 0.6.0
     */
    <T> KEventInvoker<T> getEventInvoker(String eventName);
    /**
     * Returns a simple event invoker, registered in the system, by its name.
     * If it's not presented,
     * {@link io.github.darthakiranihil.konna.core.except.KNotFoundException} will be thrown.
     * @param eventName Name of the event
     * @return The event with given name
     *
     * @since 0.6.0
     */
    KSimpleEventInvoker getSimpleEventInvoker(String eventName);

    /**
     * Returns an event subscriber, registered in the system, by its name.
     * If it's not presented,
     * {@link io.github.darthakiranihil.konna.core.except.KNotFoundException} will be thrown.
     * @param eventName Name of the event.
     * @return The event with given name or {@code null}
     * @param <T> Type of the event argument.
     *
     * @since 0.6.0
     */
    <T> KEventSubscriber<T> getEventSubscriber(String eventName);
    /**
     * Returns a simple event subscriber, registered in the system, by its name.
     * If it's not presented,
     * {@link io.github.darthakiranihil.konna.core.except.KNotFoundException} will be thrown.
     * @param eventName Name of the event
     * @return The event with given name
     *
     * @since 0.6.0
     */
    KSimpleEventSubscriber getSimpleEventSubscriber(String eventName);

}
