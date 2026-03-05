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
import io.github.darthakiranihil.konna.core.util.KHashMapBasedCache;
import io.github.darthakiranihil.konna.level.map.KLocation;
import io.github.darthakiranihil.konna.level.map.KMapSector;
import io.github.darthakiranihil.konna.level.map.KMapSectorSlice;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KLocationCollectionTests extends KAssetCollectionTestClass {

    @Test
    public void testLoadValidLocation() {

        KLocationCollection locationCollection = new KLocationCollection(
            this.assetLoader,
            KStandardTestClass.context,
            new KTileCollection(
                this.assetLoader,
                new KHashMapBasedCache(),
                new KTilePropertyCollection(this.assetLoader, KAssetCollectionTestClass.context)
            )
        );

        KLocation loaded = locationCollection.getAsset("valid");
        Assertions.assertArrayEquals(
            new String[] {
                "mf2", "mf1"
            },
            loaded.getSectorNames()
        );

        KMapSector mf1 = loaded.getSector("mf1");
        KMapSector mf2 = loaded.getSector("mf2");

        KMapSectorSlice sl1 = mf1.getSlice(0, 0);
        KMapSectorSlice sl2 = mf2.getSlice(1, 1);

        Assertions.assertEquals(mf2, sl1.sectorLink());
        Assertions.assertEquals(mf1, sl2.sectorLink());

        Assertions.assertEquals(1, sl1.tile().getId());
        Assertions.assertEquals(1, sl2.tile().getId());
    }

    @Test
    public void testLoadInvalidByTileLocation() {


        KLocationCollection locationCollection = new KLocationCollection(
            this.assetLoader,
            KStandardTestClass.context,
            new KTileCollection(
                this.assetLoader,
                new KHashMapBasedCache(),
                new KTilePropertyCollection(this.assetLoader, KAssetCollectionTestClass.context)
            )
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> locationCollection.getAsset("invalid_by_tile")
        );

    }

    @Test
    public void testLoadInvalidByLinkLocation() {

        KLocationCollection locationCollection = new KLocationCollection(
            this.assetLoader,
            KStandardTestClass.context,
            new KTileCollection(
                this.assetLoader,
                new KHashMapBasedCache(),
                new KTilePropertyCollection(this.assetLoader, KAssetCollectionTestClass.context)
            )
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> locationCollection.getAsset("invalid_by_link_position_xb")
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> locationCollection.getAsset("invalid_by_link_position_xa")
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> locationCollection.getAsset("invalid_by_link_position_yb")
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> locationCollection.getAsset("invalid_by_link_position_ya")
        );

    }

    @Test
    public void testLoadInvalidByLinkName() {

        KLocationCollection locationCollection = new KLocationCollection(
            this.assetLoader,
            KStandardTestClass.context,
            new KTileCollection(
                this.assetLoader,
                new KHashMapBasedCache(),
                new KTilePropertyCollection(this.assetLoader, KAssetCollectionTestClass.context)
            )
        );

        Assertions.assertThrows(
            KAssetLoadingException.class,
            () -> locationCollection.getAsset("invalid_by_linked_sector")
        );

    }
}
