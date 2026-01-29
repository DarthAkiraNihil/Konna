package io.github.darthakiranihil.konna.libfrontend.imgui.flag;


/**
 * A sorting direction.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public final class KImGuiSortDirection {
    private KImGuiSortDirection() {
    }

    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;

    /**
     * Ascending = 0{@code ->}9, A{@code ->}Z etc.
     *
     * <p>Definition: {@code 1}
     */
    public static final int Ascending = 1;

    /**
     * Descending = 9{@code ->}0, Z{@code ->}A etc.
     *
     * <p>Definition: {@code 2}
     */
    public static final int Descending = 2;
}
