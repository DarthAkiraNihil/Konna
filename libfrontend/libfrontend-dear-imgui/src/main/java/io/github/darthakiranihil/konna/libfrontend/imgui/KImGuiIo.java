package io.github.darthakiranihil.konna.libfrontend.imgui;

import io.github.darthakiranihil.konna.libfrontend.imgui.func.KImStrConsumer;
import io.github.darthakiranihil.konna.libfrontend.imgui.func.KImStrSupplier;
import io.github.darthakiranihil.konna.core.struct.KVector2f;

public interface KImGuiIo {

    public int getConfigFlags();
    public void setConfigFlags(final int value);
    public void addConfigFlags(final int flags);
    public void removeConfigFlags(final int flags);
    public boolean hasConfigFlags(final int flags);
    public int getBackendFlags();
    public void setBackendFlags(final int value);
    public void addBackendFlags(final int flags);
    public void removeBackendFlags(final int flags);
    public boolean hasBackendFlags(final int flags);
    public KVector2f getDisplaySize();
    public void setDisplaySize(final KVector2f value);
    public float getDisplaySizeX();
    public float getDisplaySizeY();
    public void getDisplaySize(final KVector2f dst);
    public void setDisplaySize(final float valueX, final float valueY);
    public float getDeltaTime();
    public void setDeltaTime(final float value);
    public float getIniSavingRate();
    public void setIniSavingRate(final float value);
    public String getIniFilename();
    public void setIniFilename(final String value);
    public String getLogFilename();
    public void setLogFilename(final String value);
    public KImFontAtlas getFonts();
    public void setFonts(final KImFontAtlas value);
    public float getFontGlobalScale();
    public void setFontGlobalScale(final float value);
    public boolean getFontAllowUserScaling();
    public void setFontAllowUserScaling(final boolean value);
    public KImFont getFontDefault();
    public void setFontDefault(final KImFont value);
    public KVector2f getDisplayFramebufferScale();
    public void setDisplayFramebufferScale(final KVector2f value);
    public float getDisplayFramebufferScaleX();
    public float getDisplayFramebufferScaleY();
    public void getDisplayFramebufferScale(final KVector2f dst);
    public void setDisplayFramebufferScale(final float valueX, final float valueY);
    public boolean getConfigDockingNoSplit();
    public void setConfigDockingNoSplit(final boolean value);
    public boolean getConfigDockingWithShift();
    public void setConfigDockingWithShift(final boolean value);
    public boolean getConfigDockingAlwaysTabBar();
    public void setConfigDockingAlwaysTabBar(final boolean value);
    public boolean getConfigDockingTransparentPayload();
    public void setConfigDockingTransparentPayload(final boolean value);
    public boolean getConfigViewportsNoAutoMerge();
    public void setConfigViewportsNoAutoMerge(final boolean value);
    public boolean getConfigViewportsNoTaskBarIcon();
    public void setConfigViewportsNoTaskBarIcon(final boolean value);
    public boolean getConfigViewportsNoDecoration();
    public void setConfigViewportsNoDecoration(final boolean value);
    public boolean getConfigViewportsNoDefaultParent();
    public void setConfigViewportsNoDefaultParent(final boolean value);
    public boolean getMouseDrawCursor();
    public void setMouseDrawCursor(final boolean value);
    public boolean getConfigMacOSXBehaviors();
    public void setConfigMacOSXBehaviors(final boolean value);
    public boolean getConfigInputTrickleEventQueue();
    public void setConfigInputTrickleEventQueue(final boolean value);

    public boolean getConfigInputTextCursorBlink();

    public void setConfigInputTextCursorBlink(final boolean value);

    public boolean getConfigInputTextEnterKeepActive();

    public void setConfigInputTextEnterKeepActive(final boolean value);

    public boolean getConfigDragClickToInputText();

    public void setConfigDragClickToInputText(final boolean value);

    public boolean getConfigWindowsResizeFromEdges();

    public void setConfigWindowsResizeFromEdges(final boolean value);

    public boolean getConfigWindowsMoveFromTitleBarOnly();

    public void setConfigWindowsMoveFromTitleBarOnly(final boolean value);

    public boolean getConfigMemoryCompactTimer();

    public void setConfigMemoryCompactTimer(final boolean value);

    public float getMouseDoubleClickTime();

    public void setMouseDoubleClickTime(final float value);

    public float getMouseDoubleClickMaxDist();

    public void setMouseDoubleClickMaxDist(final float value);

    public float getMouseDragThreshold();

    public void setMouseDragThreshold(final float value);

    public float getKeyRepeatDelay();

    public void setKeyRepeatDelay(final float value);

    public float getKeyRepeatRate();

    public void setKeyRepeatRate(final float value);

    public boolean getConfigDebugIsDebuggerPresent();

    public void setConfigDebugIsDebuggerPresent(final boolean value);

    public boolean getConfigDebugBeginReturnValueOnce();

    public void setConfigDebugBeginReturnValueOnce(final boolean value);

    public boolean getConfigDebugBeginReturnValueLoop();

    public void setConfigDebugBeginReturnValueLoop(final boolean value);

    public boolean getConfigDebugIgnoreFocusLoss();

    public void setConfigDebugIgnoreFocusLoss(final boolean value);

    public boolean getConfigDebugIniSettings();

    public void setConfigDebugIniSettings(final boolean value);

    public String getBackendPlatformName();

    public void setBackendPlatformName(final String value);

    public String getBackendRendererName();

    public void setBackendRendererName(final String value);

    public void setSetClipboardTextFn(KImStrConsumer setClipboardTextCallback);

    public void setGetClipboardTextFn(KImStrSupplier getClipboardTextCallback);

    public short getPlatformLocaleDecimalPoint();

    public void setPlatformLocaleDecimalPoint(final short value);

    public void addKeyEvent(final int key, final boolean down);

    public void addKeyAnalogEvent(final int key, final boolean down, final float v);

    public void addMousePosEvent(final float x, final float y);

    public void addMouseButtonEvent(final int button, final boolean down);

    public void addMouseWheelEvent(final float whX, final float whY);

    public void addMouseSourceEvent(final int source);

    public void addMouseViewportEvent(final int id);

    public void addFocusEvent(final boolean focused);

    public void addInputCharacter(final int c);

    public void addInputCharacterUTF16(final short c);

    public void addInputCharactersUTF8(final String str);

    public void setKeyEventNativeData(final int key, final int nativeKeycode, final int nativeScancode);

    public void setKeyEventNativeData(
        final int key,
        final int nativeKeycode,
        final int nativeScancode,
        final int nativeLegacyIndex
    );

    public void clearEventsQueue();


    public void clearInputKeys();


    public void clearInputMouse();


    public boolean getWantCaptureMouse();

    public void setWantCaptureMouse(final boolean value);


    public boolean getWantCaptureKeyboard();

    public void setWantCaptureKeyboard(final boolean value);


    public boolean getWantTextInput();

    public void setWantTextInput(final boolean value);


    public boolean getWantSetMousePos();

    public void setWantSetMousePos(final boolean value);


    public boolean getWantSaveIniSettings();

    public void setWantSaveIniSettings(final boolean value);


    public boolean getNavActive();

    public void setNavActive(final boolean value);


    public boolean getNavVisible();

    public void setNavVisible(final boolean value);


    public float getFramerate();

    public void setFramerate(final float value);


    public int getMetricsRenderVertices();

    public void setMetricsRenderVertices(final int value);


    public int getMetricsRenderIndices();

    public void setMetricsRenderIndices(final int value);


    public int getMetricsRenderWindows();

    public void setMetricsRenderWindows(final int value);


    public int getMetricsActiveWindows();

    public void setMetricsActiveWindows(final int value);


    public KVector2f getMouseDelta();

    public void setMouseDelta(final KVector2f value);

    public float getMouseDeltaX();

    public float getMouseDeltaY();

    public void getMouseDelta(final KVector2f dst);

    public void setMouseDelta(final float valueX, final float valueY);

    public KImGuiContext getCtx() ;

    public void setCtx(final KImGuiContext value);

    public KVector2f getMousePos();

    public void setMousePos(final KVector2f value);

    public float getMousePosX();

    public float getMousePosY();

    public void getMousePos(final KVector2f dst);

    public void setMousePos(final float valueX, final float valueY);

    public boolean[] getMouseDown();

    public void setMouseDown(final boolean[] value);

    public boolean getMouseDown(final int idx);

    public void setMouseDown(final int idx, final boolean value);

    public float getMouseWheel();

    public void setMouseWheel(final float value);

    public float getMouseWheelH();

    public void setMouseWheelH(final float value);

    public int getMouseHoveredViewport();

    public void setMouseHoveredViewport(final int value);

    public boolean getKeyCtrl();

    public void setKeyCtrl(final boolean value);

    public boolean getKeyShift();

    public void setKeyShift(final boolean value);

    public boolean getKeyAlt();

    public void setKeyAlt(final boolean value);

    public boolean getKeySuper();

    public void setKeySuper(final boolean value);

    public int getKeyMods();

    public void setKeyMods(final int value);

    public KImGuiKeyData[] getKeysData();

    public void setKeysData(final KImGuiKeyData[] value);

    public boolean getWantCaptureMouseUnlessPopupClose();

    public void setWantCaptureMouseUnlessPopupClose(final boolean value);

    public KVector2f getMousePosPrev();

    public void setMousePosPrev(final KVector2f value);

    public float getMousePosPrevX();

    public float getMousePosPrevY();

    public void getMousePosPrev(final KVector2f dst);

    public void setMousePosPrev(final float valueX, final float valueY);

    public KVector2f[] getMouseClickedPos();

    public void setMouseClickedPos(final KVector2f[] value);

    public double[] getMouseClickedTime();

    public void setMouseClickedTime(final double[] value);

    public double getMouseClickedTime(final int idx);

    public void setMouseClickedTime(final int idx, final double value);

    public boolean[] getMouseClicked();

    public void setMouseClicked(final boolean[] value);

    public boolean getMouseClicked(final int idx);

    public void setMouseClicked(final int idx, final boolean value);

    public boolean[] getMouseDoubleClicked();

    public void setMouseDoubleClicked(final boolean[] value);

    public boolean getMouseDoubleClicked(final int idx);

    public void setMouseDoubleClicked(final int idx, final boolean value);

    public int[] getMouseClickedCount();

    public void setMouseClickedCount(final int[] value);

    public int getMouseClickedCount(final int idx);

    public void setMouseClickedCount(final int idx, final int value);

    public int[] getMouseClickedLastCount();

    public void setMouseClickedLastCount(final int[] value);

    public int getMouseClickedLastCount(final int idx);

    public void setMouseClickedLastCount(final int idx, final int value);

    public boolean[] getMouseReleased();

    public void setMouseReleased(final boolean[] value);

    public boolean getMouseReleased(final int idx);

    public void setMouseReleased(final int idx, final boolean value);

    public boolean[] getMouseDownOwned();

    public void setMouseDownOwned(final boolean[] value);

    public boolean getMouseDownOwned(final int idx);

    public void setMouseDownOwned(final int idx, final boolean value);

    public boolean[] getMouseDownOwnedUnlessPopupClose();

    public void setMouseDownOwnedUnlessPopupClose(final boolean[] value);

    public boolean getMouseDownOwnedUnlessPopupClose(final int idx);

    public void setMouseDownOwnedUnlessPopupClose(final int idx, final boolean value);

    public boolean getMouseWheelRequestAxisSwap();

    public void setMouseWheelRequestAxisSwap(final boolean value);

    public boolean getMouseCtrlLeftAsRightClick();

    public void setMouseCtrlLeftAsRightClick(final boolean value);

    public float[] getMouseDownDuration();

    public void setMouseDownDuration(final float[] value);

    public float getMouseDownDuration(final int idx);

    public void setMouseDownDuration(final int idx, final float value);

    public float[] getMouseDownDurationPrev();

    public void setMouseDownDurationPrev(final float[] value);

    public float getMouseDownDurationPrev(final int idx);

    public void setMouseDownDurationPrev(final int idx, final float value);

    public KVector2f[] getMouseDragMaxDistanceAbs();

    public void setMouseDragMaxDistanceAbs(final KVector2f[] value);

    public float[] getMouseDragMaxDistanceSqr();

    public void setMouseDragMaxDistanceSqr(final float[] value);

    public float getMouseDragMaxDistanceSqr(final int idx);

    public void setMouseDragMaxDistanceSqr(final int idx, final float value);

    public float getPenPressure();

    public void setPenPressure(final float value);

    public boolean getAppFocusLost();

    public boolean getAppAcceptingEvents();

    public void setAppAcceptingEvents(final boolean acceptingEvents);

    public short getBackendUsingLegacyKeyArrays();

    public void setBackendUsingLegacyKeyArrays(final short value);

    public boolean getBackendUsingLegacyNavInputArray();

    public void setBackendUsingLegacyNavInputArray(final boolean value);

    public short getInputQueueSurrogate();

    public void setInputQueueSurrogate(final short value);

}
