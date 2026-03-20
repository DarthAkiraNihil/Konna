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

public class KVisitedPlacesLayerPositiveTests extends KStandardTestClass {

    @Test
    public void testisVisited() {

        KVisitedPlacesLayer layer = new KVisitedPlacesLayer(new KSize(2, 2));
        var tool = layer.getTool();
        
        Assertions.assertEquals(new KSize(2, 2), layer.getSize());
        Assertions.assertFalse(tool.isVisited(0, 0));
        Assertions.assertEquals(
            tool.isVisited(0, 0),
            tool.isVisited(new KVector2i(0, 0))
        );


    }

    @Test
    public void testVisitPlace() {

        KVisitedPlacesLayer layer = new KVisitedPlacesLayer(new KSize(2, 2));
        var tool = layer.getTool();
        
        Assertions.assertEquals(new KSize(2, 2), layer.getSize());
        tool.visitPlace(0, 0);
        Assertions.assertTrue(tool.isVisited(0, 0));
        Assertions.assertEquals(
            tool.isVisited(0, 0),
            tool.isVisited(new KVector2i(0, 0))
        );

        tool.visitPlace(new KVector2i(1, 1));
        Assertions.assertTrue(tool.isVisited(1, 1));
        Assertions.assertEquals(
            tool.isVisited(1, 1),
            tool.isVisited(new KVector2i(1, 1))
        );

    }

    @Test
    public void testisVisitedOutOfBounds() {

        KVisitedPlacesLayer layer = new KVisitedPlacesLayer(new KSize(2, 2));
        var tool = layer.getTool();
        
        Assertions.assertFalse(tool.isVisited(-1, 0));
        Assertions.assertFalse(tool.isVisited(2, 0));
        Assertions.assertFalse(tool.isVisited(0, -1));
        Assertions.assertFalse(tool.isVisited(0, 2));

    }

    @Test
    public void testSetSeenStatusOutOfBounds() {

        KVisitedPlacesLayer layer = new KVisitedPlacesLayer(new KSize(2, 2));
        var tool = layer.getTool();

        tool.visitPlace(-1, 0);
        tool.visitPlace(2, 0);
        tool.visitPlace(0, -1);
        tool.visitPlace(0, 2);
        Assertions.assertFalse(tool.isVisited(0, 0));
        Assertions.assertFalse(tool.isVisited(0, 1));
        Assertions.assertFalse(tool.isVisited(1, 0));
        Assertions.assertFalse(tool.isVisited(1, 1));

    }
}
