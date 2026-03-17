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

import io.github.darthakiranihil.konna.core.io.except.KAssetLoadingException;
import io.github.darthakiranihil.konna.core.message.KEvent;
import io.github.darthakiranihil.konna.core.message.KEventSystem;
import io.github.darthakiranihil.konna.core.message.KStandardEventSystem;
import io.github.darthakiranihil.konna.core.util.KHashMapBasedCache;
import io.github.darthakiranihil.konna.level.layer.KLevelSector;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KLevelCollectionNegativeTests extends KAssetCollectionTestClass {

    @Test
    public void testLoadInvalidByTileLocation() {

        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));

        KLevelCollection levelCollection = new KLevelCollection(
            this.assetLoader,
            es,
            new KTileCollection(
                this.assetLoader,
                new KHashMapBasedCache(),
                new KTilePropertyCollection(this.assetLoader, KAssetCollectionTestClass.context)
            ),
            KStandardTestClass.context
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelCollection.getAsset("invalid_by_tile")
        );

    }

    @Test
    public void testLoadInvalidByLinkLocation() {

        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));

        KLevelCollection levelCollection = new KLevelCollection(
            this.assetLoader,
            es,
            new KTileCollection(
                this.assetLoader,
                new KHashMapBasedCache(),
                new KTilePropertyCollection(this.assetLoader, KAssetCollectionTestClass.context)
            ),
            KStandardTestClass.context
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelCollection.getAsset("invalid_by_link_position_xb")
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelCollection.getAsset("invalid_by_link_position_xa")
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelCollection.getAsset("invalid_by_link_position_yb")
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelCollection.getAsset("invalid_by_link_position_ya")
        );

    }

    @Test
    public void testLoadInvalidByLinkName() {

        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));

        KLevelCollection levelCollection = new KLevelCollection(
            this.assetLoader,
            es,
            new KTileCollection(
                this.assetLoader,
                new KHashMapBasedCache(),
                new KTilePropertyCollection(this.assetLoader, KAssetCollectionTestClass.context)
            ),
            KStandardTestClass.context
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelCollection.getAsset("invalid_by_linked_sector")
        );

    }

    @Test
    public void testLoadByInvalidLinkDestination() {

        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));

        KLevelCollection levelCollection = new KLevelCollection(
            this.assetLoader,
            es,
            new KTileCollection(
                this.assetLoader,
                new KHashMapBasedCache(),
                new KTilePropertyCollection(this.assetLoader, KAssetCollectionTestClass.context)
            ),
            KStandardTestClass.context
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelCollection.getAsset("invalid_by_sector_destination_xb")
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelCollection.getAsset("invalid_by_sector_destination_xa")
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelCollection.getAsset("invalid_by_sector_destination_yb")
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelCollection.getAsset("invalid_by_sector_destination_ya")
        );

    }

    @Test
    public void testLoadInvalidByEntityPosition() {

        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));

        KLevelCollection levelCollection = new KLevelCollection(
            this.assetLoader,
            es,
            new KTileCollection(
                this.assetLoader,
                new KHashMapBasedCache(),
                new KTilePropertyCollection(this.assetLoader, KAssetCollectionTestClass.context)
            ),
            KStandardTestClass.context
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelCollection.getAsset("invalid_by_entity_position_xb")
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelCollection.getAsset("invalid_by_entity_position_xa")
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelCollection.getAsset("invalid_by_entity_position_yb")
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelCollection.getAsset("invalid_by_entity_position_ya")
        );

    }

    @Test
    public void testLoadWithAutonomousButValidationFailed() {

        KEventSystem es = new KStandardEventSystem();
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityMoved"));
        es.registerEvent(new KEvent<KLevelSector.EventData>("entityLeftSector"));

        KLevelCollection levelCollection = new KLevelCollection(
            this.assetLoader,
            es,
            new KTileCollection(
                this.assetLoader,
                new KHashMapBasedCache(),
                new KTilePropertyCollection(this.assetLoader, KAssetCollectionTestClass.context)
            ),
            KStandardTestClass.context
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> levelCollection.getAsset("invalid_validation_failed")
        );

    }
}
