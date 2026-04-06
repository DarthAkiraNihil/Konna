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

package io.github.darthakiranihil.konna.core.app;

public interface KFrameTaskScheduler {

    void scheduleTask(KFrameTaskDescription description, Runnable task);

    void scheduleTask(
        String taskId,
        KFrameEvent event,
        int initialPriority,
        int frequency,
        Runnable task,
        boolean isDebug
    );

    void scheduleTemporalTask(
        String taskId,
        KFrameEvent event,
        int initialPriority,
        int delay,
        Runnable task,
        boolean isDebug
    );

    default void scheduleTask(
        String taskId,
        KFrameEvent event,
        int initialPriority,
        Runnable task,
        boolean isDebug
    ) {
        this.scheduleTask(taskId, event, initialPriority, 0, task, isDebug);
    }

    default void scheduleTemporalTask(
        String taskId,
        KFrameEvent event,
        int initialPriority,
        Runnable task,
        boolean isDebug
    ) {
        this.scheduleTemporalTask(taskId, event, initialPriority, 0, task, isDebug);
    }

    default void scheduleTask(
        String taskId,
        KFrameEvent event,
        int initialPriority,
        Runnable task
    ) {
        this.scheduleTask(taskId, event, initialPriority, 0, task, false);
    }

    default void scheduleTemporalTask(
        String taskId,
        KFrameEvent event,
        int initialPriority,
        Runnable task
    ) {
        this.scheduleTemporalTask(taskId, event, initialPriority, 0, task, false);
    }

}
