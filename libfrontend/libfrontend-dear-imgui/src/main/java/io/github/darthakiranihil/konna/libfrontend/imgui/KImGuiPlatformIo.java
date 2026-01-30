package io.github.darthakiranihil.konna.libfrontend.imgui;

import io.github.darthakiranihil.konna.libfrontend.imgui.func.*;

/**
 * Interface representing ImGuiPlatformIO of Dear ImGui.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
public interface KImGuiPlatformIo {


    void setPlatformCreateWindow(KImPlatformFuncViewport func);
    void setPlatformDestroyWindow(KImPlatformFuncViewport func);
    void setPlatformShowWindow(KImPlatformFuncViewport func);
    void setPlatformSetWindowPos(KImPlatformFuncViewportImVec2 func);
    void setPlatformGetWindowPos(KImPlatformFuncViewportSuppImVec2 func);
    void setPlatformSetWindowSize(KImPlatformFuncViewportImVec2 func);
    void setPlatformGetWindowSize(KImPlatformFuncViewportSuppImVec2 func);
    void setPlatformSetWindowFocus(KImPlatformFuncViewport func);
    void setPlatformGetWindowFocus(KImPlatformFuncViewportSuppBoolean func);
    void setPlatformGetWindowMinimized(KImPlatformFuncViewportSuppBoolean func);
    void setPlatformSetWindowTitle(KImPlatformFuncViewportString func);
    void setPlatformSetWindowAlpha(KImPlatformFuncViewportFloat func);
    void setPlatformUpdateWindow(KImPlatformFuncViewport func);
    void setPlatformRenderWindow(KImPlatformFuncViewport func);
    void setPlatformSwapBuffers(KImPlatformFuncViewport func);
    void setPlatformGetWindowDpiScale(KImPlatformFuncViewportSuppFloat func);
    void setPlatformOnChangedViewport(KImPlatformFuncViewport func);
    void setRendererCreateWindow(KImPlatformFuncViewport func);
    void setRendererDestroyWindow(KImPlatformFuncViewport func);
    void setRendererSetWindowPos(KImPlatformFuncViewportImVec2 func);
    void setRendererRenderWindow(KImPlatformFuncViewport func);
    void setRendererSwapBuffers(KImPlatformFuncViewport func);
    void resizeMonitors(int size);
    int getMonitorsSize();

    void pushMonitors(
        long platformHandle,
        float mainPosX, float mainPosY,
        float mainSizeX, float mainSizeY,
        float workPosX, float workPosY,
        float workSizeX, float workSizeY,
        float dpiScale
    );

    KImGuiPlatformMonitor getMonitors(int idx);
    KImGuiViewport getViewports(int idx);

}
