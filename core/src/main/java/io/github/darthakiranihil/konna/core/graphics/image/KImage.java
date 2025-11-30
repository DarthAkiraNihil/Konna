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

package io.github.darthakiranihil.konna.core.graphics.image;

import io.github.darthakiranihil.konna.core.graphics.shader.KShader;
import io.github.darthakiranihil.konna.core.graphics.shape.KColor;
import io.github.darthakiranihil.konna.core.struct.KBufferUtils;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.util.KCopyable;

import java.nio.ByteBuffer;

public class KImage implements KCopyable<KImage> {

    private static final int COLOR_COMPONENTS_COUNT = 4;
    private static final int RED_OFFSET = 0;
    private static final int GREEN_OFFSET = 1;
    private static final int BLUE_OFFSET = 2;
    private static final int ALPHA_OFFSET = 3;

    private final ByteBuffer rawImageData;
    private final int width;
    private final int height;

    public KImage(final ByteBuffer rawImageData, int width, int height) {
        this.rawImageData = rawImageData;
        this.width = width;
        this.height = height;
    }

    public KImage(final byte[] rawImageData, int width, int height) {
        this(KBufferUtils.wrapByteArrayToBuffer(rawImageData), width, height);
    }

    public KColor getPixelColor(KVector2i pixel) {
        return this.getPixelColor(pixel.x(), pixel.y());
    }

    public KColor getPixelColor(int x, int y) {
        int offset = (y * this.width + x) * COLOR_COMPONENTS_COUNT;

        return new KColor(
            this.rawImageData.get(offset + RED_OFFSET),
            this.rawImageData.get(offset + GREEN_OFFSET),
            this.rawImageData.get(offset + BLUE_OFFSET),
            this.rawImageData.get(offset + ALPHA_OFFSET)
        );
    }

    public KImage slice(final KVector2i from, final KSize size) {
        return this.slice(from.x(), from.y(), size.width(), size.height());
    }

    public KImage slice(int x, int y, int width, int height) {
        int dataLength = width * height * COLOR_COMPONENTS_COUNT;
        int offset = (y * this.width + x) * COLOR_COMPONENTS_COUNT;

        byte[] cloned = new byte[dataLength];
        this.rawImageData.get(cloned, offset, dataLength);
        return new KImage(cloned, width, height);
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    public ByteBuffer rawData() {
        return this.rawImageData;
    }

    @Override
    public KImage copy() {
        ByteBuffer copied = KBufferUtils.createByteBuffer(this.width * this.height * COLOR_COMPONENTS_COUNT);
        copied.put(this.rawImageData);
        return new KImage(copied, this.width, this.height);
    }

    // todo: make a filter
    public KImage applyShaders(final KShader[] shaders) {
        KImage copy = this.copy();
//        for (KShader shader: shaders) {
//            copy = shader.apply(copy);
//        }
        return copy;
    }

}
