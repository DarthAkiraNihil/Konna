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

import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.di.KInjectedConstructor;
import io.github.darthakiranihil.konna.core.log.system.KSystemLogger;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;
import java.util.List;

@KSingleton(immortal = true)
public class KStandardFrameTaskSystem
    extends KObject
    implements KFrameTaskSystem {

    private static final class FrameTask implements KScheduledFrameTask {

        private final String id;
        private final KFrameEvent event;
        private final int priority;
        private final int frequency;
        private final boolean temporal;
        private final boolean debug;
        private final Runnable task;

        private int delay;

        FrameTask(
            final String id,
            final KFrameEvent event,
            int priority,
            int frequency,
            boolean temporal,
            boolean debug,
            final Runnable task
        ) {
            this.id = id;
            this.event = event;
            this.priority = priority;
            this.frequency = frequency;
            this.temporal = temporal;
            this.debug = debug;
            this.task = task;

            this.delay = frequency;
        }

        @Override
        public String getId() {
            return this.id;
        }

        @Override
        public KFrameEvent getEvent() {
            return this.event;
        }

        @Override
        public int getPriority() {
            return this.priority;
        }

        @Override
        public int getFrequency() {
            return this.frequency;
        }

        @Override
        public boolean isTemporal() {
            return this.temporal;
        }

        @Override
        public boolean isDebug() {
            return this.debug;
        }

        public void tryExecute() {
            if (this.delay > 0) {
                this.delay--;
                return;
            }

            this.delay = this.frequency;
            this.task.run();
        }
    }

    private static final class FrameTaskQueue {

        private static final int INITIAL_QUEUE_CAPACITY = 8;

        private Queue<FrameTask> current;
        private Queue<FrameTask> next;

        public FrameTaskQueue() {
            this.current = new PriorityQueue<>(
                INITIAL_QUEUE_CAPACITY, Comparator.comparing(KScheduledFrameTask::getPriority)
            );
            this.next = new PriorityQueue<>(
                INITIAL_QUEUE_CAPACITY, Comparator.comparing(KScheduledFrameTask::getPriority)
            );
        }

        public void executeAll() {
            while (!this.current.isEmpty()) {
                FrameTask currentTask = this.current.poll();
                currentTask.tryExecute();
                if (currentTask.temporal) {
                    continue;
                }
                this.next.add(currentTask);
            }

            Queue<FrameTask> temp = this.current;
            this.current = this.next;
            this.next = temp;
        }
    }

    private static final int TOO_MANY_TASKS_WARNING_LIMIT = 64;

    private final KFrameTaskPrioritizer prioritizer;
    private final Map<KFrameEvent, FrameTaskQueue> queues;

    private boolean debug;
    private boolean frameEntered;

    @KInjectedConstructor
    public KStandardFrameTaskSystem(
        @KInject KFrameTaskPrioritizer prioritizer
    ) {
        super(
            "FrameTaskSystem",
            KStructUtils.setOfTags(KTag.DefaultTags.SYSTEM, KTag.DefaultTags.SYSTEM)
        );
        this.prioritizer = prioritizer;

        KFrameEvent[] frameEvents = KFrameEvent.values();
        this.queues = new HashMap<>(frameEvents.length);
        for (var frameEvent: frameEvents) {
            this.queues.put(frameEvent, new FrameTaskQueue());
        }
    }

    public KStandardFrameTaskSystem() {
        this(new KFrameTaskPrioritizer.LeaveAsIs());
    }

    @Override
    public @Unmodifiable List<KScheduledFrameTask> getScheduledTasks(KFrameEvent event) {
        FrameTaskQueue queue = this.queues.get(event);
        return queue
            .current
            .stream()
            .map(x -> (KScheduledFrameTask) x)
            .toList();
    }

    @Override
    public void executeScheduledTasks(final KFrameEvent event) {
        FrameTaskQueue queue = this.queues.get(event);
        int scheduled = queue.current.size();
        queue.executeAll();
        if (event == KFrameEvent.ENTER) {
            this.frameEntered = true;
        }

        if (scheduled > TOO_MANY_TASKS_WARNING_LIMIT) {
            KSystemLogger.warning(
                this.name,
                "Too many tasks (%d) have been executed on event %s",
                scheduled,
                event
            );
        }
    }

    @Override
    public void setIsDebug(boolean flag) {
        this.debug = flag;
    }

    @Override
    public void scheduleTask(
        final String taskId,
        final KFrameEvent event,
        int initialPriority,
        int frequency,
        final Runnable task,
        boolean isDebug
    ) {
        this.scheduleTask(taskId, event, initialPriority, frequency, task, false, isDebug);
    }

    @Override
    public void scheduleTemporalTask(
        final String taskId,
        final KFrameEvent event,
        int initialPriority,
        int delay,
        final Runnable task,
        boolean isDebug
    ) {
        this.scheduleTask(taskId, event, initialPriority, delay, task, true, isDebug);
    }

    private void scheduleTask(
        final String taskId,
        final KFrameEvent event,
        int initialPriority,
        int frequency,
        final Runnable task,
        boolean temporal,
        boolean isDebug
    ) {
        if (event == KFrameEvent.ENTER && this.frameEntered) {
            KSystemLogger.warning(
                this.name,
                "Frame loop is entered so %s cannot happen. Task %s is not scheduled",
                KFrameEvent.ENTER,
                taskId
            );
            return;
        }

        FrameTaskQueue queue = this.queues.get(event);
        // don't schedule already scheduled tasks
        if (
                (!this.debug && isDebug)
            ||  queue.next.stream().anyMatch(x -> x.id.equals(taskId))
        ) {
            return;
        }

        int priority = this.prioritizer.getPriority(taskId, event, initialPriority, isDebug);
        queue.next.add(
            new FrameTask(
                taskId,
                event,
                priority,
                frequency,
                temporal,
                isDebug,
                task
            )
        );

        KSystemLogger.debug(
            this.name,
            "Scheduled %s task %s [event=%s,priority=%d,%s=%d,debug=%b]",
            temporal ? "temporal" : "persistent",
            taskId,
            event,
            priority,
            temporal ? "delay" : "frequency",
            frequency,
            isDebug
        );
    }
}
