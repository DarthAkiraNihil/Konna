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

package io.github.darthakiranihil.konna.level.asset;

import io.github.darthakiranihil.konna.core.data.json.KStandardJsonParser;
import io.github.darthakiranihil.konna.core.data.json.KStandardJsonTokenizer;
import io.github.darthakiranihil.konna.core.io.KAssetLoader;
import io.github.darthakiranihil.konna.core.io.KJsonSubtypeBasedAssetLoader;
import io.github.darthakiranihil.konna.level.type.KTilePropertyTypedef;
import io.github.darthakiranihil.konna.level.type.KTileTypedef;
import io.github.darthakiranihil.konna.test.KStandardTestClass;

import java.util.Map;

public class KAssetCollectionTestClass extends KStandardTestClass {

    protected final KAssetLoader assetLoader;

    protected KAssetCollectionTestClass() {
        super();

        this.assetLoader = new KJsonSubtypeBasedAssetLoader(
            KStandardTestClass.context,
            Map.of("tileProp", new KJsonSubtypeBasedAssetLoader.AssetTypeData(
                new String[] { KTilePropertyTypedef.TILE_PROPERTY_ASSET_TYPE },
                new String[] {"classpath:assets/props.json"}
            ), "tile", new KJsonSubtypeBasedAssetLoader.AssetTypeData(
                new String[] { KTileTypedef.TILE_ASSET_TYPE},
                new String[] {"classpath:assets/tiles.json"}
            )),
            new KStandardJsonParser(new KStandardJsonTokenizer())
        );

        this.assetLoader.addAssetTypedef(new KTilePropertyTypedef());
        this.assetLoader.addAssetTypedef(new KTileTypedef());
    }
}
