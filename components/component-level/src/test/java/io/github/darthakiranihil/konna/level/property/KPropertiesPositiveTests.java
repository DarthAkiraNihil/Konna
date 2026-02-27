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

import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KPropertiesPositiveTests extends KStandardTestClass {

    @Test
    public void testSetValue() {

        var ip = new KIntTileProperty(1);
        var fp = new KFloatTileProperty(1.0f);
        var bp = new KBooleanTileProperty(true);
        var sp = new KStringTileProperty("123");

        ip.setValue(2);
        fp.setValue(2.0f);
        bp.setValue(false);
        sp.setValue("124");

        Assertions.assertEquals(2, ip.getValue());
        Assertions.assertEquals(2.0f, fp.getValue());
        Assertions.assertFalse(bp.getValue());
        Assertions.assertEquals("124", sp.getValue());

    }
}
