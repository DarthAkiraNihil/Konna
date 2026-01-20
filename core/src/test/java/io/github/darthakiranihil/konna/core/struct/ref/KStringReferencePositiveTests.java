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

package io.github.darthakiranihil.konna.core.struct.ref;

import io.github.darthakiranihil.konna.core.test.KStandardTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class KStringReferencePositiveTests extends KStandardTestClass {

    private final String initialValue;
    private final String newValue;

    public KStringReferencePositiveTests() {
        this.initialValue = "Aboba";
        this.newValue = "Abiba";
    }

    @Test
    public void testProcessReference() {
        KStringReference ref = new KStringReference(this.initialValue);
        this.process(ref);
        Assertions.assertEquals(this.newValue, ref.get());
    }

    @Test
    public void testCreateFromAnother() {
        KStringReference ref = new KStringReference(new KStringReference(this.initialValue));
        Assertions.assertEquals(this.initialValue, ref.get());
    }

    @Test
    public void testSetFromAnother() {
        KStringReference ref = new KStringReference(this.initialValue);
        ref.set(new KStringReference(this.newValue));
        Assertions.assertEquals(this.newValue, ref.get());
    }

    @Test
    public void testClone() {
        KStringReference ref = new KStringReference(this.initialValue);
        KStringReference clone = ref.clone();
        clone.set(this.newValue);
        Assertions.assertNotEquals(ref.get(), clone.get());
    }

    @Test
    @SuppressWarnings({ "ConstantValue", "SimplifiableAssertion" })
    public void testEqualsAndHashCode() {

        KStringReference primal = new KStringReference(this.initialValue);
        KStringReference diff = new KStringReference(this.newValue);
        KStringReference equal = new KStringReference(primal);

        Assertions.assertFalse(primal.equals(null));
        Assertions.assertFalse(primal.equals(1));
        Assertions.assertFalse(primal.equals(diff));

        Assertions.assertTrue(primal.equals(equal));
        Assertions.assertEquals(primal, primal);
        Assertions.assertEquals(primal.hashCode(), equal.hashCode());

    }

    @Test
    public void testCreateWithDefault() {
        KStringReference ref = new KStringReference();
        Assertions.assertEquals("", ref.get());
    }

    @Test
    public void testToString() {
        KStringReference ref = new KStringReference(this.initialValue);
        Assertions.assertEquals(this.initialValue, ref.toString());
    }

    private void process(final KStringReference ref) {
        ref.set(this.newValue);
    }
    
}
