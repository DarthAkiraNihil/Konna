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

package io.github.darthakiranihil.konna.graphics.image;

import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

public class KImagePositiveTests extends KStandardTestClass {

    @Test
    @SuppressWarnings({ "ConstantValue", "SimplifiableAssertion" })
    public void testEqualsAndHashCodeWithClone() {

        KImage img = new KImage(new byte[0], 0, 0);

        KImage diff1 = new KImage(ByteBuffer.allocate(1), 0, 0);
        KImage diff2 = new KImage(new byte[0], 1, 0);
        KImage diff3 = new KImage(new byte[0], 0, 1);

        Assertions.assertFalse(img.equals(null));
        Assertions.assertFalse(img.equals(1));

        Assertions.assertFalse(img.equals(diff1));
        Assertions.assertFalse(img.equals(diff2));
        Assertions.assertFalse(img.equals(diff3));

        KImage equal = new KImage(new byte[0], 0, 0);
        KImage clone = img.copy();

        Assertions.assertTrue(img.equals(equal));
        Assertions.assertTrue(img.equals(clone));
        Assertions.assertEquals(img.rawData(), clone.rawData());

        Assertions.assertEquals(img.hashCode(), equal.hashCode());
        Assertions.assertEquals(img.hashCode(), clone.hashCode());

    }
}
