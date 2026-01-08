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

import io.github.darthakiranihil.konna.backend.lwjgl.stbimage.KStbImageLwjgl;
import io.github.darthakiranihil.konna.core.struct.KVector2f;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import io.github.darthakiranihil.konna.graphics.KColor;
import io.github.darthakiranihil.konna.graphics.asset.KAssetCollectionTestClass;
import io.github.darthakiranihil.konna.graphics.asset.KShaderCollection;
import io.github.darthakiranihil.konna.graphics.asset.KShaderProgramCollection;
import io.github.darthakiranihil.konna.graphics.asset.KTextureCollection;
import io.github.darthakiranihil.konna.graphics.impl.TestShaderCompiler;
import io.github.darthakiranihil.konna.graphics.stb.KStbImageLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KRenderableTexturePositiveTests extends KAssetCollectionTestClass {

    @Test
    public void testLoadTextureAndWrapIntoRectangle() {

        KTextureCollection tc = new KTextureCollection(
            this.assetLoader,
            new KStbImageLoader(
                new KStbImageLwjgl(),
                KStandardTestClass.context
            ),
            new KShaderProgramCollection(
                this.assetLoader,
                new KShaderCollection(
                    this.assetLoader,
                    KStandardTestClass.context,
                    new TestShaderCompiler()
                ),
                new TestShaderCompiler()
            )
        );
        KTexture tex = tc.getAsset("cute_image");

        KRenderableTexture rtex = KRenderableTexture.wrapIntoRectangle(
            new KVector2i(10, 10),
            tex
        );

        Assertions.assertEquals(tex, rtex.texture());
        KColor[] colors = new KColor[] {
            KColor.WHITE,
            KColor.WHITE,
            KColor.WHITE,
            KColor.WHITE,
        };

        KVector2i[] points = new KVector2i[] {
            new KVector2i(10, 10),
            new KVector2i(410, 10),
            new KVector2i(410, 517),
            new KVector2i(10, 517),
        };

        KVector2f[] uvs = new KVector2f[] {
            new KVector2f(0.0f, 0.0f),
            new KVector2f(1.0f, 0.0f),
            new KVector2f(1.0f, 1.0f),
            new KVector2f(0.0f, 1.0f),
        };

        KVector2i[] xy = rtex.xy();
        KVector2f[] uv = rtex.uv();
        KColor[] texColors = rtex.colors();

        Assertions.assertEquals(xy.length, uv.length, colors.length);
        for (int i = 0; i < xy.length; i++) {
            Assertions.assertEquals(xy[i], points[i]);
            Assertions.assertEquals(uv[i], uvs[i]);
            Assertions.assertEquals(texColors[i], colors[i]);
        }
    }

}
