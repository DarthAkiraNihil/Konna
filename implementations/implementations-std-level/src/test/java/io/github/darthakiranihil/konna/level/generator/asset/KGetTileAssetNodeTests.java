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

package io.github.darthakiranihil.konna.level.generator.asset;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.level.KTileInfo;
import io.github.darthakiranihil.konna.level.asset.KTileCollection;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;
import java.util.Random;

public class KGetTileAssetNodeTests extends KStandardTestClass {

    @Test
    public void testProcessSuccess() {

        KTileCollection mock = Mockito.mock(KTileCollection.class);
        Mockito
            .when(mock.getAsset("TATA"))
            .thenReturn(new KTileInfo("TATA", 1, true, 0, Map.of()));

        KUniversalMap params = new KUniversalMap();
        params.put("asset_id", "TATA");
        var node = new KGetTileAssetNode(mock);
        KUniversalMap result = node.process(params, new Random(42069L));

        Assertions.assertNotNull(result.getSafe("tile", KTileInfo.class));
        KTileInfo tileInfo = result.get("tile", KTileInfo.class);

        Assertions.assertEquals("TATA", tileInfo.getFullId());
        Assertions.assertEquals(1, tileInfo.getId());

    }
}
