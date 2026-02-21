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

import imgui.ImFontConfig;
import io.github.darthakiranihil.konna.core.di.KInjectedConstructor;
import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImFont;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImFontConfig;

@KExcludeFromGeneratedCoverageReport
final class KImFontConfigSpair implements KImFontConfig {

    private final ImFontConfig boxed;

    @KInjectedConstructor
    KImFontConfigSpair() {
        this.boxed = new ImFontConfig();
    }

    @Override
    public void destroy() {
        this.boxed.destroy();
    }

    @Override
    public byte[] getFontData() {
        return this.boxed.getFontData();
    }

    @Override
    public void setFontData(final byte[] fontData) {
        this.boxed.setFontData(fontData);
    }

    @Override
    public int getFontDataSize() {
        return this.boxed.getFontDataSize();
    }

    @Override
    public void setFontDataSize(int value) {
        this.boxed.setFontDataSize(value);
    }

    @Override
    public boolean getFontDataOwnedByAtlas() {
        return this.boxed.getFontDataOwnedByAtlas();
    }

    @Override
    public void setFontDataOwnedByAtlas(boolean value) {
        this.boxed.setFontDataOwnedByAtlas(value);
    }

    @Override
    public int getFontNo() {
        return this.boxed.getFontNo();
    }

    @Override
    public void setFontNo(int value) {
        this.boxed.setFontNo(value);
    }

    @Override
    public float getSizePixels() {
        return this.boxed.getSizePixels();
    }

    @Override
    public void setSizePixels(float value) {
        this.boxed.setSizePixels(value);
    }

    @Override
    public int getOversampleH() {
        return this.boxed.getOversampleH();
    }

    @Override
    public void setOversampleH(int value) {
        this.boxed.setOversampleH(value);
    }

    @Override
    public int getOversampleV() {
        return this.boxed.getOversampleV();
    }

    @Override
    public void setOversampleV(int value) {
        this.boxed.setOversampleV(value);
    }

    @Override
    public boolean getPixelSnapH() {
        return this.boxed.getPixelSnapH();
    }

    @Override
    public void setPixelSnapH(boolean value) {
        this.boxed.setPixelSnapH(value);
    }

    @Override
    public KVector2f getGlyphExtraSpacing() {
        return KImGuiSpairUnwrapper.wrap(this.boxed.getGlyphExtraSpacing());
    }

    @Override
    public float getGlyphExtraSpacingX() {
        return this.boxed.getGlyphExtraSpacingX();
    }

    @Override
    public float getGlyphExtraSpacingY() {
        return this.boxed.getGlyphExtraSpacingY();
    }

    @Override
    public void setGlyphExtraSpacing(final KVector2f value) {
        this.boxed.setGlyphExtraSpacing(KImGuiSpairWrapper.wrap(value));
    }

    @Override
    public void setGlyphExtraSpacing(float valueX, float valueY) {
        this.boxed.setGlyphExtraSpacing(valueX, valueY);
    }

    @Override
    public KVector2f getGlyphOffset() {
        return KImGuiSpairUnwrapper.wrap(this.boxed.getGlyphOffset());
    }

    @Override
    public float getGlyphOffsetX() {
        return this.boxed.getGlyphOffsetX();
    }

    @Override
    public float getGlyphOffsetY() {
        return this.boxed.getGlyphOffsetY();
    }

    @Override
    public void setGlyphOffset(final KVector2f value) {
        this.boxed.setGlyphOffset(KImGuiSpairWrapper.wrap(value));
    }

    @Override
    public void setGlyphOffset(float valueX, float valueY) {
        this.boxed.setGlyphOffset(valueX, valueY);
    }

    @Override
    public short[] getGlyphRanges() {
        return this.boxed.getGlyphRanges();
    }

    @Override
    public void setGlyphRanges(final short[] glyphRanges) {
        this.boxed.setGlyphRanges(glyphRanges);
    }

    @Override
    public float getGlyphMinAdvanceX() {
        return this.boxed.getGlyphMinAdvanceX();
    }

    @Override
    public void setGlyphMinAdvanceX(float value) {
        this.boxed.setGlyphMinAdvanceX(value);
    }

    @Override
    public float getGlyphMaxAdvanceX() {
        return this.boxed.getGlyphMaxAdvanceX();
    }

    @Override
    public void setGlyphMaxAdvanceX(float value) {
        this.boxed.setGlyphMaxAdvanceX(value);
    }

    @Override
    public boolean getMergeMode() {
        return this.boxed.getMergeMode();
    }

    @Override
    public void setMergeMode(boolean value) {
        this.boxed.setMergeMode(value);
    }

    @Override
    public int getFontBuilderFlags() {
        return this.boxed.getFontBuilderFlags();
    }

    @Override
    public void setFontBuilderFlags(int value) {
        this.boxed.setFontBuilderFlags(value);
    }

    @Override
    public void addFontBuilderFlags(int flags) {
        this.boxed.addFontBuilderFlags(flags);
    }

    @Override
    public void removeFontBuilderFlags(int flags) {
        this.boxed.removeFontBuilderFlags(flags);
    }

    @Override
    public boolean hasFontBuilderFlags(int flags) {
        return this.boxed.hasFontBuilderFlags(flags);
    }

    @Override
    public float getRasterizerMultiply() {
        return this.boxed.getRasterizerMultiply();
    }

    @Override
    public void setRasterizerMultiply(float value) {
        this.boxed.setRasterizerMultiply(value);
    }

    @Override
    public float getRasterizerDensity() {
        return this.boxed.getRasterizerDensity();
    }

    @Override
    public void setRasterizerDensity(float value) {
        this.boxed.setRasterizerDensity(value);
    }

    @Override
    public short getEllipsisChar() {
        return this.boxed.getEllipsisChar();
    }

    @Override
    public void setEllipsisChar(short value) {
        this.boxed.setEllipsisChar(value);
    }

    @Override
    public void setName(final String name) {
        this.boxed.setName(name);
    }

    @Override
    public KImFont getDstFont() {
        return KImGuiSpairUnwrapper.wrap(this.boxed.getDstFont());
    }

    @Override
    public void setDstFont(final KImFont value) {
        this.boxed.setDstFont(KImGuiSpairUnboxer.unbox(value));
    }



}
