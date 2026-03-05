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

package io.github.darthakiranihil.konna.core.util;

import io.github.darthakiranihil.konna.core.log.system.KSystemLogger;
import io.github.darthakiranihil.konna.core.object.KUninstantiable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Provides different useful utils, connected with threads and concurrency.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
public final class KThreadUtils extends KUninstantiable {

    private KThreadUtils() {
        super();
    }

    /**
     * Runs a task asynchronously. Uses {@link CompletableFuture} for it.
     * @param task Task to run asynchronously
     */
    public static void runAsync(final Runnable task) {
        CompletableFuture.runAsync(task);
    }

    /**
     * Sleeps for seconds in the current thread.
     * @param seconds Seconds to sleep
     * @since 0.5.0
     */
    public static void sleepForSeconds(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            KSystemLogger.info("sleep", "Could not sleep :(");
        }
    }
}
