package io.github.darthakiranihil.konna.core.struct;

public record KVector2i(
    int x,
    int y
) {

    public static final KVector2i ZERO = new KVector2i(0, 0);

}
