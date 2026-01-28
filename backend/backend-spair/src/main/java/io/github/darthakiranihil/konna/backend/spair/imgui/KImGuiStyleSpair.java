/*
 * Copyright 2025-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.this.box. (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.this.box.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.darthakiranihil.konna.backend.spair.imgui;

import imgui.ImGuiStyle;
import imgui.ImVec4;
import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.struct.KVector4f;
import io.github.darthakiranihil.konna.core.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.libfrontend.imgui.KImGuiStyle;

@KExcludeFromGeneratedCoverageReport
public final class KImGuiStyleSpair implements KImGuiStyle {
    
    private final ImGuiStyle box;
    
    public KImGuiStyleSpair(final ImGuiStyle original) {
        this.box = original;
    }

    public KImGuiStyleSpair() {
        this.box = new ImGuiStyle();
    }

    @Override
    public float getAlpha() {
        return this.box.getAlpha();
    }

    @Override
    public void setAlpha(float value) {
        this.box.setAlpha(value);
    }

    @Override
    public float getDisabledAlpha() {
        return this.box.getDisabledAlpha();
    }

    @Override
    public void setDisabledAlpha(float value) {
        this.box.setDisabledAlpha(value);
    }

    @Override
    public KVector2f getWindowPadding() {
        return KImGuiSpairUnwrapper.wrap(this.box.getWindowPadding());
    }

    @Override
    public float getWindowPaddingX() {
        return this.box.getWindowPaddingX();
    }

    @Override
    public float getWindowPaddingY() {
        return this.box.getWindowPaddingY();
    }

    @Override
    public void setWindowPadding(final KVector2f value) {
        this.box.setWindowPadding(KImGuiSpairWrapper.wrap(value));
    }

    @Override
    public void setWindowPadding(float valueX, float valueY) {
        this.box.setWindowPadding(valueX, valueY);
    }

    @Override
    public float getWindowRounding() {
        return this.box.getWindowRounding();
    }

    @Override
    public void setWindowRounding(float value) {
        this.box.setWindowRounding(value);
    }

    @Override
    public float getWindowBorderSize() {
        return this.box.getWindowBorderSize();
    }

    @Override
    public void setWindowBorderSize(float value) {
        this.box.setWindowBorderSize(value);
    }

    @Override
    public KVector2f getWindowMinSize() {
        return KImGuiSpairUnwrapper.wrap(this.box.getWindowMinSize());
    }

    @Override
    public float getWindowMinSizeX() {
        return this.box.getWindowMinSizeX();
    }

    @Override
    public float getWindowMinSizeY() {
        return this.box.getWindowMinSizeY();
    }

    @Override
    public void setWindowMinSize(final KVector2f value) {
        this.box.setWindowMinSize(KImGuiSpairWrapper.wrap(value));
    }

    @Override
    public void setWindowMinSize(float valueX, float valueY) {
        this.box.setWindowMinSize(valueX, valueY);
    }

    @Override
    public KVector2f getWindowTitleAlign() {
        return KImGuiSpairUnwrapper.wrap(this.box.getWindowTitleAlign());
    }

    @Override
    public float getWindowTitleAlignX() {
        return this.box.getWindowTitleAlignX();
    }

    @Override
    public float getWindowTitleAlignY() {
        return this.box.getWindowTitleAlignY();
    }

    @Override
    public void setWindowTitleAlign(final KVector2f value) {
        this.box.setWindowTitleAlign(KImGuiSpairWrapper.wrap(value));
    }

    @Override
    public void setWindowTitleAlign(float valueX, float valueY) {
        this.box.setWindowTitleAlign(valueX, valueY);
    }

    @Override
    public int getWindowMenuButtonPosition() {
        return this.box.getWindowMenuButtonPosition();
    }

    @Override
    public void setWindowMenuButtonPosition(int value) {
        this.box.setWindowMenuButtonPosition(value);
    }

    @Override
    public float getChildRounding() {
        return this.box.getChildRounding();
    }

    @Override
    public void setChildRounding(float value) {
        this.box.setChildRounding(value);
    }

    @Override
    public float getChildBorderSize() {
        return this.box.getChildBorderSize();
    }

    @Override
    public void setChildBorderSize(float value) {
        this.box.setChildBorderSize(value);
    }

    @Override
    public float getPopupRounding() {
        return this.box.getPopupRounding();
    }

    @Override
    public void setPopupRounding(float value) {
        this.box.setPopupRounding(value);
    }

    @Override
    public float getPopupBorderSize() {
        return this.box.getPopupBorderSize();
    }

    @Override
    public void setPopupBorderSize(float value) {
        this.box.setPopupBorderSize(value);
    }

    @Override
    public KVector2f getFramePadding() {
        return KImGuiSpairUnwrapper.wrap(this.box.getFramePadding());
    }

    @Override
    public float getFramePaddingX() {
        return this.box.getFramePaddingX();
    }

    @Override
    public float getFramePaddingY() {
        return this.box.getFramePaddingY();
    }

    @Override
    public void setFramePadding(final KVector2f value) {
        this.box.setFramePadding(KImGuiSpairWrapper.wrap(value));
    }

    @Override
    public void setFramePadding(float valueX, float valueY) {
        this.box.setFramePadding(valueX, valueY);
    }

    @Override
    public float getFrameRounding() {
        return this.box.getFrameRounding();
    }

    @Override
    public void setFrameRounding(float value) {
        this.box.setFrameRounding(value);
    }

    @Override
    public float getFrameBorderSize() {
        return this.box.getFrameBorderSize();
    }

    @Override
    public void setFrameBorderSize(float value) {
        this.box.setFrameBorderSize(value);
    }

    @Override
    public KVector2f getItemSpacing() {
        return KImGuiSpairUnwrapper.wrap(this.box.getItemSpacing());
    }

    @Override
    public float getItemSpacingX() {
        return this.box.getItemSpacingX();
    }

    @Override
    public float getItemSpacingY() {
        return this.box.getItemSpacingY();
    }

    @Override
    public void setItemSpacing(final KVector2f value) {
        this.box.setItemSpacing(KImGuiSpairWrapper.wrap(value));
    }

    @Override
    public void setItemSpacing(float valueX, float valueY) {
        this.box.setItemSpacing(valueX, valueY);
    }

    @Override
    public KVector2f getItemInnerSpacing() {
        return KImGuiSpairUnwrapper.wrap(this.box.getItemInnerSpacing());
    }

    @Override
    public float getItemInnerSpacingX() {
        return this.box.getItemInnerSpacingX();
    }

    @Override
    public float getItemInnerSpacingY() {
        return this.box.getItemInnerSpacingY();
    }

    @Override
    public void setItemInnerSpacing(final KVector2f value) {
        this.box.setItemInnerSpacing(KImGuiSpairWrapper.wrap(value));
    }

    @Override
    public void setItemInnerSpacing(float valueX, float valueY) {
        this.box.setItemInnerSpacing(valueX, valueY);
    }

    @Override
    public KVector2f getCellPadding() {
        return KImGuiSpairUnwrapper.wrap(this.box.getCellPadding());
    }

    @Override
    public float getCellPaddingX() {
        return this.box.getCellPaddingX();
    }

    @Override
    public float getCellPaddingY() {
        return this.box.getCellPaddingY();
    }

    @Override
    public void setCellPadding(final KVector2f value) {
        this.box.setCellPadding(KImGuiSpairWrapper.wrap(value));
    }

    @Override
    public void setCellPadding(float valueX, float valueY) {
        this.box.setCellPadding(valueX, valueY);
    }

    @Override
    public KVector2f getTouchExtraPadding() {
        return KImGuiSpairUnwrapper.wrap(this.box.getTouchExtraPadding());
    }

    @Override
    public float getTouchExtraPaddingX() {
        return this.box.getTouchExtraPaddingX();
    }

    @Override
    public float getTouchExtraPaddingY() {
        return this.box.getTouchExtraPaddingY();
    }

    @Override
    public void setTouchExtraPadding(final KVector2f value) {
        this.box.setTouchExtraPadding(KImGuiSpairWrapper.wrap(value));
    }

    @Override
    public void setTouchExtraPadding(float valueX, float valueY) {
        this.box.setTouchExtraPadding(valueX, valueY);
    }

    @Override
    public float getIndentSpacing() {
        return this.box.getIndentSpacing();
    }

    @Override
    public void setIndentSpacing(float value) {
        this.box.setIndentSpacing(value);
    }

    @Override
    public float getColumnsMinSpacing() {
        return this.box.getColumnsMinSpacing();
    }

    @Override
    public void setColumnsMinSpacing(float value) {
        this.box.setColumnsMinSpacing(value);
    }

    @Override
    public float getScrollbarSize() {
        return this.box.getScrollbarSize();
    }

    @Override
    public void setScrollbarSize(float value) {
        this.box.setScrollbarSize(value);
    }

    @Override
    public float getScrollbarRounding() {
        return this.box.getScrollbarRounding();
    }

    @Override
    public void setScrollbarRounding(float value) {
        this.box.setScrollbarRounding(value);
    }

    @Override
    public float getGrabMinSize() {
        return this.box.getGrabMinSize();
    }

    @Override
    public void setGrabMinSize(float value) {
        this.box.setGrabMinSize(value);
    }

    @Override
    public float getGrabRounding() {
        return this.box.getGrabRounding();
    }

    @Override
    public void setGrabRounding(float value) {
        this.box.setGrabRounding(value);
    }

    @Override
    public float getLogSliderDeadzone() {
        return this.box.getLogSliderDeadzone();
    }

    @Override
    public void setLogSliderDeadzone(float value) {
        this.box.setLogSliderDeadzone(value);
    }

    @Override
    public float getTabRounding() {
        return this.box.getTabRounding();
    }

    @Override
    public void setTabRounding(float value) {
        this.box.setTabRounding(value);
    }

    @Override
    public float getTabBorderSize() {
        return this.box.getTabBorderSize();
    }

    @Override
    public void setTabBorderSize(float value) {
        this.box.setTabBorderSize(value);
    }

    @Override
    public float getTabMinWidthForCloseButton() {
        return this.box.getTabMinWidthForCloseButton();
    }

    @Override
    public void setTabMinWidthForCloseButton(float value) {
        this.box.setTabMinWidthForCloseButton(value);
    }

    @Override
    public int getColorButtonPosition() {
        return this.box.getColorButtonPosition();
    }

    @Override
    public void setColorButtonPosition(int value) {
        this.box.setColorButtonPosition(value);
    }

    @Override
    public KVector2f getButtonTextAlign() {
        return KImGuiSpairUnwrapper.wrap(this.box.getButtonTextAlign());
    }

    @Override
    public float getButtonTextAlignX() {
        return this.box.getButtonTextAlignX();
    }

    @Override
    public float getButtonTextAlignY() {
        return this.box.getButtonTextAlignY();
    }

    @Override
    public void setButtonTextAlign(final KVector2f value) {
        this.box.setButtonTextAlign(KImGuiSpairWrapper.wrap(value));
    }

    @Override
    public void setButtonTextAlign(float valueX, float valueY) {
        this.box.setButtonTextAlign(valueX, valueY);
    }

    @Override
    public KVector2f getSelectableTextAlign() {
        return KImGuiSpairUnwrapper.wrap(this.box.getSelectableTextAlign());
    }

    @Override
    public float getSelectableTextAlignX() {
        return this.box.getSelectableTextAlignX();
    }

    @Override
    public float getSelectableTextAlignY() {
        return this.box.getSelectableTextAlignY();
    }

    @Override
    public void setSelectableTextAlign(final KVector2f value) {
        this.box.setSelectableTextAlign(KImGuiSpairWrapper.wrap(value));
    }

    @Override
    public void setSelectableTextAlign(float valueX, float valueY) {
        this.box.setSelectableTextAlign(valueX, valueY);
    }

    @Override
    public float getSeparatorTextBorderSize() {
        return this.box.getSeparatorTextBorderSize();
    }

    @Override
    public void setSeparatorTextBorderSize(float value) {
        this.box.setSeparatorTextBorderSize(value);
    }

    @Override
    public KVector2f getSeparatorTextAlign() {
        return KImGuiSpairUnwrapper.wrap(this.box.getSeparatorTextAlign());
    }

    @Override
    public float getSeparatorTextAlignX() {
        return this.box.getSeparatorTextAlignX();
    }

    @Override
    public float getSeparatorTextAlignY() {
        return this.box.getSeparatorTextAlignY();
    }

    @Override
    public void setSeparatorTextAlign(final KVector2f value) {
        this.box.setSeparatorTextAlign(KImGuiSpairWrapper.wrap(value));
    }

    @Override
    public void setSeparatorTextAlign(float valueX, float valueY) {
        this.box.setSeparatorTextAlign(valueX, valueY);
    }

    @Override
    public KVector2f getSeparatorTextPadding() {
        return KImGuiSpairUnwrapper.wrap(this.box.getSeparatorTextPadding());
    }

    @Override
    public float getSeparatorTextPaddingX() {
        return this.box.getSeparatorTextPaddingX();
    }

    @Override
    public float getSeparatorTextPaddingY() {
        return this.box.getSeparatorTextPaddingY();
    }

    @Override
    public void setSeparatorTextPadding(final KVector2f value) {
        this.box.setSeparatorTextPadding(KImGuiSpairWrapper.wrap(value));
    }

    @Override
    public void setSeparatorTextPadding(float valueX, float valueY) {
        this.box.setSeparatorTextPadding(valueX, valueY);
    }

    @Override
    public KVector2f getDisplayWindowPadding() {
        return KImGuiSpairUnwrapper.wrap(this.box.getDisplayWindowPadding());
    }

    @Override
    public float getDisplayWindowPaddingX() {
        return this.box.getDisplayWindowPaddingX();
    }

    @Override
    public float getDisplayWindowPaddingY() {
        return this.box.getDisplayWindowPaddingY();
    }

    @Override
    public void setDisplayWindowPadding(final KVector2f value) {
        this.box.setDisplayWindowPadding(KImGuiSpairWrapper.wrap(value));
    }

    @Override
    public void setDisplayWindowPadding(float valueX, float valueY) {
        this.box.setDisplayWindowPadding(valueX, valueY);
    }

    @Override
    public KVector2f getDisplaySafeAreaPadding() {
        return KImGuiSpairUnwrapper.wrap(this.box.getDisplaySafeAreaPadding());
    }

    @Override
    public float getDisplaySafeAreaPaddingX() {
        return this.box.getDisplaySafeAreaPaddingX();
    }

    @Override
    public float getDisplaySafeAreaPaddingY() {
        return this.box.getDisplaySafeAreaPaddingY();
    }

    @Override
    public void setDisplaySafeAreaPadding(final KVector2f value) {
        this.box.setDisplaySafeAreaPadding(KImGuiSpairWrapper.wrap(value));
    }

    @Override
    public void setDisplaySafeAreaPadding(float valueX, float valueY) {
        this.box.setDisplaySafeAreaPadding(valueX, valueY);
    }

    @Override
    public float getDockingSeparatorSize() {
        return this.box.getDockingSeparatorSize();
    }

    @Override
    public void setDockingSeparatorSize(float value) {
        this.box.setDockingSeparatorSize(value);
    }

    @Override
    public float getMouseCursorScale() {
        return this.box.getMouseCursorScale();
    }

    @Override
    public void setMouseCursorScale(float value) {
        this.box.setMouseCursorScale(value);
    }

    @Override
    public boolean getAntiAliasedLines() {
        return this.box.getAntiAliasedLines();
    }

    @Override
    public void setAntiAliasedLines(boolean value) {
        this.box.setAntiAliasedLines(value);
    }

    @Override
    public boolean getAntiAliasedLinesUseTex() {
        return this.box.getAntiAliasedLinesUseTex();
    }

    @Override
    public void setAntiAliasedLinesUseTex(boolean value) {
        this.box.setAntiAliasedLinesUseTex(value);
    }

    @Override
    public boolean getAntiAliasedFill() {
        return this.box.getAntiAliasedFill();
    }

    @Override
    public void setAntiAliasedFill(boolean value) {
        this.box.setAntiAliasedFill(value);
    }

    @Override
    public float getCurveTessellationTol() {
        return this.box.getCurveTessellationTol();
    }

    @Override
    public void setCurveTessellationTol(float value) {
        this.box.setCurveTessellationTol(value);
    }

    @Override
    public float getCircleTessellationMaxError() {
        return this.box.getCircleTessellationMaxError();
    }

    @Override
    public void setCircleTessellationMaxError(float value) {
        this.box.setCircleTessellationMaxError(value);
    }

    @Override
    public KVector4f[] getColors() {
        ImVec4[] colors = this.box.getColors();
        KVector4f[] result = new KVector4f[colors.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = KImGuiSpairUnwrapper.wrap(colors[i]);
        }
        return result;
    }

    @Override
    public void setColors(final KVector4f[] value) {
        ImVec4[] vecs = new ImVec4[value.length];
        for (int i = 0; i < vecs.length; i++) {
            vecs[i] = KImGuiSpairWrapper.wrap(value[i]);
        }
        this.box.setColors(vecs);
    }

    @Override
    public KVector4f getColor(int col) {
        return KImGuiSpairUnwrapper.wrap(this.box.getColor(col));
    }

    @Override
    public float getHoverStationaryDelay() {
        return this.box.getHoverStationaryDelay();
    }

    @Override
    public void setHoverStationaryDelay(float value) {
        this.box.setHoverStationaryDelay(value);
    }

    @Override
    public float getHoverDelayShort() {
        return this.box.getHoverDelayShort();
    }

    @Override
    public void setHoverDelayShort(float value) {
        this.box.setHoverDelayShort(value);
    }

    @Override
    public float getHoverDelayNormal() {
        return this.box.getHoverDelayNormal();
    }

    @Override
    public void setHoverDelayNormal(float value) {
        this.box.setHoverDelayNormal(value);
    }

    @Override
    public int getHoverFlagsForTooltipMouse() {
        return this.box.getHoverFlagsForTooltipMouse();
    }

    @Override
    public void setHoverFlagsForTooltipMouse(int value) {
        this.box.setHoverFlagsForTooltipMouse(value);
    }

    @Override
    public int getHoverFlagsForTooltipNav() {
        return this.box.getHoverFlagsForTooltipNav();
    }

    @Override
    public void setHoverFlagsForTooltipNav(int value) {
        this.box.setHoverFlagsForTooltipNav(value);
    }

    @Override
    public void scaleAllSizes(float scaleFactor) {
        this.box.scaleAllSizes(scaleFactor);
    }
}
