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
import io.github.darthakiranihil.konna.graphics.image.KImage;
import io.github.darthakiranihil.konna.graphics.image.KRenderableTexture;
import io.github.darthakiranihil.konna.graphics.image.KTexture;
import io.github.darthakiranihil.konna.libfrontend.opengl.KGl33;
import org.jetbrains.annotations.ApiStatus;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApiStatus.Internal
@KExcludeFromGeneratedCoverageReport
final class KTextureMaker {

    public static final int TEXTURE_ELEMENTS_COUNT = 8;
    public static final int POINT_ATTRIBUTE_ELEMENTS_COUNT = 2;
    public static final int COLOR_ATTRIBUTE_ELEMENTS_COUNT = 4;
    public static final int UV_ATTRIBUTE_ELEMENTS_COUNT = 2;

    public record TextureInfo(
        int id,
        int vao,
        int vbo,
        int ebo
    ) {

        public void free(final KGl33 gl) {
            gl.glDeleteBuffers(vao);
            gl.glDeleteBuffers(vbo);
            gl.glDeleteBuffers(ebo);
            gl.glDeleteTextures(id);
        }

    }

    private final Map<Integer, Integer> textureCache;
    private final Map<Integer, TextureInfo> cache;
    private final Map<Integer, Integer> ttl;
    private final KGl33 gl;
    private KSize viewportSize;

    public KTextureMaker(final KGl33 gl) {
        this.gl = gl;
        this.viewportSize = KSize.squared(KGl33RenderFrontend.DEFAULT_VIEWPORT_SIZE_SIDE);

        this.cache = new ConcurrentHashMap<>();
        this.ttl = new ConcurrentHashMap<>();
        this.textureCache = new ConcurrentHashMap<>();
    }

    public void setViewportSize(final KSize newSize) {
        this.viewportSize = newSize;
    }


    public TextureInfo make(KRenderableTexture texture) {

        int hash = KRenderableHasher.hash(texture);

        if (!this.cache.containsKey(hash)) {

            this.cache.put(
                hash,
                this.createTextureInfo(texture)
            );

        }

        this.ttl.put(hash, KInternals.DEFAULT_TTL);
        return this.cache.get(hash);

    }

    public void updateTtl() {

        this.ttl.replaceAll((k, v) -> v - 1);

        var entrySet = this.ttl.entrySet();
        for (var entry: entrySet) {
            if (entry.getValue() > 0) {
                continue;
            }

            var key = entry.getKey();
            TextureInfo info = this.cache.get(key);

            info.free(this.gl);
            this.cache.remove(key);
            this.ttl.remove(key);

        }

    }

    private TextureInfo createTextureInfo(final KRenderableTexture texture) {

        KTexture sourceTexture = texture.texture();

        KImage attachedImage = sourceTexture.getAttachedImage();
        int imageHash = attachedImage.hashCode();

        int tex;
        if (this.textureCache.containsKey(imageHash)) {
            tex = this.textureCache.get(imageHash);
            this.gl.glActiveTexture(KGl33.GL_TEXTURE0);
            this.gl.glBindTexture(KGl33.GL_TEXTURE_2D, tex);
        } else {
            tex = this.createTexture(attachedImage);
        }

        KVector2i[] vertices = texture.xy();
        KVector2f[] uvs = texture.uv();

        FloatBuffer verticesBuffer = KBufferUtils.createFloatBuffer(vertices.length * TEXTURE_ELEMENTS_COUNT);
        IntBuffer indices = KBufferUtils.createIntBuffer(6);
        // TODO: triangulation (fuck)
        indices.put(0).put(1).put(3).put(1).put(2).put(3);

        for (int i = 0; i < vertices.length; i++) {
            KVector2f glPoint = this.plainToGl(vertices[i]);

            verticesBuffer
                .put(glPoint.x())
                .put(glPoint.y())
                .put(sourceTexture.color().normalized())
                .put(uvs[i].x())
                .put(uvs[i].y());
            //indices.put(i);
            //indices.put(i);

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
            ebo
        );

    }

    private int createTexture(final KImage attachedImage) {
        int tex = this.gl.glGenTextures();

        this.gl.glActiveTexture(KGl33.GL_TEXTURE0);
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

        this.gl.glGenerateMipmap(KGl33.GL_TEXTURE_2D);
        this.gl.glTexParameteri(KGl33.GL_TEXTURE_2D, KGl33.GL_TEXTURE_WRAP_S, KGl33.GL_REPEAT);	// set texture wrapping to GL_REPEAT (default wrapping method)
        this.gl.glTexParameteri(KGl33.GL_TEXTURE_2D, KGl33.GL_TEXTURE_WRAP_T, KGl33.GL_REPEAT);
        // set texture filtering parameters
        this.gl.glTexParameteri(KGl33.GL_TEXTURE_2D, KGl33.GL_TEXTURE_MIN_FILTER, KGl33.GL_LINEAR_MIPMAP_LINEAR);
        this.gl.glTexParameteri(KGl33.GL_TEXTURE_2D, KGl33.GL_TEXTURE_MAG_FILTER, KGl33.GL_LINEAR);

        return tex;
    }

    private KVector2f plainToGl(final KVector2i v) {
        float x = 2.0f * ((float) v.x() / this.viewportSize.width()) - 1.0f;
        float y = -2.0f * ((float) v.y() / this.viewportSize.height()) + 1.0f;

        return new KVector2f(x, y);
    }
}
