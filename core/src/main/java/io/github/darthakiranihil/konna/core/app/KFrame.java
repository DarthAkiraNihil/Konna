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

    void addLock(KFrameLock lock);
    void removeLock(KFrameLock lock);

    boolean isLocked();

    void initializeContext();

}
