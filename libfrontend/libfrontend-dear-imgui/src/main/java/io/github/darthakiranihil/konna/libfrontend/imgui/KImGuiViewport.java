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

import io.github.darthakiranihil.konna.core.struct.KVector2f;

/**
 * Interface representing ImGuiViewport of Dear ImGui.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
public interface KImGuiViewport {
    
    int getID();
    void setID(int value);

    int getFlags();
    void setFlags(int value);
    void addFlags(int flags);
    void removeFlags(int flags);
    boolean hasFlags(int flags);

    KVector2f getPos();
    float getPosX();
    float getPosY();
    void setPos(KVector2f value);
    void setPos(float valueX, float valueY);

    KVector2f getSize();
    float getSizeX();
    float getSizeY();
    void setSize(KVector2f value);
    void setSize(float valueX, float valueY);

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

    int getParentViewportId();
    void setParentViewportId(int value);

    KImDrawData getDrawData();
    void setDrawData(KImDrawData value);

    void setRendererUserData(Object data);
    Object getRendererUserData();

    void setPlatformUserData(Object data);
    Object getPlatformUserData();

    void setPlatformHandle(long data);
    long getPlatformHandle();
    void setPlatformHandleRaw(long data);
    long getPlatformHandleRaw();

    boolean getPlatformWindowCreated();
    void setPlatformWindowCreated(boolean value);

    boolean getPlatformRequestMove();
    void setPlatformRequestMove(boolean value);

    boolean getPlatformRequestResize();
    void setPlatformRequestResize(boolean value);

    boolean getPlatformRequestClose();
    void setPlatformRequestClose(boolean value);

    KVector2f getCenter();
    float getCenterX();
    float getCenterY();

    KVector2f getWorkCenter();
}
