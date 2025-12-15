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

package io.github.darthakiranihil.konna.core;

import io.github.darthakiranihil.konna.core.app.KApplicationArgument;
import io.github.darthakiranihil.konna.core.except.KException;
import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class KonnaPositiveTests extends KStandardTestClass {

    private final Method shutdown;
    private final Field hypervisorThread;

    public KonnaPositiveTests() {

        try {
            this.shutdown = Konna.class.getDeclaredMethod("shutdown");
            this.hypervisorThread = Konna.class.getDeclaredField("hypervisorThread");

            this.shutdown.setAccessible(true);
            this.hypervisorThread.setAccessible(true);
        } catch (Throwable e) {
            throw new KException(e);
        }

    }

    @Test
    public void testStartKonna() {

        try {
            Konna konnaWithOnlyDefaultArgs = new Konna(new String[0]);
            konnaWithOnlyDefaultArgs.run();
            Assertions.assertNotNull(hypervisorThread.get(konnaWithOnlyDefaultArgs));
            Assertions.assertDoesNotThrow(() -> this.shutdown.invoke(konnaWithOnlyDefaultArgs));

            Konna konnaWithCustomArgs = new Konna(new String[0], List.of(new KApplicationArgument("a", "aaa")));
            konnaWithCustomArgs.run();
            Assertions.assertNotNull(hypervisorThread.get(konnaWithCustomArgs));
            Assertions.assertDoesNotThrow(() -> this.shutdown.invoke(konnaWithCustomArgs));

        } catch (Throwable e) {
            throw new KException(e);
        }

    }
}
