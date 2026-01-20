package io.github.darthakiranihil.konna.libfrontend.imgui;

public interface KImGuiWindowClass {
    
    int getClassId();
    void setClassId(final int value);

    int getParentViewportId();
    void setParentViewportId(final int value);

    int getFocusRouteParentWindowId();
    void setFocusRouteParentWindowId(final int value);

    int getViewportFlagsOverrideSet();
    void setViewportFlagsOverrideSet(final int value);
    void addViewportFlagsOverrideSet(final int flags);
    void removeViewportFlagsOverrideSet(final int flags);
    boolean hasViewportFlagsOverrideSet(final int flags);


    int getViewportFlagsOverrideClear();
    void setViewportFlagsOverrideClear(final int value);
    void addViewportFlagsOverrideClear(final int flags);
    void removeViewportFlagsOverrideClear(final int flags);
    boolean hasViewportFlagsOverrideClear(final int flags);


    int getTabItemFlagsOverrideSet();
    void setTabItemFlagsOverrideSet(final int value);
    void addTabItemFlagsOverrideSet(final int flags);
    void removeTabItemFlagsOverrideSet(final int flags);
    boolean hasTabItemFlagsOverrideSet(final int flags);

    int getDockNodeFlagsOverrideSet();
    void setDockNodeFlagsOverrideSet(final int value);
    void addDockNodeFlagsOverrideSet(final int flags);
    void removeDockNodeFlagsOverrideSet(final int flags);
    boolean hasDockNodeFlagsOverrideSet(final int flags);

    boolean getDockingAlwaysTabBar();
    void setDockingAlwaysTabBar(final boolean value);

    boolean getDockingAllowUnclassed();
    void setDockingAllowUnclassed(final boolean value);

}
