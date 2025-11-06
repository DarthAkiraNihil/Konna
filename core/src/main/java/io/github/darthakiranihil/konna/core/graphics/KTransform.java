package io.github.darthakiranihil.konna.core.graphics;

import io.github.darthakiranihil.konna.core.struct.KVector2d;

public record KTransform(
    KVector2d translation,
    double rotation,
    boolean isRotationPivoted,
    KVector2d rotationPivot,
    KVector2d scaling
) {

    public KTransform(
        final KVector2d translation,
        double rotation,
        final KVector2d scaling
    ) {
        this(translation, rotation, false, null, scaling);
    }

}
