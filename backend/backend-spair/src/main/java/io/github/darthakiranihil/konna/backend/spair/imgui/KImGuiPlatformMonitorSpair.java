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

import imgui.ImGuiPlatformMonitor;
import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImGuiPlatformMonitor;

final class KImGuiPlatformMonitorSpair implements KImGuiPlatformMonitor {

    private final ImGuiPlatformMonitor boxed;

    KImGuiPlatformMonitorSpair(final ImGuiPlatformMonitor boxed) {
        this.boxed = boxed;
    }

    @Override
    public KVector2f getMainPos() {
        return KImGuiSpairUnwrapper.wrap(this.boxed.getMainPos());
    }

    @Override
    public float getMainPosX() {
        return this.boxed.getMainPosX();
    }

    @Override
    public float getMainPosY() {
        return this.boxed.getMainPosY();
    }

    @Override
    public void setMainPos(final KVector2f value) {
        this.boxed.setMainPos(KImGuiSpairWrapper.wrap(value));
    }

    @Override
    public void setMainPos(float valueX, float valueY) {
        this.boxed.setMainPos(valueX, valueY);
    }

    @Override
    public KVector2f getMainSize() {
        return KImGuiSpairUnwrapper.wrap(this.boxed.getMainSize());
    }

    @Override
    public float getMainSizeX() {
        return this.boxed.getMainSizeX();
    }

    @Override
    public float getMainSizeY() {
        return this.boxed.getMainSizeY();
    }

    @Override
    public void setMainSize(final KVector2f value) {
        this.boxed.setMainSize(KImGuiSpairWrapper.wrap(value));
    }

    @Override
    public void setMainSize(float valueX, float valueY) {
        this.boxed.setMainSize(valueX, valueY);
    }

    @Override
    public KVector2f getWorkPos() {
        return KImGuiSpairUnwrapper.wrap(this.boxed.getWorkPos());
    }

    @Override
    public float getWorkPosX() {
        return this.boxed.getWorkPosX();
    }

    @Override
    public float getWorkPosY() {
        return this.boxed.getWorkPosY();
    }

    @Override
    public void setWorkPos(final KVector2f value) {
        this.boxed.setWorkPos(KImGuiSpairWrapper.wrap(value));
    }

    @Override
    public void setWorkPos(float valueX, float valueY) {
        this.boxed.setWorkPos(valueX, valueY);
    }

    @Override
    public KVector2f getWorkSize() {
        return KImGuiSpairUnwrapper.wrap(this.boxed.getWorkSize());
    }

    @Override
    public float getWorkSizeX() {
        return this.boxed.getWorkSizeX();
    }

    @Override
    public float getWorkSizeY() {
        return this.boxed.getWorkSizeY();
    }

    @Override
    public void setWorkSize(final KVector2f value) {
        this.boxed.setWorkSize(KImGuiSpairWrapper.wrap(value));
    }

    @Override
    public void setWorkSize(float valueX, float valueY) {
        this.boxed.setWorkSize(valueX, valueY);
    }

    @Override
    public float getDpiScale() {
        return this.boxed.getDpiScale();
    }

    @Override
    public void setDpiScale(float value) {
        this.boxed.setDpiScale(value);
    }

    @Override
    public void setPlatformHandle(long platformHandle) {
        this.boxed.setPlatformHandle(platformHandle);
    }

    @Override
    public long getPlatformHandle() {
        return this.boxed.getPlatformHandle();
    }

}
