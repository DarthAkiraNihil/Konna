package io.github.darthakiranihil.konna.libfrontend.imgui.flag;


/**
 * A cardinal direction.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
public final class KImGuiDir {
    private KImGuiDir() {
    }

    /**
     * Definition: {@code -1}
     */
    public static final int None = -1;

    /**
     * Definition: {@code 0}
     */
    public static final int Left = 0;

    /**
     * Definition: {@code 1}
     */
    public static final int Right = 1;

    /**
     * Definition: {@code 2}
     */
    public static final int Up = 2;

    /**
     * Definition: {@code 3}
     */
    public static final int Down = 3;

    public static final int COUNT = 4;
}
