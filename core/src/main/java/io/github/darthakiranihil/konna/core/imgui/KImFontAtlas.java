package io.github.darthakiranihil.konna.core.imgui;

import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.struct.ref.KIntReferenceValue;

import java.nio.ByteBuffer;

public interface KImFontAtlas {
    
    KImFont addFontDefault();

    KImFont addFontDefault(final KImFontConfig KImFontConfig);
    KImFont addFontFromFileTTF(final String filename, final float sizePixels);
    KImFont addFontFromFileTTF(final String filename, final float sizePixels, final KImFontConfig fontConfig);
    KImFont addFontFromFileTTF(final String filename, final float sizePixels, final KImFontConfig fontConfig, final short[] glyphRanges);
    KImFont addFontFromFileTTF(final String filename, final float sizePixels, final short[] glyphRanges);
    KImFont addFontFromMemoryTTF(final byte[] fontData, final float sizePixels);
    KImFont addFontFromMemoryTTF(final byte[] fontData, final float sizePixels, final KImFontConfig fontConfig);
    KImFont addFontFromMemoryTTF(final byte[] fontData, final float sizePixels, final KImFontConfig fontConfig, final short[] glyphRanges);
    KImFont addFontFromMemoryTTF(final byte[] fontData, final float sizePixels, final short[] glyphRanges);
    KImFont addFontFromMemoryTTF(final byte[] fontData, final int fontDataSize, final float sizePixels);
    KImFont addFontFromMemoryTTF(final byte[] fontData, final int fontDataSize, final float sizePixels, final KImFontConfig fontConfig);
    KImFont addFontFromMemoryTTF(final byte[] fontData, final int fontDataSize, final float sizePixels, final KImFontConfig fontConfig, final short[] glyphRanges);
    KImFont addFontFromMemoryTTF(final byte[] fontData, final int fontDataSize, final float sizePixels, final short[] glyphRanges);
    KImFont addFontFromMemoryCompressedTTF(final byte[] compressedFontData, final float sizePixels);
    KImFont addFontFromMemoryCompressedTTF(final byte[] compressedFontData, final float sizePixels, final KImFontConfig KImFontConfig);
    KImFont addFontFromMemoryCompressedTTF(final byte[] compressedFontData, final float sizePixels, final KImFontConfig KImFontConfig, final short[] glyphRanges);
    KImFont addFontFromMemoryCompressedTTF(final byte[] compressedFontData, final float sizePixels, final short[] glyphRanges);
    KImFont addFontFromMemoryCompressedTTF(final byte[] compressedFontData, final int compressedFontDataSize, final float sizePixels);
    KImFont addFontFromMemoryCompressedTTF(final byte[] compressedFontData, final int compressedFontDataSize, final float sizePixels, final KImFontConfig KImFontConfig);
    KImFont addFontFromMemoryCompressedTTF(final byte[] compressedFontData, final int compressedFontDataSize, final float sizePixels, final KImFontConfig KImFontConfig, final short[] glyphRanges);
    KImFont addFontFromMemoryCompressedTTF(final byte[] compressedFontData, final int compressedFontDataSize, final float sizePixels, final short[] glyphRanges);
    KImFont addFontFromMemoryCompressedBase85TTF(final String compressedFontDataBase85, final float sizePixels, final KImFontConfig fontConfig);
    KImFont addFontFromMemoryCompressedBase85TTF(final String compressedFontDataBase85, final float sizePixels, final KImFontConfig fontConfig, final short[] glyphRanges);
    void clearInputData();
    void clearTexData();
    void clearFonts();
    void clear();
    void setFreeTypeRenderer(boolean enabled);

    boolean build();
    ByteBuffer getTexDataAsAlpha8(final KIntReferenceValue outWidth, final KIntReferenceValue outHeight);
    ByteBuffer getTexDataAsAlpha8(final KIntReferenceValue outWidth, final KIntReferenceValue outHeight, final KIntReferenceValue outBytesPerPixel);
    ByteBuffer getTexDataAsRGBA32(final KIntReferenceValue outWidth, final KIntReferenceValue outHeight);
    ByteBuffer getTexDataAsRGBA32(final KIntReferenceValue outWidth, final KIntReferenceValue outHeight, final KIntReferenceValue outBytesPerPixel);
    boolean isBuilt();

    void setTexID(final long textureID);
    short[] getGlyphRangesDefault();
    short[] getGlyphRangesGreek();
    short[] getGlyphRangesKorean();
    short[] getGlyphRangesJapanese();
    short[] getGlyphRangesChineseFull();
    short[] getGlyphRangesChineseSimplifiedCommon();
    short[] getGlyphRangesCyrillic();
    short[] getGlyphRangesThai();
    short[] getGlyphRangesVietnamese();

    int addCustomRectRegular(final int width, final int height);

    int addCustomRectFontGlyph(final KImFont KImFont, final short id, final int width, final int height, final float advanceX);

    int addCustomRectFontGlyph(final KImFont KImFont, final short id, final int width, final int height, final float advanceX, final KVector2f offset);
    int addCustomRectFontGlyph(final KImFont KImFont, final short id, final int width, final int height, final float advanceX, final float offsetX, final float offsetY);

    int getFlags();
    void setFlags(final int value);
    void addFlags(final int flags);
    void removeFlags(final int flags);
    boolean hasFlags(final int flags);

    int getTexDesiredWidth();
    void setTexDesiredWidth(final int value);

    int getTexGlyphPadding();
    void setTexGlyphPadding(final int value);
    boolean getLocked();
    void setLocked(final boolean value);

}
