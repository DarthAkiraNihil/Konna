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

import io.github.darthakiranihil.konna.core.io.protocol.KClasspathProtocol;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KClassPathProtocolPositiveTests extends KStandardTestClass {

    private final KProtocol protocol;

    public KClassPathProtocolPositiveTests() {
        this.protocol = new KClasspathProtocol(ClassLoader.getSystemClassLoader());
    }


    @Test
    public void testResolveWithIncorrectSchema() {
        Assertions.assertNull(protocol.resolve("aboba134"));
    }

    @Test
    public void testResolveManyWithIncorrectSchema() {

        Assertions.assertEquals(0, protocol.resolveMany("abiba457", false).length);

    }
}
