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
 * <p>
 *     Interface for scheduling frame tasks.
 * </p>
 * <p>
 *     The core principle for frame tasks is "If task can be executed,
 *     it will be executed eventually". I.e. a scheduled task may not be completed during the next
 *     frame, but will be completed during following frames,
 *     so it will be executed sooner or later.
 * </p>
 * <p>
 *     However, a scheduler must reject all tasks that are not suitable for it.
 *     For example, if debug mode is disabled, no debug tasks should be scheduled;
 *     or non-repeatable temporal task that has been scheduled twice should be rejected
 *     on the second scheduling attempt.
 * </p>
 *
 * @since 0.6.0
 * @author Darth Akira Nihil
 */
public interface KFrameTaskScheduler {

    /**
     * Schedules a frame task to be completed on next frames.
     * @param description Task description
     * @param task Task executable object to run
     */
    void scheduleTask(KFrameTaskDescription description, Runnable task);

}
