package io.github.darthakiranihil.konna.core.graphics.shape;

import io.github.darthakiranihil.konna.core.struct.KVector2i;

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

    public KPolyline(final KVector2i[] points) {
        this(points, null);
    }

    public KPolyline(final KVector2i[] points, final KColor color) {
        this(
            Arrays.stream(points).mapToInt(KVector2i::x).toArray(),
            Arrays.stream(points).mapToInt(KVector2i::y).toArray(),
            points.length,
            color
        );
    }

}
