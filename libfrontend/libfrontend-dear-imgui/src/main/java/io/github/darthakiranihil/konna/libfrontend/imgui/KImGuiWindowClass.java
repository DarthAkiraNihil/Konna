package io.github.darthakiranihil.konna.libfrontend.imgui;

/**
 * Interface representing ImGuiWindowClass of Dear ImGui.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public interface KImGuiWindowClass {
    
    int getClassId();
    void setClassId(int value);

    int getParentViewportId();
    void setParentViewportId(int value);

    int getFocusRouteParentWindowId();
    void setFocusRouteParentWindowId(int value);

    int getViewportFlagsOverrideSet();
    void setViewportFlagsOverrideSet(int value);
    void addViewportFlagsOverrideSet(int flags);
    void removeViewportFlagsOverrideSet(int flags);
    boolean hasViewportFlagsOverrideSet(int flags);


    int getViewportFlagsOverrideClear();
    void setViewportFlagsOverrideClear(int value);
    void addViewportFlagsOverrideClear(int flags);
    void removeViewportFlagsOverrideClear(int flags);
    boolean hasViewportFlagsOverrideClear(int flags);


    int getTabItemFlagsOverrideSet();
    void setTabItemFlagsOverrideSet(int value);
    void addTabItemFlagsOverrideSet(int flags);
    void removeTabItemFlagsOverrideSet(int flags);
    boolean hasTabItemFlagsOverrideSet(int flags);

    int getDockNodeFlagsOverrideSet();
    void setDockNodeFlagsOverrideSet(int value);
    void addDockNodeFlagsOverrideSet(int flags);
    void removeDockNodeFlagsOverrideSet(int flags);
    boolean hasDockNodeFlagsOverrideSet(int flags);

    boolean getDockingAlwaysTabBar();
    void setDockingAlwaysTabBar(boolean value);

    boolean getDockingAllowUnclassed();
    void setDockingAllowUnclassed(boolean value);

}
