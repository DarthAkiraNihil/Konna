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
import io.github.darthakiranihil.konna.core.log.system.KSystemLogger;
import io.github.darthakiranihil.konna.core.object.KDefaultTags;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.util.KThreadUtils;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.Nullable;

import java.util.*;

/**
 * Standard implementation of {@link KFrameTaskSystem}, based of prioritizer
 * to define frame task execution order.
 *
 * @since 0.6.0
 * @author Darth Akira Nihil
 */
public class KStandardFrameTaskSystem
    extends KObject
    implements KFrameTaskSystem {

    /**
     * Description of task to remove all empty waitlists created for repeatable
     * temporal tasks.
     */
    public static final KFrameTaskDescription
        REMOVE_DEAD_WAITLISTS_TASK = KFrameTaskDescription.ofDelayedPersistent(
        "FrameTaskSystem.removeDeadWaitlists",
        KFrameEvent.FRAME_FINISHED,
        Integer.MAX_VALUE,
        3600
    );

    private static final class FrameTask implements KScheduledFrameTask {

        private final String id;
        private final KFrameEvent event;
        private final int priority;
        private final int delay;
        private final boolean async;
        private final boolean repeatable;
        private final boolean temporal;
        private final boolean debug;
        private final Runnable task;

        private int currentDelay;
        private volatile boolean started;

        FrameTask(
            final KFrameTaskDescription description,
            int priority,
            final Runnable task
        ) {
            this.id = description.taskId();
            this.event = description.event();
            this.priority = priority;
            this.delay = description.delay();
            this.async = description.async();
            this.temporal = description.temporal();
            this.debug = description.debug();
            this.repeatable = description.mayBeRepeated();
            this.task = task;

            this.currentDelay = delay;
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
        public int getDelay() {
            return this.delay;
        }

        @Override
        public boolean isAsync() {
            return this.async;
        }

        @Override
        public boolean isTemporal() {
            return this.temporal;
        }

        @Override
        public boolean isDebug() {
            return this.debug;
        }

        public boolean tryExecute() {
            if (this.started) { // don't decrease delay if the task is not parked (completed) yet
                return false;
            }

            if (this.currentDelay > 1) {
                this.currentDelay--;
                return false;
            }

            this.currentDelay = this.delay;
            if (this.async) {
                this.started = true;
                KThreadUtils.runAsync(() -> {
                    this.task.run();
                    this.started = false;
                });
            } else {
                this.task.run();
            }
            return true;
        }
    }

    private static final class FrameTaskQueue {

        private static final int INITIAL_QUEUE_CAPACITY = 8;
        private static final int INITIAL_WAITLIST_CAPACITY = 2;

        private final Object lock = new Object();

        private final Map<String, Queue<FrameTask>> executionWaitlists;
        private final Set<String> nextTaskIds;

        private Queue<FrameTask> current;
        private Queue<FrameTask> next;

        private boolean executedOnce;

        FrameTaskQueue() {
            this.executionWaitlists = new HashMap<>(INITIAL_QUEUE_CAPACITY);
            this.nextTaskIds = new HashSet<>(INITIAL_QUEUE_CAPACITY);

            this.current = new PriorityQueue<>(
                INITIAL_QUEUE_CAPACITY, Comparator.comparing(KScheduledFrameTask::getPriority)
            );
            this.next = new PriorityQueue<>(
                INITIAL_QUEUE_CAPACITY, Comparator.comparing(KScheduledFrameTask::getPriority)
            );
        }

        public void executeAll() {
            if (!this.executedOnce) {
                this.executedOnce = true;
            }

            synchronized (this.lock) {
                this.nextTaskIds.clear();
            }

            while (!this.current.isEmpty()) {
                FrameTask currentTask = this.current.poll();
                boolean temporal = currentTask.temporal;
                boolean repeatable = currentTask.repeatable;
                boolean executed = currentTask.tryExecute();

                if (!temporal || !executed) {
                    synchronized (this.lock) {
                        this.next.add(currentTask);
                        this.nextTaskIds.add(currentTask.id);
                    }
                    continue;
                }

                if (!repeatable) {
                    continue;
                }

                this.executionWaitlists.putIfAbsent(
                    currentTask.id, new ArrayDeque<>(INITIAL_QUEUE_CAPACITY)
                );
                Queue<FrameTask> waitlist = this.executionWaitlists.get(currentTask.id);
                FrameTask awaited = waitlist.poll();
                if (awaited != null) {
                    synchronized (this.lock) {
                        this.nextTaskIds.add(awaited.id);
                        this.next.add(awaited);
                    }
                }

            }

            Queue<FrameTask> temp = this.current;
            this.current = this.next;
            this.next = temp;
        }

        public @Nullable KScheduledFrameTask enqueueTask(
            final KFrameTaskDescription description,
            final Runnable task,
            final KFrameTaskPrioritizer prioritizer
        ) {
            boolean temporal = description.temporal();
            boolean repeatable = description.mayBeRepeated();

            String taskId = description.taskId();
            if (!this.nextTaskIds.contains(taskId)) {
                // add to q...
                int priority = prioritizer.getPriority(description);
                FrameTask enqueuedTask = new FrameTask(description, priority, task);
                synchronized (this.lock) {
                    this.nextTaskIds.add(taskId);
                    if (this.executedOnce) {
                        this.next.add(enqueuedTask);
                    } else {
                        this.current.add(enqueuedTask);
                    }
                }

                return enqueuedTask;
            }

            if (!temporal || !repeatable) {
                return null;
            }

            int priority = prioritizer.getPriority(description);
            this.executionWaitlists.put(taskId, new ArrayDeque<>(INITIAL_WAITLIST_CAPACITY));
            Queue<FrameTask> waitlist = this.executionWaitlists.get(taskId);
            FrameTask enqueuedTask = new FrameTask(description, priority, task);
            waitlist.add(enqueuedTask);
            return enqueuedTask;
        }

    }

    private static final int TOO_MANY_TASKS_WARNING_LIMIT = 64;

    private final KFrameTaskPrioritizer prioritizer;
    private final Map<KFrameEvent, FrameTaskQueue> queues;

    private boolean debug;
    private boolean frameEntered;

    /**
     * Standard constructor.
     * @param prioritizer Prioritizer to use to assign task priorities
     */
    @KInject
    public KStandardFrameTaskSystem(
        final KFrameTaskPrioritizer prioritizer
    ) {
        super(
            "FrameTaskSystem",
            Set.of(KDefaultTags.STD, KDefaultTags.SYSTEM)
        );
        this.prioritizer = prioritizer;

        KFrameEvent[] frameEvents = KFrameEvent.values();
        this.queues = new HashMap<>(frameEvents.length);
        for (var frameEvent: frameEvents) {
            this.queues.put(frameEvent, new FrameTaskQueue());
        }

        this.scheduleTask(
            REMOVE_DEAD_WAITLISTS_TASK,
            this::removeDeadWaitlists
        );
    }

    /**
     * Creates a new task system with
     * {@link io.github.darthakiranihil.konna.core.app.KFrameTaskPrioritizer.LeaveAsIs}
     * prioritizer.
     */
    public KStandardFrameTaskSystem() {
        this(new KFrameTaskPrioritizer.LeaveAsIs());
    }

    @Override
    public @Unmodifiable List<KScheduledFrameTask> getScheduledTasks(final KFrameEvent event) {
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
            queue.next.clear();
            queue.current.clear();
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
    public void scheduleTask(final KFrameTaskDescription description, final Runnable task) {
        String taskId = description.taskId();
        KFrameEvent event = description.event();
        if (event == KFrameEvent.ENTER && this.frameEntered) {
            KSystemLogger.warning(
                this.name,
                "Frame loop is entered so %s cannot happen. Task %s is not scheduled",
                KFrameEvent.ENTER,
                taskId
            );
            return;
        }

        boolean isDebug = description.debug();
        if (isDebug && !this.debug) {
            return;
        }

        FrameTaskQueue queue = this.queues.get(event);
        KScheduledFrameTask enqueued = queue.enqueueTask(description, task, this.prioritizer);
        if (enqueued == null) {
            return;
        }

        KSystemLogger.debug(
            this.name,
            "Scheduled %s task %s [event=%s,priority=%d,delay=%d,debug=%b]",
            enqueued.isTemporal() ? "temporal" : "persistent",
            taskId,
            event,
            enqueued.getPriority(),
            description.delay(),
            isDebug
        );
    }

    private void removeDeadWaitlists() {

        for (var queue: this.queues.values()) {
            var dead = queue.executionWaitlists
                .entrySet()
                .stream()
                .filter(x -> x.getValue().isEmpty())
                .map(Map.Entry::getKey)
                .toList();

            for (var deadWaitlist: dead) {
                queue.executionWaitlists.remove(deadWaitlist);
            }
        }

    }

}
