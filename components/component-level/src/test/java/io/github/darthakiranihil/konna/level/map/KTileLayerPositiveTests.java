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

package io.github.darthakiranihil.konna.level.map;

import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.core.util.KReflectionUtils;
import io.github.darthakiranihil.konna.level.KTileInfo;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Objects;

public class KTileLayerPositiveTests extends KStandardTestClass {

    @Test
    public void testGetTileSuccess() {

        KTileLayer layer = new KTileLayer(2, 2);
        KTileInfo tileInfo = new KTileInfo(1, true, 16, Map.of());

        Assertions.assertEquals(KSize.squared(2), layer.getSize());

        layer.placeTile(0, 0, tileInfo);
        layer.placeTile(0, 1, tileInfo);
        layer.placeTile(1, 0, tileInfo);
        layer.placeTile(1, 1, tileInfo);

        KTileInfo t00 = layer.getOnPosition(0, 0);
        KTileInfo t01 = layer.getOnPosition(0, 1);
        KTileInfo t10 = layer.getOnPosition(1, 0);
        KTileInfo t11 = layer.getOnPosition(1, 1);

        Assertions.assertNotNull(t00);
        Assertions.assertNotNull(t01);
        Assertions.assertNotNull(t10);
        Assertions.assertNotNull(t11);

        Assertions.assertEquals(tileInfo.getId(), t00.getId());
        Assertions.assertEquals(tileInfo.getId(), t01.getId());
        Assertions.assertEquals(tileInfo.getId(), t10.getId());
        Assertions.assertEquals(tileInfo.getId(), t11.getId());

    }

    @Test
    public void testGetOutOfBorders() {

        KTileLayer layer = new KTileLayer(2, 2);

        Assertions.assertEquals(KSize.squared(2), layer.getSize());

        KTileInfo t00 = layer.getOnPosition(-1, 0);
        KTileInfo t01 = layer.getOnPosition(0, -1);
        KTileInfo t10 = layer.getOnPosition(2, 0);
        KTileInfo t11 = layer.getOnPosition(0, 2);

        Assertions.assertNull(t00);
        Assertions.assertNull(t01);
        Assertions.assertNull(t10);
        Assertions.assertNull(t11);

    }

    @Test
    public void testPlaceSelfTwice() {

        KTileLayer layer = new KTileLayer(2, 2);
        KTileInfo tileInfo = new KTileInfo(1, true, 16, Map.of());

        Assertions.assertEquals(KSize.squared(2), layer.getSize());

        layer.placeTile(new KVector2i(0, 0), tileInfo);
        layer.placeTile(new KVector2i(0, 0), tileInfo);
        layer.placeTile(new KVector2i(0, 1), tileInfo);
        layer.placeTile(new KVector2i(1, 0), tileInfo);
        layer.placeTile(new KVector2i(1, 1), tileInfo);

        KTileInfo t00 = layer.getOnPosition(new KVector2i(0, 0));
        KTileInfo t01 = layer.getOnPosition(new KVector2i(0, 1));
        KTileInfo t10 = layer.getOnPosition(new KVector2i(1, 0));
        KTileInfo t11 = layer.getOnPosition(new KVector2i(1, 1));

        Assertions.assertNotNull(t00);
        Assertions.assertNotNull(t01);
        Assertions.assertNotNull(t10);
        Assertions.assertNotNull(t11);

        Assertions.assertEquals(tileInfo.getId(), t00.getId());
        Assertions.assertEquals(tileInfo.getId(), t01.getId());
        Assertions.assertEquals(tileInfo.getId(), t10.getId());
        Assertions.assertEquals(tileInfo.getId(), t11.getId());

    }

    @Test
    public void testPlaceOutOfBorders() {

        KTileLayer layer = new KTileLayer(new KSize(2, 2));
        KTileInfo tileInfo = new KTileInfo(1, true, 16, Map.of());
        Assertions.assertEquals(KSize.squared(2), layer.getSize());

        layer.placeTile(-1, 0, tileInfo);
        layer.placeTile(0, -1, tileInfo);
        layer.placeTile(2, 0, tileInfo);
        layer.placeTile(0, 2, tileInfo);

        KTileInfo t00 = layer.getOnPosition(-1, 0);
        KTileInfo t01 = layer.getOnPosition(0, -1);
        KTileInfo t10 = layer.getOnPosition(2, 0);
        KTileInfo t11 = layer.getOnPosition(0, 2);

        Assertions.assertNull(t00);
        Assertions.assertNull(t01);
        Assertions.assertNull(t10);
        Assertions.assertNull(t11);

    }

    @Test
    public void testPlaceNewTileFullReplace() {

        KTileLayer layer = new KTileLayer(2, 2);
        KTileInfo tileInfo = new KTileInfo(1, true, 16, Map.of());
        KTileInfo tileInfo2 = new KTileInfo(2, true, 16, Map.of());

        Assertions.assertEquals(KSize.squared(2), layer.getSize());

        layer.placeTile(0, 0, tileInfo);
        layer.placeTile(0, 1, tileInfo);
        layer.placeTile(1, 0, tileInfo);
        layer.placeTile(1, 1, tileInfo);

        layer.placeTile(0, 0, tileInfo2);
        layer.placeTile(0, 1, tileInfo2);
        layer.placeTile(1, 0, tileInfo2);
        layer.placeTile(1, 1, tileInfo2);

        KTileInfo t00 = layer.getOnPosition(0, 0);
        KTileInfo t01 = layer.getOnPosition(0, 1);
        KTileInfo t10 = layer.getOnPosition(1, 0);
        KTileInfo t11 = layer.getOnPosition(1, 1);

        Assertions.assertNotNull(t00);
        Assertions.assertNotNull(t01);
        Assertions.assertNotNull(t10);
        Assertions.assertNotNull(t11);

        Assertions.assertEquals(tileInfo2.getId(), t00.getId());
        Assertions.assertEquals(tileInfo2.getId(), t01.getId());
        Assertions.assertEquals(tileInfo2.getId(), t10.getId());
        Assertions.assertEquals(tileInfo2.getId(), t11.getId());

    }

    @Test
    public void testGetTileButItsNotHere() {

        KTileLayer layer = new KTileLayer(2, 2);
        KTileInfo tileInfo = new KTileInfo(1, true, 16, Map.of());

        int[][] array = Objects.requireNonNull(KReflectionUtils.getField(KTileLayer.class, layer, "tiles", int[][].class));

        layer.placeTile(0, 0, tileInfo);
        layer.placeTile(0, 1, tileInfo);
        layer.placeTile(1, 0, tileInfo);
        layer.placeTile(1, 1, tileInfo);

        array[0][0] = 2;

        KTileInfo t00 = layer.getOnPosition(0, 0);
        KTileInfo t01 = layer.getOnPosition(0, 1);
        KTileInfo t10 = layer.getOnPosition(1, 0);
        KTileInfo t11 = layer.getOnPosition(1, 1);

        Assertions.assertNull(t00);
        Assertions.assertNotNull(t01);
        Assertions.assertNotNull(t10);
        Assertions.assertNotNull(t11);

        Assertions.assertEquals(tileInfo.getId(), t01.getId());
        Assertions.assertEquals(tileInfo.getId(), t10.getId());
        Assertions.assertEquals(tileInfo.getId(), t11.getId());

    }
}
