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

package io.github.darthakiranihil.konna.libfrontend.gl33;

import io.github.darthakiranihil.konna.core.graphics.KTransform;
import io.github.darthakiranihil.konna.core.graphics.image.KImage;
import io.github.darthakiranihil.konna.core.graphics.image.KTexture;
import io.github.darthakiranihil.konna.core.graphics.render.KRenderFrontend;
import io.github.darthakiranihil.konna.core.graphics.shader.KShaderProgram;
import io.github.darthakiranihil.konna.core.graphics.shape.*;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.struct.*;
import io.github.darthakiranihil.konna.libfrontend.gl20.KGl20;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

//TODO: optimize
/**
 * Implementation of {@link KRenderFrontend} that uses OpenGL3.3
 * to render objects.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public final class KGl33RenderFrontend extends KObject implements KRenderFrontend {

    private static final int CIRCLE_DISCRETIZATION_POINTS = 16384;

    private final KGl33 gl;
    private KSize viewportSize;

    /**
     * Constructs render frontend with provided OpenGL 3.3
     * library frontend.
     * @param gl OpenGL 3.3 frontend
     */
    public KGl33RenderFrontend(KGl33 gl) {
        this.gl = gl;
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
        this.applyTransform(polygon);

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

    @Override
    public void render(KLine line) {

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

        int vbo = this.gl.glGenBuffers();
        int ibo = this.gl.glGenBuffers();

        this.gl.glBindBuffer(KGl20.GL_ARRAY_BUFFER, vbo);
        this.gl.glBufferData(KGl20.GL_ARRAY_BUFFER, pointBuffer, KGl20.GL_STATIC_DRAW);

        this.gl.glBindBuffer(KGl20.GL_ELEMENT_ARRAY_BUFFER, ibo);
        this.gl.glBufferData(KGl20.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, KGl20.GL_STATIC_DRAW);

        this.gl.glEnableClientState(KGl20.GL_VERTEX_ARRAY);
        this.gl.glVertexPointer(2, KGl20.GL_FLOAT, 0, 0L);

        // transform applying TODO: move to shader
        this.applyTransform(line);

        this.gl.glColor4fv(line.getColor().normalized());
        this.gl.glDrawElements(KGl20.GL_LINE_STRIP, 2, KGl20.GL_UNSIGNED_INT, 0L);

        this.gl.glDisableClientState(KGl20.GL_VERTEX_ARRAY);
        this.gl.glBindBuffer(KGl20.GL_ARRAY_BUFFER, 0);
        this.gl.glBindBuffer(KGl20.GL_ELEMENT_ARRAY_BUFFER, 0);
        this.gl.glDeleteBuffers(vbo);
        this.gl.glDeleteBuffers(ibo);
    }

    @Override
    public void render(KPolyline polyline) {
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

        int vbo = this.gl.glGenBuffers();
        int ibo = this.gl.glGenBuffers();

        this.gl.glBindBuffer(KGl20.GL_ARRAY_BUFFER, vbo);
        this.gl.glBufferData(KGl20.GL_ARRAY_BUFFER, pointBuffer, KGl20.GL_STATIC_DRAW);

        this.gl.glBindBuffer(KGl20.GL_ELEMENT_ARRAY_BUFFER, ibo);
        this.gl.glBufferData(KGl20.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, KGl20.GL_STATIC_DRAW);

        this.gl.glEnableClientState(KGl20.GL_VERTEX_ARRAY);
        this.gl.glVertexPointer(2, KGl20.GL_FLOAT, 0, 0L);

        // transform applying TODO: move to shader
        this.applyTransform(polyline);

        this.gl.glColor4fv(polyline.getColor().normalized());
        this.gl.glDrawElements(KGl20.GL_LINE_STRIP, points.length, KGl20.GL_UNSIGNED_INT, 0L);

        this.gl.glDisableClientState(KGl20.GL_VERTEX_ARRAY);
        this.gl.glBindBuffer(KGl20.GL_ARRAY_BUFFER, 0);
        this.gl.glBindBuffer(KGl20.GL_ELEMENT_ARRAY_BUFFER, 0);
        this.gl.glDeleteBuffers(vbo);
        this.gl.glDeleteBuffers(ibo);
    }

    @Override
    public void render(KOval oval) {
        FloatBuffer pointBuffer = KBufferUtils.createFloatBuffer(CIRCLE_DISCRETIZATION_POINTS * 2);
        IntBuffer indicesBuffer = KBufferUtils.createIntBuffer(CIRCLE_DISCRETIZATION_POINTS);

        KSize size = oval.size();
        KVector2f center = this.plainToGl(oval.center());
        for (int i = 0; i < CIRCLE_DISCRETIZATION_POINTS; i++) {
            float x = ((float) size.width() / this.viewportSize.width()) * (float) Math.cos(i * 2 * Math.PI / CIRCLE_DISCRETIZATION_POINTS) + center.x();
            float y = ((float) size.height() / this.viewportSize.height()) * (float) Math.sin(i * 2 * Math.PI / CIRCLE_DISCRETIZATION_POINTS) + center.y();
            pointBuffer.put(x);
            pointBuffer.put(y);
            indicesBuffer.put(i);
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
        this.applyTransform(oval);

        this.gl.glColor4fv(oval.getFillColor().normalized());
        this.gl.glDrawElements(KGl20.GL_TRIANGLE_FAN, CIRCLE_DISCRETIZATION_POINTS, KGl20.GL_UNSIGNED_INT, 0L);

        this.gl.glColor4fv(oval.getOutlineColor().normalized());
        this.gl.glDrawElements(KGl20.GL_LINE_LOOP, CIRCLE_DISCRETIZATION_POINTS, KGl20.GL_UNSIGNED_INT, 0L);

        this.gl.glDisableClientState(KGl20.GL_VERTEX_ARRAY);
        this.gl.glBindBuffer(KGl20.GL_ARRAY_BUFFER, 0);
        this.gl.glBindBuffer(KGl20.GL_ELEMENT_ARRAY_BUFFER, 0);
        this.gl.glDeleteBuffers(vbo);
        this.gl.glDeleteBuffers(ibo);
    }

    @Override
    public void render(KCircle circle) {
        this.render((KOval) circle);
    }

    @Override
    public void render(KArc arc) {
        FloatBuffer pointBuffer = KBufferUtils.createFloatBuffer(CIRCLE_DISCRETIZATION_POINTS * 2);
        IntBuffer indicesBuffer = KBufferUtils.createIntBuffer(CIRCLE_DISCRETIZATION_POINTS);

        KSize size = arc.size();
        KVector2f center = this.plainToGl(arc.center());
        int startAngle = arc.startAngle();
        int arcAngle = arc.arcAngle();


        for (int i = startAngle; i < startAngle + arcAngle; i++) {
            float x = ((float) size.width() / this.viewportSize.width()) * (float) Math.cos(i * Math.PI / 180) + center.x();
            float y = ((float) size.height() / this.viewportSize.height()) * (float) Math.sin(i * Math.PI / 180) + center.y();
            pointBuffer.put(x);
            pointBuffer.put(y);
            indicesBuffer.put(i);
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

        // transform applying TODO: move to shader`
        this.applyTransform(arc);

        this.gl.glColor4fv(arc.getFillColor().normalized());
        this.gl.glDrawElements(KGl20.GL_TRIANGLE_FAN, CIRCLE_DISCRETIZATION_POINTS, KGl20.GL_UNSIGNED_INT, 0L);

        this.gl.glColor4fv(arc.getOutlineColor().normalized());
        this.gl.glDrawElements(KGl20.GL_LINE_STRIP, CIRCLE_DISCRETIZATION_POINTS, KGl20.GL_UNSIGNED_INT, 0L);

        this.gl.glDisableClientState(KGl20.GL_VERTEX_ARRAY);
        this.gl.glBindBuffer(KGl20.GL_ARRAY_BUFFER, 0);
        this.gl.glBindBuffer(KGl20.GL_ELEMENT_ARRAY_BUFFER, 0);
        this.gl.glDeleteBuffers(vbo);
        this.gl.glDeleteBuffers(ibo);
    }

    @Override
    public void render(KTexture texture) {




        int tex = this.gl.glGenTextures();
        this.gl.glActiveTexture(KGl33.GL_TEXTURE0);
        this.gl.glBindTexture(KGl33.GL_TEXTURE_2D, tex);

        KImage attachedImage = texture.getAttachedImage();
        this.gl.glTexImage2D(
            KGl33.GL_TEXTURE_2D,
            0,
            KGl33.GL_RGBA,
            attachedImage.width(),
            attachedImage.height(),
            0,
            KGl33.GL_RGBA,
            KGl20.GL_UNSIGNED_BYTE,
            attachedImage.rawData()
        );
        this.gl.glGenerateMipmap(KGl33.GL_TEXTURE_2D);
        this.gl.glTexParameteri(KGl33.GL_TEXTURE_2D, KGl33.GL_TEXTURE_WRAP_S, KGl33.GL_REPEAT);	// set texture wrapping to GL_REPEAT (default wrapping method)
        this.gl.glTexParameteri(KGl33.GL_TEXTURE_2D, KGl33.GL_TEXTURE_WRAP_T, KGl33.GL_REPEAT);
        // set texture filtering parameters
        this.gl.glTexParameteri(KGl33.GL_TEXTURE_2D, KGl33.GL_TEXTURE_MIN_FILTER, KGl33.GL_LINEAR_MIPMAP_LINEAR);
        this.gl.glTexParameteri(KGl33.GL_TEXTURE_2D, KGl33.GL_TEXTURE_MAG_FILTER, KGl33.GL_LINEAR);


        KVector2i[] verts = texture.xy();
        KVector2f[] uvs = texture.uv();
        FloatBuffer vertices = KBufferUtils.createFloatBuffer(32); // TODO: remove hardcode
        IntBuffer indices = KBufferUtils.createIntBuffer(6);
        indices.put(0).put(1).put(3).put(1).put(2).put(3);

        for (int i = 0; i < verts.length; i++) {
            KVector2f glPoint = this.plainToGl(verts[i]);

            vertices
                .put(glPoint.x())
                .put(glPoint.y())
                .put(texture.color().normalized())
                .put(uvs[i].x())
                .put(uvs[i].y());

        }
        vertices.flip();
        indices.flip();
        int vao = this.gl.glGenVertexArrays();
        int vbo = this.gl.glGenBuffers();
        int ebo = this.gl.glGenBuffers();

        this.gl.glBindVertexArray(vao);

        this.gl.glBindBuffer(KGl20.GL_ARRAY_BUFFER, vbo);
        this.gl.glBufferData(KGl20.GL_ARRAY_BUFFER, vertices, KGl20.GL_STATIC_DRAW);

        this.gl.glBindBuffer(KGl20.GL_ELEMENT_ARRAY_BUFFER, ebo);
        this.gl.glBufferData(KGl20.GL_ELEMENT_ARRAY_BUFFER, indices, KGl20.GL_STATIC_DRAW);

        int stride = Float.BYTES * 8;

        this.gl.glVertexAttribPointer(0, 2, KGl33.GL_FLOAT, false, stride, 0);
        this.gl.glEnableVertexAttribArray(0);

        this.gl.glVertexAttribPointer(1, 4, KGl33.GL_FLOAT, false, stride, 2 * Float.BYTES);
        this.gl.glEnableVertexAttribArray(1);

        this.gl.glVertexAttribPointer(2, 2, KGl33.GL_FLOAT, false, stride, 6 * Float.BYTES);
        this.gl.glEnableVertexAttribArray(2);
        //vertices.flip();
        this.gl.glUniform1i(
            this.gl.glGetUniformLocation(texture.getShader().id(), "ourTexture"), 0
        );
        this.gl.glBindVertexArray(vao);
        this.gl.glDrawElements(KGl33.GL_TRIANGLES, 6, KGl33.GL_UNSIGNED_INT, 0L);

        this.gl.glDisableVertexAttribArray(0);
        this.gl.glDisableVertexAttribArray(1);
        this.gl.glDisableVertexAttribArray(2);

        this.gl.glDeleteBuffers(vbo);
        this.gl.glDeleteBuffers(ebo);
        this.gl.glDeleteVertexArrays(vao);
        this.gl.glDeleteTextures(tex);
    }

    @Override
    public void setActiveShader(KShaderProgram shader) {
        this.gl.glUseProgram(shader.id());
    }

    @Override
    public void disableActiveShader() {
        this.gl.glUseProgram(0);
    }

    private KVector2f plainToGl(KVector2i v) {
        float x = 2.0f * ((float) v.x() / this.viewportSize.width()) - 1.0f;
        float y = -2.0f * ((float) v.y() / this.viewportSize.height()) + 1.0f;

        return new KVector2f(x, y);
    }

    private void applyTransform(final KShape shape) {

        KTransform transform = shape.getTransform();
        double rotation = transform.getRotation();
        KVector2i translation = transform.getTranslation();
        KVector2d scaling = transform.getScaling();
        float glTranslationX = (float) translation.x() / this.viewportSize.width();
        float glTranslationY = (float) -translation.y() / this.viewportSize.height();

        this.gl.glLoadIdentity();
        this.gl.glRotated(rotation, 0.0, 0.0, 1.0); // TODO: fix pivot working
        this.gl.glScaled(scaling.x(), scaling.y(), 1.0);
        this.gl.glTranslatef(glTranslationX, glTranslationY, 0.0f);

    }

}
