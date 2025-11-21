package io.github.darthakiranihil.konna.core.graphics.shape;

import java.awt.*;
import java.util.Objects;

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

    public KColor(int r, int g, int b) {
        this(r, g, b, 255);
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

    public static final KColor TRANSPARENT = new KColor(0, 0, 0, 0);

    public static final KColor WHITE = new KColor(255, 255, 255);
    public static final KColor LIGHT_GRAY = new KColor(192, 192, 192);
    public static final KColor GRAY = new KColor(128, 128, 128);
    public static final KColor DARK_GRAY = new KColor(64, 64, 64);
    public static final KColor BLACK = new KColor(0, 0, 0);

    public static final KColor RED = new KColor(255, 0, 0);
    public static final KColor PINK = new KColor(255, 175, 175);
    public static final KColor ORANGE = new KColor(255, 200, 0);
    public static final KColor YELLOW = new KColor(255, 255, 0);
    public static final KColor GREEN = new KColor(0, 255, 0);
    public static final KColor MAGENTA = new KColor(255, 0, 255);
    public static final KColor CYAN = new KColor(0, 255, 255);
    public static final KColor BLUE = new KColor(0, 0, 255);

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KColor kColor = (KColor) o;
        return r == kColor.r && g == kColor.g && b == kColor.b && alpha == kColor.alpha;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, g, b, alpha);
    }
}
