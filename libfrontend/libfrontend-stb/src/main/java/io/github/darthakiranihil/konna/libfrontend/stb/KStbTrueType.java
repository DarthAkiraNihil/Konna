package io.github.darthakiranihil.konna.libfrontend.stb;

import org.jspecify.annotations.Nullable;

import java.nio.*;

/**
 * Library frontend of STBTrueType.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
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

    /**
     * Non-standard method.
     * @return Created font info instance.
     */
    KStbTtFontInfo createFontInfo();
    KStbTtAlignedQuad createAlignedQuad();

    int stbtt_BakeFontBitmap(ByteBuffer data, float pixel_height, ByteBuffer pixels, int pw, int ph, int first_char, KStbTtBakedChar[] chardata);
    void stbtt_GetBakedQuad(KStbTtBakedChar[] chardata, int pw, int ph, int char_index, FloatBuffer xpos, FloatBuffer ypos, KStbTtAlignedQuad q, boolean opengl_fillrule);
    void stbtt_GetScaledFontVMetrics(ByteBuffer fontdata, int index, float size, FloatBuffer ascent, FloatBuffer descent, FloatBuffer lineGap);
    boolean stbtt_PackBegin(KStbTtPackContext spc, @Nullable ByteBuffer pixels, int width, int height, int stride_in_bytes, int padding, long alloc_context);
    boolean stbtt_PackBegin(KStbTtPackContext spc, @Nullable ByteBuffer pixels, int width, int height, int stride_in_bytes, int padding);
    void stbtt_PackEnd(KStbTtPackContext spc);
    int STBTT_POINT_SIZE(int font_size);
    boolean stbtt_PackFontRange(KStbTtPackContext spc, ByteBuffer fontdata, int font_index, float font_size, int first_unicode_char_in_range, KStbTtPackedChar[] chardata_for_range);
    boolean stbtt_PackFontRanges(KStbTtPackContext spc, ByteBuffer fontdata, int font_index, KStbTtPackRange[] ranges);
    void stbtt_PackSetOversampling(KStbTtPackContext spc, int h_oversample, int v_oversample);
    void stbtt_PackSetSkipMissingCodepoints(KStbTtPackContext spc, boolean skip);
    void stbtt_GetPackedQuad(KStbTtPackedChar[] chardata, int pw, int ph, int char_index, FloatBuffer xpos, FloatBuffer ypos, KStbTtAlignedQuad q, boolean align_to_integer);
    int stbtt_PackFontRangesGatherRects(KStbTtPackContext spc, KStbTtFontInfo info, KStbTtPackRange[] ranges, KStbRpRect[] rects);
    void stbtt_PackFontRangesPackRects(KStbTtPackContext spc, KStbRpRect[] rects);
    boolean stbtt_PackFontRangesRenderIntoRects(KStbTtPackContext spc, KStbTtFontInfo info, KStbTtPackRange[] ranges, KStbRpRect[] rects);
    int stbtt_GetNumberOfFonts(ByteBuffer data);
    int stbtt_GetFontOffsetForIndex(ByteBuffer data, int index);
    boolean stbtt_InitFont(KStbTtFontInfo info, ByteBuffer data, int offset);
    boolean stbtt_InitFont(KStbTtFontInfo info, ByteBuffer data);
    int stbtt_FindGlyphIndex(KStbTtFontInfo info, int unicode_codepoint);
    float stbtt_ScaleForPixelHeight(KStbTtFontInfo info, float pixels);
    float stbtt_ScaleForMappingEmToPixels(KStbTtFontInfo info, float pixels);
    void stbtt_GetFontVMetrics(KStbTtFontInfo info, @Nullable IntBuffer ascent, @Nullable IntBuffer descent, @Nullable IntBuffer lineGap);
    boolean stbtt_GetFontVMetricsOS2(KStbTtFontInfo info, @Nullable IntBuffer typoAscent, @Nullable IntBuffer typoDescent, @Nullable IntBuffer typoLineGap);
    void stbtt_GetFontBoundingBox(KStbTtFontInfo info, IntBuffer x0, IntBuffer y0, IntBuffer x1, IntBuffer y1);
    void stbtt_GetCodepointHMetrics(KStbTtFontInfo info, int codepoint, @Nullable IntBuffer advanceWidth, @Nullable IntBuffer leftSideBearing);
    int stbtt_GetCodepointKernAdvance(KStbTtFontInfo info, int ch1, int ch2);
    boolean stbtt_GetCodepointBox(KStbTtFontInfo info, int codepoint, @Nullable IntBuffer x0, @Nullable IntBuffer y0, @Nullable IntBuffer x1, @Nullable IntBuffer y1);
    void stbtt_GetGlyphHMetrics(KStbTtFontInfo info, int glyph_index, @Nullable IntBuffer advanceWidth, @Nullable IntBuffer leftSideBearing);
    int stbtt_GetGlyphKernAdvance(KStbTtFontInfo info, int glyph1, int glyph2);
    boolean stbtt_GetGlyphBox(KStbTtFontInfo info, int glyph_index, @Nullable IntBuffer x0, @Nullable IntBuffer y0, @Nullable IntBuffer x1, @Nullable IntBuffer y1);
    int stbtt_GetKerningTableLength(KStbTtFontInfo info);
    int stbtt_GetKerningTable(KStbTtFontInfo info, KStbTtKerningEntry table);
    boolean stbtt_IsGlyphEmpty(KStbTtFontInfo info, int glyph_index);
    int stbtt_GetCodepointShape(KStbTtFontInfo info, int unicode_codepoint, LongBuffer vertices);
    @Nullable KStbTtVertex[] stbtt_GetCodepointShape(KStbTtFontInfo info, int unicode_codepoint);
    int stbtt_GetGlyphShape(KStbTtFontInfo info, int glyph_index, LongBuffer vertices);
    @Nullable KStbTtVertex[] stbtt_GetGlyphShape(KStbTtFontInfo info, int glyph_index);
    void stbtt_FreeShape(KStbTtFontInfo info, KStbTtVertex[] vertices);
    long stbtt_FindSVGDoc(KStbTtFontInfo info, int gl);
    int stbtt_GetCodepointSVG(KStbTtFontInfo info, int unicode_codepoint, LongBuffer svg);
    int stbtt_GetGlyphSVG(KStbTtFontInfo info, int gl, LongBuffer svg);
    void stbtt_FreeBitmap(ByteBuffer bitmap, long userdata);
    void stbtt_FreeBitmap(ByteBuffer bitmap);
    @Nullable ByteBuffer stbtt_GetCodepointBitmap(KStbTtFontInfo info, float scale_x, float scale_y, int codepoint, IntBuffer width, IntBuffer height, @Nullable IntBuffer xoff, @Nullable IntBuffer yoff);
    @Nullable ByteBuffer stbtt_GetCodepointBitmapSubpixel(KStbTtFontInfo info, float scale_x, float scale_y, float shift_x, float shift_y, int codepoint, IntBuffer width, IntBuffer height, @Nullable IntBuffer xoff, @Nullable IntBuffer yoff);
    void stbtt_MakeCodepointBitmap(KStbTtFontInfo info, ByteBuffer output, int out_w, int out_h, int out_stride, float scale_x, float scale_y, int codepoint);
    void stbtt_MakeCodepointBitmapSubpixel(KStbTtFontInfo info, ByteBuffer output, int out_w, int out_h, int out_stride, float scale_x, float scale_y, float shift_x, float shift_y, int codepoint);
    void stbtt_MakeCodepointBitmapSubpixelPrefilter(KStbTtFontInfo info, ByteBuffer output, int out_w, int out_h, int out_stride, float scale_x, float scale_y, float shift_x, float shift_y, int oversample_x, int oversample_y, FloatBuffer sub_x, FloatBuffer sub_y, int codepoint);
    void stbtt_GetCodepointBitmapBox(KStbTtFontInfo font, int codepoint, float scale_x, float scale_y, @Nullable IntBuffer ix0, @Nullable IntBuffer iy0, @Nullable IntBuffer ix1, @Nullable IntBuffer iy1);
    void stbtt_GetCodepointBitmapBoxSubpixel(KStbTtFontInfo font, int codepoint, float scale_x, float scale_y, float shift_x, float shift_y, @Nullable IntBuffer ix0, @Nullable IntBuffer iy0, @Nullable IntBuffer ix1, @Nullable IntBuffer iy1);
    @Nullable ByteBuffer stbtt_GetGlyphBitmap(KStbTtFontInfo info, float scale_x, float scale_y, int glyph, IntBuffer width, IntBuffer height, @Nullable IntBuffer xoff, @Nullable IntBuffer yoff);
    @Nullable ByteBuffer stbtt_GetGlyphBitmapSubpixel(KStbTtFontInfo info, float scale_x, float scale_y, float shift_x, float shift_y, int glyph, IntBuffer width, IntBuffer height, @Nullable IntBuffer xoff, @Nullable IntBuffer yoff);
    void stbtt_MakeGlyphBitmap(KStbTtFontInfo info, ByteBuffer output, int out_w, int out_h, int out_stride, float scale_x, float scale_y, int glyph);
    void stbtt_MakeGlyphBitmapSubpixel(KStbTtFontInfo info, ByteBuffer output, int out_w, int out_h, int out_stride, float scale_x, float scale_y, float shift_x, float shift_y, int glyph);
    void stbtt_MakeGlyphBitmapSubpixelPrefilter(KStbTtFontInfo info, ByteBuffer output, int out_w, int out_h, int out_stride, float scale_x, float scale_y, float shift_x, float shift_y, int oversample_x, int oversample_y, FloatBuffer sub_x, FloatBuffer sub_y, int glyph);
    void stbtt_GetGlyphBitmapBox(KStbTtFontInfo font, int glyph, float scale_x, float scale_y, @Nullable IntBuffer ix0, @Nullable IntBuffer iy0, @Nullable IntBuffer ix1, @Nullable IntBuffer iy1);
    void stbtt_GetGlyphBitmapBoxSubpixel(KStbTtFontInfo font, int glyph, float scale_x, float scale_y, float shift_x, float shift_y, @Nullable IntBuffer ix0, @Nullable IntBuffer iy0, @Nullable IntBuffer ix1, @Nullable IntBuffer iy1);
    void stbtt_Rasterize(KStbTtBitmap result, float flatness_in_pixels, KStbTtVertex[] vertices, float scale_x, float scale_y, float shift_x, float shift_y, int x_off, int y_off, boolean invert);
    void stbtt_FreeSDF(ByteBuffer bitmap, long userdata);
    void stbtt_FreeSDF(ByteBuffer bitmap);
    @Nullable ByteBuffer stbtt_GetGlyphSDF(KStbTtFontInfo font, float scale, int glyph, int padding, byte onedge_value, float pixel_dist_scale, IntBuffer width, IntBuffer height, IntBuffer xoff, IntBuffer yoff);
    @Nullable ByteBuffer stbtt_GetCodepointSDF(KStbTtFontInfo font, float scale, int codepoint, int padding, byte onedge_value, float pixel_dist_scale, IntBuffer width, IntBuffer height, IntBuffer xoff, IntBuffer yoff);
    int stbtt_FindMatchingFont(ByteBuffer fontdata, ByteBuffer name, int flags);
    int stbtt_FindMatchingFont(ByteBuffer fontdata, CharSequence name, int flags);
    boolean stbtt_CompareUTF8toUTF16_bigendian(ByteBuffer s1, ByteBuffer s2);
    @Nullable ByteBuffer stbtt_GetFontNameString(KStbTtFontInfo font, int platformID, int encodingID, int languageID, int nameID);
    void stbtt_GetBakedQuad(KStbTtBakedChar[] chardata, int pw, int ph, int char_index, float[] xpos, float[] ypos, KStbTtAlignedQuad q, boolean opengl_fillrule);
    void stbtt_GetScaledFontVMetrics(ByteBuffer fontdata, int index, float size, float[] ascent, float[] descent, float[] lineGap);
    void stbtt_GetPackedQuad(KStbTtPackedChar[] chardata, int pw, int ph, int char_index, float[] xpos, float[] ypos, KStbTtAlignedQuad q, boolean align_to_integer);
    void stbtt_GetFontVMetrics(KStbTtFontInfo info, int @Nullable [] ascent, int @Nullable [] descent, int @Nullable [] lineGap);
    boolean stbtt_GetFontVMetricsOS2(KStbTtFontInfo info, int @Nullable [] typoAscent, int @Nullable [] typoDescent, int @Nullable [] typoLineGap);
    void stbtt_GetFontBoundingBox(KStbTtFontInfo info, int[] x0, int[] y0, int[] x1, int[] y1);
    void stbtt_GetCodepointHMetrics(KStbTtFontInfo info, int codepoint, int @Nullable [] advanceWidth, int @Nullable [] leftSideBearing);
    boolean stbtt_GetCodepointBox(KStbTtFontInfo info, int codepoint, int @Nullable [] x0, int @Nullable [] y0, int @Nullable [] x1, int @Nullable [] y1);
    void stbtt_GetGlyphHMetrics(KStbTtFontInfo info, int glyph_index, int @Nullable [] advanceWidth, int @Nullable [] leftSideBearing);
    boolean stbtt_GetGlyphBox(KStbTtFontInfo info, int glyph_index, int @Nullable [] x0, int @Nullable [] y0, int @Nullable [] x1, int @Nullable [] y1);
    @Nullable ByteBuffer stbtt_GetCodepointBitmap(KStbTtFontInfo info, float scale_x, float scale_y, int codepoint, int[] width, int[] height, int @Nullable [] xoff, int @Nullable [] yoff);
    @Nullable ByteBuffer stbtt_GetCodepointBitmapSubpixel(KStbTtFontInfo info, float scale_x, float scale_y, float shift_x, float shift_y, int codepoint, int[] width, int[] height, int @Nullable [] xoff, int @Nullable [] yoff);
    void stbtt_MakeCodepointBitmapSubpixelPrefilter(KStbTtFontInfo info, ByteBuffer output, int out_w, int out_h, int out_stride, float scale_x, float scale_y, float shift_x, float shift_y, int oversample_x, int oversample_y, float[] sub_x, float[] sub_y, int codepoint);
    void stbtt_GetCodepointBitmapBox(KStbTtFontInfo font, int codepoint, float scale_x, float scale_y, int @Nullable [] ix0, int @Nullable [] iy0, int @Nullable [] ix1, int @Nullable [] iy1);
    void stbtt_GetCodepointBitmapBoxSubpixel(KStbTtFontInfo font, int codepoint, float scale_x, float scale_y, float shift_x, float shift_y, int @Nullable [] ix0, int @Nullable [] iy0, int @Nullable [] ix1, int @Nullable [] iy1);
    @Nullable ByteBuffer stbtt_GetGlyphBitmap(KStbTtFontInfo info, float scale_x, float scale_y, int glyph, int[] width, int[] height, int @Nullable [] xoff, int @Nullable [] yoff);
    @Nullable ByteBuffer stbtt_GetGlyphBitmapSubpixel(KStbTtFontInfo info, float scale_x, float scale_y, float shift_x, float shift_y, int glyph, int[] width, int[] height, int @Nullable [] xoff, int @Nullable [] yoff);
    void stbtt_MakeGlyphBitmapSubpixelPrefilter(KStbTtFontInfo info, ByteBuffer output, int out_w, int out_h, int out_stride, float scale_x, float scale_y, float shift_x, float shift_y, int oversample_x, int oversample_y, float[] sub_x, float[] sub_y, int glyph);
    void stbtt_GetGlyphBitmapBox(KStbTtFontInfo font, int glyph, float scale_x, float scale_y, int @Nullable [] ix0, int @Nullable [] iy0, int @Nullable [] ix1, int @Nullable [] iy1);
    void stbtt_GetGlyphBitmapBoxSubpixel(KStbTtFontInfo font, int glyph, float scale_x, float scale_y, float shift_x, float shift_y, int @Nullable [] ix0, int @Nullable [] iy0, int @Nullable [] ix1, int @Nullable [] iy1);
    @Nullable ByteBuffer stbtt_GetGlyphSDF(KStbTtFontInfo font, float scale, int glyph, int padding, byte onedge_value, float pixel_dist_scale, int[] width, int[] height, int[] xoff, int[] yoff);
    @Nullable ByteBuffer stbtt_GetCodepointSDF(KStbTtFontInfo font, float scale, int codepoint, int padding, byte onedge_value, float pixel_dist_scale, int[] width, int[] height, int[] xoff, int[] yoff);

}
