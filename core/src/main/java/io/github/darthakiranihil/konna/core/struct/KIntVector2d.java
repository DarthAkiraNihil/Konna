package io.github.darthakiranihil.konna.core.struct;

public record KIntVector2d(
    int x,
    int y
) {

    public static final KIntVector2d ZERO = new KIntVector2d(0, 0);

}
