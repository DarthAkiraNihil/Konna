package io.github.darthakiranihil.konna.core.graphics.shape;

import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;

public record KRoundedRectangle(
    int x,
    int y,
    int width,
    int height,
    int arcWidth,
    int arcHeight,
    KColor outlineColor,
    KColor fillColor
) {

    public KRoundedRectangle(final KVector2i coordinates, final KSize size, final KSize arcSize) {
        this(coordinates, size, arcSize, null, null);
    }

    public KRoundedRectangle(final KVector2i coordinates, final KSize size, final KSize arcSize, final KColor outlineColor) {
        this(coordinates, size, arcSize, outlineColor, null);
    }

    public KRoundedRectangle(final KVector2i coordinates, final KSize size, final KSize arcSize, final KColor outlineColor, final KColor fillColor) {
        this(coordinates.x(), coordinates.y(), size.width(), size.height(), arcSize.width(), arcSize.height(), outlineColor, fillColor);
    }

    public KRoundedRectangle(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        this(x, y, width, height, arcWidth, arcHeight, null, null);
    }

    public KRoundedRectangle(int x, int y, int width, int height, int arcWidth, int arcHeight, final KColor outlineColor) {
        this(x, y, width, height, arcWidth, arcHeight, outlineColor, null);
    }

    public static KRoundedRectangle square(int x, int y, int side, int arcWidth, int arcHeight) {
        return new KRoundedRectangle(x, y, side, side, arcWidth, arcHeight, null, null);
    }

    public static KRoundedRectangle square(int x, int y, int side, int arcWidth, int arcHeight, final KColor outlineColor) {
        return new KRoundedRectangle(x, y, side, side, arcWidth, arcHeight, outlineColor, null);
    }

    public static KRoundedRectangle square(int x, int y, int side, int arcWidth, int arcHeight, final KColor outlineColor, final KColor fillColor) {
        return new KRoundedRectangle(x, y, side, side, arcWidth, arcHeight, outlineColor, fillColor);
    }

    public static KRoundedRectangle square(final KVector2i coordinates, int side, final KSize arcSize) {
        return new KRoundedRectangle(coordinates, KSize.squared(side), arcSize);
    }

    public static KRoundedRectangle square(final KVector2i coordinates, int side, final KSize arcSize, final KColor outlineColor) {
        return new KRoundedRectangle(coordinates, KSize.squared(side), arcSize, outlineColor);
    }

    public static KRoundedRectangle square(final KVector2i coordinates, int side, final KSize arcSize, final KColor outlineColor, final KColor fillColor) {
        return new KRoundedRectangle(coordinates, KSize.squared(side), arcSize, outlineColor, fillColor);
    }

}
