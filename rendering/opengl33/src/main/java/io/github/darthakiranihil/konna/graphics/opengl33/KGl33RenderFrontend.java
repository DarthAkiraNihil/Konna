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

import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.struct.*;
import io.github.darthakiranihil.konna.graphics.KTransform;
import io.github.darthakiranihil.konna.graphics.render.KRenderFrontend;
import io.github.darthakiranihil.konna.graphics.render.KRenderable;
import io.github.darthakiranihil.konna.graphics.shape.*;
import io.github.darthakiranihil.konna.libfrontend.opengl.KGl33;

import java.nio.*;
import java.util.HashMap;
import java.util.Map;

public final class KGl33RenderFrontend extends KObject implements KRenderFrontend {

    private static final int CIRCLE_DISCRETIZATION_POINTS = 16384;
    public static final int DEFAULT_VIEWPORT_SIZE_SIDE = 640;

    private final KGl33 gl;
    private KSize viewportSize;

    private final KBufferMaker bufferMaker;
    private final Map<KPair<Class<? extends KRenderable>, Integer>, KBufferMaker.BufferInfo> cache;

    /**
     * Constructs render frontend with provided OpenGL 3.3
     * library frontend.
     * @param gl OpenGL 3.3 frontend
     */
    public KGl33RenderFrontend(@KInject KGl33 gl) {
        this.gl = gl;
        this.viewportSize = KSize.squared(DEFAULT_VIEWPORT_SIZE_SIDE);

        this.cache = new HashMap<>();
        this.bufferMaker = new KBufferMaker(this.gl);
    }

    @Override
    public void setViewportSize(KSize size) {
        this.viewportSize = size;
        this.bufferMaker.setViewportSize(size);
    }

    @Override
    public void render(KRectangle rectangle) {
        this.render((KPolygon) rectangle);
    }

    @Override
    public void render(KPolygon polygon) {

        int hash = KRenderableHasher.hash(polygon);
        KPair<Class<? extends KRenderable>, Integer> key = new KPair<>(KPolygon.class, hash);
        if (!this.cache.containsKey(key)) {
            this.cache.put(
                key,
                this.bufferMaker.make(polygon)
            );
        }
        KBufferMaker.BufferInfo info = this.cache.get(key);

        this.gl.glBindBuffer(KGl33.GL_ARRAY_BUFFER, info.vbo());
        this.gl.glBindBuffer(KGl33.GL_ELEMENT_ARRAY_BUFFER, info.ibo());

        this.gl.glEnableClientState(KGl33.GL_VERTEX_ARRAY);
        this.gl.glVertexPointer(2, KGl33.GL_FLOAT, 0, 0L);


        // transform applying TODO: move to shader
        this.applyTransform(polygon);

        this.gl.glColor4fv(polygon.getFillColor().normalized());
        this.gl.glDrawElements(KGl33.GL_TRIANGLE_FAN, polygon.points().length, KGl33.GL_UNSIGNED_INT, 0L);

        this.gl.glColor4fv(polygon.getOutlineColor().normalized());
        this.gl.glDrawElements(KGl33.GL_LINE_LOOP, polygon.points().length, KGl33.GL_UNSIGNED_INT, 0L);

        this.gl.glDisableClientState(KGl33.GL_VERTEX_ARRAY);
        this.gl.glBindBuffer(KGl33.GL_ARRAY_BUFFER, 0);
        this.gl.glBindBuffer(KGl33.GL_ELEMENT_ARRAY_BUFFER, 0);

    }

    @Override
    public void render(KLine line) {

        int hash = KRenderableHasher.hash(line);
        KPair<Class<? extends KRenderable>, Integer> key = new KPair<>(KLine.class, hash);
        if (!this.cache.containsKey(key)) {
            this.cache.put(
                key,
                this.bufferMaker.make(line)
            );
        }
        KBufferMaker.BufferInfo info = this.cache.get(key);

        this.gl.glBindBuffer(KGl33.GL_ARRAY_BUFFER, info.vbo());
        this.gl.glBindBuffer(KGl33.GL_ELEMENT_ARRAY_BUFFER, info.ibo());

        this.gl.glEnableClientState(KGl33.GL_VERTEX_ARRAY);
        this.gl.glVertexPointer(2, KGl33.GL_FLOAT, 0, 0L);

        // transform applying TODO: move to shader
        this.applyTransform(line);

        this.gl.glColor4fv(line.getColor().normalized());
        this.gl.glDrawElements(KGl33.GL_LINE_STRIP, 2, KGl33.GL_UNSIGNED_INT, 0L);

        this.gl.glDisableClientState(KGl33.GL_VERTEX_ARRAY);
        this.gl.glBindBuffer(KGl33.GL_ARRAY_BUFFER, 0);
        this.gl.glBindBuffer(KGl33.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    @Override
    public void render(KPolyline polyline) {
        int hash = KRenderableHasher.hash(polyline);
        KPair<Class<? extends KRenderable>, Integer> key = new KPair<>(KPolyline.class, hash);
        if (!this.cache.containsKey(key)) {
            this.cache.put(
                key,
                this.bufferMaker.make(polyline)
            );
        }
        KBufferMaker.BufferInfo info = this.cache.get(key);

        this.gl.glBindBuffer(KGl33.GL_ARRAY_BUFFER, info.vbo());
        this.gl.glBindBuffer(KGl33.GL_ELEMENT_ARRAY_BUFFER, info.ibo());

        this.gl.glEnableClientState(KGl33.GL_VERTEX_ARRAY);
        this.gl.glVertexPointer(2, KGl33.GL_FLOAT, 0, 0L);

        // transform applying TODO: move to shader
        this.applyTransform(polyline);

        this.gl.glColor4fv(polyline.getColor().normalized());
        this.gl.glDrawElements(KGl33.GL_LINE_STRIP, polyline.points().length, KGl33.GL_UNSIGNED_INT, 0L);

        this.gl.glDisableClientState(KGl33.GL_VERTEX_ARRAY);
        this.gl.glBindBuffer(KGl33.GL_ARRAY_BUFFER, 0);
        this.gl.glBindBuffer(KGl33.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    @Override
    public void render(KOval oval) {
        int hash = KRenderableHasher.hash(oval);
        KPair<Class<? extends KRenderable>, Integer> key = new KPair<>(KOval.class, hash);
        if (!this.cache.containsKey(key)) {
            this.cache.put(
                key,
                this.bufferMaker.make(oval, CIRCLE_DISCRETIZATION_POINTS)
            );
        }
        KBufferMaker.BufferInfo info = this.cache.get(key);

        this.gl.glBindBuffer(KGl33.GL_ARRAY_BUFFER, info.vbo());
        this.gl.glBindBuffer(KGl33.GL_ELEMENT_ARRAY_BUFFER, info.ibo());

        this.gl.glEnableClientState(KGl33.GL_VERTEX_ARRAY);
        this.gl.glVertexPointer(2, KGl33.GL_FLOAT, 0, 0L);

        // transform applying TODO: move to shader
        this.applyTransform(oval);

        this.gl.glColor4fv(oval.getFillColor().normalized());
        this.gl.glDrawElements(KGl33.GL_TRIANGLE_FAN, CIRCLE_DISCRETIZATION_POINTS, KGl33.GL_UNSIGNED_INT, 0L);

        this.gl.glColor4fv(oval.getOutlineColor().normalized());
        this.gl.glDrawElements(KGl33.GL_LINE_LOOP, CIRCLE_DISCRETIZATION_POINTS, KGl33.GL_UNSIGNED_INT, 0L);

        this.gl.glDisableClientState(KGl33.GL_VERTEX_ARRAY);
        this.gl.glBindBuffer(KGl33.GL_ARRAY_BUFFER, 0);
        this.gl.glBindBuffer(KGl33.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    @Override
    public void render(KCircle circle) {
        this.render((KOval) circle);
    }

    @Override
    public void render(KArc arc) {
        int hash = KRenderableHasher.hash(arc);
        KPair<Class<? extends KRenderable>, Integer> key = new KPair<>(KArc.class, hash);
        if (!this.cache.containsKey(key)) {
            this.cache.put(
                key,
                this.bufferMaker.make(arc, CIRCLE_DISCRETIZATION_POINTS)
            );
        }
        KBufferMaker.BufferInfo info = this.cache.get(key);

        this.gl.glBindBuffer(KGl33.GL_ARRAY_BUFFER, info.vbo());
        this.gl.glBindBuffer(KGl33.GL_ELEMENT_ARRAY_BUFFER, info.ibo());

        this.gl.glEnableClientState(KGl33.GL_VERTEX_ARRAY);
        this.gl.glVertexPointer(2, KGl33.GL_FLOAT, 0, 0L);

        // transform applying TODO: move to shader`
        this.applyTransform(arc);

        this.gl.glColor4fv(arc.getFillColor().normalized());
        this.gl.glDrawElements(KGl33.GL_TRIANGLE_FAN, CIRCLE_DISCRETIZATION_POINTS, KGl33.GL_UNSIGNED_INT, 0L);

        this.gl.glColor4fv(arc.getOutlineColor().normalized());
        this.gl.glDrawElements(KGl33.GL_LINE_STRIP, CIRCLE_DISCRETIZATION_POINTS, KGl33.GL_UNSIGNED_INT, 0L);

        this.gl.glDisableClientState(KGl33.GL_VERTEX_ARRAY);
        this.gl.glBindBuffer(KGl33.GL_ARRAY_BUFFER, 0);
        this.gl.glBindBuffer(KGl33.GL_ELEMENT_ARRAY_BUFFER, 0);
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
