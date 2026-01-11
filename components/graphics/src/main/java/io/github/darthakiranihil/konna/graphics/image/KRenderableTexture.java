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

package io.github.darthakiranihil.konna.graphics.image;

import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.core.test.KExcludeFromGeneratedCoverageReport;
import io.github.darthakiranihil.konna.graphics.KColor;
import io.github.darthakiranihil.konna.graphics.render.KRenderFrontend;
import io.github.darthakiranihil.konna.graphics.shape.KAbstractShape;

import java.util.Arrays;
import java.util.Objects;

/**
 * Representation of a texture that can be rendered on screen that does
 * not depend on used graphics library.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public class KRenderableTexture extends KAbstractShape {

    private static final KVector2f[] DEFAULT_UV = new KVector2f[] {
        new KVector2f(0.0f, 0.0f),
        new KVector2f(1.0f, 0.0f),
        new KVector2f(1.0f, 1.0f),
        new KVector2f(0.0f, 1.0f),
    };

    private static final KColor[] ALL_WHITES = new KColor[] {
        KColor.WHITE,
        KColor.WHITE,
        KColor.WHITE,
        KColor.WHITE,
    };

    private final KVector2f[] uv;
    private final KVector2i[] xy;
    private final KColor[] colors;
    private final KTexture texture;

    /**
     * Does the same job as
     * {@link KRenderableTexture#wrapIntoRectangle(KVector2i, KTexture, KColor[])},
     * but all vertices colors are {@link KColor#WHITE}.
     * @param leftTopCorner Left top corner coordinate of texture
     * @param texture Attached texture data
     * @return Wrapped renderable texture of full image
     */
    public static KRenderableTexture wrapIntoRectangle(
        final KVector2i leftTopCorner,
        final KTexture texture
    ) {

        return KRenderableTexture.wrapIntoRectangle(leftTopCorner, texture, ALL_WHITES);

    }

    /**
     * Wraps texture data into that renderable texture that has full size as its source
     * image, without rotation with specified place of origin and vertices colors.
     * @param leftTopCorner Left top corner coordinate of texture
     * @param texture Attached texture data
     * @param colors Vertices colors
     * @return Wrapped renderable texture of full image
     */
    public static KRenderableTexture wrapIntoRectangle(
        final KVector2i leftTopCorner,
        final KTexture texture,
        final KColor[] colors
    ) {

        KImage sourceImage = texture.attachedImage();
        KVector2i[] xy = new KVector2i[] {
            new KVector2i(
                leftTopCorner.x(),
                leftTopCorner.y()
            ),
            new KVector2i(
                leftTopCorner.x() + sourceImage.width(),
                leftTopCorner.y()
            ),
            new KVector2i(
                leftTopCorner.x() + sourceImage.width(),
                leftTopCorner.y() + sourceImage.height()
            ),
            new KVector2i(
                leftTopCorner.x(),
                leftTopCorner.y() + sourceImage.height()
            ),
        };

        return new KRenderableTexture(
            DEFAULT_UV,
            xy,
            colors,
            texture
        );

    }

    /**
     * Standard constructor.
     * @param uv UV coordinates of this texture
     * @param xy Screen coordinates of texture vertices
     * @param colors Color of texture vertices
     * @param texture Attached texture data
     */
    public KRenderableTexture(
        final KVector2f[] uv,
        final KVector2i[] xy,
        final KColor[] colors,
        final KTexture texture
    ) {
        super(KAbstractShape.centroidOfPoints(xy));
        this.uv = uv;
        this.xy = xy;
        this.colors = colors;
        this.texture = texture;
    }

    /**
     * Returns vertices screen coordinates for this texture.
     * @return Screen coordinates of vertices of this texture.
     */
    public KVector2i[] xy() {
        return this.xy;
    }

    /**
     * Returns UV coordinates for this texture.
     * @return UV coordinates for this texture
     */
    public KVector2f[] uv() {
        return this.uv;
    }

    /**
     * Returns vertices' colors of this texture.
     * @return Vertices' colors of this texture
     */
    public KColor[] colors() {
        return this.colors;
    }

    /**
     * Returns texture data for this renderable texture.
     * @return Texture data of this renderable texture
     */
    public KTexture texture() {
        return this.texture;
    }

    @Override
    public void render(final KRenderFrontend rf) {
        rf.render(this);
    }

    @Override
    @KExcludeFromGeneratedCoverageReport
    public boolean equals(final Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        KRenderableTexture that = (KRenderableTexture) o;
        return
                Objects.deepEquals(this.uv, that.uv)
            &&  Objects.deepEquals(this.xy, that.xy)
            &&  Objects.equals(this.texture, that.texture);
    }

    @Override
    @KExcludeFromGeneratedCoverageReport
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(this.uv), Arrays.hashCode(this.xy), this.texture);
    }
}
