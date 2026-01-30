/*
 * Copyright 2025-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.darthakiranihil.konna.graphics;

import java.util.Objects;

/**
 * Representation of a color with RGBA model.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
public final class KColor {

    private static final int MAX_CHANNEL_VALUE = 255;

    private final int r;
    private final int g;
    private final int b;
    private final int alpha;
    private final float[] normalized;

    /**
     * Standard constructor.
     * @param r Red channel of the color
     * @param g Green channel of the color
     * @param b Blue channel of the color
     * @param alpha Alpha channel of the color
     */
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

    /**
     * Standard constructor that creates a color with alpha channel set to 255.
     * @param r Red channel of the color
     * @param g Green channel of the color
     * @param b Blue channel of the color
     */
    public KColor(int r, int g, int b) {
        this(r, g, b, 255);
    }

    /**
     * Returns red channel value of the color.
     * @return Red channel of the color
     */
    public int r() {
        return this.r;
    }

    /**
     * Returns green channel value of the color.
     * @return Green channel of the color
     */
    public int g() {
        return this.g;
    }

    /**
     * Returns blue channel value of the color.
     * @return Blue channel of the color
     */
    public int b() {
        return this.b;
    }

    /**
     * Returns alpha channel value of the color.
     * @return Alpha channel of the color
     */
    public int alpha() {
        return this.alpha;
    }

    /**
     * Constant for the transparent color.
     */
    public static final KColor TRANSPARENT = new KColor(0, 0, 0, 0);

    /**
     * The color white.
     */
    public static final KColor WHITE = new KColor(255, 255, 255);
    /**
     * The color light gray.
     */
    public static final KColor LIGHT_GRAY = new KColor(192, 192, 192);
    /**
     * The color gray.
     */
    public static final KColor GRAY = new KColor(128, 128, 128);
    /**
     * The color dark gray.
     */
    public static final KColor DARK_GRAY = new KColor(64, 64, 64);
    /**
     * The color black.
     */
    public static final KColor BLACK = new KColor(0, 0, 0);

    /**
     * The color red.
     */
    public static final KColor RED = new KColor(255, 0, 0);
    /**
     * The color pink.
     */
    public static final KColor PINK = new KColor(255, 175, 175);
    /**
     * The color orange.
     */
    public static final KColor ORANGE = new KColor(255, 200, 0);
    /**
     * The color yellow.
     */
    public static final KColor YELLOW = new KColor(255, 255, 0);
    /**
     * The color green.
     */
    public static final KColor GREEN = new KColor(0, 255, 0);
    /**
     * The color magenta.
     */
    public static final KColor MAGENTA = new KColor(255, 0, 255);
    /**
     * The color cyan.
     */
    public static final KColor CYAN = new KColor(0, 255, 255);
    /**
     * The color blue.
     */
    public static final KColor BLUE = new KColor(0, 0, 255);

    private static final int ALPHA_SHIFT = 24;
    private static final int RED_SHIFT = 16;
    private static final int GREEN_SHIFT = 8;

    /**
     * Returns this color as int with (alpha|red|green|blue) mapping.
     * @return This color as int
     */
    public int getAsInt() {
        return this.alpha << ALPHA_SHIFT | this.r << RED_SHIFT | this.g << GREEN_SHIFT | this.b;
    }

    /**
     * Returns this color as array of floats with (0.0, 1.0) range.
     * @return Normalized representation of the color
     */
    public float[] normalized() {
        return this.normalized;
    }

    @Override
    public boolean equals(final Object o) {
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
