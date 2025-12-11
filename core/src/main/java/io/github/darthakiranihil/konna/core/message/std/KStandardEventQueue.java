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

import io.github.darthakiranihil.konna.core.log.KSystemLogger;
import io.github.darthakiranihil.konna.core.message.KEvent;
import io.github.darthakiranihil.konna.core.message.KEventQueue;
import io.github.darthakiranihil.konna.core.message.KSimpleEvent;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import org.jspecify.annotations.Nullable;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Standard implementation of {@link KEventQueue} that uses a watcher thread
 * to poll and handle invoked events.
 *
 * @since 0.2.0
 * @author Darth Akira Nihil
 */
@KSingleton(immortal = true)
public class KStandardEventQueue extends KObject implements KEventQueue {

    private static final String WATCHER_THREAD_NAME = "event_watcher";
    private final Queue<Runnable> eventQueue;

    private @Nullable Thread watcher;
    private volatile boolean polling;

    public KStandardEventQueue() {
        super(
            "standard_event_queue",
            KStructUtils.setOfTags(
                KTag.DefaultTags.STD,
                KTag.DefaultTags.SYSTEM
            )
        );
        this.eventQueue = new ConcurrentLinkedQueue<>();
    }

    @Override
    public <T> void queueEvent(final KEvent<T> event, final T arg) {
        this.eventQueue.add(() -> {
            event.invokeSync(arg);
            KSystemLogger.debug("Invoked event %s", event);
        });
    }

    @Override
    public void queueEvent(final KSimpleEvent event) {
        this.eventQueue.add(() -> {
            event.invokeSync();
            KSystemLogger.debug("Invoked event %s", event);
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
            "Event watcher thread has been stopped [host = %s]",
            this
        );
    }

}
