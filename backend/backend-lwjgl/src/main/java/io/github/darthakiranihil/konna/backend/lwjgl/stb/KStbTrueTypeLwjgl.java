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

package io.github.darthakiranihil.konna.backend.lwjgl.stb;

import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.test.KExcludeFromGeneratedCoverageReport;
import org.jspecify.annotations.Nullable;

import io.github.darthakiranihil.konna.libfrontend.stb.*;
import org.lwjgl.stb.*;

import java.nio.*;

@KSingleton
@KExcludeFromGeneratedCoverageReport
public final class KStbTrueTypeLwjgl extends KObject implements KStbTrueType {

    @Override
    public KStbTtFontInfo createFontInfo() {
        return KStbTrueTypeUnwrapper.wrap(STBTTFontinfo.create());
    }

    @Override
    public KStbTtAlignedQuad createAlignedQuad() {
        return KStbTrueTypeUnwrapper.wrap(STBTTAlignedQuad.create());
    }

    @Override
    public int stbtt_BakeFontBitmap(ByteBuffer data, float pixel_height, ByteBuffer pixels, int pw, int ph, int first_char, KStbTtBakedChar[] chardata) {
        STBTTBakedChar.Buffer chars = STBTTBakedChar.malloc(chardata.length);
        int result = STBTruetype.stbtt_BakeFontBitmap(data, pixel_height, pixels, pw, ph, first_char, chars);
        for (int i = 0; i < chardata.length; i++) {
            chardata[i] = KStbTrueTypeUnwrapper.wrap(chars.get(i));
        }
        chars.free();
        return result;
    }

    @Override
    public void stbtt_GetBakedQuad(KStbTtBakedChar[] chardata, int pw, int ph, int char_index, FloatBuffer xpos, FloatBuffer ypos, KStbTtAlignedQuad q, boolean opengl_fillrule) {

        var chars = STBTTBakedChar.malloc(chardata.length);
        for (int i = 0; i < chardata.length; i++) {
            chars.put(i, KStbTrueTypeWrapper.wrap(chardata[i]));
        }
        STBTTAlignedQuad aq = STBTTAlignedQuad.malloc();
        STBTruetype.stbtt_GetBakedQuad(chars, pw, ph, char_index, xpos, ypos, aq, opengl_fillrule);
        q.set(aq.x0(), aq.y0(), aq.s0(), aq.t0(), aq.x1(), aq.y1(), aq.s1(), aq.t1());
        chars.free();
        aq.free();

    }

    @Override
    public void stbtt_GetScaledFontVMetrics(ByteBuffer fontdata, int index, float size, FloatBuffer ascent, FloatBuffer descent, FloatBuffer lineGap) {
        STBTruetype.stbtt_GetScaledFontVMetrics(fontdata, index, size, ascent, descent, lineGap);
    }

    @Override
    public boolean stbtt_PackBegin(KStbTtPackContext spc, @Nullable ByteBuffer pixels, int width, int height, int stride_in_bytes, int padding, long alloc_context) {
        return STBTruetype.stbtt_PackBegin(spc, pixels, width, height, stride_in_bytes, padding, alloc_context);
    }

    @Override
    public boolean stbtt_PackBegin(KStbTtPackContext spc, @Nullable ByteBuffer pixels, int width, int height, int stride_in_bytes, int padding) {
        return STBTruetype.stbtt_PackBegin(spc, pixels, width, height, stride_in_bytes, padding);
    }

    @Override
    public void stbtt_PackEnd(KStbTtPackContext spc) {
        STBTruetype.stbtt_PackEnd(spc);
    }

    @Override
    public int STBTT_POINT_SIZE(int font_size) {
        return STBTruetype.STBTT_POINT_SIZE(font_size);
    }

    @Override
    public boolean stbtt_PackFontRange(KStbTtPackContext spc, ByteBuffer fontdata, int font_index, float font_size, int first_unicode_char_in_range, KStbTtPackedChar[] chardata_for_range) {
        return STBTruetype.stbtt_PackFontRange(spc, fontdata, font_index, font_size, first_unicode_char_in_range, chardata_for_range);
    }

    @Override
    public boolean stbtt_PackFontRanges(KStbTtPackContext spc, ByteBuffer fontdata, int font_index, KStbTtPackRange[] ranges) {
        return STBTruetype.stbtt_PackFontRanges(spc, fontdata, font_index, ranges);
    }

    @Override
    public void stbtt_PackSetOversampling(KStbTtPackContext spc, int h_oversample, int v_oversample) {
        STBTruetype.stbtt_PackSetOversampling(spc, h_oversample, v_oversample);
    }

    @Override
    public void stbtt_PackSetSkipMissingCodepoints(KStbTtPackContext spc, boolean skip) {
        STBTruetype.stbtt_PackSetSkipMissingCodepoints(spc, skip);
    }

    @Override
    public void stbtt_GetPackedQuad(KStbTtPackedChar[] chardata, int pw, int ph, int char_index, FloatBuffer xpos, FloatBuffer ypos, KStbTtAlignedQuad q, boolean align_to_integer) {
        STBTruetype.stbtt_GetPackedQuad(chardata, pw, ph, char_index, xpos, ypos, q, align_to_integer);
    }

    @Override
    public int stbtt_PackFontRangesGatherRects(KStbTtPackContext spc, KStbTtFontInfo info, KStbTtPackRange[] ranges, KStbRpRect[] rects) {
        return STBTruetype.stbtt_PackFontRangesGatherRects(spc, info, ranges, rects);
    }

    @Override
    public void stbtt_PackFontRangesPackRects(KStbTtPackContext spc, KStbRpRect[] rects) {
        STBTruetype.stbtt_PackFontRangesPackRects(spc, rects);
    }

    @Override
    public boolean stbtt_PackFontRangesRenderIntoRects(KStbTtPackContext spc, KStbTtFontInfo info, KStbTtPackRange[] ranges, KStbRpRect[] rects) {
        return STBTruetype.stbtt_PackFontRangesRenderIntoRects(spc, info, ranges, rects);
    }

    @Override
    public int stbtt_GetNumberOfFonts(ByteBuffer data) {
        return STBTruetype.stbtt_GetNumberOfFonts(data);
    }

    @Override
    public int stbtt_GetFontOffsetForIndex(ByteBuffer data, int index) {
        return STBTruetype.stbtt_GetFontOffsetForIndex(data, index);
    }

    @Override
    public boolean stbtt_InitFont(KStbTtFontInfo info, ByteBuffer data, int offset) {
        return STBTruetype.stbtt_InitFont(KStbTrueTypeWrapper.wrap(info), data, offset);
    }

    @Override
    public boolean stbtt_InitFont(KStbTtFontInfo info, ByteBuffer data) {
        return STBTruetype.stbtt_InitFont(KStbTrueTypeWrapper.wrap(info), data);
    }

    @Override
    public int stbtt_FindGlyphIndex(KStbTtFontInfo info, int unicode_codepoint) {
        return STBTruetype.stbtt_FindGlyphIndex(KStbTrueTypeWrapper.wrap(info), unicode_codepoint);
    }

    @Override
    public float stbtt_ScaleForPixelHeight(KStbTtFontInfo info, float pixels) {
        return STBTruetype.stbtt_ScaleForPixelHeight(KStbTrueTypeWrapper.wrap(info), pixels);
    }

    @Override
    public float stbtt_ScaleForMappingEmToPixels(KStbTtFontInfo info, float pixels) {
        return STBTruetype.stbtt_ScaleForMappingEmToPixels(KStbTrueTypeWrapper.wrap(info), pixels);
    }

    @Override
    public void stbtt_GetFontVMetrics(KStbTtFontInfo info, @Nullable IntBuffer ascent, @Nullable IntBuffer descent, @Nullable IntBuffer lineGap) {
        STBTruetype.stbtt_GetFontVMetrics(KStbTrueTypeWrapper.wrap(info), IntBuffer, IntBuffer, IntBuffer);
    }

    @Override
    public boolean stbtt_GetFontVMetricsOS2(KStbTtFontInfo info, @Nullable IntBuffer typoAscent, @Nullable IntBuffer typoDescent, @Nullable IntBuffer typoLineGap) {
        return STBTruetype.stbtt_GetFontVMetricsOS2(KStbTrueTypeWrapper.wrap(info), IntBuffer, IntBuffer, IntBuffer);
    }

    @Override
    public void stbtt_GetFontBoundingBox(KStbTtFontInfo info, IntBuffer x0, IntBuffer y0, IntBuffer x1, IntBuffer y1) {
        STBTruetype.stbtt_GetFontBoundingBox(KStbTrueTypeWrapper.wrap(info), x0, y0, x1, y1);
    }

    @Override
    public void stbtt_GetCodepointHMetrics(KStbTtFontInfo info, int codepoint, @Nullable IntBuffer advanceWidth, @Nullable IntBuffer leftSideBearing) {
        STBTruetype.stbtt_GetCodepointHMetrics(KStbTrueTypeWrapper.wrap(info), codepoint, IntBuffer, IntBuffer);
    }

    @Override
    public int stbtt_GetCodepointKernAdvance(KStbTtFontInfo info, int ch1, int ch2) {
        return STBTruetype.stbtt_GetCodepointKernAdvance(KStbTrueTypeWrapper.wrap(info), ch1, ch2);
    }

    @Override
    public boolean stbtt_GetCodepointBox(KStbTtFontInfo info, int codepoint, @Nullable IntBuffer x0, @Nullable IntBuffer y0, @Nullable IntBuffer x1, @Nullable IntBuffer y1) {
        return STBTruetype.stbtt_GetCodepointBox(KStbTrueTypeWrapper.wrap(info), codepoint, IntBuffer, IntBuffer, IntBuffer, IntBuffer);
    }

    @Override
    public void stbtt_GetGlyphHMetrics(KStbTtFontInfo info, int glyph_index, @Nullable IntBuffer advanceWidth, @Nullable IntBuffer leftSideBearing) {
        STBTruetype.stbtt_GetGlyphHMetrics(KStbTrueTypeWrapper.wrap(info), glyph_index, IntBuffer, IntBuffer);
    }

    @Override
    public int stbtt_GetGlyphKernAdvance(KStbTtFontInfo info, int glyph1, int glyph2) {
        return STBTruetype.stbtt_GetGlyphKernAdvance(KStbTrueTypeWrapper.wrap(info), glyph1, glyph2);
    }

    @Override
    public boolean stbtt_GetGlyphBox(KStbTtFontInfo info, int glyph_index, @Nullable IntBuffer x0, @Nullable IntBuffer y0, @Nullable IntBuffer x1, @Nullable IntBuffer y1) {
        return STBTruetype.stbtt_GetGlyphBox(KStbTrueTypeWrapper.wrap(info), glyph_index, IntBuffer, IntBuffer, IntBuffer, IntBuffer);
    }

    @Override
    public int stbtt_GetKerningTableLength(KStbTtFontInfo info) {
        return STBTruetype.stbtt_GetKerningTableLength(info);
    }

    @Override
    public int stbtt_GetKerningTable(KStbTtFontInfo info, KStbTtKerningEntry table) {
        return STBTruetype.stbtt_GetKerningTable(KStbTrueTypeWrapper.wrap(info), table);
    }

    @Override
    public boolean stbtt_IsGlyphEmpty(KStbTtFontInfo info, int glyph_index) {
        return STBTruetype.stbtt_IsGlyphEmpty(KStbTrueTypeWrapper.wrap(info), glyph_index);
    }

    @Override
    public int stbtt_GetCodepointShape(KStbTtFontInfo info, int unicode_codepoint, LongBuffer vertices) {
        return STBTruetype.stbtt_GetCodepointShape(KStbTrueTypeWrapper.wrap(info), unicode_codepoint, vertices);
    }

    @Override
    public @Nullable KStbTtVertex[] stbtt_GetCodepointShape(KStbTtFontInfo info, int unicode_codepoint) {
        return STBTruetype.KStbTtVertex[] stbtt_GetCodepointShape(KStbTrueTypeWrapper.wrap(info), unicode_codepoint);
    }

    @Override
    public int stbtt_GetGlyphShape(KStbTtFontInfo info, int glyph_index, LongBuffer vertices) {
        return STBTruetype.stbtt_GetGlyphShape(KStbTrueTypeWrapper.wrap(info), glyph_index, vertices);
    }

    @Override
    public @Nullable KStbTtVertex[] stbtt_GetGlyphShape(KStbTtFontInfo info, int glyph_index) {
        return STBTruetype.KStbTtVertex[] stbtt_GetGlyphShape(info, glyph_index);
    }

    @Override
    public void stbtt_FreeShape(KStbTtFontInfo info, KStbTtVertex[] vertices) {
        STBTruetype.stbtt_FreeShape(KStbTrueTypeWrapper.wrap(info), vertices);
    }

    @Override
    public long stbtt_FindSVGDoc(KStbTtFontInfo info, int gl) {
        return STBTruetype.stbtt_FindSVGDoc(KStbTrueTypeWrapper.wrap(info), gl);
    }

    @Override
    public int stbtt_GetCodepointSVG(KStbTtFontInfo info, int unicode_codepoint, LongBuffer svg) {
        return STBTruetype.stbtt_GetCodepointSVG(KStbTrueTypeWrapper.wrap(info), unicode_codepoint, svg);
    }

    @Override
    public int stbtt_GetGlyphSVG(KStbTtFontInfo info, int gl, LongBuffer svg) {
        return STBTruetype.stbtt_GetGlyphSVG(KStbTrueTypeWrapper.wrap(info), gl, svg);
    }

    @Override
    public void stbtt_FreeBitmap(ByteBuffer bitmap, long userdata) {
        STBTruetype.stbtt_FreeBitmap(bitmap, userdata);
    }

    @Override
    public void stbtt_FreeBitmap(ByteBuffer bitmap) {
        STBTruetype.stbtt_FreeBitmap(bitmap);
    }

    @Override
    public @Nullable ByteBuffer stbtt_GetCodepointBitmap(KStbTtFontInfo info, float scale_x, float scale_y, int codepoint, IntBuffer width, IntBuffer height, @Nullable IntBuffer xoff, @Nullable IntBuffer yoff) {
        return STBTruetype.ByteBuffer stbtt_GetCodepointBitmap(KStbTrueTypeWrapper.wrap(info), scale_x, scale_y, codepoint, width, height, IntBuffer, IntBuffer);
    }

    @Override
    public @Nullable ByteBuffer stbtt_GetCodepointBitmapSubpixel(KStbTtFontInfo info, float scale_x, float scale_y, float shift_x, float shift_y, int codepoint, IntBuffer width, IntBuffer height, @Nullable IntBuffer xoff, @Nullable IntBuffer yoff) {
        return STBTruetype.ByteBuffer stbtt_GetCodepointBitmapSubpixel(KStbTrueTypeWrapper.wrap(info), scale_x, scale_y, shift_x, shift_y, codepoint, width, height, IntBuffer, IntBuffer);
    }

    @Override
    public void stbtt_MakeCodepointBitmap(KStbTtFontInfo info, ByteBuffer output, int out_w, int out_h, int out_stride, float scale_x, float scale_y, int codepoint) {
        STBTruetype.stbtt_MakeCodepointBitmap(KStbTrueTypeWrapper.wrap(info), output, out_w, out_h, out_stride, scale_x, scale_y, codepoint);
    }

    @Override
    public void stbtt_MakeCodepointBitmapSubpixel(KStbTtFontInfo info, ByteBuffer output, int out_w, int out_h, int out_stride, float scale_x, float scale_y, float shift_x, float shift_y, int codepoint) {
        STBTruetype.stbtt_MakeCodepointBitmapSubpixel(KStbTrueTypeWrapper.wrap(info), output, out_w, out_h, out_stride, scale_x, scale_y, shift_x, shift_y, codepoint);
    }

    @Override
    public void stbtt_MakeCodepointBitmapSubpixelPrefilter(KStbTtFontInfo info, ByteBuffer output, int out_w, int out_h, int out_stride, float scale_x, float scale_y, float shift_x, float shift_y, int oversample_x, int oversample_y, FloatBuffer sub_x, FloatBuffer sub_y, int codepoint) {
        STBTruetype.stbtt_MakeCodepointBitmapSubpixelPrefilter(KStbTrueTypeWrapper.wrap(info), output, out_w, out_h, out_stride, scale_x, scale_y, shift_x, shift_y, oversample_x, oversample_y, sub_x, sub_y, codepoint);
    }

    @Override
    public void stbtt_GetCodepointBitmapBox(KStbTtFontInfo font, int codepoint, float scale_x, float scale_y, @Nullable IntBuffer ix0, @Nullable IntBuffer iy0, @Nullable IntBuffer ix1, @Nullable IntBuffer iy1) {
        STBTruetype.stbtt_GetCodepointBitmapBox(font, codepoint, scale_x, scale_y, IntBuffer, IntBuffer, IntBuffer, IntBuffer);
    }

    @Override
    public void stbtt_GetCodepointBitmapBoxSubpixel(KStbTtFontInfo font, int codepoint, float scale_x, float scale_y, float shift_x, float shift_y, @Nullable IntBuffer ix0, @Nullable IntBuffer iy0, @Nullable IntBuffer ix1, @Nullable IntBuffer iy1) {
        STBTruetype.stbtt_GetCodepointBitmapBoxSubpixel(font, codepoint, scale_x, scale_y, shift_x, shift_y, IntBuffer, IntBuffer, IntBuffer, IntBuffer);
    }

    @Override
    public @Nullable ByteBuffer stbtt_GetGlyphBitmap(KStbTtFontInfo info, float scale_x, float scale_y, int glyph, IntBuffer width, IntBuffer height, @Nullable IntBuffer xoff, @Nullable IntBuffer yoff) {
        return STBTruetype.ByteBuffer stbtt_GetGlyphBitmap(KStbTrueTypeWrapper.wrap(info), scale_x, scale_y, glyph, width, height, IntBuffer, IntBuffer);
    }

    @Override
    public @Nullable ByteBuffer stbtt_GetGlyphBitmapSubpixel(KStbTtFontInfo info, float scale_x, float scale_y, float shift_x, float shift_y, int glyph, IntBuffer width, IntBuffer height, @Nullable IntBuffer xoff, @Nullable IntBuffer yoff) {
        return STBTruetype.ByteBuffer stbtt_GetGlyphBitmapSubpixel(KStbTrueTypeWrapper.wrap(info), scale_x, scale_y, shift_x, shift_y, glyph, width, height, IntBuffer, IntBuffer);
    }

    @Override
    public void stbtt_MakeGlyphBitmap(KStbTtFontInfo info, ByteBuffer output, int out_w, int out_h, int out_stride, float scale_x, float scale_y, int glyph) {
        STBTruetype.stbtt_MakeGlyphBitmap(KStbTrueTypeWrapper.wrap(info), output, out_w, out_h, out_stride, scale_x, scale_y, glyph);
    }

    @Override
    public void stbtt_MakeGlyphBitmapSubpixel(KStbTtFontInfo info, ByteBuffer output, int out_w, int out_h, int out_stride, float scale_x, float scale_y, float shift_x, float shift_y, int glyph) {
        STBTruetype.stbtt_MakeGlyphBitmapSubpixel(KStbTrueTypeWrapper.wrap(info), output, out_w, out_h, out_stride, scale_x, scale_y, shift_x, shift_y, glyph);
    }

    @Override
    public void stbtt_MakeGlyphBitmapSubpixelPrefilter(KStbTtFontInfo info, ByteBuffer output, int out_w, int out_h, int out_stride, float scale_x, float scale_y, float shift_x, float shift_y, int oversample_x, int oversample_y, FloatBuffer sub_x, FloatBuffer sub_y, int glyph) {
        STBTruetype.stbtt_MakeGlyphBitmapSubpixelPrefilter(KStbTrueTypeWrapper.wrap(info), output, out_w, out_h, out_stride, scale_x, scale_y, shift_x, shift_y, oversample_x, oversample_y, sub_x, sub_y, glyph);
    }

    @Override
    public void stbtt_GetGlyphBitmapBox(KStbTtFontInfo font, int glyph, float scale_x, float scale_y, @Nullable IntBuffer ix0, @Nullable IntBuffer iy0, @Nullable IntBuffer ix1, @Nullable IntBuffer iy1) {
        STBTruetype.stbtt_GetGlyphBitmapBox(font, glyph, scale_x, scale_y, IntBuffer, IntBuffer, IntBuffer, IntBuffer);
    }

    @Override
    public void stbtt_GetGlyphBitmapBoxSubpixel(KStbTtFontInfo font, int glyph, float scale_x, float scale_y, float shift_x, float shift_y, @Nullable IntBuffer ix0, @Nullable IntBuffer iy0, @Nullable IntBuffer ix1, @Nullable IntBuffer iy1) {
        STBTruetype.stbtt_GetGlyphBitmapBoxSubpixel(font, glyph, scale_x, scale_y, shift_x, shift_y, IntBuffer, IntBuffer, IntBuffer, IntBuffer);
    }

    @Override
    public void stbtt_Rasterize(KStbTtBitmap result, float flatness_in_pixels, KStbTtVertex[] vertices, float scale_x, float scale_y, float shift_x, float shift_y, int x_off, int y_off, boolean invert) {
        STBTruetype.stbtt_Rasterize(result, flatness_in_pixels, vertices, scale_x, scale_y, shift_x, shift_y, x_off, y_off, invert);
    }

    @Override
    public void stbtt_FreeSDF(ByteBuffer bitmap, long userdata) {
        STBTruetype.stbtt_FreeSDF(bitmap, userdata);
    }

    @Override
    public void stbtt_FreeSDF(ByteBuffer bitmap) {
        STBTruetype.stbtt_FreeSDF(bitmap);
    }

    @Override
    public @Nullable ByteBuffer stbtt_GetGlyphSDF(KStbTtFontInfo font, float scale, int glyph, int padding, byte onedge_value, float pixel_dist_scale, IntBuffer width, IntBuffer height, IntBuffer xoff, IntBuffer yoff) {
        return STBTruetype.ByteBuffer stbtt_GetGlyphSDF(KStbTrueTypeWrapper.wrap(font), scale, glyph, padding, onedge_value, pixel_dist_scale, width, height, xoff, yoff);
    }

    @Override
    public @Nullable ByteBuffer stbtt_GetCodepointSDF(KStbTtFontInfo font, float scale, int codepoint, int padding, byte onedge_value, float pixel_dist_scale, IntBuffer width, IntBuffer height, IntBuffer xoff, IntBuffer yoff) {
        return STBTruetype.ByteBuffer stbtt_GetCodepointSDF(KStbTrueTypeWrapper.wrap(font), scale, codepoint, padding, onedge_value, pixel_dist_scale, width, height, xoff, yoff);
    }

    @Override
    public int stbtt_FindMatchingFont(ByteBuffer fontdata, ByteBuffer name, int flags) {
        return STBTruetype.stbtt_FindMatchingFont(fontdata, name, flags);
    }

    @Override
    public int stbtt_FindMatchingFont(ByteBuffer fontdata, CharSequence name, int flags) {
        return STBTruetype.stbtt_FindMatchingFont(fontdata, name, flags);
    }

    @Override
    public boolean stbtt_CompareUTF8toUTF16_bigendian(ByteBuffer s1, ByteBuffer s2) {
        return STBTruetype.stbtt_CompareUTF8toUTF16_bigendian(s1, s2);
    }

    @Override
    public @Nullable ByteBuffer stbtt_GetFontNameString(KStbTtFontInfo font, int platformID, int encodingID, int languageID, int nameID) {
        return STBTruetype.ByteBuffer stbtt_GetFontNameString(KStbTrueTypeWrapper.wrap(font), platformID, encodingID, languageID, nameID);
    }

    @Override
    public void stbtt_GetBakedQuad(KStbTtBakedChar[] chardata, int pw, int ph, int char_index, float[] xpos, float[] ypos, KStbTtAlignedQuad q, boolean opengl_fillrule) {
        STBTruetype.stbtt_GetBakedQuad(chardata, pw, ph, char_index, xpos, ypos, q, opengl_fillrule);
    }

    @Override
    public void stbtt_GetScaledFontVMetrics(ByteBuffer fontdata, int index, float size, float[] ascent, float[] descent, float[] lineGap) {
        STBTruetype.stbtt_GetScaledFontVMetrics(fontdata, index, size, ascent, descent, lineGap);
    }

    @Override
    public void stbtt_GetPackedQuad(KStbTtPackedChar[] chardata, int pw, int ph, int char_index, float[] xpos, float[] ypos, KStbTtAlignedQuad q, boolean align_to_integer) {
        STBTruetype.stbtt_GetPackedQuad(chardata, pw, ph, char_index, xpos, ypos, q, align_to_integer);
    }

    @Override
    public void stbtt_GetFontVMetrics(KStbTtFontInfo info, int @Nullable [] ascent, int @Nullable [] descent, int @Nullable [] lineGap) {
        STBTruetype.stbtt_GetFontVMetrics(KStbTrueTypeWrapper.wrap(info), @Nullable, @Nullable, @Nullable);
    }

    @Override
    public boolean stbtt_GetFontVMetricsOS2(KStbTtFontInfo info, int @Nullable [] typoAscent, int @Nullable [] typoDescent, int @Nullable [] typoLineGap) {
        return STBTruetype.stbtt_GetFontVMetricsOS2(KStbTrueTypeWrapper.wrap(info), @Nullable, @Nullable, @Nullable);
    }

    @Override
    public void stbtt_GetFontBoundingBox(KStbTtFontInfo info, int[] x0, int[] y0, int[] x1, int[] y1) {
        STBTruetype.stbtt_GetFontBoundingBox(KStbTrueTypeWrapper.wrap(info), x0, y0, x1, y1);
    }

    @Override
    public void stbtt_GetCodepointHMetrics(KStbTtFontInfo info, int codepoint, int @Nullable [] advanceWidth, int @Nullable [] leftSideBearing) {
        STBTruetype.stbtt_GetCodepointHMetrics(KStbTrueTypeWrapper.wrap(info), codepoint, @Nullable, @Nullable);
    }

    @Override
    public boolean stbtt_GetCodepointBox(KStbTtFontInfo info, int codepoint, int @Nullable [] x0, int @Nullable [] y0, int @Nullable [] x1, int @Nullable [] y1) {
        return STBTruetype.stbtt_GetCodepointBox(KStbTrueTypeWrapper.wrap(info), codepoint, @Nullable, @Nullable, @Nullable, @Nullable);
    }

    @Override
    public void stbtt_GetGlyphHMetrics(KStbTtFontInfo info, int glyph_index, int @Nullable [] advanceWidth, int @Nullable [] leftSideBearing) {
        STBTruetype.stbtt_GetGlyphHMetrics(KStbTrueTypeWrapper.wrap(info), glyph_index, @Nullable, @Nullable);
    }

    @Override
    public boolean stbtt_GetGlyphBox(KStbTtFontInfo info, int glyph_index, int @Nullable [] x0, int @Nullable [] y0, int @Nullable [] x1, int @Nullable [] y1) {
        return STBTruetype.stbtt_GetGlyphBox(KStbTrueTypeWrapper.wrap(info), glyph_index, @Nullable, @Nullable, @Nullable, @Nullable);
    }

    @Override
    public @Nullable ByteBuffer stbtt_GetCodepointBitmap(KStbTtFontInfo info, float scale_x, float scale_y, int codepoint, int[] width, int[] height, int @Nullable [] xoff, int @Nullable [] yoff) {
        return STBTruetype.ByteBuffer stbtt_GetCodepointBitmap(KStbTrueTypeWrapper.wrap(info), scale_x, scale_y, codepoint, width, height, @Nullable, @Nullable);
    }

    @Override
    public @Nullable ByteBuffer stbtt_GetCodepointBitmapSubpixel(KStbTtFontInfo info, float scale_x, float scale_y, float shift_x, float shift_y, int codepoint, int[] width, int[] height, int @Nullable [] xoff, int @Nullable [] yoff) {
        return STBTruetype.ByteBuffer stbtt_GetCodepointBitmapSubpixel(KStbTrueTypeWrapper.wrap(info), scale_x, scale_y, shift_x, shift_y, codepoint, width, height, @Nullable, @Nullable);
    }

    @Override
    public void stbtt_MakeCodepointBitmapSubpixelPrefilter(KStbTtFontInfo info, ByteBuffer output, int out_w, int out_h, int out_stride, float scale_x, float scale_y, float shift_x, float shift_y, int oversample_x, int oversample_y, float[] sub_x, float[] sub_y, int codepoint) {
        STBTruetype.stbtt_MakeCodepointBitmapSubpixelPrefilter(KStbTrueTypeWrapper.wrap(info), output, out_w, out_h, out_stride, scale_x, scale_y, shift_x, shift_y, oversample_x, oversample_y, sub_x, sub_y, codepoint);
    }

    @Override
    public void stbtt_GetCodepointBitmapBox(KStbTtFontInfo font, int codepoint, float scale_x, float scale_y, int @Nullable [] ix0, int @Nullable [] iy0, int @Nullable [] ix1, int @Nullable [] iy1) {
        STBTruetype.stbtt_GetCodepointBitmapBox(KStbTrueTypeWrapper.wrap(font), codepoint, scale_x, scale_y, @Nullable, @Nullable, @Nullable, @Nullable);
    }

    @Override
    public void stbtt_GetCodepointBitmapBoxSubpixel(KStbTtFontInfo font, int codepoint, float scale_x, float scale_y, float shift_x, float shift_y, int @Nullable [] ix0, int @Nullable [] iy0, int @Nullable [] ix1, int @Nullable [] iy1) {
        STBTruetype.stbtt_GetCodepointBitmapBoxSubpixel(KStbTrueTypeWrapper.wrap(font), codepoint, scale_x, scale_y, shift_x, shift_y, @Nullable, @Nullable, @Nullable, @Nullable);
    }

    @Override
    public @Nullable ByteBuffer stbtt_GetGlyphBitmap(KStbTtFontInfo info, float scale_x, float scale_y, int glyph, int[] width, int[] height, int @Nullable [] xoff, int @Nullable [] yoff) {
        return STBTruetype.stbtt_GetGlyphBitmap(KStbTrueTypeWrapper.wrap(info), scale_x, scale_y, glyph, width, height, xoff, yoff);
    }

    @Override
    public @Nullable ByteBuffer stbtt_GetGlyphBitmapSubpixel(KStbTtFontInfo info, float scale_x, float scale_y, float shift_x, float shift_y, int glyph, int[] width, int[] height, int @Nullable [] xoff, int @Nullable [] yoff) {
        return STBTruetype.stbtt_GetGlyphBitmapSubpixel(KStbTrueTypeWrapper.wrap(info), scale_x, scale_y, shift_x, shift_y, glyph, width, height, @Nullable, @Nullable);
    }

    @Override
    public void stbtt_MakeGlyphBitmapSubpixelPrefilter(KStbTtFontInfo info, ByteBuffer output, int out_w, int out_h, int out_stride, float scale_x, float scale_y, float shift_x, float shift_y, int oversample_x, int oversample_y, float[] sub_x, float[] sub_y, int glyph) {
        STBTruetype.stbtt_MakeGlyphBitmapSubpixelPrefilter(KStbTrueTypeWrapper.wrap(info), output, out_w, out_h, out_stride, scale_x, scale_y, shift_x, shift_y, oversample_x, oversample_y, sub_x, sub_y, glyph);
    }

    @Override
    public void stbtt_GetGlyphBitmapBox(KStbTtFontInfo font, int glyph, float scale_x, float scale_y, int @Nullable [] ix0, int @Nullable [] iy0, int @Nullable [] ix1, int @Nullable [] iy1) {
        STBTruetype.stbtt_GetGlyphBitmapBox(KStbTrueTypeWrapper.wrap(font), glyph, scale_x, scale_y, ix0, iy0, ix1, iy1);
    }

    @Override
    public void stbtt_GetGlyphBitmapBoxSubpixel(KStbTtFontInfo font, int glyph, float scale_x, float scale_y, float shift_x, float shift_y, int @Nullable [] ix0, int @Nullable [] iy0, int @Nullable [] ix1, int @Nullable [] iy1) {
        STBTruetype.stbtt_GetGlyphBitmapBoxSubpixel(KStbTrueTypeWrapper.wrap(font), glyph, scale_x, scale_y, shift_x, shift_y, ix0, iy0, ix1, iy1);
    }

    @Override
    public @Nullable ByteBuffer stbtt_GetGlyphSDF(KStbTtFontInfo font, float scale, int glyph, int padding, byte onedge_value, float pixel_dist_scale, int[] width, int[] height, int[] xoff, int[] yoff) {
        return STBTruetype.stbtt_GetGlyphSDF(KStbTrueTypeWrapper.wrap(font), scale, glyph, padding, onedge_value, pixel_dist_scale, width, height, xoff, yoff);
    }

    @Override
    public @Nullable ByteBuffer stbtt_GetCodepointSDF(KStbTtFontInfo font, float scale, int codepoint, int padding, byte onedge_value, float pixel_dist_scale, int[] width, int[] height, int[] xoff, int[] yoff) {
        return STBTruetype.stbtt_GetCodepointSDF(KStbTrueTypeWrapper.wrap(font), scale, codepoint, padding, onedge_value, pixel_dist_scale, width, height, xoff, yoff);
    }



}
