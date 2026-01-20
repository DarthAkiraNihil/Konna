package io.github.darthakiranihil.konna.libfrontend.imgui;

import io.github.darthakiranihil.konna.libfrontend.imgui.func.KImStrConsumer;
import io.github.darthakiranihil.konna.libfrontend.imgui.func.KImStrSupplier;
import io.github.darthakiranihil.konna.core.struct.KVector2f;

public interface KImGuiIo {

    public int getConfigFlags();
    public void setConfigFlags(int value);
    public void addConfigFlags(int flags);
    public void removeConfigFlags(int flags);
    public boolean hasConfigFlags(int flags);
    public int getBackendFlags();
    public void setBackendFlags(int value);
    public void addBackendFlags(int flags);
    public void removeBackendFlags(int flags);
    public boolean hasBackendFlags(int flags);
    public KVector2f getDisplaySize();
    public void setDisplaySize(KVector2f value);
    public float getDisplaySizeX();
    public float getDisplaySizeY();
    public void getDisplaySize(KVector2f dst);
    public void setDisplaySize(float valueX, float valueY);
    public float getDeltaTime();
    public void setDeltaTime(float value);
    public float getIniSavingRate();
    public void setIniSavingRate(float value);
    public String getIniFilename();
    public void setIniFilename(String value);
    public String getLogFilename();
    public void setLogFilename(String value);
    public KImFontAtlas getFonts();
    public void setFonts(KImFontAtlas value);
    public float getFontGlobalScale();
    public void setFontGlobalScale(float value);
    public boolean getFontAllowUserScaling();
    public void setFontAllowUserScaling(boolean value);
    public KImFont getFontDefault();
    public void setFontDefault(KImFont value);
    public KVector2f getDisplayFramebufferScale();
    public void setDisplayFramebufferScale(KVector2f value);
    public float getDisplayFramebufferScaleX();
    public float getDisplayFramebufferScaleY();
    public void getDisplayFramebufferScale(KVector2f dst);
    public void setDisplayFramebufferScale(float valueX, float valueY);
    public boolean getConfigDockingNoSplit();
    public void setConfigDockingNoSplit(boolean value);
    public boolean getConfigDockingWithShift();
    public void setConfigDockingWithShift(boolean value);
    public boolean getConfigDockingAlwaysTabBar();
    public void setConfigDockingAlwaysTabBar(boolean value);
    public boolean getConfigDockingTransparentPayload();
    public void setConfigDockingTransparentPayload(boolean value);
    public boolean getConfigViewportsNoAutoMerge();
    public void setConfigViewportsNoAutoMerge(boolean value);
    public boolean getConfigViewportsNoTaskBarIcon();
    public void setConfigViewportsNoTaskBarIcon(boolean value);
    public boolean getConfigViewportsNoDecoration();
    public void setConfigViewportsNoDecoration(boolean value);
    public boolean getConfigViewportsNoDefaultParent();
    public void setConfigViewportsNoDefaultParent(boolean value);
    public boolean getMouseDrawCursor();
    public void setMouseDrawCursor(boolean value);
    public boolean getConfigMacOSXBehaviors();
    public void setConfigMacOSXBehaviors(boolean value);
    public boolean getConfigInputTrickleEventQueue();
    public void setConfigInputTrickleEventQueue(boolean value);

    public boolean getConfigInputTextCursorBlink();

    public void setConfigInputTextCursorBlink(boolean value);

    public boolean getConfigInputTextEnterKeepActive();

    public void setConfigInputTextEnterKeepActive(boolean value);

    public boolean getConfigDragClickToInputText();

    public void setConfigDragClickToInputText(boolean value);

    public boolean getConfigWindowsResizeFromEdges();

    public void setConfigWindowsResizeFromEdges(boolean value);

    public boolean getConfigWindowsMoveFromTitleBarOnly();

    public void setConfigWindowsMoveFromTitleBarOnly(boolean value);

    public boolean getConfigMemoryCompactTimer();

    public void setConfigMemoryCompactTimer(boolean value);

    public float getMouseDoubleClickTime();

    public void setMouseDoubleClickTime(float value);

    public float getMouseDoubleClickMaxDist();

    public void setMouseDoubleClickMaxDist(float value);

    public float getMouseDragThreshold();

    public void setMouseDragThreshold(float value);

    public float getKeyRepeatDelay();

    public void setKeyRepeatDelay(float value);

    public float getKeyRepeatRate();

    public void setKeyRepeatRate(float value);

    public boolean getConfigDebugIsDebuggerPresent();

    public void setConfigDebugIsDebuggerPresent(boolean value);

    public boolean getConfigDebugBeginReturnValueOnce();

    public void setConfigDebugBeginReturnValueOnce(boolean value);

    public boolean getConfigDebugBeginReturnValueLoop();

    public void setConfigDebugBeginReturnValueLoop(boolean value);

    public boolean getConfigDebugIgnoreFocusLoss();

    public void setConfigDebugIgnoreFocusLoss(boolean value);

    public boolean getConfigDebugIniSettings();

    public void setConfigDebugIniSettings(boolean value);

    public String getBackendPlatformName();

    public void setBackendPlatformName(String value);

    public String getBackendRendererName();

    public void setBackendRendererName(String value);

    public void setSetClipboardTextFn(KImStrConsumer setClipboardTextCallback);

    public void setGetClipboardTextFn(KImStrSupplier getClipboardTextCallback);

    public short getPlatformLocaleDecimalPoint();

    public void setPlatformLocaleDecimalPoint(short value);

    public void addKeyEvent(int key, boolean down);

    public void addKeyAnalogEvent(int key, boolean down, float v);

    public void addMousePosEvent(float x, float y);

    public void addMouseButtonEvent(int button, boolean down);

    public void addMouseWheelEvent(float whX, float whY);

    public void addMouseSourceEvent(int source);

    public void addMouseViewportEvent(int id);

    public void addFocusEvent(boolean focused);

    public void addInputCharacter(int c);

    public void addInputCharacterUTF16(short c);

    public void addInputCharactersUTF8(String str);

    public void setKeyEventNativeData(int key, int nativeKeycode, int nativeScancode);

    public void setKeyEventNativeData(
        int key,
        int nativeKeycode,
        int nativeScancode,
        int nativeLegacyIndex
    );

    public void clearEventsQueue();


    public void clearInputKeys();


    public void clearInputMouse();


    public boolean getWantCaptureMouse();

    public void setWantCaptureMouse(boolean value);


    public boolean getWantCaptureKeyboard();

    public void setWantCaptureKeyboard(boolean value);


    public boolean getWantTextInput();

    public void setWantTextInput(boolean value);


    public boolean getWantSetMousePos();

    public void setWantSetMousePos(boolean value);


    public boolean getWantSaveIniSettings();

    public void setWantSaveIniSettings(boolean value);


    public boolean getNavActive();

    public void setNavActive(boolean value);


    public boolean getNavVisible();

    public void setNavVisible(boolean value);


    public float getFramerate();

    public void setFramerate(float value);


    public int getMetricsRenderVertices();

    public void setMetricsRenderVertices(int value);


    public int getMetricsRenderIndices();

    public void setMetricsRenderIndices(int value);


    public int getMetricsRenderWindows();

    public void setMetricsRenderWindows(int value);


    public int getMetricsActiveWindows();

    public void setMetricsActiveWindows(int value);


    public KVector2f getMouseDelta();

    public void setMouseDelta(KVector2f value);

    public float getMouseDeltaX();

    public float getMouseDeltaY();

    public void getMouseDelta(KVector2f dst);

    public void setMouseDelta(float valueX, float valueY);

    public KImGuiContext getCtx() ;

    public void setCtx(KImGuiContext value);

    public KVector2f getMousePos();

    public void setMousePos(KVector2f value);

    public float getMousePosX();

    public float getMousePosY();

    public void getMousePos(KVector2f dst);

    public void setMousePos(float valueX, float valueY);

    public boolean[] getMouseDown();

    public void setMouseDown(boolean[] value);

    public boolean getMouseDown(int idx);

    public void setMouseDown(int idx, boolean value);

    public float getMouseWheel();

    public void setMouseWheel(float value);

    public float getMouseWheelH();

    public void setMouseWheelH(float value);

    public int getMouseHoveredViewport();

    public void setMouseHoveredViewport(int value);

    public boolean getKeyCtrl();

    public void setKeyCtrl(boolean value);

    public boolean getKeyShift();

    public void setKeyShift(boolean value);

    public boolean getKeyAlt();

    public void setKeyAlt(boolean value);

    public boolean getKeySuper();

    public void setKeySuper(boolean value);

    public int getKeyMods();

    public void setKeyMods(int value);

    public KImGuiKeyData[] getKeysData();

    public void setKeysData(KImGuiKeyData[] value);

    public boolean getWantCaptureMouseUnlessPopupClose();

    public void setWantCaptureMouseUnlessPopupClose(boolean value);

    public KVector2f getMousePosPrev();

    public void setMousePosPrev(KVector2f value);

    public float getMousePosPrevX();

    public float getMousePosPrevY();

    public void getMousePosPrev(KVector2f dst);

    public void setMousePosPrev(float valueX, float valueY);

    public KVector2f[] getMouseClickedPos();

    public void setMouseClickedPos(KVector2f[] value);

    public double[] getMouseClickedTime();

    public void setMouseClickedTime(double[] value);

    public double getMouseClickedTime(int idx);

    public void setMouseClickedTime(int idx, double value);

    public boolean[] getMouseClicked();

    public void setMouseClicked(boolean[] value);

    public boolean getMouseClicked(int idx);

    public void setMouseClicked(int idx, boolean value);

    public boolean[] getMouseDoubleClicked();

    public void setMouseDoubleClicked(boolean[] value);

    public boolean getMouseDoubleClicked(int idx);

    public void setMouseDoubleClicked(int idx, boolean value);

    public int[] getMouseClickedCount();

    public void setMouseClickedCount(int[] value);

    public int getMouseClickedCount(int idx);

    public void setMouseClickedCount(int idx, int value);

    public int[] getMouseClickedLastCount();

    public void setMouseClickedLastCount(int[] value);

    public int getMouseClickedLastCount(int idx);

    public void setMouseClickedLastCount(int idx, int value);

    public boolean[] getMouseReleased();

    public void setMouseReleased(boolean[] value);

    public boolean getMouseReleased(int idx);

    public void setMouseReleased(int idx, boolean value);

    public boolean[] getMouseDownOwned();

    public void setMouseDownOwned(boolean[] value);

    public boolean getMouseDownOwned(int idx);

    public void setMouseDownOwned(int idx, boolean value);

    public boolean[] getMouseDownOwnedUnlessPopupClose();

    public void setMouseDownOwnedUnlessPopupClose(boolean[] value);

    public boolean getMouseDownOwnedUnlessPopupClose(int idx);

    public void setMouseDownOwnedUnlessPopupClose(int idx, boolean value);

    public boolean getMouseWheelRequestAxisSwap();

    public void setMouseWheelRequestAxisSwap(boolean value);

    public boolean getMouseCtrlLeftAsRightClick();

    public void setMouseCtrlLeftAsRightClick(boolean value);

    public float[] getMouseDownDuration();

    public void setMouseDownDuration(float[] value);

    public float getMouseDownDuration(int idx);

    public void setMouseDownDuration(int idx, float value);

    public float[] getMouseDownDurationPrev();

    public void setMouseDownDurationPrev(float[] value);

    public float getMouseDownDurationPrev(int idx);

    public void setMouseDownDurationPrev(int idx, float value);

    public KVector2f[] getMouseDragMaxDistanceAbs();

    public void setMouseDragMaxDistanceAbs(KVector2f[] value);

    public float[] getMouseDragMaxDistanceSqr();

    public void setMouseDragMaxDistanceSqr(float[] value);

    public float getMouseDragMaxDistanceSqr(int idx);

    public void setMouseDragMaxDistanceSqr(int idx, float value);

    public float getPenPressure();

    public void setPenPressure(float value);

    public boolean getAppFocusLost();

    public boolean getAppAcceptingEvents();

    public void setAppAcceptingEvents(boolean acceptingEvents);

    public short getBackendUsingLegacyKeyArrays();

    public void setBackendUsingLegacyKeyArrays(short value);

    public boolean getBackendUsingLegacyNavInputArray();

    public void setBackendUsingLegacyNavInputArray(boolean value);

    public short getInputQueueSurrogate();

    public void setInputQueueSurrogate(short value);

}
