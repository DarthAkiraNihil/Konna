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

package io.github.darthakiranihil.konna.level.generator.math;

import io.github.darthakiranihil.konna.core.data.KUniversalMap;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class KNegateVector2iNodeNodeTests extends KStandardTestClass {

    @Test
    public void testProcessSuccess() {

        KUniversalMap params = new KUniversalMap();
        params.put("value", new KVector2i(2, -2));
        var node = new KNegateVector2iNode();
        KUniversalMap result = node.process(params, new Random(42069L));
        Assertions.assertNotNull(result.getSafe("value", KVector2i.class));
        Assertions.assertEquals(new KVector2i(-2, 2), result.get("value", KVector2i.class));

    }
}
