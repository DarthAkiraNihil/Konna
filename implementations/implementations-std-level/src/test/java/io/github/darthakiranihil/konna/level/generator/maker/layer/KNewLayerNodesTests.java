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

package io.github.darthakiranihil.konna.level.generator.maker.layer;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.level.layer.KLevelEntityLayer;
import io.github.darthakiranihil.konna.level.layer.KLevelTransitionLayer;
import io.github.darthakiranihil.konna.level.layer.KSectorLinkLayer;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class KNewLayerNodesTests extends KStandardTestClass {

    @Test
    public void testNewSectorLinkLayer() {

        var node = new KNewSectorLinkLayerNode();
        KUniversalMap result = node.process(new KUniversalMap(), new Random(42069L));
        Assertions.assertNotNull(result.getSafe("layer", KSectorLinkLayer.class));

    }

    @Test
    public void testNewLevelEntitiesLayer() {

        var node = new KNewLevelEntitiesLayer();
        KUniversalMap result = node.process(new KUniversalMap(), new Random(42069L));
        Assertions.assertNotNull(result.getSafe("layer", KLevelEntityLayer.class));

    }

    @Test
    public void testNewLevelTransitionLayer() {

        var node = new KNewLevelTransitionLayer();
        KUniversalMap result = node.process(new KUniversalMap(), new Random(42069L));
        Assertions.assertNotNull(result.getSafe("layer", KLevelTransitionLayer.class));

    }
}
