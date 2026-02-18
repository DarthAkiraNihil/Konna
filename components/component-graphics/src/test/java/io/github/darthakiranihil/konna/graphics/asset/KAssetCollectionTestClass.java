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

import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonParser;
import io.github.darthakiranihil.konna.core.data.json.std.KStandardJsonTokenizer;
import io.github.darthakiranihil.konna.core.io.KAssetLoader;
import io.github.darthakiranihil.konna.core.io.std.KJsonSubtypeBasedAssetLoader;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import io.github.darthakiranihil.konna.graphics.type.KShaderProgramTypedef;
import io.github.darthakiranihil.konna.graphics.type.KShaderTypedef;
import io.github.darthakiranihil.konna.graphics.type.KTextureTypedef;
import io.github.darthakiranihil.konna.graphics.type.KTiledFontTypedef;

import java.util.Map;

public class KAssetCollectionTestClass extends KStandardTestClass {

    protected final KAssetLoader assetLoader;

    protected KAssetCollectionTestClass() {
        super();

        this.assetLoader = new KJsonSubtypeBasedAssetLoader(
            KStandardTestClass.context,
            Map.of("shader", new KJsonSubtypeBasedAssetLoader.AssetTypeData(
                new String[] { KShaderTypedef.SHADER_ASSET_TYPE },
                new String[] {"classpath:assets/shaders.json"}
            ), "shaderProgram", new KJsonSubtypeBasedAssetLoader.AssetTypeData(
                new String[] { KShaderProgramTypedef.SHADER_PROGRAM_ASSET_TYPE},
                new String[] {"classpath:assets/shader_programs.json"}
            ), "texture", new KJsonSubtypeBasedAssetLoader.AssetTypeData(
                new String[] { KTextureTypedef.TEXTURE_ASSET_TYPE},
                new String[] {"classpath:assets/textures.json"}
            ),
            "tiledFont", new KJsonSubtypeBasedAssetLoader.AssetTypeData(
                new String[] { KTiledFontTypedef.TILED_FONT_ASSET_TYPE},
                new String[] {"classpath:assets/tiled_fonts.json"}
            )),
            new KStandardJsonParser(new KStandardJsonTokenizer())
        );

        this.assetLoader.addAssetTypedef(new KShaderTypedef());
        this.assetLoader.addAssetTypedef(new KShaderProgramTypedef());
        this.assetLoader.addAssetTypedef(new KTextureTypedef());
        this.assetLoader.addAssetTypedef(new KTiledFontTypedef());
    }
}
