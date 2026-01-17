package io.github.darthakiranihil.konna.libfrontend.stb;

/**
 * Interface representing STBTTAlignedQuad of STBTrueType.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public interface KStbTtAlignedQuad {

    float x0();
    float y0();
    float s0();
    float t0();
    float x1();
    float y1();
    float s1();
    float t1();

    void set(
        float x0,
        float y0,
        float s0,
        float t0,
        float x1,
        float y1,
        float s1,
        float t1
    );

}
