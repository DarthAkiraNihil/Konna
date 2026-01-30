package io.github.darthakiranihil.konna.libfrontend.imgui;

import io.github.darthakiranihil.konna.core.struct.KDestroyable;
import io.github.darthakiranihil.konna.core.struct.KVector2f;

/**
 * Interface representing ImFontConfig of Dear ImGui.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
public interface KImFontConfig extends KDestroyable {
    
    byte[] getFontData();
    void setFontData(byte[] fontData);
    int getFontDataSize();
    void setFontDataSize(int value);
    boolean getFontDataOwnedByAtlas();
    void setFontDataOwnedByAtlas(boolean value);
    int getFontNo();
    void setFontNo(int value);
    float getSizePixels();
    void setSizePixels(float value);
    int getOversampleH();
    void setOversampleH(int value);
    int getOversampleV();
    void setOversampleV(int value);
    boolean getPixelSnapH();
    void setPixelSnapH(boolean value);
    
    KVector2f getGlyphExtraSpacing();

    float getGlyphExtraSpacingX();
    float getGlyphExtraSpacingY();
    // void getGlyphExtraSpacing(KVector2f dst);
    void setGlyphExtraSpacing(KVector2f value);

    void setGlyphExtraSpacing(float valueX, float valueY);
    KVector2f getGlyphOffset();

    float getGlyphOffsetX();
    float getGlyphOffsetY();
    // void getGlyphOffset(KVector2f dst);
    void setGlyphOffset(KVector2f value);
    void setGlyphOffset(float valueX, float valueY);
    short[] getGlyphRanges();
    void setGlyphRanges(short[] glyphRanges);
    float getGlyphMinAdvanceX();
    void setGlyphMinAdvanceX(float value);
    float getGlyphMaxAdvanceX();
    void setGlyphMaxAdvanceX(float value);
    boolean getMergeMode();
    void setMergeMode(boolean value);
    int getFontBuilderFlags();
    void setFontBuilderFlags(int value);
    void addFontBuilderFlags(int flags);
    void removeFontBuilderFlags(int flags);
    boolean hasFontBuilderFlags(int flags);
    float getRasterizerMultiply();
    void setRasterizerMultiply(float value);
    float getRasterizerDensity();
    void setRasterizerDensity(float value);
    short getEllipsisChar();
    void setEllipsisChar(short value);
    void setName(String name);
    KImFont getDstFont();
    void setDstFont(KImFont value);

}
