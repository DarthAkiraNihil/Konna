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

package io.github.darthakiranihil.konna.level.generator.mutator.passability;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.level.layer.KPassabilityLayer;
import io.github.darthakiranihil.konna.level.layer.KPassabilityState;
import io.github.darthakiranihil.konna.level.layer.tool.KPassabilityLayerTool;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class KRemovePassableTouchingVoidNodeTests extends KStandardTestClass {

    @Test
    public void testProcessSuccess() {

        KPassabilityLayer passabilityLayer = new KPassabilityLayer(new KSize(3, 3));
        KPassabilityLayerTool tool = passabilityLayer.getTool();

        tool.setState(1, 1, KPassabilityState.PASSABLE);
        passabilityLayer.refresh();

        KUniversalMap params = new KUniversalMap();
        params.put("layer", passabilityLayer);

        KRemovePassableTouchingVoidNode node = new KRemovePassableTouchingVoidNode();
        KUniversalMap result = node.process(params, new Random());

        Assertions.assertNotNull(result.getSafe("layer", KPassabilityLayer.class));
        KPassabilityLayer layer = result.get("layer", KPassabilityLayer.class);

        Assertions.assertEquals(KPassabilityState.IMPASSABLE, layer.getOnPosition(1, 1));

    }
}
