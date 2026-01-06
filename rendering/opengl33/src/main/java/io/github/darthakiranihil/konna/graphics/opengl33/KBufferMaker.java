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

import io.github.darthakiranihil.konna.core.struct.KBufferUtils;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.core.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.graphics.shape.*;
import io.github.darthakiranihil.konna.libfrontend.opengl.KGl33;
import org.jetbrains.annotations.ApiStatus;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

@ApiStatus.Internal
@KExcludeFromGeneratedCoverageReport
final class KBufferMaker {

    public record BufferInfo(
        int vbo,
        int ibo
    ) {

        public void free(final KGl33 gl) {
            gl.glDeleteBuffers(vbo);
            gl.glDeleteBuffers(ibo);
        }
    }

    private final KGl33 gl;
    private KSize viewportSize;

    KBufferMaker(final KGl33 gl) {
        this.gl = gl;
        this.viewportSize = KSize.squared(KGl33RenderFrontend.DEFAULT_VIEWPORT_SIZE_SIDE);
    }

    public void setViewportSize(final KSize newSize) {
        this.viewportSize = newSize;
    }

    public BufferInfo make(final KPolygon polygon) {

        KVector2i[] points = polygon.points();
        FloatBuffer pointBuffer = KBufferUtils.createFloatBuffer(points.length * 2);
        IntBuffer indicesBuffer = KBufferUtils.createIntBuffer(points.length);

        for (int i = 0; i < points.length * 2; i += 2) {
            KVector2f glPoint = this.plainToGl(points[i / 2]);
            pointBuffer.put(glPoint.x());
            pointBuffer.put(glPoint.y());
            indicesBuffer.put(i / 2);
        }

        pointBuffer.flip();
        indicesBuffer.flip();

        return this.createBuffer(pointBuffer, indicesBuffer);
    }

    public BufferInfo make(final KArc arc, int circleDiscretizationPoints) {

        FloatBuffer pointBuffer = KBufferUtils.createFloatBuffer(circleDiscretizationPoints * 2);
        IntBuffer indicesBuffer = KBufferUtils.createIntBuffer(circleDiscretizationPoints);

        KSize size = arc.size();
        KVector2f center = this.plainToGl(arc.center());
        int startAngle = arc.startAngle();
        int arcAngle = arc.arcAngle();

        for (int i = startAngle; i < startAngle + arcAngle; i++) {
            float x =
                    ((float) size.width() / this.viewportSize.width())
                *   (float) Math.cos(i * Math.PI / 180) + center.x();
            float y =
                    ((float) size.height() / this.viewportSize.height())
                *   (float) Math.sin(i * Math.PI / 180) + center.y();
            pointBuffer.put(x);
            pointBuffer.put(y);
            indicesBuffer.put(i);
        }

        pointBuffer.flip();
        indicesBuffer.flip();

        return this.createBuffer(pointBuffer, indicesBuffer);
    }

    public BufferInfo make(final KLine line) {
        FloatBuffer pointBuffer = KBufferUtils.createFloatBuffer(4); // 2 points with 2 coordinates
        IntBuffer indicesBuffer = KBufferUtils.createIntBuffer(2);

        KVector2f glStart = this.plainToGl(line.start());
        KVector2f glEnd = this.plainToGl(line.end());
        pointBuffer.put(glStart.x());
        pointBuffer.put(glStart.y());
        pointBuffer.put(glEnd.x());
        pointBuffer.put(glEnd.y());

        indicesBuffer.put(0);
        indicesBuffer.put(1);

        pointBuffer.flip();
        indicesBuffer.flip();

        return this.createBuffer(pointBuffer, indicesBuffer);
    }

    public BufferInfo make(final KPolyline polyline) {
        KVector2i[] points = polyline.points();
        FloatBuffer pointBuffer = KBufferUtils.createFloatBuffer(points.length * 2);
        IntBuffer indicesBuffer = KBufferUtils.createIntBuffer(points.length);

        for (int i = 0; i < points.length * 2; i += 2) {
            KVector2f glPoint = this.plainToGl(points[i / 2]);
            pointBuffer.put(glPoint.x());
            pointBuffer.put(glPoint.y());
            indicesBuffer.put(i / 2);
        }

        pointBuffer.flip();
        indicesBuffer.flip();

        return this.createBuffer(pointBuffer, indicesBuffer);
    }

    public BufferInfo make(final KOval oval, int circleDiscretizationPoints) {
        FloatBuffer pointBuffer = KBufferUtils.createFloatBuffer(circleDiscretizationPoints * 2);
        IntBuffer indicesBuffer = KBufferUtils.createIntBuffer(circleDiscretizationPoints);

        KSize size = oval.size();
        KVector2f center = this.plainToGl(oval.center());
        for (int i = 0; i < circleDiscretizationPoints; i++) {
            float x =
                    ((float) size.width() / this.viewportSize.width())
                *   (float) Math.cos(i * 2 * Math.PI / circleDiscretizationPoints) + center.x();
            float y =
                    ((float) size.height() / this.viewportSize.height())
                *   (float) Math.sin(i * 2 * Math.PI / circleDiscretizationPoints) + center.y();
            pointBuffer.put(x);
            pointBuffer.put(y);
            indicesBuffer.put(i);
        }

        pointBuffer.flip();
        indicesBuffer.flip();

        return this.createBuffer(pointBuffer, indicesBuffer);
    }

    private BufferInfo createBuffer(final FloatBuffer pointBuffer, final IntBuffer indicesBuffer) {
        int vbo = this.gl.glGenBuffers();
        int ibo = this.gl.glGenBuffers();

        this.gl.glBindBuffer(KGl33.GL_ARRAY_BUFFER, vbo);
        this.gl.glBufferData(KGl33.GL_ARRAY_BUFFER, pointBuffer, KGl33.GL_STATIC_DRAW);

        this.gl.glBindBuffer(KGl33.GL_ELEMENT_ARRAY_BUFFER, ibo);
        this.gl.glBufferData(KGl33.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, KGl33.GL_STATIC_DRAW);

        this.gl.glBindBuffer(KGl33.GL_ARRAY_BUFFER, 0);
        this.gl.glBindBuffer(KGl33.GL_ELEMENT_ARRAY_BUFFER, 0);

        return new BufferInfo(vbo, ibo);
    }

    private KVector2f plainToGl(final KVector2i v) {
        float x = 2.0f * ((float) v.x() / this.viewportSize.width()) - 1.0f;
        float y = -2.0f * ((float) v.y() / this.viewportSize.height()) + 1.0f;

        return new KVector2f(x, y);
    }
}
