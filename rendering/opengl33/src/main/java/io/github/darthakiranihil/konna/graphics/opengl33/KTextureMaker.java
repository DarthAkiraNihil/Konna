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

import io.github.darthakiranihil.konna.core.struct.*;
import io.github.darthakiranihil.konna.core.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.core.util.KCache;
import io.github.darthakiranihil.konna.graphics.KColor;
import io.github.darthakiranihil.konna.graphics.image.*;
import io.github.darthakiranihil.konna.libfrontend.opengl.KGl33;
import org.jetbrains.annotations.ApiStatus;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@ApiStatus.Internal
@KExcludeFromGeneratedCoverageReport
final class KTextureMaker {

    /**
     * Total texture elements count.
     */
    public static final int TEXTURE_ELEMENTS_COUNT = 8;
    /**
     * Points attribute elements count.
     */
    public static final int POINT_ATTRIBUTE_ELEMENTS_COUNT = 2;
    /**
     * Color attribute elements count.
     */
    public static final int COLOR_ATTRIBUTE_ELEMENTS_COUNT = 4;
    /**
     * UV attribute elements count.
     */
    public static final int UV_ATTRIBUTE_ELEMENTS_COUNT = 2;

    @KExcludeFromGeneratedCoverageReport
    public record TextureInfo(
        int id,
        int vao,
        int vbo,
        int ebo,
        int indicesCount
    ) {

        public void free(final KGl33 gl) {
            gl.glDeleteBuffers(vao);
            gl.glDeleteBuffers(vbo);
            gl.glDeleteBuffers(ebo);
            // gl.glDeleteTextures(id);
        }

    }

    private final KCache cache;
    private final KGl33 gl;
    private KSize viewportSize;

    KTextureMaker(final KGl33 gl, final KCache cache) {
        this.gl = gl;
        this.viewportSize = KSize.squared(KGl33RenderFrontend.DEFAULT_VIEWPORT_SIZE_SIDE);

        this.cache = cache;
    }

    public void setViewportSize(final KSize newSize) {
        this.viewportSize = newSize;
    }


    public TextureInfo make(final KRenderableTexture texture, boolean doNotTriangulate) {

        String hash = Integer.toString(KRenderableHasher.hash(texture));

        if (!this.cache.hasKey(hash)) {

            this.cache.putToCache(
                hash,
                this.createTextureInfo(texture, doNotTriangulate),
                i -> i.free(this.gl),
                KCache.TTL_EVERLASTING
            );
        }

        return Objects.requireNonNull(
            this.cache.getFromCache(hash, TextureInfo.class)
        );

    }

    private TextureInfo createTextureInfo(
        final KRenderableTexture texture,
        boolean doNotTriangulate
    ) {

        KTexture sourceTexture = texture.texture();

        KImage attachedImage = sourceTexture.attachedImage();
        String imageHash = Integer.toString(attachedImage.hashCode());

        int tex;
        if (this.cache.hasKey(imageHash)) {
            tex = Objects.requireNonNull(this.cache.getFromCache(imageHash, Integer.class));
            this.gl.glBindTexture(KGl33.GL_TEXTURE_2D, tex);
        } else {
            tex = this.createTexture(attachedImage, sourceTexture);
            this.cache.putToCache(
                imageHash,
                tex,
                this.gl::glDeleteTextures,
                KCache.TTL_EVERLASTING
            );
        }

        KVector2i[] vertices = texture.xy(); // v * 4
        KVector2f[] uvs = texture.uv();
        KColor[] colors = texture.colors();

        FloatBuffer verticesBuffer = KBufferUtils.createFloatBuffer(
            vertices.length * TEXTURE_ELEMENTS_COUNT
        );

        int[] triangulatedIndices;
        if (doNotTriangulate) {
            triangulatedIndices = new int[(vertices.length * 3) / 2]; // v * 6

            for (int i = 0; i < vertices.length / 4; i++) {
                triangulatedIndices[(i * 6)] = i * 4;
                triangulatedIndices[(i * 6) + 1] = i * 4 + 1;
                triangulatedIndices[(i * 6) + 2] = i * 4 + 3;
                triangulatedIndices[(i * 6) + 3] = i * 4 + 1;
                triangulatedIndices[(i * 6) + 4] = i * 4 + 2;
                triangulatedIndices[(i * 6) + 5] = i * 4 + 3;
            }
        } else {
            triangulatedIndices = KGeometryUtils.getTriangulatedVerticesIndices(vertices);
        }
        IntBuffer indices = KBufferUtils.createIntBuffer(triangulatedIndices.length);
        indices.put(triangulatedIndices);

        for (int i = 0; i < vertices.length; i++) {
            KVector2f glPoint = KGeometryUtils.plainToGl(vertices[i], this.viewportSize);

            verticesBuffer
                .put(glPoint.x())
                .put(glPoint.y())
                .put(colors[i].normalized())
                .put(uvs[i].x())
                .put(uvs[i].y());
        }

        verticesBuffer.flip();
        indices.flip();

        int vao = this.gl.glGenVertexArrays();
        int vbo = this.gl.glGenBuffers();
        int ebo = this.gl.glGenBuffers();

        this.gl.glBindBuffer(KGl33.GL_ARRAY_BUFFER, vbo);
        this.gl.glBufferData(KGl33.GL_ARRAY_BUFFER, verticesBuffer, KGl33.GL_STATIC_DRAW);

        this.gl.glBindBuffer(KGl33.GL_ELEMENT_ARRAY_BUFFER, ebo);
        this.gl.glBufferData(KGl33.GL_ELEMENT_ARRAY_BUFFER, indices, KGl33.GL_STATIC_DRAW);

        this.gl.glBindBuffer(KGl33.GL_ARRAY_BUFFER, 0);
        this.gl.glBindBuffer(KGl33.GL_ELEMENT_ARRAY_BUFFER, 0);

        return new TextureInfo(
            tex,
            vao,
            vbo,
            ebo,
            triangulatedIndices.length
        );

    }

    private int createTexture(final KImage attachedImage, final KTexture texture) {
        int tex = this.gl.glGenTextures();
        this.gl.glBindTexture(KGl33.GL_TEXTURE_2D, tex);

        this.gl.glTexImage2D(
            KGl33.GL_TEXTURE_2D,
            0,
            KGl33.GL_RGBA,
            attachedImage.width(),
            attachedImage.height(),
            0,
            KGl33.GL_RGBA,
            KGl33.GL_UNSIGNED_BYTE,
            attachedImage.rawData()
        );

        this.applyTextureParameters(texture);

        return tex;
    }

    private void applyTextureParameters(final KTexture texture) {

        if (texture.minFilter().isMipmap()) {
            this.gl.glGenerateMipmap(
                KGl33.GL_TEXTURE_2D
            );
        }

        this.gl.glTexParameteri(
            KGl33.GL_TEXTURE_2D,
            KGl33.GL_TEXTURE_WRAP_S,
            this.getGlTextureWrapping(texture.uWrapping())
        );
        this.gl.glTexParameteri(
            KGl33.GL_TEXTURE_2D,
            KGl33.GL_TEXTURE_WRAP_T,
            this.getGlTextureWrapping(texture.vWrapping())
        );
        this.gl.glTexParameteri(
            KGl33.GL_TEXTURE_2D,
            KGl33.GL_TEXTURE_MIN_FILTER,
            this.getGlTextureFiltering(texture.minFilter())
        );
        this.gl.glTexParameteri(
            KGl33.GL_TEXTURE_2D,
            KGl33.GL_TEXTURE_MAG_FILTER,
            this.getGlTextureFiltering(texture.magFilter())
        );

    }

    private int getGlTextureWrapping(final KTextureWrapping wrapping) {
        return switch (wrapping) {
            case REPEAT ->  KGl33.GL_REPEAT;
            case CLAMP_TO_BORDER ->  KGl33.GL_CLAMP_TO_BORDER;
            case CLAMP_TO_EDGE ->  KGl33.GL_CLAMP_TO_EDGE;
            case MIRRORED_REPEAT ->  KGl33.GL_MIRRORED_REPEAT;
        };
    }

    private int getGlTextureFiltering(final KTextureFiltering filtering) {
        return switch (filtering) {
            case LINEAR -> KGl33.GL_LINEAR;
            case NEAREST -> KGl33.GL_NEAREST;
            case MIPMAP, MIPMAP_LINEAR_LINEAR -> KGl33.GL_LINEAR_MIPMAP_LINEAR;
            case MIPMAP_LINEAR_NEAREST -> KGl33.GL_LINEAR_MIPMAP_NEAREST;
            case MIPMAP_NEAREST_LINEAR -> KGl33.GL_NEAREST_MIPMAP_LINEAR;
            case MIPMAP_NEAREST_NEAREST -> KGl33.GL_NEAREST_MIPMAP_NEAREST;
        };
    }

}
