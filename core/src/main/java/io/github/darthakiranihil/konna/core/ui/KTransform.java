package io.github.darthakiranihil.konna.core.ui;

import io.github.darthakiranihil.konna.core.struct.KDoubleVector2d;

public record KTransform(
    KDoubleVector2d translate,
    double rotation,
    boolean isRotationPivoted
) {
}
