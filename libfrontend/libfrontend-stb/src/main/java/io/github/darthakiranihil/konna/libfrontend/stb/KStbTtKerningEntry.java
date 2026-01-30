package io.github.darthakiranihil.konna.libfrontend.stb;

/**
 * Interface representing STBTTKerningEntry of STBTrueType.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
public interface KStbTtKerningEntry {

    int glyph1();
    int glyph2();
    int advance();

}
