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
import io.github.darthakiranihil.konna.core.app.KFrameTaskScheduler;
import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.di.KSingleton;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImGui;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImGuiController;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImGuiIo;
import io.github.darthakiranihil.konna.test.KExcludeFromGeneratedCoverageReport;

/**
 * ImGui controller implementation using SpaiR bindings.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
@KSingleton
@KExcludeFromGeneratedCoverageReport
public class KImGuiControllerSpair extends KImGuiController {

    private final ImGuiImplGl3 imGuiImplGl3;
    private final ImGuiImplGlfw imGuiImplGlfw;
    private final KFrame frame;

    @KInject
    public KImGuiControllerSpair(
        final KImGui imGui,
        final KFrameTaskScheduler frameTaskScheduler,
        final KFrame frame
    ) {
        super(imGui, frameTaskScheduler);

        this.imGuiImplGl3 = new ImGuiImplGl3();
        this.imGuiImplGlfw = new ImGuiImplGlfw();

        this.imGui.createContext();
        KImGuiIo io = this.imGui.getIO();

        io.setIniFilename(null);

        io.getFonts().addFontDefault();
        io.getFonts().build();

        this.imGuiImplGlfw.init(frame.handle(), true);
        this.imGuiImplGl3.init("#version 330 core");

        // maybe there are more of them but idk
        // todo: manually setting this is boring, need to add runtime modules or smth like that
        //        containerAccessor
        //            .getContainer()
        //            .add(KImFontConfig.class, KImFontConfigSpair.class)
        //            .add(KImFontGlyph.class, KImFontGlyphSpair.class)
        //            .add(KImGuiIo.class, KImGuiIoSpair.class)
        //            .add(KImGuiStorage.class, KImGuiStorageSpair.class)
        //            .add(KImGuiStyle.class, KImGuiStyleSpair.class)
        //            .add(KImGuiKeyData.class, KImGuiKeyDataSpair.class);

        this.frame = frame;

    }

    @Override
    protected void onNewFrame() {
        this.frame.getInputProcessor().enable();

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

        if (
                this.imGui.getIO().getWantCaptureKeyboard()
            ||  this.imGui.getIO().getWantCaptureMouse()) {
            this.frame.getInputProcessor().disable();
        }
    }

    @Override
    protected void onDestroy() {
        this.imGui.destroyContext();
    }
}
