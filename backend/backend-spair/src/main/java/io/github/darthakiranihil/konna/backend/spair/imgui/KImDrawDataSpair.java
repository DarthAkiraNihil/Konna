/*
 * Copyright 2025-present the this.boxed author or authors.
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

import imgui.ImDrawData;
import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.struct.KVector4f;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImDrawData;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImDrawList;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImGuiViewport;

import java.nio.ByteBuffer;

final class KImDrawDataSpair implements KImDrawData {
    
    private final ImDrawData boxed;

    KImDrawDataSpair(final ImDrawData boxed) {
        this.boxed = boxed;
    }

    @Override
    public int getCmdListCmdBufferSize(int cmdListIdx) {
        return this.boxed.getCmdListCmdBufferSize(cmdListIdx);
    }

    @Override
    public int getCmdListCmdBufferElemCount(int cmdListIdx, int cmdBufferIdx) {
        return this.boxed.getCmdListCmdBufferElemCount(cmdListIdx, cmdBufferIdx);
    }

    @Override
    public KVector4f getCmdListCmdBufferClipRect(int cmdListIdx, int cmdBufferIdx) {
        return KImGuiSpairUnwrapper.wrap(
            this.boxed.getCmdListCmdBufferClipRect(cmdListIdx, cmdBufferIdx)
        );
    }

    @Override
    public void getCmdListCmdBufferClipRect(final KVector4f dst, int cmdListIdx, int cmdBufferIdx) {
        this.boxed.getCmdListCmdBufferClipRect(
            KImGuiSpairWrapper.wrap(dst),
            cmdListIdx,
            cmdBufferIdx
        );
    }

    @Override
    public long getCmdListCmdBufferTextureId(int cmdListIdx, int cmdBufferIdx) {
        return this.boxed.getCmdListCmdBufferTextureId(cmdListIdx, cmdBufferIdx);
    }

    @Override
    public int getCmdListCmdBufferVtxOffset(int cmdListIdx, int cmdBufferIdx) {
        return this.boxed.getCmdListCmdBufferVtxOffset(cmdListIdx, cmdBufferIdx);
    }

    @Override
    public int getCmdListCmdBufferIdxOffset(int cmdListIdx, int cmdBufferIdx) {
        return this.boxed.getCmdListCmdBufferIdxOffset(cmdListIdx, cmdBufferIdx);
    }

    @Override
    public int getCmdListIdxBufferSize(int cmdListIdx) {
        return this.boxed.getCmdListIdxBufferSize(cmdListIdx);
    }

    @Override
    public ByteBuffer getCmdListIdxBufferData(int cmdListIdx) {
        return this.boxed.getCmdListIdxBufferData(cmdListIdx);
    }

    @Override
    public int getCmdListVtxBufferSize(int cmdListIdx) {
        return this.boxed.getCmdListVtxBufferSize(cmdListIdx);
    }

    @Override
    public ByteBuffer getCmdListVtxBufferData(int cmdListIdx) {
        return this.boxed.getCmdListVtxBufferData(cmdListIdx);
    }

    @Override
    public int getCmdListsCount() {
        return this.boxed.getCmdListsCount();
    }

    @Override
    public int getTotalIdxCount() {
        return this.boxed.getTotalIdxCount();
    }

    @Override
    public int getTotalVtxCount() {
        return this.boxed.getTotalVtxCount();
    }

    @Override
    public KVector2f getDisplayPos() {
        return KImGuiSpairUnwrapper.wrap(this.boxed.getDisplayPos());
    }

    @Override
    public KVector2f getDisplaySize() {
        return KImGuiSpairUnwrapper.wrap(this.boxed.getDisplaySize());
    }

    @Override
    public KVector2f getFramebufferScale() {
        return KImGuiSpairUnwrapper.wrap(this.boxed.getFramebufferScale());
    }

    @Override
    public KImGuiViewport getOwnerViewport() {
        return KImGuiSpairUnwrapper.wrap(this.boxed.getOwnerViewport());
    }

    @Override
    public void clear() {
        this.boxed.clear();
    }
    @Override
    public void addDrawList(final KImDrawList drawList) {
        this.boxed.addDrawList(KImGuiSpairUnboxer.unbox(drawList));
    }

    @Override
    public void deIndexAllBuffers() {
        this.boxed.deIndexAllBuffers();
    }

    @Override
    public void scaleClipRects(final KVector2f fbScale) {
        this.boxed.scaleClipRects(KImGuiSpairWrapper.wrap(fbScale));
    }
    
    
}
