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

package io.github.darthakiranihil.konna.level.property;

import io.github.darthakiranihil.konna.core.except.KInvalidArgumentException;
import io.github.darthakiranihil.konna.level.property.factory.*;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KPropertyFactoriesTests extends KStandardTestClass {

    @Test
    public void testPushFactoriesWithInvalidArguments() {

        Assertions.assertThrows(
            KInvalidArgumentException.class,
            () -> new KIntPropertyFactory().create("123")
        );
        Assertions.assertThrows(
            KInvalidArgumentException.class,
            () -> new KIntArrayPropertyFactory().create("123")
        );
        Assertions.assertThrows(
            KInvalidArgumentException.class,
            () -> new KFloatPropertyFactory().create("123")
        );
        Assertions.assertThrows(
            KInvalidArgumentException.class,
            () -> new KFloatArrayPropertyFactory().create("123")
        );
        Assertions.assertThrows(
            KInvalidArgumentException.class,
            () -> new KBooleanPropertyFactory().create("123")
        );
        Assertions.assertThrows(
            KInvalidArgumentException.class,
            () -> new KBooleanArrayPropertyFactory().create("123")
        );
        Assertions.assertThrows(
            KInvalidArgumentException.class,
            () -> new KStringPropertyFactory().create(1)
        );
        Assertions.assertThrows(
            KInvalidArgumentException.class,
            () -> new KStringArrayPropertyFactory().create(1)
        );

    }

}
