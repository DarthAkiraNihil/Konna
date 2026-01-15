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
import io.github.darthakiranihil.konna.core.io.except.KAssetLoadingException;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import io.github.darthakiranihil.konna.graphics.impl.TestShaderCompiler;
import io.github.darthakiranihil.konna.graphics.stb.KStbImageLoader;
import io.github.darthakiranihil.konna.graphics.text.KTiledFont;
import io.github.darthakiranihil.konna.graphics.text.std.KSquaredAsciiTiledFontFormat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KTiledFontCollectionPositiveTests extends KAssetCollectionTestClass {

    @Test
    public void testGetTiledFontSuccess() {

        KTiledFontCollection tfc = new KTiledFontCollection(
            this.assetLoader,
            new KTextureCollection(
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
            ),
            KStandardTestClass.context
        );

        KTiledFont font = tfc.getAsset("cp437");

        Assertions.assertEquals("CP437", font.name());
        Assertions.assertEquals(
            KSquaredAsciiTiledFontFormat.class,
            font.format().getClass()
        );
        Assertions.assertEquals(
            KSize.squared(16),
            font.glyphSize()
        );

        KTiledFont f2 = tfc.getAsset("cp437");
        Assertions.assertEquals(f2, font);

    }

}
