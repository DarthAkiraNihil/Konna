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

package io.github.darthakiranihil.konna.graphics.shape;

import io.github.darthakiranihil.konna.core.struct.KVector2i;
import io.github.darthakiranihil.konna.graphics.KColor;
import org.junit.jupiter.api.Test;

public class KLinePositiveTests extends KShapeTestClass {

    private final KVector2i start = new KVector2i(10, 10);
    private final KVector2i end = new KVector2i(16, 16);
    private final KColor color = KColor.WHITE;

    @Test
    public void testCreateShapeStandard() {

        KLine shape = new KLine(this.start, this.end, this.color);
        this.assertLine(shape, this.start, this.end, this.color);

    }

    @Test
    public void testCreateShapeWithoutColors() {

        KLine shape = new KLine(this.start, this.end);
        this.assertLine(shape, this.start, this.end, KColor.TRANSPARENT);

    }

    @Test
    public void testCreateShapeStandardSeparated() {

        KLine shape = new KLine(this.start.x(), this.end.x(), this.start.y(), this.end.y(), this.color);
        this.assertLine(shape, this.start, this.end, this.color);

    }

    @Test
    public void testCreateShapeWithoutColorsSeparated() {

        KLine shape = new KLine(this.start.x(), this.end.x(), this.start.y(), this.end.y());
        this.assertLine(shape, this.start, this.end, KColor.TRANSPARENT);

    }

}
