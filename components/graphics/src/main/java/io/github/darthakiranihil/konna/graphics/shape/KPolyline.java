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
 * Representation of a polyline.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public class KPolyline extends KAbstractShape {

    private final KVector2i[] points;
    private KColor color;

    /**
     * Standard constructor.
     * @param points Array of polyline's points (vertices)
     * @param color Polyline's color
     */
    public KPolyline(final KVector2i[] points, final KColor color) {
        super(KAbstractShape.centroidOfPoints(points));
        this.points = points;
        this.color = color;
    }

    /**
     * Creates a polyline with transparent color.
     * @param points Array of polyline's points (vertices)
     */
    public KPolyline(final KVector2i[] points) {
        this(points, KColor.TRANSPARENT);
    }

    /**
     * Standard constructor, but X and Y coordinates of polyline's points
     * are passed in different arrays.
     * @param xPoints Array X coordinates of polyline's points (vertices)
     * @param yPoints Array Y coordinates of polyline's points (vertices)
     * @param color Polyline's color
     */
    public KPolyline(final int[] xPoints, final int[] yPoints, final KColor color) {
        this(KStructUtils.furlArraysToVectors(xPoints, yPoints), color);
    }

    /**
     * Standard constructor, but X and Y coordinates of polyline's points
     * are passed in different arrays. Also creates a polyline with transparent color.
     * @param xPoints Array X coordinates of polyline's points (vertices)
     * @param yPoints Array Y coordinates of polyline's points (vertices)
     */
    public KPolyline(final int[] xPoints, final int[] yPoints) {
        this(KStructUtils.furlArraysToVectors(xPoints, yPoints), KColor.TRANSPARENT);
    }

    /**
     * Standard constructor but used shader is not default.
     * @param points Array of polyline's points (vertices)
     * @param shader Specific shader used for its rendering
     * @param color Polyline's color
     */
    public KPolyline(final KVector2i[] points, final KShaderProgram shader, final KColor color) {
        super(KAbstractShape.centroidOfPoints(points), shader);
        this.points = points;
        this.color = color;
    }

    /**
     * Creates a polyline with transparent color and used shader is not default.
     * @param points Array of polyline's points (vertices)
     * @param shader Specific shader used for its rendering
     */
    public KPolyline(final KVector2i[] points, final KShaderProgram shader) {
        this(points, shader, KColor.TRANSPARENT);
    }

    /**
     * Standard constructor, but X and Y coordinates of polyline's points
     * are passed in different arrays and used shader is not default.
     * @param xPoints Array X coordinates of polyline's points (vertices)
     * @param yPoints Array Y coordinates of polyline's points (vertices)
     *  @param shader Specific shader used for its rendering
     * @param color Polyline's color
     */
    public KPolyline(final int[] xPoints, final int[] yPoints, final KShaderProgram shader, final KColor color) {
        this(KStructUtils.furlArraysToVectors(xPoints, yPoints), shader, color);
    }

    /**
     * Standard constructor, but X and Y coordinates of polyline's points
     * are passed in different arrays. Also creates a polyline with transparent color
     * and used shader is not default.
     * @param xPoints Array X coordinates of polyline's points (vertices)
     * @param yPoints Array Y coordinates of polyline's points (vertices)
     *  @param shader Specific shader used for its rendering
     */
    public KPolyline(final int[] xPoints, final int[] yPoints, final KShaderProgram shader) {
        this(KStructUtils.furlArraysToVectors(xPoints, yPoints), shader, KColor.TRANSPARENT);
    }

    @Override
    public void render(final KRenderFrontend rf) {
        rf.render(this);
    }

    /**
     * Returns points of the polyline.
     * @return Points (vertices) of this polyline
     */
    public KVector2i[] points() {
        return this.points;
    }

    /**
     * Returns color of the polyline.
     * @return Color of this polyline
     */
    public KColor getColor() {
        return color;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KPolyline kPolyline = (KPolyline) o;
        return
                Objects.deepEquals(this.points, kPolyline.points)
            &&  Objects.equals(this.color, kPolyline.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(this.points), this.color);
    }

}
