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

package io.github.darthakiranihil.konna.libfrontend.gl20;

import io.github.darthakiranihil.konna.core.graphics.KTransform;
import io.github.darthakiranihil.konna.core.graphics.render.KRenderFrontend;
import io.github.darthakiranihil.konna.core.graphics.shape.KColor;
import io.github.darthakiranihil.konna.core.graphics.shape.KPolygon;
import io.github.darthakiranihil.konna.core.graphics.shape.KRectangle;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.struct.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public final class KGl20RenderFrontend extends KObject implements KRenderFrontend {

    private final KGl20 gl;
    private KSize viewportSize;

    public KGl20RenderFrontend(KGl20 gl) {
        this.gl = gl;
    }

    private KVector2f plainToGl(KVector2i v) {
        float x = 2.0f * ((float) v.x() / this.viewportSize.width()) - 1.0f;
        float y = -2.0f * ((float) v.y() / this.viewportSize.height()) + 1.0f;

        return new KVector2f(x, y);
    }

    @Override
    public void setViewportSize(KSize size) {
        this.viewportSize = size;
    }

    @Override
    public void render(KRectangle rectangle) {
        this.render((KPolygon) rectangle);
    }

    @Override
    public void render(KPolygon polygon) {

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

        int vbo = this.gl.glGenBuffers();
        int ibo = this.gl.glGenBuffers();

        this.gl.glBindBuffer(KGl20.GL_ARRAY_BUFFER, vbo);
        this.gl.glBufferData(KGl20.GL_ARRAY_BUFFER, pointBuffer, KGl20.GL_STATIC_DRAW);

        this.gl.glBindBuffer(KGl20.GL_ELEMENT_ARRAY_BUFFER, ibo);
        this.gl.glBufferData(KGl20.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, KGl20.GL_STATIC_DRAW);

        this.gl.glEnableClientState(KGl20.GL_VERTEX_ARRAY);
        this.gl.glVertexPointer(2, KGl20.GL_FLOAT, 0, 0L);

        // transform applying TODO: move to shader
        KTransform transform = polygon.getTransform();
        double rotation = transform.getRotation();
        KVector2i translation = transform.getTranslation();
        KVector2d scaling = transform.getScaling();
        KVector2f glTranslation = this.plainToGl(translation);

        this.gl.glLoadIdentity();
        this.gl.glRotated(rotation, 0.0, 0.0, 1.0); // TODO: fix pivot working
        this.gl.glScaled(scaling.x(), scaling.y(), 1.0);
        this.gl.glTranslatef(glTranslation.x(), glTranslation.y(), 0.0f);

        this.gl.glColor4fv(polygon.getFillColor().normalized());
        this.gl.glDrawElements(KGl20.GL_TRIANGLE_FAN, points.length, KGl20.GL_UNSIGNED_INT, 0L);

        this.gl.glColor4fv(polygon.getOutlineColor().normalized());
        this.gl.glDrawElements(KGl20.GL_LINE_LOOP, points.length, KGl20.GL_UNSIGNED_INT, 0L);

        this.gl.glDisableClientState(KGl20.GL_VERTEX_ARRAY);
        this.gl.glBindBuffer(KGl20.GL_ARRAY_BUFFER, 0);
        this.gl.glBindBuffer(KGl20.GL_ELEMENT_ARRAY_BUFFER, 0);
        this.gl.glDeleteBuffers(vbo);
        this.gl.glDeleteBuffers(ibo);

    }
}
