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

package io.github.darthakiranihil.konna.level.generator.maker.partition;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.level.struct.KPartition;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

public class KBspNodeTests extends KStandardTestClass {

    @Test
    public void testProcessSuccess() {

        KUniversalMap params = new KUniversalMap();
        params.put("size", new KSize(60, 60));
        params.put("iterations", 4);
        params.put("vertical_split_min_ratio", 0.2f);
        params.put("vertical_split_max_ratio", 0.8f);
        params.put("horizontal_split_min_ratio", 0.4f);
        params.put("horizontal_split_max_ratio", 0.6f);

        KBspNode node = new KBspNode();
        KUniversalMap result = node.process(params, new Random(42069L));

        Assertions.assertNotNull(result.getSafe("partition", KPartition.class));
        KPartition partition = result.get("partition", KPartition.class);

        Queue<KPartition> checkQueue = new ArrayDeque<>(16);
        checkQueue.add(partition);

        while (!checkQueue.isEmpty()) {
            KPartition checked = checkQueue.poll();
            if (!checked.getSubpartitions().isEmpty()) {
                Assertions.assertEquals(2, checked.getSubpartitions().size());
                checkQueue.addAll(checked.getSubpartitions());
            }
        }
    }
}
