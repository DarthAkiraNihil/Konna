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

import java.util.UUID;

/**
 * Interface encapsulation simple event subscription-unsubscription logic.
 *
 * @since 0.6.0
 * @author Darth Akira Nihil
 */
public interface KSimpleEventSubscriber {

    /**
     * Adds a new subscriber to the event.
     * @param action Method reference to be called on event invocation
     * @return Subscription token (don't lose it, it's needed for unsubscription)
     */
    UUID subscribe(KSimpleEventAction action);

    /**
     * Removes a subscriber from the event.
     * @param subscriptionToken Subscription token,
     *                          retrieved from
     *                          {@link KSimpleEventSubscriber#subscribe(KSimpleEventAction)}
     */
    void unsubscribe(UUID subscriptionToken);

}
