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
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.message.KSimpleEvent;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.object.KTag;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.core.test.KExcludeFromGeneratedCoverageReport;

/**
 * Helper class. Represents controller of Dear ImGui rendering process that
 * starts it on a new frame and finishes on frame finishing. Should be implemented
 * by a backend.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
@KSingleton
@KExcludeFromGeneratedCoverageReport
public abstract class KImGuiController extends KObject {

    /**
     * Dear ImGui reference.
     */
    protected final KImGui imGui;

    /**
     * Standard constructor. Also subscribes to {@link KFrame#NEW_FRAME_EVENT_NAME} and
     * {@link KFrame#FRAME_FINISHED_EVENT_NAME} events.
     * @param imGui Dear ImGui
     * @param eventSystem Event system
     */
    public KImGuiController(
        final KImGui imGui,
        final KEventSystem eventSystem
    ) {
        super("imgui_capsule", KStructUtils.setOfTags(KTag.DefaultTags.SYSTEM));
        this.imGui = imGui;

        KSimpleEvent newFrame = eventSystem.getSimpleEvent(KFrame.NEW_FRAME_EVENT_NAME);
        if (newFrame != null) {
            newFrame.subscribe(this::onNewFrame);
        }

        KSimpleEvent preSwap = eventSystem.getSimpleEvent(KFrame.PRE_SWAP_EVENT_NAME);
        if (preSwap != null) {
            preSwap.subscribe(this::onFrameFinished);
        }

        KSimpleEvent loopLeaving = eventSystem.getSimpleEvent(KFrame.LOOP_LEAVING_EVENT_NAME);
        if (loopLeaving != null) {
            loopLeaving.subscribe(this::onDestroy);
        }
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
