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

/**
 * Representation of a rectangle, a polygon of 4 points and right angles.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public class KRectangle extends KPolygon {

    private final int width;
    private final int height;

    /**
     * Standard constructor.
     * @param coordinates Coordinates of rectangle's top left corner
     * @param size Size of the rectangle
     * @param outlineColor Rectangle's outline color
     * @param fillColor Rectangle's fill color
     */
    public KRectangle(final KVector2i coordinates, final KSize size, final KColor outlineColor, final KColor fillColor) {
        this(coordinates.x(), coordinates.y(), size.width(), size.height(), outlineColor, fillColor);
    }

    /**
     * Creates a rectangle with transparent outline and fill colors.
     * @param coordinates Coordinates of rectangle's top left corner
     * @param size Size of the rectangle
     */
    public KRectangle(final KVector2i coordinates, final KSize size) {
        this(coordinates, size, KColor.TRANSPARENT, KColor.TRANSPARENT);
    }

    /**
     * Creates a rectangle with transparent fill color.
     * @param coordinates Coordinates of rectangle's top left corner
     * @param size Size of the rectangle
     * @param outlineColor Rectangle's outline color
     */
    public KRectangle(final KVector2i coordinates, final KSize size, final KColor outlineColor) {
        this(coordinates, size, outlineColor, KColor.TRANSPARENT);
    }

    /**
     * Standard constructor, but top left corner coordinates and size are passed
     * in separated int parameters.
     * @param x X coordinate of rectangle's top left corner
     * @param y Y coordinate of rectangle's top left corner
     * @param width Width of the rectangle
     * @param height Height of the rectangle
     * @param outlineColor Rectangle's outline color
     * @param fillColor Rectangle's fill color
     */
    public KRectangle(int x, int y, int width, int height, final KColor outlineColor, final KColor fillColor) {
        super(
            new KVector2i[] {
                new KVector2i(x, y),
                new KVector2i(x + width, y),
                new KVector2i(x + width, y + height),
                new KVector2i(x, y + height),
            },
            outlineColor,
            fillColor
        );
        this.width = width;
        this.height = height;
    }

    /**
     * Standard constructor, but top left corner coordinates and size are passed
     * in separated int parameters.
     * Also creates a rectangle with transparent outline and fill colors.
     * @param x X coordinate of rectangle's top left corner
     * @param y Y coordinate of rectangle's top left corner
     * @param width Width of the rectangle
     * @param height Height of the rectangle
     */
    public KRectangle(int x, int y, int width, int height) {
        this(x, y, width, height, KColor.TRANSPARENT, KColor.TRANSPARENT);
    }

    /**
     * Standard constructor, but top left corner coordinates and size are passed
     * in separated int parameters. Also creates a rectangle with transparent fill color.
     * @param x X coordinate of rectangle's top left corner
     * @param y Y coordinate of rectangle's top left corner
     * @param width Width of the rectangle
     * @param height Height of the rectangle
     * @param outlineColor Rectangle's outline color
     */
    public KRectangle(int x, int y, int width, int height, final KColor outlineColor) {
        this(x, y, width, height, outlineColor, KColor.TRANSPARENT);
    }

    /**
     * Creates a square with transparent outline and fill colors.
     * @param coordinates Coordinates of square's top left corner
     * @param side Side of the square
     * @return Rectangle with square dimensions
     */
    public static KRectangle square(final KVector2i coordinates, int side) {
        return new KRectangle(coordinates, KSize.squared(side));
    }

    /**
     * Creates a square with transparent fill color.
     * @param coordinates Coordinates of square's top left corner
     * @param side Side of the square
     * @param outlineColor Square's outline color
     * @return Rectangle with square dimensions
     */
    public static KRectangle square(final KVector2i coordinates, int side, final KColor outlineColor) {
        return new KRectangle(coordinates, KSize.squared(side), outlineColor);
    }

    /**
     * Creates a square.
     * @param coordinates Coordinates of square's top left corner
     * @param side Side of the square
     * @param outlineColor Square's outline color
     * @param fillColor Square's fill color
     * @return Rectangle with square dimensions
     */
    public static KRectangle square(final KVector2i coordinates, int side, final KColor outlineColor, final KColor fillColor) {
        return new KRectangle(coordinates, KSize.squared(side), outlineColor, fillColor);
    }

    /**
     * Creates a square with transparent outline and fill colors.
     * Coordinates of square's top left corner are passed in separated int parameters.
     * @param x X coordinate of square's top left corner
     * @param y Y coordinate of square's top left corner
     * @param side Side of the square
     * @return Rectangle with square dimensions
     */
    public static KRectangle square(int x, int y, int side) {
        return new KRectangle(x, y, side, side, KColor.TRANSPARENT, KColor.TRANSPARENT);
    }

    /**
     * Creates a square with transparent fill color.
     * Coordinates of square's top left corner are passed in separated int parameters.
     * @param x X coordinate of square's top left corner
     * @param y Y coordinate of square's top left corner
     * @param side Side of the square
     * @param outlineColor Square's outline color
     * @return Rectangle with square dimensions
     */
    public static KRectangle square(int x, int y, int side, final KColor outlineColor) {
        return new KRectangle(x, y, side, side, outlineColor, KColor.TRANSPARENT);
    }

    /**
     * Creates a square
     * Coordinates of square's top left corner are passed in separated int parameters.
     * @param x X coordinate of square's top left corner
     * @param y Y coordinate of square's top left corner
     * @param side Side of the square
     * @param outlineColor Square's outline color
     * @param fillColor Square's fill color
     * @return Rectangle with square dimensions
     */
    public static KRectangle square(int x, int y, int side, final KColor outlineColor, final KColor fillColor) {
        return new KRectangle(x, y, side, side, outlineColor, fillColor);
    }

    /**
     * Returns width of the rectangle.
     * @return Width of the rectangle.
     */
    public int width() {
        return this.width;
    }

    /**
     * Returns height of the rectangle.
     * @return Height of the rectangle.
     */
    public int height() {
        return this.height;
    }

    @Override
    public void render(final KRenderFrontend rf) {
        rf.render(this);
    }

}
