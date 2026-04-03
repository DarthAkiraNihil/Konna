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

package io.github.darthakiranihil.konna.level;

import io.github.darthakiranihil.konna.core.data.json.KStandardJsonParser;
import io.github.darthakiranihil.konna.core.data.json.KStandardJsonTokenizer;
import io.github.darthakiranihil.konna.core.io.KAssetLoader;
import io.github.darthakiranihil.konna.core.io.KJsonSubtypeBasedAssetLoader;
import io.github.darthakiranihil.konna.core.io.except.KAssetLoadingException;
import io.github.darthakiranihil.konna.core.message.KEvent;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.message.KStandardEventSystem;
import io.github.darthakiranihil.konna.core.util.KHashMapBasedCache;
import io.github.darthakiranihil.konna.level.asset.KLevelMetadataCollection;
import io.github.darthakiranihil.konna.level.asset.KTileCollection;
import io.github.darthakiranihil.konna.level.asset.KTilePropertyCollection;
import io.github.darthakiranihil.konna.level.type.KLevelMetadataTypedef;
import io.github.darthakiranihil.konna.level.type.KTilePropertyTypedef;
import io.github.darthakiranihil.konna.level.type.KTileTypedef;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class KStandardLevelLoaderNegativeTests extends KStandardTestClass {

    private final KAssetLoader assetLoader;
    private final KEventSystem es;

    public KStandardLevelLoaderNegativeTests() {

        this.assetLoader = new KJsonSubtypeBasedAssetLoader(
            KStandardTestClass.context,
            Map.of("tileProp", new KJsonSubtypeBasedAssetLoader.AssetTypeData(
                new String[] { KTilePropertyTypedef.TILE_PROPERTY_ASSET_TYPE },
                new String[] {"classpath:assets/props.json"}
            ), "tile", new KJsonSubtypeBasedAssetLoader.AssetTypeData(
                new String[] { KTileTypedef.TILE_ASSET_TYPE},
                new String[] {"classpath:assets/tiles.json"}
            ), "level", new KJsonSubtypeBasedAssetLoader.AssetTypeData(
                new String[] { KLevelMetadataTypedef.LEVEL_METADATA_ASSET_TYPE },
                new String[] {"classpath:assets/levels.json"}
            )),
            new KStandardJsonParser(new KStandardJsonTokenizer())
        );

        this.assetLoader.addAssetTypedef(new KTilePropertyTypedef());
        this.assetLoader.addAssetTypedef(new KTileTypedef());
        this.assetLoader.addAssetTypedef(new KLevelMetadataTypedef());

        this.es = new KStandardEventSystem();
        this.es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        this.es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));

    }

    @Test
    public void testLoadInvalidByTileLocation() {

        KLevelMetadataCollection levelCollection = new KLevelMetadataCollection(this.assetLoader);
        KLevelLoader levelLoader = new KStandardLevelLoader(
            this.es,
            KStandardTestClass.context,
            new KTileCollection(
                this.assetLoader,
                new KHashMapBasedCache(),
                new KTilePropertyCollection(this.assetLoader, KStandardTestClass.context)
            )
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelLoader.load(
                levelCollection.getAsset("invalid_by_tile")
            )
        );

    }

    @Test
    public void testLoadInvalidByLinkLocation() {

        KLevelMetadataCollection levelCollection = new KLevelMetadataCollection(this.assetLoader);
        KLevelLoader levelLoader = new KStandardLevelLoader(
            this.es,
            KStandardTestClass.context,
            new KTileCollection(
                this.assetLoader,
                new KHashMapBasedCache(),
                new KTilePropertyCollection(this.assetLoader, KStandardTestClass.context)
            )
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelLoader.load(levelCollection.getAsset("invalid_by_link_position_xb"))
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelLoader.load(levelCollection.getAsset("invalid_by_link_position_xa"))
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelLoader.load(levelCollection.getAsset("invalid_by_link_position_yb"))
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelLoader.load(levelCollection.getAsset("invalid_by_link_position_ya"))
        );

    }

    @Test
    public void testLoadInvalidByLinkName() {

        KLevelMetadataCollection levelCollection = new KLevelMetadataCollection(this.assetLoader);
        KLevelLoader levelLoader = new KStandardLevelLoader(
            this.es,
            KStandardTestClass.context,
            new KTileCollection(
                this.assetLoader,
                new KHashMapBasedCache(),
                new KTilePropertyCollection(this.assetLoader, KStandardTestClass.context)
            )
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelLoader.load(levelCollection.getAsset("invalid_by_linked_sector"))
        );

    }

    @Test
    public void testLoadByInvalidLinkDestination() {

        KLevelMetadataCollection levelCollection = new KLevelMetadataCollection(this.assetLoader);
        KLevelLoader levelLoader = new KStandardLevelLoader(
            this.es,
            KStandardTestClass.context,
            new KTileCollection(
                this.assetLoader,
                new KHashMapBasedCache(),
                new KTilePropertyCollection(this.assetLoader, KStandardTestClass.context)
            )
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelLoader.load(levelCollection.getAsset("invalid_by_sector_destination_xb"))
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelLoader.load(levelCollection.getAsset("invalid_by_sector_destination_xa"))
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelLoader.load(levelCollection.getAsset("invalid_by_sector_destination_yb"))
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelLoader.load(levelCollection.getAsset("invalid_by_sector_destination_ya"))
        );

    }

    @Test
    public void testLoadInvalidByEntityPosition() {

        KLevelMetadataCollection levelCollection = new KLevelMetadataCollection(this.assetLoader);
        KLevelLoader levelLoader = new KStandardLevelLoader(
            this.es,
            KStandardTestClass.context,
            new KTileCollection(
                this.assetLoader,
                new KHashMapBasedCache(),
                new KTilePropertyCollection(this.assetLoader, KStandardTestClass.context)
            )
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelLoader.load(levelCollection.getAsset("invalid_by_entity_position_xb"))
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelLoader.load(levelCollection.getAsset("invalid_by_entity_position_xa"))
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelLoader.load(levelCollection.getAsset("invalid_by_entity_position_yb"))
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelLoader.load(levelCollection.getAsset("invalid_by_entity_position_ya"))
        );

    }

    @Test
    public void testLoadWithAutonomousButValidationFailed() {

        KLevelMetadataCollection levelCollection = new KLevelMetadataCollection(this.assetLoader);
        KLevelLoader levelLoader = new KStandardLevelLoader(
            this.es,
            KStandardTestClass.context,
            new KTileCollection(
                this.assetLoader,
                new KHashMapBasedCache(),
                new KTilePropertyCollection(this.assetLoader, KStandardTestClass.context)
            )
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelLoader.load(levelCollection.getAsset("invalid_validation_failed"))
        );

    }

    @Test
    public void testLoadWithInvalidLevelTransitions() {

        KLevelMetadataCollection levelCollection = new KLevelMetadataCollection(this.assetLoader);
        KLevelLoader levelLoader = new KStandardLevelLoader(
            this.es,
            KStandardTestClass.context,
            new KTileCollection(
                this.assetLoader,
                new KHashMapBasedCache(),
                new KTilePropertyCollection(this.assetLoader, KStandardTestClass.context)
            )
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelLoader.load(levelCollection.getAsset("invalid_with_level_transition_source_xb"))
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelLoader.load(levelCollection.getAsset("invalid_with_level_transition_source_xa"))
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelLoader.load(levelCollection.getAsset("invalid_with_level_transition_source_yb"))
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelLoader.load(levelCollection.getAsset("invalid_with_level_transition_source_ya"))
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelLoader.load(levelCollection.getAsset("invalid_with_level_transition_destination_xb"))
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelLoader.load(levelCollection.getAsset("invalid_with_level_transition_destination_yb"))
        );

    }

}
