package io.github.darthakiranihil.konna.libfrontend.imgui;

/**
 * Interface representing ImGuiTableSortSpecs of Dear ImGui.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
public interface KImGuiTableSortSpecs {

    KImGuiTableColumnSortSpecs[] getSpecs();
    int getSpecsCount();

    boolean getSpecsDirty();
    void setSpecsDirty(boolean value);

}
