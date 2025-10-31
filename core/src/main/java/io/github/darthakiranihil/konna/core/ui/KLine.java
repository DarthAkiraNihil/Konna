package io.github.darthakiranihil.konna.core.ui;

import io.github.darthakiranihil.konna.core.struct.KVector2d;

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

    public KLine(final KVector2d start, final KVector2d end) {
        this(start, end, null);
    }

    public KLine(final KVector2d start, final KVector2d end, final KColor color) {
        this(start.x(), end.x(), start.y(), end.y(), color);
    }

    public KVector2d start() {
        return new KVector2d(this.x1, this.y1);
    }

    public KVector2d end() {
        return new KVector2d(this.x2, this.y2);
    }

}
