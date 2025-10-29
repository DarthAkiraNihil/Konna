package io.github.darthakiranihil.konna.core.ui;

public record KGrid(
    int width,
    int height
) {

    public static final KGrid SQUARE_16 = new KGrid(16, 16);

}
