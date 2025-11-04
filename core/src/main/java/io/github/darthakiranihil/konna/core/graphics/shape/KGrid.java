package io.github.darthakiranihil.konna.core.graphics.shape;

public record KGrid(
    int width,
    int height
) {

    public static final KGrid SQUARE_16 = new KGrid(16, 16);

}
