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

import io.github.darthakiranihil.konna.core.input.KKeyListener;
import io.github.darthakiranihil.konna.core.struct.KSize;

/**
 * Interface for application's frame that is usually the window
 * where everything is rendered.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
public interface KFrame {

    /**
     * Name of the event that is invoked on entering the frame loop.
     * Used by {@link io.github.darthakiranihil.konna.core.engine.KEngineHypervisor}.
     */
    String LOOP_ENTER_EVENT_NAME = "loop_enter";
    /**
     * Tick event name. It is used by
     * {@link io.github.darthakiranihil.konna.core.engine.KEngineHypervisor} to
     * create the tick event that one should subscribe if its action is required
     * to be executed in the frame loop (since it is invoked in it).
     */
    String TICK_EVENT_NAME = "tick";
    /**
     * Debug tick event name. It is used by
     * {@link io.github.darthakiranihil.konna.core.engine.KEngineHypervisor} to
     * create the debug tick event that is supposed to be used by
     * {@link io.github.darthakiranihil.konna.core.debug.KDebugger}s. It won't be invoked
     * if debug mode is disabled.
     */
    String DEBUG_TICK_EVENT_NAME = "debug_tick";
    /**
     * Name of the event that is invoked on the beginning of a new frame.
     * Used by {@link io.github.darthakiranihil.konna.core.engine.KEngineHypervisor}.
     */
    String NEW_FRAME_EVENT_NAME = "new_frame";
    /**
     * Name of the event that is invoked at the end of the current frame.
     * Used by {@link io.github.darthakiranihil.konna.core.engine.KEngineHypervisor}.
     */
    String FRAME_FINISHED_EVENT_NAME = "frame_finished";
    /**
     * Name of the event that is invoked before swapping frame buffers.
     * Used by {@link io.github.darthakiranihil.konna.core.engine.KEngineHypervisor}.
     */
    String PRE_SWAP_EVENT_NAME = "pre_swap";
    /**
     * Name of the event that is invoked on frame loop leaving.
     * Used by {@link io.github.darthakiranihil.konna.core.engine.KEngineHypervisor}.
     */
    String LOOP_LEAVING_EVENT_NAME = "loop_leaving";

    /**
     * @return Handle of this frame
     */
    long handle();
    /**
     * Sets frame's title.
     * @param newTitle Title of the frame
     */
    void setTitle(String newTitle);

    /**
     * Returns size of this frame.
     * @return Size of the frame
     */
    KSize getSize();

    /**
     * Sets a new size to this frame.
     * @param newSize New size of the frame
     */
    void setSize(KSize newSize);

    /**
     * Sets state of frame's resizability.
     * @param state New state flag of resizability
     */
    void setResizable(boolean state);
    // void setVisible(boolean state);

    /**
     * Shows this frame. Does not take effect if the frame is already shown.
     */
    void show();
    /**
     * Hides this frame. Does not take effect if the frame is already hidden.
     */
    void hide();

    /**
     * Adds a key listener to this frame.
     * @param listener A new key listener
     */
    void addKeyListener(KKeyListener listener);

    /**
     * Terminates this frame (including closing) and releases all its resources.
     */
    void terminate();

    /**
     * Returns flag of if the frame should close.
     * @return Flag of "should close" state of the frame
     */
    boolean shouldClose();
    /**
     * Sets flag of if the frame should close.
     * @param flag New flag of "should close" state of the frame
     */
    void setShouldClose(boolean flag);

    /**
     * If the frame has double-buffering, then this method swaps used buffers.
     */
    void swapBuffers();

    /**
     * Polls events to process of the main loop.
     */
    void pollEvents();
    //void assignToCurrentContext();

    /**
     * Adds a frame lock to this frame. Should not be called manually since this is
     * automatically called when obtaining a {@link KFrameLock} object.
     * @param lock The frame lock
     */
    void addLock(KFrameLock lock);

    /**
     * Removes the lock from this frame. As well as for {@link KFrame#addLock(KFrameLock)},
     * this method should not be called manually since it is called automatically when releasing
     * a {@link KFrameLock} object.
     * @param lock The frame lock to remove
     */
    void removeLock(KFrameLock lock);

    /**
     * Returns flag if the frame has active locks or not.
     * @return Flag of frame locking state
     */
    boolean isLocked();

}
