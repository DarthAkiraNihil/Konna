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
 * Interface for scheduled frame task information.
 *
 * @since 0.6.0
 * @author Darth Akira Nihil
 */
public interface KScheduledFrameTask {

    /**
     * @return ID of this task
     */
    String getId();

    /**
     * @return Event the task is executed on
     */
    KFrameEvent getEvent();

    /**
     * @return Real task priority
     */
    int getPriority();

    /**
     * @return Delay of this task
     */
    int getDelay();

    /**
     * @return Whether this task is temporal or not
     */
    boolean isTemporal();

    /**
     * @return Whether this task is debug or not
     */
    boolean isDebug();

}
