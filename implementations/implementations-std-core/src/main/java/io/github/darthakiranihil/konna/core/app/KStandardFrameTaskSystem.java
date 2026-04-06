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

/**
 * Standard implementation of {@link KFrameTaskSystem}, based of prioritizer
 * to define frame task execution order.
 *
 * @since 0.6.0
 * @author Darth Akira Nihil
 */
@KSingleton(immortal = true)
public class KStandardFrameTaskSystem
    extends KObject
    implements KFrameTaskSystem {

    private static final class FrameTask implements KScheduledFrameTask {

        private final String id;
        private final KFrameEvent event;
        private final int priority;
        private final int delay;
        private final boolean temporal;
        private final boolean debug;
        private final Runnable task;

        private int currentDelay;

        FrameTask(
            final String id,
            final KFrameEvent event,
            int priority,
            int delay,
            boolean temporal,
            boolean debug,
            final Runnable task
        ) {
            this.id = id;
            this.event = event;
            this.priority = priority;
            this.delay = delay;
            this.temporal = temporal;
            this.debug = debug;
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
        public boolean isTemporal() {
            return this.temporal;
        }

        @Override
        public boolean isDebug() {
            return this.debug;
        }

        public void tryExecute() {
            if (this.currentDelay > 0) {
                this.currentDelay--;
                return;
            }

            this.currentDelay = this.delay;
            this.task.run();
        }
    }

    private static final class FrameTaskQueue {

        private static final int INITIAL_QUEUE_CAPACITY = 8;
        private static final int INITIAL_CHAIN_CAPACITY = 2;

        private final Map<String, Queue<FrameTask>> executionChains;

        private Queue<KScheduledFrameTask> current;
        private Queue<KScheduledFrameTask> next;

        FrameTaskQueue() {
            this.executionChains = new HashMap<>(INITIAL_QUEUE_CAPACITY);

            this.current = new PriorityQueue<>(
                INITIAL_QUEUE_CAPACITY, Comparator.comparing(KScheduledFrameTask::getPriority)
            );
            this.next = new PriorityQueue<>(
                INITIAL_QUEUE_CAPACITY, Comparator.comparing(KScheduledFrameTask::getPriority)
            );
        }

        public void executeAll() {
            while (!this.current.isEmpty()) {
                KScheduledFrameTask currentScheduledTask = this.current.poll();
                String currentTaskId = currentScheduledTask.getId();

                Queue<FrameTask>
                    executionChain = this.executionChains.get(currentTaskId);

                FrameTask currentTask = Objects.requireNonNull(currentScheduledTask.isTemporal()
                    ? executionChain.poll()
                    : executionChain.peek()
                );
                currentTask.tryExecute();
                if (currentTask.temporal) {
                    continue;
                }
                if (executionChain.isEmpty()) {
                    this.executionChains.remove(currentTaskId);
                }
                this.addTask(currentTask);
            }

            Queue<KScheduledFrameTask> temp = this.current;
            this.current = this.next;
            this.next = temp;
        }

        public void addTask(final FrameTask task) {
            this.executionChains.putIfAbsent(task.id, new ArrayDeque<>(INITIAL_CHAIN_CAPACITY));
            Queue<FrameTask> executionChain = this.executionChains.get(task.id);
            executionChain.add(task);
            // we can't schedule more than 1 task with same id
            if (
                this.next
                    .stream()
                    .anyMatch(x -> x.getId().equals(task.id))
            ) {
                return;
            }
            this.next.add(task);
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
    @KInjectedConstructor
    public KStandardFrameTaskSystem(
        @KInject final KFrameTaskPrioritizer prioritizer
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
        FrameTaskQueue queue = this.queues.get(event);
        if (
                (!this.debug && isDebug)
            ||  (queue.executionChains.containsKey(taskId) && !description.mayBeRepeated())
        ) {
            return;
        }

        int priority = this.prioritizer.getPriority(description);
        boolean temporal = description.temporal();
        queue.addTask(
            new FrameTask(
                taskId,
                event,
                priority,
                description.delay(),
                temporal,
                isDebug,
                task
            )
        );

        KSystemLogger.debug(
            this.name,
            "Scheduled %s task %s [event=%s,priority=%d,delay=%d,debug=%b]",
            temporal ? "temporal" : "persistent",
            taskId,
            event,
            priority,
            description.delay(),
            isDebug
        );
    }

}
