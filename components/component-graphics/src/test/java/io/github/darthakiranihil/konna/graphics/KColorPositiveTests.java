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

package io.github.darthakiranihil.konna.graphics;

import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

public class KColorPositiveTests extends KStandardTestClass {

    @Test
    public void testColorRepresentationsAndSomethingElse() {

        KColor color = new KColor(255, 255, 255);

        Assertions.assertEquals(KColor.WHITE, color);
        float[] normalized = color.normalized();
        Assertions.assertEquals(1.0f, normalized[0]);
        Assertions.assertEquals(1.0f, normalized[1]);
        Assertions.assertEquals(1.0f, normalized[2]);
        Assertions.assertEquals(1.0f, normalized[3]);

        Assertions.assertEquals(0xFFFFFFFF, color.getAsInt());

        Assertions.assertEquals(255, color.r());
        Assertions.assertEquals(255, color.g());
        Assertions.assertEquals(255, color.b());
        Assertions.assertEquals(255, color.alpha());

    }

    @Test
    public void testColorRepresentationsAndSomethingElseWithAlpha() {

        KColor color = new KColor(255, 255, 255, 128);

        float[] normalized = color.normalized();
        Assertions.assertEquals(1.0f, normalized[0]);
        Assertions.assertEquals(1.0f, normalized[1]);
        Assertions.assertEquals(1.0f, normalized[2]);
        Assertions.assertEquals(0.5019608f, normalized[3]);

        Assertions.assertEquals(0x80FFFFFF, color.getAsInt());

        Assertions.assertEquals(255, color.r());
        Assertions.assertEquals(255, color.g());
        Assertions.assertEquals(255, color.b());
        Assertions.assertEquals(128, color.alpha());

    }

    @Test
    @SuppressWarnings({ "ConstantValue", "SimplifiableAssertion" })
    public void testEqualsAndHashCode() {

        KColor test = new KColor(255, 255, 255);
        Assertions.assertFalse(test.equals(null));
        Assertions.assertFalse(test.equals(1));

        Assertions.assertFalse(test.equals(KColor.BLACK));
        Assertions.assertFalse(test.equals(KColor.TRANSPARENT));
        Assertions.assertFalse(test.equals(KColor.CYAN));
        Assertions.assertFalse(test.equals(KColor.MAGENTA));
        Assertions.assertFalse(test.equals(KColor.YELLOW));
        Assertions.assertFalse(test.equals(KColor.RED));
        Assertions.assertFalse(test.equals(KColor.GREEN));
        Assertions.assertFalse(test.equals(KColor.BLUE));
        Assertions.assertTrue(test.equals(KColor.WHITE));
        Assertions.assertFalse(test.equals(new KColor(255, 255, 255, 0)));

        Assertions.assertEquals(Objects.hash(test.r(), test.g(), test.b(), test.alpha()), test.hashCode());

    }
}
