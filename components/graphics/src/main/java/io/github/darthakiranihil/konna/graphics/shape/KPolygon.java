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

import io.github.darthakiranihil.konna.core.struct.KStructUtils;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.graphics.KColor;
import io.github.darthakiranihil.konna.graphics.render.KRenderFrontend;
import io.github.darthakiranihil.konna.graphics.shader.KShaderProgram;

import java.util.Arrays;
import java.util.Objects;

/**
 * Representation of a polygon.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public class KPolygon extends KAbstractShape {

    private final KVector2i[] points;
    private KColor outlineColor;
    private KColor fillColor;

    /**
     * Standard constructor.
     * @param points Array of polygon's points (vertices)
     * @param outlineColor Polygon's outline color
     * @param fillColor Polygon's fill color
     */
    public KPolygon(
        final KVector2i[] points,
        final KColor outlineColor,
        final KColor fillColor
    ) {
        super(KAbstractShape.centroidOfPoints(points));
        this.points = points;
        this.outlineColor = outlineColor;
        this.fillColor = fillColor;
    }

    /**
     * Creates a polygon with transparent outline and fill colors.
     * @param points Array of polygon's points (vertices)
     */
    public KPolygon(final KVector2i[] points) {
        this(points, KColor.TRANSPARENT, KColor.TRANSPARENT);
    }

    /**
     * Creates a polygon with transparent fill color.
     * @param points Array of polygon's points (vertices)
     * @param outlineColor Polygon's outline color
     */
    public KPolygon(final KVector2i[] points, final KColor outlineColor) {
        this(points, outlineColor, KColor.TRANSPARENT);
    };

    /**
     * Standard constructor, but X and Y coordinates of polygon's points
     * are passed in different arrays.
     * Also creates a polygon with transparent outline and fill colors.
     * @param xPoints Array of X coordinates of polygon's points (vertices)
     * @param yPoints Array of Y coordinates of polygon's points (vertices)
     */
    public KPolygon(final int[] xPoints, final int[] yPoints) {
        this(xPoints, yPoints, KColor.TRANSPARENT, KColor.TRANSPARENT);
    }

    /**
     * Standard constructor, but X and Y coordinates of polygon's points
     * are passed in different arrays. Also creates a polygon with transparent fill color.
     * @param xPoints Array of X coordinates of polygon's points (vertices)
     * @param yPoints Array of Y coordinates of polygon's points (vertices)
     * @param outlineColor Polygon's outline color
     */
    public KPolygon(final int[] xPoints, final int[] yPoints, final KColor outlineColor) {
        this(xPoints, yPoints, outlineColor, KColor.TRANSPARENT);
    }

    /**
     * Standard constructor, but X and Y coordinates of polygon's points
     * are passed in different arrays.
     * @param xPoints Array of X coordinates of polygon's points (vertices)
     * @param yPoints Array of Y coordinates of polygon's points (vertices)
     * @param outlineColor Polygon's outline color
     * @param fillColor Polygon's fill color
     */
    public KPolygon(
        final int[] xPoints,
        final int[] yPoints,
        final KColor outlineColor,
        final KColor fillColor
    ) {
        this(KStructUtils.furlArraysToVectors(xPoints, yPoints), outlineColor, fillColor);
    }

    /**
     * Standard constructor but used shader is not default.
     * @param points Array of polygon's points (vertices)
     * @param shader Specific shader used for its rendering
     * @param outlineColor Polygon's outline color
     * @param fillColor Polygon's fill color
     */
    public KPolygon(
        final KVector2i[] points,
        final KShaderProgram shader,
        final KColor outlineColor,
        final KColor fillColor
    ) {
        super(KAbstractShape.centroidOfPoints(points), shader);
        this.points = points;
        this.outlineColor = outlineColor;
        this.fillColor = fillColor;
    }

    /**
     * Creates a polygon with transparent outline and fill colors and used shader is not default.
     * @param points Array of polygon's points (vertices)
     * @param shader Specific shader used for its rendering
     */
    public KPolygon(final KVector2i[] points, final KShaderProgram shader) {
        this(points, shader, KColor.TRANSPARENT, KColor.TRANSPARENT);
    }

    /**
     * Creates a polygon with transparent fill color and used shader is not default.
     * @param points Array of polygon's points (vertices)
     * @param shader Specific shader used for its rendering
     * @param outlineColor Polygon's outline color
     */
    public KPolygon(
        final KVector2i[] points,
        final KShaderProgram shader,
        final KColor outlineColor
    ) {
        this(points, shader, outlineColor, KColor.TRANSPARENT);
    };

    /**
     * Standard constructor, but X and Y coordinates of polygon's points
     * are passed in different arrays and used shader is not default.
     * Also creates a polygon with transparent outline and fill colors.
     * @param xPoints Array of X coordinates of polygon's points (vertices)
     * @param yPoints Array of Y coordinates of polygon's points (vertices)
     *  @param shader Specific shader used for its rendering
     */
    public KPolygon(
        final int[] xPoints,
        final int[] yPoints,
        final KShaderProgram shader
    ) {
        this(xPoints, yPoints, shader, KColor.TRANSPARENT, KColor.TRANSPARENT);
    }

    /**
     * Standard constructor, but X and Y coordinates of polygon's points
     * are passed in different arrays. Also creates a polygon with transparent fill color
     * and used shader is not default.
     * @param xPoints Array of X coordinates of polygon's points (vertices)
     * @param yPoints Array of Y coordinates of polygon's points (vertices)
     * @param outlineColor Polygon's outline color
     */
    public KPolygon(
        final int[] xPoints,
        final int[] yPoints,
        final KShaderProgram shader,
        final KColor outlineColor
    ) {
        this(xPoints, yPoints, shader, outlineColor, KColor.TRANSPARENT);
    }

    /**
     * Standard constructor, but X and Y coordinates of polygon's points
     * are passed in different arrays and used shader is not default.
     * @param xPoints Array of X coordinates of polygon's points (vertices)
     * @param yPoints Array of Y coordinates of polygon's points (vertices)
     * @param outlineColor Polygon's outline color
     * @param fillColor Polygon's fill color
     */
    public KPolygon(
        final int[] xPoints,
        final int[] yPoints,
        final KShaderProgram shader,
        final KColor outlineColor,
        final KColor fillColor
    ) {
        this(KStructUtils.furlArraysToVectors(xPoints, yPoints), shader, outlineColor, fillColor);
    }

    /**
     * Returns points of the polygon.
     * @return Points (vertices) of this polygon
     */
    public KVector2i[] points() {
        return this.points;
    }

    /**
     * Returns outline color of the polygon.
     * @return Outline color of this polygon
     */
    public KColor getOutlineColor() {
        return this.outlineColor;
    }

    /**
     * Returns fill color of the polygon.
     * @return Fill color of this polygon
     */
    public KColor getFillColor() {
        return this.fillColor;
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
        KPolygon kPolygon = (KPolygon) o;
        return
            Objects.deepEquals(this.points, kPolygon.points)
                &&  Objects.equals(this.outlineColor, kPolygon.outlineColor)
                &&  Objects.equals(this.fillColor, kPolygon.fillColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(this.points), this.outlineColor, this.fillColor);
    }

}
