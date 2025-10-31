package io.github.darthakiranihil.konna.core.graphics;

import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KIntVector2d;

public record KArc(
    int x,
    int y,
    int width,
    int height,
    int startAngle,
    int arcAngle,
    KColor outlineColor,
    KColor fillColor
) {

    public KArc(final KIntVector2d coordinates, final KSize size, int startAngle, int arcAngle) {
        this(coordinates, size, startAngle, arcAngle, null, null);
    }

    public KArc(final KIntVector2d coordinates, final KSize size, int startAngle, int arcAngle, final KColor outlineColor) {
        this(coordinates, size, startAngle, arcAngle, outlineColor, null);
    }

    public KArc(final KIntVector2d coordinates, final KSize size, int startAngle, int arcAngle, final KColor outlineColor, final KColor fillColor) {
        this(coordinates.x(), coordinates.y(), size.width(), size.height(), startAngle, arcAngle, outlineColor, fillColor);
    }

    public KArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        this(x, y, width, height, startAngle, arcAngle, null, null);
    }

    public KArc(int x, int y, int width, int height, int startAngle, int arcAngle, final KColor outlineColor) {
        this(x, y, width, height, startAngle, arcAngle, outlineColor, null);
    }

}
