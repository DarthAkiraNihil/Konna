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

import imgui.ImGui;
import imgui.type.*;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.struct.KVector4f;
import io.github.darthakiranihil.konna.core.struct.ref.*;
import io.github.darthakiranihil.konna.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.libfrontend.imgui.*;

/**
 * Dear ImGui libfrontend implementation using SpaiR bindings.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
@KSingleton
@KExcludeFromGeneratedCoverageReport
public final class KImGuiSpair extends KObject implements KImGui {

    @Override
    public void setAssertCallback(final KImAssertCallback callback) {
        ImGui.setAssertCallback(
            KImGuiSpairWrapper.wrap(callback)
        );
    }

    @Override
    public KImGuiContext createContext() {
        return KImGuiSpairUnwrapper.wrap(ImGui.createContext());
    }

    @Override
    public KImGuiContext createContext(final KImFontAtlas sharedFontAtlas) {
        return KImGuiSpairUnwrapper.wrap(ImGui.createContext(KImGuiSpairUnboxer.unbox(
            sharedFontAtlas)));
    }

    @Override
    public void destroyContext() {
        ImGui.destroyContext();
    }

    @Override
    public void destroyContext(final KImGuiContext ctx) {
        ImGui.destroyContext(KImGuiSpairWrapper.wrap(ctx));
    }

    @Override
    public KImGuiContext getCurrentContext() {
        return KImGuiSpairUnwrapper.wrap(ImGui.getCurrentContext());
    }

    @Override
    public void setCurrentContext(final KImGuiContext ctx) {
        ImGui.setCurrentContext(KImGuiSpairWrapper.wrap(ctx));
    }

    @Override
    public KImGuiIo getIO() {
        return KImGuiSpairUnwrapper.wrap(ImGui.getIO());
    }

    @Override
    public KImGuiStyle getStyle() {
        return KImGuiSpairUnwrapper.wrap(ImGui.getStyle());
    }

    @Override
    public void newFrame() {
        ImGui.newFrame();
    }

    @Override
    public void endFrame() {
        ImGui.endFrame();
    }

    @Override
    public void render() {
        ImGui.render();
    }

    @Override
    public KImDrawData getDrawData() {
        return KImGuiSpairUnwrapper.wrap(ImGui.getDrawData());
    }

    @Override
    public void showDemoWindow() {
        ImGui.showDemoWindow();
    }

    @Override
    public void showDemoWindow(final KBooleanReference pOpen) {
        ImBoolean b = new ImBoolean(pOpen.get());
        ImGui.showDemoWindow(b);
        pOpen.set(b.get());
    }

    @Override
    public void showMetricsWindow() {
        ImGui.showMetricsWindow();
    }

    @Override
    public void showMetricsWindow(final KBooleanReference pOpen) {
        ImBoolean b = new ImBoolean(pOpen.get());
        ImGui.showMetricsWindow(b);
        pOpen.set(b.get());
    }

    @Override
    public void showDebugLogWindow() {
        ImGui.showDebugLogWindow();
    }

    @Override
    public void showDebugLogWindow(final KBooleanReference pOpen) {
        ImBoolean b = new ImBoolean(pOpen.get());
        ImGui.showDebugLogWindow(b);
        pOpen.set(b.get());
    }

    @Override
    public void showIDStackToolWindow() {
        ImGui.showIDStackToolWindow();
    }

    @Override
    public void showIDStackToolWindow(final KBooleanReference pOpen) {
        ImBoolean b = new ImBoolean(pOpen.get());
        ImGui.showIDStackToolWindow(b);
        pOpen.set(b.get());
    }

    @Override
    public void showAboutWindow() {
        ImGui.showAboutWindow();
    }

    @Override
    public void showAboutWindow(final KBooleanReference pOpen) {
        ImBoolean b = new ImBoolean(pOpen.get());
        ImGui.showAboutWindow(b);
        pOpen.set(b.get());
    }

    @Override
    public void showStyleEditor() {
        ImGui.showStyleEditor();
    }

    @Override
    public void showStyleEditor(final KImGuiStyle ref) {
        ImGui.showStyleEditor(KImGuiSpairUnboxer.unbox(ref));
    }

    @Override
    public boolean showStyleSelector(final String label) {
        return ImGui.showStyleSelector(label);
    }

    @Override
    public void showFontSelector(final String label) {
        ImGui.showFontSelector(label);
    }

    @Override
    public void showUserGuide() {
        ImGui.showUserGuide();
    }

    @Override
    public String getVersion() {
        return ImGui.getVersion();
    }

    @Override
    public void styleColorsDark() {
        ImGui.styleColorsDark();
    }

    @Override
    public void styleColorsDark(final KImGuiStyle style) {
        ImGui.styleColorsDark(KImGuiSpairUnboxer.unbox(style));
    }

    @Override
    public void styleColorsLight() {
        ImGui.styleColorsLight();
    }

    @Override
    public void styleColorsLight(final KImGuiStyle style) {
        ImGui.styleColorsLight(KImGuiSpairUnboxer.unbox(style));
    }

    @Override
    public void styleColorsClassic() {
        ImGui.styleColorsClassic();
    }

    @Override
    public void styleColorsClassic(final KImGuiStyle style) {
        ImGui.styleColorsClassic(KImGuiSpairUnboxer.unbox(style));
    }

    @Override
    public boolean begin(final String title) {
        return ImGui.begin(title);
    }

    @Override
    public boolean begin(final String title, final KBooleanReference pOpen) {
        ImBoolean b = new ImBoolean(pOpen.get());
        boolean result = ImGui.begin(title, b);
        pOpen.set(b.get());
        return result;
    }

    @Override
    public boolean begin(final String title, final KBooleanReference pOpen, int imGuiWindowFlags) {
        ImBoolean b = new ImBoolean(pOpen.get());
        boolean result = ImGui.begin(title, b, imGuiWindowFlags);
        pOpen.set(b.get());
        return result;
    }

    @Override
    public boolean begin(final String title, int imGuiWindowFlags) {
        return ImGui.begin(title, imGuiWindowFlags);
    }

    @Override
    public void end() {
        ImGui.end();
    }

    @Override
    public boolean beginChild(final String strId) {
        return ImGui.beginChild(strId);
    }

    @Override
    public boolean beginChild(final String strId, final KVector2f size) {
        return ImGui.beginChild(strId, KImGuiSpairWrapper.wrap(size));
    }

    @Override
    public boolean beginChild(final String strId, float sizeX, float sizeY) {
        return ImGui.beginChild(strId, sizeX, sizeY);
    }

    @Override
    public boolean beginChild(final String strId, final KVector2f size, int childFlags) {
        return ImGui.beginChild(strId, KImGuiSpairWrapper.wrap(size), childFlags);
    }

    @Override
    public boolean beginChild(final String strId, float sizeX, float sizeY, int childFlags) {
        return ImGui.beginChild(strId, sizeX, sizeY, childFlags);
    }

    @Override
    public boolean beginChild(
        final String strId,
        final KVector2f size,
        int childFlags,
        int windowFlags
    ) {
        return ImGui.beginChild(strId, KImGuiSpairWrapper.wrap(size), childFlags, windowFlags);
    }

    @Override
    public boolean beginChild(
        final String strId,
        float sizeX,
        float sizeY,
        int childFlags,
        int windowFlags
    ) {
        return ImGui.beginChild(strId, sizeX, sizeY, childFlags, windowFlags);
    }

    @Override
    public boolean beginChild(final String strId, int childFlags, int windowFlags) {
        return ImGui.beginChild(strId, childFlags, windowFlags);
    }

    @Override
    public boolean beginChild(final String strId, int windowFlags) {
        return ImGui.beginChild(strId, windowFlags);
    }

    @Override
    public boolean beginChild(int id) {
        return ImGui.beginChild(id);
    }

    @Override
    public boolean beginChild(int id, final KVector2f size) {
        return ImGui.beginChild(id, KImGuiSpairWrapper.wrap(size));
    }

    @Override
    public boolean beginChild(int id, float sizeX, float sizeY) {
        return ImGui.beginChild(id, sizeX, sizeY);
    }

    @Override
    public boolean beginChild(int id, final KVector2f size, int childFlags) {
        return ImGui.beginChild(id, KImGuiSpairWrapper.wrap(size), childFlags);
    }

    @Override
    public boolean beginChild(int id, float sizeX, float sizeY, int childFlags) {
        return ImGui.beginChild(id, sizeX, sizeY, childFlags);
    }

    @Override
    public boolean beginChild(int id, final KVector2f size, int childFlags, int windowFlags) {
        return ImGui.beginChild(id, KImGuiSpairWrapper.wrap(size), childFlags, windowFlags);
    }

    @Override
    public boolean beginChild(int id, float sizeX, float sizeY, int childFlags, int windowFlags) {
        return ImGui.beginChild(id, sizeX, sizeY, childFlags, windowFlags);
    }

    @Override
    public boolean beginChild(int id, int childFlags, int windowFlags) {
        return ImGui.beginChild(id, childFlags, windowFlags);
    }

    @Override
    public boolean beginChild(int id, int windowFlags) {
        return ImGui.beginChild(id, windowFlags);
    }

    @Override
    public boolean beginChild(final String strId, final KVector2f size, boolean border) {
        return ImGui.beginChild(strId, KImGuiSpairWrapper.wrap(size), border);
    }

    @Override
    public boolean beginChild(final String strId, float sizeX, float sizeY, boolean border) {
        return ImGui.beginChild(strId, sizeX, sizeY, border);
    }

    @Override
    public boolean beginChild(
        final String strId,
        final KVector2f size,
        boolean border,
        int windowFlags
    ) {
        return ImGui.beginChild(strId, KImGuiSpairWrapper.wrap(size), border, windowFlags);
    }

    @Override
    public boolean beginChild(
        final String strId,
        float sizeX,
        float sizeY,
        boolean border,
        int windowFlags
    ) {
        return ImGui.beginChild(strId, sizeX, sizeY, border, windowFlags);
    }

    @Override
    public void endChild() {
        ImGui.endChild();
    }

    @Override
    public boolean isWindowAppearing() {
        return ImGui.isWindowAppearing();
    }

    @Override
    public boolean isWindowCollapsed() {
        return ImGui.isWindowCollapsed();
    }

    @Override
    public void setWindowCollapsed(boolean collapsed) {
        ImGui.setWindowCollapsed(collapsed);
    }

    @Override
    public boolean isWindowFocused() {
        return ImGui.isWindowFocused();
    }

    @Override
    public boolean isWindowFocused(int imGuiFocusedFlags) {
        return ImGui.isWindowFocused(imGuiFocusedFlags);
    }

    @Override
    public boolean isWindowHovered() {
        return ImGui.isWindowHovered();
    }

    @Override
    public boolean isWindowHovered(int imGuiHoveredFlags) {
        return ImGui.isWindowHovered(imGuiHoveredFlags);
    }

    @Override
    public KImDrawList getWindowDrawList() {
        return KImGuiSpairUnwrapper.wrap(ImGui.getWindowDrawList());
    }

    @Override
    public float getWindowDpiScale() {
        return ImGui.getWindowDpiScale();
    }

    @Override
    public KVector2f getWindowPos() {
        return KImGuiSpairUnwrapper.wrap(ImGui.getWindowPos());
    }

    @Override
    public void setWindowPos(final KVector2f pos) {
        ImGui.setWindowPos(KImGuiSpairWrapper.wrap(pos));
    }

    @Override
    public float getWindowPosX() {
        return ImGui.getWindowPosX();
    }

    @Override
    public float getWindowPosY() {
        return ImGui.getWindowPosY();
    }

    @Override
    public KVector2f getWindowSize() {
        return KImGuiSpairUnwrapper.wrap(ImGui.getWindowSize());
    }

    @Override
    public void setWindowSize(final KVector2f size) {
        ImGui.setWindowSize(KImGuiSpairWrapper.wrap(size));
    }

    @Override
    public float getWindowSizeX() {
        return ImGui.getWindowSizeX();
    }

    @Override
    public float getWindowSizeY() {
        return ImGui.getWindowSizeY();
    }

    @Override
    public float getWindowWidth() {
        return ImGui.getWindowWidth();
    }

    @Override
    public float getWindowHeight() {
        return ImGui.getWindowHeight();
    }

    @Override
    public KImGuiViewport getWindowViewport() {
        return KImGuiSpairUnwrapper.wrap(ImGui.getWindowViewport());
    }

    @Override
    public void setNextWindowPos(final KVector2f pos) {
        ImGui.setNextWindowPos(KImGuiSpairWrapper.wrap(pos));
    }

    @Override
    public void setNextWindowPos(float posX, float posY) {
        ImGui.setNextWindowPos(posX, posY);
    }

    @Override
    public void setNextWindowPos(final KVector2f pos, int cond) {
        ImGui.setNextWindowPos(KImGuiSpairWrapper.wrap(pos), cond);
    }

    @Override
    public void setNextWindowPos(float posX, float posY, int cond) {
        ImGui.setNextWindowPos(posX, posY, cond);
    }

    @Override
    public void setNextWindowPos(final KVector2f pos, int cond, final KVector2f pivot) {
        ImGui.setNextWindowPos(KImGuiSpairWrapper.wrap(pos), cond, KImGuiSpairWrapper.wrap(pivot));
    }

    @Override
    public void setNextWindowPos(float posX, float posY, int cond, float pivotX, float pivotY) {
        ImGui.setNextWindowPos(posX, posY, cond, pivotX, pivotY);
    }

    @Override
    public void setNextWindowPos(final KVector2f pos, final KVector2f pivot) {
        ImGui.setNextWindowPos(KImGuiSpairWrapper.wrap(pos), KImGuiSpairWrapper.wrap(pivot));
    }

    @Override
    public void setNextWindowPos(float posX, float posY, float pivotX, float pivotY) {
        ImGui.setNextWindowPos(posX, posY, pivotX, pivotY);
    }

    @Override
    public void setNextWindowSize(final KVector2f size) {
        ImGui.setNextWindowSize(KImGuiSpairWrapper.wrap(size));
    }

    @Override
    public void setNextWindowSize(float sizeX, float sizeY) {
        ImGui.setNextWindowSize(sizeX, sizeY);
    }

    @Override
    public void setNextWindowSize(final KVector2f size, int cond) {
        ImGui.setNextWindowSize(KImGuiSpairWrapper.wrap(size), cond);
    }

    @Override
    public void setNextWindowSize(float sizeX, float sizeY, int cond) {
        ImGui.setNextWindowSize(sizeX, sizeY, cond);
    }

    @Override
    public void setNextWindowSizeConstraints(final KVector2f sizeMin, final KVector2f sizeMax) {
        ImGui.setNextWindowSizeConstraints(
            KImGuiSpairWrapper.wrap(sizeMin),
            KImGuiSpairWrapper.wrap(sizeMax)
        );
    }

    @Override
    public void setNextWindowSizeConstraints(
        float sizeMinX,
        float sizeMinY,
        float sizeMaxX,
        float sizeMaxY
    ) {
        ImGui.setNextWindowSizeConstraints(sizeMinX, sizeMinY, sizeMaxX, sizeMaxY);
    }

    @Override
    public void setNextWindowContentSize(final KVector2f size) {
        ImGui.setNextWindowContentSize(KImGuiSpairWrapper.wrap(size));
    }

    @Override
    public void setNextWindowContentSize(float sizeX, float sizeY) {
        ImGui.setNextWindowContentSize(sizeX, sizeY);
    }

    @Override
    public void setNextWindowCollapsed(boolean collapsed) {
        ImGui.setNextWindowCollapsed(collapsed);
    }

    @Override
    public void setNextWindowCollapsed(boolean collapsed, int cond) {
        ImGui.setNextWindowCollapsed(collapsed, cond);
    }

    @Override
    public void setNextWindowFocus() {
        ImGui.setNextWindowFocus();
    }

    @Override
    public void setNextWindowScroll(final KVector2f scroll) {
        ImGui.setNextWindowScroll(KImGuiSpairWrapper.wrap(scroll));
    }

    @Override
    public void setNextWindowScroll(float scrollX, float scrollY) {
        ImGui.setNextWindowScroll(scrollX, scrollY);
    }

    @Override
    public void setNextWindowBgAlpha(float alpha) {
        ImGui.setNextWindowBgAlpha(alpha);
    }

    @Override
    public void setNextWindowViewport(int viewportId) {
        ImGui.setNextWindowViewport(viewportId);
    }

    @Override
    public void setWindowPos(float posX, float posY) {
        ImGui.setWindowPos(posX, posY);
    }

    @Override
    public void setWindowPos(final KVector2f pos, int cond) {
        ImGui.setWindowPos(KImGuiSpairWrapper.wrap(pos), cond);
    }

    @Override
    public void setWindowPos(float posX, float posY, int cond) {
        ImGui.setWindowPos(posX, posY, cond);
    }

    @Override
    public void setWindowSize(float sizeX, float sizeY) {
        ImGui.setWindowSize(sizeX, sizeY);
    }

    @Override
    public void setWindowSize(final KVector2f size, int cond) {
        ImGui.setWindowSize(KImGuiSpairWrapper.wrap(size), cond);
    }

    @Override
    public void setWindowSize(float sizeX, float sizeY, int cond) {
        ImGui.setWindowSize(sizeX, sizeY, cond);
    }

    @Override
    public void setWindowCollapsed(boolean collapsed, int cond) {
        ImGui.setWindowCollapsed(collapsed, cond);
    }

    @Override
    public void setWindowFocus() {
        ImGui.setWindowFocus();
    }

    @Override
    public void setWindowFontScale(float scale) {
        ImGui.setWindowFontScale(scale);
    }

    @Override
    public void setWindowPos(final String name, final KVector2f pos) {
        ImGui.setWindowPos(name, KImGuiSpairWrapper.wrap(pos));
    }

    @Override
    public void setWindowPos(final String name, float posX, float posY) {
        ImGui.setWindowPos(name, posX, posY);
    }

    @Override
    public void setWindowPos(final String name, final KVector2f pos, int cond) {
        ImGui.setWindowPos(name, KImGuiSpairWrapper.wrap(pos), cond);
    }

    @Override
    public void setWindowPos(final String name, float posX, float posY, int cond) {
        ImGui.setWindowPos(name, posX, posY, cond);
    }

    @Override
    public void setWindowSize(final String name, final KVector2f size) {
        ImGui.setWindowSize(name, KImGuiSpairWrapper.wrap(size));
    }

    @Override
    public void setWindowSize(final String name, float sizeX, float sizeY) {
        ImGui.setWindowSize(name, sizeX, sizeY);
    }

    @Override
    public void setWindowSize(final String name, final KVector2f size, int cond) {
        ImGui.setWindowSize(name, KImGuiSpairWrapper.wrap(size), cond);
    }

    @Override
    public void setWindowSize(final String name, float sizeX, float sizeY, int cond) {
        ImGui.setWindowSize(name, sizeX, sizeY, cond);
    }

    @Override
    public void setWindowCollapsed(final String name, boolean collapsed) {
        ImGui.setWindowCollapsed(name, collapsed);
    }

    @Override
    public void setWindowCollapsed(final String name, boolean collapsed, int cond) {
        ImGui.setWindowCollapsed(name, collapsed, cond);
    }

    @Override
    public void setWindowFocus(final String name) {
        ImGui.setWindowFocus(name);
    }

    @Override
    public KVector2f getContentRegionAvail() {
        return KImGuiSpairUnwrapper.wrap(ImGui.getContentRegionAvail());
    }

    @Override
    public float getContentRegionAvailX() {
        return ImGui.getContentRegionAvailX();
    }

    @Override
    public float getContentRegionAvailY() {
        return ImGui.getContentRegionAvailY();
    }

    @Override
    public KVector2f getContentRegionMax() {
        return KImGuiSpairUnwrapper.wrap(ImGui.getContentRegionMax());
    }

    @Override
    public float getContentRegionMaxX() {
        return ImGui.getContentRegionMaxX();
    }

    @Override
    public float getContentRegionMaxY() {
        return ImGui.getContentRegionMaxY();
    }

    @Override
    public KVector2f getWindowContentRegionMin() {
        return KImGuiSpairUnwrapper.wrap(ImGui.getWindowContentRegionMin());
    }

    @Override
    public float getWindowContentRegionMinX() {
        return ImGui.getWindowContentRegionMinX();
    }

    @Override
    public float getWindowContentRegionMinY() {
        return ImGui.getWindowContentRegionMinY();
    }

    @Override
    public KVector2f getWindowContentRegionMax() {
        return KImGuiSpairUnwrapper.wrap(ImGui.getWindowContentRegionMax());
    }

    @Override
    public float getWindowContentRegionMaxX() {
        return ImGui.getWindowContentRegionMaxX();
    }

    @Override
    public float getWindowContentRegionMaxY() {
        return ImGui.getWindowContentRegionMaxY();
    }

    @Override
    public float getScrollX() {
        return ImGui.getScrollX();
    }

    @Override
    public void setScrollX(float scrollX) {
        ImGui.setScrollX(scrollX);
    }

    @Override
    public float getScrollY() {
        return ImGui.getScrollY();
    }

    @Override
    public void setScrollY(float scrollY) {
        ImGui.setScrollY(scrollY);
    }

    @Override
    public float getScrollMaxX() {
        return ImGui.getScrollMaxX();
    }

    @Override
    public float getScrollMaxY() {
        return ImGui.getScrollMaxY();
    }

    @Override
    public void setScrollHereX() {
        ImGui.setScrollHereX();
    }

    @Override
    public void setScrollHereX(float centerXRatio) {
        ImGui.setScrollHereX(centerXRatio);
    }

    @Override
    public void setScrollHereY() {
        ImGui.setScrollHereY();
    }

    @Override
    public void setScrollHereY(float centerYRatio) {
        ImGui.setScrollHereY(centerYRatio);
    }

    @Override
    public void setScrollFromPosX(float localX) {
        ImGui.setScrollFromPosX(localX);
    }

    @Override
    public void setScrollFromPosX(float localX, float centerXRatio) {
        ImGui.setScrollFromPosX(localX, centerXRatio);
    }

    @Override
    public void setScrollFromPosY(float localY) {
        ImGui.setScrollFromPosY(localY);
    }

    @Override
    public void setScrollFromPosY(float localY, float centerYRatio) {
        ImGui.setScrollFromPosY(localY, centerYRatio);
    }

    @Override
    public void pushFont(final KImFont font) {
        ImGui.pushFont(KImGuiSpairUnboxer.unbox(font));
    }

    @Override
    public void popFont() {
        ImGui.popFont();
    }

    @Override
    public void pushStyleColor(int imGuiCol, int r, int g, int b, int a) {
        ImGui.pushStyleColor(imGuiCol, r, g, b, a);
    }

    @Override
    public void pushStyleColor(int imGuiCol, final KVector4f col) {
        ImGui.pushStyleColor(imGuiCol, KImGuiSpairWrapper.wrap(col));
    }

    @Override
    public void pushStyleColor(int imGuiCol, float colX, float colY, float colZ, float colW) {
        ImGui.pushStyleColor(imGuiCol, colX, colY, colZ, colW);
    }

    @Override
    public void pushStyleColor(int imGuiCol, int col) {
        ImGui.pushStyleColor(imGuiCol, col);
    }

    @Override
    public void popStyleColor() {
        ImGui.popStyleColor();
    }

    @Override
    public void popStyleColor(int count) {
        ImGui.popStyleColor(count);
    }

    @Override
    public void pushStyleVar(int imGuiStyleVar, float val) {
        ImGui.pushStyleVar(imGuiStyleVar, val);
    }

    @Override
    public void pushStyleVar(int imGuiStyleVar, final KVector2f val) {
        ImGui.pushStyleVar(imGuiStyleVar, KImGuiSpairWrapper.wrap(val));
    }

    @Override
    public void pushStyleVar(int imGuiStyleVar, float valX, float valY) {
        ImGui.pushStyleVar(imGuiStyleVar, valX, valY);
    }

    @Override
    public void popStyleVar() {
        ImGui.popStyleVar();
    }

    @Override
    public void popStyleVar(int count) {
        ImGui.popStyleVar(count);
    }

    @Override
    public void pushTabStop(boolean tabStop) {
        ImGui.pushTabStop(tabStop);
    }

    @Override
    public void popTabStop() {
        ImGui.popTabStop();
    }

    @Override
    public void pushButtonRepeat(boolean repeat) {
        ImGui.pushButtonRepeat(repeat);
    }

    @Override
    public void popButtonRepeat() {
        ImGui.popButtonRepeat();
    }

    @Override
    public void pushItemWidth(float itemWidth) {
        ImGui.pushItemWidth(itemWidth);
    }

    @Override
    public void popItemWidth() {
        ImGui.popItemWidth();
    }

    @Override
    public void setNextItemWidth(float itemWidth) {
        ImGui.setNextItemWidth(itemWidth);
    }

    @Override
    public float calcItemWidth() {
        return ImGui.calcItemWidth();
    }

    @Override
    public void pushTextWrapPos() {
        ImGui.pushTextWrapPos();
    }

    @Override
    public void pushTextWrapPos(float wrapLocalPosX) {
        ImGui.pushTextWrapPos(wrapLocalPosX);
    }

    @Override
    public void popTextWrapPos() {
        ImGui.popTextWrapPos();
    }

    @Override
    public KImFont getFont() {
        return KImGuiSpairUnwrapper.wrap(ImGui.getFont());
    }

    @Override
    public int getFontSize() {
        return ImGui.getFontSize();
    }

    @Override
    public KVector2f getFontTexUvWhitePixel() {
        return KImGuiSpairUnwrapper.wrap(ImGui.getFontTexUvWhitePixel());
    }

    @Override
    public float getFontTexUvWhitePixelX() {
        return ImGui.getFontTexUvWhitePixelX();
    }

    @Override
    public float getFontTexUvWhitePixelY() {
        return ImGui.getFontTexUvWhitePixelY();
    }

    @Override
    public int getColorU32(int idx) {
        return ImGui.getColorU32(idx);
    }

    @Override
    public int getColorU32(int idx, float alphaMul) {
        return ImGui.getColorU32(idx, alphaMul);
    }

    @Override
    public int getColorU32(final KVector4f col) {
        return ImGui.getColorU32(KImGuiSpairWrapper.wrap(col));
    }

    @Override
    public int getColorU32(float colX, float colY, float colZ, float colW) {
        return ImGui.getColorU32(colX, colY, colZ, colW);
    }

    @Override
    public int getColorU32i(int col) {
        return ImGui.getColorU32i(col);
    }

    @Override
    public int getColorU32i(int col, float alphaMul) {
        return ImGui.getColorU32i(col, alphaMul);
    }

    @Override
    public KVector4f getStyleColorVec4(int imGuiColIdx) {
        return KImGuiSpairUnwrapper.wrap(ImGui.getStyleColorVec4(imGuiColIdx));
    }

    @Override
    public float getStyleColorVec4X(int imGuiColIdx) {
        return ImGui.getStyleColorVec4X(imGuiColIdx);
    }

    @Override
    public float getStyleColorVec4Y(int imGuiColIdx) {
        return ImGui.getStyleColorVec4Y(imGuiColIdx);
    }

    @Override
    public float getStyleColorVec4Z(int imGuiColIdx) {
        return ImGui.getStyleColorVec4Z(imGuiColIdx);
    }

    @Override
    public float getStyleColorVec4W(int imGuiColIdx) {
        return ImGui.getStyleColorVec4W(imGuiColIdx);
    }

    @Override
    public KVector2f getCursorScreenPos() {
        return KImGuiSpairUnwrapper.wrap(ImGui.getCursorScreenPos());
    }

    @Override
    public void setCursorScreenPos(final KVector2f pos) {
        ImGui.setCursorScreenPos(KImGuiSpairWrapper.wrap(pos));
    }

    @Override
    public float getCursorScreenPosX() {
        return ImGui.getCursorScreenPosX();
    }

    @Override
    public float getCursorScreenPosY() {
        return ImGui.getCursorScreenPosY();
    }

    @Override
    public void setCursorScreenPos(float posX, float posY) {
        ImGui.setCursorScreenPos(posX, posY);
    }

    @Override
    public KVector2f getCursorPos() {
        return KImGuiSpairUnwrapper.wrap(ImGui.getCursorPos());
    }

    @Override
    public void setCursorPos(final KVector2f localPos) {
        ImGui.setCursorPos(KImGuiSpairWrapper.wrap(localPos));
    }

    @Override
    public float getCursorPosX() {
        return ImGui.getCursorPosX();
    }

    @Override
    public void setCursorPosX(float localX) {
        ImGui.setCursorPosX(localX);
    }

    @Override
    public float getCursorPosY() {
        return ImGui.getCursorPosY();
    }

    @Override
    public void setCursorPosY(float localY) {
        ImGui.setCursorPosY(localY);
    }

    @Override
    public void setCursorPos(float localPosX, float localPosY) {
        ImGui.setCursorPos(localPosX, localPosY);
    }

    @Override
    public KVector2f getCursorStartPos() {
        return KImGuiSpairUnwrapper.wrap(ImGui.getCursorStartPos());
    }

    @Override
    public float getCursorStartPosX() {
        return ImGui.getCursorStartPosX();
    }

    @Override
    public float getCursorStartPosY() {
        return ImGui.getCursorStartPosY();
    }

    @Override
    public void separator() {
        ImGui.separator();
    }

    @Override
    public void sameLine() {
        ImGui.sameLine();
    }

    @Override
    public void sameLine(float offsetFromStartX) {
        ImGui.sameLine(offsetFromStartX);
    }

    @Override
    public void sameLine(float offsetFromStartX, float spacing) {
        ImGui.sameLine(offsetFromStartX, spacing);
    }

    @Override
    public void newLine() {
        ImGui.newLine();
    }

    @Override
    public void spacing() {
        ImGui.spacing();
    }

    @Override
    public void dummy(final KVector2f size) {
        ImGui.dummy(KImGuiSpairWrapper.wrap(size));
    }

    @Override
    public void dummy(float sizeX, float sizeY) {
        ImGui.dummy(sizeX, sizeY);
    }

    @Override
    public void indent() {
        ImGui.indent();
    }

    @Override
    public void indent(float indentW) {
        ImGui.indent(indentW);
    }

    @Override
    public void unindent() {
        ImGui.unindent();
    }

    @Override
    public void unindent(float indentW) {
        ImGui.unindent(indentW);
    }

    @Override
    public void beginGroup() {
        ImGui.beginGroup();
    }

    @Override
    public void endGroup() {
        ImGui.endGroup();
    }

    @Override
    public void alignTextToFramePadding() {
        ImGui.alignTextToFramePadding();
    }

    @Override
    public float getTextLineHeight() {
        return ImGui.getTextLineHeight();
    }

    @Override
    public float getTextLineHeightWithSpacing() {
        return ImGui.getTextLineHeightWithSpacing();
    }

    @Override
    public float getFrameHeight() {
        return ImGui.getFrameHeight();
    }

    @Override
    public float getFrameHeightWithSpacing() {
        return ImGui.getFrameHeightWithSpacing();
    }

    @Override
    public void pushID(final String strId) {
        ImGui.pushID(strId);
    }

    @Override
    public void pushID(final String strIdBegin, final String strIdEnd) {
        ImGui.pushID(strIdBegin, strIdEnd);
    }

    @Override
    public void pushID(long ptrId) {
        ImGui.pushID(ptrId);
    }

    @Override
    public void pushID(int intId) {
        ImGui.pushID(intId);
    }

    @Override
    public void popID() {
        ImGui.popID();
    }

    @Override
    public int getID(final String strId) {
        return ImGui.getID(strId);
    }

    @Override
    public int getID(final String strIdBegin, final String strIdEnd) {
        return ImGui.getID(strIdBegin, strIdEnd);
    }

    @Override
    public int getID(long ptrId) {
        return ImGui.getID(ptrId);
    }

    @Override
    public void textUnformatted(final String text) {
        ImGui.textUnformatted(text);
    }

    @Override
    public void textUnformatted(final String text, final String textEnd) {
        ImGui.textUnformatted(text, textEnd);
    }

    @Override
    public void text(final String text) {
        ImGui.text(text);
    }

    @Override
    public void textColored(final KVector4f col, final String text) {
        ImGui.textColored(KImGuiSpairWrapper.wrap(col), text);
    }

    @Override
    public void textColored(float colX, float colY, float colZ, float colW, final String text) {
        ImGui.textColored(colX, colY, colZ, colW, text);
    }

    @Override
    public void textColored(int r, int g, int b, int a, final String text) {
        ImGui.textColored(r, g, b, a, text);
    }

    @Override
    public void textColored(int col, final String text) {
        ImGui.textColored(col, text);
    }

    @Override
    public void textDisabled(final String text) {
        ImGui.textDisabled(text);
    }

    @Override
    public void textWrapped(final String text) {
        ImGui.textWrapped(text);
    }

    @Override
    public void labelText(final String label, final String text) {
        ImGui.labelText(label, text);
    }

    @Override
    public void bulletText(final String text) {
        ImGui.bulletText(text);
    }

    @Override
    public void separatorText(final String label) {
        ImGui.separatorText(label);
    }

    @Override
    public boolean button(final String label) {
        return ImGui.button(label);
    }

    @Override
    public boolean button(final String label, final KVector2f size) {
        return ImGui.button(label, KImGuiSpairWrapper.wrap(size));
    }

    @Override
    public boolean button(final String label, float sizeX, float sizeY) {
        return ImGui.button(label, sizeX, sizeY);
    }

    @Override
    public boolean smallButton(final String label) {
        return ImGui.smallButton(label);
    }

    @Override
    public boolean invisibleButton(final String strId, final KVector2f size) {
        return ImGui.invisibleButton(strId, KImGuiSpairWrapper.wrap(size));
    }

    @Override
    public boolean invisibleButton(final String strId, float sizeX, float sizeY) {
        return ImGui.invisibleButton(strId, sizeX, sizeY);
    }

    @Override
    public boolean invisibleButton(final String strId, final KVector2f size, int imGuiButtonFlags) {
        return ImGui.invisibleButton(strId, KImGuiSpairWrapper.wrap(size), imGuiButtonFlags);
    }

    @Override
    public boolean invisibleButton(
        final String strId,
        float sizeX,
        float sizeY,
        int imGuiButtonFlags
    ) {
        return ImGui.invisibleButton(strId, sizeX, sizeY, imGuiButtonFlags);
    }

    @Override
    public boolean arrowButton(final String strId, int dir) {
        return ImGui.arrowButton(strId, dir);
    }

    @Override
    public boolean checkbox(final String label, boolean active) {
        return ImGui.checkbox(label, active);
    }

    @Override
    public boolean checkbox(final String label, final KBooleanReference data) {
        ImBoolean b = new ImBoolean(data.get());
        boolean result = ImGui.checkbox(label, b);
        data.set(b.get());
        return result;
    }

    @Override
    public boolean checkboxFlags(final String label, final KIntReference flags, int flagsValue) {
        ImInt i = new ImInt(flags.get());
        boolean result = ImGui.checkboxFlags(label, i, flagsValue);
        flags.set(i.get());
        return result;
    }

    @Override
    public boolean radioButton(final String label, boolean active) {
        return ImGui.radioButton(label, active);
    }

    @Override
    public boolean radioButton(final String label, final KIntReference v, int vButton) {
        ImInt i = new ImInt(v.get());
        boolean result = ImGui.radioButton(label, i, vButton);
        v.set(i.get());
        return result;
    }

    @Override
    public void progressBar(float fraction) {
        ImGui.progressBar(fraction);
    }

    @Override
    public void progressBar(float fraction, final KVector2f size) {
        ImGui.progressBar(fraction, KImGuiSpairWrapper.wrap(size));
    }

    @Override
    public void progressBar(float fraction, float sizeX, float sizeY) {
        ImGui.progressBar(fraction, sizeX, sizeY);
    }

    @Override
    public void progressBar(float fraction, final KVector2f size, final String overlay) {
        ImGui.progressBar(fraction, KImGuiSpairWrapper.wrap(size), overlay);
    }

    @Override
    public void progressBar(float fraction, float sizeX, float sizeY, final String overlay) {
        ImGui.progressBar(fraction, sizeX, sizeY, overlay);
    }

    @Override
    public void progressBar(float fraction, final String overlay) {
        ImGui.progressBar(fraction, overlay);
    }

    @Override
    public void bullet() {
        ImGui.bullet();
    }

    @Override
    public void image(long userTextureId, final KVector2f imageSize) {
        ImGui.image(userTextureId, KImGuiSpairWrapper.wrap(imageSize));
    }

    @Override
    public void image(long userTextureId, float imageSizeX, float imageSizeY) {
        ImGui.image(userTextureId, imageSizeX, imageSizeY);
    }

    @Override
    public void image(long userTextureId, final KVector2f imageSize, final KVector2f uv0) {
        ImGui.image(
            userTextureId,
            KImGuiSpairWrapper.wrap(imageSize),
            KImGuiSpairWrapper.wrap(uv0)
        );
    }

    @Override
    public void image(
        long userTextureId,
        float imageSizeX,
        float imageSizeY,
        float uv0X,
        float uv0Y
    ) {
        ImGui.image(userTextureId, imageSizeX, imageSizeY, uv0X, uv0Y);
    }

    @Override
    public void image(
        long userTextureId,
        final KVector2f imageSize,
        final KVector2f uv0,
        final KVector2f uv1
    ) {
        ImGui.image(
            userTextureId,
            KImGuiSpairWrapper.wrap(imageSize),
            KImGuiSpairWrapper.wrap(uv0),
            KImGuiSpairWrapper.wrap(uv1)
        );
    }

    @Override
    public void image(
        long userTextureId,
        float imageSizeX,
        float imageSizeY,
        float uv0X,
        float uv0Y,
        float uv1X,
        float uv1Y
    ) {
        ImGui.image(userTextureId, imageSizeX, imageSizeY, uv0X, uv0Y, uv1X, uv1Y);
    }

    @Override
    public void image(
        long userTextureId,
        final KVector2f imageSize,
        final KVector2f uv0,
        final KVector2f uv1,
        final KVector4f tintCol
    ) {
        ImGui.image(
            userTextureId,
            KImGuiSpairWrapper.wrap(imageSize),
            KImGuiSpairWrapper.wrap(uv0),
            KImGuiSpairWrapper.wrap(uv1),
            KImGuiSpairWrapper.wrap(tintCol)
        );
    }

    @Override
    public void image(
        long userTextureId,
        float imageSizeX,
        float imageSizeY,
        float uv0X,
        float uv0Y,
        float uv1X,
        float uv1Y,
        float tintColX,
        float tintColY,
        float tintColZ,
        float tintColW
    ) {
        ImGui.image(
            userTextureId,
            imageSizeX,
            imageSizeY,
            uv0X,
            uv0Y,
            uv1X,
            uv1Y,
            tintColX,
            tintColY,
            tintColZ,
            tintColW
        );
    }

    @Override
    public void image(
        long userTextureId,
        final KVector2f imageSize,
        final KVector2f uv0,
        final KVector2f uv1,
        final KVector4f tintCol,
        final KVector4f borderCol
    ) {
        ImGui.image(
            userTextureId,
            KImGuiSpairWrapper.wrap(imageSize),
            KImGuiSpairWrapper.wrap(uv0),
            KImGuiSpairWrapper.wrap(uv1),
            KImGuiSpairWrapper.wrap(tintCol),
            KImGuiSpairWrapper.wrap(borderCol)
        );
    }

    @Override
    public void image(
        long userTextureId,
        float imageSizeX,
        float imageSizeY,
        float uv0X,
        float uv0Y,
        float uv1X,
        float uv1Y,
        float tintColX,
        float tintColY,
        float tintColZ,
        float tintColW,
        float borderColX,
        float borderColY,
        float borderColZ,
        float borderColW
    ) {
        ImGui.image(
            userTextureId,
            imageSizeX,
            imageSizeY,
            uv0X,
            uv0Y,
            uv1X,
            uv1Y,
            tintColX,
            tintColY,
            tintColZ,
            tintColW,
            borderColX,
            borderColY,
            borderColZ,
            borderColW
        );
    }

    @Override
    public boolean imageButton(final String strId, long userTextureId, final KVector2f imageSize) {
        return ImGui.imageButton(strId, userTextureId, KImGuiSpairWrapper.wrap(imageSize));
    }

    @Override
    public boolean imageButton(
        final String strId,
        long userTextureId,
        float imageSizeX,
        float imageSizeY
    ) {
        return ImGui.imageButton(strId, userTextureId, imageSizeX, imageSizeY);
    }

    @Override
    public boolean imageButton(
        final String strId,
        long userTextureId,
        final KVector2f imageSize,
        final KVector2f uv0
    ) {
        return ImGui.imageButton(
            strId,
            userTextureId,
            KImGuiSpairWrapper.wrap(imageSize),
            KImGuiSpairWrapper.wrap(uv0)
        );
    }

    @Override
    public boolean imageButton(
        final String strId,
        long userTextureId,
        float imageSizeX,
        float imageSizeY,
        float uv0X,
        float uv0Y
    ) {
        return ImGui.imageButton(strId, userTextureId, imageSizeX, imageSizeY, uv0X, uv0Y);
    }

    @Override
    public boolean imageButton(
        final String strId,
        long userTextureId,
        final KVector2f imageSize,
        final KVector2f uv0,
        final KVector2f uv1
    ) {
        return ImGui.imageButton(
            strId,
            userTextureId,
            KImGuiSpairWrapper.wrap(imageSize),
            KImGuiSpairWrapper.wrap(uv0),
            KImGuiSpairWrapper.wrap(uv1)
        );
    }

    @Override
    public boolean imageButton(
        final String strId,
        long userTextureId,
        float imageSizeX,
        float imageSizeY,
        float uv0X,
        float uv0Y,
        float uv1X,
        float uv1Y
    ) {
        return ImGui.imageButton(
            strId,
            userTextureId,
            imageSizeX,
            imageSizeY,
            uv0X,
            uv0Y,
            uv1X,
            uv1Y
        );
    }

    @Override
    public boolean imageButton(
        final String strId,
        long userTextureId,
        final KVector2f imageSize,
        final KVector2f uv0,
        final KVector2f uv1,
        final KVector4f bgCol
    ) {
        return ImGui.imageButton(
            strId,
            userTextureId,
            KImGuiSpairWrapper.wrap(imageSize),
            KImGuiSpairWrapper.wrap(uv0),
            KImGuiSpairWrapper.wrap(uv1),
            KImGuiSpairWrapper.wrap(bgCol)
        );
    }

    @Override
    public boolean imageButton(
        final String strId,
        long userTextureId,
        float imageSizeX,
        float imageSizeY,
        float uv0X,
        float uv0Y,
        float uv1X,
        float uv1Y,
        float bgColX,
        float bgColY,
        float bgColZ,
        float bgColW
    ) {
        return ImGui.imageButton(
            strId,
            userTextureId,
            imageSizeX,
            imageSizeY,
            uv0X,
            uv0Y,
            uv1X,
            uv1Y,
            bgColX,
            bgColY,
            bgColZ,
            bgColW
        );
    }

    @Override
    public boolean imageButton(
        final String strId,
        long userTextureId,
        final KVector2f imageSize,
        final KVector2f uv0,
        final KVector2f uv1,
        final KVector4f bgCol,
        final KVector4f tintCol
    ) {
        return ImGui.imageButton(
            strId,
            userTextureId,
            KImGuiSpairWrapper.wrap(imageSize),
            KImGuiSpairWrapper.wrap(uv0),
            KImGuiSpairWrapper.wrap(uv1),
            KImGuiSpairWrapper.wrap(bgCol),
            KImGuiSpairWrapper.wrap(tintCol)
        );
    }

    @Override
    public boolean imageButton(
        final String strId,
        long userTextureId,
        float imageSizeX,
        float imageSizeY,
        float uv0X,
        float uv0Y,
        float uv1X,
        float uv1Y,
        float bgColX,
        float bgColY,
        float bgColZ,
        float bgColW,
        float tintColX,
        float tintColY,
        float tintColZ,
        float tintColW
    ) {
        return ImGui.imageButton(
            strId,
            userTextureId,
            imageSizeX,
            imageSizeY,
            uv0X,
            uv0Y,
            uv1X,
            uv1Y,
            bgColX,
            bgColY,
            bgColZ,
            bgColW,
            tintColX,
            tintColY,
            tintColZ,
            tintColW
        );
    }

    @Override
    public boolean beginCombo(final String label, final String previewValue) {
        return ImGui.beginCombo(label, previewValue);
    }

    @Override
    public boolean beginCombo(final String label, final String previewValue, int imGuiComboFlags) {
        return ImGui.beginCombo(label, previewValue, imGuiComboFlags);
    }

    @Override
    public void endCombo() {
        ImGui.endCombo();
    }

    @Override
    public boolean combo(
        final String label,
        final KIntReference currentItem,
        final String[] items
    ) {
        ImInt i = new ImInt(currentItem.get());
        boolean result = ImGui.combo(label, i, items);
        currentItem.set(i.get());
        return result;
    }

    @Override
    public boolean combo(
        final String label,
        final KIntReference currentItem,
        final String[] items,
        int popupMaxHeightInItems
    ) {
        ImInt i = new ImInt(currentItem.get());
        boolean result = ImGui.combo(label, i, items, popupMaxHeightInItems);
        currentItem.set(i.get());
        return result;
    }

    @Override
    public boolean combo(
        final String label,
        final KIntReference currentItem,
        final String itemsSeparatedByZeros
    ) {
        ImInt i = new ImInt(currentItem.get());
        boolean result = ImGui.combo(label, i, itemsSeparatedByZeros);
        currentItem.set(i.get());
        return result;
    }

    @Override
    public boolean combo(
        final String label,
        final KIntReference currentItem,
        final String itemsSeparatedByZeros,
        int popupMaxHeightInItems
    ) {
        ImInt i = new ImInt(currentItem.get());
        boolean result = ImGui.combo(label, i, itemsSeparatedByZeros, popupMaxHeightInItems);
        currentItem.set(i.get());
        return result;
    }

    @Override
    public boolean dragFloat(final String label, final float[] v) {
        return ImGui.dragFloat(label, v);
    }

    @Override
    public boolean dragFloat(final String label, final float[] v, float vSpeed) {
        return ImGui.dragFloat(label, v, vSpeed);
    }

    @Override
    public boolean dragFloat(final String label, final float[] v, float vSpeed, float vMin) {
        return ImGui.dragFloat(label, v, vSpeed, vMin);
    }

    @Override
    public boolean dragFloat(
        final String label,
        final float[] v,
        float vSpeed,
        float vMin,
        float vMax
    ) {
        return ImGui.dragFloat(label, v, vSpeed, vMin, vMax);
    }

    @Override
    public boolean dragFloat(
        final String label,
        final float[] v,
        float vSpeed,
        float vMin,
        float vMax,
        final String format
    ) {
        return ImGui.dragFloat(label, v, vSpeed, vMin, vMax, format);
    }

    @Override
    public boolean dragFloat(
        final String label,
        final float[] v,
        float vSpeed,
        float vMin,
        float vMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.dragFloat(label, v, vSpeed, vMin, vMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean dragFloat(
        final String label,
        final float[] v,
        float vSpeed,
        float vMin,
        float vMax,
        int imGuiSliderFlags
    ) {
        return ImGui.dragFloat(label, v, vSpeed, vMin, vMax, imGuiSliderFlags);
    }

    @Override
    public boolean dragFloat2(final String label, final float[] v) {
        return ImGui.dragFloat2(label, v);
    }

    @Override
    public boolean dragFloat2(final String label, final float[] v, float vSpeed) {
        return ImGui.dragFloat2(label, v, vSpeed);
    }

    @Override
    public boolean dragFloat2(final String label, final float[] v, float vSpeed, float vMin) {
        return ImGui.dragFloat2(label, v, vSpeed, vMin);
    }

    @Override
    public boolean dragFloat2(
        final String label,
        final float[] v,
        float vSpeed,
        float vMin,
        float vMax
    ) {
        return ImGui.dragFloat2(label, v, vSpeed, vMin, vMax);
    }

    @Override
    public boolean dragFloat2(
        final String label,
        final float[] v,
        float vSpeed,
        float vMin,
        float vMax,
        final String format
    ) {
        return ImGui.dragFloat2(label, v, vSpeed, vMin, vMax, format);
    }

    @Override
    public boolean dragFloat2(
        final String label,
        final float[] v,
        float vSpeed,
        float vMin,
        float vMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.dragFloat2(label, v, vSpeed, vMin, vMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean dragFloat2(
        final String label,
        final float[] v,
        float vSpeed,
        float vMin,
        float vMax,
        int imGuiSliderFlags
    ) {
        return ImGui.dragFloat2(label, v, vSpeed, vMin, vMax, imGuiSliderFlags);
    }

    @Override
    public boolean dragFloat3(final String label, final float[] v) {
        return ImGui.dragFloat3(label, v);
    }

    @Override
    public boolean dragFloat3(final String label, final float[] v, float vSpeed) {
        return ImGui.dragFloat3(label, v, vSpeed);
    }

    @Override
    public boolean dragFloat3(final String label, final float[] v, float vSpeed, float vMin) {
        return ImGui.dragFloat3(label, v, vSpeed, vMin);
    }

    @Override
    public boolean dragFloat3(
        final String label,
        final float[] v,
        float vSpeed,
        float vMin,
        float vMax
    ) {
        return ImGui.dragFloat3(label, v, vSpeed, vMin, vMax);
    }

    @Override
    public boolean dragFloat3(
        final String label,
        final float[] v,
        float vSpeed,
        float vMin,
        float vMax,
        final String format
    ) {
        return ImGui.dragFloat3(label, v, vSpeed, vMin, vMax, format);
    }

    @Override
    public boolean dragFloat3(
        final String label,
        final float[] v,
        float vSpeed,
        float vMin,
        float vMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.dragFloat3(label, v, vSpeed, vMin, vMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean dragFloat3(
        final String label,
        final float[] v,
        float vSpeed,
        float vMin,
        float vMax,
        int imGuiSliderFlags
    ) {
        return ImGui.dragFloat3(label, v, vSpeed, vMin, vMax, imGuiSliderFlags);
    }

    @Override
    public boolean dragFloat4(final String label, final float[] v) {
        return ImGui.dragFloat4(label, v);
    }

    @Override
    public boolean dragFloat4(final String label, final float[] v, float vSpeed) {
        return ImGui.dragFloat4(label, v, vSpeed);
    }

    @Override
    public boolean dragFloat4(final String label, final float[] v, float vSpeed, float vMin) {
        return ImGui.dragFloat4(label, v, vSpeed, vMin);
    }

    @Override
    public boolean dragFloat4(
        final String label,
        final float[] v,
        float vSpeed,
        float vMin,
        float vMax
    ) {
        return ImGui.dragFloat4(label, v, vSpeed, vMin, vMax);
    }

    @Override
    public boolean dragFloat4(
        final String label,
        final float[] v,
        float vSpeed,
        float vMin,
        float vMax,
        final String format
    ) {
        return ImGui.dragFloat4(label, v, vSpeed, vMin, vMax, format);
    }

    @Override
    public boolean dragFloat4(
        final String label,
        final float[] v,
        float vSpeed,
        float vMin,
        float vMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.dragFloat4(label, v, vSpeed, vMin, vMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean dragFloat4(
        final String label,
        final float[] v,
        float vSpeed,
        float vMin,
        float vMax,
        int imGuiSliderFlags
    ) {
        return ImGui.dragFloat4(label, v, vSpeed, vMin, vMax, imGuiSliderFlags);
    }

    @Override
    public boolean dragFloatRange2(
        final String label,
        final float[] vCurrentMin,
        final float[] vCurrentMax,
        float vSpeed
    ) {
        return ImGui.dragFloatRange2(label, vCurrentMin, vCurrentMax, vSpeed);
    }

    @Override
    public boolean dragFloatRange2(
        final String label,
        final float[] vCurrentMin,
        final float[] vCurrentMax,
        float vSpeed,
        float vMin
    ) {
        return ImGui.dragFloatRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin);
    }

    @Override
    public boolean dragFloatRange2(
        final String label,
        final float[] vCurrentMin,
        final float[] vCurrentMax,
        float vSpeed,
        float vMin,
        float vMax
    ) {
        return ImGui.dragFloatRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax);
    }

    @Override
    public boolean dragFloatRange2(
        final String label,
        final float[] vCurrentMin,
        final float[] vCurrentMax,
        float vSpeed,
        float vMin,
        float vMax,
        final String format
    ) {
        return ImGui.dragFloatRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax, format);
    }

    @Override
    public boolean dragFloatRange2(
        final String label,
        final float[] vCurrentMin,
        final float[] vCurrentMax,
        float vSpeed,
        float vMin,
        float vMax,
        final String format,
        final String formatMax
    ) {
        return ImGui.dragFloatRange2(
            label,
            vCurrentMin,
            vCurrentMax,
            vSpeed,
            vMin,
            vMax,
            format,
            formatMax
        );
    }

    @Override
    public boolean dragFloatRange2(
        final String label,
        final float[] vCurrentMin,
        final float[] vCurrentMax,
        float vSpeed,
        float vMin,
        float vMax,
        final String format,
        final String formatMax,
        int imGuiSliderFlags
    ) {
        return ImGui.dragFloatRange2(
            label,
            vCurrentMin,
            vCurrentMax,
            vSpeed,
            vMin,
            vMax,
            format,
            formatMax,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean dragInt(final String label, final int[] v) {
        return ImGui.dragInt(label, v);
    }

    @Override
    public boolean dragInt(final String label, final int[] v, float vSpeed) {
        return ImGui.dragInt(label, v, vSpeed);
    }

    @Override
    public boolean dragInt(final String label, final int[] v, float vSpeed, int vMin) {
        return ImGui.dragInt(label, v, vSpeed, vMin);
    }

    @Override
    public boolean dragInt(final String label, final int[] v, float vSpeed, int vMin, int vMax) {
        return ImGui.dragInt(label, v, vSpeed, vMin, vMax);
    }

    @Override
    public boolean dragInt(
        final String label,
        final int[] v,
        float vSpeed,
        int vMin,
        int vMax,
        final String format
    ) {
        return ImGui.dragInt(label, v, vSpeed, vMin, vMax, format);
    }

    @Override
    public boolean dragInt(
        final String label,
        final int[] v,
        float vSpeed,
        int vMin,
        int vMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.dragInt(label, v, vSpeed, vMin, vMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean dragInt(
        final String label,
        final int[] v,
        float vSpeed,
        int vMin,
        int vMax,
        int imGuiSliderFlags
    ) {
        return ImGui.dragInt(label, v, vSpeed, vMin, vMax, imGuiSliderFlags);
    }

    @Override
    public boolean dragInt2(final String label, final int[] v) {
        return ImGui.dragInt2(label, v);
    }

    @Override
    public boolean dragInt2(final String label, final int[] v, float vSpeed) {
        return ImGui.dragInt2(label, v, vSpeed);
    }

    @Override
    public boolean dragInt2(final String label, final int[] v, float vSpeed, int vMin) {
        return ImGui.dragInt2(label, v, vSpeed, vMin);
    }

    @Override
    public boolean dragInt2(final String label, final int[] v, float vSpeed, int vMin, int vMax) {
        return ImGui.dragInt2(label, v, vSpeed, vMin, vMax);
    }

    @Override
    public boolean dragInt2(
        final String label,
        final int[] v,
        float vSpeed,
        int vMin,
        int vMax,
        final String format
    ) {
        return ImGui.dragInt2(label, v, vSpeed, vMin, vMax, format);
    }

    @Override
    public boolean dragInt2(
        final String label,
        final int[] v,
        float vSpeed,
        int vMin,
        int vMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.dragInt2(label, v, vSpeed, vMin, vMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean dragInt2(
        final String label,
        final int[] v,
        float vSpeed,
        int vMin,
        int vMax,
        int imGuiSliderFlags
    ) {
        return ImGui.dragInt2(label, v, vSpeed, vMin, vMax, imGuiSliderFlags);
    }

    @Override
    public boolean dragInt3(final String label, final int[] v) {
        return ImGui.dragInt3(label, v);
    }

    @Override
    public boolean dragInt3(final String label, final int[] v, float vSpeed) {
        return ImGui.dragInt3(label, v, vSpeed);
    }

    @Override
    public boolean dragInt3(final String label, final int[] v, float vSpeed, int vMin) {
        return ImGui.dragInt3(label, v, vSpeed, vMin);
    }

    @Override
    public boolean dragInt3(final String label, final int[] v, float vSpeed, int vMin, int vMax) {
        return ImGui.dragInt3(label, v, vSpeed, vMin, vMax);
    }

    @Override
    public boolean dragInt3(
        final String label,
        final int[] v,
        float vSpeed,
        int vMin,
        int vMax,
        final String format
    ) {
        return ImGui.dragInt3(label, v, vSpeed, vMin, vMax, format);
    }

    @Override
    public boolean dragInt3(
        final String label,
        final int[] v,
        float vSpeed,
        int vMin,
        int vMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.dragInt3(label, v, vSpeed, vMin, vMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean dragInt3(
        final String label,
        final int[] v,
        float vSpeed,
        int vMin,
        int vMax,
        int imGuiSliderFlags
    ) {
        return ImGui.dragInt3(label, v, vSpeed, vMin, vMax, imGuiSliderFlags);
    }

    @Override
    public boolean dragInt4(final String label, final int[] v) {
        return ImGui.dragInt4(label, v);
    }

    @Override
    public boolean dragInt4(final String label, final int[] v, float vSpeed) {
        return ImGui.dragInt4(label, v, vSpeed);
    }

    @Override
    public boolean dragInt4(final String label, final int[] v, float vSpeed, int vMin) {
        return ImGui.dragInt4(label, v, vSpeed, vMin);
    }

    @Override
    public boolean dragInt4(final String label, final int[] v, float vSpeed, int vMin, int vMax) {
        return ImGui.dragInt4(label, v, vSpeed, vMin, vMax);
    }

    @Override
    public boolean dragInt4(
        final String label,
        final int[] v,
        float vSpeed,
        int vMin,
        int vMax,
        final String format
    ) {
        return ImGui.dragInt4(label, v, vSpeed, vMin, vMax, format);
    }

    @Override
    public boolean dragInt4(
        final String label,
        final int[] v,
        float vSpeed,
        int vMin,
        int vMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.dragInt4(label, v, vSpeed, vMin, vMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean dragInt4(
        final String label,
        final int[] v,
        float vSpeed,
        int vMin,
        int vMax,
        int imGuiSliderFlags
    ) {
        return ImGui.dragInt4(label, v, vSpeed, vMin, vMax, imGuiSliderFlags);
    }

    @Override
    public boolean dragIntRange2(
        final String label,
        final int[] vCurrentMin,
        final int[] vCurrentMax
    ) {
        return ImGui.dragIntRange2(label, vCurrentMin, vCurrentMax);
    }

    @Override
    public boolean dragIntRange2(
        final String label,
        final int[] vCurrentMin,
        final int[] vCurrentMax,
        float vSpeed
    ) {
        return ImGui.dragIntRange2(label, vCurrentMin, vCurrentMax, vSpeed);
    }

    @Override
    public boolean dragIntRange2(
        final String label,
        final int[] vCurrentMin,
        final int[] vCurrentMax,
        float vSpeed,
        int vMin
    ) {
        return ImGui.dragIntRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin);
    }

    @Override
    public boolean dragIntRange2(
        final String label,
        final int[] vCurrentMin,
        final int[] vCurrentMax,
        float vSpeed,
        int vMin,
        int vMax
    ) {
        return ImGui.dragIntRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax);
    }

    @Override
    public boolean dragIntRange2(
        final String label,
        final int[] vCurrentMin,
        final int[] vCurrentMax,
        float vSpeed,
        int vMin,
        int vMax,
        final String format
    ) {
        return ImGui.dragIntRange2(label, vCurrentMin, vCurrentMax, vSpeed, vMin, vMax, format);
    }

    @Override
    public boolean dragIntRange2(
        final String label,
        final int[] vCurrentMin,
        final int[] vCurrentMax,
        float vSpeed,
        int vMin,
        int vMax,
        final String format,
        final String formatMax
    ) {
        return ImGui.dragIntRange2(
            label,
            vCurrentMin,
            vCurrentMax,
            vSpeed,
            vMin,
            vMax,
            format,
            formatMax
        );
    }

    @Override
    public boolean dragIntRange2(
        final String label,
        final int[] vCurrentMin,
        final int[] vCurrentMax,
        float vSpeed,
        int vMin,
        int vMax,
        final String format,
        final String formatMax,
        int imGuiSliderFlags
    ) {
        return ImGui.dragIntRange2(
            label,
            vCurrentMin,
            vCurrentMax,
            vSpeed,
            vMin,
            vMax,
            format,
            formatMax,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean dragScalar(final String label, final short[] pData) {
        return ImGui.dragScalar(label, pData);
    }

    @Override
    public boolean dragScalar(final String label, final short[] pData, float vSpeed) {
        return ImGui.dragScalar(label, pData, vSpeed);
    }

    @Override
    public boolean dragScalar(final String label, final short[] pData, float vSpeed, short pMin) {
        return ImGui.dragScalar(label, pData, vSpeed, pMin);
    }

    @Override
    public boolean dragScalar(
        final String label,
        final short[] pData,
        float vSpeed,
        short pMin,
        short pMax
    ) {
        return ImGui.dragScalar(label, pData, vSpeed, pMin, pMax);
    }

    @Override
    public boolean dragScalar(
        final String label,
        final short[] pData,
        float vSpeed,
        short pMin,
        short pMax,
        final String format
    ) {
        return ImGui.dragScalar(label, pData, vSpeed, pMin, pMax, format);
    }

    @Override
    public boolean dragScalar(
        final String label,
        final short[] pData,
        float vSpeed,
        short pMin,
        short pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.dragScalar(label, pData, vSpeed, pMin, pMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean dragScalar(final String label, final int[] pData) {
        return ImGui.dragScalar(label, pData);
    }

    @Override
    public boolean dragScalar(final String label, final int[] pData, float vSpeed) {
        return ImGui.dragScalar(label, pData, vSpeed);
    }

    @Override
    public boolean dragScalar(final String label, final int[] pData, float vSpeed, int pMin) {
        return ImGui.dragScalar(label, pData, vSpeed, pMin);
    }

    @Override
    public boolean dragScalar(
        final String label,
        final int[] pData,
        float vSpeed,
        int pMin,
        int pMax
    ) {
        return ImGui.dragScalar(label, pData, vSpeed, pMin, pMax);
    }

    @Override
    public boolean dragScalar(
        final String label,
        final int[] pData,
        float vSpeed,
        int pMin,
        int pMax,
        final String format
    ) {
        return ImGui.dragScalar(label, pData, vSpeed, pMin, pMax, format);
    }

    @Override
    public boolean dragScalar(
        final String label,
        final int[] pData,
        float vSpeed,
        int pMin,
        int pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.dragScalar(label, pData, vSpeed, pMin, pMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean dragScalar(final String label, final long[] pData) {
        return ImGui.dragScalar(label, pData);
    }

    @Override
    public boolean dragScalar(final String label, final long[] pData, float vSpeed) {
        return ImGui.dragScalar(label, pData, vSpeed);
    }

    @Override
    public boolean dragScalar(final String label, final long[] pData, float vSpeed, long pMin) {
        return ImGui.dragScalar(label, pData, vSpeed, pMin);
    }

    @Override
    public boolean dragScalar(
        final String label,
        final long[] pData,
        float vSpeed,
        long pMin,
        long pMax
    ) {
        return ImGui.dragScalar(label, pData, vSpeed, pMin, pMax);
    }

    @Override
    public boolean dragScalar(
        final String label,
        final long[] pData,
        float vSpeed,
        long pMin,
        long pMax,
        final String format
    ) {
        return ImGui.dragScalar(label, pData, vSpeed, pMin, pMax, format);
    }

    @Override
    public boolean dragScalar(
        final String label,
        final long[] pData,
        float vSpeed,
        long pMin,
        long pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.dragScalar(label, pData, vSpeed, pMin, pMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean dragScalar(final String label, final float[] pData) {
        return ImGui.dragScalar(label, pData);
    }

    @Override
    public boolean dragScalar(final String label, final float[] pData, float vSpeed) {
        return ImGui.dragScalar(label, pData, vSpeed);
    }

    @Override
    public boolean dragScalar(final String label, final float[] pData, float vSpeed, float pMin) {
        return ImGui.dragScalar(label, pData, vSpeed, pMin);
    }

    @Override
    public boolean dragScalar(
        final String label,
        final float[] pData,
        float vSpeed,
        float pMin,
        float pMax
    ) {
        return ImGui.dragScalar(label, pData, vSpeed, pMin, pMax);
    }

    @Override
    public boolean dragScalar(
        final String label,
        final float[] pData,
        float vSpeed,
        float pMin,
        float pMax,
        final String format
    ) {
        return ImGui.dragScalar(label, pData, vSpeed, pMin, pMax, format);
    }

    @Override
    public boolean dragScalar(
        final String label,
        final float[] pData,
        float vSpeed,
        float pMin,
        float pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.dragScalar(label, pData, vSpeed, pMin, pMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean dragScalar(final String label, final double[] pData) {
        return ImGui.dragScalar(label, pData);
    }

    @Override
    public boolean dragScalar(final String label, final double[] pData, float vSpeed) {
        return ImGui.dragScalar(label, pData, vSpeed);
    }

    @Override
    public boolean dragScalar(final String label, final double[] pData, float vSpeed, double pMin) {
        return ImGui.dragScalar(label, pData, vSpeed, pMin);
    }

    @Override
    public boolean dragScalar(
        final String label,
        final double[] pData,
        float vSpeed,
        double pMin,
        double pMax
    ) {
        return ImGui.dragScalar(label, pData, vSpeed, pMin, pMax);
    }

    @Override
    public boolean dragScalar(
        final String label,
        final double[] pData,
        float vSpeed,
        double pMin,
        double pMax,
        final String format
    ) {
        return ImGui.dragScalar(label, pData, vSpeed, pMin, pMax, format);
    }

    @Override
    public boolean dragScalar(
        final String label,
        final double[] pData,
        float vSpeed,
        double pMin,
        double pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.dragScalar(label, pData, vSpeed, pMin, pMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean dragScalarN(final String label, final short[] pData, int components) {
        return ImGui.dragScalarN(label, pData, components);
    }

    @Override
    public boolean dragScalarN(
        final String label,
        final short[] pData,
        int components,
        float vSpeed
    ) {
        return ImGui.dragScalarN(label, pData, components, vSpeed);
    }

    @Override
    public boolean dragScalarN(
        final String label,
        final short[] pData,
        int components,
        float vSpeed,
        short pMin
    ) {
        return ImGui.dragScalarN(label, pData, components, vSpeed, pMin);
    }

    @Override
    public boolean dragScalarN(
        final String label,
        final short[] pData,
        int components,
        float vSpeed,
        short pMin,
        short pMax
    ) {
        return ImGui.dragScalarN(label, pData, components, vSpeed, pMin, pMax);
    }

    @Override
    public boolean dragScalarN(
        final String label,
        final short[] pData,
        int components,
        float vSpeed,
        short pMin,
        short pMax,
        final String format
    ) {
        return ImGui.dragScalarN(label, pData, components, vSpeed, pMin, pMax, format);
    }

    @Override
    public boolean dragScalarN(
        final String label,
        final short[] pData,
        int components,
        float vSpeed,
        short pMin,
        short pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.dragScalarN(
            label,
            pData,
            components,
            vSpeed,
            pMin,
            pMax,
            format,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean dragScalarN(final String label, final int[] pData, int components) {
        return ImGui.dragScalarN(label, pData, components);
    }

    @Override
    public boolean dragScalarN(
        final String label,
        final int[] pData,
        int components,
        float vSpeed
    ) {
        return ImGui.dragScalarN(label, pData, components, vSpeed);
    }

    @Override
    public boolean dragScalarN(
        final String label,
        final int[] pData,
        int components,
        float vSpeed,
        int pMin
    ) {
        return ImGui.dragScalarN(label, pData, components, vSpeed, pMin);
    }

    @Override
    public boolean dragScalarN(
        final String label,
        final int[] pData,
        int components,
        float vSpeed,
        int pMin,
        int pMax
    ) {
        return ImGui.dragScalarN(label, pData, components, vSpeed, pMin, pMax);
    }

    @Override
    public boolean dragScalarN(
        final String label,
        final int[] pData,
        int components,
        float vSpeed,
        int pMin,
        int pMax,
        final String format
    ) {
        return ImGui.dragScalarN(label, pData, components, vSpeed, pMin, pMax, format);
    }

    @Override
    public boolean dragScalarN(
        final String label,
        final int[] pData,
        int components,
        float vSpeed,
        int pMin,
        int pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.dragScalarN(
            label,
            pData,
            components,
            vSpeed,
            pMin,
            pMax,
            format,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean dragScalarN(final String label, final long[] pData, int components) {
        return ImGui.dragScalarN(label, pData, components);
    }

    @Override
    public boolean dragScalarN(
        final String label,
        final long[] pData,
        int components,
        float vSpeed
    ) {
        return ImGui.dragScalarN(label, pData, components, vSpeed);
    }

    @Override
    public boolean dragScalarN(
        final String label,
        final long[] pData,
        int components,
        float vSpeed,
        long pMin
    ) {
        return ImGui.dragScalarN(label, pData, components, vSpeed, pMin);
    }

    @Override
    public boolean dragScalarN(
        final String label,
        final long[] pData,
        int components,
        float vSpeed,
        long pMin,
        long pMax
    ) {
        return ImGui.dragScalarN(label, pData, components, vSpeed, pMin, pMax);
    }

    @Override
    public boolean dragScalarN(
        final String label,
        final long[] pData,
        int components,
        float vSpeed,
        long pMin,
        long pMax,
        final String format
    ) {
        return ImGui.dragScalarN(label, pData, components, vSpeed, pMin, pMax, format);
    }

    @Override
    public boolean dragScalarN(
        final String label,
        final long[] pData,
        int components,
        float vSpeed,
        long pMin,
        long pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.dragScalarN(
            label,
            pData,
            components,
            vSpeed,
            pMin,
            pMax,
            format,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean dragScalarN(final String label, final float[] pData, int components) {
        return ImGui.dragScalarN(label, pData, components);
    }

    @Override
    public boolean dragScalarN(
        final String label,
        final float[] pData,
        int components,
        float vSpeed
    ) {
        return ImGui.dragScalarN(label, pData, components, vSpeed);
    }

    @Override
    public boolean dragScalarN(
        final String label,
        final float[] pData,
        int components,
        float vSpeed,
        float pMin
    ) {
        return ImGui.dragScalarN(label, pData, components, vSpeed, pMin);
    }

    @Override
    public boolean dragScalarN(
        final String label,
        final float[] pData,
        int components,
        float vSpeed,
        float pMin,
        float pMax
    ) {
        return ImGui.dragScalarN(label, pData, components, vSpeed, pMin, pMax);
    }

    @Override
    public boolean dragScalarN(
        final String label,
        final float[] pData,
        int components,
        float vSpeed,
        float pMin,
        float pMax,
        final String format
    ) {
        return ImGui.dragScalarN(label, pData, components, vSpeed, pMin, pMax, format);
    }

    @Override
    public boolean dragScalarN(
        final String label,
        final float[] pData,
        int components,
        float vSpeed,
        float pMin,
        float pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.dragScalarN(
            label,
            pData,
            components,
            vSpeed,
            pMin,
            pMax,
            format,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean dragScalarN(final String label, final double[] pData, int components) {
        return ImGui.dragScalarN(label, pData, components);
    }

    @Override
    public boolean dragScalarN(
        final String label,
        final double[] pData,
        int components,
        float vSpeed
    ) {
        return ImGui.dragScalarN(label, pData, components, vSpeed);
    }

    @Override
    public boolean dragScalarN(
        final String label,
        final double[] pData,
        int components,
        float vSpeed,
        double pMin
    ) {
        return ImGui.dragScalarN(label, pData, components, vSpeed, pMin);
    }

    @Override
    public boolean dragScalarN(
        final String label,
        final double[] pData,
        int components,
        float vSpeed,
        double pMin,
        double pMax
    ) {
        return ImGui.dragScalarN(label, pData, components, vSpeed, pMin, pMax);
    }

    @Override
    public boolean dragScalarN(
        final String label,
        final double[] pData,
        int components,
        float vSpeed,
        double pMin,
        double pMax,
        final String format
    ) {
        return ImGui.dragScalarN(label, pData, components, vSpeed, pMin, pMax, format);
    }

    @Override
    public boolean dragScalarN(
        final String label,
        final double[] pData,
        int components,
        float vSpeed,
        double pMin,
        double pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.dragScalarN(
            label,
            pData,
            components,
            vSpeed,
            pMin,
            pMax,
            format,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean sliderFloat(final String label, final float[] v, float vMin, float vMax) {
        return ImGui.sliderFloat(label, v, vMin, vMax);
    }

    @Override
    public boolean sliderFloat(
        final String label,
        final float[] v,
        float vMin,
        float vMax,
        final String format
    ) {
        return ImGui.sliderFloat(label, v, vMin, vMax, format);
    }

    @Override
    public boolean sliderFloat(
        final String label,
        final float[] v,
        float vMin,
        float vMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.sliderFloat(label, v, vMin, vMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean sliderFloat(
        final String label,
        final float[] v,
        float vMin,
        float vMax,
        int imGuiSliderFlags
    ) {
        return ImGui.sliderFloat(label, v, vMin, vMax, imGuiSliderFlags);
    }

    @Override
    public boolean sliderFloat2(final String label, final float[] v, float vMin, float vMax) {
        return ImGui.sliderFloat2(label, v, vMin, vMax);
    }

    @Override
    public boolean sliderFloat2(
        final String label,
        final float[] v,
        float vMin,
        float vMax,
        final String format
    ) {
        return ImGui.sliderFloat2(label, v, vMin, vMax, format);
    }

    @Override
    public boolean sliderFloat2(
        final String label,
        final float[] v,
        float vMin,
        float vMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.sliderFloat2(label, v, vMin, vMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean sliderFloat2(
        final String label,
        final float[] v,
        float vMin,
        float vMax,
        int imGuiSliderFlags
    ) {
        return ImGui.sliderFloat2(label, v, vMin, vMax, imGuiSliderFlags);
    }

    @Override
    public boolean sliderFloat3(final String label, final float[] v, float vMin, float vMax) {
        return ImGui.sliderFloat3(label, v, vMin, vMax);
    }

    @Override
    public boolean sliderFloat3(
        final String label,
        final float[] v,
        float vMin,
        float vMax,
        final String format
    ) {
        return ImGui.sliderFloat3(label, v, vMin, vMax, format);
    }

    @Override
    public boolean sliderFloat3(
        final String label,
        final float[] v,
        float vMin,
        float vMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.sliderFloat3(label, v, vMin, vMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean sliderFloat3(
        final String label,
        final float[] v,
        float vMin,
        float vMax,
        int imGuiSliderFlags
    ) {
        return ImGui.sliderFloat3(label, v, vMin, vMax, imGuiSliderFlags);
    }

    @Override
    public boolean sliderFloat4(final String label, final float[] v, float vMin, float vMax) {
        return ImGui.sliderFloat4(label, v, vMin, vMax);
    }

    @Override
    public boolean sliderFloat4(
        final String label,
        final float[] v,
        float vMin,
        float vMax,
        final String format
    ) {
        return ImGui.sliderFloat4(label, v, vMin, vMax, format);
    }

    @Override
    public boolean sliderFloat4(
        final String label,
        final float[] v,
        float vMin,
        float vMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.sliderFloat4(label, v, vMin, vMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean sliderFloat4(
        final String label,
        final float[] v,
        float vMin,
        float vMax,
        int imGuiSliderFlags
    ) {
        return ImGui.sliderFloat4(label, v, vMin, vMax, imGuiSliderFlags);
    }

    @Override
    public boolean sliderAngle(final String label, final float[] vRad) {
        return ImGui.sliderAngle(label, vRad);
    }

    @Override
    public boolean sliderAngle(final String label, final float[] vRad, float vDegreesMin) {
        return ImGui.sliderAngle(label, vRad, vDegreesMin);
    }

    @Override
    public boolean sliderAngle(
        final String label,
        final float[] vRad,
        float vDegreesMin,
        float vDegreesMax
    ) {
        return ImGui.sliderAngle(label, vRad, vDegreesMin, vDegreesMax);
    }

    @Override
    public boolean sliderAngle(
        final String label,
        final float[] vRad,
        float vDegreesMin,
        float vDegreesMax,
        final String format
    ) {
        return ImGui.sliderAngle(label, vRad, vDegreesMin, vDegreesMax, format);
    }

    @Override
    public boolean sliderAngle(
        final String label,
        final float[] vRad,
        float vDegreesMin,
        float vDegreesMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.sliderAngle(label, vRad, vDegreesMin, vDegreesMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean sliderAngle(
        final String label,
        final float[] vRad,
        float vDegreesMin,
        float vDegreesMax,
        int imGuiSliderFlags
    ) {
        return ImGui.sliderAngle(label, vRad, vDegreesMin, vDegreesMax, imGuiSliderFlags);
    }

    @Override
    public boolean sliderInt(final String label, final int[] v, int vMin, int vMax) {
        return ImGui.sliderInt(label, v, vMin, vMax);
    }

    @Override
    public boolean sliderInt(
        final String label,
        final int[] v,
        int vMin,
        int vMax,
        final String format
    ) {
        return ImGui.sliderInt(label, v, vMin, vMax, format);
    }

    @Override
    public boolean sliderInt(
        final String label,
        final int[] v,
        int vMin,
        int vMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.sliderInt(label, v, vMin, vMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean sliderInt(
        final String label,
        final int[] v,
        int vMin,
        int vMax,
        int imGuiSliderFlags
    ) {
        return ImGui.sliderInt(label, v, vMin, vMax, imGuiSliderFlags);
    }

    @Override
    public boolean sliderInt2(final String label, final int[] v, int vMin, int vMax) {
        return ImGui.sliderInt2(label, v, vMin, vMax);
    }

    @Override
    public boolean sliderInt2(
        final String label,
        final int[] v,
        int vMin,
        int vMax,
        final String format
    ) {
        return ImGui.sliderInt2(label, v, vMin, vMax, format);
    }

    @Override
    public boolean sliderInt2(
        final String label,
        final int[] v,
        int vMin,
        int vMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.sliderInt2(label, v, vMin, vMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean sliderInt2(
        final String label,
        final int[] v,
        int vMin,
        int vMax,
        int imGuiSliderFlags
    ) {
        return ImGui.sliderInt2(label, v, vMin, vMax, imGuiSliderFlags);
    }

    @Override
    public boolean sliderInt3(final String label, final int[] v, int vMin, int vMax) {
        return ImGui.sliderInt3(label, v, vMin, vMax);
    }

    @Override
    public boolean sliderInt3(
        final String label,
        final int[] v,
        int vMin,
        int vMax,
        final String format
    ) {
        return ImGui.sliderInt3(label, v, vMin, vMax, format);
    }

    @Override
    public boolean sliderInt3(
        final String label,
        final int[] v,
        int vMin,
        int vMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.sliderInt3(label, v, vMin, vMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean sliderInt3(
        final String label,
        final int[] v,
        int vMin,
        int vMax,
        int imGuiSliderFlags
    ) {
        return ImGui.sliderInt3(label, v, vMin, vMax, imGuiSliderFlags);
    }

    @Override
    public boolean sliderInt4(final String label, final int[] v, int vMin, int vMax) {
        return ImGui.sliderInt4(label, v, vMin, vMax);
    }

    @Override
    public boolean sliderInt4(
        final String label,
        final int[] v,
        int vMin,
        int vMax,
        final String format
    ) {
        return ImGui.sliderInt4(label, v, vMin, vMax, format);
    }

    @Override
    public boolean sliderInt4(
        final String label,
        final int[] v,
        int vMin,
        int vMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.sliderInt4(label, v, vMin, vMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean sliderInt4(
        final String label,
        final int[] v,
        int vMin,
        int vMax,
        int imGuiSliderFlags
    ) {
        return ImGui.sliderInt4(label, v, vMin, vMax, imGuiSliderFlags);
    }

    @Override
    public boolean sliderScalar(final String label, final short[] pData, short pMin, short pMax) {
        return ImGui.sliderScalar(label, pData, pMin, pMax);
    }

    @Override
    public boolean sliderScalar(
        final String label,
        final short[] pData,
        short pMin,
        short pMax,
        final String format
    ) {
        return ImGui.sliderScalar(label, pData, pMin, pMax, format);
    }

    @Override
    public boolean sliderScalar(
        final String label,
        final short[] pData,
        short pMin,
        short pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.sliderScalar(label, pData, pMin, pMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean sliderScalar(final String label, final int[] pData, int pMin, int pMax) {
        return ImGui.sliderScalar(label, pData, pMin, pMax);
    }

    @Override
    public boolean sliderScalar(
        final String label,
        final int[] pData,
        int pMin,
        int pMax,
        final String format
    ) {
        return ImGui.sliderScalar(label, pData, pMin, pMax, format);
    }

    @Override
    public boolean sliderScalar(
        final String label,
        final int[] pData,
        int pMin,
        int pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.sliderScalar(label, pData, pMin, pMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean sliderScalar(final String label, final long[] pData, long pMin, long pMax) {
        return ImGui.sliderScalar(label, pData, pMin, pMax);
    }

    @Override
    public boolean sliderScalar(
        final String label,
        final long[] pData,
        long pMin,
        long pMax,
        final String format
    ) {
        return ImGui.sliderScalar(label, pData, pMin, pMax, format);
    }

    @Override
    public boolean sliderScalar(
        final String label,
        final long[] pData,
        long pMin,
        long pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.sliderScalar(label, pData, pMin, pMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean sliderScalar(final String label, final float[] pData, float pMin, float pMax) {
        return ImGui.sliderScalar(label, pData, pMin, pMax);
    }

    @Override
    public boolean sliderScalar(
        final String label,
        final float[] pData,
        float pMin,
        float pMax,
        final String format
    ) {
        return ImGui.sliderScalar(label, pData, pMin, pMax, format);
    }

    @Override
    public boolean sliderScalar(
        final String label,
        final float[] pData,
        float pMin,
        float pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.sliderScalar(label, pData, pMin, pMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean sliderScalar(
        final String label,
        final double[] pData,
        double pMin,
        double pMax
    ) {
        return ImGui.sliderScalar(label, pData, pMin, pMax);
    }

    @Override
    public boolean sliderScalar(
        final String label,
        final double[] pData,
        double pMin,
        double pMax,
        final String format
    ) {
        return ImGui.sliderScalar(label, pData, pMin, pMax, format);
    }

    @Override
    public boolean sliderScalar(
        final String label,
        final double[] pData,
        double pMin,
        double pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.sliderScalar(label, pData, pMin, pMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean sliderScalarN(
        final String label,
        final short[] pData,
        int components,
        short pMin,
        short pMax
    ) {
        return ImGui.sliderScalarN(label, pData, components, pMin, pMax);
    }

    @Override
    public boolean sliderScalarN(
        final String label,
        final short[] pData,
        int components,
        short pMin,
        short pMax,
        final String format
    ) {
        return ImGui.sliderScalarN(label, pData, components, pMin, pMax, format);
    }

    @Override
    public boolean sliderScalarN(
        final String label,
        final short[] pData,
        int components,
        short pMin,
        short pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.sliderScalarN(label, pData, components, pMin, pMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean sliderScalarN(
        final String label,
        final int[] pData,
        int components,
        int pMin,
        int pMax
    ) {
        return ImGui.sliderScalarN(label, pData, components, pMin, pMax);
    }

    @Override
    public boolean sliderScalarN(
        final String label,
        final int[] pData,
        int components,
        int pMin,
        int pMax,
        final String format
    ) {
        return ImGui.sliderScalarN(label, pData, components, pMin, pMax, format);
    }

    @Override
    public boolean sliderScalarN(
        final String label,
        final int[] pData,
        int components,
        int pMin,
        int pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.sliderScalarN(label, pData, components, pMin, pMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean sliderScalarN(
        final String label,
        final long[] pData,
        int components,
        long pMin,
        long pMax
    ) {
        return ImGui.sliderScalarN(label, pData, components, pMin, pMax);
    }

    @Override
    public boolean sliderScalarN(
        final String label,
        final long[] pData,
        int components,
        long pMin,
        long pMax,
        final String format
    ) {
        return ImGui.sliderScalarN(label, pData, components, pMin, pMax, format);
    }

    @Override
    public boolean sliderScalarN(
        final String label,
        final long[] pData,
        int components,
        long pMin,
        long pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.sliderScalarN(label, pData, components, pMin, pMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean sliderScalarN(
        final String label,
        final float[] pData,
        int components,
        float pMin,
        float pMax
    ) {
        return ImGui.sliderScalarN(label, pData, components, pMin, pMax);
    }

    @Override
    public boolean sliderScalarN(
        final String label,
        final float[] pData,
        int components,
        float pMin,
        float pMax,
        final String format
    ) {
        return ImGui.sliderScalarN(label, pData, components, pMin, pMax, format);
    }

    @Override
    public boolean sliderScalarN(
        final String label,
        final float[] pData,
        int components,
        float pMin,
        float pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.sliderScalarN(label, pData, components, pMin, pMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean sliderScalarN(
        final String label,
        final double[] pData,
        int components,
        double pMin,
        double pMax
    ) {
        return ImGui.sliderScalarN(label, pData, components, pMin, pMax);
    }

    @Override
    public boolean sliderScalarN(
        final String label,
        final double[] pData,
        int components,
        double pMin,
        double pMax,
        final String format
    ) {
        return ImGui.sliderScalarN(label, pData, components, pMin, pMax, format);
    }

    @Override
    public boolean sliderScalarN(
        final String label,
        final double[] pData,
        int components,
        double pMin,
        double pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.sliderScalarN(label, pData, components, pMin, pMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean vSliderFloat(
        final String label,
        final KVector2f size,
        final float[] v,
        float vMin,
        float vMax
    ) {
        return ImGui.vSliderFloat(label, KImGuiSpairWrapper.wrap(size), v, vMin, vMax);
    }

    @Override
    public boolean vSliderFloat(
        final String label,
        float sizeX,
        float sizeY,
        final float[] v,
        float vMin,
        float vMax
    ) {
        return ImGui.vSliderFloat(label, sizeX, sizeY, v, vMin, vMax);
    }

    @Override
    public boolean vSliderFloat(
        final String label,
        final KVector2f size,
        final float[] v,
        float vMin,
        float vMax,
        final String format
    ) {
        return ImGui.vSliderFloat(label, KImGuiSpairWrapper.wrap(size), v, vMin, vMax, format);
    }

    @Override
    public boolean vSliderFloat(
        final String label,
        float sizeX,
        float sizeY,
        final float[] v,
        float vMin,
        float vMax,
        final String format
    ) {
        return ImGui.vSliderFloat(label, sizeX, sizeY, v, vMin, vMax, format);
    }

    @Override
    public boolean vSliderFloat(
        final String label,
        final KVector2f size,
        final float[] v,
        float vMin,
        float vMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.vSliderFloat(
            label,
            KImGuiSpairWrapper.wrap(size),
            v,
            vMin,
            vMax,
            format,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean vSliderFloat(
        final String label,
        float sizeX,
        float sizeY,
        final float[] v,
        float vMin,
        float vMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.vSliderFloat(label, sizeX, sizeY, v, vMin, vMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean vSliderFloat(
        final String label,
        final KVector2f size,
        final float[] v,
        float vMin,
        float vMax,
        int imGuiSliderFlags
    ) {
        return ImGui.vSliderFloat(
            label,
            KImGuiSpairWrapper.wrap(size),
            v,
            vMin,
            vMax,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean vSliderFloat(
        final String label,
        float sizeX,
        float sizeY,
        final float[] v,
        float vMin,
        float vMax,
        int imGuiSliderFlags
    ) {
        return ImGui.vSliderFloat(label, sizeX, sizeY, v, vMin, vMax, imGuiSliderFlags);
    }

    @Override
    public boolean vSliderInt(
        final String label,
        final KVector2f size,
        final int[] v,
        int vMin,
        int vMax
    ) {
        return ImGui.vSliderInt(label, KImGuiSpairWrapper.wrap(size), v, vMin, vMax);
    }

    @Override
    public boolean vSliderInt(
        final String label,
        float sizeX,
        float sizeY,
        final int[] v,
        int vMin,
        int vMax
    ) {
        return ImGui.vSliderInt(label, sizeX, sizeY, v, vMin, vMax);
    }

    @Override
    public boolean vSliderInt(
        final String label,
        final KVector2f size,
        final int[] v,
        int vMin,
        int vMax,
        final String format
    ) {
        return ImGui.vSliderInt(label, KImGuiSpairWrapper.wrap(size), v, vMin, vMax, format);
    }

    @Override
    public boolean vSliderInt(
        final String label,
        float sizeX,
        float sizeY,
        final int[] v,
        int vMin,
        int vMax,
        final String format
    ) {
        return ImGui.vSliderInt(label, sizeX, sizeY, v, vMin, vMax, format);
    }

    @Override
    public boolean vSliderInt(
        final String label,
        final KVector2f size,
        final int[] v,
        int vMin,
        int vMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.vSliderInt(
            label,
            KImGuiSpairWrapper.wrap(size),
            v,
            vMin,
            vMax,
            format,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean vSliderInt(
        final String label,
        float sizeX,
        float sizeY,
        final int[] v,
        int vMin,
        int vMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.vSliderInt(label, sizeX, sizeY, v, vMin, vMax, format, imGuiSliderFlags);
    }

    @Override
    public boolean vSliderInt(
        final String label,
        final KVector2f size,
        final int[] v,
        int vMin,
        int vMax,
        int imGuiSliderFlags
    ) {
        return ImGui.vSliderInt(
            label,
            KImGuiSpairWrapper.wrap(size),
            v,
            vMin,
            vMax,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean vSliderInt(
        final String label,
        float sizeX,
        float sizeY,
        final int[] v,
        int vMin,
        int vMax,
        int imGuiSliderFlags
    ) {
        return ImGui.vSliderInt(label, sizeX, sizeY, v, vMin, vMax, imGuiSliderFlags);
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        final KVector2f size,
        final short[] pData,
        short pMin,
        short pMax
    ) {
        return ImGui.vSliderScalar(label, KImGuiSpairWrapper.wrap(size), pData, pMin, pMax);
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        float sizeX,
        float sizeY,
        final short[] pData,
        short pMin,
        short pMax
    ) {
        return ImGui.vSliderScalar(label, sizeX, sizeY, pData, pMin, pMax);
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        final KVector2f size,
        final short[] pData,
        short pMin,
        short pMax,
        final String format
    ) {
        return ImGui.vSliderScalar(label, KImGuiSpairWrapper.wrap(size), pData, pMin, pMax, format);
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        float sizeX,
        float sizeY,
        final short[] pData,
        short pMin,
        short pMax,
        final String format
    ) {
        return ImGui.vSliderScalar(label, sizeX, sizeY, pData, pMin, pMax, format);
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        final KVector2f size,
        final short[] pData,
        short pMin,
        short pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.vSliderScalar(
            label,
            KImGuiSpairWrapper.wrap(size),
            pData,
            pMin,
            pMax,
            format,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        float sizeX,
        float sizeY,
        final short[] pData,
        short pMin,
        short pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.vSliderScalar(
            label,
            sizeX,
            sizeY,
            pData,
            pMin,
            pMax,
            format,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        final KVector2f size,
        final int[] pData,
        int pMin,
        int pMax
    ) {
        return ImGui.vSliderScalar(label, KImGuiSpairWrapper.wrap(size), pData, pMin, pMax);
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        float sizeX,
        float sizeY,
        final int[] pData,
        int pMin,
        int pMax
    ) {
        return ImGui.vSliderScalar(label, sizeX, sizeY, pData, pMin, pMax);
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        final KVector2f size,
        final int[] pData,
        int pMin,
        int pMax,
        final String format
    ) {
        return ImGui.vSliderScalar(label, KImGuiSpairWrapper.wrap(size), pData, pMin, pMax, format);
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        float sizeX,
        float sizeY,
        final int[] pData,
        int pMin,
        int pMax,
        final String format
    ) {
        return ImGui.vSliderScalar(label, sizeX, sizeY, pData, pMin, pMax, format);
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        final KVector2f size,
        final int[] pData,
        int pMin,
        int pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.vSliderScalar(
            label,
            KImGuiSpairWrapper.wrap(size),
            pData,
            pMin,
            pMax,
            format,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        float sizeX,
        float sizeY,
        final int[] pData,
        int pMin,
        int pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.vSliderScalar(
            label,
            sizeX,
            sizeY,
            pData,
            pMin,
            pMax,
            format,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        final KVector2f size,
        final long[] pData,
        long pMin,
        long pMax
    ) {
        return ImGui.vSliderScalar(label, KImGuiSpairWrapper.wrap(size), pData, pMin, pMax);
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        float sizeX,
        float sizeY,
        final long[] pData,
        long pMin,
        long pMax
    ) {
        return ImGui.vSliderScalar(label, sizeX, sizeY, pData, pMin, pMax);
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        final KVector2f size,
        final long[] pData,
        long pMin,
        long pMax,
        final String format
    ) {
        return ImGui.vSliderScalar(label, KImGuiSpairWrapper.wrap(size), pData, pMin, pMax, format);
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        float sizeX,
        float sizeY,
        final long[] pData,
        long pMin,
        long pMax,
        final String format
    ) {
        return ImGui.vSliderScalar(label, sizeX, sizeY, pData, pMin, pMax, format);
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        final KVector2f size,
        final long[] pData,
        long pMin,
        long pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.vSliderScalar(
            label,
            KImGuiSpairWrapper.wrap(size),
            pData,
            pMin,
            pMax,
            format,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        float sizeX,
        float sizeY,
        final long[] pData,
        long pMin,
        long pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.vSliderScalar(
            label,
            sizeX,
            sizeY,
            pData,
            pMin,
            pMax,
            format,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        final KVector2f size,
        final float[] pData,
        float pMin,
        float pMax
    ) {
        return ImGui.vSliderScalar(label, KImGuiSpairWrapper.wrap(size), pData, pMin, pMax);
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        float sizeX,
        float sizeY,
        final float[] pData,
        float pMin,
        float pMax
    ) {
        return ImGui.vSliderScalar(label, sizeX, sizeY, pData, pMin, pMax);
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        final KVector2f size,
        final float[] pData,
        float pMin,
        float pMax,
        final String format
    ) {
        return ImGui.vSliderScalar(label, KImGuiSpairWrapper.wrap(size), pData, pMin, pMax, format);
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        float sizeX,
        float sizeY,
        final float[] pData,
        float pMin,
        float pMax,
        final String format
    ) {
        return ImGui.vSliderScalar(label, sizeX, sizeY, pData, pMin, pMax, format);
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        final KVector2f size,
        final float[] pData,
        float pMin,
        float pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.vSliderScalar(
            label,
            KImGuiSpairWrapper.wrap(size),
            pData,
            pMin,
            pMax,
            format,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        float sizeX,
        float sizeY,
        final float[] pData,
        float pMin,
        float pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.vSliderScalar(
            label,
            sizeX,
            sizeY,
            pData,
            pMin,
            pMax,
            format,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        final KVector2f size,
        final double[] pData,
        double pMin,
        double pMax
    ) {
        return ImGui.vSliderScalar(label, KImGuiSpairWrapper.wrap(size), pData, pMin, pMax);
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        float sizeX,
        float sizeY,
        final double[] pData,
        double pMin,
        double pMax
    ) {
        return ImGui.vSliderScalar(label, sizeX, sizeY, pData, pMin, pMax);
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        final KVector2f size,
        final double[] pData,
        double pMin,
        double pMax,
        final String format
    ) {
        return ImGui.vSliderScalar(label, KImGuiSpairWrapper.wrap(size), pData, pMin, pMax, format);
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        float sizeX,
        float sizeY,
        final double[] pData,
        double pMin,
        double pMax,
        final String format
    ) {
        return ImGui.vSliderScalar(label, sizeX, sizeY, pData, pMin, pMax, format);
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        final KVector2f size,
        final double[] pData,
        double pMin,
        double pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.vSliderScalar(
            label,
            KImGuiSpairWrapper.wrap(size),
            pData,
            pMin,
            pMax,
            format,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean vSliderScalar(
        final String label,
        float sizeX,
        float sizeY,
        final double[] pData,
        double pMin,
        double pMax,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.vSliderScalar(
            label,
            sizeX,
            sizeY,
            pData,
            pMin,
            pMax,
            format,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean inputText(final String label, final KStringReference text) {
        ImString str = new ImString(text.toString());
        boolean result = ImGui.inputText(label, str);
        text.set(str.get());
        return result;
    }

    @Override
    public boolean inputText(
        final String label,
        final KStringReference text,
        int imGuiInputTextFlags
    ) {
        ImString str = new ImString(text.toString());
        boolean result = ImGui.inputText(label, str, imGuiInputTextFlags);
        text.set(str.get());
        return result;
    }

    @Override
    public boolean inputText(
        final String label,
        final KStringReference text,
        int imGuiInputTextFlags,
        final KImGuiInputTextCallback callback
    ) {
        ImString str = new ImString(text.toString());
        boolean result = ImGui.inputText(
            label, str, imGuiInputTextFlags, KImGuiSpairWrapper.wrap(callback)
        );
        text.set(str.get());
        return result;
    }

    @Override
    public boolean inputTextMultiline(final String label, final KStringReference text) {
        ImString str = new ImString(text.toString());
        boolean result = ImGui.inputTextMultiline(label, str);
        text.set(str.get());
        return result;
    }

    @Override
    public boolean inputTextMultiline(
        final String label,
        final KStringReference text,
        float width,
        float height
    ) {
        ImString str = new ImString(text.toString());
        boolean result = ImGui.inputTextMultiline(label, str, width, height);
        text.set(str.get());
        return result;
    }

    @Override
    public boolean inputTextMultiline(
        final String label,
        final KStringReference text,
        int imGuiInputTextFlags
    ) {
        ImString str = new ImString(text.toString());
        boolean result = ImGui.inputTextMultiline(label, str, imGuiInputTextFlags);
        text.set(str.get());
        return result;
    }

    @Override
    public boolean inputTextMultiline(
        final String label,
        final KStringReference text,
        int imGuiInputTextFlags,
        final KImGuiInputTextCallback callback
    ) {
        ImString str = new ImString(text.toString());
        boolean result = ImGui.inputTextMultiline(
            label, str, imGuiInputTextFlags, KImGuiSpairWrapper.wrap(callback)
        );
        text.set(str.get());
        return result;
    }

    @Override
    public boolean inputTextMultiline(
        final String label,
        final KStringReference text,
        float width,
        float height,
        int imGuiInputTextFlags
    ) {
        ImString str = new ImString(text.toString());
        boolean result = ImGui.inputTextMultiline(label, str, width, height, imGuiInputTextFlags);
        text.set(str.get());
        return result;
    }

    @Override
    public boolean inputTextMultiline(
        final String label,
        final KStringReference text,
        float width,
        float height,
        int imGuiInputTextFlags,
        final KImGuiInputTextCallback callback
    ) {
        ImString str = new ImString(text.toString());
        boolean result = ImGui.inputTextMultiline(
            label, str, width, height, imGuiInputTextFlags, KImGuiSpairWrapper.wrap(callback)
        );
        text.set(str.get());
        return result;
    }

    @Override
    public boolean inputTextWithHint(
        final String label,
        final String hint,
        final KStringReference text
    ) {
        ImString str = new ImString(text.toString());
        boolean result = ImGui.inputTextWithHint(label, hint, str);
        text.set(str.get());
        return result;
    }

    @Override
    public boolean inputTextWithHint(
        final String label,
        final String hint,
        final KStringReference text,
        int imGuiInputTextFlags
    ) {
        ImString str = new ImString(text.toString());
        boolean result = ImGui.inputTextWithHint(label, hint, str, imGuiInputTextFlags);
        text.set(str.get());
        return result;
    }

    @Override
    public boolean inputTextWithHint(
        final String label,
        final String hint,
        final KStringReference text,
        int imGuiInputTextFlags,
        final KImGuiInputTextCallback callback
    ) {
        ImString str = new ImString(text.toString());
        boolean result = ImGui.inputTextWithHint(
            label, hint, str, imGuiInputTextFlags, KImGuiSpairWrapper.wrap(callback)
        );
        text.set(str.get());
        return result;
    }

    @Override
    public boolean inputFloat(final String label, final KFloatReference v) {
        ImFloat f = new ImFloat(v.get());
        boolean result = ImGui.inputFloat(label, f);
        v.set(f.get());
        return result;
    }

    @Override
    public boolean inputFloat(final String label, final KFloatReference v, float step) {
        ImFloat f = new ImFloat(v.get());
        boolean result = ImGui.inputFloat(label, f, step);
        v.set(f.get());
        return result;
    }

    @Override
    public boolean inputFloat(
        final String label,
        final KFloatReference v,
        float step,
        float stepFast
    ) {
        ImFloat f = new ImFloat(v.get());
        boolean result = ImGui.inputFloat(label, f, step, stepFast);
        v.set(f.get());
        return result;
    }

    @Override
    public boolean inputFloat(
        final String label,
        final KFloatReference v,
        float step,
        float stepFast,
        final String format
    ) {
        ImFloat f = new ImFloat(v.get());
        boolean result = ImGui.inputFloat(label, f, step, stepFast, format);
        v.set(f.get());
        return result;
    }

    @Override
    public boolean inputFloat(
        final String label,
        final KFloatReference v,
        float step,
        float stepFast,
        final String format,
        int imGuiInputTextFlags
    ) {
        ImFloat f = new ImFloat(v.get());
        boolean result = ImGui.inputFloat(label, f, step, stepFast, format, imGuiInputTextFlags);
        v.set(f.get());
        return result;
    }

    @Override
    public boolean inputFloat(
        final String label,
        final KFloatReference v,
        float step,
        float stepFast,
        int imGuiInputTextFlags
    ) {
        ImFloat f = new ImFloat(v.get());
        boolean result = ImGui.inputFloat(label, f, step, stepFast, imGuiInputTextFlags);
        v.set(f.get());
        return result;
    }

    @Override
    public boolean inputFloat2(final String label, final float[] v) {
        return ImGui.inputFloat2(label, v);
    }

    @Override
    public boolean inputFloat2(final String label, final float[] v, final String format) {
        return ImGui.inputFloat2(label, v, format);
    }

    @Override
    public boolean inputFloat2(
        final String label,
        final float[] v,
        final String format,
        int imGuiInputTextFlags
    ) {
        return ImGui.inputFloat2(label, v, format, imGuiInputTextFlags);
    }

    @Override
    public boolean inputFloat2(final String label, final float[] v, int imGuiInputTextFlags) {
        return ImGui.inputFloat2(label, v, imGuiInputTextFlags);
    }

    @Override
    public boolean inputFloat3(final String label, final float[] v) {
        return ImGui.inputFloat3(label, v);
    }

    @Override
    public boolean inputFloat3(final String label, final float[] v, final String format) {
        return ImGui.inputFloat3(label, v, format);
    }

    @Override
    public boolean inputFloat3(
        final String label,
        final float[] v,
        final String format,
        int imGuiInputTextFlags
    ) {
        return ImGui.inputFloat3(label, v, format, imGuiInputTextFlags);
    }

    @Override
    public boolean inputFloat3(final String label, final float[] v, int imGuiInputTextFlags) {
        return ImGui.inputFloat3(label, v, imGuiInputTextFlags);
    }

    @Override
    public boolean inputFloat4(final String label, final float[] v) {
        return ImGui.inputFloat4(label, v);
    }

    @Override
    public boolean inputFloat4(final String label, final float[] v, final String format) {
        return ImGui.inputFloat4(label, v, format);
    }

    @Override
    public boolean inputFloat4(
        final String label,
        final float[] v,
        final String format,
        int imGuiInputTextFlags
    ) {
        return ImGui.inputFloat4(label, v, format, imGuiInputTextFlags);
    }

    @Override
    public boolean inputFloat4(final String label, final float[] v, int imGuiInputTextFlags) {
        return ImGui.inputFloat4(label, v, imGuiInputTextFlags);
    }

    @Override
    public boolean inputInt(final String label, final KIntReference v) {
        ImInt i = new ImInt(v.get());
        boolean result = ImGui.inputInt(label, i);
        v.set(i.get());
        return result;
    }

    @Override
    public boolean inputInt(final String label, final KIntReference v, int step) {
        ImInt i = new ImInt(v.get());
        boolean result = ImGui.inputInt(label, i, step);
        v.set(i.get());
        return result;
    }

    @Override
    public boolean inputInt(final String label, final KIntReference v, int step, int stepFast) {
        ImInt i = new ImInt(v.get());
        boolean result = ImGui.inputInt(label, i, step, stepFast);
        v.set(i.get());
        return result;
    }

    @Override
    public boolean inputInt(
        final String label,
        final KIntReference v,
        int step,
        int stepFast,
        int imGuiInputTextFlags
    ) {
        ImInt i = new ImInt(v.get());
        boolean result = ImGui.inputInt(label, i, step, stepFast, imGuiInputTextFlags);
        v.set(i.get());
        return result;
    }

    @Override
    public boolean inputInt2(final String label, final int[] v) {
        return ImGui.inputInt2(label, v);
    }

    @Override
    public boolean inputInt2(final String label, final int[] v, int imGuiInputTextFlags) {
        return ImGui.inputInt2(label, v, imGuiInputTextFlags);
    }

    @Override
    public boolean inputInt3(final String label, final int[] v) {
        return ImGui.inputInt3(label, v);
    }

    @Override
    public boolean inputInt3(final String label, final int[] v, int imGuiInputTextFlags) {
        return ImGui.inputInt3(label, v, imGuiInputTextFlags);
    }

    @Override
    public boolean inputInt4(final String label, final int[] v) {
        return ImGui.inputInt4(label, v);
    }

    @Override
    public boolean inputInt4(final String label, final int[] v, int imGuiInputTextFlags) {
        return ImGui.inputInt4(label, v, imGuiInputTextFlags);
    }

    @Override
    public boolean inputDouble(final String label, final KDoubleReference v) {
        ImDouble d = new ImDouble(v.get());
        boolean result = ImGui.inputDouble(label, d);
        v.set(d.get());
        return result;
    }

    @Override
    public boolean inputDouble(final String label, final KDoubleReference v, double step) {
        ImDouble d = new ImDouble(v.get());
        boolean result = ImGui.inputDouble(label, d, step);
        v.set(d.get());
        return result;
    }

    @Override
    public boolean inputDouble(
        final String label,
        final KDoubleReference v,
        double step,
        double stepFast
    ) {
        ImDouble d = new ImDouble(v.get());
        boolean result = ImGui.inputDouble(label, d, step, stepFast);
        v.set(d.get());
        return result;
    }

    @Override
    public boolean inputDouble(
        final String label,
        final KDoubleReference v,
        double step,
        double stepFast,
        final String format
    ) {
        ImDouble d = new ImDouble(v.get());
        boolean result = ImGui.inputDouble(label, d, step, stepFast, format);
        v.set(d.get());
        return result;
    }

    @Override
    public boolean inputDouble(
        final String label,
        final KDoubleReference v,
        double step,
        double stepFast,
        final String format,
        int imGuiInputTextFlags
    ) {
        ImDouble d = new ImDouble(v.get());
        boolean result = ImGui.inputDouble(label, d, step, stepFast, format, imGuiInputTextFlags);
        v.set(d.get());
        return result;
    }

    @Override
    public boolean inputDouble(
        final String label,
        final KDoubleReference v,
        double step,
        double stepFast,
        int imGuiInputTextFlags
    ) {
        ImDouble d = new ImDouble(v.get());
        boolean result = ImGui.inputDouble(label, d, step, stepFast, imGuiInputTextFlags);
        v.set(d.get());
        return result;
    }

    @Override
    public boolean inputScalar(final String label, final KShortReference pData) {
        ImShort s = new ImShort(pData.get());
        boolean result = ImGui.inputScalar(label, s);
        pData.set(s.get());
        return result;
    }

    @Override
    public boolean inputScalar(final String label, final KShortReference pData, short pStep) {
        ImShort s = new ImShort(pData.get());
        boolean result = ImGui.inputScalar(label, s, pStep);
        pData.set(s.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        final KShortReference pData,
        short pStep,
        short pStepFast
    ) {
        ImShort s = new ImShort(pData.get());
        boolean result = ImGui.inputScalar(label, s, pStep, pStepFast);
        pData.set(s.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        final KShortReference pData,
        short pStep,
        short pStepFast,
        final String format
    ) {
        ImShort s = new ImShort(pData.get());
        boolean result = ImGui.inputScalar(label, s, pStep, pStepFast, format);
        pData.set(s.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        final KShortReference pData,
        short pStep,
        short pStepFast,
        final String format,
        int imGuiSliderFlags
    ) {
        ImShort s = new ImShort(pData.get());
        boolean result = ImGui.inputScalar(label, s, pStep, pStepFast, format, imGuiSliderFlags);
        pData.set(s.get());
        return result;
    }

    @Override
    public boolean inputScalar(final String label, final KIntReference pData) {
        ImInt i = new ImInt(pData.get());
        boolean result = ImGui.inputScalar(label, i);
        pData.set(i.get());
        return result;
    }

    @Override
    public boolean inputScalar(final String label, final KIntReference pData, int pStep) {
        ImInt i = new ImInt(pData.get());
        boolean result = ImGui.inputScalar(label, i, pStep);
        pData.set(i.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        final KIntReference pData,
        int pStep,
        int pStepFast
    ) {
        ImInt i = new ImInt(pData.get());
        boolean result = ImGui.inputScalar(label, i, pStep, pStepFast);
        pData.set(i.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        final KIntReference pData,
        int pStep,
        int pStepFast,
        final String format
    ) {
        ImInt i = new ImInt(pData.get());
        boolean result = ImGui.inputScalar(label, i, pStep, pStepFast, format);
        pData.set(i.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        final KIntReference pData,
        int pStep,
        int pStepFast,
        final String format,
        int imGuiSliderFlags
    ) {
        ImInt i = new ImInt(pData.get());
        boolean result = ImGui.inputScalar(label, i, pStep, pStepFast, format, imGuiSliderFlags);
        pData.set(i.get());
        return result;
    }

    @Override
    public boolean inputScalar(final String label, final KLongReference pData) {
        ImLong l = new ImLong(pData.get());
        boolean result = ImGui.inputScalar(label, l);
        pData.set(l.get());
        return result;
    }

    @Override
    public boolean inputScalar(final String label, final KLongReference pData, long pStep) {
        ImLong l = new ImLong(pData.get());
        boolean result = ImGui.inputScalar(label, l, pStep);
        pData.set(l.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        final KLongReference pData,
        long pStep,
        long pStepFast
    ) {
        ImLong l = new ImLong(pData.get());
        boolean result = ImGui.inputScalar(label, l, pStep, pStepFast);
        pData.set(l.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        final KLongReference pData,
        long pStep,
        long pStepFast,
        final String format
    ) {
        ImLong l = new ImLong(pData.get());
        boolean result = ImGui.inputScalar(label, l, pStep, pStepFast, format);
        pData.set(l.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        final KLongReference pData,
        long pStep,
        long pStepFast,
        final String format,
        int imGuiSliderFlags
    ) {
        ImLong l = new ImLong(pData.get());
        boolean result = ImGui.inputScalar(label, l, pStep, pStepFast, format, imGuiSliderFlags);
        pData.set(l.get());
        return result;
    }

    @Override
    public boolean inputScalar(final String label, final KFloatReference pData) {
        ImFloat f = new ImFloat(pData.get());
        boolean result = ImGui.inputScalar(label, f);
        pData.set(f.get());
        return result;
    }

    @Override
    public boolean inputScalar(final String label, final KFloatReference pData, float pStep) {
        ImFloat f = new ImFloat(pData.get());
        boolean result = ImGui.inputScalar(label, f, pStep);
        pData.set(f.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        final KFloatReference pData,
        float pStep,
        float pStepFast
    ) {
        ImFloat f = new ImFloat(pData.get());
        boolean result = ImGui.inputScalar(label, f, pStep, pStepFast);
        pData.set(f.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        final KFloatReference pData,
        float pStep,
        float pStepFast,
        final String format
    ) {
        ImFloat f = new ImFloat(pData.get());
        boolean result = ImGui.inputScalar(label, f, pStep, pStepFast, format);
        pData.set(f.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        final KFloatReference pData,
        float pStep,
        float pStepFast,
        final String format,
        int imGuiSliderFlags
    ) {
        ImFloat f = new ImFloat(pData.get());
        boolean result = ImGui.inputScalar(label, f, pStep, pStepFast, format, imGuiSliderFlags);
        pData.set(f.get());
        return result;
    }

    @Override
    public boolean inputScalar(final String label, final KDoubleReference pData) {
        ImDouble d = new ImDouble(pData.get());
        boolean result = ImGui.inputScalar(label, d);
        pData.set(d.get());
        return result;
    }

    @Override
    public boolean inputScalar(final String label, final KDoubleReference pData, double pStep) {
        ImDouble d = new ImDouble(pData.get());
        boolean result = ImGui.inputScalar(label, d, pStep);
        pData.set(d.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        final KDoubleReference pData,
        double pStep,
        double pStepFast
    ) {
        ImDouble d = new ImDouble(pData.get());
        boolean result = ImGui.inputScalar(label, d, pStep, pStepFast);
        pData.set(d.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        final KDoubleReference pData,
        double pStep,
        double pStepFast,
        final String format
    ) {
        ImDouble d = new ImDouble(pData.get());
        boolean result = ImGui.inputScalar(label, d, pStep, pStepFast, format);
        pData.set(d.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        final KDoubleReference pData,
        double pStep,
        double pStepFast,
        final String format,
        int imGuiSliderFlags
    ) {
        ImDouble d = new ImDouble(pData.get());
        boolean result = ImGui.inputScalar(label, d, pStep, pStepFast, format, imGuiSliderFlags);
        pData.set(d.get());
        return result;
    }

    @Override
    public boolean inputScalar(final String label, int dataType, final KShortReference pData) {
        ImShort s = new ImShort(pData.get());
        boolean result = ImGui.inputScalar(label, dataType, s);
        pData.set(s.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        int dataType,
        final KShortReference pData,
        short pStep
    ) {
        ImShort s = new ImShort(pData.get());
        boolean result = ImGui.inputScalar(label, dataType, s, pStep);
        pData.set(s.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        int dataType,
        final KShortReference pData,
        short pStep,
        short pStepFast
    ) {
        ImShort s = new ImShort(pData.get());
        boolean result = ImGui.inputScalar(label, dataType, s, pStep, pStepFast);
        pData.set(s.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        int dataType,
        final KShortReference pData,
        short pStep,
        short pStepFast,
        final String format
    ) {
        ImShort s = new ImShort(pData.get());
        boolean result = ImGui.inputScalar(label, dataType, s, pStep, pStepFast, format);
        pData.set(s.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        int dataType,
        final KShortReference pData,
        short pStep,
        short pStepFast,
        final String format,
        int imGuiSliderFlags
    ) {
        ImShort s = new ImShort(pData.get());
        boolean result = ImGui.inputScalar(
            label,
            dataType,
            s,
            pStep,
            pStepFast,
            format,
            imGuiSliderFlags
        );
        pData.set(s.get());
        return result;
    }

    @Override
    public boolean inputScalar(final String label, int dataType, final KIntReference pData) {
        ImInt i = new ImInt(pData.get());
        boolean result = ImGui.inputScalar(label, dataType, i);
        pData.set(i.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        int dataType,
        final KIntReference pData,
        int pStep
    ) {
        ImInt i = new ImInt(pData.get());
        boolean result = ImGui.inputScalar(label, dataType, i, pStep);
        pData.set(i.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        int dataType,
        final KIntReference pData,
        int pStep,
        int pStepFast
    ) {
        ImInt i = new ImInt(pData.get());
        boolean result = ImGui.inputScalar(label, dataType, i, pStep, pStepFast);
        pData.set(i.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        int dataType,
        final KIntReference pData,
        int pStep,
        int pStepFast,
        final String format
    ) {
        ImInt i = new ImInt(pData.get());
        boolean result = ImGui.inputScalar(label, dataType, i, pStep, pStepFast, format);
        pData.set(i.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        int dataType,
        final KIntReference pData,
        int pStep,
        int pStepFast,
        final String format,
        int imGuiSliderFlags
    ) {
        ImInt i = new ImInt(pData.get());
        boolean result = ImGui.inputScalar(
            label,
            dataType,
            i,
            pStep,
            pStepFast,
            format,
            imGuiSliderFlags
        );
        pData.set(i.get());
        return result;
    }

    @Override
    public boolean inputScalar(final String label, int dataType, final KLongReference pData) {
        ImLong l = new ImLong(pData.get());
        boolean result = ImGui.inputScalar(label, dataType, l);
        pData.set(l.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        int dataType,
        final KLongReference pData,
        long pStep
    ) {
        ImLong l = new ImLong(pData.get());
        boolean result = ImGui.inputScalar(label, dataType, l, pStep);
        pData.set(l.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        int dataType,
        final KLongReference pData,
        long pStep,
        long pStepFast
    ) {
        ImLong l = new ImLong(pData.get());
        boolean result = ImGui.inputScalar(label, dataType, l, pStep, pStepFast);
        pData.set(l.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        int dataType,
        final KLongReference pData,
        long pStep,
        long pStepFast,
        final String format
    ) {
        ImLong l = new ImLong(pData.get());
        boolean result = ImGui.inputScalar(label, dataType, l, pStep, pStepFast, format);
        pData.set(l.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        int dataType,
        final KLongReference pData,
        long pStep,
        long pStepFast,
        final String format,
        int imGuiSliderFlags
    ) {
        ImLong l = new ImLong(pData.get());
        boolean result = ImGui.inputScalar(
            label,
            dataType,
            l,
            pStep,
            pStepFast,
            format,
            imGuiSliderFlags
        );
        pData.set(l.get());
        return result;
    }

    @Override
    public boolean inputScalar(final String label, int dataType, final KFloatReference pData) {
        ImFloat f = new ImFloat(pData.get());
        boolean result = ImGui.inputScalar(label, dataType, f);
        pData.set(f.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        int dataType,
        final KFloatReference pData,
        float pStep
    ) {
        ImFloat f = new ImFloat(pData.get());
        boolean result = ImGui.inputScalar(label, dataType, f, pStep);
        pData.set(f.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        int dataType,
        final KFloatReference pData,
        float pStep,
        float pStepFast
    ) {
        ImFloat f = new ImFloat(pData.get());
        boolean result = ImGui.inputScalar(label, dataType, f, pStep, pStepFast);
        pData.set(f.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        int dataType,
        final KFloatReference pData,
        float pStep,
        float pStepFast,
        final String format
    ) {
        ImFloat f = new ImFloat(pData.get());
        boolean result = ImGui.inputScalar(label, dataType, f, pStep, pStepFast, format);
        pData.set(f.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        int dataType,
        final KFloatReference pData,
        float pStep,
        float pStepFast,
        final String format,
        int imGuiSliderFlags
    ) {
        ImFloat f = new ImFloat(pData.get());
        boolean result = ImGui.inputScalar(
            label,
            dataType,
            f,
            pStep,
            pStepFast,
            format,
            imGuiSliderFlags
        );
        pData.set(f.get());
        return result;
    }

    @Override
    public boolean inputScalar(final String label, int dataType, final KDoubleReference pData) {
        ImDouble d = new ImDouble(pData.get());
        boolean result = ImGui.inputScalar(label, dataType, d);
        pData.set(d.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        int dataType,
        final KDoubleReference pData,
        double pStep
    ) {
        ImDouble d = new ImDouble(pData.get());
        boolean result = ImGui.inputScalar(label, dataType, d, pStep);
        pData.set(d.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        int dataType,
        final KDoubleReference pData,
        double pStep,
        double pStepFast
    ) {
        ImDouble d = new ImDouble(pData.get());
        boolean result = ImGui.inputScalar(label, dataType, d, pStep, pStepFast);
        pData.set(d.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        int dataType,
        final KDoubleReference pData,
        double pStep,
        double pStepFast,
        final String format
    ) {
        ImDouble d = new ImDouble(pData.get());
        boolean result = ImGui.inputScalar(label, dataType, d, pStep, pStepFast, format);
        pData.set(d.get());
        return result;
    }

    @Override
    public boolean inputScalar(
        final String label,
        int dataType,
        final KDoubleReference pData,
        double pStep,
        double pStepFast,
        final String format,
        int imGuiSliderFlags
    ) {
        ImDouble d = new ImDouble(pData.get());
        boolean result = ImGui.inputScalar(
            label,
            dataType,
            d,
            pStep,
            pStepFast,
            format,
            imGuiSliderFlags
        );
        pData.set(d.get());
        return result;
    }

    @Override
    public boolean inputScalarN(final String label, final short[] pData, int components) {
        return ImGui.inputScalarN(label, pData, components);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        final short[] pData,
        int components,
        short pStep
    ) {
        return ImGui.inputScalarN(label, pData, components, pStep);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        final short[] pData,
        int components,
        short pStep,
        short pStepFast
    ) {
        return ImGui.inputScalarN(label, pData, components, pStep, pStepFast);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        final short[] pData,
        int components,
        short pStep,
        short pStepFast,
        final String format
    ) {
        return ImGui.inputScalarN(label, pData, components, pStep, pStepFast, format);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        final short[] pData,
        int components,
        short pStep,
        short pStepFast,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.inputScalarN(
            label,
            pData,
            components,
            pStep,
            pStepFast,
            format,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean inputScalarN(final String label, final int[] pData, int components) {
        return ImGui.inputScalarN(label, pData, components);
    }

    @Override
    public boolean inputScalarN(final String label, final int[] pData, int components, int pStep) {
        return ImGui.inputScalarN(label, pData, components, pStep);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        final int[] pData,
        int components,
        int pStep,
        int pStepFast
    ) {
        return ImGui.inputScalarN(label, pData, components, pStep, pStepFast);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        final int[] pData,
        int components,
        int pStep,
        int pStepFast,
        final String format
    ) {
        return ImGui.inputScalarN(label, pData, components, pStep, pStepFast, format);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        final int[] pData,
        int components,
        int pStep,
        int pStepFast,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.inputScalarN(
            label,
            pData,
            components,
            pStep,
            pStepFast,
            format,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean inputScalarN(final String label, final long[] pData, int components) {
        return ImGui.inputScalarN(label, pData, components);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        final long[] pData,
        int components,
        long pStep
    ) {
        return ImGui.inputScalarN(label, pData, components, pStep);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        final long[] pData,
        int components,
        long pStep,
        long pStepFast
    ) {
        return ImGui.inputScalarN(label, pData, components, pStep, pStepFast);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        final long[] pData,
        int components,
        long pStep,
        long pStepFast,
        final String format
    ) {
        return ImGui.inputScalarN(label, pData, components, pStep, pStepFast, format);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        final long[] pData,
        int components,
        long pStep,
        long pStepFast,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.inputScalarN(
            label,
            pData,
            components,
            pStep,
            pStepFast,
            format,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean inputScalarN(final String label, final float[] pData, int components) {
        return ImGui.inputScalarN(label, pData, components);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        final float[] pData,
        int components,
        float pStep
    ) {
        return ImGui.inputScalarN(label, pData, components, pStep);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        final float[] pData,
        int components,
        float pStep,
        float pStepFast
    ) {
        return ImGui.inputScalarN(label, pData, components, pStep, pStepFast);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        final float[] pData,
        int components,
        float pStep,
        float pStepFast,
        final String format
    ) {
        return ImGui.inputScalarN(label, pData, components, pStep, pStepFast, format);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        final float[] pData,
        int components,
        float pStep,
        float pStepFast,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.inputScalarN(
            label,
            pData,
            components,
            pStep,
            pStepFast,
            format,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean inputScalarN(final String label, final double[] pData, int components) {
        return ImGui.inputScalarN(label, pData, components);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        final double[] pData,
        int components,
        double pStep
    ) {
        return ImGui.inputScalarN(label, pData, components, pStep);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        final double[] pData,
        int components,
        double pStep,
        double pStepFast
    ) {
        return ImGui.inputScalarN(label, pData, components, pStep, pStepFast);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        final double[] pData,
        int components,
        double pStep,
        double pStepFast,
        final String format
    ) {
        return ImGui.inputScalarN(label, pData, components, pStep, pStepFast, format);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        final double[] pData,
        int components,
        double pStep,
        double pStepFast,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.inputScalarN(
            label,
            pData,
            components,
            pStep,
            pStepFast,
            format,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean inputScalarN(
        final String label,
        int dataType,
        final short[] pData,
        int components
    ) {
        return ImGui.inputScalarN(label, dataType, pData, components);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        int dataType,
        final short[] pData,
        int components,
        short pStep
    ) {
        return ImGui.inputScalarN(label, dataType, pData, components, pStep);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        int dataType,
        final short[] pData,
        int components,
        short pStep,
        short pStepFast
    ) {
        return ImGui.inputScalarN(label, dataType, pData, components, pStep, pStepFast);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        int dataType,
        final short[] pData,
        int components,
        short pStep,
        short pStepFast,
        final String format
    ) {
        return ImGui.inputScalarN(label, dataType, pData, components, pStep, pStepFast, format);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        int dataType,
        final short[] pData,
        int components,
        short pStep,
        short pStepFast,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.inputScalarN(
            label,
            dataType,
            pData,
            components,
            pStep,
            pStepFast,
            format,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean inputScalarN(
        final String label,
        int dataType,
        final int[] pData,
        int components
    ) {
        return ImGui.inputScalarN(label, dataType, pData, components);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        int dataType,
        final int[] pData,
        int components,
        int pStep
    ) {
        return ImGui.inputScalarN(label, dataType, pData, components, pStep);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        int dataType,
        final int[] pData,
        int components,
        int pStep,
        int pStepFast
    ) {
        return ImGui.inputScalarN(label, dataType, pData, components, pStep, pStepFast);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        int dataType,
        final int[] pData,
        int components,
        int pStep,
        int pStepFast,
        final String format
    ) {
        return ImGui.inputScalarN(label, dataType, pData, components, pStep, pStepFast, format);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        int dataType,
        final int[] pData,
        int components,
        int pStep,
        int pStepFast,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.inputScalarN(
            label,
            dataType,
            pData,
            components,
            pStep,
            pStepFast,
            format,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean inputScalarN(
        final String label,
        int dataType,
        final long[] pData,
        int components
    ) {
        return ImGui.inputScalarN(label, dataType, pData, components);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        int dataType,
        final long[] pData,
        int components,
        long pStep
    ) {
        return ImGui.inputScalarN(label, dataType, pData, components, pStep);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        int dataType,
        final long[] pData,
        int components,
        long pStep,
        long pStepFast
    ) {
        return ImGui.inputScalarN(label, dataType, pData, components, pStep, pStepFast);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        int dataType,
        final long[] pData,
        int components,
        long pStep,
        long pStepFast,
        final String format
    ) {
        return ImGui.inputScalarN(label, dataType, pData, components, pStep, pStepFast, format);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        int dataType,
        final long[] pData,
        int components,
        long pStep,
        long pStepFast,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.inputScalarN(
            label,
            dataType,
            pData,
            components,
            pStep,
            pStepFast,
            format,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean inputScalarN(
        final String label,
        int dataType,
        final float[] pData,
        int components
    ) {
        return ImGui.inputScalarN(label, dataType, pData, components);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        int dataType,
        final float[] pData,
        int components,
        float pStep
    ) {
        return ImGui.inputScalarN(label, dataType, pData, components, pStep);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        int dataType,
        final float[] pData,
        int components,
        float pStep,
        float pStepFast
    ) {
        return ImGui.inputScalarN(label, dataType, pData, components, pStep, pStepFast);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        int dataType,
        final float[] pData,
        int components,
        float pStep,
        float pStepFast,
        final String format
    ) {
        return ImGui.inputScalarN(label, dataType, pData, components, pStep, pStepFast, format);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        int dataType,
        final float[] pData,
        int components,
        float pStep,
        float pStepFast,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.inputScalarN(
            label,
            dataType,
            pData,
            components,
            pStep,
            pStepFast,
            format,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean inputScalarN(
        final String label,
        int dataType,
        final double[] pData,
        int components
    ) {
        return ImGui.inputScalarN(label, dataType, pData, components);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        int dataType,
        final double[] pData,
        int components,
        double pStep
    ) {
        return ImGui.inputScalarN(label, dataType, pData, components, pStep);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        int dataType,
        final double[] pData,
        int components,
        double pStep,
        double pStepFast
    ) {
        return ImGui.inputScalarN(label, dataType, pData, components, pStep, pStepFast);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        int dataType,
        final double[] pData,
        int components,
        double pStep,
        double pStepFast,
        final String format
    ) {
        return ImGui.inputScalarN(label, dataType, pData, components, pStep, pStepFast, format);
    }

    @Override
    public boolean inputScalarN(
        final String label,
        int dataType,
        final double[] pData,
        int components,
        double pStep,
        double pStepFast,
        final String format,
        int imGuiSliderFlags
    ) {
        return ImGui.inputScalarN(
            label,
            dataType,
            pData,
            components,
            pStep,
            pStepFast,
            format,
            imGuiSliderFlags
        );
    }

    @Override
    public boolean colorEdit3(final String label, final float[] col) {
        return ImGui.colorEdit3(label, col);
    }

    @Override
    public boolean colorEdit3(final String label, final float[] col, int imGuiColorEditFlags) {
        return ImGui.colorEdit3(label, col, imGuiColorEditFlags);
    }

    @Override
    public boolean colorEdit4(final String label, final float[] col) {
        return ImGui.colorEdit4(label, col);
    }

    @Override
    public boolean colorEdit4(final String label, final float[] col, int imGuiColorEditFlags) {
        return ImGui.colorEdit4(label, col, imGuiColorEditFlags);
    }

    @Override
    public boolean colorPicker3(final String label, final float[] col) {
        return ImGui.colorPicker3(label, col);
    }

    @Override
    public boolean colorPicker3(final String label, final float[] col, int imGuiColorEditFlags) {
        return ImGui.colorPicker3(label, col, imGuiColorEditFlags);
    }

    @Override
    public boolean colorPicker4(final String label, final float[] col) {
        return ImGui.colorPicker4(label, col);
    }

    @Override
    public boolean colorPicker4(final String label, final float[] col, int imGuiColorEditFlags) {
        return ImGui.colorPicker4(label, col, imGuiColorEditFlags);
    }

    @Override
    public boolean colorPicker4(
        final String label,
        final float[] col,
        int imGuiColorEditFlags,
        final float[] refCol
    ) {
        return ImGui.colorPicker4(label, col, imGuiColorEditFlags, refCol);
    }

    @Override
    public boolean colorPicker4(final String label, final float[] col, final float[] refCol) {
        return ImGui.colorPicker4(label, col, refCol);
    }

    @Override
    public boolean colorButton(final String descId, final KVector4f col) {
        return ImGui.colorButton(descId, KImGuiSpairWrapper.wrap(col));
    }

    @Override
    public boolean colorButton(
        final String descId,
        float colX,
        float colY,
        float colZ,
        float colW
    ) {
        return ImGui.colorButton(descId, colX, colY, colZ, colW);
    }

    @Override
    public boolean colorButton(final String descId, final KVector4f col, int imGuiColorEditFlags) {
        return ImGui.colorButton(descId, KImGuiSpairWrapper.wrap(col), imGuiColorEditFlags);
    }

    @Override
    public boolean colorButton(
        final String descId,
        float colX,
        float colY,
        float colZ,
        float colW,
        int imGuiColorEditFlags
    ) {
        return ImGui.colorButton(descId, colX, colY, colZ, colW, imGuiColorEditFlags);
    }

    @Override
    public boolean colorButton(
        final String descId,
        final KVector4f col,
        int imGuiColorEditFlags,
        final KVector2f size
    ) {
        return ImGui.colorButton(
            descId,
            KImGuiSpairWrapper.wrap(col),
            imGuiColorEditFlags,
            KImGuiSpairWrapper.wrap(size)
        );
    }

    @Override
    public boolean colorButton(
        final String descId,
        float colX,
        float colY,
        float colZ,
        float colW,
        int imGuiColorEditFlags,
        float sizeX,
        float sizeY
    ) {
        return ImGui.colorButton(descId, colX, colY, colZ, colW, imGuiColorEditFlags, sizeX, sizeY);
    }

    @Override
    public boolean colorButton(final String descId, final KVector4f col, final KVector2f size) {
        return ImGui.colorButton(
            descId,
            KImGuiSpairWrapper.wrap(col),
            KImGuiSpairWrapper.wrap(size)
        );
    }

    @Override
    public boolean colorButton(
        final String descId,
        float colX,
        float colY,
        float colZ,
        float colW,
        float sizeX,
        float sizeY
    ) {
        return ImGui.colorButton(descId, colX, colY, colZ, colW, sizeX, sizeY);
    }

    @Override
    public void setColorEditOptions(int imGuiColorEditFlags) {
        ImGui.setColorEditOptions(imGuiColorEditFlags);
    }

    @Override
    public boolean treeNode(final String label) {
        return ImGui.treeNode(label);
    }

    @Override
    public boolean treeNode(final String strId, final String label) {
        return ImGui.treeNode(strId, label);
    }

    @Override
    public boolean treeNode(long ptrId, final String label) {
        return ImGui.treeNode(ptrId, label);
    }

    @Override
    public boolean treeNodeEx(final String label) {
        return ImGui.treeNodeEx(label);
    }

    @Override
    public boolean treeNodeEx(final String label, int flags) {
        return ImGui.treeNodeEx(label, flags);
    }

    @Override
    public boolean treeNodeEx(final String strId, int flags, final String label) {
        return ImGui.treeNodeEx(strId, flags, label);
    }

    @Override
    public boolean treeNodeEx(long ptrId, int flags, final String label) {
        return ImGui.treeNodeEx(ptrId, flags, label);
    }

    @Override
    public void treePush(final String strId) {
        ImGui.treePush(strId);
    }

    @Override
    public void treePush(long ptrId) {
        ImGui.treePush(ptrId);
    }

    @Override
    public void treePop() {
        ImGui.treePop();
    }

    @Override
    public float getTreeNodeToLabelSpacing() {
        return ImGui.getTreeNodeToLabelSpacing();
    }

    @Override
    public boolean collapsingHeader(final String label) {
        return ImGui.collapsingHeader(label);
    }

    @Override
    public boolean collapsingHeader(final String label, int imGuiTreeNodeFlags) {
        return ImGui.collapsingHeader(label, imGuiTreeNodeFlags);
    }

    @Override
    public boolean collapsingHeader(final String label, final KBooleanReference pVisible) {
        ImBoolean b = new ImBoolean(pVisible.get());
        boolean result = ImGui.collapsingHeader(label, b);
        pVisible.set(b.get());
        return result;
    }

    @Override
    public boolean collapsingHeader(
        final String label,
        final KBooleanReference pVisible,
        int imGuiTreeNodeFlags
    ) {
        ImBoolean b = new ImBoolean(pVisible.get());
        boolean result = ImGui.collapsingHeader(label, b, imGuiTreeNodeFlags);
        pVisible.set(b.get());
        return result;
    }

    @Override
    public void setNextItemOpen(boolean isOpen) {
        ImGui.setNextItemOpen(isOpen);
    }

    @Override
    public void setNextItemOpen(boolean isOpen, int cond) {
        ImGui.setNextItemOpen(isOpen, cond);
    }

    @Override
    public boolean selectable(final String label) {
        return ImGui.selectable(label);
    }

    @Override
    public boolean selectable(final String label, boolean selected) {
        return ImGui.selectable(label, selected);
    }

    @Override
    public boolean selectable(final String label, boolean selected, int imGuiSelectableFlags) {
        return ImGui.selectable(label, selected, imGuiSelectableFlags);
    }

    @Override
    public boolean selectable(
        final String label,
        boolean selected,
        int imGuiSelectableFlags,
        final KVector2f size
    ) {
        return ImGui.selectable(
            label,
            selected,
            imGuiSelectableFlags,
            KImGuiSpairWrapper.wrap(size)
        );
    }

    @Override
    public boolean selectable(
        final String label,
        boolean selected,
        int imGuiSelectableFlags,
        float sizeX,
        float sizeY
    ) {
        return ImGui.selectable(label, selected, imGuiSelectableFlags, sizeX, sizeY);
    }

    @Override
    public boolean selectable(final String label, int imGuiSelectableFlags, final KVector2f size) {
        return ImGui.selectable(label, imGuiSelectableFlags, KImGuiSpairWrapper.wrap(size));
    }

    @Override
    public boolean selectable(
        final String label,
        int imGuiSelectableFlags,
        float sizeX,
        float sizeY
    ) {
        return ImGui.selectable(label, imGuiSelectableFlags, sizeX, sizeY);
    }

    @Override
    public boolean selectable(final String label, final KVector2f size) {
        return ImGui.selectable(label, KImGuiSpairWrapper.wrap(size));
    }

    @Override
    public boolean selectable(final String label, float sizeX, float sizeY) {
        return ImGui.selectable(label, sizeX, sizeY);
    }

    @Override
    public boolean selectable(final String label, boolean selected, final KVector2f size) {
        return ImGui.selectable(label, selected, KImGuiSpairWrapper.wrap(size));
    }

    @Override
    public boolean selectable(final String label, boolean selected, float sizeX, float sizeY) {
        return ImGui.selectable(label, selected, sizeX, sizeY);
    }

    @Override
    public boolean selectable(final String label, final KBooleanReference pSelected) {
        ImBoolean b = new ImBoolean(pSelected.get());
        boolean result = ImGui.selectable(label, b);
        pSelected.set(b.get());
        return result;
    }

    @Override
    public boolean selectable(
        final String label,
        final KBooleanReference pSelected,
        int imGuiSelectableFlags
    ) {
        ImBoolean b = new ImBoolean(pSelected.get());
        boolean result = ImGui.selectable(label, b, imGuiSelectableFlags);
        pSelected.set(b.get());
        return result;
    }

    @Override
    public boolean selectable(
        final String label,
        final KBooleanReference pSelected,
        int imGuiSelectableFlags,
        final KVector2f size
    ) {
        ImBoolean b = new ImBoolean(pSelected.get());
        boolean result = ImGui.selectable(
            label,
            b,
            imGuiSelectableFlags,
            KImGuiSpairWrapper.wrap(size)
        );
        pSelected.set(b.get());
        return result;
    }

    @Override
    public boolean selectable(
        final String label,
        final KBooleanReference pSelected,
        int imGuiSelectableFlags,
        float sizeX,
        float sizeY
    ) {
        ImBoolean b = new ImBoolean(pSelected.get());
        boolean result = ImGui.selectable(label, b, imGuiSelectableFlags, sizeX, sizeY);
        pSelected.set(b.get());
        return result;
    }

    @Override
    public boolean selectable(
        final String label,
        final KBooleanReference pSelected,
        final KVector2f size
    ) {
        ImBoolean b = new ImBoolean(pSelected.get());
        boolean result = ImGui.selectable(label, b, KImGuiSpairWrapper.wrap(size));
        pSelected.set(b.get());
        return result;
    }

    @Override
    public boolean selectable(
        final String label,
        final KBooleanReference pSelected,
        float sizeX,
        float sizeY
    ) {
        ImBoolean b = new ImBoolean(pSelected.get());
        boolean result = ImGui.selectable(label, b, sizeX, sizeY);
        pSelected.set(b.get());
        return result;
    }

    @Override
    public boolean beginListBox(final String label) {
        return ImGui.beginListBox(label);
    }

    @Override
    public boolean beginListBox(final String label, final KVector2f size) {
        return ImGui.beginListBox(label, KImGuiSpairWrapper.wrap(size));
    }

    @Override
    public boolean beginListBox(final String label, float sizeX, float sizeY) {
        return ImGui.beginListBox(label, sizeX, sizeY);
    }

    @Override
    public void endListBox() {
        ImGui.endListBox();
    }

    @Override
    public void listBox(final String label, final KIntReference currentItem, final String[] items) {
        ImInt i = new ImInt(currentItem.get());
        ImGui.listBox(label, i, items);
        currentItem.set(i.get());
    }

    @Override
    public void listBox(
        final String label,
        final KIntReference currentItem,
        final String[] items,
        int heightInItems
    ) {
        ImInt i = new ImInt(currentItem.get());
        ImGui.listBox(label, i, items, heightInItems);
        currentItem.set(i.get());
    }

    @Override
    public void plotLines(final String label, final float[] values, int valuesCount) {
        ImGui.plotLines(label, values, valuesCount);
    }

    @Override
    public void plotLines(
        final String label,
        final float[] values,
        int valuesCount,
        int valuesOffset
    ) {
        ImGui.plotLines(label, values, valuesCount, valuesOffset);
    }

    @Override
    public void plotLines(
        final String label,
        final float[] values,
        int valuesCount,
        int valuesOffset,
        final String overlayText
    ) {
        ImGui.plotLines(label, values, valuesCount, valuesOffset, overlayText);
    }

    @Override
    public void plotLines(
        final String label,
        final float[] values,
        int valuesCount,
        int valuesOffset,
        final String overlayText,
        float scaleMin
    ) {
        ImGui.plotLines(label, values, valuesCount, valuesOffset, overlayText, scaleMin);
    }

    @Override
    public void plotLines(
        final String label,
        final float[] values,
        int valuesCount,
        int valuesOffset,
        final String overlayText,
        float scaleMin,
        float scaleMax
    ) {
        ImGui.plotLines(label, values, valuesCount, valuesOffset, overlayText, scaleMin, scaleMax);
    }

    @Override
    public void plotLines(
        final String label,
        final float[] values,
        int valuesCount,
        int valuesOffset,
        final String overlayText,
        float scaleMin,
        float scaleMax,
        final KVector2f graphSize
    ) {
        ImGui.plotLines(
            label,
            values,
            valuesCount,
            valuesOffset,
            overlayText,
            scaleMin,
            scaleMax,
            KImGuiSpairWrapper.wrap(graphSize)
        );
    }

    @Override
    public void plotLines(
        final String label,
        final float[] values,
        int valuesCount,
        int valuesOffset,
        final String overlayText,
        float scaleMin,
        float scaleMax,
        float graphSizeX,
        float graphSizeY
    ) {
        ImGui.plotLines(
            label,
            values,
            valuesCount,
            valuesOffset,
            overlayText,
            scaleMin,
            scaleMax,
            graphSizeX,
            graphSizeY
        );
    }

    @Override
    public void plotLines(
        final String label,
        final float[] values,
        int valuesCount,
        int valuesOffset,
        final String overlayText,
        float scaleMin,
        float scaleMax,
        final KVector2f graphSize,
        int stride
    ) {
        ImGui.plotLines(
            label,
            values,
            valuesCount,
            valuesOffset,
            overlayText,
            scaleMin,
            scaleMax,
            KImGuiSpairWrapper.wrap(graphSize),
            stride
        );
    }

    @Override
    public void plotLines(
        final String label,
        final float[] values,
        int valuesCount,
        int valuesOffset,
        final String overlayText,
        float scaleMin,
        float scaleMax,
        float graphSizeX,
        float graphSizeY,
        int stride
    ) {
        ImGui.plotLines(
            label,
            values,
            valuesCount,
            valuesOffset,
            overlayText,
            scaleMin,
            scaleMax,
            graphSizeX,
            graphSizeY,
            stride
        );
    }

    @Override
    public void plotLines(
        final String label,
        final float[] values,
        int valuesCount,
        final String overlayText,
        float scaleMin,
        float scaleMax,
        final KVector2f graphSize,
        int stride
    ) {
        ImGui.plotLines(
            label,
            values,
            valuesCount,
            overlayText,
            scaleMin,
            scaleMax,
            KImGuiSpairWrapper.wrap(graphSize),
            stride
        );
    }

    @Override
    public void plotLines(
        final String label,
        final float[] values,
        int valuesCount,
        final String overlayText,
        float scaleMin,
        float scaleMax,
        float graphSizeX,
        float graphSizeY,
        int stride
    ) {
        ImGui.plotLines(
            label,
            values,
            valuesCount,
            overlayText,
            scaleMin,
            scaleMax,
            graphSizeX,
            graphSizeY,
            stride
        );
    }

    @Override
    public void plotLines(
        final String label,
        final float[] values,
        int valuesCount,
        float scaleMin,
        float scaleMax,
        final KVector2f graphSize,
        int stride
    ) {
        ImGui.plotLines(
            label,
            values,
            valuesCount,
            scaleMin,
            scaleMax,
            KImGuiSpairWrapper.wrap(graphSize),
            stride
        );
    }

    @Override
    public void plotLines(
        final String label,
        final float[] values,
        int valuesCount,
        float scaleMin,
        float scaleMax,
        float graphSizeX,
        float graphSizeY,
        int stride
    ) {
        ImGui.plotLines(
            label,
            values,
            valuesCount,
            scaleMin,
            scaleMax,
            graphSizeX,
            graphSizeY,
            stride
        );
    }

    @Override
    public void plotLines(
        final String label,
        final float[] values,
        int valuesCount,
        int valuesOffset,
        float scaleMin,
        float scaleMax,
        final KVector2f graphSize,
        int stride
    ) {
        ImGui.plotLines(
            label,
            values,
            valuesCount,
            valuesOffset,
            scaleMin,
            scaleMax,
            KImGuiSpairWrapper.wrap(graphSize),
            stride
        );
    }

    @Override
    public void plotLines(
        final String label,
        final float[] values,
        int valuesCount,
        int valuesOffset,
        float scaleMin,
        float scaleMax,
        float graphSizeX,
        float graphSizeY,
        int stride
    ) {
        ImGui.plotLines(
            label,
            values,
            valuesCount,
            valuesOffset,
            scaleMin,
            scaleMax,
            graphSizeX,
            graphSizeY,
            stride
        );
    }

    @Override
    public void plotLines(
        final String label,
        final float[] values,
        int valuesCount,
        int valuesOffset,
        final String overlayText,
        float scaleMin,
        float scaleMax,
        int stride
    ) {
        ImGui.plotLines(
            label,
            values,
            valuesCount,
            valuesOffset,
            overlayText,
            scaleMin,
            scaleMax,
            stride
        );
    }

    @Override
    public void plotHistogram(final String label, final float[] values, int valuesCount) {
        ImGui.plotHistogram(label, values, valuesCount);
    }

    @Override
    public void plotHistogram(
        final String label,
        final float[] values,
        int valuesCount,
        int valuesOffset
    ) {
        ImGui.plotHistogram(label, values, valuesCount, valuesOffset);
    }

    @Override
    public void plotHistogram(
        final String label,
        final float[] values,
        int valuesCount,
        int valuesOffset,
        final String overlayText
    ) {
        ImGui.plotHistogram(label, values, valuesCount, valuesOffset, overlayText);
    }

    @Override
    public void plotHistogram(
        final String label,
        final float[] values,
        int valuesCount,
        int valuesOffset,
        final String overlayText,
        float scaleMin
    ) {
        ImGui.plotHistogram(label, values, valuesCount, valuesOffset, overlayText, scaleMin);
    }

    @Override
    public void plotHistogram(
        final String label,
        final float[] values,
        int valuesCount,
        int valuesOffset,
        final String overlayText,
        float scaleMin,
        float scaleMax
    ) {
        ImGui.plotHistogram(
            label,
            values,
            valuesCount,
            valuesOffset,
            overlayText,
            scaleMin,
            scaleMax
        );
    }

    @Override
    public void plotHistogram(
        final String label,
        final float[] values,
        int valuesCount,
        int valuesOffset,
        final String overlayText,
        float scaleMin,
        float scaleMax,
        final KVector2f graphSize
    ) {
        ImGui.plotHistogram(
            label,
            values,
            valuesCount,
            valuesOffset,
            overlayText,
            scaleMin,
            scaleMax,
            KImGuiSpairWrapper.wrap(graphSize)
        );
    }

    @Override
    public void plotHistogram(
        final String label,
        final float[] values,
        int valuesCount,
        int valuesOffset,
        final String overlayText,
        float scaleMin,
        float scaleMax,
        float graphSizeX,
        float graphSizeY
    ) {
        ImGui.plotHistogram(
            label,
            values,
            valuesCount,
            valuesOffset,
            overlayText,
            scaleMin,
            scaleMax,
            graphSizeX,
            graphSizeY
        );
    }

    @Override
    public void plotHistogram(
        final String label,
        final float[] values,
        int valuesCount,
        int valuesOffset,
        final String overlayText,
        float scaleMin,
        float scaleMax,
        final KVector2f graphSize,
        int stride
    ) {
        ImGui.plotHistogram(
            label,
            values,
            valuesCount,
            valuesOffset,
            overlayText,
            scaleMin,
            scaleMax,
            KImGuiSpairWrapper.wrap(graphSize),
            stride
        );
    }

    @Override
    public void plotHistogram(
        final String label,
        final float[] values,
        int valuesCount,
        int valuesOffset,
        final String overlayText,
        float scaleMin,
        float scaleMax,
        float graphSizeX,
        float graphSizeY,
        int stride
    ) {
        ImGui.plotHistogram(
            label,
            values,
            valuesCount,
            valuesOffset,
            overlayText,
            scaleMin,
            scaleMax,
            graphSizeX,
            graphSizeY,
            stride
        );
    }

    @Override
    public void plotHistogram(
        final String label,
        final float[] values,
        int valuesCount,
        final String overlayText,
        float scaleMin,
        float scaleMax,
        final KVector2f graphSize,
        int stride
    ) {
        ImGui.plotHistogram(
            label,
            values,
            valuesCount,
            overlayText,
            scaleMin,
            scaleMax,
            KImGuiSpairWrapper.wrap(graphSize),
            stride
        );
    }

    @Override
    public void plotHistogram(
        final String label,
        final float[] values,
        int valuesCount,
        final String overlayText,
        float scaleMin,
        float scaleMax,
        float graphSizeX,
        float graphSizeY,
        int stride
    ) {
        ImGui.plotHistogram(
            label,
            values,
            valuesCount,
            overlayText,
            scaleMin,
            scaleMax,
            graphSizeX,
            graphSizeY,
            stride
        );
    }

    @Override
    public void plotHistogram(
        final String label,
        final float[] values,
        int valuesCount,
        float scaleMin,
        float scaleMax,
        final KVector2f graphSize,
        int stride
    ) {
        ImGui.plotHistogram(
            label,
            values,
            valuesCount,
            scaleMin,
            scaleMax,
            KImGuiSpairWrapper.wrap(graphSize),
            stride
        );
    }

    @Override
    public void plotHistogram(
        final String label,
        final float[] values,
        int valuesCount,
        float scaleMin,
        float scaleMax,
        float graphSizeX,
        float graphSizeY,
        int stride
    ) {
        ImGui.plotHistogram(
            label,
            values,
            valuesCount,
            scaleMin,
            scaleMax,
            graphSizeX,
            graphSizeY,
            stride
        );
    }

    @Override
    public void plotHistogram(
        final String label,
        final float[] values,
        int valuesCount,
        int valuesOffset,
        float scaleMin,
        float scaleMax,
        final KVector2f graphSize,
        int stride
    ) {
        ImGui.plotHistogram(
            label,
            values,
            valuesCount,
            valuesOffset,
            scaleMin,
            scaleMax,
            KImGuiSpairWrapper.wrap(graphSize),
            stride
        );
    }

    @Override
    public void plotHistogram(
        final String label,
        final float[] values,
        int valuesCount,
        int valuesOffset,
        float scaleMin,
        float scaleMax,
        float graphSizeX,
        float graphSizeY,
        int stride
    ) {
        ImGui.plotHistogram(
            label,
            values,
            valuesCount,
            valuesOffset,
            scaleMin,
            scaleMax,
            graphSizeX,
            graphSizeY,
            stride
        );
    }

    @Override
    public void plotHistogram(
        final String label,
        final float[] values,
        int valuesCount,
        int valuesOffset,
        final String overlayText,
        float scaleMin,
        float scaleMax,
        int stride
    ) {
        ImGui.plotHistogram(
            label,
            values,
            valuesCount,
            valuesOffset,
            overlayText,
            scaleMin,
            scaleMax,
            stride
        );
    }

    @Override
    public void value(final String prefix, final Number value) {
        ImGui.value(prefix, value);
    }

    @Override
    public void value(final String prefix, float value, final String floatFormat) {
        ImGui.value(prefix, value, floatFormat);
    }

    @Override
    public boolean beginMenuBar() {
        return ImGui.beginMenuBar();
    }

    @Override
    public void endMenuBar() {
        ImGui.endMenuBar();
    }

    @Override
    public boolean beginMainMenuBar() {
        return ImGui.beginMainMenuBar();
    }

    @Override
    public void endMainMenuBar() {
        ImGui.endMainMenuBar();
    }

    @Override
    public boolean beginMenu(final String label) {
        return ImGui.beginMenu(label);
    }

    @Override
    public boolean beginMenu(final String label, boolean enabled) {
        return ImGui.beginMenu(label, enabled);
    }

    @Override
    public void endMenu() {
        ImGui.endMenu();
    }

    @Override
    public boolean menuItem(final String label) {
        return ImGui.menuItem(label);
    }

    @Override
    public boolean menuItem(final String label, boolean selected) {
        return ImGui.menuItem(label, selected);
    }

    @Override
    public boolean menuItem(final String label, boolean selected, boolean enabled) {
        return ImGui.menuItem(label, selected, enabled);
    }

    @Override
    public boolean menuItem(final String label, final String shortcut) {
        return ImGui.menuItem(label, shortcut);
    }

    @Override
    public boolean menuItem(final String label, final String shortcut, boolean selected) {
        return ImGui.menuItem(label, shortcut, selected);
    }

    @Override
    public boolean menuItem(
        final String label,
        final String shortcut,
        boolean selected,
        boolean enabled
    ) {
        return ImGui.menuItem(label, shortcut, selected, enabled);
    }

    @Override
    public boolean menuItem(
        final String label,
        final String shortcut,
        final KBooleanReference pSelected
    ) {
        ImBoolean b = new ImBoolean(pSelected.get());
        boolean result = ImGui.menuItem(label, shortcut, b);
        pSelected.set(b.get());
        return result;
    }

    @Override
    public boolean menuItem(
        final String label,
        final String shortcut,
        final KBooleanReference pSelected,
        boolean enabled
    ) {
        ImBoolean b = new ImBoolean(pSelected.get());
        boolean result = ImGui.menuItem(label, shortcut, b, enabled);
        pSelected.set(b.get());
        return result;
    }

    @Override
    public void beginTooltip() {
        ImGui.beginTooltip();
    }

    @Override
    public void endTooltip() {
        ImGui.endTooltip();
    }

    @Override
    public void setTooltip(final String text) {
        ImGui.setTooltip(text);
    }

    @Override
    public boolean beginItemTooltip() {
        return ImGui.beginItemTooltip();
    }

    @Override
    public void setItemTooltip(final String text) {
        ImGui.setItemTooltip(text);
    }

    @Override
    public boolean beginPopup(final String strId) {
        return ImGui.beginPopup(strId);
    }

    @Override
    public boolean beginPopup(final String strId, int imGuiWindowFlags) {
        return ImGui.beginPopup(strId, imGuiWindowFlags);
    }

    @Override
    public boolean beginPopupModal(final String name) {
        return ImGui.beginPopupModal(name);
    }

    @Override
    public boolean beginPopupModal(final String name, final KBooleanReference pOpen) {
        ImBoolean b = new ImBoolean(pOpen.get());
        boolean result = ImGui.beginPopupModal(name, b);
        pOpen.set(b.get());
        return result;
    }

    @Override
    public boolean beginPopupModal(
        final String name,
        final KBooleanReference pOpen,
        int imGuiWindowFlags
    ) {
        ImBoolean b = new ImBoolean(pOpen.get());
        boolean result = ImGui.beginPopupModal(name, b, imGuiWindowFlags);
        pOpen.set(b.get());
        return result;
    }

    @Override
    public boolean beginPopupModal(final String name, int imGuiWindowFlags) {
        return ImGui.beginPopupModal(name, imGuiWindowFlags);
    }

    @Override
    public void endPopup() {
        ImGui.endPopup();
    }

    @Override
    public void openPopup(final String strId) {
        ImGui.openPopup(strId);
    }

    @Override
    public void openPopup(final String strId, int imGuiPopupFlags) {
        ImGui.openPopup(strId, imGuiPopupFlags);
    }

    @Override
    public void openPopup(int id) {
        ImGui.openPopup(id);
    }

    @Override
    public void openPopup(int id, int imGuiPopupFlags) {
        ImGui.openPopup(id, imGuiPopupFlags);
    }

    @Override
    public void openPopupOnItemClick() {
        ImGui.openPopupOnItemClick();
    }

    @Override
    public void openPopupOnItemClick(final String strId) {
        ImGui.openPopupOnItemClick(strId);
    }

    @Override
    public void openPopupOnItemClick(final String strId, int imGuiPopupFlags) {
        ImGui.openPopupOnItemClick(strId, imGuiPopupFlags);
    }

    @Override
    public void openPopupOnItemClick(int imGuiPopupFlags) {
        ImGui.openPopupOnItemClick(imGuiPopupFlags);
    }

    @Override
    public void closeCurrentPopup() {
        ImGui.closeCurrentPopup();
    }

    @Override
    public boolean beginPopupContextItem() {
        return ImGui.beginPopupContextItem();
    }

    @Override
    public boolean beginPopupContextItem(final String strId) {
        return ImGui.beginPopupContextItem(strId);
    }

    @Override
    public boolean beginPopupContextItem(final String strId, int imGuiPopupFlags) {
        return ImGui.beginPopupContextItem(strId, imGuiPopupFlags);
    }

    @Override
    public boolean beginPopupContextItem(int imGuiPopupFlags) {
        return ImGui.beginPopupContextItem(imGuiPopupFlags);
    }

    @Override
    public boolean beginPopupContextWindow() {
        return ImGui.beginPopupContextWindow();
    }

    @Override
    public boolean beginPopupContextWindow(final String strId) {
        return ImGui.beginPopupContextWindow(strId);
    }

    @Override
    public boolean beginPopupContextWindow(final String strId, int imGuiPopupFlags) {
        return ImGui.beginPopupContextWindow(strId, imGuiPopupFlags);
    }

    @Override
    public boolean beginPopupContextWindow(int imGuiPopupFlags) {
        return ImGui.beginPopupContextWindow(imGuiPopupFlags);
    }

    @Override
    public boolean beginPopupContextVoid() {
        return ImGui.beginPopupContextVoid();
    }

    @Override
    public boolean beginPopupContextVoid(final String strId) {
        return ImGui.beginPopupContextVoid(strId);
    }

    @Override
    public boolean beginPopupContextVoid(final String strId, int imGuiPopupFlags) {
        return ImGui.beginPopupContextVoid(strId, imGuiPopupFlags);
    }

    @Override
    public boolean beginPopupContextVoid(int imGuiPopupFlags) {
        return ImGui.beginPopupContextVoid(imGuiPopupFlags);
    }

    @Override
    public boolean isPopupOpen(final String strId) {
        return ImGui.isPopupOpen(strId);
    }

    @Override
    public boolean isPopupOpen(final String strId, int imGuiPopupFlags) {
        return ImGui.isPopupOpen(strId, imGuiPopupFlags);
    }

    @Override
    public boolean beginTable(final String id, int columns) {
        return ImGui.beginTable(id, columns);
    }

    @Override
    public boolean beginTable(final String id, int columns, int imGuiTableFlags) {
        return ImGui.beginTable(id, columns, imGuiTableFlags);
    }

    @Override
    public boolean beginTable(
        final String id,
        int columns,
        int imGuiTableFlags,
        final KVector2f outerSize
    ) {
        return ImGui.beginTable(id, columns, imGuiTableFlags, KImGuiSpairWrapper.wrap(outerSize));
    }

    @Override
    public boolean beginTable(
        final String id,
        int columns,
        int imGuiTableFlags,
        float outerSizeX,
        float outerSizeY
    ) {
        return ImGui.beginTable(id, columns, imGuiTableFlags, outerSizeX, outerSizeY);
    }

    @Override
    public boolean beginTable(
        final String id,
        int columns,
        int imGuiTableFlags,
        final KVector2f outerSize,
        float innerWidth
    ) {
        return ImGui.beginTable(
            id,
            columns,
            imGuiTableFlags,
            KImGuiSpairWrapper.wrap(outerSize),
            innerWidth
        );
    }

    @Override
    public boolean beginTable(
        final String id,
        int columns,
        int imGuiTableFlags,
        float outerSizeX,
        float outerSizeY,
        float innerWidth
    ) {
        return ImGui.beginTable(id, columns, imGuiTableFlags, outerSizeX, outerSizeY, innerWidth);
    }

    @Override
    public boolean beginTable(
        final String id,
        int columns,
        final KVector2f outerSize,
        float innerWidth
    ) {
        return ImGui.beginTable(id, columns, KImGuiSpairWrapper.wrap(outerSize), innerWidth);
    }

    @Override
    public boolean beginTable(
        final String id,
        int columns,
        float outerSizeX,
        float outerSizeY,
        float innerWidth
    ) {
        return ImGui.beginTable(id, columns, outerSizeX, outerSizeY, innerWidth);
    }

    @Override
    public boolean beginTable(final String id, int columns, float innerWidth) {
        return ImGui.beginTable(id, columns, innerWidth);
    }

    @Override
    public boolean beginTable(final String id, int columns, int imGuiTableFlags, float innerWidth) {
        return ImGui.beginTable(id, columns, imGuiTableFlags, innerWidth);
    }

    @Override
    public void endTable() {
        ImGui.endTable();
    }

    @Override
    public void tableNextRow() {
        ImGui.tableNextRow();
    }

    @Override
    public void tableNextRow(int imGuiTableRowFlags) {
        ImGui.tableNextRow(imGuiTableRowFlags);
    }

    @Override
    public void tableNextRow(int imGuiTableRowFlags, float minRowHeight) {
        ImGui.tableNextRow(imGuiTableRowFlags, minRowHeight);
    }

    @Override
    public void tableNextRow(float minRowHeight) {
        ImGui.tableNextRow(minRowHeight);
    }

    @Override
    public boolean tableNextColumn() {
        return ImGui.tableNextColumn();
    }

    @Override
    public boolean tableSetColumnIndex(int columnN) {
        return ImGui.tableSetColumnIndex(columnN);
    }

    @Override
    public void tableSetupColumn(final String label) {
        ImGui.tableSetupColumn(label);
    }

    @Override
    public void tableSetupColumn(final String label, int imGuiTableColumnFlags) {
        ImGui.tableSetupColumn(label, imGuiTableColumnFlags);
    }

    @Override
    public void tableSetupColumn(
        final String label,
        int imGuiTableColumnFlags,
        float initWidthOrWeight
    ) {
        ImGui.tableSetupColumn(label, imGuiTableColumnFlags, initWidthOrWeight);
    }

    @Override
    public void tableSetupColumn(
        final String label,
        int imGuiTableColumnFlags,
        float initWidthOrWeight,
        int userId
    ) {
        ImGui.tableSetupColumn(label, imGuiTableColumnFlags, initWidthOrWeight, userId);
    }

    @Override
    public void tableSetupColumn(final String label, float initWidthOrWeight, int userId) {
        ImGui.tableSetupColumn(label, initWidthOrWeight, userId);
    }

    @Override
    public void tableSetupColumn(final String label, int imGuiTableColumnFlags, int userId) {
        ImGui.tableSetupColumn(label, imGuiTableColumnFlags, userId);
    }

    @Override
    public void tableSetupScrollFreeze(int cols, int rows) {
        ImGui.tableSetupScrollFreeze(cols, rows);
    }

    @Override
    public void tableHeader(final String label) {
        ImGui.tableHeader(label);
    }

    @Override
    public void tableHeadersRow() {
        ImGui.tableHeadersRow();
    }

    @Override
    public void tableAngledHeadersRow() {
        ImGui.tableAngledHeadersRow();
    }

    @Override
    public KImGuiTableSortSpecs tableGetSortSpecs() {
        return KImGuiSpairUnwrapper.wrap(ImGui.tableGetSortSpecs());
    }

    @Override
    public int tableGetColumnCount() {
        return ImGui.tableGetColumnCount();
    }

    @Override
    public int tableGetColumnIndex() {
        return ImGui.tableGetColumnIndex();
    }

    @Override
    public int tableGetRowIndex() {
        return ImGui.tableGetRowIndex();
    }

    @Override
    public String tableGetColumnName() {
        return ImGui.tableGetColumnName();
    }

    @Override
    public String tableGetColumnName(int columnN) {
        return ImGui.tableGetColumnName(columnN);
    }

    @Override
    public int tableGetColumnFlags() {
        return ImGui.tableGetColumnFlags();
    }

    @Override
    public int tableGetColumnFlags(int columnN) {
        return ImGui.tableGetColumnFlags(columnN);
    }

    @Override
    public void tableSetColumnEnabled(int columnN, boolean value) {
        ImGui.tableSetColumnEnabled(columnN, value);
    }

    @Override
    public int tableGetHoveredColumn() {
        return ImGui.tableGetHoveredColumn();
    }

    @Override
    public void tableSetBgColor(int imGuiTableBgTarget, int color) {
        ImGui.tableSetBgColor(imGuiTableBgTarget, color);
    }

    @Override
    public void tableSetBgColor(int imGuiTableBgTarget, int color, int columnN) {
        ImGui.tableSetBgColor(imGuiTableBgTarget, color, columnN);
    }

    @Override
    public void columns() {
        ImGui.columns();
    }

    @Override
    public void columns(int count) {
        ImGui.columns(count);
    }

    @Override
    public void columns(int count, final String id) {
        ImGui.columns(count, id);
    }

    @Override
    public void columns(int count, final String id, boolean border) {
        ImGui.columns(count, id, border);
    }

    @Override
    public void columns(final String id, boolean border) {
        ImGui.columns(id, border);
    }

    @Override
    public void columns(boolean border) {
        ImGui.columns(border);
    }

    @Override
    public void columns(int count, boolean border) {
        ImGui.columns(count, border);
    }

    @Override
    public void nextColumn() {
        ImGui.nextColumn();
    }

    @Override
    public int getColumnIndex() {
        return ImGui.getColumnIndex();
    }

    @Override
    public float getColumnWidth() {
        return ImGui.getColumnWidth();
    }

    @Override
    public float getColumnWidth(int columnIndex) {
        return ImGui.getColumnWidth(columnIndex);
    }

    @Override
    public void setColumnWidth(int columnIndex, float width) {
        ImGui.setColumnWidth(columnIndex, width);
    }

    @Override
    public float getColumnOffset() {
        return ImGui.getColumnOffset();
    }

    @Override
    public float getColumnOffset(int columnIndex) {
        return ImGui.getColumnOffset(columnIndex);
    }

    @Override
    public void setColumnOffset(int columnIndex, float offsetX) {
        ImGui.setColumnOffset(columnIndex, offsetX);
    }

    @Override
    public int getColumnsCount() {
        return ImGui.getColumnsCount();
    }

    @Override
    public boolean beginTabBar(final String strId) {
        return ImGui.beginTabBar(strId);
    }

    @Override
    public boolean beginTabBar(final String strId, int imGuiTabBarFlags) {
        return ImGui.beginTabBar(strId, imGuiTabBarFlags);
    }

    @Override
    public void endTabBar() {
        ImGui.endTabBar();
    }

    @Override
    public boolean beginTabItem(final String label) {
        return ImGui.beginTabItem(label);
    }

    @Override
    public boolean beginTabItem(final String label, final KBooleanReference pOpen) {
        ImBoolean b = new ImBoolean(pOpen.get());
        boolean result = ImGui.beginTabItem(label, b);
        pOpen.set(b.get());
        return result;
    }

    @Override
    public boolean beginTabItem(
        final String label,
        final KBooleanReference pOpen,
        int imGuiTabItemFlags
    ) {
        ImBoolean b = new ImBoolean(pOpen.get());
        boolean result = ImGui.beginTabItem(label, b, imGuiTabItemFlags);
        pOpen.set(b.get());
        return result;
    }

    @Override
    public boolean beginTabItem(final String label, int imGuiTabItemFlags) {
        return ImGui.beginTabItem(label, imGuiTabItemFlags);
    }

    @Override
    public void endTabItem() {
        ImGui.endTabItem();
    }

    @Override
    public boolean tabItemButton(final String label) {
        return ImGui.tabItemButton(label);
    }

    @Override
    public boolean tabItemButton(final String label, int imGuiTabItemFlags) {
        return ImGui.tabItemButton(label, imGuiTabItemFlags);
    }

    @Override
    public void setTabItemClosed(final String tabOrDockedWindowLabel) {
        ImGui.setTabItemClosed(tabOrDockedWindowLabel);
    }

    @Override
    public int dockSpace(int dockspaceId) {
        return ImGui.dockSpace(dockspaceId);
    }

    @Override
    public int dockSpace(int dockspaceId, final KVector2f size) {
        return ImGui.dockSpace(dockspaceId, KImGuiSpairWrapper.wrap(size));
    }

    @Override
    public int dockSpace(int dockspaceId, float sizeX, float sizeY) {
        return ImGui.dockSpace(dockspaceId, sizeX, sizeY);
    }

    @Override
    public int dockSpace(int dockspaceId, final KVector2f size, int imGuiDockNodeFlags) {
        return ImGui.dockSpace(dockspaceId, KImGuiSpairWrapper.wrap(size), imGuiDockNodeFlags);
    }

    @Override
    public int dockSpace(int dockspaceId, float sizeX, float sizeY, int imGuiDockNodeFlags) {
        return ImGui.dockSpace(dockspaceId, sizeX, sizeY, imGuiDockNodeFlags);
    }

    @Override
    public int dockSpace(
        int dockspaceId,
        final KVector2f size,
        int imGuiDockNodeFlags,
        final KImGuiWindowClass windowClass
    ) {
        return ImGui.dockSpace(
            dockspaceId,
            KImGuiSpairWrapper.wrap(size),
            imGuiDockNodeFlags,
            KImGuiSpairUnboxer.unbox(windowClass)
        );
    }

    @Override
    public int dockSpace(
        int dockspaceId,
        float sizeX,
        float sizeY,
        int imGuiDockNodeFlags,
        final KImGuiWindowClass windowClass
    ) {
        return ImGui.dockSpace(
            dockspaceId,
            sizeX,
            sizeY,
            imGuiDockNodeFlags,
            KImGuiSpairUnboxer.unbox(windowClass)
        );
    }

    @Override
    public int dockSpace(
        int dockspaceId,
        int imGuiDockNodeFlags,
        final KImGuiWindowClass windowClass
    ) {
        return ImGui.dockSpace(
            dockspaceId,
            imGuiDockNodeFlags,
            KImGuiSpairUnboxer.unbox(windowClass)
        );
    }

    @Override
    public int dockSpace(int dockspaceId, final KImGuiWindowClass windowClass) {
        return ImGui.dockSpace(dockspaceId, KImGuiSpairUnboxer.unbox(windowClass));
    }

    @Override
    public int dockSpace(
        int dockspaceId,
        final KVector2f size,
        final KImGuiWindowClass windowClass
    ) {
        return ImGui.dockSpace(
            dockspaceId,
            KImGuiSpairWrapper.wrap(size),
            KImGuiSpairUnboxer.unbox(windowClass)
        );
    }

    @Override
    public int dockSpace(
        int dockspaceId,
        float sizeX,
        float sizeY,
        final KImGuiWindowClass windowClass
    ) {
        return ImGui.dockSpace(dockspaceId, sizeX, sizeY, KImGuiSpairUnboxer.unbox(windowClass));
    }

    @Override
    public int dockSpaceOverViewport() {
        return ImGui.dockSpaceOverViewport();
    }

    @Override
    public int dockSpaceOverViewport(int dockspaceId) {
        return ImGui.dockSpaceOverViewport(dockspaceId);
    }

    @Override
    public int dockSpaceOverViewport(int dockspaceId, final KImGuiViewport viewport) {
        return ImGui.dockSpaceOverViewport(dockspaceId, KImGuiSpairUnboxer.unbox(viewport));
    }

    @Override
    public int dockSpaceOverViewport(
        int dockspaceId,
        final KImGuiViewport viewport,
        int imGuiDockNodeFlags
    ) {
        return ImGui.dockSpaceOverViewport(
            dockspaceId,
            KImGuiSpairUnboxer.unbox(viewport),
            imGuiDockNodeFlags
        );
    }

    @Override
    public int dockSpaceOverViewport(
        int dockspaceId,
        final KImGuiViewport viewport,
        int imGuiDockNodeFlags,
        final KImGuiWindowClass windowClass
    ) {
        return ImGui.dockSpaceOverViewport(
            dockspaceId,
            KImGuiSpairUnboxer.unbox(viewport),
            imGuiDockNodeFlags,
            KImGuiSpairUnboxer.unbox(windowClass)
        );
    }

    @Override
    public int dockSpaceOverViewport(
        final KImGuiViewport viewport,
        int imGuiDockNodeFlags,
        final KImGuiWindowClass windowClass
    ) {
        return ImGui.dockSpaceOverViewport(
            KImGuiSpairUnboxer.unbox(viewport),
            imGuiDockNodeFlags,
            KImGuiSpairUnboxer.unbox(windowClass)
        );
    }

    @Override
    public int dockSpaceOverViewport(
        int dockspaceId,
        final KImGuiViewport viewport,
        final KImGuiWindowClass windowClass
    ) {
        return ImGui.dockSpaceOverViewport(
            dockspaceId,
            KImGuiSpairUnboxer.unbox(viewport),
            KImGuiSpairUnboxer.unbox(windowClass)
        );
    }

    @Override
    public void setNextWindowDockID(int dockId) {
        ImGui.setNextWindowDockID(dockId);
    }

    @Override
    public void setNextWindowDockID(int dockId, int imGuiCond) {
        ImGui.setNextWindowDockID(dockId, imGuiCond);
    }

    @Override
    public void setNextWindowClass(final KImGuiWindowClass windowClass) {
        ImGui.setNextWindowClass(KImGuiSpairUnboxer.unbox(windowClass));
    }

    @Override
    public int getWindowDockID() {
        return ImGui.getWindowDockID();
    }

    @Override
    public boolean isWindowDocked() {
        return ImGui.isWindowDocked();
    }

    @Override
    public void logToTTY() {
        ImGui.logToTTY();
    }

    @Override
    public void logToTTY(int autoOpenDepth) {
        ImGui.logToTTY(autoOpenDepth);
    }

    @Override
    public void logToFile() {
        ImGui.logToFile();
    }

    @Override
    public void logToFile(int autoOpenDepth) {
        ImGui.logToFile(autoOpenDepth);
    }

    @Override
    public void logToFile(int autoOpenDepth, final String filename) {
        ImGui.logToFile(autoOpenDepth, filename);
    }

    @Override
    public void logToFile(final String filename) {
        ImGui.logToFile(filename);
    }

    @Override
    public void logToClipboard() {
        ImGui.logToClipboard();
    }

    @Override
    public void logToClipboard(int autoOpenDepth) {
        ImGui.logToClipboard(autoOpenDepth);
    }

    @Override
    public void logFinish() {
        ImGui.logFinish();
    }

    @Override
    public void logButtons() {
        ImGui.logButtons();
    }

    @Override
    public void logText(final String text) {
        ImGui.logText(text);
    }

    @Override
    public boolean beginDragDropSource() {
        return ImGui.beginDragDropSource();
    }

    @Override
    public boolean beginDragDropSource(int imGuiDragDropFlags) {
        return ImGui.beginDragDropSource(imGuiDragDropFlags);
    }

    @Override
    public boolean setDragDropPayload(final String dataType, final Object payload) {
        return ImGui.setDragDropPayload(dataType, payload);
    }

    @Override
    public boolean setDragDropPayload(final String dataType, final Object payload, int imGuiCond) {
        return ImGui.setDragDropPayload(dataType, payload, imGuiCond);
    }

    @Override
    public boolean setDragDropPayload(final Object payload) {
        return ImGui.setDragDropPayload(payload);
    }

    @Override
    public boolean setDragDropPayload(final Object payload, int imGuiCond) {
        return ImGui.setDragDropPayload(payload, imGuiCond);
    }

    @Override
    public void endDragDropSource() {
        ImGui.endDragDropSource();
    }

    @Override
    public boolean beginDragDropTarget() {
        return ImGui.beginDragDropTarget();
    }

    @Override
    public <T> T acceptDragDropPayload(final String dataType) {
        return ImGui.acceptDragDropPayload(dataType);
    }

    @Override
    public <T> T acceptDragDropPayload(final String dataType, final Class<T> aClass) {
        return ImGui.acceptDragDropPayload(dataType, aClass);
    }

    @Override
    public <T> T acceptDragDropPayload(final String dataType, int imGuiDragDropFlags) {
        return ImGui.acceptDragDropPayload(dataType, imGuiDragDropFlags);
    }

    @Override
    public <T> T acceptDragDropPayload(
        final String dataType,
        int imGuiDragDropFlags,
        final Class<T> aClass
    ) {
        return ImGui.acceptDragDropPayload(dataType, imGuiDragDropFlags, aClass);
    }

    @Override
    public <T> T acceptDragDropPayload(final Class<T> aClass) {
        return ImGui.acceptDragDropPayload(aClass);
    }

    @Override
    public <T> T acceptDragDropPayload(final Class<T> aClass, int imGuiDragDropFlags) {
        return ImGui.acceptDragDropPayload(aClass, imGuiDragDropFlags);
    }

    @Override
    public void endDragDropTarget() {
        ImGui.endDragDropTarget();
    }

    @Override
    public <T> T getDragDropPayload() {
        return ImGui.getDragDropPayload();
    }

    @Override
    public <T> T getDragDropPayload(final String dataType) {
        return ImGui.getDragDropPayload(dataType);
    }

    @Override
    public <T> T getDragDropPayload(final Class<T> aClass) {
        return ImGui.getDragDropPayload(aClass);
    }

    @Override
    public void beginDisabled() {
        ImGui.beginDisabled();
    }

    @Override
    public void beginDisabled(boolean disabled) {
        ImGui.beginDisabled(disabled);
    }

    @Override
    public void endDisabled() {
        ImGui.endDisabled();
    }

    @Override
    public void pushClipRect(
        final KVector2f clipRectMin,
        final KVector2f clipRectMax,
        boolean intersectWithCurrentClipRect
    ) {
        ImGui.pushClipRect(
            KImGuiSpairWrapper.wrap(clipRectMin),
            KImGuiSpairWrapper.wrap(clipRectMax),
            intersectWithCurrentClipRect
        );
    }

    @Override
    public void pushClipRect(
        float clipRectMinX,
        float clipRectMinY,
        float clipRectMaxX,
        float clipRectMaxY,
        boolean intersectWithCurrentClipRect
    ) {
        ImGui.pushClipRect(
            clipRectMinX,
            clipRectMinY,
            clipRectMaxX,
            clipRectMaxY,
            intersectWithCurrentClipRect
        );
    }

    @Override
    public void popClipRect() {
        ImGui.popClipRect();
    }

    @Override
    public void setItemDefaultFocus() {
        ImGui.setItemDefaultFocus();
    }

    @Override
    public void setKeyboardFocusHere() {
        ImGui.setKeyboardFocusHere();
    }

    @Override
    public void setKeyboardFocusHere(int offset) {
        ImGui.setKeyboardFocusHere(offset);
    }

    @Override
    public void setNextItemAllowOverlap() {
        ImGui.setNextItemAllowOverlap();
    }

    @Override
    public boolean isItemHovered() {
        return ImGui.isItemHovered();
    }

    @Override
    public boolean isItemHovered(int imGuiHoveredFlags) {
        return ImGui.isItemHovered(imGuiHoveredFlags);
    }

    @Override
    public boolean isItemActive() {
        return ImGui.isItemActive();
    }

    @Override
    public boolean isItemFocused() {
        return ImGui.isItemFocused();
    }

    @Override
    public boolean isItemClicked() {
        return ImGui.isItemClicked();
    }

    @Override
    public boolean isItemClicked(int mouseButton) {
        return ImGui.isItemClicked(mouseButton);
    }

    @Override
    public boolean isItemVisible() {
        return ImGui.isItemVisible();
    }

    @Override
    public boolean isItemEdited() {
        return ImGui.isItemEdited();
    }

    @Override
    public boolean isItemActivated() {
        return ImGui.isItemActivated();
    }

    @Override
    public boolean isItemDeactivated() {
        return ImGui.isItemDeactivated();
    }

    @Override
    public boolean isItemDeactivatedAfterEdit() {
        return ImGui.isItemDeactivatedAfterEdit();
    }

    @Override
    public boolean isItemToggledOpen() {
        return ImGui.isItemToggledOpen();
    }

    @Override
    public boolean isAnyItemHovered() {
        return ImGui.isAnyItemHovered();
    }

    @Override
    public boolean isAnyItemActive() {
        return ImGui.isAnyItemActive();
    }

    @Override
    public boolean isAnyItemFocused() {
        return ImGui.isAnyItemFocused();
    }

    @Override
    public int getItemID() {
        return ImGui.getItemID();
    }

    @Override
    public KVector2f getItemRectMin() {
        return KImGuiSpairUnwrapper.wrap(ImGui.getItemRectMin());
    }

    @Override
    public float getItemRectMinX() {
        return ImGui.getItemRectMinX();
    }

    @Override
    public float getItemRectMinY() {
        return ImGui.getItemRectMinY();
    }

    @Override
    public KVector2f getItemRectMax() {
        return KImGuiSpairUnwrapper.wrap(ImGui.getItemRectMax());
    }

    @Override
    public float getItemRectMaxX() {
        return ImGui.getItemRectMaxX();
    }

    @Override
    public float getItemRectMaxY() {
        return ImGui.getItemRectMaxY();
    }

    @Override
    public KVector2f getItemRectSize() {
        return KImGuiSpairUnwrapper.wrap(ImGui.getItemRectSize());
    }

    @Override
    public float getItemRectSizeX() {
        return ImGui.getItemRectSizeX();
    }

    @Override
    public float getItemRectSizeY() {
        return ImGui.getItemRectSizeY();
    }

    @Override
    public KImGuiViewport getMainViewport() {
        return KImGuiSpairUnwrapper.wrap(ImGui.getMainViewport());
    }

    @Override
    public KImDrawList getBackgroundDrawList() {
        return KImGuiSpairUnwrapper.wrap(ImGui.getBackgroundDrawList());
    }

    @Override
    public KImDrawList getBackgroundDrawList(final KImGuiViewport viewport) {
        return KImGuiSpairUnwrapper.wrap(ImGui.getBackgroundDrawList(KImGuiSpairUnboxer.unbox(
            viewport)));
    }

    @Override
    public KImDrawList getForegroundDrawList() {
        return KImGuiSpairUnwrapper.wrap(ImGui.getForegroundDrawList());
    }

    @Override
    public KImDrawList getForegroundDrawList(final KImGuiViewport viewport) {
        return KImGuiSpairUnwrapper.wrap(ImGui.getForegroundDrawList(KImGuiSpairUnboxer.unbox(
            viewport)));
    }

    @Override
    public boolean isRectVisible(final KVector2f size) {
        return ImGui.isRectVisible(KImGuiSpairWrapper.wrap(size));
    }

    @Override
    public boolean isRectVisible(float sizeX, float sizeY) {
        return ImGui.isRectVisible(sizeX, sizeY);
    }

    @Override
    public boolean isRectVisible(final KVector2f rectMin, final KVector2f rectMax) {
        return ImGui.isRectVisible(
            KImGuiSpairWrapper.wrap(rectMin),
            KImGuiSpairWrapper.wrap(rectMax)
        );
    }

    @Override
    public boolean isRectVisible(float rectMinX, float rectMinY, float rectMaxX, float rectMaxY) {
        return ImGui.isRectVisible(rectMinX, rectMinY, rectMaxX, rectMaxY);
    }

    @Override
    public double getTime() {
        return ImGui.getTime();
    }

    @Override
    public int getFrameCount() {
        return ImGui.getFrameCount();
    }

    @Override
    public String getStyleColorName(int imGuiColIdx) {
        return ImGui.getStyleColorName(imGuiColIdx);
    }

    @Override
    public KImGuiStorage getStateStorage() {
        return KImGuiSpairUnwrapper.wrap(ImGui.getStateStorage());
    }

    @Override
    public void setStateStorage(final KImGuiStorage storage) {
        ImGui.setStateStorage(KImGuiSpairUnboxer.unbox(storage));
    }

    @Override
    public KVector2f calcTextSize(final String text) {
        return KImGuiSpairUnwrapper.wrap(ImGui.calcTextSize(text));
    }

    @Override
    public float calcTextSizeX(final String text) {
        return ImGui.calcTextSizeX(text);
    }

    @Override
    public float calcTextSizeY(final String text) {
        return ImGui.calcTextSizeY(text);
    }

    @Override
    public KVector2f calcTextSize(final String text, boolean hideTextAfterDoubleHash) {
        return KImGuiSpairUnwrapper.wrap(ImGui.calcTextSize(text, hideTextAfterDoubleHash));
    }

    @Override
    public float calcTextSizeX(final String text, boolean hideTextAfterDoubleHash) {
        return ImGui.calcTextSizeX(text, hideTextAfterDoubleHash);
    }

    @Override
    public float calcTextSizeY(final String text, boolean hideTextAfterDoubleHash) {
        return ImGui.calcTextSizeY(text, hideTextAfterDoubleHash);
    }

    @Override
    public KVector2f calcTextSize(
        final String text,
        boolean hideTextAfterDoubleHash,
        float wrapWidth
    ) {
        return KImGuiSpairUnwrapper.wrap(ImGui.calcTextSize(
            text,
            hideTextAfterDoubleHash,
            wrapWidth
        ));
    }

    @Override
    public float calcTextSizeX(
        final String text,
        boolean hideTextAfterDoubleHash,
        float wrapWidth
    ) {
        return ImGui.calcTextSizeX(text, hideTextAfterDoubleHash, wrapWidth);
    }

    @Override
    public float calcTextSizeY(
        final String text,
        boolean hideTextAfterDoubleHash,
        float wrapWidth
    ) {
        return ImGui.calcTextSizeY(text, hideTextAfterDoubleHash, wrapWidth);
    }

    @Override
    public KVector2f calcTextSize(final String text, float wrapWidth) {
        return KImGuiSpairUnwrapper.wrap(ImGui.calcTextSize(text, wrapWidth));
    }

    @Override
    public float calcTextSizeX(final String text, float wrapWidth) {
        return ImGui.calcTextSizeX(text, wrapWidth);
    }

    @Override
    public float calcTextSizeY(final String text, float wrapWidth) {
        return ImGui.calcTextSizeY(text, wrapWidth);
    }

    @Override
    public KVector4f colorConvertU32ToFloat4(int in) {
        return KImGuiSpairUnwrapper.wrap(ImGui.colorConvertU32ToFloat4(in));
    }

    @Override
    public float colorConvertU32ToFloat4X(int in) {
        return ImGui.colorConvertU32ToFloat4X(in);
    }

    @Override
    public float colorConvertU32ToFloat4Y(int in) {
        return ImGui.colorConvertU32ToFloat4Y(in);
    }

    @Override
    public float colorConvertU32ToFloat4Z(int in) {
        return ImGui.colorConvertU32ToFloat4Z(in);
    }

    @Override
    public float colorConvertU32ToFloat4W(int in) {
        return ImGui.colorConvertU32ToFloat4W(in);
    }

    @Override
    public int colorConvertFloat4ToU32(final KVector4f in) {
        return ImGui.colorConvertFloat4ToU32(KImGuiSpairWrapper.wrap(in));
    }

    @Override
    public int colorConvertFloat4ToU32(float inX, float inY, float inZ, float inW) {
        return ImGui.colorConvertFloat4ToU32(inX, inY, inZ, inW);
    }

    @Override
    public void colorConvertRGBtoHSV(final float[] rgb, final float[] hsv) {
        ImGui.colorConvertRGBtoHSV(rgb, hsv);
    }

    @Override
    public void colorConvertHSVtoRGB(final float[] hsv, final float[] rgb) {
        ImGui.colorConvertHSVtoRGB(hsv, rgb);
    }

    @Override
    public boolean isKeyDown(int key) {
        return ImGui.isKeyDown(key);
    }

    @Override
    public boolean isKeyPressed(int key) {
        return ImGui.isKeyPressed(key);
    }

    @Override
    public boolean isKeyPressed(int key, boolean repeat) {
        return ImGui.isKeyPressed(key, repeat);
    }

    @Override
    public boolean isKeyReleased(int key) {
        return ImGui.isKeyReleased(key);
    }

    @Override
    public boolean isKeyChordPressed(int keyChord) {
        return ImGui.isKeyChordPressed(keyChord);
    }

    @Override
    public boolean getKeyPressedAmount(int key, float repeatDelay, float rate) {
        return ImGui.getKeyPressedAmount(key, repeatDelay, rate);
    }

    @Override
    public String getKeyName(int key) {
        return ImGui.getKeyName(key);
    }

    @Override
    public void setNextFrameWantCaptureKeyboard(boolean wantCaptureKeyboard) {
        ImGui.setNextFrameWantCaptureKeyboard(wantCaptureKeyboard);
    }

    @Override
    public boolean shortcut(int keyChord) {
        return ImGui.shortcut(keyChord);
    }

    @Override
    public boolean shortcut(int keyChord, int flags) {
        return ImGui.shortcut(keyChord, flags);
    }

    @Override
    public void setNextItemShortcut(int keyChord) {
        ImGui.setNextItemShortcut(keyChord);
    }

    @Override
    public void setNextItemShortcut(int keyChord, int flags) {
        ImGui.setNextItemShortcut(keyChord, flags);
    }

    @Override
    public boolean isMouseDown(int button) {
        return ImGui.isMouseDown(button);
    }

    @Override
    public boolean isMouseClicked(int button) {
        return ImGui.isMouseClicked(button);
    }

    @Override
    public boolean isMouseClicked(int button, boolean repeat) {
        return ImGui.isMouseClicked(button, repeat);
    }

    @Override
    public boolean isMouseReleased(int button) {
        return ImGui.isMouseReleased(button);
    }

    @Override
    public boolean isMouseDoubleClicked(int button) {
        return ImGui.isMouseDoubleClicked(button);
    }

    @Override
    public int getMouseClickedCount(int button) {
        return ImGui.getMouseClickedCount(button);
    }

    @Override
    public boolean isMouseHoveringRect(final KVector2f rMin, final KVector2f rMax) {
        return ImGui.isMouseHoveringRect(
            KImGuiSpairWrapper.wrap(rMin),
            KImGuiSpairWrapper.wrap(rMax)
        );
    }

    @Override
    public boolean isMouseHoveringRect(float rMinX, float rMinY, float rMaxX, float rMaxY) {
        return ImGui.isMouseHoveringRect(rMinX, rMinY, rMaxX, rMaxY);
    }

    @Override
    public boolean isMouseHoveringRect(final KVector2f rMin, final KVector2f rMax, boolean clip) {
        return ImGui.isMouseHoveringRect(
            KImGuiSpairWrapper.wrap(rMin),
            KImGuiSpairWrapper.wrap(rMax),
            clip
        );
    }

    @Override
    public boolean isMouseHoveringRect(
        float rMinX,
        float rMinY,
        float rMaxX,
        float rMaxY,
        boolean clip
    ) {
        return ImGui.isMouseHoveringRect(rMinX, rMinY, rMaxX, rMaxY, clip);
    }

    @Override
    public boolean isMousePosValid() {
        return ImGui.isMousePosValid();
    }

    @Override
    public boolean isMousePosValid(final KVector2f mousePos) {
        return ImGui.isMousePosValid(KImGuiSpairWrapper.wrap(mousePos));
    }

    @Override
    public boolean isMousePosValid(float mousePosX, float mousePosY) {
        return ImGui.isMousePosValid(mousePosX, mousePosY);
    }

    @Override
    public boolean isAnyMouseDown() {
        return ImGui.isAnyMouseDown();
    }

    @Override
    public KVector2f getMousePos() {
        return KImGuiSpairUnwrapper.wrap(ImGui.getMousePos());
    }

    @Override
    public float getMousePosX() {
        return ImGui.getMousePosX();
    }

    @Override
    public float getMousePosY() {
        return ImGui.getMousePosY();
    }

    @Override
    public KVector2f getMousePosOnOpeningCurrentPopup() {
        return KImGuiSpairUnwrapper.wrap(ImGui.getMousePosOnOpeningCurrentPopup());
    }

    @Override
    public float getMousePosOnOpeningCurrentPopupX() {
        return ImGui.getMousePosOnOpeningCurrentPopupX();
    }

    @Override
    public float getMousePosOnOpeningCurrentPopupY() {
        return ImGui.getMousePosOnOpeningCurrentPopupY();
    }

    @Override
    public boolean isMouseDragging(int button) {
        return ImGui.isMouseDragging(button);
    }

    @Override
    public boolean isMouseDragging(int button, float lockThreshold) {
        return ImGui.isMouseDragging(button, lockThreshold);
    }

    @Override
    public KVector2f getMouseDragDelta() {
        return KImGuiSpairUnwrapper.wrap(ImGui.getMouseDragDelta());
    }

    @Override
    public float getMouseDragDeltaX() {
        return ImGui.getMouseDragDeltaX();
    }

    @Override
    public float getMouseDragDeltaY() {
        return ImGui.getMouseDragDeltaY();
    }

    @Override
    public KVector2f getMouseDragDelta(int button) {
        return KImGuiSpairUnwrapper.wrap(ImGui.getMouseDragDelta(button));
    }

    @Override
    public float getMouseDragDeltaX(int button) {
        return ImGui.getMouseDragDeltaX(button);
    }

    @Override
    public float getMouseDragDeltaY(int button) {
        return ImGui.getMouseDragDeltaY(button);
    }

    @Override
    public KVector2f getMouseDragDelta(int button, float lockThreshold) {
        return KImGuiSpairUnwrapper.wrap(ImGui.getMouseDragDelta(button, lockThreshold));
    }

    @Override
    public float getMouseDragDeltaX(int button, float lockThreshold) {
        return ImGui.getMouseDragDeltaX(button, lockThreshold);
    }

    @Override
    public float getMouseDragDeltaY(int button, float lockThreshold) {
        return ImGui.getMouseDragDeltaY(button, lockThreshold);
    }

    @Override
    public void resetMouseDragDelta() {
        ImGui.resetMouseDragDelta();
    }

    @Override
    public void resetMouseDragDelta(int button) {
        ImGui.resetMouseDragDelta(button);
    }

    @Override
    public int getMouseCursor() {
        return ImGui.getMouseCursor();
    }

    @Override
    public void setMouseCursor(int type) {
        ImGui.setMouseCursor(type);
    }

    @Override
    public void setNextFrameWantCaptureMouse(boolean wantCaptureMouse) {
        ImGui.setNextFrameWantCaptureMouse(wantCaptureMouse);
    }

    @Override
    public String getClipboardText() {
        return ImGui.getClipboardText();
    }

    @Override
    public void setClipboardText(final String text) {
        ImGui.setClipboardText(text);
    }

    @Override
    public void loadIniSettingsFromDisk(final String iniFilename) {
        ImGui.loadIniSettingsFromDisk(iniFilename);
    }

    @Override
    public void loadIniSettingsFromMemory(final String iniData) {
        ImGui.loadIniSettingsFromMemory(iniData);
    }

    @Override
    public void loadIniSettingsFromMemory(final String iniData, int iniSize) {
        ImGui.loadIniSettingsFromMemory(iniData, iniSize);
    }

    @Override
    public void saveIniSettingsToDisk(final String iniFilename) {
        ImGui.saveIniSettingsToDisk(iniFilename);
    }

    @Override
    public String saveIniSettingsToMemory() {
        return ImGui.saveIniSettingsToMemory();
    }

    @Override
    public String saveIniSettingsToMemory(long outIniSize) {
        return ImGui.saveIniSettingsToMemory(outIniSize);
    }

    @Override
    public void debugTextEncoding(final String text) {
        ImGui.debugTextEncoding(text);
    }

    @Override
    public void debugFlashStyleColor(int idx) {
        ImGui.debugFlashStyleColor(idx);
    }

    @Override
    public void debugStartItemPicker() {
        ImGui.debugStartItemPicker();
    }

    @Override
    public boolean debugCheckVersionAndDataLayout(
        final String versionStr,
        int szIo,
        int szStyle,
        int szVec2,
        int szVec4,
        int szDrawVert,
        int szDrawIdx
    ) {
        return ImGui.debugCheckVersionAndDataLayout(
            versionStr,
            szIo,
            szStyle,
            szVec2,
            szVec4,
            szDrawVert,
            szDrawIdx
        );
    }

    @Override
    public KImGuiPlatformIo getPlatformIO() {
        return KImGuiSpairUnwrapper.wrap(ImGui.getPlatformIO());
    }

    @Override
    public void updatePlatformWindows() {
        ImGui.updatePlatformWindows();
    }

    @Override
    public void renderPlatformWindowsDefault() {
        ImGui.renderPlatformWindowsDefault();
    }

    @Override
    public void destroyPlatformWindows() {
        ImGui.destroyPlatformWindows();
    }

    @Override
    public KImGuiViewport findViewportByID(int imGuiID) {
        return KImGuiSpairUnwrapper.wrap(ImGui.findViewportByID(imGuiID));
    }

    @Override
    public KImGuiViewport findViewportByPlatformHandle(long platformHandle) {
        return KImGuiSpairUnwrapper.wrap(ImGui.findViewportByPlatformHandle(platformHandle));
    }

}
