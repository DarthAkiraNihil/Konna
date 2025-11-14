package io.github.darthakiranihil.konna.core.graphics.shape;

import java.awt.*;

public class KColor {

    private static final int MAX_CHANNEL_VALUE = 255;

    private final int r;
    private final int g;
    private final int b;
    private final int alpha;
    private final float[] normalized;

    public KColor(int r, int g, int b, int alpha) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.alpha = alpha;

        this.normalized = new float[4];
        this.normalized[0] = ((float) this.r) / MAX_CHANNEL_VALUE;
        this.normalized[1] = ((float) this.g) / MAX_CHANNEL_VALUE;
        this.normalized[2] = ((float) this.b) / MAX_CHANNEL_VALUE;
        this.normalized[3] = ((float) this.alpha) / MAX_CHANNEL_VALUE;

    }

    public int r() {
        return this.r;
    }

    public int g() {
        return this.g;
    }

    public int b() {
        return this.b;
    }

    public int alpha() {
        return this.alpha;
    }

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

    public float[] normalized() {
        return this.normalized;
    }

}
