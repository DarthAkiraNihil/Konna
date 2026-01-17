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
import io.github.darthakiranihil.konna.libfrontend.stb.*;
import org.jspecify.annotations.Nullable;
import org.lwjgl.stb.STBTruetype;

import java.nio.*;

@KSingleton
@KExcludeFromGeneratedCoverageReport
public final class KStbTrueTypeLwjgl extends KObject implements KStbTrueType {

    @Override
    public int stbtt_BakeFontBitmap(KStbTtBakedChar[] chardata) {
        return STBTruetype.stbtt_BakeFontBitmap(chardata);
    }

    @Override
    public void stbtt_GetBakedQuad(KStbTtBakedChar[] chardata, int pw, int ph, int char_index, FloatBuffer xpos, FloatBuffer ypos, KStbTtAlignedQuad q, boolean opengl_fillrule) {
        STBTruetype.stbtt_GetBakedQuad(chardata, pw, ph, char_index, xpos, ypos, q, opengl_fillrule);
    }

    @Override
    public void stbtt_GetScaledFontVMetrics(FloatBuffer lineGap) {
        STBTruetype.stbtt_GetScaledFontVMetrics(lineGap);
    }

    @Override
    public boolean stbtt_PackBegin(long alloc_context) {
        return STBTruetype.stbtt_PackBegin(alloc_context);
    }

    @Override
    public boolean stbtt_PackBegin(@Nullable ByteBuffer pixels, int width, int height, int stride_in_bytes, int padding) {
        return STBTruetype.stbtt_PackBegin(ByteBuffer, width, height, stride_in_bytes, padding);
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
    public boolean stbtt_PackFontRange(KStbTtPackedChar[] chardata_for_range) {
        return STBTruetype.stbtt_PackFontRange(chardata_for_range);
    }

    @Override
    public boolean stbtt_PackFontRanges(KStbTtPackRange[] ranges) {
        return STBTruetype.stbtt_PackFontRanges(ranges);
    }

    @Override
    public void stbtt_PackSetOversampling(int v_oversample) {
        STBTruetype.stbtt_PackSetOversampling(v_oversample);
    }

    @Override
    public void stbtt_PackSetSkipMissingCodepoints(boolean skip) {
        STBTruetype.stbtt_PackSetSkipMissingCodepoints(skip);
    }

    @Override
    public void stbtt_GetPackedQuad(boolean align_to_integer) {
        STBTruetype.stbtt_GetPackedQuad(align_to_integer);
    }

    @Override
    public int stbtt_PackFontRangesGatherRects(KStbRpRect[] rects) {
        return STBTruetype.stbtt_PackFontRangesGatherRects(rects);
    }

    @Override
    public void stbtt_PackFontRangesPackRects(KStbRpRect[] rects) {
        STBTruetype.stbtt_PackFontRangesPackRects(rects);
    }

    @Override
    public boolean stbtt_PackFontRangesRenderIntoRects(KStbRpRect[] rects) {
        return STBTruetype.stbtt_PackFontRangesRenderIntoRects(rects);
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
    public boolean stbtt_InitFont(ByteBuffer data, int offset) {
        return STBTruetype.stbtt_InitFont(data, offset);
    }

    @Override
    public boolean stbtt_InitFont(ByteBuffer data) {
        return STBTruetype.stbtt_InitFont(data);
    }

    @Override
    public int stbtt_FindGlyphIndex(KStbTtFontInfo info, int unicode_codepoint) {
        return STBTruetype.stbtt_FindGlyphIndex(info, unicode_codepoint);
    }

    @Override
    public float stbtt_ScaleForPixelHeight(KStbTtFontInfo info, float pixels) {
        return STBTruetype.stbtt_ScaleForPixelHeight(info, pixels);
    }

    @Override
    public float stbtt_ScaleForMappingEmToPixels(KStbTtFontInfo info, float pixels) {
        return STBTruetype.stbtt_ScaleForMappingEmToPixels(info, pixels);
    }

    @Override
    public void stbtt_GetFontVMetrics(@Nullable IntBuffer lineGap) {
        STBTruetype.stbtt_GetFontVMetrics(IntBuffer);
    }

    @Override
    public boolean stbtt_GetFontVMetricsOS2(@Nullable IntBuffer typoLineGap) {
        return STBTruetype.stbtt_GetFontVMetricsOS2(IntBuffer);
    }

    @Override
    public void stbtt_GetFontBoundingBox(IntBuffer y1) {
        STBTruetype.stbtt_GetFontBoundingBox(y1);
    }

    @Override
    public void stbtt_GetCodepointHMetrics(@Nullable IntBuffer leftSideBearing) {
        STBTruetype.stbtt_GetCodepointHMetrics(IntBuffer);
    }

    @Override
    public int stbtt_GetCodepointKernAdvance(KStbTtFontInfo info, int ch1, int ch2) {
        return STBTruetype.stbtt_GetCodepointKernAdvance(info, ch1, ch2);
    }

    @Override
    public boolean stbtt_GetCodepointBox(@Nullable IntBuffer y1) {
        return STBTruetype.stbtt_GetCodepointBox(IntBuffer);
    }

    @Override
    public void stbtt_GetGlyphHMetrics(@Nullable IntBuffer leftSideBearing) {
        STBTruetype.stbtt_GetGlyphHMetrics(IntBuffer);
    }

    @Override
    public int stbtt_GetGlyphKernAdvance(KStbTtFontInfo info, int glyph1, int glyph2) {
        return STBTruetype.stbtt_GetGlyphKernAdvance(info, glyph1, glyph2);
    }

    @Override
    public boolean stbtt_GetGlyphBox(@Nullable IntBuffer y1) {
        return STBTruetype.stbtt_GetGlyphBox(IntBuffer);
    }

    @Override
    public int stbtt_GetKerningTableLength(KStbTtFontInfo info) {
        return STBTruetype.stbtt_GetKerningTableLength(info);
    }

    @Override
    public int stbtt_GetKerningTable(KStbTtKerningEntry[] table) {
        return STBTruetype.stbtt_GetKerningTable(table);
    }

    @Override
    public boolean stbtt_IsGlyphEmpty(KStbTtFontInfo info, int glyph_index) {
        return STBTruetype.stbtt_IsGlyphEmpty(info, glyph_index);
    }

    @Override
    public int stbtt_GetCodepointShape(LongBuffer vertices) {
        return STBTruetype.stbtt_GetCodepointShape(vertices);
    }

    @Override
    public @Nullable KStbTtVertex[] stbtt_GetCodepointShape(KStbTtFontInfo info, int unicode_codepoint) {
        return STBTruetype.KStbTtVertex[] stbtt_GetCodepointShape(info, unicode_codepoint);
    }

    @Override
    public int stbtt_GetGlyphShape(LongBuffer vertices) {
        return STBTruetype.stbtt_GetGlyphShape(vertices);
    }

    @Override
    public @Nullable KStbTtVertex[] stbtt_GetGlyphShape(KStbTtFontInfo info, int glyph_index) {
        return STBTruetype.KStbTtVertex[] stbtt_GetGlyphShape(info, glyph_index);
    }

    @Override
    public void stbtt_FreeShape(KStbTtVertex[] vertices) {
        STBTruetype.stbtt_FreeShape(vertices);
    }

    @Override
    public long stbtt_FindSVGDoc(KStbTtFontInfo info, int gl) {
        return STBTruetype.stbtt_FindSVGDoc(info, gl);
    }

    @Override
    public int stbtt_GetCodepointSVG(LongBuffer svg) {
        return STBTruetype.stbtt_GetCodepointSVG(svg);
    }

    @Override
    public int stbtt_GetGlyphSVG(LongBuffer svg) {
        return STBTruetype.stbtt_GetGlyphSVG(svg);
    }

    @Override
    public void stbtt_FreeBitmap(long userdata) {
        STBTruetype.stbtt_FreeBitmap(userdata);
    }

    @Override
    public void stbtt_FreeBitmap(ByteBuffer bitmap) {
        STBTruetype.stbtt_FreeBitmap(bitmap);
    }

    @Override
    public @Nullable ByteBuffer stbtt_GetCodepointBitmap(@Nullable IntBuffer yoff) {
        return STBTruetype.ByteBuffer stbtt_GetCodepointBitmap(IntBuffer);
    }

    @Override
    public @Nullable ByteBuffer stbtt_GetCodepointBitmapSubpixel(@Nullable IntBuffer yoff) {
        return STBTruetype.ByteBuffer stbtt_GetCodepointBitmapSubpixel(IntBuffer);
    }

    @Override
    public void stbtt_MakeCodepointBitmap(ByteBuffer output, int out_w, int out_h, int out_stride, float scale_x, float scale_y, int codepoint) {
        STBTruetype.stbtt_MakeCodepointBitmap(output, out_w, out_h, out_stride, scale_x, scale_y, codepoint);
    }

    @Override
    public void stbtt_MakeCodepointBitmapSubpixel(ByteBuffer output, int out_w, int out_h, int out_stride, float scale_x, float scale_y, float shift_x, float shift_y, int codepoint) {
        STBTruetype.stbtt_MakeCodepointBitmapSubpixel(output, out_w, out_h, out_stride, scale_x, scale_y, shift_x, shift_y, codepoint);
    }

    @Override
    public void stbtt_MakeCodepointBitmapSubpixelPrefilter(FloatBuffer sub_y, int codepoint) {
        STBTruetype.stbtt_MakeCodepointBitmapSubpixelPrefilter(sub_y, codepoint);
    }

    @Override
    public void stbtt_GetCodepointBitmapBox(@Nullable IntBuffer iy1) {
        STBTruetype.stbtt_GetCodepointBitmapBox(IntBuffer);
    }

    @Override
    public void stbtt_GetCodepointBitmapBoxSubpixel(@Nullable IntBuffer iy1) {
        STBTruetype.stbtt_GetCodepointBitmapBoxSubpixel(IntBuffer);
    }

    @Override
    public @Nullable ByteBuffer stbtt_GetGlyphBitmap(@Nullable IntBuffer yoff) {
        return STBTruetype.ByteBuffer stbtt_GetGlyphBitmap(IntBuffer);
    }

    @Override
    public @Nullable ByteBuffer stbtt_GetGlyphBitmapSubpixel(@Nullable IntBuffer yoff) {
        return STBTruetype.ByteBuffer stbtt_GetGlyphBitmapSubpixel(IntBuffer);
    }

    @Override
    public void stbtt_MakeGlyphBitmap(ByteBuffer output, int out_w, int out_h, int out_stride, float scale_x, float scale_y, int glyph) {
        STBTruetype.stbtt_MakeGlyphBitmap(output, out_w, out_h, out_stride, scale_x, scale_y, glyph);
    }

    @Override
    public void stbtt_MakeGlyphBitmapSubpixel(ByteBuffer output, int out_w, int out_h, int out_stride, float scale_x, float scale_y, float shift_x, float shift_y, int glyph) {
        STBTruetype.stbtt_MakeGlyphBitmapSubpixel(output, out_w, out_h, out_stride, scale_x, scale_y, shift_x, shift_y, glyph);
    }

    @Override
    public void stbtt_MakeGlyphBitmapSubpixelPrefilter(FloatBuffer sub_y, int glyph) {
        STBTruetype.stbtt_MakeGlyphBitmapSubpixelPrefilter(sub_y, glyph);
    }

    @Override
    public void stbtt_GetGlyphBitmapBox(@Nullable IntBuffer iy1) {
        STBTruetype.stbtt_GetGlyphBitmapBox(IntBuffer);
    }

    @Override
    public void stbtt_GetGlyphBitmapBoxSubpixel(@Nullable IntBuffer iy1) {
        STBTruetype.stbtt_GetGlyphBitmapBoxSubpixel(IntBuffer);
    }

    @Override
    public void stbtt_Rasterize(boolean invert) {
        STBTruetype.stbtt_Rasterize(invert);
    }

    @Override
    public void stbtt_FreeSDF(long userdata) {
        STBTruetype.stbtt_FreeSDF(userdata);
    }

    @Override
    public void stbtt_FreeSDF(ByteBuffer bitmap) {
        STBTruetype.stbtt_FreeSDF(bitmap);
    }

    @Override
    public @Nullable ByteBuffer stbtt_GetGlyphSDF(IntBuffer yoff) {
        return STBTruetype.ByteBuffer stbtt_GetGlyphSDF(yoff);
    }

    @Override
    public @Nullable ByteBuffer stbtt_GetCodepointSDF(IntBuffer yoff) {
        return STBTruetype.ByteBuffer stbtt_GetCodepointSDF(yoff);
    }

    @Override
    public int stbtt_FindMatchingFont(ByteBuffer name, int flags) {
        return STBTruetype.stbtt_FindMatchingFont(name, flags);
    }

    @Override
    public int stbtt_FindMatchingFont(CharSequence name, int flags) {
        return STBTruetype.stbtt_FindMatchingFont(name, flags);
    }

    @Override
    public boolean stbtt_CompareUTF8toUTF16_bigendian(ByteBuffer s2) {
        return STBTruetype.stbtt_CompareUTF8toUTF16_bigendian(s2);
    }

    @Override
    public @Nullable ByteBuffer stbtt_GetFontNameString(KStbTtFontInfo font, int platformID, int encodingID, int languageID, int nameID) {
        return STBTruetype.ByteBuffer stbtt_GetFontNameString(font, platformID, encodingID, languageID, nameID);
    }

    @Override
    public void stbtt_GetBakedQuad(boolean opengl_fillrule) {
        STBTruetype.stbtt_GetBakedQuad(opengl_fillrule);
    }

    @Override
    public void stbtt_GetScaledFontVMetrics(float[] lineGap) {
        STBTruetype.stbtt_GetScaledFontVMetrics(lineGap);
    }

    @Override
    public void stbtt_GetPackedQuad(KStbTtBakedChar[] chardata, int pw, int ph, int char_index, float[] xpos, float[] ypos, KStbTtAlignedQuad q, boolean align_to_integer) {
        STBTruetype.stbtt_GetPackedQuad(chardata, pw, ph, char_index, xpos, ypos, q, align_to_integer);
    }

    @Override
    public void stbtt_GetFontVMetrics(int @Nullable [] lineGap) {
        STBTruetype.stbtt_GetFontVMetrics(@Nullable);
    }

    @Override
    public boolean stbtt_GetFontVMetricsOS2(int @Nullable [] typoLineGap) {
        return STBTruetype.stbtt_GetFontVMetricsOS2(@Nullable);
    }

    @Override
    public void stbtt_GetFontBoundingBox(int[] y1) {
        STBTruetype.stbtt_GetFontBoundingBox(y1);
    }

    @Override
    public void stbtt_GetCodepointHMetrics(int @Nullable [] leftSideBearing) {
        STBTruetype.stbtt_GetCodepointHMetrics(@Nullable);
    }

    @Override
    public boolean stbtt_GetCodepointBox(int @Nullable [] y1) {
        return STBTruetype.stbtt_GetCodepointBox(@Nullable);
    }

    @Override
    public void stbtt_GetGlyphHMetrics(int @Nullable [] leftSideBearing) {
        STBTruetype.stbtt_GetGlyphHMetrics(@Nullable);
    }

    @Override
    public boolean stbtt_GetGlyphBox(int @Nullable [] y1) {
        return STBTruetype.stbtt_GetGlyphBox(@Nullable);
    }

    @Override
    public @Nullable ByteBuffer stbtt_GetCodepointBitmap(int @Nullable [] yoff) {
        return STBTruetype.ByteBuffer stbtt_GetCodepointBitmap(@Nullable);
    }

    @Override
    public @Nullable ByteBuffer stbtt_GetCodepointBitmapSubpixel(int @Nullable [] yoff) {
        return STBTruetype.ByteBuffer stbtt_GetCodepointBitmapSubpixel(@Nullable);
    }

    @Override
    public void stbtt_MakeCodepointBitmapSubpixelPrefilter(float[] sub_y, int codepoint) {
        STBTruetype.stbtt_MakeCodepointBitmapSubpixelPrefilter(sub_y, codepoint);
    }

    @Override
    public void stbtt_GetCodepointBitmapBox(int @Nullable [] iy1) {
        STBTruetype.stbtt_GetCodepointBitmapBox(@Nullable);
    }

    @Override
    public void stbtt_GetCodepointBitmapBoxSubpixel(int @Nullable [] iy1) {
        STBTruetype.stbtt_GetCodepointBitmapBoxSubpixel(@Nullable);
    }

    @Override
    public @Nullable ByteBuffer stbtt_GetGlyphBitmap(int @Nullable [] yoff) {
        return STBTruetype.ByteBuffer stbtt_GetGlyphBitmap(@Nullable);
    }

    @Override
    public @Nullable ByteBuffer stbtt_GetGlyphBitmapSubpixel(int @Nullable [] yoff) {
        return STBTruetype.ByteBuffer stbtt_GetGlyphBitmapSubpixel(@Nullable);
    }

    @Override
    public void stbtt_MakeGlyphBitmapSubpixelPrefilter(float[] sub_y, int glyph) {
        STBTruetype.stbtt_MakeGlyphBitmapSubpixelPrefilter(sub_y, glyph);
    }

    @Override
    public void stbtt_GetGlyphBitmapBox(int @Nullable [] iy1) {
        STBTruetype.stbtt_GetGlyphBitmapBox(@Nullable);
    }

    @Override
    public void stbtt_GetGlyphBitmapBoxSubpixel(int @Nullable [] iy1) {
        STBTruetype.stbtt_GetGlyphBitmapBoxSubpixel(iy1);
    }

    @Override
    public @Nullable ByteBuffer stbtt_GetGlyphSDF(
        KStbTtFontInfo font,
        float scale,
        int glyph,
        int padding,
        byte onedge_value,
        float pixel_dist_scale,
        int[] width,
        int[] height,
        int[] xoff,
        int[] yoffint
    ) {
        return STBTruetype.stbtt_GetGlyphSDF(
            font,
            scale,
            glyph,
            padding,
            onedge_value,
            pixel_dist_scale,
            width,
            height,
            xoff,
            yoffint
        );
    }

    @Override
    public @Nullable ByteBuffer stbtt_GetCodepointSDF(
        KStbTtFontInfo font,
        float scale,
        int codepoint,
        int padding,
        byte onedge_value,
        float pixel_dist_scale,
        int[] width,
        int[] height,
        int[] xoff,
        int[] yoff
    ) {
        return STBTruetype.stbtt_GetCodepointSDF(
            font,
            scale,
            codepoint,
            padding,
            onedge_value,
            pixel_dist_scale,
            width,
            height,
            xoff,
            yoff
        );
    }
}
