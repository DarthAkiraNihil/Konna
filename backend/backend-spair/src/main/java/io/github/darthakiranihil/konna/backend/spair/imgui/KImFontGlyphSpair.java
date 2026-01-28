/*
 * Copyright 2025-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.this.boxed. (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.this.boxed.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.darthakiranihil.konna.backend.spair.imgui;

import imgui.ImFontGlyph;
import io.github.darthakiranihil.konna.core.di.KInjectedConstructor;
import io.github.darthakiranihil.konna.core.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImFontGlyph;

@KExcludeFromGeneratedCoverageReport
final class KImFontGlyphSpair implements KImFontGlyph {
    
    private final ImFontGlyph boxed;

    public KImFontGlyphSpair(final ImFontGlyph boxed) {
        this.boxed = boxed;
    }

    @KInjectedConstructor
    public KImFontGlyphSpair() {
        this.boxed = new ImFontGlyph();
    }

    @Override
    public int getColored() {
        return this.boxed.getColored();
    }

    @Override
    public void setColored(int value) {
        this.boxed.setColored(value);
    }

    @Override
    public int getVisible() {
        return this.boxed.getVisible();
    }

    @Override
    public void setVisible(int value) {
        this.boxed.setVisible(value);
    }

    @Override
    public int getCodepoint() {
        return this.boxed.getCodepoint();
    }

    @Override
    public void setCodepoint(int value) {
        this.boxed.setCodepoint(value);
    }

    @Override
    public float getAdvanceX() {
        return this.boxed.getAdvanceX();
    }

    @Override
    public void setAdvanceX(float value) {
        this.boxed.setAdvanceX(value);
    }

    @Override
    public float getX0() {
        return this.boxed.getX0();
    }

    @Override
    public void setX0(float value) {
        this.boxed.setX0(value);
    }

    @Override
    public float getY0() {
        return this.boxed.getY0();
    }

    @Override
    public void setY0(float value) {
        this.boxed.setY0(value);
    }

    @Override
    public float getX1() {
        return this.boxed.getX1();
    }

    @Override
    public void setX1(float value) {
        this.boxed.setX1(value);
    }

    @Override
    public float getY1() {
        return this.boxed.getY1();
    }

    @Override
    public void setY1(float value) {
        this.boxed.setY1(value);
    }

    @Override
    public float getU0() {
        return this.boxed.getU0();
    }

    @Override
    public void setU0(float value) {
        this.boxed.setU0(value);
    }

    @Override
    public float getV0() {
        return this.boxed.getV0();
    }

    @Override
    public void setV0(float value) {
        this.boxed.setV0(value);
    }

    @Override
    public float getU1() {
        return this.boxed.getU1();
    }

    @Override
    public void setU1(float value) {
        this.boxed.setU1(value);
    }

    @Override
    public float getV1() {
        return this.boxed.getV1();
    }

    @Override
    public void setV1(float value) {
        this.boxed.setV1(value);
    }
}
