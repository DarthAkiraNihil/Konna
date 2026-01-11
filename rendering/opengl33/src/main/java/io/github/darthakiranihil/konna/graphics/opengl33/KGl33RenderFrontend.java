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
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.graphics.KColor;
import io.github.darthakiranihil.konna.graphics.image.KRenderableTexture;
import io.github.darthakiranihil.konna.graphics.image.KTexture;
import io.github.darthakiranihil.konna.graphics.render.KRenderFrontend;
import io.github.darthakiranihil.konna.graphics.shader.KShaderCompiler;
import io.github.darthakiranihil.konna.graphics.shader.KShaderProgram;
import io.github.darthakiranihil.konna.graphics.shape.*;
import io.github.darthakiranihil.konna.graphics.text.KTiledText;
import io.github.darthakiranihil.konna.libfrontend.opengl.KGl33;

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

    /**
     * Default side of viewport size.
     */
    public static final int DEFAULT_VIEWPORT_SIZE_SIDE = 640;

    private final KGl33 gl;
    private boolean initialized;
    private KSize viewportSize;

    private final KBufferMaker bufferMaker;
    private final KTextureMaker textureMaker;
    private final KShaderCompiler shaderCompiler;
    private final KGl33TransformMatrixCalculator transformMatrixCalculator;


    /**
     * Constructs render frontend with provided OpenGL 3.3
     * library frontend.
     * @param gl OpenGL 3.3 frontend
     * @param shaderCompiler Shader compiler used for getting default shape shader
     * @param calculator Transform matrix calculator implemented in this module
     */
    public KGl33RenderFrontend(
        @KInject final KGl33 gl,
        @KInject final KShaderCompiler shaderCompiler,
        @KInject final KGl33TransformMatrixCalculator calculator
    ) {
        this.gl = gl;
        this.viewportSize = KSize.squared(DEFAULT_VIEWPORT_SIZE_SIDE);

        this.textureMaker = new KTextureMaker(this.gl);
        this.bufferMaker = new KBufferMaker(this.gl);
        this.shaderCompiler = shaderCompiler;
        this.transformMatrixCalculator = calculator;
    }

    @Override
    public void setViewportSize(final KSize size) {
        this.viewportSize = size;
        this.transformMatrixCalculator.setViewportSize(size);
        this.bufferMaker.setViewportSize(size);
        this.textureMaker.setViewportSize(size);
    }

    @Override
    public void render(final KRectangle rectangle) {
        this.render((KPolygon) rectangle);
    }

    @Override
    public void render(final KPolygon polygon) {
        this.render(
            this.bufferMaker.make(polygon),
            polygon,
            polygon.getFillColor(),
            polygon.getOutlineColor(),
            polygon.points().length,
            KGl33.GL_LINE_LOOP
        );

    }

    @Override
    public void render(final KLine line) {
        this.render(this.bufferMaker.make(line), line, line.getColor(), 2);
    }

    @Override
    public void render(final KPolyline polyline) {
        this.render(
            this.bufferMaker.make(polyline),
            polyline,
            polyline.getColor(),
            polyline.points().length
        );
    }

    @Override
    public void render(final KOval oval) {
        this.render(
            this.bufferMaker.make(oval),
            oval,
            oval.getFillColor(),
            oval.getOutlineColor(),
            KInternals.CIRCLE_DISCRETIZATION_POINTS,
            KGl33.GL_LINE_LOOP
        );
    }

    @Override
    public void render(final KCircle circle) {
        this.render((KOval) circle);
    }

    @Override
    public void render(final KArc arc) {
        this.render(
            this.bufferMaker.make(arc),
            arc,
            arc.getFillColor(),
            arc.getOutlineColor(),
            KInternals.CIRCLE_DISCRETIZATION_POINTS,
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

        this.gl.glEnable(KGl33.GL_BLEND);
        this.gl.glBlendFunc(KGl33.GL_SRC_ALPHA, KGl33.GL_ONE_MINUS_SRC_ALPHA);

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
        KShaderProgram shader = shape.getShader();
        if (shader == null) {
            shader = this.shaderCompiler.getDefaultShader();
        }
        this.setActiveShader(shader);

        this.gl.glBindBuffer(KGl33.GL_ARRAY_BUFFER, info.vbo());
        this.gl.glBindBuffer(KGl33.GL_ELEMENT_ARRAY_BUFFER, info.ibo());

        this.gl.glEnableClientState(KGl33.GL_VERTEX_ARRAY);
        this.gl.glVertexPointer(2, KGl33.GL_FLOAT, 0, 0L);

        shader.setUniformMatrix(
            KGl33ShaderCompiler.U_TRANSFORM,
            shape.getTransform().getMatrix()
        );

        shader.setUniform(KGl33ShaderCompiler.U_COLOR, fillColor.normalized());
        this.gl.glDrawElements(KGl33.GL_TRIANGLE_FAN, pointCount, KGl33.GL_UNSIGNED_INT, 0L);

        shader.setUniform(KGl33ShaderCompiler.U_COLOR, outlineColor.normalized());
        this.gl.glDrawElements(outlineRenderMode, pointCount, KGl33.GL_UNSIGNED_INT, 0L);

        this.gl.glDisableClientState(KGl33.GL_VERTEX_ARRAY);

        this.gl.glBindBuffer(KGl33.GL_ARRAY_BUFFER, 0);
        this.gl.glBindBuffer(KGl33.GL_ELEMENT_ARRAY_BUFFER, 0);
        this.updateTtl();
        this.disableActiveShader();
    }

    private void render(
        final KBufferMaker.BufferInfo info,
        final KShape shape,
        final KColor color,
        int pointCount
    ) {
        KShaderProgram shader = shape.getShader();
        if (shader == null) {
            shader = this.shaderCompiler.getDefaultShader();
        }
        this.setActiveShader(shader);

        this.gl.glBindBuffer(KGl33.GL_ARRAY_BUFFER, info.vbo());
        this.gl.glBindBuffer(KGl33.GL_ELEMENT_ARRAY_BUFFER, info.ibo());

        this.gl.glEnableClientState(KGl33.GL_VERTEX_ARRAY);
        this.gl.glVertexPointer(2, KGl33.GL_FLOAT, 0, 0L);

        shader.setUniformMatrix(
            KGl33ShaderCompiler.U_TRANSFORM,
            shape.getTransform().getMatrix()
        );

        shader.setUniform(KGl33ShaderCompiler.U_COLOR, color.normalized());
        this.gl.glDrawElements(KGl33.GL_LINE_STRIP, pointCount, KGl33.GL_UNSIGNED_INT, 0L);

        this.gl.glDisableClientState(KGl33.GL_VERTEX_ARRAY);
        this.gl.glBindBuffer(KGl33.GL_ARRAY_BUFFER, 0);
        this.gl.glBindBuffer(KGl33.GL_ELEMENT_ARRAY_BUFFER, 0);
        this.updateTtl();
        this.disableActiveShader();

    }

    @Override
    public void setActiveShader(final KShaderProgram shader) {
        this.gl.glUseProgram(shader.id());
    }

    @Override
    public void disableActiveShader() {
        this.gl.glUseProgram(0);
    }

    @Override
    public void render(final KRenderableTexture texture) {
        this.render(texture, false);
    }

    @Override
    public void render(KTiledText tiledText) {
        this.render(tiledText.getRendered(), true);
    }

    private void render(KRenderableTexture texture, boolean doNotTriangulate) {
        KTextureMaker.TextureInfo textureInfo = this.textureMaker.make(texture, doNotTriangulate);
        KTexture sourceTexture = texture.texture();

        this.setActiveShader(sourceTexture.shader());

        this.gl.glBindVertexArray(textureInfo.vao());
        this.gl.glBindBuffer(KGl33.GL_ARRAY_BUFFER, textureInfo.vbo());
        this.gl.glBindBuffer(KGl33.GL_ELEMENT_ARRAY_BUFFER, textureInfo.ebo());

        int stride = Float.BYTES * KTextureMaker.TEXTURE_ELEMENTS_COUNT;

        this.gl.glVertexAttribPointer(
            0,
            KTextureMaker.POINT_ATTRIBUTE_ELEMENTS_COUNT,
            KGl33.GL_FLOAT,
            false,
            stride,
            0
        );
        this.gl.glEnableVertexAttribArray(0);

        this.gl.glVertexAttribPointer(
            1,
            KTextureMaker.COLOR_ATTRIBUTE_ELEMENTS_COUNT,
            KGl33.GL_FLOAT,
            false,
            stride,
            2 * Float.BYTES
        );
        this.gl.glEnableVertexAttribArray(1);

        this.gl.glVertexAttribPointer(
            2,
            KTextureMaker.UV_ATTRIBUTE_ELEMENTS_COUNT,
            KGl33.GL_FLOAT,
            false,
            stride,
            6 * Float.BYTES
        );
        this.gl.glEnableVertexAttribArray(2);

        sourceTexture.shader().setUniform(KGl33ShaderCompiler.U_TEXTURE, 0);

        sourceTexture.shader().setUniformMatrix(
            KGl33ShaderCompiler.U_TRANSFORM,
            texture.getTransform().getMatrix()
        );

        this.gl.glBindVertexArray(textureInfo.vao());
        this.gl.glDrawElements(
            KGl33.GL_TRIANGLES,
            textureInfo.indicesCount(),
            KGl33.GL_UNSIGNED_INT,
            0L
        );

        this.gl.glDisableVertexAttribArray(0);
        this.gl.glDisableVertexAttribArray(1);
        this.gl.glDisableVertexAttribArray(2);

        this.gl.glBindBuffer(KGl33.GL_ARRAY_BUFFER, 0);
        this.gl.glBindBuffer(KGl33.GL_ELEMENT_ARRAY_BUFFER, 0);
        this.disableActiveShader();
    }

    private void updateTtl() {
        this.textureMaker.updateTtl();
        this.bufferMaker.updateTtl();
    }

}
