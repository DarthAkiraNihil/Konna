package io.github.darthakiranihil.konna.libfrontend.stb;

import org.jspecify.annotations.Nullable;

import java.nio.*;

public interface KStbTrueType {

    byte
        STBTT_vmove = 1,
        STBTT_vline = 2,
        STBTT_vcurve = 3,
        STBTT_vcubic = 4;

    int
        STBTT_MACSTYLE_DONTCARE = 0,
        STBTT_MACSTYLE_BOLD = 1,
        STBTT_MACSTYLE_ITALIC = 2,
        STBTT_MACSTYLE_UNDERSCORE = 4,
        STBTT_MACSTYLE_NONE = 8;

    int
        STBTT_PLATFORM_ID_UNICODE = 0,
        STBTT_PLATFORM_ID_MAC = 1,
        STBTT_PLATFORM_ID_ISO = 2,
        STBTT_PLATFORM_ID_MICROSOFT = 3;

    int
        STBTT_UNICODE_EID_UNICODE_1_0 = 0,
        STBTT_UNICODE_EID_UNICODE_1_1 = 1,
        STBTT_UNICODE_EID_ISO_10646 = 2,
        STBTT_UNICODE_EID_UNICODE_2_0_BMP = 3,
        STBTT_UNICODE_EID_UNICODE_2_0_FULL = 4;

    int
        STBTT_MS_EID_SYMBOL = 0,
        STBTT_MS_EID_UNICODE_BMP = 1,
        STBTT_MS_EID_SHIFTJIS = 2,
        STBTT_MS_EID_UNICODE_FULL = 10;

    int
        STBTT_MAC_EID_ROMAN = 0,
        STBTT_MAC_EID_JAPANESE = 1,
        STBTT_MAC_EID_CHINESE_TRAD = 2,
        STBTT_MAC_EID_KOREAN = 3,
        STBTT_MAC_EID_ARABIC = 4,
        STBTT_MAC_EID_HEBREW = 5,
        STBTT_MAC_EID_GREEK = 6,
        STBTT_MAC_EID_RUSSIAN = 7;

    int
        STBTT_MS_LANG_ENGLISH = 0x409,
        STBTT_MS_LANG_CHINESE = 0x804,
        STBTT_MS_LANG_DUTCH = 0x413,
        STBTT_MS_LANG_FRENCH = 0x40C,
        STBTT_MS_LANG_GERMAN = 0x407,
        STBTT_MS_LANG_HEBREW = 0x40D,
        STBTT_MS_LANG_ITALIAN = 0x410,
        STBTT_MS_LANG_JAPANESE = 0x411,
        STBTT_MS_LANG_KOREAN = 0x412,
        STBTT_MS_LANG_RUSSIAN = 0x419,
        STBTT_MS_LANG_SPANISH = 0x409,
        STBTT_MS_LANG_SWEDISH = 0x41D;

    int
        STBTT_MAC_LANG_ENGLISH = 0,
        STBTT_MAC_LANG_ARABIC = 12,
        STBTT_MAC_LANG_DUTCH = 4,
        STBTT_MAC_LANG_FRENCH = 1,
        STBTT_MAC_LANG_GERMAN = 2,
        STBTT_MAC_LANG_HEBREW = 10,
        STBTT_MAC_LANG_ITALIAN = 3,
        STBTT_MAC_LANG_JAPANESE = 11,
        STBTT_MAC_LANG_KOREAN = 23,
        STBTT_MAC_LANG_RUSSIAN = 32,
        STBTT_MAC_LANG_SPANISH = 6,
        STBTT_MAC_LANG_SWEDISH = 5,
        STBTT_MAC_LANG_CHINESE_SIMPLIFIED = 33,
        STBTT_MAC_LANG_CHINESE_TRAD = 19;

    int stbtt_BakeFontBitmap(KStbTtBakedChar[] chardata);
    void stbtt_GetBakedQuad(
        KStbTtBakedChar[] chardata,
        int pw,
        int ph,
        int char_index,
        FloatBuffer xpos,
        FloatBuffer ypos,
        KStbTtAlignedQuad q,
        boolean opengl_fillrule
    );
    void stbtt_GetScaledFontVMetrics(FloatBuffer lineGap);
    boolean stbtt_PackBegin(long alloc_context);
    boolean stbtt_PackBegin(
        @Nullable ByteBuffer pixels,
        int width,
        int height,
        int stride_in_bytes,
        int padding
    );
    void stbtt_PackEnd(KStbTtPackContext spc);
    int STBTT_POINT_SIZE(int font_size);
    boolean stbtt_PackFontRange(KStbTtPackedChar[] chardata_for_range);
    boolean stbtt_PackFontRanges(KStbTtPackRange[] ranges);
    void stbtt_PackSetOversampling(int v_oversample);
    void stbtt_PackSetSkipMissingCodepoints(boolean skip);
    void stbtt_GetPackedQuad(boolean align_to_integer);
    int stbtt_PackFontRangesGatherRects(KStbRpRect[] rects);
    void stbtt_PackFontRangesPackRects(KStbRpRect[] rects);
    boolean stbtt_PackFontRangesRenderIntoRects(KStbRpRect[] rects);
    int stbtt_GetNumberOfFonts(ByteBuffer data);
    int stbtt_GetFontOffsetForIndex(ByteBuffer data, int index);
    boolean stbtt_InitFont(ByteBuffer data, int offset);
    boolean stbtt_InitFont(ByteBuffer data);
    int stbtt_FindGlyphIndex(KStbTtFontInfo info, int unicode_codepoint);
    float stbtt_ScaleForPixelHeight(KStbTtFontInfo info, float pixels);
    float stbtt_ScaleForMappingEmToPixels(KStbTtFontInfo info, float pixels);
    void stbtt_GetFontVMetrics(@Nullable IntBuffer lineGap);
    boolean stbtt_GetFontVMetricsOS2(@Nullable IntBuffer typoLineGap);
    void stbtt_GetFontBoundingBox(IntBuffer y1);
    void stbtt_GetCodepointHMetrics(@Nullable IntBuffer leftSideBearing);
    int stbtt_GetCodepointKernAdvance(KStbTtFontInfo info, int ch1, int ch2);
    boolean stbtt_GetCodepointBox(@Nullable IntBuffer y1);
    void stbtt_GetGlyphHMetrics(@Nullable IntBuffer leftSideBearing);
    int stbtt_GetGlyphKernAdvance(KStbTtFontInfo info, int glyph1, int glyph2);
    boolean stbtt_GetGlyphBox(@Nullable IntBuffer y1);
    int stbtt_GetKerningTableLength(KStbTtFontInfo info);
    int stbtt_GetKerningTable(KStbTtKerningEntry[] table);
    boolean stbtt_IsGlyphEmpty(KStbTtFontInfo info, int glyph_index);
    int stbtt_GetCodepointShape(LongBuffer vertices);
    @Nullable KStbTtVertex[] stbtt_GetCodepointShape(KStbTtFontInfo info, int unicode_codepoint);
    int stbtt_GetGlyphShape(LongBuffer vertices);
    @Nullable KStbTtVertex[] stbtt_GetGlyphShape(KStbTtFontInfo info, int glyph_index);
    void stbtt_FreeShape(KStbTtVertex[] vertices);
    long stbtt_FindSVGDoc(KStbTtFontInfo info, int gl);
    int stbtt_GetCodepointSVG(LongBuffer svg);
    int stbtt_GetGlyphSVG(LongBuffer svg);
    void stbtt_FreeBitmap(long userdata);
    void stbtt_FreeBitmap(ByteBuffer bitmap);
    @Nullable ByteBuffer stbtt_GetCodepointBitmap(@Nullable IntBuffer yoff);
    @Nullable ByteBuffer stbtt_GetCodepointBitmapSubpixel(@Nullable IntBuffer yoff);
    void stbtt_MakeCodepointBitmap(
        ByteBuffer output,
        int out_w,
        int out_h,
        int out_stride,
        float scale_x,
        float scale_y,
        int codepoint
    );
    void stbtt_MakeCodepointBitmapSubpixel(
        ByteBuffer output,
        int out_w,
        int out_h,
        int out_stride,
        float scale_x,
        float scale_y,
        float shift_x,
        float shift_y,
        int codepoint
    );
    void stbtt_MakeCodepointBitmapSubpixelPrefilter(FloatBuffer sub_y, int codepoint);
    void stbtt_GetCodepointBitmapBox(@Nullable IntBuffer iy1);
    void stbtt_GetCodepointBitmapBoxSubpixel(@Nullable IntBuffer iy1);
    @Nullable ByteBuffer stbtt_GetGlyphBitmap(@Nullable IntBuffer yoff);
    @Nullable ByteBuffer stbtt_GetGlyphBitmapSubpixel(@Nullable IntBuffer yoff);
    void stbtt_MakeGlyphBitmap(
        ByteBuffer output,
        int out_w,
        int out_h,
        int out_stride,
        float scale_x,
        float scale_y,
        int glyph
    );
    void stbtt_MakeGlyphBitmapSubpixel(
        ByteBuffer output,
        int out_w,
        int out_h,
        int out_stride,
        float scale_x,
        float scale_y,
        float shift_x,
        float shift_y,
        int glyph
    );
    void stbtt_MakeGlyphBitmapSubpixelPrefilter(FloatBuffer sub_y, int glyph);
    void stbtt_GetGlyphBitmapBox(@Nullable IntBuffer iy1);
    void stbtt_GetGlyphBitmapBoxSubpixel(@Nullable IntBuffer iy1);
    void stbtt_Rasterize(boolean invert);
    void stbtt_FreeSDF(long userdata);
    void stbtt_FreeSDF(ByteBuffer bitmap);
    @Nullable ByteBuffer stbtt_GetGlyphSDF(IntBuffer yoff);
    @Nullable ByteBuffer stbtt_GetCodepointSDF(IntBuffer yoff);
    int stbtt_FindMatchingFont(ByteBuffer name, int flags);
    int stbtt_FindMatchingFont(CharSequence name, int flags);
    boolean stbtt_CompareUTF8toUTF16_bigendian(ByteBuffer s2);
    @Nullable ByteBuffer stbtt_GetFontNameString(
        KStbTtFontInfo font,
        int platformID,
        int encodingID,
        int languageID,
        int nameID
    );
    void stbtt_GetBakedQuad(boolean opengl_fillrule);
    void stbtt_GetScaledFontVMetrics(float[] lineGap);
    void stbtt_GetPackedQuad(
        KStbTtBakedChar[] chardata,
        int pw,
        int ph,
        int char_index,
        float[] xpos,
        float[] ypos,
        KStbTtAlignedQuad q,
        boolean align_to_integer
    );
    void stbtt_GetFontVMetrics(int @Nullable [] lineGap);
    boolean stbtt_GetFontVMetricsOS2(int @Nullable [] typoLineGap);
    void stbtt_GetFontBoundingBox(int[] y1);
    void stbtt_GetCodepointHMetrics(int @Nullable [] leftSideBearing);
    boolean stbtt_GetCodepointBox(int @Nullable [] y1);
    void stbtt_GetGlyphHMetrics(int @Nullable [] leftSideBearing);
    boolean stbtt_GetGlyphBox(int @Nullable [] y1);
    @Nullable ByteBuffer stbtt_GetCodepointBitmap(int @Nullable [] yoff);
    @Nullable ByteBuffer stbtt_GetCodepointBitmapSubpixel(int @Nullable [] yoff);
    void stbtt_MakeCodepointBitmapSubpixelPrefilter(float[] sub_y, int codepoint);
    void stbtt_GetCodepointBitmapBox(int @Nullable [] iy1);
    void stbtt_GetCodepointBitmapBoxSubpixel(int @Nullable [] iy1);
    @Nullable ByteBuffer stbtt_GetGlyphBitmap(int @Nullable [] yoff);
    @Nullable ByteBuffer stbtt_GetGlyphBitmapSubpixel(int @Nullable [] yoff);
    void stbtt_MakeGlyphBitmapSubpixelPrefilter(float[] sub_y, int glyph);
    void stbtt_GetGlyphBitmapBox(int @Nullable [] iy1);
    void stbtt_GetGlyphBitmapBoxSubpixel(int @Nullable [] iy1);
    @Nullable ByteBuffer stbtt_GetGlyphSDF(int[] yoff);
    @Nullable ByteBuffer stbtt_GetCodepointSDF(int[] yoff);

}
