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

package io.github.darthakiranihil.konna.libfrontend.imgui;

import io.github.darthakiranihil.konna.core.app.KFrame;
import io.github.darthakiranihil.konna.core.app.KFrameEvent;
import io.github.darthakiranihil.konna.core.app.KFrameTaskScheduler;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.test.KExcludeFromGeneratedCoverageReport;

/**
 * Helper class. Represents controller of Dear ImGui rendering process that
 * starts it on a new frame and finishes on frame finishing. Should be implemented
 * by a backend.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
@KSingleton
@KExcludeFromGeneratedCoverageReport
public abstract class KImGuiController extends KObject {

    private static final String ON_NEW_FRAME_TASK_ID = "DearImGui.prepareForNewFrame";
    private static final String ON_PRE_SWAP_TASK_ID = "DearImGui.finishCurrentFrame";
    private static final String ON_SHUTDOWN_FRAME_TASK_ID = "DearImGui.destroyDearImGui";
    private static final int PRIORITY = 16;

    /**
     * Dear ImGui reference.
     */
    protected final KImGui imGui;

    /**
     * Standard constructor. Also subscribes to {@link KFrame#NEW_FRAME_EVENT_NAME} and
     * {@link KFrame#FRAME_FINISHED_EVENT_NAME} events.
     * @param imGui Dear ImGui
     * @param frameTaskScheduler Frame task scheduler
     */
    public KImGuiController(
        final KImGui imGui,
        final KFrameTaskScheduler frameTaskScheduler
    ) {
        super("imgui_capsule", KStructUtils.setOfTags(KTag.DefaultTags.SYSTEM));
        this.imGui = imGui;

        frameTaskScheduler.scheduleTask(
            ON_NEW_FRAME_TASK_ID, KFrameEvent.NEW_FRAME, PRIORITY, this::onNewFrame
        );
        frameTaskScheduler.scheduleTask(
            ON_PRE_SWAP_TASK_ID, KFrameEvent.PRE_SWAP, PRIORITY, this::onFrameFinished
        );
        frameTaskScheduler.scheduleTask(
            ON_SHUTDOWN_FRAME_TASK_ID, KFrameEvent.SHUTDOWN, PRIORITY, this::onDestroy
        );
    }

    /**
     * Starts Dear ImGui for using it in this frame.
     */
    protected abstract void onNewFrame();

    /**
     * Finalizes Dear ImGui in this frame.
     */
    protected abstract void onFrameFinished();

    /**
     * Finalized Dear ImGui in this application.
     */
    protected abstract void onDestroy();

}
