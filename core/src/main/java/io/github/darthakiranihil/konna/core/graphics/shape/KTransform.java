package io.github.darthakiranihil.konna.core.graphics.shape;

import io.github.darthakiranihil.konna.core.struct.KDoubleVector2d;

public record KTransform(
    KDoubleVector2d translation,
    double rotation,
    boolean isRotationPivoted,
    KDoubleVector2d rotationPivot,
    KDoubleVector2d scaling
) {

    public KTransform(
        final KDoubleVector2d translation,
        double rotation,
        final KDoubleVector2d scaling
    ) {
        this(translation, rotation, false, null, scaling);
    }

}
