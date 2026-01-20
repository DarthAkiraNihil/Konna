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

package io.github.darthakiranihil.konna.libfrontend.imgui;

import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.struct.KVector4f;

public interface KImFont {

    float getFallbackAdvanceX();
    float setFallbackAdvanceX(float value);
    
    float getFontSize();
    void setFontSize(float value);
    
    KImFontGlyph getFallbackGlyph();
    void setFallbackGlyph(KImFontGlyph value);

    short getConfigDataCount();
    void setConfigDataCount(short value);

    short getEllipsisChar();
    void setEllipsisChar(short value);

    short getEllipsisCharCount();
    void setEllipsisCharCount(short value);

    short getEllipsisWidth();
    void setEllipsisWidth(short value);

    short getEllipsisCharStep();
    void setEllipsisCharStep(short value);

    boolean getDirtyLookupTables();
    void setDirtyLookupTables(boolean value);

    float getScale();
    void setScale(float value);

    float getAscent();
    void setAscent(float value);

    float getDescent();
    void setDescent(float value);

    int getMetricsTotalSurface();
    void setMetricsTotalSurface(int value);

    KImFontGlyph FindGlyph(int c);
    KImFontGlyph FindGlyphNoFallback(int c);
    float GetCharAdvance(int c);
    boolean IsLoaded();
    String GetDebugName();
    KVector2f CalcTextSizeA(float size, float maxWidth, float wrapWidth, String textBegin, String textEnd);
    String CalcWordWrapPositionA(float scale, String text, String textEnd, float wrapWidth);
    void RenderChar(KImDrawList drawList, float size, KVector2f pos, int col, int c);
    void RenderText(KImDrawList drawList, float size, KVector2f pos, int col, KVector4f clipRect, String textBegin, String textEnd, float wrapWidth, boolean cpuFineClip);


}
