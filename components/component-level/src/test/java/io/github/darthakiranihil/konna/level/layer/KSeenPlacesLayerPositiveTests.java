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

package io.github.darthakiranihil.konna.level.layer;

import io.github.darthakiranihil.konna.core.struct.KSize;
import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KSeenPlacesLayerPositiveTests extends KStandardTestClass {

    @Test
    public void testGetSeenStatus() {

        KSeenPlacesLayer layer = new KSeenPlacesLayer(new KSize(2, 2));
        Assertions.assertEquals(new KSize(2, 2), layer.getSize());
        Assertions.assertFalse(layer.getSeenStatus(0, 0));
        Assertions.assertEquals(
            layer.getSeenStatus(0, 0),
            layer.getSeenStatus(new KVector2i(0, 0))
        );


    }

    @Test
    public void testSeeThePlace() {

        KSeenPlacesLayer layer = new KSeenPlacesLayer(new KSize(2, 2));
        Assertions.assertEquals(new KSize(2, 2), layer.getSize());
        layer.seeThePlace(0, 0);
        Assertions.assertTrue(layer.getSeenStatus(0, 0));
        Assertions.assertEquals(
            layer.getSeenStatus(0, 0),
            layer.getSeenStatus(new KVector2i(0, 0))
        );

        layer.seeThePlace(new KVector2i(1, 1));
        Assertions.assertTrue(layer.getSeenStatus(1, 1));
        Assertions.assertEquals(
            layer.getSeenStatus(1, 1),
            layer.getSeenStatus(new KVector2i(1, 1))
        );

    }

    @Test
    public void testGetSeenStatusOutOfBounds() {

        KSeenPlacesLayer layer = new KSeenPlacesLayer(new KSize(2, 2));

        Assertions.assertFalse(layer.getSeenStatus(-1, 0));
        Assertions.assertFalse(layer.getSeenStatus(2, 0));
        Assertions.assertFalse(layer.getSeenStatus(0, -1));
        Assertions.assertFalse(layer.getSeenStatus(0, 2));

    }

    @Test
    public void testSetSeenStatusOutOfBounds() {

        KSeenPlacesLayer layer = new KSeenPlacesLayer(new KSize(2, 2));

        layer.seeThePlace(-1, 0);
        layer.seeThePlace(2, 0);
        layer.seeThePlace(0, -1);
        layer.seeThePlace(0, 2);
        Assertions.assertFalse(layer.getSeenStatus(0, 0));
        Assertions.assertFalse(layer.getSeenStatus(0, 1));
        Assertions.assertFalse(layer.getSeenStatus(1, 0));
        Assertions.assertFalse(layer.getSeenStatus(1, 1));

    }
}
