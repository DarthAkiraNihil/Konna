package io.github.darthakiranihil.konna.libfrontend.imgui;

import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.struct.KVector4f;
import io.github.darthakiranihil.konna.core.struct.ref.*;

@SuppressWarnings({ "unused", "UnusedReturnValue" })
public interface KImGui {

    void setAssertCallback(KImAssertCallback callback);

    KImGuiContext createContext();

    KImGuiContext createContext(final KImFontAtlas sharedFontAtlas);

    void destroyContext();

    void destroyContext(final KImGuiContext ctx);

    KImGuiContext getCurrentContext();

    void setCurrentContext(final KImGuiContext ctx);

    KImGuiIo getIO();

    KImGuiStyle getStyle();

    void newFrame();

    void endFrame();

    void render();

    KImDrawData getDrawData();

    void showDemoWindow();

    void showDemoWindow(final KBooleanReferenceValue pOpen);

    void showMetricsWindow();

    void showMetricsWindow(final KBooleanReferenceValue pOpen);

    void showDebugLogWindow();

    void showDebugLogWindow(final KBooleanReferenceValue pOpen);

    void showIDStackToolWindow();

    void showIDStackToolWindow(final KBooleanReferenceValue pOpen);

    void showAboutWindow();

    void showAboutWindow(final KBooleanReferenceValue pOpen);

    void showStyleEditor();

    void showStyleEditor(final KImGuiStyle ref);

    boolean showStyleSelector(final String label);

    void showFontSelector(final String label);

    void showUserGuide();

    String getVersion();

    void styleColorsDark();

    void styleColorsDark(final KImGuiStyle style);

    void styleColorsLight();

    void styleColorsLight(final KImGuiStyle style);

    void styleColorsClassic();

    void styleColorsClassic(final KImGuiStyle style);

    boolean begin(final String title);

    boolean begin(final String title, final KBooleanReferenceValue pOpen);

    boolean begin(final String title, final KBooleanReferenceValue pOpen, final int imGuiWindowFlags);

    boolean begin(final String title, final int imGuiWindowFlags);

    void end();

    boolean beginChild(final String strId);

    boolean beginChild(final String strId, final KVector2f size);

    boolean beginChild(final String strId, final float sizeX, final float sizeY);

    boolean beginChild(final String strId, final KVector2f size, final int childFlags);

    boolean beginChild(final String strId, final float sizeX, final float sizeY, final int childFlags);

    boolean beginChild(final String strId, final KVector2f size, final int childFlags, final int windowFlags);

    boolean beginChild(
        final String strId,
        final float sizeX,
        final float sizeY,
        final int childFlags,
        final int windowFlags
    );

    boolean beginChild(final String strId, final int childFlags, final int windowFlags);

    boolean beginChild(final String strId, final int windowFlags);

    boolean beginChild(final int id);

    boolean beginChild(final int id, final KVector2f size);

    boolean beginChild(final int id, final float sizeX, final float sizeY);

    boolean beginChild(final int id, final KVector2f size, final int childFlags);

    boolean beginChild(final int id, final float sizeX, final float sizeY, final int childFlags);

    boolean beginChild(final int id, final KVector2f size, final int childFlags, final int windowFlags);

    boolean beginChild(final int id, final float sizeX, final float sizeY, final int childFlags, final int windowFlags);

    boolean beginChild(final int id, final int childFlags, final int windowFlags);

    boolean beginChild(final int id, final int windowFlags);

    boolean beginChild(final String strId, final KVector2f size, final boolean border);

    boolean beginChild(final String strId, final float sizeX, final float sizeY, final boolean border);

    boolean beginChild(final String strId, final KVector2f size, final boolean border, final int windowFlags);

    boolean beginChild(
        final String strId,
        final float sizeX,
        final float sizeY,
        final boolean border,
        final int windowFlags
    );

    void endChild();

    boolean isWindowAppearing();

    boolean isWindowCollapsed();

    void setWindowCollapsed(final boolean collapsed);

    boolean isWindowFocused();

    boolean isWindowFocused(final int imGuiFocusedFlags);

    boolean isWindowHovered();

    boolean isWindowHovered(final int imGuiHoveredFlags);

    KImDrawList getWindowDrawList();

    float getWindowDpiScale();

    KVector2f getWindowPos();

    void setWindowPos(final KVector2f pos);

    float getWindowPosX();

    float getWindowPosY();

    void getWindowPos(final KVector2f dst);

    KVector2f getWindowSize();

    void setWindowSize(final KVector2f size);

    float getWindowSizeX();

    float getWindowSizeY();

    void getWindowSize(final KVector2f dst);

    float getWindowWidth();

    float getWindowHeight();

    KImGuiViewport getWindowViewport();

    void setNextWindowPos(final KVector2f pos);

    void setNextWindowPos(final float posX, final float posY);

    void setNextWindowPos(final KVector2f pos, final int cond);

    void setNextWindowPos(final float posX, final float posY, final int cond);

    void setNextWindowPos(final KVector2f pos, final int cond, final KVector2f pivot);

    void setNextWindowPos(final float posX, final float posY, final int cond, final float pivotX, final float pivotY);

    void setNextWindowPos(final KVector2f pos, final KVector2f pivot);

    void setNextWindowPos(final float posX, final float posY, final float pivotX, final float pivotY);

    void setNextWindowSize(final KVector2f size);

    void setNextWindowSize(final float sizeX, final float sizeY);

    void setNextWindowSize(final KVector2f size, final int cond);

    void setNextWindowSize(final float sizeX, final float sizeY, final int cond);

    void setNextWindowSizeConstraints(final KVector2f sizeMin, final KVector2f sizeMax);

    void setNextWindowSizeConstraints(
        final float sizeMinX,
        final float sizeMinY,
        final float sizeMaxX,
        final float sizeMaxY
    );

    void setNextWindowContentSize(final KVector2f size);

    void setNextWindowContentSize(final float sizeX, final float sizeY);

    void setNextWindowCollapsed(final boolean collapsed);

    void setNextWindowCollapsed(final boolean collapsed, final int cond);

    void setNextWindowFocus();

    void setNextWindowScroll(final KVector2f scroll);

    void setNextWindowScroll(final float scrollX, final float scrollY);

    void setNextWindowBgAlpha(final float alpha);

    void setNextWindowViewport(final int viewportId);

    void setWindowPos(final float posX, final float posY);

    void setWindowPos(final KVector2f pos, final int cond);

    void setWindowPos(final float posX, final float posY, final int cond);

    void setWindowSize(final float sizeX, final float sizeY);

    void setWindowSize(final KVector2f size, final int cond);

    void setWindowSize(final float sizeX, final float sizeY, final int cond);

    void setWindowCollapsed(final boolean collapsed, final int cond);

    void setWindowFocus();

    void setWindowFontScale(final float scale);

    void setWindowPos(final String name, final KVector2f pos);

    void setWindowPos(final String name, final float posX, final float posY);

    void setWindowPos(final String name, final KVector2f pos, final int cond);

    void setWindowPos(final String name, final float posX, final float posY, final int cond);

    void setWindowSize(final String name, final KVector2f size);

    void setWindowSize(final String name, final float sizeX, final float sizeY);

    void setWindowSize(final String name, final KVector2f size, final int cond);

    void setWindowSize(final String name, final float sizeX, final float sizeY, final int cond);

    void setWindowCollapsed(final String name, final boolean collapsed);

    void setWindowCollapsed(final String name, final boolean collapsed, final int cond);

    void setWindowFocus(final String name);

    KVector2f getContentRegionAvail();

    float getContentRegionAvailX();

    float getContentRegionAvailY();

    void getContentRegionAvail(final KVector2f dst);

    KVector2f getContentRegionMax();

    float getContentRegionMaxX();

    float getContentRegionMaxY();

    void getContentRegionMax(final KVector2f dst);

    KVector2f getWindowContentRegionMin();

    float getWindowContentRegionMinX();

    float getWindowContentRegionMinY();

    void getWindowContentRegionMin(final KVector2f dst);

    KVector2f getWindowContentRegionMax();

    float getWindowContentRegionMaxX();

    float getWindowContentRegionMaxY();

    void getWindowContentRegionMax(final KVector2f dst);

    float getScrollX();

    void setScrollX(final float scrollX);

    float getScrollY();

    void setScrollY(final float scrollY);

    float getScrollMaxX();

    float getScrollMaxY();

    void setScrollHereX();

    void setScrollHereX(final float centerXRatio);

    void setScrollHereY();

    void setScrollHereY(final float centerYRatio);

    void setScrollFromPosX(final float localX);

    void setScrollFromPosX(final float localX, final float centerXRatio);

    void setScrollFromPosY(final float localY);

    void setScrollFromPosY(final float localY, final float centerYRatio);

    void pushFont(final KImFont font);

    void popFont();

    void pushStyleColor(int imGuiCol, int r, int g, int b, int a);

    void pushStyleColor(final int imGuiCol, final KVector4f col);

    void pushStyleColor(final int imGuiCol, final float colX, final float colY, final float colZ, final float colW);

    void pushStyleColor(final int imGuiCol, final int col);

    void popStyleColor();

    void popStyleColor(final int count);

    void pushStyleVar(final int imGuiStyleVar, final float val);

    void pushStyleVar(final int imGuiStyleVar, final KVector2f val);

    void pushStyleVar(final int imGuiStyleVar, final float valX, final float valY);

    void popStyleVar();

    void popStyleVar(final int count);

    void pushTabStop(final boolean tabStop);

    void popTabStop();

    void pushButtonRepeat(final boolean repeat);

    void popButtonRepeat();

    void pushItemWidth(final float itemWidth);

    void popItemWidth();

    void setNextItemWidth(final float itemWidth);

    float calcItemWidth();

    void pushTextWrapPos();

    void pushTextWrapPos(final float wrapLocalPosX);

    void popTextWrapPos();

    KImFont getFont();

    int getFontSize();

    KVector2f getFontTexUvWhitePixel();

    float getFontTexUvWhitePixelX();

    float getFontTexUvWhitePixelY();

    void getFontTexUvWhitePixel(final KVector2f dst);

    int getColorU32(final int idx);

    int getColorU32(final int idx, final float alphaMul);

    int getColorU32(final KVector4f col);

    int getColorU32(final float colX, final float colY, final float colZ, final float colW);

    int getColorU32i(final int col);

    int getColorU32i(final int col, final float alphaMul);

    KVector4f getStyleColorVec4(final int imGuiColIdx);

    float getStyleColorVec4X(final int imGuiColIdx);

    float getStyleColorVec4Y(final int imGuiColIdx);

    float getStyleColorVec4Z(final int imGuiColIdx);

    float getStyleColorVec4W(final int imGuiColIdx);

    void getStyleColorVec4(final KVector4f dst, final int imGuiColIdx);

    KVector2f getCursorScreenPos();

    void setCursorScreenPos(final KVector2f pos);

    float getCursorScreenPosX();

    float getCursorScreenPosY();

    void getCursorScreenPos(final KVector2f dst);

    void setCursorScreenPos(final float posX, final float posY);

    KVector2f getCursorPos();

    void setCursorPos(final KVector2f localPos);

    float getCursorPosX();

    void setCursorPosX(final float localX);

    float getCursorPosY();

    void setCursorPosY(final float localY);

    void getCursorPos(final KVector2f dst);

    void setCursorPos(final float localPosX, final float localPosY);

    KVector2f getCursorStartPos();

    float getCursorStartPosX();

    float getCursorStartPosY();

    void getCursorStartPos(final KVector2f dst);

    void separator();

    void sameLine();

    void sameLine(final float offsetFromStartX);

    void sameLine(final float offsetFromStartX, final float spacing);

    void newLine();

    void spacing();

    void dummy(final KVector2f size);

    void dummy(final float sizeX, final float sizeY);

    void indent();

    void indent(final float indentW);

    void unindent();

    void unindent(final float indentW);

    void beginGroup();

    void endGroup();

    void alignTextToFramePadding();

    float getTextLineHeight();

    float getTextLineHeightWithSpacing();

    float getFrameHeight();

    float getFrameHeightWithSpacing();

    void pushID(final String strId);

    void pushID(final String strIdBegin, final String strIdEnd);

    void pushID(final long ptrId);

    void pushID(final int intId);

    void popID();

    int getID(final String strId);

    int getID(final String strIdBegin, final String strIdEnd);

    int getID(final long ptrId);

    void textUnformatted(final String text);

    void textUnformatted(final String text, final String textEnd);

    void text(final String text);

    void textColored(final KVector4f col, final String text);

    void textColored(final float colX, final float colY, final float colZ, final float colW, final String text);

    void textColored(int r, int g, int b, int a, String text);

    void textColored(int col, String text);

    void textDisabled(final String text);

    void textWrapped(final String text);

    void labelText(final String label, final String text);

    void bulletText(final String text);

    void separatorText(final String label);

    boolean button(final String label);

    boolean button(final String label, final KVector2f size);

    boolean button(final String label, final float sizeX, final float sizeY);

    boolean smallButton(final String label);

    boolean invisibleButton(final String strId, final KVector2f size);

    boolean invisibleButton(final String strId, final float sizeX, final float sizeY);

    boolean invisibleButton(final String strId, final KVector2f size, final int imGuiButtonFlags);

    boolean invisibleButton(final String strId, final float sizeX, final float sizeY, final int imGuiButtonFlags);

    boolean arrowButton(final String strId, final int dir);

    boolean checkbox(String label, boolean active);

    boolean checkbox(final String label, final KBooleanReferenceValue data);

    boolean checkboxFlags(final String label, final KIntReferenceValue flags, final int flagsValue);

    boolean radioButton(final String label, final boolean active);

    boolean radioButton(final String label, final KIntReferenceValue v, final int vButton);

    void progressBar(final float fraction);

    void progressBar(final float fraction, final KVector2f size);

    void progressBar(final float fraction, final float sizeX, final float sizeY);

    void progressBar(final float fraction, final KVector2f size, final String overlay);

    void progressBar(final float fraction, final float sizeX, final float sizeY, final String overlay);

    void progressBar(final float fraction, final String overlay);

    void bullet();

    void image(final long userTextureId, final KVector2f imageSize);

    void image(final long userTextureId, final float imageSizeX, final float imageSizeY);

    void image(final long userTextureId, final KVector2f imageSize, final KVector2f uv0);

    void image(
        final long userTextureId,
        final float imageSizeX,
        final float imageSizeY,
        final float uv0X,
        final float uv0Y
    );

    void image(final long userTextureId, final KVector2f imageSize, final KVector2f uv0, final KVector2f uv1);

    void image(
        final long userTextureId,
        final float imageSizeX,
        final float imageSizeY,
        final float uv0X,
        final float uv0Y,
        final float uv1X,
        final float uv1Y
    );

    void image(
        final long userTextureId,
        final KVector2f imageSize,
        final KVector2f uv0,
        final KVector2f uv1,
        final KVector4f tintCol
    );

    void image(
        final long userTextureId,
        final float imageSizeX,
        final float imageSizeY,
        final float uv0X,
        final float uv0Y,
        final float uv1X,
        final float uv1Y,
        final float tintColX,
        final float tintColY,
        final float tintColZ,
        final float tintColW
    );

    void image(
        final long userTextureId,
        final KVector2f imageSize,
        final KVector2f uv0,
        final KVector2f uv1,
        final KVector4f tintCol,
        final KVector4f borderCol
    );

    void image(
        final long userTextureId,
        final float imageSizeX,
        final float imageSizeY,
        final float uv0X,
        final float uv0Y,
        final float uv1X,
        final float uv1Y,
        final float tintColX,
        final float tintColY,
        final float tintColZ,
        final float tintColW,
        final float borderColX,
        final float borderColY,
        final float borderColZ,
        final float borderColW
    );

    boolean imageButton(final String strId, final long userTextureId, final KVector2f imageSize);

    boolean imageButton(final String strId, final long userTextureId, final float imageSizeX, final float imageSizeY);

    boolean imageButton(final String strId, final long userTextureId, final KVector2f imageSize, final KVector2f uv0);

    boolean imageButton(
        final String strId,
        final long userTextureId,
        final float imageSizeX,
        final float imageSizeY,
        final float uv0X,
        final float uv0Y
    );

    boolean imageButton(
        final String strId,
        final long userTextureId,
        final KVector2f imageSize,
        final KVector2f uv0,
        final KVector2f uv1
    );

    boolean imageButton(
        final String strId,
        final long userTextureId,
        final float imageSizeX,
        final float imageSizeY,
        final float uv0X,
        final float uv0Y,
        final float uv1X,
        final float uv1Y
    );

    boolean imageButton(
        final String strId,
        final long userTextureId,
        final KVector2f imageSize,
        final KVector2f uv0,
        final KVector2f uv1,
        final KVector4f bgCol
    );

    boolean imageButton(
        final String strId,
        final long userTextureId,
        final float imageSizeX,
        final float imageSizeY,
        final float uv0X,
        final float uv0Y,
        final float uv1X,
        final float uv1Y,
        final float bgColX,
        final float bgColY,
        final float bgColZ,
        final float bgColW
    );

    boolean imageButton(
        final String strId,
        final long userTextureId,
        final KVector2f imageSize,
        final KVector2f uv0,
        final KVector2f uv1,
        final KVector4f bgCol,
        final KVector4f tintCol
    );

    boolean imageButton(
        final String strId,
        final long userTextureId,
        final float imageSizeX,
        final float imageSizeY,
        final float uv0X,
        final float uv0Y,
        final float uv1X,
        final float uv1Y,
        final float bgColX,
        final float bgColY,
        final float bgColZ,
        final float bgColW,
        final float tintColX,
        final float tintColY,
        final float tintColZ,
        final float tintColW
    );

    boolean beginCombo(final String label, final String previewValue);

    boolean beginCombo(final String label, final String previewValue, final int imGuiComboFlags);

    void endCombo();

    boolean combo(final String label, final KIntReferenceValue currentItem, final String[] items);

    boolean combo(final String label, final KIntReferenceValue currentItem, final String[] items, final int popupMaxHeightInItems);

    boolean combo(final String label, final KIntReferenceValue currentItem, final String itemsSeparatedByZeros);

    boolean combo(
        final String label,
        final KIntReferenceValue currentItem,
        final String itemsSeparatedByZeros,
        final int popupMaxHeightInItems
    );

    boolean dragFloat(final String label, final float[] v);

    boolean dragFloat(final String label, final float[] v, final float vSpeed);

    boolean dragFloat(final String label, final float[] v, final float vSpeed, final float vMin);

    boolean dragFloat(final String label, final float[] v, final float vSpeed, final float vMin, final float vMax);

    boolean dragFloat(
        final String label,
        final float[] v,
        final float vSpeed,
        final float vMin,
        final float vMax,
        final String format
    );

    boolean dragFloat(
        final String label,
        final float[] v,
        final float vSpeed,
        final float vMin,
        final float vMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean dragFloat(
        final String label,
        final float[] v,
        final float vSpeed,
        final float vMin,
        final float vMax,
        final int imGuiSliderFlags
    );

    boolean dragFloat2(final String label, final float[] v);

    boolean dragFloat2(final String label, final float[] v, final float vSpeed);

    boolean dragFloat2(final String label, final float[] v, final float vSpeed, final float vMin);

    boolean dragFloat2(final String label, final float[] v, final float vSpeed, final float vMin, final float vMax);

    boolean dragFloat2(
        final String label,
        final float[] v,
        final float vSpeed,
        final float vMin,
        final float vMax,
        final String format
    );

    boolean dragFloat2(
        final String label,
        final float[] v,
        final float vSpeed,
        final float vMin,
        final float vMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean dragFloat2(
        final String label,
        final float[] v,
        final float vSpeed,
        final float vMin,
        final float vMax,
        final int imGuiSliderFlags
    );

    boolean dragFloat3(final String label, final float[] v);

    boolean dragFloat3(final String label, final float[] v, final float vSpeed);

    boolean dragFloat3(final String label, final float[] v, final float vSpeed, final float vMin);

    boolean dragFloat3(final String label, final float[] v, final float vSpeed, final float vMin, final float vMax);

    boolean dragFloat3(
        final String label,
        final float[] v,
        final float vSpeed,
        final float vMin,
        final float vMax,
        final String format
    );

    boolean dragFloat3(
        final String label,
        final float[] v,
        final float vSpeed,
        final float vMin,
        final float vMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean dragFloat3(
        final String label,
        final float[] v,
        final float vSpeed,
        final float vMin,
        final float vMax,
        final int imGuiSliderFlags
    );

    boolean dragFloat4(final String label, final float[] v);

    boolean dragFloat4(final String label, final float[] v, final float vSpeed);

    boolean dragFloat4(final String label, final float[] v, final float vSpeed, final float vMin);

    boolean dragFloat4(final String label, final float[] v, final float vSpeed, final float vMin, final float vMax);

    boolean dragFloat4(
        final String label,
        final float[] v,
        final float vSpeed,
        final float vMin,
        final float vMax,
        final String format
    );

    boolean dragFloat4(
        final String label,
        final float[] v,
        final float vSpeed,
        final float vMin,
        final float vMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean dragFloat4(
        final String label,
        final float[] v,
        final float vSpeed,
        final float vMin,
        final float vMax,
        final int imGuiSliderFlags
    );

    boolean dragFloatRange2(
        final String label,
        final float[] vCurrentMin,
        final float[] vCurrentMax,
        final float vSpeed
    );

    boolean dragFloatRange2(
        final String label,
        final float[] vCurrentMin,
        final float[] vCurrentMax,
        final float vSpeed,
        final float vMin
    );

    boolean dragFloatRange2(
        final String label,
        final float[] vCurrentMin,
        final float[] vCurrentMax,
        final float vSpeed,
        final float vMin,
        final float vMax
    );

    boolean dragFloatRange2(
        final String label,
        final float[] vCurrentMin,
        final float[] vCurrentMax,
        final float vSpeed,
        final float vMin,
        final float vMax,
        final String format
    );

    boolean dragFloatRange2(
        final String label,
        final float[] vCurrentMin,
        final float[] vCurrentMax,
        final float vSpeed,
        final float vMin,
        final float vMax,
        final String format,
        final String formatMax
    );

    boolean dragFloatRange2(
        final String label,
        final float[] vCurrentMin,
        final float[] vCurrentMax,
        final float vSpeed,
        final float vMin,
        final float vMax,
        final String format,
        final String formatMax,
        final int imGuiSliderFlags
    );

    boolean dragInt(final String label, final int[] v);

    boolean dragInt(final String label, final int[] v, final float vSpeed);

    boolean dragInt(final String label, final int[] v, final float vSpeed, final int vMin);

    boolean dragInt(final String label, final int[] v, final float vSpeed, final int vMin, final int vMax);

    boolean dragInt(
        final String label,
        final int[] v,
        final float vSpeed,
        final int vMin,
        final int vMax,
        final String format
    );

    boolean dragInt(
        final String label,
        final int[] v,
        final float vSpeed,
        final int vMin,
        final int vMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean dragInt(
        final String label,
        final int[] v,
        final float vSpeed,
        final int vMin,
        final int vMax,
        final int imGuiSliderFlags
    );

    boolean dragInt2(final String label, final int[] v);

    boolean dragInt2(final String label, final int[] v, final float vSpeed);

    boolean dragInt2(final String label, final int[] v, final float vSpeed, final int vMin);

    boolean dragInt2(final String label, final int[] v, final float vSpeed, final int vMin, final int vMax);

    boolean dragInt2(
        final String label,
        final int[] v,
        final float vSpeed,
        final int vMin,
        final int vMax,
        final String format
    );

    boolean dragInt2(
        final String label,
        final int[] v,
        final float vSpeed,
        final int vMin,
        final int vMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean dragInt2(
        final String label,
        final int[] v,
        final float vSpeed,
        final int vMin,
        final int vMax,
        final int imGuiSliderFlags
    );

    boolean dragInt3(final String label, final int[] v);

    boolean dragInt3(final String label, final int[] v, final float vSpeed);

    boolean dragInt3(final String label, final int[] v, final float vSpeed, final int vMin);

    boolean dragInt3(final String label, final int[] v, final float vSpeed, final int vMin, final int vMax);

    boolean dragInt3(
        final String label,
        final int[] v,
        final float vSpeed,
        final int vMin,
        final int vMax,
        final String format
    );

    boolean dragInt3(
        final String label,
        final int[] v,
        final float vSpeed,
        final int vMin,
        final int vMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean dragInt3(
        final String label,
        final int[] v,
        final float vSpeed,
        final int vMin,
        final int vMax,
        final int imGuiSliderFlags
    );

    boolean dragInt4(final String label, final int[] v);

    boolean dragInt4(final String label, final int[] v, final float vSpeed);

    boolean dragInt4(final String label, final int[] v, final float vSpeed, final int vMin);

    boolean dragInt4(final String label, final int[] v, final float vSpeed, final int vMin, final int vMax);

    boolean dragInt4(
        final String label,
        final int[] v,
        final float vSpeed,
        final int vMin,
        final int vMax,
        final String format
    );

    boolean dragInt4(
        final String label,
        final int[] v,
        final float vSpeed,
        final int vMin,
        final int vMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean dragInt4(
        final String label,
        final int[] v,
        final float vSpeed,
        final int vMin,
        final int vMax,
        final int imGuiSliderFlags
    );

    boolean dragIntRange2(final String label, final int[] vCurrentMin, final int[] vCurrentMax);

    boolean dragIntRange2(final String label, final int[] vCurrentMin, final int[] vCurrentMax, final float vSpeed);

    boolean dragIntRange2(
        final String label,
        final int[] vCurrentMin,
        final int[] vCurrentMax,
        final float vSpeed,
        final int vMin
    );

    boolean dragIntRange2(
        final String label,
        final int[] vCurrentMin,
        final int[] vCurrentMax,
        final float vSpeed,
        final int vMin,
        final int vMax
    );

    boolean dragIntRange2(
        final String label,
        final int[] vCurrentMin,
        final int[] vCurrentMax,
        final float vSpeed,
        final int vMin,
        final int vMax,
        final String format
    );

    boolean dragIntRange2(
        final String label,
        final int[] vCurrentMin,
        final int[] vCurrentMax,
        final float vSpeed,
        final int vMin,
        final int vMax,
        final String format,
        final String formatMax
    );

    boolean dragIntRange2(
        final String label,
        final int[] vCurrentMin,
        final int[] vCurrentMax,
        final float vSpeed,
        final int vMin,
        final int vMax,
        final String format,
        final String formatMax,
        final int imGuiSliderFlags
    );

    boolean dragScalar(final String label, final short[] pData);

    boolean dragScalar(final String label, final short[] pData, final float vSpeed);

    boolean dragScalar(final String label, final short[] pData, final float vSpeed, final short pMin);

    boolean dragScalar(final String label, final short[] pData, final float vSpeed, final short pMin, final short pMax);

    boolean dragScalar(
        final String label,
        final short[] pData,
        final float vSpeed,
        final short pMin,
        final short pMax,
        final String format
    );

    boolean dragScalar(
        final String label,
        final short[] pData,
        final float vSpeed,
        final short pMin,
        final short pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean dragScalar(final String label, final int[] pData);

    boolean dragScalar(final String label, final int[] pData, final float vSpeed);

    boolean dragScalar(final String label, final int[] pData, final float vSpeed, final int pMin);

    boolean dragScalar(final String label, final int[] pData, final float vSpeed, final int pMin, final int pMax);

    boolean dragScalar(
        final String label,
        final int[] pData,
        final float vSpeed,
        final int pMin,
        final int pMax,
        final String format
    );

    boolean dragScalar(
        final String label,
        final int[] pData,
        final float vSpeed,
        final int pMin,
        final int pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean dragScalar(final String label, final long[] pData);

    boolean dragScalar(final String label, final long[] pData, final float vSpeed);

    boolean dragScalar(final String label, final long[] pData, final float vSpeed, final long pMin);

    boolean dragScalar(final String label, final long[] pData, final float vSpeed, final long pMin, final long pMax);

    boolean dragScalar(
        final String label,
        final long[] pData,
        final float vSpeed,
        final long pMin,
        final long pMax,
        final String format
    );

    boolean dragScalar(
        final String label,
        final long[] pData,
        final float vSpeed,
        final long pMin,
        final long pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean dragScalar(final String label, final float[] pData);

    boolean dragScalar(final String label, final float[] pData, final float vSpeed);

    boolean dragScalar(final String label, final float[] pData, final float vSpeed, final float pMin);

    boolean dragScalar(final String label, final float[] pData, final float vSpeed, final float pMin, final float pMax);

    boolean dragScalar(
        final String label,
        final float[] pData,
        final float vSpeed,
        final float pMin,
        final float pMax,
        final String format
    );

    boolean dragScalar(
        final String label,
        final float[] pData,
        final float vSpeed,
        final float pMin,
        final float pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean dragScalar(final String label, final double[] pData);

    boolean dragScalar(final String label, final double[] pData, final float vSpeed);

    boolean dragScalar(final String label, final double[] pData, final float vSpeed, final double pMin);

    boolean dragScalar(
        final String label,
        final double[] pData,
        final float vSpeed,
        final double pMin,
        final double pMax
    );

    boolean dragScalar(
        final String label,
        final double[] pData,
        final float vSpeed,
        final double pMin,
        final double pMax,
        final String format
    );

    boolean dragScalar(
        final String label,
        final double[] pData,
        final float vSpeed,
        final double pMin,
        final double pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean dragScalarN(final String label, final short[] pData, final int components);

    boolean dragScalarN(final String label, final short[] pData, final int components, final float vSpeed);

    boolean dragScalarN(
        final String label,
        final short[] pData,
        final int components,
        final float vSpeed,
        final short pMin
    );

    boolean dragScalarN(
        final String label,
        final short[] pData,
        final int components,
        final float vSpeed,
        final short pMin,
        final short pMax
    );

    boolean dragScalarN(
        final String label,
        final short[] pData,
        final int components,
        final float vSpeed,
        final short pMin,
        final short pMax,
        final String format
    );

    boolean dragScalarN(
        final String label,
        final short[] pData,
        final int components,
        final float vSpeed,
        final short pMin,
        final short pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean dragScalarN(final String label, final int[] pData, final int components);

    boolean dragScalarN(final String label, final int[] pData, final int components, final float vSpeed);

    boolean dragScalarN(
        final String label,
        final int[] pData,
        final int components,
        final float vSpeed,
        final int pMin
    );

    boolean dragScalarN(
        final String label,
        final int[] pData,
        final int components,
        final float vSpeed,
        final int pMin,
        final int pMax
    );

    boolean dragScalarN(
        final String label,
        final int[] pData,
        final int components,
        final float vSpeed,
        final int pMin,
        final int pMax,
        final String format
    );

    boolean dragScalarN(
        final String label,
        final int[] pData,
        final int components,
        final float vSpeed,
        final int pMin,
        final int pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean dragScalarN(final String label, final long[] pData, final int components);

    boolean dragScalarN(final String label, final long[] pData, final int components, final float vSpeed);

    boolean dragScalarN(
        final String label,
        final long[] pData,
        final int components,
        final float vSpeed,
        final long pMin
    );

    boolean dragScalarN(
        final String label,
        final long[] pData,
        final int components,
        final float vSpeed,
        final long pMin,
        final long pMax
    );

    boolean dragScalarN(
        final String label,
        final long[] pData,
        final int components,
        final float vSpeed,
        final long pMin,
        final long pMax,
        final String format
    );

    boolean dragScalarN(
        final String label,
        final long[] pData,
        final int components,
        final float vSpeed,
        final long pMin,
        final long pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean dragScalarN(final String label, final float[] pData, final int components);

    boolean dragScalarN(final String label, final float[] pData, final int components, final float vSpeed);

    boolean dragScalarN(
        final String label,
        final float[] pData,
        final int components,
        final float vSpeed,
        final float pMin
    );

    boolean dragScalarN(
        final String label,
        final float[] pData,
        final int components,
        final float vSpeed,
        final float pMin,
        final float pMax
    );

    boolean dragScalarN(
        final String label,
        final float[] pData,
        final int components,
        final float vSpeed,
        final float pMin,
        final float pMax,
        final String format
    );

    boolean dragScalarN(
        final String label,
        final float[] pData,
        final int components,
        final float vSpeed,
        final float pMin,
        final float pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean dragScalarN(final String label, final double[] pData, final int components);

    boolean dragScalarN(final String label, final double[] pData, final int components, final float vSpeed);

    boolean dragScalarN(
        final String label,
        final double[] pData,
        final int components,
        final float vSpeed,
        final double pMin
    );

    boolean dragScalarN(
        final String label,
        final double[] pData,
        final int components,
        final float vSpeed,
        final double pMin,
        final double pMax
    );

    boolean dragScalarN(
        final String label,
        final double[] pData,
        final int components,
        final float vSpeed,
        final double pMin,
        final double pMax,
        final String format
    );

    boolean dragScalarN(
        final String label,
        final double[] pData,
        final int components,
        final float vSpeed,
        final double pMin,
        final double pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean sliderFloat(final String label, final float[] v, final float vMin, final float vMax);

    boolean sliderFloat(final String label, final float[] v, final float vMin, final float vMax, final String format);

    boolean sliderFloat(
        final String label,
        final float[] v,
        final float vMin,
        final float vMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean sliderFloat(
        final String label,
        final float[] v,
        final float vMin,
        final float vMax,
        final int imGuiSliderFlags
    );

    boolean sliderFloat2(final String label, final float[] v, final float vMin, final float vMax);

    boolean sliderFloat2(final String label, final float[] v, final float vMin, final float vMax, final String format);

    boolean sliderFloat2(
        final String label,
        final float[] v,
        final float vMin,
        final float vMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean sliderFloat2(
        final String label,
        final float[] v,
        final float vMin,
        final float vMax,
        final int imGuiSliderFlags
    );

    boolean sliderFloat3(final String label, final float[] v, final float vMin, final float vMax);

    boolean sliderFloat3(final String label, final float[] v, final float vMin, final float vMax, final String format);

    boolean sliderFloat3(
        final String label,
        final float[] v,
        final float vMin,
        final float vMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean sliderFloat3(
        final String label,
        final float[] v,
        final float vMin,
        final float vMax,
        final int imGuiSliderFlags
    );

    boolean sliderFloat4(final String label, final float[] v, final float vMin, final float vMax);

    boolean sliderFloat4(final String label, final float[] v, final float vMin, final float vMax, final String format);

    boolean sliderFloat4(
        final String label,
        final float[] v,
        final float vMin,
        final float vMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean sliderFloat4(
        final String label,
        final float[] v,
        final float vMin,
        final float vMax,
        final int imGuiSliderFlags
    );

    boolean sliderAngle(final String label, final float[] vRad);

    boolean sliderAngle(final String label, final float[] vRad, final float vDegreesMin);

    boolean sliderAngle(final String label, final float[] vRad, final float vDegreesMin, final float vDegreesMax);

    boolean sliderAngle(
        final String label,
        final float[] vRad,
        final float vDegreesMin,
        final float vDegreesMax,
        final String format
    );

    boolean sliderAngle(
        final String label,
        final float[] vRad,
        final float vDegreesMin,
        final float vDegreesMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean sliderAngle(
        final String label,
        final float[] vRad,
        final float vDegreesMin,
        final float vDegreesMax,
        final int imGuiSliderFlags
    );

    boolean sliderInt(final String label, final int[] v, final int vMin, final int vMax);

    boolean sliderInt(final String label, final int[] v, final int vMin, final int vMax, final String format);

    boolean sliderInt(
        final String label,
        final int[] v,
        final int vMin,
        final int vMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean sliderInt(final String label, final int[] v, final int vMin, final int vMax, final int imGuiSliderFlags);

    boolean sliderInt2(final String label, final int[] v, final int vMin, final int vMax);

    boolean sliderInt2(final String label, final int[] v, final int vMin, final int vMax, final String format);

    boolean sliderInt2(
        final String label,
        final int[] v,
        final int vMin,
        final int vMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean sliderInt2(final String label, final int[] v, final int vMin, final int vMax, final int imGuiSliderFlags);

    boolean sliderInt3(final String label, final int[] v, final int vMin, final int vMax);

    boolean sliderInt3(final String label, final int[] v, final int vMin, final int vMax, final String format);

    boolean sliderInt3(
        final String label,
        final int[] v,
        final int vMin,
        final int vMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean sliderInt3(final String label, final int[] v, final int vMin, final int vMax, final int imGuiSliderFlags);

    boolean sliderInt4(final String label, final int[] v, final int vMin, final int vMax);

    boolean sliderInt4(final String label, final int[] v, final int vMin, final int vMax, final String format);

    boolean sliderInt4(
        final String label,
        final int[] v,
        final int vMin,
        final int vMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean sliderInt4(final String label, final int[] v, final int vMin, final int vMax, final int imGuiSliderFlags);

    boolean sliderScalar(final String label, final short[] pData, final short pMin, final short pMax);

    boolean sliderScalar(
        final String label,
        final short[] pData,
        final short pMin,
        final short pMax,
        final String format
    );

    boolean sliderScalar(
        final String label,
        final short[] pData,
        final short pMin,
        final short pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean sliderScalar(final String label, final int[] pData, final int pMin, final int pMax);

    boolean sliderScalar(final String label, final int[] pData, final int pMin, final int pMax, final String format);

    boolean sliderScalar(
        final String label,
        final int[] pData,
        final int pMin,
        final int pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean sliderScalar(final String label, final long[] pData, final long pMin, final long pMax);

    boolean sliderScalar(final String label, final long[] pData, final long pMin, final long pMax, final String format);

    boolean sliderScalar(
        final String label,
        final long[] pData,
        final long pMin,
        final long pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean sliderScalar(final String label, final float[] pData, final float pMin, final float pMax);

    boolean sliderScalar(
        final String label,
        final float[] pData,
        final float pMin,
        final float pMax,
        final String format
    );

    boolean sliderScalar(
        final String label,
        final float[] pData,
        final float pMin,
        final float pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean sliderScalar(final String label, final double[] pData, final double pMin, final double pMax);

    boolean sliderScalar(
        final String label,
        final double[] pData,
        final double pMin,
        final double pMax,
        final String format
    );

    boolean sliderScalar(
        final String label,
        final double[] pData,
        final double pMin,
        final double pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean sliderScalarN(
        final String label,
        final short[] pData,
        final int components,
        final short pMin,
        final short pMax
    );

    boolean sliderScalarN(
        final String label,
        final short[] pData,
        final int components,
        final short pMin,
        final short pMax,
        final String format
    );

    boolean sliderScalarN(
        final String label,
        final short[] pData,
        final int components,
        final short pMin,
        final short pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean sliderScalarN(final String label, final int[] pData, final int components, final int pMin, final int pMax);

    boolean sliderScalarN(
        final String label,
        final int[] pData,
        final int components,
        final int pMin,
        final int pMax,
        final String format
    );

    boolean sliderScalarN(
        final String label,
        final int[] pData,
        final int components,
        final int pMin,
        final int pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean sliderScalarN(
        final String label,
        final long[] pData,
        final int components,
        final long pMin,
        final long pMax
    );

    boolean sliderScalarN(
        final String label,
        final long[] pData,
        final int components,
        final long pMin,
        final long pMax,
        final String format
    );

    boolean sliderScalarN(
        final String label,
        final long[] pData,
        final int components,
        final long pMin,
        final long pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean sliderScalarN(
        final String label,
        final float[] pData,
        final int components,
        final float pMin,
        final float pMax
    );

    boolean sliderScalarN(
        final String label,
        final float[] pData,
        final int components,
        final float pMin,
        final float pMax,
        final String format
    );

    boolean sliderScalarN(
        final String label,
        final float[] pData,
        final int components,
        final float pMin,
        final float pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean sliderScalarN(
        final String label,
        final double[] pData,
        final int components,
        final double pMin,
        final double pMax
    );

    boolean sliderScalarN(
        final String label,
        final double[] pData,
        final int components,
        final double pMin,
        final double pMax,
        final String format
    );

    boolean sliderScalarN(
        final String label,
        final double[] pData,
        final int components,
        final double pMin,
        final double pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean vSliderFloat(final String label, final KVector2f size, final float[] v, final float vMin, final float vMax);

    boolean vSliderFloat(
        final String label,
        final float sizeX,
        final float sizeY,
        final float[] v,
        final float vMin,
        final float vMax
    );

    boolean vSliderFloat(
        final String label,
        final KVector2f size,
        final float[] v,
        final float vMin,
        final float vMax,
        final String format
    );

    boolean vSliderFloat(
        final String label,
        final float sizeX,
        final float sizeY,
        final float[] v,
        final float vMin,
        final float vMax,
        final String format
    );

    boolean vSliderFloat(
        final String label,
        final KVector2f size,
        final float[] v,
        final float vMin,
        final float vMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean vSliderFloat(
        final String label,
        final float sizeX,
        final float sizeY,
        final float[] v,
        final float vMin,
        final float vMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean vSliderFloat(
        final String label,
        final KVector2f size,
        final float[] v,
        final float vMin,
        final float vMax,
        final int imGuiSliderFlags
    );

    boolean vSliderFloat(
        final String label,
        final float sizeX,
        final float sizeY,
        final float[] v,
        final float vMin,
        final float vMax,
        final int imGuiSliderFlags
    );

    boolean vSliderInt(final String label, final KVector2f size, final int[] v, final int vMin, final int vMax);

    boolean vSliderInt(
        final String label,
        final float sizeX,
        final float sizeY,
        final int[] v,
        final int vMin,
        final int vMax
    );

    boolean vSliderInt(
        final String label,
        final KVector2f size,
        final int[] v,
        final int vMin,
        final int vMax,
        final String format
    );

    boolean vSliderInt(
        final String label,
        final float sizeX,
        final float sizeY,
        final int[] v,
        final int vMin,
        final int vMax,
        final String format
    );

    boolean vSliderInt(
        final String label,
        final KVector2f size,
        final int[] v,
        final int vMin,
        final int vMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean vSliderInt(
        final String label,
        final float sizeX,
        final float sizeY,
        final int[] v,
        final int vMin,
        final int vMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean vSliderInt(
        final String label,
        final KVector2f size,
        final int[] v,
        final int vMin,
        final int vMax,
        final int imGuiSliderFlags
    );

    boolean vSliderInt(
        final String label,
        final float sizeX,
        final float sizeY,
        final int[] v,
        final int vMin,
        final int vMax,
        final int imGuiSliderFlags
    );

    boolean vSliderScalar(
        final String label,
        final KVector2f size,
        final short[] pData,
        final short pMin,
        final short pMax
    );

    boolean vSliderScalar(
        final String label,
        final float sizeX,
        final float sizeY,
        final short[] pData,
        final short pMin,
        final short pMax
    );

    boolean vSliderScalar(
        final String label,
        final KVector2f size,
        final short[] pData,
        final short pMin,
        final short pMax,
        final String format
    );

    boolean vSliderScalar(
        final String label,
        final float sizeX,
        final float sizeY,
        final short[] pData,
        final short pMin,
        final short pMax,
        final String format
    );

    boolean vSliderScalar(
        final String label,
        final KVector2f size,
        final short[] pData,
        final short pMin,
        final short pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean vSliderScalar(
        final String label,
        final float sizeX,
        final float sizeY,
        final short[] pData,
        final short pMin,
        final short pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean vSliderScalar(final String label, final KVector2f size, final int[] pData, final int pMin, final int pMax);

    boolean vSliderScalar(
        final String label,
        final float sizeX,
        final float sizeY,
        final int[] pData,
        final int pMin,
        final int pMax
    );

    boolean vSliderScalar(
        final String label,
        final KVector2f size,
        final int[] pData,
        final int pMin,
        final int pMax,
        final String format
    );

    boolean vSliderScalar(
        final String label,
        final float sizeX,
        final float sizeY,
        final int[] pData,
        final int pMin,
        final int pMax,
        final String format
    );

    boolean vSliderScalar(
        final String label,
        final KVector2f size,
        final int[] pData,
        final int pMin,
        final int pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean vSliderScalar(
        final String label,
        final float sizeX,
        final float sizeY,
        final int[] pData,
        final int pMin,
        final int pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean vSliderScalar(final String label, final KVector2f size, final long[] pData, final long pMin, final long pMax);

    boolean vSliderScalar(
        final String label,
        final float sizeX,
        final float sizeY,
        final long[] pData,
        final long pMin,
        final long pMax
    );

    boolean vSliderScalar(
        final String label,
        final KVector2f size,
        final long[] pData,
        final long pMin,
        final long pMax,
        final String format
    );

    boolean vSliderScalar(
        final String label,
        final float sizeX,
        final float sizeY,
        final long[] pData,
        final long pMin,
        final long pMax,
        final String format
    );

    boolean vSliderScalar(
        final String label,
        final KVector2f size,
        final long[] pData,
        final long pMin,
        final long pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean vSliderScalar(
        final String label,
        final float sizeX,
        final float sizeY,
        final long[] pData,
        final long pMin,
        final long pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean vSliderScalar(
        final String label,
        final KVector2f size,
        final float[] pData,
        final float pMin,
        final float pMax
    );

    boolean vSliderScalar(
        final String label,
        final float sizeX,
        final float sizeY,
        final float[] pData,
        final float pMin,
        final float pMax
    );

    boolean vSliderScalar(
        final String label,
        final KVector2f size,
        final float[] pData,
        final float pMin,
        final float pMax,
        final String format
    );

    boolean vSliderScalar(
        final String label,
        final float sizeX,
        final float sizeY,
        final float[] pData,
        final float pMin,
        final float pMax,
        final String format
    );

    boolean vSliderScalar(
        final String label,
        final KVector2f size,
        final float[] pData,
        final float pMin,
        final float pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean vSliderScalar(
        final String label,
        final float sizeX,
        final float sizeY,
        final float[] pData,
        final float pMin,
        final float pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean vSliderScalar(
        final String label,
        final KVector2f size,
        final double[] pData,
        final double pMin,
        final double pMax
    );

    boolean vSliderScalar(
        final String label,
        final float sizeX,
        final float sizeY,
        final double[] pData,
        final double pMin,
        final double pMax
    );

    boolean vSliderScalar(
        final String label,
        final KVector2f size,
        final double[] pData,
        final double pMin,
        final double pMax,
        final String format
    );

    boolean vSliderScalar(
        final String label,
        final float sizeX,
        final float sizeY,
        final double[] pData,
        final double pMin,
        final double pMax,
        final String format
    );

    boolean vSliderScalar(
        final String label,
        final KVector2f size,
        final double[] pData,
        final double pMin,
        final double pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean vSliderScalar(
        final String label,
        final float sizeX,
        final float sizeY,
        final double[] pData,
        final double pMin,
        final double pMax,
        final String format,
        final int imGuiSliderFlags
    );

    boolean inputText(final String label, final KStringReferenceValue text);

    boolean inputText(final String label, final KStringReferenceValue text, final int imGuiInputTextFlags);

    boolean inputText(
        final String label,
        final KStringReferenceValue text,
        final int imGuiInputTextFlags,
        final KImGuiInputTextCallback callback
    );

    boolean inputTextMultiline(final String label, final KStringReferenceValue text);

    boolean inputTextMultiline(final String label, final KStringReferenceValue text, final float width, final float height);

    boolean inputTextMultiline(final String label, final KStringReferenceValue text, final int imGuiInputTextFlags);

    boolean inputTextMultiline(
        final String label,
        final KStringReferenceValue text,
        final int imGuiInputTextFlags,
        final KImGuiInputTextCallback callback
    );

    boolean inputTextMultiline(
        final String label,
        final KStringReferenceValue text,
        final float width,
        final float height,
        final int imGuiInputTextFlags
    );

    boolean inputTextMultiline(
        final String label,
        final KStringReferenceValue text,
        final float width,
        final float height,
        final int imGuiInputTextFlags,
        final KImGuiInputTextCallback callback
    );

    boolean inputTextWithHint(final String label, final String hint, final KStringReferenceValue text);

    boolean inputTextWithHint(
        final String label,
        final String hint,
        final KStringReferenceValue text,
        final int imGuiInputTextFlags
    );

    boolean inputTextWithHint(
        final String label,
        final String hint,
        final KStringReferenceValue text,
        final int imGuiInputTextFlags,
        final KImGuiInputTextCallback callback
    );

    boolean inputFloat(final String label, final KFloatReferenceValue v);

    boolean inputFloat(final String label, final KFloatReferenceValue v, final float step);

    boolean inputFloat(final String label, final KFloatReferenceValue v, final float step, final float stepFast);

    boolean inputFloat(
        final String label,
        final KFloatReferenceValue v,
        final float step,
        final float stepFast,
        final String format
    );

    boolean inputFloat(
        final String label,
        final KFloatReferenceValue v,
        final float step,
        final float stepFast,
        final String format,
        final int imGuiInputTextFlags
    );

    boolean inputFloat(
        final String label,
        final KFloatReferenceValue v,
        final float step,
        final float stepFast,
        final int imGuiInputTextFlags
    );

    boolean inputFloat2(final String label, final float[] v);

    boolean inputFloat2(final String label, final float[] v, final String format);

    boolean inputFloat2(final String label, final float[] v, final String format, final int imGuiInputTextFlags);

    boolean inputFloat2(final String label, final float[] v, final int imGuiInputTextFlags);

    boolean inputFloat3(final String label, final float[] v);

    boolean inputFloat3(final String label, final float[] v, final String format);

    boolean inputFloat3(final String label, final float[] v, final String format, final int imGuiInputTextFlags);

    boolean inputFloat3(final String label, final float[] v, final int imGuiInputTextFlags);

    boolean inputFloat4(final String label, final float[] v);

    boolean inputFloat4(final String label, final float[] v, final String format);

    boolean inputFloat4(final String label, final float[] v, final String format, final int imGuiInputTextFlags);

    boolean inputFloat4(final String label, final float[] v, final int imGuiInputTextFlags);

    boolean inputInt(final String label, final KIntReferenceValue v);

    boolean inputInt(final String label, final KIntReferenceValue v, final int step);

    boolean inputInt(final String label, final KIntReferenceValue v, final int step, final int stepFast);

    boolean inputInt(
        final String label,
        final KIntReferenceValue v,
        final int step,
        final int stepFast,
        final int imGuiInputTextFlags
    );

    boolean inputInt2(final String label, final int[] v);

    boolean inputInt2(final String label, final int[] v, final int imGuiInputTextFlags);

    boolean inputInt3(final String label, final int[] v);

    boolean inputInt3(final String label, final int[] v, final int imGuiInputTextFlags);

    boolean inputInt4(final String label, final int[] v);

    boolean inputInt4(final String label, final int[] v, final int imGuiInputTextFlags);

    boolean inputDouble(final String label, final KDoubleReferenceValue v);

    boolean inputDouble(final String label, final KDoubleReferenceValue v, final double step);

    boolean inputDouble(final String label, final KDoubleReferenceValue v, final double step, final double stepFast);

    boolean inputDouble(
        final String label,
        final KDoubleReferenceValue v,
        final double step,
        final double stepFast,
        final String format
    );

    boolean inputDouble(
        final String label,
        final KDoubleReferenceValue v,
        final double step,
        final double stepFast,
        final String format,
        final int imGuiInputTextFlags
    );

    boolean inputDouble(
        final String label,
        final KDoubleReferenceValue v,
        final double step,
        final double stepFast,
        final int imGuiInputTextFlags
    );

    boolean inputScalar(final String label, final KShortReferenceValue pData);

    boolean inputScalar(final String label, final KShortReferenceValue pData, final short pStep);

    boolean inputScalar(final String label, final KShortReferenceValue pData, final short pStep, final short pStepFast);

    boolean inputScalar(
        final String label,
        final KShortReferenceValue pData,
        final short pStep,
        final short pStepFast,
        final String format
    );

    boolean inputScalar(
        final String label,
        final KShortReferenceValue pData,
        final short pStep,
        final short pStepFast,
        final String format,
        final int imGuiSliderFlags
    );

    boolean inputScalar(final String label, final KIntReferenceValue pData);

    boolean inputScalar(final String label, final KIntReferenceValue pData, final int pStep);

    boolean inputScalar(final String label, final KIntReferenceValue pData, final int pStep, final int pStepFast);

    boolean inputScalar(
        final String label,
        final KIntReferenceValue pData,
        final int pStep,
        final int pStepFast,
        final String format
    );

    boolean inputScalar(
        final String label,
        final KIntReferenceValue pData,
        final int pStep,
        final int pStepFast,
        final String format,
        final int imGuiSliderFlags
    );

    boolean inputScalar(final String label, final KLongReferenceValue pData);

    boolean inputScalar(final String label, final KLongReferenceValue pData, final long pStep);

    boolean inputScalar(final String label, final KLongReferenceValue pData, final long pStep, final long pStepFast);

    boolean inputScalar(
        final String label,
        final KLongReferenceValue pData,
        final long pStep,
        final long pStepFast,
        final String format
    );

    boolean inputScalar(
        final String label,
        final KLongReferenceValue pData,
        final long pStep,
        final long pStepFast,
        final String format,
        final int imGuiSliderFlags
    );

    boolean inputScalar(final String label, final KFloatReferenceValue pData);

    boolean inputScalar(final String label, final KFloatReferenceValue pData, final float pStep);

    boolean inputScalar(final String label, final KFloatReferenceValue pData, final float pStep, final float pStepFast);

    boolean inputScalar(
        final String label,
        final KFloatReferenceValue pData,
        final float pStep,
        final float pStepFast,
        final String format
    );

    boolean inputScalar(
        final String label,
        final KFloatReferenceValue pData,
        final float pStep,
        final float pStepFast,
        final String format,
        final int imGuiSliderFlags
    );

    boolean inputScalar(final String label, final KDoubleReferenceValue pData);

    boolean inputScalar(final String label, final KDoubleReferenceValue pData, final double pStep);

    boolean inputScalar(final String label, final KDoubleReferenceValue pData, final double pStep, final double pStepFast);

    boolean inputScalar(
        final String label,
        final KDoubleReferenceValue pData,
        final double pStep,
        final double pStepFast,
        final String format
    );

    boolean inputScalar(
        final String label,
        final KDoubleReferenceValue pData,
        final double pStep,
        final double pStepFast,
        final String format,
        final int imGuiSliderFlags
    );

    boolean inputScalar(final String label, final int dataType, final KShortReferenceValue pData);

    boolean inputScalar(final String label, final int dataType, final KShortReferenceValue pData, final short pStep);

    boolean inputScalar(
        final String label,
        final int dataType,
        final KShortReferenceValue pData,
        final short pStep,
        final short pStepFast
    );

    boolean inputScalar(
        final String label,
        final int dataType,
        final KShortReferenceValue pData,
        final short pStep,
        final short pStepFast,
        final String format
    );

    boolean inputScalar(
        final String label,
        final int dataType,
        final KShortReferenceValue pData,
        final short pStep,
        final short pStepFast,
        final String format,
        final int imGuiSliderFlags
    );

    boolean inputScalar(final String label, final int dataType, final KIntReferenceValue pData);

    boolean inputScalar(final String label, final int dataType, final KIntReferenceValue pData, final int pStep);

    boolean inputScalar(
        final String label,
        final int dataType,
        final KIntReferenceValue pData,
        final int pStep,
        final int pStepFast
    );

    boolean inputScalar(
        final String label,
        final int dataType,
        final KIntReferenceValue pData,
        final int pStep,
        final int pStepFast,
        final String format
    );

    boolean inputScalar(
        final String label,
        final int dataType,
        final KIntReferenceValue pData,
        final int pStep,
        final int pStepFast,
        final String format,
        final int imGuiSliderFlags
    );

    boolean inputScalar(final String label, final int dataType, final KLongReferenceValue pData);

    boolean inputScalar(final String label, final int dataType, final KLongReferenceValue pData, final long pStep);

    boolean inputScalar(
        final String label,
        final int dataType,
        final KLongReferenceValue pData,
        final long pStep,
        final long pStepFast
    );

    boolean inputScalar(
        final String label,
        final int dataType,
        final KLongReferenceValue pData,
        final long pStep,
        final long pStepFast,
        final String format
    );

    boolean inputScalar(
        final String label,
        final int dataType,
        final KLongReferenceValue pData,
        final long pStep,
        final long pStepFast,
        final String format,
        final int imGuiSliderFlags
    );

    boolean inputScalar(final String label, final int dataType, final KFloatReferenceValue pData);

    boolean inputScalar(final String label, final int dataType, final KFloatReferenceValue pData, final float pStep);

    boolean inputScalar(
        final String label,
        final int dataType,
        final KFloatReferenceValue pData,
        final float pStep,
        final float pStepFast
    );

    boolean inputScalar(
        final String label,
        final int dataType,
        final KFloatReferenceValue pData,
        final float pStep,
        final float pStepFast,
        final String format
    );

    boolean inputScalar(
        final String label,
        final int dataType,
        final KFloatReferenceValue pData,
        final float pStep,
        final float pStepFast,
        final String format,
        final int imGuiSliderFlags
    );

    boolean inputScalar(final String label, final int dataType, final KDoubleReferenceValue pData);

    boolean inputScalar(final String label, final int dataType, final KDoubleReferenceValue pData, final double pStep);

    boolean inputScalar(
        final String label,
        final int dataType,
        final KDoubleReferenceValue pData,
        final double pStep,
        final double pStepFast
    );

    boolean inputScalar(
        final String label,
        final int dataType,
        final KDoubleReferenceValue pData,
        final double pStep,
        final double pStepFast,
        final String format
    );

    boolean inputScalar(
        final String label,
        final int dataType,
        final KDoubleReferenceValue pData,
        final double pStep,
        final double pStepFast,
        final String format,
        final int imGuiSliderFlags
    );

    boolean inputScalarN(final String label, final short[] pData, final int components);

    boolean inputScalarN(final String label, final short[] pData, final int components, final short pStep);

    boolean inputScalarN(
        final String label,
        final short[] pData,
        final int components,
        final short pStep,
        final short pStepFast
    );

    boolean inputScalarN(
        final String label,
        final short[] pData,
        final int components,
        final short pStep,
        final short pStepFast,
        final String format
    );

    boolean inputScalarN(
        final String label,
        final short[] pData,
        final int components,
        final short pStep,
        final short pStepFast,
        final String format,
        final int imGuiSliderFlags
    );

    boolean inputScalarN(final String label, final int[] pData, final int components);

    boolean inputScalarN(final String label, final int[] pData, final int components, final int pStep);

    boolean inputScalarN(
        final String label,
        final int[] pData,
        final int components,
        final int pStep,
        final int pStepFast
    );

    boolean inputScalarN(
        final String label,
        final int[] pData,
        final int components,
        final int pStep,
        final int pStepFast,
        final String format
    );

    boolean inputScalarN(
        final String label,
        final int[] pData,
        final int components,
        final int pStep,
        final int pStepFast,
        final String format,
        final int imGuiSliderFlags
    );

    boolean inputScalarN(final String label, final long[] pData, final int components);

    boolean inputScalarN(final String label, final long[] pData, final int components, final long pStep);

    boolean inputScalarN(
        final String label,
        final long[] pData,
        final int components,
        final long pStep,
        final long pStepFast
    );

    boolean inputScalarN(
        final String label,
        final long[] pData,
        final int components,
        final long pStep,
        final long pStepFast,
        final String format
    );

    boolean inputScalarN(
        final String label,
        final long[] pData,
        final int components,
        final long pStep,
        final long pStepFast,
        final String format,
        final int imGuiSliderFlags
    );

    boolean inputScalarN(final String label, final float[] pData, final int components);

    boolean inputScalarN(final String label, final float[] pData, final int components, final float pStep);

    boolean inputScalarN(
        final String label,
        final float[] pData,
        final int components,
        final float pStep,
        final float pStepFast
    );

    boolean inputScalarN(
        final String label,
        final float[] pData,
        final int components,
        final float pStep,
        final float pStepFast,
        final String format
    );

    boolean inputScalarN(
        final String label,
        final float[] pData,
        final int components,
        final float pStep,
        final float pStepFast,
        final String format,
        final int imGuiSliderFlags
    );

    boolean inputScalarN(final String label, final double[] pData, final int components);

    boolean inputScalarN(final String label, final double[] pData, final int components, final double pStep);

    boolean inputScalarN(
        final String label,
        final double[] pData,
        final int components,
        final double pStep,
        final double pStepFast
    );

    boolean inputScalarN(
        final String label,
        final double[] pData,
        final int components,
        final double pStep,
        final double pStepFast,
        final String format
    );

    boolean inputScalarN(
        final String label,
        final double[] pData,
        final int components,
        final double pStep,
        final double pStepFast,
        final String format,
        final int imGuiSliderFlags
    );

    boolean inputScalarN(final String label, final int dataType, final short[] pData, final int components);

    boolean inputScalarN(
        final String label,
        final int dataType,
        final short[] pData,
        final int components,
        final short pStep
    );

    boolean inputScalarN(
        final String label,
        final int dataType,
        final short[] pData,
        final int components,
        final short pStep,
        final short pStepFast
    );

    boolean inputScalarN(
        final String label,
        final int dataType,
        final short[] pData,
        final int components,
        final short pStep,
        final short pStepFast,
        final String format
    );

    boolean inputScalarN(
        final String label,
        final int dataType,
        final short[] pData,
        final int components,
        final short pStep,
        final short pStepFast,
        final String format,
        final int imGuiSliderFlags
    );

    boolean inputScalarN(final String label, final int dataType, final int[] pData, final int components);

    boolean inputScalarN(
        final String label,
        final int dataType,
        final int[] pData,
        final int components,
        final int pStep
    );

    boolean inputScalarN(
        final String label,
        final int dataType,
        final int[] pData,
        final int components,
        final int pStep,
        final int pStepFast
    );

    boolean inputScalarN(
        final String label,
        final int dataType,
        final int[] pData,
        final int components,
        final int pStep,
        final int pStepFast,
        final String format
    );

    boolean inputScalarN(
        final String label,
        final int dataType,
        final int[] pData,
        final int components,
        final int pStep,
        final int pStepFast,
        final String format,
        final int imGuiSliderFlags
    );

    boolean inputScalarN(final String label, final int dataType, final long[] pData, final int components);

    boolean inputScalarN(
        final String label,
        final int dataType,
        final long[] pData,
        final int components,
        final long pStep
    );

    boolean inputScalarN(
        final String label,
        final int dataType,
        final long[] pData,
        final int components,
        final long pStep,
        final long pStepFast
    );

    boolean inputScalarN(
        final String label,
        final int dataType,
        final long[] pData,
        final int components,
        final long pStep,
        final long pStepFast,
        final String format
    );

    boolean inputScalarN(
        final String label,
        final int dataType,
        final long[] pData,
        final int components,
        final long pStep,
        final long pStepFast,
        final String format,
        final int imGuiSliderFlags
    );

    boolean inputScalarN(final String label, final int dataType, final float[] pData, final int components);

    boolean inputScalarN(
        final String label,
        final int dataType,
        final float[] pData,
        final int components,
        final float pStep
    );

    boolean inputScalarN(
        final String label,
        final int dataType,
        final float[] pData,
        final int components,
        final float pStep,
        final float pStepFast
    );

    boolean inputScalarN(
        final String label,
        final int dataType,
        final float[] pData,
        final int components,
        final float pStep,
        final float pStepFast,
        final String format
    );

    boolean inputScalarN(
        final String label,
        final int dataType,
        final float[] pData,
        final int components,
        final float pStep,
        final float pStepFast,
        final String format,
        final int imGuiSliderFlags
    );

    boolean inputScalarN(final String label, final int dataType, final double[] pData, final int components);

    boolean inputScalarN(
        final String label,
        final int dataType,
        final double[] pData,
        final int components,
        final double pStep
    );

    boolean inputScalarN(
        final String label,
        final int dataType,
        final double[] pData,
        final int components,
        final double pStep,
        final double pStepFast
    );

    boolean inputScalarN(
        final String label,
        final int dataType,
        final double[] pData,
        final int components,
        final double pStep,
        final double pStepFast,
        final String format
    );

    boolean inputScalarN(
        final String label,
        final int dataType,
        final double[] pData,
        final int components,
        final double pStep,
        final double pStepFast,
        final String format,
        final int imGuiSliderFlags
    );

    boolean colorEdit3(final String label, final float[] col);

    boolean colorEdit3(final String label, final float[] col, final int imGuiColorEditFlags);

    boolean colorEdit4(final String label, final float[] col);

    boolean colorEdit4(final String label, final float[] col, final int imGuiColorEditFlags);

    boolean colorPicker3(final String label, final float[] col);

    boolean colorPicker3(final String label, final float[] col, final int imGuiColorEditFlags);

    boolean colorPicker4(final String label, final float[] col);

    boolean colorPicker4(final String label, final float[] col, final int imGuiColorEditFlags);

    boolean colorPicker4(final String label, final float[] col, final int imGuiColorEditFlags, final float[] refCol);

    boolean colorPicker4(final String label, final float[] col, final float[] refCol);

    boolean colorButton(final String descId, final KVector4f col);

    boolean colorButton(final String descId, final float colX, final float colY, final float colZ, final float colW);

    boolean colorButton(final String descId, final KVector4f col, final int imGuiColorEditFlags);

    boolean colorButton(
        final String descId,
        final float colX,
        final float colY,
        final float colZ,
        final float colW,
        final int imGuiColorEditFlags
    );

    boolean colorButton(final String descId, final KVector4f col, final int imGuiColorEditFlags, final KVector2f size);

    boolean colorButton(
        final String descId,
        final float colX,
        final float colY,
        final float colZ,
        final float colW,
        final int imGuiColorEditFlags,
        final float sizeX,
        final float sizeY
    );

    boolean colorButton(final String descId, final KVector4f col, final KVector2f size);

    boolean colorButton(
        final String descId,
        final float colX,
        final float colY,
        final float colZ,
        final float colW,
        final float sizeX,
        final float sizeY
    );

    @Deprecated
    boolean colorButton(final String descId, final float[] col);

    @Deprecated
    boolean colorButton(final String descId, final float[] col, final int imGuiColorEditFlags);

    @Deprecated
    boolean colorButton(final String descId, final float[] col, final int imGuiColorEditFlags, final KVector2f size);

    @Deprecated
    boolean colorButton(
        final String descId,
        final float[] col,
        final int imGuiColorEditFlags,
        final float sizeX,
        final float sizeY
    );

    @Deprecated
    boolean colorButton(final String descId, final float[] col, final KVector2f size);

    @Deprecated
    boolean colorButton(final String descId, final float[] col, final float sizeX, final float sizeY);

    void setColorEditOptions(final int imGuiColorEditFlags);

    boolean treeNode(final String label);

    boolean treeNode(final String strId, final String label);

    boolean treeNode(final long ptrId, final String label);

    boolean treeNodeEx(final String label);

    boolean treeNodeEx(final String label, final int flags);

    boolean treeNodeEx(final String strId, final int flags, final String label);

    boolean treeNodeEx(final long ptrId, final int flags, final String label);

    void treePush(final String strId);

    void treePush(final long ptrId);

    void treePop();

    float getTreeNodeToLabelSpacing();

    boolean collapsingHeader(final String label);

    boolean collapsingHeader(final String label, final int imGuiTreeNodeFlags);

    boolean collapsingHeader(final String label, final KBooleanReferenceValue pVisible);

    boolean collapsingHeader(final String label, final KBooleanReferenceValue pVisible, final int imGuiTreeNodeFlags);

    void setNextItemOpen(final boolean isOpen);

    void setNextItemOpen(final boolean isOpen, final int cond);

    boolean selectable(final String label);

    boolean selectable(final String label, final boolean selected);

    boolean selectable(final String label, final boolean selected, final int imGuiSelectableFlags);

    boolean selectable(final String label, final boolean selected, final int imGuiSelectableFlags, final KVector2f size);

    boolean selectable(
        final String label,
        final boolean selected,
        final int imGuiSelectableFlags,
        final float sizeX,
        final float sizeY
    );

    boolean selectable(final String label, final int imGuiSelectableFlags, final KVector2f size);

    boolean selectable(final String label, final int imGuiSelectableFlags, final float sizeX, final float sizeY);

    boolean selectable(final String label, final KVector2f size);

    boolean selectable(final String label, final float sizeX, final float sizeY);

    boolean selectable(final String label, final boolean selected, final KVector2f size);

    boolean selectable(final String label, final boolean selected, final float sizeX, final float sizeY);

    boolean selectable(final String label, final KBooleanReferenceValue pSelected);

    boolean selectable(final String label, final KBooleanReferenceValue pSelected, final int imGuiSelectableFlags);

    boolean selectable(
        final String label,
        final KBooleanReferenceValue pSelected,
        final int imGuiSelectableFlags,
        final KVector2f size
    );

    boolean selectable(
        final String label,
        final KBooleanReferenceValue pSelected,
        final int imGuiSelectableFlags,
        final float sizeX,
        final float sizeY
    );

    boolean selectable(final String label, final KBooleanReferenceValue pSelected, final KVector2f size);

    boolean selectable(final String label, final KBooleanReferenceValue pSelected, final float sizeX, final float sizeY);

    boolean beginListBox(final String label);

    boolean beginListBox(final String label, final KVector2f size);

    boolean beginListBox(final String label, final float sizeX, final float sizeY);

    void endListBox();

    void listBox(final String label, final KIntReferenceValue currentItem, final String[] items);

    void listBox(final String label, final KIntReferenceValue currentItem, final String[] items, final int heightInItems);

    void plotLines(final String label, final float[] values, final int valuesCount);

    void plotLines(final String label, final float[] values, final int valuesCount, final int valuesOffset);

    void plotLines(
        final String label,
        final float[] values,
        final int valuesCount,
        final int valuesOffset,
        final String overlayText
    );

    void plotLines(
        final String label,
        final float[] values,
        final int valuesCount,
        final int valuesOffset,
        final String overlayText,
        final float scaleMin
    );

    void plotLines(
        final String label,
        final float[] values,
        final int valuesCount,
        final int valuesOffset,
        final String overlayText,
        final float scaleMin,
        final float scaleMax
    );

    void plotLines(
        final String label,
        final float[] values,
        final int valuesCount,
        final int valuesOffset,
        final String overlayText,
        final float scaleMin,
        final float scaleMax,
        final KVector2f graphSize
    );

    void plotLines(
        final String label,
        final float[] values,
        final int valuesCount,
        final int valuesOffset,
        final String overlayText,
        final float scaleMin,
        final float scaleMax,
        final float graphSizeX,
        final float graphSizeY
    );

    void plotLines(
        final String label,
        final float[] values,
        final int valuesCount,
        final int valuesOffset,
        final String overlayText,
        final float scaleMin,
        final float scaleMax,
        final KVector2f graphSize,
        final int stride
    );

    void plotLines(
        final String label,
        final float[] values,
        final int valuesCount,
        final int valuesOffset,
        final String overlayText,
        final float scaleMin,
        final float scaleMax,
        final float graphSizeX,
        final float graphSizeY,
        final int stride
    );

    void plotLines(
        final String label,
        final float[] values,
        final int valuesCount,
        final String overlayText,
        final float scaleMin,
        final float scaleMax,
        final KVector2f graphSize,
        final int stride
    );

    void plotLines(
        final String label,
        final float[] values,
        final int valuesCount,
        final String overlayText,
        final float scaleMin,
        final float scaleMax,
        final float graphSizeX,
        final float graphSizeY,
        final int stride
    );

    void plotLines(
        final String label,
        final float[] values,
        final int valuesCount,
        final float scaleMin,
        final float scaleMax,
        final KVector2f graphSize,
        final int stride
    );

    void plotLines(
        final String label,
        final float[] values,
        final int valuesCount,
        final float scaleMin,
        final float scaleMax,
        final float graphSizeX,
        final float graphSizeY,
        final int stride
    );

    void plotLines(
        final String label,
        final float[] values,
        final int valuesCount,
        final int valuesOffset,
        final float scaleMin,
        final float scaleMax,
        final KVector2f graphSize,
        final int stride
    );

    void plotLines(
        final String label,
        final float[] values,
        final int valuesCount,
        final int valuesOffset,
        final float scaleMin,
        final float scaleMax,
        final float graphSizeX,
        final float graphSizeY,
        final int stride
    );

    void plotLines(
        final String label,
        final float[] values,
        final int valuesCount,
        final int valuesOffset,
        final String overlayText,
        final float scaleMin,
        final float scaleMax,
        final int stride
    );

    void plotHistogram(final String label, final float[] values, final int valuesCount);

    void plotHistogram(final String label, final float[] values, final int valuesCount, final int valuesOffset);

    void plotHistogram(
        final String label,
        final float[] values,
        final int valuesCount,
        final int valuesOffset,
        final String overlayText
    );

    void plotHistogram(
        final String label,
        final float[] values,
        final int valuesCount,
        final int valuesOffset,
        final String overlayText,
        final float scaleMin
    );

    void plotHistogram(
        final String label,
        final float[] values,
        final int valuesCount,
        final int valuesOffset,
        final String overlayText,
        final float scaleMin,
        final float scaleMax
    );

    void plotHistogram(
        final String label,
        final float[] values,
        final int valuesCount,
        final int valuesOffset,
        final String overlayText,
        final float scaleMin,
        final float scaleMax,
        final KVector2f graphSize
    );

    void plotHistogram(
        final String label,
        final float[] values,
        final int valuesCount,
        final int valuesOffset,
        final String overlayText,
        final float scaleMin,
        final float scaleMax,
        final float graphSizeX,
        final float graphSizeY
    );

    void plotHistogram(
        final String label,
        final float[] values,
        final int valuesCount,
        final int valuesOffset,
        final String overlayText,
        final float scaleMin,
        final float scaleMax,
        final KVector2f graphSize,
        final int stride
    );

    void plotHistogram(
        final String label,
        final float[] values,
        final int valuesCount,
        final int valuesOffset,
        final String overlayText,
        final float scaleMin,
        final float scaleMax,
        final float graphSizeX,
        final float graphSizeY,
        final int stride
    );

    void plotHistogram(
        final String label,
        final float[] values,
        final int valuesCount,
        final String overlayText,
        final float scaleMin,
        final float scaleMax,
        final KVector2f graphSize,
        final int stride
    );

    void plotHistogram(
        final String label,
        final float[] values,
        final int valuesCount,
        final String overlayText,
        final float scaleMin,
        final float scaleMax,
        final float graphSizeX,
        final float graphSizeY,
        final int stride
    );

    void plotHistogram(
        final String label,
        final float[] values,
        final int valuesCount,
        final float scaleMin,
        final float scaleMax,
        final KVector2f graphSize,
        final int stride
    );

    void plotHistogram(
        final String label,
        final float[] values,
        final int valuesCount,
        final float scaleMin,
        final float scaleMax,
        final float graphSizeX,
        final float graphSizeY,
        final int stride
    );

    void plotHistogram(
        final String label,
        final float[] values,
        final int valuesCount,
        final int valuesOffset,
        final float scaleMin,
        final float scaleMax,
        final KVector2f graphSize,
        final int stride
    );

    void plotHistogram(
        final String label,
        final float[] values,
        final int valuesCount,
        final int valuesOffset,
        final float scaleMin,
        final float scaleMax,
        final float graphSizeX,
        final float graphSizeY,
        final int stride
    );

    void plotHistogram(
        final String label,
        final float[] values,
        final int valuesCount,
        final int valuesOffset,
        final String overlayText,
        final float scaleMin,
        final float scaleMax,
        final int stride
    );

    void value(final String prefix, final Number value);

    void value(final String prefix, final float value, String floatFormat);

    boolean beginMenuBar();

    void endMenuBar();

    boolean beginMainMenuBar();

    void endMainMenuBar();

    boolean beginMenu(final String label);

    boolean beginMenu(final String label, final boolean enabled);

    void endMenu();

    boolean menuItem(final String label);

    boolean menuItem(final String label, final boolean selected);

    boolean menuItem(final String label, final boolean selected, final boolean enabled);

    boolean menuItem(final String label, final String shortcut);

    boolean menuItem(final String label, final String shortcut, final boolean selected);

    boolean menuItem(final String label, final String shortcut, final boolean selected, final boolean enabled);

    boolean menuItem(final String label, final String shortcut, final KBooleanReferenceValue pSelected);

    boolean menuItem(final String label, final String shortcut, final KBooleanReferenceValue pSelected, final boolean enabled);

    void beginTooltip();

    void endTooltip();

    void setTooltip(final String text);

    boolean beginItemTooltip();

    void setItemTooltip(final String text);

    boolean beginPopup(final String strId);

    boolean beginPopup(final String strId, final int imGuiWindowFlags);

    boolean beginPopupModal(final String name);

    boolean beginPopupModal(final String name, final KBooleanReferenceValue pOpen);

    boolean beginPopupModal(final String name, final KBooleanReferenceValue pOpen, final int imGuiWindowFlags);

    boolean beginPopupModal(final String name, final int imGuiWindowFlags);

    void endPopup();

    void openPopup(final String strId);

    void openPopup(final String strId, final int imGuiPopupFlags);

    void openPopup(final int id);

    void openPopup(final int id, final int imGuiPopupFlags);

    void openPopupOnItemClick();

    void openPopupOnItemClick(final String strId);

    void openPopupOnItemClick(final String strId, final int imGuiPopupFlags);

    void openPopupOnItemClick(final int imGuiPopupFlags);

    void closeCurrentPopup();

    boolean beginPopupContextItem();

    boolean beginPopupContextItem(final String strId);

    boolean beginPopupContextItem(final String strId, final int imGuiPopupFlags);

    boolean beginPopupContextItem(final int imGuiPopupFlags);

    boolean beginPopupContextWindow();

    boolean beginPopupContextWindow(final String strId);

    boolean beginPopupContextWindow(final String strId, final int imGuiPopupFlags);

    boolean beginPopupContextWindow(final int imGuiPopupFlags);

    boolean beginPopupContextVoid();

    boolean beginPopupContextVoid(final String strId);

    boolean beginPopupContextVoid(final String strId, final int imGuiPopupFlags);

    boolean beginPopupContextVoid(final int imGuiPopupFlags);

    boolean isPopupOpen(final String strId);

    boolean isPopupOpen(final String strId, final int imGuiPopupFlags);

    boolean beginTable(final String id, final int columns);

    boolean beginTable(final String id, final int columns, final int imGuiTableFlags);

    boolean beginTable(final String id, final int columns, final int imGuiTableFlags, final KVector2f outerSize);

    boolean beginTable(
        final String id,
        final int columns,
        final int imGuiTableFlags,
        final float outerSizeX,
        final float outerSizeY
    );

    boolean beginTable(
        final String id,
        final int columns,
        final int imGuiTableFlags,
        final KVector2f outerSize,
        final float innerWidth
    );

    boolean beginTable(
        final String id,
        final int columns,
        final int imGuiTableFlags,
        final float outerSizeX,
        final float outerSizeY,
        final float innerWidth
    );

    boolean beginTable(final String id, final int columns, final KVector2f outerSize, final float innerWidth);

    boolean beginTable(
        final String id,
        final int columns,
        final float outerSizeX,
        final float outerSizeY,
        final float innerWidth
    );

    boolean beginTable(final String id, final int columns, final float innerWidth);

    boolean beginTable(final String id, final int columns, final int imGuiTableFlags, final float innerWidth);

    void endTable();

    void tableNextRow();

    void tableNextRow(final int imGuiTableRowFlags);

    void tableNextRow(final int imGuiTableRowFlags, final float minRowHeight);

    void tableNextRow(final float minRowHeight);

    boolean tableNextColumn();

    boolean tableSetColumnIndex(final int columnN);

    void tableSetupColumn(final String label);

    void tableSetupColumn(final String label, final int imGuiTableColumnFlags);

    void tableSetupColumn(final String label, final int imGuiTableColumnFlags, final float initWidthOrWeight);

    void tableSetupColumn(
        final String label,
        final int imGuiTableColumnFlags,
        final float initWidthOrWeight,
        final int userId
    );

    void tableSetupColumn(final String label, final float initWidthOrWeight, final int userId);

    void tableSetupColumn(final String label, final int imGuiTableColumnFlags, final int userId);

    void tableSetupScrollFreeze(final int cols, final int rows);

    void tableHeader(final String label);

    void tableHeadersRow();

    void tableAngledHeadersRow();

    KImGuiTableSortSpecs tableGetSortSpecs();

    int tableGetColumnCount();

    int tableGetColumnIndex();

    int tableGetRowIndex();

    String tableGetColumnName();

    String tableGetColumnName(final int columnN);

    int tableGetColumnFlags();

    int tableGetColumnFlags(final int columnN);

    void tableSetColumnEnabled(final int columnN, final boolean value);

    int tableGetHoveredColumn();

    void tableSetBgColor(final int imGuiTableBgTarget, final int color);

    void tableSetBgColor(final int imGuiTableBgTarget, final int color, final int columnN);

    void columns();

    void columns(final int count);

    void columns(final int count, final String id);

    void columns(final int count, final String id, final boolean border);

    void columns(final String id, final boolean border);

    void columns(final boolean border);

    void columns(final int count, final boolean border);

    void nextColumn();

    int getColumnIndex();

    float getColumnWidth();

    float getColumnWidth(final int columnIndex);

    void setColumnWidth(final int columnIndex, final float width);

    float getColumnOffset();

    float getColumnOffset(final int columnIndex);

    void setColumnOffset(final int columnIndex, final float offsetX);

    int getColumnsCount();

    boolean beginTabBar(final String strId);

    boolean beginTabBar(final String strId, final int imGuiTabBarFlags);

    void endTabBar();

    boolean beginTabItem(final String label);

    boolean beginTabItem(final String label, final KBooleanReferenceValue pOpen);

    boolean beginTabItem(final String label, final KBooleanReferenceValue pOpen, final int imGuiTabItemFlags);

    boolean beginTabItem(final String label, final int imGuiTabItemFlags);

    void endTabItem();

    boolean tabItemButton(final String label);

    boolean tabItemButton(final String label, final int imGuiTabItemFlags);

    void setTabItemClosed(final String tabOrDockedWindowLabel);

    int dockSpace(final int dockspaceId);

    int dockSpace(final int dockspaceId, final KVector2f size);

    int dockSpace(final int dockspaceId, final float sizeX, final float sizeY);

    int dockSpace(final int dockspaceId, final KVector2f size, final int imGuiDockNodeFlags);

    int dockSpace(final int dockspaceId, final float sizeX, final float sizeY, final int imGuiDockNodeFlags);

    int dockSpace(
        final int dockspaceId,
        final KVector2f size,
        final int imGuiDockNodeFlags,
        final KImGuiWindowClass windowClass
    );

    int dockSpace(
        final int dockspaceId,
        final float sizeX,
        final float sizeY,
        final int imGuiDockNodeFlags,
        final KImGuiWindowClass windowClass
    );

    int dockSpace(final int dockspaceId, final int imGuiDockNodeFlags, final KImGuiWindowClass windowClass);

    int dockSpace(final int dockspaceId, final KImGuiWindowClass windowClass);

    int dockSpace(final int dockspaceId, final KVector2f size, final KImGuiWindowClass windowClass);

    int dockSpace(final int dockspaceId, final float sizeX, final float sizeY, final KImGuiWindowClass windowClass);

    int dockSpaceOverViewport();

    int dockSpaceOverViewport(final int dockspaceId);

    int dockSpaceOverViewport(final int dockspaceId, final KImGuiViewport viewport);

    int dockSpaceOverViewport(final int dockspaceId, final KImGuiViewport viewport, final int imGuiDockNodeFlags);

    int dockSpaceOverViewport(
        final int dockspaceId,
        final KImGuiViewport viewport,
        final int imGuiDockNodeFlags,
        final KImGuiWindowClass windowClass
    );

    int dockSpaceOverViewport(
        final KImGuiViewport viewport,
        final int imGuiDockNodeFlags,
        final KImGuiWindowClass windowClass
    );

    int dockSpaceOverViewport(final int dockspaceId, final KImGuiViewport viewport, final KImGuiWindowClass windowClass);

    void setNextWindowDockID(final int dockId);

    void setNextWindowDockID(final int dockId, final int imGuiCond);

    void setNextWindowClass(final KImGuiWindowClass windowClass);

    int getWindowDockID();

    boolean isWindowDocked();

    void logToTTY();

    void logToTTY(final int autoOpenDepth);

    void logToFile();

    void logToFile(final int autoOpenDepth);

    void logToFile(final int autoOpenDepth, final String filename);

    void logToFile(final String filename);

    void logToClipboard();

    void logToClipboard(final int autoOpenDepth);

    void logFinish();

    void logButtons();

    void logText(final String text);

    boolean beginDragDropSource();

    boolean beginDragDropSource(final int imGuiDragDropFlags);

    boolean setDragDropPayload(final String dataType, final Object payload);

    boolean setDragDropPayload(final String dataType, final Object payload, final int imGuiCond);

    boolean setDragDropPayload(final Object payload);

    boolean setDragDropPayload(final Object payload, final int imGuiCond);

    void endDragDropSource();

    boolean beginDragDropTarget();

    <T> T acceptDragDropPayload(final String dataType);

    <T> T acceptDragDropPayload(final String dataType, final Class<T> aClass);

    <T> T acceptDragDropPayload(final String dataType, final int imGuiDragDropFlags);
    
    <T> T acceptDragDropPayload(final String dataType, final int imGuiDragDropFlags, final Class<T> aClass);

    <T> T acceptDragDropPayload(final Class<T> aClass);

    <T> T acceptDragDropPayload(final Class<T> aClass, final int imGuiDragDropFlags);

    void endDragDropTarget();
    
    <T> T getDragDropPayload();
    
    <T> T getDragDropPayload(final String dataType);

    <T> T getDragDropPayload(final Class<T> aClass);

    void beginDisabled();

    void beginDisabled(final boolean disabled);

    void endDisabled();

    void pushClipRect(final KVector2f clipRectMin, final KVector2f clipRectMax, final boolean intersectWithCurrentClipRect);

    void pushClipRect(
        final float clipRectMinX,
        final float clipRectMinY,
        final float clipRectMaxX,
        final float clipRectMaxY,
        final boolean intersectWithCurrentClipRect
    );

    void popClipRect();

    void setItemDefaultFocus();

    void setKeyboardFocusHere();

    void setKeyboardFocusHere(final int offset);

    void setNextItemAllowOverlap();

    boolean isItemHovered();

    boolean isItemHovered(final int imGuiHoveredFlags);

    boolean isItemActive();

    boolean isItemFocused();

    boolean isItemClicked();

    boolean isItemClicked(final int mouseButton);

    boolean isItemVisible();

    boolean isItemEdited();

    boolean isItemActivated();

    boolean isItemDeactivated();

    boolean isItemDeactivatedAfterEdit();

    boolean isItemToggledOpen();

    boolean isAnyItemHovered();

    boolean isAnyItemActive();

    boolean isAnyItemFocused();

    int getItemID();

    KVector2f getItemRectMin();

    float getItemRectMinX();

    float getItemRectMinY();

    void getItemRectMin(final KVector2f dst);

    KVector2f getItemRectMax();

    float getItemRectMaxX();

    float getItemRectMaxY();

    void getItemRectMax(final KVector2f dst);

    KVector2f getItemRectSize();

    float getItemRectSizeX();

    float getItemRectSizeY();

    void getItemRectSize(final KVector2f dst);

    KImGuiViewport getMainViewport();

    KImDrawList getBackgroundDrawList();

    KImDrawList getBackgroundDrawList(final KImGuiViewport viewport);

    KImDrawList getForegroundDrawList();

    KImDrawList getForegroundDrawList(final KImGuiViewport viewport);

    boolean isRectVisible(final KVector2f size);

    boolean isRectVisible(final float sizeX, final float sizeY);

    boolean isRectVisible(final KVector2f rectMin, final KVector2f rectMax);

    boolean isRectVisible(final float rectMinX, final float rectMinY, final float rectMaxX, final float rectMaxY);

    double getTime();

    int getFrameCount();

    String getStyleColorName(final int imGuiColIdx);

    KImGuiStorage getStateStorage();

    void setStateStorage(final KImGuiStorage storage);

    KVector2f calcTextSize(final String text);

    float calcTextSizeX(final String text);

    float calcTextSizeY(final String text);

    void calcTextSize(final KVector2f dst, final String text);

    KVector2f calcTextSize(final String text, final boolean hideTextAfterDoubleHash);

    float calcTextSizeX(final String text, final boolean hideTextAfterDoubleHash);

    float calcTextSizeY(final String text, final boolean hideTextAfterDoubleHash);

    void calcTextSize(final KVector2f dst, final String text, final boolean hideTextAfterDoubleHash);

    KVector2f calcTextSize(final String text, final boolean hideTextAfterDoubleHash, final float wrapWidth);

    float calcTextSizeX(final String text, final boolean hideTextAfterDoubleHash, final float wrapWidth);

    float calcTextSizeY(final String text, final boolean hideTextAfterDoubleHash, final float wrapWidth);

    void calcTextSize(
        final KVector2f dst,
        final String text,
        final boolean hideTextAfterDoubleHash,
        final float wrapWidth
    );

    KVector2f calcTextSize(final String text, final float wrapWidth);

    float calcTextSizeX(final String text, final float wrapWidth);

    float calcTextSizeY(final String text, final float wrapWidth);

    void calcTextSize(final KVector2f dst, final String text, final float wrapWidth);

    KVector4f colorConvertU32ToFloat4(final int in);

    float colorConvertU32ToFloat4X(final int in);

    float colorConvertU32ToFloat4Y(final int in);

    float colorConvertU32ToFloat4Z(final int in);

    float colorConvertU32ToFloat4W(final int in);

    void colorConvertU32ToFloat4(final KVector4f dst, final int in);

    int colorConvertFloat4ToU32(final KVector4f in);

    int colorConvertFloat4ToU32(final float inX, final float inY, final float inZ, final float inW);

    void colorConvertRGBtoHSV(float[] rgb, float[] hsv);

    void colorConvertHSVtoRGB(float[] hsv, float[] rgb);

    boolean isKeyDown(final int key);

    boolean isKeyPressed(final int key);

    boolean isKeyPressed(final int key, final boolean repeat);

    boolean isKeyReleased(final int key);

    boolean isKeyChordPressed(final int keyChord);

    boolean getKeyPressedAmount(final int key, final float repeatDelay, final float rate);

    String getKeyName(final int key);

    void setNextFrameWantCaptureKeyboard(final boolean wantCaptureKeyboard);

    boolean shortcut(final int keyChord);

    boolean shortcut(final int keyChord, final int flags);

    void setNextItemShortcut(final int keyChord);

    void setNextItemShortcut(final int keyChord, final int flags);

    boolean isMouseDown(final int button);

    boolean isMouseClicked(final int button);

    boolean isMouseClicked(final int button, final boolean repeat);

    boolean isMouseReleased(final int button);

    boolean isMouseDoubleClicked(final int button);

    int getMouseClickedCount(final int button);

    boolean isMouseHoveringRect(final KVector2f rMin, final KVector2f rMax);

    boolean isMouseHoveringRect(final float rMinX, final float rMinY, final float rMaxX, final float rMaxY);

    boolean isMouseHoveringRect(final KVector2f rMin, final KVector2f rMax, final boolean clip);

    boolean isMouseHoveringRect(
        final float rMinX,
        final float rMinY,
        final float rMaxX,
        final float rMaxY,
        final boolean clip
    );

    boolean isMousePosValid();

    boolean isMousePosValid(final KVector2f mousePos);

    boolean isMousePosValid(final float mousePosX, final float mousePosY);

    boolean isAnyMouseDown();

    KVector2f getMousePos();

    float getMousePosX();

    float getMousePosY();

    void getMousePos(final KVector2f dst);

    KVector2f getMousePosOnOpeningCurrentPopup();

    float getMousePosOnOpeningCurrentPopupX();

    float getMousePosOnOpeningCurrentPopupY();

    void getMousePosOnOpeningCurrentPopup(final KVector2f dst);

    boolean isMouseDragging(final int button);

    boolean isMouseDragging(final int button, final float lockThreshold);

    KVector2f getMouseDragDelta();

    float getMouseDragDeltaX();

    float getMouseDragDeltaY();

    void getMouseDragDelta(final KVector2f dst);

    KVector2f getMouseDragDelta(final int button);

    float getMouseDragDeltaX(final int button);

    float getMouseDragDeltaY(final int button);

    void getMouseDragDelta(final KVector2f dst, final int button);

    KVector2f getMouseDragDelta(final int button, final float lockThreshold);

    float getMouseDragDeltaX(final int button, final float lockThreshold);

    float getMouseDragDeltaY(final int button, final float lockThreshold);

    void getMouseDragDelta(final KVector2f dst, final int button, final float lockThreshold);

    void resetMouseDragDelta();

    void resetMouseDragDelta(final int button);

    int getMouseCursor();

    void setMouseCursor(final int type);

    void setNextFrameWantCaptureMouse(final boolean wantCaptureMouse);

    String getClipboardText();

    void setClipboardText(final String text);

    void loadIniSettingsFromDisk(final String iniFilename);

    void loadIniSettingsFromMemory(final String iniData);

    void loadIniSettingsFromMemory(final String iniData, final int iniSize);

    void saveIniSettingsToDisk(final String iniFilename);

    String saveIniSettingsToMemory();

    String saveIniSettingsToMemory(final long outIniSize);

    void debugTextEncoding(final String text);

    void debugFlashStyleColor(final int idx);

    void debugStartItemPicker();

    boolean debugCheckVersionAndDataLayout(
        final String versionStr,
        final int szIo,
        final int szStyle,
        final int szVec2,
        final int szVec4,
        final int szDrawVert,
        final int szDrawIdx
    );

    KImGuiPlatformIo getPlatformIO();

    void updatePlatformWindows();

    void renderPlatformWindowsDefault();

    void destroyPlatformWindows();

    KImGuiViewport findViewportByID(final int imGuiID);

    KImGuiViewport findViewportByPlatformHandle(final long platformHandle);

}

