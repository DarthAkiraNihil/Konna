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
 * Interface encapsulation simple event invocation logic.
 *
 * @since 0.6.0
 * @author Darth Akira Nihil
 */
public interface KSimpleEventInvoker {

    /**
     * Invokes the event, calling all methods that have been subscribed to the event
     * asynchronously.
     */
    void invoke();

    /**
     * Invokes the event, calling all methods that have been subscribed to the event.
     * Each subscriber will be invoked synchronously, so each listener will be called
     * in the same thread of the method. It's not recommended to call it if subscribers' methods
     * are complex and its execution time is huge or the number of listeners is enormous, since it
     * will cause a long time of execution locking. However, this method is useful in tests because
     * them often require assertions of values if they are affected, and with async invocation
     * there is no guarantee that values will change before assertions.
     */
    void invokeSync();

}
