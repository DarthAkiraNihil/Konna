package io.github.darthakiranihil.konna.core.struct;

public record KDoubleVector2d(
    double x,
    double y
) {

    public static final KDoubleVector2d ZERO = new KDoubleVector2d(0.0, 0.0);

}
