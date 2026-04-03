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

package io.github.darthakiranihil.konna.level.generator.maker.passability;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.level.layer.KPassabilityLayer;
import io.github.darthakiranihil.konna.level.layer.KPassabilityState;
import io.github.darthakiranihil.konna.level.struct.KPartition;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

public class KRandomRectangularCenteredRoomsInPartitionNodeTests extends KStandardTestClass {

    @SuppressWarnings("ExtractMethodRecommender")
    @Test
    public void testProcessSuccess() {

        KPartition partition = new KPartition(
            new KVector2i(0, 0),
            new KSize(60, 60),
            List.of(
                new KPartition(
                    new KVector2i(0, 0),
                    new KSize(30, 60),
                    List.of()
                ),
                new KPartition(
                    new KVector2i(30, 0),
                    new KSize(30, 60),
                    List.of()
                )
            )
        );

        KUniversalMap params = new KUniversalMap();
        params.put("partition", partition);
        params.put("min_filling", 0.5f);

        var node = new KRandomRectangularCenteredRoomsInPartitionNode();
        KUniversalMap result = node.process(params, new Random(42069L));

        Assertions.assertNotNull(result.getSafe("layer", KPassabilityLayer.class));
        KPassabilityLayer layer = result.get("layer", KPassabilityLayer.class);

        Assertions.assertEquals(KPassabilityState.PASSABLE, layer.getOnPosition(15, 30));
        Assertions.assertEquals(KPassabilityState.PASSABLE, layer.getOnPosition(45, 30));

    }

    @Test
    public void testProcessDepperSuccess() {

        KPartition partition = new KPartition(
            new KVector2i(0, 0),
            new KSize(120, 120),
            List.of(
                new KPartition(
                    new KVector2i(0, 0),
                    new KSize(60, 120),
                    List.of()
                ),
                new KPartition(
                    new KVector2i(60, 0),
                    new KSize(60, 120),
                    List.of(
                        new KPartition(
                            new KVector2i(60, 0),
                            new KSize(60, 60),
                            List.of()
                        ),
                        new KPartition(
                            new KVector2i(60, 60),
                            new KSize(60, 60),
                            List.of()
                        )
                    )
                )
            )
        );

        KUniversalMap params = new KUniversalMap();
        params.put("partition", partition);
        params.put("min_filling", 0.5f);

        var node = new KRandomRectangularCenteredRoomsInPartitionNode();
        KUniversalMap result = node.process(params, new Random(1234569L));

        Assertions.assertNotNull(result.getSafe("layer", KPassabilityLayer.class));
        KPassabilityLayer layer = result.get("layer", KPassabilityLayer.class);

        Assertions.assertEquals(KPassabilityState.PASSABLE, layer.getOnPosition(30, 60));
        Assertions.assertEquals(KPassabilityState.PASSABLE, layer.getOnPosition(30, 90));
        Assertions.assertEquals(KPassabilityState.PASSABLE, layer.getOnPosition(30, 30));

    }

    @Test
    public void testProcessOnlyOnePartitionSuccess() {

        KPartition partition = new KPartition(
            new KVector2i(0, 0),
            new KSize(120, 120),
            List.of()
        );

        KUniversalMap params = new KUniversalMap();
        params.put("partition", partition);
        params.put("min_filling", 0.5f);

        var node = new KRandomRectangularCenteredRoomsInPartitionNode();
        KUniversalMap result = node.process(params, new Random(1234569L));

        Assertions.assertNotNull(result.getSafe("layer", KPassabilityLayer.class));
        KPassabilityLayer layer = result.get("layer", KPassabilityLayer.class);

        Assertions.assertEquals(KPassabilityState.PASSABLE, layer.getOnPosition(60, 60));

    }
}
