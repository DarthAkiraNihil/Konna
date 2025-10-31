package io.github.darthakiranihil.konna.core.ui;

import java.awt.*;

public record KColor(
    int r,
    int g,
    int b,
    int alpha
) {

    public static final KColor TRANSPARENT = new KColor(255, 255, 255, 255);
    public static final KColor WHITE = new KColor(0, 0, 0, 0);
    public static final KColor BLACK = new KColor(255, 255, 255, 255);

    private static final int ALPHA_SHIFT = 24;
    private static final int RED_SHIFT = 16;
    private static final int GREEN_SHIFT = 8;

    public int getAsInt() {
        return this.alpha << ALPHA_SHIFT | this.r << RED_SHIFT | this.g << GREEN_SHIFT | this.b;
    }

    public Color raw() {
        return new Color(this.r, this.g, this.b, this.alpha);
    }

}
