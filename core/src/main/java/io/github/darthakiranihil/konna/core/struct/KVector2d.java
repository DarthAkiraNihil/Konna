package io.github.darthakiranihil.konna.core.struct;

public record KVector2d(
    double x,
    double y
) {

    public static final KVector2d ZERO = new KVector2d(0.0, 0.0);

}
