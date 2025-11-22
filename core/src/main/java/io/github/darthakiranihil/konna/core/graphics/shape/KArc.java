package io.github.darthakiranihil.konna.core.graphics.shape;

import io.github.darthakiranihil.konna.core.graphics.render.KRenderFrontend;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;

import java.util.Objects;

public class KArc extends KAbstractShape {

    private final KVector2i center;
    private final KSize size;
    private final int startAngle;
    private final int arcAngle;
    private KColor outlineColor;
    private KColor fillColor;

    public KArc(final KVector2i center, final KSize size, int startAngle, int arcAngle, final KColor outlineColor, final KColor fillColor) {
        this.center = center;
        this.size = size;
        this.startAngle = startAngle;
        this.arcAngle = arcAngle;
        this.outlineColor = outlineColor;
        this.fillColor = fillColor;
    }

    public KArc(final KVector2i coordinates, final KSize size, int startAngle, int arcAngle) {
        this(coordinates, size, startAngle, arcAngle, KColor.TRANSPARENT, KColor.TRANSPARENT);
    }

    public KArc(final KVector2i coordinates, final KSize size, int startAngle, int arcAngle, final KColor outlineColor) {
        this(coordinates, size, startAngle, arcAngle, outlineColor, KColor.TRANSPARENT);
    }

    public KArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        this(new KVector2i(x, y), new KSize(width, height), startAngle, arcAngle, KColor.TRANSPARENT, KColor.TRANSPARENT);
    }

    public KArc(int x, int y, int width, int height, int startAngle, int arcAngle, final KColor outlineColor) {
        this(new KVector2i(x, y), new KSize(width, height), startAngle, arcAngle, outlineColor, KColor.TRANSPARENT);
    }

    public KArc(int x, int y, int width, int height, int startAngle, int arcAngle, final KColor outlineColor, final KColor fillColor) {
        this(new KVector2i(x, y), new KSize(width, height), startAngle, arcAngle, outlineColor, fillColor);
    }

    @Override
    public void render(KRenderFrontend rf) {
        rf.render(this);
    }

    public KVector2i center() {
        return this.center;
    }

    public KSize size() {
        return this.size;
    }

    public int startAngle() {
        return this.startAngle;
    }

    public int arcAngle() {
        return this.arcAngle;
    }

    public KColor getOutlineColor() {
        return outlineColor;
    }

    public KColor getFillColor() {
        return fillColor;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KArc kArc = (KArc) o;
        return
                this.startAngle == kArc.startAngle
            &&  this.arcAngle == kArc.arcAngle
            &&  Objects.equals(this.center, kArc.center)
            &&  Objects.equals(this.size, kArc.size)
            &&  Objects.equals(this.outlineColor, kArc.outlineColor)
            &&  Objects.equals(this.fillColor, kArc.fillColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            this.center,
            this.size,
            this.startAngle,
            this.arcAngle,
            this.outlineColor,
            this.fillColor
        );
    }
}
