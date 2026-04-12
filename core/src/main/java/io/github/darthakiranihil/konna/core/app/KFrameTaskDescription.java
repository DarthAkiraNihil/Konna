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

/**
 * Record containing frame task description to be handled by {@link KFrameTaskScheduler}.
 * @param taskId Unique task id
 * @param event Event the task is executed on
 * @param priority Initial task priority. May be overridden by scheduler
 * @param delay Number of frame loop iterations that must complete before this task is executed
 * @param async Flag that indicated if task should be completed asynchronously, i.e. only
 *              <i>started</i> on scheduled frame but not mandatory completed.
 *              This flag should be set if a task is long-running.
 * @param temporal Flag that indicates if task should be executed in every frame
 *                 or only in the current frame
 * @param mayBeRepeated Flag that indicates if a temporal task may be scheduler more than one
 *                      time during current frame. {@link KFrameTaskScheduler} should ignore
 *                      this component if task is not temporal (i.e. persistent). However,
 *                      repeated tasks may not complete in the current frame,
 *                      but eventually guaranteed to be completed in the next frames.
 * @param debug Flag that indicates if the task should be scheduled only if application
 *              is running in debug mode
 *
 * @since 0.6.0
 * @author Darth Akira Nihil
 */
public record KFrameTaskDescription(
    String taskId,
    KFrameEvent event,
    int priority,
    int delay,
    boolean async,
    boolean temporal,
    boolean mayBeRepeated,
    boolean debug
) {

    /**
     * Convenience factory method for descriptions of tasks that are executed
     * in each frame.
     * @param taskId Unique task id
     * @param event Event the task is executed on
     * @param priority Initial task priority. May be overridden by scheduler
     * @return Description of a persistent task
     */
    public static KFrameTaskDescription ofPersistent(
        final String taskId,
        final KFrameEvent event,
        int priority
    ) {
        return new KFrameTaskDescription(
            taskId,
            event,
            priority,
            0,
            false,
            false,
            false,
            false
        );
    }

    /**
     * Convenience factory method for descriptions of tasks that are executed
     * only once, cannot be scheduled multiple times during one frame loop iteration and
     * executed only after specified amount of completed frames.
     * @param taskId Unique task id
     * @param event Event the task is executed on
     * @param priority Initial task priority. May be overridden by scheduler
     * @param delay Number of frame loop iterations that must complete before this task is executed
     * @return Description of a temporal delayed task
     */
    public static KFrameTaskDescription ofTemporal(
        final String taskId,
        final KFrameEvent event,
        int priority,
        int delay
    ) {
        return new KFrameTaskDescription(
            taskId,
            event,
            priority,
            delay,
            false,
            true,
            false,
            false
        );
    }

    /**
     * Convenience factory method for descriptions of tasks that are executed
     * always once in specified amount of frames.
     * @param taskId Unique task id
     * @param event Event the task is executed on
     * @param priority Initial task priority. May be overridden by scheduler
     * @param delay Number of frame loop iterations that must complete before this task is executed
     * @return Description of a persistent delayed task
     */
    public static KFrameTaskDescription ofDelayedPersistent(
        final String taskId,
        final KFrameEvent event,
        int priority,
        int delay
    ) {
        return new KFrameTaskDescription(
            taskId,
            event,
            priority,
            delay,
            false,
            false,
            false,
            false
        );
    }

    /**
     * Convenience factory method for descriptions of tasks that are executed
     * only once, cannot be scheduled multiple times during one frame loop iteration and
     * executed in the next frame.
     * @param taskId Unique task id
     * @param event Event the task is executed on
     * @param priority Initial task priority. May be overridden by scheduler
     * @return Description of a temporal immediate task
     */
    public static KFrameTaskDescription ofImmediateTemporal(
        final String taskId,
        final KFrameEvent event,
        int priority
    ) {
        return new KFrameTaskDescription(
            taskId,
            event,
            priority,
            0,
            false,
            true,
            false,
            false
        );
    }

    /**
     * Convenience factory method for descriptions of tasks that are executed
     * only once, can be scheduled multiple times during one frame loop iteration and
     * executed in the next frame.
     * @param taskId Unique task id
     * @param event Event the task is executed on
     * @param priority Initial task priority. May be overridden by scheduler
     * @return Description of a temporal immediate repeatable task
     */
    public static KFrameTaskDescription ofRepeatableTemporal(
        final String taskId,
        final KFrameEvent event,
        int priority
    ) {
        return new KFrameTaskDescription(
            taskId,
            event,
            priority,
            0,
            false,
            true,
            true,
            false
        );
    }

    /**
     * Convenience factory method for descriptions of tasks that are executed
     * only once, can be scheduled multiple times during one frame loop iteration and
     * executed only after specified amount of completed frames.
     * @param taskId Unique task id
     * @param event Event the task is executed on
     * @param priority Initial task priority. May be overridden by scheduler
     * @return Description of a temporal immediate repeatable task
     */
    public static KFrameTaskDescription ofDelayedRepeatableTemporal(
        final String taskId,
        final KFrameEvent event,
        int priority,
        int delay
    ) {
        return new KFrameTaskDescription(
            taskId,
            event,
            priority,
            delay,
            false,
            true,
            true,
            false
        );
    }

}
