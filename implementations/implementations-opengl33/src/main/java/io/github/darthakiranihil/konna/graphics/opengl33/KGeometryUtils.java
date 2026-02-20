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

package io.github.darthakiranihil.konna.graphics.opengl33;

import io.github.darthakiranihil.konna.core.object.KUninstantiable;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.annotation.core.test.KExcludeFromGeneratedCoverageReport;
import org.jetbrains.annotations.ApiStatus;
import org.poly2tri.Poly2Tri;
import org.poly2tri.geometry.polygon.Polygon;
import org.poly2tri.geometry.polygon.PolygonPoint;
import org.poly2tri.triangulation.delaunay.DelaunayTriangle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApiStatus.Internal
@KExcludeFromGeneratedCoverageReport
final class KGeometryUtils extends KUninstantiable {

    private KGeometryUtils() {
        super();
    }

    public static int[] getTriangulatedVerticesIndices(final KVector2i[] vertices) {

        Map<KVector2i, Integer> idxMap = new HashMap<>();
        PolygonPoint[] points = new PolygonPoint[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            points[i] = new PolygonPoint(vertices[i].x(), vertices[i].y());
            idxMap.put(vertices[i], i);
        }

        Polygon polygon = new Polygon(points);
        Poly2Tri.triangulate(polygon);
        List<DelaunayTriangle> triangles = polygon.getTriangles();
        int[] indices = new int[triangles.size() * 3];

        int i = 0;
        for (DelaunayTriangle triangle: triangles) {
            var first = triangle.points[0];
            var second = triangle.points[1];
            var third = triangle.points[2];

            indices[i] = idxMap.get(new KVector2i((int) first.getX(), (int) first.getY()));
            indices[i + 1] = idxMap.get(new KVector2i((int) second.getX(), (int) second.getY()));
            indices[i + 2] = idxMap.get(new KVector2i((int) third.getX(), (int) third.getY()));

            i += 3;
        }

        return indices;
    }

    /**
     * Converts screen point to OpenGL format.
     * @param v Screen point coordinates
     * @param viewportSize Viewport size
     * @return OpenGL representation of passed point
     */
    public static KVector2f plainToGl(final KVector2i v, final KSize viewportSize) {
        float x = 2.0f * ((float) v.x() / viewportSize.width()) - 1.0f;
        float y = -2.0f * ((float) v.y() / viewportSize.height()) + 1.0f;

        return new KVector2f(x, y);
    }

    /**
     * Converts translation to OpenGL format.
     * @param v Translation vector
     * @param viewportSize Viewport size
     * @return OpenGL representation of passed translation
     */
    public static KVector2f plainTranslationToGl(
        final KVector2i v,
        final KSize viewportSize
    ) {

        KVector2i pos = new KVector2i(
            viewportSize.width() / 2 + v.x(),
            viewportSize.height() / 2 + v.y()
        );

        float x = 2.0f * ((float) pos.x() / viewportSize.width()) - 1.0f;
        float y = -2.0f * ((float) pos.y() / viewportSize.height()) + 1.0f;

        return new KVector2f(x, y);
    }

}
