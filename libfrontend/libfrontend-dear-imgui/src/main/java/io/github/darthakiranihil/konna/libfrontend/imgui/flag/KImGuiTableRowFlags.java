package io.github.darthakiranihil.konna.libfrontend.imgui.flag;


/**
 * Flags for {@link io.github.darthakiranihil.konna.libfrontend.imgui.KImGui#tableNextRow(int)}.
 *
 * @author Darth Akira Nihil
 * @since 0.3.0
 */
public final class KImGuiTableRowFlags {
    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;
    /**
     * Identify header row (set default background color + width of its contents accounted
     * differently for auto column width)
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int Headers = 1;

    private KImGuiTableRowFlags() {
    }
}
