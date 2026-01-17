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

import io.github.darthakiranihil.konna.backend.lwjgl.stb.KStbImageLwjgl;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import io.github.darthakiranihil.konna.graphics.image.KImage;
import io.github.darthakiranihil.konna.graphics.image.KTexture;
import io.github.darthakiranihil.konna.graphics.image.KTextureFiltering;
import io.github.darthakiranihil.konna.graphics.image.KTextureWrapping;
import io.github.darthakiranihil.konna.graphics.impl.TestShaderCompiler;
import io.github.darthakiranihil.konna.graphics.stb.KStbImageLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KTextureCollectionPositiveTests extends KAssetCollectionTestClass {

    @Test
    public void testGetTextureSuccess() {

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
        KTexture tex2 = tc.getAsset("cute_image");

        KImage texImg = tex.attachedImage();
        Assertions.assertEquals(400, texImg.width());
        Assertions.assertEquals(507, texImg.height());
        Assertions.assertEquals(tex2, tex);
        Assertions.assertEquals(KTextureFiltering.MIPMAP_LINEAR_LINEAR, tex.minFilter());
        Assertions.assertEquals(KTextureFiltering.LINEAR, tex.magFilter());
        Assertions.assertEquals(KTextureWrapping.REPEAT, tex.uWrapping());
        Assertions.assertEquals(KTextureWrapping.REPEAT, tex.vWrapping());

    }

}
