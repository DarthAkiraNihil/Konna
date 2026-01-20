package io.github.darthakiranihil.konna.libfrontend.imgui;

import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.struct.KVector4f;

public interface KImGuiStyle {
    

    float getAlpha();
    void setAlpha(final float value);

    float getDisabledAlpha();
    void setDisabledAlpha(final float value);
    KVector2f getWindowPadding();
    float getWindowPaddingX();
    float getWindowPaddingY();
    void getWindowPadding(final KVector2f dst);
    void setWindowPadding(final KVector2f value);
    void setWindowPadding(final float valueX, final float valueY);
    
    float getWindowRounding();
    void setWindowRounding(final float value);
    
    float getWindowBorderSize();
    void setWindowBorderSize(final float value);

    KVector2f getWindowMinSize();
    float getWindowMinSizeX();
    float getWindowMinSizeY();
    void getWindowMinSize(final KVector2f dst);
    void setWindowMinSize(final KVector2f value);
    void setWindowMinSize(final float valueX, final float valueY);

    KVector2f getWindowTitleAlign();
    float getWindowTitleAlignX();
    float getWindowTitleAlignY();
    void getWindowTitleAlign(final KVector2f dst);
    void setWindowTitleAlign(final KVector2f value);
    void setWindowTitleAlign(final float valueX, final float valueY);
    
    int getWindowMenuButtonPosition();
    void setWindowMenuButtonPosition(final int value);
    
    float getChildRounding();
    void setChildRounding(final float value);


    float getChildBorderSize();

    void setChildBorderSize(final float value);
    
    float getPopupRounding();

    void setPopupRounding(final float value);
    
    float getPopupBorderSize();

    void setPopupBorderSize(final float value);

    KVector2f getFramePadding();
    float getFramePaddingX();
    float getFramePaddingY();
    void getFramePadding(final KVector2f dst);
    void setFramePadding(final KVector2f value);
    void setFramePadding(final float valueX, final float valueY);
    float getFrameRounding();

    void setFrameRounding(final float value);

    float getFrameBorderSize();
    void setFrameBorderSize(final float value);

    KVector2f getItemSpacing();
    float getItemSpacingX();
    float getItemSpacingY();
    void getItemSpacing(final KVector2f dst);
    void setItemSpacing(final KVector2f value);
    void setItemSpacing(final float valueX, final float valueY);

    KVector2f getItemInnerSpacing();
    float getItemInnerSpacingX();
    float getItemInnerSpacingY();
    void getItemInnerSpacing(final KVector2f dst);
    void setItemInnerSpacing(final KVector2f value);
    void setItemInnerSpacing(final float valueX, final float valueY);

    KVector2f getCellPadding();
    float getCellPaddingX();
    float getCellPaddingY();
    void getCellPadding(final KVector2f dst);
    void setCellPadding(final KVector2f value);
    void setCellPadding(final float valueX, final float valueY);

    KVector2f getTouchExtraPadding();
    float getTouchExtraPaddingX();
    float getTouchExtraPaddingY();
    void getTouchExtraPadding(final KVector2f dst);
    void setTouchExtraPadding(final KVector2f value);
    void setTouchExtraPadding(final float valueX, final float valueY);

    float getIndentSpacing();
    void setIndentSpacing(final float value);

    float getColumnsMinSpacing();
    void setColumnsMinSpacing(final float value);

    float getScrollbarSize();
    void setScrollbarSize(final float value);

    float getScrollbarRounding();
    void setScrollbarRounding(final float value);

    float getGrabMinSize();
    void setGrabMinSize(final float value);

    void setGrabRounding(final float value);
    float getLogSliderDeadzone();
    void setLogSliderDeadzone(final float value);
    float getTabRounding();
    void setTabRounding(final float value);

    float getTabBorderSize();
    void setTabBorderSize(final float value);
    float getTabMinWidthForCloseButton();
    void setTabMinWidthForCloseButton(final float value);
    int getColorButtonPosition();
    void setColorButtonPosition(final int value);
    KVector2f getButtonTextAlign();
    float getButtonTextAlignX();
    float getButtonTextAlignY();
    void getButtonTextAlign(final KVector2f dst);
    void setButtonTextAlign(final KVector2f value);
    void setButtonTextAlign(final float valueX, final float valueY);
    KVector2f getSelectableTextAlign();
    float getSelectableTextAlignX();
    float getSelectableTextAlignY();
    void getSelectableTextAlign(final KVector2f dst);
    void setSelectableTextAlign(final KVector2f value);
    void setSelectableTextAlign(final float valueX, final float valueY);

    float getSeparatorTextBorderSize();
    void setSeparatorTextBorderSize(final float value);

    KVector2f getSeparatorTextAlign();

    float getSeparatorTextAlignX();
    float getSeparatorTextAlignY();
    void getSeparatorTextAlign(final KVector2f dst);
    void setSeparatorTextAlign(final KVector2f value);
    void setSeparatorTextAlign(final float valueX, final float valueY);
    
    KVector2f getSeparatorTextPadding();
    float getSeparatorTextPaddingX();
    float getSeparatorTextPaddingY();
    void getSeparatorTextPadding(final KVector2f dst);
    void setSeparatorTextPadding(final KVector2f value);
    void setSeparatorTextPadding(final float valueX, final float valueY);
    KVector2f getDisplayWindowPadding();
    float getDisplayWindowPaddingX();
    float getDisplayWindowPaddingY();
    void getDisplayWindowPadding(final KVector2f dst);
    void setDisplayWindowPadding(final KVector2f value);
    void setDisplayWindowPadding(final float valueX, final float valueY);

    KVector2f getDisplaySafeAreaPadding();
    float getDisplaySafeAreaPaddingX();
    float getDisplaySafeAreaPaddingY();
    void getDisplaySafeAreaPadding(final KVector2f dst);
    void setDisplaySafeAreaPadding(final KVector2f value);
    void setDisplaySafeAreaPadding(final float valueX, final float valueY);

    float getDockingSeparatorSize();
    void setDockingSeparatorSize(final float value);
    float getMouseCursorScale();
    void setMouseCursorScale(final float value);
    boolean getAntiAliasedLines();
    void setAntiAliasedLines(final boolean value);
    boolean getAntiAliasedLinesUseTex();
    void setAntiAliasedLinesUseTex(final boolean value);
    boolean getAntiAliasedFill();
    void setAntiAliasedFill(final boolean value);
    float getCurveTessellationTol();
    void setCurveTessellationTol(final float value);
    float getCircleTessellationMaxError();
    void setCircleTessellationMaxError(final float value);
    KVector4f[] getColors();
    void setColors(final KVector4f[] value);
    KVector4f getColor(final int col);
    float getHoverStationaryDelay();
    void setHoverStationaryDelay(final float value);
    float getHoverDelayShort();
    void setHoverDelayShort(final float value);
    float getHoverDelayNormal();
    void setHoverDelayNormal(final float value);
    int getHoverFlagsForTooltipMouse();
    void setHoverFlagsForTooltipMouse(final int value);
    int getHoverFlagsForTooltipNav();
    void setHoverFlagsForTooltipNav(final int value);

    void scaleAllSizes(final float scaleFactor);


}
