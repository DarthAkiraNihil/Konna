package io.github.darthakiranihil.konna.libfrontend.imgui;

import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.struct.KVector4f;
import io.github.darthakiranihil.konna.core.struct.ref.*;

@SuppressWarnings({ "unused", "UnusedReturnValue" })
public interface KImGui {

    void setAssertCallback(KImAssertCallback callback);

    KImGuiContext createContext();

    KImGuiContext createContext(KImFontAtlas sharedFontAtlas);

    void destroyContext();

    void destroyContext(KImGuiContext ctx);

    KImGuiContext getCurrentContext();

    void setCurrentContext(KImGuiContext ctx);

    KImGuiIo getIO();

    KImGuiStyle getStyle();

    void newFrame();

    void endFrame();

    void render();

    KImDrawData getDrawData();

    void showDemoWindow();

    void showDemoWindow(KBooleanReferenceValue pOpen);

    void showMetricsWindow();

    void showMetricsWindow(KBooleanReferenceValue pOpen);

    void showDebugLogWindow();

    void showDebugLogWindow(KBooleanReferenceValue pOpen);

    void showIDStackToolWindow();

    void showIDStackToolWindow(KBooleanReferenceValue pOpen);

    void showAboutWindow();

    void showAboutWindow(KBooleanReferenceValue pOpen);

    void showStyleEditor();

    void showStyleEditor(KImGuiStyle ref);

    boolean showStyleSelector(String label);

    void showFontSelector(String label);

    void showUserGuide();

    String getVersion();

    void styleColorsDark();

    void styleColorsDark(KImGuiStyle style);

    void styleColorsLight();

    void styleColorsLight(KImGuiStyle style);

    void styleColorsClassic();

    void styleColorsClassic(KImGuiStyle style);

    boolean begin(String title);

    boolean begin(String title, KBooleanReferenceValue pOpen);

    boolean begin(String title, KBooleanReferenceValue pOpen, int imGuiWindowFlags);

    boolean begin(String title, int imGuiWindowFlags);

    void end();

    boolean beginChild(String strId);

    boolean beginChild(String strId, KVector2f size);

    boolean beginChild(String strId, float sizeX, float sizeY);

    boolean beginChild(String strId, KVector2f size, int childFlags);

    boolean beginChild(String strId, float sizeX, float sizeY, int childFlags);

    boolean beginChild(String strId, KVector2f size, int childFlags, int windowFlags);

    boolean beginChild(
        String strId,
        float sizeX,
        float sizeY,
        int childFlags,
        int windowFlags
    );

    boolean beginChild(String strId, int childFlags, int windowFlags);

    boolean beginChild(String strId, int windowFlags);

    boolean beginChild(int id);

    boolean beginChild(int id, KVector2f size);

    boolean beginChild(int id, float sizeX, float sizeY);

    boolean beginChild(int id, KVector2f size, int childFlags);

    boolean beginChild(int id, float sizeX, float sizeY, int childFlags);

    boolean beginChild(int id, KVector2f size, int childFlags, int windowFlags);

    boolean beginChild(int id, float sizeX, float sizeY, int childFlags, int windowFlags);

    boolean beginChild(int id, int childFlags, int windowFlags);

    boolean beginChild(int id, int windowFlags);

    boolean beginChild(String strId, KVector2f size, boolean border);

    boolean beginChild(String strId, float sizeX, float sizeY, boolean border);

    boolean beginChild(String strId, KVector2f size, boolean border, int windowFlags);

    boolean beginChild(
        String strId,
        float sizeX,
        float sizeY,
        boolean border,
        int windowFlags
    );

    void endChild();

    boolean isWindowAppearing();

    boolean isWindowCollapsed();

    void setWindowCollapsed(boolean collapsed);

    boolean isWindowFocused();

    boolean isWindowFocused(int imGuiFocusedFlags);

    boolean isWindowHovered();

    boolean isWindowHovered(int imGuiHoveredFlags);

    KImDrawList getWindowDrawList();

    float getWindowDpiScale();

    KVector2f getWindowPos();

    void setWindowPos(KVector2f pos);

    float getWindowPosX();

    float getWindowPosY();

    void getWindowPos(KVector2f dst);

    KVector2f getWindowSize();

    void setWindowSize(KVector2f size);

    float getWindowSizeX();

    float getWindowSizeY();

    void getWindowSize(KVector2f dst);

    float getWindowWidth();

    float getWindowHeight();

    KImGuiViewport getWindowViewport();

    void setNextWindowPos(KVector2f pos);

    void setNextWindowPos(float posX, float posY);

    void setNextWindowPos(KVector2f pos, int cond);

    void setNextWindowPos(float posX, float posY, int cond);

    void setNextWindowPos(KVector2f pos, int cond, KVector2f pivot);

    void setNextWindowPos(float posX, float posY, int cond, float pivotX, float pivotY);

    void setNextWindowPos(KVector2f pos, KVector2f pivot);

    void setNextWindowPos(float posX, float posY, float pivotX, float pivotY);

    void setNextWindowSize(KVector2f size);

    void setNextWindowSize(float sizeX, float sizeY);

    void setNextWindowSize(KVector2f size, int cond);

    void setNextWindowSize(float sizeX, float sizeY, int cond);

    void setNextWindowSizeConstraints(KVector2f sizeMin, KVector2f sizeMax);

    void setNextWindowSizeConstraints(
        float sizeMinX,
        float sizeMinY,
        float sizeMaxX,
        float sizeMaxY
    );

    void setNextWindowContentSize(KVector2f size);

    void setNextWindowContentSize(float sizeX, float sizeY);

    void setNextWindowCollapsed(boolean collapsed);

    void setNextWindowCollapsed(boolean collapsed, int cond);

    void setNextWindowFocus();

    void setNextWindowScroll(KVector2f scroll);

    void setNextWindowScroll(float scrollX, float scrollY);

    void setNextWindowBgAlpha(float alpha);

    void setNextWindowViewport(int viewportId);

    void setWindowPos(float posX, float posY);

    void setWindowPos(KVector2f pos, int cond);

    void setWindowPos(float posX, float posY, int cond);

    void setWindowSize(float sizeX, float sizeY);

    void setWindowSize(KVector2f size, int cond);

    void setWindowSize(float sizeX, float sizeY, int cond);

    void setWindowCollapsed(boolean collapsed, int cond);

    void setWindowFocus();

    void setWindowFontScale(float scale);

    void setWindowPos(String name, KVector2f pos);

    void setWindowPos(String name, float posX, float posY);

    void setWindowPos(String name, KVector2f pos, int cond);

    void setWindowPos(String name, float posX, float posY, int cond);

    void setWindowSize(String name, KVector2f size);

    void setWindowSize(String name, float sizeX, float sizeY);

    void setWindowSize(String name, KVector2f size, int cond);

    void setWindowSize(String name, float sizeX, float sizeY, int cond);

    void setWindowCollapsed(String name, boolean collapsed);

    void setWindowCollapsed(String name, boolean collapsed, int cond);

    void setWindowFocus(String name);

    KVector2f getContentRegionAvail();

    float getContentRegionAvailX();

    float getContentRegionAvailY();

    void getContentRegionAvail(KVector2f dst);

    KVector2f getContentRegionMax();

    float getContentRegionMaxX();

    float getContentRegionMaxY();

    void getContentRegionMax(KVector2f dst);

    KVector2f getWindowContentRegionMin();

    float getWindowContentRegionMinX();

    float getWindowContentRegionMinY();

    void getWindowContentRegionMin(KVector2f dst);

    KVector2f getWindowContentRegionMax();

    float getWindowContentRegionMaxX();

    float getWindowContentRegionMaxY();

    void getWindowContentRegionMax(KVector2f dst);

    float getScrollX();

    void setScrollX(float scrollX);

    float getScrollY();

    void setScrollY(float scrollY);

    float getScrollMaxX();

    float getScrollMaxY();

    void setScrollHereX();

    void setScrollHereX(float centerXRatio);

    void setScrollHereY();

    void setScrollHereY(float centerYRatio);

    void setScrollFromPosX(float localX);

    void setScrollFromPosX(float localX, float centerXRatio);

    void setScrollFromPosY(float localY);

    void setScrollFromPosY(float localY, float centerYRatio);

    void pushFont(KImFont font);

    void popFont();

    void pushStyleColor(int imGuiCol, int r, int g, int b, int a);

    void pushStyleColor(int imGuiCol, KVector4f col);

    void pushStyleColor(int imGuiCol, float colX, float colY, float colZ, float colW);

    void pushStyleColor(int imGuiCol, int col);

    void popStyleColor();

    void popStyleColor(int count);

    void pushStyleVar(int imGuiStyleVar, float val);

    void pushStyleVar(int imGuiStyleVar, KVector2f val);

    void pushStyleVar(int imGuiStyleVar, float valX, float valY);

    void popStyleVar();

    void popStyleVar(int count);

    void pushTabStop(boolean tabStop);

    void popTabStop();

    void pushButtonRepeat(boolean repeat);

    void popButtonRepeat();

    void pushItemWidth(float itemWidth);

    void popItemWidth();

    void setNextItemWidth(float itemWidth);

    float calcItemWidth();

    void pushTextWrapPos();

    void pushTextWrapPos(float wrapLocalPosX);

    void popTextWrapPos();

    KImFont getFont();

    int getFontSize();

    KVector2f getFontTexUvWhitePixel();

    float getFontTexUvWhitePixelX();

    float getFontTexUvWhitePixelY();

    void getFontTexUvWhitePixel(KVector2f dst);

    int getColorU32(int idx);

    int getColorU32(int idx, float alphaMul);

    int getColorU32(KVector4f col);

    int getColorU32(float colX, float colY, float colZ, float colW);

    int getColorU32i(int col);

    int getColorU32i(int col, float alphaMul);

    KVector4f getStyleColorVec4(int imGuiColIdx);

    float getStyleColorVec4X(int imGuiColIdx);

    float getStyleColorVec4Y(int imGuiColIdx);

    float getStyleColorVec4Z(int imGuiColIdx);

    float getStyleColorVec4W(int imGuiColIdx);

    void getStyleColorVec4(KVector4f dst, int imGuiColIdx);

    KVector2f getCursorScreenPos();

    void setCursorScreenPos(KVector2f pos);

    float getCursorScreenPosX();

    float getCursorScreenPosY();

    void getCursorScreenPos(KVector2f dst);

    void setCursorScreenPos(float posX, float posY);

    KVector2f getCursorPos();

    void setCursorPos(KVector2f localPos);

    float getCursorPosX();

    void setCursorPosX(float localX);

    float getCursorPosY();

    void setCursorPosY(float localY);

    void getCursorPos(KVector2f dst);

    void setCursorPos(float localPosX, float localPosY);

    KVector2f getCursorStartPos();

    float getCursorStartPosX();

    float getCursorStartPosY();

    void getCursorStartPos(KVector2f dst);

    void separator();

    void sameLine();

    void sameLine(float offsetFromStartX);

    void sameLine(float offsetFromStartX, float spacing);

    void newLine();

    void spacing();

    void dummy(KVector2f size);

    void dummy(float sizeX, float sizeY);

    void indent();

    void indent(float indentW);

    void unindent();

    void unindent(float indentW);

    void beginGroup();

    void endGroup();

    void alignTextToFramePadding();

    float getTextLineHeight();

    float getTextLineHeightWithSpacing();

    float getFrameHeight();

    float getFrameHeightWithSpacing();

    void pushID(String strId);

    void pushID(String strIdBegin, String strIdEnd);

    void pushID(long ptrId);

    void pushID(int intId);

    void popID();

    int getID(String strId);

    int getID(String strIdBegin, String strIdEnd);

    int getID(long ptrId);

    void textUnformatted(String text);

    void textUnformatted(String text, String textEnd);

    void text(String text);

    void textColored(KVector4f col, String text);

    void textColored(float colX, float colY, float colZ, float colW, String text);

    void textColored(int r, int g, int b, int a, String text);

    void textColored(int col, String text);

    void textDisabled(String text);

    void textWrapped(String text);

    void labelText(String label, String text);

    void bulletText(String text);

    void separatorText(String label);

    boolean button(String label);

    boolean button(String label, KVector2f size);

    boolean button(String label, float sizeX, float sizeY);

    boolean smallButton(String label);

    boolean invisibleButton(String strId, KVector2f size);

    boolean invisibleButton(String strId, float sizeX, float sizeY);

    boolean invisibleButton(String strId, KVector2f size, int imGuiButtonFlags);

    boolean invisibleButton(String strId, float sizeX, float sizeY, int imGuiButtonFlags);

    boolean arrowButton(String strId, int dir);

    boolean checkbox(String label, boolean active);

    boolean checkbox(String label, KBooleanReferenceValue data);

    boolean checkboxFlags(String label, KIntReferenceValue flags, int flagsValue);

    boolean radioButton(String label, boolean active);

    boolean radioButton(String label, KIntReferenceValue v, int vButton);

    void progressBar(float fraction);

    void progressBar(float fraction, KVector2f size);

    void progressBar(float fraction, float sizeX, float sizeY);

    void progressBar(float fraction, KVector2f size, String overlay);

    void progressBar(float fraction, float sizeX, float sizeY, String overlay);

    void progressBar(float fraction, String overlay);

    void bullet();

    void image(long userTextureId, KVector2f imageSize);

    void image(long userTextureId, float imageSizeX, float imageSizeY);

    void image(long userTextureId, KVector2f imageSize, KVector2f uv0);

    void image(
        long userTextureId,
        float imageSizeX,
        float imageSizeY,
        float uv0X,
        float uv0Y
    );

    void image(long userTextureId, KVector2f imageSize, KVector2f uv0, KVector2f uv1);

    void image(
        long userTextureId,
        float imageSizeX,
        float imageSizeY,
        float uv0X,
        float uv0Y,
        float uv1X,
        float uv1Y
    );

    void image(
        long userTextureId,
        KVector2f imageSize,
        KVector2f uv0,
        KVector2f uv1,
        KVector4f tintCol
    );

    void image(
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
    );

    void image(
        long userTextureId,
        KVector2f imageSize,
        KVector2f uv0,
        KVector2f uv1,
        KVector4f tintCol,
        KVector4f borderCol
    );

    void image(
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
    );

    boolean imageButton(String strId, long userTextureId, KVector2f imageSize);

    boolean imageButton(String strId, long userTextureId, float imageSizeX, float imageSizeY);

    boolean imageButton(String strId, long userTextureId, KVector2f imageSize, KVector2f uv0);

    boolean imageButton(
        String strId,
        long userTextureId,
        float imageSizeX,
        float imageSizeY,
        float uv0X,
        float uv0Y
    );

    boolean imageButton(
        String strId,
        long userTextureId,
        KVector2f imageSize,
        KVector2f uv0,
        KVector2f uv1
    );

    boolean imageButton(
        String strId,
        long userTextureId,
        float imageSizeX,
        float imageSizeY,
        float uv0X,
        float uv0Y,
        float uv1X,
        float uv1Y
    );

    boolean imageButton(
        String strId,
        long userTextureId,
        KVector2f imageSize,
        KVector2f uv0,
        KVector2f uv1,
        KVector4f bgCol
    );

    boolean imageButton(
        String strId,
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
    );

    boolean imageButton(
        String strId,
        long userTextureId,
        KVector2f imageSize,
        KVector2f uv0,
        KVector2f uv1,
        KVector4f bgCol,
        KVector4f tintCol
    );

    boolean imageButton(
        String strId,
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
    );

    boolean beginCombo(String label, String previewValue);

    boolean beginCombo(String label, String previewValue, int imGuiComboFlags);

    void endCombo();

    boolean combo(String label, KIntReferenceValue currentItem, String[] items);

    boolean combo(String label, KIntReferenceValue currentItem, String[] items, int popupMaxHeightInItems);

    boolean combo(String label, KIntReferenceValue currentItem, String itemsSeparatedByZeros);

    boolean combo(
        String label,
        KIntReferenceValue currentItem,
        String itemsSeparatedByZeros,
        int popupMaxHeightInItems
    );

    boolean dragFloat(String label, float[] v);

    boolean dragFloat(String label, float[] v, float vSpeed);

    boolean dragFloat(String label, float[] v, float vSpeed, float vMin);

    boolean dragFloat(String label, float[] v, float vSpeed, float vMin, float vMax);

    boolean dragFloat(
        String label,
        float[] v,
        float vSpeed,
        float vMin,
        float vMax,
        String format
    );

    boolean dragFloat(
        String label,
        float[] v,
        float vSpeed,
        float vMin,
        float vMax,
        String format,
        int imGuiSliderFlags
    );

    boolean dragFloat(
        String label,
        float[] v,
        float vSpeed,
        float vMin,
        float vMax,
        int imGuiSliderFlags
    );

    boolean dragFloat2(String label, float[] v);

    boolean dragFloat2(String label, float[] v, float vSpeed);

    boolean dragFloat2(String label, float[] v, float vSpeed, float vMin);

    boolean dragFloat2(String label, float[] v, float vSpeed, float vMin, float vMax);

    boolean dragFloat2(
        String label,
        float[] v,
        float vSpeed,
        float vMin,
        float vMax,
        String format
    );

    boolean dragFloat2(
        String label,
        float[] v,
        float vSpeed,
        float vMin,
        float vMax,
        String format,
        int imGuiSliderFlags
    );

    boolean dragFloat2(
        String label,
        float[] v,
        float vSpeed,
        float vMin,
        float vMax,
        int imGuiSliderFlags
    );

    boolean dragFloat3(String label, float[] v);

    boolean dragFloat3(String label, float[] v, float vSpeed);

    boolean dragFloat3(String label, float[] v, float vSpeed, float vMin);

    boolean dragFloat3(String label, float[] v, float vSpeed, float vMin, float vMax);

    boolean dragFloat3(
        String label,
        float[] v,
        float vSpeed,
        float vMin,
        float vMax,
        String format
    );

    boolean dragFloat3(
        String label,
        float[] v,
        float vSpeed,
        float vMin,
        float vMax,
        String format,
        int imGuiSliderFlags
    );

    boolean dragFloat3(
        String label,
        float[] v,
        float vSpeed,
        float vMin,
        float vMax,
        int imGuiSliderFlags
    );

    boolean dragFloat4(String label, float[] v);

    boolean dragFloat4(String label, float[] v, float vSpeed);

    boolean dragFloat4(String label, float[] v, float vSpeed, float vMin);

    boolean dragFloat4(String label, float[] v, float vSpeed, float vMin, float vMax);

    boolean dragFloat4(
        String label,
        float[] v,
        float vSpeed,
        float vMin,
        float vMax,
        String format
    );

    boolean dragFloat4(
        String label,
        float[] v,
        float vSpeed,
        float vMin,
        float vMax,
        String format,
        int imGuiSliderFlags
    );

    boolean dragFloat4(
        String label,
        float[] v,
        float vSpeed,
        float vMin,
        float vMax,
        int imGuiSliderFlags
    );

    boolean dragFloatRange2(
        String label,
        float[] vCurrentMin,
        float[] vCurrentMax,
        float vSpeed
    );

    boolean dragFloatRange2(
        String label,
        float[] vCurrentMin,
        float[] vCurrentMax,
        float vSpeed,
        float vMin
    );

    boolean dragFloatRange2(
        String label,
        float[] vCurrentMin,
        float[] vCurrentMax,
        float vSpeed,
        float vMin,
        float vMax
    );

    boolean dragFloatRange2(
        String label,
        float[] vCurrentMin,
        float[] vCurrentMax,
        float vSpeed,
        float vMin,
        float vMax,
        String format
    );

    boolean dragFloatRange2(
        String label,
        float[] vCurrentMin,
        float[] vCurrentMax,
        float vSpeed,
        float vMin,
        float vMax,
        String format,
        String formatMax
    );

    boolean dragFloatRange2(
        String label,
        float[] vCurrentMin,
        float[] vCurrentMax,
        float vSpeed,
        float vMin,
        float vMax,
        String format,
        String formatMax,
        int imGuiSliderFlags
    );

    boolean dragInt(String label, int[] v);

    boolean dragInt(String label, int[] v, float vSpeed);

    boolean dragInt(String label, int[] v, float vSpeed, int vMin);

    boolean dragInt(String label, int[] v, float vSpeed, int vMin, int vMax);

    boolean dragInt(
        String label,
        int[] v,
        float vSpeed,
        int vMin,
        int vMax,
        String format
    );

    boolean dragInt(
        String label,
        int[] v,
        float vSpeed,
        int vMin,
        int vMax,
        String format,
        int imGuiSliderFlags
    );

    boolean dragInt(
        String label,
        int[] v,
        float vSpeed,
        int vMin,
        int vMax,
        int imGuiSliderFlags
    );

    boolean dragInt2(String label, int[] v);

    boolean dragInt2(String label, int[] v, float vSpeed);

    boolean dragInt2(String label, int[] v, float vSpeed, int vMin);

    boolean dragInt2(String label, int[] v, float vSpeed, int vMin, int vMax);

    boolean dragInt2(
        String label,
        int[] v,
        float vSpeed,
        int vMin,
        int vMax,
        String format
    );

    boolean dragInt2(
        String label,
        int[] v,
        float vSpeed,
        int vMin,
        int vMax,
        String format,
        int imGuiSliderFlags
    );

    boolean dragInt2(
        String label,
        int[] v,
        float vSpeed,
        int vMin,
        int vMax,
        int imGuiSliderFlags
    );

    boolean dragInt3(String label, int[] v);

    boolean dragInt3(String label, int[] v, float vSpeed);

    boolean dragInt3(String label, int[] v, float vSpeed, int vMin);

    boolean dragInt3(String label, int[] v, float vSpeed, int vMin, int vMax);

    boolean dragInt3(
        String label,
        int[] v,
        float vSpeed,
        int vMin,
        int vMax,
        String format
    );

    boolean dragInt3(
        String label,
        int[] v,
        float vSpeed,
        int vMin,
        int vMax,
        String format,
        int imGuiSliderFlags
    );

    boolean dragInt3(
        String label,
        int[] v,
        float vSpeed,
        int vMin,
        int vMax,
        int imGuiSliderFlags
    );

    boolean dragInt4(String label, int[] v);

    boolean dragInt4(String label, int[] v, float vSpeed);

    boolean dragInt4(String label, int[] v, float vSpeed, int vMin);

    boolean dragInt4(String label, int[] v, float vSpeed, int vMin, int vMax);

    boolean dragInt4(
        String label,
        int[] v,
        float vSpeed,
        int vMin,
        int vMax,
        String format
    );

    boolean dragInt4(
        String label,
        int[] v,
        float vSpeed,
        int vMin,
        int vMax,
        String format,
        int imGuiSliderFlags
    );

    boolean dragInt4(
        String label,
        int[] v,
        float vSpeed,
        int vMin,
        int vMax,
        int imGuiSliderFlags
    );

    boolean dragIntRange2(String label, int[] vCurrentMin, int[] vCurrentMax);

    boolean dragIntRange2(String label, int[] vCurrentMin, int[] vCurrentMax, float vSpeed);

    boolean dragIntRange2(
        String label,
        int[] vCurrentMin,
        int[] vCurrentMax,
        float vSpeed,
        int vMin
    );

    boolean dragIntRange2(
        String label,
        int[] vCurrentMin,
        int[] vCurrentMax,
        float vSpeed,
        int vMin,
        int vMax
    );

    boolean dragIntRange2(
        String label,
        int[] vCurrentMin,
        int[] vCurrentMax,
        float vSpeed,
        int vMin,
        int vMax,
        String format
    );

    boolean dragIntRange2(
        String label,
        int[] vCurrentMin,
        int[] vCurrentMax,
        float vSpeed,
        int vMin,
        int vMax,
        String format,
        String formatMax
    );

    boolean dragIntRange2(
        String label,
        int[] vCurrentMin,
        int[] vCurrentMax,
        float vSpeed,
        int vMin,
        int vMax,
        String format,
        String formatMax,
        int imGuiSliderFlags
    );

    boolean dragScalar(String label, short[] pData);

    boolean dragScalar(String label, short[] pData, float vSpeed);

    boolean dragScalar(String label, short[] pData, float vSpeed, short pMin);

    boolean dragScalar(String label, short[] pData, float vSpeed, short pMin, short pMax);

    boolean dragScalar(
        String label,
        short[] pData,
        float vSpeed,
        short pMin,
        short pMax,
        String format
    );

    boolean dragScalar(
        String label,
        short[] pData,
        float vSpeed,
        short pMin,
        short pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean dragScalar(String label, int[] pData);

    boolean dragScalar(String label, int[] pData, float vSpeed);

    boolean dragScalar(String label, int[] pData, float vSpeed, int pMin);

    boolean dragScalar(String label, int[] pData, float vSpeed, int pMin, int pMax);

    boolean dragScalar(
        String label,
        int[] pData,
        float vSpeed,
        int pMin,
        int pMax,
        String format
    );

    boolean dragScalar(
        String label,
        int[] pData,
        float vSpeed,
        int pMin,
        int pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean dragScalar(String label, long[] pData);

    boolean dragScalar(String label, long[] pData, float vSpeed);

    boolean dragScalar(String label, long[] pData, float vSpeed, long pMin);

    boolean dragScalar(String label, long[] pData, float vSpeed, long pMin, long pMax);

    boolean dragScalar(
        String label,
        long[] pData,
        float vSpeed,
        long pMin,
        long pMax,
        String format
    );

    boolean dragScalar(
        String label,
        long[] pData,
        float vSpeed,
        long pMin,
        long pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean dragScalar(String label, float[] pData);

    boolean dragScalar(String label, float[] pData, float vSpeed);

    boolean dragScalar(String label, float[] pData, float vSpeed, float pMin);

    boolean dragScalar(String label, float[] pData, float vSpeed, float pMin, float pMax);

    boolean dragScalar(
        String label,
        float[] pData,
        float vSpeed,
        float pMin,
        float pMax,
        String format
    );

    boolean dragScalar(
        String label,
        float[] pData,
        float vSpeed,
        float pMin,
        float pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean dragScalar(String label, double[] pData);

    boolean dragScalar(String label, double[] pData, float vSpeed);

    boolean dragScalar(String label, double[] pData, float vSpeed, double pMin);

    boolean dragScalar(
        String label,
        double[] pData,
        float vSpeed,
        double pMin,
        double pMax
    );

    boolean dragScalar(
        String label,
        double[] pData,
        float vSpeed,
        double pMin,
        double pMax,
        String format
    );

    boolean dragScalar(
        String label,
        double[] pData,
        float vSpeed,
        double pMin,
        double pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean dragScalarN(String label, short[] pData, int components);

    boolean dragScalarN(String label, short[] pData, int components, float vSpeed);

    boolean dragScalarN(
        String label,
        short[] pData,
        int components,
        float vSpeed,
        short pMin
    );

    boolean dragScalarN(
        String label,
        short[] pData,
        int components,
        float vSpeed,
        short pMin,
        short pMax
    );

    boolean dragScalarN(
        String label,
        short[] pData,
        int components,
        float vSpeed,
        short pMin,
        short pMax,
        String format
    );

    boolean dragScalarN(
        String label,
        short[] pData,
        int components,
        float vSpeed,
        short pMin,
        short pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean dragScalarN(String label, int[] pData, int components);

    boolean dragScalarN(String label, int[] pData, int components, float vSpeed);

    boolean dragScalarN(
        String label,
        int[] pData,
        int components,
        float vSpeed,
        int pMin
    );

    boolean dragScalarN(
        String label,
        int[] pData,
        int components,
        float vSpeed,
        int pMin,
        int pMax
    );

    boolean dragScalarN(
        String label,
        int[] pData,
        int components,
        float vSpeed,
        int pMin,
        int pMax,
        String format
    );

    boolean dragScalarN(
        String label,
        int[] pData,
        int components,
        float vSpeed,
        int pMin,
        int pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean dragScalarN(String label, long[] pData, int components);

    boolean dragScalarN(String label, long[] pData, int components, float vSpeed);

    boolean dragScalarN(
        String label,
        long[] pData,
        int components,
        float vSpeed,
        long pMin
    );

    boolean dragScalarN(
        String label,
        long[] pData,
        int components,
        float vSpeed,
        long pMin,
        long pMax
    );

    boolean dragScalarN(
        String label,
        long[] pData,
        int components,
        float vSpeed,
        long pMin,
        long pMax,
        String format
    );

    boolean dragScalarN(
        String label,
        long[] pData,
        int components,
        float vSpeed,
        long pMin,
        long pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean dragScalarN(String label, float[] pData, int components);

    boolean dragScalarN(String label, float[] pData, int components, float vSpeed);

    boolean dragScalarN(
        String label,
        float[] pData,
        int components,
        float vSpeed,
        float pMin
    );

    boolean dragScalarN(
        String label,
        float[] pData,
        int components,
        float vSpeed,
        float pMin,
        float pMax
    );

    boolean dragScalarN(
        String label,
        float[] pData,
        int components,
        float vSpeed,
        float pMin,
        float pMax,
        String format
    );

    boolean dragScalarN(
        String label,
        float[] pData,
        int components,
        float vSpeed,
        float pMin,
        float pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean dragScalarN(String label, double[] pData, int components);

    boolean dragScalarN(String label, double[] pData, int components, float vSpeed);

    boolean dragScalarN(
        String label,
        double[] pData,
        int components,
        float vSpeed,
        double pMin
    );

    boolean dragScalarN(
        String label,
        double[] pData,
        int components,
        float vSpeed,
        double pMin,
        double pMax
    );

    boolean dragScalarN(
        String label,
        double[] pData,
        int components,
        float vSpeed,
        double pMin,
        double pMax,
        String format
    );

    boolean dragScalarN(
        String label,
        double[] pData,
        int components,
        float vSpeed,
        double pMin,
        double pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean sliderFloat(String label, float[] v, float vMin, float vMax);

    boolean sliderFloat(String label, float[] v, float vMin, float vMax, String format);

    boolean sliderFloat(
        String label,
        float[] v,
        float vMin,
        float vMax,
        String format,
        int imGuiSliderFlags
    );

    boolean sliderFloat(
        String label,
        float[] v,
        float vMin,
        float vMax,
        int imGuiSliderFlags
    );

    boolean sliderFloat2(String label, float[] v, float vMin, float vMax);

    boolean sliderFloat2(String label, float[] v, float vMin, float vMax, String format);

    boolean sliderFloat2(
        String label,
        float[] v,
        float vMin,
        float vMax,
        String format,
        int imGuiSliderFlags
    );

    boolean sliderFloat2(
        String label,
        float[] v,
        float vMin,
        float vMax,
        int imGuiSliderFlags
    );

    boolean sliderFloat3(String label, float[] v, float vMin, float vMax);

    boolean sliderFloat3(String label, float[] v, float vMin, float vMax, String format);

    boolean sliderFloat3(
        String label,
        float[] v,
        float vMin,
        float vMax,
        String format,
        int imGuiSliderFlags
    );

    boolean sliderFloat3(
        String label,
        float[] v,
        float vMin,
        float vMax,
        int imGuiSliderFlags
    );

    boolean sliderFloat4(String label, float[] v, float vMin, float vMax);

    boolean sliderFloat4(String label, float[] v, float vMin, float vMax, String format);

    boolean sliderFloat4(
        String label,
        float[] v,
        float vMin,
        float vMax,
        String format,
        int imGuiSliderFlags
    );

    boolean sliderFloat4(
        String label,
        float[] v,
        float vMin,
        float vMax,
        int imGuiSliderFlags
    );

    boolean sliderAngle(String label, float[] vRad);

    boolean sliderAngle(String label, float[] vRad, float vDegreesMin);

    boolean sliderAngle(String label, float[] vRad, float vDegreesMin, float vDegreesMax);

    boolean sliderAngle(
        String label,
        float[] vRad,
        float vDegreesMin,
        float vDegreesMax,
        String format
    );

    boolean sliderAngle(
        String label,
        float[] vRad,
        float vDegreesMin,
        float vDegreesMax,
        String format,
        int imGuiSliderFlags
    );

    boolean sliderAngle(
        String label,
        float[] vRad,
        float vDegreesMin,
        float vDegreesMax,
        int imGuiSliderFlags
    );

    boolean sliderInt(String label, int[] v, int vMin, int vMax);

    boolean sliderInt(String label, int[] v, int vMin, int vMax, String format);

    boolean sliderInt(
        String label,
        int[] v,
        int vMin,
        int vMax,
        String format,
        int imGuiSliderFlags
    );

    boolean sliderInt(String label, int[] v, int vMin, int vMax, int imGuiSliderFlags);

    boolean sliderInt2(String label, int[] v, int vMin, int vMax);

    boolean sliderInt2(String label, int[] v, int vMin, int vMax, String format);

    boolean sliderInt2(
        String label,
        int[] v,
        int vMin,
        int vMax,
        String format,
        int imGuiSliderFlags
    );

    boolean sliderInt2(String label, int[] v, int vMin, int vMax, int imGuiSliderFlags);

    boolean sliderInt3(String label, int[] v, int vMin, int vMax);

    boolean sliderInt3(String label, int[] v, int vMin, int vMax, String format);

    boolean sliderInt3(
        String label,
        int[] v,
        int vMin,
        int vMax,
        String format,
        int imGuiSliderFlags
    );

    boolean sliderInt3(String label, int[] v, int vMin, int vMax, int imGuiSliderFlags);

    boolean sliderInt4(String label, int[] v, int vMin, int vMax);

    boolean sliderInt4(String label, int[] v, int vMin, int vMax, String format);

    boolean sliderInt4(
        String label,
        int[] v,
        int vMin,
        int vMax,
        String format,
        int imGuiSliderFlags
    );

    boolean sliderInt4(String label, int[] v, int vMin, int vMax, int imGuiSliderFlags);

    boolean sliderScalar(String label, short[] pData, short pMin, short pMax);

    boolean sliderScalar(
        String label,
        short[] pData,
        short pMin,
        short pMax,
        String format
    );

    boolean sliderScalar(
        String label,
        short[] pData,
        short pMin,
        short pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean sliderScalar(String label, int[] pData, int pMin, int pMax);

    boolean sliderScalar(String label, int[] pData, int pMin, int pMax, String format);

    boolean sliderScalar(
        String label,
        int[] pData,
        int pMin,
        int pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean sliderScalar(String label, long[] pData, long pMin, long pMax);

    boolean sliderScalar(String label, long[] pData, long pMin, long pMax, String format);

    boolean sliderScalar(
        String label,
        long[] pData,
        long pMin,
        long pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean sliderScalar(String label, float[] pData, float pMin, float pMax);

    boolean sliderScalar(
        String label,
        float[] pData,
        float pMin,
        float pMax,
        String format
    );

    boolean sliderScalar(
        String label,
        float[] pData,
        float pMin,
        float pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean sliderScalar(String label, double[] pData, double pMin, double pMax);

    boolean sliderScalar(
        String label,
        double[] pData,
        double pMin,
        double pMax,
        String format
    );

    boolean sliderScalar(
        String label,
        double[] pData,
        double pMin,
        double pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean sliderScalarN(
        String label,
        short[] pData,
        int components,
        short pMin,
        short pMax
    );

    boolean sliderScalarN(
        String label,
        short[] pData,
        int components,
        short pMin,
        short pMax,
        String format
    );

    boolean sliderScalarN(
        String label,
        short[] pData,
        int components,
        short pMin,
        short pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean sliderScalarN(String label, int[] pData, int components, int pMin, int pMax);

    boolean sliderScalarN(
        String label,
        int[] pData,
        int components,
        int pMin,
        int pMax,
        String format
    );

    boolean sliderScalarN(
        String label,
        int[] pData,
        int components,
        int pMin,
        int pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean sliderScalarN(
        String label,
        long[] pData,
        int components,
        long pMin,
        long pMax
    );

    boolean sliderScalarN(
        String label,
        long[] pData,
        int components,
        long pMin,
        long pMax,
        String format
    );

    boolean sliderScalarN(
        String label,
        long[] pData,
        int components,
        long pMin,
        long pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean sliderScalarN(
        String label,
        float[] pData,
        int components,
        float pMin,
        float pMax
    );

    boolean sliderScalarN(
        String label,
        float[] pData,
        int components,
        float pMin,
        float pMax,
        String format
    );

    boolean sliderScalarN(
        String label,
        float[] pData,
        int components,
        float pMin,
        float pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean sliderScalarN(
        String label,
        double[] pData,
        int components,
        double pMin,
        double pMax
    );

    boolean sliderScalarN(
        String label,
        double[] pData,
        int components,
        double pMin,
        double pMax,
        String format
    );

    boolean sliderScalarN(
        String label,
        double[] pData,
        int components,
        double pMin,
        double pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean vSliderFloat(String label, KVector2f size, float[] v, float vMin, float vMax);

    boolean vSliderFloat(
        String label,
        float sizeX,
        float sizeY,
        float[] v,
        float vMin,
        float vMax
    );

    boolean vSliderFloat(
        String label,
        KVector2f size,
        float[] v,
        float vMin,
        float vMax,
        String format
    );

    boolean vSliderFloat(
        String label,
        float sizeX,
        float sizeY,
        float[] v,
        float vMin,
        float vMax,
        String format
    );

    boolean vSliderFloat(
        String label,
        KVector2f size,
        float[] v,
        float vMin,
        float vMax,
        String format,
        int imGuiSliderFlags
    );

    boolean vSliderFloat(
        String label,
        float sizeX,
        float sizeY,
        float[] v,
        float vMin,
        float vMax,
        String format,
        int imGuiSliderFlags
    );

    boolean vSliderFloat(
        String label,
        KVector2f size,
        float[] v,
        float vMin,
        float vMax,
        int imGuiSliderFlags
    );

    boolean vSliderFloat(
        String label,
        float sizeX,
        float sizeY,
        float[] v,
        float vMin,
        float vMax,
        int imGuiSliderFlags
    );

    boolean vSliderInt(String label, KVector2f size, int[] v, int vMin, int vMax);

    boolean vSliderInt(
        String label,
        float sizeX,
        float sizeY,
        int[] v,
        int vMin,
        int vMax
    );

    boolean vSliderInt(
        String label,
        KVector2f size,
        int[] v,
        int vMin,
        int vMax,
        String format
    );

    boolean vSliderInt(
        String label,
        float sizeX,
        float sizeY,
        int[] v,
        int vMin,
        int vMax,
        String format
    );

    boolean vSliderInt(
        String label,
        KVector2f size,
        int[] v,
        int vMin,
        int vMax,
        String format,
        int imGuiSliderFlags
    );

    boolean vSliderInt(
        String label,
        float sizeX,
        float sizeY,
        int[] v,
        int vMin,
        int vMax,
        String format,
        int imGuiSliderFlags
    );

    boolean vSliderInt(
        String label,
        KVector2f size,
        int[] v,
        int vMin,
        int vMax,
        int imGuiSliderFlags
    );

    boolean vSliderInt(
        String label,
        float sizeX,
        float sizeY,
        int[] v,
        int vMin,
        int vMax,
        int imGuiSliderFlags
    );

    boolean vSliderScalar(
        String label,
        KVector2f size,
        short[] pData,
        short pMin,
        short pMax
    );

    boolean vSliderScalar(
        String label,
        float sizeX,
        float sizeY,
        short[] pData,
        short pMin,
        short pMax
    );

    boolean vSliderScalar(
        String label,
        KVector2f size,
        short[] pData,
        short pMin,
        short pMax,
        String format
    );

    boolean vSliderScalar(
        String label,
        float sizeX,
        float sizeY,
        short[] pData,
        short pMin,
        short pMax,
        String format
    );

    boolean vSliderScalar(
        String label,
        KVector2f size,
        short[] pData,
        short pMin,
        short pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean vSliderScalar(
        String label,
        float sizeX,
        float sizeY,
        short[] pData,
        short pMin,
        short pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean vSliderScalar(String label, KVector2f size, int[] pData, int pMin, int pMax);

    boolean vSliderScalar(
        String label,
        float sizeX,
        float sizeY,
        int[] pData,
        int pMin,
        int pMax
    );

    boolean vSliderScalar(
        String label,
        KVector2f size,
        int[] pData,
        int pMin,
        int pMax,
        String format
    );

    boolean vSliderScalar(
        String label,
        float sizeX,
        float sizeY,
        int[] pData,
        int pMin,
        int pMax,
        String format
    );

    boolean vSliderScalar(
        String label,
        KVector2f size,
        int[] pData,
        int pMin,
        int pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean vSliderScalar(
        String label,
        float sizeX,
        float sizeY,
        int[] pData,
        int pMin,
        int pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean vSliderScalar(String label, KVector2f size, long[] pData, long pMin, long pMax);

    boolean vSliderScalar(
        String label,
        float sizeX,
        float sizeY,
        long[] pData,
        long pMin,
        long pMax
    );

    boolean vSliderScalar(
        String label,
        KVector2f size,
        long[] pData,
        long pMin,
        long pMax,
        String format
    );

    boolean vSliderScalar(
        String label,
        float sizeX,
        float sizeY,
        long[] pData,
        long pMin,
        long pMax,
        String format
    );

    boolean vSliderScalar(
        String label,
        KVector2f size,
        long[] pData,
        long pMin,
        long pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean vSliderScalar(
        String label,
        float sizeX,
        float sizeY,
        long[] pData,
        long pMin,
        long pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean vSliderScalar(
        String label,
        KVector2f size,
        float[] pData,
        float pMin,
        float pMax
    );

    boolean vSliderScalar(
        String label,
        float sizeX,
        float sizeY,
        float[] pData,
        float pMin,
        float pMax
    );

    boolean vSliderScalar(
        String label,
        KVector2f size,
        float[] pData,
        float pMin,
        float pMax,
        String format
    );

    boolean vSliderScalar(
        String label,
        float sizeX,
        float sizeY,
        float[] pData,
        float pMin,
        float pMax,
        String format
    );

    boolean vSliderScalar(
        String label,
        KVector2f size,
        float[] pData,
        float pMin,
        float pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean vSliderScalar(
        String label,
        float sizeX,
        float sizeY,
        float[] pData,
        float pMin,
        float pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean vSliderScalar(
        String label,
        KVector2f size,
        double[] pData,
        double pMin,
        double pMax
    );

    boolean vSliderScalar(
        String label,
        float sizeX,
        float sizeY,
        double[] pData,
        double pMin,
        double pMax
    );

    boolean vSliderScalar(
        String label,
        KVector2f size,
        double[] pData,
        double pMin,
        double pMax,
        String format
    );

    boolean vSliderScalar(
        String label,
        float sizeX,
        float sizeY,
        double[] pData,
        double pMin,
        double pMax,
        String format
    );

    boolean vSliderScalar(
        String label,
        KVector2f size,
        double[] pData,
        double pMin,
        double pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean vSliderScalar(
        String label,
        float sizeX,
        float sizeY,
        double[] pData,
        double pMin,
        double pMax,
        String format,
        int imGuiSliderFlags
    );

    boolean inputText(String label, KStringReferenceValue text);

    boolean inputText(String label, KStringReferenceValue text, int imGuiInputTextFlags);

    boolean inputText(
        String label,
        KStringReferenceValue text,
        int imGuiInputTextFlags,
        KImGuiInputTextCallback callback
    );

    boolean inputTextMultiline(String label, KStringReferenceValue text);

    boolean inputTextMultiline(String label, KStringReferenceValue text, float width, float height);

    boolean inputTextMultiline(String label, KStringReferenceValue text, int imGuiInputTextFlags);

    boolean inputTextMultiline(
        String label,
        KStringReferenceValue text,
        int imGuiInputTextFlags,
        KImGuiInputTextCallback callback
    );

    boolean inputTextMultiline(
        String label,
        KStringReferenceValue text,
        float width,
        float height,
        int imGuiInputTextFlags
    );

    boolean inputTextMultiline(
        String label,
        KStringReferenceValue text,
        float width,
        float height,
        int imGuiInputTextFlags,
        KImGuiInputTextCallback callback
    );

    boolean inputTextWithHint(String label, String hint, KStringReferenceValue text);

    boolean inputTextWithHint(
        String label,
        String hint,
        KStringReferenceValue text,
        int imGuiInputTextFlags
    );

    boolean inputTextWithHint(
        String label,
        String hint,
        KStringReferenceValue text,
        int imGuiInputTextFlags,
        KImGuiInputTextCallback callback
    );

    boolean inputFloat(String label, KFloatReferenceValue v);

    boolean inputFloat(String label, KFloatReferenceValue v, float step);

    boolean inputFloat(String label, KFloatReferenceValue v, float step, float stepFast);

    boolean inputFloat(
        String label,
        KFloatReferenceValue v,
        float step,
        float stepFast,
        String format
    );

    boolean inputFloat(
        String label,
        KFloatReferenceValue v,
        float step,
        float stepFast,
        String format,
        int imGuiInputTextFlags
    );

    boolean inputFloat(
        String label,
        KFloatReferenceValue v,
        float step,
        float stepFast,
        int imGuiInputTextFlags
    );

    boolean inputFloat2(String label, float[] v);

    boolean inputFloat2(String label, float[] v, String format);

    boolean inputFloat2(String label, float[] v, String format, int imGuiInputTextFlags);

    boolean inputFloat2(String label, float[] v, int imGuiInputTextFlags);

    boolean inputFloat3(String label, float[] v);

    boolean inputFloat3(String label, float[] v, String format);

    boolean inputFloat3(String label, float[] v, String format, int imGuiInputTextFlags);

    boolean inputFloat3(String label, float[] v, int imGuiInputTextFlags);

    boolean inputFloat4(String label, float[] v);

    boolean inputFloat4(String label, float[] v, String format);

    boolean inputFloat4(String label, float[] v, String format, int imGuiInputTextFlags);

    boolean inputFloat4(String label, float[] v, int imGuiInputTextFlags);

    boolean inputInt(String label, KIntReferenceValue v);

    boolean inputInt(String label, KIntReferenceValue v, int step);

    boolean inputInt(String label, KIntReferenceValue v, int step, int stepFast);

    boolean inputInt(
        String label,
        KIntReferenceValue v,
        int step,
        int stepFast,
        int imGuiInputTextFlags
    );

    boolean inputInt2(String label, int[] v);

    boolean inputInt2(String label, int[] v, int imGuiInputTextFlags);

    boolean inputInt3(String label, int[] v);

    boolean inputInt3(String label, int[] v, int imGuiInputTextFlags);

    boolean inputInt4(String label, int[] v);

    boolean inputInt4(String label, int[] v, int imGuiInputTextFlags);

    boolean inputDouble(String label, KDoubleReferenceValue v);

    boolean inputDouble(String label, KDoubleReferenceValue v, double step);

    boolean inputDouble(String label, KDoubleReferenceValue v, double step, double stepFast);

    boolean inputDouble(
        String label,
        KDoubleReferenceValue v,
        double step,
        double stepFast,
        String format
    );

    boolean inputDouble(
        String label,
        KDoubleReferenceValue v,
        double step,
        double stepFast,
        String format,
        int imGuiInputTextFlags
    );

    boolean inputDouble(
        String label,
        KDoubleReferenceValue v,
        double step,
        double stepFast,
        int imGuiInputTextFlags
    );

    boolean inputScalar(String label, KShortReferenceValue pData);

    boolean inputScalar(String label, KShortReferenceValue pData, short pStep);

    boolean inputScalar(String label, KShortReferenceValue pData, short pStep, short pStepFast);

    boolean inputScalar(
        String label,
        KShortReferenceValue pData,
        short pStep,
        short pStepFast,
        String format
    );

    boolean inputScalar(
        String label,
        KShortReferenceValue pData,
        short pStep,
        short pStepFast,
        String format,
        int imGuiSliderFlags
    );

    boolean inputScalar(String label, KIntReferenceValue pData);

    boolean inputScalar(String label, KIntReferenceValue pData, int pStep);

    boolean inputScalar(String label, KIntReferenceValue pData, int pStep, int pStepFast);

    boolean inputScalar(
        String label,
        KIntReferenceValue pData,
        int pStep,
        int pStepFast,
        String format
    );

    boolean inputScalar(
        String label,
        KIntReferenceValue pData,
        int pStep,
        int pStepFast,
        String format,
        int imGuiSliderFlags
    );

    boolean inputScalar(String label, KLongReferenceValue pData);

    boolean inputScalar(String label, KLongReferenceValue pData, long pStep);

    boolean inputScalar(String label, KLongReferenceValue pData, long pStep, long pStepFast);

    boolean inputScalar(
        String label,
        KLongReferenceValue pData,
        long pStep,
        long pStepFast,
        String format
    );

    boolean inputScalar(
        String label,
        KLongReferenceValue pData,
        long pStep,
        long pStepFast,
        String format,
        int imGuiSliderFlags
    );

    boolean inputScalar(String label, KFloatReferenceValue pData);

    boolean inputScalar(String label, KFloatReferenceValue pData, float pStep);

    boolean inputScalar(String label, KFloatReferenceValue pData, float pStep, float pStepFast);

    boolean inputScalar(
        String label,
        KFloatReferenceValue pData,
        float pStep,
        float pStepFast,
        String format
    );

    boolean inputScalar(
        String label,
        KFloatReferenceValue pData,
        float pStep,
        float pStepFast,
        String format,
        int imGuiSliderFlags
    );

    boolean inputScalar(String label, KDoubleReferenceValue pData);

    boolean inputScalar(String label, KDoubleReferenceValue pData, double pStep);

    boolean inputScalar(String label, KDoubleReferenceValue pData, double pStep, double pStepFast);

    boolean inputScalar(
        String label,
        KDoubleReferenceValue pData,
        double pStep,
        double pStepFast,
        String format
    );

    boolean inputScalar(
        String label,
        KDoubleReferenceValue pData,
        double pStep,
        double pStepFast,
        String format,
        int imGuiSliderFlags
    );

    boolean inputScalar(String label, int dataType, KShortReferenceValue pData);

    boolean inputScalar(String label, int dataType, KShortReferenceValue pData, short pStep);

    boolean inputScalar(
        String label,
        int dataType,
        KShortReferenceValue pData,
        short pStep,
        short pStepFast
    );

    boolean inputScalar(
        String label,
        int dataType,
        KShortReferenceValue pData,
        short pStep,
        short pStepFast,
        String format
    );

    boolean inputScalar(
        String label,
        int dataType,
        KShortReferenceValue pData,
        short pStep,
        short pStepFast,
        String format,
        int imGuiSliderFlags
    );

    boolean inputScalar(String label, int dataType, KIntReferenceValue pData);

    boolean inputScalar(String label, int dataType, KIntReferenceValue pData, int pStep);

    boolean inputScalar(
        String label,
        int dataType,
        KIntReferenceValue pData,
        int pStep,
        int pStepFast
    );

    boolean inputScalar(
        String label,
        int dataType,
        KIntReferenceValue pData,
        int pStep,
        int pStepFast,
        String format
    );

    boolean inputScalar(
        String label,
        int dataType,
        KIntReferenceValue pData,
        int pStep,
        int pStepFast,
        String format,
        int imGuiSliderFlags
    );

    boolean inputScalar(String label, int dataType, KLongReferenceValue pData);

    boolean inputScalar(String label, int dataType, KLongReferenceValue pData, long pStep);

    boolean inputScalar(
        String label,
        int dataType,
        KLongReferenceValue pData,
        long pStep,
        long pStepFast
    );

    boolean inputScalar(
        String label,
        int dataType,
        KLongReferenceValue pData,
        long pStep,
        long pStepFast,
        String format
    );

    boolean inputScalar(
        String label,
        int dataType,
        KLongReferenceValue pData,
        long pStep,
        long pStepFast,
        String format,
        int imGuiSliderFlags
    );

    boolean inputScalar(String label, int dataType, KFloatReferenceValue pData);

    boolean inputScalar(String label, int dataType, KFloatReferenceValue pData, float pStep);

    boolean inputScalar(
        String label,
        int dataType,
        KFloatReferenceValue pData,
        float pStep,
        float pStepFast
    );

    boolean inputScalar(
        String label,
        int dataType,
        KFloatReferenceValue pData,
        float pStep,
        float pStepFast,
        String format
    );

    boolean inputScalar(
        String label,
        int dataType,
        KFloatReferenceValue pData,
        float pStep,
        float pStepFast,
        String format,
        int imGuiSliderFlags
    );

    boolean inputScalar(String label, int dataType, KDoubleReferenceValue pData);

    boolean inputScalar(String label, int dataType, KDoubleReferenceValue pData, double pStep);

    boolean inputScalar(
        String label,
        int dataType,
        KDoubleReferenceValue pData,
        double pStep,
        double pStepFast
    );

    boolean inputScalar(
        String label,
        int dataType,
        KDoubleReferenceValue pData,
        double pStep,
        double pStepFast,
        String format
    );

    boolean inputScalar(
        String label,
        int dataType,
        KDoubleReferenceValue pData,
        double pStep,
        double pStepFast,
        String format,
        int imGuiSliderFlags
    );

    boolean inputScalarN(String label, short[] pData, int components);

    boolean inputScalarN(String label, short[] pData, int components, short pStep);

    boolean inputScalarN(
        String label,
        short[] pData,
        int components,
        short pStep,
        short pStepFast
    );

    boolean inputScalarN(
        String label,
        short[] pData,
        int components,
        short pStep,
        short pStepFast,
        String format
    );

    boolean inputScalarN(
        String label,
        short[] pData,
        int components,
        short pStep,
        short pStepFast,
        String format,
        int imGuiSliderFlags
    );

    boolean inputScalarN(String label, int[] pData, int components);

    boolean inputScalarN(String label, int[] pData, int components, int pStep);

    boolean inputScalarN(
        String label,
        int[] pData,
        int components,
        int pStep,
        int pStepFast
    );

    boolean inputScalarN(
        String label,
        int[] pData,
        int components,
        int pStep,
        int pStepFast,
        String format
    );

    boolean inputScalarN(
        String label,
        int[] pData,
        int components,
        int pStep,
        int pStepFast,
        String format,
        int imGuiSliderFlags
    );

    boolean inputScalarN(String label, long[] pData, int components);

    boolean inputScalarN(String label, long[] pData, int components, long pStep);

    boolean inputScalarN(
        String label,
        long[] pData,
        int components,
        long pStep,
        long pStepFast
    );

    boolean inputScalarN(
        String label,
        long[] pData,
        int components,
        long pStep,
        long pStepFast,
        String format
    );

    boolean inputScalarN(
        String label,
        long[] pData,
        int components,
        long pStep,
        long pStepFast,
        String format,
        int imGuiSliderFlags
    );

    boolean inputScalarN(String label, float[] pData, int components);

    boolean inputScalarN(String label, float[] pData, int components, float pStep);

    boolean inputScalarN(
        String label,
        float[] pData,
        int components,
        float pStep,
        float pStepFast
    );

    boolean inputScalarN(
        String label,
        float[] pData,
        int components,
        float pStep,
        float pStepFast,
        String format
    );

    boolean inputScalarN(
        String label,
        float[] pData,
        int components,
        float pStep,
        float pStepFast,
        String format,
        int imGuiSliderFlags
    );

    boolean inputScalarN(String label, double[] pData, int components);

    boolean inputScalarN(String label, double[] pData, int components, double pStep);

    boolean inputScalarN(
        String label,
        double[] pData,
        int components,
        double pStep,
        double pStepFast
    );

    boolean inputScalarN(
        String label,
        double[] pData,
        int components,
        double pStep,
        double pStepFast,
        String format
    );

    boolean inputScalarN(
        String label,
        double[] pData,
        int components,
        double pStep,
        double pStepFast,
        String format,
        int imGuiSliderFlags
    );

    boolean inputScalarN(String label, int dataType, short[] pData, int components);

    boolean inputScalarN(
        String label,
        int dataType,
        short[] pData,
        int components,
        short pStep
    );

    boolean inputScalarN(
        String label,
        int dataType,
        short[] pData,
        int components,
        short pStep,
        short pStepFast
    );

    boolean inputScalarN(
        String label,
        int dataType,
        short[] pData,
        int components,
        short pStep,
        short pStepFast,
        String format
    );

    boolean inputScalarN(
        String label,
        int dataType,
        short[] pData,
        int components,
        short pStep,
        short pStepFast,
        String format,
        int imGuiSliderFlags
    );

    boolean inputScalarN(String label, int dataType, int[] pData, int components);

    boolean inputScalarN(
        String label,
        int dataType,
        int[] pData,
        int components,
        int pStep
    );

    boolean inputScalarN(
        String label,
        int dataType,
        int[] pData,
        int components,
        int pStep,
        int pStepFast
    );

    boolean inputScalarN(
        String label,
        int dataType,
        int[] pData,
        int components,
        int pStep,
        int pStepFast,
        String format
    );

    boolean inputScalarN(
        String label,
        int dataType,
        int[] pData,
        int components,
        int pStep,
        int pStepFast,
        String format,
        int imGuiSliderFlags
    );

    boolean inputScalarN(String label, int dataType, long[] pData, int components);

    boolean inputScalarN(
        String label,
        int dataType,
        long[] pData,
        int components,
        long pStep
    );

    boolean inputScalarN(
        String label,
        int dataType,
        long[] pData,
        int components,
        long pStep,
        long pStepFast
    );

    boolean inputScalarN(
        String label,
        int dataType,
        long[] pData,
        int components,
        long pStep,
        long pStepFast,
        String format
    );

    boolean inputScalarN(
        String label,
        int dataType,
        long[] pData,
        int components,
        long pStep,
        long pStepFast,
        String format,
        int imGuiSliderFlags
    );

    boolean inputScalarN(String label, int dataType, float[] pData, int components);

    boolean inputScalarN(
        String label,
        int dataType,
        float[] pData,
        int components,
        float pStep
    );

    boolean inputScalarN(
        String label,
        int dataType,
        float[] pData,
        int components,
        float pStep,
        float pStepFast
    );

    boolean inputScalarN(
        String label,
        int dataType,
        float[] pData,
        int components,
        float pStep,
        float pStepFast,
        String format
    );

    boolean inputScalarN(
        String label,
        int dataType,
        float[] pData,
        int components,
        float pStep,
        float pStepFast,
        String format,
        int imGuiSliderFlags
    );

    boolean inputScalarN(String label, int dataType, double[] pData, int components);

    boolean inputScalarN(
        String label,
        int dataType,
        double[] pData,
        int components,
        double pStep
    );

    boolean inputScalarN(
        String label,
        int dataType,
        double[] pData,
        int components,
        double pStep,
        double pStepFast
    );

    boolean inputScalarN(
        String label,
        int dataType,
        double[] pData,
        int components,
        double pStep,
        double pStepFast,
        String format
    );

    boolean inputScalarN(
        String label,
        int dataType,
        double[] pData,
        int components,
        double pStep,
        double pStepFast,
        String format,
        int imGuiSliderFlags
    );

    boolean colorEdit3(String label, float[] col);

    boolean colorEdit3(String label, float[] col, int imGuiColorEditFlags);

    boolean colorEdit4(String label, float[] col);

    boolean colorEdit4(String label, float[] col, int imGuiColorEditFlags);

    boolean colorPicker3(String label, float[] col);

    boolean colorPicker3(String label, float[] col, int imGuiColorEditFlags);

    boolean colorPicker4(String label, float[] col);

    boolean colorPicker4(String label, float[] col, int imGuiColorEditFlags);

    boolean colorPicker4(String label, float[] col, int imGuiColorEditFlags, float[] refCol);

    boolean colorPicker4(String label, float[] col, float[] refCol);

    boolean colorButton(String descId, KVector4f col);

    boolean colorButton(String descId, float colX, float colY, float colZ, float colW);

    boolean colorButton(String descId, KVector4f col, int imGuiColorEditFlags);

    boolean colorButton(
        String descId,
        float colX,
        float colY,
        float colZ,
        float colW,
        int imGuiColorEditFlags
    );

    boolean colorButton(String descId, KVector4f col, int imGuiColorEditFlags, KVector2f size);

    boolean colorButton(
        String descId,
        float colX,
        float colY,
        float colZ,
        float colW,
        int imGuiColorEditFlags,
        float sizeX,
        float sizeY
    );

    boolean colorButton(String descId, KVector4f col, KVector2f size);

    boolean colorButton(
        String descId,
        float colX,
        float colY,
        float colZ,
        float colW,
        float sizeX,
        float sizeY
    );

    @Deprecated
    boolean colorButton(String descId, float[] col);

    @Deprecated
    boolean colorButton(String descId, float[] col, int imGuiColorEditFlags);

    @Deprecated
    boolean colorButton(String descId, float[] col, int imGuiColorEditFlags, KVector2f size);

    @Deprecated
    boolean colorButton(
        String descId,
        float[] col,
        int imGuiColorEditFlags,
        float sizeX,
        float sizeY
    );

    @Deprecated
    boolean colorButton(String descId, float[] col, KVector2f size);

    @Deprecated
    boolean colorButton(String descId, float[] col, float sizeX, float sizeY);

    void setColorEditOptions(int imGuiColorEditFlags);

    boolean treeNode(String label);

    boolean treeNode(String strId, String label);

    boolean treeNode(long ptrId, String label);

    boolean treeNodeEx(String label);

    boolean treeNodeEx(String label, int flags);

    boolean treeNodeEx(String strId, int flags, String label);

    boolean treeNodeEx(long ptrId, int flags, String label);

    void treePush(String strId);

    void treePush(long ptrId);

    void treePop();

    float getTreeNodeToLabelSpacing();

    boolean collapsingHeader(String label);

    boolean collapsingHeader(String label, int imGuiTreeNodeFlags);

    boolean collapsingHeader(String label, KBooleanReferenceValue pVisible);

    boolean collapsingHeader(String label, KBooleanReferenceValue pVisible, int imGuiTreeNodeFlags);

    void setNextItemOpen(boolean isOpen);

    void setNextItemOpen(boolean isOpen, int cond);

    boolean selectable(String label);

    boolean selectable(String label, boolean selected);

    boolean selectable(String label, boolean selected, int imGuiSelectableFlags);

    boolean selectable(String label, boolean selected, int imGuiSelectableFlags, KVector2f size);

    boolean selectable(
        String label,
        boolean selected,
        int imGuiSelectableFlags,
        float sizeX,
        float sizeY
    );

    boolean selectable(String label, int imGuiSelectableFlags, KVector2f size);

    boolean selectable(String label, int imGuiSelectableFlags, float sizeX, float sizeY);

    boolean selectable(String label, KVector2f size);

    boolean selectable(String label, float sizeX, float sizeY);

    boolean selectable(String label, boolean selected, KVector2f size);

    boolean selectable(String label, boolean selected, float sizeX, float sizeY);

    boolean selectable(String label, KBooleanReferenceValue pSelected);

    boolean selectable(String label, KBooleanReferenceValue pSelected, int imGuiSelectableFlags);

    boolean selectable(
        String label,
        KBooleanReferenceValue pSelected,
        int imGuiSelectableFlags,
        KVector2f size
    );

    boolean selectable(
        String label,
        KBooleanReferenceValue pSelected,
        int imGuiSelectableFlags,
        float sizeX,
        float sizeY
    );

    boolean selectable(String label, KBooleanReferenceValue pSelected, KVector2f size);

    boolean selectable(String label, KBooleanReferenceValue pSelected, float sizeX, float sizeY);

    boolean beginListBox(String label);

    boolean beginListBox(String label, KVector2f size);

    boolean beginListBox(String label, float sizeX, float sizeY);

    void endListBox();

    void listBox(String label, KIntReferenceValue currentItem, String[] items);

    void listBox(String label, KIntReferenceValue currentItem, String[] items, int heightInItems);

    void plotLines(String label, float[] values, int valuesCount);

    void plotLines(String label, float[] values, int valuesCount, int valuesOffset);

    void plotLines(
        String label,
        float[] values,
        int valuesCount,
        int valuesOffset,
        String overlayText
    );

    void plotLines(
        String label,
        float[] values,
        int valuesCount,
        int valuesOffset,
        String overlayText,
        float scaleMin
    );

    void plotLines(
        String label,
        float[] values,
        int valuesCount,
        int valuesOffset,
        String overlayText,
        float scaleMin,
        float scaleMax
    );

    void plotLines(
        String label,
        float[] values,
        int valuesCount,
        int valuesOffset,
        String overlayText,
        float scaleMin,
        float scaleMax,
        KVector2f graphSize
    );

    void plotLines(
        String label,
        float[] values,
        int valuesCount,
        int valuesOffset,
        String overlayText,
        float scaleMin,
        float scaleMax,
        float graphSizeX,
        float graphSizeY
    );

    void plotLines(
        String label,
        float[] values,
        int valuesCount,
        int valuesOffset,
        String overlayText,
        float scaleMin,
        float scaleMax,
        KVector2f graphSize,
        int stride
    );

    void plotLines(
        String label,
        float[] values,
        int valuesCount,
        int valuesOffset,
        String overlayText,
        float scaleMin,
        float scaleMax,
        float graphSizeX,
        float graphSizeY,
        int stride
    );

    void plotLines(
        String label,
        float[] values,
        int valuesCount,
        String overlayText,
        float scaleMin,
        float scaleMax,
        KVector2f graphSize,
        int stride
    );

    void plotLines(
        String label,
        float[] values,
        int valuesCount,
        String overlayText,
        float scaleMin,
        float scaleMax,
        float graphSizeX,
        float graphSizeY,
        int stride
    );

    void plotLines(
        String label,
        float[] values,
        int valuesCount,
        float scaleMin,
        float scaleMax,
        KVector2f graphSize,
        int stride
    );

    void plotLines(
        String label,
        float[] values,
        int valuesCount,
        float scaleMin,
        float scaleMax,
        float graphSizeX,
        float graphSizeY,
        int stride
    );

    void plotLines(
        String label,
        float[] values,
        int valuesCount,
        int valuesOffset,
        float scaleMin,
        float scaleMax,
        KVector2f graphSize,
        int stride
    );

    void plotLines(
        String label,
        float[] values,
        int valuesCount,
        int valuesOffset,
        float scaleMin,
        float scaleMax,
        float graphSizeX,
        float graphSizeY,
        int stride
    );

    void plotLines(
        String label,
        float[] values,
        int valuesCount,
        int valuesOffset,
        String overlayText,
        float scaleMin,
        float scaleMax,
        int stride
    );

    void plotHistogram(String label, float[] values, int valuesCount);

    void plotHistogram(String label, float[] values, int valuesCount, int valuesOffset);

    void plotHistogram(
        String label,
        float[] values,
        int valuesCount,
        int valuesOffset,
        String overlayText
    );

    void plotHistogram(
        String label,
        float[] values,
        int valuesCount,
        int valuesOffset,
        String overlayText,
        float scaleMin
    );

    void plotHistogram(
        String label,
        float[] values,
        int valuesCount,
        int valuesOffset,
        String overlayText,
        float scaleMin,
        float scaleMax
    );

    void plotHistogram(
        String label,
        float[] values,
        int valuesCount,
        int valuesOffset,
        String overlayText,
        float scaleMin,
        float scaleMax,
        KVector2f graphSize
    );

    void plotHistogram(
        String label,
        float[] values,
        int valuesCount,
        int valuesOffset,
        String overlayText,
        float scaleMin,
        float scaleMax,
        float graphSizeX,
        float graphSizeY
    );

    void plotHistogram(
        String label,
        float[] values,
        int valuesCount,
        int valuesOffset,
        String overlayText,
        float scaleMin,
        float scaleMax,
        KVector2f graphSize,
        int stride
    );

    void plotHistogram(
        String label,
        float[] values,
        int valuesCount,
        int valuesOffset,
        String overlayText,
        float scaleMin,
        float scaleMax,
        float graphSizeX,
        float graphSizeY,
        int stride
    );

    void plotHistogram(
        String label,
        float[] values,
        int valuesCount,
        String overlayText,
        float scaleMin,
        float scaleMax,
        KVector2f graphSize,
        int stride
    );

    void plotHistogram(
        String label,
        float[] values,
        int valuesCount,
        String overlayText,
        float scaleMin,
        float scaleMax,
        float graphSizeX,
        float graphSizeY,
        int stride
    );

    void plotHistogram(
        String label,
        float[] values,
        int valuesCount,
        float scaleMin,
        float scaleMax,
        KVector2f graphSize,
        int stride
    );

    void plotHistogram(
        String label,
        float[] values,
        int valuesCount,
        float scaleMin,
        float scaleMax,
        float graphSizeX,
        float graphSizeY,
        int stride
    );

    void plotHistogram(
        String label,
        float[] values,
        int valuesCount,
        int valuesOffset,
        float scaleMin,
        float scaleMax,
        KVector2f graphSize,
        int stride
    );

    void plotHistogram(
        String label,
        float[] values,
        int valuesCount,
        int valuesOffset,
        float scaleMin,
        float scaleMax,
        float graphSizeX,
        float graphSizeY,
        int stride
    );

    void plotHistogram(
        String label,
        float[] values,
        int valuesCount,
        int valuesOffset,
        String overlayText,
        float scaleMin,
        float scaleMax,
        int stride
    );

    void value(String prefix, Number value);

    void value(String prefix, float value, String floatFormat);

    boolean beginMenuBar();

    void endMenuBar();

    boolean beginMainMenuBar();

    void endMainMenuBar();

    boolean beginMenu(String label);

    boolean beginMenu(String label, boolean enabled);

    void endMenu();

    boolean menuItem(String label);

    boolean menuItem(String label, boolean selected);

    boolean menuItem(String label, boolean selected, boolean enabled);

    boolean menuItem(String label, String shortcut);

    boolean menuItem(String label, String shortcut, boolean selected);

    boolean menuItem(String label, String shortcut, boolean selected, boolean enabled);

    boolean menuItem(String label, String shortcut, KBooleanReferenceValue pSelected);

    boolean menuItem(String label, String shortcut, KBooleanReferenceValue pSelected, boolean enabled);

    void beginTooltip();

    void endTooltip();

    void setTooltip(String text);

    boolean beginItemTooltip();

    void setItemTooltip(String text);

    boolean beginPopup(String strId);

    boolean beginPopup(String strId, int imGuiWindowFlags);

    boolean beginPopupModal(String name);

    boolean beginPopupModal(String name, KBooleanReferenceValue pOpen);

    boolean beginPopupModal(String name, KBooleanReferenceValue pOpen, int imGuiWindowFlags);

    boolean beginPopupModal(String name, int imGuiWindowFlags);

    void endPopup();

    void openPopup(String strId);

    void openPopup(String strId, int imGuiPopupFlags);

    void openPopup(int id);

    void openPopup(int id, int imGuiPopupFlags);

    void openPopupOnItemClick();

    void openPopupOnItemClick(String strId);

    void openPopupOnItemClick(String strId, int imGuiPopupFlags);

    void openPopupOnItemClick(int imGuiPopupFlags);

    void closeCurrentPopup();

    boolean beginPopupContextItem();

    boolean beginPopupContextItem(String strId);

    boolean beginPopupContextItem(String strId, int imGuiPopupFlags);

    boolean beginPopupContextItem(int imGuiPopupFlags);

    boolean beginPopupContextWindow();

    boolean beginPopupContextWindow(String strId);

    boolean beginPopupContextWindow(String strId, int imGuiPopupFlags);

    boolean beginPopupContextWindow(int imGuiPopupFlags);

    boolean beginPopupContextVoid();

    boolean beginPopupContextVoid(String strId);

    boolean beginPopupContextVoid(String strId, int imGuiPopupFlags);

    boolean beginPopupContextVoid(int imGuiPopupFlags);

    boolean isPopupOpen(String strId);

    boolean isPopupOpen(String strId, int imGuiPopupFlags);

    boolean beginTable(String id, int columns);

    boolean beginTable(String id, int columns, int imGuiTableFlags);

    boolean beginTable(String id, int columns, int imGuiTableFlags, KVector2f outerSize);

    boolean beginTable(
        String id,
        int columns,
        int imGuiTableFlags,
        float outerSizeX,
        float outerSizeY
    );

    boolean beginTable(
        String id,
        int columns,
        int imGuiTableFlags,
        KVector2f outerSize,
        float innerWidth
    );

    boolean beginTable(
        String id,
        int columns,
        int imGuiTableFlags,
        float outerSizeX,
        float outerSizeY,
        float innerWidth
    );

    boolean beginTable(String id, int columns, KVector2f outerSize, float innerWidth);

    boolean beginTable(
        String id,
        int columns,
        float outerSizeX,
        float outerSizeY,
        float innerWidth
    );

    boolean beginTable(String id, int columns, float innerWidth);

    boolean beginTable(String id, int columns, int imGuiTableFlags, float innerWidth);

    void endTable();

    void tableNextRow();

    void tableNextRow(int imGuiTableRowFlags);

    void tableNextRow(int imGuiTableRowFlags, float minRowHeight);

    void tableNextRow(float minRowHeight);

    boolean tableNextColumn();

    boolean tableSetColumnIndex(int columnN);

    void tableSetupColumn(String label);

    void tableSetupColumn(String label, int imGuiTableColumnFlags);

    void tableSetupColumn(String label, int imGuiTableColumnFlags, float initWidthOrWeight);

    void tableSetupColumn(
        String label,
        int imGuiTableColumnFlags,
        float initWidthOrWeight,
        int userId
    );

    void tableSetupColumn(String label, float initWidthOrWeight, int userId);

    void tableSetupColumn(String label, int imGuiTableColumnFlags, int userId);

    void tableSetupScrollFreeze(int cols, int rows);

    void tableHeader(String label);

    void tableHeadersRow();

    void tableAngledHeadersRow();

    KImGuiTableSortSpecs tableGetSortSpecs();

    int tableGetColumnCount();

    int tableGetColumnIndex();

    int tableGetRowIndex();

    String tableGetColumnName();

    String tableGetColumnName(int columnN);

    int tableGetColumnFlags();

    int tableGetColumnFlags(int columnN);

    void tableSetColumnEnabled(int columnN, boolean value);

    int tableGetHoveredColumn();

    void tableSetBgColor(int imGuiTableBgTarget, int color);

    void tableSetBgColor(int imGuiTableBgTarget, int color, int columnN);

    void columns();

    void columns(int count);

    void columns(int count, String id);

    void columns(int count, String id, boolean border);

    void columns(String id, boolean border);

    void columns(boolean border);

    void columns(int count, boolean border);

    void nextColumn();

    int getColumnIndex();

    float getColumnWidth();

    float getColumnWidth(int columnIndex);

    void setColumnWidth(int columnIndex, float width);

    float getColumnOffset();

    float getColumnOffset(int columnIndex);

    void setColumnOffset(int columnIndex, float offsetX);

    int getColumnsCount();

    boolean beginTabBar(String strId);

    boolean beginTabBar(String strId, int imGuiTabBarFlags);

    void endTabBar();

    boolean beginTabItem(String label);

    boolean beginTabItem(String label, KBooleanReferenceValue pOpen);

    boolean beginTabItem(String label, KBooleanReferenceValue pOpen, int imGuiTabItemFlags);

    boolean beginTabItem(String label, int imGuiTabItemFlags);

    void endTabItem();

    boolean tabItemButton(String label);

    boolean tabItemButton(String label, int imGuiTabItemFlags);

    void setTabItemClosed(String tabOrDockedWindowLabel);

    int dockSpace(int dockspaceId);

    int dockSpace(int dockspaceId, KVector2f size);

    int dockSpace(int dockspaceId, float sizeX, float sizeY);

    int dockSpace(int dockspaceId, KVector2f size, int imGuiDockNodeFlags);

    int dockSpace(int dockspaceId, float sizeX, float sizeY, int imGuiDockNodeFlags);

    int dockSpace(
        int dockspaceId,
        KVector2f size,
        int imGuiDockNodeFlags,
        KImGuiWindowClass windowClass
    );

    int dockSpace(
        int dockspaceId,
        float sizeX,
        float sizeY,
        int imGuiDockNodeFlags,
        KImGuiWindowClass windowClass
    );

    int dockSpace(int dockspaceId, int imGuiDockNodeFlags, KImGuiWindowClass windowClass);

    int dockSpace(int dockspaceId, KImGuiWindowClass windowClass);

    int dockSpace(int dockspaceId, KVector2f size, KImGuiWindowClass windowClass);

    int dockSpace(int dockspaceId, float sizeX, float sizeY, KImGuiWindowClass windowClass);

    int dockSpaceOverViewport();

    int dockSpaceOverViewport(int dockspaceId);

    int dockSpaceOverViewport(int dockspaceId, KImGuiViewport viewport);

    int dockSpaceOverViewport(int dockspaceId, KImGuiViewport viewport, int imGuiDockNodeFlags);

    int dockSpaceOverViewport(
        int dockspaceId,
        KImGuiViewport viewport,
        int imGuiDockNodeFlags,
        KImGuiWindowClass windowClass
    );

    int dockSpaceOverViewport(
        KImGuiViewport viewport,
        int imGuiDockNodeFlags,
        KImGuiWindowClass windowClass
    );

    int dockSpaceOverViewport(int dockspaceId, KImGuiViewport viewport, KImGuiWindowClass windowClass);

    void setNextWindowDockID(int dockId);

    void setNextWindowDockID(int dockId, int imGuiCond);

    void setNextWindowClass(KImGuiWindowClass windowClass);

    int getWindowDockID();

    boolean isWindowDocked();

    void logToTTY();

    void logToTTY(int autoOpenDepth);

    void logToFile();

    void logToFile(int autoOpenDepth);

    void logToFile(int autoOpenDepth, String filename);

    void logToFile(String filename);

    void logToClipboard();

    void logToClipboard(int autoOpenDepth);

    void logFinish();

    void logButtons();

    void logText(String text);

    boolean beginDragDropSource();

    boolean beginDragDropSource(int imGuiDragDropFlags);

    boolean setDragDropPayload(String dataType, Object payload);

    boolean setDragDropPayload(String dataType, Object payload, int imGuiCond);

    boolean setDragDropPayload(Object payload);

    boolean setDragDropPayload(Object payload, int imGuiCond);

    void endDragDropSource();

    boolean beginDragDropTarget();

    <T> T acceptDragDropPayload(String dataType);

    <T> T acceptDragDropPayload(String dataType, Class<T> aClass);

    <T> T acceptDragDropPayload(String dataType, int imGuiDragDropFlags);
    
    <T> T acceptDragDropPayload(String dataType, int imGuiDragDropFlags, Class<T> aClass);

    <T> T acceptDragDropPayload(Class<T> aClass);

    <T> T acceptDragDropPayload(Class<T> aClass, int imGuiDragDropFlags);

    void endDragDropTarget();
    
    <T> T getDragDropPayload();
    
    <T> T getDragDropPayload(String dataType);

    <T> T getDragDropPayload(Class<T> aClass);

    void beginDisabled();

    void beginDisabled(boolean disabled);

    void endDisabled();

    void pushClipRect(KVector2f clipRectMin, KVector2f clipRectMax, boolean intersectWithCurrentClipRect);

    void pushClipRect(
        float clipRectMinX,
        float clipRectMinY,
        float clipRectMaxX,
        float clipRectMaxY,
        boolean intersectWithCurrentClipRect
    );

    void popClipRect();

    void setItemDefaultFocus();

    void setKeyboardFocusHere();

    void setKeyboardFocusHere(int offset);

    void setNextItemAllowOverlap();

    boolean isItemHovered();

    boolean isItemHovered(int imGuiHoveredFlags);

    boolean isItemActive();

    boolean isItemFocused();

    boolean isItemClicked();

    boolean isItemClicked(int mouseButton);

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

    void getItemRectMin(KVector2f dst);

    KVector2f getItemRectMax();

    float getItemRectMaxX();

    float getItemRectMaxY();

    void getItemRectMax(KVector2f dst);

    KVector2f getItemRectSize();

    float getItemRectSizeX();

    float getItemRectSizeY();

    void getItemRectSize(KVector2f dst);

    KImGuiViewport getMainViewport();

    KImDrawList getBackgroundDrawList();

    KImDrawList getBackgroundDrawList(KImGuiViewport viewport);

    KImDrawList getForegroundDrawList();

    KImDrawList getForegroundDrawList(KImGuiViewport viewport);

    boolean isRectVisible(KVector2f size);

    boolean isRectVisible(float sizeX, float sizeY);

    boolean isRectVisible(KVector2f rectMin, KVector2f rectMax);

    boolean isRectVisible(float rectMinX, float rectMinY, float rectMaxX, float rectMaxY);

    double getTime();

    int getFrameCount();

    String getStyleColorName(int imGuiColIdx);

    KImGuiStorage getStateStorage();

    void setStateStorage(KImGuiStorage storage);

    KVector2f calcTextSize(String text);

    float calcTextSizeX(String text);

    float calcTextSizeY(String text);

    void calcTextSize(KVector2f dst, String text);

    KVector2f calcTextSize(String text, boolean hideTextAfterDoubleHash);

    float calcTextSizeX(String text, boolean hideTextAfterDoubleHash);

    float calcTextSizeY(String text, boolean hideTextAfterDoubleHash);

    void calcTextSize(KVector2f dst, String text, boolean hideTextAfterDoubleHash);

    KVector2f calcTextSize(String text, boolean hideTextAfterDoubleHash, float wrapWidth);

    float calcTextSizeX(String text, boolean hideTextAfterDoubleHash, float wrapWidth);

    float calcTextSizeY(String text, boolean hideTextAfterDoubleHash, float wrapWidth);

    void calcTextSize(
        KVector2f dst,
        String text,
        boolean hideTextAfterDoubleHash,
        float wrapWidth
    );

    KVector2f calcTextSize(String text, float wrapWidth);

    float calcTextSizeX(String text, float wrapWidth);

    float calcTextSizeY(String text, float wrapWidth);

    void calcTextSize(KVector2f dst, String text, float wrapWidth);

    KVector4f colorConvertU32ToFloat4(int in);

    float colorConvertU32ToFloat4X(int in);

    float colorConvertU32ToFloat4Y(int in);

    float colorConvertU32ToFloat4Z(int in);

    float colorConvertU32ToFloat4W(int in);

    void colorConvertU32ToFloat4(KVector4f dst, int in);

    int colorConvertFloat4ToU32(KVector4f in);

    int colorConvertFloat4ToU32(float inX, float inY, float inZ, float inW);

    void colorConvertRGBtoHSV(float[] rgb, float[] hsv);

    void colorConvertHSVtoRGB(float[] hsv, float[] rgb);

    boolean isKeyDown(int key);

    boolean isKeyPressed(int key);

    boolean isKeyPressed(int key, boolean repeat);

    boolean isKeyReleased(int key);

    boolean isKeyChordPressed(int keyChord);

    boolean getKeyPressedAmount(int key, float repeatDelay, float rate);

    String getKeyName(int key);

    void setNextFrameWantCaptureKeyboard(boolean wantCaptureKeyboard);

    boolean shortcut(int keyChord);

    boolean shortcut(int keyChord, int flags);

    void setNextItemShortcut(int keyChord);

    void setNextItemShortcut(int keyChord, int flags);

    boolean isMouseDown(int button);

    boolean isMouseClicked(int button);

    boolean isMouseClicked(int button, boolean repeat);

    boolean isMouseReleased(int button);

    boolean isMouseDoubleClicked(int button);

    int getMouseClickedCount(int button);

    boolean isMouseHoveringRect(KVector2f rMin, KVector2f rMax);

    boolean isMouseHoveringRect(float rMinX, float rMinY, float rMaxX, float rMaxY);

    boolean isMouseHoveringRect(KVector2f rMin, KVector2f rMax, boolean clip);

    boolean isMouseHoveringRect(
        float rMinX,
        float rMinY,
        float rMaxX,
        float rMaxY,
        boolean clip
    );

    boolean isMousePosValid();

    boolean isMousePosValid(KVector2f mousePos);

    boolean isMousePosValid(float mousePosX, float mousePosY);

    boolean isAnyMouseDown();

    KVector2f getMousePos();

    float getMousePosX();

    float getMousePosY();

    void getMousePos(KVector2f dst);

    KVector2f getMousePosOnOpeningCurrentPopup();

    float getMousePosOnOpeningCurrentPopupX();

    float getMousePosOnOpeningCurrentPopupY();

    void getMousePosOnOpeningCurrentPopup(KVector2f dst);

    boolean isMouseDragging(int button);

    boolean isMouseDragging(int button, float lockThreshold);

    KVector2f getMouseDragDelta();

    float getMouseDragDeltaX();

    float getMouseDragDeltaY();

    void getMouseDragDelta(KVector2f dst);

    KVector2f getMouseDragDelta(int button);

    float getMouseDragDeltaX(int button);

    float getMouseDragDeltaY(int button);

    void getMouseDragDelta(KVector2f dst, int button);

    KVector2f getMouseDragDelta(int button, float lockThreshold);

    float getMouseDragDeltaX(int button, float lockThreshold);

    float getMouseDragDeltaY(int button, float lockThreshold);

    void getMouseDragDelta(KVector2f dst, int button, float lockThreshold);

    void resetMouseDragDelta();

    void resetMouseDragDelta(int button);

    int getMouseCursor();

    void setMouseCursor(int type);

    void setNextFrameWantCaptureMouse(boolean wantCaptureMouse);

    String getClipboardText();

    void setClipboardText(String text);

    void loadIniSettingsFromDisk(String iniFilename);

    void loadIniSettingsFromMemory(String iniData);

    void loadIniSettingsFromMemory(String iniData, int iniSize);

    void saveIniSettingsToDisk(String iniFilename);

    String saveIniSettingsToMemory();

    String saveIniSettingsToMemory(long outIniSize);

    void debugTextEncoding(String text);

    void debugFlashStyleColor(int idx);

    void debugStartItemPicker();

    boolean debugCheckVersionAndDataLayout(
        String versionStr,
        int szIo,
        int szStyle,
        int szVec2,
        int szVec4,
        int szDrawVert,
        int szDrawIdx
    );

    KImGuiPlatformIo getPlatformIO();

    void updatePlatformWindows();

    void renderPlatformWindowsDefault();

    void destroyPlatformWindows();

    KImGuiViewport findViewportByID(int imGuiID);

    KImGuiViewport findViewportByPlatformHandle(long platformHandle);

}

