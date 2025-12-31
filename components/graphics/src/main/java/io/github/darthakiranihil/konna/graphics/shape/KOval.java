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

package io.github.darthakiranihil.konna.graphics.shape;

import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.graphics.KColor;
import io.github.darthakiranihil.konna.graphics.render.KRenderFrontend;

import java.util.Objects;

/**
 * Representation of an oval.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public class KOval extends KAbstractShape {

    private final KVector2i center;
    private final KSize size;
    private KColor outlineColor;
    private KColor fillColor;

    /**
     * Standard constructor.
     * @param center Coordinates of oval's center
     * @param size Size of the oval
     * @param outlineColor Oval's outline color
     * @param fillColor Oval's fill color
     */
    public KOval(
        final KVector2i center,
        final KSize size,
        final KColor outlineColor,
        final KColor fillColor
    ) {
        this.center = center;
        this.size = size;
        this.outlineColor = outlineColor;
        this.fillColor = fillColor;
    }

    /**
     * Creates an oval with transparent outline and fill colors.
     * @param center Coordinates of oval's center
     * @param size Size of the oval
     */
    public KOval(final KVector2i center, final KSize size) {
        this(center, size, KColor.TRANSPARENT, KColor.TRANSPARENT);
    }

    /**
     * Creates an oval with transparent fill color.
     * @param center Coordinates of oval's center
     * @param size Size of the oval
     * @param outlineColor Oval's outline color
     */
    public KOval(final KVector2i center, final KSize size, final KColor outlineColor) {
        this(center, size, outlineColor, KColor.TRANSPARENT);
    }

    /**
     * Standard constructor, but center coordinates and size are defined by separated
     * int parameters. Also creates an oval with transparent outline and fill colors.
     * @param x X coordinate of oval's center
     * @param y Y coordinate of oval's center
     * @param width Oval's width
     * @param height Oval's height
     */
    public KOval(int x, int y, int width, int height) {
        this(x, y, width, height, KColor.TRANSPARENT, KColor.TRANSPARENT);
    }

    /**
     * Standard constructor, but center coordinates and size are defined by separated
     * int parameters. Also creates an oval with transparent fill color.
     * @param x X coordinate of oval's center
     * @param y Y coordinate of oval's center
     * @param width Oval's width
     * @param height Oval's height
     * @param outlineColor Oval's outline color
     */
    public KOval(int x, int y, int width, int height, final KColor outlineColor) {
        this(x, y, width, height, outlineColor, KColor.TRANSPARENT);
    }

    /**
     * Standard constructor, but center coordinates and size are defined by separated
     * int parameters.
     * @param x X coordinate of oval's center
     * @param y Y coordinate of oval's center
     * @param width Oval's width
     * @param height Oval's height
     * @param outlineColor Oval's outline color
     * @param fillColor Oval's fill color
     */
    public KOval(
        int x,
        int y,
        int width,
        int height,
        final KColor outlineColor,
        final KColor fillColor
    ) {
        this(new KVector2i(x, y), new KSize(width, height), outlineColor, fillColor);
    }

    /**
     * Returns center coordinates of the oval.
     * @return Center coordinates of this oval
     */
    public KVector2i center() {
        return this.center;
    }

    /**
     * Returns size of the oval.
     * @return Size of this oval
     */
    public KSize size() {
        return this.size;
    }

    /**
     * Returns outline color of the oval.
     * @return Outline color of this oval
     */
    public KColor getOutlineColor() {
        return outlineColor;
    }

    /**
     * Returns fill color of the oval.
     * @return Fill color of this oval
     */
    public KColor getFillColor() {
        return fillColor;
    }

    @Override
    public void render(final KRenderFrontend rf) {
        rf.render(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KOval kOval = (KOval) o;
        return
            Objects.equals(this.center, kOval.center)
                &&  Objects.equals(this.size, kOval.size)
                &&  Objects.equals(this.outlineColor, kOval.outlineColor)
                &&  Objects.equals(this.fillColor, kOval.fillColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.center, this.size, this.outlineColor, this.fillColor);
    }

}
