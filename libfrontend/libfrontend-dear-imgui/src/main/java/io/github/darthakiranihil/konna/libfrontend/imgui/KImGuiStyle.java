package io.github.darthakiranihil.konna.libfrontend.imgui;

import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.struct.KVector4f;

/**
 * Interface representing ImGuiStyle of Dear ImGui.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
public interface KImGuiStyle {
    

    float getAlpha();
    void setAlpha(float value);

    float getDisabledAlpha();
    void setDisabledAlpha(float value);
    KVector2f getWindowPadding();
    float getWindowPaddingX();
    float getWindowPaddingY();
    // void getWindowPadding(KVector2f dst);
    void setWindowPadding(KVector2f value);
    void setWindowPadding(float valueX, float valueY);
    
    float getWindowRounding();
    void setWindowRounding(float value);
    
    float getWindowBorderSize();
    void setWindowBorderSize(float value);

    KVector2f getWindowMinSize();
    float getWindowMinSizeX();
    float getWindowMinSizeY();
    // void getWindowMinSize(KVector2f dst);
    void setWindowMinSize(KVector2f value);
    void setWindowMinSize(float valueX, float valueY);

    KVector2f getWindowTitleAlign();
    float getWindowTitleAlignX();
    float getWindowTitleAlignY();
    // void getWindowTitleAlign(KVector2f dst);
    void setWindowTitleAlign(KVector2f value);
    void setWindowTitleAlign(float valueX, float valueY);
    
    int getWindowMenuButtonPosition();
    void setWindowMenuButtonPosition(int value);
    
    float getChildRounding();
    void setChildRounding(float value);


    float getChildBorderSize();

    void setChildBorderSize(float value);
    
    float getPopupRounding();

    void setPopupRounding(float value);
    
    float getPopupBorderSize();

    void setPopupBorderSize(float value);

    KVector2f getFramePadding();
    float getFramePaddingX();
    float getFramePaddingY();
    // void getFramePadding(KVector2f dst);
    void setFramePadding(KVector2f value);
    void setFramePadding(float valueX, float valueY);
    float getFrameRounding();

    void setFrameRounding(float value);

    float getFrameBorderSize();
    void setFrameBorderSize(float value);

    KVector2f getItemSpacing();
    float getItemSpacingX();
    float getItemSpacingY();
    // void getItemSpacing(KVector2f dst);
    void setItemSpacing(KVector2f value);
    void setItemSpacing(float valueX, float valueY);

    KVector2f getItemInnerSpacing();
    float getItemInnerSpacingX();
    float getItemInnerSpacingY();
    // void getItemInnerSpacing(KVector2f dst);
    void setItemInnerSpacing(KVector2f value);
    void setItemInnerSpacing(float valueX, float valueY);

    KVector2f getCellPadding();
    float getCellPaddingX();
    float getCellPaddingY();
    // void getCellPadding(KVector2f dst);
    void setCellPadding(KVector2f value);
    void setCellPadding(float valueX, float valueY);

    KVector2f getTouchExtraPadding();
    float getTouchExtraPaddingX();
    float getTouchExtraPaddingY();
    // void getTouchExtraPadding(KVector2f dst);
    void setTouchExtraPadding(KVector2f value);
    void setTouchExtraPadding(float valueX, float valueY);

    float getIndentSpacing();
    void setIndentSpacing(float value);

    float getColumnsMinSpacing();
    void setColumnsMinSpacing(float value);

    float getScrollbarSize();
    void setScrollbarSize(float value);

    float getScrollbarRounding();
    void setScrollbarRounding(float value);

    float getGrabMinSize();
    void setGrabMinSize(float value);

    float getGrabRounding();
    void setGrabRounding(float value);
    float getLogSliderDeadzone();
    void setLogSliderDeadzone(float value);
    float getTabRounding();
    void setTabRounding(float value);

    float getTabBorderSize();
    void setTabBorderSize(float value);
    float getTabMinWidthForCloseButton();
    void setTabMinWidthForCloseButton(float value);
    int getColorButtonPosition();
    void setColorButtonPosition(int value);
    KVector2f getButtonTextAlign();
    float getButtonTextAlignX();
    float getButtonTextAlignY();
    // void getButtonTextAlign(KVector2f dst);
    void setButtonTextAlign(KVector2f value);
    void setButtonTextAlign(float valueX, float valueY);
    KVector2f getSelectableTextAlign();
    float getSelectableTextAlignX();
    float getSelectableTextAlignY();
    // void getSelectableTextAlign(KVector2f dst);
    void setSelectableTextAlign(KVector2f value);
    void setSelectableTextAlign(float valueX, float valueY);

    float getSeparatorTextBorderSize();
    void setSeparatorTextBorderSize(float value);

    KVector2f getSeparatorTextAlign();

    float getSeparatorTextAlignX();
    float getSeparatorTextAlignY();
    // void getSeparatorTextAlign(KVector2f dst);
    void setSeparatorTextAlign(KVector2f value);
    void setSeparatorTextAlign(float valueX, float valueY);
    
    KVector2f getSeparatorTextPadding();
    float getSeparatorTextPaddingX();
    float getSeparatorTextPaddingY();
    // void getSeparatorTextPadding(KVector2f dst);
    void setSeparatorTextPadding(KVector2f value);
    void setSeparatorTextPadding(float valueX, float valueY);
    KVector2f getDisplayWindowPadding();
    float getDisplayWindowPaddingX();
    float getDisplayWindowPaddingY();
    // void getDisplayWindowPadding(KVector2f dst);
    void setDisplayWindowPadding(KVector2f value);
    void setDisplayWindowPadding(float valueX, float valueY);

    KVector2f getDisplaySafeAreaPadding();
    float getDisplaySafeAreaPaddingX();
    float getDisplaySafeAreaPaddingY();
    // void getDisplaySafeAreaPadding(KVector2f dst);
    void setDisplaySafeAreaPadding(KVector2f value);
    void setDisplaySafeAreaPadding(float valueX, float valueY);

    float getDockingSeparatorSize();
    void setDockingSeparatorSize(float value);
    float getMouseCursorScale();
    void setMouseCursorScale(float value);
    boolean getAntiAliasedLines();
    void setAntiAliasedLines(boolean value);
    boolean getAntiAliasedLinesUseTex();
    void setAntiAliasedLinesUseTex(boolean value);
    boolean getAntiAliasedFill();
    void setAntiAliasedFill(boolean value);
    float getCurveTessellationTol();
    void setCurveTessellationTol(float value);
    float getCircleTessellationMaxError();
    void setCircleTessellationMaxError(float value);
    KVector4f[] getColors();
    void setColors(KVector4f[] value);
    KVector4f getColor(int col);
    float getHoverStationaryDelay();
    void setHoverStationaryDelay(float value);
    float getHoverDelayShort();
    void setHoverDelayShort(float value);
    float getHoverDelayNormal();
    void setHoverDelayNormal(float value);
    int getHoverFlagsForTooltipMouse();
    void setHoverFlagsForTooltipMouse(int value);
    int getHoverFlagsForTooltipNav();
    void setHoverFlagsForTooltipNav(int value);

    void scaleAllSizes(float scaleFactor);


}
