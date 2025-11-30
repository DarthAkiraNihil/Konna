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

package io.github.darthakiranihil.konna.libfrontend.stbimage;

import io.github.darthakiranihil.konna.core.except.KUnknownException;
import io.github.darthakiranihil.konna.core.graphics.image.KImage;
import io.github.darthakiranihil.konna.core.graphics.image.KImageLoader;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.struct.KBufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class KStbImageLoader extends KObject implements KImageLoader {

    private final KStbImage stbImage;

    public KStbImageLoader(KStbImage stbImage) {
        this.stbImage = stbImage;
    }

    @Override
    public KImage load(String filename) {
        IntBuffer width = KBufferUtils.createIntBuffer(1);
        IntBuffer height = KBufferUtils.createIntBuffer(1);
        IntBuffer channels = KBufferUtils.createIntBuffer(1);

        try {
            var res = ClassLoader.getSystemClassLoader().getResourceAsStream(filename).readAllBytes();
            ByteBuffer src = KBufferUtils.createByteBuffer(res.length);
            src.put(res);
            src.flip();
            ByteBuffer loaded = this.stbImage.stbi_load_from_memory(src, width, height, channels, 0);
            return new KImage(loaded, width.get(), height.get());
        } catch (Throwable e) {
            throw new KUnknownException(e);
        }

    }
}
