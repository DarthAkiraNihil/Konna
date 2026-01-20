package io.github.darthakiranihil.konna.libfrontend.imgui;

import io.github.darthakiranihil.konna.core.struct.KVector2f;

public interface KImGuiPlatformMonitor {
    
    KVector2f getMainPos();

    float getMainPosX();
    float getMainPosY();
    void getMainPos(final KVector2f dst);
    void setMainPos(final KVector2f value);
    void setMainPos(final float valueX, final float valueY);
    KVector2f getMainSize();

    float getMainSizeX();
    float getMainSizeY();
    void getMainSize(final KVector2f dst);
    void setMainSize(final KVector2f value);
    void setMainSize(final float valueX, final float valueY);
    
    KVector2f getWorkPos();

    float getWorkPosX();
    float getWorkPosY();
    void getWorkPos(final KVector2f dst);
    void setWorkPos(final KVector2f value);
    void setWorkPos(final float valueX, final float valueY);
    KVector2f getWorkSize();

    float getWorkSizeX();
    float getWorkSizeY();
    void getWorkSize(final KVector2f dst);
    void setWorkSize(final KVector2f value);
    void setWorkSize(final float valueX, final float valueY);
    float getDpiScale();
    void setDpiScale(final float value);
    void setPlatformHandle(long platformHandle);
    long getPlatformHandle();

}
