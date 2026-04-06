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

import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

/**
 * Interface providing methods for executing scheduled frame tasks and
 * retrieving information about them.
 *
 * @since 0.6.0
 * @author Darth Akira Nihil
 */
public interface KFrameTaskExecutor {

    /**
     * @param event Event the tasks are scheduled for
     * @return List for all scheduled tasks for specific event
     */
    @Unmodifiable
    List<KScheduledFrameTask> getScheduledTasks(KFrameEvent event);

    /**
     * Executes all tasks, scheduled for specific event.
     * @param event Assigned task event to execute
     */
    void executeScheduledTasks(KFrameEvent event);

    /**
     * Sets debug mode status for this executor.
     * @param flag Flag of active debug mode
     */
    void setIsDebug(boolean flag);

}
