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

public class KHeightLayerPositiveTests extends KStandardTestClass {

    @Test
    public void testGetHeight() {

        KHeightLayer layer = new KHeightLayer(new KSize(2, 2));
        Assertions.assertEquals(new KSize(2, 2), layer.getSize());
        Assertions.assertEquals(0, layer.getOnPosition(0, 0));
        Assertions.assertEquals(
            layer.getOnPosition(0, 0),
            layer.getOnPosition(KVector2i.ZERO)
        );


    }

    @Test
    public void testSetHeight() {

        KHeightLayer layer = new KHeightLayer(new KSize(2, 2));
        Assertions.assertEquals(new KSize(2, 2), layer.getSize());
        layer.setHeight(0, 0, 1);
        Assertions.assertEquals(1, layer.getOnPosition(0, 0));
        Assertions.assertEquals(
            layer.getOnPosition(0, 0),
            layer.getOnPosition(KVector2i.ZERO)
        );

        layer.setHeight(new KVector2i(1, 1), 2);
        Assertions.assertEquals(2, layer.getOnPosition(1, 1));
        Assertions.assertEquals(
            layer.getOnPosition(1, 1),
            layer.getOnPosition(new KVector2i(1, 1))
        );

    }

    @Test
    public void testGetHeightOutOfBounds() {

        KHeightLayer layer = new KHeightLayer(new KSize(2, 2));

        Assertions.assertEquals(Integer.MAX_VALUE, layer.getOnPosition(-1, 0));
        Assertions.assertEquals(Integer.MAX_VALUE, layer.getOnPosition(2, 0));
        Assertions.assertEquals(Integer.MAX_VALUE, layer.getOnPosition(0, -1));
        Assertions.assertEquals(Integer.MAX_VALUE, layer.getOnPosition(0, 2));

    }

    @Test
    public void testSetHeightOutOfBounds() {

        KHeightLayer layer = new KHeightLayer(new KSize(2, 2));

        layer
            .setHeight(-1, 0, 1)
            .setHeight(2, 0, 1)
            .setHeight(0, -1, 1)
            .setHeight(0, 2, 1);

        Assertions.assertEquals(Integer.MAX_VALUE, layer.getOnPosition(-1, 0));
        Assertions.assertEquals(Integer.MAX_VALUE, layer.getOnPosition(2, 0));
        Assertions.assertEquals(Integer.MAX_VALUE, layer.getOnPosition(0, -1));
        Assertions.assertEquals(Integer.MAX_VALUE, layer.getOnPosition(0, 2));

    }
    
}
