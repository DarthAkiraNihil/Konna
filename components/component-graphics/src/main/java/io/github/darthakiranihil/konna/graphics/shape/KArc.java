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
import io.github.darthakiranihil.konna.graphics.shader.KShaderProgram;

import java.util.Objects;

/**
 * Representation of an arc (of an oval).
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
public class KArc extends KAbstractShape {

    private final KVector2i center;
    private final KSize size;
    private final int startAngle;
    private final int arcAngle;
    private KColor outlineColor;
    private KColor fillColor;

    /**
     * Standard constructor.
     * @param center Center of oval of the arc
     * @param size Size of oval of the arc
     * @param startAngle Start angle of the arc on the oval
     * @param arcAngle Arc angle on the oval
     * @param outlineColor Arc's outline color
     * @param fillColor Arc's fill color
     */
    public KArc(
        final KVector2i center,
        final KSize size,
        int startAngle,
        int arcAngle,
        final KColor outlineColor,
        final KColor fillColor
    ) {
        super(center);
        this.center = center;
        this.size = size;
        this.startAngle = startAngle;
        this.arcAngle = arcAngle;
        this.outlineColor = outlineColor;
        this.fillColor = fillColor;
    }

    /**
     * Creates an arc with transparent outline and fill colors.
     * @param coordinates Center of oval of the arc
     * @param size Size of oval of the arc
     * @param startAngle Start angle of the arc on the oval
     * @param arcAngle Arc angle on the oval
     */
    public KArc(
        final KVector2i coordinates, final KSize size, int startAngle, int arcAngle) {
        this(coordinates, size, startAngle, arcAngle, KColor.TRANSPARENT, KColor.TRANSPARENT);
    }

    /**
     * Creates an arc with transparent fill color.
     * @param coordinates Center of oval of the arc
     * @param size Size of oval of the arc
     * @param startAngle Start angle of the arc on the oval
     * @param arcAngle Arc angle on the oval
     * @param outlineColor Arc's outline color
     */
    public KArc(
        final KVector2i coordinates,
        final KSize size,
        int startAngle,
        int arcAngle,
        final KColor outlineColor
    ) {
        this(coordinates, size, startAngle, arcAngle, outlineColor, KColor.TRANSPARENT);
    }

    /**
     * Standard constructor, but center and size are defined by separated int parameters.
     * Also creates an arc with transparent outline and fill colors.
     * @param x X coordinate of center of oval of the arc
     * @param y Y coordinate of center of oval of the arc
     * @param width Width of oval of the arc
     * @param height Height of oval of the arc
     * @param startAngle Start angle of the arc on the oval
     * @param arcAngle Arc angle on the oval
     */
    public KArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        this(
            new KVector2i(x, y),
            new KSize(width, height),
            startAngle,
            arcAngle,
            KColor.TRANSPARENT,
            KColor.TRANSPARENT
        );
    }

    /**
     * Standard constructor, but center and size are defined by separated int parameters.
     * Also creates an arc with transparent fill color.
     * @param x X coordinate of center of oval of the arc
     * @param y Y coordinate of center of oval of the arc
     * @param width Width of oval of the arc
     * @param height Height of oval of the arc
     * @param startAngle Start angle of the arc on the oval
     * @param arcAngle Arc angle on the oval
     * @param outlineColor Arc's outline color
     */
    public KArc(
        int x,
        int y,
        int width,
        int height,
        int startAngle,
        int arcAngle,
        final KColor outlineColor
    ) {
        this(
            new KVector2i(x, y),
            new KSize(width, height),
            startAngle,
            arcAngle,
            outlineColor,
            KColor.TRANSPARENT
        );
    }

    /**
     * Standard constructor, but center and size are defined by separated int parameters.
     * @param x X coordinate of center of oval of the arc
     * @param y Y coordinate of center of oval of the arc
     * @param width Width of oval of the arc
     * @param height Height of oval of the arc
     * @param startAngle Start angle of the arc on the oval
     * @param arcAngle Arc angle on the oval
     * @param outlineColor Arc's outline color
     * @param fillColor Arc's fill color
     */
    public KArc(
        int x,
        int y,
        int width,
        int height,
        int startAngle,
        int arcAngle,
        final KColor outlineColor,
        final KColor fillColor
    ) {
        this(
            new KVector2i(x, y),
            new KSize(width, height),
            startAngle,
            arcAngle,
            outlineColor,
            fillColor
        );
    }

    /**
     * Standard constructor but shader is not default.
     * @param center Center of oval of the arc
     * @param size Size of oval of the arc
     * @param startAngle Start angle of the arc on the oval
     * @param arcAngle Arc angle on the oval
     * @param shader Specific shader used for its rendering
     * @param outlineColor Arc's outline color
     * @param fillColor Arc's fill color
     */
    public KArc(
        final KVector2i center,
        final KSize size,
        int startAngle,
        int arcAngle,
        final KShaderProgram shader,
        final KColor outlineColor,
        final KColor fillColor
    ) {
        super(center, shader);
        this.center = center;
        this.size = size;
        this.startAngle = startAngle;
        this.arcAngle = arcAngle;
        this.outlineColor = outlineColor;
        this.fillColor = fillColor;
    }

    /**
     * Creates an arc with transparent outline and fill colors and shader is not default.
     * @param coordinates Center of oval of the arc
     * @param size Size of oval of the arc
     * @param startAngle Start angle of the arc on the oval
     * @param arcAngle Arc angle on the oval
     * @param shader Specific shader used for its rendering
     */
    public KArc(
        final KVector2i coordinates,
        final KSize size,
        int startAngle,
        int arcAngle,
        final KShaderProgram shader
    ) {
        this(
            coordinates,
            size,
            startAngle,
            arcAngle,
            shader,
            KColor.TRANSPARENT,
            KColor.TRANSPARENT
        );
    }

    /**
     * Creates an arc with transparent fill color and shader is not default.
     * @param coordinates Center of oval of the arc
     * @param size Size of oval of the arc
     * @param startAngle Start angle of the arc on the oval
     * @param arcAngle Arc angle on the oval
     * @param shader Specific shader used for its rendering
     * @param outlineColor Arc's outline color
     */
    public KArc(
        final KVector2i coordinates,
        final KSize size,
        int startAngle,
        int arcAngle,
        final KShaderProgram shader,
        final KColor outlineColor
    ) {
        this(coordinates, size, startAngle, arcAngle, shader, outlineColor, KColor.TRANSPARENT);
    }

    /**
     * Standard constructor, but center and size are defined by separated int parameters
     * and shader is not default.
     * Also creates an arc with transparent outline and fill colors.
     * @param x X coordinate of center of oval of the arc
     * @param y Y coordinate of center of oval of the arc
     * @param width Width of oval of the arc
     * @param height Height of oval of the arc
     * @param startAngle Start angle of the arc on the oval
     * @param arcAngle Arc angle on the oval
     * @param shader Specific shader used for its rendering
     */
    public KArc(
        int x,
        int y,
        int width,
        int height,
        int startAngle,
        int arcAngle,
        final KShaderProgram shader
    ) {
        this(
            new KVector2i(x, y),
            new KSize(width, height),
            startAngle,
            arcAngle,
            shader,
            KColor.TRANSPARENT,
            KColor.TRANSPARENT
        );
    }

    /**
     * Standard constructor, but center and size are defined by separated int parameters
     * and shader is not default.
     * Also creates an arc with transparent fill color.
     * @param x X coordinate of center of oval of the arc
     * @param y Y coordinate of center of oval of the arc
     * @param width Width of oval of the arc
     * @param height Height of oval of the arc
     * @param startAngle Start angle of the arc on the oval
     * @param arcAngle Arc angle on the oval
     * @param shader Specific shader used for its rendering
     * @param outlineColor Arc's outline color
     */
    public KArc(
        int x,
        int y,
        int width,
        int height,
        int startAngle,
        int arcAngle,
        final KShaderProgram shader,
        final KColor outlineColor
    ) {
        this(
            new KVector2i(x, y),
            new KSize(width, height),
            startAngle,
            arcAngle,
            shader,
            outlineColor,
            KColor.TRANSPARENT
        );
    }

    /**
     * Standard constructor, but center and size are defined by separated int parameters
     * and shader is not default.
     * @param x X coordinate of center of oval of the arc
     * @param y Y coordinate of center of oval of the arc
     * @param width Width of oval of the arc
     * @param height Height of oval of the arc
     * @param startAngle Start angle of the arc on the oval
     * @param arcAngle Arc angle on the oval
     * @param shader Specific shader used for its rendering
     * @param outlineColor Arc's outline color
     * @param fillColor Arc's fill color
     */
    public KArc(
        int x,
        int y,
        int width,
        int height,
        int startAngle,
        int arcAngle,
        final KShaderProgram shader,
        final KColor outlineColor,
        final KColor fillColor
    ) {
        this(
            new KVector2i(x, y),
            new KSize(width, height),
            startAngle,
            arcAngle,
            shader,
            outlineColor,
            fillColor
        );
    }

    @Override
    public void render(final KRenderFrontend rf) {
        rf.render(this);
    }

    /**
     * Returns center coordinates of the oval of the arc.
     * @return Center coordinates of this arc
     */
    public KVector2i center() {
        return this.center;
    }

    /**
     * Returns size of the oval of the arc.
     * @return Size of this arc
     */
    public KSize size() {
        return this.size;
    }

    /**
     * Returns arc's start angle on its oval.
     * @return Start angle of this arc
     */
    public int startAngle() {
        return this.startAngle;
    }

    /**
     * Returns arc's angle on its oval.
     * @return Angle of this arc
     */
    public int arcAngle() {
        return this.arcAngle;
    }

    /**
     * Returns outline color of the arc.
     * @return Outline color of this arc
     */
    public KColor getOutlineColor() {
        return outlineColor;
    }

    /**
     * Returns fill color of the arc.
     * @return Fill color of this arc
     */
    public KColor getFillColor() {
        return fillColor;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KArc kArc = (KArc) o;
        return
            this.startAngle == kArc.startAngle
                &&  this.arcAngle == kArc.arcAngle
                &&  Objects.equals(this.center, kArc.center)
                &&  Objects.equals(this.size, kArc.size)
                &&  Objects.equals(this.outlineColor, kArc.outlineColor)
                &&  Objects.equals(this.fillColor, kArc.fillColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            this.center,
            this.size,
            this.startAngle,
            this.arcAngle,
            this.outlineColor,
            this.fillColor
        );
    }

}
