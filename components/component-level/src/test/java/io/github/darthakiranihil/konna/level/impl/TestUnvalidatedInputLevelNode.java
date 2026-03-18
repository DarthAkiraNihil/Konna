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

package io.github.darthakiranihil.konna.level.impl;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.data.json.KStandardJsonParser;
import io.github.darthakiranihil.konna.core.data.json.KStandardJsonTokenizer;
import io.github.darthakiranihil.konna.core.io.KJsonSubtypeBasedAssetLoader;
import io.github.darthakiranihil.konna.core.message.KEvent;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.message.KStandardEventSystem;
import io.github.darthakiranihil.konna.core.util.KHashMapBasedCache;
import io.github.darthakiranihil.konna.level.KLevel;
import io.github.darthakiranihil.konna.level.KLevelSector;
import io.github.darthakiranihil.konna.level.asset.KAssetCollectionTestClass;
import io.github.darthakiranihil.konna.level.asset.KLevelCollection;
import io.github.darthakiranihil.konna.level.asset.KTileCollection;
import io.github.darthakiranihil.konna.level.asset.KTilePropertyCollection;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNode;
import io.github.darthakiranihil.konna.level.generator.KGeneratorNodeOutputParam;
import io.github.darthakiranihil.konna.level.type.KLevelGeneratorMetadataTypedef;
import io.github.darthakiranihil.konna.level.type.KLevelTypedef;
import io.github.darthakiranihil.konna.level.type.KTilePropertyTypedef;
import io.github.darthakiranihil.konna.level.type.KTileTypedef;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.jspecify.annotations.NullMarked;

import java.util.Map;
import java.util.Random;

@NullMarked
public class TestUnvalidatedInputLevelNode implements KGeneratorNode {

    private final KLevelCollection levelCollection;

    public TestUnvalidatedInputLevelNode() {
        // I hate this
        var assetLoader = new KJsonSubtypeBasedAssetLoader(
            KStandardTestClass.getContext(),
            Map.of("tileProp", new KJsonSubtypeBasedAssetLoader.AssetTypeData(
                new String[] { KTilePropertyTypedef.TILE_PROPERTY_ASSET_TYPE },
                new String[] {"classpath:assets/props.json"}
            ), "tile", new KJsonSubtypeBasedAssetLoader.AssetTypeData(
                new String[] { KTileTypedef.TILE_ASSET_TYPE},
                new String[] {"classpath:assets/tiles.json"}
            ), "level", new KJsonSubtypeBasedAssetLoader.AssetTypeData(
                new String[] { KLevelTypedef.LEVEL_ASSET_TYPE },
                new String[] {"classpath:assets/levels.json"}
            ), "generator", new KJsonSubtypeBasedAssetLoader.AssetTypeData(
                new String[] { KLevelGeneratorMetadataTypedef.LEVEL_GENERATOR_METADATA_TYPE },
                new String[] {"classpath:assets/generators.json"}
            )),
            new KStandardJsonParser(new KStandardJsonTokenizer())
        );

        assetLoader.addAssetTypedef(new KTilePropertyTypedef());
        assetLoader.addAssetTypedef(new KTileTypedef());
        assetLoader.addAssetTypedef(new KLevelTypedef());
        assetLoader.addAssetTypedef(new KLevelGeneratorMetadataTypedef());

        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));

        this.levelCollection = new KLevelCollection(
            assetLoader,
            es,
            new KTileCollection(
                assetLoader,
                new KHashMapBasedCache(),
                new KTilePropertyCollection(assetLoader, KAssetCollectionTestClass.getContext())
            ),
            KStandardTestClass.getContext()
        );
    }

    @Override
    @KGeneratorNodeOutputParam(name = "level", type = KLevel.class)
    public KUniversalMap process(KUniversalMap params, Random rnd) {

        String assetId = params.get("asset_id", String.class);
        KUniversalMap result = new KUniversalMap();
        result.put("level", this.levelCollection.getAsset(assetId));
        return result;

    }

}
