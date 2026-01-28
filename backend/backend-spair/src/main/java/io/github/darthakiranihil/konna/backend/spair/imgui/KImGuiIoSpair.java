/*
 * Copyright 2this.boxed.25-present the original author or authors.
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

import imgui.ImGuiIO;
import imgui.ImGuiKeyData;
import imgui.ImVec2;
import imgui.callback.ImStrConsumer;
import imgui.callback.ImStrSupplier;
import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.libfrontend.imgui.*;
import io.github.darthakiranihil.konna.libfrontend.imgui.func.KImStrConsumer;
import io.github.darthakiranihil.konna.libfrontend.imgui.func.KImStrSupplier;

final class KImGuiIoSpair implements KImGuiIo {

    private final ImGuiIO boxed;

    KImGuiIoSpair(final ImGuiIO boxed) {
        this.boxed = boxed;
    }

    @Override
    public int getConfigFlags() {
        return this.boxed.getConfigFlags();
    }

    @Override
    public void setConfigFlags(int value) {
        this.boxed.setConfigFlags(value);
    }

    @Override
    public void addConfigFlags(int flags) {
        this.boxed.addConfigFlags(flags);
    }

    @Override
    public void removeConfigFlags(int flags) {
        this.boxed.removeConfigFlags(flags);
    }

    @Override
    public boolean hasConfigFlags(int flags) {
        return this.boxed.hasConfigFlags(flags);
    }

    @Override
    public int getBackendFlags() {
        return this.boxed.getBackendFlags();
    }

    @Override
    public void setBackendFlags(int value) {
        this.boxed.setBackendFlags(value);
    }

    @Override
    public void addBackendFlags(int flags) {
        this.boxed.addBackendFlags(flags);
    }

    @Override
    public void removeBackendFlags(int flags) {
        this.boxed.removeBackendFlags(flags);
    }

    @Override
    public boolean hasBackendFlags(int flags) {
        return this.boxed.hasBackendFlags(flags);
    }

    @Override
    public KVector2f getDisplaySize() {
        return KImGuiSpairUnwrapper.wrap(this.boxed.getDisplaySize());
    }

    @Override
    public void setDisplaySize(final KVector2f value) {
        this.boxed.setDisplaySize(KImGuiSpairWrapper.wrap(value));
    }

    @Override
    public float getDisplaySizeX() {
        return this.boxed.getDisplaySizeX();
    }

    @Override
    public float getDisplaySizeY() {
        return this.boxed.getDisplaySizeY();
    }

    @Override
    public void setDisplaySize(float valueX, float valueY) {
        this.boxed.setDisplaySize(valueX, valueY);
    }

    @Override
    public float getDeltaTime() {
        return this.boxed.getDeltaTime();
    }

    @Override
    public void setDeltaTime(float value) {
        this.boxed.setDeltaTime(value);
    }

    @Override
    public float getIniSavingRate() {
        return this.boxed.getIniSavingRate();
    }

    @Override
    public void setIniSavingRate(float value) {
        this.boxed.setIniSavingRate(value);
    }

    @Override
    public String getIniFilename() {
        return this.boxed.getIniFilename();
    }

    @Override
    public void setIniFilename(final String value) {
        this.boxed.setIniFilename(value);
    }

    @Override
    public String getLogFilename() {
        return this.boxed.getLogFilename();
    }

    @Override
    public void setLogFilename(final String value) {
        this.boxed.setLogFilename(value);
    }

    @Override
    public KImFontAtlas getFonts() {
        return KImGuiSpairUnwrapper.wrap(this.boxed.getFonts());
    }

    @Override
    public void setFonts(final KImFontAtlas value) {
        this.boxed.setFonts(KImGuiSpairUnboxer.unbox(value));
    }

    @Override
    public float getFontGlobalScale() {
        return this.boxed.getFontGlobalScale();
    }

    @Override
    public void setFontGlobalScale(float value) {
        this.boxed.setFontGlobalScale(value);
    }

    @Override
    public boolean getFontAllowUserScaling() {
        return this.boxed.getFontAllowUserScaling();
    }

    @Override
    public void setFontAllowUserScaling(boolean value) {
        this.boxed.setFontAllowUserScaling(value);
    }

    @Override
    public KImFont getFontDefault() {
        return KImGuiSpairUnwrapper.wrap(this.boxed.getFontDefault());
    }

    @Override
    public void setFontDefault(final KImFont value) {
        this.boxed.setFontDefault(KImGuiSpairUnboxer.unbox(value));
    }

    @Override
    public KVector2f getDisplayFramebufferScale() {
        return KImGuiSpairUnwrapper.wrap(this.boxed.getDisplayFramebufferScale());
    }

    @Override
    public void setDisplayFramebufferScale(final KVector2f value) {
        this.boxed.setDisplayFramebufferScale(KImGuiSpairWrapper.wrap(value));
    }

    @Override
    public float getDisplayFramebufferScaleX() {
        return this.boxed.getDisplayFramebufferScaleX();
    }

    @Override
    public float getDisplayFramebufferScaleY() {
        return this.boxed.getDisplayFramebufferScaleY();
    }

    @Override
    public void setDisplayFramebufferScale(float valueX, float valueY) {
        this.boxed.setDisplayFramebufferScale(valueX, valueY);
    }

    @Override
    public boolean getConfigDockingNoSplit() {
        return this.boxed.getConfigDockingNoSplit();
    }

    @Override
    public void setConfigDockingNoSplit(boolean value) {
        this.boxed.setConfigDockingNoSplit(value);
    }

    @Override
    public boolean getConfigDockingWithShift() {
        return this.boxed.getConfigDockingWithShift();
    }

    @Override
    public void setConfigDockingWithShift(boolean value) {
        this.boxed.setConfigDockingWithShift(value);
    }

    @Override
    public boolean getConfigDockingAlwaysTabBar() {
        return this.boxed.getConfigDockingAlwaysTabBar();
    }

    @Override
    public void setConfigDockingAlwaysTabBar(boolean value) {
        this.boxed.setConfigDockingAlwaysTabBar(value);
    }

    @Override
    public boolean getConfigDockingTransparentPayload() {
        return this.boxed.getConfigDockingTransparentPayload();
    }

    @Override
    public void setConfigDockingTransparentPayload(boolean value) {
        this.boxed.setConfigDockingTransparentPayload(value);
    }

    @Override
    public boolean getConfigViewportsNoAutoMerge() {
        return this.boxed.getConfigViewportsNoAutoMerge();
    }

    @Override
    public void setConfigViewportsNoAutoMerge(boolean value) {
        this.boxed.setConfigViewportsNoAutoMerge(value);
    }

    @Override
    public boolean getConfigViewportsNoTaskBarIcon() {
        return this.boxed.getConfigViewportsNoTaskBarIcon();
    }

    @Override
    public void setConfigViewportsNoTaskBarIcon(boolean value) {
        this.boxed.setConfigViewportsNoTaskBarIcon(value);
    }

    @Override
    public boolean getConfigViewportsNoDecoration() {
        return this.boxed.getConfigViewportsNoDecoration();
    }

    @Override
    public void setConfigViewportsNoDecoration(boolean value) {
        this.boxed.setConfigViewportsNoDecoration(value);
    }

    @Override
    public boolean getConfigViewportsNoDefaultParent() {
        return this.boxed.getConfigViewportsNoDefaultParent();
    }

    @Override
    public void setConfigViewportsNoDefaultParent(boolean value) {
        this.boxed.setConfigViewportsNoDefaultParent(value);
    }

    @Override
    public boolean getMouseDrawCursor() {
        return this.boxed.getMouseDrawCursor();
    }

    @Override
    public void setMouseDrawCursor(boolean value) {
        this.boxed.setMouseDrawCursor(value);
    }

    @Override
    public boolean getConfigMacOSXBehaviors() {
        return this.boxed.getConfigMacOSXBehaviors();
    }

    @Override
    public void setConfigMacOSXBehaviors(boolean value) {
        this.boxed.setConfigMacOSXBehaviors(value);
    }

    @Override
    public boolean getConfigInputTrickleEventQueue() {
        return this.boxed.getConfigInputTrickleEventQueue();
    }

    @Override
    public void setConfigInputTrickleEventQueue(boolean value) {
        this.boxed.setConfigInputTrickleEventQueue(value);
    }

    @Override
    public boolean getConfigInputTextCursorBlink() {
        return this.boxed.getConfigInputTextCursorBlink();
    }

    @Override
    public void setConfigInputTextCursorBlink(boolean value) {
        this.boxed.setConfigInputTextCursorBlink(value);
    }

    @Override
    public boolean getConfigInputTextEnterKeepActive() {
        return this.boxed.getConfigInputTextEnterKeepActive();
    }

    @Override
    public void setConfigInputTextEnterKeepActive(boolean value) {
        this.boxed.setConfigInputTextEnterKeepActive(value);
    }

    @Override
    public boolean getConfigDragClickToInputText() {
        return this.boxed.getConfigDragClickToInputText();
    }

    @Override
    public void setConfigDragClickToInputText(boolean value) {
        this.boxed.setConfigDragClickToInputText(value);
    }

    @Override
    public boolean getConfigWindowsResizeFromEdges() {
        return this.boxed.getConfigWindowsResizeFromEdges();
    }

    @Override
    public void setConfigWindowsResizeFromEdges(boolean value) {
        this.boxed.setConfigWindowsResizeFromEdges(value);
    }

    @Override
    public boolean getConfigWindowsMoveFromTitleBarOnly() {
        return this.boxed.getConfigWindowsMoveFromTitleBarOnly();
    }

    @Override
    public void setConfigWindowsMoveFromTitleBarOnly(boolean value) {
        this.boxed.setConfigWindowsMoveFromTitleBarOnly(value);
    }

    @Override
    public boolean getConfigMemoryCompactTimer() {
        return this.boxed.getConfigMemoryCompactTimer();
    }

    @Override
    public void setConfigMemoryCompactTimer(boolean value) {
        this.boxed.setConfigMemoryCompactTimer(value);
    }

    @Override
    public float getMouseDoubleClickTime() {
        return this.boxed.getMouseDoubleClickTime();
    }

    @Override
    public void setMouseDoubleClickTime(float value) {
        this.boxed.setMouseDoubleClickTime(value);
    }

    @Override
    public float getMouseDoubleClickMaxDist() {
        return this.boxed.getMouseDoubleClickMaxDist();
    }

    @Override
    public void setMouseDoubleClickMaxDist(float value) {
        this.boxed.setMouseDoubleClickMaxDist(value);
    }

    @Override
    public float getMouseDragThreshold() {
        return this.boxed.getMouseDragThreshold();
    }

    @Override
    public void setMouseDragThreshold(float value) {
        this.boxed.setMouseDragThreshold(value);
    }

    @Override
    public float getKeyRepeatDelay() {
        return this.boxed.getKeyRepeatDelay();
    }

    @Override
    public void setKeyRepeatDelay(float value) {
        this.boxed.setKeyRepeatDelay(value);
    }

    @Override
    public float getKeyRepeatRate() {
        return this.boxed.getKeyRepeatRate();
    }

    @Override
    public void setKeyRepeatRate(float value) {
        this.boxed.setKeyRepeatRate(value);
    }

    @Override
    public boolean getConfigDebugIsDebuggerPresent() {
        return this.boxed.getConfigDebugIsDebuggerPresent();
    }

    @Override
    public void setConfigDebugIsDebuggerPresent(boolean value) {
        this.boxed.setConfigDebugIsDebuggerPresent(value);
    }

    @Override
    public boolean getConfigDebugBeginReturnValueOnce() {
        return this.boxed.getConfigDebugBeginReturnValueOnce();
    }

    @Override
    public void setConfigDebugBeginReturnValueOnce(boolean value) {
        this.boxed.setConfigDebugBeginReturnValueOnce(value);
    }

    @Override
    public boolean getConfigDebugBeginReturnValueLoop() {
        return this.boxed.getConfigDebugBeginReturnValueLoop();
    }

    @Override
    public void setConfigDebugBeginReturnValueLoop(boolean value) {
        this.boxed.setConfigDebugBeginReturnValueLoop(value);
    }

    @Override
    public boolean getConfigDebugIgnoreFocusLoss() {
        return this.boxed.getConfigDebugIgnoreFocusLoss();
    }

    @Override
    public void setConfigDebugIgnoreFocusLoss(boolean value) {
        this.boxed.setConfigDebugIgnoreFocusLoss(value);
    }

    @Override
    public boolean getConfigDebugIniSettings() {
        return this.boxed.getConfigDebugIniSettings();
    }

    @Override
    public void setConfigDebugIniSettings(boolean value) {
        this.boxed.setConfigDebugIniSettings(value);
    }

    @Override
    public String getBackendPlatformName() {
        return this.boxed.getBackendPlatformName();
    }

    @Override
    public void setBackendPlatformName(final String value) {
        this.boxed.setBackendPlatformName(value);
    }

    @Override
    public String getBackendRendererName() {
        return this.boxed.getBackendRendererName();
    }

    @Override
    public void setBackendRendererName(final String value) {
        this.boxed.setBackendRendererName(value);
    }

    @Override
    public void setSetClipboardTextFn(final KImStrConsumer setClipboardTextCallback) {
        this.boxed.setSetClipboardTextFn(new ImStrConsumer() {
            @Override
            public void accept(final String str) {
                setClipboardTextCallback.accept(str);
            }
        });
    }

    @Override
    public void setGetClipboardTextFn(final KImStrSupplier getClipboardTextCallback) {
        this.boxed.setGetClipboardTextFn(new ImStrSupplier() {
            @Override
            public String get() {
                return getClipboardTextCallback.get();
            }
        });
    }

    @Override
    public short getPlatformLocaleDecimalPoint() {
        return this.boxed.getPlatformLocaleDecimalPoint();
    }

    @Override
    public void setPlatformLocaleDecimalPoint(short value) {
        this.boxed.setPlatformLocaleDecimalPoint(value);
    }

    @Override
    public void addKeyEvent(int key, boolean down) {
        this.boxed.addKeyEvent(key, down);
    }

    @Override
    public void addKeyAnalogEvent(int key, boolean down, float v) {
        this.boxed.addKeyAnalogEvent(key, down, v);
    }

    @Override
    public void addMousePosEvent(float x, float y) {
        this.boxed.addMousePosEvent(x, y);
    }

    @Override
    public void addMouseButtonEvent(int button, boolean down) {
        this.boxed.addMouseButtonEvent(button, down);
    }

    @Override
    public void addMouseWheelEvent(float whX, float whY) {
        this.boxed.addMouseWheelEvent(whX, whY);
    }

    @Override
    public void addMouseSourceEvent(int source) {
        this.boxed.addMouseSourceEvent(source);
    }

    @Override
    public void addMouseViewportEvent(int id) {
        this.boxed.addMouseViewportEvent(id);
    }

    @Override
    public void addFocusEvent(boolean focused) {
        this.boxed.addFocusEvent(focused);
    }

    @Override
    public void addInputCharacter(int c) {
        this.boxed.addInputCharacter(c);
    }

    @Override
    public void addInputCharacterUTF16(short c) {
        this.boxed.addInputCharacterUTF16(c);
    }

    @Override
    public void addInputCharactersUTF8(final String str) {
        this.boxed.addInputCharactersUTF8(str);
    }

    @Override
    public void setKeyEventNativeData(int key, int nativeKeycode, int nativeScancode) {
        this.boxed.setKeyEventNativeData(key, nativeKeycode, nativeScancode);
    }

    @Override
    public void setKeyEventNativeData(
        int key,
        int nativeKeycode,
        int nativeScancode,
        int nativeLegacyIndex
    ) {
        this.boxed.setKeyEventNativeData(key, nativeKeycode, nativeScancode, nativeLegacyIndex);
    }

    @Override
    public void clearEventsQueue() {
        this.boxed.clearEventsQueue();
    }

    @Override
    public void clearInputKeys() {
        this.boxed.clearInputKeys();
    }

    @Override
    public void clearInputMouse() {
        this.boxed.clearInputMouse();
    }

    @Override
    public boolean getWantCaptureMouse() {
        return this.boxed.getWantCaptureMouse();
    }

    @Override
    public void setWantCaptureMouse(boolean value) {
        this.boxed.setWantCaptureMouse(value);
    }

    @Override
    public boolean getWantCaptureKeyboard() {
        return this.boxed.getWantCaptureKeyboard();
    }

    @Override
    public void setWantCaptureKeyboard(boolean value) {
        this.boxed.setWantCaptureKeyboard(value);
    }

    @Override
    public boolean getWantTextInput() {
        return this.boxed.getWantTextInput();
    }

    @Override
    public void setWantTextInput(boolean value) {
        this.boxed.setWantTextInput(value);
    }

    @Override
    public boolean getWantSetMousePos() {
        return this.boxed.getWantSetMousePos();
    }

    @Override
    public void setWantSetMousePos(boolean value) {
        this.boxed.setWantSetMousePos(value);
    }

    @Override
    public boolean getWantSaveIniSettings() {
        return this.boxed.getWantSaveIniSettings();
    }

    @Override
    public void setWantSaveIniSettings(boolean value) {
        this.boxed.setWantSaveIniSettings(value);
    }

    @Override
    public boolean getNavActive() {
        return this.boxed.getNavActive();
    }

    @Override
    public void setNavActive(boolean value) {
        this.boxed.setNavActive(value);
    }

    @Override
    public boolean getNavVisible() {
        return this.boxed.getNavVisible();
    }

    @Override
    public void setNavVisible(boolean value) {
        this.boxed.setNavVisible(value);
    }

    @Override
    public float getFramerate() {
        return this.boxed.getFramerate();
    }

    @Override
    public void setFramerate(float value) {
        this.boxed.setFramerate(value);
    }

    @Override
    public int getMetricsRenderVertices() {
        return this.boxed.getMetricsRenderVertices();
    }

    @Override
    public void setMetricsRenderVertices(int value) {
        this.boxed.setMetricsRenderVertices(value);
    }

    @Override
    public int getMetricsRenderIndices() {
        return this.boxed.getMetricsRenderIndices();
    }

    @Override
    public void setMetricsRenderIndices(int value) {
        this.boxed.setMetricsRenderIndices(value);
    }

    @Override
    public int getMetricsRenderWindows() {
        return this.boxed.getMetricsRenderWindows();
    }

    @Override
    public void setMetricsRenderWindows(int value) {
        this.boxed.setMetricsRenderWindows(value);
    }

    @Override
    public int getMetricsActiveWindows() {
        return this.boxed.getMetricsActiveWindows();
    }

    @Override
    public void setMetricsActiveWindows(int value) {
        this.boxed.setMetricsActiveWindows(value);
    }

    @Override
    public KVector2f getMouseDelta() {
        return KImGuiSpairUnwrapper.wrap(this.boxed.getMouseDelta());
    }

    @Override
    public void setMouseDelta(final KVector2f value) {
        this.boxed.setMouseDelta(KImGuiSpairWrapper.wrap(value));
    }

    @Override
    public float getMouseDeltaX() {
        return this.boxed.getMouseDeltaX();
    }

    @Override
    public float getMouseDeltaY() {
        return this.boxed.getMouseDeltaY();
    }

    @Override
    public void setMouseDelta(float valueX, float valueY) {
        this.boxed.setMouseDelta(valueX, valueY);
    }

    @Override
    public KImGuiContext getCtx() {
        return KImGuiSpairUnwrapper.wrap(this.boxed.getCtx());
    }

    @Override
    public void setCtx(final KImGuiContext value) {
        this.boxed.setCtx(KImGuiSpairWrapper.wrap(value));
    }

    @Override
    public KVector2f getMousePos() {
        return KImGuiSpairUnwrapper.wrap(this.boxed.getMousePos());
    }

    @Override
    public void setMousePos(final KVector2f value) {
        this.boxed.setMousePos(KImGuiSpairWrapper.wrap(value));
    }

    @Override
    public float getMousePosX() {
        return this.boxed.getMousePosX();
    }

    @Override
    public float getMousePosY() {
        return this.boxed.getMousePosY();
    }

    @Override
    public void setMousePos(float valueX, float valueY) {
        this.boxed.setMousePos(valueX, valueY);
    }

    @Override
    public boolean[] getMouseDown() {
        return this.boxed.getMouseDown();
    }

    @Override
    public void setMouseDown(final boolean[] value) {
        this.boxed.setMouseDown(value);
    }

    @Override
    public boolean getMouseDown(int idx) {
        return this.boxed.getMouseDown(idx);
    }

    @Override
    public void setMouseDown(int idx, boolean value) {
        this.boxed.setMouseDown(idx, value);
    }

    @Override
    public float getMouseWheel() {
        return this.boxed.getMouseWheel();
    }

    @Override
    public void setMouseWheel(float value) {
        this.boxed.setMouseWheel(value);
    }

    @Override
    public float getMouseWheelH() {
        return this.boxed.getMouseWheelH();
    }

    @Override
    public void setMouseWheelH(float value) {
        this.boxed.setMouseWheelH(value);
    }

    @Override
    public int getMouseHoveredViewport() {
        return this.boxed.getMouseHoveredViewport();
    }

    @Override
    public void setMouseHoveredViewport(int value) {
        this.boxed.setMouseHoveredViewport(value);
    }

    @Override
    public boolean getKeyCtrl() {
        return this.boxed.getKeyCtrl();
    }

    @Override
    public void setKeyCtrl(boolean value) {
        this.boxed.setKeyCtrl(value);
    }

    @Override
    public boolean getKeyShift() {
        return this.boxed.getKeyShift();
    }

    @Override
    public void setKeyShift(boolean value) {
        this.boxed.setKeyShift(value);
    }

    @Override
    public boolean getKeyAlt() {
        return this.boxed.getKeyAlt();
    }

    @Override
    public void setKeyAlt(boolean value) {
        this.boxed.setKeyAlt(value);
    }

    @Override
    public boolean getKeySuper() {
        return this.boxed.getKeySuper();
    }

    @Override
    public void setKeySuper(boolean value) {
        this.boxed.setKeySuper(value);
    }

    @Override
    public int getKeyMods() {
        return this.boxed.getKeyMods();
    }

    @Override
    public void setKeyMods(int value) {
        this.boxed.setKeyMods(value);
    }

    @Override
    public KImGuiKeyData[] getKeysData() {
        ImGuiKeyData[] dd = this.boxed.getKeysData();
        KImGuiKeyData[] result = new KImGuiKeyData[dd.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = KImGuiSpairUnwrapper.wrap(dd[i]);
        }
        return result;
    }

    @Override
    public void setKeysData(final KImGuiKeyData[] value) {
        ImGuiKeyData[] data = new ImGuiKeyData[value.length];
        for (int i = 0; i < data.length; i++) {
            data[i] = KImGuiSpairUnboxer.unbox(value[i]);
        }
        this.boxed.setKeysData(data);
    }

    @Override
    public boolean getWantCaptureMouseUnlessPopupClose() {
        return this.boxed.getWantCaptureMouseUnlessPopupClose();
    }

    @Override
    public void setWantCaptureMouseUnlessPopupClose(boolean value) {
        this.boxed.setWantCaptureMouseUnlessPopupClose(value);
    }

    @Override
    public KVector2f getMousePosPrev() {
        return KImGuiSpairUnwrapper.wrap(this.boxed.getMousePosPrev());
    }

    @Override
    public void setMousePosPrev(final KVector2f value) {
        this.boxed.setMousePosPrev(KImGuiSpairWrapper.wrap(value));
    }

    @Override
    public float getMousePosPrevX() {
        return this.boxed.getMousePosPrevX();
    }

    @Override
    public float getMousePosPrevY() {
        return this.boxed.getMousePosPrevY();
    }

    @Override
    public void setMousePosPrev(float valueX, float valueY) {
        this.boxed.setMousePosPrev(valueX, valueY);
    }

    @Override
    public KVector2f[] getMouseClickedPos() {
        ImVec2[] imv2s = this.boxed.getMouseClickedPos();
        KVector2f[] result = new KVector2f[imv2s.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = KImGuiSpairUnwrapper.wrap(imv2s[i]);
        }
        return result;
    }


    @Override
    public void setMouseClickedPos(final KVector2f[] value) {
        ImVec2[] imVec2s = new ImVec2[value.length];
        for (int i = 0; i < imVec2s.length; i++) {
            imVec2s[i] = KImGuiSpairWrapper.wrap(value[i]);
        }
        this.boxed.setMouseClickedPos(imVec2s);
    }

    @Override
    public double[] getMouseClickedTime() {
        return this.boxed.getMouseClickedTime();
    }

    @Override
    public void setMouseClickedTime(final double[] value) {
        this.boxed.setMouseClickedTime(value);
    }

    @Override
    public double getMouseClickedTime(int idx) {
        return this.boxed.getMouseClickedTime(idx);
    }

    @Override
    public void setMouseClickedTime(int idx, double value) {
        this.boxed.setMouseClickedTime(idx, value);
    }

    @Override
    public boolean[] getMouseClicked() {
        return this.boxed.getMouseClicked();
    }

    @Override
    public void setMouseClicked(final boolean[] value) {
        this.boxed.setMouseClicked(value);
    }

    @Override
    public boolean getMouseClicked(int idx) {
        return this.boxed.getMouseClicked(idx);
    }

    @Override
    public void setMouseClicked(int idx, boolean value) {
        this.boxed.setMouseClicked(idx, value);
    }

    @Override
    public boolean[] getMouseDoubleClicked() {
        return this.boxed.getMouseDoubleClicked();
    }

    @Override
    public void setMouseDoubleClicked(final boolean[] value) {
        this.boxed.setMouseDoubleClicked(value);
    }

    @Override
    public boolean getMouseDoubleClicked(int idx) {
        return this.boxed.getMouseDoubleClicked(idx);
    }

    @Override
    public void setMouseDoubleClicked(int idx, boolean value) {
        this.boxed.setMouseDoubleClicked(idx, value);
    }

    @Override
    public int[] getMouseClickedCount() {
        return this.boxed.getMouseClickedCount();
    }

    @Override
    public void setMouseClickedCount(final int[] value) {
        this.boxed.setMouseClickedCount(value);
    }

    @Override
    public int getMouseClickedCount(int idx) {
        return this.boxed.getMouseClickedCount(idx);
    }

    @Override
    public void setMouseClickedCount(int idx, int value) {
        this.boxed.setMouseClickedCount(idx, value);
    }

    @Override
    public int[] getMouseClickedLastCount() {
        return this.boxed.getMouseClickedLastCount();
    }

    @Override
    public void setMouseClickedLastCount(final int[] value) {
        this.boxed.setMouseClickedLastCount(value);
    }

    @Override
    public int getMouseClickedLastCount(int idx) {
        return this.boxed.getMouseClickedLastCount(idx);
    }

    @Override
    public void setMouseClickedLastCount(int idx, int value) {
        this.boxed.setMouseClickedLastCount(idx, value);
    }

    @Override
    public boolean[] getMouseReleased() {
        return this.boxed.getMouseReleased();
    }

    @Override
    public void setMouseReleased(final boolean[] value) {
        this.boxed.setMouseReleased(value);
    }

    @Override
    public boolean getMouseReleased(int idx) {
        return this.boxed.getMouseReleased(idx);
    }

    @Override
    public void setMouseReleased(int idx, boolean value) {
        this.boxed.setMouseReleased(idx, value);
    }

    @Override
    public boolean[] getMouseDownOwned() {
        return this.boxed.getMouseDownOwned();
    }

    @Override
    public void setMouseDownOwned(final boolean[] value) {
        this.boxed.setMouseDownOwned(value);
    }

    @Override
    public boolean getMouseDownOwned(int idx) {
        return this.boxed.getMouseDownOwned(idx);
    }

    @Override
    public void setMouseDownOwned(int idx, boolean value) {
        this.boxed.setMouseDownOwned(idx, value);
    }

    @Override
    public boolean[] getMouseDownOwnedUnlessPopupClose() {
        return this.boxed.getMouseDownOwnedUnlessPopupClose();
    }

    @Override
    public void setMouseDownOwnedUnlessPopupClose(final boolean[] value) {
        this.boxed.setMouseDownOwnedUnlessPopupClose(value);
    }

    @Override
    public boolean getMouseDownOwnedUnlessPopupClose(int idx) {
        return this.boxed.getMouseDownOwnedUnlessPopupClose(idx);
    }

    @Override
    public void setMouseDownOwnedUnlessPopupClose(int idx, boolean value) {
        this.boxed.setMouseDownOwnedUnlessPopupClose(idx, value);
    }

    @Override
    public boolean getMouseWheelRequestAxisSwap() {
        return this.boxed.getMouseWheelRequestAxisSwap();
    }

    @Override
    public void setMouseWheelRequestAxisSwap(boolean value) {
        this.boxed.setMouseWheelRequestAxisSwap(value);
    }

    @Override
    public boolean getMouseCtrlLeftAsRightClick() {
        return this.boxed.getMouseCtrlLeftAsRightClick();
    }

    @Override
    public void setMouseCtrlLeftAsRightClick(boolean value) {
        this.boxed.setMouseCtrlLeftAsRightClick(value);
    }

    @Override
    public float[] getMouseDownDuration() {
        return this.boxed.getMouseDownDuration();
    }

    @Override
    public void setMouseDownDuration(final float[] value) {
        this.boxed.setMouseDownDuration(value);
    }

    @Override
    public float getMouseDownDuration(int idx) {
        return this.boxed.getMouseDownDuration(idx);
    }

    @Override
    public void setMouseDownDuration(int idx, float value) {
        this.boxed.setMouseDownDuration(idx, value);
    }

    @Override
    public float[] getMouseDownDurationPrev() {
        return this.boxed.getMouseDownDurationPrev();
    }

    @Override
    public void setMouseDownDurationPrev(final float[] value) {
        this.boxed.setMouseDownDurationPrev(value);
    }

    @Override
    public float getMouseDownDurationPrev(int idx) {
        return this.boxed.getMouseDownDurationPrev(idx);
    }

    @Override
    public void setMouseDownDurationPrev(int idx, float value) {
        this.boxed.setMouseDownDurationPrev(idx, value);
    }

    @Override
    public KVector2f[] getMouseDragMaxDistanceAbs() {
        ImVec2[] imv2s = this.boxed.getMouseDragMaxDistanceAbs();
        KVector2f[] result = new KVector2f[imv2s.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = KImGuiSpairUnwrapper.wrap(imv2s[i]);
        }
        return result;
    }

    @Override
    public void setMouseDragMaxDistanceAbs(final KVector2f[] value) {
        ImVec2[] imVec2s = new ImVec2[value.length];
        for (int i = 0; i < imVec2s.length; i++) {
            imVec2s[i] = KImGuiSpairWrapper.wrap(value[i]);
        }
        this.boxed.setMouseDragMaxDistanceAbs(imVec2s);
    }

    @Override
    public float[] getMouseDragMaxDistanceSqr() {
        return this.boxed.getMouseDragMaxDistanceSqr();
    }

    @Override
    public void setMouseDragMaxDistanceSqr(final float[] value) {
        this.boxed.setMouseDragMaxDistanceSqr(value);
    }

    @Override
    public float getMouseDragMaxDistanceSqr(int idx) {
        return this.boxed.getMouseDragMaxDistanceSqr(idx);
    }

    @Override
    public void setMouseDragMaxDistanceSqr(int idx, float value) {
        this.boxed.setMouseDragMaxDistanceSqr(idx, value);
    }

    @Override
    public float getPenPressure() {
        return this.boxed.getPenPressure();
    }

    @Override
    public void setPenPressure(float value) {
        this.boxed.setPenPressure(value);
    }

    @Override
    public boolean getAppFocusLost() {
        return this.boxed.getAppFocusLost();
    }

    @Override
    public boolean getAppAcceptingEvents() {
        return this.boxed.getAppAcceptingEvents();
    }

    @Override
    public void setAppAcceptingEvents(boolean acceptingEvents) {
        this.boxed.setAppAcceptingEvents(acceptingEvents);
    }

    @Override
    public short getBackendUsingLegacyKeyArrays() {
        return this.boxed.getBackendUsingLegacyKeyArrays();
    }

    @Override
    public void setBackendUsingLegacyKeyArrays(short value) {
        this.boxed.setBackendUsingLegacyKeyArrays(value);
    }

    @Override
    public boolean getBackendUsingLegacyNavInputArray() {
        return this.boxed.getBackendUsingLegacyNavInputArray();
    }

    @Override
    public void setBackendUsingLegacyNavInputArray(boolean value) {
        this.boxed.setBackendUsingLegacyNavInputArray(value);
    }

    @Override
    public short getInputQueueSurrogate() {
        return this.boxed.getInputQueueSurrogate();
    }

    @Override
    public void setInputQueueSurrogate(short value) {
        this.boxed.setInputQueueSurrogate(value);
    }
}
