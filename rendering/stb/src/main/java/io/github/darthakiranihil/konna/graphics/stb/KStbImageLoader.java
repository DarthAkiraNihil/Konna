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

package io.github.darthakiranihil.konna.graphics.stb;

import io.github.darthakiranihil.konna.core.di.KInject;
import io.github.darthakiranihil.konna.core.io.KResource;
import io.github.darthakiranihil.konna.core.io.KResourceLoader;
import io.github.darthakiranihil.konna.core.io.except.KIoException;
import io.github.darthakiranihil.konna.core.object.KObject;
import io.github.darthakiranihil.konna.core.object.KSingleton;
import io.github.darthakiranihil.konna.core.struct.KBufferUtils;
import io.github.darthakiranihil.konna.graphics.image.KImage;
import io.github.darthakiranihil.konna.graphics.image.KImageLoader;
import io.github.darthakiranihil.konna.libfrontend.stb.KStbImage;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * Implementation of {@link KImageLoader} that uses STBImage
 * to load and process image resources.
 *
 * @since 0.1.0
 * @author Darth Akira Nihil
 */
@KSingleton
public class KStbImageLoader extends KObject implements KImageLoader {

    private final KStbImage stbImage;
    private final KResourceLoader resourceLoader;
    private static final int DESIRED_CHANNELS = 4;

    /**
     * Constructs image loader with provided STBImage frontend.
     * @param stbImage STBImage frontend
     * @param resourceLoader Resource loader
     */
    public KStbImageLoader(
        @KInject final KStbImage stbImage,
        @KInject final KResourceLoader resourceLoader
    ) {
        this.stbImage = stbImage;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public KImage load(final String filename) {
        IntBuffer width = KBufferUtils.createIntBuffer(1);
        IntBuffer height = KBufferUtils.createIntBuffer(1);
        IntBuffer channels = KBufferUtils.createIntBuffer(1);

        try (KResource resource = this.resourceLoader.loadResource(filename)) {

            if (!resource.exists()) {
                throw new KIoException(
                    String.format(
                        "Image %s does not exist", filename
                    )
                );
            }

            byte[] sourceBytes = resource.bytes();
            ByteBuffer src = KBufferUtils.createByteBuffer(sourceBytes.length);
            src.put(sourceBytes);
            src.flip();

            ByteBuffer loaded = this.stbImage.stbi_load_from_memory(
                src, width, height, channels, DESIRED_CHANNELS
            );
            if (loaded == null) {
                throw new KIoException(
                    String.format(
                        "Image %s failed to load", filename
                    )
                );
            }

            return new KImage(loaded, width.get(), height.get());

        } catch (IOException e) {
            throw new KIoException(e);
        }

    }

}
