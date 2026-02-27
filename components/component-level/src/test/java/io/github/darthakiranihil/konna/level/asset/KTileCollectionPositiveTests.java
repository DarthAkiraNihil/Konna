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

import io.github.darthakiranihil.konna.core.util.KHashMapBasedCache;
import io.github.darthakiranihil.konna.level.KTileInfo;
import io.github.darthakiranihil.konna.level.impl.TestObjectTileProperty;
import io.github.darthakiranihil.konna.level.property.KObjectArrayTileProperty;
import io.github.darthakiranihil.konna.level.property.KObjectTileProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KTileCollectionPositiveTests extends KAssetCollectionTestClass {

    @Test
    public void testLoadTile() {

        KTileCollection tc = new KTileCollection(
            this.assetLoader,
            new KHashMapBasedCache(),
            new KTilePropertyCollection(this.assetLoader, KAssetCollectionTestClass.context)
        );

        KTileInfo tile = tc.getAsset("tile_valid");
        tc.getAsset("tile_valid");

        Assertions.assertEquals(1, tile.getId());
        Assertions.assertEquals(10, tile.getTransparency());
        Assertions.assertTrue(tile.isPassable());

        var ip = tile.getIntProperty("prop1");
        var iap = tile.getIntArrayProperty("prop2");
        var fp = tile.getFloatProperty("prop3");
        var fap = tile.getFloatArrayProperty("prop4");
        var bp = tile.getBooleanProperty("prop5");
        var bap = tile.getBooleanArrayProperty("prop6");
        var sp = tile.getStringProperty("prop7");
        var sap = tile.getStringArrayProperty("prop8");
        KObjectTileProperty<TestObjectTileProperty> op = tile.getObjectProperty("prop9");
        KObjectArrayTileProperty<TestObjectTileProperty> oap = tile.getObjectArrayProperty("prop10");

        Assertions.assertNotNull(ip);
        Assertions.assertNotNull(iap);
        Assertions.assertNotNull(fp);
        Assertions.assertNotNull(fap);
        Assertions.assertNotNull(bp);
        Assertions.assertNotNull(bap);
        Assertions.assertNotNull(sp);
        Assertions.assertNotNull(sap);
        Assertions.assertNotNull(op);
        Assertions.assertNotNull(oap);

        Assertions.assertEquals(1, ip.getValue());
        Assertions.assertEquals(1.0, fp.getValue());
        Assertions.assertTrue(bp.getValue());
        Assertions.assertEquals("test", sp.getValue());
        Assertions.assertEquals(2, op.getValue().getTestValue());

        Assertions.assertEquals(1, iap.getValue().length);
        Assertions.assertEquals(1, fap.getValue().length);
        Assertions.assertEquals(1, bap.getValue().length);
        Assertions.assertEquals(1, sap.getValue().length);
        Assertions.assertEquals(1, oap.getValue().length);

        Assertions.assertEquals(1, iap.getValue()[0]);
        Assertions.assertEquals(1.0, fap.getValue()[0]);
        Assertions.assertFalse(bap.getValue()[0]);
        Assertions.assertEquals("test1", sap.getValue()[0]);
        Assertions.assertEquals(3, oap.getValue()[0].getTestValue());

    }
}
