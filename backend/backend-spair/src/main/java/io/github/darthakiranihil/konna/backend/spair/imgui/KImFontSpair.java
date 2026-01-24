/*
 * Copyright 2this.boxed25-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.this.boxed (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.this.boxed
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.darthakiranihil.konna.backend.spair.imgui;

import imgui.ImFont;
import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.struct.KVector4f;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImDrawList;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImFont;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImFontGlyph;

final class KImFontSpair implements KImFont {
    
    private final ImFont boxed;
    
    public KImFontSpair(final ImFont original) {
        this.boxed = original;
    }
    
    public KImFontSpair() {
        this.boxed = new ImFont();
    }

    @Override
    public float getFallbackAdvanceX() {
        return this.boxed.getFallbackAdvanceX();
    }

    @Override
    public void setFallbackAdvanceX(float value) {
        this.boxed.setFallbackAdvanceX(value);
    }

    @Override
    public float getFontSize() {
        return this.boxed.getFontSize();
    }

    @Override
    public void setFontSize(float value) {
        this.boxed.setFontSize(value);
    }

    @Override
    public KImFontGlyph getFallbackGlyph() {
        return KImGuiSpairUnwrapper.wrap(this.boxed.getFallbackGlyph());
    }

    @Override
    public void setFallbackGlyph(KImFontGlyph value) {
        this.boxed.setFallbackGlyph(
            KImGuiSpairUnboxer.unbox(value)
        );
    }

    @Override
    public short getConfigDataCount() {
        return this.boxed.getConfigDataCount();
    }

    @Override
    public void setConfigDataCount(short value) {
        this.boxed.setConfigDataCount(value);
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
    public short getEllipsisCharCount() {
        return this.boxed.getEllipsisCharCount();
    }

    @Override
    public void setEllipsisCharCount(short value) {
        this.boxed.setEllipsisCharCount(value);
    }

    @Override
    public float getEllipsisWidth() {
        return this.boxed.getEllipsisWidth();
    }

    @Override
    public void setEllipsisWidth(float value) {
        this.boxed.setEllipsisWidth(value);
    }

    @Override
    public float getEllipsisCharStep() {
        return this.boxed.getEllipsisCharStep();
    }

    @Override
    public void setEllipsisCharStep(float value) {
        this.boxed.setEllipsisCharStep(value);
    }

    @Override
    public boolean getDirtyLookupTables() {
        return this.boxed.getDirtyLookupTables();
    }

    @Override
    public void setDirtyLookupTables(boolean value) {
        this.boxed.setDirtyLookupTables(value);
    }

    @Override
    public float getScale() {
        return this.boxed.getScale();
    }

    @Override
    public void setScale(float value) {
        this.boxed.setScale(value);
    }

    @Override
    public float getAscent() {
        return this.boxed.getAscent();
    }

    @Override
    public void setAscent(float value) {
        this.boxed.setAscent(value);
    }

    @Override
    public float getDescent() {
        return this.boxed.getDescent();
    }

    @Override
    public void setDescent(float value) {
        this.boxed.setDescent(value);
    }

    @Override
    public int getMetricsTotalSurface() {
        return this.boxed.getMetricsTotalSurface();
    }

    @Override
    public void setMetricsTotalSurface(int value) {
        this.boxed.setMetricsTotalSurface(value);
    }

    @Override
    public KImFontGlyph findGlyph(int c) {
        return KImGuiSpairUnwrapper.wrap(
            this.boxed.findGlyph(c)
        );
    }

    @Override
    public KImFontGlyph findGlyphNoFallback(int c) {
        return KImGuiSpairUnwrapper.wrap(
            this.boxed.findGlyphNoFallback(c)
        );
    }

    @Override
    public float getCharAdvance(int c) {
        return this.boxed.getCharAdvance(c);
    }

    @Override
    public boolean isLoaded() {
        return this.boxed.isLoaded();
    }

    @Override
    public String getDebugName() {
        return this.boxed.getDebugName();
    }

    @Override
    public KVector2f calcTextSizeA(float size, float maxWidth, float wrapWidth, String textBegin, String textEnd) {
        return KImGuiSpairUnwrapper.wrap(this.boxed.calcTextSizeA(size, maxWidth, wrapWidth, textBegin, textEnd));
    }

    @Override
    public String calcWordWrapPositionA(float scale, String text, String textEnd, float wrapWidth) {
        return this.boxed.calcWordWrapPositionA(scale, text, textEnd, wrapWidth);
    }

    @Override
    public void renderChar(KImDrawList drawList, float size, KVector2f pos, int col, int c) {
        this.boxed.renderChar(
            KImGuiSpairUnboxer.unbox(drawList),
            size,
            KImGuiSpairWrapper.wrap(pos),
            col,
            c
        );
    }

    @Override
    public void renderText(
        KImDrawList drawList,
        float size,
        KVector2f pos,
        int col,
        KVector4f clipRect,
        String textBegin,
        String textEnd,
        float wrapWidth,
        boolean cpuFineClip
    ) {
        this.boxed.renderText(
            KImGuiSpairUnboxer.unbox(drawList),
            size,
            KImGuiSpairWrapper.wrap(pos),
            col,
            KImGuiSpairWrapper.wrap(clipRect),
            textBegin,
            textEnd,
            wrapWidth,
            cpuFineClip
        );
    }
}
