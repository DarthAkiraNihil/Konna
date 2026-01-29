package io.github.darthakiranihil.konna.libfrontend.imgui.flag;


/**
 * Flags for ImFontAtlas build.
 *
 * @author Darth Akira Nihil
 * @since 0.1.0
 */
public final class KImFontAtlasFlags {
    /**
     * Definition: {@code 0}
     */
    public static final int None = 0;
    /**
     * Don't round the height to next power of two
     *
     * <p>Definition: {@code 1 << 0}
     */
    public static final int NoPowerOfTwoHeight = 1;
    /**
     * Don't build software mouse cursors into the atlas (save a little texture memory)
     *
     * <p>Definition: {@code 1 << 1}
     */
    public static final int NoMouseCursors = 2;
    /**
     * Don't build thick line textures into the atlas (save a little texture memory, allow support
     * for point/nearest filtering). The AntiAliasedLinesUseTex features uses them, otherwise they
     * will be rendered using polygons (more expensive for CPU/GPU).
     *
     * <p>Definition: {@code 1 << 2}
     */
    public static final int NoBakedLines = 4;

    private KImFontAtlasFlags() {
    }
}
