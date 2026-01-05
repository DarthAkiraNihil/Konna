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
import io.github.darthakiranihil.konna.core.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.graphics.KColor;
import io.github.darthakiranihil.konna.graphics.KTransform;
import io.github.darthakiranihil.konna.graphics.render.KRenderFrontend;
import io.github.darthakiranihil.konna.graphics.render.KRenderable;
import io.github.darthakiranihil.konna.graphics.shape.*;
import io.github.darthakiranihil.konna.libfrontend.opengl.KGl33;

import java.nio.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Render frontend implementation using OpenGL 3.3.
 * Uses buffers for storing object render data (at least for primitives)
 * that are cached. Cache expires after 512 render calls.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
@KExcludeFromGeneratedCoverageReport
public final class KGl33RenderFrontend extends KObject implements KRenderFrontend {

    private static final int CIRCLE_DISCRETIZATION_POINTS = 16384;
    private static final int DEFAULT_TTL = 512;

    /**
     * Default side of viewport size.
     */
    public static final int DEFAULT_VIEWPORT_SIZE_SIDE = 640;

    private final KGl33 gl;
    private KSize viewportSize;
    private boolean initialized;

    private final KBufferMaker bufferMaker;

    private final Map<KPair<Class<? extends KRenderable>, Integer>, KBufferMaker.BufferInfo> cache;
    private final Map<KPair<Class<? extends KRenderable>, Integer>, Integer> ttl;

    /**
     * Constructs render frontend with provided OpenGL 3.3
     * library frontend.
     * @param gl OpenGL 3.3 frontend
     */
    public KGl33RenderFrontend(@KInject final KGl33 gl) {
        this.gl = gl;
        this.viewportSize = KSize.squared(DEFAULT_VIEWPORT_SIZE_SIDE);

        this.cache = new ConcurrentHashMap<>();
        this.ttl = new ConcurrentHashMap<>();

        this.bufferMaker = new KBufferMaker(this.gl);
    }

    @Override
    public void setViewportSize(final KSize size) {
        this.viewportSize = size;
        this.bufferMaker.setViewportSize(size);
    }

    @Override
    public void render(final KRectangle rectangle) {
        this.render((KPolygon) rectangle);
    }

    @Override
    public void render(final KPolygon polygon) {

        int hash = KRenderableHasher.hash(polygon);
        KPair<Class<? extends KRenderable>, Integer> key = new KPair<>(KPolygon.class, hash);
        if (!this.cache.containsKey(key)) {
            this.cache.put(
                key,
                this.bufferMaker.make(polygon)
            );
        }

        this.ttl.put(key, DEFAULT_TTL);
        KBufferMaker.BufferInfo info = this.cache.get(key);
        this.render(
            info,
            polygon,
            polygon.getFillColor(),
            polygon.getOutlineColor(),
            polygon.points().length,
            KGl33.GL_LINE_LOOP
        );

    }

    @Override
    public void render(final KLine line) {

        int hash = KRenderableHasher.hash(line);
        KPair<Class<? extends KRenderable>, Integer> key = new KPair<>(KLine.class, hash);
        if (!this.cache.containsKey(key)) {
            this.cache.put(
                key,
                this.bufferMaker.make(line)
            );
        }
        this.ttl.put(key, DEFAULT_TTL);
        KBufferMaker.BufferInfo info = this.cache.get(key);
        this.render(info, line, line.getColor(), 2);
    }

    @Override
    public void render(final KPolyline polyline) {
        int hash = KRenderableHasher.hash(polyline);
        KPair<Class<? extends KRenderable>, Integer> key = new KPair<>(KPolyline.class, hash);
        if (!this.cache.containsKey(key)) {
            this.cache.put(
                key,
                this.bufferMaker.make(polyline)
            );
        }
        this.ttl.put(key, DEFAULT_TTL);
        KBufferMaker.BufferInfo info = this.cache.get(key);
        this.render(info, polyline, polyline.getColor(), polyline.points().length);
    }

    @Override
    public void render(final KOval oval) {
        int hash = KRenderableHasher.hash(oval);
        KPair<Class<? extends KRenderable>, Integer> key = new KPair<>(KOval.class, hash);
        if (!this.cache.containsKey(key)) {
            this.cache.put(
                key,
                this.bufferMaker.make(oval, CIRCLE_DISCRETIZATION_POINTS)
            );
        }

        this.ttl.put(key, DEFAULT_TTL);
        KBufferMaker.BufferInfo info = this.cache.get(key);
        this.render(
            info,
            oval,
            oval.getFillColor(),
            oval.getOutlineColor(),
            CIRCLE_DISCRETIZATION_POINTS,
            KGl33.GL_LINE_LOOP
        );
    }

    @Override
    public void render(final KCircle circle) {
        this.render((KOval) circle);
    }

    @Override
    public void render(final KArc arc) {
        int hash = KRenderableHasher.hash(arc);
        KPair<Class<? extends KRenderable>, Integer> key = new KPair<>(KArc.class, hash);
        if (!this.cache.containsKey(key)) {
            this.cache.put(
                key,
                this.bufferMaker.make(arc, CIRCLE_DISCRETIZATION_POINTS)
            );
        }

        this.ttl.put(key, DEFAULT_TTL);
        KBufferMaker.BufferInfo info = this.cache.get(key);
        this.render(
            info,
            arc,
            arc.getFillColor(),
            arc.getOutlineColor(),
            CIRCLE_DISCRETIZATION_POINTS,
            KGl33.GL_LINE_STRIP
        );
    }

    @Override
    public void clear() {
        this.gl.glClear(KGl33.GL_COLOR_BUFFER_BIT | KGl33.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void initialize() {
        if (this.initialized) {
            return;
        }

        this.gl.createCapabilities();
        this.initialized = true;
    }

    private void render(
        final KBufferMaker.BufferInfo info,
        final KShape shape,
        final KColor fillColor,
        final KColor outlineColor,
        int pointCount,
        int outlineRenderMode
    ) {

        this.gl.glBindBuffer(KGl33.GL_ARRAY_BUFFER, info.vbo());
        this.gl.glBindBuffer(KGl33.GL_ELEMENT_ARRAY_BUFFER, info.ibo());

        this.gl.glEnableClientState(KGl33.GL_VERTEX_ARRAY);
        this.gl.glVertexPointer(2, KGl33.GL_FLOAT, 0, 0L);

        // transform applying todo: move to shader`
        this.applyTransform(shape);

        this.gl.glColor4fv(fillColor.normalized());
        this.gl.glDrawElements(KGl33.GL_TRIANGLE_FAN, pointCount, KGl33.GL_UNSIGNED_INT, 0L);

        this.gl.glColor4fv(outlineColor.normalized());
        this.gl.glDrawElements(outlineRenderMode, pointCount, KGl33.GL_UNSIGNED_INT, 0L);

        this.gl.glDisableClientState(KGl33.GL_VERTEX_ARRAY);

        this.gl.glBindBuffer(KGl33.GL_ARRAY_BUFFER, 0);
        this.gl.glBindBuffer(KGl33.GL_ELEMENT_ARRAY_BUFFER, 0);
        this.updateTtl();
    }

    private void render(
        final KBufferMaker.BufferInfo info,
        final KShape shape,
        final KColor color,
        int pointCount
    ) {

        this.gl.glBindBuffer(KGl33.GL_ARRAY_BUFFER, info.vbo());
        this.gl.glBindBuffer(KGl33.GL_ELEMENT_ARRAY_BUFFER, info.ibo());

        this.gl.glEnableClientState(KGl33.GL_VERTEX_ARRAY);
        this.gl.glVertexPointer(2, KGl33.GL_FLOAT, 0, 0L);

        // transform applying todo: move to shader
        this.applyTransform(shape);

        this.gl.glColor4fv(color.normalized());
        this.gl.glDrawElements(KGl33.GL_LINE_STRIP, pointCount, KGl33.GL_UNSIGNED_INT, 0L);

        this.gl.glDisableClientState(KGl33.GL_VERTEX_ARRAY);
        this.gl.glBindBuffer(KGl33.GL_ARRAY_BUFFER, 0);
        this.gl.glBindBuffer(KGl33.GL_ELEMENT_ARRAY_BUFFER, 0);
        this.updateTtl();

    }

    private void applyTransform(final KShape shape) {

        KTransform transform = shape.getTransform();
        double rotation = transform.getRotation();
        KVector2i translation = transform.getTranslation();
        KVector2d scaling = transform.getScaling();
        float glTranslationX = (float) translation.x() / this.viewportSize.width();
        float glTranslationY = (float) -translation.y() / this.viewportSize.height();

        this.gl.glLoadIdentity();
        this.gl.glRotated(rotation, 0.0, 0.0, 1.0); // todo: fix pivot working
        this.gl.glScaled(scaling.x(), scaling.y(), 1.0);
        this.gl.glTranslatef(glTranslationX, glTranslationY, 0.0f);

    }

    private void updateTtl() {
        this.ttl.replaceAll((k, v) -> v - 1);

        var entrySet = this.ttl.entrySet();
        for (var entry: entrySet) {
            if (entry.getValue() > 0) {
                continue;
            }

            var key = entry.getKey();
            KBufferMaker.BufferInfo info = this.cache.get(key);

            info.free(this.gl);
            this.cache.remove(key);
            this.ttl.remove(key);

        }
    }

}
