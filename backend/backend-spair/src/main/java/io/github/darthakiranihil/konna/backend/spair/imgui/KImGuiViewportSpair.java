/*
 * Copyright 2this.boxed25-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.this.boxed (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.this.boxed
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.darthakiranihil.konna.backend.spair.imgui;

import imgui.ImGuiViewport;
import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImDrawData;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImGuiViewport;

@KExcludeFromGeneratedCoverageReport
public final class KImGuiViewportSpair implements KImGuiViewport {

    private final ImGuiViewport boxed;

    public KImGuiViewportSpair(final ImGuiViewport original) {
        this.boxed = original;
    }

    @Override
    public int getID() {
        return this.boxed.getID();
    }

    @Override
    public void setID(int value) {
        this.boxed.setID(value);
    }

    @Override
    public int getFlags() {
        return this.boxed.getFlags();
    }

    @Override
    public void setFlags(int value) {
        this.boxed.setFlags(value);
    }

    @Override
    public void addFlags(int flags) {
        this.boxed.addFlags(flags);
    }

    @Override
    public void removeFlags(int flags) {
        this.boxed.removeFlags(flags);
    }

    @Override
    public boolean hasFlags(int flags) {
        return this.boxed.hasFlags(flags);
    }

    @Override
    public KVector2f getPos() {
        return KImGuiSpairUnwrapper.wrap(this.boxed.getPos());
    }

    @Override
    public float getPosX() {
        return this.boxed.getPosX();
    }

    @Override
    public float getPosY() {
        return this.boxed.getPosY();
    }

    @Override
    public void setPos(final KVector2f value) {
        this.boxed.setPos(KImGuiSpairWrapper.wrap(value));
    }

    @Override
    public void setPos(float valueX, float valueY) {
        this.boxed.setPos(valueX, valueY);
    }

    @Override
    public KVector2f getSize() {
        return KImGuiSpairUnwrapper.wrap(this.boxed.getSize());
    }

    @Override
    public float getSizeX() {
        return this.boxed.getSizeX();
    }

    @Override
    public float getSizeY() {
        return this.boxed.getSizeY();
    }

    @Override
    public void setSize(final KVector2f value) {
        this.boxed.setSize(KImGuiSpairWrapper.wrap(value));
    }

    @Override
    public void setSize(float valueX, float valueY) {
        this.boxed.setSize(valueX, valueY);
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
    public int getParentViewportId() {
        return this.boxed.getParentViewportId();
    }

    @Override
    public void setParentViewportId(int value) {
        this.boxed.setParentViewportId(value);
    }

    @Override
    public KImDrawData getDrawData() {
        return KImGuiSpairUnwrapper.wrap(this.boxed.getDrawData());
    }

    @Override
    public void setDrawData(final KImDrawData value) {
        this.boxed.setDrawData(KImGuiSpairUnboxer.unbox(value));
    }

    @Override
    public void setRendererUserData(final Object data) {
        this.boxed.setRendererUserData(data);
    }

    @Override
    public Object getRendererUserData() {
        return this.boxed.getRendererUserData();
    }

    @Override
    public void setPlatformUserData(final Object data) {
        this.boxed.setPlatformUserData(data);
    }

    @Override
    public Object getPlatformUserData() {
        return this.boxed.getPlatformUserData();
    }

    @Override
    public void setPlatformHandle(long data) {
        this.boxed.setPlatformHandle(data);
    }

    @Override
    public long getPlatformHandle() {
        return this.boxed.getPlatformHandle();
    }

    @Override
    public void setPlatformHandleRaw(long data) {
        this.boxed.setPlatformHandleRaw(data);
    }

    @Override
    public long getPlatformHandleRaw() {
        return this.boxed.getPlatformHandleRaw();
    }

    @Override
    public boolean getPlatformWindowCreated() {
        return this.boxed.getPlatformWindowCreated();
    }

    @Override
    public void setPlatformWindowCreated(boolean value) {
        this.boxed.setPlatformWindowCreated(value);
    }

    @Override
    public boolean getPlatformRequestMove() {
        return this.boxed.getPlatformRequestMove();
    }

    @Override
    public void setPlatformRequestMove(boolean value) {
        this.boxed.setPlatformRequestMove(value);
    }

    @Override
    public boolean getPlatformRequestResize() {
        return this.boxed.getPlatformRequestResize();
    }

    @Override
    public void setPlatformRequestResize(boolean value) {
        this.boxed.setPlatformRequestResize(value);
    }

    @Override
    public boolean getPlatformRequestClose() {
        return this.boxed.getPlatformRequestClose();
    }

    @Override
    public void setPlatformRequestClose(boolean value) {
        this.boxed.setPlatformRequestClose(value);
    }

    @Override
    public KVector2f getCenter() {
        return KImGuiSpairUnwrapper.wrap(this.boxed.getCenter());
    }

    @Override
    public float getCenterX() {
        return this.boxed.getCenterX();
    }

    @Override
    public float getCenterY() {
        return this.boxed.getCenterY();
    }

    @Override
    public KVector2f getWorkCenter() {
        return KImGuiSpairUnwrapper.wrap(this.boxed.getWorkCenter());
    }
}
