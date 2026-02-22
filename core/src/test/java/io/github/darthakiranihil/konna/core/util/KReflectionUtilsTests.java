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

package io.github.darthakiranihil.konna.core.util;

import io.github.darthakiranihil.konna.core.Konna;
import io.github.darthakiranihil.konna.core.except.KException;
import io.github.darthakiranihil.konna.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class KReflectionUtilsTests extends KStandardTestClass {

    private static final class TestClass {

        private int property;

        public TestClass(int property) {
            this.property = property;
        }

        public int getProperty() {
            return this.property;
        }

        public void setProperty(int property) {
            this.property = property;
        }

        private void unaccessible() {

        }
    }

    @Test
    public void testGetField() {
        TestClass test = new TestClass(1);
        Assertions.assertEquals(
            1,
            KReflectionUtils.getField(TestClass.class, test, "property", Integer.class)
        );
    }

    @Test
    public void testGetFieldOfAnotherClass() {
        TestClass test = new TestClass(1);
        Assertions.assertNull(
            KReflectionUtils.getField(Konna.class, test, "property", int.class)
        );
    }

    @Test
    public void testGetNonExistentField() {
        TestClass test = new TestClass(1);
        Assertions.assertNull(
            KReflectionUtils.getField(TestClass.class, test, "property1", int.class)
        );
    }

    @Test
    public void testGetMethod() {

        TestClass test = new TestClass(1);
        Method setter = KReflectionUtils.getMethod(TestClass.class, "setProperty", int.class);
        Assertions.assertNotNull(setter);
        KReflectionUtils.invokeMethod(setter, test, 2);
        Assertions.assertEquals(2, test.getProperty());

    }

    @Test
    public void testInvokeMethodWithIllegalAccess() {

        TestClass test = new TestClass(1);
        try {
            Assertions.assertThrows(
                KException.class,
                () -> KReflectionUtils.invokeMethod(TestClass.class.getDeclaredMethod("unaccessible"), test)
            );
        } catch (Exception e) {
            Assertions.fail(e);
        }

    }

    @Test
    public void testGetNonExistentMethod() {
        Assertions.assertNull(
            KReflectionUtils.getMethod(TestClass.class, "setProperty1", int.class)
        );
    }

    @Test
    public void testGetGetter() {

        TestClass test = new TestClass(1);
        Method getter = KReflectionUtils.getGetter(TestClass.class, "property");
        Assertions.assertNotNull(getter);
        Assertions.assertEquals(1, KReflectionUtils.invokeMethod(getter, test));

    }

    @Test
    public void testGetSetter() {
        TestClass test = new TestClass(1);
        Method setter = KReflectionUtils.getSetter(TestClass.class, "property", int.class);
        Assertions.assertNotNull(setter);
        KReflectionUtils.invokeMethod(setter, test, 2);
        Assertions.assertEquals(2, test.getProperty());
    }
}
