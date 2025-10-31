package io.github.darthakiranihil.konna.core.ui;

import io.github.darthakiranihil.konna.core.struct.KVector2d;

import java.util.Arrays;

public record KPolyline(
    int[] xPoints,
    int[] yPoints,
    int pointsCount,
    KColor color
) {

    public KPolyline(final int[] xPoints, final int[] yPoints, int edgesCount) {
        this(xPoints, yPoints, edgesCount, null);
    }

    public KPolyline(final KVector2d[] points) {
        this(points, null);
    }

    public KPolyline(final KVector2d[] points, final KColor color) {
        this(
            Arrays.stream(points).mapToInt(KVector2d::x).toArray(),
            Arrays.stream(points).mapToInt(KVector2d::y).toArray(),
            points.length,
            color
        );
    }

}
