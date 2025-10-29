package io.github.darthakiranihil.konna.core.struct;

public record KSize(
    int width,
    int height
) {
    public static KSize squared(int side) {
        return new KSize(side, side);
    }
}
