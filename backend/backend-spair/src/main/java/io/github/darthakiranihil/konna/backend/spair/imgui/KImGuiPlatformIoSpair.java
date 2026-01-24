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

import imgui.ImGuiPlatformIO;
import imgui.ImGuiViewport;
import imgui.ImVec2;
import imgui.callback.*;
import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.struct.ref.KReference;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImGuiPlatformIo;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImGuiPlatformMonitor;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImGuiViewport;
import io.github.darthakiranihil.konna.libfrontend.imgui.func.*;

final class KImGuiPlatformIoSpair implements KImGuiPlatformIo {

    private static final class PlatformFuncViewportSpair extends ImPlatformFuncViewport {

        private final KImPlatformFuncViewport func;

        public PlatformFuncViewportSpair(final KImPlatformFuncViewport func) {
            this.func = func;
        }

        @Override
        public void accept(final ImGuiViewport vp) {
            this.func.accept(KImGuiSpairUnwrapper.wrap(vp));
        }
    }

    private static final class PlatformFuncViewportStringSpair extends ImPlatformFuncViewportString {

        private final KImPlatformFuncViewportString func;

        public PlatformFuncViewportStringSpair(final KImPlatformFuncViewportString func) {
            this.func = func;
        }

        @Override
        public void accept(final ImGuiViewport vp, final String str) {
            this.func.accept(KImGuiSpairUnwrapper.wrap(vp), str);
        }
    }

    private static final class PlatformFuncViewportVec2Spair extends ImPlatformFuncViewportImVec2 {

        private final KImPlatformFuncViewportImVec2 func;

        public PlatformFuncViewportVec2Spair(final KImPlatformFuncViewportImVec2 func) {
            this.func = func;
        }

        @Override
        public void accept(final ImGuiViewport vp, final ImVec2 imVec2) {
            this.func.accept(KImGuiSpairUnwrapper.wrap(vp), KImGuiSpairUnwrapper.wrap(imVec2));
        }
    }

    private static final class PlatformFuncViewportFloatSpair extends ImPlatformFuncViewportFloat {

        private final KImPlatformFuncViewportFloat func;

        public PlatformFuncViewportFloatSpair(final KImPlatformFuncViewportFloat func) {
            this.func = func;
        }

        @Override
        public void accept(final ImGuiViewport vp, float f) {
            this.func.accept(KImGuiSpairUnwrapper.wrap(vp), f);
        }
    }

    private static final class PlatformFuncViewportSuppFloat extends ImPlatformFuncViewportSuppFloat {

        private final KImPlatformFuncViewportSuppFloat func;

        public PlatformFuncViewportSuppFloat(final KImPlatformFuncViewportSuppFloat func) {
            this.func = func;
        }

        @Override
        public float get(final ImGuiViewport vp) {
            return this.func.get(KImGuiSpairUnwrapper.wrap(vp));
        }
    }

    private static final class PlatformFuncViewportSuppVec2 extends ImPlatformFuncViewportSuppImVec2 {

        private final KImPlatformFuncViewportSuppImVec2 func;

        public PlatformFuncViewportSuppVec2(final KImPlatformFuncViewportSuppImVec2 func) {
            this.func = func;
        }

        @Override
        public void get(final ImGuiViewport vp, final ImVec2 dstImVec2) {
            KReference<KVector2f> v = new KReference<>();
            this.func.get(KImGuiSpairUnwrapper.wrap(vp), v);
            KVector2f res = v.get();
            if (res != null) {
                dstImVec2.set(res.x(), res.y());
            }
        }
    }

    private static final class PlatformFuncViewportSuppBoolean extends ImPlatformFuncViewportSuppBoolean {

        private final KImPlatformFuncViewportSuppBoolean func;

        public PlatformFuncViewportSuppBoolean(final KImPlatformFuncViewportSuppBoolean func) {
            this.func = func;
        }

        @Override
        public boolean get(final ImGuiViewport vp) {
            return this.func.get(KImGuiSpairUnwrapper.wrap(vp));
        }
    }

    private final ImGuiPlatformIO boxed;

    public KImGuiPlatformIoSpair(final ImGuiPlatformIO boxed) {
        this.boxed = boxed;
    }

    @Override
    public void setPlatformCreateWindow(KImPlatformFuncViewport func) {
        this.boxed.setPlatformCreateWindow(new PlatformFuncViewportSpair(func));
    }

    @Override
    public void setPlatformDestroyWindow(KImPlatformFuncViewport func) {
        this.boxed.setPlatformDestroyWindow(new PlatformFuncViewportSpair(func));
    }

    @Override
    public void setPlatformShowWindow(KImPlatformFuncViewport func) {
        this.boxed.setPlatformShowWindow(new PlatformFuncViewportSpair(func));
    }

    @Override
    public void setPlatformSetWindowPos(KImPlatformFuncViewportImVec2 func) {
        this.boxed.setPlatformSetWindowPos(new PlatformFuncViewportVec2Spair(func));
    }

    @Override
    public void setPlatformGetWindowPos(KImPlatformFuncViewportSuppImVec2 func) {
        this.boxed.setPlatformGetWindowPos(new PlatformFuncViewportSuppVec2(func));
    }

    @Override
    public void setPlatformSetWindowSize(KImPlatformFuncViewportImVec2 func) {
        this.boxed.setPlatformSetWindowSize(new PlatformFuncViewportVec2Spair(func));
    }

    @Override
    public void setPlatformGetWindowSize(KImPlatformFuncViewportSuppImVec2 func) {
        this.boxed.setPlatformGetWindowSize(new PlatformFuncViewportSuppVec2(func));
    }

    @Override
    public void setPlatformSetWindowFocus(KImPlatformFuncViewport func) {
        this.boxed.setPlatformSetWindowFocus(new PlatformFuncViewportSpair(func));
    }

    @Override
    public void setPlatformGetWindowFocus(KImPlatformFuncViewportSuppBoolean func) {
        this.boxed.setPlatformGetWindowFocus(new PlatformFuncViewportSuppBoolean(func));
    }

    @Override
    public void setPlatformGetWindowMinimized(KImPlatformFuncViewportSuppBoolean func) {
        this.boxed.setPlatformGetWindowMinimized(new PlatformFuncViewportSuppBoolean(func));
    }

    @Override
    public void setPlatformSetWindowTitle(KImPlatformFuncViewportString func) {
        this.boxed.setPlatformSetWindowTitle(new PlatformFuncViewportStringSpair(func));
    }

    @Override
    public void setPlatformSetWindowAlpha(KImPlatformFuncViewportFloat func) {
        this.boxed.setPlatformSetWindowAlpha(new PlatformFuncViewportFloatSpair(func));
    }

    @Override
    public void setPlatformUpdateWindow(KImPlatformFuncViewport func) {
        this.boxed.setPlatformUpdateWindow(new PlatformFuncViewportSpair(func));
    }

    @Override
    public void setPlatformRenderWindow(KImPlatformFuncViewport func) {
        this.boxed.setPlatformRenderWindow(new PlatformFuncViewportSpair(func));
    }

    @Override
    public void setPlatformSwapBuffers(KImPlatformFuncViewport func) {
        this.boxed.setPlatformSwapBuffers(new PlatformFuncViewportSpair(func));
    }

    @Override
    public void setPlatformGetWindowDpiScale(KImPlatformFuncViewportSuppFloat func) {
        this.boxed.setPlatformGetWindowDpiScale(new PlatformFuncViewportSuppFloat(func));
    }

    @Override
    public void setPlatformOnChangedViewport(KImPlatformFuncViewport func) {
        this.boxed.setPlatformOnChangedViewport(new PlatformFuncViewportSpair(func));
    }

    @Override
    public void setRendererCreateWindow(KImPlatformFuncViewport func) {
        this.boxed.setRendererCreateWindow(new PlatformFuncViewportSpair(func));
    }

    @Override
    public void setRendererDestroyWindow(KImPlatformFuncViewport func) {
        this.boxed.setRendererDestroyWindow(new PlatformFuncViewportSpair(func));
    }

    @Override
    public void setRendererSetWindowPos(KImPlatformFuncViewportImVec2 func) {
        this.boxed.setRendererSetWindowPos(new PlatformFuncViewportVec2Spair(func));
    }

    @Override
    public void setRendererRenderWindow(KImPlatformFuncViewport func) {
        this.boxed.setRendererRenderWindow(new PlatformFuncViewportSpair(func));
    }

    @Override
    public void setRendererSwapBuffers(KImPlatformFuncViewport func) {
        this.boxed.setRendererSwapBuffers(new PlatformFuncViewportSpair(func));
    }

    @Override
    public void resizeMonitors(int size) {
        this.boxed.resizeMonitors(size);
    }

    @Override
    public int getMonitorsSize() {
        return this.boxed.getMonitorsSize();
    }

    @Override
    public void pushMonitors(
        long platformHandle,
        float mainPosX,
        float mainPosY,
        float mainSizeX,
        float mainSizeY,
        float workPosX,
        float workPosY,
        float workSizeX,
        float workSizeY,
        float dpiScale
    ) {
        this
            .boxed
            .pushMonitors(
                platformHandle,
                mainPosX,
                mainPosY,
                mainSizeX,
                mainSizeY,
                workPosX,
                workPosY,
                workSizeX,
                workSizeY,
                dpiScale
            );
    }

    @Override
    public KImGuiPlatformMonitor getMonitors(int idx) {
        return null;
    }

    @Override
    public KImGuiViewport getViewports(int idx) {
        return null;
    }
}
