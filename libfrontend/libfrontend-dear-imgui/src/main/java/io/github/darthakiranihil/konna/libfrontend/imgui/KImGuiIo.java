package io.github.darthakiranihil.konna.libfrontend.imgui;

import io.github.darthakiranihil.konna.libfrontend.imgui.func.KImStrConsumer;
import io.github.darthakiranihil.konna.libfrontend.imgui.func.KImStrSupplier;
import io.github.darthakiranihil.konna.core.struct.KVector2f;
import org.jspecify.annotations.Nullable;

/**
 * Interface representing ImGuiIO of Dear ImGui.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public interface KImGuiIo {

    int getConfigFlags();
    void setConfigFlags(int value);
    void addConfigFlags(int flags);
    void removeConfigFlags(int flags);
    boolean hasConfigFlags(int flags);
    int getBackendFlags();
    void setBackendFlags(int value);
    void addBackendFlags(int flags);
    void removeBackendFlags(int flags);
    boolean hasBackendFlags(int flags);
    KVector2f getDisplaySize();
    void setDisplaySize(KVector2f value);
    float getDisplaySizeX();
    float getDisplaySizeY();
    // void getDisplaySize(KVector2f dst);
    void setDisplaySize(float valueX, float valueY);
    float getDeltaTime();
    void setDeltaTime(float value);
    float getIniSavingRate();
    void setIniSavingRate(float value);
    String getIniFilename();
    void setIniFilename(@Nullable String value);
    String getLogFilename();
    void setLogFilename(String value);
    KImFontAtlas getFonts();
    void setFonts(KImFontAtlas value);
    float getFontGlobalScale();
    void setFontGlobalScale(float value);
    boolean getFontAllowUserScaling();
    void setFontAllowUserScaling(boolean value);
    KImFont getFontDefault();
    void setFontDefault(KImFont value);
    KVector2f getDisplayFramebufferScale();
    void setDisplayFramebufferScale(KVector2f value);
    float getDisplayFramebufferScaleX();
    float getDisplayFramebufferScaleY();
    // void getDisplayFramebufferScale(KVector2f dst);
    void setDisplayFramebufferScale(float valueX, float valueY);
    boolean getConfigDockingNoSplit();
    void setConfigDockingNoSplit(boolean value);
    boolean getConfigDockingWithShift();
    void setConfigDockingWithShift(boolean value);
    boolean getConfigDockingAlwaysTabBar();
    void setConfigDockingAlwaysTabBar(boolean value);
    boolean getConfigDockingTransparentPayload();
    void setConfigDockingTransparentPayload(boolean value);
    boolean getConfigViewportsNoAutoMerge();
    void setConfigViewportsNoAutoMerge(boolean value);
    boolean getConfigViewportsNoTaskBarIcon();
    void setConfigViewportsNoTaskBarIcon(boolean value);
    boolean getConfigViewportsNoDecoration();
    void setConfigViewportsNoDecoration(boolean value);
    boolean getConfigViewportsNoDefaultParent();
    void setConfigViewportsNoDefaultParent(boolean value);
    boolean getMouseDrawCursor();
    void setMouseDrawCursor(boolean value);
    boolean getConfigMacOSXBehaviors();
    void setConfigMacOSXBehaviors(boolean value);
    boolean getConfigInputTrickleEventQueue();
    void setConfigInputTrickleEventQueue(boolean value);

    boolean getConfigInputTextCursorBlink();

    void setConfigInputTextCursorBlink(boolean value);

    boolean getConfigInputTextEnterKeepActive();

    void setConfigInputTextEnterKeepActive(boolean value);

    boolean getConfigDragClickToInputText();

    void setConfigDragClickToInputText(boolean value);

    boolean getConfigWindowsResizeFromEdges();

    void setConfigWindowsResizeFromEdges(boolean value);

    boolean getConfigWindowsMoveFromTitleBarOnly();

    void setConfigWindowsMoveFromTitleBarOnly(boolean value);

    boolean getConfigMemoryCompactTimer();

    void setConfigMemoryCompactTimer(boolean value);

    float getMouseDoubleClickTime();

    void setMouseDoubleClickTime(float value);

    float getMouseDoubleClickMaxDist();

    void setMouseDoubleClickMaxDist(float value);

    float getMouseDragThreshold();

    void setMouseDragThreshold(float value);

    float getKeyRepeatDelay();

    void setKeyRepeatDelay(float value);

    float getKeyRepeatRate();

    void setKeyRepeatRate(float value);

    boolean getConfigDebugIsDebuggerPresent();

    void setConfigDebugIsDebuggerPresent(boolean value);

    boolean getConfigDebugBeginReturnValueOnce();

    void setConfigDebugBeginReturnValueOnce(boolean value);

    boolean getConfigDebugBeginReturnValueLoop();

    void setConfigDebugBeginReturnValueLoop(boolean value);

    boolean getConfigDebugIgnoreFocusLoss();

    void setConfigDebugIgnoreFocusLoss(boolean value);

    boolean getConfigDebugIniSettings();

    void setConfigDebugIniSettings(boolean value);

    String getBackendPlatformName();

    void setBackendPlatformName(String value);

    String getBackendRendererName();

    void setBackendRendererName(String value);

    void setSetClipboardTextFn(KImStrConsumer setClipboardTextCallback);

    void setGetClipboardTextFn(KImStrSupplier getClipboardTextCallback);

    short getPlatformLocaleDecimalPoint();

    void setPlatformLocaleDecimalPoint(short value);

    void addKeyEvent(int key, boolean down);

    void addKeyAnalogEvent(int key, boolean down, float v);

    void addMousePosEvent(float x, float y);

    void addMouseButtonEvent(int button, boolean down);

    void addMouseWheelEvent(float whX, float whY);

    void addMouseSourceEvent(int source);

    void addMouseViewportEvent(int id);

    void addFocusEvent(boolean focused);

    void addInputCharacter(int c);

    void addInputCharacterUTF16(short c);

    void addInputCharactersUTF8(String str);

    void setKeyEventNativeData(int key, int nativeKeycode, int nativeScancode);

    void setKeyEventNativeData(
        int key,
        int nativeKeycode,
        int nativeScancode,
        int nativeLegacyIndex
    );

    void clearEventsQueue();


    void clearInputKeys();


    void clearInputMouse();


    boolean getWantCaptureMouse();

    void setWantCaptureMouse(boolean value);


    boolean getWantCaptureKeyboard();

    void setWantCaptureKeyboard(boolean value);


    boolean getWantTextInput();

    void setWantTextInput(boolean value);


    boolean getWantSetMousePos();

    void setWantSetMousePos(boolean value);


    boolean getWantSaveIniSettings();

    void setWantSaveIniSettings(boolean value);


    boolean getNavActive();

    void setNavActive(boolean value);


    boolean getNavVisible();

    void setNavVisible(boolean value);


    float getFramerate();

    void setFramerate(float value);


    int getMetricsRenderVertices();

    void setMetricsRenderVertices(int value);


    int getMetricsRenderIndices();

    void setMetricsRenderIndices(int value);


    int getMetricsRenderWindows();

    void setMetricsRenderWindows(int value);


    int getMetricsActiveWindows();

    void setMetricsActiveWindows(int value);


    KVector2f getMouseDelta();

    void setMouseDelta(KVector2f value);

    float getMouseDeltaX();

    float getMouseDeltaY();

    // void getMouseDelta(KVector2f dst);

    void setMouseDelta(float valueX, float valueY);

    KImGuiContext getCtx();

    void setCtx(KImGuiContext value);

    KVector2f getMousePos();

    void setMousePos(KVector2f value);

    float getMousePosX();

    float getMousePosY();

    // void getMousePos(KVector2f dst);

    void setMousePos(float valueX, float valueY);

    boolean[] getMouseDown();

    void setMouseDown(boolean[] value);

    boolean getMouseDown(int idx);

    void setMouseDown(int idx, boolean value);

    float getMouseWheel();

    void setMouseWheel(float value);

    float getMouseWheelH();

    void setMouseWheelH(float value);

    int getMouseHoveredViewport();

    void setMouseHoveredViewport(int value);

    boolean getKeyCtrl();

    void setKeyCtrl(boolean value);

    boolean getKeyShift();

    void setKeyShift(boolean value);

    boolean getKeyAlt();

    void setKeyAlt(boolean value);

    boolean getKeySuper();

    void setKeySuper(boolean value);

    int getKeyMods();

    void setKeyMods(int value);

    KImGuiKeyData[] getKeysData();

    void setKeysData(KImGuiKeyData[] value);

    boolean getWantCaptureMouseUnlessPopupClose();

    void setWantCaptureMouseUnlessPopupClose(boolean value);

    KVector2f getMousePosPrev();

    void setMousePosPrev(KVector2f value);

    float getMousePosPrevX();

    float getMousePosPrevY();

    // void getMousePosPrev(KVector2f dst);

    void setMousePosPrev(float valueX, float valueY);

    KVector2f[] getMouseClickedPos();

    void setMouseClickedPos(KVector2f[] value);

    double[] getMouseClickedTime();

    void setMouseClickedTime(double[] value);

    double getMouseClickedTime(int idx);

    void setMouseClickedTime(int idx, double value);

    boolean[] getMouseClicked();

    void setMouseClicked(boolean[] value);

    boolean getMouseClicked(int idx);

    void setMouseClicked(int idx, boolean value);

    boolean[] getMouseDoubleClicked();

    void setMouseDoubleClicked(boolean[] value);

    boolean getMouseDoubleClicked(int idx);

    void setMouseDoubleClicked(int idx, boolean value);

    int[] getMouseClickedCount();

    void setMouseClickedCount(int[] value);

    int getMouseClickedCount(int idx);

    void setMouseClickedCount(int idx, int value);

    int[] getMouseClickedLastCount();

    void setMouseClickedLastCount(int[] value);

    int getMouseClickedLastCount(int idx);

    void setMouseClickedLastCount(int idx, int value);

    boolean[] getMouseReleased();

    void setMouseReleased(boolean[] value);

    boolean getMouseReleased(int idx);

    void setMouseReleased(int idx, boolean value);

    boolean[] getMouseDownOwned();

    void setMouseDownOwned(boolean[] value);

    boolean getMouseDownOwned(int idx);

    void setMouseDownOwned(int idx, boolean value);

    boolean[] getMouseDownOwnedUnlessPopupClose();

    void setMouseDownOwnedUnlessPopupClose(boolean[] value);

    boolean getMouseDownOwnedUnlessPopupClose(int idx);

    void setMouseDownOwnedUnlessPopupClose(int idx, boolean value);

    boolean getMouseWheelRequestAxisSwap();

    void setMouseWheelRequestAxisSwap(boolean value);

    boolean getMouseCtrlLeftAsRightClick();

    void setMouseCtrlLeftAsRightClick(boolean value);

    float[] getMouseDownDuration();

    void setMouseDownDuration(float[] value);

    float getMouseDownDuration(int idx);

    void setMouseDownDuration(int idx, float value);

    float[] getMouseDownDurationPrev();

    void setMouseDownDurationPrev(float[] value);

    float getMouseDownDurationPrev(int idx);

    void setMouseDownDurationPrev(int idx, float value);

    KVector2f[] getMouseDragMaxDistanceAbs();

    void setMouseDragMaxDistanceAbs(KVector2f[] value);

    float[] getMouseDragMaxDistanceSqr();

    void setMouseDragMaxDistanceSqr(float[] value);

    float getMouseDragMaxDistanceSqr(int idx);

    void setMouseDragMaxDistanceSqr(int idx, float value);

    float getPenPressure();

    void setPenPressure(float value);

    boolean getAppFocusLost();

    boolean getAppAcceptingEvents();

    void setAppAcceptingEvents(boolean acceptingEvents);

    short getBackendUsingLegacyKeyArrays();

    void setBackendUsingLegacyKeyArrays(short value);

    boolean getBackendUsingLegacyNavInputArray();

    void setBackendUsingLegacyNavInputArray(boolean value);

    short getInputQueueSurrogate();

    void setInputQueueSurrogate(short value);

}
