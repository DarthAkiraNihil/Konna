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

import imgui.*;
import imgui.internal.ImGuiContext;
import io.github.darthakiranihil.konna.core.object.KUninstantiable;
import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.struct.KVector4f;
import io.github.darthakiranihil.konna.core.util.KReflectionUtils;
import io.github.darthakiranihil.konna.libfrontend.imgui.*;

final class KImGuiSpairWrapper extends KUninstantiable {

    private KImGuiSpairWrapper() {
        super();
    }

    public static ImVec2 wrap(final KVector2f original) {
        return new ImVec2(original.x(), original.y());
    }

    public static ImVec4 wrap(final KVector4f original) {
        return new ImVec4(original.x(), original.y(), original.z(), original.w());
    }

    public static ImGuiContext wrap(final KImGuiContext original) {
        return new ImGuiContext(original.handle());
    }

    public static ImGuiStyle wrap(final KImGuiStyle original) {
        ImGuiStyle st = KReflectionUtils.getField(
            KImGuiStyleSpair.class,
            original,
            "box",
            ImGuiStyle.class
        );

        if (st != null) {
            return st;
        }

        ImGuiStyle internal = new ImGuiStyle();
        internal.setAlpha(original.getAlpha());
        internal.setDisabledAlpha(original.getDisabledAlpha());
        internal.setWindowPadding(KImGuiSpairWrapper.wrap(original.getWindowPadding()));
        internal.setWindowRounding(original.getWindowRounding());
        internal.setWindowBorderSize(original.getWindowBorderSize());
        internal.setWindowMinSize(KImGuiSpairWrapper.wrap(original.getWindowMinSize()));
        internal.setWindowTitleAlign(KImGuiSpairWrapper.wrap(original.getWindowTitleAlign()));
        internal.setWindowMenuButtonPosition(original.getWindowMenuButtonPosition());
        internal.setChildRounding(original.getChildRounding());
        internal.setChildBorderSize(original.getChildBorderSize());
        internal.setPopupRounding(original.getPopupRounding());
        internal.setPopupBorderSize(original.getPopupBorderSize());
        internal.setFramePadding(KImGuiSpairWrapper.wrap(original.getFramePadding()));
        internal.setFrameRounding(original.getFrameRounding());
        internal.setFrameBorderSize(original.getFrameBorderSize());
        internal.setItemSpacing(KImGuiSpairWrapper.wrap(original.getItemSpacing()));
        internal.setItemInnerSpacing(KImGuiSpairWrapper.wrap(original.getItemInnerSpacing()));
        internal.setCellPadding(KImGuiSpairWrapper.wrap(original.getCellPadding()));
        internal.setTouchExtraPadding(KImGuiSpairWrapper.wrap(original.getTouchExtraPadding()));
        internal.setIndentSpacing(original.getIndentSpacing());
        internal.setColumnsMinSpacing(original.getColumnsMinSpacing());
        internal.setScrollbarSize(original.getScrollbarSize());
        internal.setScrollbarRounding(original.getScrollbarRounding());
        internal.setGrabMinSize(original.getGrabMinSize());
        internal.setGrabRounding(original.getGrabRounding());
        internal.setLogSliderDeadzone(original.getLogSliderDeadzone());
        internal.setTabRounding(original.getTabRounding());
        internal.setTabBorderSize(original.getTabBorderSize());
        internal.setTabMinWidthForCloseButton(original.getTabMinWidthForCloseButton());
        internal.setColorButtonPosition(original.getColorButtonPosition());
        internal.setButtonTextAlign(KImGuiSpairWrapper.wrap(original.getButtonTextAlign()));
        internal.setSelectableTextAlign(KImGuiSpairWrapper.wrap(original.getSelectableTextAlign()));
        internal.setSeparatorTextBorderSize(original.getSeparatorTextBorderSize());
        internal.setSeparatorTextAlign(KImGuiSpairWrapper.wrap(original.getSeparatorTextAlign()));
        internal.setSeparatorTextPadding(KImGuiSpairWrapper.wrap(original.getSeparatorTextPadding()));
        internal.setDisplayWindowPadding(KImGuiSpairWrapper.wrap(original.getDisplayWindowPadding()));
        internal.setDisplaySafeAreaPadding(KImGuiSpairWrapper.wrap(original.getDisplaySafeAreaPadding()));
        internal.setDockingSeparatorSize(original.getDockingSeparatorSize());
        internal.setMouseCursorScale(original.getMouseCursorScale());
        internal.setAntiAliasedLines(original.getAntiAliasedLines());
        internal.setAntiAliasedLinesUseTex(original.getAntiAliasedLinesUseTex());
        internal.setAntiAliasedFill(original.getAntiAliasedFill());
        internal.setCurveTessellationTol(original.getCurveTessellationTol());
        internal.setCircleTessellationMaxError(original.getCircleTessellationMaxError());

        KVector4f[] colors = original.getColors();
        ImVec4[] v = new ImVec4[colors.length];
        for (int i = 0; i < v.length; i++) {
            v[i] = KImGuiSpairWrapper.wrap(colors[i]);
        }
        internal.setColors(v);
        internal.setHoverStationaryDelay(original.getHoverStationaryDelay());
        internal.setHoverDelayShort(original.getHoverDelayShort());
        internal.setHoverDelayNormal(original.getHoverDelayNormal());
        internal.setHoverFlagsForTooltipMouse(original.getHoverFlagsForTooltipMouse());
        internal.setHoverFlagsForTooltipNav(original.getHoverFlagsForTooltipNav());
        return internal;
    }

}
