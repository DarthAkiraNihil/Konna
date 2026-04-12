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

package io.github.darthakiranihil.konna.graphics.asset;

import io.github.darthakiranihil.konna.backend.lwjgl.stbimage.KStbImageLwjgl;
import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.graphics.KColor;
import io.github.darthakiranihil.konna.graphics.image.KRenderableTexture;
import io.github.darthakiranihil.konna.graphics.image.KTextureSliceSet;
import io.github.darthakiranihil.konna.graphics.impl.TestShaderCompiler;
import io.github.darthakiranihil.konna.graphics.stb.KStbImageLoader;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KTextureSliceSetCollectionPositiveTests extends KAssetCollectionTestClass {

    @Test
    public void testGetSetSuccessBasic() {

        KTextureSliceSetCollection collection = new KTextureSliceSetCollection(
            this.assetLoader,
            new KTextureCollection(
                this.assetLoader,
                new KStbImageLoader(
                    new KStbImageLwjgl(),
                    this.engineModule.resourceLoader()
                ),
                new KShaderProgramCollection(
                    this.assetLoader,
                    new KShaderCollection(
                        this.assetLoader,
                        this.engineModule.resourceLoader(),
                        new TestShaderCompiler()
                    ),
                    new TestShaderCompiler()
                )
            )
        );

        KTextureSliceSet set = collection.getAsset("set_1");
        collection.getAsset("set_1");

        KRenderableTexture tex = set.getTexture("sl1", 1);
        Assertions.assertEquals(1, tex.getUnit());

        KVector2i[] xy = tex.xy();
        KVector2f[] uv = tex.uv();
        KColor[] colors = tex.colors();

        Assertions.assertEquals(4, xy.length);
        Assertions.assertEquals(4, uv.length);
        Assertions.assertEquals(4, colors.length);

        Assertions.assertArrayEquals(
            new KVector2i[] {
                new KVector2i(0, 0),
                new KVector2i(16, 0),
                new KVector2i(16, 16),
                new KVector2i(0, 16)
            },
            xy
        );

        Assertions.assertArrayEquals(
            new KVector2f[] {
                new KVector2f(0.0f, 0.0f),
                new KVector2f(1.0f, 0.0f),
                new KVector2f(0.5f, 0.5f),
                new KVector2f(0.0f, 0.5f)
            },
            uv
        );

        Assertions.assertArrayEquals(
            new KColor[] {
                new KColor(255, 255, 255),
                new KColor(255, 240, 255),
                new KColor(255, 210, 255),
                new KColor(255, 195, 255),
            },
            colors
        );

    }

    @Test
    public void testGetSetSuccessWithTopLeftCorner() {

        KTextureSliceSetCollection collection = new KTextureSliceSetCollection(
            this.assetLoader,
            new KTextureCollection(
                this.assetLoader,
                new KStbImageLoader(
                    new KStbImageLwjgl(),
                    this.engineModule.resourceLoader()
                ),
                new KShaderProgramCollection(
                    this.assetLoader,
                    new KShaderCollection(
                        this.assetLoader,
                        this.engineModule.resourceLoader(),
                        new TestShaderCompiler()
                    ),
                    new TestShaderCompiler()
                )
            )
        );

        KTextureSliceSet set = collection.getAsset("set_1");
        collection.getAsset("set_1");

        KRenderableTexture tex = set.getTexture("sl1", new KVector2i(400, 400), 1);
        Assertions.assertEquals(1, tex.getUnit());

        KVector2i[] xy = tex.xy();
        KVector2f[] uv = tex.uv();
        KColor[] colors = tex.colors();

        Assertions.assertEquals(4, xy.length);
        Assertions.assertEquals(4, uv.length);
        Assertions.assertEquals(4, colors.length);

        Assertions.assertArrayEquals(
            new KVector2i[] {
                new KVector2i(400, 400),
                new KVector2i(416, 400),
                new KVector2i(416, 416),
                new KVector2i(400, 416)
            },
            xy
        );

        Assertions.assertArrayEquals(
            new KVector2f[] {
                new KVector2f(0.0f, 0.0f),
                new KVector2f(1.0f, 0.0f),
                new KVector2f(0.5f, 0.5f),
                new KVector2f(0.0f, 0.5f)
            },
            uv
        );

        Assertions.assertArrayEquals(
            new KColor[] {
                new KColor(255, 255, 255),
                new KColor(255, 240, 255),
                new KColor(255, 210, 255),
                new KColor(255, 195, 255),
            },
            colors
        );

    }

}
