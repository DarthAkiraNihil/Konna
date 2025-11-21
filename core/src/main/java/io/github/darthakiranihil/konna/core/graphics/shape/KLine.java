package io.github.darthakiranihil.konna.core.graphics.shape;

import io.github.darthakiranihil.konna.core.graphics.render.KRenderFrontend;
import io.github.darthakiranihil.konna.core.struct.KVector2i;

public class KLine extends KAbstractShape {

    private final KVector2i start;
    private final KVector2i end;
    private KColor color;

    public KLine(final KVector2i start, final KVector2i end, final KColor color) {
        this.start = start;
        this.end = end;
        this.color = color;
    }

    public KLine(final KVector2i start, final KVector2i end) {
        this(start, end, KColor.TRANSPARENT);
    }

    public KLine(int x1, int x2, int y1, int y2, final KColor color) {
        this(new KVector2i(x1, y1), new KVector2i(x2, y2), color);
    }

    public KLine(int x1, int x2, int y1, int y2) {
        this(new KVector2i(x1, y1), new KVector2i(x2, y2), KColor.TRANSPARENT);
    }

    @Override
    public void render(KRenderFrontend rf) {
        rf.render(this);
    }

    public KColor getColor() {
        return color;
    }

    public KVector2i start() {
        return this.start;
    }

    public KVector2i end() {
        return this.end;
    }



}
