package io.github.darthakiranihil.konna.core.graphics.shape;

import io.github.darthakiranihil.konna.core.struct.KVector2i;

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

    public KLine(final KVector2i start, final KVector2i end) {
        this(start, end, null);
    }

    public KLine(final KVector2i start, final KVector2i end, final KColor color) {
        this(start.x(), end.x(), start.y(), end.y(), color);
    }

    public KVector2i start() {
        return new KVector2i(this.x1, this.y1);
    }

    public KVector2i end() {
        return new KVector2i(this.x2, this.y2);
    }

}
