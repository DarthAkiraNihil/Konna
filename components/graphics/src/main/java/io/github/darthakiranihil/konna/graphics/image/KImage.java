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

import io.github.darthakiranihil.konna.core.struct.KBufferUtils;
import io.github.darthakiranihil.konna.core.struct.KCopyable;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.graphics.KColor;

import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * Representation of a simple image.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
public class KImage implements KCopyable<KImage> {

    private static final int COLOR_COMPONENTS_COUNT = 4;
    private static final int RED_OFFSET = 0;
    private static final int GREEN_OFFSET = 1;
    private static final int BLUE_OFFSET = 2;
    private static final int ALPHA_OFFSET = 3;
    private static final int CHANNEL_LIMIT = 256;

    private final ByteBuffer rawImageData;
    private final int width;
    private final int height;

    /**
     * Standard constructor.
     * @param rawImageData Buffer of raw image data
     * @param width Image width
     * @param height Image height
     */
    public KImage(final ByteBuffer rawImageData, int width, int height) {
        this.rawImageData = rawImageData;
        this.width = width;
        this.height = height;
    }

    /**
     * Standard constructor, but raw image data is stored in a byte array.
     * @param rawImageData Array of raw image data
     * @param width Image width
     * @param height Image height
     */
    public KImage(final byte[] rawImageData, int width, int height) {
        this(KBufferUtils.wrapByteArrayToBuffer(rawImageData), width, height);
    }

    /**
     * Returns color of specific image pixel.
     * @param pixel Image pixel coordinates
     * @return Pixel's color
     */
    public KColor getPixelColor(final KVector2i pixel) {
        return this.getPixelColor(pixel.x(), pixel.y());
    }

    /**
     * Returns color of specific image pixel.
     * @param x X coordinate of a pixel
     * @param y Y coordinate of a pixel
     * @return Pixel's color
     */
    public KColor getPixelColor(int x, int y) {
        int offset = (y * this.width + x) * COLOR_COMPONENTS_COUNT;

        int r = this.rawImageData.get(offset + RED_OFFSET);
        int g = this.rawImageData.get(offset + GREEN_OFFSET);
        int b = this.rawImageData.get(offset + BLUE_OFFSET);
        int alpha = this.rawImageData.get(offset + ALPHA_OFFSET);


        return new KColor(
            r < 0 ? CHANNEL_LIMIT + r : r,
            g < 0 ? CHANNEL_LIMIT + g : g,
            b < 0 ? CHANNEL_LIMIT + b : b,
            alpha < 0 ? CHANNEL_LIMIT + alpha : alpha
        );
    }

    /**
     * Returns slice of the image.
     * @param from Top left corner of slicing beginning
     * @param size Size of the sliced image
     * @return Slice of this image
     */
    public KImage slice(final KVector2i from, final KSize size) {
        return this.slice(from.x(), from.y(), size.width(), size.height());
    }

    /**
     * Returns slice of the image.
     * @param x X coordinate of top left corner of slicing beginning
     * @param y Y coordinate of top left corner of slicing beginning
     * @param sliceWidth Width of the sliced image
     * @param sliceHeight Height of the sliced image
     * @return Slice of this image
     */
    public KImage slice(int x, int y, int sliceWidth, int sliceHeight) {
        int dataLength = sliceWidth * sliceHeight * COLOR_COMPONENTS_COUNT;
        byte[] cloned = new byte[dataLength];

        for (int h = 0; h < sliceHeight; h++) {
            int offset = ((y + h) * this.width + x) * COLOR_COMPONENTS_COUNT;
            this.rawImageData.position(offset);
            this.rawImageData.get(
                cloned,
                sliceWidth * COLOR_COMPONENTS_COUNT * h,
                sliceWidth * COLOR_COMPONENTS_COUNT
            );
        }

        this.rawImageData.position(0);
        return new KImage(cloned, sliceWidth, sliceHeight);
    }

    /**
     * Returns width of this image.
     * @return Width of this image
     */
    public int width() {
        return this.width;
    }

    /**
     * Returns height of this image.
     * @return Height of this image
     */
    public int height() {
        return this.height;
    }

    /**
     * Returns raw data of this image.
     * @return Raw data of this image
     */
    public ByteBuffer rawData() {
        return this.rawImageData;
    }

    @Override
    public KImage copy() {
        ByteBuffer copied = KBufferUtils.createByteBuffer(
            this.width * this.height * COLOR_COMPONENTS_COUNT
        );
        copied.put(this.rawImageData);
        return new KImage(copied, this.width, this.height);
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        KImage kImage = (KImage) o;
        return
                this.width == kImage.width
            &&  this.height == kImage.height
            &&  Objects.equals(this.rawImageData, kImage.rawImageData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.rawImageData, this.width, this.height);
    }

    // todo: make a filter
    //    public KImage applyShaders(final KShader[] shaders) {
    //        KImage copy = this.copy();
    //        //        for (KShader shader: shaders) {
    //        //            copy = shader.apply(copy);
    //        //        }
    //        return copy;
    //    }

}
