package io.github.darthakiranihil.konna.core.graphics.shape;

import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;

public record KOval(
    int x,
    int y,
    int width,
    int height,
    KColor outlineColor,
    KColor fillColor
) {

    public KOval(final KVector2i coordinates, final KSize size) {
        this(coordinates, size, null, null);
    }

    public KOval(final KVector2i coordinates, final KSize size, final KColor outlineColor) {
        this(coordinates, size, outlineColor, null);
    }

    public KOval(final KVector2i coordinates, final KSize size, final KColor outlineColor, final KColor fillColor) {
        this(coordinates.x(), coordinates.y(), size.width(), size.height(), outlineColor, fillColor);
    }

    public KOval(int x, int y, int width, int height) {
        this(x, y, width, height, null, null);
    }

    public KOval(int x, int y, int width, int height, final KColor outlineColor) {
        this(x, y, width, height, outlineColor, null);
    }

}
