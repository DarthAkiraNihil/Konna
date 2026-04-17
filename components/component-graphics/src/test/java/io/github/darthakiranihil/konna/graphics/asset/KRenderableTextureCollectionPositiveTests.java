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
import io.github.darthakiranihil.konna.graphics.impl.TestShaderCompiler;
import io.github.darthakiranihil.konna.graphics.stb.KStbImageLoader;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KRenderableTextureCollectionPositiveTests extends KAssetCollectionTestClass {

    @Test
    public void testGetTextureSuccess() {

        KTextureCollection tesco = new KTextureCollection(
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
        );

        KTextureSliceSetCollection textureSliceSetCollection = new KTextureSliceSetCollection(
            this.assetLoader,
            tesco
        );

        KRenderableTextureCollection collection = new KRenderableTextureCollection(
            this.assetLoader,
            tesco,
            textureSliceSetCollection
        );

        KRenderableTexture tex = collection.getAsset("sl1");

        Assertions.assertArrayEquals(
            new KVector2i[] {
                new KVector2i(0, 0),
                new KVector2i(16, 0),
                new KVector2i(16, 16),
                new KVector2i(0, 16)
            },
            tex.xy()
        );
        Assertions.assertArrayEquals(
            new KVector2f[] {
                new KVector2f(0.0f, 0.0f),
                new KVector2f(1.0f, 0.0f),
                new KVector2f(0.5f, 0.5f),
                new KVector2f(0.0f, 0.5f)
            },
            tex.uv()
        );

        Assertions.assertArrayEquals(
            new KColor[] {
                new KColor(255, 255, 255),
                new KColor(255, 240, 255),
                new KColor(255, 210, 255),
                new KColor(255, 195, 255),
            },
            tex.colors()
        );

    }

    @Test
    public void testGetTextureSuccessWithTopLeftCorner() {

        KTextureCollection tesco = new KTextureCollection(
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
        );

        KTextureSliceSetCollection textureSliceSetCollection = new KTextureSliceSetCollection(
            this.assetLoader,
            tesco
        );

        KRenderableTextureCollection collection = new KRenderableTextureCollection(
            this.assetLoader,
            tesco,
            textureSliceSetCollection
        );

        KRenderableTexture tex = collection.getAsset("sl1", new KVector2i(400, 400));

        Assertions.assertArrayEquals(
            new KVector2i[] {
                new KVector2i(400, 400),
                new KVector2i(416, 400),
                new KVector2i(416, 416),
                new KVector2i(400, 416)
            },
            tex.xy()
        );
        Assertions.assertArrayEquals(
            new KVector2f[] {
                new KVector2f(0.0f, 0.0f),
                new KVector2f(1.0f, 0.0f),
                new KVector2f(0.5f, 0.5f),
                new KVector2f(0.0f, 0.5f)
            },
            tex.uv()
        );

        Assertions.assertArrayEquals(
            new KColor[] {
                new KColor(255, 255, 255),
                new KColor(255, 240, 255),
                new KColor(255, 210, 255),
                new KColor(255, 195, 255),
            },
            tex.colors()
        );

    }

    @Test
    public void testGetWholeTexture() {

        KTextureCollection tesco = new KTextureCollection(
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
        );

        KTextureSliceSetCollection textureSliceSetCollection = new KTextureSliceSetCollection(
            this.assetLoader,
            tesco
        );

        KRenderableTextureCollection collection = new KRenderableTextureCollection(
            this.assetLoader,
            tesco,
            textureSliceSetCollection
        );

        KRenderableTexture tex = collection.getAsset("sl2");

        Assertions.assertArrayEquals(
            new KVector2i[] {
                new KVector2i(0, 0),
                new KVector2i(400, 0),
                new KVector2i(400, 507),
                new KVector2i(0, 507)
            },
            tex.xy()
        );
        Assertions.assertArrayEquals(
            new KVector2f[] {
                new KVector2f(0.0f, 0.0f),
                new KVector2f(1.0f, 0.0f),
                new KVector2f(1.0f, 1.0f),
                new KVector2f(0.0f, 1.0f)
            },
            tex.uv()
        );

        Assertions.assertArrayEquals(
            new KColor[] {
                new KColor(255, 255, 255),
                new KColor(255, 255, 255),
                new KColor(255, 255, 255),
                new KColor(255, 255, 255),
            },
            tex.colors()
        );

    }

    @Test
    public void testGetWholeTextureWithTopLeftCorner() {

        KTextureCollection tesco = new KTextureCollection(
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
        );

        KTextureSliceSetCollection textureSliceSetCollection = new KTextureSliceSetCollection(
            this.assetLoader,
            tesco
        );

        KRenderableTextureCollection collection = new KRenderableTextureCollection(
            this.assetLoader,
            tesco,
            textureSliceSetCollection
        );

        KRenderableTexture tex = collection.getAsset("sl2", new KVector2i(400, 400));

        Assertions.assertArrayEquals(
            new KVector2i[] {
                new KVector2i(400, 400),
                new KVector2i(800, 400),
                new KVector2i(800, 907),
                new KVector2i(400, 907)
            },
            tex.xy()
        );
        Assertions.assertArrayEquals(
            new KVector2f[] {
                new KVector2f(0.0f, 0.0f),
                new KVector2f(1.0f, 0.0f),
                new KVector2f(1.0f, 1.0f),
                new KVector2f(0.0f, 1.0f)
            },
            tex.uv()
        );

        Assertions.assertArrayEquals(
            new KColor[] {
                new KColor(255, 255, 255),
                new KColor(255, 255, 255),
                new KColor(255, 255, 255),
                new KColor(255, 255, 255),
            },
            tex.colors()
        );

    }
}
