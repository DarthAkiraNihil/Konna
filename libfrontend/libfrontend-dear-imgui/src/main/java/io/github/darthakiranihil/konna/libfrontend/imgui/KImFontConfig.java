package io.github.darthakiranihil.konna.libfrontend.imgui;

import io.github.darthakiranihil.konna.core.struct.KVector2f;

public interface KImFontConfig {
    
    byte[] getFontData();
    void setFontData(byte[] fontData);
    int getFontDataSize();
    void setFontDataSize(final int value);
    boolean getFontDataOwnedByAtlas();
    void setFontDataOwnedByAtlas(final boolean value);
    int getFontNo();
    void setFontNo(final int value);
    float getSizePixels();
    void setSizePixels(final float value);
    int getOversampleH();
    void setOversampleH(final int value);
    int getOversampleV();
    void setOversampleV(final int value);
    boolean getPixelSnapH();
    void setPixelSnapH(final boolean value);
    
    KVector2f getGlyphExtraSpacing();

    float getGlyphExtraSpacingX();
    float getGlyphExtraSpacingY();
    void getGlyphExtraSpacing(final KVector2f dst);
    void setGlyphExtraSpacing(final KVector2f value);

    void setGlyphExtraSpacing(final float valueX, final float valueY);
    KVector2f getGlyphOffset();

    float getGlyphOffsetX();
    float getGlyphOffsetY();
    void getGlyphOffset(final KVector2f dst);
    void setGlyphOffset(final KVector2f value);
    void setGlyphOffset(final float valueX, final float valueY);
    short[] getGlyphRanges();
    void setGlyphRanges(final short[] glyphRanges);
    float getGlyphMinAdvanceX();
    void setGlyphMinAdvanceX(final float value);
    float getGlyphMaxAdvanceX();
    void setGlyphMaxAdvanceX(final float value);
    boolean getMergeMode();
    void setMergeMode(final boolean value);
    int getFontBuilderFlags();
    void setFontBuilderFlags(final int value);
    void addFontBuilderFlags(final int flags);
    void removeFontBuilderFlags(final int flags);
    boolean hasFontBuilderFlags(final int flags);
    float getRasterizerMultiply();
    void setRasterizerMultiply(final float value);
    float getRasterizerDensity();
    void setRasterizerDensity(final float value);
    short getEllipsisChar();
    void setEllipsisChar(final short value);
    void setName(String name);
    KImFont getDstFont();
    void setDstFont(final KImFont value);

}
