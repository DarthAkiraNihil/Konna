package io.github.darthakiranihil.konna.core.imgui;

public interface KImGuiTableSortSpecs {

    KImGuiTableColumnSortSpecs[] getSpecs();
    int getSpecsCount();

    boolean getSpecsDirty();
    void setSpecsDirty(final boolean value);

}
