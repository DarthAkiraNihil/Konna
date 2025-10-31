package io.github.darthakiranihil.konna.core.graphics;

import io.github.darthakiranihil.konna.core.struct.KIntVector2d;

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

    public KPolyline(final KIntVector2d[] points) {
        this(points, null);
    }

    public KPolyline(final KIntVector2d[] points, final KColor color) {
        this(
            Arrays.stream(points).mapToInt(KIntVector2d::x).toArray(),
            Arrays.stream(points).mapToInt(KIntVector2d::y).toArray(),
            points.length,
            color
        );
    }

}
