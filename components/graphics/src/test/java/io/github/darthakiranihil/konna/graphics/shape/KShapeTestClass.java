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
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import io.github.darthakiranihil.konna.graphics.KColor;
import io.github.darthakiranihil.konna.graphics.impl.TestShaderProgram;
import io.github.darthakiranihil.konna.graphics.shader.KShaderProgram;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Assertions;

class KShapeTestClass extends KStandardTestClass {

    protected final KShaderProgram shader;

    protected KShapeTestClass() {
        this.shader = new TestShaderProgram();
    }

    protected void assertArc(
        KArc arc,
        KVector2i center,
        KSize size,
        int startAngle,
        int arcAngle,
        @Nullable KShaderProgram shader,
        KColor outlineColor,
        KColor fillColor
    ) {

        Assertions.assertEquals(center, arc.center());
        Assertions.assertEquals(size, arc.size());
        Assertions.assertEquals(startAngle, arc.startAngle());
        Assertions.assertEquals(arcAngle, arc.arcAngle());
        Assertions.assertEquals(shader, arc.getShader());
        Assertions.assertEquals(outlineColor, arc.getOutlineColor());
        Assertions.assertEquals(fillColor, arc.getFillColor());

    }

    protected void assertCircle(
        KCircle circle,
        KVector2i coordinates,
        int r,
        @Nullable KShaderProgram shader,
        KColor outlineColor,
        KColor fillColor
    ) {

        Assertions.assertEquals(coordinates, circle.center());
        Assertions.assertEquals(KSize.squared(2 * r), circle.size());
        Assertions.assertEquals(shader, circle.getShader());
        Assertions.assertEquals(outlineColor, circle.getOutlineColor());
        Assertions.assertEquals(fillColor, circle.getFillColor());

    }

    protected void assertLine(
        KLine line,
        KVector2i start,
        KVector2i end,
        @Nullable KShaderProgram shader,
        KColor color
    ) {

        Assertions.assertEquals(start, line.start());
        Assertions.assertEquals(end, line.end());
        Assertions.assertEquals(shader, line.getShader());
        Assertions.assertEquals(color, line.getColor());

    }

    protected void assertOval(
        KOval oval,
        KVector2i center,
        final KSize size,
        @Nullable KShaderProgram shader,
        final KColor outlineColor,
        final KColor fillColor
    ) {

        Assertions.assertEquals(center, oval.center());
        Assertions.assertEquals(size, oval.size());
        Assertions.assertEquals(shader, oval.getShader());
        Assertions.assertEquals(outlineColor, oval.getOutlineColor());
        Assertions.assertEquals(fillColor, oval.getFillColor());

    }

    protected void assertPolygon(
        KPolygon polygon,
        KVector2i[] points,
        @Nullable KShaderProgram shader,
        KColor outlineColor,
        KColor fillColor
    ) {

        var polygonPoints = polygon.points();
        Assertions.assertEquals(points.length, polygonPoints.length);
        for (int i = 0; i < points.length; i++) {
            Assertions.assertEquals(points[i], polygonPoints[i]);
        }
        Assertions.assertEquals(shader, polygon.getShader());
        Assertions.assertEquals(outlineColor, polygon.getOutlineColor());
        Assertions.assertEquals(fillColor, polygon.getFillColor());

    }

    protected void assertPolyline(
        KPolyline polyline,
        KVector2i[] points,
        @Nullable KShaderProgram shader,
        KColor color
    ) {

        var polylinePoints = polyline.points();
        Assertions.assertEquals(points.length, polylinePoints.length);
        for (int i = 0; i < points.length; i++) {
            Assertions.assertEquals(points[i], polylinePoints[i]);
        }
        Assertions.assertEquals(shader, polyline.getShader());
        Assertions.assertEquals(color, polyline.getColor());

    }

    protected void assertRectangle(
        KRectangle rectangle,
        KVector2i coordinates,
        KSize size,
        @Nullable KShaderProgram shader,
        KColor outlineColor,
        KColor fillColor
    ) {

        var rectanglePoints = rectangle.points();
        Assertions.assertEquals(4, rectanglePoints.length);

        Assertions.assertEquals(coordinates, rectanglePoints[0]);
        Assertions.assertEquals(coordinates.add(new KVector2i(size.width(), 0)), rectanglePoints[1]);
        Assertions.assertEquals(coordinates.add(size.vector()), rectanglePoints[2]);
        Assertions.assertEquals(coordinates.add(new KVector2i(0, size.height())), rectanglePoints[3]);

        Assertions.assertEquals(size.width(), rectangle.width());
        Assertions.assertEquals(size.height(), rectangle.height());
        Assertions.assertEquals(shader, rectangle.getShader());
        Assertions.assertEquals(outlineColor, rectangle.getOutlineColor());
        Assertions.assertEquals(fillColor, rectangle.getFillColor());

    }

    protected void assertSquare(
        KRectangle square,
        KVector2i coordinates,
        int side,
        @Nullable KShaderProgram shader,
        KColor outlineColor,
        KColor fillColor
    ) {

        var squarePoints = square.points();
        Assertions.assertEquals(4, squarePoints.length);

        Assertions.assertEquals(coordinates, squarePoints[0]);
        Assertions.assertEquals(coordinates.add(new KVector2i(side, 0)), squarePoints[1]);
        Assertions.assertEquals(coordinates.add(new KVector2i(side, side)), squarePoints[2]);
        Assertions.assertEquals(coordinates.add(new KVector2i(0, side)), squarePoints[3]);

        Assertions.assertEquals(side, square.width());
        Assertions.assertEquals(side, square.height());
        Assertions.assertEquals(shader, square.getShader());
        Assertions.assertEquals(outlineColor, square.getOutlineColor());
        Assertions.assertEquals(fillColor, square.getFillColor());

    }

}
