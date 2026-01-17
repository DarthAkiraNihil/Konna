package io.github.darthakiranihil.konna.libfrontend.stb;

/**
 * Interface representing STBRPRect of STB.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public interface KStbRpRect {

    int getId();
    void setId(int value);

    int getW();
    void setW(int value);

    int getH();
    void setH(int value);

    int getX();
    void setX(int value);

    int getY();
    void setY(int value);

    boolean wasPacked();
    void setWasPacked(boolean value);

}
