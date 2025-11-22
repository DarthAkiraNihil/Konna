package io.github.darthakiranihil.konna.core.graphics.shape;

import io.github.darthakiranihil.konna.core.graphics.render.KRenderFrontend;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;

public class KOval extends KAbstractShape {

    private final KVector2i center;
    private final KSize size;
    private KColor outlineColor;
    private KColor fillColor;

    public KOval(final KVector2i center, final KSize size, final KColor outlineColor, final KColor fillColor) {
        this.center = center;
        this.size = size;
        this.outlineColor = outlineColor;
        this.fillColor = fillColor;
    }

    public KOval(final KVector2i center, final KSize size) {
        this(center, size, KColor.TRANSPARENT, KColor.TRANSPARENT);
    }

    public KOval(final KVector2i center, final KSize size, final KColor outlineColor) {
        this(center, size, outlineColor, KColor.TRANSPARENT);
    }

    public KOval(int x, int y, int width, int height) {
        this(x, y, width, height, KColor.TRANSPARENT, KColor.TRANSPARENT);
    }

    public KOval(int x, int y, int width, int height, final KColor outlineColor) {
        this(x, y, width, height, outlineColor, KColor.TRANSPARENT);
    }

    public KOval(int x, int y, int width, int height, final KColor outlineColor, final KColor fillColor) {
        this(new KVector2i(x, y), new KSize(width, height), outlineColor, fillColor);
    }

    public KVector2i center() {
        return this.center;
    }

    public KSize size() {
        return this.size;
    }

    public KColor getOutlineColor() {
        return outlineColor;
    }

    public KColor getFillColor() {
        return fillColor;
    }

    @Override
    public void render(KRenderFrontend rf) {
        rf.render(this);
    }
}
