package io.github.darthakiranihil.konna.core.graphics.shape;

import io.github.darthakiranihil.konna.core.graphics.render.KRenderFrontend;
import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.core.struct.KVector2i;

import java.util.Arrays;
import java.util.Objects;

public class KPolyline extends KAbstractShape {

    private final KVector2i[] points;
    private KColor color;

    public KPolyline(final KVector2i[] points, final KColor color) {
        this.points = points;
        this.color = color;
    }

    public KPolyline(final int[] xPoints, final int[] yPoints, final KColor color) {
        this(KStructUtils.furlArraysToVectors(xPoints, yPoints), color);
    }

    public KPolyline(final int[] xPoints, final int[] yPoints) {
        this(KStructUtils.furlArraysToVectors(xPoints, yPoints), KColor.TRANSPARENT);
    }

    public KPolyline(final KVector2i[] points) {
        this(points, KColor.TRANSPARENT);
    }

    @Override
    public void render(KRenderFrontend rf) {
        rf.render(this);
    }

    public KVector2i[] points() {
        return this.points;
    }

    public KColor getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KPolyline kPolyline = (KPolyline) o;
        return Objects.deepEquals(this.points, kPolyline.points) && Objects.equals(this.color, kPolyline.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(this.points), this.color);
    }
}
