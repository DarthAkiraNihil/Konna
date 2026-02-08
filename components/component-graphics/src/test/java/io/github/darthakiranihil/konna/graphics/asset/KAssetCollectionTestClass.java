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
import io.github.darthakiranihil.konna.core.io.std.KJsonAssetLoader;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import io.github.darthakiranihil.konna.graphics.type.KShaderProgramTypeDefinition;
import io.github.darthakiranihil.konna.graphics.type.KShaderTypeDefinition;
import io.github.darthakiranihil.konna.graphics.type.KTextureTypeDefinition;
import io.github.darthakiranihil.konna.graphics.type.KTiledFontTypeDefinition;

import java.util.Map;

public class KAssetCollectionTestClass extends KStandardTestClass {

    protected final KAssetLoader assetLoader;

    protected KAssetCollectionTestClass() {
        super();

        this.assetLoader = new KJsonAssetLoader(
            KStandardTestClass.context,
            Map.of("shader", new KJsonAssetLoader.AssetTypeData(
                new String[] {KShaderCollection.SHADER_ASSET_TYPE },
                new String[] {"classpath:assets/shaders.json"}
            ), "shaderProgram", new KJsonAssetLoader.AssetTypeData(
                new String[] {KShaderProgramCollection.SHADER_PROGRAM_ASSET_TYPE},
                new String[] {"classpath:assets/shader_programs.json"}
            ), "texture", new KJsonAssetLoader.AssetTypeData(
                new String[] {KTextureCollection.TEXTURE_ASSET_TYPE},
                new String[] {"classpath:assets/textures.json"}
            ),
            "tiledFont", new KJsonAssetLoader.AssetTypeData(
                new String[] {KTiledFontCollection.TILED_FONT_ASSET_TYPE},
                new String[] {"classpath:assets/tiled_fonts.json"}
            )),
            new KStandardJsonParser(new KStandardJsonTokenizer())
        );

        this.assetLoader.addAssetTypedef(new KShaderTypeDefinition());
        this.assetLoader.addAssetTypedef(new KShaderProgramTypeDefinition());
        this.assetLoader.addAssetTypedef(new KTextureTypeDefinition());
        this.assetLoader.addAssetTypedef(new KTiledFontTypeDefinition());
    }
}
