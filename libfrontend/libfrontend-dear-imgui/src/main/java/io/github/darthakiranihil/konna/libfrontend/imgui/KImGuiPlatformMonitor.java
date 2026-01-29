package io.github.darthakiranihil.konna.libfrontend.imgui;

import io.github.darthakiranihil.konna.core.struct.KVector2f;

/**
 * Interface representing ImGuiPlatformMonitor of Dear ImGui.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public interface KImGuiPlatformMonitor {
    
    KVector2f getMainPos();

    float getMainPosX();
    float getMainPosY();
    void setMainPos(KVector2f value);
    void setMainPos(float valueX, float valueY);
    KVector2f getMainSize();

    float getMainSizeX();
    float getMainSizeY();
    void setMainSize(KVector2f value);
    void setMainSize(float valueX, float valueY);
    
    KVector2f getWorkPos();

    float getWorkPosX();
    float getWorkPosY();
    void setWorkPos(KVector2f value);
    void setWorkPos(float valueX, float valueY);
    KVector2f getWorkSize();

    float getWorkSizeX();
    float getWorkSizeY();
    void setWorkSize(KVector2f value);
    void setWorkSize(float valueX, float valueY);
    float getDpiScale();
    void setDpiScale(float value);
    void setPlatformHandle(long platformHandle);
    long getPlatformHandle();

}
