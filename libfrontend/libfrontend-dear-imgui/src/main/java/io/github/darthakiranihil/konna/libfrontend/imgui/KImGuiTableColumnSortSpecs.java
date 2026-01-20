package io.github.darthakiranihil.konna.libfrontend.imgui;

/**
 * Interface representing ImGuiTableColumnSortSpecs of Dear ImGui.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public interface KImGuiTableColumnSortSpecs {
    
    int getColumnUserID();
    int getColumnIndex();
    int getSortOrder();
    int getSortDirection();
}
