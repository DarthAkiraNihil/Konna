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

package io.github.darthakiranihil.konna.backend.spair.imgui;

import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import io.github.darthakiranihil.konna.core.app.KFrame;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImGui;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImGuiCapsule;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImGuiIo;

@KSingleton
public final class KImGuiCapsuleSpair extends KImGuiCapsule {

    private final ImGuiImplGl3 imGuiImplGl3;
    private final ImGuiImplGlfw imGuiImplGlfw;

    public KImGuiCapsuleSpair(
        @KInject final KImGui imGui,
        @KInject final KEventSystem eventSystem,
        @KInject final KFrame frame
    ) {
        super(imGui, eventSystem);

        this.imGuiImplGl3 = new ImGuiImplGl3();
        this.imGuiImplGlfw = new ImGuiImplGlfw();

        this.imGui.createContext();
        KImGuiIo io = this.imGui.getIO();
        io.setIniFilename(null); // todo: nullable
        io.getFonts().addFontDefault();
        io.getFonts().build();
        this.imGuiImplGlfw.init(frame.handle(), true);
        this.imGuiImplGl3.init("#version 330 core");
    }

    @Override
    protected void onNewFrame() {
        this.imGuiImplGl3.newFrame();
        this.imGuiImplGlfw.newFrame();
        this.imGui.newFrame();
    }

    @Override
    protected void onFrameFinished() {
        this.imGui.render();
        this.imGuiImplGl3.renderDrawData(
            KImGuiSpairUnboxer.unbox(this.imGui.getDrawData())
        );
    }
}
