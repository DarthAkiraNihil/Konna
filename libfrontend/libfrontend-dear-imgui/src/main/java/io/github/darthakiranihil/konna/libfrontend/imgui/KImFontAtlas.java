package io.github.darthakiranihil.konna.libfrontend.imgui;

import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.struct.ref.KIntReference;

import java.nio.ByteBuffer;

/**
 * Interface representing ImFontAtlas    of Dear ImGui.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public interface KImFontAtlas {
    
    KImFont addFontDefault();

    KImFont addFontDefault(KImFontConfig fontConfig);
    KImFont addFontFromFileTTF(String filename, float sizePixels);
    KImFont addFontFromFileTTF(String filename, float sizePixels, KImFontConfig fontConfig);
    KImFont addFontFromFileTTF(
        String filename,
        float sizePixels,
        KImFontConfig fontConfig,
        short[] glyphRanges
    );
    KImFont addFontFromFileTTF(String filename, float sizePixels, short[] glyphRanges);
    KImFont addFontFromMemoryTTF(byte[] fontData, float sizePixels);
    KImFont addFontFromMemoryTTF(byte[] fontData, float sizePixels, KImFontConfig fontConfig);
    KImFont addFontFromMemoryTTF(
        byte[] fontData,
        float sizePixels,
        KImFontConfig fontConfig,
        short[] glyphRanges
    );
    KImFont addFontFromMemoryTTF(byte[] fontData, float sizePixels, short[] glyphRanges);
    KImFont addFontFromMemoryTTF(byte[] fontData, int fontDataSize, float sizePixels);
    KImFont addFontFromMemoryTTF(
        byte[] fontData,
        int fontDataSize,
        float sizePixels,
        KImFontConfig fontConfig
    );
    KImFont addFontFromMemoryTTF(
        byte[] fontData,
        int fontDataSize,
        float sizePixels,
        KImFontConfig fontConfig,
        short[] glyphRanges
    );
    KImFont addFontFromMemoryTTF(
        byte[] fontData,
        int fontDataSize, float sizePixels, short[] glyphRanges);
    KImFont addFontFromMemoryCompressedTTF(byte[] compressedFontData, float sizePixels);
    KImFont addFontFromMemoryCompressedTTF(
        byte[] compressedFontData,
        float sizePixels,
        KImFontConfig fontConfig
    );
    KImFont addFontFromMemoryCompressedTTF(
        byte[] compressedFontData,
        float sizePixels,
        KImFontConfig fontConfig,
        short[] glyphRanges
    );
    KImFont addFontFromMemoryCompressedTTF(
        byte[] compressedFontData,
        float sizePixels,
        short[] glyphRanges
    );
    KImFont addFontFromMemoryCompressedTTF(
        byte[] compressedFontData,
        int compressedFontDataSize,
        float sizePixels
    );
    KImFont addFontFromMemoryCompressedTTF(
        byte[] compressedFontData,
        int compressedFontDataSize,
        float sizePixels,
        KImFontConfig fontConfig
    );
    KImFont addFontFromMemoryCompressedTTF(
        byte[] compressedFontData,
        int compressedFontDataSize,
        float sizePixels,
        KImFontConfig fontConfig,
        short[] glyphRanges
    );
    KImFont addFontFromMemoryCompressedTTF(
        byte[] compressedFontData,
        int compressedFontDataSize,
        float sizePixels,
        short[] glyphRanges
    );
    KImFont addFontFromMemoryCompressedBase85TTF(
        String compressedFontDataBase85,
        float sizePixels,
        KImFontConfig fontConfig
    );
    KImFont addFontFromMemoryCompressedBase85TTF(
        String compressedFontDataBase85,
        float sizePixels,
        KImFontConfig fontConfig,
        short[] glyphRanges
    );
    void clearInputData();
    void clearTexData();
    void clearFonts();
    void clear();
    void setFreeTypeRenderer(boolean enabled);

    boolean build();
    ByteBuffer getTexDataAsAlpha8(KIntReference outWidth, KIntReference outHeight);
    ByteBuffer getTexDataAsAlpha8(
        KIntReference outWidth,
        KIntReference outHeight,
        KIntReference outBytesPerPixel
    );
    ByteBuffer getTexDataAsRGBA32(
        KIntReference outWidth,
        KIntReference outHeight
    );
    ByteBuffer getTexDataAsRGBA32(
        KIntReference outWidth,
        KIntReference outHeight,
        KIntReference outBytesPerPixel
    );
    boolean isBuilt();

    void setTexID(long textureID);
    short[] getGlyphRangesDefault();
    short[] getGlyphRangesGreek();
    short[] getGlyphRangesKorean();
    short[] getGlyphRangesJapanese();
    short[] getGlyphRangesChineseFull();
    short[] getGlyphRangesChineseSimplifiedCommon();
    short[] getGlyphRangesCyrillic();
    short[] getGlyphRangesThai();
    short[] getGlyphRangesVietnamese();

    int addCustomRectRegular(int width, int height);

    int addCustomRectFontGlyph(KImFont font, short id, int width, int height, float advanceX);

    int addCustomRectFontGlyph(
        KImFont font,
        short id,
        int width,
        int height,
        float advanceX,
        KVector2f offset
    );
    int addCustomRectFontGlyph(
        KImFont font,
        short id,
        int width,
        int height,
        float advanceX,
        float offsetX,
        float offsetY
    );

    int getFlags();
    void setFlags(int value);
    void addFlags(int flags);
    void removeFlags(int flags);
    boolean hasFlags(int flags);

    int getTexDesiredWidth();
    void setTexDesiredWidth(int value);

    int getTexGlyphPadding();
    void setTexGlyphPadding(int value);
    boolean getLocked();
    void setLocked(boolean value);

}
