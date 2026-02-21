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

package io.github.darthakiranihil.konna.backend.spair.imgui;

import imgui.ImFontAtlas;
import imgui.type.ImInt;
import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.struct.ref.KIntReference;
import io.github.darthakiranihil.konna.core.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImFont;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImFontAtlas;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImFontConfig;

import java.nio.ByteBuffer;

@KExcludeFromGeneratedCoverageReport
final class KImFontAtlasSpair implements KImFontAtlas {

    private final ImFontAtlas boxed;

    KImFontAtlasSpair(final ImFontAtlas boxed) {
        this.boxed = boxed;
    }

    @Override
    public void destroy() {
        this.boxed.destroy();
    }

    @Override
    public KImFont addFontDefault() {
        return KImGuiSpairUnwrapper.wrap(this.boxed.addFontDefault());
    }

    @Override
    public KImFont addFontDefault(final KImFontConfig fontConfig) {
        return KImGuiSpairUnwrapper.wrap(this.boxed.addFontDefault(KImGuiSpairUnboxer.unbox(
            fontConfig)));
    }

    @Override
    public KImFont addFontFromFileTTF(final String filename, float sizePixels) {
        return KImGuiSpairUnwrapper.wrap(this.boxed.addFontFromFileTTF(filename, sizePixels));
    }

    @Override
    public KImFont addFontFromFileTTF(
        final String filename,
        float sizePixels,
        final KImFontConfig fontConfig
    ) {
        return KImGuiSpairUnwrapper.wrap(this.boxed.addFontFromFileTTF(
            filename,
            sizePixels,
            KImGuiSpairUnboxer.unbox(fontConfig)
        ));
    }

    @Override
    public KImFont addFontFromFileTTF(
        final String filename,
        float sizePixels,
        final KImFontConfig fontConfig,
        final short[] glyphRanges
    ) {
        return KImGuiSpairUnwrapper.wrap(this.boxed.addFontFromFileTTF(
            filename,
            sizePixels,
            KImGuiSpairUnboxer.unbox(fontConfig),
            glyphRanges
        ));
    }

    @Override
    public KImFont addFontFromFileTTF(
        final String filename,
        float sizePixels,
        final short[] glyphRanges
    ) {
        return KImGuiSpairUnwrapper.wrap(this.boxed.addFontFromFileTTF(
            filename,
            sizePixels,
            glyphRanges
        ));
    }

    @Override
    public KImFont addFontFromMemoryTTF(final byte[] fontData, float sizePixels) {
        return KImGuiSpairUnwrapper.wrap(this.boxed.addFontFromMemoryTTF(fontData, sizePixels));
    }

    @Override
    public KImFont addFontFromMemoryTTF(
        final byte[] fontData,
        float sizePixels,
        final KImFontConfig fontConfig
    ) {
        return KImGuiSpairUnwrapper.wrap(this.boxed.addFontFromMemoryTTF(
            fontData,
            sizePixels,
            KImGuiSpairUnboxer.unbox(fontConfig)
        ));
    }

    @Override
    public KImFont addFontFromMemoryTTF(
        final byte[] fontData,
        float sizePixels,
        final KImFontConfig fontConfig,
        final short[] glyphRanges
    ) {
        return KImGuiSpairUnwrapper.wrap(this.boxed.addFontFromMemoryTTF(
            fontData,
            sizePixels,
            KImGuiSpairUnboxer.unbox(fontConfig),
            glyphRanges
        ));
    }

    @Override
    public KImFont addFontFromMemoryTTF(
        final byte[] fontData,
        float sizePixels,
        final short[] glyphRanges
    ) {
        return KImGuiSpairUnwrapper.wrap(this.boxed.addFontFromMemoryTTF(
            fontData,
            sizePixels,
            glyphRanges
        ));
    }

    @Override
    public KImFont addFontFromMemoryTTF(final byte[] fontData, int fontDataSize, float sizePixels) {
        return KImGuiSpairUnwrapper.wrap(this.boxed.addFontFromMemoryTTF(
            fontData,
            fontDataSize,
            sizePixels
        ));
    }

    @Override
    public KImFont addFontFromMemoryTTF(
        final byte[] fontData,
        int fontDataSize,
        float sizePixels,
        final KImFontConfig fontConfig
    ) {
        return KImGuiSpairUnwrapper.wrap(this.boxed.addFontFromMemoryTTF(
            fontData,
            fontDataSize,
            sizePixels,
            KImGuiSpairUnboxer.unbox(fontConfig)
        ));
    }

    @Override
    public KImFont addFontFromMemoryTTF(
        final byte[] fontData,
        int fontDataSize,
        float sizePixels,
        final KImFontConfig fontConfig,
        final short[] glyphRanges
    ) {
        return KImGuiSpairUnwrapper.wrap(this.boxed.addFontFromMemoryTTF(
            fontData,
            fontDataSize,
            sizePixels,
            KImGuiSpairUnboxer.unbox(fontConfig),
            glyphRanges
        ));
    }

    @Override
    public KImFont addFontFromMemoryTTF(
        final byte[] fontData,
        int fontDataSize,
        float sizePixels,
        final short[] glyphRanges
    ) {
        return KImGuiSpairUnwrapper.wrap(this.boxed.addFontFromMemoryTTF(
            fontData,
            fontDataSize,
            sizePixels,
            glyphRanges
        ));
    }

    @Override
    public KImFont addFontFromMemoryCompressedTTF(
        final byte[] compressedFontData,
        float sizePixels
    ) {
        return KImGuiSpairUnwrapper.wrap(this.boxed.addFontFromMemoryCompressedTTF(
            compressedFontData,
            sizePixels
        ));
    }

    @Override
    public KImFont addFontFromMemoryCompressedTTF(
        final byte[] compressedFontData,
        float sizePixels,
        final KImFontConfig fontConfig
    ) {
        return KImGuiSpairUnwrapper.wrap(this.boxed.addFontFromMemoryCompressedTTF(
            compressedFontData,
            sizePixels,
            KImGuiSpairUnboxer.unbox(fontConfig)
        ));
    }

    @Override
    public KImFont addFontFromMemoryCompressedTTF(
        final byte[] compressedFontData,
        float sizePixels,
        final KImFontConfig fontConfig,
        final short[] glyphRanges
    ) {
        return KImGuiSpairUnwrapper.wrap(this.boxed.addFontFromMemoryCompressedTTF(
            compressedFontData,
            sizePixels,
            KImGuiSpairUnboxer.unbox(fontConfig),
            glyphRanges
        ));
    }

    @Override
    public KImFont addFontFromMemoryCompressedTTF(
        final byte[] compressedFontData,
        float sizePixels,
        final short[] glyphRanges
    ) {
        return KImGuiSpairUnwrapper.wrap(this.boxed.addFontFromMemoryCompressedTTF(
            compressedFontData,
            sizePixels,
            glyphRanges
        ));
    }

    @Override
    public KImFont addFontFromMemoryCompressedTTF(
        final byte[] compressedFontData,
        int compressedFontDataSize,
        float sizePixels
    ) {
        return KImGuiSpairUnwrapper.wrap(this.boxed.addFontFromMemoryCompressedTTF(
            compressedFontData,
            compressedFontDataSize,
            sizePixels
        ));
    }

    @Override
    public KImFont addFontFromMemoryCompressedTTF(
        final byte[] compressedFontData,
        int compressedFontDataSize,
        float sizePixels,
        final KImFontConfig fontConfig
    ) {
        return KImGuiSpairUnwrapper.wrap(this.boxed.addFontFromMemoryCompressedTTF(
            compressedFontData,
            compressedFontDataSize,
            sizePixels,
            KImGuiSpairUnboxer.unbox(fontConfig)
        ));
    }

    @Override
    public KImFont addFontFromMemoryCompressedTTF(
        final byte[] compressedFontData,
        int compressedFontDataSize,
        float sizePixels,
        final KImFontConfig fontConfig,
        final short[] glyphRanges
    ) {
        return KImGuiSpairUnwrapper.wrap(this.boxed.addFontFromMemoryCompressedTTF(
            compressedFontData,
            compressedFontDataSize,
            sizePixels,
            KImGuiSpairUnboxer.unbox(fontConfig),
            glyphRanges
        ));
    }

    @Override
    public KImFont addFontFromMemoryCompressedTTF(
        final byte[] compressedFontData,
        int compressedFontDataSize,
        float sizePixels,
        final short[] glyphRanges
    ) {
        return KImGuiSpairUnwrapper.wrap(this.boxed.addFontFromMemoryCompressedTTF(
            compressedFontData,
            compressedFontDataSize,
            sizePixels,
            glyphRanges
        ));
    }

    @Override
    public KImFont addFontFromMemoryCompressedBase85TTF(
        final String compressedFontDataBase85,
        float sizePixels,
        final KImFontConfig fontConfig
    ) {
        return KImGuiSpairUnwrapper.wrap(this.boxed.addFontFromMemoryCompressedBase85TTF(
            compressedFontDataBase85,
            sizePixels,
            KImGuiSpairUnboxer.unbox(fontConfig)
        ));
    }

    @Override
    public KImFont addFontFromMemoryCompressedBase85TTF(
        final String compressedFontDataBase85,
        float sizePixels,
        final KImFontConfig fontConfig,
        final short[] glyphRanges
    ) {
        return KImGuiSpairUnwrapper.wrap(this.boxed.addFontFromMemoryCompressedBase85TTF(
            compressedFontDataBase85,
            sizePixels,
            KImGuiSpairUnboxer.unbox(fontConfig),
            glyphRanges
        ));
    }

    @Override
    public void clearInputData() {
        this.boxed.clearInputData();
    }

    @Override
    public void clearTexData() {
        this.boxed.clearTexData();
    }

    @Override
    public void clearFonts() {
        this.boxed.clearFonts();
    }

    @Override
    public void clear() {
        this.boxed.clear();
    }

    @Override
    public void setFreeTypeRenderer(boolean enabled) {
        this.boxed.setFreeTypeRenderer(enabled);
    }

    @Override
    public boolean build() {
        return this.boxed.build();
    }

    @Override
    public ByteBuffer getTexDataAsAlpha8(
        final KIntReference outWidth,
        final KIntReference outHeight
    ) {
        ImInt w = new ImInt(outWidth.get());
        ImInt h = new ImInt(outHeight.get());
        ByteBuffer result = this.boxed.getTexDataAsAlpha8(w, h);
        outWidth.set(w.get());
        outHeight.set(h.get());
        return result;
    }

    @Override
    public ByteBuffer getTexDataAsAlpha8(
        final KIntReference outWidth,
        final KIntReference outHeight,
        final KIntReference outBytesPerPixel
    ) {
        ImInt w = new ImInt(outWidth.get());
        ImInt h = new ImInt(outHeight.get());
        ImInt bpp = new ImInt(outBytesPerPixel.get());
        ByteBuffer result = this.boxed.getTexDataAsAlpha8(w, h, bpp);
        outWidth.set(w.get());
        outHeight.set(h.get());
        outBytesPerPixel.set(bpp.get());
        return result;
    }

    @Override
    public ByteBuffer getTexDataAsRGBA32(
        final KIntReference outWidth,
        final KIntReference outHeight
    ) {
        ImInt w = new ImInt(outWidth.get());
        ImInt h = new ImInt(outHeight.get());
        ByteBuffer result = this.boxed.getTexDataAsRGBA32(w, h);
        outWidth.set(w.get());
        outHeight.set(h.get());
        return result;
    }

    @Override
    public ByteBuffer getTexDataAsRGBA32(
        final KIntReference outWidth,
        final KIntReference outHeight,
        final KIntReference outBytesPerPixel
    ) {
        ImInt w = new ImInt(outWidth.get());
        ImInt h = new ImInt(outHeight.get());
        ImInt bpp = new ImInt(outBytesPerPixel.get());
        ByteBuffer result = this.boxed.getTexDataAsRGBA32(w, h, bpp);
        outWidth.set(w.get());
        outHeight.set(h.get());
        outBytesPerPixel.set(bpp.get());
        return result;
    }

    @Override
    public boolean isBuilt() {
        return this.boxed.isBuilt();
    }

    @Override
    public void setTexID(long textureID) {
        this.boxed.setTexID(textureID);
    }

    @Override
    public short[] getGlyphRangesDefault() {
        return this.boxed.getGlyphRangesDefault();
    }

    @Override
    public short[] getGlyphRangesGreek() {
        return this.boxed.getGlyphRangesGreek();
    }

    @Override
    public short[] getGlyphRangesKorean() {
        return this.boxed.getGlyphRangesKorean();
    }

    @Override
    public short[] getGlyphRangesJapanese() {
        return this.boxed.getGlyphRangesJapanese();
    }

    @Override
    public short[] getGlyphRangesChineseFull() {
        return this.boxed.getGlyphRangesChineseFull();
    }

    @Override
    public short[] getGlyphRangesChineseSimplifiedCommon() {
        return this.boxed.getGlyphRangesChineseSimplifiedCommon();
    }

    @Override
    public short[] getGlyphRangesCyrillic() {
        return this.boxed.getGlyphRangesCyrillic();
    }

    @Override
    public short[] getGlyphRangesThai() {
        return this.boxed.getGlyphRangesThai();
    }

    @Override
    public short[] getGlyphRangesVietnamese() {
        return this.boxed.getGlyphRangesVietnamese();
    }

    @Override
    public int addCustomRectRegular(int width, int height) {
        return this.boxed.addCustomRectRegular(width, height);
    }

    @Override
    public int addCustomRectFontGlyph(
        final KImFont font,
        short id,
        int width,
        int height,
        float advanceX
    ) {
        return this.boxed.addCustomRectFontGlyph(
            KImGuiSpairUnboxer.unbox(font),
            id,
            width,
            height,
            advanceX
        );
    }

    @Override
    public int addCustomRectFontGlyph(
        final KImFont font,
        short id,
        int width,
        int height,
        float advanceX,
        final KVector2f offset
    ) {
        return this.boxed.addCustomRectFontGlyph(
            KImGuiSpairUnboxer.unbox(font),
            id,
            width,
            height,
            advanceX,
            KImGuiSpairWrapper.wrap(offset)
        );
    }

    @Override
    public int addCustomRectFontGlyph(
        final KImFont font,
        short id,
        int width,
        int height,
        float advanceX,
        float offsetX,
        float offsetY
    ) {
        return this.boxed.addCustomRectFontGlyph(
            KImGuiSpairUnboxer.unbox(font),
            id,
            width,
            height,
            advanceX,
            offsetX,
            offsetY
        );
    }

    @Override
    public int getFlags() {
        return this.boxed.getFlags();
    }

    @Override
    public void setFlags(int value) {
        this.boxed.setFlags(value);
    }

    @Override
    public void addFlags(int flags) {
        this.boxed.addFlags(flags);
    }

    @Override
    public void removeFlags(int flags) {
        this.boxed.removeFlags(flags);
    }

    @Override
    public boolean hasFlags(int flags) {
        return this.boxed.hasFlags(flags);
    }

    @Override
    public int getTexDesiredWidth() {
        return this.boxed.getTexDesiredWidth();
    }

    @Override
    public void setTexDesiredWidth(int value) {
        this.boxed.setTexDesiredWidth(value);
    }

    @Override
    public int getTexGlyphPadding() {
        return this.boxed.getTexGlyphPadding();
    }

    @Override
    public void setTexGlyphPadding(int value) {
        this.boxed.setTexGlyphPadding(value);
    }

    @Override
    public boolean getLocked() {
        return this.boxed.getLocked();
    }

    @Override
    public void setLocked(boolean value) {
        this.boxed.setLocked(value);
    }


}
