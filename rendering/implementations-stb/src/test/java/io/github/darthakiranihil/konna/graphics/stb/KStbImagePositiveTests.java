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

import io.github.darthakiranihil.konna.backend.lwjgl.stbimage.KStbImageLwjgl;
import io.github.darthakiranihil.konna.core.io.std.KStandardResourceLoader;
import io.github.darthakiranihil.konna.core.io.std.protocol.KClasspathProtocol;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import io.github.darthakiranihil.konna.graphics.KColor;
import io.github.darthakiranihil.konna.graphics.image.KImage;
import io.github.darthakiranihil.konna.graphics.image.KImageLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.util.List;

public class KStbImagePositiveTests extends KStandardTestClass {

    @Test
    public void testLoadImageSuccess() {

        KImageLoader imageLoader = new KStbImageLoader(
            new KStbImageLwjgl(),
            new KStandardResourceLoader(
                List.of(
                    new KClasspathProtocol(
                        ClassLoader.getSystemClassLoader()
                    )
                )
            )
        );

        KImage image = imageLoader.load("classpath:test_image.png");

        Assertions.assertEquals(8, image.width());
        Assertions.assertEquals(8, image.height());

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Assertions.assertEquals(KColor.RED, image.getPixelColor(new KVector2i(i, j)));
            }
        }

        for (int i = 4; i < 7; i++) {
            for (int j = 0; j < 4; j++) {
                Assertions.assertEquals(KColor.GREEN, image.getPixelColor(new KVector2i(i, j)));
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 4; j < 7; j++) {
                Assertions.assertEquals(KColor.BLUE, image.getPixelColor(i, j));
            }
        }

        for (int i = 4; i < 7; i++) {
            for (int j = 4; j < 7; j++) {
                Assertions.assertEquals(KColor.WHITE, image.getPixelColor(i, j));
            }
        }

        KImage slice = image.slice(0, 0, 4, 4);
        Assertions.assertEquals(4, slice.width());
        Assertions.assertEquals(4, slice.height());
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Assertions.assertEquals(KColor.RED, slice.getPixelColor(new KVector2i(i, j)));
            }
        }

        KImage slice2 = image.slice(new KVector2i(4, 4), KSize.squared(4));
        Assertions.assertEquals(4, slice2.width());
        Assertions.assertEquals(4, slice2.height());
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Assertions.assertEquals(KColor.WHITE, slice2.getPixelColor(new KVector2i(i, j)));
            }
        }

    }
}
