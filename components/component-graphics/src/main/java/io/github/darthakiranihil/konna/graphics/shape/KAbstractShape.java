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

import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.core.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.graphics.KTransform;
import io.github.darthakiranihil.konna.graphics.shader.KShaderProgram;
import org.jspecify.annotations.Nullable;

/**
 * Abstract base for all standard shapes.
 *
 * @since 0.3.0
 * @author Darth Akira Nihil
 */
// since transformable methods are wrappers to KTransform methods
@KExcludeFromGeneratedCoverageReport
public abstract class KAbstractShape implements KShape {

    private KTransform transform;
    private final @Nullable KShaderProgram shader;

    /**
     * Creates the shape with initial transform without shader.
     * @param center Center of the shape
     */
    protected KAbstractShape(final KVector2i center) {
        this.transform = new KTransform(center);
        this.shader = null;
    }

    /**
     * Creates the shape with initial transform and passed shader.
     * @param center Center of the shape
     * @param shader Shader using for rendering this shape
     */
    protected KAbstractShape(
        final KVector2i center,
        final KShaderProgram shader
    ) {
        this.transform = new KTransform(center);
        this.shader = shader;
    }

    @Override
    public KTransform getTransform() {
        return this.transform;
    }

    @Override
    public void setTransform(final KTransform newTransform) {
        this.transform = newTransform;
    }

    @Override
    public @Nullable KShaderProgram getShader() {
        return this.shader;
    }

    /**
     * Returns centroid of passed array of points.
     * @param points Array of points to get centroid of
     * @return Centroid of passed points
     */
    public static KVector2i centroidOfPoints(final KVector2i[] points) {
        double x = 0.0;
        double y = 0.0;
        double signedArea = 0;
        for (int i = 0; i < points.length; i++) {
            KVector2i p0 = points[i];
            KVector2i p1 = points[(i + 1) % points.length];

            double area = (p0.x() * p1.y()) - (p1.x() * p0.y());
            signedArea += area;

            x += (p0.x() + p1.x()) * area;
            y += (p0.y() + p1.y()) * area;

        }

        signedArea *= 0.5;
        x /= 6 * signedArea;
        y /= 6 * signedArea;

        return new KVector2i((int) x, (int) y);
    }

}
