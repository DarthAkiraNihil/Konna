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
 * Enumeration that represents possible event, executing in a frame loop.
 *
 * @since 0.6.0
 * @author Darth Akira Nihil
 */
public enum KFrameEvent {

    /**
     * Event that executes right before entering the frame loop itself.
     * Invokes exactly once, so any attempt to schedule tasks for this event
     * after loop entering <i>should</i> be rejected since they won't be executed.
     */
    ENTER,
    /**
     * Event that executes in the beginning of a new frame loop iteration.
     */
    NEW_FRAME,
    /**
     * <p>
     *     Event that executes in the middle of a frame loop iteration.
     * </p>
     * <p>
     *     This event is supposed to contain tasks that execute general game logic such as
     *     processing active objects etc. This should not be used in cases when the only actions
     *     are some preparations, initializations and so on. However, it's not controlled,
     *     it is still possible to schedule similar task on this event.
     * </p>
     */
    TICK,
    /**
     * <p>
     *     Event that executes between buffers swapping.
     * </p>
     * <p>
     *     General purpose of this event is rendering, but it's not limited.
     * </p>
     */
    PRE_SWAP,
    /**
     * <p>
     *     Event that executes in the end of a frame loop iteration.
     * </p>
     * <p>
     *     This event is supposed to contain tasks that finalizes objects,
     *     actual only for exact one frame. This should not be used for finalizing global objects,
     *     that may be used later. However, it's not controlled.
     * </p>
     */
    FRAME_FINISHED,
    /**
     * Event that executes right after leaving a frame loop. Use this event for all global
     * object finalizers.
     */
    SHUTDOWN

}
