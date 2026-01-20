package io.github.darthakiranihil.konna.libfrontend.imgui;

public interface KImGuiTableSortSpecs {

    KImGuiTableColumnSortSpecs[] getSpecs();
    int getSpecsCount();

    boolean getSpecsDirty();
    void setSpecsDirty(final boolean value);

}
