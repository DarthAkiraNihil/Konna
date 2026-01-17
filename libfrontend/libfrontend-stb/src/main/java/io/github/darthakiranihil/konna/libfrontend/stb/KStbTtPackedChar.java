package io.github.darthakiranihil.konna.libfrontend.stb;

/**
 * Interface representing STBTTPackedChar of STBTrueType.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public interface KStbTtPackedChar {

    short getX0();
    short getY0();
    short getX1();
    short getY1();
    float getXoff();
    float getYoff();
    float getXadvance();
    float getXoff2();
    float getYoff2();

    void setX0(short value);
    void setY0(short value);
    void setX1(short value);
    void setY1(short value);
    void setXoff(float value);
    void setYoff(float value);
    void setXadvance(float value);
    void setXoff2(float value);
    void setYoff2(float value);

}
