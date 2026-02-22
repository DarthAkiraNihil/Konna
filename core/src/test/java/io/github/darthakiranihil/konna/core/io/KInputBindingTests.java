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

package io.github.darthakiranihil.konna.core.io;

import io.github.darthakiranihil.konna.core.io.control.KInputBinding;
import io.github.darthakiranihil.konna.core.io.control.KInputData;
import io.github.darthakiranihil.konna.core.io.control.KKey;
import io.github.darthakiranihil.konna.core.io.control.KKeyInputData;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KInputBindingTests extends KStandardTestClass {

    private static final class TestInputData implements KInputData {

    }

    @Test
    public void testOfKeySuccess() {

        KInputBinding binding = KInputBinding.ofKey("aboba", KKey.A);

        Assertions.assertEquals("aboba", binding.getActionName());
        Assertions.assertTrue(binding.isActionPerformed(
            new KKeyInputData(KKey.A, false, false, false, false, false)
        ));

    }

    @Test
    public void testOfKeyActionIsNotPerformed() {

        KInputBinding binding = KInputBinding.ofKey("aboba", KKey.A);

        Assertions.assertFalse(binding.isActionPerformed(
            new KKeyInputData(KKey.B, false, false, false, false, false)
        ));
        Assertions.assertFalse(binding.isActionPerformed(new TestInputData()));

    }
}
