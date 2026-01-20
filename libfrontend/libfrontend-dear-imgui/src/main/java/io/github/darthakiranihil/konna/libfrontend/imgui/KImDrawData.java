package io.github.darthakiranihil.konna.libfrontend.imgui;

import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.struct.KVector4f;

import java.nio.ByteBuffer;

public interface KImDrawData {

    int getCmdListCmdBufferSize(int cmdListIdx);
    int getCmdListCmdBufferElemCount(int cmdListIdx, int cmdBufferIdx);
    KVector4f getCmdListCmdBufferClipRect(final int cmdListIdx, final int cmdBufferIdx);
    void getCmdListCmdBufferClipRect(KVector4f dst, int cmdListIdx, int cmdBufferIdx);
    long getCmdListCmdBufferTextureId(int cmdListIdx, int cmdBufferIdx);
    int getCmdListCmdBufferVtxOffset(int cmdListIdx, int cmdBufferIdx);
    int getCmdListCmdBufferIdxOffset(int cmdListIdx, int cmdBufferIdx);
    int getCmdListIdxBufferSize(int cmdListIdx);

    ByteBuffer getCmdListIdxBufferData(final int cmdListIdx);
    int getCmdListVtxBufferSize(int cmdListIdx);

    ByteBuffer getCmdListVtxBufferData(final int cmdListIdx);
    int sizeOfImDrawVert();
    int sizeOfImDrawIdx();
    
    boolean isValid();
    int getCmdListsCount();
    int getTotalIdxCount();
    int getTotalVtxCount();
    KVector2f getDisplayPos();
    KVector2f getDisplaySize();
    KVector2f getFramebufferScale();
    KImGuiViewport getOwnerViewport();
    
    void clear();
    void addDrawList(KImDrawList drawList);
    void deIndexAllBuffers();
    void scaleClipRects(KVector2f fbScale);

}
