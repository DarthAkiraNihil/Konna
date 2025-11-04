package io.github.darthakiranihil.konna.core.graphics.shape;

import io.github.darthakiranihil.konna.core.struct.KIntVector2d;

public record KLine(
    int x1,
    int x2,
    int y1,
    int y2,
    KColor color
) {

    public KLine(int x1, int x2, int y1, int y2) {
        this(x1, x2, y1, y2, null);
    }

    public KLine(final KIntVector2d start, final KIntVector2d end) {
        this(start, end, null);
    }

    public KLine(final KIntVector2d start, final KIntVector2d end, final KColor color) {
        this(start.x(), end.x(), start.y(), end.y(), color);
    }

    public KIntVector2d start() {
        return new KIntVector2d(this.x1, this.y1);
    }

    public KIntVector2d end() {
        return new KIntVector2d(this.x2, this.y2);
    }

}
